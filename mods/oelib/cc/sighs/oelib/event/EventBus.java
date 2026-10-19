package cc.sighs.oelib.event;

import cc.sighs.oelib.platform.Platform;
import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.annotations.NotNull;

public final class EventBus {
   private static final EventBus INSTANCE = new EventBus();
   private static final EventBus.Handler[] NO_HANDLERS = new EventBus.Handler[0];
   private static final EventPriority[] PRIORITIES = EventPriority.values();
   private final Map<Class<? extends Event>, EventBus.Handler[]> directHandlers = new ConcurrentHashMap<>();
   private final AtomicInteger cacheEpoch = new AtomicInteger();
   private final ClassValue<EventBus.CachedDispatch> dispatch = new ClassValue<EventBus.CachedDispatch>() {
      protected EventBus.CachedDispatch computeValue(@NotNull Class<?> type) {
         return new EventBus.CachedDispatch();
      }
   };
   private volatile Executor asyncExecutor = ForkJoinPool.commonPool();

   private EventBus() {
   }

   public static EventBus getInstance() {
      return INSTANCE;
   }

   public static void register(Object listener) {
      INSTANCE.registerInternal(listener);
   }

   public static void register(Class<?> listenerClass) {
      INSTANCE.registerInternal(listenerClass, null);
   }

   public static void unregister(Object listener) {
      INSTANCE.unregisterInternal(listener);
   }

   public static void unregister(Class<?> listenerClass) {
      INSTANCE.unregisterInternal(listenerClass);
   }

   public static boolean post(Event event) {
      INSTANCE.postInternal(event);
      return event instanceof CancellableEvent cancellableEvent && cancellableEvent.isCanceled();
   }

   public static <E extends Event> CompletableFuture<E> postAsync(E event) {
      return INSTANCE.postAsyncInternal(event);
   }

   public static void setAsyncExecutor(Executor executor) {
      INSTANCE.setAsyncExecutorInternal(executor);
   }

   private static EventBus.Handler[] insertSorted(EventBus.Handler[] existing, EventBus.Handler handler) {
      if (existing != null && existing.length != 0) {
         int idx = existing.length;

         for (int i = 0; i < existing.length; i++) {
            if (shouldComeBefore(handler, existing[i])) {
               idx = i;
               break;
            }
         }

         EventBus.Handler[] out = new EventBus.Handler[existing.length + 1];
         System.arraycopy(existing, 0, out, 0, idx);
         out[idx] = handler;
         System.arraycopy(existing, idx, out, idx + 1, existing.length - idx);
         return out;
      } else {
         return new EventBus.Handler[]{handler};
      }
   }

   private static boolean shouldComeBefore(EventBus.Handler a, EventBus.Handler b) {
      int prioCmp = Integer.compare(a.priority.ordinal(), b.priority.ordinal());
      return prioCmp < 0;
   }

   private void registerInternal(Object listener) {
      Objects.requireNonNull(listener);
      Class<?> listenerClass = listener.getClass();
      this.registerInternal(listenerClass, listener);
   }

   private void registerInternal(Class<?> listenerClass, Object listenerInstance) {
      Method[] methods = listenerClass.getDeclaredMethods();

      for (Method method : methods) {
         Subscribe subscribe = method.getAnnotation(Subscribe.class);
         if (subscribe != null) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == 1 && void.class.equals(method.getReturnType())) {
               Class<?> eventType = parameterTypes[0];
               if (Event.class.isAssignableFrom(eventType)) {
                  boolean isStatic = Modifier.isStatic(method.getModifiers());
                  if (isStatic || listenerInstance != null) {
                     EventBus.Invoker invoker = this.createInvoker(listenerInstance, method, isStatic);
                     if (invoker != null) {
                        EventSide side = subscribe.side();
                        if ((side != EventSide.CLIENT || !Platform.isServer()) && (side != EventSide.SERVER || !Platform.isClient())) {
                           EventBus.Handler handler = new EventBus.Handler(
                              listenerInstance != null ? listenerInstance : listenerClass, invoker, subscribe.priority(), subscribe.receiveCanceled()
                           );
                           this.addHandler(eventType.asSubclass(Event.class), handler);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void unregisterInternal(Object key) {
      boolean changed = false;

      for (Entry<Class<? extends Event>, EventBus.Handler[]> entry : this.directHandlers.entrySet()) {
         EventBus.Handler[] existing = entry.getValue();
         int remainingCount = 0;

         for (EventBus.Handler value : existing) {
            if (!value.owner.equals(key)) {
               remainingCount++;
            }
         }

         if (remainingCount != existing.length) {
            changed = true;
            if (remainingCount == 0) {
               this.directHandlers.remove(entry.getKey());
            } else {
               EventBus.Handler[] remaining = new EventBus.Handler[remainingCount];
               int idx = 0;

               for (EventBus.Handler handler : existing) {
                  if (!handler.owner.equals(key)) {
                     remaining[idx++] = handler;
                  }
               }

               this.directHandlers.put(entry.getKey(), remaining);
            }
         }
      }

      if (changed) {
         this.cacheEpoch.incrementAndGet();
      }
   }

   public <E extends Event> E postInternal(E event) {
      if (event == null) {
         return null;
      }

      Class<? extends Event> eventClass = (Class<? extends Event>)event.getClass();
      EventBus.Handler[] allHandlers = this.getDispatchHandlers(eventClass);
      if (allHandlers.length == 0) {
         return event;
      }

      if (event instanceof CancellableEvent cancellableEvent) {
         for (EventBus.Handler handler : allHandlers) {
            if (!cancellableEvent.isCanceled() || handler.receiveCanceled) {
               handler.invoker.invoke(event);
            }
         }

         return event;
      } else {
         for (EventBus.Handler allHandler : allHandlers) {
            allHandler.invoker.invoke(event);
         }

         return event;
      }
   }

   private <E extends Event> CompletableFuture<E> postAsyncInternal(E event) {
      if (event instanceof CancellableEvent) {
         throw new IllegalStateException(
            "postAsync does not support CancellableEvent. Use post() on main thread or design immutable, non-cancellable events for async."
         );
      }

      Executor executor = this.asyncExecutor;
      return CompletableFuture.supplyAsync(() -> {
         this.postInternal(event);
         return event;
      }, executor);
   }

   private void setAsyncExecutorInternal(Executor executor) {
      this.asyncExecutor = Objects.requireNonNull(executor);
   }

   private EventBus.Handler[] getDispatchHandlers(Class<? extends Event> eventClass) {
      EventBus.CachedDispatch cached = this.dispatch.get(eventClass);
      int epoch = this.cacheEpoch.get();
      if (cached.epoch == epoch) {
         return cached.handlers;
      }

      synchronized (cached) {
         if (cached.epoch == epoch) {
            return cached.handlers;
         }

         EventBus.Handler[] computed = this.computeHandlers(eventClass);
         cached.handlers = computed;
         cached.epoch = epoch;
         return computed;
      }
   }

   private EventBus.Handler[] computeHandlers(Class<? extends Event> eventClass) {
      ArrayList<EventBus.Handler>[] buckets = new ArrayList[PRIORITIES.length];
      Set<Class<?>> visited = Collections.newSetFromMap(new IdentityHashMap<>());
      ArrayDeque<Class<?>> stack = new ArrayDeque<>();
      stack.push(eventClass);

      while (!stack.isEmpty()) {
         Class<?> type = stack.pop();
         if (type != null && visited.add(type)) {
            if (Event.class.isAssignableFrom(type)) {
               Class<? extends Event> evtType = type.asSubclass(Event.class);
               EventBus.Handler[] direct = this.directHandlers.get(evtType);
               if (direct != null) {
                  for (EventBus.Handler handler : direct) {
                     int bucketIndex = handler.priority.ordinal();
                     ArrayList<EventBus.Handler> bucket = buckets[bucketIndex];
                     if (bucket == null) {
                        bucket = new ArrayList<>();
                        buckets[bucketIndex] = bucket;
                     }

                     bucket.add(handler);
                  }
               }
            }

            Class<?> superClass = type.getSuperclass();
            if (superClass != null && Event.class.isAssignableFrom(superClass)) {
               stack.push(superClass);
            }

            Class<?>[] interfaces = type.getInterfaces();

            for (Class<?> iface : interfaces) {
               if (Event.class.isAssignableFrom(iface)) {
                  stack.push(iface);
               }
            }
         }
      }

      int total = 0;

      for (List<EventBus.Handler> bucket : buckets) {
         if (bucket != null) {
            total += bucket.size();
         }
      }

      if (total == 0) {
         return NO_HANDLERS;
      }

      EventBus.Handler[] out = new EventBus.Handler[total];
      int idx = 0;

      for (int prio = 0; prio < PRIORITIES.length; prio++) {
         int bucketIndex = prio;
         List<EventBus.Handler> bucket = buckets[bucketIndex];
         if (bucket != null) {
            for (EventBus.Handler handler : bucket) {
               out[idx++] = handler;
            }
         }
      }

      return out;
   }

   private void addHandler(Class<? extends Event> eventType, EventBus.Handler handler) {
      this.directHandlers.compute(eventType, (type, existing) -> insertSorted(existing, handler));
      this.cacheEpoch.incrementAndGet();
   }

   private EventBus.Invoker createInvoker(Object listener, Method method, boolean isStatic) {
      try {
         method.setAccessible(true);
         Lookup baseLookup = MethodHandles.lookup();
         Lookup lookup = MethodHandles.privateLookupIn(method.getDeclaringClass(), baseLookup);
         MethodHandle handle = lookup.unreflect(method);
         if (!isStatic) {
            handle = handle.bindTo(listener);
         }

         MethodType invokedType = MethodType.methodType(EventBus.Invoker.class);
         MethodType samMethodType = MethodType.methodType(void.class, Event.class);
         MethodType implMethodType = handle.type();
         CallSite site = LambdaMetafactory.metafactory(lookup, "invoke", invokedType, samMethodType, handle, implMethodType);
         return (Object)site.getTarget().invokeExact() instanceof EventBus.Invoker invoker ? invoker : null;
      } catch (Throwable t) {
         return event -> {
            try {
               if (isStatic) {
                  method.invoke(null, event);
               } else {
                  method.invoke(listener, event);
               }
            } catch (Exception var5x) {
            }
         };
      }
   }

   private static final class CachedDispatch {
      private volatile int epoch = -1;
      private volatile EventBus.Handler[] handlers = EventBus.NO_HANDLERS;
   }

   private record Handler(Object owner, EventBus.Invoker invoker, EventPriority priority, boolean receiveCanceled) {
   }

   @FunctionalInterface
   private interface Invoker {
      void invoke(Event var1);
   }
}
