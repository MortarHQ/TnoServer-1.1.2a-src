package net.regions_unexplored.world.level.block.plant.flower;

import com.mojang.serialization.MapCodec;
import java.util.function.BiFunction;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.regions_unexplored.util.RUUtils;

public class RULeafLitterBlock extends BushBlock {
   public static final MapCodec<RULeafLitterBlock> CODEC = simpleCodec(RULeafLitterBlock::new);
   public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
   public static final IntegerProperty AMOUNT = BlockStateProperties.FLOWER_AMOUNT;
   private static final BiFunction<Direction, Integer, VoxelShape> SHAPE_BY_PROPERTIES = RUUtils.memoize(
      (direction, amount) -> {
         VoxelShape[] shapes = new VoxelShape[]{
            Block.box(8.0, 0.0, 8.0, 16.0, 3.0, 16.0),
            Block.box(8.0, 0.0, 0.0, 16.0, 3.0, 8.0),
            Block.box(0.0, 0.0, 0.0, 8.0, 3.0, 8.0),
            Block.box(0.0, 0.0, 8.0, 8.0, 3.0, 16.0)
         };
         VoxelShape mergedShapes = Shapes.empty();

         for (int i = 0; i < amount; i++) {
            int index = Math.floorMod(i - direction.get2DDataValue(), 4);
            mergedShapes = Shapes.or(mergedShapes, shapes[index]);
         }

         return mergedShapes.singleEncompassing();
      }
   );

   public RULeafLitterBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(this.getSegmentAmountProperty(), 1)
      );
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return SHAPE_BY_PROPERTIES.apply((Direction)state.getValue(FACING), (Integer)state.getValue(AMOUNT));
   }

   protected MapCodec<RULeafLitterBlock> codec() {
      return CODEC;
   }

   public IntegerProperty getSegmentAmountProperty() {
      return AMOUNT;
   }

   public boolean canBeReplaced(BlockState state, BlockPlaceContext context, IntegerProperty segment) {
      return !context.isSecondaryUseActive() && context.getItemInHand().is(state.getBlock().asItem()) && (Integer)state.getValue(segment) < 4;
   }

   public BlockState getStateForPlacement(BlockPlaceContext context, Block block, IntegerProperty segment, EnumProperty<Direction> facing) {
      BlockState state = context.getLevel().getBlockState(context.getClickedPos());
      return state.is(block)
         ? (BlockState)state.setValue(segment, Math.min(4, (Integer)state.getValue(segment) + 1))
         : (BlockState)block.defaultBlockState().setValue(facing, context.getHorizontalDirection().getOpposite());
   }

   public BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }

   public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
      return this.canBeReplaced(state, context, this.getSegmentAmountProperty()) ? true : super.canBeReplaced(state, context);
   }

   protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockPos belowPos = pos.below();
      return level.getBlockState(belowPos).isFaceSturdy(level, belowPos, Direction.UP);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return this.getStateForPlacement(context, this, this.getSegmentAmountProperty(), FACING);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, this.getSegmentAmountProperty()});
   }
}
