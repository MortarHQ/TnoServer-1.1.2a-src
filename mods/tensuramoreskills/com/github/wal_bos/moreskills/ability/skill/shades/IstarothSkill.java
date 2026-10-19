package com.github.wal_bos.moreskills.ability.skill.shades;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.UniqueOwnerSkillHandler;
import com.github.wal_bos.moreskills.handler.Istaroth.IstarothTimeHandler;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class IstarothSkill extends AnimatedShadeSkill {
   private static final MoreSkillsConfigs.IstarothConfig CONFIG = MoreSkillsConfigs.GRAND.istaroth;
   public static final ResourceLocation CHRONO_DIVINITY = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "istaroth_chrono_divinity");
   public static final ResourceLocation PREDICTION = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "istaroth_prediction");
   public static final ResourceLocation OUTSIDE_STREAM = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "istaroth_outside_stream");
   public static final int MODE_CHRONICLE = 0;
   public static final int MODE_TIME_SKIP = 1;
   public static final int MODE_DURATION = 2;
   public static final int MODE_STASIS = 3;
   public static final int MODE_REWIND = 4;
   public static final int MODE_CAUSALITY = 5;
   public static final int MODE_LOOP = 6;
   public static final int MODE_BANISHMENT = 7;
   public static final int MODE_WORLD_OF_STILL_SECONDS = 8;
   public static final int MODE_LAST_SECOND = 9;
   private static final ResourceLocation[] ICON_FRAMES = new ResourceLocation[]{
      ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/ultimate/istaroth_1.png")
   };

   public IstarothSkill() {
      super(SkillType.ULTIMATE, ICON_FRAMES, 90L);
   }

   public int getMaxMastery() {
      return (Integer)CONFIG.maxMastery.get();
   }

   public int getModes(ManasSkillInstance instance) {
      return 10;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode <= 0 ? 9 : mode - 1;
      } else {
         return mode >= 9 ? 0 : mode + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "istaroth.chronicle";
         case 1 -> "istaroth.time_skip";
         case 2 -> "istaroth.duration";
         case 3 -> "istaroth.stasis";
         case 4 -> "istaroth.rewind";
         case 5 -> "istaroth.causality";
         case 6 -> "istaroth.loop";
         case 7 -> "istaroth.temporal_banishment";
         case 8 -> "istaroth.world_of_still_seconds";
         case 9 -> "istaroth.last_second_creation";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      boolean mastered = instance.isMastered(entity);

      return switch (mode) {
         case 0 -> CONFIG.chronicleCost.get();
         case 1 -> mastered ? (Double)CONFIG.timeSkipCostMastered.get() : (Double)CONFIG.timeSkipCost.get();
         case 2 -> mastered ? (Double)CONFIG.durationCostMastered.get() : (Double)CONFIG.durationCost.get();
         case 3 -> mastered ? (Double)CONFIG.stasisCostMastered.get() : (Double)CONFIG.stasisCost.get();
         case 4 -> mastered ? (Double)CONFIG.rewindCostMastered.get() : (Double)CONFIG.rewindCost.get();
         case 5 -> mastered ? (Double)CONFIG.causalityCostMastered.get() : (Double)CONFIG.causalityCost.get();
         case 6 -> mastered ? (Double)CONFIG.loopCostMastered.get() : (Double)CONFIG.loopCost.get();
         case 7 -> mastered ? (Double)CONFIG.banishmentCostMastered.get() : (Double)CONFIG.banishmentCost.get();
         case 8 -> mastered ? (Double)CONFIG.worldOfStillSecondsCostMastered.get() : (Double)CONFIG.worldOfStillSecondsCost.get();
         case 9 -> mastered ? (Double)CONFIG.lastSecondCostMastered.get() : (Double)CONFIG.lastSecondCost.get();
         default -> 0.0;
      };
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isToggled() || IstarothTimeHandler.hasActiveTemporalState(entity);
   }

   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return mode == 8 && IstarothTimeHandler.isHoldingWorldStop(entity);
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onLearnSkill(instance, entity);
      this.applyChronoDivinity(entity, instance.isMastered(entity));
      UniqueOwnerSkillHandler.learnExclusiveSkill(instance, entity);
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      UniqueOwnerSkillHandler.releaseSkill(instance, entity);
      this.removeChronoDivinity(entity);
      IstarothTimeHandler.clearOwner(entity);
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      this.applyChronoDivinity(entity, instance.isMastered(entity));
      entity.sendSystemMessage(Component.literal("Istaroth isolates your timeline.").withStyle(ChatFormatting.GRAY));
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      IstarothTimeHandler.clearOwner(entity);
      entity.sendSystemMessage(Component.literal("Your timeline rejoins the world.").withStyle(ChatFormatting.DARK_GRAY));
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      this.applyChronoDivinity(entity, instance.isMastered(entity));
      IstarothTimeHandler.tickOwner(this, instance, entity);
      CompoundTag tag = instance.getOrCreateTag();
      int time = tag.getInt("IstarothMasteryTicks");
      int interval = (Integer)CONFIG.masteryTickInterval.get();
      if (time % interval == 0) {
         instance.addMasteryPoint(entity);
      }

      tag.putInt("IstarothMasteryTicks", time + 1);
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity entity, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (source.getEntity() == entity) {
         IstarothTimeHandler.addTemporalDebt(target, (Float)amount.get() * (instance.isMastered(entity) ? 0.8F : 0.55F), entity.getUUID());
         IstarothTimeHandler.onIstarothHitTarget(entity, target, (Float)amount.get());
      }

      return true;
   }

   public boolean onBeingDamaged(ManasSkillInstance instance, LivingEntity entity, DamageSource source, Changeable<Float> amount) {
      if (!instance.isToggled()) {
         return true;
      } else {
         IstarothTimeHandler.onOwnerThreatened(entity, source, amount);
         if (IstarothTimeHandler.tryStillnessReduction(entity, amount)) {
            return true;
         } else if (instance.isMastered(entity) && IstarothTimeHandler.shouldChronologyDeny(entity, source, (Float)amount.get())) {
            amount.set(0.0F);
            return false;
         } else {
            return true;
         }
      }
   }

   public boolean onEffectAdded(ManasSkillInstance instance, LivingEntity entity, Entity source, Changeable<MobEffectInstance> effect) {
      return !instance.isToggled() ? true : !IstarothTimeHandler.shouldRejectEffect(entity, (MobEffectInstance)effect.get());
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (mode == 9 && !instance.isMastered(entity)) {
         entity.sendSystemMessage(Component.literal("The Last Second of Creation requires mastery.").withStyle(ChatFormatting.RED));
      } else if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         boolean shift = entity.isShiftKeyDown();
         switch (mode) {
            case 0:
               if (shift) {
                  IstarothTimeHandler.manualAnchor(instance, entity);
                  instance.setCoolDown((Integer)CONFIG.chronicleAnchorCooldownTicks.get(), mode);
               } else {
                  IstarothTimeHandler.chronicle(instance, entity);
                  instance.setCoolDown((Integer)CONFIG.chronicleCooldownTicks.get(), mode);
               }
               break;
            case 1:
               if (shift) {
                  IstarothTimeHandler.momentumTheft(this, instance, entity);
                  instance.setCoolDown((Integer)CONFIG.momentumTheftCooldownTicks.get(), mode);
               } else {
                  IstarothTimeHandler.timeSkip(this, instance, entity);
                  instance.setCoolDown((Integer)CONFIG.timeSkipCooldownTicks.get(), mode);
               }
               break;
            case 2:
               if (shift) {
                  IstarothTimeHandler.eternalMoment(instance, entity);
                  instance.setCoolDown((Integer)CONFIG.eternalMomentCooldownTicks.get(), mode);
               } else {
                  IstarothTimeHandler.borrowedSeconds(this, instance, entity);
                  instance.setCoolDown((Integer)CONFIG.borrowedSecondsCooldownTicks.get(), mode);
               }
               break;
            case 3:
               if (shift) {
                  IstarothTimeHandler.objectPermanence(this, instance, entity);
                  instance.setCoolDown((Integer)CONFIG.objectPermanenceCooldownTicks.get(), mode);
               } else {
                  IstarothTimeHandler.stillnessOfTheKing(instance, entity);
                  instance.setCoolDown((Integer)CONFIG.stasisCooldownTicks.get(), mode);
               }
               break;
            case 4:
               if (shift) {
                  IstarothTimeHandler.rewindTarget(instance, entity);
                  instance.setCoolDown((Integer)CONFIG.rewindTargetCooldownTicks.get(), mode);
               } else {
                  IstarothTimeHandler.rewindSelf(instance, entity);
                  instance.setCoolDown((Integer)CONFIG.rewindSelfCooldownTicks.get(), mode);
               }
               break;
            case 5:
               if (shift) {
                  IstarothTimeHandler.effectBeforeCause(this, instance, entity);
                  instance.setCoolDown((Integer)CONFIG.effectBeforeCauseCooldownTicks.get(), mode);
               } else {
                  IstarothTimeHandler.delayedVerdict(this, instance, entity);
                  instance.setCoolDown((Integer)CONFIG.delayedVerdictCooldownTicks.get(), mode);
               }
               break;
            case 6:
               if (shift) {
                  IstarothTimeHandler.condemnedRepeat(this, instance, entity);
                  instance.setCoolDown((Integer)CONFIG.condemnedRepeatCooldownTicks.get(), mode);
               } else {
                  IstarothTimeHandler.twentySecondLoop(this, instance, entity);
                  instance.setCoolDown((Integer)CONFIG.loopCooldownTicks.get(), mode);
               }
               break;
            case 7:
               if (shift) {
                  IstarothTimeHandler.pastBurial(this, instance, entity);
                  instance.setCoolDown((Integer)CONFIG.pastBurialCooldownTicks.get(), mode);
               } else {
                  IstarothTimeHandler.futureExile(this, instance, entity);
                  instance.setCoolDown((Integer)CONFIG.futureExileCooldownTicks.get(), mode);
               }
               break;
            case 8:
               IstarothTimeHandler.clockbreakRelease(this, instance, entity);
               instance.setCoolDown(
                  instance.isMastered(entity)
                     ? (Integer)CONFIG.worldStopReleaseCooldownTicksMastered.get()
                     : (Integer)CONFIG.worldStopReleaseCooldownTicks.get(),
                  mode
               );
               break;
            case 9:
               IstarothTimeHandler.lastSecondOfCreation(this, instance, entity);
               instance.setCoolDown((Integer)CONFIG.lastSecondCooldownTicks.get(), mode);
         }

         instance.addMasteryPoint(entity);
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (mode != 8) {
         return false;
      }

      if (heldTicks % 20 == 0 && EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         return false;
      }

      IstarothTimeHandler.worldOfStillSeconds(this, instance, entity, heldTicks);
      return heldTicks < instance.isMastered(entity) ? (Integer)CONFIG.worldStopMaxHeldTicksMastered.get() : (Integer)CONFIG.worldStopMaxHeldTicks.get();
   }

   private void applyChronoDivinity(LivingEntity entity, boolean mastered) {
      AttributeHelper.addPermanentAttributeIfHigher(
         entity,
         Attributes.MOVEMENT_SPEED,
         CHRONO_DIVINITY,
         mastered ? (Double)CONFIG.chronoMoveSpeedMastered.get() : (Double)CONFIG.chronoMoveSpeed.get(),
         Operation.ADD_MULTIPLIED_TOTAL
      );
      AttributeHelper.addPermanentAttributeIfHigher(
         entity,
         Attributes.ATTACK_SPEED,
         CHRONO_DIVINITY,
         mastered ? (Double)CONFIG.chronoAttackSpeedMastered.get() : (Double)CONFIG.chronoAttackSpeed.get(),
         Operation.ADD_MULTIPLIED_TOTAL
      );
      AttributeHelper.addPermanentAttributeIfHigher(
         entity,
         Attributes.FLYING_SPEED,
         CHRONO_DIVINITY,
         mastered ? (Double)CONFIG.chronoFlyingSpeedMastered.get() : (Double)CONFIG.chronoFlyingSpeed.get(),
         Operation.ADD_MULTIPLIED_TOTAL
      );
      AttributeHelper.addPermanentAttributeIfHigher(
         entity,
         TensuraAttributes.CHANT_SPEED,
         CHRONO_DIVINITY,
         mastered ? (Double)CONFIG.chronoChantSpeedMastered.get() : (Double)CONFIG.chronoChantSpeed.get(),
         Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttributeIfHigher(
         entity,
         TensuraAttributes.PRESENCE_SENSE,
         CHRONO_DIVINITY,
         mastered ? (Double)CONFIG.chronoPresenceSenseMastered.get() : (Double)CONFIG.chronoPresenceSense.get(),
         Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttributeIfHigher(
         entity,
         TensuraAttributes.DODGE_INVULNERABILITY,
         CHRONO_DIVINITY,
         mastered ? (Double)CONFIG.chronoDodgeInvulnerabilityMastered.get() : (Double)CONFIG.chronoDodgeInvulnerability.get(),
         Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttributeIfHigher(
         entity,
         TensuraAttributes.RESISTANCE_DEGRADATION,
         CHRONO_DIVINITY,
         mastered ? (Double)CONFIG.chronoResistanceDegradationMastered.get() : (Double)CONFIG.chronoResistanceDegradation.get(),
         Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttributeIfHigher(
         entity,
         TensuraAttributes.AUTO_MELEE_DODGE_CHANCE,
         PREDICTION,
         mastered ? (Double)CONFIG.predictionMeleeDodgeMastered.get() : (Double)CONFIG.predictionMeleeDodge.get(),
         Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttributeIfHigher(
         entity,
         TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE,
         PREDICTION,
         mastered ? (Double)CONFIG.predictionProjectileDodgeMastered.get() : (Double)CONFIG.predictionProjectileDodge.get(),
         Operation.ADD_VALUE
      );
      if (mastered) {
         AttributeHelper.addPermanentAttributeIfHigher(
            entity, TensuraAttributes.LAW_DEGRADATION, OUTSIDE_STREAM, (Double)CONFIG.outsideStreamLawDegradation.get(), Operation.ADD_VALUE
         );
         AttributeHelper.addPermanentAttributeIfHigher(
            entity, TensuraAttributes.SPACE_RESIST_DEGRADATION, OUTSIDE_STREAM, (Double)CONFIG.outsideStreamSpaceDegradation.get(), Operation.ADD_VALUE
         );
      }
   }

   private void removeChronoDivinity(LivingEntity entity) {
      AttributeHelper.removeAttribute(entity, Attributes.MOVEMENT_SPEED, CHRONO_DIVINITY);
      AttributeHelper.removeAttribute(entity, Attributes.ATTACK_SPEED, CHRONO_DIVINITY);
      AttributeHelper.removeAttribute(entity, Attributes.FLYING_SPEED, CHRONO_DIVINITY);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.CHANT_SPEED, CHRONO_DIVINITY);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.PRESENCE_SENSE, CHRONO_DIVINITY);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.DODGE_INVULNERABILITY, CHRONO_DIVINITY);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.RESISTANCE_DEGRADATION, CHRONO_DIVINITY);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.AUTO_MELEE_DODGE_CHANCE, PREDICTION);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE, PREDICTION);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.LAW_DEGRADATION, OUTSIDE_STREAM);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.SPACE_RESIST_DEGRADATION, OUTSIDE_STREAM);
   }
}
