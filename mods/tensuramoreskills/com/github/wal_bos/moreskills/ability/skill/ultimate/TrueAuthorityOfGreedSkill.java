package com.github.wal_bos.moreskills.ability.skill.ultimate;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.TrueAuthorityOfGreedHeartChunks;
import com.github.wal_bos.moreskills.handler.UniqueOwnerSkillHandler;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import com.github.wal_bos.moreskills.util.SkillObtainmentHelper;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.unique.CookSkill;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.effect.IEffect;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class TrueAuthorityOfGreedSkill extends Skill {
   private static final int MODE_LIONHEART = 0;
   private static final int MODE_LITTLE_KING = 1;
   private static final ResourceLocation RESISTANCE_PIERCE = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "true_authority_of_greed_resistance_pierce"
   );
   private static final ResourceLocation LAW_PIERCE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "true_authority_of_greed_law_pierce");
   private static final ResourceLocation HP_PENALTY = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "true_authority_of_greed_hp_penalty");
   private static final ResourceLocation ACTIVE_ATTACK_DAMAGE = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "true_authority_of_greed_active_attack_damage"
   );
   private static final ResourceLocation ACTIVE_ENTITY_RANGE = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "true_authority_of_greed_active_entity_range"
   );
   private static final ResourceLocation ACTIVE_BLOCK_RANGE = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "true_authority_of_greed_active_block_range"
   );

   public TrueAuthorityOfGreedSkill() {
      super(SkillType.ULTIMATE);
   }

   private static MoreSkillsConfigs.TrueAuthorityOfGreedConfig cfg() {
      return MoreSkillsConfigs.GRAND.trueAuthorityOfGreed;
   }

   public double getDefaultAcquiringMagiculeCost() {
      return (Double)cfg().acquirementMagiculeCost.get();
   }

   public int getAcquirementMastery(LivingEntity entity) {
      return (Integer)cfg().acquirementMastery.get();
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return SkillObtainmentHelper.canAcquire(
         entity, (Double)cfg().acquirementMagiculeCost.get(), (String)cfg().requiredSkillId.get(), (Boolean)cfg().requiredSkillMustBeMastered.get()
      );
   }

   public int getMaxMastery() {
      return (Integer)cfg().maxMastery.get();
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/ultimate/authority_of_greed2.png");
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return instance.getMastery() >= 0.0;
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return mode == 0 ? 1 : 0;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "true_authority_of_greed.lionheart";
         case 1 -> "true_authority_of_greed.little_king";
         default -> super.getModeId(instance, mode);
      };
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      UniqueOwnerSkillHandler.learnExclusiveSkill(instance, entity);
      this.revokeOldAuthority(entity);
      this.applyDamageGreed(entity, instance.isMastered(entity));
   }

   public void onSkillMastered(ManasSkillInstance instance, LivingEntity entity) {
      this.applyDamageGreed(entity, true);
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         player.displayClientMessage(
            Component.translatable("tensuramoreskills.skill.true_authority_of_greed.prevention.on").withStyle(ChatFormatting.GREEN), true
         );
      }

      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         player.displayClientMessage(
            Component.translatable("tensuramoreskills.skill.true_authority_of_greed.prevention.off").withStyle(ChatFormatting.RED), true
         );
      }

      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_DEACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      return tag.contains("LittleKingHeart")
         || tag.getBoolean("LionheartActive")
         || tag.getInt("GreedPenaltyTime") > 0
         || entity.tickCount % (Integer)cfg().passiveRepairIntervalTicks.get() == 0;
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         this.applyDamageGreed(entity, instance.isMastered(entity));
         if (entity.tickCount % (Integer)cfg().passiveRepairIntervalTicks.get() == 0) {
            clearSeverance(entity);
            CookSkill.removeCookedHP(entity);
         }

         CompoundTag tag = instance.getOrCreateTag();
         this.tickPenalty(entity, tag);
         this.tickLittleKing(instance, entity, tag);
         this.tickLionheart(instance, entity, tag);
         if (!isLittleKingActive(instance) && !isLionheartActive(instance)) {
            this.removeLittleKingState(entity);
         } else {
            this.applyLittleKingState(entity, instance.isMastered(entity));
         }
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide() && mode == 1) {
         int range = instance.isMastered(entity) ? (Integer)cfg().heartRangeMastered.get() : (Integer)cfg().heartRange.get();
         LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, range, false);
         if (target != null && target != entity && this.isOwnedBy(entity, target)) {
            CompoundTag tag = instance.getOrCreateTag();
            this.releaseHeartChunk(entity, tag);
            tag.putUUID("LittleKingHeart", target.getUUID());
            tag.putString("LittleKingDimension", target.level().dimension().location().toString());
            tag.putString("LittleKingName", target.getDisplayName().getString());
            tag.put("LittleKingSnapshot", this.saveResourceState(entity));
            tag.putInt("LittleKingLastChunkTick", 0);
            tag.remove("LionheartSnapshot");
            tag.remove("LionheartActive");
            tag.remove("LionheartOverTimeSeconds");
            TrueAuthorityOfGreedHeartChunks.holdHeart(entity, target, tag);
            restoreResourceState(entity, tag.getCompound("LittleKingSnapshot"));
            this.applyLittleKingState(entity, instance.isMastered(entity));
            instance.addMasteryPoint(entity);
            entity.swing(InteractionHand.MAIN_HAND, true);
            entity.level()
               .playSound(
                  null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 0.8F
               );
            if (entity instanceof Player player) {
               player.displayClientMessage(
                  Component.translatable("tensuramoreskills.skill.true_authority_of_greed.little_king.bound").withStyle(ChatFormatting.GOLD), true
               );
            }

            this.showLittleKingIndicator(entity, target, range);
         } else {
            this.fail(entity);
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (entity.level().isClientSide() || mode != 0) {
         return false;
      }

      if (heldTicks < (Integer)cfg().holdArmTicks.get()) {
         return true;
      }

      if (instance.onCoolDown(mode)) {
         return false;
      }

      CompoundTag tag = instance.getOrCreateTag();
      int activeTicks = heldTicks - (Integer)cfg().holdArmTicks.get();
      if (!tag.getBoolean("LionheartActive")) {
         tag.putBoolean("LionheartActive", true);
         tag.put("LionheartSnapshot", this.saveResourceState(entity));
         tag.putDouble("LionheartOriginalMaxHealth", entity.getAttributeValue(Attributes.MAX_HEALTH));
         tag.putInt("LionheartLastPenaltySecond", 0);
         tag.putInt("LionheartOverTimeSeconds", 0);
         entity.level()
            .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 0.7F);
      }

      restoreResourceState(entity, tag.getCompound("LionheartSnapshot"));
      this.applyLittleKingState(entity, instance.isMastered(entity));
      if (instance.isToggled() && activeTicks >= (Integer)cfg().preventionFreeTicks.get()) {
         this.finishLionheart(instance, entity, activeTicks, true);
         return false;
      }

      if (activeTicks > (Integer)cfg().preventionFreeTicks.get()) {
         int currentSecond = activeTicks / 20;
         if (currentSecond > tag.getInt("LionheartLastPenaltySecond")) {
            tag.putInt("LionheartLastPenaltySecond", currentSecond);
            tag.putInt("LionheartOverTimeSeconds", tag.getInt("LionheartOverTimeSeconds") + 1);
            this.punishLionheart(instance, entity);
         }
      }

      if (activeTicks > 0 && activeTicks % (Integer)cfg().masteryHoldTick.get() == 0) {
         instance.addMasteryPoint(entity);
      }

      if (activeTicks % 20 == 0 && entity instanceof Player player) {
         int seconds = activeTicks / 20;
         player.displayClientMessage(
            Component.literal("Lionheart: " + seconds + "s")
               .withStyle(seconds >= cfg().preventionFreeTicks.get() / 20 ? ChatFormatting.RED : ChatFormatting.GOLD),
            true
         );
      }

      return true;
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (!entity.level().isClientSide() && mode == 0) {
         int activeTicks = Math.max(0, heldTicks - (Integer)cfg().holdArmTicks.get());
         this.finishLionheart(instance, entity, activeTicks, false);
      }
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity entity, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      this.applyDamageGreed(entity, instance.isMastered(entity));
      if (isLittleKingActive(instance) || isLionheartActive(instance)) {
         this.applyLittleKingState(entity, instance.isMastered(entity));
      }

      return true;
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity entity, DamageSource source, Changeable<Float> amount) {
      CookSkill.removeCookedHP(entity);
      boolean severanceWithEnchant = TensuraDamageHelper.isSeveranceDamage(source, entity, true);
      boolean severanceWithoutEnchant = TensuraDamageHelper.isSeveranceDamage(source, entity, false);
      if (severanceWithEnchant) {
         clearSeverance(entity);
      }

      if (severanceWithoutEnchant && source.getEntity() != entity) {
         clearSeverance(entity);
         amount.set(0.0F);
         return false;
      } else if (source.getMsgId().contains("cook") || source.getMsgId().contains("cooked")) {
         CookSkill.removeCookedHP(entity);
         amount.set(0.0F);
         return false;
      } else if ((isLittleKingActive(instance) || isLionheartActive(instance)) && source.getEntity() != entity && source.getDirectEntity() != entity) {
         this.restoreActiveResourceState(instance, entity);
         amount.set(0.0F);
         entity.invulnerableTime = 0;
         return false;
      } else {
         return true;
      }
   }

   public boolean onBeingDamaged(ManasSkillInstance instance, LivingEntity entity, DamageSource source, float amount) {
      this.restoreActiveResourceState(instance, entity);
      return true;
   }

   public void onPostBeingDamaged(ManasSkillInstance instance, LivingEntity entity, DamageSource source, float amount) {
      this.restoreActiveResourceState(instance, entity);
      clearSeverance(entity);
      if (source.getMsgId().contains("cook") || source.getMsgId().contains("cooked")) {
         CookSkill.removeCookedHP(entity);
      }
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      UniqueOwnerSkillHandler.releaseSkill(instance, entity);
      this.removeLittleKingState(entity);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.RESISTANCE_DEGRADATION, RESISTANCE_PIERCE);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.LAW_DEGRADATION, LAW_PIERCE);
      AttributeHelper.removeAttribute(entity, Attributes.MAX_HEALTH, HP_PENALTY);
      CompoundTag tag = instance.getOrCreateTag();
      this.releaseHeartChunk(entity, tag);
      tag.remove("LittleKingHeart");
      tag.remove("LittleKingDimension");
      tag.remove("LittleKingName");
      tag.remove("LittleKingSnapshot");
      tag.remove("LittleKingLastChunkTick");
      tag.remove("LionheartSnapshot");
      tag.remove("LionheartActive");
      tag.remove("LionheartLastPenaltySecond");
      tag.remove("LionheartOverTimeSeconds");
      tag.remove("LionheartOriginalMaxHealth");
      tag.remove("GreedPenaltyTime");
      tag.remove("GreedHpPenalty");
   }

   public void cleanupOnDeath(ManasSkillInstance instance, LivingEntity entity, DamageSource source) {
      CompoundTag tag = instance.getOrCreateTag();
      this.releaseHeartChunk(entity, tag);
      this.removeLittleKingState(entity);
      AttributeHelper.removeAttribute(entity, Attributes.MAX_HEALTH, HP_PENALTY);
      tag.remove("LittleKingHeart");
      tag.remove("LittleKingSnapshot");
      tag.remove("LionheartSnapshot");
      tag.remove("LionheartActive");
      tag.remove("LionheartOverTimeSeconds");
      tag.remove("LionheartOriginalMaxHealth");
      tag.remove("GreedPenaltyTime");
      tag.remove("GreedHpPenalty");
   }

   private void tickPenalty(LivingEntity entity, CompoundTag tag) {
      if (tag.getInt("GreedPenaltyTime") > 0) {
         int time = tag.getInt("GreedPenaltyTime") - 1;
         tag.putInt("GreedPenaltyTime", time);
         if (time <= 0 || !entity.isAlive()) {
            tag.remove("GreedPenaltyTime");
            tag.remove("GreedHpPenalty");
            AttributeHelper.removeAttribute(entity, Attributes.MAX_HEALTH, HP_PENALTY);
         }
      }
   }

   private void tickLittleKing(ManasSkillInstance instance, LivingEntity entity, CompoundTag tag) {
      if (tag.contains("LittleKingHeart") && tag.contains("LittleKingSnapshot")) {
         LivingEntity heart = this.findLiving(entity, tag.getUUID("LittleKingHeart"));
         int range = instance.isMastered(entity) ? (Integer)cfg().heartRangeMastered.get() : (Integer)cfg().heartRange.get();
         if (heart != null && heart.isAlive() && this.isOwnedBy(entity, heart) && !(entity.distanceTo(heart) > range)) {
            restoreResourceState(entity, tag.getCompound("LittleKingSnapshot"));
            this.applyLittleKingState(entity, instance.isMastered(entity));
            if (entity.tickCount - tag.getInt("LittleKingLastChunkTick") >= (Integer)cfg().heartChunkRefreshTicks.get()) {
               tag.putInt("LittleKingLastChunkTick", entity.tickCount);
               TrueAuthorityOfGreedHeartChunks.holdHeart(entity, heart, tag);
            }

            if (entity.tickCount % (Integer)cfg().indicatorIntervalTicks.get() == 0) {
               this.showLittleKingIndicator(entity, heart, range);
            }

            if (entity.tickCount % (Integer)cfg().masteryHoldTick.get() == 0) {
               instance.addMasteryPoint(entity);
            }
         } else {
            this.removeLittleKing(entity, tag);
         }
      }
   }

   private void tickLionheart(ManasSkillInstance instance, LivingEntity entity, CompoundTag tag) {
      if (tag.getBoolean("LionheartActive") && tag.contains("LionheartSnapshot")) {
         restoreResourceState(entity, tag.getCompound("LionheartSnapshot"));
         this.applyLittleKingState(entity, instance.isMastered(entity));
      }
   }

   private void finishLionheart(ManasSkillInstance instance, LivingEntity entity, int activeTicks, boolean preventionStop) {
      CompoundTag tag = instance.getOrCreateTag();
      if (!tag.getBoolean("LionheartActive")) {
         tag.remove("LionheartSnapshot");
         tag.remove("LionheartActive");
         tag.remove("LionheartLastPenaltySecond");
         tag.remove("LionheartOverTimeSeconds");
         tag.remove("LionheartOriginalMaxHealth");
         if (!isLittleKingActive(instance)) {
            this.removeLittleKingState(entity);
         }
      } else {
         if (tag.contains("LionheartSnapshot")) {
            restoreResourceState(entity, tag.getCompound("LionheartSnapshot"));
         }

         int overSeconds = Math.max(tag.getInt("LionheartOverTimeSeconds"), Math.max(0, activeTicks - (Integer)cfg().preventionFreeTicks.get()) / 20);
         int cooldown = (Integer)cfg().baseCooldownTicks.get() + overSeconds * (Integer)cfg().extraCooldownPerOverSecondTicks.get();
         tag.remove("LionheartSnapshot");
         tag.remove("LionheartActive");
         tag.remove("LionheartLastPenaltySecond");
         tag.remove("LionheartOverTimeSeconds");
         tag.remove("LionheartOriginalMaxHealth");
         instance.setCoolDown(cooldown, 0);
         if (!isLittleKingActive(instance)) {
            this.removeLittleKingState(entity);
         }

         entity.level()
            .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_DEACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 0.7F);
         if (preventionStop && entity instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("tensuramoreskills.skill.true_authority_of_greed.prevention.stop").withStyle(ChatFormatting.GREEN), true
            );
         }
      }
   }

   private void punishLionheart(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      double originalMaxHealth = tag.contains("LionheartOriginalMaxHealth")
         ? tag.getDouble("LionheartOriginalMaxHealth")
         : entity.getAttributeValue(Attributes.MAX_HEALTH);
      double addPenalty = Math.max(1.0, originalMaxHealth * (Double)cfg().lionheartOvertimeHpPercentPerSecond.get());
      double penalty = tag.getDouble("GreedHpPenalty") + addPenalty;
      tag.putDouble("GreedHpPenalty", penalty);
      tag.putInt("GreedPenaltyTime", (Integer)cfg().penaltyDurationTicks.get());
      AttributeHelper.addPermanentAttribute(entity, Attributes.MAX_HEALTH, HP_PENALTY, -penalty, Operation.ADD_VALUE);
      if (entity.getHealth() > entity.getMaxHealth()) {
         entity.setHealth(entity.getMaxHealth());
      }

      if (entity instanceof Player player) {
         player.displayClientMessage(
            Component.translatable("tensuramoreskills.skill.true_authority_of_greed.lionheart.penalty").withStyle(ChatFormatting.DARK_RED), true
         );
      }

      if (penalty >= originalMaxHealth || entity.getMaxHealth() <= 0.0 || entity.getHealth() <= 0.0F) {
         this.finishLionheart(instance, entity, (Integer)cfg().preventionFreeTicks.get() + tag.getInt("LionheartOverTimeSeconds") * 20, false);
         entity.hurt(entity.damageSources().mobAttack(entity), Float.MAX_VALUE);
      }
   }

   private void applyLittleKingState(LivingEntity entity, boolean mastered) {
      this.restoreAir(entity);
      clearSeverance(entity);
      CookSkill.removeCookedHP(entity);
      AttributeHelper.addPermanentAttribute(
         entity, Attributes.ATTACK_DAMAGE, ACTIVE_ATTACK_DAMAGE, (Double)cfg().physicalBonusDamage.get(), Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttribute(
         entity,
         Attributes.ENTITY_INTERACTION_RANGE,
         ACTIVE_ENTITY_RANGE,
         mastered ? (Double)cfg().physicalRangeBonusMastered.get() : (Double)cfg().physicalRangeBonus.get(),
         Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttribute(
         entity,
         Attributes.BLOCK_INTERACTION_RANGE,
         ACTIVE_BLOCK_RANGE,
         mastered ? (Double)cfg().physicalRangeBonusMastered.get() : (Double)cfg().physicalRangeBonus.get(),
         Operation.ADD_VALUE
      );
   }

   private void removeLittleKingState(LivingEntity entity) {
      AttributeHelper.removeAttribute(entity, Attributes.ATTACK_DAMAGE, ACTIVE_ATTACK_DAMAGE);
      AttributeHelper.removeAttribute(entity, Attributes.ENTITY_INTERACTION_RANGE, ACTIVE_ENTITY_RANGE);
      AttributeHelper.removeAttribute(entity, Attributes.BLOCK_INTERACTION_RANGE, ACTIVE_BLOCK_RANGE);
   }

   private void restoreAir(LivingEntity entity) {
      entity.setAirSupply(entity.getMaxAirSupply());
   }

   private void applyDamageGreed(LivingEntity entity, boolean mastered) {
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.LAW_DEGRADATION, LAW_PIERCE, (Double)cfg().lawDegradation.get(), Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(
         entity,
         TensuraAttributes.RESISTANCE_DEGRADATION,
         RESISTANCE_PIERCE,
         mastered ? (Double)cfg().resistanceDegradationMastered.get() : (Double)cfg().resistanceDegradation.get(),
         Operation.ADD_VALUE
      );
   }

   private CompoundTag saveResourceState(LivingEntity entity) {
      CompoundTag tag = new CompoundTag();
      IExistence existence = TensuraStorages.getExistenceFrom(entity);
      tag.putDouble("MP", existence.getMagicule());
      tag.putDouble("AP", existence.getAura());
      return tag;
   }

   public static void restoreResourceState(LivingEntity entity, CompoundTag tag) {
      if (!tag.isEmpty() && entity.isAlive()) {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         existence.setMagicule(Math.min(tag.getDouble("MP"), EnergyHelper.getMaxMagicule(entity)));
         existence.setAura(Math.min(tag.getDouble("AP"), EnergyHelper.getMaxAura(entity)));
         existence.markDirty();
         entity.setAirSupply(entity.getMaxAirSupply());
      }
   }

   private static void putAttributeBase(LivingEntity entity, CompoundTag tag, Holder<Attribute> attribute, String key) {
      AttributeInstance instance = entity.getAttribute(attribute);
      if (instance != null) {
         tag.putDouble(key, instance.getBaseValue());
      }
   }

   private static void restoreAttributeBase(LivingEntity entity, CompoundTag tag, Holder<Attribute> attribute, String key) {
      if (tag.contains(key)) {
         AttributeInstance instance = entity.getAttribute(attribute);
         if (instance != null && instance.getBaseValue() != tag.getDouble(key)) {
            instance.setBaseValue(tag.getDouble(key));
         }
      }
   }

   private void restoreActiveResourceState(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag snapshot = getActiveSnapshot(instance);
      if (!snapshot.isEmpty()) {
         restoreResourceState(entity, snapshot);
      }
   }

   private static CompoundTag getActiveSnapshot(ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      if (tag.getBoolean("LionheartActive") && tag.contains("LionheartSnapshot")) {
         return tag.getCompound("LionheartSnapshot");
      } else {
         return tag.contains("LittleKingHeart") && tag.contains("LittleKingSnapshot") ? tag.getCompound("LittleKingSnapshot") : new CompoundTag();
      }
   }

   public static boolean hasLockedState(ManasSkillInstance instance) {
      return !getActiveSnapshot(instance).isEmpty();
   }

   public static boolean restoreLockedState(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag snapshot = getActiveSnapshot(instance);
      if (snapshot.isEmpty()) {
         return false;
      }

      restoreResourceState(entity, snapshot);
      return true;
   }

   public static boolean isLittleKingActive(ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      return tag.contains("LittleKingHeart") && tag.contains("LittleKingSnapshot");
   }

   public static boolean isLionheartActive(ManasSkillInstance instance) {
      return instance.getOrCreateTag().getBoolean("LionheartActive");
   }

   public static boolean shouldCancelIncomingHit(ManasSkillInstance instance) {
      return isLittleKingActive(instance) || isLionheartActive(instance);
   }

   public static float getSpiritualDamageMultiplier(ManasSkillInstance instance) {
      return shouldCancelIncomingHit(instance) ? ((Double)cfg().spiritualDamageMultiplier.get()).floatValue() : 1.0F;
   }

   private void removeLittleKing(LivingEntity entity, CompoundTag tag) {
      this.releaseHeartChunk(entity, tag);
      tag.remove("LittleKingHeart");
      tag.remove("LittleKingDimension");
      tag.remove("LittleKingName");
      tag.remove("LittleKingSnapshot");
      tag.remove("LittleKingLastChunkTick");
      if (entity instanceof Player player) {
         player.displayClientMessage(
            Component.translatable("tensuramoreskills.skill.true_authority_of_greed.little_king.lost").withStyle(ChatFormatting.RED), true
         );
      }

      if (!tag.getBoolean("LionheartActive")) {
         this.removeLittleKingState(entity);
      }

      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_DEACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 0.7F);
   }

   private void showLittleKingIndicator(LivingEntity entity, LivingEntity heart, int range) {
      if (entity instanceof Player player) {
         int distance = (int)entity.distanceTo(heart);
         player.displayClientMessage(
            Component.literal("Little King: ")
               .append(heart.getDisplayName())
               .append(Component.literal(" [" + distance + "/" + range + "]"))
               .withStyle(ChatFormatting.GOLD),
            true
         );
      }

      if (heart.level() instanceof ServerLevel serverLevel) {
         serverLevel.sendParticles(ParticleTypes.HEART, heart.getX(), heart.getY() + heart.getBbHeight() + 0.25, heart.getZ(), 2, 0.2, 0.2, 0.2, 0.0);
         serverLevel.sendParticles(ParticleTypes.ENCHANT, entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(), 3, 0.3, 0.3, 0.3, 0.0);
      }
   }

   private static void clearSeverance(LivingEntity entity) {
      IEffect effect = TensuraStorages.getEffectFrom(entity);
      if (effect.getSeveranceAmount() > 0.0F) {
         effect.setSeveranceAmount(0.0F);
         effect.markDirty();
      }
   }

   private boolean isOwnedBy(LivingEntity owner, LivingEntity target) {
      LivingEntity subordinateOwner = SubordinateHelper.getSubordinateOwner(target);
      if (subordinateOwner != null && Objects.equals(subordinateOwner.getUUID(), owner.getUUID())) {
         return true;
      } else if (target instanceof TamableAnimal tamable) {
         return tamable.isOwnedBy(owner);
      } else {
         IExistence existence = TensuraStorages.getExistenceFrom(target);
         return Objects.equals(existence.getPermanentOwner(), owner.getUUID());
      }
   }

   private LivingEntity findLiving(LivingEntity owner, UUID uuid) {
      if (uuid != null && !owner.level().isClientSide()) {
         if (owner.level() instanceof ServerLevel serverLevel && serverLevel.getEntity(uuid) instanceof LivingEntity living && living.isAlive()) {
            return living;
         } else {
            double searchRange = ((Integer)cfg().heartRangeMastered.get()).intValue() + 16.0;

            for (LivingEntity living : owner.level().getEntitiesOfClass(LivingEntity.class, owner.getBoundingBox().inflate(searchRange), LivingEntity::isAlive)) {
               if (Objects.equals(living.getUUID(), uuid)) {
                  return living;
               }
            }

            return null;
         }
      } else {
         return null;
      }
   }

   private void releaseHeartChunk(LivingEntity entity, CompoundTag tag) {
      TrueAuthorityOfGreedHeartChunks.releaseHeart(entity, tag);
   }

   private void revokeOldAuthority(LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         Skills skills = SkillAPI.getSkillsFrom(entity);
         Optional<ManasSkillInstance> old = skills.getSkill((ManasSkill)AllSkills.AUTHORITY_OF_GREED.get());
         if (old.isPresent()) {
            skills.forgetSkill((ManasSkill)AllSkills.AUTHORITY_OF_GREED.get());
            skills.markDirty();
            this.syncEntity(entity);
         }
      }
   }

   private void syncEntity(LivingEntity entity) {
      try {
         entity.getClass().getMethod("manasCore$sync").invoke(entity);
      } catch (ReflectiveOperationException var3) {
      }
   }

   private void fail(LivingEntity entity) {
      if (entity instanceof Player player) {
         player.displayClientMessage(Component.translatable("tensura.ability.activation_failed").withStyle(ChatFormatting.RED), true);
      }

      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
   }
}
