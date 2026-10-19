package com.benji.netherman.common.block;

import com.benji.netherman.common.block.entity.EyeBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class EyeBlock extends HorizontalDirectionalBlock implements EntityBlock {
   public static final MapCodec<EyeBlock> CODEC = simpleCodec(EyeBlock::new);
   private static final VoxelShape SHAPE_N = Block.box(0.0, 0.0, 14.0, 16.0, 16.0, 16.0);
   private static final VoxelShape SHAPE_E = Block.box(0.0, 0.0, 0.0, 2.0, 16.0, 16.0);
   private static final VoxelShape SHAPE_S = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 2.0);
   private static final VoxelShape SHAPE_W = Block.box(14.0, 0.0, 0.0, 16.0, 16.0, 16.0);

   public EyeBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH));
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING});
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return switch ((Direction)state.getValue(FACING)) {
         case NORTH, UP, DOWN -> SHAPE_N;
         case SOUTH -> SHAPE_S;
         case WEST -> SHAPE_W;
         case EAST -> SHAPE_E;
         default -> throw new MatchException(null, null);
      };
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new EyeBlockEntity(pos, state);
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }
}
