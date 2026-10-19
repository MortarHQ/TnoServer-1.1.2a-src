package com.cerbon.cerbons_api.api.registry;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface ResourcefulRegistry<T> {
   <I extends T> RegistryEntry<I> register(String var1, Supplier<I> var2);

   Collection<RegistryEntry<T>> getEntries();

   default Stream<RegistryEntry<T>> stream() {
      return this.getEntries().stream();
   }

   default Stream<T> boundStream() {
      return this.stream().map(RegistryEntry::get);
   }

   void register();
}
