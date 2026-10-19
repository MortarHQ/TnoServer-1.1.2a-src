package dev.ftb.mods.ftblibrary.util;

import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

public class Lazy<T> implements Supplier<T> {
   private final Supplier<T> valueSupplier;
   private transient T value;
   private transient volatile boolean initialized;

   private Lazy(Supplier<@NotNull T> valueSupplier) {
      this.valueSupplier = valueSupplier;
   }

   public static <T> Lazy<T> of(Supplier<@NotNull T> valueSupplier) {
      return new Lazy<>(valueSupplier);
   }

   @Override
   public T get() {
      if (!this.initialized) {
         synchronized (this) {
            if (!this.initialized) {
               this.value = this.valueSupplier.get();
               this.initialized = true;
            }
         }
      }

      return this.value;
   }

   public synchronized void invalidate() {
      this.initialized = false;
      this.value = null;
   }

   @Override
   public String toString() {
      return "Lazy(" + (this.initialized ? this.value : "uninitialized") + ")";
   }
}
