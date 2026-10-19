package com.shin.tensura_kumodesu.effects;

import com.shin.tensura_kumodesu.registry.effects.KumodesuEffects;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import java.awt.Color;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class DeadlyPoisonEffect extends TensuraMobEffect {
   public DeadlyPoisonEffect() {
      super(MobEffectCategory.HARMFUL, new Color(230, 57, 48).getRGB());
   }

   public boolean applyEffectTick(LivingEntity entity, int pAmplifier) {
      if (entity.getHealth() > 0.0F) {
         if (entity.level() instanceof ServerLevel level) {
            MobEffectInstance instance = entity.getEffect(
               level.registryAccess()
                  .registryOrThrow(Registries.MOB_EFFECT)
                  .getHolderOrThrow(ResourceKey.create(Registries.MOB_EFFECT, KumodesuEffects.DEADLY_POISON.getId()))
            );
            if (instance == null) {
               return true;
            }

            float damage = 15.0F * (pAmplifier + 1);
            entity.hurt(
               TensuraDamageHelper.getUUIDDamageSource(
                     TensuraDamageTypes.FATAL_POISON, level, instance.tensura$getSource(), instance.tensura$getSourceAbility()
                  )
                  .tensura$setDodgeBypass(),
               damage
            );
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
      int k = 25 >> amplifier;
      return k <= 0 || duration % k == 0;
   }
}
