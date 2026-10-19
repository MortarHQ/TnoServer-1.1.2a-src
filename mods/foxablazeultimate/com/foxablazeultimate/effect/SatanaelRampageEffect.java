package com.foxablazeultimate.effect;

import com.foxablazeultimate.config.SatanaelConfig;
import com.foxablazeultimate.registry.FoxAblazeUltimateMobEffects;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import java.awt.Color;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.player.Player;

public class SatanaelRampageEffect extends TensuraMobEffect {
   private static final ResourceLocation MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "satanael_rampage");
   private static final ResourceKey<Attribute> SWIM_SPEED = ResourceKey.create(
      Registries.ATTRIBUTE, ResourceLocation.fromNamespaceAndPath("manascore", "swim_speed_multiplier")
   );
   private static final String TAG_DOMINATED_BY = "FoxSatanaelDominatedBy";
   private static final String TAG_DOMINATED_UNTIL = "FoxSatanaelDominatedUntil";
   private static final String TAG_LAST_FORCED_ATTACK = "FoxSatanaelLastForcedAttack";

   public SatanaelRampageEffect() {
      super(MobEffectCategory.NEUTRAL, new Color(145, 18, 18).getRGB());
      SatanaelConfig.SatanaelSettings config = SatanaelConfig.get().Satanael;
      this.addAttributeModifier(Attributes.ARMOR, MODIFIER_ID, config.rampageArmorPerLevel, Operation.ADD_VALUE);
      this.addAttributeModifier(Attributes.ATTACK_DAMAGE, MODIFIER_ID, config.rampageAttackPerLevel, Operation.ADD_VALUE);
      this.addAttributeModifier(Attributes.ATTACK_SPEED, MODIFIER_ID, config.rampageAttackSpeedPerLevel, Operation.ADD_VALUE);
      this.addAttributeModifier(Attributes.MOVEMENT_SPEED, MODIFIER_ID, config.rampageMovementSpeedPerLevel, Operation.ADD_VALUE);
      BuiltInRegistries.ATTRIBUTE
         .getHolder(SWIM_SPEED)
         .ifPresent(attribute -> this.addAttributeModifier(attribute, MODIFIER_ID, config.rampageSwimSpeedPerLevel, Operation.ADD_VALUE));
      this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, MODIFIER_ID, config.rampageKnockbackResistancePerLevel, Operation.ADD_VALUE);
   }

   public boolean applyEffectTick(LivingEntity entity, int amplifier) {
      if (entity.level().isClientSide()) {
         return true;
      } else if (!(entity instanceof Mob mob && isDominationActive(entity))) {
         return true;
      } else if (SkillUtils.isSkillToggled(entity, (ManasSkill)ResistanceSkills.SPIRITUAL_ATTACK_NULLIFICATION.get())) {
         clearDomination(entity);
         entity.removeEffect(FoxAblazeUltimateMobEffects.SATANAEL_RAMPAGE);
         return true;
      } else {
         UUID ownerId = entity.getPersistentData().getUUID("FoxSatanaelDominatedBy");
         double radius = Math.max(1.0, SatanaelConfig.get().Satanael.dominationRadius * 2.0);
         List<LivingEntity> targets = mob.level()
            .getEntitiesOfClass(LivingEntity.class, mob.getBoundingBox().inflate(radius), targetx -> this.isValidDominationTarget(mob, targetx, ownerId));
         if (targets.isEmpty()) {
            mob.setTarget(null);
            return true;
         } else {
            LivingEntity currentTarget = mob.getTarget();
            if (currentTarget != null && this.isValidDominationTarget(mob, currentTarget, ownerId)) {
               this.forceDominationTarget(mob, currentTarget);
               return true;
            } else {
               LivingEntity target = targets.get(mob.getRandom().nextInt(targets.size()));
               this.forceDominationTarget(mob, target);
               return true;
            }
         }
      }
   }

   private void forceDominationTarget(Mob mob, LivingEntity target) {
      mob.setAggressive(true);
      mob.setTarget(target);
      if (mob.getBrain().checkMemory(MemoryModuleType.ATTACK_TARGET, MemoryStatus.REGISTERED)) {
         mob.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, target);
      }

      mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
      mob.getNavigation().moveTo(target, Math.max(0.1, SatanaelConfig.get().Satanael.dominationForcedMovementSpeed));
      long gameTime = mob.level().getGameTime();
      long lastAttack = mob.getPersistentData().getLong("FoxSatanaelLastForcedAttack");
      int attackInterval = Math.max(1, SatanaelConfig.get().Satanael.dominationForcedAttackIntervalTicks);
      if (gameTime - lastAttack >= attackInterval && mob.isWithinMeleeAttackRange(target)) {
         mob.getPersistentData().putLong("FoxSatanaelLastForcedAttack", gameTime);
         mob.swing(InteractionHand.MAIN_HAND);
         mob.doHurtTarget(target);
      }
   }

   public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
      return duration % Math.max(1, SatanaelConfig.get().Satanael.dominationRetargetIntervalTicks) == 0;
   }

   public static void markDominated(LivingEntity target, LivingEntity owner, int durationTicks) {
      target.getPersistentData().putUUID("FoxSatanaelDominatedBy", owner.getUUID());
      target.getPersistentData().putLong("FoxSatanaelDominatedUntil", target.level().getGameTime() + Math.max(1, durationTicks));
   }

   public static boolean isDominatedBy(LivingEntity target, LivingEntity owner) {
      return isDominatedBy(target, owner.getUUID());
   }

   private static boolean isDominatedBy(LivingEntity target, UUID ownerId) {
      return isDominationActive(target) && ownerId.equals(target.getPersistentData().getUUID("FoxSatanaelDominatedBy"));
   }

   private static boolean isDominationActive(LivingEntity target) {
      if (target.getPersistentData().hasUUID("FoxSatanaelDominatedBy") && target.getPersistentData().contains("FoxSatanaelDominatedUntil")) {
         if (target.level().getGameTime() < target.getPersistentData().getLong("FoxSatanaelDominatedUntil")) {
            return true;
         }

         clearDomination(target);
         return false;
      } else {
         return false;
      }
   }

   private static void clearDomination(LivingEntity target) {
      target.getPersistentData().remove("FoxSatanaelDominatedBy");
      target.getPersistentData().remove("FoxSatanaelDominatedUntil");
   }

   private boolean isValidDominationTarget(Mob mob, LivingEntity target, UUID ownerId) {
      if (target != mob && target.isAlive() && !target.isSpectator()) {
         return target instanceof Player player && player.isCreative() ? false : isDominatedBy(target, ownerId);
      } else {
         return false;
      }
   }
}
