package com.mortal_cultivation.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SpiritRecoveryHerbBlock extends DoublePlantBlock {
   public static final MapCodec<SpiritRecoveryHerbBlock> CODEC = simpleCodec(SpiritRecoveryHerbBlock::new);
   private static final VoxelShape LOWER_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
   private static final VoxelShape UPPER_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 8.0, 13.0);

   public SpiritRecoveryHerbBlock(Properties properties) {
      super(properties);
   }

   public MapCodec<SpiritRecoveryHerbBlock> codec() {
      return CODEC;
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      Vec3 offset = state.getOffset(level, pos);
      VoxelShape shape = state.getValue(HALF) == DoubleBlockHalf.UPPER ? UPPER_SHAPE : LOWER_SHAPE;
      return shape.move(offset.x, offset.y, offset.z);
   }
}
