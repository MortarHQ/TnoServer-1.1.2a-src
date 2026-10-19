package com.mortal_cultivation.ability.skill.base;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public abstract class LegacySkill extends Skill {
   private static final String LEGACY_MODE_TAG = "LegacyMode";

   protected LegacySkill(SkillType type) {
      super(type);
   }

   protected int getLegacyMode(ManasSkillInstance instance) {
      return Math.max(1, instance.getOrCreateTag().getInt("LegacyMode"));
   }

   private void rememberMode(ManasSkillInstance instance, int mode) {
      instance.getOrCreateTag().putInt("LegacyMode", mode + 1);
   }

   public boolean meetEPRequirement(Player entity, double curEP) {
      return super.checkAcquiringRequirement(entity, curEP);
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return this.meetEPRequirement(entity, newEP);
   }

   public double learningCost() {
      return 1.0;
   }

   public double getLearningPointRequirement(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return super.getLearningPointRequirement(instance, entity, mode) * this.learningCost();
   }

   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return 0.0;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return this.magiculeCost(entity, instance);
   }

   public int modes() {
      return 1;
   }

   public int getModes(ManasSkillInstance instance) {
      return this.modes();
   }

   public int nextMode(LivingEntity entity, TensuraSkillInstance instance, boolean reverse) {
      return this.getLegacyMode(instance);
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      this.rememberMode(instance, mode);
      return instance instanceof TensuraSkillInstance tensuraSkillInstance ? this.nextMode(entity, tensuraSkillInstance, reverse) - 1 : mode;
   }

   public Component getModeName(int mode) {
      return Component.empty();
   }

   public Component getModeName(ManasSkillInstance instance, int mode) {
      this.rememberMode(instance, mode);
      Component modeName = this.getModeName(this.getLegacyMode(instance));
      return this.getModes(instance) > 1 && modeName != null && !modeName.getString().isEmpty() ? modeName : super.getModeName(instance, mode);
   }

   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity) {
      return false;
   }

   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
      this.rememberMode(instance, mode);
      return this.canIgnoreCoolDown(instance, entity);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity) {
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      this.rememberMode(instance, mode);
      this.onPressed(instance, entity);
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks) {
      return false;
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      this.rememberMode(instance, mode);
      return this.onHeld(instance, entity, heldTicks);
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks) {
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      this.rememberMode(instance, mode);
      this.onRelease(instance, entity, heldTicks);
   }

   public void onScroll(ManasSkillInstance instance, LivingEntity entity, double delta) {
   }

   public void onScroll(ManasSkillInstance instance, LivingEntity entity, double delta, int mode) {
      this.rememberMode(instance, mode);
      this.onScroll(instance, entity, delta);
   }

   public double getObtainingEpCost() {
      return 0.0;
   }
}
