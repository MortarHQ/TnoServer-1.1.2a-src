package cc.sighs.oelib.registry;

import cc.sighs.oelib.registry.action.ListenAction;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public final class RegisterSupplier<T> implements Supplier<T> {
   private final ResourceKey<? extends Registry<T>> registryKey;
   private final ResourceLocation id;
   private final Supplier<? extends T> creator;
   private final AtomicReference<Supplier<? extends T>> getterRef = new AtomicReference<>();
   private final AtomicReference<T> instanceRef = new AtomicReference<>();
   private final List<Consumer<? super T>> listeners = new CopyOnWriteArrayList<>();
   private volatile boolean present = false;

   public RegisterSupplier(ResourceKey<? extends Registry<T>> registryKey, ResourceLocation id, Supplier<? extends T> creator) {
      this.registryKey = Objects.requireNonNull(registryKey, "registryKey");
      this.id = Objects.requireNonNull(id, "id");
      this.creator = Objects.requireNonNull(creator, "creator");
      this.getterRef.set(() -> {
         T current = this.instanceRef.get();
         if (current != null) {
            return current;
         } else {
            throw new IllegalStateException("Object is not registered yet: " + registryKey.location() + " " + id);
         }
      });
   }

   public ResourceLocation id() {
      return this.id;
   }

   public Supplier<? extends T> getCreator() {
      return this.creator;
   }

   public void setGetter(Supplier<? extends T> getter) {
      Objects.requireNonNull(getter, "getter");
      this.getterRef.set(() -> {
         T current = this.instanceRef.get();
         if (current != null) {
            return current;
         }

         try {
            T v = (T)getter.get();
            if (v != null) {
               return v;
            }
         } catch (Throwable var4) {
         }

         throw new IllegalStateException("Object is not registered yet: " + this.registryKey.location() + " " + this.id);
      });
   }

   public void bindInstance(T instance) {
      this.present = true;
      this.instanceRef.set(instance);
      this.getterRef.set(() -> instance);

      for (Consumer<? super T> l : this.listeners) {
         l.accept(instance);
      }

      this.listeners.clear();
   }

   @Override
   public T get() {
      return (T)this.getterRef.get().get();
   }

   public ResourceKey<? extends Registry<T>> registryKey() {
      return this.registryKey;
   }

   public void listen(Consumer<? super T> callback) {
      Objects.requireNonNull(callback, "callback");
      if (this.present) {
         callback.accept(this.get());
      } else {
         this.listeners.add(callback);
         RegistrationDispatcher.perform(new ListenAction<>(this.registryKey, this, callback));
      }
   }

   public boolean isPresent() {
      return this.present;
   }

   public ResourceKey<T> key() {
      return ResourceKey.create(this.registryKey, this.id);
   }

   public void ifPresent(Consumer<? super T> action) {
      if (this.isPresent()) {
         action.accept(this.get());
      }
   }

   public void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) {
      if (this.isPresent()) {
         action.accept(this.get());
      } else {
         emptyAction.run();
      }
   }

   public T orElse(T other) {
      return this.isPresent() ? this.get() : other;
   }

   public T orElseGet(Supplier<? extends T> supplier) {
      return this.isPresent() ? this.get() : supplier.get();
   }

   public void markPresent() {
      this.present = true;
   }
}
