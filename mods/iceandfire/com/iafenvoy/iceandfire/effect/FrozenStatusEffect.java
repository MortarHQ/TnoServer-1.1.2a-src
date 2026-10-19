package com.iafenvoy.iceandfire.effect;

import com.iafenvoy.iceandfire.entity.IceDragonEntity;
import com.iafenvoy.iceandfire.registry.IafBlocks;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;

public class FrozenStatusEffect extends MobEffect {
   public FrozenStatusEffect() {
      super(MobEffectCategory.NEUTRAL, -4600330);
   }

   public boolean applyEffectTick(LivingEntity entity, int amplifier) {
      if (entity instanceof IceDragonEntity || entity.isDeadOrDying()) {
         return false;
      }

      if (entity.isOnFire()) {
         entity.clearFire();
         return false;
      }

      if (!(entity instanceof Player player && player.isCreative())) {
         entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.25, 1.0, 0.25));
         if (!(entity instanceof EnderDragon) && !entity.onGround()) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, -0.2, 0.0));
         }
      }

      return true;
   }

   public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
      return true;
   }

   public void onEffectStarted(LivingEntity entity, int amplifier) {
      playSound(entity, SoundEvents.GLASS_PLACE, 1);
   }

   public void onRemoved(LivingEntity entity) {
      if (entity.level() instanceof ServerLevel serverWorld) {
         for (int i = 0; i < 15; i++) {
            serverWorld.sendParticles(
               new BlockParticleOption(ParticleTypes.BLOCK, ((Block)IafBlocks.DRAGON_ICE.get()).defaultBlockState()),
               entity.getX() + (entity.getRandom().nextDouble() - 0.5) * entity.getBbWidth(),
               entity.getY() + entity.getRandom().nextDouble() * entity.getBbHeight(),
               entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * entity.getBbWidth(),
               0,
               0.0,
               0.0,
               0.0,
               0.0
            );
         }
      }

      playSound(entity, SoundEvents.GLASS_BREAK, 3);
   }

   private static void playSound(LivingEntity entity, SoundEvent sound, int volume) {
      entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), sound, SoundSource.NEUTRAL, volume, 1.0F);
   }
}
