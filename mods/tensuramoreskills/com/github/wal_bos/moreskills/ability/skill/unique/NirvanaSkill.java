package com.github.wal_bos.moreskills.ability.skill.unique;

import com.github.wal_bos.moreskills.handler.NirvanaHandler;
import com.github.wal_bos.moreskills.nirvana.NirvanaData;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.EntityEvents.ProjectileHitResult;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.subclass.ISubAbilityModeHolder;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class NirvanaSkill extends Skill implements ISubAbilityModeHolder {
   public NirvanaSkill() {
      super(SkillType.UNIQUE);
   }

   public int getMaxMastery() {
      return SKILL_CONFIG.Mastery.masteryUnique;
   }

   public double getDefaultAcquiringMagiculeCost() {
      return 0.0;
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      if (entity instanceof Player player && !NirvanaData.root(player).getBoolean("NirvanaHandledDeath")) {
         NirvanaData.clearAll(player);
      }
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/unique/nirvana.png");
   }

   public int getSubModeOffset(ManasSkillInstance instance) {
      return 1;
   }

   public int getModes(ManasSkillInstance instance) {
      List<AbilitySlot> slots = ISubAbilityModeHolder.getSubSlots(instance);
      return slots.isEmpty() ? 1 : 1 + slots.size();
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode == 0 ? instance.getModes() - 1 : mode - 1;
      } else {
         return mode == instance.getModes() - 1 ? 0 : mode + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return mode == 0 ? "nirvana.migration" : "nirvana.archived";
   }

   public Component getModeName(ManasSkillInstance instance, int mode) {
      if (mode == 0) {
         return Component.translatable("skill.moreskills.nirvana.migration");
      }

      List<AbilitySlot> slots = ISubAbilityModeHolder.getSubSlots(instance);
      int index = mode - this.getSubModeOffset(instance);
      if (index >= 0 && index < slots.size()) {
         AbilitySlot slot = slots.get(index);
         ManasSkill skill = slot.getSkill();
         if (skill instanceof TensuraSkill tensuraSkill) {
            ManasSkillInstance sub = (ManasSkillInstance)instance.getSubInstances().get(skill);
            if (sub != null) {
               return (Component)(Objects.equals(tensuraSkill.getModeId(sub, slot.getMode()), "default")
                  ? skill.getName()
                  : tensuraSkill.getModeName(sub, slot.getMode()));
            }
         }

         return (Component)(skill == null ? super.getModeName(instance, mode) : skill.getName());
      } else {
         return super.getModeName(instance, mode);
      }
   }

   public boolean canScroll(ManasSkillInstance instance, LivingEntity entity, int mode) {
      ManasSkillInstance sub = this.getSubInstance(instance, mode);
      AbilitySlot slot = this.getSlot(instance, mode);
      return sub != null && slot != null && sub.canScroll(entity, slot.getMode());
   }

   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (mode == 0) {
         return true;
      }

      ManasSkillInstance sub = this.getSubInstance(instance, mode);
      AbilitySlot slot = this.getSlot(instance, mode);
      return sub != null && slot != null && sub.canIgnoreCoolDown(entity, slot.getMode());
   }

   public boolean shouldShowCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return mode == 0 ? false : super.shouldShowCoolDown(instance, entity, mode);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (mode == 0) {
         if (entity instanceof ServerPlayer player) {
            NirvanaHandler.openArchive(player);
         }
      } else {
         ManasSkillInstance sub = this.getSubInstance(instance, mode);
         AbilitySlot slot = this.getSlot(instance, mode);
         if (sub != null && slot != null) {
            if (sub != null && slot != null) {
               sub.onPressed(entity, keyNumber, slot.getMode());
               sub.addHeldAttributeModifiers(entity, slot.getMode());
               instance.setCoolDown(Math.max(sub.getCoolDown(slot.getMode()), instance.getCoolDown(mode)), mode);
               sub.setCoolDown(0, slot.getMode());
            }
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      ManasSkillInstance sub = this.getSubInstance(instance, mode);
      AbilitySlot slot = this.getSlot(instance, mode);
      return sub != null && slot != null && sub.onHeld(entity, heldTicks, slot.getMode());
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      ManasSkillInstance sub = this.getSubInstance(instance, mode);
      AbilitySlot slot = this.getSlot(instance, mode);
      if (sub != null && slot != null) {
         sub.onRelease(entity, heldTicks, keyNumber, slot.getMode());
         sub.removeAttributeModifiers(entity, slot.getMode());
         instance.setCoolDown(Math.max(sub.getCoolDown(slot.getMode()), instance.getCoolDown(mode)), mode);
         sub.setCoolDown(0, slot.getMode());
      }
   }

   public void onScroll(ManasSkillInstance instance, LivingEntity entity, double delta, int mode) {
      ManasSkillInstance sub = this.getSubInstance(instance, mode);
      AbilitySlot slot = this.getSlot(instance, mode);
      if (sub != null && slot != null) {
         sub.onScroll(entity, delta, slot.getMode());
      }
   }

   public boolean onBeingDamaged(ManasSkillInstance instance, LivingEntity entity, DamageSource source, float amount) {
      for (AbilitySlot slot : ISubAbilityModeHolder.getSubSlots(instance)) {
         ManasSkillInstance sub = (ManasSkillInstance)instance.getSubInstances().get(slot.getSkill());
         if (sub != null
            && this.isInSlot(entity, instance, ISubAbilityModeHolder.getSubSlots(instance).indexOf(slot) + this.getSubModeOffset(instance))
            && !sub.onBeingDamaged(entity, source, amount)) {
            return false;
         }
      }

      return true;
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity entity, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      for (AbilitySlot slot : ISubAbilityModeHolder.getSubSlots(instance)) {
         ManasSkillInstance sub = (ManasSkillInstance)instance.getSubInstances().get(slot.getSkill());
         if (sub != null
            && this.isInSlot(entity, instance, ISubAbilityModeHolder.getSubSlots(instance).indexOf(slot) + this.getSubModeOffset(instance))
            && !sub.onDamageEntity(entity, target, source, amount)) {
            return false;
         }
      }

      return true;
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity entity, DamageSource source, Changeable<Float> amount) {
      for (AbilitySlot slot : ISubAbilityModeHolder.getSubSlots(instance)) {
         ManasSkillInstance sub = (ManasSkillInstance)instance.getSubInstances().get(slot.getSkill());
         if (sub != null
            && this.isInSlot(entity, instance, ISubAbilityModeHolder.getSubSlots(instance).indexOf(slot) + this.getSubModeOffset(instance))
            && !sub.onTakenDamage(entity, source, amount)) {
            return false;
         }
      }

      return true;
   }

   public boolean onDeath(ManasSkillInstance instance, LivingEntity entity, DamageSource source) {
      for (AbilitySlot slot : ISubAbilityModeHolder.getSubSlots(instance)) {
         ManasSkillInstance sub = (ManasSkillInstance)instance.getSubInstances().get(slot.getSkill());
         if (sub != null && !sub.onDeath(entity, source)) {
            return false;
         }
      }

      return true;
   }

   public void onRespawn(ManasSkillInstance instance, ServerPlayer entity, boolean conqueredEnd) {
      for (AbilitySlot slot : ISubAbilityModeHolder.getSubSlots(instance)) {
         ManasSkillInstance sub = (ManasSkillInstance)instance.getSubInstances().get(slot.getSkill());
         if (sub != null) {
            sub.onRespawn(entity, conqueredEnd);
         }
      }
   }

   public void onProjectileHit(
      ManasSkillInstance instance,
      LivingEntity entity,
      EntityHitResult hitResult,
      Projectile projectile,
      Changeable<ProjectileDeflection> deflection,
      Changeable<ProjectileHitResult> result
   ) {
      for (AbilitySlot slot : ISubAbilityModeHolder.getSubSlots(instance)) {
         ManasSkillInstance sub = (ManasSkillInstance)instance.getSubInstances().get(slot.getSkill());
         if (sub != null && this.isInSlot(entity, instance, ISubAbilityModeHolder.getSubSlots(instance).indexOf(slot) + this.getSubModeOffset(instance))) {
            sub.onProjectileHit(entity, hitResult, projectile, deflection, result);
         }
      }
   }

   private AbilitySlot getSlot(ManasSkillInstance instance, int mode) {
      if (mode == 0) {
         return null;
      }

      List<AbilitySlot> slots = ISubAbilityModeHolder.getSubSlots(instance);
      int index = mode - this.getSubModeOffset(instance);
      return index >= 0 && index < slots.size() ? slots.get(index) : null;
   }

   private ManasSkillInstance getSubInstance(ManasSkillInstance instance, int mode) {
      AbilitySlot slot = this.getSlot(instance, mode);
      if (slot != null && slot.getSkill() != null) {
         Map<ManasSkill, ManasSkillInstance> subInstances = instance.getSubInstances();
         return subInstances.get(slot.getSkill());
      } else {
         return null;
      }
   }
}
