package com.blamejared.crafttweaker.impl.registry.recipe;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.StringData;
import com.blamejared.crafttweaker.api.data.op.IDataOps;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.mojang.serialization.DataResult;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

final class DefaultRecipeHandler implements IRecipeHandler<Recipe<?>> {
   static final DefaultRecipeHandler INSTANCE = new DefaultRecipeHandler();

   @Override
   public String dumpToCommandString(IRecipeManager<? super Recipe<?>> manager, RegistryAccess registryAccess, RecipeHolder<Recipe<?>> holder) {
      Recipe<?> recipe = holder.value();
      Optional<ResourceLocation> serializerKey = registryAccess.registry(Registries.RECIPE_SERIALIZER)
         .map(recipeSerializers -> recipeSerializers.getKey(recipe.getSerializer()));
      Supplier<String> fallback = () -> {
         String ingredients = recipe.getIngredients()
            .stream()
            .map(IIngredient::fromIngredient)
            .map(IIngredient::getCommandString)
            .collect(Collectors.joining(", "));
         return String.format(
            "~~ Recipe name: %s, Outputs: %s, Inputs: [%s], Recipe Class: %s, Recipe Serializer: %s ~~",
            holder.id(),
            ItemStackUtil.getCommandString(recipe.getResultItem(registryAccess)),
            ingredients,
            recipe.getClass().getName(),
            BuiltInRegistries.RECIPE_SERIALIZER.getKey(recipe.getSerializer())
         );
      };
      if (serializerKey.isEmpty()) {
         return fallback.get();
      }

      try {
         MapData data = new MapData();
         data.put("type", new StringData(serializerKey.get().toString()));
         DataResult<IData> result = recipe.getSerializer()
            .codec()
            .codec()
            .encode((Recipe)GenericUtil.uncheck(recipe), IDataOps.INSTANCE.withRegistryAccess(), data);
         return result.result()
            .map(
               it -> "<recipetype:%s>.addJsonRecipe(\"%s\", %s);"
                  .formatted(BuiltInRegistries.RECIPE_TYPE.getKey(manager.getRecipeType()), holder.id(), it.toString())
            )
            .orElseGet(fallback);
      } catch (Exception var9) {
         return fallback.get();
      }
   }

   @Override
   public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super Recipe<?>> manager, Recipe<?> firstRecipe, U secondRecipe) {
      return false;
   }

   @Override
   public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super Recipe<?>> manager, RegistryAccess registryAccess, Recipe<?> recipe) {
      return Optional.empty();
   }

   @Override
   public Optional<Recipe<?>> recompose(IRecipeManager<? super Recipe<?>> manager, RegistryAccess registryAccess, IDecomposedRecipe recipe) {
      return Optional.empty();
   }
}
