package io.github.Memoires.mysticism.effect.ability;

import io.github.Memoires.mysticism.ability.skill.intrinsic.TenacitySkill;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.storage.effect.EffectStorage;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.awt.Color;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class AuraHealingEffect extends TensuraMobEffect {
   public AuraHealingEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(222, 56, 56).getRGB());
   }

   public boolean applyEffectTick(LivingEntity entity, int pAmplifier) {
      if (!SkillUtils.shouldCancelInteraction(entity) && entity.isAlive()) {
         double maxHealth = EffectStorage.getSeveranceMaxHealth(entity);
         if (maxHealth <= 0.0) {
            return false;
         }

         MobEffectInstance regeneration = entity.getEffect(TensuraMobEffects.getReference(MysticismMobEffects.AURA_HEALING));
         if (regeneration == null) {
            return true;
         }

         Skills skills = SkillAPI.getSkillsFrom(entity);
         AbilitySlot skill = regeneration.tensura$getSourceAbility();
         Optional<ManasSkillInstance> instance = skill != null && skill.getSkill() != null ? skills.getSkill(skill.getSkill()) : Optional.empty();
         float lackedHealth = (float)maxHealth - entity.getHealth();
         if (lackedHealth > 0.0F && !this.shouldCancelAuraHealing(entity)) {
            double cost;
            if (instance.isPresent() && instance.get().isMastered(entity)) {
               cost = TenacitySkill.CONFIG.auraCostMastered;
            } else {
               cost = TenacitySkill.CONFIG.auraCost;
            }

            float lackedAura = (float)EnergyHelper.isOutOfAuraConsuming(entity, lackedHealth * cost, entity.getType().equals(EntityType.PLAYER) ? 0.0 : 10.0);
            if (lackedAura > 0.0F) {
               lackedHealth = (float)(lackedHealth - lackedAura / cost);
               this.heal(entity, lackedHealth);
               if (!entity.getType().equals(EntityType.PLAYER)) {
                  return false;
               }

               if (instance.isPresent() && instance.get().isToggled()) {
                  instance.get().setToggled(false);
                  skills.markDirty();
                  entity.sendSystemMessage(
                     Component.translatable("tensura.skill.lack_aura.toggled_off", new Object[]{instance.get().getChatDisplayName(true)})
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.RED))
                  );
                  return false;
               }
            }

            this.heal(entity, lackedHealth);
         }

         if (pAmplifier >= 1 && TenacitySkill.CONFIG.shouldRegenSHPOnMastery) {
            IExistence existence = TensuraStorages.getExistenceFrom(entity);
            double SHP = existence.getSpiritualHealth();
            double maxSHP = entity.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH);
            double lackedSHP = maxSHP - SHP;
            if (lackedSHP > 0.0) {
               double cost = instance.isPresent() && instance.get().isMastered(entity)
                  ? TenacitySkill.CONFIG.shpAuraCostMastered
                  : TenacitySkill.CONFIG.shpAuraCost;
               double lackedAura = EnergyHelper.isOutOfAuraConsuming(entity, (int)(lackedSHP * cost), entity.getType().equals(EntityType.PLAYER) ? 0.0 : 10.0);
               if (lackedAura > 0.0) {
                  lackedSHP -= lackedAura / cost;
                  this.healSHP(entity, existence, SHP, lackedSHP, maxSHP);
                  if (!entity.getType().equals(EntityType.PLAYER)) {
                     return false;
                  }

                  if (instance.isPresent() && instance.get().isToggled()) {
                     instance.get().setToggled(false);
                     skills.markDirty();
                     entity.sendSystemMessage(
                        Component.translatable("tensura.skill.lack_aura.toggled_off", new Object[]{instance.get().getChatDisplayName(true)})
                           .withStyle(ChatFormatting.RED)
                     );
                     return false;
                  }
               } else {
                  this.healSHP(entity, existence, SHP, lackedSHP, maxSHP);
               }
            }

            return true;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   private void heal(LivingEntity entity, float lackedHealth) {
      entity.heal(lackedHealth);
      TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.COMPOSTER, 1.0);
      TensuraParticleHelper.spawnServerParticles(
         entity.level(),
         TensuraParticleUtils.getGreenWave(0.9F, entity.getBbWidth() * 3.0F, -0.5F, true),
         entity.getX(),
         entity.getY() + entity.getBbHeight() * 0.33,
         entity.getZ()
      );
      TensuraParticleHelper.spawnServerParticles(
         entity.level(),
         TensuraParticleUtils.getGreenWave(0.9F, entity.getBbWidth() * 3.0F, -0.5F, true),
         entity.getX(),
         entity.getY() + entity.getBbHeight() * 0.66,
         entity.getZ()
      );
   }

   private void healSHP(LivingEntity entity, IExistence existence, double currentSHP, double lackedSHP, double maxSHP) {
      existence.setSpiritualHealth(Math.min(currentSHP + lackedSHP, maxSHP));
      TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.COMPOSTER, 1.0);
      TensuraParticleHelper.spawnServerParticles(
         entity.level(),
         TensuraParticleUtils.getGreenWave(0.9F, entity.getBbWidth() * 3.0F, -0.5F, true),
         entity.getX(),
         entity.getY() + entity.getBbHeight() * 0.33,
         entity.getZ()
      );
      TensuraParticleHelper.spawnServerParticles(
         entity.level(),
         TensuraParticleUtils.getGreenWave(0.9F, entity.getBbWidth() * 3.0F, -0.5F, true),
         entity.getX(),
         entity.getY() + entity.getBbHeight() * 0.66,
         entity.getZ()
      );
   }

   public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
      return pDuration % 10 == 0;
   }

   private boolean shouldCancelAuraHealing(LivingEntity entity) {
      MobEffectInstance instance = entity.getEffect(TensuraMobEffects.getReference(MysticismMobEffects.AURA_HEALING));
      if (instance != null && instance.getAmplifier() >= 1) {
         return false;
      }

      if (entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.CURSE))) {
         return true;
      }

      if (entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.FROST))) {
         return true;
      }

      if (entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.BRIMSTONE_FLAMES))) {
         return true;
      }

      MobEffectInstance backBurnt = entity.getEffect(TensuraMobEffects.getReference(TensuraMobEffects.BLACK_BURN));
      return backBurnt != null && backBurnt.getAmplifier() >= 1;
   }

   public static boolean canStopDeath(DamageSource source, LivingEntity entity) {
      if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         return false;
      }

      if (source.tensura$getBarrierBypassLevel() >= 1.75) {
         return false;
      }

      if (EffectStorage.getSeveranceMaxHealth(entity) <= 0.0F) {
         return false;
      }

      MobEffectInstance regeneration = entity.getEffect(TensuraMobEffects.getReference(MysticismMobEffects.AURA_HEALING));
      return regeneration == null ? false : regeneration.getAmplifier() >= 1;
   }
}
