package net.regions_unexplored.world.level.block.plant.tall;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.regions_unexplored.registry.RUBlocks;

public class ElephantEarBlock extends DoublePlantBlock {
   protected static final float AABB_OFFSET = 6.0F;
   protected static final VoxelShape SHAPE_LOWER = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape SHAPE_UPPER = Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0);

   public ElephantEarBlock(Properties properties) {
      super(properties);
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      if (state == RUBlocks.ELEPHANT_EAR.get().defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)) {
         return SHAPE_LOWER;
      } else {
         return state == RUBlocks.ELEPHANT_EAR.get().defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER) ? SHAPE_UPPER : SHAPE_LOWER;
      }
   }

   public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
      return false;
   }
}
