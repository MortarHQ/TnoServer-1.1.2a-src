package com.yungnickyoung.minecraft.yungscavebiomes.effect;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class FrostMobEffect extends InstantenousMobEffect {
   private final int minFreezeTicks;
   private final int additionalFreezeTicks;
   private final int maxFreezeTicks;

   public FrostMobEffect(int minFreezeTicks, int additionalFreezeTicks, int maxFreezeTicks) {
      super(MobEffectCategory.HARMFUL, 246524);
      this.minFreezeTicks = minFreezeTicks;
      this.additionalFreezeTicks = additionalFreezeTicks;
      this.maxFreezeTicks = maxFreezeTicks;
   }

   public boolean applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
      if (!(
         livingEntity instanceof ServerPlayer serverPlayer && (serverPlayer.isSpectator() || serverPlayer.serverLevel().getDifficulty() == Difficulty.PEACEFUL)
      )) {
         int frozenTicks = Math.max(this.minFreezeTicks * (amplifier + 1), livingEntity.getTicksFrozen() + this.additionalFreezeTicks * (amplifier + 1));
         frozenTicks = Math.min(frozenTicks, this.maxFreezeTicks * (amplifier + 1));
         livingEntity.setTicksFrozen(frozenTicks);
         return true;
      } else {
         return true;
      }
   }

   public void applyInstantenousEffect(Entity areaCloud, Entity areaCloudThrower, @NotNull LivingEntity target, int amplifier, double damage) {
      this.applyEffectTick(target, amplifier);
   }
}
