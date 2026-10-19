package io.github.Memoires.mysticism.effect.ability;

import io.github.Memoires.mysticism.damage.MysticismDamageTypes;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import java.awt.Color;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class DragonBurnEffect extends TensuraMobEffect {
   public DragonBurnEffect() {
      super(MobEffectCategory.HARMFUL, new Color(143, 20, 20).getRGB());
   }

   public boolean applyEffectTick(LivingEntity entity, int pAmplifier) {
      if (entity.getHealth() > 0.0F) {
         if (entity.level() instanceof ServerLevel serverLevel) {
            MobEffectInstance mobEffectInstance = entity.getEffect(TensuraMobEffects.getReference(MysticismMobEffects.DRAGON_BURN));
            if (mobEffectInstance == null) {
               return true;
            }

            float damage = entity.getMaxHealth() * 0.05F;
            DamageSource source = TensuraDamageTypes.getDamageSource(entity.level(), MysticismDamageTypes.DRAGON_EMBER);
            entity.hurt(source, damage);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
      return pDuration % 20 == 0;
   }
}
