package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.event.Phase;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.reflect.TypeToken;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.apache.logging.log4j.Logger;

abstract class PhasedEventBus<T> implements IEventBus<T> {
   private static final Logger LOGGER = CraftTweakerAPI.getLogger("Event");
   private static final Phase[] PHASES = Phase.values();
   private final TypeToken<T> eventType;
   private final IEventBusWire wire;
   private final ArrayBackedDispatcher<T>[] phasedDispatchers;

   protected PhasedEventBus(TypeToken<T> eventType, IEventBusWire wire) {
      this.eventType = Objects.requireNonNull(eventType, "eventType");
      this.wire = Objects.requireNonNull(wire, "wire");
      this.phasedDispatchers = GenericUtil.uncheck(new ArrayBackedDispatcher[PHASES.length]);
   }

   @Override
   public final IHandlerToken<T> registerHandler(Consumer<T> handler) {
      return this.registerHandler(Phase.NORMAL, handler);
   }

   @Override
   public final IHandlerToken<T> registerHandler(Phase phase, Consumer<T> handler) {
      return this.registerHandler(phase, false, handler);
   }

   @Override
   public final IHandlerToken<T> registerHandler(boolean listenToCanceled, Consumer<T> handler) {
      return this.registerHandler(Phase.NORMAL, listenToCanceled, handler);
   }

   @Override
   public final IHandlerToken<T> registerHandler(Phase phase, boolean listenToCanceled, Consumer<T> handler) {
      Objects.requireNonNull(phase, "phase");
      Objects.requireNonNull(handler, "handler");
      return this.modifyHandlers(() -> {
         ArrayBackedDispatcher<T> dispatcher = this.dispatcherAt(phase);
         IHandlerToken<T> token = this.registerPhased(dispatcher, listenToCanceled, handler);
         return new PhasedEventBus.PhasedHandlerToken<>(token, phase, this);
      });
   }

   @Override
   public final void unregisterHandler(IHandlerToken<T> token) {
      if (token instanceof PhasedEventBus.PhasedHandlerToken<T> phasedToken) {
         if (phasedToken.bus() != this) {
            throw new IllegalArgumentException("Cross-bus token referencing is disallowed");
         }

         this.modifyHandlers(() -> {
            this.dispatcherAt(phasedToken.phase()).unregister(phasedToken.delegate());
            return null;
         });
      } else {
         throw new IllegalArgumentException("Unknown token");
      }
   }

   @Override
   public T post(T event) {
      return this.postCatching(event, this::onException);
   }

   @Override
   public final T post(Phase phase, T event) {
      return this.postCatching(phase, event, this::onException);
   }

   @Override
   public T postCatching(T event, Consumer<BusHandlingException> exceptionHandler) {
      return this.dispatch(event, exceptionHandler, this.phasedDispatchers);
   }

   @Override
   public final T postCatching(Phase phase, T event, Consumer<BusHandlingException> exceptionHandler) {
      return this.dispatch(event, exceptionHandler, this.phasedDispatchers[phase.ordinal()]);
   }

   @Override
   public final TypeToken<T> eventType() {
      return this.eventType;
   }

   protected abstract IHandlerToken<T> registerPhased(ArrayBackedDispatcher<T> var1, boolean var2, Consumer<T> var3);

   protected final void wire() {
      this.wire.registerBusForDispatch(this.eventType(), this);
   }

   private <R> R dispatch(R event, Consumer<BusHandlingException> exceptionHandler, ArrayBackedDispatcher<R> dispatcher) {
      return this.queryHandlers(event, exceptionHandler, it -> {
         if (dispatcher != null) {
            dispatcher.dispatch(it);
         }
      });
   }

   @SafeVarargs
   private <R> R dispatch(R event, Consumer<BusHandlingException> exceptionHandler, ArrayBackedDispatcher<R>... dispatchers) {
      return this.queryHandlers(event, exceptionHandler, it -> {
         for (ArrayBackedDispatcher<R> dispatcher : dispatchers) {
            if (dispatcher != null) {
               dispatcher.dispatch(it);
            }
         }
      });
   }

   private <R> R modifyHandlers(Supplier<R> block) {
      return block.get();
   }

   private <R> R queryHandlers(R event, Consumer<BusHandlingException> exceptionHandler, Consumer<R> block) {
      try {
         block.accept(event);
         return event;
      } catch (BusHandlingException e) {
         exceptionHandler.accept(e);
         return event;
      }
   }

   private ArrayBackedDispatcher<T> dispatcherAt(Phase phase) {
      int phaseIndex = phase.ordinal();
      ArrayBackedDispatcher<T> dispatcher = this.phasedDispatchers[phaseIndex];
      if (dispatcher != null) {
         return dispatcher;
      }

      ArrayBackedDispatcher<T> newDispatcher = new ArrayBackedDispatcher<>();
      this.phasedDispatchers[phaseIndex] = newDispatcher;
      return newDispatcher;
   }

   private void onException(BusHandlingException e) {
      LOGGER.error(() -> "An error occurred while attempting to dispatch an event of type " + this.eventType, e.original());
   }

   private record PhasedHandlerToken<T>(IHandlerToken<T> delegate, Phase phase, IEventBus<T> bus) implements IHandlerToken<T> {
   }
}
