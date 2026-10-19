package com.shin.tensura_kumodesu.ability.skill.unique;

import com.shin.tensura_kumodesu.config.skill.KumodesuUniqueSkillConfig;
import com.shin.tensura_kumodesu.registry.effects.KumodesuEffects;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class PerseveranceSkill extends Skill {
   public static final KumodesuUniqueSkillConfig.Perseverance CONFIG = ((KumodesuUniqueSkillConfig)ConfigRegistry.getConfig(KumodesuUniqueSkillConfig.class)).Perseverance;

   public PerseveranceSkill() {
      super(SkillType.UNIQUE);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "textures/skill/unique/example_unique.png");
   }

   public int getMaxMastery() {
      return 200;
   }

   public double getDefaultAcquiringMagiculeCost() {
      return CONFIG.mpAcquirement;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isMastered(entity);
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return !instance.isToggled() && !this.isInSlot(entity, instance) ? false : entity.getHealth() < entity.getMaxHealth() * CONFIG.hpMultiplier;
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity livingEntity, DamageSource source, Changeable<Float> amount) {
      if (livingEntity != null) {
         if (livingEntity.getHealth() >= 1.0F) {
            return false;
         }

         if (livingEntity.getHealth() <= 1.0F) {
            livingEntity.setHealth(1.0F);
         }
      }

      return false;
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      int level = instance.isMastered(entity) ? 1 : 0;
      entity.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(KumodesuEffects.PERSEVERANCE), 340, level, false, false, false));
      if (level == 0) {
         int time = tag.getInt("activatedTimes");
         if (time % BASE_CONFIG.Mastery.masteryActivateTime == 0) {
            instance.addMasteryPoint(entity);
         }

         tag.putInt("activatedTimes", time + 1);
      }
   }
}
