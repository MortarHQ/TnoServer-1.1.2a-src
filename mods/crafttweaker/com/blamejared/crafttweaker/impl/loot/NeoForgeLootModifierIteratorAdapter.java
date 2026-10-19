package com.blamejared.crafttweaker.impl.loot;

import java.util.Iterator;
import java.util.function.Function;

final class NeoForgeLootModifierIteratorAdapter<T, U> implements Iterator<T> {
   private final Iterator<U> wrapped;
   private final Function<U, T> converter;

   private NeoForgeLootModifierIteratorAdapter(Iterator<U> wrapped, Function<U, T> converter) {
      this.wrapped = wrapped;
      this.converter = converter;
   }

   static <T, U> Iterator<T> adapt(Iterator<U> iterator, Function<U, T> converter) {
      return new NeoForgeLootModifierIteratorAdapter<>(iterator, converter);
   }

   @Override
   public void remove() {
      this.wrapped.remove();
   }

   @Override
   public boolean hasNext() {
      return this.wrapped.hasNext();
   }

   @Override
   public T next() {
      return this.converter.apply(this.wrapped.next());
   }
}
