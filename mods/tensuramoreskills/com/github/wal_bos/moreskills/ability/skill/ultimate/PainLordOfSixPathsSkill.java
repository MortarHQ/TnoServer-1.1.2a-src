package com.github.wal_bos.moreskills.ability.skill.ultimate;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.PainFlightSuppressionHandler;
import com.github.wal_bos.moreskills.handler.PainPlanetaryDevastationHandler;
import com.github.wal_bos.moreskills.net.msg.PainSixPathsVfxPayload;
import com.github.wal_bos.moreskills.util.SkillObtainmentHelper;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.ForceMovementEvent;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.entity.MonsterEntityTypes;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.EnergyHelper.DrainType;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class PainLordOfSixPathsSkill extends Skill {
   private static final MoreSkillsConfigs.PainLordOfSixPaths CONFIG = MoreSkillsConfigs.GRAND.painLordOfSixPaths;
   private static final ResourceLocation PAIN_KNOCKBACK = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "pain_six_paths_knockback");
   private static final ResourceLocation PAIN_SPEED = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "pain_six_paths_speed");
   private static final ResourceLocation PAIN_ATTACK = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "pain_six_paths_attack");
   private static final ResourceLocation PAIN_CHANT = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "pain_six_paths_chant");
   private static final ResourceLocation PAIN_PRESENCE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "pain_six_paths_presence");
   private static final ResourceLocation PAIN_PRESENCE_RADIUS = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "pain_six_paths_presence_radius");
   private static final ResourceLocation PAIN_PROJECTILE_DODGE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "pain_six_paths_projectile_dodge");
   private static final ResourceLocation PAIN_MELEE_DODGE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "pain_six_paths_melee_dodge");
   private static final ResourceLocation PAIN_DODGE_STRENGTH = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "pain_six_paths_dodge_strength");
   private static final ResourceLocation PAIN_DODGE_INVULN = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "pain_six_paths_dodge_invulnerability");
   private static final ResourceLocation PAIN_DODGE_NEGATE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "pain_six_paths_dodge_negate");
   private static final ResourceLocation PAIN_RESIST_DEGRADE = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "pain_six_paths_resistance_degradation"
   );
   private static final ResourceLocation PAIN_LAW_DEGRADE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "pain_six_paths_law_degradation");
   private static final ResourceLocation PAIN_GRAVITY_BOOST = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "pain_six_paths_gravity_boost");

   public PainLordOfSixPathsSkill() {
      super(SkillType.ULTIMATE);
      PainFlightSuppressionHandler.init();
   }

   public double getDefaultAcquiringMagiculeCost() {
      return (Double)CONFIG.acquirementMagiculeCost.get();
   }

   public int getAcquirementMastery(LivingEntity entity) {
      return (Integer)CONFIG.acquirementMastery.get();
   }

   public int getMaxMastery() {
      return (Integer)CONFIG.maxMastery.get();
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      boolean main = SkillObtainmentHelper.canAcquire(
         entity, (Double)CONFIG.acquirementMagiculeCost.get(), (String)CONFIG.requiredSkillId.get(), (Boolean)CONFIG.requiredSkillMustBeMastered.get()
      );
      boolean gravity = SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)ExtraSkills.GRAVITY_MANIPULATION.get()).isPresent();
      return main && gravity && newEP >= (Double)CONFIG.requiredEp.get();
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/ultimate/pain.png");
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public int getModes(ManasSkillInstance instance) {
      return 5;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return reverse ? (mode == 0 ? 4 : mode - 1) : (mode == 4 ? 0 : mode + 1);
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "pain_lord_of_six_paths.almighty_push";
         case 1 -> "pain_lord_of_six_paths.universal_pull";
         case 2 -> "pain_lord_of_six_paths.six_paths";
         case 3 -> "pain_lord_of_six_paths.heavenly_rupture";
         case 4 -> "pain_lord_of_six_paths.planetary_devastation";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> CONFIG.almightyPushCost.get();
         case 1 -> CONFIG.universalPullCost.get();
         case 2 -> this.pathCost(this.getPath(instance));
         case 3 -> CONFIG.heavenlyRuptureCost.get();
         case 4 -> CONFIG.planetaryDevastationCost.get();
         default -> 0.0;
      };
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      return tag.getBoolean("PlanetaryDevastationActive")
         || tag.getBoolean("AlmightyPushFlight")
         || tag.getInt("PretaShieldTicks") > 0
         || tag.hasUUID("RuptureTarget")
         || instance.isToggled()
         || PainPlanetaryDevastationHandler.hasPushDebris(entity);
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      if (tag.getBoolean("PlanetaryDevastationActive")) {
         PainPlanetaryDevastationHandler.tick(entity, instance, this);
      }

      if (PainPlanetaryDevastationHandler.hasPushDebris(entity)) {
         PainPlanetaryDevastationHandler.tickPushDebris(entity);
      }

      this.tickHeavenlyRupture(instance, entity);
      this.tickPretaShield(instance, entity);
      if (tag.getBoolean("AlmightyPushFlight")) {
         this.tickAlmightyPushHover(instance, entity);
      }

      if (instance.isToggled()) {
         PainFlightSuppressionHandler.startNoFlyAura(entity);
         if (entity.tickCount % (Integer)CONFIG.passiveRefreshTicks.get() == 0) {
            if (entity instanceof Player player) {
               player.resetFallDistance();
            }

            if (instance.isMastered(entity)) {
               this.rejectProjectiles(entity, (Double)CONFIG.pathPretaRadius.get(), (Double)CONFIG.pathPretaProjectileSpeed.get(), false);
            }
         }
      }
   }

   public boolean onBeingDamaged(ManasSkillInstance instance, LivingEntity entity, DamageSource source, float amount) {
      CompoundTag tag = instance.getOrCreateTag();
      if (tag.getInt("PretaShieldTicks") > 0) {
         long endTick = tag.getLong("PretaShieldEndTick");
         if (endTick > 0L && entity.level().getGameTime() > endTick) {
            tag.remove("PretaShieldTicks");
            tag.remove("PretaShieldEndTick");
            instance.markDirty();
         } else {
            Entity direct = source.getDirectEntity();
            if (direct instanceof Projectile || direct instanceof AbstractArrow) {
               double gain = Math.max((Double)CONFIG.pathPretaMinAbsorbGain.get(), amount * (Double)CONFIG.pathPretaDamageToMagiculeRatio.get());
               EnergyHelper.gainMagicule(entity, gain, GainType.NORMAL);
               entity.level()
                  .playSound(
                     null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.ENERGY_DRAIN.get(), SoundSource.PLAYERS, 0.8F, 0.55F
                  );
               TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.REVERSE_PORTAL, 1.0);
               return false;
            }
         }
      }

      if (!instance.isToggled()) {
         return true;
      }

      if (source.is(DamageTypeTags.IS_FALL)) {
         return false;
      }

      Entity direct = source.getDirectEntity();
      return !(direct instanceof AbstractArrow) && !(direct instanceof Projectile);
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.addPermanentAttribute(entity, Attributes.KNOCKBACK_RESISTANCE, PAIN_KNOCKBACK, 1.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, Attributes.MOVEMENT_SPEED, PAIN_SPEED, 0.18, Operation.ADD_MULTIPLIED_TOTAL);
      AttributeHelper.addPermanentAttribute(entity, Attributes.ATTACK_DAMAGE, PAIN_ATTACK, 0.25, Operation.ADD_MULTIPLIED_TOTAL);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.CHANT_SPEED, PAIN_CHANT, 1.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.PRESENCE_SENSE, PAIN_PRESENCE, 3.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.PRESENCE_SENSE_RADIUS, PAIN_PRESENCE_RADIUS, 48.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE, PAIN_PROJECTILE_DODGE, 100.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(
         entity, TensuraAttributes.AUTO_MELEE_DODGE_CHANCE, PAIN_MELEE_DODGE, instance.isMastered(entity) ? 45.0 : 25.0, Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.DODGE_STRENGTH, PAIN_DODGE_STRENGTH, 1.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.DODGE_INVULNERABILITY, PAIN_DODGE_INVULN, 12.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.DODGE_NEGATE_CHANCE, PAIN_DODGE_NEGATE, 100.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(
         entity, TensuraAttributes.RESISTANCE_DEGRADATION, PAIN_RESIST_DEGRADE, instance.isMastered(entity) ? 2.0 : 1.0, Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttribute(
         entity, TensuraAttributes.LAW_DEGRADATION, PAIN_LAW_DEGRADE, instance.isMastered(entity) ? 1.0 : 0.0, Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.GRAVITY_BOOST, PAIN_GRAVITY_BOOST, 0.75, Operation.ADD_MULTIPLIED_TOTAL);
      PainFlightSuppressionHandler.startNoFlyAura(entity);
      if (entity instanceof Player player) {
         player.displayClientMessage(
            Component.translatable("tensuramoreskills.skill.pain_lord_of_six_paths.toggle_on").withStyle(ChatFormatting.DARK_PURPLE), true
         );
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      this.removeToggleAttributes(entity);
      PainFlightSuppressionHandler.stopNoFlyAura(entity);
      if (entity instanceof Player player) {
         player.displayClientMessage(Component.translatable("tensuramoreskills.skill.pain_lord_of_six_paths.toggle_off").withStyle(ChatFormatting.GRAY), true);
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (mode == 0) {
         this.beginAlmightyPush(instance, entity);
      } else {
         switch (mode) {
            case 1:
               this.universalPull(instance, entity, mode);
               break;
            case 2:
               if (entity.isShiftKeyDown()) {
                  this.cyclePath(instance, entity);
               } else {
                  this.usePath(instance, entity, mode);
               }
               break;
            case 3:
               this.heavenlyRupture(instance, entity, mode);
               break;
            case 4:
               if (entity.isShiftKeyDown()) {
                  PainPlanetaryDevastationHandler.drop(entity, instance, this);
               } else {
                  this.planetaryDevastation(instance, entity, mode);
               }
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (mode != 0) {
         return false;
      }

      if (instance.onCoolDown(mode) && !instance.canIgnoreCoolDown(entity, mode)) {
         return false;
      }

      int maxTicks = (Integer)CONFIG.almightyPushMaxChargeTicks.get();
      int clamped = Mth.clamp(heldTicks, 0, maxTicks);
      CompoundTag tag = instance.getOrCreateTag();
      tag.putInt("AlmightyPushCharge", clamped);
      tag.putBoolean("AlmightyPushFlight", true);
      instance.markDirty();
      this.enableFlight(entity, tag);
      if (entity instanceof Player player && heldTicks % 20 == 0) {
         player.displayClientMessage(
            Component.translatable(
                  "tensuramoreskills.skill.pain_lord_of_six_paths.charge", new Object[]{String.format("%.1f", this.chargeRatio(clamped) * 100.0)}
               )
               .setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_PURPLE)),
            true
         );
      }

      if (heldTicks % (Integer)CONFIG.almightyPushChargeParticleIntervalTicks.get() == 0) {
         this.spawnAlmightyChargeParticles(entity, this.chargeRatio(clamped));
      }

      if (heldTicks > 0 && heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0) {
         instance.addMasteryPoint(entity);
      }

      return true;
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (mode == 0) {
         int rawCharge = Math.max(heldTicks, instance.getOrCreateTag().getInt("AlmightyPushCharge"));
         if (rawCharge < 40) {
            CompoundTag tag = instance.getOrCreateTag();
            tag.putInt("AlmightyPushCharge", 0);
            tag.putBoolean("AlmightyPushFlight", false);
            instance.markDirty();
            this.restoreFlight(instance, entity);
         } else {
            int chargeTicks = Mth.clamp(rawCharge, (Integer)CONFIG.almightyPushMinChargeTicks.get(), (Integer)CONFIG.almightyPushMaxChargeTicks.get());
            this.releaseAlmightyPush(instance, entity, mode, chargeTicks);
            this.restoreFlight(instance, entity);
         }
      }
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      PainPlanetaryDevastationHandler.cancel(entity, instance);
      PainPlanetaryDevastationHandler.clearPushDebris(entity);
      this.restoreFlight(instance, entity);
      PainFlightSuppressionHandler.stopNoFlyAura(entity);
      this.removeToggleAttributes(entity);
      super.onForgetSkill(instance, entity);
   }

   private void beginAlmightyPush(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.putInt("AlmightyPushCharge", 0);
      tag.putBoolean("AlmightyPushFlight", true);
      this.enableFlight(entity, tag);
      instance.markDirty();
   }

   private void releaseAlmightyPush(ManasSkillInstance instance, LivingEntity entity, int mode, int chargeTicks) {
      if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         double charge = this.chargeRatio(chargeTicks);
         boolean finalAoE = charge >= 0.985;
         double radius = finalAoE ? Mth.lerp(charge, 95.0, 135.0) : Mth.lerp(charge, 7.0, 22.0);
         double lineRange = Mth.lerp(charge, 42.0, 190.0);
         double lineWidth = Mth.lerp(charge, 5.0, 22.0);
         double damage = Mth.lerp(
            charge,
            (Double)CONFIG.almightyPushMinDamage.get() * 1.8,
            instance.isMastered(entity) ? (Double)CONFIG.almightyPushMaxDamageMastered.get() * 1.45 : (Double)CONFIG.almightyPushMaxDamage.get() * 1.35
         );
         double force = Mth.lerp(charge, 24.0, 98.0);
         DamageSource source = this.createSource(instance, entity, TensuraDamageTypes.GRAVITY_PRESS, mode);
         Vec3 origin = entity.position().add(0.0, entity.getBbHeight() * 0.5, 0.0);
         Vec3 look = entity.getLookAngle().normalize();
         AABB box = finalAoE ? entity.getBoundingBox().inflate(radius) : new AABB(origin, origin.add(look.scale(lineRange))).inflate(lineWidth + 4.0);

         for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, box, targetx -> this.validEnemy(entity, targetx))) {
            Vec3 targetCenter = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
            Vec3 dir = targetCenter.subtract(origin);
            double along = dir.dot(look);
            if (finalAoE || !(along < -2.0) && !(along > lineRange) && !(dir.subtract(look.scale(along)).length() > lineWidth)) {
               Vec3 pushDir = finalAoE ? dir : look.scale(1.0).add(dir.normalize().scale(0.18));
               if (pushDir.lengthSqr() < 0.01) {
                  pushDir = look;
               }

               double dist = finalAoE ? target.distanceTo(entity) : Math.max(1.0, along);
               double falloff = Math.max((Double)CONFIG.almightyPushMinFalloff.get(), 1.0 - dist / (finalAoE ? radius : lineRange));
               this.forceMove(
                  target,
                  entity,
                  instance,
                  pushDir.normalize().scale(force * falloff).add(0.0, ((Double)CONFIG.almightyPushVerticalLift.get() + charge * 4.0) * falloff, 0.0)
               );
               target.hurt(source, (float)(damage * falloff));
               target.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.BURDEN),
                     (Integer)CONFIG.almightyPushBurdenTicks.get(),
                     (Integer)CONFIG.almightyPushBurdenAmplifier.get() + (finalAoE ? 2 : 0),
                     true,
                     false,
                     true
                  )
               );
               target.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE),
                     (Integer)CONFIG.almightyPushStaggerTicks.get(),
                     (Integer)CONFIG.almightyPushStaggerAmplifier.get() + (finalAoE ? 2 : 0),
                     true,
                     false,
                     true
                  )
               );
            }
         }

         this.rejectProjectiles(
            entity,
            finalAoE ? radius : Math.max(lineWidth + 8.0, 18.0),
            (Double)CONFIG.almightyPushProjectileSpeed.get() + charge * 24.0,
            false,
            finalAoE,
            lineRange,
            lineWidth
         );
         if (entity.level() instanceof ServerLevel level) {
            PainPlanetaryDevastationHandler.flingAlmightyPushBlocks(
               level, entity, instance, this, finalAoE, finalAoE ? radius : lineRange, lineWidth, force, charge
            );
            if (finalAoE && PainPlanetaryDevastationHandler.canSkillGrief(level, instance, entity, entity.position())) {
               PainPlanetaryDevastationHandler.carveAlmightyCrater(level, entity, instance, this, radius, charge);
            }
         }

         this.spawnAlmightyReleaseParticles(entity, finalAoE, finalAoE ? radius : Math.min(lineRange, 90.0), lineWidth, charge);
         entity.level()
            .playSound(
               null,
               entity.getX(),
               entity.getY(),
               entity.getZ(),
               SoundEvents.GENERIC_EXPLODE,
               SoundSource.PLAYERS,
               (float)Mth.lerp(charge, 2.4, 7.5),
               (float)Mth.lerp(charge, 0.85, 0.18)
            );
         entity.swing(InteractionHand.MAIN_HAND, true);
         instance.addMasteryPoint(entity);
         instance.setCoolDown(finalAoE ? (Integer)CONFIG.almightyPushMaxCooldownTicks.get() : (Integer)CONFIG.almightyPushCooldownTicks.get(), mode);
         CompoundTag tag = instance.getOrCreateTag();
         tag.putInt("AlmightyPushCharge", 0);
         tag.putBoolean("AlmightyPushFlight", false);
         instance.markDirty();
      }
   }

   private void universalPull(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         boolean sphere = entity.isShiftKeyDown();
         double range = instance.isMastered(entity) ? (Double)CONFIG.universalPullRangeMastered.get() * 1.65 : (Double)CONFIG.universalPullRange.get() * 1.5;
         double lineWidth = 5.0 + (instance.isMastered(entity) ? 2.5 : 0.0);
         Vec3 anchor = entity.position().add(0.0, entity.getBbHeight() * 0.55, 0.0);
         Vec3 look = entity.getLookAngle().normalize();
         AABB box = sphere ? entity.getBoundingBox().inflate(range) : new AABB(anchor, anchor.add(look.scale(range))).inflate(lineWidth + 3.0);
         int pulled = 0;

         for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, box, targetx -> this.validEnemy(entity, targetx))) {
            Vec3 to = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0).subtract(anchor);
            if (!(to.lengthSqr() > range * range)) {
               if (!sphere) {
                  double along = to.dot(look);
                  double side = to.subtract(look.scale(along)).length();
                  if (along < -1.5 || along > range || side > lineWidth) {
                     continue;
                  }
               }

               this.pullTarget(instance, entity, target, mode);
               pulled++;
            }
         }

         if (entity.level() instanceof ServerLevel level) {
            PainPlanetaryDevastationHandler.pullUniversalBlocks(
               level,
               entity,
               instance,
               this,
               range,
               instance.isMastered(entity) ? (Double)CONFIG.universalPullForceMastered.get() * 4.2 : (Double)CONFIG.universalPullForce.get() * 4.0,
               sphere
            );
            this.spawnUniversalPullParticles(level, entity, range, lineWidth, sphere, pulled);
         }

         entity.level()
            .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_SPACE.get(), SoundSource.PLAYERS, 1.8F, 0.42F);
         entity.swing(InteractionHand.MAIN_HAND, true);
         instance.addMasteryPoint(entity);
         instance.setCoolDown(100, mode);
      }
   }

   private void pullTarget(ManasSkillInstance instance, LivingEntity entity, LivingEntity target, int mode) {
      Vec3 dir = entity.position().add(0.0, entity.getBbHeight() * 0.5, 0.0).subtract(target.position().add(0.0, target.getBbHeight() * 0.5, 0.0));
      if (!(dir.lengthSqr() < 0.01)) {
         double force = instance.isMastered(entity) ? (Double)CONFIG.universalPullForceMastered.get() * 4.8 : (Double)CONFIG.universalPullForce.get() * 4.4;
         double dist = Math.max(1.0, dir.length());
         double brake = dist < 3.0 ? 0.18 : 1.0;
         this.forceMove(target, entity, instance, dir.normalize().scale(force * brake).add(0.0, 0.18, 0.0));
         TensuraDamageHelper.markHurt(target, entity);
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), 80, 2, true, false, true));
         TensuraParticleHelper.addServerParticlesAroundSelf(target, ParticleTypes.PORTAL, 1.0);
      }
   }

   private void cyclePath(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      int next = (tag.getInt("PathIndex") + 1) % 6;
      tag.putInt("PathIndex", next);
      instance.markDirty();
      if (entity instanceof Player player) {
         player.displayClientMessage(
            Component.translatable(
                  "tensuramoreskills.skill.pain_lord_of_six_paths.path",
                  new Object[]{Component.translatable("tensuramoreskills.skill.pain_lord_of_six_paths.path." + this.pathName(next))}
               )
               .setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_PURPLE)),
            true
         );
      }

      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), SoundSource.PLAYERS, 0.8F, 1.2F);
   }

   private void usePath(ManasSkillInstance instance, LivingEntity entity, int mode) {
      switch (this.getPath(instance)) {
         case 0:
            this.pathDeva(instance, entity, mode);
            break;
         case 1:
            this.pathAsura(instance, entity, mode);
            break;
         case 2:
            this.pathHuman(instance, entity, mode);
            break;
         case 3:
            this.pathAnimal(instance, entity, mode);
            break;
         case 4:
            this.pathPreta(instance, entity, mode);
            break;
         case 5:
            this.pathNaraka(instance, entity, mode);
      }
   }

   private void pathDeva(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         double radius = (Double)CONFIG.pathDevaRadius.get();
         DamageSource source = this.createSource(instance, entity, TensuraDamageTypes.GRAVITY_PRESS, mode);

         for (LivingEntity target : entity.level()
            .getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius), targetx -> this.validEnemy(entity, targetx))) {
            Vec3 dir = target.position().subtract(entity.position());
            if (dir.lengthSqr() < 0.01) {
               dir = entity.getLookAngle();
            }

            this.forceMove(target, entity, instance, dir.normalize().scale((Double)CONFIG.pathDevaForce.get()).add(0.0, 0.2, 0.0));
            target.hurt(source, ((Double)CONFIG.pathDevaDamage.get()).floatValue());
         }

         TensuraParticleHelper.spawnServerParticles(
            entity.level(),
            TensuraParticleUtils.getRedWave(1.3F, (float)(radius * 1.7), -0.35F, true),
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.5,
            entity.getZ()
         );
         if (entity.level() instanceof ServerLevel level) {
            PainSixPathsVfxPayload.send(level, entity.position(), (float)(radius * 2.0), 20, 0.8F);
         }

         instance.setCoolDown((Integer)CONFIG.pathDevaCooldownTicks.get(), mode);
         entity.swing(InteractionHand.MAIN_HAND, true);
         instance.addMasteryPoint(entity);
      }
   }

   private void pathAsura(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         DamageSource source = this.createSource(instance, entity, TensuraDamageTypes.GRAVITY_EXPLODE, mode);
         Vec3 eye = entity.getEyePosition();
         Vec3 look = entity.getLookAngle().normalize();

         for (int i = 1; i <= CONFIG.pathAsuraShots.get(); i++) {
            Vec3 pos = eye.add(look.scale(i * (Double)CONFIG.pathAsuraShotSpacing.get()));
            TensuraParticleHelper.addServerParticlesAroundPos(entity.getRandom(), entity.level(), pos, ParticleTypes.FLASH, 1.0);
            TensuraParticleHelper.addServerParticlesAroundPos(entity.getRandom(), entity.level(), pos, ParticleTypes.FIREWORK, 1.0);
            AABB box = new AABB(pos, pos).inflate((Double)CONFIG.pathAsuraShotRadius.get());

            for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, box, targetx -> this.validEnemy(entity, targetx))) {
               target.hurt(source, ((Double)CONFIG.pathAsuraDamage.get()).floatValue());
               this.forceMove(target, entity, instance, look.scale((Double)CONFIG.pathAsuraForce.get()).add(0.0, 0.15, 0.0));
            }
         }

         if (entity.level() instanceof ServerLevel level) {
            PainSixPathsVfxPayload.send(level, entity.getEyePosition().add(entity.getLookAngle().scale(12.0)), 24.0F, 21, 0.9F);
         }

         entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.PLAYERS, 2.0F, 0.45F);
         instance.setCoolDown((Integer)CONFIG.pathAsuraCooldownTicks.get(), mode);
         entity.swing(InteractionHand.MAIN_HAND, true);
         instance.addMasteryPoint(entity);
      }
   }

   private void pathHuman(ManasSkillInstance instance, LivingEntity entity, int mode) {
      LivingEntity target = (LivingEntity)ObjectSelectionHelper.getTargetingEntity(LivingEntity.class, entity, (Double)CONFIG.pathHumanRange.get(), 0.15, false);
      if (target == null || !this.validEnemy(entity, target)) {
         this.fail(entity);
         instance.setCoolDown((Integer)CONFIG.failedTargetCooldownTicks.get(), mode);
      } else if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         DamageSource source = this.createSource(instance, entity, TensuraDamageTypes.SOUL_SCATTER, mode);
         double hpDamage = Math.min((Double)CONFIG.pathHumanMaxDamage.get(), target.getMaxHealth() * (Double)CONFIG.pathHumanHealthRatio.get());
         float soulDamage = (float)Math.max((Double)CONFIG.pathHumanMinDamage.get(), hpDamage);
         target.hurt(source, soulDamage);
         TensuraDamageHelper.directSpiritualHurt(target, entity, source, soulDamage * 0.5F);
         EnergyHelper.drainEnergy(target, entity, (Double)CONFIG.pathHumanEnergyDrain.get(), false, DrainType.EP, GainType.NORMAL);
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.SOUL_DRAIN),
               (Integer)CONFIG.pathHumanSoulDrainTicks.get(),
               (Integer)CONFIG.pathHumanSoulDrainAmplifier.get(),
               true,
               false,
               true
            )
         );
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.INSANITY), (Integer)CONFIG.pathHumanSoulDrainTicks.get(), 2, true, false, true
            )
         );
         if (entity.level() instanceof ServerLevel level) {
            PainSixPathsVfxPayload.send(level, target.position().add(0.0, target.getBbHeight() * 0.5, 0.0), 8.0F, 22, 1.0F);
         }

         entity.level()
            .playSound(
               null, target.getX(), target.getY(), target.getZ(), (SoundEvent)TensuraSoundEvents.DEBUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.4F, 0.35F
            );
         instance.setCoolDown((Integer)CONFIG.pathHumanCooldownTicks.get(), mode);
         entity.swing(InteractionHand.MAIN_HAND, true);
         instance.addMasteryPoint(entity);
      }
   }

   private void pathAnimal(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (entity.level() instanceof ServerLevel level) {
         if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            EntityType<?>[] types = new EntityType[]{
               (EntityType)MonsterEntityTypes.DIREWOLF.get(),
               (EntityType)MonsterEntityTypes.BARGHEST.get(),
               (EntityType)MonsterEntityTypes.BLADE_TIGER.get(),
               (EntityType)MonsterEntityTypes.PEGASUS.get(),
               (EntityType)MonsterEntityTypes.GIANT_BEAR.get(),
               (EntityType)MonsterEntityTypes.FEATHERED_SERPENT.get()
            };
            int count = instance.isMastered(entity) ? (Integer)CONFIG.pathAnimalSummonsMastered.get() : (Integer)CONFIG.pathAnimalSummons.get();
            LivingEntity target = (LivingEntity)ObjectSelectionHelper.getTargetingEntity(
               LivingEntity.class, entity, (Double)CONFIG.pathAnimalTargetRange.get(), 0.4, false
            );

            for (int i = 0; i < count; i++) {
               this.spawnAnimal(level, entity, target, types[i % types.length], i, count);
            }

            TensuraParticleHelper.spawnServerParticles(
               level, TensuraParticleUtils.getBlackAura(1.4F, 11.0F, -0.45F), entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ()
            );
            PainSixPathsVfxPayload.send(level, entity.position(), 18.0F, 23, 1.0F);
            entity.level()
               .playSound(
                  null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), SoundSource.PLAYERS, 1.6F, 0.55F
               );
            instance.setCoolDown((Integer)CONFIG.pathAnimalCooldownTicks.get(), mode);
            entity.swing(InteractionHand.MAIN_HAND, true);
            instance.addMasteryPoint(entity);
         }
      }
   }

   private void spawnAnimal(ServerLevel level, LivingEntity owner, LivingEntity target, EntityType<?> type, int index, int count) {
      if (type.create(level) instanceof Mob mob) {
         double angle = (Math.PI * 2) * index / Math.max(1, count);
         Vec3 pos = owner.position()
            .add(Math.cos(angle) * (Double)CONFIG.pathAnimalSpawnRadius.get(), 0.2, Math.sin(angle) * (Double)CONFIG.pathAnimalSpawnRadius.get());
         Vec3 ground = ObjectSelectionHelper.getNearestGround(pos, level, 8.0, owner);
         mob.moveTo(ground.x, ground.y + 1.0, ground.z, owner.getYRot(), 0.0F);
         if (target != null && this.validEnemy(owner, target)) {
            mob.setTarget(target);
         }

         IExistence existence = TensuraStorages.getExistenceFrom(mob);
         existence.setSummoner(owner.getUUID());
         existence.setSummonedSecond((Integer)CONFIG.pathAnimalDurationTicks.get() / 20);
         existence.setSummonedAbility(this, 2);
         existence.markDirty();
         mob.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_AURA),
               (Integer)CONFIG.pathAnimalDurationTicks.get(),
               (Integer)CONFIG.pathAnimalAuraAmplifier.get(),
               true,
               false,
               true
            )
         );
         level.addFreshEntity(mob);
         TensuraParticleHelper.addServerParticlesAroundSelf(mob, ParticleTypes.REVERSE_PORTAL, 1.0);
      }
   }

   private void pathPreta(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         CompoundTag tag = instance.getOrCreateTag();
         tag.putInt("PretaShieldTicks", (Integer)CONFIG.pathPretaDurationTicks.get());
         tag.putLong("PretaShieldEndTick", entity.level().getGameTime() + ((Integer)CONFIG.pathPretaDurationTicks.get()).intValue());
         instance.markDirty();
         this.rejectProjectiles(entity, (Double)CONFIG.pathPretaRadius.get(), (Double)CONFIG.pathPretaProjectileSpeed.get(), true);
         entity.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_BARRIER),
               (Integer)CONFIG.pathPretaDurationTicks.get(),
               (Integer)CONFIG.pathPretaBarrierAmplifier.get(),
               true,
               false,
               true
            )
         );
         entity.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE),
               (Integer)CONFIG.pathPretaDurationTicks.get(),
               (Integer)CONFIG.pathPretaMagicInterferenceAmplifier.get(),
               true,
               false,
               true
            )
         );
         TensuraParticleHelper.spawnServerParticles(
            entity.level(),
            TensuraParticleUtils.getColorlessWave(1.1F, (float)((Double)CONFIG.pathPretaRadius.get() * 1.8), 0.2F, true),
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.5,
            entity.getZ()
         );
         if (entity.level() instanceof ServerLevel level) {
            PainSixPathsVfxPayload.send(level, entity.position(), (float)((Double)CONFIG.pathPretaRadius.get() * 2.0), 24, 1.0F);
         }

         instance.setCoolDown((Integer)CONFIG.pathPretaCooldownTicks.get(), mode);
         entity.swing(InteractionHand.MAIN_HAND, true);
         instance.addMasteryPoint(entity);
      }
   }

   private void tickPretaShield(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      int ticks = tag.getInt("PretaShieldTicks");
      if (ticks > 0) {
         long endTick = tag.getLong("PretaShieldEndTick");
         if (endTick > 0L && entity.level().getGameTime() > endTick) {
            tag.remove("PretaShieldTicks");
            tag.remove("PretaShieldEndTick");
            instance.markDirty();
         } else {
            tag.putInt("PretaShieldTicks", --ticks);
            if (ticks % (Integer)CONFIG.pathPretaProjectileAbsorbIntervalTicks.get() == 0) {
               this.rejectProjectiles(entity, (Double)CONFIG.pathPretaRadius.get(), (Double)CONFIG.pathPretaProjectileSpeed.get(), true);
            }

            if (ticks <= 0) {
               tag.remove("PretaShieldTicks");
               tag.remove("PretaShieldEndTick");
            }

            instance.markDirty();
         }
      }
   }

   private void pathNaraka(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         double heal = Math.max((Double)CONFIG.pathNarakaMinHeal.get(), entity.getMaxHealth() * (Double)CONFIG.pathNarakaHealRatio.get());
         entity.heal((float)heal);
         EnergyHelper.gainMagicule(entity, (Double)CONFIG.pathNarakaMagiculeHeal.get(), GainType.NORMAL);
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         existence.setSpiritualHealth(existence.getSpiritualHealth() + (Double)CONFIG.pathNarakaSpiritualHeal.get());
         existence.markDirty();
         entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.BURDEN));
         entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.CURSE));
         entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.FEAR));
         entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.INSANITY));
         entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.MAGICULE_POISON));
         entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.SOUL_DRAIN));
         entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.SILENCE));
         entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE));
         entity.level()
            .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_HEAL.get(), SoundSource.PLAYERS, 1.0F, 0.7F);
         if (entity.level() instanceof ServerLevel level) {
            PainSixPathsVfxPayload.send(level, entity.position(), 14.0F, 25, 1.0F);
         }

         TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.SOUL_FIRE_FLAME, 1.8);
         instance.setCoolDown((Integer)CONFIG.pathNarakaCooldownTicks.get(), mode);
         entity.swing(InteractionHand.MAIN_HAND, true);
         instance.addMasteryPoint(entity);
      }
   }

   private void heavenlyRupture(ManasSkillInstance instance, LivingEntity entity, int mode) {
      LivingEntity target = (LivingEntity)ObjectSelectionHelper.getTargetingEntity(
         LivingEntity.class, entity, (Double)CONFIG.heavenlyRuptureRange.get(), 0.2, false
      );
      if (target == null || !this.validEnemy(entity, target)) {
         this.fail(entity);
         instance.setCoolDown((Integer)CONFIG.failedTargetCooldownTicks.get(), mode);
      } else if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         DamageSource source = this.createSource(instance, entity, TensuraDamageTypes.GRAVITY_EXPLODE, mode);
         target.hurt(
            source,
            instance.isMastered(entity)
               ? ((Double)CONFIG.heavenlyRuptureDamageMastered.get()).floatValue()
               : ((Double)CONFIG.heavenlyRuptureDamage.get()).floatValue()
         );
         this.forceMove(
            target,
            entity,
            instance,
            new Vec3(0.0, instance.isMastered(entity) ? (Double)CONFIG.heavenlyRuptureLaunchMastered.get() : (Double)CONFIG.heavenlyRuptureLaunch.get(), 0.0)
         );
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.BURDEN),
               (Integer)CONFIG.heavenlyRuptureBurdenTicks.get(),
               (Integer)CONFIG.heavenlyRuptureBurdenAmplifier.get(),
               true,
               false,
               true
            )
         );
         CompoundTag tag = instance.getOrCreateTag();
         tag.putUUID("RuptureTarget", target.getUUID());
         tag.putInt("RuptureDropTicks", (Integer)CONFIG.heavenlyRuptureDropDelayTicks.get());
         instance.markDirty();
         entity.level().playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0F, 0.9F);
         TensuraParticleHelper.addServerParticlesAroundSelf(target, ParticleTypes.EXPLOSION_EMITTER, 1.0);
         instance.setCoolDown((Integer)CONFIG.heavenlyRuptureCooldownTicks.get(), mode);
         entity.swing(InteractionHand.MAIN_HAND, true);
         instance.addMasteryPoint(entity);
      }
   }

   private void planetaryDevastation(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (PainPlanetaryDevastationHandler.hasActive(entity)) {
         if (entity instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("tensuramoreskills.skill.pain_lord_of_six_paths.planet_active").withStyle(ChatFormatting.RED), true
            );
         }
      } else if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         PainPlanetaryDevastationHandler.start(entity, instance, this);
         instance.setCoolDown((Integer)CONFIG.planetaryDevastationCooldownTicks.get(), mode);
         entity.swing(InteractionHand.MAIN_HAND, true);
         instance.addMasteryPoint(entity);
      }
   }

   private void tickHeavenlyRupture(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      if (tag.hasUUID("RuptureTarget")) {
         int ticks = tag.getInt("RuptureDropTicks") - 1;
         tag.putInt("RuptureDropTicks", ticks);
         if (ticks > 0) {
            instance.markDirty();
         } else {
            if (entity.level() instanceof ServerLevel level) {
               UUID id = tag.getUUID("RuptureTarget");
               if (level.getEntity(id) instanceof LivingEntity target && target.isAlive()) {
                  DamageSource source = this.createSource(instance, entity, TensuraDamageTypes.GRAVITY_EXPLODE, 3);
                  this.forceMove(target, entity, instance, new Vec3(0.0, -(Double)CONFIG.heavenlyRuptureLaunch.get() * 1.65, 0.0));
                  target.hurt(source, (float)((Double)CONFIG.heavenlyRuptureDamage.get() * 0.55));

                  for (LivingEntity nearby : level.getEntitiesOfClass(
                     LivingEntity.class, target.getBoundingBox().inflate(5.5), other -> this.validEnemy(entity, other)
                  )) {
                     nearby.hurt(source, (float)((Double)CONFIG.heavenlyRuptureDamage.get() * 0.35));
                     Vec3 dir = nearby.position().subtract(target.position());
                     if (dir.lengthSqr() > 0.01) {
                        this.forceMove(nearby, entity, instance, dir.normalize().scale(1.6).add(0.0, 0.55, 0.0));
                     }
                  }

                  level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.2F, 0.5F);
                  TensuraParticleHelper.spawnServerParticles(
                     level, TensuraParticleUtils.getRedWave(1.0F, 7.5F, -0.3F, true), target.getX(), target.getY() + target.getBbHeight() * 0.5, target.getZ()
                  );
               }
            }

            tag.remove("RuptureTarget");
            tag.remove("RuptureDropTicks");
            instance.markDirty();
         }
      }
   }

   private void removeToggleAttributes(LivingEntity entity) {
      AttributeHelper.removeAttribute(entity, Attributes.KNOCKBACK_RESISTANCE, PAIN_KNOCKBACK);
      AttributeHelper.removeAttribute(entity, Attributes.MOVEMENT_SPEED, PAIN_SPEED);
      AttributeHelper.removeAttribute(entity, Attributes.ATTACK_DAMAGE, PAIN_ATTACK);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.CHANT_SPEED, PAIN_CHANT);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.PRESENCE_SENSE, PAIN_PRESENCE);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.PRESENCE_SENSE_RADIUS, PAIN_PRESENCE_RADIUS);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE, PAIN_PROJECTILE_DODGE);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.AUTO_MELEE_DODGE_CHANCE, PAIN_MELEE_DODGE);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.DODGE_STRENGTH, PAIN_DODGE_STRENGTH);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.DODGE_INVULNERABILITY, PAIN_DODGE_INVULN);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.DODGE_NEGATE_CHANCE, PAIN_DODGE_NEGATE);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.RESISTANCE_DEGRADATION, PAIN_RESIST_DEGRADE);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.LAW_DEGRADATION, PAIN_LAW_DEGRADE);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.GRAVITY_BOOST, PAIN_GRAVITY_BOOST);
   }

   private void enableFlight(LivingEntity entity, CompoundTag tag) {
      tag.putBoolean("AlmightyPushFlight", true);
      PainFlightSuppressionHandler.startAlmightyPushFlight(entity);
   }

   private void tickAlmightyPushHover(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      if (tag.getBoolean("AlmightyPushFlight")) {
         PainFlightSuppressionHandler.startAlmightyPushFlight(entity);
         entity.resetFallDistance();
      }
   }

   private void restoreFlight(ManasSkillInstance instance, LivingEntity entity) {
      PainFlightSuppressionHandler.stopAlmightyPushFlight(entity);
      CompoundTag tag = instance.getOrCreateTag();
      tag.remove("AlmightyPushFlight");
      tag.remove("AlmightyPushFlightStored");
      tag.remove("AlmightyPushHadNoGravity");
      tag.remove("AlmightyPushHadMayfly");
      tag.remove("AlmightyPushHadFlying");
      instance.markDirty();
   }

   private double chargeRatio(int ticks) {
      return Mth.clamp((double)ticks / ((Integer)CONFIG.almightyPushMaxChargeTicks.get()).intValue(), (Double)CONFIG.almightyPushMinChargeRatio.get(), 1.0);
   }

   private double pathCost(int path) {
      return switch (path) {
         case 0 -> CONFIG.pathDevaCost.get();
         case 1 -> CONFIG.pathAsuraCost.get();
         case 2 -> CONFIG.pathHumanCost.get();
         case 3 -> CONFIG.pathAnimalCost.get();
         case 4 -> CONFIG.pathPretaCost.get();
         case 5 -> CONFIG.pathNarakaCost.get();
         default -> 0.0;
      };
   }

   private int getPath(ManasSkillInstance instance) {
      return Math.floorMod(instance.getOrCreateTag().getInt("PathIndex"), 6);
   }

   private String pathName(int path) {
      return switch (path) {
         case 0 -> "deva";
         case 1 -> "asura";
         case 2 -> "human";
         case 3 -> "animal";
         case 4 -> "preta";
         case 5 -> "naraka";
         default -> "deva";
      };
   }

   private boolean validEnemy(LivingEntity owner, LivingEntity target) {
      if (target == owner) {
         return false;
      } else if (!target.isAlive()) {
         return false;
      } else {
         return target.isAlliedTo(owner) ? false : !(target instanceof Player player && player.getAbilities().invulnerable);
      }
   }

   private double safeViewDot(LivingEntity entity, LivingEntity target) {
      Vec3 to = target.position().subtract(entity.position());
      return to.lengthSqr() < 0.01 ? 1.0 : entity.getLookAngle().normalize().dot(to.normalize());
   }

   private void rejectProjectiles(LivingEntity entity, double radius, double speed, boolean absorb) {
      this.rejectProjectiles(entity, radius, speed, absorb, true, radius, radius);
   }

   private void rejectProjectiles(LivingEntity entity, double radius, double speed, boolean absorb, boolean aoe, double lineRange, double lineWidth) {
      Vec3 origin = entity.position().add(0.0, entity.getBbHeight() * 0.55, 0.0);
      Vec3 look = entity.getLookAngle().normalize();
      AABB box = aoe ? entity.getBoundingBox().inflate(radius) : new AABB(origin, origin.add(look.scale(lineRange))).inflate(lineWidth + 4.0);

      for (Projectile projectile : entity.level()
         .getEntitiesOfClass(Projectile.class, box, projectilex -> projectilex.isAlive() && projectilex.getOwner() != entity)) {
         Vec3 rel = projectile.position().subtract(origin);
         if (!aoe) {
            double along = rel.dot(look);
            double side = rel.subtract(look.scale(along)).length();
            if (along < -2.0 || along > lineRange || side > lineWidth + 3.0) {
               continue;
            }
         }

         if (absorb) {
            projectile.discard();
            EnergyHelper.gainMagicule(entity, (Double)CONFIG.pathPretaProjectileMagiculeGain.get(), GainType.NORMAL);
         } else {
            Vec3 dir = aoe ? rel : look.scale(1.0).add(rel.normalize().scale(0.08));
            if (dir.lengthSqr() < 0.01) {
               dir = look;
            }

            projectile.setDeltaMovement(dir.normalize().scale(speed));
            projectile.hurtMarked = true;
         }
      }
   }

   private void forceMove(LivingEntity target, LivingEntity owner, ManasSkillInstance instance, Vec3 motion) {
      if (!target.getType().is(TensuraEntityTags.NO_FORCED_MOVE)) {
         Changeable<Vec3> changeable = Changeable.of(motion);
         if (!((ForceMovementEvent)TensuraEntityEvents.FORCE_MOVEMENT_EVENT.invoker()).move(target, owner, instance, changeable).isFalse()) {
            target.setDeltaMovement((Vec3)changeable.get());
         } else {
            target.setDeltaMovement(motion);
         }

         target.fallDistance = 0.0F;
         target.hurtMarked = true;
      }
   }

   private void spawnAlmightyChargeParticles(LivingEntity entity, double charge) {
      double spread = Mth.lerp(charge, 0.8, 6.0);
      if (entity.level() instanceof ServerLevel level) {
         level.sendParticles(
            ParticleTypes.CLOUD,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.5,
            entity.getZ(),
            (int)Mth.lerp(charge, 8.0, 80.0),
            spread,
            spread * 0.3,
            spread,
            0.04
         );
         level.sendParticles(
            ParticleTypes.GUST,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.5,
            entity.getZ(),
            (int)Mth.lerp(charge, 2.0, 24.0),
            spread * 0.5,
            spread * 0.2,
            spread * 0.5,
            0.05
         );
         level.sendParticles(
            ParticleTypes.REVERSE_PORTAL,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.5,
            entity.getZ(),
            (int)Mth.lerp(charge, 4.0, 50.0),
            spread * 0.6,
            spread * 0.4,
            spread * 0.6,
            0.03
         );
      }

      TensuraParticleHelper.addServerAuraParticles(entity, TensuraParticleUtils.getBlackAura(0.6F, (float)(2.0 + charge * 12.0), -0.4F), 6, 0.01, 2.0F);
   }

   private void spawnAlmightyReleaseParticles(LivingEntity entity, boolean finalAoE, double rangeOrRadius, double lineWidth, double charge) {
      if (entity.level() instanceof ServerLevel level) {
         Vec3 var19 = entity.position().add(0.0, entity.getBbHeight() * 0.55, 0.0);
         Vec3 look = entity.getLookAngle().normalize();
         if (finalAoE) {
            TensuraParticleHelper.spawnServerParticles(
               entity.level(),
               TensuraParticleUtils.getColorlessWave(1.0F, (float)rangeOrRadius, -0.6F, true),
               entity.getX(),
               entity.getY() + entity.getBbHeight() * 0.5,
               entity.getZ()
            );
            level.sendParticles(
               ParticleTypes.GUST_EMITTER_LARGE, var19.x, var19.y, var19.z, 4, rangeOrRadius * 0.08, rangeOrRadius * 0.04, rangeOrRadius * 0.08, 0.1
            );
            level.sendParticles(
               ParticleTypes.EXPLOSION_EMITTER, var19.x, var19.y, var19.z, 2, rangeOrRadius * 0.08, rangeOrRadius * 0.04, rangeOrRadius * 0.08, 0.0
            );
            level.sendParticles(ParticleTypes.CLOUD, var19.x, var19.y, var19.z, 180, rangeOrRadius * 0.34, rangeOrRadius * 0.12, rangeOrRadius * 0.34, 0.28);
            level.sendParticles(ParticleTypes.POOF, var19.x, var19.y, var19.z, 120, rangeOrRadius * 0.3, rangeOrRadius * 0.1, rangeOrRadius * 0.3, 0.22);
         } else {
            int steps = 18;

            for (int i = 1; i <= steps; i++) {
               double d = rangeOrRadius * i / steps;
               Vec3 pos = var19.add(look.scale(d));
               double spread = lineWidth * (0.25 + (double)i / steps * 0.75);
               level.sendParticles(ParticleTypes.CLOUD, pos.x, pos.y, pos.z, 10, spread, spread * 0.22, spread, 0.18 + charge * 0.1);
               level.sendParticles(ParticleTypes.POOF, pos.x, pos.y, pos.z, 6, spread * 0.7, spread * 0.15, spread * 0.7, 0.12);
               level.sendParticles(ParticleTypes.GUST, pos.x, pos.y, pos.z, 4, spread * 0.35, spread * 0.12, spread * 0.35, 0.08);
            }

            level.sendParticles(ParticleTypes.GUST_EMITTER_LARGE, var19.x + look.x * 4.0, var19.y, var19.z + look.z * 4.0, 2, 1.0, 0.4, 1.0, 0.0);
         }
      }
   }

   private void spawnUniversalPullParticles(ServerLevel level, LivingEntity entity, double range, double lineWidth, boolean sphere, int pulled) {
      Vec3 anchor = entity.position().add(0.0, entity.getBbHeight() * 0.55, 0.0);
      Vec3 look = entity.getLookAngle().normalize();
      if (sphere) {
         for (int i = 0; i < 10; i++) {
            double r = range * (0.15 + i * 0.075);
            level.sendParticles(ParticleTypes.PORTAL, anchor.x, anchor.y, anchor.z, 35, r, r * 0.35, r, -0.08);
            level.sendParticles(ParticleTypes.REVERSE_PORTAL, anchor.x, anchor.y, anchor.z, 20, r * 0.8, r * 0.25, r * 0.8, 0.02);
         }
      } else {
         int steps = 16;

         for (int i = 1; i <= steps; i++) {
            Vec3 pos = anchor.add(look.scale(range * i / steps));
            double spread = lineWidth * (0.5 + (double)i / steps * 0.5);
            level.sendParticles(ParticleTypes.PORTAL, pos.x, pos.y, pos.z, 18, spread, spread * 0.18, spread, -0.08);
            level.sendParticles(ParticleTypes.REVERSE_PORTAL, pos.x, pos.y, pos.z, 8, spread * 0.65, spread * 0.12, spread * 0.65, 0.02);
         }
      }

      level.sendParticles(ParticleTypes.SMOKE, anchor.x, anchor.y, anchor.z, Math.max(18, pulled * 3), 1.4, 0.7, 1.4, 0.08);
   }

   public DamageSource createSource(ManasSkillInstance instance, LivingEntity entity, ResourceKey<DamageType> type, int mode) {
      return TensuraDamageTypes.getEntityDamageSource(entity.level(), type, entity);
   }

   private void fail(LivingEntity entity) {
      if (entity instanceof Player player) {
         player.displayClientMessage(Component.translatable("tensura.targeting.not_targeted").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), true);
      }

      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
   }
}
