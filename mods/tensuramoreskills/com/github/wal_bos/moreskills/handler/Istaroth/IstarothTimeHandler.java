package com.github.wal_bos.moreskills.handler.Istaroth;

import com.github.wal_bos.moreskills.ability.skill.shades.IstarothSkill;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import java.util.UUID;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public final class IstarothTimeHandler {
   private static boolean initialized;

   private IstarothTimeHandler() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         IstarothCommon.init();
         IstarothPassives.init();
         IstarothActions.init();
         IstarothUltimate.init();
         IstarothDeathHandler.init();
      }
   }

   public static void tickOwner(IstarothSkill skill, ManasSkillInstance instance, LivingEntity owner) {
      if (!owner.level().isClientSide()) {
         IstarothCommon.tick(owner);
         IstarothPassives.tick(instance, owner);
         IstarothActions.tick(owner.level());
         IstarothUltimate.tick(skill, instance, owner);
         if (owner.tickCount % 8 == 0) {
            IstarothFx.passiveClockAura(owner);
         }
      }
   }

   public static boolean hasActiveTemporalState(LivingEntity entity) {
      return IstarothPassives.hasActiveState(entity) || IstarothActions.hasActiveState(entity) || IstarothUltimate.hasActiveState(entity);
   }

   public static boolean isHoldingWorldStop(LivingEntity entity) {
      return IstarothUltimate.isHoldingWorldStop(entity);
   }

   public static boolean isLastSecondActive(LivingEntity entity) {
      return IstarothUltimate.isLastSecondActive(entity);
   }

   public static void clearOwner(LivingEntity entity) {
      IstarothPassives.clearOwner(entity);
      IstarothActions.clearOwner(entity);
      IstarothUltimate.clearOwner(entity);
   }

   public static void clearOwnerSoft(LivingEntity entity) {
      IstarothPassives.clearOwner(entity);
      IstarothActions.clearOwner(entity);
      IstarothUltimate.clearNonFinalOwner(entity);
   }

   public static void chronicle(ManasSkillInstance instance, LivingEntity entity) {
      IstarothActions.chronicle(instance, entity);
   }

   public static void manualAnchor(ManasSkillInstance instance, LivingEntity entity) {
      IstarothCommon.manualAnchor(instance, entity);
   }

   public static void timeSkip(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      IstarothActions.timeSkip(skill, instance, entity);
   }

   public static void momentumTheft(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      IstarothActions.momentumTheft(skill, instance, entity);
   }

   public static void borrowedSeconds(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      IstarothActions.borrowedSeconds(skill, instance, entity);
   }

   public static void eternalMoment(ManasSkillInstance instance, LivingEntity entity) {
      IstarothActions.eternalMoment(instance, entity);
   }

   public static void stillnessOfTheKing(ManasSkillInstance instance, LivingEntity entity) {
      IstarothPassives.stillnessOfTheKing(instance, entity);
   }

   public static void objectPermanence(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      IstarothActions.objectPermanence(skill, instance, entity);
   }

   public static void rewindSelf(ManasSkillInstance instance, LivingEntity entity) {
      IstarothCommon.rewindSelf(instance, entity);
   }

   public static void rewindTarget(ManasSkillInstance instance, LivingEntity entity) {
      IstarothActions.rewindTarget(instance, entity);
   }

   public static void delayedVerdict(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      IstarothActions.delayedVerdict(skill, instance, entity);
   }

   public static void effectBeforeCause(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      IstarothActions.effectBeforeCause(skill, instance, entity);
   }

   public static void twentySecondLoop(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      IstarothActions.twentySecondLoop(skill, instance, entity);
   }

   public static void condemnedRepeat(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      IstarothActions.condemnedRepeat(skill, instance, entity);
   }

   public static void futureExile(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      IstarothActions.futureExile(skill, instance, entity);
   }

   public static void pastBurial(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      IstarothActions.pastBurial(skill, instance, entity);
   }

   public static void worldOfStillSeconds(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity, int heldTicks) {
      IstarothUltimate.worldOfStillSeconds(skill, instance, entity, heldTicks);
   }

   public static void clockbreakRelease(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      IstarothUltimate.clockbreakRelease(skill, instance, entity);
   }

   public static void lastSecondOfCreation(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      IstarothUltimate.lastSecondOfCreation(skill, instance, entity);
   }

   public static void addTemporalDebt(LivingEntity target, float amount, UUID owner) {
      IstarothCommon.addTemporalDebt(target, amount, owner);
   }

   public static void onIstarothHitTarget(LivingEntity owner, LivingEntity target, float damage) {
      IstarothCommon.addTemporalDebt(target, damage * 0.35F, owner.getUUID());
      IstarothPassives.onOwnerDamagedTarget(owner, target, damage);
      IstarothActions.onIstarothHitTarget(owner, target, damage);
      IstarothUltimate.onIstarothHitTarget(owner, target, damage);
   }

   public static void onOwnerThreatened(LivingEntity entity, DamageSource source, Changeable<Float> amount) {
      IstarothPassives.onOwnerThreatened(entity, source, amount);
   }

   public static boolean tryStillnessReduction(LivingEntity entity, Changeable<Float> amount) {
      return IstarothPassives.tryStillnessReduction(entity, amount);
   }

   public static boolean shouldChronologyDeny(LivingEntity entity, DamageSource source, float damage) {
      return IstarothCommon.shouldChronologyDeny(entity, source, damage);
   }

   public static boolean shouldRejectEffect(LivingEntity entity, MobEffectInstance effect) {
      return IstarothPassives.shouldRejectEffect(entity, effect);
   }
}
