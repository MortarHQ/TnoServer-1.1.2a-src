package com.shin.tensura_kumodesu.ability.skill.intrinsic;

import com.shin.tensura_kumodesu.config.skill.KumodesuIntrinsicSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.util.AttributeHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class DivineMagicReleaseSkill extends Skill {
   public static final KumodesuIntrinsicSkillConfig.DivineMagicRelease CONFIG = ((KumodesuIntrinsicSkillConfig)ConfigRegistry.getConfig(
         KumodesuIntrinsicSkillConfig.class
      ))
      .DivineMagicRelease;
   protected static final ResourceLocation DIVINE_MAGIC_RELEASE = ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "divine_magic_release");

   public DivineMagicReleaseSkill() {
      super(SkillType.INTRINSIC);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "textures/skill/intrinsic/divine_magic_release.png");
   }

   public int getMaxMastery() {
      return 2000;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return instance.getMastery() >= 0.0;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.multiplyChantSpeed(entity, CONFIG.chantSpeed);
      AttributeInstance learning = entity.getAttribute(TensuraAttributes.ABILITY_LEARNING_GAIN);
      if (learning != null) {
         learning.addOrReplacePermanentModifier(new AttributeModifier(DIVINE_MAGIC_RELEASE, CONFIG.learningPoint, Operation.ADD_VALUE));
      }

      AttributeInstance mastery = entity.getAttribute(TensuraAttributes.ABILITY_MASTERY_GAIN);
      if (mastery != null) {
         mastery.addOrReplacePermanentModifier(new AttributeModifier(DIVINE_MAGIC_RELEASE, CONFIG.masteryPoint, Operation.ADD_VALUE));
      }

      AttributeInstance attack = entity.getAttribute(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER);
      if (attack != null) {
         attack.addOrReplacePermanentModifier(new AttributeModifier(DIVINE_MAGIC_RELEASE, CONFIG.magiculeregen, Operation.ADD_VALUE));
      }

      AttributeInstance chant = entity.getAttribute(TensuraAttributes.CHANT_SPEED);
      if (chant != null) {
         chant.addOrReplacePermanentModifier(new AttributeModifier(DIVINE_MAGIC_RELEASE, CONFIG.chantSpeed, Operation.ADD_VALUE));
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.removeChantSpeed(entity, CONFIG.chantSpeed);
      AttributeInstance learning = entity.getAttribute(TensuraAttributes.ABILITY_LEARNING_GAIN);
      if (learning != null) {
         learning.removeModifier(DIVINE_MAGIC_RELEASE);
      }

      AttributeInstance mastery = entity.getAttribute(TensuraAttributes.ABILITY_MASTERY_GAIN);
      if (mastery != null) {
         mastery.removeModifier(DIVINE_MAGIC_RELEASE);
      }

      AttributeInstance attack = entity.getAttribute(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER);
      if (attack != null) {
         attack.removeModifier(DIVINE_MAGIC_RELEASE);
      }

      AttributeInstance chant = entity.getAttribute(TensuraAttributes.CHANT_SPEED);
      if (chant != null) {
         chant.removeModifier(DIVINE_MAGIC_RELEASE);
      }
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (instance.isToggled()) {
         this.gainMastery(instance, entity);
      }
   }

   private void gainMastery(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      int time = tag.getInt("activatedTimes");
      if (time % BASE_CONFIG.Mastery.masteryActivateTime == 0) {
         instance.addMasteryPoint(entity);
      }

      tag.putInt("activatedTimes", time + 1);
   }
}
