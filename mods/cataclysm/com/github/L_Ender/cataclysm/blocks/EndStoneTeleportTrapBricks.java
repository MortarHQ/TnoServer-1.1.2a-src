package com.github.L_Ender.cataclysm.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class EndStoneTeleportTrapBricks extends TrapBlock {
   public EndStoneTeleportTrapBricks(Properties properties) {
      super(properties);
   }

   @Override
   public void stepOn(Level worldIn, BlockPos pos, BlockState state, Entity entityIn) {
      activate(worldIn.getBlockState(pos), worldIn, pos, entityIn);
      super.stepOn(worldIn, pos, state, entityIn);
   }

   private static void activate(BlockState state, Level world, BlockPos pos, Entity entity) {
      if (!(Boolean)state.getValue(LIT) && shouldTrigger(entity)) {
         double d0 = entity.getX() + (entity.level().random.nextDouble() - 0.5) * 16.0;
         double d1 = entity.getY();
         double d2 = entity.getZ() + (entity.level().random.nextDouble() - 0.5) * 16.0;
         ((LivingEntity)entity).randomTeleport(d0, d1, d2, false);
         ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 25));
         world.setBlock(pos, (BlockState)state.setValue(LIT, true), 3);
         world.playSound(null, pos, SoundEvents.ENDERMAN_TELEPORT, SoundSource.BLOCKS, 1.0F, 1.0F);
      }
   }
}
