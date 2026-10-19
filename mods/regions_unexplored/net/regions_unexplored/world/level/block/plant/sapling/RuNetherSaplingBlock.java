package net.regions_unexplored.world.level.block.plant.sapling;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class RuNetherSaplingBlock extends SaplingBlock implements BonemealableBlock {
   public RuNetherSaplingBlock(TreeGrower treeGrower, Properties properties) {
      super(treeGrower, properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(STAGE, 0));
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos p_51030_) {
      BlockPos blockpos = p_51030_.below();
      return this.mayPlaceOn(level.getBlockState(blockpos), level, blockpos);
   }

   public boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos pos) {
      return state.is(BlockTags.NYLIUM);
   }
}
