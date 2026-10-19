package net.pixeldreamstudios.mythika.worldgen;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;

final class LabyrinthBlocks {
   static final BlockState HANGING_LANTERN = (BlockState)Blocks.LANTERN.defaultBlockState().setValue(BlockStateProperties.HANGING, true);
   static final BlockState STANDING_LANTERN = Blocks.LANTERN.defaultBlockState();
   static final BlockState TREAD_SLAB = (BlockState)Blocks.STONE_BRICK_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM);
   static final BlockState CHISELED = Blocks.CHISELED_STONE_BRICKS.defaultBlockState();
   static final BlockState CHAIN = Blocks.CHAIN.defaultBlockState();

   private LabyrinthBlocks() {
   }

   static BlockState wall(int x, int y, int z) {
      int roll = variant(x, y, z, 1) % 100;
      if (roll < 55) {
         return Blocks.STONE_BRICKS.defaultBlockState();
      } else if (roll < 78) {
         return Blocks.CRACKED_STONE_BRICKS.defaultBlockState();
      } else {
         return roll < 91 ? Blocks.MOSSY_STONE_BRICKS.defaultBlockState() : Blocks.COBBLESTONE.defaultBlockState();
      }
   }

   static BlockState floor(int x, int z) {
      int roll = variant(x, 0, z, 2) % 100;
      if (roll < 60) {
         return Blocks.STONE_BRICKS.defaultBlockState();
      } else if (roll < 80) {
         return Blocks.CRACKED_STONE_BRICKS.defaultBlockState();
      } else {
         return roll < 93 ? Blocks.COBBLESTONE.defaultBlockState() : Blocks.MOSSY_COBBLESTONE.defaultBlockState();
      }
   }

   static BlockState paved(int x, int z) {
      return variant(x, 0, z, 3) % 100 < 70 ? Blocks.POLISHED_ANDESITE.defaultBlockState() : Blocks.STONE_BRICKS.defaultBlockState();
   }

   static BlockState ceiling(int x, int z) {
      int roll = variant(x, 0, z, 4) % 100;
      if (roll < 70) {
         return Blocks.STONE_BRICKS.defaultBlockState();
      } else {
         return roll < 90 ? Blocks.CRACKED_STONE_BRICKS.defaultBlockState() : Blocks.MOSSY_STONE_BRICKS.defaultBlockState();
      }
   }

   static int variant(int x, int y, int z, int salt) {
      return Variance.at(x, y, z, salt);
   }
}
