package com.github.wal_bos.moreskills.ability.skill.shades;

import com.github.wal_bos.moreskills.handler.UniqueOwnerSkillHandler;
import com.github.wal_bos.moreskills.handler.asmoday.AsmodayActions;
import com.github.wal_bos.moreskills.handler.asmoday.AsmodayPassives;
import com.github.wal_bos.moreskills.handler.asmoday.AsmodayPrisonRealmManager;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class AsmodaySkill extends AnimatedShadeSkill {
   private static final ResourceLocation[] ICON_FRAMES = new ResourceLocation[]{
      ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/ultimate/asmoday_1.png")
   };

   public AsmodaySkill() {
      super(SkillType.ULTIMATE, ICON_FRAMES, 90L);
   }

   public int getModes(ManasSkillInstance instance) {
      return AsmodayActions.getModes(instance);
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return AsmodayActions.nextMode(entity, instance, mode, reverse);
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onLearnSkill(instance, entity);
      UniqueOwnerSkillHandler.learnExclusiveSkill(instance, entity);
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return AsmodayActions.getModeId(instance, mode, super.getModeId(instance, mode));
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return AsmodayActions.getMagiculeCost(entity, instance, mode);
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isToggled() || AsmodayPassives.hasActiveState(entity) || AsmodayPrisonRealmManager.hasActivePrison(entity);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      AsmodayActions.onPressed(instance, entity, keyNumber, mode);
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      return AsmodayActions.onHeld(instance, entity, heldTicks, mode);
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      AsmodayActions.onRelease(instance, entity, heldTicks, keyNumber, mode);
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      super.onTick(instance, entity);
      AsmodayPassives.onTick(instance, entity);
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOn(instance, entity);
      AsmodayPassives.onToggleOn(instance, entity);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOff(instance, entity);
      AsmodayPassives.onToggleOff(instance, entity);
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      AsmodayActions.cleanup(entity);
      AsmodayPassives.onToggleOff(instance, entity);
      AsmodayPrisonRealmManager.releaseOwnedBy(entity, true, "Phanes will be dissapointed in you.");
      UniqueOwnerSkillHandler.releaseSkill(instance, entity);
   }
}
