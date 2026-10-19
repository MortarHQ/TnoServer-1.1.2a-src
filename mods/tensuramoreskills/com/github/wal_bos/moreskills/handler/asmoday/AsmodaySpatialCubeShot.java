package com.github.wal_bos.moreskills.handler.asmoday;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.AsmodaySpatialCubePayload;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class AsmodaySpatialCubeShot {
   private static final Map<UUID, AsmodaySpatialCubeShot.ShotState> STATES = new ConcurrentHashMap<>();

   private AsmodaySpatialCubeShot() {
   }

   public static void fire(LivingEntity owner, LivingEntity target, boolean mastered, boolean shifted) {
      if (owner.level() instanceof ServerLevel level) {
         int streams = shifted ? (Integer)cfg().spatialCubeStreamsShifted.get() : (Integer)cfg().spatialCubeStreams.get();
         int serverCubesPerStream = shifted
            ? (mastered ? (Integer)cfg().spatialCubeCubesPerStreamShiftedMastered.get() : (Integer)cfg().spatialCubeCubesPerStreamShifted.get())
            : (mastered ? (Integer)cfg().spatialCubeCubesPerStreamMastered.get() : (Integer)cfg().spatialCubeCubesPerStream.get());
         int visualCubes = shifted
            ? (mastered ? (Integer)cfg().spatialCubeVisualCubesShiftedMastered.get() : (Integer)cfg().spatialCubeVisualCubesShifted.get())
            : (mastered ? (Integer)cfg().spatialCubeVisualCubesMastered.get() : (Integer)cfg().spatialCubeVisualCubes.get());
         int seed = owner.getRandom().nextInt();
         Vec3 origin = owner.position().add(0.0, owner.getBbHeight() * 0.72, 0.0);
         Vec3 targetPos = target.position().add(0.0, target.getBbHeight() * 0.62, 0.0);
         UUID ownerId = owner.getUUID();
         STATES.put(
            ownerId,
            new AsmodaySpatialCubeShot.ShotState(
               ownerId,
               target.getUUID(),
               level.dimension(),
               origin,
               seed,
               streams,
               serverCubesPerStream,
               mastered,
               shifted,
               owner.tickCount,
               (Integer)cfg().spatialCubeServerLifeTicks.get()
            )
         );
         AsmodaySpatialCubePayload payload = new AsmodaySpatialCubePayload(
            owner.getId() * 65536 + owner.tickCount * 31 + target.getId(),
            owner.getId(),
            target.getId(),
            origin.x,
            origin.y,
            origin.z,
            targetPos.x,
            targetPos.y,
            targetPos.z,
            visualCubes,
            seed,
            (Integer)cfg().spatialCubeClientDurationTicks.get(),
            mastered,
            shifted
         );
         AABB watchers = owner.getBoundingBox().inflate((Double)cfg().spatialCubeWatchRange.get());

         for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, watchers, p -> p != null && !p.isSpectator())) {
            NetworkManager.sendToPlayer(player, payload);
         }

         level.playSound(null, owner.blockPosition(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.PLAYERS, 1.75F, shifted ? 0.18F : 0.26F);
         level.playSound(null, owner.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 0.95F, shifted ? 0.11F : 0.17F);
         level.playSound(null, target.blockPosition(), SoundEvents.TRIAL_SPAWNER_DETECT_PLAYER, SoundSource.PLAYERS, 1.15F, shifted ? 0.28F : 0.38F);
      }
   }

   public static void tick(ServerLevel level) {
      Iterator<Entry<UUID, AsmodaySpatialCubeShot.ShotState>> iterator = STATES.entrySet().iterator();

      while (iterator.hasNext()) {
         AsmodaySpatialCubeShot.ShotState state = iterator.next().getValue();
         if (state.dimension == level.dimension()) {
            LivingEntity owner = level.getEntity(state.owner) instanceof LivingEntity living && living.isAlive() ? living : null;
            LivingEntity target = level.getEntity(state.target) instanceof LivingEntity living && living.isAlive() ? living : null;
            if (owner != null && target != null && validEnemy(owner, target)) {
               int age = owner.tickCount - state.startTick;
               if (age > state.life) {
                  iterator.remove();
               } else {
                  Vec3 origin = state.origin;
                  Vec3 targetPos = target.position().add(0.0, target.getBbHeight() * 0.62, 0.0);
                  AsmodaySpatialCubeShot.Basis basis = basis(origin, targetPos);
                  if (age >= state.nextHitAge) {
                     boolean damaged = false;

                     for (int stream = 0; stream < state.streams && !damaged; stream++) {
                        for (int step = 0; step < state.cubesPerStream; step++) {
                           int delay = step
                              * state.shifted ? (Integer)cfg().spatialCubeShiftedStepDelayTicks.get() : (Integer)cfg().spatialCubeNormalStepDelayTicks.get();
                           float localAge = age - delay;
                           if (!(localAge < 0.0F)) {
                              float progress = Mth.clamp(
                                 localAge
                                    / (
                                       state.shifted
                                          ? ((Double)cfg().spatialCubeShiftedTravelTicks.get()).floatValue()
                                          : ((Double)cfg().spatialCubeNormalTravelTicks.get()).floatValue()
                                    ),
                                 0.0F,
                                 1.0F
                              );
                              progress = smoothAggressive(progress, state.shifted);
                              if (!(progress >= 1.0F)) {
                                 Vec3 current = streamPoint(origin, targetPos, basis, stream, state.streams, step, progress, state.seed, state.shifted);
                                 Vec3 previous = streamPoint(
                                    origin, targetPos, basis, stream, state.streams, step, Math.max(0.0F, progress - 0.04F), state.seed, state.shifted
                                 );
                                 double hitbox = cubeHitRadius(state.mastered, state.shifted, stream, step);
                                 if (segmentHits(target, previous, current, hitbox)) {
                                    hit(level, owner, target, state);
                                    state.nextHitAge = age
                                       + state.shifted
                                          ? (Integer)cfg().spatialCubeShiftedHitCooldownTicks.get()
                                          : (Integer)cfg().spatialCubeNormalHitCooldownTicks.get();
                                    damaged = true;
                                    break;
                                 }
                              }
                           }
                        }
                     }

                     if (age % (Integer)cfg().spatialCubeTrailParticleIntervalTicks.get() == 0) {
                        for (int stream = 0; stream < state.streams; stream++) {
                           Vec3 p = streamPoint(
                              origin, targetPos, basis, stream, state.streams, 0, Mth.clamp(age / 72.0F, 0.0F, 1.0F), state.seed, state.shifted
                           );
                           level.sendParticles(ParticleTypes.REVERSE_PORTAL, p.x, p.y, p.z, state.shifted ? 10 : 7, 0.2, 0.2, 0.2, 0.08);
                        }
                     }
                  }
               }
            } else {
               iterator.remove();
            }
         }
      }
   }

   private static double cubeHitRadius(boolean mastered, boolean shifted, int stream, int step) {
      double base = shifted ? (Double)cfg().spatialCubeShiftedBaseHitRadius.get() : (Double)cfg().spatialCubeNormalBaseHitRadius.get();

      double variance = switch ((step + stream * 3) % 10) {
         case 0 -> cfg().spatialCubeVariance0.get();
         case 1 -> cfg().spatialCubeVariance1.get();
         case 2 -> cfg().spatialCubeVariance2.get();
         default -> 0.0;
         case 4 -> cfg().spatialCubeVariance4.get();
      };
      if (mastered) {
         variance += cfg().spatialCubeMasteredHitRadiusBonus.get();
      }

      return base + variance + (Double)cfg().spatialCubeHitboxPadding.get();
   }

   private static void hit(ServerLevel level, LivingEntity owner, LivingEntity target, AsmodaySpatialCubeShot.ShotState state) {
      if (!(target instanceof Player player && player.getAbilities().invulnerable)) {
         DamageSource source = TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.SPACE_ELEMENTAL, owner);
         float base = state.mastered ? ((Double)cfg().spatialCubeDamageMastered.get()).floatValue() : ((Double)cfg().spatialCubeDamage.get()).floatValue();
         float shiftBonus = state.shifted ? ((Double)cfg().spatialCubeShiftDamageBonus.get()).floatValue() : 0.0F;
         float streamBonus = state.streams
            * (
               state.shifted
                  ? ((Double)cfg().spatialCubeShiftedStreamDamage.get()).floatValue()
                  : ((Double)cfg().spatialCubeNormalStreamDamage.get()).floatValue()
            );
         float cubeBonus = state.cubesPerStream
            * (state.mastered ? ((Double)cfg().spatialCubeCubeDamageMastered.get()).floatValue() : ((Double)cfg().spatialCubeCubeDamage.get()).floatValue());
         float percent = target instanceof Player
            ? target.getMaxHealth()
               * (
                  state.shifted
                     ? ((Double)cfg().spatialCubeShiftedPlayerMaxHealthDamage.get()).floatValue()
                     : ((Double)cfg().spatialCubeNormalPlayerMaxHealthDamage.get()).floatValue()
               )
            : target.getMaxHealth()
               * (
                  state.shifted
                     ? ((Double)cfg().spatialCubeShiftedMobMaxHealthDamage.get()).floatValue()
                     : ((Double)cfg().spatialCubeNormalMobMaxHealthDamage.get()).floatValue()
               );
         float damage = base + shiftBonus + streamBonus + cubeBonus + percent;
         if (state.mastered) {
            damage *= ((Double)cfg().spatialCubeMasteredDamageMultiplier.get()).floatValue();
         }

         if (state.shifted) {
            damage *= ((Double)cfg().spatialCubeShiftedDamageMultiplier.get()).floatValue();
         }

         if (target instanceof Player) {
            damage *= ((Double)cfg().spatialCubePlayerDamageMultiplier.get()).floatValue();
         }

         target.invulnerableTime = 0;
         target.hurt(source, damage);
         target.invulnerableTime = 0;
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE),
               state.mastered ? (Integer)cfg().spatialCubeSpatialBlockadeTicksMastered.get() : (Integer)cfg().spatialCubeSpatialBlockadeTicks.get(),
               state.shifted ? (Integer)cfg().spatialCubeSpatialBlockadeAmplifierShifted.get() : (Integer)cfg().spatialCubeSpatialBlockadeAmplifier.get(),
               false,
               false,
               true
            )
         );
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE),
               state.mastered ? (Integer)cfg().spatialCubeMagicInterferenceTicksMastered.get() : (Integer)cfg().spatialCubeMagicInterferenceTicks.get(),
               state.shifted ? (Integer)cfg().spatialCubeMagicInterferenceAmplifierShifted.get() : (Integer)cfg().spatialCubeMagicInterferenceAmplifier.get(),
               false,
               false,
               true
            )
         );
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE),
               state.mastered ? (Integer)cfg().spatialCubeMovementInterferenceTicksMastered.get() : (Integer)cfg().spatialCubeMovementInterferenceTicks.get(),
               state.shifted
                  ? (Integer)cfg().spatialCubeMovementInterferenceAmplifierShifted.get()
                  : (Integer)cfg().spatialCubeMovementInterferenceAmplifier.get(),
               false,
               false,
               true
            )
         );
         Vec3 p = target.position().add(0.0, target.getBbHeight() * 0.55, 0.0);
         level.sendParticles(ParticleTypes.REVERSE_PORTAL, p.x, p.y, p.z, state.shifted ? 70 : 46, 0.55, 0.55, 0.55, 0.24);
         level.sendParticles(ParticleTypes.DRAGON_BREATH, p.x, p.y, p.z, state.shifted ? 46 : 30, 0.32, 0.32, 0.32, 0.07);
         level.playSound(null, target.blockPosition(), (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.PLAYERS, 1.25F, 0.12F);
         level.playSound(null, target.blockPosition(), SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS, 1.15F, 0.36F);
      }
   }

   private static Vec3 streamPoint(
      Vec3 origin, Vec3 target, AsmodaySpatialCubeShot.Basis basis, int stream, int streams, int step, float progress, int seed, boolean shifted
   ) {
      double sideSpread = shifted ? (Double)cfg().spatialCubeShiftedSideSpread.get() : (Double)cfg().spatialCubeNormalSideSpread.get();
      double side = (stream - (streams - 1) * 0.5) * (sideSpread / Math.max(1.0, streams - 1.0));
      double back = shifted ? (Double)cfg().spatialCubeShiftedBackOffset.get() : (Double)cfg().spatialCubeNormalBackOffset.get();
      Vec3 start = origin.subtract(basis.forward.scale(back + step * (Double)cfg().spatialCubeStepBackOffset.get()))
         .add(basis.right.scale(side))
         .add(basis.up.scale(streamHeight(stream, shifted)));
      Vec3 end = target.add(
            basis.right
               .scale(side * shifted ? (Double)cfg().spatialCubeShiftedTargetSideMultiplier.get() : (Double)cfg().spatialCubeNormalTargetSideMultiplier.get())
         )
         .add(
            basis.up
               .scale(
                  streamHeight(stream, shifted)
                     * shifted ? (Double)cfg().spatialCubeShiftedTargetHeightMultiplier.get() : (Double)cfg().spatialCubeNormalTargetHeightMultiplier.get()
               )
         );
      double bend = Math.sin(progress * Math.PI);
      double curveLift = bend * shifted ? (Double)cfg().spatialCubeShiftedCurveLift.get() : (Double)cfg().spatialCubeNormalCurveLift.get();
      double curveSide = bend
         * side
         * shifted ? (Double)cfg().spatialCubeShiftedCurveSideMultiplier.get() : (Double)cfg().spatialCubeNormalCurveSideMultiplier.get();
      Vec3 mid = origin.lerp(target, 0.46).add(basis.right.scale(side * 0.43 + curveSide)).add(basis.up.scale(curveLift + streamHeight(stream, shifted)));
      Vec3 a = start.lerp(mid, progress);
      Vec3 b = mid.lerp(end, progress);
      Vec3 p = a.lerp(b, progress);
      double packedJitter = (hash(seed, stream * 911 + step * 37) - 0.5)
         * shifted ? (Double)cfg().spatialCubeShiftedJitter.get() : (Double)cfg().spatialCubeNormalJitter.get();
      double packedUp = (hash(seed, stream * 733 + step * 53) - 0.5)
         * shifted ? (Double)cfg().spatialCubeShiftedUpJitter.get() : (Double)cfg().spatialCubeNormalUpJitter.get();
      double packedForward = (hash(seed, stream * 557 + step * 71) - 0.5)
         * shifted ? (Double)cfg().spatialCubeShiftedForwardJitter.get() : (Double)cfg().spatialCubeNormalForwardJitter.get();
      return p.add(basis.right.scale(packedJitter)).add(basis.up.scale(packedUp)).add(basis.forward.scale(packedForward));
   }

   private static double streamHeight(int stream, boolean shifted) {
      return switch (stream % 4) {
         case 0 -> shifted ? (Double)cfg().spatialCubeHeight0Shifted.get() : (Double)cfg().spatialCubeHeight0.get();
         case 1 -> shifted ? (Double)cfg().spatialCubeHeight1Shifted.get() : (Double)cfg().spatialCubeHeight1.get();
         case 2 -> shifted ? (Double)cfg().spatialCubeHeight2Shifted.get() : (Double)cfg().spatialCubeHeight2.get();
         default -> shifted ? (Double)cfg().spatialCubeHeight3Shifted.get() : (Double)cfg().spatialCubeHeight3.get();
      };
   }

   private static float smoothAggressive(float x, boolean shifted) {
      x = Mth.clamp(x, 0.0F, 1.0F);
      x = x * x * (3.0F - 2.0F * x);
      return Mth.clamp(
         (float)(x * shifted ? (Double)cfg().spatialCubeShiftedSmoothMultiplier.get() : (Double)cfg().spatialCubeNormalSmoothMultiplier.get()), 0.0F, 1.0F
      );
   }

   private static boolean segmentHits(LivingEntity target, Vec3 start, Vec3 end, double inflate) {
      AABB box = target.getBoundingBox().inflate(inflate);
      return !box.contains(start) && !box.contains(end) ? box.clip(start, end).isPresent() : true;
   }

   public static boolean validEnemy(LivingEntity owner, LivingEntity target) {
      if (target == owner) {
         return false;
      } else {
         return target.isAlive() && !target.isSpectator() ? !target.isAlliedTo(owner) : false;
      }
   }

   private static AsmodaySpatialCubeShot.Basis basis(Vec3 caster, Vec3 target) {
      Vec3 aim = target.subtract(caster);
      if (aim.lengthSqr() < 0.001) {
         aim = new Vec3(0.0, 0.0, 1.0);
      }

      Vec3 forward = aim.normalize();
      Vec3 worldUp = Math.abs(forward.y) > 0.92 ? new Vec3(1.0, 0.0, 0.0) : new Vec3(0.0, 1.0, 0.0);
      Vec3 right = forward.cross(worldUp);
      if (right.lengthSqr() < 0.001) {
         right = new Vec3(1.0, 0.0, 0.0);
      }

      right = right.normalize();
      Vec3 up = right.cross(forward);
      if (up.lengthSqr() < 0.001) {
         up = new Vec3(0.0, 1.0, 0.0);
      }

      up = up.normalize();
      return new AsmodaySpatialCubeShot.Basis(forward, right, up);
   }

   private static float hash(int seed, int index) {
      int x = seed ^ index * 1103515245;
      x ^= x << 13;
      x ^= x >>> 17;
      x ^= x << 5;
      return (x & 1023) / 1023.0F;
   }

   private static MoreSkillsConfigs.AsmodayConfig cfg() {
      return MoreSkillsConfigs.GRAND.asmoday;
   }

   private record Basis(Vec3 forward, Vec3 right, Vec3 up) {
   }

   private static final class ShotState {
      private final UUID owner;
      private final UUID target;
      private final ResourceKey<Level> dimension;
      private final Vec3 origin;
      private final int seed;
      private final int streams;
      private final int cubesPerStream;
      private final boolean mastered;
      private final boolean shifted;
      private final int startTick;
      private final int life;
      private int nextHitAge;

      private ShotState(
         UUID owner,
         UUID target,
         ResourceKey<Level> dimension,
         Vec3 origin,
         int seed,
         int streams,
         int cubesPerStream,
         boolean mastered,
         boolean shifted,
         int startTick,
         int life
      ) {
         this.owner = owner;
         this.target = target;
         this.dimension = dimension;
         this.origin = origin;
         this.seed = seed;
         this.streams = streams;
         this.cubesPerStream = cubesPerStream;
         this.mastered = mastered;
         this.shifted = shifted;
         this.startTick = startTick;
         this.life = life;
         this.nextHitAge = 0;
      }
   }
}
