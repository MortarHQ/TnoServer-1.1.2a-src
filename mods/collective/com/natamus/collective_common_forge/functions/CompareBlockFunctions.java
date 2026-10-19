package com.natamus.collective_common_forge.functions;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DeadBushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.TallGrassBlock;

public class CompareBlockFunctions {
   public static boolean blockIsInRegistryHolder(Block block, TagKey<Block> tagKey) {
      return block.builtInRegistryHolder().is(tagKey);
   }

   public static boolean isStoneBlock(Block block) {
      return blockIsInRegistryHolder(block, BlockTags.BASE_STONE_OVERWORLD);
   }

   public static boolean isNetherStoneBlock(Block block) {
      return blockIsInRegistryHolder(block, BlockTags.BASE_STONE_NETHER);
   }

   public static boolean isTreeLeaf(Block block, boolean withNetherVariants) {
      if (!blockIsInRegistryHolder(block, BlockTags.LEAVES) && !(block instanceof LeavesBlock)) {
         if (!withNetherVariants || !block.equals(Blocks.NETHER_WART_BLOCK) && !block.equals(Blocks.WARPED_WART_BLOCK) && !block.equals(Blocks.SHROOMLIGHT)) {
            return !(block instanceof BushBlock)
               ? false
               : !(block instanceof CropBlock)
                  && !(block instanceof DeadBushBlock)
                  && !(block instanceof DoublePlantBlock)
                  && !(block instanceof FlowerBlock)
                  && !(block instanceof SaplingBlock)
                  && !(block instanceof StemBlock)
                  && !(block instanceof AttachedStemBlock)
                  && !(block instanceof SweetBerryBushBlock)
                  && !(block instanceof TallGrassBlock);
         } else {
            return true;
         }
      } else {
         return true;
      }
   }

   public static boolean isTreeLeaf(Block block) {
      return isTreeLeaf(block, true);
   }

   public static boolean isTreeLog(Block block) {
      return blockIsInRegistryHolder(block, BlockTags.LOGS) || block instanceof RotatedPillarBlock;
   }

   public static boolean isSapling(Block block) {
      return blockIsInRegistryHolder(block, BlockTags.SAPLINGS) || block instanceof SaplingBlock;
   }

   public static boolean isDirtBlock(Block block) {
      return block.equals(Blocks.GRASS_BLOCK) || block.equals(Blocks.DIRT) || block.equals(Blocks.COARSE_DIRT) || block.equals(Blocks.PODZOL);
   }

   public static boolean isPortalBlock(Block block) {
      return block instanceof NetherPortalBlock || BlockFunctions.blockToReadableString(block).equals("portal placeholder");
   }

   public static boolean isAirOrOverwritableBlock(Block block) {
      return block.equals(Blocks.AIR) || block instanceof BushBlock || block instanceof SnowLayerBlock;
   }
}
