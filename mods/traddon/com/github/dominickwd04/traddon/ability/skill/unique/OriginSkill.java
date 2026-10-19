package com.github.dominickwd04.traddon.ability.skill.unique;

import com.github.dominickwd04.traddon.config.ability.skill.TrAddonSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.extra.ThoughtAccelerationSkill;
import io.github.manasmods.tensura.ability.skill.resist.ResistSkill;
import io.github.manasmods.tensura.ability.skill.resist.ResistSkill.ResistType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;

public class OriginSkill extends ResistSkill {
   public static final TrAddonSkillConfig.Origin CONFIG = ((TrAddonSkillConfig)ConfigRegistry.getConfig(TrAddonSkillConfig.class)).origin;
   protected static final ResourceLocation ORIGIN = ResourceLocation.fromNamespaceAndPath("traddon", "origin");

   public OriginSkill() {
      super(SkillType.UNIQUE, ResistType.RESISTANCE);
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return false;
   }

   public double getDefaultAcquiringMagiculeCost() {
      return CONFIG.mpAcquirement;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return instance.getMastery() >= 0.0;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isToggled();
   }

   public boolean isDamageResisted(LivingEntity entity, DamageSource damageSource, ManasSkillInstance instance) {
      if (TensuraDamageHelper.isAbnormal(damageSource)) {
         return true;
      } else if (TensuraDamageHelper.isNaturalEffects(damageSource)) {
         return true;
      } else {
         return TensuraDamageHelper.isPoison(damageSource) ? true : TensuraDamageHelper.isPhysicalAttack(damageSource);
      }
   }

   public boolean onBeingDamaged(ManasSkillInstance instance, LivingEntity entity, DamageSource source, float amount) {
      if (!instance.isToggled()) {
         return true;
      } else if (!TensuraDamageHelper.isAbnormal(source)) {
         return true;
      } else if (ResistSkill.isNullificationDisabled(entity.level())) {
         return true;
      } else if (this.getResistanceDamageMultiplier(true) > 0.0) {
         return true;
      } else if (this.getHpMultiplierForResistance(true) >= 0.0) {
         return true;
      } else {
         return this.isNullificationBypass(source) ? true : this.isResistanceBypass(source);
      }
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      double maxHealth = entity.getMaxHealth() - TensuraStorages.getEffectFrom(entity).getSeveranceAmount();
      if (!(entity instanceof Player) || !(entity.getHealth() >= maxHealth)) {
         entity.addEffect(
            new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SELF_REGENERATION), 240, CONFIG.regenLevel - 1, false, false, false)
         );
         if (entity.getHealth() < maxHealth) {
            CompoundTag tag = instance.getOrCreateTag();
            int time = tag.getInt("activatedTimes");
            if (time % BASE_CONFIG.Mastery.masteryActivateTime == 0) {
               instance.addMasteryPoint(entity);
            }

            tag.putInt("activatedTimes", time + 1);
         }
      }
   }

   public boolean canBeSlotted(ManasSkillInstance instance, int mode) {
      return instance.getMastery() < 0.0;
   }

   public List<Holder<MobEffect>> getImmuneEffects(ManasSkillInstance instance, LivingEntity entity) {
      return List.of(
         MobEffects.HUNGER,
         MobEffects.POISON,
         MobEffects.BLINDNESS,
         MobEffects.CONFUSION,
         MobEffects.DARKNESS,
         MobEffects.DIG_SLOWDOWN,
         MobEffects.MOVEMENT_SLOWDOWN,
         MobEffects.WEAKNESS,
         TensuraMobEffects.getReference(TensuraMobEffects.BURDEN),
         TensuraMobEffects.getReference(TensuraMobEffects.CHILL),
         TensuraMobEffects.getReference(TensuraMobEffects.FRAGILITY),
         TensuraMobEffects.getReference(TensuraMobEffects.CURSE),
         TensuraMobEffects.getReference(TensuraMobEffects.FATAL_POISON),
         TensuraMobEffects.getReference(TensuraMobEffects.INFECTION),
         TensuraMobEffects.getReference(TensuraMobEffects.PARALYSIS),
         TensuraMobEffects.getReference(TensuraMobEffects.PETRIFICATION)
      );
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      if (!(instance.getMastery() < 0.0) && !instance.isTemporarySkill()) {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         existence.setBlessed(true);
         existence.markDirty();
      }
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      ThoughtAccelerationSkill.onToggle(instance, entity, ORIGIN, true);
      AttributeInstance melee = entity.getAttribute(TensuraAttributes.MELEE_DODGE_CHANCE);
      if (melee != null) {
         melee.addOrReplacePermanentModifier(new AttributeModifier(ORIGIN, CONFIG.meleeDodge, Operation.ADD_VALUE));
      }

      AttributeInstance projectile = entity.getAttribute(TensuraAttributes.PROJECTILE_DODGE_CHANCE);
      if (projectile != null) {
         projectile.addOrReplacePermanentModifier(new AttributeModifier(ORIGIN, CONFIG.projectileDodge, Operation.ADD_VALUE));
      }

      AttributeInstance negate = entity.getAttribute(TensuraAttributes.DODGE_NEGATE_CHANCE);
      if (negate != null) {
         negate.addOrReplacePermanentModifier(new AttributeModifier(ORIGIN, CONFIG.dodgeNegation, Operation.ADD_VALUE));
      }

      AttributeInstance learning = entity.getAttribute(TensuraAttributes.ABILITY_LEARNING_GAIN);
      if (learning != null) {
         learning.addOrReplacePermanentModifier(new AttributeModifier(ORIGIN, CONFIG.learningPoint, Operation.ADD_VALUE));
      }

      AttributeInstance mastery = entity.getAttribute(TensuraAttributes.ABILITY_MASTERY_GAIN);
      if (mastery != null) {
         mastery.addOrReplacePermanentModifier(new AttributeModifier(ORIGIN, CONFIG.masteryPoint, Operation.ADD_VALUE));
      }

      double bonusAura = CONFIG.auraPercentage;
      AttributeInstance aura = entity.getAttribute(TensuraAttributes.AURA_GAIN);
      if (aura != null && !aura.hasModifier(ORIGIN)) {
         aura.addOrReplacePermanentModifier(new AttributeModifier(ORIGIN, bonusAura, Operation.ADD_VALUE));
      }

      double bonusMagicule = CONFIG.magiculePercentage;
      AttributeInstance magicule = entity.getAttribute(TensuraAttributes.MAGICULE_GAIN);
      if (magicule != null && !magicule.hasModifier(ORIGIN)) {
         magicule.addOrReplacePermanentModifier(new AttributeModifier(ORIGIN, bonusMagicule, Operation.ADD_VALUE));
      }

      entity.addEffect(
         new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SELF_REGENERATION), 240, CONFIG.regenLevel - 1, false, false, false)
      );
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      ThoughtAccelerationSkill.onToggle(instance, entity, ORIGIN, false);
      AttributeInstance melee = entity.getAttribute(TensuraAttributes.MELEE_DODGE_CHANCE);
      if (melee != null) {
         melee.removeModifier(ORIGIN);
      }

      AttributeInstance projectile = entity.getAttribute(TensuraAttributes.PROJECTILE_DODGE_CHANCE);
      if (projectile != null) {
         projectile.removeModifier(ORIGIN);
      }

      AttributeInstance negate = entity.getAttribute(TensuraAttributes.DODGE_NEGATE_CHANCE);
      if (negate != null) {
         negate.removeModifier(ORIGIN);
      }

      AttributeInstance learning = entity.getAttribute(TensuraAttributes.ABILITY_LEARNING_GAIN);
      if (learning != null) {
         learning.removeModifier(ORIGIN);
      }

      AttributeInstance mastery = entity.getAttribute(TensuraAttributes.ABILITY_MASTERY_GAIN);
      if (mastery != null) {
         mastery.removeModifier(ORIGIN);
      }

      AttributeInstance aura = entity.getAttribute(TensuraAttributes.AURA_GAIN);
      if (aura != null) {
         aura.removeModifier(ORIGIN);
      }

      AttributeInstance magicule = entity.getAttribute(TensuraAttributes.MAGICULE_GAIN);
      if (magicule != null) {
         magicule.removeModifier(ORIGIN);
      }

      entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.SELF_REGENERATION));
   }
}
