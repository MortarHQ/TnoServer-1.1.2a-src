package net.regions_unexplored.world.level.block.plant.other;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.regions_unexplored.registry.RUBlocks;

public class DeadShrubBlock extends BushBlock {
   public static final MapCodec<DeadShrubBlock> CODEC = simpleCodec(DeadShrubBlock::new);
   protected static final float AABB_OFFSET = 6.0F;
   protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);
   protected static final VoxelShape SHAPE_SMALL_DESERT_SHRUB = Block.box(2.0, 0.0, 2.0, 14.0, 9.0, 14.0);

   public DeadShrubBlock(Properties properties) {
      super(properties);
   }

   protected MapCodec<? extends BushBlock> codec() {
      return CODEC;
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return state == RUBlocks.SMALL_DESERT_SHRUB.get().defaultBlockState() ? SHAPE_SMALL_DESERT_SHRUB : SHAPE;
   }

   protected boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos pos) {
      return state.is(BlockTags.DEAD_BUSH_MAY_PLACE_ON);
   }
}
