package com.github.wal_bos.moreskills.handler.Istaroth;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.IstarothDoomTornadoPayload;
import com.github.wal_bos.moreskills.registry.effect.ModMobEffects;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.tensura.world.TensuraGameRules;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public final class IstarothDoomTornado {
   private static final MoreSkillsConfigs.IstarothConfig CONFIG = MoreSkillsConfigs.GRAND.istaroth;
   private static final Map<UUID, IstarothDoomTornado.DoomTornadoState> TORNADOES = new HashMap<>();
   private static final Map<ServerLevel, Long> LAST_TICKED_LEVEL_TIME = new HashMap<>();
   private static final Map<UUID, Boolean> ORIGINAL_NO_GRAVITY = new HashMap<>();
   private static final Map<UUID, Integer> END_CLEANUP_LOCK = new HashMap<>();

   private IstarothDoomTornado() {
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
   }

   public static boolean hasTornado(UUID owner) {
      return TORNADOES.containsKey(owner);
   }

   public static boolean isDoomDisplay(Entity entity) {
      return entity instanceof Display && entity.getPersistentData().getBoolean("IstarothDoomTornadoDisplay");
   }

   public static int getRemainingTicks(UUID owner) {
      IstarothDoomTornado.DoomTornadoState state = TORNADOES.get(owner);
      return state == null ? 0 : Math.max(0, state.remainingTicks);
   }

   public static float getProgress(UUID owner) {
      IstarothDoomTornado.DoomTornadoState state = TORNADOES.get(owner);
      return state == null ? 0.0F : Mth.clamp(1.0F - (float)state.remainingTicks / state.totalTicks, 0.0F, 1.0F);
   }

   public static void clear(UUID owner) {
      IstarothDoomTornado.DoomTornadoState state = TORNADOES.remove(owner);
      if (state != null) {
         state.fullCleanup();
         sendStop(state.level, owner);
      }

      END_CLEANUP_LOCK.put(owner, i(CONFIG.doomTornadoEndCleanupLockTicks));
   }

   public static void start(ServerLevel level, UUID owner, Vec3 center, int totalTicks, float finalRadius) {
      clear(owner);
      IstarothDoomTornado.DoomTornadoState state = new IstarothDoomTornado.DoomTornadoState(
         level, owner, center, Math.max(i(CONFIG.doomTornadoMinTicks), totalTicks), Math.max(f(CONFIG.doomTornadoMinRadius), finalRadius)
      );
      TORNADOES.put(owner, state);
      END_CLEANUP_LOCK.remove(owner);
      sendStart(level, owner, state.center, state.totalTicks, state.finalRadius);
      state.refreshTemporalScreen(true);
      state.beginningOfTheEnd();
   }

   public static void tick(ServerLevel level) {
      long gameTime = level.getGameTime();
      Long last = LAST_TICKED_LEVEL_TIME.get(level);
      if (last == null || last != gameTime) {
         LAST_TICKED_LEVEL_TIME.put(level, gameTime);
         Iterator<Entry<UUID, Integer>> cleanupIterator = END_CLEANUP_LOCK.entrySet().iterator();

         while (cleanupIterator.hasNext()) {
            Entry<UUID, Integer> entry = cleanupIterator.next();
            int ticks = entry.getValue() - 1;
            if (ticks <= 0) {
               cleanupIterator.remove();
            } else {
               entry.setValue(ticks);
            }
         }

         Iterator<Entry<UUID, IstarothDoomTornado.DoomTornadoState>> iterator = TORNADOES.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<UUID, IstarothDoomTornado.DoomTornadoState> entry = iterator.next();
            IstarothDoomTornado.DoomTornadoState state = entry.getValue();
            if (state.level != level) {
               state.fullCleanup();
               sendStop(state.level, state.owner);
               END_CLEANUP_LOCK.put(state.owner, i(CONFIG.doomTornadoEndCleanupLockTicks));
               iterator.remove();
            } else if (state.remainingTicks <= 0) {
               iterator.remove();
               detonateState(level, state);
            } else {
               state.tick();
            }
         }
      }
   }

   public static void detonate(ServerLevel level, UUID owner) {
      IstarothDoomTornado.DoomTornadoState state = TORNADOES.remove(owner);
      if (state != null) {
         detonateState(level, state);
      }
   }

   private static void detonateState(ServerLevel level, IstarothDoomTornado.DoomTornadoState state) {
      Vec3 center = state.center;
      float radius = state.finalRadius;
      UUID owner = state.owner;
      END_CLEANUP_LOCK.put(owner, i(CONFIG.doomTornadoDetonateCleanupLockTicks));
      sendStop(level, owner);
      state.finalUniverseResetSky();
      state.zeroSecondFreeze();
      state.preCollapseFreeze();
      state.worldGravityDies();
      state.finalMirrorShatterParticles();
      state.temporalSingularityParticles();
      state.skyFractureJudgement();
      state.worldEndingGravityCrush();
      state.explosionRainParticles();
      state.finalShockwaveParticles();
      state.mirrorGlassRainParticles();
      state.finalBurstParticles();
      level.playSound(null, center.x, center.y + 12.0, center.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 14.0F, 0.2F);
      level.playSound(null, center.x, center.y + 70.0, center.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 12.0F, 0.13F);
      level.playSound(null, center.x, center.y + 40.0, center.z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, 16.0F, 0.25F);
      level.playSound(null, center.x, center.y + 20.0, center.z, SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 9.0F, 0.1F);
      level.playSound(null, center.x, center.y + 30.0, center.z, SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 8.0F, 0.22F);
      level.playSound(null, center.x, center.y + 18.0, center.z, SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 12.0F, 0.08F);
      level.playSound(null, center.x, center.y + 22.0, center.z, SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS, 10.0F, 0.12F);
      level.playSound(null, center.x, center.y + 26.0, center.z, (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.PLAYERS, 9.0F, 0.16F);
      level.playSound(null, center.x, center.y + 55.0, center.z, SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 7.0F, 0.07F);
      AABB area = new AABB(center, center).inflate(radius * 3.75F, radius * 3.0F, radius * 3.75F);

      for (Entity entity : level.getEntitiesOfClass(Entity.class, area, e -> e.isAlive() && !isDoomDisplay(e))) {
         if (!(entity instanceof Player player && player.isCreative())) {
            Vec3 away = entity.position().subtract(center);
            if (away.lengthSqr() < 0.01) {
               away = new Vec3(level.random.nextDouble() - 0.5, 0.35, level.random.nextDouble() - 0.5);
            }

            double dist = Math.max(1.0, away.length());
            double force = d(CONFIG.doomTornadoFinalLaunchForceBase)
               + Math.min(d(CONFIG.doomTornadoFinalLaunchForceCap), radius / dist * d(CONFIG.doomTornadoFinalLaunchForceRadiusMultiplier));
            double lift = d(CONFIG.doomTornadoFinalLaunchLiftBase)
               + Math.min(d(CONFIG.doomTornadoFinalLaunchLiftCap), radius / dist * d(CONFIG.doomTornadoFinalLaunchLiftRadiusMultiplier));
            Vec3 tangent = new Vec3(-away.z, 0.0, away.x);
            if (tangent.lengthSqr() < 0.01) {
               tangent = new Vec3(1.0, 0.0, 0.0);
            }

            Vec3 launch = away.normalize().scale(force).add(tangent.normalize().scale(d(CONFIG.doomTornadoFinalLaunchTangentForce))).add(0.0, lift, 0.0);
            entity.setNoGravity(false);
            entity.setDeltaMovement(entity.getDeltaMovement().add(launch));
            entity.hurtMarked = true;
            if (entity instanceof LivingEntity living) {
               living.addEffect(
                  new MobEffectInstance(
                     ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER),
                     i(CONFIG.doomTornadoFinalTemporalStutterTicks),
                     i(CONFIG.doomTornadoFinalTemporalStutterAmplifier),
                     false,
                     false,
                     true
                  )
               );
               living.addEffect(
                  new MobEffectInstance(
                     ModMobEffects.getReference(ModMobEffects.WORLD_OF_STILL_SECONDS),
                     i(CONFIG.doomTornadoFinalStillSecondsTicks),
                     i(CONFIG.doomTornadoFinalStillSecondsAmplifier),
                     false,
                     false,
                     true
                  )
               );
            }
         }
      }

      state.finalBlockDevourCrater();
      state.finalMirrorCrater();
      state.fullCleanup();
      state.forceTimeResync();
   }

   private static void sendStart(ServerLevel level, UUID owner, Vec3 center, int ticks, float radius) {
      IstarothDoomTornadoPayload payload = IstarothDoomTornadoPayload.start(owner, center, ticks, radius);

      for (ServerPlayer player : level.players()) {
         if (player.distanceToSqr(center) <= radius * radius * d(CONFIG.doomTornadoSendStartRangeMultiplier)) {
            NetworkManager.sendToPlayer(player, payload);
         }
      }
   }

   private static void sendStop(ServerLevel level, UUID owner) {
      IstarothDoomTornadoPayload payload = IstarothDoomTornadoPayload.stop(owner);

      for (ServerPlayer player : level.players()) {
         NetworkManager.sendToPlayer(player, payload);
      }
   }

   private static final class DoomTornadoState {
      private final ServerLevel level;
      private final UUID owner;
      private final Vec3 center;
      private final int totalTicks;
      private final float finalRadius;
      private final long originalDayTime;
      private final long originalGameTime;
      private final boolean originalDaylightRule;
      private int remainingTicks;
      private boolean restoredDayTime;
      private double visualDayTime;

      private DoomTornadoState(ServerLevel level, UUID owner, Vec3 center, int totalTicks, float finalRadius) {
         this.level = level;
         this.owner = owner;
         this.center = center;
         this.totalTicks = totalTicks;
         this.remainingTicks = totalTicks;
         this.finalRadius = finalRadius;
         this.originalDayTime = level.getDayTime();
         this.originalGameTime = level.getGameTime();
         this.originalDaylightRule = level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT);
         this.visualDayTime = this.originalDayTime;
         this.restoredDayTime = false;
      }

      private void tick() {
         int age = this.totalTicks - this.remainingTicks;
         float progress = Mth.clamp((float)age / this.totalTicks, 0.0F, 1.0F);
         this.spinDayCycleMadeInHeaven(age, progress);
         this.refreshTemporalScreen(false);
         this.pullDust(age, progress);
         this.eatBlocks(age, progress);
         this.pullEntities(age, progress);
         this.nullGravity(age, progress);
         this.apocalypticSkyTears(age, progress);
         this.ambientRumble(age, progress);
         this.zeroSecondSequence(age, progress);
         this.remainingTicks--;
      }

      private void fullCleanup() {
         this.removeTemporalScreen();
         this.restoreGravity();
         this.restoreDayTime();
         this.forceTimeResync();
      }

      private void removeTemporalScreen() {
         double radius = this.finalRadius * 8.0;

         for (ServerPlayer player : this.level.players()) {
            if (player.distanceToSqr(this.center) <= radius * radius) {
               player.removeEffect(ModMobEffects.getReference(ModMobEffects.TEMPORAL_SCREEN));
            }
         }
      }

      private void sendTimePacket(long dayTime) {
         ClientboundSetTimePacket packet = new ClientboundSetTimePacket(
            this.level.getGameTime(), dayTime, this.level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)
         );

         for (ServerPlayer player : this.level.players()) {
            player.connection.send(packet);
         }
      }

      private void setVisualDayTime(long time) {
         this.level.setDayTime(time);
         this.sendTimePacket(time);
      }

      private void spinDayCycleMadeInHeaven(int age, float progress) {
         float eased = progress * progress * (3.0F - 2.0F * progress);
         float violentEnd = Mth.clamp((progress - 0.4F) / 0.6F, 0.0F, 1.0F);
         violentEnd = violentEnd * violentEnd * violentEnd;
         double rawJump = 9000.0 + eased * 145000.0 + violentEnd * 285000.0;
         double offset = Math.sin(age * 0.93) * 21000.0 + Math.sin(age * 2.31) * 48000.0 * violentEnd;
         double nonLoopingSkew = 13777.0 + age * (31.0 + progress * 211.0);
         if (this.remainingTicks <= 180) {
            rawJump *= 1.55;
         }

         if (this.remainingTicks <= 120) {
            rawJump *= 2.2;
         }

         if (this.remainingTicks <= 80) {
            rawJump *= 3.1;
         }

         if (this.remainingTicks <= 40) {
            rawJump *= 4.8;
         }

         if (this.remainingTicks <= 20) {
            rawJump *= 7.5;
         }

         this.visualDayTime += rawJump + offset + nonLoopingSkew;
         this.setVisualDayTime((long)this.visualDayTime);
         int flashCount = this.remainingTicks <= 80 ? 5 : (this.remainingTicks <= 160 ? 3 : 1);
         this.level.sendParticles(ParticleTypes.FLASH, this.center.x, this.center.y + this.finalRadius * 1.55, this.center.z, flashCount, 0.0, 0.0, 0.0, 0.0);
         this.level
            .sendParticles(
               ParticleTypes.END_ROD,
               this.center.x,
               this.center.y + this.finalRadius * 1.5,
               this.center.z,
               120 + (int)(progress * 260.0F),
               this.finalRadius * 1.9,
               this.finalRadius * 0.65,
               this.finalRadius * 1.9,
               0.35 + progress * 0.55
            );
         this.level
            .sendParticles(
               ParticleTypes.REVERSE_PORTAL,
               this.center.x,
               this.center.y + this.finalRadius * 1.3,
               this.center.z,
               80 + (int)(progress * 220.0F),
               this.finalRadius * 1.4,
               this.finalRadius * 0.65,
               this.finalRadius * 1.4,
               0.55 + progress * 0.65
            );
         if (age % 2 == 0 && this.remainingTicks <= 120) {
            this.level
               .playSound(
                  null,
                  this.center.x,
                  this.center.y + 90.0,
                  this.center.z,
                  SoundEvents.AMETHYST_BLOCK_RESONATE,
                  SoundSource.AMBIENT,
                  2.7F,
                  0.1F + progress * 0.12F
               );
         }

         if (age % 4 == 0) {
            this.level
               .playSound(
                  null,
                  this.center.x,
                  this.center.y + 80.0,
                  this.center.z,
                  SoundEvents.BEACON_AMBIENT,
                  SoundSource.AMBIENT,
                  1.8F + progress * 3.2F,
                  0.08F + progress * 0.18F
               );
         }

         if (this.remainingTicks <= 40) {
            this.level.playSound(null, this.center.x, this.center.y + 100.0, this.center.z, SoundEvents.BEACON_DEACTIVATE, SoundSource.AMBIENT, 2.4F, 0.05F);
         }
      }

      private void beginningOfTheEnd() {
         this.level.sendParticles(ParticleTypes.FLASH, this.center.x, this.center.y + 20.0, this.center.z, 6, 0.0, 0.0, 0.0, 0.0);
         this.level
            .sendParticles(
               ParticleTypes.REVERSE_PORTAL,
               this.center.x,
               this.center.y + 12.0,
               this.center.z,
               450,
               this.finalRadius * 0.9,
               this.finalRadius * 0.3,
               this.finalRadius * 0.9,
               0.65
            );
         this.level
            .sendParticles(
               ParticleTypes.END_ROD,
               this.center.x,
               this.center.y + 14.0,
               this.center.z,
               220,
               this.finalRadius * 0.65,
               this.finalRadius * 0.24,
               this.finalRadius * 0.65,
               0.28
            );
         this.level.playSound(null, this.center.x, this.center.y + 12.0, this.center.z, SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 4.0F, 0.25F);
         this.level.playSound(null, this.center.x, this.center.y + 12.0, this.center.z, SoundEvents.WARDEN_SONIC_CHARGE, SoundSource.PLAYERS, 3.0F, 0.15F);
      }

      private void refreshTemporalScreen(boolean force) {
         if (!IstarothDoomTornado.END_CLEANUP_LOCK.containsKey(this.owner)) {
            if (IstarothDoomTornado.TORNADOES.containsKey(this.owner)) {
               if (force
                  || this.remainingTicks % 20 == 0
                  || this.remainingTicks == 120
                  || this.remainingTicks == 80
                  || this.remainingTicks == 40
                  || this.remainingTicks == 20
                  || this.remainingTicks == 10) {
                  float progress = Mth.clamp(1.0F - (float)this.remainingTicks / this.totalTicks, 0.0F, 1.0F);
                  double radius = this.finalRadius * (2.1 + progress * 4.2);
                  int duration;
                  if (force) {
                     duration = Math.min(120, Math.max(60, this.remainingTicks + 2));
                  } else {
                     duration = 45;
                  }

                  int amplifier = this.remainingTicks > 80 && (!force || this.remainingTicks > 200) ? 6 : 7;

                  for (ServerPlayer player : this.level.players()) {
                     if (!(player.distanceToSqr(this.center) > radius * radius)) {
                        player.addEffect(
                           new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_SCREEN), duration, amplifier, false, false, true)
                        );
                     }
                  }
               }
            }
         }
      }

      private void zeroSecondSequence(int age, float progress) {
         if (this.remainingTicks <= 40) {
            double radius = this.finalRadius * 3.8;
            AABB area = new AABB(this.center, this.center).inflate(radius, this.finalRadius * 3.5, radius);
            if (this.remainingTicks <= 40) {
               for (ServerPlayer player : this.level.players()) {
                  if (player.distanceToSqr(this.center) <= radius * radius) {
                     player.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_SCREEN), 45, 7, false, false, true));
                  }
               }
            }

            if (this.remainingTicks <= 40) {
               this.visualDayTime = this.visualDayTime + (888888.0 + Math.sin(age * 3.1) * 180000.0);
               this.setVisualDayTime((long)this.visualDayTime);
            }

            if (this.remainingTicks == 40) {
               this.level.playSound(null, this.center.x, this.center.y + 30.0, this.center.z, SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 8.0F, 0.04F);
               this.level
                  .playSound(null, this.center.x, this.center.y + 30.0, this.center.z, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS, 8.0F, 0.05F);
               this.level.sendParticles(ParticleTypes.FLASH, this.center.x, this.center.y + this.finalRadius, this.center.z, 10, 0.0, 0.0, 0.0, 0.0);
            }

            if (this.remainingTicks <= 40 && this.remainingTicks > 10) {
               for (Entity entity : this.level.getEntitiesOfClass(Entity.class, area, e -> e.isAlive() && !IstarothDoomTornado.isDoomDisplay(e))) {
                  if (!(entity instanceof Player player && player.isCreative())) {
                     if (!IstarothDoomTornado.ORIGINAL_NO_GRAVITY.containsKey(entity.getUUID())) {
                        IstarothDoomTornado.ORIGINAL_NO_GRAVITY.put(entity.getUUID(), entity.isNoGravity());
                     }

                     entity.setNoGravity(true);
                     entity.setDeltaMovement(entity.getDeltaMovement().scale(0.18).add(0.0, 0.08, 0.0));
                     entity.hurtMarked = true;
                  }
               }
            }

            if (this.remainingTicks == 10) {
               for (Entity entity : this.level.getEntitiesOfClass(Entity.class, area, e -> e.isAlive() && !IstarothDoomTornado.isDoomDisplay(e))) {
                  if (!(entity instanceof Player player && player.isCreative())) {
                     entity.setDeltaMovement(Vec3.ZERO);
                     entity.hurtMarked = true;
                  }
               }

               this.level.sendParticles(ParticleTypes.FLASH, this.center.x, this.center.y + this.finalRadius * 0.8, this.center.z, 35, 0.0, 0.0, 0.0, 0.0);
               this.level
                  .playSound(null, this.center.x, this.center.y + 42.0, this.center.z, SoundEvents.WARDEN_SONIC_CHARGE, SoundSource.PLAYERS, 10.0F, 0.08F);
            }

            if (this.remainingTicks <= 5) {
               this.level
                  .sendParticles(
                     ParticleTypes.WHITE_ASH,
                     this.center.x,
                     this.center.y + this.finalRadius * 0.8,
                     this.center.z,
                     900,
                     this.finalRadius * 2.5,
                     this.finalRadius * 1.5,
                     this.finalRadius * 2.5,
                     0.02
                  );
            }
         }
      }

      private void zeroSecondFreeze() {
         double radius = this.finalRadius * 4.0;
         AABB area = new AABB(this.center, this.center).inflate(radius, this.finalRadius * 3.8, radius);

         for (Entity entity : this.level.getEntitiesOfClass(Entity.class, area, e -> e.isAlive() && !IstarothDoomTornado.isDoomDisplay(e))) {
            if (!(entity instanceof Player player && player.isCreative())) {
               entity.setDeltaMovement(Vec3.ZERO);
               entity.hurtMarked = true;
            }
         }

         this.level.sendParticles(ParticleTypes.FLASH, this.center.x, this.center.y + this.finalRadius * 0.9, this.center.z, 50, 0.0, 0.0, 0.0, 0.0);
         this.level.playSound(null, this.center.x, this.center.y + 60.0, this.center.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 12.0F, 0.06F);
      }

      private void finalUniverseResetSky() {
         for (int i = 0; i < 44; i++) {
            this.visualDayTime = this.visualDayTime + (77777.0 + i * 43111.0 + Math.sin(i * 1.7) * 50000.0);
            this.setVisualDayTime((long)this.visualDayTime);
         }

         this.level.sendParticles(ParticleTypes.FLASH, this.center.x, this.center.y + this.finalRadius * 1.2, this.center.z, 34, 0.0, 0.0, 0.0, 0.0);
         this.level
            .sendParticles(
               ParticleTypes.END_ROD,
               this.center.x,
               this.center.y + this.finalRadius * 1.8,
               this.center.z,
               2100,
               this.finalRadius * 3.8,
               this.finalRadius * 1.4,
               this.finalRadius * 3.8,
               0.7
            );
         this.level
            .sendParticles(
               ParticleTypes.WHITE_ASH,
               this.center.x,
               this.center.y + this.finalRadius * 1.5,
               this.center.z,
               2600,
               this.finalRadius * 4.4,
               this.finalRadius * 1.5,
               this.finalRadius * 4.4,
               0.52
            );
         this.level
            .sendParticles(
               ParticleTypes.REVERSE_PORTAL,
               this.center.x,
               this.center.y + this.finalRadius * 1.4,
               this.center.z,
               1200,
               this.finalRadius * 3.1,
               this.finalRadius * 1.3,
               this.finalRadius * 3.1,
               0.95
            );
         this.level.playSound(null, this.center.x, this.center.y + 90.0, this.center.z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, 16.0F, 0.12F);
         this.level.playSound(null, this.center.x, this.center.y + 90.0, this.center.z, SoundEvents.BEACON_DEACTIVATE, SoundSource.AMBIENT, 10.0F, 0.05F);
         this.level.playSound(null, this.center.x, this.center.y + 90.0, this.center.z, SoundEvents.END_PORTAL_SPAWN, SoundSource.AMBIENT, 7.0F, 0.08F);
         this.level
            .playSound(
               null,
               this.center.x,
               this.center.y + 90.0,
               this.center.z,
               (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(),
               SoundSource.AMBIENT,
               7.0F,
               0.11F
            );
      }

      private void restoreDayTime() {
         if (!this.restoredDayTime) {
            this.restoredDayTime = true;
            long elapsed = Math.max(0L, this.level.getGameTime() - this.originalGameTime);
            long restored = this.originalDaylightRule ? this.originalDayTime + elapsed : this.originalDayTime;
            this.level.setDayTime(restored);
            this.sendTimePacket(restored);
         }
      }

      private void forceTimeResync() {
         long restored = this.level.getDayTime();

         for (int i = 0; i < 5; i++) {
            ClientboundSetTimePacket packet = new ClientboundSetTimePacket(
               this.level.getGameTime(), restored, this.level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)
            );

            for (ServerPlayer player : this.level.players()) {
               player.connection.send(packet);
            }
         }
      }

      private void restoreGravity() {
         for (Iterator<Entry<UUID, Boolean>> iterator = IstarothDoomTornado.ORIGINAL_NO_GRAVITY.entrySet().iterator(); iterator.hasNext(); iterator.remove()) {
            Entry<UUID, Boolean> entry = iterator.next();
            Entity entity = this.level.getEntity(entry.getKey());
            if (entity != null) {
               entity.setNoGravity(entry.getValue());
               entity.hurtMarked = true;
            }
         }
      }

      private void ambientRumble(int age, float progress) {
         if (age % 14 == 0) {
            this.level
               .playSound(null, this.center.x, this.center.y + 6.0, this.center.z, SoundEvents.SAND_BREAK, SoundSource.BLOCKS, 2.3F + progress * 2.4F, 0.32F);
            this.level
               .playSound(
                  null,
                  this.center.x,
                  this.center.y + 42.0,
                  this.center.z,
                  (SoundEvent)SoundEvents.AMBIENT_BASALT_DELTAS_LOOP.value(),
                  SoundSource.AMBIENT,
                  2.0F + progress * 2.8F,
                  0.22F + progress * 0.12F
               );
            if (this.remainingTicks <= 180) {
               this.level
                  .playSound(null, this.center.x, this.center.y + 35.0, this.center.z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, 5.2F, 0.42F);
               this.level.playSound(null, this.center.x, this.center.y + 22.0, this.center.z, SoundEvents.GLASS_HIT, SoundSource.PLAYERS, 3.4F, 0.2F);
            }

            if (this.remainingTicks <= 90) {
               this.level
                  .playSound(null, this.center.x, this.center.y + 25.0, this.center.z, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS, 4.8F, 0.11F);
               this.level
                  .playSound(null, this.center.x, this.center.y + 40.0, this.center.z, SoundEvents.WARDEN_SONIC_CHARGE, SoundSource.PLAYERS, 2.8F, 0.15F);
            }
         }
      }

      private void pullEntities(int age, float progress) {
         double radius = this.finalRadius * (1.35 + progress * 2.75);
         double height = 50.0 + progress * 240.0;
         AABB area = new AABB(this.center, this.center).inflate(radius, height, radius);

         for (Entity entity : this.level
            .getEntitiesOfClass(Entity.class, area, e -> e.isAlive() && !(e instanceof Player p && p.isCreative()) && !IstarothDoomTornado.isDoomDisplay(e))) {
            double dy = entity.getY() - this.center.y;
            if (!(dy < -10.0) && !(dy > height)) {
               Vec3 toCenter = new Vec3(this.center.x - entity.getX(), 0.0, this.center.z - entity.getZ());
               double dist = Math.max(1.0, toCenter.length());
               Vec3 tangent = new Vec3(-toCenter.z, 0.0, toCenter.x);
               if (tangent.lengthSqr() < 0.01) {
                  tangent = new Vec3(1.0, 0.0, 0.0);
               }

               tangent = tangent.normalize();
               Vec3 inward = toCenter.normalize().scale(0.055 + progress * 0.14);
               Vec3 spiral = tangent.scale(0.14 + progress * 0.32);
               Vec3 lift = new Vec3(0.0, 0.1 + progress * 0.22, 0.0);
               if (dist < 13.0 + progress * 14.0) {
                  inward = inward.scale(-0.75);
               }

               if (this.remainingTicks <= 120) {
                  double doom = (120.0 - this.remainingTicks) / 120.0;
                  lift = lift.add(0.0, doom * 0.34, 0.0);
                  spiral = spiral.scale(1.0 + doom * 2.1);
               }

               entity.setDeltaMovement(entity.getDeltaMovement().add(inward).add(spiral).add(lift));
               entity.hurtMarked = true;
               if (age % 12 == 0 && entity instanceof LivingEntity living) {
                  living.addEffect(
                     new MobEffectInstance(
                        ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), 90, this.remainingTicks <= 90 ? 5 : 4, false, false, true
                     )
                  );
               }
            }
         }
      }

      private void nullGravity(int age, float progress) {
         if (age % 2 == 0) {
            double radius = this.finalRadius * (1.45 + progress * 3.2);
            double height = 80.0 + progress * 260.0;
            AABB area = new AABB(this.center, this.center).inflate(radius, height, radius);

            for (Entity entity : this.level
               .getEntitiesOfClass(Entity.class, area, e -> e.isAlive() && !(e instanceof Player p && p.isCreative()) && !IstarothDoomTornado.isDoomDisplay(e))) {
               if (!IstarothDoomTornado.ORIGINAL_NO_GRAVITY.containsKey(entity.getUUID())) {
                  IstarothDoomTornado.ORIGINAL_NO_GRAVITY.put(entity.getUUID(), entity.isNoGravity());
               }

               entity.setNoGravity(true);
               Vec3 offset = entity.position().subtract(this.center);
               double dist = Math.max(1.0, offset.horizontalDistance());
               double pull = Mth.clamp(radius / dist, 0.0, 5.0);
               double vertical = 0.1 + progress * 0.22 + pull * 0.03;
               if (this.remainingTicks <= 120) {
                  vertical += 0.2;
               }

               if (this.remainingTicks <= 80) {
                  vertical += 0.28;
               }

               if (this.remainingTicks <= 40) {
                  vertical += 0.42;
               }

               entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, vertical, 0.0));
               entity.hurtMarked = true;
            }
         }
      }

      private void eatBlocks(int age, float progress) {
         if (TensuraGameRules.canSkillGrief(this.level)) {
            int attempts = 150 + (int)(progress * 310.0F);
            int maxEaten = 42 + (int)(progress * 92.0F);
            int eaten = 0;
            double maxRadius = this.finalRadius * (0.75 + progress * 2.85);
            double minRadius = 3.0 + progress * 10.0;
            int maxHeight = 42 + (int)(progress * 230.0F);

            for (int i = 0; i < attempts && eaten < maxEaten; i++) {
               double angle = this.level.random.nextDouble() * Math.PI * 2.0;
               double r = minRadius + this.level.random.nextDouble() * Math.max(1.0, maxRadius - minRadius);
               int x = Mth.floor(this.center.x + Math.cos(angle) * r);
               int z = Mth.floor(this.center.z + Math.sin(angle) * r);
               int y = Mth.floor(this.center.y - 8.0 + this.level.random.nextInt(Math.max(12, maxHeight)));
               BlockPos pos = new BlockPos(x, y, z);
               if (this.level.isLoaded(pos) && this.level.getBlockEntity(pos) == null) {
                  BlockState state = this.level.getBlockState(pos);
                  if (this.canEatBlock(state, pos)) {
                     this.level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                     double dx = this.center.x - (pos.getX() + 0.5);
                     double dz = this.center.z - (pos.getZ() + 0.5);
                     double len = Math.max(1.0, Math.sqrt(dx * dx + dz * dz));
                     double spiralX = -dz / len;
                     double spiralZ = dx / len;
                     this.level
                        .sendParticles(
                           new BlockParticleOption(ParticleTypes.BLOCK, state),
                           pos.getX() + 0.5,
                           pos.getY() + 0.5,
                           pos.getZ() + 0.5,
                           72,
                           dx / len * 1.55 + spiralX * 2.15,
                           1.75 + progress * 3.05,
                           dz / len * 1.55 + spiralZ * 2.15,
                           0.78 + progress * 0.65
                        );
                     this.level.sendParticles(ParticleTypes.CLOUD, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 9, 0.28, 0.24, 0.28, 0.12);
                     this.level
                        .sendParticles(
                           ParticleTypes.REVERSE_PORTAL, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8, dx / len * 0.85, 0.95, dz / len * 0.85, 0.16
                        );
                     if (age % 4 == 0) {
                        this.level
                           .sendParticles(
                              ParticleTypes.END_ROD, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 5, dx / len * 0.85, 1.05, dz / len * 0.85, 0.1
                           );
                     }

                     eaten++;
                  }
               }
            }

            if (eaten > 0 && age % 6 == 0) {
               this.level.playSound(null, this.center.x, this.center.y + 4.0, this.center.z, SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 2.4F, 0.3F);
               this.level.playSound(null, this.center.x, this.center.y + 9.0, this.center.z, SoundEvents.GRAVEL_BREAK, SoundSource.BLOCKS, 2.2F, 0.32F);
               this.level.playSound(null, this.center.x, this.center.y + 14.0, this.center.z, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.4F, 0.18F);
            }
         }
      }

      private boolean canEatBlock(BlockState state, BlockPos pos) {
         if (state.isAir()) {
            return false;
         } else if (state.is(Blocks.BEDROCK)) {
            return false;
         } else if (state.is(Blocks.END_PORTAL_FRAME)) {
            return false;
         } else if (state.is(Blocks.END_PORTAL)) {
            return false;
         } else if (state.is(Blocks.NETHER_PORTAL)) {
            return false;
         } else if (state.is(Blocks.COMMAND_BLOCK)) {
            return false;
         } else if (state.is(Blocks.CHAIN_COMMAND_BLOCK)) {
            return false;
         } else if (state.is(Blocks.REPEATING_COMMAND_BLOCK)) {
            return false;
         } else if (state.is(Blocks.BARRIER)) {
            return false;
         } else if (state.is(Blocks.STRUCTURE_BLOCK)) {
            return false;
         } else {
            return state.is(Blocks.JIGSAW) ? false : state.getDestroySpeed(this.level, pos) >= 0.0F;
         }
      }

      private void pullDust(int age, float progress) {
         double speed = 0.2 + progress * 0.6;
         double radius = this.finalRadius * (1.55 + progress * 4.25);
         double height = 36.0 + progress * this.finalRadius * 4.8;
         int count = 70 + (int)(progress * 190.0F);

         for (int i = 0; i < count; i++) {
            double angle = this.level.random.nextDouble() * Math.PI * 2.0 + age * speed;
            double r = Math.sqrt(this.level.random.nextDouble()) * radius;
            double x = this.center.x + Math.cos(angle) * r;
            double z = this.center.z + Math.sin(angle) * r;
            double y = this.center.y + this.level.random.nextDouble() * height;
            this.level
               .sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, this.pickBurstBlock(i)), x, y, z, 1, 0.55, 0.72, 0.55, 0.15 + progress * 0.16);
         }

         if (age % 4 == 0) {
            this.level
               .sendParticles(
                  ParticleTypes.WHITE_ASH, this.center.x, this.center.y + height * 0.55, this.center.z, 320, radius * 0.95, height * 0.6, radius * 0.95, 0.3
               );
            this.level
               .sendParticles(
                  ParticleTypes.END_ROD, this.center.x, this.center.y + height * 0.72, this.center.z, 140, radius * 0.72, height * 0.45, radius * 0.72, 0.11
               );
         }

         if (this.remainingTicks <= 160 && age % 3 == 0) {
            this.level.sendParticles(ParticleTypes.FLASH, this.center.x, this.center.y + height * 0.55, this.center.z, 1, 0.0, 0.0, 0.0, 0.0);
         }

         if (this.remainingTicks <= 90 && age % 2 == 0) {
            this.level
               .sendParticles(
                  ParticleTypes.POOF, this.center.x, this.center.y + height * 0.48, this.center.z, 220, radius * 0.8, height * 0.52, radius * 0.8, 0.18
               );
         }

         if (this.remainingTicks <= 45) {
            this.level.sendParticles(ParticleTypes.SONIC_BOOM, this.center.x, this.center.y + height * 0.35, this.center.z, 1, 0.0, 0.0, 0.0, 0.0);
         }
      }

      private void apocalypticSkyTears(int age, float progress) {
         if (age % 6 == 0) {
            double radius = this.finalRadius * (1.15 + progress * 3.4);
            double y = this.center.y + 85.0 + progress * 185.0;
            int tears = 14 + (int)(progress * 30.0F);

            for (int i = 0; i < tears; i++) {
               double angle = this.level.random.nextDouble() * Math.PI * 2.0;
               double r = Math.sqrt(this.level.random.nextDouble()) * radius;
               Vec3 top = new Vec3(this.center.x + Math.cos(angle) * r, y + 30.0 + this.level.random.nextDouble() * 55.0, this.center.z + Math.sin(angle) * r);
               Vec3 bottom = new Vec3(
                  this.center.x + Math.cos(angle) * r * 0.22,
                  this.center.y + 8.0 + this.level.random.nextDouble() * 50.0,
                  this.center.z + Math.sin(angle) * r * 0.22
               );
               this.lineParticles(top, bottom, i % 2 == 0 ? ParticleTypes.END_ROD : ParticleTypes.REVERSE_PORTAL, 0.13 + progress * 0.12);
            }
         }
      }

      private void preCollapseFreeze() {
         double radius = this.finalRadius * 3.2;
         double height = this.finalRadius * 2.8;
         AABB area = new AABB(this.center, this.center).inflate(radius, height, radius);

         for (Entity entity : this.level.getEntitiesOfClass(Entity.class, area, e -> e.isAlive() && !IstarothDoomTornado.isDoomDisplay(e))) {
            if (!(entity instanceof Player player && player.isCreative())) {
               entity.setDeltaMovement(entity.getDeltaMovement().scale(0.01));
               entity.hurtMarked = true;
            }
         }

         this.level.sendParticles(ParticleTypes.FLASH, this.center.x, this.center.y + this.finalRadius * 0.8, this.center.z, 14, 0.0, 0.0, 0.0, 0.0);
         this.level
            .sendParticles(
               ParticleTypes.WHITE_ASH,
               this.center.x,
               this.center.y + this.finalRadius * 0.7,
               this.center.z,
               1200,
               this.finalRadius * 2.5,
               this.finalRadius * 1.5,
               this.finalRadius * 2.5,
               0.025
            );
         this.level.playSound(null, this.center.x, this.center.y + 24.0, this.center.z, SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 4.5F, 0.1F);
         this.level.playSound(null, this.center.x, this.center.y + 24.0, this.center.z, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS, 6.0F, 0.06F);
      }

      private void worldGravityDies() {
         double radius = this.finalRadius * 3.4;
         double height = this.finalRadius * 3.4;
         AABB area = new AABB(this.center, this.center).inflate(radius, height, radius);

         for (Entity entity : this.level
            .getEntitiesOfClass(Entity.class, area, e -> e.isAlive() && !(e instanceof Player p && p.isCreative()) && !IstarothDoomTornado.isDoomDisplay(e))) {
            if (!IstarothDoomTornado.ORIGINAL_NO_GRAVITY.containsKey(entity.getUUID())) {
               IstarothDoomTornado.ORIGINAL_NO_GRAVITY.put(entity.getUUID(), entity.isNoGravity());
            }

            entity.setNoGravity(true);
            entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, 2.2 + this.level.random.nextDouble() * 2.2, 0.0));
            entity.hurtMarked = true;
         }

         this.level.sendParticles(ParticleTypes.FLASH, this.center.x, this.center.y + this.finalRadius, this.center.z, 20, 0.0, 0.0, 0.0, 0.0);
         this.level
            .sendParticles(
               ParticleTypes.REVERSE_PORTAL,
               this.center.x,
               this.center.y + this.finalRadius * 0.8,
               this.center.z,
               900,
               this.finalRadius * 2.1,
               this.finalRadius * 1.2,
               this.finalRadius * 2.1,
               1.1
            );
      }

      private void worldEndingGravityCrush() {
         double radius = this.finalRadius * 3.55;
         AABB area = new AABB(this.center, this.center).inflate(radius, this.finalRadius * 3.2, radius);

         for (Entity entity : this.level.getEntitiesOfClass(Entity.class, area, e -> e.isAlive() && !IstarothDoomTornado.isDoomDisplay(e))) {
            if (!(entity instanceof Player player && player.isCreative())) {
               Vec3 toCenter = this.center.subtract(entity.position());
               Vec3 horizontal = new Vec3(toCenter.x, 0.0, toCenter.z);
               if (horizontal.lengthSqr() < 0.01) {
                  horizontal = new Vec3(1.0, 0.0, 0.0);
               }

               Vec3 motion = horizontal.normalize().scale(5.2).add(0.0, -3.25, 0.0);
               entity.setNoGravity(false);
               entity.setDeltaMovement(entity.getDeltaMovement().add(motion));
               entity.hurtMarked = true;
               if (entity instanceof LivingEntity living) {
                  living.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.WORLD_OF_STILL_SECONDS), 130, 6, false, false, true));
                  living.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), 180, 6, false, false, true));
               }
            }
         }

         this.level.sendParticles(ParticleTypes.SONIC_BOOM, this.center.x, this.center.y + this.finalRadius * 0.45, this.center.z, 4, 0.0, 0.0, 0.0, 0.0);
         this.level
            .sendParticles(ParticleTypes.EXPLOSION_EMITTER, this.center.x, this.center.y + this.finalRadius * 0.45, this.center.z, 5, 0.0, 0.0, 0.0, 0.0);
      }

      private void skyFractureJudgement() {
         for (int x = 0; x < 36; x++) {
            double angle = (Math.PI * 2) * x / 36.0;
            double r = this.finalRadius * (1.2 + x % 5 * 0.38);
            Vec3 top = new Vec3(this.center.x + Math.cos(angle) * r, this.center.y + this.finalRadius * 3.5, this.center.z + Math.sin(angle) * r);
            Vec3 bottom = new Vec3(
               this.center.x + Math.cos(angle) * this.finalRadius * 0.22, this.center.y + 8.0, this.center.z + Math.sin(angle) * this.finalRadius * 0.22
            );
            this.lineParticles(top, bottom, x % 2 == 0 ? ParticleTypes.END_ROD : ParticleTypes.FLASH, 0.18);
         }
      }

      private void finalMirrorShatterParticles() {
         double baseY = this.center.y + this.finalRadius * 0.72;

         for (int ring = 0; ring < 12; ring++) {
            double ringRadius = this.finalRadius * (0.25 + ring * 0.3);
            int count = 100 + ring * 38;

            for (int x = 0; x < count; x++) {
               double angle = (Math.PI * 2) * x / count;
               double wobble = Math.sin(x * 0.77 + ring * 1.91) * 0.12;
               double px = this.center.x + Math.cos(angle + wobble) * ringRadius;
               double pz = this.center.z + Math.sin(angle + wobble) * ringRadius;
               double py = baseY + Math.sin(angle * 3.0 + ring) * 2.4;
               this.level
                  .sendParticles(
                     x % 3 == 0 ? ParticleTypes.END_ROD : ParticleTypes.WHITE_ASH,
                     px,
                     py,
                     pz,
                     1,
                     Math.cos(angle) * 0.9,
                     0.22 + ring * 0.05,
                     Math.sin(angle) * 0.9,
                     0.34
                  );
               if (x % 8 == 0) {
                  this.level.sendParticles(ParticleTypes.CRIT, px, py, pz, 5, 0.18, 0.18, 0.18, 0.22);
               }
            }
         }

         for (int x = 0; x < 850; x++) {
            double angle = this.level.random.nextDouble() * Math.PI * 2.0;
            double r = Math.sqrt(this.level.random.nextDouble()) * this.finalRadius * 3.1;
            double px = this.center.x + Math.cos(angle) * r;
            double pz = this.center.z + Math.sin(angle) * r;
            double py = this.center.y + 5.0 + this.level.random.nextDouble() * this.finalRadius * 3.2;
            this.level
               .sendParticles(
                  ParticleTypes.END_ROD, px, py, pz, 1, Math.cos(angle) * 1.7, 0.42 + this.level.random.nextDouble() * 1.1, Math.sin(angle) * 1.7, 0.52
               );
            if (x % 3 == 0) {
               this.level.sendParticles(ParticleTypes.WHITE_ASH, px, py, pz, 1, Math.cos(angle) * 1.9, 0.52, Math.sin(angle) * 1.9, 0.42);
            }
         }

         this.level.sendParticles(ParticleTypes.FLASH, this.center.x, baseY, this.center.z, 32, 0.0, 0.0, 0.0, 0.0);
         this.level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, this.center.x, baseY, this.center.z, 4, 0.0, 0.0, 0.0, 0.0);
      }

      private void finalBurstParticles() {
         double radius = this.finalRadius * 4.35;

         for (int x = 0; x < 3000; x++) {
            double angle = this.level.random.nextDouble() * Math.PI * 2.0;
            double r = Math.sqrt(this.level.random.nextDouble()) * radius;
            double px = this.center.x + Math.cos(angle) * r;
            double pz = this.center.z + Math.sin(angle) * r;
            double py = this.center.y + this.level.random.nextDouble() * this.finalRadius * 4.25;
            this.level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, this.pickBurstBlock(x)), px, py, pz, 1, 1.2, 1.2, 1.2, 1.05);
         }

         this.level
            .sendParticles(ParticleTypes.EXPLOSION_EMITTER, this.center.x, this.center.y + this.finalRadius * 0.76, this.center.z, 9, 0.0, 0.0, 0.0, 0.0);
         this.level.sendParticles(ParticleTypes.FLASH, this.center.x, this.center.y + this.finalRadius * 0.76, this.center.z, 80, 0.0, 0.0, 0.0, 0.0);
         this.level
            .sendParticles(
               ParticleTypes.WHITE_ASH,
               this.center.x,
               this.center.y + this.finalRadius * 0.76,
               this.center.z,
               2600,
               this.finalRadius * 3.0,
               this.finalRadius * 2.2,
               this.finalRadius * 3.0,
               1.1
            );
         this.level
            .sendParticles(
               ParticleTypes.END_ROD,
               this.center.x,
               this.center.y + this.finalRadius * 0.76,
               this.center.z,
               1600,
               this.finalRadius * 2.2,
               this.finalRadius * 1.5,
               this.finalRadius * 2.2,
               0.65
            );
         this.level
            .sendParticles(
               ParticleTypes.CRIT,
               this.center.x,
               this.center.y + this.finalRadius * 0.76,
               this.center.z,
               780,
               this.finalRadius * 1.55,
               this.finalRadius * 1.15,
               this.finalRadius * 1.55,
               0.85
            );
         this.level.sendParticles(ParticleTypes.SONIC_BOOM, this.center.x, this.center.y + this.finalRadius * 0.44, this.center.z, 3, 0.0, 0.0, 0.0, 0.0);
      }

      private void explosionRainParticles() {
         for (int wave = 0; wave < 28; wave++) {
            double y = this.center.y + 48.0 + wave * 20.0;
            double radius = this.finalRadius * (1.25 + wave * 0.3);

            for (int x = 0; x < 130; x++) {
               double angle = this.level.random.nextDouble() * Math.PI * 2.0;
               double r = Math.sqrt(this.level.random.nextDouble()) * radius;
               double px = this.center.x + Math.cos(angle) * r;
               double pz = this.center.z + Math.sin(angle) * r;
               this.level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, this.pickBurstBlock(x + wave)), px, y, pz, 1, 1.55, -3.2, 1.55, 1.35);
               if (x % 4 == 0) {
                  this.level.sendParticles(ParticleTypes.EXPLOSION, px, y - 8.0 - this.level.random.nextDouble() * 42.0, pz, 1, 0.0, 0.0, 0.0, 0.0);
               }

               if (x % 10 == 0) {
                  this.level.sendParticles(ParticleTypes.FLASH, px, y - 6.0, pz, 1, 0.0, 0.0, 0.0, 0.0);
               }

               if (x % 16 == 0) {
                  this.level.sendParticles(ParticleTypes.LARGE_SMOKE, px, y - 4.0, pz, 24, 2.0, 1.5, 2.0, 0.24);
               }
            }
         }
      }

      private void finalShockwaveParticles() {
         for (int ring = 0; ring < 14; ring++) {
            double ringRadius = this.finalRadius * (0.45 + ring * 0.42);
            int count = 145 + ring * 44;

            for (int x = 0; x < count; x++) {
               double angle = (Math.PI * 2) * x / count;
               double px = this.center.x + Math.cos(angle) * ringRadius;
               double pz = this.center.z + Math.sin(angle) * ringRadius;
               double py = this.center.y + 1.0 + ring * 5.1;
               this.level
                  .sendParticles(
                     new BlockParticleOption(ParticleTypes.BLOCK, this.pickBurstBlock(x + ring)),
                     px,
                     py,
                     pz,
                     1,
                     Math.cos(angle) * 2.75,
                     0.42 + ring * 0.16,
                     Math.sin(angle) * 2.75,
                     0.92
                  );
               if (x % 7 == 0) {
                  this.level.sendParticles(ParticleTypes.END_ROD, px, py + 1.0, pz, 1, Math.cos(angle) * 1.35, 0.32, Math.sin(angle) * 1.35, 0.25);
               }
            }
         }
      }

      private void temporalSingularityParticles() {
         double y = this.center.y + this.finalRadius * 0.95;

         for (int beam = 0; beam < 24; beam++) {
            double angle = (Math.PI * 2) * beam / 24.0;
            Vec3 outer = new Vec3(
               this.center.x + Math.cos(angle) * this.finalRadius * 3.65,
               y + Math.sin(angle * 2.0) * 14.0,
               this.center.z + Math.sin(angle) * this.finalRadius * 3.65
            );
            Vec3 inner = new Vec3(this.center.x, y, this.center.z);
            this.lineParticles(outer, inner, beam % 2 == 0 ? ParticleTypes.END_ROD : ParticleTypes.WHITE_ASH, 0.08);
         }

         for (int x = 0; x < 720; x++) {
            double angle = this.level.random.nextDouble() * Math.PI * 2.0;
            double r = this.finalRadius * (0.35 + this.level.random.nextDouble() * 3.7);
            double px = this.center.x + Math.cos(angle) * r;
            double pz = this.center.z + Math.sin(angle) * r;
            double py = this.center.y + this.level.random.nextDouble() * this.finalRadius * 3.2;
            double dx = this.center.x - px;
            double dz = this.center.z - pz;
            double len = Math.max(1.0, Math.sqrt(dx * dx + dz * dz));
            this.level.sendParticles(ParticleTypes.REVERSE_PORTAL, px, py, pz, 1, dx / len * 1.75, 0.42, dz / len * 1.75, 0.55);
         }

         this.level.sendParticles(ParticleTypes.FLASH, this.center.x, y, this.center.z, 24, 0.0, 0.0, 0.0, 0.0);
         this.level.sendParticles(ParticleTypes.SONIC_BOOM, this.center.x, this.center.y + this.finalRadius * 0.6, this.center.z, 3, 0.0, 0.0, 0.0, 0.0);
      }

      private void mirrorGlassRainParticles() {
         for (int layer = 0; layer < 10; layer++) {
            double y = this.center.y + this.finalRadius * (0.8 + layer * 0.3);
            double radius = this.finalRadius * (0.9 + layer * 0.4);
            int count = 110 + layer * 34;

            for (int x = 0; x < count; x++) {
               double angle = (Math.PI * 2) * x / count + this.level.random.nextDouble() * 0.16;
               double r = radius * (0.4 + this.level.random.nextDouble() * 0.8);
               double px = this.center.x + Math.cos(angle) * r;
               double pz = this.center.z + Math.sin(angle) * r;
               this.level.sendParticles(ParticleTypes.CRIT, px, y, pz, 2, Math.cos(angle) * 0.9, -1.2 - layer * 0.22, Math.sin(angle) * 0.9, 0.48);
               if (x % 3 == 0) {
                  this.level.sendParticles(ParticleTypes.END_ROD, px, y, pz, 1, Math.cos(angle) * 0.62, -0.82, Math.sin(angle) * 0.62, 0.3);
               }
            }
         }
      }

      private void lineParticles(Vec3 start, Vec3 end, ParticleOptions particle, double speed) {
         Vec3 diff = end.subtract(start);
         int steps = Math.max(8, Mth.floor(diff.length() * 1.6));

         for (int x = 0; x <= steps; x++) {
            Vec3 pos = start.add(diff.scale((double)x / steps));
            this.level.sendParticles(particle, pos.x, pos.y, pos.z, 1, 0.02, 0.02, 0.02, speed);
         }
      }

      private void finalBlockDevourCrater() {
         if (TensuraGameRules.canSkillGrief(this.level)) {
            int attempts = 1150;
            int removed = 0;
            double radius = this.finalRadius * 1.22;

            for (int x = 0; x < attempts && removed < 360; x++) {
               double angle = this.level.random.nextDouble() * Math.PI * 2.0;
               double r = Math.sqrt(this.level.random.nextDouble()) * radius;
               int bx = Mth.floor(this.center.x + Math.cos(angle) * r);
               int bz = Mth.floor(this.center.z + Math.sin(angle) * r);
               int by = Mth.floor(this.center.y - 6.0 + this.level.random.nextInt(22));
               BlockPos pos = new BlockPos(bx, by, bz);
               if (this.level.isLoaded(pos) && this.level.getBlockEntity(pos) == null) {
                  BlockState state = this.level.getBlockState(pos);
                  if (this.canEatBlock(state, pos)) {
                     this.level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                     this.level
                        .sendParticles(
                           new BlockParticleOption(ParticleTypes.BLOCK, state),
                           pos.getX() + 0.5,
                           pos.getY() + 0.5,
                           pos.getZ() + 0.5,
                           32,
                           1.35,
                           2.05,
                           1.35,
                           0.96
                        );
                     removed++;
                  }
               }
            }
         }
      }

      private void finalMirrorCrater() {
         if (TensuraGameRules.canSkillGrief(this.level)) {
            int attempts = 760;
            int changed = 0;
            double radius = this.finalRadius * 0.88;

            for (int x = 0; x < attempts && changed < 210; x++) {
               double angle = this.level.random.nextDouble() * Math.PI * 2.0;
               double r = Math.sqrt(this.level.random.nextDouble()) * radius;
               int bx = Mth.floor(this.center.x + Math.cos(angle) * r);
               int bz = Mth.floor(this.center.z + Math.sin(angle) * r);
               int by = Mth.floor(this.center.y - 1.0 + this.level.random.nextInt(7));
               BlockPos pos = new BlockPos(bx, by, bz);
               if (this.level.isLoaded(pos) && this.level.getBlockEntity(pos) == null) {
                  BlockState old = this.level.getBlockState(pos);
                  if (!old.isAir() && this.canEatBlock(old, pos)) {
                     BlockState glass = this.level.random.nextBoolean() ? Blocks.GLASS.defaultBlockState() : Blocks.TINTED_GLASS.defaultBlockState();
                     this.level.setBlock(pos, glass, 3);
                     this.level.sendParticles(ParticleTypes.CRIT, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 14, 0.65, 0.46, 0.65, 0.3);
                     changed++;
                  }
               }
            }
         }
      }

      private BlockState pickBurstBlock(int x) {
         return switch (x % 24) {
            case 0 -> Blocks.GRASS_BLOCK.defaultBlockState();
            case 1 -> Blocks.DIRT.defaultBlockState();
            case 2 -> Blocks.COARSE_DIRT.defaultBlockState();
            case 3 -> Blocks.ROOTED_DIRT.defaultBlockState();
            case 4 -> Blocks.STONE.defaultBlockState();
            case 5 -> Blocks.COBBLESTONE.defaultBlockState();
            case 6 -> Blocks.ANDESITE.defaultBlockState();
            case 7 -> Blocks.DIORITE.defaultBlockState();
            case 8 -> Blocks.GRANITE.defaultBlockState();
            case 9 -> Blocks.DEEPSLATE.defaultBlockState();
            case 10 -> Blocks.TUFF.defaultBlockState();
            case 11 -> Blocks.CALCITE.defaultBlockState();
            case 12 -> Blocks.SMOOTH_BASALT.defaultBlockState();
            case 13 -> Blocks.BLACKSTONE.defaultBlockState();
            case 14 -> Blocks.OAK_LOG.defaultBlockState();
            case 15 -> Blocks.OAK_LEAVES.defaultBlockState();
            case 16 -> Blocks.SAND.defaultBlockState();
            case 17 -> Blocks.RED_SAND.defaultBlockState();
            case 18 -> Blocks.SANDSTONE.defaultBlockState();
            case 19 -> Blocks.RED_SANDSTONE.defaultBlockState();
            case 20 -> Blocks.GLASS.defaultBlockState();
            case 21 -> Blocks.TINTED_GLASS.defaultBlockState();
            case 22 -> Blocks.WHITE_STAINED_GLASS.defaultBlockState();
            default -> Blocks.LIGHT_GRAY_STAINED_GLASS.defaultBlockState();
         };
      }
   }
}
