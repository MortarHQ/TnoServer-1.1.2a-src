package com.progwml6.ironchest.common.ai;

import com.progwml6.ironchest.common.block.regular.AbstractIronChestBlock;
import com.progwml6.ironchest.common.block.regular.entity.AbstractIronChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.CatSitOnBlockGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class IronChestCatSitOnBlockGoal extends CatSitOnBlockGoal {
   public IronChestCatSitOnBlockGoal(Cat cat, double speedModifier) {
      super(cat, speedModifier);
   }

   protected boolean isValidTarget(LevelReader worldIn, BlockPos pos) {
      if (!worldIn.isEmptyBlock(pos.above())) {
         return false;
      }

      BlockState blockstate = worldIn.getBlockState(pos);
      Block block = blockstate.getBlock();
      return block instanceof AbstractIronChestBlock ? AbstractIronChestBlockEntity.getOpenCount(worldIn, pos) < 1 : super.isValidTarget(worldIn, pos);
   }
}
