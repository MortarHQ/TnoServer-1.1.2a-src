package com.github.ysbbbbbb.kaleidoscopecookery.init;

import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.ChoppingBoardRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.FlexPotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.FlexStockpotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.MillstoneRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.PotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.SteamerRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.StockpotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.TeapotRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer.ChoppingBoardRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer.FlexPotRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer.FlexStockpotRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer.MillstoneRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer.PotRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer.RiceBowlRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer.SteamerRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer.StockpotRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer.TeapotRecipeSerializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber(bus = Bus.MOD)
public class ModRecipes {
   public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(
      BuiltInRegistries.RECIPE_SERIALIZER, "kaleidoscope_cookery"
   );
   public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> POT_SERIALIZER = RECIPE_SERIALIZERS.register("pot", PotRecipeSerializer::new);
   public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> FLEX_POT_SERIALIZER = RECIPE_SERIALIZERS.register(
      "flex_pot", FlexPotRecipeSerializer::new
   );
   public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> CHOPPING_BOARD_SERIALIZER = RECIPE_SERIALIZERS.register(
      "chopping_board", ChoppingBoardRecipeSerializer::new
   );
   public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> STOCKPOT_SERIALIZER = RECIPE_SERIALIZERS.register(
      "stockpot", StockpotRecipeSerializer::new
   );
   public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> FLEX_STOCKPOT_SERIALIZER = RECIPE_SERIALIZERS.register(
      "flex_stockpot", FlexStockpotRecipeSerializer::new
   );
   public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> MILLSTONE_SERIALIZER = RECIPE_SERIALIZERS.register(
      "millstone", MillstoneRecipeSerializer::new
   );
   public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> STEAMER_SERIALIZER = RECIPE_SERIALIZERS.register(
      "steamer", SteamerRecipeSerializer::new
   );
   public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> TEAPOT_SERIALIZER = RECIPE_SERIALIZERS.register("teapot", TeapotRecipeSerializer::new);
   public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> RICE_BOWL_SERIALIZER = RECIPE_SERIALIZERS.register(
      "rice_bowl", RiceBowlRecipeSerializer::new
   );
   public static RecipeType<PotRecipe> POT_RECIPE;
   public static RecipeType<FlexPotRecipe> FLEX_POT_RECIPE;
   public static RecipeType<ChoppingBoardRecipe> CHOPPING_BOARD_RECIPE;
   public static RecipeType<StockpotRecipe> STOCKPOT_RECIPE;
   public static RecipeType<FlexStockpotRecipe> FLEX_STOCKPOT_RECIPE;
   public static RecipeType<MillstoneRecipe> MILLSTONE_RECIPE;
   public static RecipeType<SteamerRecipe> STEAMER_RECIPE;
   public static RecipeType<TeapotRecipe> TEAPOT_RECIPE;

   @SubscribeEvent
   public static void register(RegisterEvent evt) {
      if (evt.getRegistryKey().equals(Registries.RECIPE_SERIALIZER)) {
         POT_RECIPE = RecipeType.simple(ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "pot"));
         FLEX_POT_RECIPE = RecipeType.simple(ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "flex_pot"));
         CHOPPING_BOARD_RECIPE = RecipeType.simple(ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "chopping_board"));
         STOCKPOT_RECIPE = RecipeType.simple(ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "stockpot"));
         FLEX_STOCKPOT_RECIPE = RecipeType.simple(ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "flex_stockpot"));
         MILLSTONE_RECIPE = RecipeType.simple(ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "millstone"));
         STEAMER_RECIPE = RecipeType.simple(ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "steamer"));
         TEAPOT_RECIPE = RecipeType.simple(ResourceLocation.fromNamespaceAndPath("kaleidoscope_cookery", "teapot"));
      }
   }
}
