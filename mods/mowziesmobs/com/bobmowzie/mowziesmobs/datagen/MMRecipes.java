package com.bobmowzie.mowziesmobs.datagen;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.server.block.BlockHandler;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import java.util.concurrent.CompletableFuture;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.advancements.critereon.ItemPredicate.Builder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class MMRecipes extends RecipeProvider {
   public MMRecipes(PackOutput output, CompletableFuture<Provider> registries) {
      super(output, registries);
   }

   protected void buildRecipes(@NotNull RecipeOutput output, @NotNull Provider lookup) {
      ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)ItemHandler.SPEAR.value())
         .pattern(" F ")
         .pattern("ES ")
         .pattern(" S ")
         .define('F', Items.FLINT)
         .define('S', net.neoforged.neoforge.common.Tags.Items.RODS_WOODEN)
         .define('E', net.neoforged.neoforge.common.Tags.Items.FEATHERS)
         .unlockedBy(getHasName(Items.FLINT), has(Items.FLINT))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)ItemHandler.BLOWGUN.value())
         .pattern(" B ")
         .pattern("SBS")
         .pattern(" B ")
         .define('B', Items.BAMBOO)
         .define('S', net.neoforged.neoforge.common.Tags.Items.STRINGS)
         .unlockedBy(getHasName(Items.BAMBOO), has(Items.BAMBOO))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)ItemHandler.DART.value(), 8)
         .pattern("F")
         .pattern("S")
         .pattern("E")
         .define('F', (ItemLike)ItemHandler.NAGA_FANG.value())
         .define('S', net.neoforged.neoforge.common.Tags.Items.RODS_WOODEN)
         .define('E', net.neoforged.neoforge.common.Tags.Items.FEATHERS)
         .unlockedBy(getHasName((ItemLike)ItemHandler.NAGA_FANG.value()), has((ItemLike)ItemHandler.NAGA_FANG.value()))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)ItemHandler.NAGA_FANG_DAGGER.value())
         .pattern("F")
         .pattern("I")
         .pattern("S")
         .define('F', (ItemLike)ItemHandler.NAGA_FANG.value())
         .define('I', net.neoforged.neoforge.common.Tags.Items.INGOTS_IRON)
         .define('S', net.neoforged.neoforge.common.Tags.Items.RODS_WOODEN)
         .unlockedBy(getHasName((ItemLike)ItemHandler.NAGA_FANG.value()), has((ItemLike)ItemHandler.NAGA_FANG.value()))
         .save(output);
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)BlockHandler.PAINTED_ACACIA.value())
         .pattern("S")
         .pattern("S")
         .define('S', (ItemLike)BlockHandler.PAINTED_ACACIA_SLAB.value())
         .unlockedBy(getHasName((ItemLike)BlockHandler.PAINTED_ACACIA_SLAB.value()), has((ItemLike)BlockHandler.PAINTED_ACACIA_SLAB.value()))
         .save(output, MMCommon.resource("painted_acacia_block_from_slab"));
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)BlockHandler.PAINTED_ACACIA_SLAB.value())
         .pattern("###")
         .define('#', (ItemLike)BlockHandler.PAINTED_ACACIA.value())
         .unlockedBy(getHasName((ItemLike)BlockHandler.PAINTED_ACACIA.value()), has((ItemLike)BlockHandler.PAINTED_ACACIA.value()))
         .save(output);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Items.JUNGLE_PLANKS, 4)
         .requires((ItemLike)BlockHandler.CLAWED_LOG.value())
         .unlockedBy(getHasName((ItemLike)BlockHandler.CLAWED_LOG.value()), has((ItemLike)BlockHandler.CLAWED_LOG.value()))
         .save(output, MMCommon.resource("jungle_planks_from_clawed_log"));
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)BlockHandler.PAINTED_ACACIA.value(), 4)
         .requires(Items.ACACIA_PLANKS, 4)
         .requires(Items.YELLOW_DYE)
         .requires(Items.WHITE_DYE)
         .requires(Items.CYAN_DYE)
         .unlockedBy("has_painted_acacia_materials", has((ItemLike)BlockHandler.PAINTED_ACACIA.value(), Items.YELLOW_DYE, Items.WHITE_DYE, Items.CYAN_DYE))
         .save(output);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)BlockHandler.THATCH.value())
         .requires(Ingredient.of(new ItemLike[]{Items.SHORT_GRASS, Items.TALL_GRASS}), 9)
         .unlockedBy("has_thatch_material", has(Items.SHORT_GRASS, Items.TALL_GRASS))
         .save(output);
      SimpleCookingRecipeBuilder.smelting(
            Ingredient.of(new ItemLike[]{(ItemLike)BlockHandler.CLAWED_LOG.value()}), RecipeCategory.MISC, Items.CHARCOAL, 0.15F, 200
         )
         .unlockedBy(getHasName((ItemLike)BlockHandler.CLAWED_LOG.value()), has((ItemLike)BlockHandler.CLAWED_LOG.value()))
         .save(output, MMCommon.resource("charcoal"));
      SimpleCookingRecipeBuilder.smelting(
            Ingredient.of(new ItemLike[]{(ItemLike)ItemHandler.CAPTURED_GROTTOL.value()}), RecipeCategory.MISC, Items.DIAMOND, 1.0F, 200
         )
         .unlockedBy(getHasName((ItemLike)ItemHandler.CAPTURED_GROTTOL.value()), has((ItemLike)ItemHandler.CAPTURED_GROTTOL.value()))
         .save(output, MMCommon.resource("grottol_smelt"));
      SimpleCookingRecipeBuilder.blasting(
            Ingredient.of(new ItemLike[]{(ItemLike)ItemHandler.CAPTURED_GROTTOL.value()}), RecipeCategory.MISC, Items.DIAMOND, 1.0F, 200
         )
         .unlockedBy(getHasName((ItemLike)ItemHandler.CAPTURED_GROTTOL.value()), has((ItemLike)ItemHandler.CAPTURED_GROTTOL.value()))
         .save(output, MMCommon.resource("grottol_blast"));
   }

   @NotNull
   protected static Criterion<TriggerInstance> has(ItemLike... items) {
      return inventoryTrigger(new Builder[]{Builder.item().of(items)});
   }
}
