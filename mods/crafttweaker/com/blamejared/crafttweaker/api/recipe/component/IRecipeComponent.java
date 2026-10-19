package com.blamejared.crafttweaker.api.recipe.component;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.gson.reflect.TypeToken;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@Document("vanilla/api/recipe/IRecipeComponent")
@ZenCodeType.Name("crafttweaker.api.recipe.IRecipeComponent")
@ZenRegister
public sealed interface IRecipeComponent<T> extends CommandStringDisplayable permits SimpleRecipeComponent, ListRecipeComponent {
   static <T> IRecipeComponent<T> find(ResourceLocation id) {
      return CraftTweakerAPI.getRegistry().findRecipeComponent(id);
   }

   static <T> IRecipeComponent<T> simple(ResourceLocation id, TypeToken<T> objectType, BiPredicate<T, T> matcher) {
      return new SimpleRecipeComponent<>(Objects.requireNonNull(id), Objects.requireNonNull(objectType), Objects.requireNonNull(matcher));
   }

   static <T> IRecipeComponent<T> composite(
      ResourceLocation id,
      TypeToken<T> objectType,
      BiPredicate<T, T> matcher,
      Function<T, Collection<T>> unwrappingFunction,
      Function<Collection<T>, T> wrapper
   ) {
      return new ListRecipeComponent<>(
         Objects.requireNonNull(id),
         Objects.requireNonNull(objectType),
         Objects.requireNonNull(matcher),
         Objects.requireNonNull(unwrappingFunction),
         Objects.requireNonNull(wrapper)
      );
   }

   ResourceLocation id();

   TypeToken<T> objectType();

   boolean match(T var1, T var2);

   Collection<T> unwrap(T var1);

   T wrap(Collection<T> var1);

   @Override
   default String getCommandString() {
      return "<recipecomponent:" + this.id() + ">";
   }
}
