package io.github.Memoires.mysticism.effect.ability;

import io.github.Memoires.mysticism.damage.MysticismDamageTypes;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import java.awt.Color;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class RupturingEffect extends TensuraMobEffect {
   public RupturingEffect() {
      super(MobEffectCategory.HARMFUL, new Color(255, 20, 0, 255).getRGB());
   }

   public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
      return pDuration % 20 == 0;
   }

   public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
      DamageSource source = TensuraDamageTypes.getDamageSource(livingEntity.level(), MysticismDamageTypes.RUPTURED_VEINS);
      livingEntity.hurt(source, this.getDamagePercentageFromAmplifier(amplifier) * livingEntity.getMaxHealth());
      return true;
   }

   private float getDamagePercentageFromAmplifier(int amplifier) {
      return (amplifier + 1) * 0.05F;
   }
}
