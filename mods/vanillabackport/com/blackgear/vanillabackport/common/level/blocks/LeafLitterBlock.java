package com.blackgear.vanillabackport.common.level.blocks;

import com.blackgear.vanillabackport.common.registries.ModBlockStateProperties;
import com.mojang.serialization.MapCodec;
import java.util.function.BiFunction;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LeafLitterBlock extends BushBlock {
   public static final MapCodec<LeafLitterBlock> CODEC = simpleCodec(LeafLitterBlock::new);
   public static final int MIN_SEGMENT = 1;
   public static final int MAX_SEGMENT = 4;
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
   public static final IntegerProperty AMOUNT = ModBlockStateProperties.SEGMENT_AMOUNT;
   private static final BiFunction<Direction, Integer, VoxelShape> SHAPE_BY_PROPERTIES = Util.memoize(
      (direction, value) -> {
         VoxelShape shape = Shapes.empty();
         VoxelShape[] shapes = new VoxelShape[]{
            Block.box(8.0, 0.0, 8.0, 16.0, 3.0, 16.0),
            Block.box(8.0, 0.0, 0.0, 16.0, 3.0, 8.0),
            Block.box(0.0, 0.0, 0.0, 8.0, 3.0, 8.0),
            Block.box(0.0, 0.0, 8.0, 8.0, 3.0, 16.0)
         };

         for (int index = 0; index < value; index++) {
            int i = Math.floorMod(index - direction.get2DDataValue(), 4);
            shape = Shapes.or(shape, shapes[i]);
         }

         return shape.singleEncompassing();
      }
   );

   public MapCodec<LeafLitterBlock> codec() {
      return CODEC;
   }

   public LeafLitterBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(FACING, Direction.NORTH)).setValue(AMOUNT, 1));
   }

   public BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }

   public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
      return !useContext.isSecondaryUseActive() && useContext.getItemInHand().is(this.asItem()) && (Integer)state.getValue(AMOUNT) < 4
         || super.canBeReplaced(state, useContext);
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockPos below = pos.below();
      return level.getBlockState(below).isFaceSturdy(level, below, Direction.UP);
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE_BY_PROPERTIES.apply((Direction)state.getValue(FACING), (Integer)state.getValue(AMOUNT));
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockState state = context.getLevel().getBlockState(context.getClickedPos());
      return state.is(this)
         ? (BlockState)state.setValue(AMOUNT, Math.min(4, (Integer)state.getValue(AMOUNT) + 1))
         : (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, AMOUNT});
   }
}
