package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DirectionalBudBlock extends Block implements SimpleWaterloggedBlock {
   public static final MapCodec<DirectionalBudBlock> CODEC = simpleCodec(DirectionalBudBlock::new);
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   public static final DirectionProperty FACING = BlockStateProperties.FACING;
   protected final VoxelShape northAabb;
   protected final VoxelShape southAabb;
   protected final VoxelShape eastAabb;
   protected final VoxelShape westAabb;
   protected final VoxelShape upAabb;
   protected final VoxelShape downAabb;

   public DirectionalBudBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.defaultBlockState().setValue(WATERLOGGED, false)).setValue(FACING, Direction.UP));
      double border = (16.0 - this.getShapeWidth()) / 2.0;
      this.upAabb = Block.box(border, 0.0, border, 16.0 - border, this.getShapeHeight(), 16.0 - border);
      this.downAabb = Block.box(border, 16.0 - this.getShapeHeight(), border, 16.0 - border, 16.0, 16.0 - border);
      this.northAabb = Block.box(border, border, 16.0 - this.getShapeHeight(), 16.0 - border, 16.0 - border, 16.0);
      this.southAabb = Block.box(border, border, 0.0, 16.0 - border, 16.0 - border, this.getShapeHeight());
      this.eastAabb = Block.box(0.0, border, border, this.getShapeHeight(), 16.0 - border, 16.0 - border);
      this.westAabb = Block.box(16.0 - this.getShapeHeight(), border, border, 16.0, 16.0 - border, 16.0 - border);
   }

   protected MapCodec<? extends DirectionalBudBlock> codec() {
      return CODEC;
   }

   protected double getShapeWidth() {
      return 10.0;
   }

   protected double getShapeHeight() {
      return 5.0;
   }

   public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
      Direction direction = (Direction)state.getValue(FACING);

      return switch (direction) {
         case NORTH -> this.northAabb;
         case SOUTH -> this.southAabb;
         case EAST -> this.eastAabb;
         case WEST -> this.westAabb;
         case DOWN -> this.downAabb;
         default -> this.upAabb;
      };
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      Direction direction = (Direction)state.getValue(FACING);
      BlockPos attachPos = pos.relative(direction.getOpposite());
      BlockState attachState = level.getBlockState(attachPos);
      return attachState.isFaceSturdy(level, attachPos, direction);
   }

   public BlockState updateShape(BlockState state, Direction direction, BlockState blockState, LevelAccessor levelAccessor, BlockPos pos, BlockPos blockPos) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         levelAccessor.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
      }

      return direction == ((Direction)state.getValue(FACING)).getOpposite() && !state.canSurvive(levelAccessor, pos)
         ? Blocks.AIR.defaultBlockState()
         : super.updateShape(state, direction, blockState, levelAccessor, pos, blockPos);
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      LevelAccessor levelaccessor = context.getLevel();
      BlockPos blockpos = context.getClickedPos();
      return (BlockState)((BlockState)this.defaultBlockState().setValue(WATERLOGGED, levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER))
         .setValue(FACING, context.getClickedFace());
   }

   public BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{WATERLOGGED, FACING});
   }
}
