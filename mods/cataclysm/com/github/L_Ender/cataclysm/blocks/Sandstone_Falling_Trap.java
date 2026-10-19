package com.github.L_Ender.cataclysm.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class Sandstone_Falling_Trap extends SandStoneTrapBlock implements Fallable {
   public Sandstone_Falling_Trap(Properties properties) {
      super(properties);
   }

   @Override
   public void stepOn(Level worldIn, BlockPos pos, BlockState state, Entity entityIn) {
      this.activate(worldIn.getBlockState(pos), worldIn, pos, entityIn);
      super.stepOn(worldIn, pos, state, entityIn);
   }

   private void activate(BlockState state, Level world, BlockPos pos, Entity entity) {
      if (!(Boolean)state.getValue(LIT) && shouldTrigger(entity) && isFree(world.getBlockState(pos.below())) && pos.getY() >= world.getMinBuildHeight()) {
         FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(world, pos, state);
         this.falling(fallingblockentity);
      }
   }

   public static boolean isFree(BlockState p_53242_) {
      return p_53242_.isAir() || p_53242_.is(BlockTags.FIRE) || p_53242_.liquid() || p_53242_.canBeReplaced();
   }

   protected void falling(FallingBlockEntity p_53206_) {
   }
}
