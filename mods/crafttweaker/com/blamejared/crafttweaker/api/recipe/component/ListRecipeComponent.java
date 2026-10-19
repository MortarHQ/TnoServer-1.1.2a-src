package com.blamejared.crafttweaker.api.recipe.component;

import com.google.gson.reflect.TypeToken;
import java.util.Collection;
import java.util.function.BiPredicate;
import java.util.function.Function;
import net.minecraft.resources.ResourceLocation;

final class ListRecipeComponent<T> implements IRecipeComponent<T> {
   private final ResourceLocation id;
   private final TypeToken<T> objectType;
   private final BiPredicate<T, T> matcher;
   private final Function<T, Collection<T>> unwrappingFunction;
   private final Function<Collection<T>, T> wrapper;

   ListRecipeComponent(
      ResourceLocation id,
      TypeToken<T> objectType,
      BiPredicate<T, T> matcher,
      Function<T, Collection<T>> unwrappingFunction,
      Function<Collection<T>, T> wrapper
   ) {
      this.id = id;
      this.objectType = objectType;
      this.matcher = matcher;
      this.unwrappingFunction = unwrappingFunction;
      this.wrapper = wrapper;
   }

   @Override
   public ResourceLocation id() {
      return this.id;
   }

   @Override
   public TypeToken<T> objectType() {
      return this.objectType;
   }

   @Override
   public boolean match(T oracle, T object) {
      return this.matcher.test(oracle, object);
   }

   @Override
   public Collection<T> unwrap(T object) {
      return this.unwrappingFunction.apply(object);
   }

   @Override
   public T wrap(Collection<T> objects) {
      return this.wrapper.apply(objects);
   }

   @Override
   public String toString() {
      return "ListComponent[" + this.id() + "/" + this.objectType() + "]";
   }
}
