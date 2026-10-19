package fzzyhmstrs.emi_loot.client;

import java.util.function.Supplier;

public final class InitializedSupplier<T> implements Supplier<T> {
   private final Supplier<T> delegate;
   private T value;

   public InitializedSupplier(Supplier<T> delegate) {
      this.delegate = delegate;
   }

   public void init() {
      this.value = this.delegate.get();
   }

   @Override
   public T get() {
      return this.value;
   }
}
