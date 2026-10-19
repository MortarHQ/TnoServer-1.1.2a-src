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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class LayeredBlock extends Block {
   public static final MapCodec<LayeredBlock> CODEC = simpleCodec(LayeredBlock::new);
   public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS;
   protected static final VoxelShape[] SHAPE_BY_LAYER = new VoxelShape[]{
      Shapes.empty(),
      Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
   };

   public MapCodec<? extends LayeredBlock> codec() {
      return CODEC;
   }

   public LayeredBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(LAYERS, 1));
   }

   protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
      return false;
   }

   public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
      return SHAPE_BY_LAYER[blockState.getValue(LAYERS)];
   }

   public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
      return SHAPE_BY_LAYER[blockState.getValue(LAYERS) - 1];
   }

   public VoxelShape getBlockSupportShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
      return SHAPE_BY_LAYER[blockState.getValue(LAYERS)];
   }

   public VoxelShape getVisualShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
      return SHAPE_BY_LAYER[blockState.getValue(LAYERS)];
   }

   public boolean useShapeForLightOcclusion(BlockState blockState) {
      return true;
   }

   public float getShadeBrightness(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
      return blockState.getValue(LAYERS) == 8 ? 0.2F : 1.0F;
   }

   public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
      BlockState belowState = levelReader.getBlockState(blockPos.below());
      return Block.isFaceFull(belowState.getCollisionShape(levelReader, blockPos.below()), Direction.UP)
         || belowState.is(this) && (Integer)belowState.getValue(LAYERS) == 8;
   }

   public BlockState updateShape(
      BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2
   ) {
      return !blockState.canSurvive(levelAccessor, blockPos)
         ? Blocks.AIR.defaultBlockState()
         : super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
   }

   public boolean canBeReplaced(BlockState blockState, BlockPlaceContext blockPlaceContext) {
      int i = (Integer)blockState.getValue(LAYERS);
      if (!blockPlaceContext.getItemInHand().is(this.asItem()) || i >= 8) {
         return i == 1;
      } else {
         return blockPlaceContext.replacingClickedOnBlock() ? blockPlaceContext.getClickedFace() == Direction.UP : true;
      }
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
      BlockState blockState = blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos());
      if (blockState.is(this)) {
         int i = (Integer)blockState.getValue(LAYERS);
         return (BlockState)blockState.setValue(LAYERS, Math.min(8, i + 1));
      } else {
         return super.getStateForPlacement(blockPlaceContext);
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{LAYERS});
   }
}
