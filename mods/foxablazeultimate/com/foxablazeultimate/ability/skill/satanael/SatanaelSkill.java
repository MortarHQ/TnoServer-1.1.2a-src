package com.foxablazeultimate.ability.skill.satanael;

import com.foxablazeultimate.config.SatanaelConfig;
import com.foxablazeultimate.effect.SatanaelRampageEffect;
import com.foxablazeultimate.registry.FoxAblazeUltimateMobEffects;
import com.foxablazeultimate.world.FoxAblazeGameRules;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.storage.unique.ITrulyUnique;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Locale;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;

public class SatanaelSkill extends Skill {
   private static final int MODE_WRATH_DOMINATION = 0;
   private static final int MODE_STAMPEDE = 1;
   private static final int MODE_COUNT = 2;
   private static final int EFFECT_MODE_BREEDER_REACTOR = 0;
   private static final String TAG_REACTOR_TICKS = "SatanaelReactorTicks";
   private static final String TAG_LAST_COMBAT_TICK = "SatanaelLastCombatTick";
   private static final String TAG_LAST_DECAY_TICK = "SatanaelLastDecayTick";
   private static final String TAG_WRATH_VALUE = "SatanaelWrathValue";
   private static final String TAG_LAST_WRATH_UPDATE = "SatanaelLastWrathUpdate";
   private static final String TAG_DOMINATED_COUNT = "SatanaelDominatedCount";
   private static final String TAG_DOMINATION_UNTIL = "SatanaelDominationUntil";
   private static final String TAG_STAMPEDE_START = "SatanaelStampedeStart";
   private static final String TAG_STAMPEDE_END = "SatanaelStampedeEnd";
   private static final String TAG_WEAKNESS_END = "SatanaelWeaknessEnd";
   private static final ResourceLocation STAMPEDE_ATTACK = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "satanael_stampede_attack");
   private static final ResourceLocation STAMPEDE_ARMOR = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "satanael_stampede_armor");
   private static final ResourceLocation STAMPEDE_SPEED = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "satanael_stampede_speed");
   private static final ResourceLocation STAMPEDE_MAGICULE_OUTPUT = ResourceLocation.fromNamespaceAndPath(
      "foxablazeultimate", "satanael_stampede_magicule_output"
   );
   private static final ResourceLocation STAMPEDE_WEAKNESS = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "satanael_stampede_weakness");

   public SatanaelSkill() {
      super(SkillType.ULTIMATE);
   }

   private static SatanaelConfig.SatanaelSettings config() {
      return SatanaelConfig.get().Satanael;
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "textures/skill/satanael.png");
   }

   public MutableComponent getColoredName() {
      MutableComponent name = super.getName();
      return name == null ? null : name.withStyle(ChatFormatting.DARK_RED);
   }

   public double getDefaultAcquiringMagiculeCost() {
      return 0.0;
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return false;
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      int safeMode = Math.floorMod(mode, 2);
      return reverse ? (safeMode == 0 ? 1 : safeMode - 1) : (safeMode == 1 ? 0 : safeMode + 1);
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (Math.floorMod(mode, 2)) {
         case 0 -> "satanael.wrath_domination";
         case 1 -> "satanael.stampede";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return Math.floorMod(mode, 2) == 0 ? Math.max(0.0, config().dominationMagiculeCost) : 0.0;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return instance.getMastery() >= 0.0;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getTag();
      return instance.isToggled()
         || tag != null
            && (
               tag.contains("SatanaelDominationUntil")
                  || tag.contains("SatanaelStampedeEnd")
                  || tag.contains("SatanaelWeaknessEnd")
                  || this.isInCombat(tag, entity)
            );
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         CompoundTag tag = instance.getOrCreateTag();
         long gameTime = entity.level().getGameTime();
         boolean stampedeActive = this.tickStampedeState(instance, entity, tag, gameTime);
         this.tickForcedWeakness(instance, entity, tag, gameTime);
         this.updateWrathAbsorption(instance, entity, tag, gameTime, stampedeActive);
         boolean combatActive = this.isInCombat(tag, entity);
         boolean reactorActive = stampedeActive || instance.isToggled() && combatActive;
         this.syncCombatStatus(entity, tag, combatActive);
         if (reactorActive) {
            this.tickBreederReactor(instance, entity, tag, stampedeActive, gameTime);
         } else {
            tag.remove("SatanaelReactorTicks");
         }

         if (instance.isToggled() && !stampedeActive) {
            this.decayRampageOutsideCombat(instance, entity);
         }
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (Math.floorMod(mode, 2) == 1 && !entity.level().isClientSide()) {
         this.activateStampede(instance, entity);
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      int safeMode = Math.floorMod(mode, 2);
      if (safeMode != 0) {
         return false;
      }

      if (entity.level().isClientSide()) {
         return true;
      }

      if (heldTicks % 20 == 0 && EnergyHelper.isOutOfEnergy(entity, instance, safeMode)) {
         return false;
      }

      int soundInterval = Math.max(1, config().dominationSoundIntervalTicks);
      if (heldTicks % soundInterval == 0) {
         this.playSkillSound(entity, SoundEvents.WARDEN_ANGRY, config().dominationSoundVolume, config().dominationSoundPitch);
      }

      int applyInterval = Math.max(1, config().dominationApplyIntervalTicks);
      if (heldTicks % applyInterval == 0) {
         this.applyWrathDomination(instance, entity, heldTicks);
      }

      this.awardHoldMastery(instance, entity, heldTicks);
      this.summonWrathParticles(entity, 5, 4);
      return true;
   }

   public boolean onTouchEntity(ManasSkillInstance instance, LivingEntity entity, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if ((instance.isToggled() || this.isStampedeActive(instance, entity)) && !target.isAlliedTo(entity) && !entity.isAlliedTo(target)) {
         this.recordCombat(instance, entity);
         if (this.hasRampage(entity)) {
            this.extendRampage(entity, Math.max(0, config().rampageCombatExtensionTicks));
         }
      }

      return true;
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity owner, DamageSource source, Changeable<Float> amount) {
      if (source.getEntity() != null && source.getEntity() != owner) {
         this.recordCombat(instance, owner);
         if ((instance.isToggled() || this.isStampedeActive(instance, owner)) && this.hasRampage(owner)) {
            this.extendRampage(owner, Math.max(0, config().rampageCombatExtensionTicks));
         }
      }

      return true;
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getTag();
      if (tag != null) {
         tag.remove("SatanaelReactorTicks");
         instance.markDirty();
      }

      entity.removeEffect(FoxAblazeUltimateMobEffects.SATANAEL_COMBAT);
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onLearnSkill(instance, entity);
      if (entity instanceof ServerPlayer player) {
         this.claimUniqueOwnership(player);
      }
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      entity.removeEffect(FoxAblazeUltimateMobEffects.SATANAEL_RAMPAGE);
      entity.removeEffect(FoxAblazeUltimateMobEffects.SATANAEL_WEAKNESS);
      entity.removeEffect(FoxAblazeUltimateMobEffects.SATANAEL_COMBAT);
      this.removeStampedeModifiers(entity);
      this.removeWeaknessModifier(entity);
      if (entity instanceof ServerPlayer player) {
         this.releaseUniqueOwnership(player);
      }
   }

   public void onRespawn(ManasSkillInstance instance, ServerPlayer owner, boolean conqueredEnd) {
      owner.removeEffect(FoxAblazeUltimateMobEffects.SATANAEL_WEAKNESS);
      owner.removeEffect(FoxAblazeUltimateMobEffects.SATANAEL_COMBAT);
      this.removeStampedeModifiers(owner);
      this.removeWeaknessModifier(owner);
      if (!conqueredEnd && instance.isToggled()) {
         instance.setToggled(false);
         instance.onToggleOff(owner);
      }
   }

   private void activateBreederReactor(ManasSkillInstance instance, LivingEntity entity, double outputMultiplier, boolean stampedeActive) {
      IExistence existence = TensuraStorages.getExistenceFrom(entity);
      double maximumMagicule = EnergyHelper.getMaxMagicule(entity);
      existence.setMagicule(existence.getMagicule() + maximumMagicule * Math.max(0.0, config().breederMagiculeFraction) * Math.max(0.0, outputMultiplier));
      existence.markDirty();
      double chance = existence.getMagicule() > maximumMagicule ? config().breederAboveMaxRampageChance : config().breederRampageChance;
      if (instance.isToggled() || stampedeActive) {
         double healthRatio = entity.getMaxHealth() <= 0.0F ? 1.0 : entity.getHealth() / entity.getMaxHealth();
         if (healthRatio < config().criticalHealthThreshold) {
            chance *= Math.max(0.0, config().criticalHealthChanceMultiplier);
         } else if (healthRatio < config().lowHealthThreshold) {
            chance *= Math.max(0.0, config().lowHealthChanceMultiplier);
         }
      }

      if (entity.getRandom().nextDouble() < Math.max(0.0, chance)) {
         this.increaseRampageLevel(instance, entity);
      }
   }

   private void increaseRampageLevel(ManasSkillInstance instance, LivingEntity entity) {
      MobEffectInstance current = entity.getEffect(FoxAblazeUltimateMobEffects.SATANAEL_RAMPAGE);
      int maximumLevel = Math.max(1, config().rampageMaximumLevel);
      int currentLevel = current == null ? 0 : current.getAmplifier() + 1;
      int newLevel = Math.min(maximumLevel, currentLevel + 1);
      int duration = Math.max(1, config().rampageDurationTicks);
      if (current != null) {
         duration = Math.max(duration, current.getDuration());
      }

      this.applyRampage(entity, duration, newLevel, entity, 0);
   }

   private void applyWrathDomination(ManasSkillInstance instance, LivingEntity entity, int heldTicks) {
      int levelInterval = Math.max(1, config().dominationLevelIntervalTicks);
      int desiredLevel = Math.min(Math.max(1, config().rampageMaximumLevel), 1 + heldTicks / levelInterval);
      int duration = Math.max(1, config().dominationDurationTicks);
      double radius = Math.max(0.0, config().dominationRadius);
      int affected = 0;

      for (LivingEntity target : entity.level()
         .getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius), targetx -> this.isValidDominationTarget(entity, targetx))) {
         if (!SkillUtils.isSkillToggled(target, (ManasSkill)ResistanceSkills.SPIRITUAL_ATTACK_NULLIFICATION.get())) {
            MobEffectInstance current = target.getEffect(FoxAblazeUltimateMobEffects.SATANAEL_RAMPAGE);
            int level = current == null ? desiredLevel : Math.max(desiredLevel, current.getAmplifier() + 1);
            this.applyRampage(target, duration, level, entity, 0);
            if (target.hasEffect(FoxAblazeUltimateMobEffects.SATANAEL_RAMPAGE)) {
               SatanaelRampageEffect.markDominated(target, entity, duration);
               affected++;
            }

            if (heldTicks % 20 == 0) {
               TensuraParticleHelper.addServerParticlesAroundSelf(target, ParticleTypes.ANGRY_VILLAGER, 1.5);
            }
         }
      }

      if (affected > 0) {
         CompoundTag tag = instance.getOrCreateTag();
         long gameTime = entity.level().getGameTime();
         boolean dominationWasActive = tag.contains("SatanaelDominationUntil") && gameTime < tag.getLong("SatanaelDominationUntil");
         if (!dominationWasActive) {
            tag.putLong("SatanaelLastWrathUpdate", gameTime);
         }

         tag.putInt("SatanaelDominatedCount", affected);
         tag.putLong("SatanaelDominationUntil", gameTime + duration);
         instance.markDirty();
         this.updateWrathAbsorption(instance, entity, tag, gameTime, false);
      }
   }

   private boolean isValidDominationTarget(LivingEntity owner, LivingEntity target) {
      if (target == owner || !target.isAlive() || target.isSpectator()) {
         return false;
      } else {
         return !target.isAlliedTo(owner) && !owner.isAlliedTo(target) ? !(target instanceof Player player && player.isCreative()) : false;
      }
   }

   private void applyRampage(LivingEntity target, int duration, int level, Entity source, int mode) {
      int safeLevel = Math.max(1, Math.min(Math.max(1, config().rampageMaximumLevel), level));
      MobEffectInstance effect = new MobEffectInstance(FoxAblazeUltimateMobEffects.SATANAEL_RAMPAGE, Math.max(1, duration), safeLevel - 1, true, false, true);
      TensuraMobEffect.addEffect(target, effect, source, this, mode);
   }

   private void tickBreederReactor(ManasSkillInstance instance, LivingEntity entity, CompoundTag tag, boolean stampedeActive, long gameTime) {
      int pulseTicks = stampedeActive ? Math.max(1, config().stampedeBreederPulseTicks) : Math.max(1, config().breederPulseTicks);
      int reactorTicks = tag.getInt("SatanaelReactorTicks") + 1;
      if (reactorTicks >= pulseTicks) {
         reactorTicks = 0;
         int dominatedCount = Math.max(0, tag.getInt("SatanaelDominatedCount"));
         double multiplier = 1.0 + dominatedCount * Math.max(0.0, config().breederEfficiencyPerDominatedEnemy);
         if (stampedeActive) {
            long start = tag.getLong("SatanaelStampedeStart");
            double elapsedSeconds = Math.max(0.0, (gameTime - start) / 20.0);
            multiplier *= Math.max(0.0, config().stampedeBreederBaseMultiplier) + elapsedSeconds * Math.max(0.0, config().stampedeBreederGrowthPerSecond);
         }

         this.activateBreederReactor(instance, entity, multiplier, stampedeActive);
         instance.markDirty();
      }

      tag.putInt("SatanaelReactorTicks", reactorTicks);
      if (stampedeActive) {
         this.summonWrathParticles(entity, Math.max(0, config().stampedeParticleCount), Math.max(1, config().stampedeParticleIntervalTicks));
      }
   }

   private void updateWrathAbsorption(ManasSkillInstance instance, LivingEntity entity, CompoundTag tag, long gameTime, boolean stampedeActive) {
      if (!tag.contains("SatanaelLastWrathUpdate")) {
         tag.putLong("SatanaelLastWrathUpdate", gameTime);
         instance.markDirty();
      } else {
         long elapsedTicks = gameTime - tag.getLong("SatanaelLastWrathUpdate");
         if (elapsedTicks >= 20L) {
            tag.putLong("SatanaelLastWrathUpdate", gameTime);
            if (stampedeActive) {
               this.setWrathValue(tag, 100.0);
               instance.markDirty();
            } else {
               int dominatedCount = this.countDominatedEnemies(entity);
               tag.putInt("SatanaelDominatedCount", dominatedCount);
               if (tag.contains("SatanaelDominationUntil") && gameTime >= tag.getLong("SatanaelDominationUntil")) {
                  tag.remove("SatanaelDominationUntil");
                  tag.putInt("SatanaelDominatedCount", 0);
                  dominatedCount = 0;
               }

               if (instance.onCoolDown(1)) {
                  this.setWrathValue(tag, 0.0);
                  instance.markDirty();
               } else {
                  double wrath = this.getWrathValue(tag);
                  double elapsedSeconds = elapsedTicks / 20.0;
                  if (dominatedCount > 0) {
                     wrath += dominatedCount * Math.max(0.0, config().wrathGainPerDominatedEnemyPerSecond) * elapsedSeconds;
                  } else if (!this.isInCombat(tag, entity)) {
                     wrath -= Math.max(0.0, config().wrathDecayPerSecond) * elapsedSeconds;
                  }

                  this.setWrathValue(tag, wrath);
                  instance.markDirty();
               }
            }
         }
      }
   }

   private int countDominatedEnemies(LivingEntity owner) {
      double radius = Math.max(0.0, config().dominationRadius);
      return owner.level()
         .getEntitiesOfClass(
            LivingEntity.class, owner.getBoundingBox().inflate(radius), target -> target.isAlive() && SatanaelRampageEffect.isDominatedBy(target, owner)
         )
         .size();
   }

   private void activateStampede(ManasSkillInstance instance, LivingEntity entity) {
      if (!instance.onCoolDown(1)) {
         CompoundTag tag = instance.getOrCreateTag();
         if (!tag.contains("SatanaelStampedeEnd")) {
            double wrath = this.getWrathValue(tag);
            double required = Math.max(0.0, Math.min(100.0, config().stampedeRequiredWrath));
            if (wrath + 1.0E-4 < required) {
               entity.sendSystemMessage(
                  Component.translatable("foxablazeultimate.skill.satanael.stampede.failed", new Object[]{String.format(Locale.ROOT, "%.1f", wrath)})
                     .withStyle(ChatFormatting.RED)
               );
            } else {
               long gameTime = entity.level().getGameTime();
               tag.putLong("SatanaelStampedeStart", gameTime);
               tag.putLong("SatanaelStampedeEnd", gameTime + Math.max(1, config().stampedeDurationTicks));
               tag.putLong("SatanaelLastWrathUpdate", gameTime);
               tag.putDouble("SatanaelWrathValue", 100.0);
               tag.putInt("SatanaelReactorTicks", 0);
               instance.markDirty();
               this.recordCombat(instance, entity);
               this.applyStampedeModifiers(entity, 1.0);
               this.playSkillSound(entity, SoundEvents.WARDEN_ROAR, config().stampedeSoundVolume, config().stampedeSoundPitch);
            }
         }
      }
   }

   private boolean tickStampedeState(ManasSkillInstance instance, LivingEntity entity, CompoundTag tag, long gameTime) {
      if (!tag.contains("SatanaelStampedeEnd")) {
         return false;
      }

      long end = tag.getLong("SatanaelStampedeEnd");
      if (gameTime >= end) {
         this.finishStampede(instance, entity, tag, gameTime);
         return false;
      }

      long start = tag.getLong("SatanaelStampedeStart");
      double elapsedSeconds = Math.max(1.0, (gameTime - start) / 20.0 + 1.0);
      if (entity.tickCount % 20 == 0) {
         this.applyStampedeModifiers(entity, elapsedSeconds);
      }

      return true;
   }

   private void finishStampede(ManasSkillInstance instance, LivingEntity entity, CompoundTag tag, long gameTime) {
      tag.remove("SatanaelStampedeStart");
      tag.remove("SatanaelStampedeEnd");
      tag.remove("SatanaelReactorTicks");
      tag.putDouble("SatanaelWrathValue", 0.0);
      long weaknessEnd = gameTime + Math.max(1, config().stampedeWeaknessDurationTicks);
      tag.putLong("SatanaelWeaknessEnd", weaknessEnd);
      instance.setCoolDown(Math.max(0, config().stampedeCooldownSeconds), 1);
      instance.markDirty();
      this.removeStampedeModifiers(entity);
      this.enforceWeakness(entity, weaknessEnd - gameTime);
   }

   private void tickForcedWeakness(ManasSkillInstance instance, LivingEntity entity, CompoundTag tag, long gameTime) {
      if (tag.contains("SatanaelWeaknessEnd")) {
         long remainingTicks = tag.getLong("SatanaelWeaknessEnd") - gameTime;
         if (remainingTicks <= 0L) {
            tag.remove("SatanaelWeaknessEnd");
            instance.markDirty();
            this.removeWeaknessModifier(entity);
            entity.removeEffect(FoxAblazeUltimateMobEffects.SATANAEL_WEAKNESS);
         } else {
            if (entity.tickCount % 10 == 0 || !entity.hasEffect(FoxAblazeUltimateMobEffects.SATANAEL_WEAKNESS)) {
               this.enforceWeakness(entity, remainingTicks);
            }
         }
      }
   }

   private void enforceWeakness(LivingEntity entity, long remainingTicks) {
      entity.addEffect(
         new MobEffectInstance(
            FoxAblazeUltimateMobEffects.SATANAEL_WEAKNESS,
            this.getEffectDisplayDuration(remainingTicks),
            Math.max(0, config().stampedeWeaknessAmplifier),
            false,
            true,
            true
         )
      );
      this.applyAttributeModifier(
         entity, Attributes.ATTACK_DAMAGE, STAMPEDE_WEAKNESS, Math.min(0.0, config().stampedeWeaknessAttackPenalty), Operation.ADD_VALUE
      );
   }

   private void applyStampedeModifiers(LivingEntity entity, double elapsedSeconds) {
      this.applyAttributeModifier(
         entity, Attributes.ATTACK_DAMAGE, STAMPEDE_ATTACK, Math.max(0.0, config().stampedeAttackPerSecond) * elapsedSeconds, Operation.ADD_VALUE
      );
      this.applyAttributeModifier(
         entity, Attributes.ARMOR, STAMPEDE_ARMOR, Math.max(0.0, config().stampedeArmorPerSecond) * elapsedSeconds, Operation.ADD_VALUE
      );
      this.applyAttributeModifier(
         entity, Attributes.MOVEMENT_SPEED, STAMPEDE_SPEED, Math.max(0.0, config().stampedeSpeedPerSecond) * elapsedSeconds, Operation.ADD_MULTIPLIED_TOTAL
      );
      this.applyAttributeModifier(
         entity,
         TensuraAttributes.MAGICULE_GAIN,
         STAMPEDE_MAGICULE_OUTPUT,
         Math.max(0.0, config().stampedeMagiculeOutputPerSecond) * elapsedSeconds,
         Operation.ADD_VALUE
      );
   }

   private void removeStampedeModifiers(LivingEntity entity) {
      this.removeAttributeModifier(entity, Attributes.ATTACK_DAMAGE, STAMPEDE_ATTACK);
      this.removeAttributeModifier(entity, Attributes.ARMOR, STAMPEDE_ARMOR);
      this.removeAttributeModifier(entity, Attributes.MOVEMENT_SPEED, STAMPEDE_SPEED);
      this.removeAttributeModifier(entity, TensuraAttributes.MAGICULE_GAIN, STAMPEDE_MAGICULE_OUTPUT);
   }

   private void removeWeaknessModifier(LivingEntity entity) {
      this.removeAttributeModifier(entity, Attributes.ATTACK_DAMAGE, STAMPEDE_WEAKNESS);
   }

   private void syncCombatStatus(LivingEntity entity, CompoundTag tag, boolean active) {
      if (!active) {
         entity.removeEffect(FoxAblazeUltimateMobEffects.SATANAEL_COMBAT);
      } else {
         int remainingTicks = this.getRemainingCombatTicks(tag, entity);
         entity.addEffect(
            new MobEffectInstance(FoxAblazeUltimateMobEffects.SATANAEL_COMBAT, this.getEffectDisplayDuration(remainingTicks), 0, true, false, true)
         );
      }
   }

   private int getEffectDisplayDuration(long remainingTicks) {
      return (int)Math.min(2147483647L, Math.max(1L, remainingTicks + 19L));
   }

   private void applyAttributeModifier(LivingEntity entity, Holder<Attribute> attribute, ResourceLocation id, double amount, Operation operation) {
      AttributeInstance instance = entity.getAttribute(attribute);
      if (instance != null) {
         instance.addOrReplacePermanentModifier(new AttributeModifier(id, amount, operation));
      }
   }

   private void removeAttributeModifier(LivingEntity entity, Holder<Attribute> attribute, ResourceLocation id) {
      AttributeInstance instance = entity.getAttribute(attribute);
      if (instance != null) {
         instance.removeModifier(id);
      }
   }

   private double getWrathValue(CompoundTag tag) {
      return Math.max(0.0, Math.min(100.0, tag.getDouble("SatanaelWrathValue")));
   }

   private void setWrathValue(CompoundTag tag, double value) {
      tag.putDouble("SatanaelWrathValue", Math.max(0.0, Math.min(100.0, value)));
   }

   private boolean isStampedeActive(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getTag();
      return tag != null && tag.contains("SatanaelStampedeEnd") && entity.level().getGameTime() < tag.getLong("SatanaelStampedeEnd");
   }

   private boolean isInCombat(CompoundTag tag, LivingEntity entity) {
      return this.getRemainingCombatTicks(tag, entity) > 0;
   }

   private int getRemainingCombatTicks(CompoundTag tag, LivingEntity entity) {
      if (!tag.contains("SatanaelLastCombatTick")) {
         return 0;
      }

      int lastCombatTick = tag.getInt("SatanaelLastCombatTick");
      int elapsedTicks = entity.tickCount - lastCombatTick;
      int timeoutTicks = Math.max(0, config().combatTimeoutTicks);
      return elapsedTicks >= 0 && elapsedTicks < timeoutTicks ? timeoutTicks - elapsedTicks : 0;
   }

   private void extendRampage(LivingEntity entity, int extensionTicks) {
      MobEffectInstance current = entity.getEffect(FoxAblazeUltimateMobEffects.SATANAEL_RAMPAGE);
      if (current != null && extensionTicks > 0) {
         int maximum = Math.max(current.getDuration(), config().rampageMaximumDurationTicks);
         int duration = Math.min(maximum, current.getDuration() + extensionTicks);
         this.applyRampage(entity, duration, current.getAmplifier() + 1, entity, 0);
      }
   }

   private void decayRampageOutsideCombat(ManasSkillInstance instance, LivingEntity entity) {
      MobEffectInstance current = entity.getEffect(FoxAblazeUltimateMobEffects.SATANAEL_RAMPAGE);
      if (current != null) {
         CompoundTag tag = instance.getOrCreateTag();
         int currentTick = entity.tickCount;
         int lastCombatTick = tag.contains("SatanaelLastCombatTick") ? tag.getInt("SatanaelLastCombatTick") : currentTick;
         if (!tag.contains("SatanaelLastCombatTick")) {
            tag.putInt("SatanaelLastCombatTick", currentTick);
            instance.markDirty();
         }

         if (currentTick - lastCombatTick >= Math.max(0, config().combatTimeoutTicks)) {
            int lastDecayTick = tag.contains("SatanaelLastDecayTick") ? tag.getInt("SatanaelLastDecayTick") : lastCombatTick;
            if (currentTick - lastDecayTick >= Math.max(1, config().rampageDecayIntervalTicks)) {
               tag.putInt("SatanaelLastDecayTick", currentTick);
               instance.markDirty();
               int level = current.getAmplifier() + 1;
               entity.removeEffect(FoxAblazeUltimateMobEffects.SATANAEL_RAMPAGE);
               if (level > 1) {
                  this.applyRampage(entity, current.getDuration(), level - 1, entity, 0);
               }
            }
         }
      }
   }

   private void recordCombat(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.putInt("SatanaelLastCombatTick", entity.tickCount);
      tag.putInt("SatanaelLastDecayTick", entity.tickCount);
      instance.markDirty();
   }

   private void awardHoldMastery(ManasSkillInstance instance, LivingEntity entity, int heldTicks) {
      int interval = Math.max(1, config().masteryPointIntervalTicks);
      if (heldTicks > 0 && heldTicks % interval == 0 && !instance.isMastered(entity)) {
         instance.addMasteryPoint(entity);
      }
   }

   private void summonWrathParticles(LivingEntity entity, int count, int intervalTicks) {
      if (count > 0 && entity.tickCount % Math.max(1, intervalTicks) == 0) {
         double size = entity.getAttributeValue(Attributes.SCALE) * 4.0;
         TensuraParticleHelper.addServerAuraParticles(entity, TensuraParticleUtils.getRedAura(0.65F, (float)size, -0.3F), count, 0.02);
      }
   }

   private void playSkillSound(LivingEntity entity, SoundEvent sound, double volume, double pitch) {
      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), sound, SoundSource.PLAYERS, (float)Math.max(0.0, volume), (float)Math.max(0.01, pitch));
   }

   private boolean hasRampage(LivingEntity entity) {
      return entity.hasEffect(FoxAblazeUltimateMobEffects.SATANAEL_RAMPAGE);
   }

   private void claimUniqueOwnership(ServerPlayer player) {
      if (this.isTrulyUniqueEnabled(player)) {
         ResourceLocation skillId = this.getRegistryName();
         ServerLevel overworld = this.overworldOf(player);
         if (skillId != null && overworld != null) {
            ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
            UUID owner = unique.getOwner(skillId);
            if (owner == null || owner.equals(player.getUUID())) {
               unique.addSkill(skillId, player.getUUID());
               unique.markDirty();
            }
         }
      }
   }

   private void releaseUniqueOwnership(ServerPlayer player) {
      ResourceLocation skillId = this.getRegistryName();
      ServerLevel overworld = this.overworldOf(player);
      if (skillId != null && overworld != null) {
         ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
         if (player.getUUID().equals(unique.getOwner(skillId))) {
            unique.removeSkill(skillId);
            unique.markDirty();
         }
      }
   }

   private boolean isTrulyUniqueEnabled(ServerPlayer player) {
      return player.level().getGameRules().getBoolean(FoxAblazeGameRules.SATANAEL_TRULY_UNIQUE);
   }

   private ServerLevel overworldOf(ServerPlayer player) {
      MinecraftServer server = player.getServer();
      return server == null ? null : server.overworld();
   }
}
