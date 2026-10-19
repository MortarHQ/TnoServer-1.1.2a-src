package gardensofthedead.neoforge.datagen;

import gardensofthedead.neoforge.datagen.registry.ModBlockFamilies;
import gardensofthedead.registry.ModBlocks;
import gardensofthedead.registry.ModTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider {
   public RecipeProvider(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
      super(packOutput, lookupProvider);
   }

   protected void buildRecipes(RecipeOutput output) {
      this.generateForEnabledBlockFamilies(output, FeatureFlags.VANILLA_SET);
      this.addCraftingRecipes(output);
   }

   protected void generateForEnabledBlockFamilies(RecipeOutput consumer, FeatureFlagSet enabledFeatures) {
      ModBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateRecipe).forEach(family -> generateRecipes(consumer, family, enabledFeatures));
   }

   private void addCraftingRecipes(RecipeOutput consumer) {
      woodFromLogs(consumer, (ItemLike)ModBlocks.SOULBLIGHT_HYPHAE.get(), (ItemLike)ModBlocks.SOULBLIGHT_STEM.get());
      woodFromLogs(consumer, (ItemLike)ModBlocks.STRIPPED_SOULBLIGHT_HYPHAE.get(), (ItemLike)ModBlocks.STRIPPED_SOULBLIGHT_STEM.get());
      planksFromLogs(consumer, (ItemLike)ModBlocks.SOULBLIGHT_PLANKS.get(), ModTags.Items.SOULBLIGHT_STEMS, 4);
      planksFromLogs(consumer, (ItemLike)ModBlocks.WHISTLECANE_PLANKS.get(), ModTags.Items.WHISTLECANE_BLOCKS, 2);
      this.whistlecaneBlock(consumer);
      blistercrown(consumer);
      mosaicBuilder(consumer, RecipeCategory.DECORATIONS, (ItemLike)ModBlocks.WHISTLECANE_MOSAIC.get(), (ItemLike)ModBlocks.WHISTLECANE_SLAB.get());
      hangingSign(consumer, (ItemLike)ModBlocks.SOULBLIGHT_HANGING_SIGN.get(), (ItemLike)ModBlocks.STRIPPED_SOULBLIGHT_STEM.get());
      hangingSign(consumer, (ItemLike)ModBlocks.WHISTLECANE_HANGING_SIGN.get(), (ItemLike)ModBlocks.WHISTLECANE_BLOCK.get());
   }

   protected void whistlecaneBlock(RecipeOutput output) {
      Block whistlecane = (Block)ModBlocks.WHISTLECANE.get();
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)ModBlocks.WHISTLECANE_BLOCK.get())
         .define('#', whistlecane)
         .pattern("##")
         .pattern("##")
         .unlockedBy("has_whistlecane", has(whistlecane))
         .save(output);
   }

   protected static void blistercrown(RecipeOutput output) {
      ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, (ItemLike)ModBlocks.BLISTERCROWN.get(), 3)
         .requires((ItemLike)ModBlocks.TALL_BLISTERCROWN.get())
         .unlockedBy("has_blistercrown", has((ItemLike)ModBlocks.TALL_BLISTERCROWN.get()))
         .save(output);
   }
}
