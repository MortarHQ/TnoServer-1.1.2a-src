package com.github.wal_bos.moreskills.ability.skill.unique;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.UniqueOwnerSkillHandler;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
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
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class AuthorityOfGreedSkill extends Skill {
   private static final int MODE_LIONHEART = 0;
   private static final int MODE_LITTLE_KING = 1;
   private static final ResourceLocation RESISTANCE_PIERCE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "authority_of_greed_resistance_pierce");
   private static final ResourceLocation LAW_PIERCE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "authority_of_greed_law_pierce");
   private static final ResourceLocation HP_PENALTY = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "authority_of_greed_hp_penalty");

   public AuthorityOfGreedSkill() {
      super(SkillType.UNIQUE);
   }

   private static MoreSkillsConfigs.AuthorityOfGreedConfig cfg() {
      return MoreSkillsConfigs.GRAND.authorityOfGreed;
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/unique/authority_of_greed.png");
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
         case 0 -> "authority_of_greed.lionheart";
         case 1 -> "authority_of_greed.little_king";
         default -> super.getModeId(instance, mode);
      };
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      this.applyDamageGreed(entity, instance.isMastered(entity));
   }

   public void onSkillMastered(ManasSkillInstance instance, LivingEntity entity) {
      this.applyDamageGreed(entity, true);
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         player.displayClientMessage(Component.translatable("tensuramoreskills.skill.authority_of_greed.prevention.on").withStyle(ChatFormatting.GREEN), true);
      }

      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         player.displayClientMessage(Component.translatable("tensuramoreskills.skill.authority_of_greed.prevention.off").withStyle(ChatFormatting.RED), true);
      }

      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_DEACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      return tag.contains("LittleKingHeart")
         || tag.contains("LionheartSnapshot")
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
         if (tag.getBoolean("LionheartActive") && tag.contains("LionheartSnapshot")) {
            restoreState(entity, tag.getCompound("LionheartSnapshot"));
         }
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide() && mode == 1) {
         int range = instance.isMastered(entity) ? (Integer)cfg().heartRangeMastered.get() : (Integer)cfg().heartRange.get();
         LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, range, false);
         if (target != null && target != entity && this.isOwnedBy(entity, target)) {
            CompoundTag tag = instance.getOrCreateTag();
            tag.putUUID("LittleKingHeart", target.getUUID());
            tag.putString("LittleKingName", target.getDisplayName().getString());
            tag.put("LittleKingSnapshot", this.saveState(entity));
            tag.remove("LionheartSnapshot");
            tag.remove("LionheartActive");
            tag.remove("LastPenaltyTick");
            restoreState(entity, tag.getCompound("LittleKingSnapshot"));
            instance.addMasteryPoint(entity);
            entity.swing(InteractionHand.MAIN_HAND, true);
            entity.level()
               .playSound(
                  null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 0.8F
               );
            if (entity instanceof Player player) {
               player.displayClientMessage(Component.literal("Little King: LOCKED → ").append(target.getDisplayName()).withStyle(ChatFormatting.GOLD), true);
               player.sendSystemMessage(Component.literal("Little King pseudo heart placed on ").append(target.getDisplayName()).withStyle(ChatFormatting.GOLD));
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
         tag.put("LionheartSnapshot", this.saveState(entity));
         tag.putInt("LastPenaltyTick", 0);
         entity.level()
            .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 0.7F);
      }

      restoreState(entity, tag.getCompound("LionheartSnapshot"));
      if (SkillUtils.isSkillToggled(entity, this) && activeTicks >= (Integer)cfg().preventionFreeTicks.get()) {
         this.finishLionheart(instance, entity, activeTicks, true);
         return false;
      }

      if (activeTicks > (Integer)cfg().preventionFreeTicks.get()
         && activeTicks % (Integer)cfg().penaltyIntervalTicks.get() == 0
         && tag.getInt("LastPenaltyTick") != activeTicks) {
         tag.putInt("LastPenaltyTick", activeTicks);
         this.punishLionheart(instance, entity);
         tag.put("LionheartSnapshot", this.saveState(entity));
      }

      if (activeTicks > 0 && activeTicks % (Integer)cfg().masteryHoldTick.get() == 0) {
         instance.addMasteryPoint(entity);
      }

      if (activeTicks % 20 == 0 && entity instanceof Player player) {
         int seconds = activeTicks / 20;
         player.displayClientMessage(Component.literal("Lionheart: " + seconds + "s").withStyle(seconds > 5 ? ChatFormatting.RED : ChatFormatting.GOLD), true);
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
      return true;
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity entity, DamageSource source, Changeable<Float> amount) {
      CompoundTag snapshot = getActiveSnapshot(instance);
      if (!snapshot.isEmpty()) {
         restoreState(entity, snapshot);
         amount.set(0.0F);
         entity.invulnerableTime = 0;
         return false;
      }

      CookSkill.removeCookedHP(entity);
      boolean severanceWithEnchant = TensuraDamageHelper.isSeveranceDamage(source, entity, true);
      boolean severanceWithoutEnchant = TensuraDamageHelper.isSeveranceDamage(source, entity, false);
      boolean physical = TensuraDamageHelper.isPhysicalAttack(source);
      if (severanceWithEnchant) {
         clearSeverance(entity);
      }

      if (severanceWithoutEnchant && !physical) {
         clearSeverance(entity);
         amount.set(0.0F);
         return false;
      }

      if (source.getMsgId().contains("cook") || source.getMsgId().contains("cooked")) {
         CookSkill.removeCookedHP(entity);
      }

      return true;
   }

   public boolean onBeingDamaged(ManasSkillInstance instance, LivingEntity entity, DamageSource source, float amount) {
      CompoundTag snapshot = getActiveSnapshot(instance);
      if (!snapshot.isEmpty()) {
         restoreState(entity, snapshot);
         entity.invulnerableTime = 0;
      }

      return true;
   }

   public void onPostBeingDamaged(ManasSkillInstance instance, LivingEntity entity, DamageSource source, float amount) {
      CompoundTag snapshot = getActiveSnapshot(instance);
      if (!snapshot.isEmpty()) {
         restoreState(entity, snapshot);
         entity.invulnerableTime = 0;
      }

      boolean severanceWithEnchant = TensuraDamageHelper.isSeveranceDamage(source, entity, true);
      if (severanceWithEnchant) {
         clearSeverance(entity);
      }

      if (source.getMsgId().contains("cook") || source.getMsgId().contains("cooked")) {
         CookSkill.removeCookedHP(entity);
      }
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      UniqueOwnerSkillHandler.releaseSkill(instance, entity);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.RESISTANCE_DEGRADATION, RESISTANCE_PIERCE);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.LAW_DEGRADATION, LAW_PIERCE);
      AttributeHelper.removeAttribute(entity, Attributes.MAX_HEALTH, HP_PENALTY);
      CompoundTag tag = instance.getOrCreateTag();
      tag.remove("LittleKingHeart");
      tag.remove("LittleKingName");
      tag.remove("LittleKingSnapshot");
      tag.remove("LionheartSnapshot");
      tag.remove("LionheartActive");
      tag.remove("LastPenaltyTick");
      tag.remove("GreedPenaltyTime");
      tag.remove("GreedHpPenalty");
   }

   private void finishLionheart(ManasSkillInstance instance, LivingEntity entity, int activeTicks, boolean preventionStop) {
      CompoundTag tag = instance.getOrCreateTag();
      if (!tag.getBoolean("LionheartActive")) {
         tag.remove("LionheartSnapshot");
         tag.remove("LionheartActive");
         tag.remove("LastPenaltyTick");
      } else {
         if (tag.contains("LionheartSnapshot")) {
            restoreState(entity, tag.getCompound("LionheartSnapshot"));
         }

         tag.remove("LionheartSnapshot");
         tag.remove("LionheartActive");
         tag.remove("LastPenaltyTick");
         int seconds = Math.max(0, activeTicks / 20);
         int freeSeconds = Math.max(0, (Integer)cfg().preventionFreeTicks.get() / 20);
         int cooldown = (Integer)cfg().baseCooldownTicks.get() + Math.max(0, seconds - freeSeconds);
         instance.setCoolDown(cooldown, 0);
         entity.level()
            .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_DEACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 0.7F);
         if (preventionStop && entity instanceof Player player) {
            player.displayClientMessage(Component.literal("Prevention stopped Lionheart at " + freeSeconds + "s").withStyle(ChatFormatting.GREEN), true);
         }
      }
   }

   private void tickLittleKing(ManasSkillInstance instance, LivingEntity entity, CompoundTag tag) {
      if (tag.contains("LittleKingHeart") && tag.contains("LittleKingSnapshot")) {
         LivingEntity heart = this.findLiving(entity, tag.getUUID("LittleKingHeart"));
         int range = instance.isMastered(entity) ? (Integer)cfg().heartRangeMastered.get() : (Integer)cfg().heartRange.get();
         if (heart != null && heart.isAlive() && this.isOwnedBy(entity, heart) && !(entity.distanceTo(heart) > range)) {
            restoreState(entity, tag.getCompound("LittleKingSnapshot"));
            if (entity.tickCount % 20 == 0) {
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

   private void removeLittleKing(LivingEntity entity, CompoundTag tag) {
      tag.remove("LittleKingHeart");
      tag.remove("LittleKingName");
      tag.remove("LittleKingSnapshot");
      if (entity instanceof Player player) {
         player.displayClientMessage(Component.literal("Little King: HEART LOST").withStyle(ChatFormatting.RED), true);
         player.sendSystemMessage(Component.translatable("tensuramoreskills.skill.authority_of_greed.little_king.lost").withStyle(ChatFormatting.RED));
      }

      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_DEACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 0.7F);
   }

   private void showLittleKingIndicator(LivingEntity entity, LivingEntity heart, int range) {
      if (entity instanceof Player player) {
         int distance = (int)entity.distanceTo(heart);
         player.displayClientMessage(
            Component.literal("Little King: LOCKED → ")
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

   private CompoundTag saveState(LivingEntity entity) {
      CompoundTag tag = new CompoundTag();
      IExistence existence = TensuraStorages.getExistenceFrom(entity);
      tag.putFloat("Health", entity.getHealth());
      tag.putFloat("Absorption", entity.getAbsorptionAmount());
      tag.putDouble("SHP", existence.getSpiritualHealth());
      tag.putDouble("MP", existence.getMagicule());
      tag.putDouble("AP", existence.getAura());
      tag.putDouble("EP", EnergyHelper.getMaxEP(entity));
      tag.putInt("Fire", entity.getRemainingFireTicks());
      putAttributeBase(entity, tag, Attributes.MAX_HEALTH, "MaxHPBase");
      putAttributeBase(entity, tag, TensuraAttributes.MAX_SPIRITUAL_HEALTH, "MaxSHPBase");
      putAttributeBase(entity, tag, TensuraAttributes.MAX_MAGICULE, "MaxMPBase");
      putAttributeBase(entity, tag, TensuraAttributes.MAX_AURA, "MaxAPBase");
      putAttributeBase(entity, tag, TensuraAttributes.LIMITED_SPIRITUAL_MAX_MAGICULE, "LimitedMaxMPBase");
      putAttributeBase(entity, tag, TensuraAttributes.LIMITED_SPIRITUAL_MAX_AURA, "LimitedMaxAPBase");
      ListTag effects = new ListTag();

      for (MobEffectInstance effect : entity.getActiveEffects()) {
         effects.add(effect.save());
      }

      tag.put("Effects", effects);
      return tag;
   }

   public static void restoreState(LivingEntity entity, CompoundTag tag) {
      if (!tag.isEmpty() && entity.isAlive()) {
         restoreAttributeBase(entity, tag, Attributes.MAX_HEALTH, "MaxHPBase");
         restoreAttributeBase(entity, tag, TensuraAttributes.MAX_SPIRITUAL_HEALTH, "MaxSHPBase");
         restoreAttributeBase(entity, tag, TensuraAttributes.MAX_MAGICULE, "MaxMPBase");
         restoreAttributeBase(entity, tag, TensuraAttributes.MAX_AURA, "MaxAPBase");
         restoreAttributeBase(entity, tag, TensuraAttributes.LIMITED_SPIRITUAL_MAX_MAGICULE, "LimitedMaxMPBase");
         restoreAttributeBase(entity, tag, TensuraAttributes.LIMITED_SPIRITUAL_MAX_AURA, "LimitedMaxAPBase");
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         entity.setHealth(Math.min(tag.getFloat("Health"), entity.getMaxHealth()));
         entity.setAbsorptionAmount(tag.getFloat("Absorption"));
         entity.setRemainingFireTicks(tag.getInt("Fire"));
         existence.setSpiritualHealth(Math.min(tag.getDouble("SHP"), entity.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH)));
         existence.setMagicule(Math.min(tag.getDouble("MP"), EnergyHelper.getMaxMagicule(entity)));
         existence.setAura(Math.min(tag.getDouble("AP"), EnergyHelper.getMaxAura(entity)));
         existence.markDirty();

         for (MobEffectInstance effect : new ArrayList(entity.getActiveEffects())) {
            entity.removeEffect(effect.getEffect());
         }

         for (Tag effectTag : tag.getList("Effects", 10)) {
            MobEffectInstance effect = MobEffectInstance.load((CompoundTag)effectTag);
            if (effect != null) {
               entity.addEffect(effect);
            }
         }

         clearSeverance(entity);
         CookSkill.removeCookedHP(entity);
         entity.invulnerableTime = 0;
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

   private static CompoundTag getActiveSnapshot(ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      if (tag.contains("LittleKingHeart") && tag.contains("LittleKingSnapshot")) {
         return tag.getCompound("LittleKingSnapshot");
      } else {
         return tag.getBoolean("LionheartActive") && tag.contains("LionheartSnapshot") ? tag.getCompound("LionheartSnapshot") : new CompoundTag();
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

      restoreState(entity, snapshot);
      return true;
   }

   private void punishLionheart(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      IExistence existence = TensuraStorages.getExistenceFrom(entity);
      double mpLoss = EnergyHelper.getMaxMagicule(entity) * (Double)cfg().lionheartMagiculeLossPercent.get();
      existence.setMagicule(Math.max(0.0, existence.getMagicule() - mpLoss));
      existence.markDirty();
      double maxHealth = entity.getAttributeValue(Attributes.MAX_HEALTH);
      double addPenalty = Math.max((Double)cfg().lionheartMinHpPenalty.get(), maxHealth * (Double)cfg().lionheartMaxHealthPenaltyPercent.get());
      double penalty = tag.getDouble("GreedHpPenalty") + addPenalty;
      tag.putDouble("GreedHpPenalty", penalty);
      tag.putInt("GreedPenaltyTime", (Integer)cfg().penaltyDurationTicks.get());
      AttributeHelper.addPermanentAttribute(entity, Attributes.MAX_HEALTH, HP_PENALTY, -penalty, Operation.ADD_VALUE);
      if (entity.getHealth() > entity.getMaxHealth()) {
         entity.setHealth(entity.getMaxHealth());
      }

      if (entity instanceof Player player) {
         player.displayClientMessage(
            Component.translatable("tensuramoreskills.skill.authority_of_greed.lionheart.penalty").withStyle(ChatFormatting.DARK_RED), true
         );
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
         double searchRange = ((Integer)cfg().heartRangeMastered.get()).intValue() + 16.0;

         for (LivingEntity living : owner.level().getEntitiesOfClass(LivingEntity.class, owner.getBoundingBox().inflate(searchRange), LivingEntity::isAlive)) {
            if (Objects.equals(living.getUUID(), uuid)) {
               return living;
            }
         }

         return null;
      } else {
         return null;
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
