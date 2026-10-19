package net.regions_unexplored.world.level.block.plant.sapling;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.regions_unexplored.registry.tag.RUBlockTags;

public class RuBrimSaplingBlock extends SaplingBlock implements BonemealableBlock {
   public RuBrimSaplingBlock(TreeGrower treeGrower, Properties properties) {
      super(treeGrower, properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(STAGE, 0));
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockPos below = pos.below();
      return this.mayPlaceOn(level.getBlockState(below), level, below);
   }

   public boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos pos) {
      return state.is(RUBlockTags.BRIM_PLANT_CAN_SURVIVE_ON);
   }
}
