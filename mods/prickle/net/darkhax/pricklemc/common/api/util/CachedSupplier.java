package net.darkhax.pricklemc.common.api.util;

import java.util.function.Consumer;
import java.util.function.Supplier;
import org.jetbrains.annotations.Nullable;

public class CachedSupplier<T> implements Supplier<T> {
   private final Supplier<T> delegate;
   private boolean cached = false;
   @Nullable
   private T cachedValue;

   protected CachedSupplier(Supplier<T> delegate) {
      this.delegate = delegate;
   }

   @Override
   public T get() {
      if (!this.isCached()) {
         this.cachedValue = this.delegate.get();
         this.cached = true;
      }

      return this.cachedValue;
   }

   public void invalidate() {
      this.cached = false;
      this.cachedValue = null;
   }

   public boolean isCached() {
      return this.cached;
   }

   public void ifCached(Consumer<T> consumer) {
      if (this.isCached()) {
         consumer.accept(this.get());
      }
   }

   public void ifPresent(Consumer<T> consumer) {
      if (this.cachedValue != null) {
         consumer.accept(this.get());
      }
   }

   public void apply(Consumer<T> consumer) {
      consumer.accept(this.get());
   }

   public <X> CachedSupplier<X> cast() {
      return (CachedSupplier<X>)this;
   }

   public static <T> CachedSupplier<T> singleton(T singleton) {
      return cache(() -> singleton);
   }

   public static <T> CachedSupplier<T> cache(Supplier<T> delegate) {
      return new CachedSupplier<>(delegate);
   }
}
