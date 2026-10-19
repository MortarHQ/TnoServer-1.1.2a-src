package twilightforest.data;

import java.util.Collections;
import java.util.Optional;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.Slots;
import net.minecraft.advancements.critereon.ItemPredicate.Builder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;

public class StonecuttingGenerator {
   protected static void buildRecipes(RecipeOutput output) {
      stonecutting(output, (ItemLike)TFBlocks.CASTLE_BRICK.get(), (ItemLike)TFBlocks.CASTLE_BRICK_STAIRS.get());
      stonecutting(output, (ItemLike)TFBlocks.CASTLE_BRICK.get(), (ItemLike)TFBlocks.THICK_CASTLE_BRICK.get());
      stonecutting(output, (ItemLike)TFBlocks.CASTLE_BRICK.get(), (ItemLike)TFBlocks.WORN_CASTLE_BRICK.get());
      stonecutting(output, (ItemLike)TFBlocks.CASTLE_BRICK.get(), (ItemLike)TFBlocks.WORN_CASTLE_BRICK_STAIRS.get());
      stonecutting(output, (ItemLike)TFBlocks.CASTLE_BRICK.get(), (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_TILE.get());
      stonecutting(output, (ItemLike)TFBlocks.CASTLE_BRICK.get(), (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_PILLAR.get());
      stonecutting(output, (ItemLike)TFBlocks.CASTLE_BRICK.get(), (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_STAIRS.get());
      stonecutting(output, (ItemLike)TFBlocks.CASTLE_BRICK.get(), (ItemLike)TFBlocks.ENCASED_CASTLE_BRICK_PILLAR.get());
      stonecutting(output, (ItemLike)TFBlocks.CASTLE_BRICK.get(), (ItemLike)TFBlocks.ENCASED_CASTLE_BRICK_TILE.get());
      stonecutting(output, (ItemLike)TFBlocks.CASTLE_BRICK.get(), (ItemLike)TFBlocks.ENCASED_CASTLE_BRICK_STAIRS.get());
      stonecutting(output, (ItemLike)TFBlocks.CASTLE_BRICK.get(), (ItemLike)TFBlocks.CASTLE_ROOF_TILE.get());
      stonecutting(output, (ItemLike)TFBlocks.CRACKED_CASTLE_BRICK.get(), (ItemLike)TFBlocks.THICK_CASTLE_BRICK.get());
      stonecutting(output, (ItemLike)TFBlocks.CRACKED_CASTLE_BRICK.get(), (ItemLike)TFBlocks.CRACKED_CASTLE_BRICK_STAIRS.get());
      stonecutting(output, (ItemLike)TFBlocks.CRACKED_CASTLE_BRICK.get(), (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_TILE.get());
      stonecutting(output, (ItemLike)TFBlocks.CRACKED_CASTLE_BRICK.get(), (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_PILLAR.get());
      stonecutting(output, (ItemLike)TFBlocks.CRACKED_CASTLE_BRICK.get(), (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_STAIRS.get());
      stonecutting(output, (ItemLike)TFBlocks.WORN_CASTLE_BRICK.get(), (ItemLike)TFBlocks.THICK_CASTLE_BRICK.get());
      stonecutting(output, (ItemLike)TFBlocks.WORN_CASTLE_BRICK.get(), (ItemLike)TFBlocks.WORN_CASTLE_BRICK_STAIRS.get());
      stonecutting(output, (ItemLike)TFBlocks.WORN_CASTLE_BRICK.get(), (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_TILE.get());
      stonecutting(output, (ItemLike)TFBlocks.WORN_CASTLE_BRICK.get(), (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_PILLAR.get());
      stonecutting(output, (ItemLike)TFBlocks.WORN_CASTLE_BRICK.get(), (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_STAIRS.get());
      stonecutting(output, (ItemLike)TFBlocks.MOSSY_CASTLE_BRICK.get(), (ItemLike)TFBlocks.THICK_CASTLE_BRICK.get());
      stonecutting(output, (ItemLike)TFBlocks.MOSSY_CASTLE_BRICK.get(), (ItemLike)TFBlocks.MOSSY_CASTLE_BRICK_STAIRS.get());
      stonecutting(output, (ItemLike)TFBlocks.MOSSY_CASTLE_BRICK.get(), (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_TILE.get());
      stonecutting(output, (ItemLike)TFBlocks.MOSSY_CASTLE_BRICK.get(), (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_PILLAR.get());
      stonecutting(output, (ItemLike)TFBlocks.MOSSY_CASTLE_BRICK.get(), (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_STAIRS.get());
      stonecutting(output, (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_PILLAR.get(), (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_STAIRS.get());
      stonecutting(output, (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_PILLAR.get(), (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_TILE.get());
      stonecutting(output, (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_TILE.get(), (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_STAIRS.get());
      stonecutting(output, (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_TILE.get(), (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_PILLAR.get());
      stonecutting(output, (ItemLike)TFBlocks.ENCASED_CASTLE_BRICK_PILLAR.get(), (ItemLike)TFBlocks.ENCASED_CASTLE_BRICK_STAIRS.get());
      stonecutting(output, (ItemLike)TFBlocks.ENCASED_CASTLE_BRICK_PILLAR.get(), (ItemLike)TFBlocks.ENCASED_CASTLE_BRICK_TILE.get());
      stonecutting(output, (ItemLike)TFBlocks.ENCASED_CASTLE_BRICK_PILLAR.get(), (ItemLike)TFBlocks.CASTLE_ROOF_TILE.get());
      stonecutting(output, (ItemLike)TFBlocks.ENCASED_CASTLE_BRICK_TILE.get(), (ItemLike)TFBlocks.ENCASED_CASTLE_BRICK_STAIRS.get());
      stonecutting(output, (ItemLike)TFBlocks.ENCASED_CASTLE_BRICK_TILE.get(), (ItemLike)TFBlocks.ENCASED_CASTLE_BRICK_PILLAR.get());
      stonecutting(output, (ItemLike)TFBlocks.ENCASED_CASTLE_BRICK_TILE.get(), (ItemLike)TFBlocks.CASTLE_ROOF_TILE.get());
      stonecutting(output, (ItemLike)TFBlocks.THICK_CASTLE_BRICK.get(), (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_PILLAR.get());
      stonecutting(output, (ItemLike)TFBlocks.THICK_CASTLE_BRICK.get(), (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_TILE.get());
      stonecutting(output, (ItemLike)TFBlocks.THICK_CASTLE_BRICK.get(), (ItemLike)TFBlocks.BOLD_CASTLE_BRICK_STAIRS.get());
      stonecutting(output, (ItemLike)TFBlocks.ETCHED_NAGASTONE.get(), (ItemLike)TFBlocks.NAGASTONE_STAIRS_LEFT.get());
      stonecutting(output, (ItemLike)TFBlocks.ETCHED_NAGASTONE.get(), (ItemLike)TFBlocks.NAGASTONE_STAIRS_RIGHT.get());
      stonecutting(output, (ItemLike)TFBlocks.MOSSY_ETCHED_NAGASTONE.get(), (ItemLike)TFBlocks.MOSSY_NAGASTONE_STAIRS_LEFT.get());
      stonecutting(output, (ItemLike)TFBlocks.MOSSY_ETCHED_NAGASTONE.get(), (ItemLike)TFBlocks.MOSSY_NAGASTONE_STAIRS_RIGHT.get());
      stonecutting(output, (ItemLike)TFBlocks.CRACKED_ETCHED_NAGASTONE.get(), (ItemLike)TFBlocks.CRACKED_NAGASTONE_STAIRS_LEFT.get());
      stonecutting(output, (ItemLike)TFBlocks.CRACKED_ETCHED_NAGASTONE.get(), (ItemLike)TFBlocks.CRACKED_NAGASTONE_STAIRS_RIGHT.get());
      stonecutting(output, (ItemLike)TFBlocks.NAGASTONE_STAIRS_RIGHT.get(), (ItemLike)TFBlocks.NAGASTONE_STAIRS_LEFT.get());
      stonecutting(output, (ItemLike)TFBlocks.NAGASTONE_STAIRS_LEFT.get(), (ItemLike)TFBlocks.NAGASTONE_STAIRS_RIGHT.get());
      stonecutting(output, (ItemLike)TFBlocks.MOSSY_NAGASTONE_STAIRS_RIGHT.get(), (ItemLike)TFBlocks.MOSSY_NAGASTONE_STAIRS_LEFT.get());
      stonecutting(output, (ItemLike)TFBlocks.MOSSY_NAGASTONE_STAIRS_LEFT.get(), (ItemLike)TFBlocks.MOSSY_NAGASTONE_STAIRS_RIGHT.get());
      stonecutting(output, (ItemLike)TFBlocks.CRACKED_NAGASTONE_STAIRS_RIGHT.get(), (ItemLike)TFBlocks.CRACKED_NAGASTONE_STAIRS_LEFT.get());
      stonecutting(output, (ItemLike)TFBlocks.CRACKED_NAGASTONE_STAIRS_LEFT.get(), (ItemLike)TFBlocks.CRACKED_NAGASTONE_STAIRS_RIGHT.get());
      stonecutting(output, (ItemLike)TFBlocks.DARK_LOG.get(), (ItemLike)TFBlocks.TOWERWOOD.get());
      stonecutting(output, (ItemLike)TFBlocks.DARK_WOOD.get(), (ItemLike)TFBlocks.TOWERWOOD.get());
      stonecutting(output, (ItemLike)TFBlocks.DARK_LOG.get(), (ItemLike)TFBlocks.ENCASED_TOWERWOOD.get());
      stonecutting(output, (ItemLike)TFBlocks.DARK_WOOD.get(), (ItemLike)TFBlocks.ENCASED_TOWERWOOD.get());
      stonecutting(output, (ItemLike)TFBlocks.MAZESTONE.get(), (ItemLike)TFBlocks.MAZESTONE_BORDER.get());
      stonecutting(output, (ItemLike)TFBlocks.MAZESTONE.get(), (ItemLike)TFBlocks.MAZESTONE_BRICK.get());
      stonecutting(output, (ItemLike)TFBlocks.MAZESTONE.get(), (ItemLike)TFBlocks.CUT_MAZESTONE.get());
      stonecutting(output, (ItemLike)TFBlocks.MAZESTONE.get(), (ItemLike)TFBlocks.DECORATIVE_MAZESTONE.get());
      stonecutting(output, (ItemLike)TFBlocks.MAZESTONE.get(), (ItemLike)TFBlocks.MAZESTONE_MOSAIC.get());
      stonecutting(output, (ItemLike)TFBlocks.MAZESTONE_BRICK.get(), (ItemLike)TFBlocks.MAZESTONE_BORDER.get());
      stonecutting(output, (ItemLike)TFBlocks.MAZESTONE_BRICK.get(), (ItemLike)TFBlocks.CUT_MAZESTONE.get());
      stonecutting(output, (ItemLike)TFBlocks.MAZESTONE_BRICK.get(), (ItemLike)TFBlocks.DECORATIVE_MAZESTONE.get());
      stonecutting(output, (ItemLike)TFBlocks.MAZESTONE_BRICK.get(), (ItemLike)TFBlocks.MAZESTONE_MOSAIC.get());
      stonecutting(output, (ItemLike)TFBlocks.MAZESTONE_BORDER.get(), (ItemLike)TFBlocks.MAZESTONE_BRICK.get());
      stonecutting(output, (ItemLike)TFBlocks.MAZESTONE_BORDER.get(), (ItemLike)TFBlocks.CUT_MAZESTONE.get());
      stonecutting(output, (ItemLike)TFBlocks.MAZESTONE_BORDER.get(), (ItemLike)TFBlocks.DECORATIVE_MAZESTONE.get());
      stonecutting(output, (ItemLike)TFBlocks.MAZESTONE_BORDER.get(), (ItemLike)TFBlocks.MAZESTONE_MOSAIC.get());
      stonecutting(output, (ItemLike)TFBlocks.CUT_MAZESTONE.get(), (ItemLike)TFBlocks.MAZESTONE_BORDER.get());
      stonecutting(output, (ItemLike)TFBlocks.CUT_MAZESTONE.get(), (ItemLike)TFBlocks.MAZESTONE_BRICK.get());
      stonecutting(output, (ItemLike)TFBlocks.CUT_MAZESTONE.get(), (ItemLike)TFBlocks.DECORATIVE_MAZESTONE.get());
      stonecutting(output, (ItemLike)TFBlocks.CUT_MAZESTONE.get(), (ItemLike)TFBlocks.MAZESTONE_MOSAIC.get());
      stonecutting(output, (ItemLike)TFBlocks.DECORATIVE_MAZESTONE.get(), (ItemLike)TFBlocks.MAZESTONE_BORDER.get());
      stonecutting(output, (ItemLike)TFBlocks.DECORATIVE_MAZESTONE.get(), (ItemLike)TFBlocks.CUT_MAZESTONE.get());
      stonecutting(output, (ItemLike)TFBlocks.DECORATIVE_MAZESTONE.get(), (ItemLike)TFBlocks.MAZESTONE_BRICK.get());
      stonecutting(output, (ItemLike)TFBlocks.DECORATIVE_MAZESTONE.get(), (ItemLike)TFBlocks.MAZESTONE_MOSAIC.get());
      stonecutting(output, (ItemLike)TFBlocks.MAZESTONE_MOSAIC.get(), (ItemLike)TFBlocks.MAZESTONE_BORDER.get());
      stonecutting(output, (ItemLike)TFBlocks.MAZESTONE_MOSAIC.get(), (ItemLike)TFBlocks.CUT_MAZESTONE.get());
      stonecutting(output, (ItemLike)TFBlocks.MAZESTONE_MOSAIC.get(), (ItemLike)TFBlocks.DECORATIVE_MAZESTONE.get());
      stonecutting(output, (ItemLike)TFBlocks.MAZESTONE_MOSAIC.get(), (ItemLike)TFBlocks.MAZESTONE_BRICK.get());
      stonecutting(output, (ItemLike)TFBlocks.TWILIGHT_OAK_LOG.get(), (ItemLike)TFItems.HOLLOW_TWILIGHT_OAK_LOG.get());
      stonecutting(output, (ItemLike)TFBlocks.CANOPY_LOG.get(), (ItemLike)TFItems.HOLLOW_CANOPY_LOG.get());
      stonecutting(output, (ItemLike)TFBlocks.MANGROVE_LOG.get(), (ItemLike)TFItems.HOLLOW_MANGROVE_LOG.get());
      stonecutting(output, (ItemLike)TFBlocks.DARK_LOG.get(), (ItemLike)TFItems.HOLLOW_DARK_LOG.get());
      stonecutting(output, (ItemLike)TFBlocks.TIME_LOG.get(), (ItemLike)TFItems.HOLLOW_TIME_LOG.get());
      stonecutting(output, (ItemLike)TFBlocks.TRANSFORMATION_LOG.get(), (ItemLike)TFItems.HOLLOW_TRANSFORMATION_LOG.get());
      stonecutting(output, (ItemLike)TFBlocks.MINING_LOG.get(), (ItemLike)TFItems.HOLLOW_MINING_LOG.get());
      stonecutting(output, (ItemLike)TFBlocks.SORTING_LOG.get(), (ItemLike)TFItems.HOLLOW_SORTING_LOG.get());
      stonecutting(output, Blocks.OAK_LOG, (ItemLike)TFItems.HOLLOW_OAK_LOG.get());
      stonecutting(output, Blocks.SPRUCE_LOG, (ItemLike)TFItems.HOLLOW_SPRUCE_LOG.get());
      stonecutting(output, Blocks.BIRCH_LOG, (ItemLike)TFItems.HOLLOW_BIRCH_LOG.get());
      stonecutting(output, Blocks.JUNGLE_LOG, (ItemLike)TFItems.HOLLOW_JUNGLE_LOG.get());
      stonecutting(output, Blocks.ACACIA_LOG, (ItemLike)TFItems.HOLLOW_ACACIA_LOG.get());
      stonecutting(output, Blocks.DARK_OAK_LOG, (ItemLike)TFItems.HOLLOW_DARK_OAK_LOG.get());
      stonecutting(output, Blocks.CRIMSON_STEM, (ItemLike)TFItems.HOLLOW_CRIMSON_STEM.get());
      stonecutting(output, Blocks.WARPED_STEM, (ItemLike)TFItems.HOLLOW_WARPED_STEM.get());
      stonecutting(output, Blocks.MANGROVE_LOG, (ItemLike)TFItems.HOLLOW_VANGROVE_LOG.get());
      stonecutting(output, Blocks.CHERRY_LOG, (ItemLike)TFItems.HOLLOW_CHERRY_LOG.get());
      stonecutting(output, Blocks.STONE, (ItemLike)TFBlocks.TWISTED_STONE.get());
      stonecutting(output, Blocks.STONE, (ItemLike)TFBlocks.BOLD_STONE_PILLAR.get());
      stonecutting(output, Blocks.STONE, (ItemLike)TFBlocks.TWISTED_STONE_PILLAR.get());
      stonecutting(output, Blocks.STONE, (ItemLike)TFBlocks.SPIRAL_BRICKS.get());
      stonecutting(output, (ItemLike)TFBlocks.TWISTED_STONE.get(), (ItemLike)TFBlocks.TWISTED_STONE_PILLAR.get());
      stonecutting(output, Blocks.STONE, (ItemLike)TFBlocks.TERRORCOTTA_ARCS.get());
      stonecutting(output, Blocks.STONE, (ItemLike)TFBlocks.TERRORCOTTA_CURVES.get());
      stonecutting(output, Blocks.STONE, (ItemLike)TFBlocks.TERRORCOTTA_LINES.get());
      stonecutting(output, (ItemLike)TFBlocks.TERRORCOTTA_ARCS.get(), (ItemLike)TFBlocks.TERRORCOTTA_CURVES.get());
      stonecutting(output, (ItemLike)TFBlocks.TERRORCOTTA_ARCS.get(), (ItemLike)TFBlocks.TERRORCOTTA_LINES.get());
      stonecutting(output, (ItemLike)TFBlocks.TERRORCOTTA_CURVES.get(), (ItemLike)TFBlocks.TERRORCOTTA_ARCS.get());
      stonecutting(output, (ItemLike)TFBlocks.TERRORCOTTA_CURVES.get(), (ItemLike)TFBlocks.TERRORCOTTA_LINES.get());
      stonecutting(output, (ItemLike)TFBlocks.TERRORCOTTA_LINES.get(), (ItemLike)TFBlocks.TERRORCOTTA_ARCS.get());
      stonecutting(output, (ItemLike)TFBlocks.TERRORCOTTA_LINES.get(), (ItemLike)TFBlocks.TERRORCOTTA_CURVES.get());
      stonecutting(output, (ItemLike)TFBlocks.UNDERBRICK.get(), (ItemLike)TFBlocks.UNDERBRICK_FLOOR.get());
   }

   private static void stonecutting(RecipeOutput recipe, ItemLike input, ItemLike output) {
      stonecutting(recipe, input, output, 1);
   }

   private static void stonecutting(RecipeOutput recipe, ItemLike input, ItemLike output, int count) {
      SingleItemRecipeBuilder.stonecutting(Ingredient.of(new ItemLike[]{input}), RecipeCategory.BUILDING_BLOCKS, output.asItem(), count)
         .unlockedBy("has_block", has(input))
         .save(recipe, getIdFor(input, output));
   }

   private static ResourceLocation getIdFor(ItemLike input, ItemLike output) {
      String path = String.format(
         "stonecutting/%s/%s", BuiltInRegistries.ITEM.getKey(input.asItem()).getPath(), BuiltInRegistries.ITEM.getKey(output.asItem()).getPath()
      );
      return TwilightForestMod.prefix(path);
   }

   protected static Criterion<TriggerInstance> has(ItemLike item) {
      return CriteriaTriggers.INVENTORY_CHANGED
         .createCriterion(new TriggerInstance(Optional.empty(), Slots.ANY, Collections.singletonList(Builder.item().of(new ItemLike[]{item}).build())));
   }
}
