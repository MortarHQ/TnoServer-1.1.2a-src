package com.github.wal_bos.moreskills.handler.Istaroth;

import com.github.wal_bos.moreskills.registry.effect.ModMobEffects;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.entity.variant.MagicCircleVariant;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class IstarothPassives {
   private static final int MAX_FROZEN_PROJECTILES_PER_OWNER = 24;
   private static final int MAX_TRACKED_ENEMY_STATES = 180;
   private static final Map<UUID, Integer> STILLNESS = new HashMap<>();
   private static final Map<UUID, Float> STILLNESS_PRESSURE = new HashMap<>();
   private static final Map<UUID, IstarothPassives.FrozenProjectileState> FROZEN_PROJECTILES = new HashMap<>();
   private static final Map<UUID, Float> FROZEN_FORCE = new HashMap<>();
   private static final Map<UUID, Integer> PROJECTILE_REFLECT_COOLDOWN = new HashMap<>();
   private static final Map<UUID, Integer> EVASION_SURGE = new HashMap<>();
   private static final Map<UUID, Integer> TEMPORAL_CROWN = new HashMap<>();
   private static final Map<UUID, Boolean> MASTERED_CACHE = new HashMap<>();
   private static final Map<UUID, Integer> PASSIVE_STACKS = new HashMap<>();
   private static final Map<UUID, Integer> TIME_TAX_COOLDOWN = new HashMap<>();
   private static final Map<UUID, Integer> PARADOX_MARKS = new HashMap<>();
   private static final Map<UUID, Integer> FROZEN_PROJECTILE_BATTERY = new HashMap<>();
   private static final Map<UUID, String> LAST_DAMAGE_KEY = new HashMap<>();
   private static final Map<UUID, Integer> SAME_TIMELINE_HITS = new HashMap<>();
   private static final Map<UUID, Integer> TIMELINE_SOVEREIGNTY_TICKS = new HashMap<>();
   private static final Map<UUID, Float> LAST_HEALTH = new HashMap<>();
   private static final Map<UUID, Float> TIME_LOCKED_WOUNDS = new HashMap<>();
   private static final Map<UUID, Integer> TIME_LOCK_TICKS = new HashMap<>();
   private static final Map<UUID, UUID> SECOND_HAND_TARGET = new HashMap<>();
   private static final Map<UUID, Integer> SECOND_HAND_TICKS = new HashMap<>();
   private static final Map<UUID, Integer> EXECUTION_MARKS = new HashMap<>();
   private static final Map<UUID, Integer> UNWRITTEN_DEATH_COOLDOWN = new HashMap<>();
   private static final Map<UUID, UUID> RECENT_ATTACKER = new HashMap<>();
   private static final Map<UUID, Integer> RECENT_ATTACKER_TICKS = new HashMap<>();
   private static final Map<UUID, Integer> AGE_SENTENCE = new HashMap<>();
   private static final Map<UUID, Integer> CHRONO_RUPTURE = new HashMap<>();
   private static final Map<UUID, Integer> EVENT_HORIZON = new HashMap<>();
   private static final Map<UUID, Integer> KING_OF_ONE_SECOND = new HashMap<>();
   private static final Map<UUID, Integer> DEAD_SECOND_MARKS = new HashMap<>();

   private IstarothPassives() {
   }

   public static void init() {
   }

   public static void tick(ManasSkillInstance instance, LivingEntity owner) {
      if (!owner.level().isClientSide()) {
         boolean mastered = instance.isMastered(owner);
         UUID ownerId = owner.getUUID();
         MASTERED_CACHE.put(ownerId, mastered);
         tickPassiveProjectileInfinity(instance, owner);
         tickFrozenProjectiles(owner.level(), ownerId);
         tickProjectileReflectCooldown();
         tickTemporalIsolation(owner, mastered);
         tickStolenDuration(owner, mastered);
         tickChronoPrediction(owner, mastered);
         tickPassiveCooldownRecovery(instance, owner, mastered);
         tickEvasionSurge(owner, mastered);
         tickTemporalCrown(owner, mastered);
         tickSovereignTimeAuthority(owner, mastered);
         tickTimeTax(owner, mastered);
         tickFrozenProjectileBattery(instance, owner, mastered);
         tickParadoxMarks(owner, mastered);
         tickTimelineSovereignty(owner);
         tickStolenTomorrow(owner, mastered);
         tickSecondHandDominion(owner, mastered);
         tickAgingOfPower(owner, mastered);
         tickClockworkExecution(owner, mastered);
         tickTimeLockedWounds(owner, mastered);
         tickUnwrittenDeathCooldown(owner);
         tickRecentAttacker(owner);
         tickAgeSentence(owner, mastered);
         tickChronoRupture(owner, mastered);
         tickEventHorizon(owner, mastered);
         tickKingOfOneSecond(owner, mastered);
         tickDeadSecondMarks(owner, mastered);
         tickStillness(owner);
         if (owner.tickCount % 200 == 0) {
            trimGlobalMaps();
         }
      }
   }

   public static boolean hasActiveState(LivingEntity entity) {
      UUID id = entity.getUUID();
      return STILLNESS.containsKey(id)
         || EVASION_SURGE.containsKey(id)
         || TEMPORAL_CROWN.containsKey(id)
         || FROZEN_PROJECTILE_BATTERY.containsKey(id)
         || PARADOX_MARKS.containsKey(id)
         || SAME_TIMELINE_HITS.containsKey(id)
         || TIME_LOCKED_WOUNDS.containsKey(id)
         || EXECUTION_MARKS.containsKey(id)
         || SECOND_HAND_TARGET.containsKey(id)
         || UNWRITTEN_DEATH_COOLDOWN.containsKey(id)
         || AGE_SENTENCE.containsKey(id)
         || CHRONO_RUPTURE.containsKey(id)
         || EVENT_HORIZON.containsKey(id)
         || KING_OF_ONE_SECOND.containsKey(id)
         || DEAD_SECOND_MARKS.containsKey(id);
   }

   public static void clearOwner(LivingEntity entity) {
      UUID ownerId = entity.getUUID();
      STILLNESS.remove(ownerId);
      STILLNESS_PRESSURE.remove(ownerId);
      EVASION_SURGE.remove(ownerId);
      TEMPORAL_CROWN.remove(ownerId);
      MASTERED_CACHE.remove(ownerId);
      PASSIVE_STACKS.remove(ownerId);
      FROZEN_PROJECTILE_BATTERY.remove(ownerId);
      LAST_DAMAGE_KEY.remove(ownerId);
      SAME_TIMELINE_HITS.remove(ownerId);
      TIMELINE_SOVEREIGNTY_TICKS.remove(ownerId);
      SECOND_HAND_TARGET.remove(ownerId);
      SECOND_HAND_TICKS.remove(ownerId);
      UNWRITTEN_DEATH_COOLDOWN.remove(ownerId);
      RECENT_ATTACKER.remove(ownerId);
      RECENT_ATTACKER_TICKS.remove(ownerId);
      KING_OF_ONE_SECOND.remove(ownerId);
      entity.removeEffect(ModMobEffects.getReference(ModMobEffects.STILLNESS_OF_THE_KING));
      entity.removeEffect(ModMobEffects.getReference(ModMobEffects.ETERNAL_MOMENT));
      TIME_TAX_COOLDOWN.clear();
      clearOwnedEnemyMap(entity, PARADOX_MARKS, true);
      clearOwnedEnemyMap(entity, TIME_LOCK_TICKS, false);
      clearOwnedEnemyMap(entity, EXECUTION_MARKS, true);
      clearOwnedEnemyMap(entity, AGE_SENTENCE, true);
      clearOwnedEnemyMap(entity, CHRONO_RUPTURE, true);
      clearOwnedEnemyMap(entity, EVENT_HORIZON, true);
      clearOwnedEnemyMap(entity, DEAD_SECOND_MARKS, true);
      LAST_HEALTH.clear();
      TIME_LOCKED_WOUNDS.clear();

      for (IstarothPassives.FrozenProjectileState state : FROZEN_PROJECTILES.values()) {
         if (state.owner.equals(ownerId)) {
            state.ticks = Math.min(state.ticks, 2);
            state.redirectOnRelease = true;
            state.reflectOnRelease = false;
         }
      }
   }

   private static void clearOwnedEnemyMap(LivingEntity owner, Map<UUID, Integer> map, boolean removeVisualEffects) {
      if (owner.level() instanceof ServerLevel serverLevel) {
         for (Iterator var8 = map.entrySet().iterator(); var8.hasNext(); var8.remove()) {
            Entry<UUID, Integer> entry = (Entry<UUID, Integer>)var8.next();
            if (serverLevel.getEntity(entry.getKey()) instanceof LivingEntity living && IstarothCommon.validEnemy(owner, living) && removeVisualEffects) {
               living.removeEffect(ModMobEffects.getReference(ModMobEffects.FUTURE_REJECTION));
               living.removeEffect(ModMobEffects.getReference(ModMobEffects.TIMELINE_VERDICT));
               living.removeEffect(ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER));
            }
         }
      } else {
         map.clear();
      }
   }

   public static void stillnessOfTheKing(ManasSkillInstance instance, LivingEntity entity) {
      int ticks = instance.isMastered(entity) ? 150 : 100;
      STILLNESS.put(entity.getUUID(), ticks);
      STILLNESS_PRESSURE.put(entity.getUUID(), 0.0F);
      TEMPORAL_CROWN.put(entity.getUUID(), instance.isMastered(entity) ? 180 : 120);
      KING_OF_ONE_SECOND.put(entity.getUUID(), instance.isMastered(entity) ? 140 : 80);
      entity.addEffect(
         new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.STILLNESS_OF_THE_KING), ticks, instance.isMastered(entity) ? 4 : 2, false, false, true)
      );
      IstarothFx.clockCircle(entity, 4.0F, ticks, MagicCircleVariant.SPACE);
      IstarothFx.followClockCircle(entity, 2.5F, ticks, MagicCircleVariant.SPACE);
      IstarothFx.grayPulse(entity, 3.0F);
      IstarothFx.timeStopScreen(entity, 10.0, 25, 1);
      IstarothFx.sound(entity, SoundEvents.AMETHYST_BLOCK_CHIME, 1.2F, 0.35F);
   }

   public static boolean tryStillnessReduction(LivingEntity entity, Changeable<Float> amount) {
      Integer ticks = STILLNESS.get(entity.getUUID());
      if (ticks != null && ticks > 0) {
         boolean mastered = MASTERED_CACHE.getOrDefault(entity.getUUID(), false);
         float original = (Float)amount.get();
         float reduction = mastered ? (ticks > 20 ? 0.94F : 0.78F) : (ticks > 20 ? 0.88F : 0.65F);
         amount.set(original * (1.0F - reduction));
         STILLNESS_PRESSURE.put(entity.getUUID(), STILLNESS_PRESSURE.getOrDefault(entity.getUUID(), 0.0F) + original * reduction * (mastered ? 0.55F : 0.35F));
         if (mastered && original > 8.0F) {
            Entity attacker = entity.getLastHurtByMob();
            if (attacker instanceof LivingEntity living && IstarothCommon.validEnemy(entity, living)) {
               IstarothCommon.addTemporalDebt(living, original * 0.65F, entity.getUUID());
               living.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), 45, 1, false, false, true));
               putCapped(PARADOX_MARKS, living.getUUID(), Math.max(PARADOX_MARKS.getOrDefault(living.getUUID(), 0), mastered ? 120 : 70));
               putCapped(DEAD_SECOND_MARKS, living.getUUID(), Math.max(DEAD_SECOND_MARKS.getOrDefault(living.getUUID(), 0), 80));
            }
         }

         IstarothFx.grayPulse(entity, mastered ? 2.4F : 1.7F);
         return true;
      } else {
         return false;
      }
   }

   public static void onOwnerThreatened(LivingEntity entity, DamageSource source, Changeable<Float> amount) {
      if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && !source.isCreativePlayer()) {
         boolean mastered = MASTERED_CACHE.getOrDefault(entity.getUUID(), false);
         applyUnwrittenDeath(entity, source, amount, mastered);
         applyTimelineSovereignty(entity, source, amount, mastered);
         Entity attacker = source.getEntity();
         if (attacker instanceof LivingEntity living && living != entity) {
            RECENT_ATTACKER.put(entity.getUUID(), living.getUUID());
            RECENT_ATTACKER_TICKS.put(entity.getUUID(), 50);
         }

         int evasion = EVASION_SURGE.getOrDefault(entity.getUUID(), 0);
         float baseReduction = mastered ? 0.58F : 0.72F;
         if (evasion > 0) {
            amount.set((Float)amount.get() * (mastered ? 0.42F : 0.58F));
            if (entity.tickCount % 6 == 0) {
               IstarothFx.afterimage(entity, entity.position());
            }

            if (entity.tickCount % 10 == 0) {
               IstarothFx.grayPulse(entity, mastered ? 2.2F : 1.6F);
            }
         }

         if (attacker instanceof LivingEntity living && living != entity && entity.distanceTo(living) <= (mastered ? 18.0F : 12.0F)) {
            float chance = mastered ? 0.42F : 0.22F;
            if (EVASION_SURGE.containsKey(entity.getUUID())) {
               chance += mastered ? 0.25F : 0.12F;
            }

            if (frozenForceForOwner(entity.getUUID()) > 0.0F) {
               chance += mastered ? 0.22F : 0.1F;
            }

            if (entity.getRandom().nextFloat() < chance) {
               Vec3 side = entity.getLookAngle().cross(new Vec3(0.0, 1.0, 0.0));
               if (side.lengthSqr() < 0.01) {
                  side = new Vec3(entity.getRandom().nextBoolean() ? 1.0 : -1.0, 0.0, 0.0);
               }

               side = side.normalize();
               if (entity.getRandom().nextBoolean()) {
                  side = side.scale(-1.0);
               }

               double push = mastered ? 1.05 : 0.62;
               entity.setDeltaMovement(entity.getDeltaMovement().add(side.scale(push)).add(0.0, mastered ? 0.08 : 0.03, 0.0));
               entity.hurtMarked = true;
               amount.set((Float)amount.get() * baseReduction);
               EVASION_SURGE.put(entity.getUUID(), mastered ? 70 : 45);
               PASSIVE_STACKS.put(entity.getUUID(), Math.min(mastered ? 12 : 7, PASSIVE_STACKS.getOrDefault(entity.getUUID(), 0) + 1));
               putCapped(PARADOX_MARKS, living.getUUID(), Math.max(PARADOX_MARKS.getOrDefault(living.getUUID(), 0), mastered ? 130 : 80));
               putCapped(CHRONO_RUPTURE, living.getUUID(), Math.max(CHRONO_RUPTURE.getOrDefault(living.getUUID(), 0), mastered ? 100 : 55));
               if (mastered) {
                  IstarothCommon.addTemporalDebt(living, Math.max(18.0F, (Float)amount.get() * 1.65F), entity.getUUID());
                  living.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.FUTURE_REJECTION), 80, 2, false, false, true));
                  IstarothUltimate.applyFutureRejection(entity, living, 65, 2);
                  if (frozenForceForOwner(entity.getUUID()) >= 40.0F && entity.getRandom().nextFloat() < 0.38F) {
                     releaseOwnerProjectiles(entity.getUUID());
                  }
               }

               IstarothFx.afterimage(entity, entity.position());
               IstarothFx.rejectedFutureAfterimage(living, living.position().add(living.getLookAngle().scale(2.2)));
               IstarothFx.grayPulse(entity, mastered ? 2.8F : 1.8F);
               IstarothFx.clockCrackSound(entity);
            }
         }
      }
   }

   public static void onOwnerDamagedTarget(LivingEntity owner, LivingEntity target, float damage) {
      if (!owner.level().isClientSide()) {
         if (IstarothCommon.validEnemy(owner, target)) {
            boolean mastered = MASTERED_CACHE.getOrDefault(owner.getUUID(), false);
            float lock = damage * (mastered ? 0.55F : 0.35F);
            TIME_LOCKED_WOUNDS.put(target.getUUID(), Math.min(target.getMaxHealth() * 1.5F, TIME_LOCKED_WOUNDS.getOrDefault(target.getUUID(), 0.0F) + lock));
            putCapped(TIME_LOCK_TICKS, target.getUUID(), mastered ? 260 : 180);
            IstarothCommon.addTemporalDebt(target, damage * (mastered ? 0.75F : 0.45F), owner.getUUID());
            putCapped(PARADOX_MARKS, target.getUUID(), Math.max(PARADOX_MARKS.getOrDefault(target.getUUID(), 0), mastered ? 130 : 75));
            putCapped(AGE_SENTENCE, target.getUUID(), Math.max(AGE_SENTENCE.getOrDefault(target.getUUID(), 0), mastered ? 220 : 140));
            putCapped(DEAD_SECOND_MARKS, target.getUUID(), Math.max(DEAD_SECOND_MARKS.getOrDefault(target.getUUID(), 0), mastered ? 120 : 75));
            applyCausalSuperiority(owner, target, damage, mastered);
            applyChronoRuptureOnHit(owner, target, damage, mastered);
            if (target.getHealth() - damage <= 0.0F) {
               applyBorrowedEnding(owner, target, damage, mastered);
            }
         }
      }
   }

   public static boolean shouldRejectEffect(LivingEntity entity, MobEffectInstance effect) {
      if (effect == null) {
         return false;
      }

      boolean mastered = MASTERED_CACHE.getOrDefault(entity.getUUID(), false);
      Holder<MobEffect> holder = effect.getEffect();
      boolean listed = effect.is(TensuraMobEffects.getReference(TensuraMobEffects.FEAR))
         || effect.is(TensuraMobEffects.getReference(TensuraMobEffects.INSANITY))
         || effect.is(TensuraMobEffects.getReference(TensuraMobEffects.HYPNOSIS))
         || effect.is(TensuraMobEffects.getReference(TensuraMobEffects.MIND_CONTROL))
         || effect.is(TensuraMobEffects.getReference(TensuraMobEffects.BURDEN))
         || effect.is(TensuraMobEffects.getReference(TensuraMobEffects.WEBBED))
         || effect.is(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE))
         || effect.is(TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE))
         || effect.is(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE))
         || effect.is(TensuraMobEffects.getReference(TensuraMobEffects.ENERGY_BLOCKADE))
         || effect.is(TensuraMobEffects.getReference(TensuraMobEffects.FRAGILITY))
         || effect.is(TensuraMobEffects.getReference(TensuraMobEffects.FLASHED_BLINDNESS))
         || effect.is(TensuraMobEffects.getReference(TensuraMobEffects.TRUE_BLINDNESS))
         || effect.is(TensuraMobEffects.getReference(TensuraMobEffects.SILENCE))
         || effect.is(TensuraMobEffects.getReference(TensuraMobEffects.SLEEP))
         || effect.is(TensuraMobEffects.getReference(TensuraMobEffects.PARALYSIS))
         || effect.is(TensuraMobEffects.getReference(TensuraMobEffects.PETRIFICATION))
         || effect.is(TensuraMobEffects.getReference(TensuraMobEffects.INFINITE_IMPRISONMENT))
         || effect.is(TensuraMobEffects.getReference(TensuraMobEffects.DROWSINESS));
      float randomReject = mastered ? 0.72F : 0.45F;
      boolean rejected = listed || ((MobEffect)holder.value()).getCategory() == MobEffectCategory.HARMFUL && entity.getRandom().nextFloat() < randomReject;
      if (rejected) {
         EVASION_SURGE.put(entity.getUUID(), mastered ? 55 : 30);
         PASSIVE_STACKS.put(entity.getUUID(), Math.min(mastered ? 14 : 8, PASSIVE_STACKS.getOrDefault(entity.getUUID(), 0) + 1));
         IstarothFx.grayPulse(entity, mastered ? 3.0F : 2.0F);
         IstarothFx.sound(entity, SoundEvents.AMETHYST_BLOCK_BREAK, 0.75F, 1.8F);
         Entity last = entity.getLastHurtByMob();
         if (mastered && last instanceof LivingEntity living && IstarothCommon.validEnemy(entity, living)) {
            living.addEffect(
               new MobEffectInstance(
                  effect.getEffect(), Math.min(100, Math.max(30, effect.getDuration() / 3)), Math.max(0, effect.getAmplifier() - 1), false, false, true
               )
            );
            IstarothCommon.addTemporalDebt(living, 22.0F, entity.getUUID());
            putCapped(PARADOX_MARKS, living.getUUID(), 90);
         }
      }

      return rejected;
   }

   public static void freezeProjectiles(Level level, AABB area, int ticks, LivingEntity owner, boolean redirect) {
      boolean mastered = MASTERED_CACHE.getOrDefault(owner.getUUID(), false);

      for (Projectile projectile : level.getEntitiesOfClass(Projectile.class, area, p -> p.getOwner() != owner)) {
         freezeProjectileHard(projectile, owner, ticks, redirect, mastered);
      }
   }

   public static void freezeProjectileHard(Projectile projectile, LivingEntity owner, int ticks, boolean redirectOnRelease) {
      freezeProjectileHard(projectile, owner, ticks, redirectOnRelease, false);
   }

   public static void freezeProjectileHard(Projectile projectile, LivingEntity owner, int ticks, boolean redirectOnRelease, boolean reflectOnRelease) {
      if (!projectile.level().isClientSide()) {
         if (projectile.isAlive()) {
            if (owner != null && owner.isAlive()) {
               UUID id = projectile.getUUID();
               boolean alreadyFrozen = FROZEN_PROJECTILES.containsKey(id);
               if (alreadyFrozen || countFrozenProjectiles(owner.getUUID()) < 24) {
                  Vec3 motion = projectile.getDeltaMovement();
                  double speed = Math.max(1.15, motion.length());
                  Vec3 anchor = projectile.position();
                  if (!alreadyFrozen) {
                     IstarothPassives.FrozenProjectileState state = new IstarothPassives.FrozenProjectileState(
                        owner.getUUID(),
                        projectile.getOwner() == null ? null : projectile.getOwner().getUUID(),
                        motion,
                        anchor,
                        ticks,
                        redirectOnRelease,
                        reflectOnRelease,
                        speed,
                        projectile.tickCount,
                        projectile.isNoGravity()
                     );
                     FROZEN_PROJECTILES.put(id, state);
                     FROZEN_FORCE.put(id, (float)Math.min(300.0, speed * 42.0 + projectile.getBbWidth() * 24.0 + 30.0));
                     FROZEN_PROJECTILE_BATTERY.put(owner.getUUID(), Math.min(30, FROZEN_PROJECTILE_BATTERY.getOrDefault(owner.getUUID(), 0) + 2));
                  } else {
                     IstarothPassives.FrozenProjectileState state = FROZEN_PROJECTILES.get(id);
                     state.ticks = Math.max(state.ticks, ticks);
                     state.redirectOnRelease = state.redirectOnRelease || redirectOnRelease;
                     state.reflectOnRelease = state.reflectOnRelease || reflectOnRelease;
                     state.releaseSpeed = Math.max(state.releaseSpeed, speed);
                     if (state.motion.lengthSqr() < 0.01 && motion.lengthSqr() > 0.01) {
                        state.motion = motion;
                     }
                  }

                  IstarothPassives.FrozenProjectileState state = FROZEN_PROJECTILES.get(id);
                  Vec3 hold = state == null ? anchor : state.anchor;
                  projectile.teleportTo(hold.x, hold.y, hold.z);
                  projectile.setDeltaMovement(Vec3.ZERO);
                  projectile.setNoGravity(true);
                  projectile.hasImpulse = true;
                  projectile.hurtMarked = true;
                  projectile.getPersistentData().putBoolean("IstarothFrozenProjectile", true);
                  projectile.getPersistentData().putUUID("IstarothFrozenOwner", owner.getUUID());
                  if (projectile.tickCount % 8 == 0) {
                     IstarothFx.frozenProjectileAura(projectile);
                  }
               }
            }
         }
      }
   }

   public static float frozenForceForOwner(UUID owner) {
      float total = 0.0F;

      for (Entry<UUID, IstarothPassives.FrozenProjectileState> entry : FROZEN_PROJECTILES.entrySet()) {
         IstarothPassives.FrozenProjectileState state = entry.getValue();
         if (state.owner.equals(owner)) {
            total += FROZEN_FORCE.getOrDefault(entry.getKey(), 0.0F);
         }
      }

      return total;
   }

   public static int releaseOwnerProjectiles(UUID owner) {
      int count = 0;

      for (IstarothPassives.FrozenProjectileState state : FROZEN_PROJECTILES.values()) {
         if (state.owner.equals(owner)) {
            state.ticks = Math.min(state.ticks, 2);
            state.redirectOnRelease = true;
            state.reflectOnRelease = true;
            count++;
         }
      }

      return count;
   }

   private static void tickPassiveProjectileInfinity(ManasSkillInstance instance, LivingEntity owner) {
      boolean mastered = instance.isMastered(owner);
      int interval = mastered ? 2 : 4;
      if (owner.tickCount % interval == 0) {
         double radius = mastered ? 5.0 : 2.5;
         AABB area = owner.getBoundingBox().inflate(radius);

         for (Projectile projectile : owner.level().getEntitiesOfClass(Projectile.class, area, p -> shouldCatchProjectile(owner, p, mastered))) {
            int ticks = mastered ? 120 : 80;
            freezeProjectileHard(projectile, owner, ticks, true, mastered);
            if (owner.tickCount % 12 == 0) {
               IstarothFx.glassWall(owner, projectile.position());
            }

            if (mastered && owner.tickCount % 20 == 0) {
               IstarothFx.clockDust(owner, 1.1F, 8);
            }
         }
      }
   }

   private static boolean shouldCatchProjectile(LivingEntity owner, Projectile projectile, boolean mastered) {
      if (!projectile.isAlive()) {
         return false;
      }

      if (projectile.getOwner() == owner) {
         return false;
      }

      if (projectile.getPersistentData().getBoolean("IstarothFrozenProjectile")) {
         return false;
      }

      if (PROJECTILE_REFLECT_COOLDOWN.containsKey(projectile.getUUID())) {
         return false;
      }

      double catchRadius = mastered ? 5.0 : 2.5;
      double distance = projectile.distanceTo(owner);
      if (distance <= catchRadius) {
         return true;
      }

      Vec3 motion = projectile.getDeltaMovement();
      double speed = motion.length();
      if (speed <= 0.05) {
         return false;
      }

      Vec3 toOwner = owner.position().add(0.0, owner.getBbHeight() * 0.5, 0.0).subtract(projectile.position());
      if (toOwner.lengthSqr() < 0.01) {
         return true;
      }

      double approach = motion.normalize().dot(toOwner.normalize());
      return mastered && distance <= 7.0 && approach > 0.05;
   }

   private static void tickFrozenProjectiles(Level level, UUID ownerId) {
      if (level instanceof ServerLevel serverLevel) {
         Iterator iterator = FROZEN_PROJECTILES.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<UUID, IstarothPassives.FrozenProjectileState> entry = (Entry<UUID, IstarothPassives.FrozenProjectileState>)iterator.next();
            IstarothPassives.FrozenProjectileState state = entry.getValue();
            if (state.owner.equals(ownerId)) {
               if (serverLevel.getEntity(entry.getKey()) instanceof Projectile projectile && projectile.isAlive()) {
                  LivingEntity owner = serverLevel.getEntity(state.owner) instanceof LivingEntity living && living.isAlive() ? living : null;
                  if (owner == null) {
                     releaseProjectile(serverLevel, projectile, state, null, false);
                     FROZEN_FORCE.remove(entry.getKey());
                     iterator.remove();
                  } else {
                     projectile.teleportTo(state.anchor.x, state.anchor.y, state.anchor.z);
                     projectile.setDeltaMovement(Vec3.ZERO);
                     projectile.setNoGravity(true);
                     projectile.hasImpulse = true;
                     projectile.hurtMarked = true;
                     projectile.getPersistentData().putBoolean("IstarothFrozenProjectile", true);
                     projectile.getPersistentData().putUUID("IstarothFrozenOwner", owner.getUUID());
                     if (projectile.tickCount % 8 == 0) {
                        IstarothFx.frozenProjectileAura(projectile);
                     }

                     if (projectile.tickCount % 24 == 0) {
                        IstarothFx.glassWall(owner, projectile.position());
                     }

                     if (owner.tickCount % 40 == 0) {
                        FROZEN_PROJECTILE_BATTERY.put(owner.getUUID(), Math.min(40, FROZEN_PROJECTILE_BATTERY.getOrDefault(owner.getUUID(), 0) + 1));
                     }

                     state.ticks--;
                     if (state.ticks <= 0) {
                        releaseProjectile(serverLevel, projectile, state, owner, state.reflectOnRelease);
                        FROZEN_FORCE.remove(entry.getKey());
                        iterator.remove();
                     }
                  }
               } else {
                  FROZEN_FORCE.remove(entry.getKey());
                  iterator.remove();
               }
            }
         }
      }
   }

   private static void releaseProjectile(
      ServerLevel level, Projectile projectile, IstarothPassives.FrozenProjectileState state, LivingEntity owner, boolean reflect
   ) {
      Vec3 release = state.motion;
      double speed = Math.max(1.75, state.releaseSpeed);
      if (owner != null) {
         Entity originalShooter = state.originalShooter == null ? null : level.getEntity(state.originalShooter);
         LivingEntity reflectionTarget = null;
         if (originalShooter instanceof LivingEntity shooter && shooter.isAlive() && shooter != owner) {
            reflectionTarget = shooter;
         }

         if (reflectionTarget == null && reflect) {
            reflectionTarget = findReflectionTarget(level, owner, projectile.position(), 32.0);
         }

         if (reflect && reflectionTarget != null) {
            Vec3 target = reflectionTarget.getEyePosition().subtract(projectile.position());
            release = safeDirection(target, owner.getLookAngle()).scale(Math.max(3.4, speed * 2.15));
            PROJECTILE_REFLECT_COOLDOWN.put(projectile.getUUID(), 14);
            IstarothCommon.addTemporalDebt(reflectionTarget, (float)Math.min(110.0, speed * 16.0 + 26.0), owner.getUUID());
            reflectionTarget.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), 70, 2, false, false, true));
            putCapped(PARADOX_MARKS, reflectionTarget.getUUID(), Math.max(PARADOX_MARKS.getOrDefault(reflectionTarget.getUUID(), 0), 90));
         } else if (state.redirectOnRelease) {
            Vec3 away = projectile.position().subtract(owner.position());
            release = safeDirection(away, owner.getLookAngle()).scale(Math.max(1.65, speed * 1.1));
         } else if (release.lengthSqr() < 0.01) {
            release = safeDirection(projectile.position().subtract(owner.position()), owner.getLookAngle()).scale(1.4);
         }
      } else if (release.lengthSqr() < 0.01) {
         release = new Vec3(0.0, -0.08, 0.0);
      }

      projectile.setNoGravity(state.originalNoGravity);
      projectile.setDeltaMovement(release);
      projectile.hasImpulse = true;
      projectile.hurtMarked = true;
      projectile.getPersistentData().remove("IstarothFrozenProjectile");
      projectile.getPersistentData().remove("IstarothFrozenOwner");
      Vec3 pos = projectile.position();
      level.sendParticles(ParticleTypes.EXPLOSION, pos.x, pos.y, pos.z, reflect ? 2 : 1, 0.0, 0.0, 0.0, 0.0);
      level.sendParticles(ParticleTypes.END_ROD, pos.x, pos.y, pos.z, reflect ? 36 : 16, 0.25, 0.25, 0.25, reflect ? 0.16 : 0.04);
      level.sendParticles(ParticleTypes.WHITE_ASH, pos.x, pos.y, pos.z, reflect ? 54 : 22, 0.38, 0.38, 0.38, reflect ? 0.16 : 0.06);
      level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, reflect ? 1.4F : 0.7F, reflect ? 0.14F : 0.3F);
      level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS, reflect ? 1.0F : 0.35F, reflect ? 1.95F : 1.45F);
   }

   private static LivingEntity findReflectionTarget(ServerLevel level, LivingEntity owner, Vec3 projectilePos, double range) {
      LivingEntity best = null;
      double bestDistance = Double.MAX_VALUE;

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, owner.getBoundingBox().inflate(range), e -> IstarothCommon.validEnemy(owner, e))) {
         double distance = target.position().distanceToSqr(projectilePos);
         if (distance < bestDistance) {
            bestDistance = distance;
            best = target;
         }
      }

      return best;
   }

   private static void tickProjectileReflectCooldown() {
      if (!PROJECTILE_REFLECT_COOLDOWN.isEmpty()) {
         Iterator<Entry<UUID, Integer>> iterator = PROJECTILE_REFLECT_COOLDOWN.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<UUID, Integer> entry = iterator.next();
            int ticks = entry.getValue() - 1;
            if (ticks <= 0) {
               iterator.remove();
            } else {
               entry.setValue(ticks);
            }
         }
      }
   }

   private static void applyUnwrittenDeath(LivingEntity owner, DamageSource source, Changeable<Float> amount, boolean mastered) {
      if (!((Float)amount.get() <= 0.0F)) {
         if (!(owner.getHealth() - (Float)amount.get() > 0.0F)) {
            if (UNWRITTEN_DEATH_COOLDOWN.getOrDefault(owner.getUUID(), 0) <= 0) {
               float nearbyDebt = 0.0F;

               for (LivingEntity target : IstarothCommon.nearbyLiving(owner, mastered ? 20.0 : 13.0)) {
                  if (IstarothCommon.validEnemy(owner, target)) {
                     nearbyDebt += IstarothCommon.getDebt(target);
                  }
               }

               boolean enoughDebt = nearbyDebt >= (mastered ? 60.0F : 100.0F);
               boolean randomMercy = mastered && owner.getRandom().nextFloat() < 0.35F;
               if (enoughDebt || randomMercy) {
                  amount.set(0.0F);
                  owner.setHealth(Math.max(1.0F, owner.getMaxHealth() * (mastered ? 0.22F : 0.12F)));
                  owner.invulnerableTime = mastered ? 90 : 55;
                  UNWRITTEN_DEATH_COOLDOWN.put(owner.getUUID(), mastered ? 2400 : 3600);
                  KING_OF_ONE_SECOND.put(owner.getUUID(), mastered ? 180 : 100);
                  owner.addEffect(
                     new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.ETERNAL_MOMENT), mastered ? 120 : 70, mastered ? 1 : 0, false, false, true)
                  );
                  IstarothFx.clockShatter(owner, mastered ? 7.5F : 5.0F);
                  IstarothFx.timeStopScreen(owner, mastered ? 22.0 : 16.0, mastered ? 80 : 55, 6);
                  IstarothFx.sound(owner, (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), 1.5F, 0.2F);
               }
            }
         }
      }
   }

   private static void applyTimelineSovereignty(LivingEntity owner, DamageSource source, Changeable<Float> amount, boolean mastered) {
      UUID id = owner.getUUID();
      String key = source.getMsgId();
      String last = LAST_DAMAGE_KEY.get(id);
      int hits = key.equals(last) ? SAME_TIMELINE_HITS.getOrDefault(id, 0) + 1 : 1;
      LAST_DAMAGE_KEY.put(id, key);
      SAME_TIMELINE_HITS.put(id, Math.min(6, hits));
      TIMELINE_SOVEREIGNTY_TICKS.put(id, 160);

      float reduction = switch (Math.min(6, hits)) {
         case 1 -> mastered ? 0.1F : 0.05F;
         case 2 -> mastered ? 0.24F : 0.14F;
         case 3 -> mastered ? 0.38F : 0.24F;
         case 4 -> mastered ? 0.52F : 0.34F;
         default -> mastered ? 0.66F : 0.44F;
      };
      amount.set((Float)amount.get() * (1.0F - reduction));
      if (mastered && hits >= 3 && source.getEntity() instanceof LivingEntity living && IstarothCommon.validEnemy(owner, living)) {
         float debt = Math.max(12.0F, (Float)amount.get() * 0.65F);
         IstarothCommon.addTemporalDebt(living, debt, owner.getUUID());
         living.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.FUTURE_REJECTION), 55, 1, false, false, true));
         putCapped(PARADOX_MARKS, living.getUUID(), 90);
      }

      if (hits >= 2 && owner.tickCount % 8 == 0) {
         IstarothFx.grayPulse(owner, mastered ? 2.3F : 1.6F);
      }
   }

   private static void tickTimelineSovereignty(LivingEntity owner) {
      UUID id = owner.getUUID();
      Integer ticks = TIMELINE_SOVEREIGNTY_TICKS.get(id);
      if (ticks != null) {
         if (ticks <= 0) {
            TIMELINE_SOVEREIGNTY_TICKS.remove(id);
            SAME_TIMELINE_HITS.remove(id);
            LAST_DAMAGE_KEY.remove(id);
         } else {
            TIMELINE_SOVEREIGNTY_TICKS.put(id, ticks - 1);
         }
      }
   }

   private static void tickTemporalIsolation(LivingEntity owner, boolean mastered) {
      int interval = mastered ? 40 : 60;
      if (owner.tickCount % interval == 0) {
         int removed = 0;
         int maxRemove = mastered ? 2 : 1;

         for (MobEffectInstance effect : owner.getActiveEffects().stream().toList()) {
            if (removed >= maxRemove) {
               break;
            }

            if (((MobEffect)effect.getEffect().value()).getCategory() == MobEffectCategory.HARMFUL) {
               owner.removeEffect(effect.getEffect());
               removed++;
               EVASION_SURGE.put(owner.getUUID(), mastered ? 55 : 30);
               PASSIVE_STACKS.put(owner.getUUID(), Math.min(mastered ? 14 : 8, PASSIVE_STACKS.getOrDefault(owner.getUUID(), 0) + 1));
               IstarothFx.grayPulse(owner, mastered ? 3.0F : 2.0F);
               IstarothFx.clockCrackSound(owner);
            }
         }
      }
   }

   private static void tickStolenDuration(LivingEntity owner, boolean mastered) {
      int interval = mastered ? 70 : 100;
      if (owner.tickCount % interval == 0) {
         int beneficialGain = mastered ? 80 : 45;
         int harmfulLoss = mastered ? 95 : 55;
         int enemyLoss = mastered ? 75 : 45;

         for (MobEffectInstance effect : owner.getActiveEffects().stream().toList()) {
            if (((MobEffect)effect.getEffect().value()).getCategory() == MobEffectCategory.BENEFICIAL
               && !effect.is(ModMobEffects.getReference(ModMobEffects.TEMPORAL_SCREEN))) {
               owner.removeEffect(effect.getEffect());
               owner.addEffect(
                  new MobEffectInstance(
                     effect.getEffect(),
                     effect.getDuration() + beneficialGain,
                     effect.getAmplifier(),
                     effect.isAmbient(),
                     effect.isVisible(),
                     effect.showIcon()
                  )
               );
            } else if (((MobEffect)effect.getEffect().value()).getCategory() == MobEffectCategory.HARMFUL) {
               int duration = Math.max(1, effect.getDuration() - harmfulLoss);
               owner.removeEffect(effect.getEffect());
               owner.addEffect(
                  new MobEffectInstance(effect.getEffect(), duration, effect.getAmplifier(), effect.isAmbient(), effect.isVisible(), effect.showIcon())
               );
            }
         }

         double radius = mastered ? 16.0 : 12.0;

         for (LivingEntity target : IstarothCommon.nearbyLiving(owner, radius)) {
            if (IstarothCommon.validEnemy(owner, target)) {
               for (MobEffectInstance effect : target.getActiveEffects().stream().toList()) {
                  if (((MobEffect)effect.getEffect().value()).getCategory() == MobEffectCategory.BENEFICIAL) {
                     int duration = Math.max(1, effect.getDuration() - enemyLoss);
                     target.removeEffect(effect.getEffect());
                     target.addEffect(
                        new MobEffectInstance(effect.getEffect(), duration, effect.getAmplifier(), effect.isAmbient(), effect.isVisible(), effect.showIcon())
                     );
                     IstarothCommon.addTemporalDebt(target, mastered ? 9.0F : 5.0F, owner.getUUID());
                     putCapped(PARADOX_MARKS, target.getUUID(), Math.max(PARADOX_MARKS.getOrDefault(target.getUUID(), 0), mastered ? 100 : 60));
                     if (target.tickCount % 20 == 0) {
                        IstarothFx.clockDust(target, mastered ? 1.5F : 1.0F, mastered ? 14 : 8);
                     }
                  }
               }
            }
         }
      }
   }

   private static void tickChronoPrediction(LivingEntity owner, boolean mastered) {
      if (owner.tickCount % (mastered ? 8 : 12) == 0) {
         double radius = mastered ? 18.0 : 14.0;

         for (LivingEntity target : IstarothCommon.nearbyLiving(owner, radius)) {
            if (IstarothCommon.validEnemy(owner, target)) {
               Vec3 toOwner = owner.position().subtract(target.position());
               if (!(toOwner.lengthSqr() < 0.01)) {
                  boolean movingFast = target.getDeltaMovement().length() > (mastered ? 0.42 : 0.65);
                  boolean aiming = target.getLookAngle().dot(toOwner.normalize()) > (mastered ? 0.68 : 0.82);
                  boolean threatening = movingFast || aiming;
                  if (threatening && owner.getRandom().nextFloat() < (mastered ? 0.46F : 0.25F)) {
                     Vec3 motion = target.getDeltaMovement();
                     Vec3 predicted = motion.lengthSqr() < 0.01
                        ? target.position().add(target.getLookAngle().scale(mastered ? 2.6 : 1.5))
                        : target.position().add(motion.normalize().scale(mastered ? 2.8 : 1.5));
                     if (target.tickCount % 20 == 0) {
                        IstarothFx.rejectedFutureAfterimage(target, predicted);
                     }

                     if (mastered && target.tickCount % 24 == 0) {
                        target.setDeltaMovement(target.getDeltaMovement().scale(0.66));
                        target.hurtMarked = true;
                        target.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.FUTURE_REJECTION), 50, 1, false, false, true));
                        IstarothCommon.addTemporalDebt(target, 8.0F, owner.getUUID());
                        putCapped(PARADOX_MARKS, target.getUUID(), Math.max(PARADOX_MARKS.getOrDefault(target.getUUID(), 0), 70));
                     }
                  }
               }
            }
         }
      }
   }

   private static void tickPassiveCooldownRecovery(ManasSkillInstance instance, LivingEntity owner, boolean mastered) {
      int interval = mastered ? 45 : 70;
      if (owner.tickCount % interval == 0) {
         int stacks = PASSIVE_STACKS.getOrDefault(owner.getUUID(), 0);
         int frozenCount = countFrozenProjectiles(owner.getUUID());
         int reduction = mastered ? 3 : 1;
         if (stacks >= 4) {
            reduction += mastered ? 2 : 1;
         }

         if (frozenCount >= 3) {
            reduction += mastered ? 3 : 1;
         }

         if (TEMPORAL_CROWN.containsKey(owner.getUUID())) {
            reduction += mastered ? 2 : 1;
         }

         if (FROZEN_PROJECTILE_BATTERY.getOrDefault(owner.getUUID(), 0) >= 10) {
            reduction += mastered ? 3 : 1;
         }

         if (KING_OF_ONE_SECOND.containsKey(owner.getUUID())) {
            reduction += mastered ? 4 : 2;
         }

         for (int i = 0; i < instance.getModes(); i++) {
            int cd = instance.getCoolDown(i);
            if (cd > 0) {
               instance.setCoolDown(Math.max(0, cd - reduction), i);
            }
         }

         if (stacks > 0) {
            PASSIVE_STACKS.put(owner.getUUID(), Math.max(0, stacks - 1));
         }

         if (mastered && owner.tickCount % 90 == 0) {
            IstarothFx.clockDust(owner, 1.4F, 12);
         }
      }
   }

   private static void tickEvasionSurge(LivingEntity owner, boolean mastered) {
      UUID id = owner.getUUID();
      Integer ticks = EVASION_SURGE.get(id);
      if (ticks != null) {
         if (ticks <= 0) {
            EVASION_SURGE.remove(id);
         } else {
            EVASION_SURGE.put(id, ticks - 1);
            if (owner.tickCount % 16 == 0) {
               Vec3 back = owner.position().subtract(owner.getDeltaMovement().scale(mastered ? 4.0 : 2.5));
               IstarothFx.afterimage(owner, back);
            }

            if (mastered && owner.tickCount % 24 == 0) {
               IstarothFx.clockDust(owner, 1.2F, 10);
            }
         }
      }
   }

   private static void tickTemporalCrown(LivingEntity owner, boolean mastered) {
      UUID id = owner.getUUID();
      Integer ticks = TEMPORAL_CROWN.get(id);
      if (ticks != null) {
         if (ticks <= 0) {
            TEMPORAL_CROWN.remove(id);
         } else {
            TEMPORAL_CROWN.put(id, ticks - 1);
            if (owner.tickCount % 20 == 0) {
               IstarothFx.followClockCircle(owner, mastered ? 3.1F : 2.3F, 24, MagicCircleVariant.SPACE);
               IstarothFx.grayPulse(owner, mastered ? 2.2F : 1.5F);
            }

            if (owner.tickCount % 30 == 0) {
               for (LivingEntity target : IstarothCommon.nearbyLiving(owner, mastered ? 12.0 : 8.0)) {
                  if (IstarothCommon.validEnemy(owner, target)) {
                     target.setDeltaMovement(target.getDeltaMovement().scale(mastered ? 0.34 : 0.55));
                     target.hurtMarked = true;
                     target.addEffect(
                        new MobEffectInstance(
                           ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), mastered ? 70 : 40, mastered ? 2 : 1, false, false, true
                        )
                     );
                     IstarothCommon.addTemporalDebt(target, mastered ? 18.0F : 8.0F, owner.getUUID());
                     putCapped(PARADOX_MARKS, target.getUUID(), Math.max(PARADOX_MARKS.getOrDefault(target.getUUID(), 0), mastered ? 85 : 45));
                     IstarothFx.clockDust(target, mastered ? 1.6F : 1.0F, mastered ? 16 : 8);
                  }
               }
            }
         }
      }
   }

   private static void tickSovereignTimeAuthority(LivingEntity owner, boolean mastered) {
      int interval = mastered ? 45 : 70;
      if (owner.tickCount % interval == 0) {
         double radius = mastered ? 18.0 : 11.0;
         int affected = 0;

         for (LivingEntity target : IstarothCommon.nearbyLiving(owner, radius)) {
            if (IstarothCommon.validEnemy(owner, target)) {
               Vec3 toOwner = owner.position().subtract(target.position());
               if (!(toOwner.lengthSqr() < 0.01)) {
                  boolean lookingAtOwner = target.getLookAngle().dot(toOwner.normalize()) > (mastered ? 0.55 : 0.75);
                  boolean rushing = target.getDeltaMovement().length() > (mastered ? 0.32 : 0.55);
                  boolean close = target.distanceTo(owner) <= (mastered ? 7.0F : 4.5F);
                  if (lookingAtOwner || rushing || close) {
                     affected++;
                     target.setDeltaMovement(target.getDeltaMovement().scale(mastered ? 0.48 : 0.72));
                     target.hurtMarked = true;
                     target.addEffect(
                        new MobEffectInstance(
                           ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), mastered ? 75 : 40, mastered ? 2 : 1, false, false, true
                        )
                     );
                     IstarothCommon.addTemporalDebt(target, mastered ? 16.0F : 7.0F, owner.getUUID());
                     putCapped(PARADOX_MARKS, target.getUUID(), Math.max(PARADOX_MARKS.getOrDefault(target.getUUID(), 0), mastered ? 95 : 55));
                     if (target.tickCount % 30 == 0) {
                        IstarothFx.rejectedFutureAfterimage(target, target.position().add(target.getLookAngle().scale(mastered ? 3.5 : 2.0)));
                     }

                     if (mastered && affected <= 3) {
                        IstarothUltimate.applyFutureRejection(owner, target, 60, 2);
                        IstarothUltimate.applyEventSilence(owner, target, 50, 1);
                     }
                  }
               }
            }
         }

         if (affected > 0) {
            PASSIVE_STACKS.put(owner.getUUID(), Math.min(mastered ? 14 : 8, PASSIVE_STACKS.getOrDefault(owner.getUUID(), 0) + affected));
            if (mastered) {
               IstarothFx.grayPulse(owner, 3.0F);
            }
         }
      }
   }

   private static void tickTimeTax(LivingEntity owner, boolean mastered) {
      int interval = mastered ? 30 : 45;
      if (owner.tickCount % interval == 0) {
         double radius = mastered ? 14.0 : 9.0;

         for (LivingEntity target : IstarothCommon.nearbyLiving(owner, radius)) {
            if (IstarothCommon.validEnemy(owner, target)) {
               UUID id = target.getUUID();
               int cooldown = TIME_TAX_COOLDOWN.getOrDefault(id, 0);
               if (cooldown > 0) {
                  TIME_TAX_COOLDOWN.put(id, cooldown - interval);
               } else {
                  boolean moving = target.getDeltaMovement().lengthSqr() > 0.012;
                  boolean hasTemporalEffect = target.hasEffect(ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER))
                     || target.hasEffect(ModMobEffects.getReference(ModMobEffects.FUTURE_REJECTION))
                     || target.hasEffect(ModMobEffects.getReference(ModMobEffects.TIMELINE_VERDICT))
                     || target.hasEffect(ModMobEffects.getReference(ModMobEffects.CAUSALITY_LOOP))
                     || target.hasEffect(ModMobEffects.getReference(ModMobEffects.CONDEMNED_REPEAT))
                     || target.hasEffect(ModMobEffects.getReference(ModMobEffects.BORROWED_SECONDS))
                     || target.hasEffect(ModMobEffects.getReference(ModMobEffects.DELAYED_VERDICT));
                  boolean hasDebt = IstarothCommon.hasDebt(target);
                  if (moving || hasTemporalEffect || hasDebt) {
                     float damage = mastered ? 9.0F : 4.0F;
                     if (hasDebt) {
                        damage += mastered ? 5.0F : 2.0F;
                     }

                     if (moving) {
                        damage += mastered ? 4.0F : 2.0F;
                     }

                     target.invulnerableTime = 0;
                     target.hurt(IstarothCommon.damageSource(owner), damage);
                     target.addEffect(
                        new MobEffectInstance(
                           ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), mastered ? 45 : 25, mastered ? 1 : 0, false, false, true
                        )
                     );
                     IstarothCommon.addTemporalDebt(target, damage * 0.75F, owner.getUUID());
                     putCapped(PARADOX_MARKS, target.getUUID(), Math.max(PARADOX_MARKS.getOrDefault(target.getUUID(), 0), mastered ? 80 : 40));
                     TIME_TAX_COOLDOWN.put(id, mastered ? 36 : 56);
                     IstarothFx.clockDust(target, mastered ? 1.3F : 0.9F, mastered ? 12 : 7);
                  }
               }
            }
         }
      }
   }

   private static void tickFrozenProjectileBattery(ManasSkillInstance instance, LivingEntity owner, boolean mastered) {
      UUID id = owner.getUUID();
      int battery = FROZEN_PROJECTILE_BATTERY.getOrDefault(id, 0);
      if (battery > 0) {
         if (owner.tickCount % 30 == 0) {
            int reduction = mastered ? Math.min(5, battery / 4) : Math.min(2, battery / 7);
            if (reduction > 0) {
               for (int i = 0; i < instance.getModes(); i++) {
                  int cd = instance.getCoolDown(i);
                  if (cd > 0) {
                     instance.setCoolDown(Math.max(0, cd - reduction), i);
                  }
               }

               IstarothFx.clockDust(owner, mastered ? 1.5F : 1.1F, mastered ? 16 : 9);
            }

            FROZEN_PROJECTILE_BATTERY.put(id, Math.max(0, battery - 1));
         }

         if (mastered && battery >= 12 && owner.tickCount % 60 == 0) {
            owner.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.ETERNAL_MOMENT), 45, 0, false, false, true));
         }
      }
   }

   private static void tickParadoxMarks(LivingEntity owner, boolean mastered) {
      if (owner.level() instanceof ServerLevel serverLevel) {
         if (owner.tickCount % (mastered ? 10 : 15) == 0) {
            Iterator<Entry<UUID, Integer>> iterator = PARADOX_MARKS.entrySet().iterator();

            while (iterator.hasNext()) {
               Entry<UUID, Integer> entry = iterator.next();
               if (serverLevel.getEntity(entry.getKey()) instanceof LivingEntity living && living.isAlive()) {
                  int ticks = entry.getValue();
                  if (ticks <= 0) {
                     iterator.remove();
                  } else {
                     if (living.tickCount % (mastered ? 30 : 45) == 0 && IstarothCommon.validEnemy(owner, living)) {
                        float damage = mastered ? 12.0F : 5.0F;
                        living.invulnerableTime = 0;
                        living.hurt(IstarothCommon.damageSource(owner), damage);
                        living.setDeltaMovement(living.getDeltaMovement().scale(mastered ? 0.72 : 0.82));
                        living.hurtMarked = true;
                        IstarothCommon.addTemporalDebt(living, damage * 0.8F, owner.getUUID());
                        IstarothFx.rejectedFutureAfterimage(living, living.position().add(living.getLookAngle().scale(mastered ? 2.4 : 1.4)));
                     }

                     entry.setValue(ticks - (mastered ? 10 : 15));
                  }
               } else {
                  iterator.remove();
               }
            }
         }
      }
   }

   private static void tickStolenTomorrow(LivingEntity owner, boolean mastered) {
      if (owner.tickCount % 20 == 0) {
         double radius = mastered ? 18.0 : 12.0;

         for (LivingEntity target : IstarothCommon.nearbyLiving(owner, radius)) {
            if (IstarothCommon.validEnemy(owner, target)) {
               UUID id = target.getUUID();
               float last = LAST_HEALTH.getOrDefault(id, target.getHealth());
               float now = target.getHealth();
               if (now > last + 0.5F) {
                  float healed = now - last;
                  float stolen = healed * (mastered ? 0.65F : 0.38F);
                  IstarothCommon.addTemporalDebt(target, stolen * 1.6F, owner.getUUID());
                  TIME_LOCKED_WOUNDS.put(id, Math.min(target.getMaxHealth(), TIME_LOCKED_WOUNDS.getOrDefault(id, 0.0F) + stolen));
                  putCapped(TIME_LOCK_TICKS, id, mastered ? 220 : 150);
                  owner.heal(Math.min(stolen * 0.35F, mastered ? 10.0F : 5.0F));
                  owner.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.ETERNAL_MOMENT), mastered ? 45 : 25, 0, false, false, true));
                  IstarothFx.line(owner.level(), target.getEyePosition(), owner.getEyePosition(), ParticleTypes.WHITE_ASH);
                  IstarothFx.clockDust(target, mastered ? 1.6F : 1.0F, mastered ? 18 : 9);
               }

               LAST_HEALTH.put(id, now);
            }
         }
      }
   }

   private static void tickSecondHandDominion(LivingEntity owner, boolean mastered) {
      UUID ownerId = owner.getUUID();
      Integer ticks = SECOND_HAND_TICKS.get(ownerId);
      if (ticks != null && ticks > 0 && SECOND_HAND_TARGET.containsKey(ownerId)) {
         SECOND_HAND_TICKS.put(ownerId, ticks - 1);
         if (owner.level() instanceof ServerLevel serverLevel) {
            if (serverLevel.getEntity(SECOND_HAND_TARGET.get(ownerId)) instanceof LivingEntity target
               && target.isAlive()
               && IstarothCommon.validEnemy(owner, target)) {
               if (target.tickCount % (mastered ? 18 : 26) == 0) {
                  Vec3 motion = target.getDeltaMovement();
                  target.setDeltaMovement(motion.scale(mastered ? -0.18 : 0.12));
                  target.hurtMarked = true;
                  target.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), mastered ? 35 : 22, 1, false, false, true));
                  IstarothCommon.addTemporalDebt(target, mastered ? 10.0F : 5.0F, owner.getUUID());
                  IstarothFx.rejectedFutureAfterimage(target, target.position().add(target.getLookAngle().scale(mastered ? 2.2 : 1.2)));
               }
            } else {
               SECOND_HAND_TARGET.remove(ownerId);
               SECOND_HAND_TICKS.remove(ownerId);
            }
         }
      } else if (owner.tickCount % 40 == 0) {
         LivingEntity chosen = null;
         double best = Double.MAX_VALUE;

         for (LivingEntity target : IstarothCommon.nearbyLiving(owner, mastered ? 18.0 : 12.0)) {
            if (IstarothCommon.validEnemy(owner, target)) {
               double score = target.distanceToSqr(owner) - IstarothCommon.getDebt(target);
               if (score < best) {
                  best = score;
                  chosen = target;
               }
            }
         }

         if (chosen != null) {
            SECOND_HAND_TARGET.put(ownerId, chosen.getUUID());
            SECOND_HAND_TICKS.put(ownerId, mastered ? 160 : 100);
            chosen.addEffect(
               new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.FUTURE_REJECTION), mastered ? 90 : 55, mastered ? 1 : 0, false, false, true)
            );
            IstarothFx.verdictClock(chosen, Math.max(2.2F, chosen.getBbWidth() * 3.4F), 55);
         }
      }
   }

   private static void tickAgingOfPower(LivingEntity owner, boolean mastered) {
      int interval = mastered ? 60 : 90;
      if (owner.tickCount % interval == 0) {
         for (LivingEntity target : IstarothCommon.nearbyLiving(owner, mastered ? 17.0 : 11.0)) {
            if (IstarothCommon.validEnemy(owner, target)) {
               MobEffectInstance strongest = null;

               for (MobEffectInstance effect : target.getActiveEffects().stream().toList()) {
                  if (((MobEffect)effect.getEffect().value()).getCategory() == MobEffectCategory.BENEFICIAL
                     && (strongest == null || effect.getAmplifier() > strongest.getAmplifier() || effect.getDuration() > strongest.getDuration())) {
                     strongest = effect;
                  }
               }

               if (strongest != null) {
                  int loss = mastered ? 120 : 70;
                  int newDuration = strongest.getDuration() - loss;
                  target.removeEffect(strongest.getEffect());
                  if (newDuration > 10) {
                     target.addEffect(
                        new MobEffectInstance(
                           strongest.getEffect(), newDuration, strongest.getAmplifier(), strongest.isAmbient(), strongest.isVisible(), strongest.showIcon()
                        )
                     );
                  } else {
                     float burst = mastered ? 26.0F : 14.0F;
                     target.invulnerableTime = 0;
                     target.hurt(IstarothCommon.damageSource(owner), burst);
                     IstarothCommon.addTemporalDebt(target, burst * 1.25F, owner.getUUID());
                     if (mastered) {
                        owner.addEffect(
                           new MobEffectInstance(
                              strongest.getEffect(),
                              Math.min(120, Math.max(40, strongest.getDuration() / 3)),
                              Math.max(0, strongest.getAmplifier() - 1),
                              false,
                              false,
                              true
                           )
                        );
                     }

                     IstarothFx.clockShatter(target, mastered ? 3.0F : 2.0F);
                  }
               }
            }
         }
      }
   }

   private static void tickClockworkExecution(LivingEntity owner, boolean mastered) {
      if (owner.level() instanceof ServerLevel serverLevel) {
         if (owner.tickCount % 20 == 0) {
            for (LivingEntity target : IstarothCommon.nearbyLiving(owner, mastered ? 22.0 : 15.0)) {
               if (IstarothCommon.validEnemy(owner, target)) {
                  float hpRatio = target.getHealth() / Math.max(1.0F, target.getMaxHealth());
                  float debt = IstarothCommon.getDebt(target);
                  if (hpRatio <= (mastered ? 0.28F : 0.2F) && debt >= (mastered ? 45.0F : 70.0F)) {
                     EXECUTION_MARKS.putIfAbsent(target.getUUID(), mastered ? 50 : 65);
                     target.addEffect(
                        new MobEffectInstance(
                           ModMobEffects.getReference(ModMobEffects.TIMELINE_VERDICT), mastered ? 65 : 50, mastered ? 2 : 1, false, false, true
                        )
                     );
                     if (target.tickCount % 40 == 0) {
                        IstarothFx.verdictClock(target, Math.max(2.8F, target.getBbWidth() * 4.0F), 35);
                     }
                  }
               }
            }

            Iterator<Entry<UUID, Integer>> iterator = EXECUTION_MARKS.entrySet().iterator();

            while (iterator.hasNext()) {
               Entry<UUID, Integer> entry = iterator.next();
               if (serverLevel.getEntity(entry.getKey()) instanceof LivingEntity target && target.isAlive()) {
                  int ticks = entry.getValue();
                  if (!IstarothCommon.validEnemy(owner, target)) {
                     iterator.remove();
                  } else if (ticks <= 0) {
                     float debt = IstarothCommon.consumeDebt(target, owner.getUUID());
                     float damage = (mastered ? 65.0F : 40.0F)
                        + debt * (mastered ? 0.9F : 0.55F)
                        + target.getMaxHealth() * (target instanceof Player ? 0.08F : (mastered ? 0.18F : 0.12F));
                     target.invulnerableTime = 0;
                     target.hurt(IstarothCommon.damageSource(owner), damage);
                     target.addEffect(
                        new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TIMELINE_VERDICT), 90, mastered ? 3 : 2, false, false, true)
                     );
                     IstarothFx.judgementStreak(target, mastered ? 13.0F : 9.0F, mastered ? 16 : 10);
                     IstarothFx.clockShatter(target, mastered ? 4.8F : 3.2F);
                     iterator.remove();
                  } else {
                     entry.setValue(ticks - 20);
                  }
               } else {
                  iterator.remove();
               }
            }
         }
      }
   }

   private static void tickTimeLockedWounds(LivingEntity owner, boolean mastered) {
      if (owner.level() instanceof ServerLevel serverLevel) {
         if (owner.tickCount % 10 == 0) {
            Iterator<Entry<UUID, Integer>> iterator = TIME_LOCK_TICKS.entrySet().iterator();

            while (iterator.hasNext()) {
               Entry<UUID, Integer> entry = iterator.next();
               if (serverLevel.getEntity(entry.getKey()) instanceof LivingEntity target && target.isAlive()) {
                  int ticks = entry.getValue();
                  float locked = TIME_LOCKED_WOUNDS.getOrDefault(entry.getKey(), 0.0F);
                  if (ticks > 0 && !(locked <= 0.0F)) {
                     if (target.tickCount % 40 == 0 && IstarothCommon.validEnemy(owner, target)) {
                        float chip = Math.min(mastered ? 10.0F : 5.0F, locked * 0.08F);
                        target.invulnerableTime = 0;
                        target.hurt(IstarothCommon.damageSource(owner), chip);
                        IstarothCommon.addTemporalDebt(target, chip, owner.getUUID());
                        IstarothFx.clockDust(target, mastered ? 1.2F : 0.8F, mastered ? 12 : 7);
                     }

                     entry.setValue(ticks - 10);
                  } else {
                     TIME_LOCKED_WOUNDS.remove(entry.getKey());
                     iterator.remove();
                  }
               } else {
                  TIME_LOCKED_WOUNDS.remove(entry.getKey());
                  iterator.remove();
               }
            }
         }
      }
   }

   private static void tickAgeSentence(LivingEntity owner, boolean mastered) {
      if (owner.level() instanceof ServerLevel serverLevel) {
         if (owner.tickCount % 25 == 0) {
            Iterator<Entry<UUID, Integer>> iterator = AGE_SENTENCE.entrySet().iterator();

            while (iterator.hasNext()) {
               Entry<UUID, Integer> entry = iterator.next();
               if (serverLevel.getEntity(entry.getKey()) instanceof LivingEntity target && target.isAlive()) {
                  int ticks = entry.getValue();
                  if (ticks > 0 && IstarothCommon.validEnemy(owner, target)) {
                     float damage = mastered ? 8.0F : 3.5F;
                     float debtBonus = Math.min(mastered ? 14.0F : 6.0F, IstarothCommon.getDebt(target) * 0.06F);
                     target.invulnerableTime = 0;
                     target.hurt(IstarothCommon.damageSource(owner), damage + debtBonus);
                     target.setDeltaMovement(target.getDeltaMovement().scale(mastered ? 0.7 : 0.82));
                     target.hurtMarked = true;
                     if (target.tickCount % 50 == 0) {
                        target.addEffect(
                           new MobEffectInstance(
                              ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), mastered ? 70 : 40, mastered ? 2 : 1, false, false, true
                           )
                        );
                        IstarothFx.clockDust(target, mastered ? 1.4F : 0.9F, mastered ? 14 : 8);
                     }

                     entry.setValue(ticks - 25);
                  } else {
                     iterator.remove();
                  }
               } else {
                  iterator.remove();
               }
            }
         }
      }
   }

   private static void tickChronoRupture(LivingEntity owner, boolean mastered) {
      if (owner.level() instanceof ServerLevel serverLevel) {
         if (owner.tickCount % 20 == 0) {
            Iterator<Entry<UUID, Integer>> iterator = CHRONO_RUPTURE.entrySet().iterator();

            while (iterator.hasNext()) {
               Entry<UUID, Integer> entry = iterator.next();
               if (serverLevel.getEntity(entry.getKey()) instanceof LivingEntity target && target.isAlive()) {
                  int ticks = entry.getValue();
                  if (ticks > 0 && IstarothCommon.validEnemy(owner, target)) {
                     float velocity = (float)target.getDeltaMovement().length();
                     float damage = (mastered ? 6.0F : 2.5F) + Math.min(mastered ? 18.0F : 8.0F, velocity * (mastered ? 18.0F : 10.0F));
                     target.invulnerableTime = 0;
                     target.hurt(IstarothCommon.damageSource(owner), damage);
                     IstarothCommon.addTemporalDebt(target, damage * 0.65F, owner.getUUID());
                     if (velocity > 0.35F) {
                        target.setDeltaMovement(target.getDeltaMovement().scale(mastered ? 0.25 : 0.45));
                        target.hurtMarked = true;
                     }

                     if (target.tickCount % 40 == 0) {
                        IstarothFx.clockShatter(target, mastered ? 2.4F : 1.5F);
                     }

                     entry.setValue(ticks - 20);
                  } else {
                     iterator.remove();
                  }
               } else {
                  iterator.remove();
               }
            }
         }
      }
   }

   private static void tickEventHorizon(LivingEntity owner, boolean mastered) {
      if (owner.tickCount % (mastered ? 40 : 60) == 0) {
         double radius = mastered ? 15.0 : 9.0;

         for (LivingEntity target : IstarothCommon.nearbyLiving(owner, radius)) {
            if (IstarothCommon.validEnemy(owner, target)) {
               boolean marked = PARADOX_MARKS.containsKey(target.getUUID())
                  || AGE_SENTENCE.containsKey(target.getUUID())
                  || CHRONO_RUPTURE.containsKey(target.getUUID())
                  || DEAD_SECOND_MARKS.containsKey(target.getUUID())
                  || IstarothCommon.hasDebt(target);
               if (marked) {
                  float damage = mastered ? 14.0F : 6.0F;
                  damage += Math.min(mastered ? 20.0F : 8.0F, IstarothCommon.getDebt(target) * 0.05F);
                  target.invulnerableTime = 0;
                  target.hurt(IstarothCommon.damageSource(owner), damage);
                  target.addEffect(
                     new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TIMELINE_VERDICT), mastered ? 55 : 35, mastered ? 1 : 0, false, false, true)
                  );
                  target.setDeltaMovement(target.getDeltaMovement().scale(mastered ? 0.38 : 0.55));
                  target.hurtMarked = true;
                  IstarothCommon.addTemporalDebt(target, damage * 0.55F, owner.getUUID());
                  putCapped(EVENT_HORIZON, target.getUUID(), mastered ? 100 : 60);
                  if (target.tickCount % 80 == 0) {
                     IstarothFx.verdictClock(target, Math.max(2.6F, target.getBbWidth() * 3.2F), 40);
                  }
               }
            }
         }
      }
   }

   private static void tickKingOfOneSecond(LivingEntity owner, boolean mastered) {
      UUID id = owner.getUUID();
      Integer ticks = KING_OF_ONE_SECOND.get(id);
      if (ticks != null) {
         if (ticks <= 0) {
            KING_OF_ONE_SECOND.remove(id);
         } else {
            KING_OF_ONE_SECOND.put(id, ticks - 1);
            if (owner.tickCount % 20 == 0) {
               PASSIVE_STACKS.put(id, Math.min(mastered ? 18 : 10, PASSIVE_STACKS.getOrDefault(id, 0) + 1));
               owner.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.ETERNAL_MOMENT), mastered ? 35 : 20, 0, false, false, true));
               IstarothFx.clockDust(owner, mastered ? 1.3F : 0.9F, mastered ? 14 : 8);
            }

            if (owner.tickCount % 30 == 0) {
               for (LivingEntity target : IstarothCommon.nearbyLiving(owner, mastered ? 10.0 : 6.0)) {
                  if (IstarothCommon.validEnemy(owner, target)) {
                     target.addEffect(
                        new MobEffectInstance(
                           ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), mastered ? 55 : 30, mastered ? 2 : 1, false, false, true
                        )
                     );
                     IstarothCommon.addTemporalDebt(target, mastered ? 12.0F : 5.0F, owner.getUUID());
                     putCapped(DEAD_SECOND_MARKS, target.getUUID(), Math.max(DEAD_SECOND_MARKS.getOrDefault(target.getUUID(), 0), mastered ? 100 : 55));
                  }
               }
            }
         }
      }
   }

   private static void tickDeadSecondMarks(LivingEntity owner, boolean mastered) {
      if (owner.level() instanceof ServerLevel serverLevel) {
         if (owner.tickCount % 20 == 0) {
            Iterator<Entry<UUID, Integer>> iterator = DEAD_SECOND_MARKS.entrySet().iterator();

            while (iterator.hasNext()) {
               Entry<UUID, Integer> entry = iterator.next();
               if (serverLevel.getEntity(entry.getKey()) instanceof LivingEntity target && target.isAlive()) {
                  int ticks = entry.getValue();
                  if (ticks <= 0 || !IstarothCommon.validEnemy(owner, target)) {
                     iterator.remove();
                  } else if (ticks <= 20) {
                     float damage = mastered ? 38.0F : 20.0F;
                     damage += Math.min(mastered ? 55.0F : 25.0F, IstarothCommon.getDebt(target) * (mastered ? 0.2F : 0.1F));
                     target.invulnerableTime = 0;
                     target.hurt(IstarothCommon.damageSource(owner), damage);
                     target.addEffect(
                        new MobEffectInstance(
                           ModMobEffects.getReference(ModMobEffects.TIMELINE_VERDICT), mastered ? 80 : 50, mastered ? 2 : 1, false, false, true
                        )
                     );
                     IstarothFx.clockShatter(target, mastered ? 4.0F : 2.6F);
                     iterator.remove();
                  } else {
                     if (target.tickCount % 60 == 0) {
                        target.addEffect(
                           new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.FUTURE_REJECTION), mastered ? 45 : 25, 1, false, false, true)
                        );
                        IstarothFx.clockDust(target, mastered ? 1.2F : 0.8F, mastered ? 12 : 7);
                     }

                     entry.setValue(ticks - 20);
                  }
               } else {
                  iterator.remove();
               }
            }
         }
      }
   }

   private static void applyBorrowedEnding(LivingEntity owner, LivingEntity target, float damage, boolean mastered) {
      int batteryGain = mastered ? 10 : 5;
      PASSIVE_STACKS.put(owner.getUUID(), Math.min(mastered ? 18 : 10, PASSIVE_STACKS.getOrDefault(owner.getUUID(), 0) + 3));
      FROZEN_PROJECTILE_BATTERY.put(owner.getUUID(), Math.min(50, FROZEN_PROJECTILE_BATTERY.getOrDefault(owner.getUUID(), 0) + batteryGain));
      KING_OF_ONE_SECOND.put(owner.getUUID(), Math.max(KING_OF_ONE_SECOND.getOrDefault(owner.getUUID(), 0), mastered ? 120 : 65));
      owner.heal(Math.min(mastered ? 18.0F : 9.0F, Math.max(2.0F, damage * 0.12F)));
      owner.addEffect(
         new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.ETERNAL_MOMENT), mastered ? 100 : 60, mastered ? 1 : 0, false, false, true)
      );
      IstarothFx.line(owner.level(), target.getEyePosition(), owner.getEyePosition(), ParticleTypes.END_ROD);
      IstarothFx.clockDust(owner, mastered ? 2.0F : 1.2F, mastered ? 24 : 12);
   }

   private static void applyCausalSuperiority(LivingEntity owner, LivingEntity target, float damage, boolean mastered) {
      UUID recent = RECENT_ATTACKER.get(owner.getUUID());
      int ticks = RECENT_ATTACKER_TICKS.getOrDefault(owner.getUUID(), 0);
      if (recent != null && ticks > 0 && recent.equals(target.getUUID())) {
         float bonus = Math.max(mastered ? 22.0F : 12.0F, damage * (mastered ? 0.38F : 0.22F));
         target.invulnerableTime = 0;
         target.hurt(IstarothCommon.damageSource(owner), bonus);
         target.addEffect(
            new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TIMELINE_VERDICT), mastered ? 80 : 50, mastered ? 2 : 1, false, false, true)
         );
         IstarothCommon.addTemporalDebt(target, bonus * 1.2F, owner.getUUID());
         IstarothFx.verdictClock(target, Math.max(2.6F, target.getBbWidth() * 3.5F), 45);
         IstarothFx.clockCrackSound(target);
         RECENT_ATTACKER.remove(owner.getUUID());
         RECENT_ATTACKER_TICKS.remove(owner.getUUID());
      }
   }

   private static void applyChronoRuptureOnHit(LivingEntity owner, LivingEntity target, float damage, boolean mastered) {
      if (!(damage < (mastered ? 10.0F : 18.0F))) {
         int ticks = mastered ? 140 : 90;
         putCapped(CHRONO_RUPTURE, target.getUUID(), Math.max(CHRONO_RUPTURE.getOrDefault(target.getUUID(), 0), ticks));
         if (damage >= (mastered ? 40.0F : 60.0F)) {
            putCapped(EVENT_HORIZON, target.getUUID(), Math.max(EVENT_HORIZON.getOrDefault(target.getUUID(), 0), mastered ? 120 : 70));
         }
      }
   }

   private static void tickUnwrittenDeathCooldown(LivingEntity owner) {
      UUID id = owner.getUUID();
      int ticks = UNWRITTEN_DEATH_COOLDOWN.getOrDefault(id, 0);
      if (ticks <= 0) {
         UNWRITTEN_DEATH_COOLDOWN.remove(id);
      } else {
         UNWRITTEN_DEATH_COOLDOWN.put(id, ticks - 1);
      }
   }

   private static void tickRecentAttacker(LivingEntity owner) {
      UUID id = owner.getUUID();
      int ticks = RECENT_ATTACKER_TICKS.getOrDefault(id, 0);
      if (ticks <= 0) {
         RECENT_ATTACKER.remove(id);
         RECENT_ATTACKER_TICKS.remove(id);
      } else {
         RECENT_ATTACKER_TICKS.put(id, ticks - 1);
      }
   }

   private static void tickStillness(LivingEntity owner) {
      UUID id = owner.getUUID();
      Integer ticks = STILLNESS.get(id);
      if (ticks != null) {
         boolean mastered = MASTERED_CACHE.getOrDefault(id, false);
         if (ticks > 0) {
            STILLNESS.put(id, ticks - 1);
            owner.setDeltaMovement(owner.getDeltaMovement().scale(mastered ? 0.62 : 0.45));
            if (owner.tickCount % 12 == 0) {
               IstarothFx.grayPulse(owner, mastered ? 2.4F : 1.8F);
            }
         } else {
            float pressure = STILLNESS_PRESSURE.getOrDefault(id, 0.0F);
            STILLNESS.remove(id);
            STILLNESS_PRESSURE.remove(id);
            if (pressure > 8.0F) {
               for (LivingEntity target : IstarothCommon.nearbyLiving(owner, mastered ? 10.0 : 7.0)) {
                  if (IstarothCommon.validEnemy(owner, target)) {
                     float damage = Math.min(mastered ? 165.0F : 95.0F, pressure * (mastered ? 1.45F : 1.08F));
                     target.invulnerableTime = 0;
                     target.hurt(IstarothCommon.damageSource(owner), damage);
                     target.addEffect(
                        new MobEffectInstance(
                           ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), mastered ? 120 : 80, mastered ? 2 : 1, false, false, true
                        )
                     );
                     IstarothCommon.addTemporalDebt(target, damage * 0.52F, owner.getUUID());
                     putCapped(PARADOX_MARKS, target.getUUID(), Math.max(PARADOX_MARKS.getOrDefault(target.getUUID(), 0), mastered ? 120 : 70));
                     putCapped(DEAD_SECOND_MARKS, target.getUUID(), Math.max(DEAD_SECOND_MARKS.getOrDefault(target.getUUID(), 0), mastered ? 80 : 45));
                     if (mastered) {
                        IstarothUltimate.applyEventSilence(owner, target, 75, 2);
                     }
                  }
               }

               IstarothFx.clockShatter(owner, mastered ? 8.5F : 6.0F);
            }
         }
      }
   }

   private static int countFrozenProjectiles(UUID owner) {
      int count = 0;

      for (IstarothPassives.FrozenProjectileState state : FROZEN_PROJECTILES.values()) {
         if (state.owner.equals(owner)) {
            count++;
         }
      }

      return count;
   }

   private static void putCapped(Map<UUID, Integer> map, UUID id, int value) {
      if (map.size() <= 180 || map.containsKey(id)) {
         map.put(id, value);
      }
   }

   private static void trimGlobalMaps() {
      if (PARADOX_MARKS.size() > 180) {
         PARADOX_MARKS.clear();
      }

      if (AGE_SENTENCE.size() > 180) {
         AGE_SENTENCE.clear();
      }

      if (CHRONO_RUPTURE.size() > 180) {
         CHRONO_RUPTURE.clear();
      }

      if (EVENT_HORIZON.size() > 180) {
         EVENT_HORIZON.clear();
      }

      if (DEAD_SECOND_MARKS.size() > 180) {
         DEAD_SECOND_MARKS.clear();
      }

      if (TIME_LOCK_TICKS.size() > 180) {
         TIME_LOCK_TICKS.clear();
      }

      if (TIME_LOCKED_WOUNDS.size() > 180) {
         TIME_LOCKED_WOUNDS.clear();
      }

      if (LAST_HEALTH.size() > 180) {
         LAST_HEALTH.clear();
      }

      if (EXECUTION_MARKS.size() > 180) {
         EXECUTION_MARKS.clear();
      }
   }

   private static Vec3 safeDirection(Vec3 preferred, Vec3 fallback) {
      if (preferred.lengthSqr() > 0.01) {
         return preferred.normalize();
      } else {
         return fallback.lengthSqr() > 0.01 ? fallback.normalize() : new Vec3(0.0, 0.0, 1.0);
      }
   }

   private static final class FrozenProjectileState {
      private final UUID owner;
      private final UUID originalShooter;
      private Vec3 motion;
      private final Vec3 anchor;
      private int ticks;
      private boolean redirectOnRelease;
      private boolean reflectOnRelease;
      private double releaseSpeed;
      private final int caughtAtTick;
      private final boolean originalNoGravity;

      private FrozenProjectileState(
         UUID owner,
         UUID originalShooter,
         Vec3 motion,
         Vec3 anchor,
         int ticks,
         boolean redirectOnRelease,
         boolean reflectOnRelease,
         double releaseSpeed,
         int caughtAtTick,
         boolean originalNoGravity
      ) {
         this.owner = owner;
         this.originalShooter = originalShooter;
         this.motion = motion;
         this.anchor = anchor;
         this.ticks = ticks;
         this.redirectOnRelease = redirectOnRelease;
         this.reflectOnRelease = reflectOnRelease;
         this.releaseSpeed = releaseSpeed;
         this.caughtAtTick = caughtAtTick;
         this.originalNoGravity = originalNoGravity;
      }
   }
}
