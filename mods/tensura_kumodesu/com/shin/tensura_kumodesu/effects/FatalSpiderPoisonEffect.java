package com.shin.tensura_kumodesu.effects;

import com.shin.tensura_kumodesu.registry.effects.KumodesuEffects;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import java.awt.Color;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class FatalSpiderPoisonEffect extends TensuraMobEffect {
   public FatalSpiderPoisonEffect() {
      super(MobEffectCategory.HARMFUL, new Color(230, 57, 48).getRGB());
   }

   public boolean applyEffectTick(LivingEntity entity, int pAmplifier) {
      if (!(entity.getHealth() > 0.0F)) {
         return false;
      }

      if (entity.level() instanceof ServerLevel level) {
         MobEffectInstance instance = entity.getEffect(
            level.registryAccess()
               .registryOrThrow(Registries.MOB_EFFECT)
               .getHolderOrThrow(ResourceKey.create(Registries.MOB_EFFECT, KumodesuEffects.FATAL_SPIDER_POISON.getId()))
         );
         if (instance == null) {
            return true;
         }

         float percentdamage = 0.1F * entity.getHealth();
         float spiritualDamage = 150.0F * (pAmplifier + 1);
         Entity source = instance != null && instance.tensura$hasSource() ? level.getEntity(instance.tensura$getSource()) : null;
         DamageSource damageSource = TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.SOUL_SCATTER, source);
         TensuraDamageHelper.directSpiritualHurt(entity, (Entity)null, damageSource, spiritualDamage);
         entity.hurt(
            TensuraDamageHelper.getUUIDDamageSource(TensuraDamageTypes.FATAL_POISON, level, instance.tensura$getSource(), instance.tensura$getSourceAbility())
               .tensura$setDodgeBypass(),
            percentdamage
         );
      }

      return true;
   }

   public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
      int k = 25 >> pAmplifier;
      return k > 0 ? pDuration % k == 0 : true;
   }
}
