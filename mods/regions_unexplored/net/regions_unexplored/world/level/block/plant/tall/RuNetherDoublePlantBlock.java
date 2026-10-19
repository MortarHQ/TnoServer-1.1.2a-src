package net.regions_unexplored.world.level.block.plant.tall;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class RuNetherDoublePlantBlock extends DoublePlantBlock {
   public RuNetherDoublePlantBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(HALF, DoubleBlockHalf.LOWER));
   }

   public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
      return false;
   }

   protected boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos pos) {
      return state.is(BlockTags.NYLIUM) || state.is(Blocks.SOUL_SOIL) || super.mayPlaceOn(state, getter, pos);
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockPos blockPos = pos.below();
      BlockState groundState = level.getBlockState(blockPos);
      return state.getValue(HALF) != DoubleBlockHalf.UPPER
         ? this.mayPlaceOn(groundState, level, blockPos)
         : groundState.is(this) && groundState.getValue(HALF) == DoubleBlockHalf.LOWER;
   }
}
