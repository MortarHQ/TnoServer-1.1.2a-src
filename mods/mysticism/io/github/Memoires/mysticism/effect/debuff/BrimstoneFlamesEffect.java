package io.github.Memoires.mysticism.effect.debuff;

import io.github.Memoires.mysticism.config.effect.EffectConfig;
import io.github.Memoires.mysticism.damage.MysticismDamageTypes;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.storage.MysticismStorages;
import io.github.Memoires.mysticism.storage.effect.IMysticismEffect;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import java.awt.Color;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;

public class BrimstoneFlamesEffect extends TensuraMobEffect {
   private static final EffectConfig.BrimstoneFlames CONFIG = ((EffectConfig)ConfigRegistry.getConfig(EffectConfig.class)).BrimstoneFlames;

   public BrimstoneFlamesEffect() {
      super(MobEffectCategory.HARMFUL, new Color(209, 155, 47).getRGB());
   }

   public void onEffectStarted(LivingEntity entity, int i) {
      super.onEffectStarted(entity, i);
      IMysticismEffect effect = MysticismStorages.getEffectDataFrom(entity);
      effect.setOnBrimstoneFlames(true);
      effect.markDirty();
   }

   public boolean applyEffectTick(LivingEntity entity, int pAmplifier) {
      if (entity.level() instanceof ServerLevel serverLevel) {
         MobEffectInstance instance = entity.getEffect(TensuraMobEffects.getReference(MysticismMobEffects.BRIMSTONE_FLAMES));
         if (instance == null) {
            return true;
         }

         float damage = (pAmplifier + 1) * CONFIG.brimstoneFlamesDamage;
         DamageSource damageSource = TensuraDamageHelper.getUUIDDamageSource(
               MysticismDamageTypes.BRIMSTONE_FLAMES, serverLevel, instance.tensura$getSource(), instance.tensura$getSourceAbility()
            )
            .tensura$setElement(Element.FLAME)
            .tensura$setDodgeBypass();
         if (CONFIG.brimstoneFlamesBypassResistances) {
            damageSource.tensura$setResistanceBypassLevel(1.0F);
         }

         TensuraDamageHelper.hurtSplitElemental(entity, damageSource, 0.9F, damage);
      }

      return true;
   }

   public void onAttributeRemoved(LivingEntity entity, MobEffectInstance instance) {
      IMysticismEffect effect = MysticismStorages.getEffectDataFrom(entity);
      effect.setOnBrimstoneFlames(false);
      effect.markDirty();
   }

   public void onEffectAdded(LivingEntity livingEntity, int i) {
      super.onEffectAdded(livingEntity, i);
   }

   public void onMobRemoved(LivingEntity livingEntity, int i, RemovalReason removalReason) {
      IMysticismEffect effect = MysticismStorages.getEffectDataFrom(livingEntity);
      effect.setOnBrimstoneFlames(false);
      effect.markDirty();
   }

   public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
      return pDuration % 20 == 0;
   }
}
