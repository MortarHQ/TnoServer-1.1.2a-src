package net.unusual.block_factorys_bosses.block.abstracts;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class AbstractStackableBlock extends Block {
   protected final IntegerProperty stackableProperty;
   protected final int minStack;
   protected final int maxStack;

   public AbstractStackableBlock(Properties properties, IntegerProperty stackableProperty, int minStack, int maxStack) {
      super(properties);
      this.stackableProperty = stackableProperty;
      this.minStack = minStack;
      this.maxStack = maxStack;
   }

   public IntegerProperty getStackableProperty() {
      return this.stackableProperty;
   }

   public int getMinStack() {
      return this.minStack;
   }

   public int getMaxStack() {
      return this.maxStack;
   }

   protected abstract void createBlockStateDefinition(Builder<Block, BlockState> var1);

   public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
      return !context.isSecondaryUseActive() && context.getItemInHand().is(this.asItem()) && (Integer)state.getValue(this.stackableProperty) < this.maxStack
         || super.canBeReplaced(state, context);
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      if (!this.canSurvive(this.defaultBlockState(), context.getLevel(), context.getClickedPos())) {
         return null;
      }

      BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
      return blockstate.is(this)
         ? (BlockState)blockstate.setValue(this.stackableProperty, Math.min(this.maxStack, (Integer)blockstate.getValue(this.stackableProperty) + 1))
         : this.defaultBlockState();
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      return level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP);
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
      return facing == Direction.DOWN && !this.canSurvive(state, level, pos)
         ? Blocks.AIR.defaultBlockState()
         : super.updateShape(state, facing, facingState, level, pos, facingPos);
   }
}
