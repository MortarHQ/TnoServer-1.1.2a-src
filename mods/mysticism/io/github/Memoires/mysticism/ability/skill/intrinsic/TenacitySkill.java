package io.github.Memoires.mysticism.ability.skill.intrinsic;

import io.github.Memoires.mysticism.config.ability.skill.IntrinsicMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class TenacitySkill extends Skill {
   public static final IntrinsicMysticismSkillConfig.Tenacity CONFIG = ((IntrinsicMysticismSkillConfig)ConfigRegistry.getConfig(
         IntrinsicMysticismSkillConfig.class
      ))
      .Tenacity;

   public TenacitySkill() {
      super(SkillType.INTRINSIC);
   }

   public int getMaxMastery() {
      return 500;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return !SkillUtils.isSkillToggled(living, (ManasSkill)ExtraSkills.ULTRASPEED_REGENERATION.get())
         && !SkillUtils.isSkillToggled(living, (ManasSkill)ExtraSkills.INFINITE_REGENERATION.get());
   }

   public boolean canBeSlotted(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return instance.getMastery() < 0.0;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isToggled();
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      int time = tag.getInt("activatedTimes");
      if (time % BASE_CONFIG.Mastery.masteryActivateTime == 0) {
         instance.addMasteryPoint(entity);
      }

      tag.putInt("activatedTimes", time + 1);
      this.onToggleOn(instance, entity);
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      MobEffectInstance regeneration = new MobEffectInstance(
         TensuraMobEffects.getReference(MysticismMobEffects.AURA_HEALING), 240, instance.isMastered(entity) ? 1 : 0, false, false, false
      );
      TensuraMobEffect.addEffect(entity, regeneration, entity, this);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      MobEffectInstance effectInstance = entity.getEffect(TensuraMobEffects.getReference(MysticismMobEffects.AURA_HEALING));
      if (effectInstance != null && effectInstance.getAmplifier() < 1) {
         entity.removeEffect(TensuraMobEffects.getReference(MysticismMobEffects.AURA_HEALING));
      }
   }
}
