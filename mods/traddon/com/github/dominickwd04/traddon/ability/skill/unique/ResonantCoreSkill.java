package com.github.dominickwd04.traddon.ability.skill.unique;

import com.github.dominickwd04.traddon.config.ability.skill.TrAddonSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.resist.ResistSkill;
import io.github.manasmods.tensura.ability.skill.resist.ResistSkill.ResistType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.jetbrains.annotations.Nullable;

public class ResonantCoreSkill extends ResistSkill {
   public static final TrAddonSkillConfig.ResonantCore CONFIG = ((TrAddonSkillConfig)ConfigRegistry.getConfig(TrAddonSkillConfig.class)).ResonantCore;
   public static final ResourceLocation REFRACTION = ResourceLocation.fromNamespaceAndPath("traddon", "refraction");

   public ResonantCoreSkill() {
      super(SkillType.UNIQUE, ResistType.NULLIFICATION);
      this.addHeldAttributeModifier(Attributes.MOVEMENT_SPEED, REFRACTION, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensura", "textures/skill/unique/guardian.png");
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "resonant_core.refraction";
         case 1 -> "resonant_core.overload";
         default -> super.getModeId(instance, mode);
      };
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return 0;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public boolean canBeSlotted(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return true;
   }

   public boolean isDamageResisted(LivingEntity entity, DamageSource damageSource, ManasSkillInstance instance) {
      return TensuraDamageHelper.isPhysicalAttack(damageSource);
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (mode == 1 || instance.onCoolDown(mode)) {
         return false;
      }

      if (heldTicks > CONFIG.spectralRefractionDuration) {
         instance.setCoolDown(CONFIG.spectralRefractionCooldown, mode);
         return false;
      }

      if (heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0 && heldTicks > 0) {
         instance.addMasteryPoint(entity);
      }

      return true;
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity entity, DamageSource damageSource, Changeable<Float> amount) {
      if (hasRefraction(entity) && TensuraDamageHelper.isTensuraMagic(damageSource)) {
         float original = (Float)amount.get();
         float reduced = original / 7.0F;
         amount.set(reduced);
         if (damageSource.getEntity() instanceof LivingEntity attacker) {
            attacker.hurt(damageSource, reduced);
         }

         return true;
      } else {
         return super.onTakenDamage(instance, entity, damageSource, amount);
      }
   }

   public void addHeldAttributeModifiers(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (mode == 0) {
         super.addHeldAttributeModifiers(instance, entity, mode);
      }
   }

   public static boolean hasRefraction(LivingEntity entity) {
      AttributeInstance attributeInstance = entity.getAttribute(Attributes.MOVEMENT_SPEED);
      return attributeInstance == null ? false : attributeInstance.getModifier(REFRACTION) != null;
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity entity, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (this.isInSlot(entity, instance) && !instance.onCoolDown(1) && target != null && target != entity) {
         int duration = CONFIG.dataOverloadStunDuration;
         target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration, 2));
         target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, duration, 2));
         target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, duration, 0));
         instance.setCoolDown(CONFIG.dataOverloadCooldown, 1);
      }

      return true;
   }
}
