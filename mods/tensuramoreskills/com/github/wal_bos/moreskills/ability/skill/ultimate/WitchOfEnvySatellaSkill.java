package com.github.wal_bos.moreskills.ability.skill.ultimate;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.WitchOfEnvyDevouringShadowsHandler;
import com.github.wal_bos.moreskills.handler.WitchOfEnvySatellaHandler;
import com.github.wal_bos.moreskills.net.msg.WitchOfEnvySatellaVfxPayload;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import com.github.wal_bos.moreskills.util.SkillObtainmentHelper;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.data.TensuraSkillTags;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillPlunderEvent;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.magic.SpiritualMagics;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class WitchOfEnvySatellaSkill extends Skill {
   public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "witch_of_envy_satella");
   private static final ResourceLocation MAGICULE_GAIN_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "witch_envy_magicule_gain");
   private static final ResourceLocation MAGICULE_REGEN_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "witch_envy_magicule_regen");
   public static final int MODE_SHADOW_TENDRILS = 0;
   public static final int MODE_SHADOW_HAND = 1;
   public static final int MODE_SHADOW_HANDS = 2;
   public static final int MODE_AUTHORITY_OF_ENVY = 3;
   public static final int MODE_DEVOURING_SHADOWS = 4;
   private static final String SHADOW_COPY_COOLDOWN_UNTIL = "WitchOfEnvyShadowCopyCooldownUntil";

   public WitchOfEnvySatellaSkill() {
      super(SkillType.ULTIMATE);
   }

   private static MoreSkillsConfigs.WitchOfEnvySatellaConfig cfg() {
      return MoreSkillsConfigs.GRAND.witchOfEnvySatella;
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/ultimate/witch_of_envy_satella.png");
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
      return SkillObtainmentHelper.canAcquire(
         entity, (Double)cfg().acquirementMagiculeCost.get(), (String)cfg().requiredSkillId.get(), (Boolean)cfg().requiredSkillMustBeMastered.get()
      );
   }

   public int getModes(ManasSkillInstance instance) {
      return 5;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode <= 0 ? 4 : mode - 1;
      } else {
         return mode >= 4 ? 0 : mode + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "witch_of_envy_satella.shadow_tendrils";
         case 1 -> "witch_of_envy_satella.shadow_hand";
         case 2 -> "witch_of_envy_satella.shadow_hands";
         case 3 -> "witch_of_envy_satella.authority_of_envy";
         case 4 -> "witch_of_envy_satella.devouring_shadows";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> cfg().shadowTendrilsCost.get();
         case 1 -> cfg().shadowHandCost.get();
         case 2 -> cfg().shadowHandsCost.get();
         case 3 -> entity.isShiftKeyDown() ? (Double)cfg().checkpointCost.get() : (Double)cfg().authorityOfEnvyCost.get();
         case 4 -> cfg().devouringShadowsCost.get();
         default -> 0.0;
      };
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      if (!(instance.getMastery() < 0.0) && !instance.isTemporarySkill()) {
         WitchOfEnvySatellaHandler.init();
         WitchOfEnvyDevouringShadowsHandler.init();
         this.applyPassiveAttributes(instance, entity);
         this.grantPassiveSkills(entity, instance.isMastered(entity));
         WitchOfEnvySatellaHandler.ensureReviveWindow(instance, entity);
         WitchOfEnvySatellaHandler.setCheckpoint(entity, entity, instance, false);
      }
   }

   public void onSkillMastered(ManasSkillInstance instance, LivingEntity entity) {
      this.applyPassiveAttributes(instance, entity);
      this.grantPassiveSkills(entity, true);
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      this.applyPassiveAttributes(instance, entity);
      this.grantPassiveSkills(entity, instance.isMastered(entity));
      WitchOfEnvySatellaHandler.init();
      WitchOfEnvyDevouringShadowsHandler.init();
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      this.removePassiveAttributes(entity);
      if (instance.getMastery() >= 0.0) {
         this.applyPassiveAttributes(instance, entity);
      }
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      this.removePassiveAttributes(entity);
      WitchOfEnvySatellaHandler.clearOwner(entity);
      WitchOfEnvyDevouringShadowsHandler.stop(entity.getUUID());
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         WitchOfEnvySatellaHandler.tickOwner(this, instance, entity);
      }
   }

   public boolean onDeath(ManasSkillInstance instance, LivingEntity entity, DamageSource source) {
      return !WitchOfEnvySatellaHandler.trySelfRevive(entity, instance, source);
   }

   public boolean onBeingDamaged(ManasSkillInstance instance, LivingEntity entity, DamageSource source, float amount) {
      if (!this.isInSlot(entity, instance)) {
         return true;
      } else if (source == null || source.getEntity() == entity || source.getEntity() == null) {
         return true;
      } else if (this.shadowCopyOnCooldown(instance, entity)) {
         return true;
      } else if (source.getEntity().getType().is(TensuraEntityTags.NO_SKILL_PLUNDER)) {
         return true;
      } else if (source.getEntity() instanceof Player player && player.getAbilities().invulnerable) {
         return true;
      } else {
         if (this.tryCopy(instance, entity, source.getEntity())) {
            instance.addMasteryPoint(entity);
            this.setShadowCopyCooldown(instance, entity);
         }

         return true;
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            switch (mode) {
               case 0:
                  this.castShadowTendrils(instance, entity, mode);
                  break;
               case 1:
                  this.startShadowHand(instance, entity, mode);
                  break;
               case 2:
                  this.castShadowHands(instance, entity, mode);
                  break;
               case 3:
                  if (entity.isShiftKeyDown()) {
                     this.setAuthorityCheckpoint(instance, entity, true);
                  } else {
                     this.castAuthorityOfEnvy(instance, entity, mode);
                  }
                  break;
               case 4:
                  this.castDevouringShadows(instance, entity, mode);
            }
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (mode != 1) {
         return false;
      }

      if (entity.level().isClientSide()) {
         return true;
      }

      if (heldTicks % 20 == 0 && EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         return false;
      }

      WitchOfEnvySatellaHandler.holdShadowHand(this, instance, entity, heldTicks);
      if (heldTicks > 0 && heldTicks % 40 == 0) {
         instance.addMasteryPoint(entity);
      }

      return true;
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (mode == 1) {
         WitchOfEnvySatellaHandler.stopShadowHand(entity.getUUID());
      }
   }

   public boolean shouldTriggerReleaseOnHeldInterrupt(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      return mode == 1;
   }

   public DamageSource envyDamageSource(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return this.createSource(instance, entity, TensuraDamageTypes.DARKNESS_ELEMENTAL, mode);
   }

   private void castShadowTendrils(ManasSkillInstance instance, LivingEntity entity, int mode) {
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, (Double)cfg().shadowTendrilsRange.get(), false);
      if (!validEnemy(entity, target)) {
         this.fail(entity);
      } else {
         int count = instance.isMastered(entity) ? (Integer)cfg().shadowTendrilsCountMastered.get() : (Integer)cfg().shadowTendrilsCount.get();
         if (entity.level() instanceof ServerLevel level) {
            WitchOfEnvySatellaVfxPayload.sendTendrils(
               level, entity, target, count, (Integer)cfg().shadowTendrilsDurationTicks.get(), instance.isMastered(entity)
            );
         }

         DamageSource physical = this.envyDamageSource(entity, instance, mode);

         for (int i = 0; i < count; i++) {
            this.forceMixedDamage(
               instance,
               entity,
               target,
               physical,
               ((Double)cfg().shadowTendrilsPhysicalDamage.get()).floatValue(),
               ((Double)cfg().shadowTendrilsSpiritualDamage.get()).floatValue()
            );
         }

         instance.setCoolDown((Integer)cfg().shadowTendrilsCooldownTicks.get(), mode);
         instance.addMasteryPoint(entity);
         entity.swing(InteractionHand.MAIN_HAND, true);
         this.playDarkCast(entity, 0.68F);
      }
   }

   private void startShadowHand(ManasSkillInstance instance, LivingEntity entity, int mode) {
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, (Double)cfg().shadowHandRange.get(), false);
      if (!validEnemy(entity, target)) {
         this.fail(entity);
      } else {
         WitchOfEnvySatellaHandler.startShadowHand(this, instance, entity, target);
         instance.setCoolDown((Integer)cfg().shadowHandCooldownTicks.get(), mode);
         instance.addMasteryPoint(entity);
         entity.swing(InteractionHand.MAIN_HAND, true);
      }
   }

   private void castShadowHands(ManasSkillInstance instance, LivingEntity entity, int mode) {
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, (Double)cfg().shadowHandsRange.get(), false);
      if (!validEnemy(entity, target)) {
         this.fail(entity);
      } else {
         WitchOfEnvySatellaHandler.startShadowHands(this, instance, entity, target);
         instance.setCoolDown((Integer)cfg().shadowHandsCooldownTicks.get(), mode);
         instance.addMasteryPoint(entity);
         entity.swing(InteractionHand.MAIN_HAND, true);
         this.playDarkCast(entity, 0.5F);
      }
   }

   private void castAuthorityOfEnvy(ManasSkillInstance instance, LivingEntity entity, int mode) {
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, (Double)cfg().authorityRange.get(), false);
      if (!validAlly(entity, target)) {
         this.failAuthority(entity);
      } else if (WitchOfEnvySatellaHandler.removeAuthority(entity, target)) {
         instance.setCoolDown((Integer)cfg().authorityOfEnvyCooldownTicks.get(), mode);
         instance.addMasteryPoint(entity);
         entity.swing(InteractionHand.MAIN_HAND, true);
         if (entity instanceof Player player) {
            player.displayClientMessage(
               Component.literal("Authority of Envy removed from " + target.getName().getString()).withStyle(ChatFormatting.DARK_PURPLE), true
            );
         }
      } else {
         WitchOfEnvySatellaHandler.bindAuthority(entity, target, instance);
         instance.setCoolDown((Integer)cfg().authorityOfEnvyCooldownTicks.get(), mode);
         instance.addMasteryPoint(entity);
         entity.swing(InteractionHand.MAIN_HAND, true);
         if (entity instanceof Player player) {
            player.displayClientMessage(
               Component.literal("Authority of Envy bound to " + target.getName().getString()).withStyle(ChatFormatting.DARK_PURPLE), true
            );
         }
      }
   }

   private void setAuthorityCheckpoint(ManasSkillInstance instance, LivingEntity entity, boolean fromAuthorityMode) {
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, (Double)cfg().authorityRange.get(), false);
      if (!validAlly(entity, target)) {
         if (entity instanceof Player player) {
            player.displayClientMessage(
               Component.literal(fromAuthorityMode ? "Nobody with Authority of Envy was selected." : "No ally selected for checkpoint.")
                  .withStyle(ChatFormatting.RED),
               true
            );
         }

         this.fail(entity);
      } else if (!WitchOfEnvySatellaHandler.hasAuthority(entity, target)) {
         if (entity instanceof Player player) {
            player.displayClientMessage(Component.literal("That ally does not have your Authority of Envy.").withStyle(ChatFormatting.RED), true);
         }

         this.fail(entity);
      } else {
         WitchOfEnvySatellaHandler.setCheckpoint(entity, target, instance, true);
         instance.setCoolDown((Integer)cfg().checkpointCooldownTicks.get(), 3);
         instance.addMasteryPoint(entity);
         entity.swing(InteractionHand.MAIN_HAND, true);
      }
   }

   private void castDevouringShadows(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (entity.level() instanceof ServerLevel level) {
         WitchOfEnvyDevouringShadowsHandler.start(level, entity, instance);
         instance.setCoolDown((Integer)cfg().devouringShadowsCooldownTicks.get(), mode);
         instance.addMasteryPoint(entity);
         entity.swing(InteractionHand.MAIN_HAND, true);
         this.playDarkCast(entity, 0.42F);
      }
   }

   private void applyPassiveAttributes(ManasSkillInstance instance, LivingEntity entity) {
      double gain = instance.isMastered(entity) ? (Double)cfg().magiculeGainMastered.get() : (Double)cfg().magiculeGain.get();
      double regen = instance.isMastered(entity) ? (Double)cfg().magiculeRegenMastered.get() : (Double)cfg().magiculeRegen.get();
      AttributeInstance magiculeGain = entity.getAttribute(TensuraAttributes.MAGICULE_GAIN);
      if (magiculeGain != null) {
         magiculeGain.addOrReplacePermanentModifier(new AttributeModifier(MAGICULE_GAIN_ID, gain, Operation.ADD_VALUE));
      }

      AttributeInstance magiculeRegen = entity.getAttribute(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER);
      if (magiculeRegen != null) {
         magiculeRegen.addOrReplacePermanentModifier(new AttributeModifier(MAGICULE_REGEN_ID, regen - 1.0, Operation.ADD_MULTIPLIED_TOTAL));
      }
   }

   private void removePassiveAttributes(LivingEntity entity) {
      AttributeInstance magiculeGain = entity.getAttribute(TensuraAttributes.MAGICULE_GAIN);
      if (magiculeGain != null) {
         magiculeGain.removeModifier(MAGICULE_GAIN_ID);
      }

      AttributeInstance magiculeRegen = entity.getAttribute(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER);
      if (magiculeRegen != null) {
         magiculeRegen.removeModifier(MAGICULE_REGEN_ID);
      }
   }

   private void grantPassiveSkills(LivingEntity entity, boolean mastered) {
      this.grant(entity, "tensura:shadow_motion", true);
      this.grant(entity, "tensura:physical_attack_nullification", true);
      this.grant(entity, "tensura:magic_darkness_transform", true);
      this.grant(entity, "tensura:darkness_attack_nullification", true);
      this.grant(entity, "tensura:spatial_domination", true);
      this.grant(entity, "tensura:magic_space_transform", true);
      this.grant(entity, "tensura:holy_attack_resistance", true);
      this.grant(entity, "tensura:abnormal_condition_nullification", true);
      this.grant(entity, "tensura:cold_resistance", true);
      this.grant(entity, "tensura:corrosion_resistance", true);
      this.grant(entity, "tensura:magic_resistance", true);
      this.grant(entity, "tensura:pain_nullification", true);
      this.grant(entity, "tensura:paralysis_resistance", true);
      this.grant(entity, "tensura:poison_resistance", true);
      this.grant(entity, "tensura:spatial_attack_nullification", true);
      this.grant(entity, mastered ? "tensura:spiritual_attack_nullification" : "tensura:spiritual_attack_resistance", true);
      this.grant(entity, "tensura:wind_attack_resistance", true);
      this.grant(entity, "tensura:summon_otherworlder", true);
      this.grant(entity, "tensura:summon_daemon", true);
   }

   private static double chance01(double value) {
      return value > 1.0 ? value / 100.0 : value;
   }

   private void grant(LivingEntity entity, String id, boolean maxMastery) {
      ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(ResourceLocation.parse(id));
      if (skill != null) {
         SkillAPI.getSkillsFrom(entity).getSkill(skill).ifPresentOrElse(existing -> {
            if (maxMastery) {
               existing.setMastery(Math.max(existing.getMastery(), existing.getMaxMastery()));
            }

            existing.markDirty();
         }, () -> {
            TensuraSkillInstance granted = new TensuraSkillInstance(skill);
            if (maxMastery) {
               granted.setMastery(granted.getMaxMastery());
            }

            granted.setRemoveTime(-1);
            granted.getOrCreateTag().putBoolean("NoMagiculeCost", true);
            SkillHelper.learnSkill(entity, granted);
         });
      }
   }

   public void applyFearAura(ManasSkillInstance instance, LivingEntity entity) {
      WitchOfEnvySatellaHandler.applyFearAura(entity, instance);
   }

   private boolean tryCopy(ManasSkillInstance envy, LivingEntity owner, Entity source) {
      if (source instanceof LivingEntity target) {
         List<ManasSkillInstance> valid = SkillAPI.getSkillsFrom(target)
            .getLearnedSkills()
            .stream()
            .filter(targetInstance -> targetInstance != null && !targetInstance.isTemporarySkill() && targetInstance.getMastery() >= 0.0)
            .filter(targetInstance -> targetInstance.getSkill() != this)
            .filter(targetInstance -> this.canCopy(targetInstance.getSkill()))
            .filter(targetInstance -> SkillAPI.getSkillsFrom(owner).getSkill(targetInstance.getSkill()).isEmpty())
            .toList();
         if (valid.isEmpty()) {
            return false;
         }

         ManasSkillInstance chosen = valid.get(owner.getRandom().nextInt(valid.size()));
         ManasSkill skill = chosen.getSkill();
         double chance = this.copyChance(owner, envy, skill);
         return owner.getRandom().nextDouble() > chance ? false : this.copyWholeSkill(envy, owner, target, skill);
      } else {
         return false;
      }
   }

   private double copyChance(LivingEntity owner, ManasSkillInstance envy, ManasSkill skill) {
      return this.isAuthorityOrWitchCopy(skill)
         ? envy.isMastered(owner) ? (Double)cfg().copyChanceMastered.get() : (Double)cfg().copyChance.get()
         : envy.isMastered(owner) ? (Double)cfg().basicCopyChanceMastered.get() : (Double)cfg().basicCopyChance.get();
   }

   private boolean shadowCopyOnCooldown(ManasSkillInstance instance, LivingEntity entity) {
      return entity.level().getGameTime() < instance.getOrCreateTag().getLong("WitchOfEnvyShadowCopyCooldownUntil");
   }

   private void setShadowCopyCooldown(ManasSkillInstance instance, LivingEntity entity) {
      instance.getOrCreateTag()
         .putLong("WitchOfEnvyShadowCopyCooldownUntil", entity.level().getGameTime() + Math.max(0, (Integer)cfg().copyCooldownTicks.get()));
      instance.markDirty();
   }

   private boolean isAuthorityOrWitchCopy(ManasSkill skill) {
      return skill == AllSkills.AUTHORITY_OF_GREED.get()
         || skill == AllSkills.TRUE_AUTHORITY_OF_GREED.get()
         || skill == AllSkills.WITCH_OF_ENVY_SATELLA.get()
         || skill == AllSkills.SATELLA.get()
         || skill instanceof Skill tensuraSkill && tensuraSkill.getType() == SkillType.UNIQUE;
   }

   private boolean copyWholeSkill(ManasSkillInstance envy, LivingEntity owner, LivingEntity source, ManasSkill skill) {
      if (!this.canCopy(skill)) {
         return false;
      }

      Skills storage = SkillAPI.getSkillsFrom(owner);
      if (storage.getSkill(skill).isPresent()) {
         return false;
      }

      Changeable<ManasSkill> changeable = Changeable.of(skill);
      if (((SkillPlunderEvent)TensuraSkillEvents.SKILL_PLUNDER.invoker()).plunder(source, owner, false, changeable).isFalse()) {
         return false;
      }

      ManasSkill copied = (ManasSkill)changeable.get();
      TensuraSkillInstance copy = new TensuraSkillInstance(copied);
      copy.setMastery(Math.max(0.0, copy.getMaxMastery() * (Double)cfg().copyMasteryRatio.get()));
      boolean learned = SkillHelper.learnSkill(owner, copy, envy.getRemoveTime());
      if (learned) {
         owner.level().playSound(null, owner.getX(), owner.getY(), owner.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 0.8F, 0.55F);
         if (owner instanceof Player player) {
            player.displayClientMessage(Component.literal("Envy copied ").append(copied.getChatDisplayName(true)).withStyle(ChatFormatting.DARK_PURPLE), true);
         }
      }

      return learned;
   }

   private boolean canCopy(ManasSkill skill) {
      ResourceLocation id = skill.getRegistryName();
      if (id == null) {
         return false;
      }

      if (skill != AllSkills.AUTHORITY_OF_GREED.get() && skill != AllSkills.TRUE_AUTHORITY_OF_GREED.get()) {
         if (SkillAPI.getSkillRegistry().delegate(id).is(TensuraSkillTags.NO_PLUNDERING)) {
            return false;
         } else if (SkillAPI.getSkillRegistry().delegate(id).is(TensuraSkillTags.COPIABLE_MAGIC)) {
            return true;
         } else if (SkillAPI.getSkillRegistry().delegate(id).is(TensuraSkillTags.SPIRITUAL_MAGIC)) {
            return true;
         } else if (skill == SpiritualMagics.DARKNESS.get()
            || skill == SpiritualMagics.SHADOW_BIND.get()
            || skill == SpiritualMagics.DARK_CUBE.get()
            || skill == SpiritualMagics.DARKNESS_CANNON.get()
            || skill == SpiritualMagics.TRUE_DARKNESS.get()) {
            return true;
         } else {
            return !(skill instanceof Skill tensuraSkill)
               ? false
               : tensuraSkill.getType().equals(SkillType.COMMON)
                  || tensuraSkill.getType().equals(SkillType.EXTRA)
                  || tensuraSkill.getType().equals(SkillType.INTRINSIC)
                  || tensuraSkill.getType().equals(SkillType.UNIQUE);
         }
      } else {
         return true;
      }
   }

   public void forceMixedDamage(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, float physical, float spiritual) {
      if (target != null && target.isAlive()) {
         float beforeHealth = target.getHealth();
         float beforeAbsorb = target.getAbsorptionAmount();
         target.invulnerableTime = 0;
         if (physical > 0.0F) {
            target.hurt(source, physical);
         }

         target.invulnerableTime = 0;
         if (spiritual > 0.0F) {
            TensuraDamageHelper.directSpiritualHurt(target, owner, spiritual);
         }

         float total = Math.max(0.0F, physical + spiritual);
         if (target.isAlive()
            && total > 0.0F
            && target.getHealth() >= beforeHealth
            && target.getAbsorptionAmount() >= beforeAbsorb
            && !target.hasInfiniteMaterials()) {
            float remaining = total;
            float absorption = target.getAbsorptionAmount();
            if (absorption > 0.0F) {
               float used = Math.min(absorption, remaining);
               target.setAbsorptionAmount(absorption - used);
               remaining -= used;
            }

            if (remaining > 0.0F) {
               target.setHealth(Math.max(0.0F, beforeHealth - remaining));
            }

            if (target.getHealth() <= 0.0F) {
               target.getCombatTracker().recordDamage(source, Math.max(1.0F, beforeHealth));
               target.die(source);
            }
         }

         TensuraDamageHelper.markHurt(target, owner);
         target.invulnerableTime = 0;
         target.hurtMarked = true;
      }
   }

   private static boolean validEnemy(LivingEntity owner, LivingEntity target) {
      return target != null
         && target.isAlive()
         && target != owner
         && !target.isAlliedTo(owner)
         && !(target instanceof Player player && (player.isCreative() || player.isSpectator()));
   }

   private static boolean validAlly(LivingEntity owner, LivingEntity target) {
      return target != null && target.isAlive() && target != owner && (target.isAlliedTo(owner) || owner.isAlliedTo(target) || target instanceof Player);
   }

   private void fail(LivingEntity entity) {
      if (entity instanceof Player player) {
         player.displayClientMessage(Component.translatable("tensura.ability.activation_failed").withStyle(ChatFormatting.RED), true);
      }

      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
   }

   private void failAuthority(LivingEntity entity) {
      if (entity instanceof Player player) {
         player.displayClientMessage(Component.literal("Authority of Envy needs a selected ally.").withStyle(ChatFormatting.RED), true);
      }

      this.fail(entity);
   }

   private void playDarkCast(LivingEntity entity, float pitch) {
      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_DARK.get(), SoundSource.PLAYERS, 1.0F, pitch);
   }
}
