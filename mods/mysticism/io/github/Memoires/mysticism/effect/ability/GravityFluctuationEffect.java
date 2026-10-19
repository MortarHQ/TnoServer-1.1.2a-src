package io.github.Memoires.mysticism.effect.ability;

import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import java.awt.Color;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class GravityFluctuationEffect extends TensuraMobEffect {
   public GravityFluctuationEffect() {
      super(MobEffectCategory.HARMFUL, new Color(150, 30, 255, 255).getRGB());
   }

   public boolean applyEffectTick(LivingEntity entity, int amplifier) {
      if (entity.level().isClientSide()) {
         return true;
      }

      if (entity.isPassenger()) {
         return true;
      }

      double amplitude = 0.04 * (1 + amplifier);
      double periodTicks = 40.0 / (1.0 + amplifier * 0.25);
      double omega = (Math.PI * 2) / periodTicks;
      long t = entity.level().getGameTime();
      double phase = t + entity.getId() * 7L;
      double yKick = amplitude * Math.sin(phase * omega);
      Vec3 v = entity.getDeltaMovement();
      if (entity.onGround() && yKick < 0.0) {
         yKick *= 0.15;
      }

      double newY = Mth.clamp(v.y * 0.9 + yKick, -0.4, 0.4);
      entity.setDeltaMovement(v.x, newY, v.z);
      entity.resetFallDistance();
      return true;
   }

   public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
      return true;
   }
}
