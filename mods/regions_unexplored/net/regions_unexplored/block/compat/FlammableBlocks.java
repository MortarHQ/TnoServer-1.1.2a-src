package net.regions_unexplored.block.compat;

import net.minecraft.world.level.block.Block;
import net.regions_unexplored.block.set.NaturalSet;
import net.regions_unexplored.block.set.WoodSet;
import net.regions_unexplored.registry.RUBlocks;
import net.regions_unexplored.util.BlockCompatUtil;

public class FlammableBlocks {
   public static void setup() {
      BlockCompatUtil.registerFlammableBlock(RUBlocks.PRISMOSS_SPROUT.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.CORPSE_FLOWER.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.BLADED_GRASS.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.BLADED_TALL_GRASS.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.DROPLEAF.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.DROPLEAF_PLANT.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.DUSKMELON.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.DUSKTRAP.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.DEAD_STEPPE_SHRUB.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.FROZEN_GRASS.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.SANDY_GRASS.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.SMALL_DESERT_SHRUB.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.STEPPE_GRASS.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.STEPPE_SHRUB.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.GRASS_SPROUTS.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.ELEPHANT_EAR.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.SANDY_TALL_GRASS.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.STEPPE_TALL_GRASS.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.WINDSWEPT_GRASS.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.ALPHA_DANDELION.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.ALPHA_ROSE.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.ASTER.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.BLEEDING_HEART.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.BLUE_LUPINE.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.DAISY.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.FELICIA_DAISY.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.FIREWEED.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.HIBISCUS.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.HYSSOP.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.MALLOW.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.PINK_LUPINE.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.POPPY_BUSH.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.SALMON_POPPY.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.SALMON_POPPY_BUSH.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.PURPLE_LUPINE.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.RED_LUPINE.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.WARATAH.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.WHITE_TRILLIUM.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.WILTING_TRILLIUM.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.YELLOW_LUPINE.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.ORANGE_CONEFLOWER.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.PURPLE_CONEFLOWER.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.CLOVER.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock((Block)RUBlocks.BLUE_MAGNOLIA_FLOWERS.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock((Block)RUBlocks.PINK_MAGNOLIA_FLOWERS.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock((Block)RUBlocks.WHITE_MAGNOLIA_FLOWERS.get(), 60, 100);

      for (Block snowbelle : RUBlocks.SNOWBELLES.getAll()) {
         BlockCompatUtil.registerFlammableBlock(snowbelle, 60, 100);
      }

      BlockCompatUtil.registerFlammableBlock(RUBlocks.MAPLE_LEAF_LITTER.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.RED_MAPLE_LEAF_LITTER.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.ORANGE_MAPLE_LEAF_LITTER.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.SILVER_BIRCH_LEAF_LITTER.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.ENCHANTED_BIRCH_LEAF_LITTER.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.MEADOW_SAGE.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.BARLEY.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.CATTAIL.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.TASSEL.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.TSUBAKI.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.DAY_LILY.get(), 60, 100);

      for (NaturalSet set : RUBlocks.NATURAL_SETS) {
         if (!set.fireproof && set.getSapling() != null) {
            BlockCompatUtil.registerFlammableBlock(set.getSapling(), 60, 100);
         }
      }

      for (NaturalSet set : RUBlocks.NATURAL_SETS) {
         if (!set.fireproof && set.getShrub() != null) {
            BlockCompatUtil.registerFlammableBlock(set.getShrub(), 60, 100);
         }
      }

      BlockCompatUtil.registerFlammableBlock(RUBlocks.BLUE_BIOSHROOM.get(), 30, 60);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.GREEN_BIOSHROOM.get(), 30, 60);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.PINK_BIOSHROOM.get(), 30, 60);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.TALL_BLUE_BIOSHROOM.get(), 30, 60);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.TALL_GREEN_BIOSHROOM.get(), 30, 60);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.TALL_PINK_BIOSHROOM.get(), 30, 60);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.BARREL_CACTUS.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.CAVE_HYSSOP.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.DUCKWEED.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.SPANISH_MOSS.get(), 15, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.SPANISH_MOSS_PLANT.get(), 15, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.KAPOK_VINES.get(), 15, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.KAPOK_VINES_PLANT.get(), 15, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.FLOWERING_LILY_PAD.get(), 15, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.GIANT_LILY_PAD.get(), 15, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.SALMONBERRY_BUSH.get(), 60, 100);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.BLUE_BIOSHROOM_BLOCK.get(), 30, 60);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.GLOWING_BLUE_BIOSHROOM_BLOCK.get(), 30, 60);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.GREEN_BIOSHROOM_BLOCK.get(), 30, 60);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.GLOWING_GREEN_BIOSHROOM_BLOCK.get(), 30, 60);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.PINK_BIOSHROOM_BLOCK.get(), 30, 60);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.GLOWING_PINK_BIOSHROOM_BLOCK.get(), 30, 60);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.BAMBOO_LOG.get(), 5, 5);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.STRIPPED_BAMBOO_LOG.get(), 5, 5);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.SMALL_OAK_LOG.get(), 5, 5);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.STRIPPED_SMALL_OAK_LOG.get(), 5, 5);
      BlockCompatUtil.registerFlammableBlock(RUBlocks.SAGUARO_CACTUS.get(), 5, 5);

      for (NaturalSet set : RUBlocks.NATURAL_SETS) {
         if (!set.fireproof && set.getLeaves() != null) {
            BlockCompatUtil.registerFlammableBlock(set.getLeaves(), 30, 60);
         }
      }

      for (NaturalSet set : RUBlocks.NATURAL_SETS) {
         if (!set.fireproof && set.getBranch() != null) {
            BlockCompatUtil.registerFlammableBlock(set.getBranch(), 30, 100);
         }
      }

      for (WoodSet set : RUBlocks.WOOD_SETS) {
         if (!set.fireproof) {
            BlockCompatUtil.registerFlammableBlock(set.getLog(), 5, 5);
            BlockCompatUtil.registerFlammableBlock(set.getStrippedLog(), 5, 5);
            BlockCompatUtil.registerFlammableBlock(set.getWood(), 5, 5);
            BlockCompatUtil.registerFlammableBlock(set.getStrippedWood(), 5, 5);
            BlockCompatUtil.registerFlammableBlock(set.getPlanks(), 5, 20);
            BlockCompatUtil.registerFlammableBlock(set.getStairs(), 5, 20);
            BlockCompatUtil.registerFlammableBlock(set.getSlab(), 5, 20);
            BlockCompatUtil.registerFlammableBlock(set.getFence(), 5, 20);
            BlockCompatUtil.registerFlammableBlock(set.getFenceGate(), 5, 20);
            BlockCompatUtil.registerFlammableBlock(set.getDoor(), 5, 20);
            BlockCompatUtil.registerFlammableBlock(set.getTrapdoor(), 5, 20);
            BlockCompatUtil.registerFlammableBlock(set.getPressurePlate(), 5, 20);
            BlockCompatUtil.registerFlammableBlock(set.getButton(), 5, 20);
            BlockCompatUtil.registerFlammableBlock(set.getSign(), 5, 20);
            BlockCompatUtil.registerFlammableBlock(set.getWallSign(), 5, 20);
            BlockCompatUtil.registerFlammableBlock(set.getHangingSign(), 5, 20);
            BlockCompatUtil.registerFlammableBlock(set.getWallHangingSign(), 5, 20);
         }
      }

      for (Block block : RUBlocks.PAINTED_PLANKS.getAll()) {
         BlockCompatUtil.registerFlammableBlock(block, 5, 20);
      }

      for (Block block : RUBlocks.PAINTED_STAIRS.getAll()) {
         BlockCompatUtil.registerFlammableBlock(block, 5, 20);
      }

      for (Block block : RUBlocks.PAINTED_SLABS.getAll()) {
         BlockCompatUtil.registerFlammableBlock(block, 5, 20);
      }
   }
}
