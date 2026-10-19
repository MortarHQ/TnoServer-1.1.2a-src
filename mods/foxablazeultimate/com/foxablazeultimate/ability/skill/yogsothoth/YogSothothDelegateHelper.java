package com.foxablazeultimate.ability.skill.yogsothoth;

import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.EntityEvents.ProjectileHitResult;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.phys.EntityHitResult;

final class YogSothothDelegateHelper {
   private YogSothothDelegateHelper() {
   }

   static TensuraSkillInstance child(ManasSkillInstance parent, ManasSkill target) {
      ManasSkillInstance existing = (ManasSkillInstance)parent.getSubInstances().get(target);
      if (existing instanceof TensuraSkillInstance tensura) {
         if (tensura.getParentSkill() != parent.getSkill()) {
            tensura.setParentSkill(parent.getSkill());
         }

         return tensura;
      } else {
         TensuraSkillInstance created = new TensuraSkillInstance(target);
         created.setMastery(Math.max(0.0, parent.getMastery()));
         parent.addSubInstance(created);
         return created;
      }
   }

   static void importPrerequisite(ManasSkillInstance parent, ManasSkillInstance source) {
      TensuraSkillInstance copied = new TensuraSkillInstance(source.getSkill());
      copied.deserialize(source.toNBT());
      if (source.getSkillId().equals(YogSothothSkill.INFINITY_PRISON)) {
         CompoundTag tag = copied.getTag();
         if (tag != null) {
            tag.remove("SpatialStorage");
         }
      }

      copied.setRemoveTime(-1);
      parent.addSubInstance(copied);
   }

   static boolean onPressed(ManasSkillInstance parent, Skill target, LivingEntity owner, int keyNumber, int parentMode, int childMode) {
      TensuraSkillInstance child = prepare(parent, target, parentMode, childMode);
      if (!target.canInteractSkill(child, owner)) {
         return false;
      }

      double parentMasteryBefore = parent.getMastery();
      double before = child.getMastery();
      target.onPressed(child, owner, keyNumber, childMode);
      finish(parent, owner, child, before, parentMode, childMode);
      if (parent.getMastery() <= parentMasteryBefore && parentMasteryBefore < parent.getSkill().getMaxMastery()) {
         parent.getSkill().addMasteryPoint(parent, owner);
      }

      return true;
   }

   static boolean onHeld(ManasSkillInstance parent, Skill target, LivingEntity owner, int heldTicks, int parentMode, int childMode) {
      TensuraSkillInstance child = prepare(parent, target, parentMode, childMode);
      if (!target.canInteractSkill(child, owner)) {
         return false;
      }

      double before = child.getMastery();
      boolean keepHolding = target.onHeld(child, owner, heldTicks, childMode);
      finish(parent, owner, child, before, parentMode, childMode);
      return keepHolding;
   }

   static void onRelease(ManasSkillInstance parent, Skill target, LivingEntity owner, int heldTicks, int keyNumber, int parentMode, int childMode) {
      TensuraSkillInstance child = prepare(parent, target, parentMode, childMode);
      if (target.canInteractSkill(child, owner)) {
         double before = child.getMastery();
         target.onRelease(child, owner, heldTicks, keyNumber, childMode);
         finish(parent, owner, child, before, parentMode, childMode);
      }
   }

   static void onScroll(ManasSkillInstance parent, Skill target, LivingEntity owner, double delta, int parentMode, int childMode) {
      TensuraSkillInstance child = prepare(parent, target, parentMode, childMode);
      if (target.canInteractSkill(child, owner)) {
         double before = child.getMastery();
         target.onScroll(child, owner, delta, childMode);
         finish(parent, owner, child, before, parentMode, childMode);
      }
   }

   static boolean onBeingDamaged(ManasSkillInstance parent, Skill target, LivingEntity owner, DamageSource source, float amount) {
      TensuraSkillInstance child = child(parent, target);
      if (!target.canInteractSkill(child, owner)) {
         return true;
      }

      double before = child.getMastery();
      boolean result = target.onBeingDamaged(child, owner, source, amount);
      finishState(parent, owner, child, before);
      return result;
   }

   static boolean onTakenDamage(ManasSkillInstance parent, Skill target, LivingEntity owner, DamageSource source, Changeable<Float> amount) {
      TensuraSkillInstance child = child(parent, target);
      if (!target.canInteractSkill(child, owner)) {
         return true;
      }

      double before = child.getMastery();
      boolean result = target.onTakenDamage(child, owner, source, amount);
      finishState(parent, owner, child, before);
      return result;
   }

   static boolean onTouchEntity(
      ManasSkillInstance parent, Skill target, LivingEntity owner, LivingEntity targetEntity, DamageSource source, Changeable<Float> amount
   ) {
      TensuraSkillInstance child = child(parent, target);
      if (!target.canInteractSkill(child, owner)) {
         return true;
      }

      double before = child.getMastery();
      boolean result = target.onTouchEntity(child, owner, targetEntity, source, amount);
      finishState(parent, owner, child, before);
      return result;
   }

   static boolean canIgnoreCoolDown(ManasSkillInstance parent, Skill target, LivingEntity owner, int childMode) {
      TensuraSkillInstance child = child(parent, target);
      return target.canInteractSkill(child, owner) && target.canIgnoreCoolDown(child, owner, childMode);
   }

   static boolean shouldTriggerRelease(ManasSkillInstance parent, Skill target, LivingEntity owner, int keyNumber, int childMode) {
      TensuraSkillInstance child = child(parent, target);
      return target.shouldTriggerReleaseOnHeldInterrupt(child, owner, keyNumber, childMode);
   }

   static boolean canTick(ManasSkillInstance parent, Skill target, LivingEntity owner) {
      TensuraSkillInstance child = child(parent, target);
      return target.canInteractSkill(child, owner) && target.canTick(child, owner);
   }

   static void onTick(ManasSkillInstance parent, Skill target, LivingEntity owner) {
      TensuraSkillInstance child = child(parent, target);
      if (target.canInteractSkill(child, owner) && target.canTick(child, owner)) {
         double before = child.getMastery();
         target.onTick(child, owner);
         finishState(parent, owner, child, before);
      }
   }

   static void onLearnSkill(ManasSkillInstance parent, Skill target, LivingEntity owner) {
      TensuraSkillInstance child = child(parent, target);
      target.onLearnSkill(child, owner);
      propagateDirty(parent, child);
   }

   static void onForgetSkill(ManasSkillInstance parent, Skill target, LivingEntity owner) {
      TensuraSkillInstance child = child(parent, target);
      target.onForgetSkill(child, owner);
      propagateDirty(parent, child);
   }

   static void onToggleOn(ManasSkillInstance parent, Skill target, LivingEntity owner) {
      TensuraSkillInstance child = child(parent, target);
      child.setToggled(true);
      target.onToggleOn(child, owner);
      propagateDirty(parent, child);
   }

   static void onToggleOff(ManasSkillInstance parent, Skill target, LivingEntity owner) {
      TensuraSkillInstance child = child(parent, target);
      child.setToggled(false);
      target.onToggleOff(child, owner);
      propagateDirty(parent, child);
   }

   static boolean onEffectAdded(ManasSkillInstance parent, Skill target, LivingEntity owner, Entity source, Changeable<MobEffectInstance> effect) {
      TensuraSkillInstance child = child(parent, target);
      if (!target.canInteractSkill(child, owner)) {
         return true;
      }

      double before = child.getMastery();
      boolean result = target.onEffectAdded(child, owner, source, effect);
      finishState(parent, owner, child, before);
      return result;
   }

   static boolean onBeingTargeted(ManasSkillInstance parent, Skill target, Changeable<LivingEntity> changedTarget, LivingEntity owner) {
      TensuraSkillInstance child = child(parent, target);
      if (!target.canInteractSkill(child, owner)) {
         return true;
      }

      double before = child.getMastery();
      boolean result = target.onBeingTargeted(child, changedTarget, owner);
      finishState(parent, owner, child, before);
      return result;
   }

   static boolean onDamageEntity(
      ManasSkillInstance parent, Skill target, LivingEntity owner, LivingEntity targetEntity, DamageSource source, Changeable<Float> amount
   ) {
      TensuraSkillInstance child = child(parent, target);
      if (!target.canInteractSkill(child, owner)) {
         return true;
      }

      double before = child.getMastery();
      boolean result = target.onDamageEntity(child, owner, targetEntity, source, amount);
      finishState(parent, owner, child, before);
      return result;
   }

   static void onProjectileHit(
      ManasSkillInstance parent,
      Skill target,
      LivingEntity owner,
      EntityHitResult hitResult,
      Projectile projectile,
      Changeable<ProjectileDeflection> deflection,
      Changeable<ProjectileHitResult> result
   ) {
      TensuraSkillInstance child = child(parent, target);
      if (target.canInteractSkill(child, owner)) {
         double before = child.getMastery();
         target.onProjectileHit(child, owner, hitResult, projectile, deflection, result);
         finishState(parent, owner, child, before);
      }
   }

   static boolean onDeath(ManasSkillInstance parent, Skill target, LivingEntity owner, DamageSource source) {
      TensuraSkillInstance child = child(parent, target);
      if (!target.canInteractSkill(child, owner)) {
         return true;
      }

      double before = child.getMastery();
      boolean result = target.onDeath(child, owner, source);
      finishState(parent, owner, child, before);
      return result;
   }

   static void onRespawn(ManasSkillInstance parent, Skill target, ServerPlayer owner, boolean conqueredEnd) {
      TensuraSkillInstance child = child(parent, target);
      target.onRespawn(child, owner, conqueredEnd);
      propagateDirty(parent, child);
   }

   private static TensuraSkillInstance prepare(ManasSkillInstance parent, Skill target, int parentMode, int childMode) {
      TensuraSkillInstance child = child(parent, target);
      child.setCoolDown(parent.getCoolDown(parentMode), childMode);
      return child;
   }

   private static void finish(ManasSkillInstance parent, LivingEntity owner, TensuraSkillInstance child, double masteryBefore, int parentMode, int childMode) {
      parent.setCoolDown(child.getCoolDown(childMode), parentMode);
      finishState(parent, owner, child, masteryBefore);
   }

   private static void finishState(ManasSkillInstance parent, LivingEntity owner, TensuraSkillInstance child, double masteryBefore) {
      double gained = child.getMastery() - masteryBefore;
      if (gained > 0.0) {
         parent.getSkill().addMasteryPoint(parent, owner, gained);
      }

      propagateDirty(parent, child);
   }

   private static void propagateDirty(ManasSkillInstance parent, TensuraSkillInstance child) {
      if (child.isDirty()) {
         parent.markDirty();
         child.resetDirty();
      }
   }
}
