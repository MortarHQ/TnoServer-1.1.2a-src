package com.github.dominickwd04.traddon.ability.skill.intrinsic;

import com.github.dominickwd04.traddon.config.ability.skill.TrAddonSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.resist.ResistSkill;
import io.github.manasmods.tensura.ability.skill.resist.ResistSkill.ResistType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.data.TensuraTags.DamageTypes;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class RefractiveShellSkill extends ResistSkill {
   public static final TrAddonSkillConfig.RefractiveShell CONFIG = ((TrAddonSkillConfig)ConfigRegistry.getConfig(TrAddonSkillConfig.class)).RefractiveShell;
   public static final ResourceLocation DISPERSION = ResourceLocation.fromNamespaceAndPath("traddon", "dispersion");

   public RefractiveShellSkill() {
      super(SkillType.INTRINSIC, ResistType.RESISTANCE);
      this.addHeldAttributeModifier(Attributes.MOVEMENT_SPEED, DISPERSION, CONFIG.refractiveShellSpeed - 1.0, Operation.ADD_MULTIPLIED_TOTAL);
   }

   public int getModes(ManasSkillInstance instance) {
      return 1;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return true;
   }

   public boolean canBeSlotted(ManasSkillInstance instance, int mode) {
      return true;
   }

   public boolean isDamageResisted(LivingEntity entity, DamageSource damageSource, ManasSkillInstance instance) {
      return TensuraDamageHelper.isTensuraMagic(damageSource);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (heldTicks % 20 == 0 && EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         return false;
      }

      if (heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0 && heldTicks > 0) {
         instance.addMasteryPoint(entity);
      }

      return true;
   }

   public void addHeldAttributeModifiers(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (mode == 0) {
         super.addHeldAttributeModifiers(instance, entity, mode);
      }
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity entity, DamageSource source, Changeable<Float> amount) {
      if (!hasDispersion(entity)) {
         return true;
      } else if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         return true;
      } else if (source.is(DamageTypes.BYPASS_DIMENSION_FAULT)) {
         return true;
      } else if (TensuraDamageHelper.isSeveranceDamage(source, entity, true)) {
         return true;
      } else if (!TensuraDamageHelper.isTensuraMagic(source)) {
         return true;
      } else {
         float damageCanceled = (Float)amount.get();
         double lackedMagicule = EnergyHelper.isOutOfMagiculeConsuming(entity, (int)damageCanceled * CONFIG.magiculeCostRefractiveShell);
         if (lackedMagicule > 0.0) {
            damageCanceled = (float)(damageCanceled - lackedMagicule / CONFIG.magiculeCostRefractiveShell);
            amount.set((Float)amount.get() - damageCanceled);
            return true;
         } else {
            return false;
         }
      }
   }

   public static boolean hasDispersion(LivingEntity entity) {
      AttributeInstance attributeInstance = entity.getAttribute(Attributes.MOVEMENT_SPEED);
      return attributeInstance == null ? false : attributeInstance.getModifier(DISPERSION) != null;
   }
}
