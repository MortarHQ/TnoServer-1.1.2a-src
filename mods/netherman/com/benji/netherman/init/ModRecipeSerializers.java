package com.benji.netherman.init;

import com.benji.netherman.common.item.crafting.CrimsonArrowRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeSerializers {
   public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, "netherman");
   public static final DeferredHolder<RecipeSerializer<?>, SimpleCraftingRecipeSerializer<?>> CRIMSON_ARROW_COATING = RECIPE_SERIALIZERS.register(
      "crimson_arrow_coating", () -> new SimpleCraftingRecipeSerializer(CrimsonArrowRecipe::new)
   );

   public static void init(IEventBus bus) {
      RECIPE_SERIALIZERS.register(bus);
   }
}
