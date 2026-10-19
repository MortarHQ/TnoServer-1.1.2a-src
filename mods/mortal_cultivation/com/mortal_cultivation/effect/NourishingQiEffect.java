package com.mortal_cultivation.effect;

import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class NourishingQiEffect extends MobEffect {
   private static final double MAGICULE_PERCENT = 0.03;
   private static final double AURA_PERCENT = 0.03;
   private static final float HEALTH_PERCENT = 0.01F;

   public NourishingQiEffect() {
      super(MobEffectCategory.BENEFICIAL, 8900331);
   }

   public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
      return duration % 20 == 0;
   }

   public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
      if (livingEntity instanceof Player player && !player.level().isClientSide) {
         float maxHealth = player.getMaxHealth();
         float healAmount = maxHealth * (0.01F + amplifier * 0.002F);
         player.heal(healAmount);
         double maxMagicule = EnergyHelper.getMaxMagicule(player);
         double regenPercent = 0.03 + amplifier * 0.003;
         double regenAmount = maxMagicule * regenPercent;
         EnergyHelper.gainMagicule(player, regenAmount, GainType.NORMAL);
         double maxAura = EnergyHelper.getMaxAura(player);
         if (maxAura > 0.0) {
            double auraRegenPercent = 0.03 + amplifier * 0.003;
            EnergyHelper.gainAura(player, maxAura * auraRegenPercent, GainType.NORMAL);
         }

         if (player.tickCount % 40 == 0) {
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.PLAYERS, 0.3F, 1.0F);
            if (player.level() instanceof ServerLevel serverLevel) {
               serverLevel.sendParticles(ParticleTypes.DRAGON_BREATH, player.getX(), player.getY() + 1.0, player.getZ(), 8, 0.3, 0.3, 0.3, 0.05);
            }
         }
      }

      return true;
   }

   public String getDescriptionId() {
      return "effect.mortal_cultivation.nourishing";
   }
}
