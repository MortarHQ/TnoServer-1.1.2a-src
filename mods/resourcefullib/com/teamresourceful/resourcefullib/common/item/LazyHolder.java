package com.teamresourceful.resourcefullib.common.item;

import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public sealed class LazyHolder<T> implements Supplier<T> permits LazyHolder.StaticHolder {
   protected final ResourceLocation id;
   protected final Registry<T> registry;
   protected T item;

   public LazyHolder(Registry<T> registry, ResourceLocation id) {
      this.registry = registry;
      this.id = id;
   }

   public static <R> LazyHolder<R> of(Registry<R> registry, ResourceLocation id) {
      return new LazyHolder((Registry<T>)registry, id);
   }

   public static <R> LazyHolder<R> of(Registry<R> registry, R value) {
      return new LazyHolder.StaticHolder<>(registry, value);
   }

   public ResourceLocation getId() {
      return this.id;
   }

   public Registry<T> getRegistry() {
      return this.registry;
   }

   @Override
   public T get() {
      if (this.item == null) {
         this.item = (T)this.registry
            .getOptional(this.id)
            .orElseThrow(() -> new IllegalStateException(this.registry.key() + ": " + this.id + " does not exist!"));
      }

      return this.item;
   }

   public static <T> Function<ResourceLocation, LazyHolder<T>> map(Registry<T> registry) {
      return id -> new LazyHolder<>(registry, id);
   }

   private static final class StaticHolder<T> extends LazyHolder<T> {
      public StaticHolder(Registry<T> registry, T value) {
         super(registry, registry.getKey(value));
         this.item = value;
      }
   }
}
