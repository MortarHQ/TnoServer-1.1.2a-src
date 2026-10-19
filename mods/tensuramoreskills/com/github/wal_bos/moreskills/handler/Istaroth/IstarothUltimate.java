package com.github.wal_bos.moreskills.handler.Istaroth;

import com.github.wal_bos.moreskills.ability.skill.shades.IstarothSkill;
import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.registry.effect.ModMobEffects;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.entity.variant.MagicCircleVariant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public final class IstarothUltimate {
   private static final MoreSkillsConfigs.IstarothConfig CONFIG = MoreSkillsConfigs.GRAND.istaroth;
   private static final Map<UUID, Integer> WORLD_STOP = new HashMap<>();
   private static final Map<UUID, Integer> WORLD_STOP_HELD = new HashMap<>();
   private static final Map<UUID, Integer> LAST_SECOND = new HashMap<>();
   private static final Map<UUID, IstarothUltimate.EventSilenceState> EVENT_SILENCE = new HashMap<>();
   private static final Map<UUID, IstarothUltimate.FutureRejectionState> FUTURE_REJECTION = new HashMap<>();
   private static final Map<UUID, IstarothUltimate.TimelineVerdictState> TIMELINE_VERDICT = new HashMap<>();
   private static final Map<UUID, Float> FROZEN_WORLD_FORCE = new HashMap<>();
   private static final Map<UUID, Float> LAST_SECOND_FORCE = new HashMap<>();

   private IstarothUltimate() {
   }

   private static double d(DoubleValue value) {
      return (Double)value.get();
   }

   private static int i(IntValue value) {
      return (Integer)value.get();
   }

   private static float f(DoubleValue value) {
      return ((Double)value.get()).floatValue();
   }

   private static float f(IntValue value) {
      return ((Integer)value.get()).floatValue();
   }

   public static void init() {
      IstarothDoomTornado.init();
   }

   public static boolean hasActiveState(LivingEntity entity) {
      UUID id = entity.getUUID();
      return WORLD_STOP.containsKey(id)
         || WORLD_STOP_HELD.containsKey(id)
         || LAST_SECOND.containsKey(id)
         || EVENT_SILENCE.containsKey(id)
         || FUTURE_REJECTION.containsKey(id)
         || TIMELINE_VERDICT.containsKey(id)
         || IstarothDoomTornado.hasTornado(id);
   }

   public static boolean isHoldingWorldStop(LivingEntity entity) {
      return WORLD_STOP.containsKey(entity.getUUID());
   }

   public static boolean isLastSecondActive(LivingEntity entity) {
      return LAST_SECOND.containsKey(entity.getUUID()) || IstarothDoomTornado.hasTornado(entity.getUUID());
   }

   public static void clearOwner(LivingEntity entity) {
      UUID id = entity.getUUID();
      WORLD_STOP.remove(id);
      WORLD_STOP_HELD.remove(id);
      LAST_SECOND.remove(id);
      FROZEN_WORLD_FORCE.remove(id);
      LAST_SECOND_FORCE.remove(id);
      EVENT_SILENCE.entrySet().removeIf(entry -> id.equals(entry.getValue().owner));
      FUTURE_REJECTION.entrySet().removeIf(entry -> id.equals(entry.getValue().owner));
      TIMELINE_VERDICT.entrySet().removeIf(entry -> id.equals(entry.getValue().owner));
      IstarothDoomTornado.clear(id);
      if (entity.level() instanceof ServerLevel level) {
         cleanupFinalSecondAfterEnd(level, id, entity.position(), i(CONFIG.lastSecondRadius) + 120.0, true);
      }
   }

   public static void clearNonFinalOwner(LivingEntity entity) {
      UUID id = entity.getUUID();
      WORLD_STOP.remove(id);
      WORLD_STOP_HELD.remove(id);
      FROZEN_WORLD_FORCE.remove(id);
   }

   public static void tick(IstarothSkill skill, ManasSkillInstance instance, LivingEntity owner) {
      if (!owner.level().isClientSide()) {
         if (owner.level() instanceof ServerLevel level) {
            IstarothDoomTornado.tick(level);
         }

         tickWorldStopTimer(owner);
         tickEventSilence(owner.level());
         tickFutureRejection(owner.level());
         tickTimelineVerdict(owner.level());
         tickLastSecond(skill, instance, owner);
      }
   }

   public static void worldOfStillSeconds(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity, int heldTicks) {
      if (entity.level() instanceof ServerLevel level) {
         UUID var16 = entity.getUUID();
         int radius = instance.isMastered(entity) ? i(CONFIG.worldStopRadiusMastered) : i(CONFIG.worldStopRadius);
         WORLD_STOP.put(var16, 14);
         WORLD_STOP_HELD.put(var16, heldTicks);
         if (heldTicks == 0 || heldTicks % 6 == 0) {
            IstarothFx.clockCircle(entity, radius, 30, MagicCircleVariant.SPACE);
         }

         if (heldTicks == 0 || heldTicks % 18 == 0) {
            IstarothFx.overheadClock(entity, radius * 0.62F, 40);
            IstarothFx.timeStopScreen(entity, radius, 42, 3);
            IstarothFx.sound(entity, SoundEvents.BEACON_AMBIENT, 0.65F, 0.25F);
            IstarothFx.cameraShake(entity, radius + 4.0, 0.035F, 7);
         }

         AABB area = entity.getBoundingBox().inflate(radius);
         int frozenProjectiles = 0;

         for (Projectile projectile : level.getEntitiesOfClass(Projectile.class, area, p -> p.getOwner() != entity)) {
            float force = (float)Math.min(140.0, projectile.getDeltaMovement().length() * 28.0 + projectile.getBbWidth() * 18.0 + 14.0);
            FROZEN_WORLD_FORCE.put(var16, FROZEN_WORLD_FORCE.getOrDefault(var16, 0.0F) + force);
            IstarothPassives.freezeProjectileHard(projectile, entity, i(CONFIG.worldStopProjectileFreezeTicks), true);
            IstarothFx.frozenProjectileAura(projectile);
            frozenProjectiles++;
         }

         for (FallingBlockEntity falling : level.getEntitiesOfClass(FallingBlockEntity.class, area)) {
            falling.setDeltaMovement(Vec3.ZERO);
            falling.hurtMarked = true;
            IstarothFx.grayPulse(falling, 1.0F);
         }

         int frozenTargets = 0;

         for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area, e -> IstarothCommon.validEnemy(entity, e))) {
            double resistance = IstarothCommon.resistanceFactor(target, entity);
            int amplifier = resistance > 0.85 ? 2 : (resistance > 0.65 ? 3 : 5);
            target.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.WORLD_OF_STILL_SECONDS), 60, amplifier, false, false, true));
            target.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), 70, 2, false, false, true));
            target.setDeltaMovement(target.getDeltaMovement().scale(0.006 + resistance * 0.16));
            target.hurtMarked = true;
            applyEventSilence(entity, target, 55, amplifier);
            applyFutureRejection(entity, target, 55, Math.max(2, amplifier - 1));
            if (heldTicks % 16 == 0) {
               float debt = instance.isMastered(entity) ? 38.0F : 24.0F;
               IstarothCommon.addTemporalDebt(target, debt, var16);
               FROZEN_WORLD_FORCE.put(var16, FROZEN_WORLD_FORCE.getOrDefault(var16, 0.0F) + debt);
               decayBeneficialEffectsHard(target, 60);
               IstarothFx.clockDust(target, 1.8F, 24);
            }

            if (heldTicks % 12 == 0) {
               IstarothFx.verdictClock(target, Math.max(2.3F, target.getBbWidth() * 3.4F), 28);
            }

            frozenTargets++;
         }

         if (heldTicks % 40 == 0 && heldTicks > 0) {
            entity.sendSystemMessage(
               Component.literal("World of Still Seconds | Frozen targets: " + frozenTargets + " | Frozen projectiles: " + frozenProjectiles)
                  .withStyle(ChatFormatting.GRAY)
            );
         }
      }
   }

   public static void clockbreakRelease(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         UUID var20 = entity.getUUID();
         int held = WORLD_STOP_HELD.getOrDefault(var20, 0);
         boolean wasHolding = WORLD_STOP.containsKey(var20) || held > 0;
         WORLD_STOP.remove(var20);
         WORLD_STOP_HELD.remove(var20);
         if (!wasHolding) {
            entity.sendSystemMessage(Component.literal("No stopped world exists to release. Hold the skill first.").withStyle(ChatFormatting.DARK_GRAY));
         } else {
            int radius = instance.isMastered(entity) ? i(CONFIG.worldStopRadiusMastered) + 4 : i(CONFIG.worldStopRadius) + 4;
            float frozenForce = FROZEN_WORLD_FORCE.getOrDefault(var20, 0.0F);
            FROZEN_WORLD_FORCE.remove(var20);
            DamageSource source = IstarothCommon.damageSource(entity);
            AABB area = entity.getBoundingBox().inflate(radius);
            int hit = 0;
            int projectileCount = 0;

            for (Projectile projectile : level.getEntitiesOfClass(Projectile.class, area, p -> p.getPersistentData().getBoolean("IstarothFrozenProjectile"))) {
               projectileCount++;
               Vec3 away = projectile.position().subtract(entity.position());
               if (away.lengthSqr() < 0.01) {
                  away = entity.getLookAngle();
               }

               projectile.setNoGravity(false);
               projectile.setDeltaMovement(away.normalize().scale(2.4 + Math.min(3.2, held * 0.016)));
               projectile.hurtMarked = true;
               projectile.getPersistentData().remove("IstarothFrozenProjectile");
               IstarothFx.clockShatter(projectile, 1.6F);
            }

            for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area, e -> IstarothCommon.validEnemy(entity, e))) {
               float debt = IstarothCommon.consumeDebt(target, var20);
               float heldPower = Math.min(
                  f(CONFIG.clockbreakHeldPowerCap),
                  held * (instance.isMastered(entity) ? f(CONFIG.clockbreakHeldPowerMultiplierMastered) : f(CONFIG.clockbreakHeldPowerMultiplier))
               );
               float projectilePower = Math.min(f(CONFIG.clockbreakProjectilePowerCap), frozenForce * f(CONFIG.clockbreakProjectilePowerMultiplier));
               float maxDamage = target instanceof Player
                  ? target.getMaxHealth() * f(CONFIG.clockbreakPlayerMaxHealthDamage)
                  : target.getMaxHealth() * f(CONFIG.clockbreakMobMaxHealthDamage);
               float damage = (instance.isMastered(entity) ? f(CONFIG.clockbreakBaseDamageMastered) : f(CONFIG.clockbreakBaseDamage))
                  + debt
                  + heldPower
                  + projectilePower
                  + maxDamage;
               if (target instanceof Player) {
                  damage *= f(CONFIG.clockbreakPlayerDamageMultiplier);
               }

               target.invulnerableTime = 0;
               target.hurt(source, damage);
               target.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), 160, 3, false, false, true));
               target.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.EVENT_SILENCE), 120, 3, false, false, true));
               target.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.FUTURE_REJECTION), 120, 3, false, false, true));
               applyEventSilence(entity, target, 100, 4);
               applyFutureRejection(entity, target, 100, 4);
               decayBeneficialEffectsHard(target, 120);
               IstarothFx.causalityPillar(target, Math.max(2.8F, target.getBbWidth() * 3.7F), 18);
               IstarothFx.clockShatter(target, 4.6F);
               hit++;
            }

            Vec3 acceleration = entity.getLookAngle().normalize().scale(Math.min(4.8, 1.0 + hit * 0.22 + projectileCount * 0.13));
            entity.setDeltaMovement(entity.getDeltaMovement().add(acceleration));
            entity.hurtMarked = true;
            IstarothFx.finalCollapseScreen(entity, radius);
            IstarothFx.finalRuptureExplosion(entity, radius * 0.72F);
            IstarothFx.clockShatter(entity, radius);
            IstarothFx.cameraShake(entity, radius + 14.0, 0.14F, 28);
            entity.sendSystemMessage(Component.literal("Clockbreak Release shattered " + hit + " timelines.").withStyle(ChatFormatting.DARK_GRAY));
         }
      }
   }

   public static void lastSecondOfCreation(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         UUID var11 = entity.getUUID();
         if (!LAST_SECOND.containsKey(var11) && !IstarothDoomTornado.hasTornado(var11)) {
            int ticks = i(CONFIG.lastSecondTicks);
            int radius = i(CONFIG.lastSecondRadius);
            LAST_SECOND.put(var11, ticks);
            LAST_SECOND_FORCE.put(var11, 0.0F);
            entity.sendSystemMessage(Component.literal("Istaroth declares the final second.").withStyle(ChatFormatting.GRAY));
            entity.sendSystemMessage(Component.literal("The future will collapse in thirty seconds.").withStyle(ChatFormatting.DARK_GRAY));
            Vec3 tornadoCenter = new Vec3(Mth.floor(entity.getX()) + 0.5, entity.getY(), Mth.floor(entity.getZ()) + 0.5);
            IstarothDoomTornado.start(level, var11, tornadoCenter, ticks, radius);
            IstarothFx.timeStopScreen(entity, radius, ticks, 6);
            IstarothFx.finalClock(entity, radius, ticks);
            IstarothFx.finalChargeScreen(entity, radius);
            IstarothFx.grayPulse(entity, radius);
            IstarothFx.cameraShake(entity, radius + 16.0, 0.1F, 34);
            IstarothFx.sound(entity, SoundEvents.WARDEN_SONIC_CHARGE, 2.3F, 0.13F);
            AABB area = entity.getBoundingBox().inflate(radius);

            for (Projectile projectile : level.getEntitiesOfClass(Projectile.class, area, p -> p.getOwner() != entity)) {
               addLastSecondForce(var11, projectile.getDeltaMovement().length() * 42.0 + 24.0);
               IstarothPassives.freezeProjectileHard(projectile, entity, ticks, true);
               IstarothFx.frozenProjectileAura(projectile);
            }

            for (FallingBlockEntity falling : level.getEntitiesOfClass(FallingBlockEntity.class, area)) {
               falling.setDeltaMovement(Vec3.ZERO);
               falling.hurtMarked = true;
               addLastSecondForce(var11, 26.0);
               IstarothFx.grayPulse(falling, 1.5F);
            }

            for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area, e -> IstarothCommon.validEnemy(entity, e))) {
               applyEventSilence(entity, target, ticks, 5);
               applyFutureRejection(entity, target, ticks, 5);
               target.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.EVENT_SILENCE), ticks, 5, false, false, true));
               target.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.FUTURE_REJECTION), ticks, 5, false, false, true));
               target.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.WORLD_OF_STILL_SECONDS), 220, 4, false, false, true));
               target.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), 220, 4, false, false, true));
               decayBeneficialEffectsHard(target, 320);
               IstarothCommon.addTemporalDebt(target, 190.0F, var11);
               addLastSecondForce(var11, 130.0);
               IstarothFx.verdictClock(target, Math.max(3.2F, target.getBbWidth() * 5.5F), 150);
               IstarothFx.judgementStreak(target, 16.0F, 14);
            }
         } else {
            entity.sendSystemMessage(Component.literal("The final second is already being counted.").withStyle(ChatFormatting.DARK_GRAY));
         }
      }
   }

   public static void onIstarothHitTarget(LivingEntity owner, LivingEntity target, float damage) {
      if (LAST_SECOND.containsKey(owner.getUUID())) {
         IstarothCommon.addTemporalDebt(target, damage * 1.15F, owner.getUUID());
         addLastSecondForce(owner.getUUID(), damage * 0.85F);
         applyEventSilence(owner, target, 65, 3);
         applyFutureRejection(owner, target, 65, 4);
         IstarothFx.clockDust(target, 1.9F, 18);
      }
   }

   public static void applyEventSilence(LivingEntity owner, LivingEntity target, int ticks, int power) {
      EVENT_SILENCE.put(target.getUUID(), new IstarothUltimate.EventSilenceState(owner.getUUID(), ticks, power, target.position(), target.getHealth()));
      target.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.EVENT_SILENCE), ticks, power, false, false, true));
      target.setDeltaMovement(target.getDeltaMovement().scale(0.025));
      target.hurtMarked = true;
      if (power >= 2) {
         target.addEffect(
            new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), Math.min(ticks, 120), Math.min(5, power), false, false, true)
         );
      }
   }

   public static void applyFutureRejection(LivingEntity owner, LivingEntity target, int ticks, int power) {
      Vec3 velocity = target.getDeltaMovement();
      Vec3 lookFuture = target.getLookAngle().normalize().scale(3.8 + power * 1.25);
      Vec3 moveFuture = velocity.lengthSqr() > 0.01 ? velocity.normalize().scale(7.5 + power * 3.0) : lookFuture;
      Vec3 future = target.position().add(moveFuture);
      FUTURE_REJECTION.put(
         target.getUUID(), new IstarothUltimate.FutureRejectionState(owner.getUUID(), ticks, power, target.position(), future, target.getHealth())
      );
      target.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.FUTURE_REJECTION), ticks, power, false, false, true));
      IstarothFx.rejectedFutureAfterimage(target, future);
   }

   private static void addLastSecondForce(UUID owner, double amount) {
      if (!(amount <= 0.0)) {
         LAST_SECOND_FORCE.put(owner, Math.min(12000.0F, LAST_SECOND_FORCE.getOrDefault(owner, 0.0F) + (float)amount));
      }
   }

   private static void tickWorldStopTimer(LivingEntity owner) {
      UUID id = owner.getUUID();
      Integer ticks = WORLD_STOP.get(id);
      if (ticks != null) {
         if (ticks <= 0) {
            WORLD_STOP.remove(id);
            WORLD_STOP_HELD.remove(id);
         } else {
            WORLD_STOP.put(id, ticks - 1);
         }
      }
   }

   private static void tickLastSecond(IstarothSkill skill, ManasSkillInstance instance, LivingEntity owner) {
      UUID id = owner.getUUID();
      Integer ticks = LAST_SECOND.get(id);
      if (ticks != null) {
         if (owner.level() instanceof ServerLevel level) {
            if (ticks <= 0) {
               try {
                  collapseLastSecond(skill, instance, owner);
               } finally {
                  LAST_SECOND.remove(id);
               }
            } else {
               int radius = i(CONFIG.lastSecondRadius);
               if (ticks % 5 == 0) {
                  int elapsed = i(CONFIG.lastSecondTicks) - ticks;
                  float progress = Mth.clamp((float)elapsed / i(CONFIG.lastSecondTicks), 0.0F, 1.0F);
                  float shake = 0.045F + progress * 0.115F;
                  if (ticks <= 120) {
                     shake += 0.045F;
                  }

                  if (ticks <= 80) {
                     shake += 0.05F;
                  }

                  if (ticks <= 40) {
                     shake += 0.075F;
                  }

                  if (ticks <= 20) {
                     shake += 0.095F;
                  }

                  IstarothFx.cameraShake(owner, radius + 38.0, shake, 9);
               }

               if (ticks % 20 == 0) {
                  int seconds = Math.max(0, Mth.ceil(ticks.intValue() / 20.0F));
                  IstarothFx.timeStopScreen(owner, radius, ticks + 1, ticks <= 40 ? 7 : 6);
                  if (seconds <= 5) {
                     IstarothFx.cameraShake(owner, radius + 28.0, 0.13F + (5 - seconds) * 0.033F, 18);
                     IstarothFx.sound(owner, SoundEvents.AMETHYST_BLOCK_RESONATE, 1.65F, 0.1F + seconds * 0.025F);
                  } else {
                     IstarothFx.sound(owner, SoundEvents.BEACON_AMBIENT, 0.62F, 0.2F);
                  }
               }

               if (ticks % 50 == 0) {
                  IstarothFx.overheadClock(owner, radius * 0.72F, 85);
                  IstarothFx.clockCircle(owner, radius * 0.62F, 80, MagicCircleVariant.SPACE);
               }

               if (ticks <= 120 && ticks % 10 == 0) {
                  IstarothFx.finalChargeScreen(owner, radius + 18.0F);
               }

               if (ticks <= 60 && ticks % 6 == 0) {
                  IstarothFx.finalCollapseScreen(owner, radius + 8.0F);
               }

               if (ticks <= 40) {
                  tickZeroSecondSequence(level, owner, ticks, radius);
               } else if (ticks <= 160) {
                  tickReverseGravityField(level, owner, ticks, radius);
               } else {
                  tickFinalPressureField(level, owner, ticks, radius);
               }

               LAST_SECOND.put(id, ticks - 1);
            }
         } else {
            LAST_SECOND.remove(id);
            LAST_SECOND_FORCE.remove(id);
         }
      }
   }

   private static void tickFinalPressureField(ServerLevel level, LivingEntity owner, int ticks, int radius) {
      UUID id = owner.getUUID();
      AABB area = owner.getBoundingBox().inflate(radius);

      for (Projectile projectile : level.getEntitiesOfClass(Projectile.class, area, p -> p.getOwner() != owner)) {
         addLastSecondForce(id, projectile.getDeltaMovement().length() * 22.0 + 12.0);
         IstarothPassives.freezeProjectileHard(projectile, owner, 100, true);
         if (ticks % 10 == 0) {
            IstarothFx.frozenProjectileAura(projectile);
         }
      }

      for (FallingBlockEntity falling : level.getEntitiesOfClass(FallingBlockEntity.class, area)) {
         falling.setDeltaMovement(Vec3.ZERO);
         falling.hurtMarked = true;
         if (ticks % 20 == 0) {
            addLastSecondForce(id, 16.0);
         }
      }

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area, e -> IstarothCommon.validEnemy(owner, e))) {
         double resistance = IstarothCommon.resistanceFactor(target, owner);
         target.setDeltaMovement(target.getDeltaMovement().scale(0.003 + resistance * 0.12));
         target.hurtMarked = true;
         if (ticks % 20 == 0) {
            float debt = ticks <= 100 ? 92.0F : 52.0F;
            IstarothCommon.addTemporalDebt(target, debt, owner.getUUID());
            addLastSecondForce(id, debt);
            applyEventSilence(owner, target, 95, ticks <= 100 ? 6 : 5);
            applyFutureRejection(owner, target, 95, ticks <= 100 ? 6 : 5);
            decayBeneficialEffectsHard(target, ticks <= 100 ? 240 : 125);
         }

         if (ticks % 12 == 0) {
            IstarothFx.clockDust(target, 2.6F, 32);
         }

         if (ticks % 35 == 0) {
            IstarothFx.judgementStreak(target, 17.5F, ticks <= 100 ? 20 : 12);
         }

         if (ticks <= 120 && ticks % 10 == 0) {
            IstarothFx.causalityPillar(target, Math.max(3.3F, target.getBbWidth() * 4.6F), 18);
         }

         if (ticks <= 40 && ticks % 8 == 0) {
            IstarothFx.finalRuptureExplosion(target, Math.max(4.0F, target.getBbWidth() * 5.0F));
         }
      }
   }

   private static void tickReverseGravityField(ServerLevel level, LivingEntity owner, int ticks, int radius) {
      UUID ownerId = owner.getUUID();
      Vec3 center = owner.position();
      double progress = Mth.clamp((160.0 - ticks) / 160.0, 0.0, 1.0);
      AABB area = owner.getBoundingBox().inflate(radius + 28.0, radius + 90.0, radius + 28.0);

      for (Entity entity : level.getEntitiesOfClass(Entity.class, area, e -> e.isAlive() && e != owner)) {
         if (!(entity instanceof Player player && player.isCreative())) {
            Vec3 offset = entity.position().subtract(center);
            double horizontalDistance = Math.max(1.0, offset.horizontalDistance());
            Vec3 inward = new Vec3(center.x - entity.getX(), 0.0, center.z - entity.getZ());
            Vec3 tangent = new Vec3(-inward.z, 0.0, inward.x);
            if (inward.lengthSqr() < 0.01) {
               inward = owner.getLookAngle();
            }

            if (tangent.lengthSqr() < 0.01) {
               tangent = new Vec3(1.0, 0.0, 0.0);
            }

            entity.setNoGravity(true);
            double pull = Mth.clamp((radius + 20.0) / horizontalDistance, 0.0, 4.0);
            Vec3 motion = inward.normalize()
               .scale(0.025 + progress * 0.1 + pull * 0.018)
               .add(tangent.normalize().scale(0.05 + progress * 0.16))
               .add(0.0, 0.05 + progress * 0.2, 0.0);
            entity.setDeltaMovement(entity.getDeltaMovement().scale(0.78).add(motion));
            entity.hurtMarked = true;
            if (entity instanceof Projectile projectile) {
               projectile.getPersistentData().putBoolean("IstarothFrozenProjectile", true);
               addLastSecondForce(ownerId, projectile.getDeltaMovement().length() * 18.0 + 18.0);
            }

            if (entity instanceof LivingEntity living && IstarothCommon.validEnemy(owner, living) && ticks % 10 == 0) {
               IstarothCommon.addTemporalDebt(living, 38.0F + (float)(progress * 80.0), ownerId);
               addLastSecondForce(ownerId, 50.0 + progress * 110.0);
               living.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.WORLD_OF_STILL_SECONDS), 45, 5, false, false, true));
               living.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), 65, 5, false, false, true));
               IstarothFx.clockDust(living, 2.4F, 26);
            }
         }
      }

      if (ticks == 160 || ticks == 120 || ticks == 80) {
         IstarothFx.finalChargeScreen(owner, radius + 24.0F);
         IstarothFx.sound(owner, SoundEvents.BEACON_DEACTIVATE, 2.0F, 0.08F);
      }
   }

   private static void tickZeroSecondSequence(ServerLevel level, LivingEntity owner, int ticks, int radius) {
      UUID ownerId = owner.getUUID();
      AABB area = owner.getBoundingBox().inflate(radius + 44.0, radius + 130.0, radius + 44.0);
      if (ticks == 40) {
         owner.sendSystemMessage(Component.literal("Zero second begins.").withStyle(ChatFormatting.DARK_GRAY));
         IstarothFx.finalCollapseScreen(owner, radius + 18.0F);
         IstarothFx.cameraShake(owner, radius + 44.0, 0.24F, 36);
         IstarothFx.sound(owner, SoundEvents.BEACON_DEACTIVATE, 3.4F, 0.04F);
      }

      if (ticks == 20) {
         IstarothFx.finalCollapseScreen(owner, radius + 32.0F);
         IstarothFx.finalRuptureExplosion(owner, radius * 0.95F);
         IstarothFx.cameraShake(owner, radius + 60.0, 0.34F, 42);
         IstarothFx.sound(owner, SoundEvents.WARDEN_SONIC_CHARGE, 3.8F, 0.06F);
      }

      if (ticks <= 10 && ticks % 2 == 0) {
         IstarothFx.finalCollapseScreen(owner, radius + 42.0F);
         IstarothFx.sound(owner, SoundEvents.AMETHYST_BLOCK_RESONATE, 2.6F, 0.05F);
      }

      for (Entity entity : level.getEntitiesOfClass(Entity.class, area, e -> e.isAlive() && e != owner)) {
         if (!(entity instanceof Player player && player.isCreative())) {
            entity.setNoGravity(true);
            entity.setDeltaMovement(entity.getDeltaMovement().scale(ticks <= 12 ? 0.0 : 0.18));
            entity.hurtMarked = true;
            if (entity instanceof Projectile projectile) {
               projectile.getPersistentData().putBoolean("IstarothFrozenProjectile", true);
               addLastSecondForce(ownerId, 42.0);
            }

            if (entity instanceof FallingBlockEntity) {
               addLastSecondForce(ownerId, 35.0);
            }

            if (entity instanceof LivingEntity living && IstarothCommon.validEnemy(owner, living)) {
               living.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_SCREEN), 45, 7, false, false, true));
               living.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.WORLD_OF_STILL_SECONDS), 45, 7, false, false, true));
               living.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), 55, 7, false, false, true));
               if (ticks % 5 == 0) {
                  float debt = ticks <= 20 ? 180.0F : 95.0F;
                  IstarothCommon.addTemporalDebt(living, debt, ownerId);
                  addLastSecondForce(ownerId, debt);
                  decayBeneficialEffectsHard(living, 500);
                  IstarothFx.causalityPillar(living, Math.max(4.0F, living.getBbWidth() * 5.8F), 18);
               }
            }
         }
      }

      owner.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_SCREEN), 45, 7, false, false, true));
   }

   private static void collapseLastSecond(IstarothSkill skill, ManasSkillInstance instance, LivingEntity owner) {
      if (owner.level() instanceof ServerLevel level) {
         UUID var27 = owner.getUUID();
         Vec3 center = owner.position();
         int radius = i(CONFIG.lastSecondCollapseRadius);
         DamageSource source = IstarothCommon.damageSource(owner);
         float accumulatedForce = LAST_SECOND_FORCE.getOrDefault(var27, 0.0F);

         try {
            IstarothDoomTornado.detonate(level, var27);
            Optional<LivingEntity> selected = IstarothCommon.nearbyLiving(owner, radius)
               .stream()
               .filter(e -> IstarothCommon.validEnemy(owner, e))
               .max(Comparator.comparingDouble(e -> e.getMaxHealth() + IstarothCommon.getDebt(e)));
            int judged = 0;
            int erased = 0;
            AABB area = owner.getBoundingBox().inflate(radius + 30.0, radius + 90.0, radius + 30.0);

            for (Projectile projectile : level.getEntitiesOfClass(Projectile.class, area, p -> p.getOwner() != owner)) {
               Vec3 away = projectile.position().subtract(center);
               if (away.lengthSqr() < 0.01) {
                  away = owner.getLookAngle();
               }

               projectile.setNoGravity(false);
               projectile.setDeltaMovement(away.normalize().scale(7.5).add(0.0, 1.6, 0.0));
               projectile.hurtMarked = true;
               projectile.getPersistentData().remove("IstarothFrozenProjectile");
               IstarothFx.clockShatter(projectile, 2.4F);
            }

            for (FallingBlockEntity falling : level.getEntitiesOfClass(FallingBlockEntity.class, area)) {
               Vec3 away = falling.position().subtract(center);
               if (away.lengthSqr() < 0.01) {
                  away = owner.getLookAngle();
               }

               falling.setNoGravity(false);
               falling.setDeltaMovement(away.normalize().scale(5.8).add(0.0, -2.6, 0.0));
               falling.hurtMarked = true;
               IstarothFx.clockShatter(falling, 2.0F);
            }

            for (LivingEntity target : IstarothCommon.nearbyLiving(owner, radius + 24)) {
               if (IstarothCommon.validEnemy(owner, target)) {
                  boolean primary = selected.isPresent() && selected.get() == target;
                  float debt = IstarothCommon.consumeDebt(target, var27);
                  float maxHealthDamage = target instanceof Player
                     ? target.getMaxHealth() * f(CONFIG.lastSecondPlayerMaxHealthDamage)
                     : target.getMaxHealth() * f(CONFIG.lastSecondMobMaxHealthDamage);
                  float forceDamage = Math.min(
                     primary ? f(CONFIG.lastSecondPrimaryForceCap) : f(CONFIG.lastSecondForceCap),
                     accumulatedForce * (primary ? f(CONFIG.lastSecondPrimaryForceMultiplier) : f(CONFIG.lastSecondForceMultiplier))
                  );
                  float baseDamage = instance.isMastered(owner) ? f(CONFIG.lastSecondBaseDamageMastered) : f(CONFIG.lastSecondBaseDamage);
                  float damage = baseDamage
                     + debt * (primary ? f(CONFIG.lastSecondPrimaryDebtMultiplier) : f(CONFIG.lastSecondDebtMultiplier))
                     + maxHealthDamage
                     + forceDamage;
                  if (primary) {
                     damage *= target instanceof Player ? f(CONFIG.lastSecondPrimaryPlayerMultiplier) : f(CONFIG.lastSecondPrimaryMobMultiplier);
                  }

                  if (target instanceof Player) {
                     damage *= f(CONFIG.lastSecondPlayerDamageMultiplier);
                  }

                  target.invulnerableTime = 0;
                  target.hurt(source, damage);
                  Vec3 away = target.position().subtract(center);
                  if (away.lengthSqr() < 0.01) {
                     away = owner.getLookAngle();
                  }

                  Vec3 slam = away.normalize().scale(primary ? 8.0 : 5.8).add(0.0, primary ? -5.6 : -4.0, 0.0);
                  target.setNoGravity(false);
                  target.setDeltaMovement(slam);
                  target.hurtMarked = true;
                  target.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TIMELINE_VERDICT), 40, primary ? 7 : 6, false, false, true));
                  target.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.EVENT_SILENCE), 40, primary ? 6 : 5, false, false, true));
                  target.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.FUTURE_REJECTION), 40, primary ? 6 : 5, false, false, true));
                  target.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), 40, primary ? 7 : 6, false, false, true));
                  IstarothCommon.Snapshot bad = IstarothCommon.getOldSnapshot(target, 100);
                  if (bad != null && primary) {
                     bad.restorePositionOnly(target);
                  }

                  decayBeneficialEffectsHard(target, 900);
                  IstarothFx.judgementStreak(target, 22.0F, primary ? 34 : 22);
                  IstarothFx.causalityPillar(target, Math.max(4.6F, target.getBbWidth() * 6.4F), primary ? 34 : 24);
                  IstarothFx.finalRuptureExplosion(target, primary ? 16.0F : 10.0F);
                  IstarothFx.clockShatter(target, primary ? 9.0F : 6.2F);
                  if (!(target instanceof Player) && target.getHealth() < target.getMaxHealth() * 0.45F) {
                     target.invulnerableTime = 0;
                     target.hurt(source, target.getMaxHealth() * 8.0F + 500.0F);
                     erased++;
                  }

                  judged++;
               }
            }

            IstarothFx.finalCollapseScreen(owner, radius);
            IstarothFx.finalRuptureExplosion(owner, radius * 1.05F);
            IstarothFx.clockShatter(owner, radius);
            IstarothFx.cameraShake(owner, radius + 36.0, 0.28F, 52);
            IstarothFx.sound(owner, SoundEvents.WARDEN_SONIC_BOOM, 4.0F, 0.08F);
            IstarothFx.sound(owner, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), 4.0F, 0.18F);
            IstarothFx.sound(owner, SoundEvents.END_PORTAL_SPAWN, 3.2F, 0.06F);
            owner.sendSystemMessage(
               Component.literal("The future collapses into one wound. Targets judged: " + judged + " | erased: " + erased).withStyle(ChatFormatting.DARK_GRAY)
            );
         } finally {
            cleanupFinalSecondAfterEnd(level, var27, center, radius + 140.0, true);
         }
      }
   }

   private static void cleanupFinalSecondAfterEnd(ServerLevel level, UUID ownerId, Vec3 center, double radius, boolean removeScreenEffects) {
      LAST_SECOND.remove(ownerId);
      LAST_SECOND_FORCE.remove(ownerId);
      FROZEN_WORLD_FORCE.remove(ownerId);
      WORLD_STOP.remove(ownerId);
      WORLD_STOP_HELD.remove(ownerId);
      EVENT_SILENCE.entrySet().removeIf(entry -> ownerId.equals(entry.getValue().owner));
      FUTURE_REJECTION.entrySet().removeIf(entry -> ownerId.equals(entry.getValue().owner));
      TIMELINE_VERDICT.entrySet().removeIf(entry -> ownerId.equals(entry.getValue().owner));
      AABB area = new AABB(center, center).inflate(radius, radius, radius);

      for (Entity entity : level.getEntitiesOfClass(Entity.class, area, Entity::isAlive)) {
         if (!(entity instanceof Player player && player.isCreative())) {
            entity.setNoGravity(false);
            entity.hurtMarked = true;
         }

         if (entity instanceof Projectile projectile) {
            projectile.setNoGravity(false);
            projectile.getPersistentData().remove("IstarothFrozenProjectile");
            projectile.hurtMarked = true;
         }

         if (entity instanceof LivingEntity living) {
            if (removeScreenEffects) {
               living.removeEffect(ModMobEffects.getReference(ModMobEffects.TEMPORAL_SCREEN));
            }

            living.removeEffect(ModMobEffects.getReference(ModMobEffects.WORLD_OF_STILL_SECONDS));
            living.removeEffect(ModMobEffects.getReference(ModMobEffects.EVENT_SILENCE));
            living.removeEffect(ModMobEffects.getReference(ModMobEffects.FUTURE_REJECTION));
            living.removeEffect(ModMobEffects.getReference(ModMobEffects.TIMELINE_VERDICT));
            MobEffectInstance stutter = living.getEffect(ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER));
            if (stutter != null && stutter.getAmplifier() >= 4) {
               living.removeEffect(ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER));
            }
         }
      }
   }

   private static void tickEventSilence(Level level) {
      if (level instanceof ServerLevel serverLevel) {
         Iterator iterator = EVENT_SILENCE.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<UUID, IstarothUltimate.EventSilenceState> entry = (Entry<UUID, IstarothUltimate.EventSilenceState>)iterator.next();
            if (serverLevel.getEntity(entry.getKey()) instanceof LivingEntity living && living.isAlive()) {
               IstarothUltimate.EventSilenceState state = entry.getValue();
               LivingEntity owner = serverLevel.getEntity(state.owner) instanceof LivingEntity o ? o : null;
               Vec3 movement = living.position().subtract(state.lastPos);
               boolean escaped = movement.length() > 2.0 + state.power * 0.25 || living.getDeltaMovement().length() > 0.9;
               boolean healed = living.getHealth() > state.lastHealth + 1.25F;
               if (escaped) {
                  living.teleportTo(state.lastPos.x, state.lastPos.y, state.lastPos.z);
                  living.setDeltaMovement(Vec3.ZERO);
                  living.hurtMarked = true;
                  if (owner != null) {
                     float force = 24.0F + state.power * 10.0F;
                     IstarothCommon.addTemporalDebt(living, force, owner.getUUID());
                     addLastSecondForce(owner.getUUID(), force);
                  }

                  IstarothFx.loopSnapScreen(living);
               }

               if (healed && owner != null) {
                  float excess = living.getHealth() - state.lastHealth;
                  living.setHealth(Math.max(1.0F, state.lastHealth + excess * 0.12F));
                  IstarothCommon.addTemporalDebt(living, excess * 2.4F, owner.getUUID());
                  addLastSecondForce(owner.getUUID(), excess * 1.6F);
                  IstarothFx.clockDust(living, 1.5F, 18);
               }

               if (owner != null) {
                  for (Projectile projectile : serverLevel.getEntitiesOfClass(
                     Projectile.class, living.getBoundingBox().inflate(9.0), p -> p.getOwner() == living
                  )) {
                     addLastSecondForce(owner.getUUID(), projectile.getDeltaMovement().length() * 16.0 + 10.0);
                     IstarothPassives.freezeProjectileHard(projectile, owner, 100, true);
                     IstarothFx.frozenProjectileAura(projectile);
                  }
               }

               living.setDeltaMovement(living.getDeltaMovement().scale(0.34));
               living.hurtMarked = true;
               state.lastPos = living.position();
               state.lastHealth = living.getHealth();
               state.ticks--;
               if (living.tickCount % 8 == 0) {
                  IstarothFx.clockDust(living, 1.2F, 10);
               }

               if (state.ticks <= 0) {
                  iterator.remove();
               }
            } else {
               iterator.remove();
            }
         }
      }
   }

   private static void tickFutureRejection(Level level) {
      if (level instanceof ServerLevel serverLevel) {
         Iterator iterator = FUTURE_REJECTION.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<UUID, IstarothUltimate.FutureRejectionState> entry = (Entry<UUID, IstarothUltimate.FutureRejectionState>)iterator.next();
            if (serverLevel.getEntity(entry.getKey()) instanceof LivingEntity living && living.isAlive()) {
               IstarothUltimate.FutureRejectionState state = entry.getValue();
               LivingEntity owner = serverLevel.getEntity(state.owner) instanceof LivingEntity o ? o : null;
               double futureDistance = living.position().distanceTo(state.future);
               boolean reachedFuture = futureDistance < 2.8;
               boolean escapedAnchor = living.position().distanceTo(state.anchor) > 6.0 + state.power * 1.8;
               boolean healed = living.getHealth() > state.health + 3.0F;
               if (reachedFuture || escapedAnchor || healed) {
                  Vec3 rejected = state.anchor.add(living.position().subtract(state.anchor).scale(0.14));
                  living.teleportTo(rejected.x, rejected.y, rejected.z);
                  living.setDeltaMovement(Vec3.ZERO);
                  living.hurtMarked = true;
                  if (owner != null) {
                     float damage = 28.0F + state.power * 14.0F;
                     if (healed) {
                        damage += (living.getHealth() - state.health) * 1.9F;
                     }

                     living.invulnerableTime = 0;
                     living.hurt(IstarothCommon.damageSource(owner), damage);
                     IstarothCommon.addTemporalDebt(living, damage, owner.getUUID());
                     addLastSecondForce(owner.getUUID(), damage);
                  }

                  IstarothFx.loopSnapScreen(living);
                  IstarothFx.verdictClock(living, Math.max(2.2F, living.getBbWidth() * 3.8F), 50);
                  state.anchor = living.position();
                  state.future = living.position().add(living.getLookAngle().normalize().scale(5.0 + state.power * 2.4));
                  state.health = living.getHealth();
               }

               if (living.tickCount % 10 == 0) {
                  IstarothFx.rejectedFutureAfterimage(living, state.future);
               }

               state.ticks--;
               if (state.ticks <= 0) {
                  iterator.remove();
               }
            } else {
               iterator.remove();
            }
         }
      }
   }

   private static void tickTimelineVerdict(Level level) {
      if (level instanceof ServerLevel serverLevel) {
         Iterator iterator = TIMELINE_VERDICT.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<UUID, IstarothUltimate.TimelineVerdictState> entry = (Entry<UUID, IstarothUltimate.TimelineVerdictState>)iterator.next();
            if (serverLevel.getEntity(entry.getKey()) instanceof LivingEntity living && living.isAlive()) {
               IstarothUltimate.TimelineVerdictState state = entry.getValue();
               LivingEntity owner = serverLevel.getEntity(state.owner) instanceof LivingEntity o ? o : null;
               living.setDeltaMovement(living.getDeltaMovement().scale(state.selected ? 0.12 : 0.3));
               living.hurtMarked = true;
               if (state.selected && state.ticks % 20 == 0 && living.position().distanceTo(state.bad.pos) > 6.0) {
                  state.bad.restorePositionOnly(living);
                  IstarothFx.loopSnapScreen(living);
               }

               if (state.ticks % 18 == 0 && owner != null) {
                  float damage = state.selected ? 32.0F : 16.0F;
                  living.invulnerableTime = 0;
                  living.hurt(IstarothCommon.damageSource(owner), damage);
                  IstarothCommon.addTemporalDebt(living, state.selected ? 22.0F : 11.0F, owner.getUUID());
                  IstarothFx.verdictClock(living, Math.max(2.2F, living.getBbWidth() * 3.3F), 40);
               }

               state.ticks--;
               if (state.ticks <= 0) {
                  iterator.remove();
               }
            } else {
               iterator.remove();
            }
         }
      }
   }

   private static void decayBeneficialEffectsHard(LivingEntity entity, int amount) {
      for (MobEffectInstance effect : entity.getActiveEffects().stream().toList()) {
         if (((MobEffect)effect.getEffect().value()).getCategory() == MobEffectCategory.BENEFICIAL) {
            int duration = Math.max(1, effect.getDuration() - amount);
            entity.removeEffect(effect.getEffect());
            entity.addEffect(
               new MobEffectInstance(effect.getEffect(), duration, effect.getAmplifier(), effect.isAmbient(), effect.isVisible(), effect.showIcon())
            );
         }
      }
   }

   private static final class EventSilenceState {
      private final UUID owner;
      private int ticks;
      private final int power;
      private Vec3 lastPos;
      private float lastHealth;

      private EventSilenceState(UUID owner, int ticks, int power, Vec3 lastPos, float lastHealth) {
         this.owner = owner;
         this.ticks = ticks;
         this.power = power;
         this.lastPos = lastPos;
         this.lastHealth = lastHealth;
      }
   }

   private static final class FutureRejectionState {
      private final UUID owner;
      private int ticks;
      private final int power;
      private Vec3 anchor;
      private Vec3 future;
      private float health;

      private FutureRejectionState(UUID owner, int ticks, int power, Vec3 anchor, Vec3 future, float health) {
         this.owner = owner;
         this.ticks = ticks;
         this.power = power;
         this.anchor = anchor;
         this.future = future;
         this.health = health;
      }
   }

   private static final class TimelineVerdictState {
      private final UUID owner;
      private int ticks;
      private final IstarothCommon.Snapshot bad;
      private final boolean selected;

      private TimelineVerdictState(UUID owner, int ticks, IstarothCommon.Snapshot bad, boolean selected) {
         this.owner = owner;
         this.ticks = ticks;
         this.bad = bad;
         this.selected = selected;
      }
   }
}
