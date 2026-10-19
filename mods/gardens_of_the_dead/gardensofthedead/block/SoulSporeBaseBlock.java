package gardensofthedead.block;

import gardensofthedead.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SoulSporeBaseBlock extends Block {
   public static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
   public static final DirectionProperty DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;

   public SoulSporeBaseBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(DIRECTION, Direction.UP));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{DIRECTION});
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(DIRECTION, context.getNearestLookingVerticalDirection().getOpposite());
   }

   public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
      if (!state.canSurvive(level, pos)) {
         level.destroyBlock(pos, true);
      }
   }

   public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos updatedPos) {
      if (!state.canSurvive(level, pos)) {
         level.scheduleTick(pos, this, 1);
      }

      return super.updateShape(state, direction, newState, level, pos, updatedPos);
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      Direction direction = (Direction)state.getValue(DIRECTION);
      BlockPos attachedPos = pos.relative(direction.getOpposite());
      BlockState supportingBlock = level.getBlockState(attachedPos);
      return supportingBlock.is(Blocks.SOUL_SAND)
         || supportingBlock.is(Blocks.SOUL_SOIL)
         || supportingBlock.is(BlockTags.WART_BLOCKS)
         || supportingBlock.is((Block)ModBlocks.SOUL_SPORE.get()) && supportingBlock.getValue(DIRECTION) == direction;
   }
}
