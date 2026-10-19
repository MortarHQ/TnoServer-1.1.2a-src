package net.regions_unexplored.block.compat;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.regions_unexplored.block.set.NaturalSet;
import net.regions_unexplored.registry.RUBlocks;

public class CompostableBlocks {
   public static final Map<ItemLike, Float> COMPOSTABLES = new HashMap<>();

   static {
      for (Block block : RUBlocks.NATURAL_SETS.stream().map(NaturalSet::getSapling).toList()) {
         if (block != null) {
            COMPOSTABLES.put(block, 0.3F);
         }
      }

      for (Block block : RUBlocks.NATURAL_SETS.stream().map(NaturalSet::getLeaves).toList()) {
         if (block != null) {
            COMPOSTABLES.put(block, 0.3F);
         }
      }

      for (Block block : RUBlocks.NATURAL_SETS.stream().map(NaturalSet::getBranch).toList()) {
         if (block != null) {
            COMPOSTABLES.put(block, 0.3F);
         }
      }

      for (Block block : RUBlocks.NATURAL_SETS.stream().map(NaturalSet::getShrub).toList()) {
         if (block != null) {
            COMPOSTABLES.put(block, 0.65F);
         }
      }

      COMPOSTABLES.put((ItemLike)RUBlocks.GREEN_BIOSHROOM.get(), 0.35F);
      COMPOSTABLES.put((ItemLike)RUBlocks.BLUE_BIOSHROOM.get(), 0.35F);
      COMPOSTABLES.put((ItemLike)RUBlocks.PINK_BIOSHROOM.get(), 0.35F);
      COMPOSTABLES.put((ItemLike)RUBlocks.YELLOW_BIOSHROOM.get(), 0.35F);
      COMPOSTABLES.put((ItemLike)RUBlocks.TALL_GREEN_BIOSHROOM.get(), 0.65F);
      COMPOSTABLES.put((ItemLike)RUBlocks.TALL_BLUE_BIOSHROOM.get(), 0.65F);
      COMPOSTABLES.put((ItemLike)RUBlocks.TALL_PINK_BIOSHROOM.get(), 0.65F);
      COMPOSTABLES.put((ItemLike)RUBlocks.TALL_YELLOW_BIOSHROOM.get(), 0.65F);
      COMPOSTABLES.put((ItemLike)RUBlocks.DEAD_STEPPE_SHRUB.get(), 0.3F);
      COMPOSTABLES.put((ItemLike)RUBlocks.FROZEN_GRASS.get(), 0.3F);
      COMPOSTABLES.put((ItemLike)RUBlocks.SANDY_GRASS.get(), 0.3F);
      COMPOSTABLES.put((ItemLike)RUBlocks.SMALL_DESERT_SHRUB.get(), 0.3F);
      COMPOSTABLES.put((ItemLike)RUBlocks.STEPPE_GRASS.get(), 0.3F);
      COMPOSTABLES.put((ItemLike)RUBlocks.STEPPE_SHRUB.get(), 0.3F);
      COMPOSTABLES.put((ItemLike)RUBlocks.GRASS_SPROUTS.get(), 0.3F);
      COMPOSTABLES.put((ItemLike)RUBlocks.ELEPHANT_EAR.get(), 0.5F);
      COMPOSTABLES.put((ItemLike)RUBlocks.SANDY_TALL_GRASS.get(), 0.5F);
      COMPOSTABLES.put((ItemLike)RUBlocks.STEPPE_TALL_GRASS.get(), 0.5F);
      COMPOSTABLES.put((ItemLike)RUBlocks.WINDSWEPT_GRASS.get(), 0.5F);
      COMPOSTABLES.put((ItemLike)RUBlocks.ALPHA_DANDELION.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.ALPHA_ROSE.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.ASTER.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.BLEEDING_HEART.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.BLUE_LUPINE.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.DAISY.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.DORCEL.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.FELICIA_DAISY.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.FIREWEED.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.HIBISCUS.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.HYSSOP.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.MALLOW.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.PINK_LUPINE.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.POPPY_BUSH.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.SALMON_POPPY.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.SALMON_POPPY_BUSH.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.PURPLE_LUPINE.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.RED_LUPINE.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.WARATAH.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.WHITE_TRILLIUM.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.WILTING_TRILLIUM.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.YELLOW_LUPINE.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.TSUBAKI.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.ORANGE_CONEFLOWER.get(), 0.25F);
      COMPOSTABLES.put((ItemLike)RUBlocks.PURPLE_CONEFLOWER.get(), 0.25F);
      COMPOSTABLES.put((ItemLike)RUBlocks.BLUE_MAGNOLIA_FLOWERS.get(), 0.25F);
      COMPOSTABLES.put((ItemLike)RUBlocks.PINK_MAGNOLIA_FLOWERS.get(), 0.25F);
      COMPOSTABLES.put((ItemLike)RUBlocks.WHITE_MAGNOLIA_FLOWERS.get(), 0.25F);
      COMPOSTABLES.put((ItemLike)RUBlocks.CLOVER.get(), 0.2F);

      for (Block snowbelle : RUBlocks.SNOWBELLES.getAll()) {
         COMPOSTABLES.put(snowbelle, 0.4F);
      }

      COMPOSTABLES.put((ItemLike)RUBlocks.MAPLE_LEAF_LITTER.get(), 0.1F);
      COMPOSTABLES.put((ItemLike)RUBlocks.RED_MAPLE_LEAF_LITTER.get(), 0.1F);
      COMPOSTABLES.put((ItemLike)RUBlocks.ORANGE_MAPLE_LEAF_LITTER.get(), 0.1F);
      COMPOSTABLES.put((ItemLike)RUBlocks.SILVER_BIRCH_LEAF_LITTER.get(), 0.1F);
      COMPOSTABLES.put((ItemLike)RUBlocks.ENCHANTED_BIRCH_LEAF_LITTER.get(), 0.1F);
      COMPOSTABLES.put((ItemLike)RUBlocks.MEADOW_SAGE.get(), 0.6F);
      COMPOSTABLES.put((ItemLike)RUBlocks.BARLEY.get(), 0.6F);
      COMPOSTABLES.put((ItemLike)RUBlocks.CATTAIL.get(), 0.6F);
      COMPOSTABLES.put((ItemLike)RUBlocks.TASSEL.get(), 0.6F);
      COMPOSTABLES.put((ItemLike)RUBlocks.DAY_LILY.get(), 0.6F);
      COMPOSTABLES.put((ItemLike)RUBlocks.BARREL_CACTUS.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.CAVE_HYSSOP.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.DUCKWEED.get(), 0.15F);
      COMPOSTABLES.put((ItemLike)RUBlocks.SPANISH_MOSS.get(), 0.3F);
      COMPOSTABLES.put((ItemLike)RUBlocks.KAPOK_VINES.get(), 0.3F);
      COMPOSTABLES.put((ItemLike)RUBlocks.FLOWERING_LILY_PAD.get(), 0.3F);
      COMPOSTABLES.put((ItemLike)RUBlocks.SALMONBERRY_BUSH.get(), 0.25F);
      COMPOSTABLES.put((ItemLike)RUBlocks.PRISMOSS_SPROUT.get(), 0.2F);
      COMPOSTABLES.put((ItemLike)RUBlocks.CORPSE_FLOWER.get(), 0.6F);
      COMPOSTABLES.put((ItemLike)RUBlocks.BLADED_GRASS.get(), 0.3F);
      COMPOSTABLES.put((ItemLike)RUBlocks.BLADED_TALL_GRASS.get(), 0.5F);
      COMPOSTABLES.put((ItemLike)RUBlocks.DROPLEAF.get(), 0.15F);
      COMPOSTABLES.put((ItemLike)RUBlocks.DUSKMELON.get(), 0.5F);
      COMPOSTABLES.put((ItemLike)RUBlocks.DUSKTRAP.get(), 0.3F);
      COMPOSTABLES.put((ItemLike)RUBlocks.BRIMSPROUT.get(), 0.1F);
      COMPOSTABLES.put((ItemLike)RUBlocks.COBALT_EARLIGHT.get(), 0.3F);
      COMPOSTABLES.put((ItemLike)RUBlocks.TALL_COBALT_EARLIGHT.get(), 0.35F);
      COMPOSTABLES.put((ItemLike)RUBlocks.COBALT_ROOTS.get(), 0.2F);
      COMPOSTABLES.put((ItemLike)RUBlocks.HANGING_EARLIGHT.get(), 0.1F);
      COMPOSTABLES.put((ItemLike)RUBlocks.GLISTERING_IVY.get(), 0.1F);
      COMPOSTABLES.put((ItemLike)RUBlocks.GLISTERING_SPROUT.get(), 0.2F);
      COMPOSTABLES.put((ItemLike)RUBlocks.GLISTER_BULB.get(), 0.4F);
      COMPOSTABLES.put((ItemLike)RUBlocks.GLISTER_SPIRE.get(), 0.3F);
      COMPOSTABLES.put((ItemLike)RUBlocks.MYCOTOXIC_MUSHROOMS.get(), 0.1F);
      COMPOSTABLES.put((ItemLike)RUBlocks.MYCOTOXIC_DAISY.get(), 0.3F);
      COMPOSTABLES.put((ItemLike)RUBlocks.MYCOTOXIC_GRASS.get(), 0.2F);
   }
}
