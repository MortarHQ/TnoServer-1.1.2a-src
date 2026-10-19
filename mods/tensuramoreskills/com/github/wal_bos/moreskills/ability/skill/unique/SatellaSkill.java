package com.github.wal_bos.moreskills.ability.skill.unique;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.SatellaDevouringShadowsHandler;
import com.github.wal_bos.moreskills.net.msg.SatellaVfxPayload;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
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
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class SatellaSkill extends Skill {
   public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "satella");
   private static final ResourceLocation MAGICULE_GAIN_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "satella_magicule_gain");
   private static final ResourceLocation MAGICULE_REGEN_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "satella_magicule_regen");
   public static final int MODE_SHADOW_TENDRILS = 0;
   public static final int MODE_DEVOURING_SHADOWS = 1;
   private static final String SHADOW_COPY_COOLDOWN_UNTIL = "SatellaShadowCopyCooldownUntil";

   public SatellaSkill() {
      super(SkillType.UNIQUE);
   }

   private static MoreSkillsConfigs.SatellaConfig cfg() {
      return MoreSkillsConfigs.GRAND.satella;
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/unique/satella.png");
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

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return mode == 0 ? 1 : 0;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "satella.shadow_tendrils";
         case 1 -> "satella.devouring_shadows";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return (mode == 1 ? (Integer)cfg().devouringShadowsCost.get() : (Integer)cfg().shadowTendrilsCost.get()).intValue();
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      if (!(instance.getMastery() < 0.0) && !instance.isTemporarySkill()) {
         this.applyPassiveAttributes(instance, entity);
         this.grantPassiveSkills(entity);
         SatellaDevouringShadowsHandler.init();
      }
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      this.removePassiveAttributes(entity);
      SatellaDevouringShadowsHandler.stop(entity.getUUID());
   }

   public void onSkillMastered(ManasSkillInstance instance, LivingEntity entity) {
      this.applyPassiveAttributes(instance, entity);
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      this.applyPassiveAttributes(instance, entity);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      this.removePassiveAttributes(entity);
      if (instance.getMastery() >= 0.0) {
         this.applyPassiveAttributes(instance, entity);
      }
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         this.applyPassiveAttributes(instance, entity);
         if (entity.tickCount % 80 == 0) {
            this.grantPassiveSkills(entity);
         }

         if (instance.isToggled() && entity.tickCount % 20 == 0) {
            SatellaDevouringShadowsHandler.applyFearAura(entity, instance);
         }
      }
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
         if (this.copyRandomValidSkill(instance, entity, source.getEntity())) {
            instance.addMasteryPoint(entity);
            this.setShadowCopyCooldown(instance, entity);
         }

         return true;
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         switch (mode) {
            case 0:
               this.castShadowTendrils(instance, entity, mode);
               break;
            case 1:
               this.castDevouringShadows(instance, entity, mode);
         }
      }
   }

   public DamageSource damageSource(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return this.createSource(instance, entity, TensuraDamageTypes.DARKNESS_ELEMENTAL, mode);
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

   private void grantPassiveSkills(LivingEntity entity) {
      this.grantPassiveSkill(entity, (ManasSkill)ExtraSkills.SHADOW_MOTION.get());
      this.grantPassiveSkill(entity, (ManasSkill)ResistanceSkills.PHYSICAL_ATTACK_NULLIFICATION.get());
      this.grantPassiveSkill(entity, (ManasSkill)ExtraSkills.MAGIC_DARKNESS_TRANSFORM.get());
      this.grantPassiveSkill(entity, (ManasSkill)ResistanceSkills.DARKNESS_ATTACK_NULLIFICATION.get());
   }

   private void grantPassiveSkill(LivingEntity entity, ManasSkill skill) {
      SkillAPI.getSkillsFrom(entity).getSkill(skill).ifPresentOrElse(existing -> {
         existing.setMastery(Math.max(existing.getMastery(), existing.getMaxMastery()));
         existing.markDirty();
      }, () -> {
         TensuraSkillInstance granted = new TensuraSkillInstance(skill);
         granted.setMastery(granted.getMaxMastery());
         SkillHelper.learnSkill(entity, granted);
      });
   }

   private static double chance01(double value) {
      return value > 1.0 ? value / 100.0 : value;
   }

   private void applyFearAura(ManasSkillInstance instance, LivingEntity entity) {
      SatellaDevouringShadowsHandler.applyFearAura(entity, instance);
   }

   private boolean copyRandomValidSkill(ManasSkillInstance satella, LivingEntity owner, Entity source) {
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

         ManasSkill skill = valid.get(owner.getRandom().nextInt(valid.size())).getSkill();
         double chance = this.copyChance(satella, owner, skill);
         return owner.getRandom().nextDouble() > chance ? false : this.copyWholeSkill(satella, owner, source, skill);
      } else {
         return false;
      }
   }

   private double copyChance(ManasSkillInstance instance, LivingEntity owner, ManasSkill skill) {
      if (this.isAuthorityOrWitchCopy(skill)) {
         return instance.isMastered(owner) ? chance01((Double)cfg().copyAuthorityChanceMastered.get()) : chance01((Double)cfg().copyAuthorityChance.get());
      } else {
         return instance.isMastered(owner) ? chance01((Double)cfg().copyBasicChanceMastered.get()) : chance01((Double)cfg().copyBasicChance.get());
      }
   }

   private boolean shadowCopyOnCooldown(ManasSkillInstance instance, LivingEntity entity) {
      return entity.level().getGameTime() < instance.getOrCreateTag().getLong("SatellaShadowCopyCooldownUntil");
   }

   private void setShadowCopyCooldown(ManasSkillInstance instance, LivingEntity entity) {
      instance.getOrCreateTag().putLong("SatellaShadowCopyCooldownUntil", entity.level().getGameTime() + Math.max(0, (Integer)cfg().copyCooldownTicks.get()));
      instance.markDirty();
   }

   private boolean isAuthorityOrWitchCopy(ManasSkill skill) {
      return skill == AllSkills.AUTHORITY_OF_GREED.get() || skill == AllSkills.TRUE_AUTHORITY_OF_GREED.get() || skill == AllSkills.WITCH_OF_ENVY_SATELLA.get();
   }

   private boolean copyWholeSkill(ManasSkillInstance satella, LivingEntity owner, Entity source, ManasSkill skill) {
      if (source instanceof LivingEntity target) {
         if (!this.canCopy(skill)) {
            return false;
         }

         Skills storage = SkillAPI.getSkillsFrom(owner);
         if (storage.getSkill(skill).isPresent()) {
            return false;
         }

         Changeable<ManasSkill> changeable = Changeable.of(skill);
         if (((SkillPlunderEvent)TensuraSkillEvents.SKILL_PLUNDER.invoker()).plunder(target, owner, false, changeable).isFalse()) {
            return false;
         }

         ManasSkill copied = (ManasSkill)changeable.get();
         TensuraSkillInstance copy = new TensuraSkillInstance(copied);
         copy.setMastery(Math.max(0.0, copy.getMaxMastery() * (Double)cfg().copyMasteryRatio.get()));
         boolean learned = SkillHelper.learnSkill(owner, copy, satella.getRemoveTime());
         if (learned) {
            owner.level().playSound(null, owner.getX(), owner.getY(), owner.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 0.8F, 0.65F);
            if (owner instanceof Player player) {
               player.displayClientMessage(
                  Component.translatable("tensuramoreskills.skill.satella.copied", new Object[]{copied.getChatDisplayName(true)})
                     .withStyle(ChatFormatting.DARK_PURPLE),
                  true
               );
            }
         }

         return learned;
      } else {
         return false;
      }
   }

   private boolean canCopy(ManasSkill skill) {
      ResourceLocation id = skill.getRegistryName();
      if (id == null) {
         return false;
      } else if (this.isAuthorityOrWitchCopy(skill)) {
         return true;
      } else if (SkillAPI.getSkillRegistry().delegate(id).is(TensuraSkillTags.NO_PLUNDERING)) {
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
               || tensuraSkill.getType().equals(SkillType.INTRINSIC);
      }
   }

   private void castShadowTendrils(ManasSkillInstance instance, LivingEntity entity, int mode) {
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, (Double)cfg().shadowTendrilsRange.get(), false);
      if (target != null && target.isAlive() && !target.isAlliedTo(entity)) {
         int count = instance.isMastered(entity) ? (Integer)cfg().shadowTendrilsCountMastered.get() : (Integer)cfg().shadowTendrilsCount.get();
         Vec3 start = entity.getEyePosition().add(entity.getLookAngle().normalize().scale(0.65));
         Vec3 end = target.getEyePosition().add(0.0, target.getBbHeight() * 0.15, 0.0);
         if (entity.level() instanceof ServerLevel level) {
            SatellaVfxPayload.sendTendrilsTracking(
               level, entity, target, start, end, count, (Integer)cfg().shadowTendrilsVfxLifeTicks.get(), instance.isMastered(entity)
            );
         }

         DamageSource physical = this.createSource(instance, entity, TensuraDamageTypes.DARKNESS_ELEMENTAL, mode);

         for (int i = 0; i < count; i++) {
            target.invulnerableTime = 0;
            target.hurt(physical, ((Double)cfg().shadowTendrilsPhysicalDamage.get()).floatValue());
            target.invulnerableTime = 0;
            TensuraDamageHelper.directSpiritualHurt(target, entity, ((Double)cfg().shadowTendrilsSpiritualDamage.get()).floatValue());
         }

         instance.addMasteryPoint(entity);
         instance.setCoolDown((Integer)cfg().shadowTendrilsCooldownTicks.get(), mode);
         entity.swing(InteractionHand.MAIN_HAND, true);
         entity.level()
            .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_DARK.get(), SoundSource.PLAYERS, 1.0F, 0.7F);
      }
   }

   private void castDevouringShadows(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (entity.level() instanceof ServerLevel level) {
         instance.addMasteryPoint(entity);
         instance.setCoolDown((Integer)cfg().devouringShadowsCooldownTicks.get(), mode);
         entity.swing(InteractionHand.MAIN_HAND, true);
         entity.level()
            .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_DARK.get(), SoundSource.PLAYERS, 1.0F, 0.45F);
         SatellaDevouringShadowsHandler.start(level, entity, instance);
      }
   }
}
