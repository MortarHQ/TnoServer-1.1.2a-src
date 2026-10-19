package net.jadenxgamer.netherexp.core.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
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

public class JNELayerBlock extends Block {
   public static final int MAX_HEIGHT = 8;
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
   public static final int HEIGHT_IMPASSABLE = 5;
   private final TagKey<Block> canSurvive;
   private final TagKey<Block> cannotSurvive;

   public JNELayerBlock(TagKey<Block> canSurvive, TagKey<Block> cannotSurvive, Properties properties) {
      super(properties);
      this.canSurvive = canSurvive;
      this.cannotSurvive = cannotSurvive;
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(LAYERS, 1));
   }

   protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
      return pathComputationType == PathComputationType.LAND ? (Integer)state.getValue(LAYERS) < 5 : false;
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE_BY_LAYER[state.getValue(LAYERS)];
   }

   protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE_BY_LAYER[state.getValue(LAYERS) - 1];
   }

   protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
      return SHAPE_BY_LAYER[state.getValue(LAYERS)];
   }

   protected VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE_BY_LAYER[state.getValue(LAYERS)];
   }

   protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockState blockState = level.getBlockState(pos.below());
      if (blockState.is(this.cannotSurvive)) {
         return false;
      } else {
         return blockState.is(this.canSurvive)
            ? true
            : Block.isFaceFull(blockState.getCollisionShape(level, pos.below()), Direction.UP)
               || blockState.is(this) && (Integer)blockState.getValue(LAYERS) == 8;
      }
   }

   protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
   }

   protected boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
      int layers = (Integer)state.getValue(LAYERS);
      if (!useContext.getItemInHand().is(this.asItem()) || layers >= 8) {
         return layers == 1;
      } else {
         return useContext.replacingClickedOnBlock() ? useContext.getClickedFace() == Direction.UP : true;
      }
   }

   protected boolean useShapeForLightOcclusion(BlockState state) {
      return true;
   }

   protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
      return state.getValue(LAYERS) == 8 ? 0.2F : 1.0F;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockState state = context.getLevel().getBlockState(context.getClickedPos());
      if (state.is(this)) {
         int layers = (Integer)state.getValue(LAYERS);
         return (BlockState)state.setValue(LAYERS, Math.min(8, layers + 1));
      } else {
         return super.getStateForPlacement(context);
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{LAYERS});
   }
}
