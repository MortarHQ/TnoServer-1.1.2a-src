package com.github.wal_bos.moreskills.ability.skill.unique;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.GodlyScienceSmitheryHandler;
import com.github.wal_bos.moreskills.menu.GodlyWorkMenu;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import com.github.wal_bos.moreskills.util.SkillObtainmentHelper;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.magic.Magic;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.data.TensuraSkillTags;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.util.AttributeHelper;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class GodlyScientistSkill extends Skill {
   public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "godly_scientist");
   public static final ResourceLocation UNIVERSE_THREAD = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "godly_scientist_universe_thread");
   public static final ResourceLocation OMNI_ANALYSIS_LEVEL = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "godly_scientist_analysis_level");
   public static final ResourceLocation OMNI_ANALYSIS_DISTANCE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "godly_scientist_analysis_distance");
   public static final ResourceLocation OMNI_PRESENCE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "godly_scientist_presence");
   public static final ResourceLocation OMNI_PRESENCE_RADIUS = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "godly_scientist_presence_radius");

   public GodlyScientistSkill() {
      super(SkillType.UNIQUE);
   }

   private static MoreSkillsConfigs.GodlyScientistConfig cfg() {
      return MoreSkillsConfigs.GRAND.godlyScientist;
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/unique/godly_scientist.png");
   }

   public double getDefaultAcquiringMagiculeCost() {
      return (Double)cfg().acquirementMagiculeCost.get();
   }

   public int getAcquirementMastery(LivingEntity entity) {
      return (Integer)cfg().acquirementMastery.get();
   }

   public int getMaxMastery() {
      return (Integer)cfg().maxMastery.get();
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      if (!SkillObtainmentHelper.canAcquire(
         entity, (Double)cfg().acquirementMagiculeCost.get(), (String)cfg().requiredSkillId.get(), (Boolean)cfg().requiredSkillMustBeMastered.get()
      )) {
         return false;
      } else if (newEP < (Double)cfg().requiredEp.get()) {
         return false;
      } else if (SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)ExtraSkills.BLACK_FLAME.get()).isEmpty()) {
         return false;
      } else if (GodlyScienceSmitheryHandler.getElementalEssenceCount(entity) < (Integer)cfg().elementalEssenceRequired.get()) {
         return false;
      } else {
         return GodlyScienceSmitheryHandler.getDragonEssenceCount(entity) < cfg().dragonEssenceRequired.get()
            ? false
            : entity.getMainHandItem().is(Items.NETHER_STAR) || entity.getOffhandItem().is(Items.NETHER_STAR);
      }
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return mode == 0 ? 1 : 0;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return mode == 1 ? "godly_scientist.divine_increment" : "godly_scientist.genesis";
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return mode == 1 ? (Double)cfg().divineIncrementCost.get() : (Double)cfg().genesisCost.get();
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         long gameTime = entity.level().getGameTime();
         if (gameTime % Math.max(1, (Integer)cfg().spellMasteryRefreshTicks.get()) == 0L) {
            ensurePassives(entity);
            masterAllMagic(entity);
         }

         if (instance.isToggled() && gameTime % Math.max(1, (Integer)cfg().masteryGainIntervalTicks.get()) == 0L) {
            instance.addMasteryPoint(entity);
         }
      }
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      if (!(instance.getMastery() < 0.0) && !instance.isTemporarySkill()) {
         ensurePassives(entity);
         masterAllMagic(entity);
      }
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      ensurePassives(entity);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      ensurePassives(entity);
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      removeOmniAnalysis(entity);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.ABILITY_MASTERY_GAIN, UNIVERSE_THREAD);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof ServerPlayer player) {
         if (mode == 1) {
            if (!instance.onCoolDown(mode) && !this.isOutOfEnergy(entity, instance, mode, 1.0F)) {
               GodlyWorkMenu.open(player, GodlyWorkMenu.Kind.DIVINE_INCREMENT);
               instance.addMasteryPoint(entity);
               instance.setCoolDown((Integer)cfg().divineIncrementCooldownTicks.get(), mode);
            }
         } else {
            GodlyWorkMenu.open(player, GodlyWorkMenu.Kind.GENESIS);
         }
      }
   }

   public static void applyOmniAnalysis(LivingEntity entity) {
      AttributeHelper.addPermanentAttribute(
         entity, TensuraAttributes.ANALYSIS_LEVEL, OMNI_ANALYSIS_LEVEL, ((Integer)cfg().analysisLevel.get()).intValue(), Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttribute(
         entity, TensuraAttributes.ANALYSIS_DISTANCE, OMNI_ANALYSIS_DISTANCE, ((Integer)cfg().analysisDistance.get()).intValue(), Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.PRESENCE_SENSE, OMNI_PRESENCE, 255.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(
         entity, TensuraAttributes.PRESENCE_SENSE_RADIUS, OMNI_PRESENCE_RADIUS, (Double)cfg().presenceSenseRadius.get(), Operation.ADD_VALUE
      );
   }

   public static void removeOmniAnalysis(LivingEntity entity) {
      AttributeHelper.removeAttribute(entity, TensuraAttributes.ANALYSIS_LEVEL, OMNI_ANALYSIS_LEVEL);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.ANALYSIS_DISTANCE, OMNI_ANALYSIS_DISTANCE);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.PRESENCE_SENSE, OMNI_PRESENCE);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.PRESENCE_SENSE_RADIUS, OMNI_PRESENCE_RADIUS);
   }

   public static void restorePassiveState(LivingEntity entity) {
      Skills skills = SkillAPI.getSkillsFrom(entity);
      if (skills.getSkill((ManasSkill)AllSkills.GODLY_SCIENTIST.get()).isEmpty()) {
         AttributeHelper.removeAttribute(entity, TensuraAttributes.ABILITY_MASTERY_GAIN, UNIVERSE_THREAD);
         removeOmniAnalysis(entity);
      } else {
         ensurePassives(entity);
         masterAllMagic(entity);
      }
   }

   private static void ensurePassives(LivingEntity entity) {
      AttributeHelper.addPermanentAttribute(
         entity,
         TensuraAttributes.ABILITY_MASTERY_GAIN,
         UNIVERSE_THREAD,
         Math.max(0.0, (Double)cfg().masteryMultiplier.get() - 1.0),
         Operation.ADD_MULTIPLIED_TOTAL
      );
      applyOmniAnalysis(entity);
      giveAntiSkill(entity);
   }

   private static void giveAntiSkill(LivingEntity entity) {
      Skills skills = SkillAPI.getSkillsFrom(entity);
      if (!skills.getSkill((ManasSkill)UniqueSkills.ANTI_SKILL.get()).isPresent()) {
         TensuraSkillInstance antiSkill = new TensuraSkillInstance((ManasSkill)UniqueSkills.ANTI_SKILL.get());
         antiSkill.setMastery(antiSkill.getMaxMastery());
         antiSkill.getOrCreateTag().putBoolean("NoMagiculeCost", true);
         SkillHelper.learnSkill(entity, antiSkill);
      }
   }

   private static void masterAllMagic(LivingEntity entity) {
      Skills skills = SkillAPI.getSkillsFrom(entity);
      boolean dirty = false;

      for (ManasSkillInstance learned : List.copyOf(skills.getLearnedSkills())) {
         if (masterMagicInstance(entity, learned)) {
            dirty = true;
         }
      }

      if (dirty) {
         skills.markDirty();
      }
   }

   public static boolean masterMagicInstance(LivingEntity entity, ManasSkillInstance instance) {
      if (isMagic(instance) && !instance.isTemporarySkill() && !(instance.getMastery() >= instance.getMaxMastery())) {
         boolean mastered = instance.isMastered(entity);
         instance.setMastery(instance.getMaxMastery());
         if (!mastered) {
            instance.onSkillMastered(entity);
         }

         instance.markDirty();
         SkillAPI.getSkillsFrom(entity).markDirty();
         return true;
      } else {
         return false;
      }
   }

   public static boolean isMagic(ManasSkillInstance instance) {
      return instance.getSkill() instanceof Magic
         || instance.is(TensuraSkillTags.MAGIC)
         || instance.is(TensuraSkillTags.ASPECTUAL_MAGIC)
         || instance.is(TensuraSkillTags.SPIRITUAL_MAGIC)
         || instance.is(TensuraSkillTags.SUMMONING_MAGIC)
         || instance.is(TensuraSkillTags.NECROMANCY_MAGIC);
   }
}
