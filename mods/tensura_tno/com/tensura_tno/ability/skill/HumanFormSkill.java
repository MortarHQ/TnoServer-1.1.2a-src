package com.tensura_tno.ability.skill;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;

public class HumanFormSkill extends Skill {
   public static final ResourceLocation HUMAN_FORM_SCALE = ResourceLocation.fromNamespaceAndPath("tensura_tno", "human_form_scale");
   public static final ResourceLocation HUMAN_FORM_SPEED = ResourceLocation.fromNamespaceAndPath("tensura_tno", "human_form_speed");
   private static final double SPEED_COMPENSATION = 0.03;
   private static final ResourceLocation TENSURA_WIDTH_MULTIPLIER_ID = ResourceLocation.fromNamespaceAndPath("tensura", "width_multiplier");
   private static final ResourceLocation TENSURA_HEIGHT_MULTIPLIER_ID = ResourceLocation.fromNamespaceAndPath("tensura", "height_multiplier");
   private static final double TARGET_VALUE = 1.0;
   public static final double MAGICULE_COST = 1000.0;

   public HumanFormSkill() {
      super(SkillType.INTRINSIC);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensura_tno", "textures/skill/human_form.png");
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      IExistence existence = TensuraStorages.getExistenceFrom(entity);
      return existence.isTrueDemonLord() || existence.isTrueHero();
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return false;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return false;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return 1000.0;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            applyHumanForm(entity);
         }
      }
   }

   public static void applyHumanForm(LivingEntity entity) {
      boolean changed = lockToOne(entity, entity.getAttribute(Attributes.SCALE));
      changed |= lockToOne(entity, getAttribute(entity, TENSURA_WIDTH_MULTIPLIER_ID));
      changed |= lockToOne(entity, getAttribute(entity, TENSURA_HEIGHT_MULTIPLIER_ID));
      applySpeedCompensation(entity);
      if (changed) {
         entity.refreshDimensions();
      }
   }

   public static void removeHumanForm(LivingEntity entity) {
      boolean removed = removeOurModifier(entity.getAttribute(Attributes.SCALE));
      removed |= removeOurModifier(getAttribute(entity, TENSURA_WIDTH_MULTIPLIER_ID));
      removed |= removeOurModifier(getAttribute(entity, TENSURA_HEIGHT_MULTIPLIER_ID));
      removeSpeedCompensation(entity);
      if (removed) {
         entity.refreshDimensions();
      }
   }

   private static void applySpeedCompensation(LivingEntity entity) {
      AttributeInstance speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
      if (speed != null) {
         speed.removeModifier(HUMAN_FORM_SPEED);
         speed.addOrReplacePermanentModifier(new AttributeModifier(HUMAN_FORM_SPEED, 0.03, Operation.ADD_VALUE));
      }
   }

   private static void removeSpeedCompensation(LivingEntity entity) {
      AttributeInstance speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
      if (speed != null) {
         if (speed.getModifier(HUMAN_FORM_SPEED) != null) {
            speed.removeModifier(HUMAN_FORM_SPEED);
         }
      }
   }

   private static boolean lockToOne(LivingEntity entity, AttributeInstance instance) {
      if (instance == null) {
         return false;
      }

      instance.removeModifier(HUMAN_FORM_SCALE);
      double delta = 1.0 - instance.getValue();
      instance.addOrReplacePermanentModifier(new AttributeModifier(HUMAN_FORM_SCALE, delta, Operation.ADD_VALUE));
      return true;
   }

   private static boolean removeOurModifier(AttributeInstance instance) {
      if (instance == null) {
         return false;
      }

      if (instance.getModifier(HUMAN_FORM_SCALE) == null) {
         return false;
      }

      instance.removeModifier(HUMAN_FORM_SCALE);
      return true;
   }

   private static AttributeInstance getAttribute(LivingEntity entity, ResourceLocation id) {
      Attribute raw = (Attribute)BuiltInRegistries.ATTRIBUTE.get(id);
      if (raw == null) {
         return null;
      }

      Holder<Attribute> holder = BuiltInRegistries.ATTRIBUTE.wrapAsHolder(raw);
      return entity.getAttribute(holder);
   }
}
