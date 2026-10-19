package net.marmar.enhanced_ore_variety.data.recipe;

import java.util.concurrent.CompletableFuture;
import net.marmar.enhanced_ore_variety.data.tag.EOVTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

public class EOVRecipeProvider extends RecipeProvider implements IConditionBuilder {
   public EOVRecipeProvider(PackOutput output, CompletableFuture<Provider> registries) {
      super(output, registries);
   }

   protected void buildRecipes(RecipeOutput consumer) {
      smeltRecipe(consumer, ItemTags.COAL_ORES, Items.COAL, 0.1F);
      smeltRecipe(consumer, ItemTags.COPPER_ORES, Items.COPPER_INGOT, 0.7F);
      smeltRecipe(consumer, ItemTags.IRON_ORES, Items.IRON_INGOT, 0.7F);
      smeltRecipe(consumer, ItemTags.GOLD_ORES, Items.GOLD_INGOT, 1.0F);
      smeltRecipe(consumer, EOVTags.Items.QUARTZ_ORES, Items.QUARTZ, 1.0F);
      smeltRecipe(consumer, ItemTags.LAPIS_ORES, Items.LAPIS_LAZULI, 0.2F);
      smeltRecipe(consumer, ItemTags.REDSTONE_ORES, Items.REDSTONE, 0.7F);
      smeltRecipe(consumer, ItemTags.EMERALD_ORES, Items.EMERALD, 1.0F);
      smeltRecipe(consumer, ItemTags.DIAMOND_ORES, Items.DIAMOND, 1.0F);
   }

   protected static void smeltRecipe(RecipeOutput pOutput, TagKey<Item> pIngredient, ItemLike pResult, float pExperience) {
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(pIngredient), RecipeCategory.MISC, pResult, pExperience, 200)
         .unlockedBy(pIngredient.toString(), has(pIngredient))
         .save(pOutput, "enhanced_ore_variety:" + getItemName(pResult) + "_from_smelting");
      SimpleCookingRecipeBuilder.blasting(Ingredient.of(pIngredient), RecipeCategory.MISC, pResult, pExperience, 100)
         .unlockedBy(pIngredient.toString(), has(pIngredient))
         .save(pOutput, "enhanced_ore_variety:" + getItemName(pResult) + "_from_blasting");
   }
}
