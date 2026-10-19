package com.mortal_cultivation.registry;

import com.mortal_cultivation.data.recipe.AncientScholarTableRecipe;
import com.mortal_cultivation.data.recipe.TalismanRecipe;
import com.mortal_cultivation.recipe.AlchemyFurnaceRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RecipeRegistry {
   public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, "mortal_cultivation");
   public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, "mortal_cultivation");
   public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<TalismanRecipe>> TALISMAN_SERIALIZER = RECIPE_SERIALIZERS.register(
      "talisman_making", () -> TalismanRecipe.Serializer.INSTANCE
   );
   public static final DeferredHolder<RecipeType<?>, RecipeType<TalismanRecipe>> TALISMAN_TYPE = RECIPE_TYPES.register(
      "talisman_making", () -> TalismanRecipe.Type.INSTANCE
   );
   public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AncientScholarTableRecipe>> ANCIENT_SCHOLAR_TABLE_SERIALIZER = RECIPE_SERIALIZERS.register(
      "ancient_scholar_table", () -> AncientScholarTableRecipe.Serializer.INSTANCE
   );
   public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AlchemyFurnaceRecipe>> ALCHEMY_FURNACE_SERIALIZER = RECIPE_SERIALIZERS.register(
      "alchemy_furnace", () -> new AlchemyFurnaceRecipe.Serializer()
   );
   public static final DeferredHolder<RecipeType<?>, RecipeType<AlchemyFurnaceRecipe>> ALCHEMY_FURNACE_TYPE = RECIPE_TYPES.register(
      "alchemy_furnace", () -> AlchemyFurnaceRecipe.Type.INSTANCE
   );

   public static void register(IEventBus modEventBus) {
      RECIPE_SERIALIZERS.register(modEventBus);
      RECIPE_TYPES.register(modEventBus);
   }
}
