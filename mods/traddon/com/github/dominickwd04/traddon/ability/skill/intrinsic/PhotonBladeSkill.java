package com.github.dominickwd04.traddon.ability.skill.intrinsic;

import com.github.dominickwd04.traddon.config.ability.skill.TrAddonSkillConfig;
import com.github.dominickwd04.traddon.registry.effect.TrAddonMobEffects;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class PhotonBladeSkill extends Skill {
   public static final TrAddonSkillConfig.PhotonBlade CONFIG = ((TrAddonSkillConfig)ConfigRegistry.getConfig(TrAddonSkillConfig.class)).PhotonBlade;

   public PhotonBladeSkill() {
      super(SkillType.INTRINSIC);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensura", "textures/skill/intrinsic/magic_aura.png");
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return "default";
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public boolean canBeSlotted(ManasSkillInstance instance, int mode) {
      return instance.getMastery() < 0.0;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      TensuraMobEffect.addEffect(
         entity, new MobEffectInstance(TrAddonMobEffects.getReference(TrAddonMobEffects.PHOTON_BLADE), 2400, 0, false, false, false), entity, this
      );
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      if (entity.hasEffect(TrAddonMobEffects.getReference(TrAddonMobEffects.PHOTON_BLADE))) {
         entity.removeEffect(TrAddonMobEffects.getReference(TrAddonMobEffects.PHOTON_BLADE));
      }
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      instance.onToggleOff(entity);
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isToggled();
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      TensuraMobEffect.addEffect(
         entity, new MobEffectInstance(TrAddonMobEffects.getReference(TrAddonMobEffects.PHOTON_BLADE), 2400, 0, false, false, false), entity, this
      );
   }

   public boolean onTouchEntity(ManasSkillInstance instance, LivingEntity entity, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (instance.isToggled() && !instance.onCoolDown(0)) {
         instance.setCoolDown(1, 0);
         return true;
      } else {
         return true;
      }
   }
}
