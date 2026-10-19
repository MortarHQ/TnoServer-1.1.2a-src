package gardensofthedead.block;

import gardensofthedead.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SoulSporeBlock extends SoulSporeBaseBlock {
   public static final BooleanProperty TOP = BooleanProperty.create("top");
   public static final BooleanProperty GROWING = BooleanProperty.create("growing");
   public static final VoxelShape TOP_SHAPE_UP = box(1.0, 0.0, 1.0, 15.0, 8.0, 15.0);
   public static final VoxelShape TOP_SHAPE_DOWN = box(1.0, 8.0, 1.0, 15.0, 16.0, 15.0);
   public static final int MAX_LENGTH_LONG = 8;
   public static final int MAX_LENGTH_SHORT = 3;

   public SoulSporeBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.defaultBlockState().setValue(TOP, true)).setValue(GROWING, true));
   }

   public static int getMaxLength(BlockState supportingBlock) {
      return supportingBlock.is(Blocks.SOUL_SAND) ? 8 : 3;
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{TOP, GROWING});
   }

   @Override
   public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos updatedPos) {
      return direction == state.getValue(DIRECTION)
            && (newState.is(this) || newState.is((Block)ModBlocks.GLOWING_SOUL_SPORE.get()))
            && state.getValue(TOP)
            && newState.getValue(DIRECTION) == state.getValue(DIRECTION)
         ? (BlockState)((BlockState)state.setValue(TOP, false)).setValue(GROWING, true)
         : super.updateShape(state, direction, newState, level, pos, updatedPos);
   }

   @Override
   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      if (state.hasProperty(TOP) && (Boolean)state.getValue(TOP)) {
         return state.hasProperty(DIRECTION) && state.getValue(DIRECTION) == Direction.UP ? TOP_SHAPE_UP : TOP_SHAPE_DOWN;
      } else {
         return super.getShape(state, level, pos, context);
      }
   }

   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
      Direction direction = (Direction)state.getValue(DIRECTION);
      if (randomSource.nextFloat() < 0.1F && level.isEmptyBlock(pos.relative(direction))) {
         int length = 1;

         while (level.getBlockState(pos.relative(direction.getOpposite(), length)).is(this)) {
            length++;
         }

         BlockState supportingBlock = level.getBlockState(pos.relative(direction.getOpposite(), length));
         int maxLength = getMaxLength(supportingBlock);
         if ((Boolean)state.getValue(TOP) && (length >= maxLength || randomSource.nextInt(maxLength - length + 1) == 0)) {
            if (length > 1 && randomSource.nextInt(8) == 0) {
               BlockState glowingSoulSpore = (BlockState)((SoulSporeBaseBlock)ModBlocks.GLOWING_SOUL_SPORE.get())
                  .defaultBlockState()
                  .setValue(DIRECTION, direction);
               level.setBlockAndUpdate(pos, glowingSoulSpore);
            } else {
               level.setBlock(pos, (BlockState)state.setValue(GROWING, false), 4);
            }
         } else {
            BlockState grownState = (BlockState)this.defaultBlockState().setValue(DIRECTION, direction);
            level.setBlockAndUpdate(pos.relative(direction), grownState);
            level.setBlock(pos, (BlockState)state.setValue(TOP, false), 4);
         }
      }
   }

   public boolean isRandomlyTicking(BlockState state) {
      return (Boolean)state.getValue(GROWING);
   }
}
