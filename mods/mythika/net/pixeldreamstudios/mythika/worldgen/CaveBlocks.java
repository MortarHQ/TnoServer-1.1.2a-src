package net.pixeldreamstudios.mythika.worldgen;

import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.WallSide;

final class CaveBlocks {
   static final BlockState TREAD = (BlockState)Blocks.COBBLESTONE_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM);
   static final BlockState HANGING_LANTERN = (BlockState)Blocks.LANTERN.defaultBlockState().setValue(BlockStateProperties.HANGING, true);
   static final BlockState CHAIN = Blocks.CHAIN.defaultBlockState();
   static final BlockState HEARTH_FIRE = (BlockState)((BlockState)Blocks.CAMPFIRE.defaultBlockState().setValue(BlockStateProperties.LIT, true))
      .setValue(BlockStateProperties.SIGNAL_FIRE, true);

   private CaveBlocks() {
   }

   static BlockState rock(int x, int y, int z) {
      int roll = Variance.at(x, y, z, 21) % 100;
      if (roll < 55) {
         return Blocks.STONE.defaultBlockState();
      } else if (roll < 75) {
         return Blocks.ANDESITE.defaultBlockState();
      } else {
         return roll < 90 ? Blocks.TUFF.defaultBlockState() : Blocks.COBBLESTONE.defaultBlockState();
      }
   }

   static BlockState floor(int x, int z) {
      int roll = Variance.at(x, 0, z, 22) % 100;
      if (roll < 34) {
         return Blocks.COBBLESTONE.defaultBlockState();
      } else if (roll < 60) {
         return Blocks.STONE.defaultBlockState();
      } else if (roll < 80) {
         return Blocks.ANDESITE.defaultBlockState();
      } else {
         return roll < 92 ? Blocks.TUFF.defaultBlockState() : Blocks.MOSSY_COBBLESTONE.defaultBlockState();
      }
   }

   static BlockState scorched(int x, int z) {
      int roll = Variance.at(x, 0, z, 23) % 100;
      if (roll < 55) {
         return Blocks.BLACKSTONE.defaultBlockState();
      } else {
         return roll < 80 ? Blocks.BASALT.defaultBlockState() : Blocks.COBBLESTONE.defaultBlockState();
      }
   }

   static BlockState dripstone(Direction towards, int i, int length) {
      DripstoneThickness thickness = i == length - 1
         ? DripstoneThickness.TIP
         : (i == 0 ? DripstoneThickness.BASE : (i == 1 ? DripstoneThickness.MIDDLE : DripstoneThickness.FRUSTUM));
      return (BlockState)((BlockState)Blocks.POINTED_DRIPSTONE.defaultBlockState().setValue(PointedDripstoneBlock.TIP_DIRECTION, towards))
         .setValue(PointedDripstoneBlock.THICKNESS, thickness);
   }

   static BlockState dryStone(Axis along) {
      BlockState state = (BlockState)Blocks.COBBLESTONE_WALL.defaultBlockState().setValue(WallBlock.UP, false);
      return along == Axis.X
         ? (BlockState)((BlockState)state.setValue(WallBlock.EAST_WALL, WallSide.TALL)).setValue(WallBlock.WEST_WALL, WallSide.TALL)
         : (BlockState)((BlockState)state.setValue(WallBlock.NORTH_WALL, WallSide.TALL)).setValue(WallBlock.SOUTH_WALL, WallSide.TALL);
   }
}
