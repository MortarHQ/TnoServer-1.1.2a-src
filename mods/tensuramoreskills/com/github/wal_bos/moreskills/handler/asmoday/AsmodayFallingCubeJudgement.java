package com.github.wal_bos.moreskills.handler.asmoday;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.AsmodayFallingCubePayload;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class AsmodayFallingCubeJudgement {
   private static final Map<UUID, AsmodayFallingCubeJudgement.JudgementState> STATES = new ConcurrentHashMap<>();

   private AsmodayFallingCubeJudgement() {
   }

   public static void single(LivingEntity owner, LivingEntity target, boolean mastered) {
      if (owner.level() instanceof ServerLevel level) {
         int var11 = owner.getRandom().nextInt();
         Vec3 origin = owner.position().add(0.0, owner.getBbHeight() * 0.7, 0.0);
         Vec3 center = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
         double groundY = findGroundY(level, center, center.y + 64.0, 96);
         boolean hasGround = !Double.isNaN(groundY);
         AsmodayFallingCubeJudgement.JudgementState state = AsmodayFallingCubeJudgement.JudgementState.single(
            owner.getUUID(), target.getUUID(), level.dimension(), origin, center, var11, mastered, owner.tickCount, groundY, hasGround
         );
         STATES.put(owner.getUUID(), state);
         send(level, owner, target, origin, center, 1, var11, state.totalDuration, mastered, false);
         level.playSound(null, owner.blockPosition(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.PLAYERS, 1.7F, 0.2F);
         level.playSound(null, target.blockPosition(), SoundEvents.TRIAL_SPAWNER_DETECT_PLAYER, SoundSource.PLAYERS, 1.15F, 0.26F);
      }
   }

   public static void rain(LivingEntity owner, boolean mastered) {
      if (owner.level() instanceof ServerLevel level) {
         int var13 = owner.getRandom().nextInt();
         int cubes = mastered ? (Integer)cfg().cubeJudgementRainCubesMastered.get() : (Integer)cfg().cubeJudgementRainCubes.get();
         Vec3 origin = owner.position().add(0.0, owner.getBbHeight() * 0.7, 0.0);
         Vec3 center = owner.position();
         AsmodayFallingCubeJudgement.JudgementState state = AsmodayFallingCubeJudgement.JudgementState.rain(
            owner.getUUID(), level.dimension(), origin, center, var13, cubes, mastered, owner.tickCount
         );

         for (int i = 0; i < cubes; i++) {
            Vec3 base = rainBase(center, i, var13, mastered);
            double groundY = findGroundY(level, base, base.y + 64.0, 96);
            boolean hasGround = !Double.isNaN(groundY);
            state.cubeGroundY.put(i, groundY);
            state.cubeHasGround.put(i, hasGround);
         }

         STATES.put(owner.getUUID(), state);
         send(level, owner, null, origin, center, cubes, var13, state.totalDuration, mastered, true);
         level.playSound(null, owner.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 1.15F, 0.09F);
         level.playSound(null, owner.blockPosition(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.PLAYERS, 1.9F, 0.14F);
      }
   }

   public static void tick(ServerLevel level) {
      Iterator<Entry<UUID, AsmodayFallingCubeJudgement.JudgementState>> iterator = STATES.entrySet().iterator();

      while (iterator.hasNext()) {
         AsmodayFallingCubeJudgement.JudgementState state = iterator.next().getValue();
         if (state.dimension == level.dimension()) {
            LivingEntity owner = level.getEntity(state.owner) instanceof LivingEntity living && living.isAlive() ? living : null;
            if (owner != null && !state.forceExpired) {
               int age = owner.tickCount - state.startTick;
               if (age > state.totalDuration) {
                  iterator.remove();
               } else if (state.shifted) {
                  tickRain(level, owner, state, age);
               } else {
                  tickSingle(level, owner, state, age);
               }
            } else {
               iterator.remove();
            }
         }
      }
   }

   private static void tickSingle(ServerLevel level, LivingEntity owner, AsmodayFallingCubeJudgement.JudgementState state, int age) {
      Vec3 base = state.center;
      LivingEntity target = level.getEntity(state.target) instanceof LivingEntity living && living.isAlive() ? living : null;
      if (target != null && validEnemy(owner, target) && !state.hasGround) {
         base = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
      }

      float fallProgress = Mth.clamp((float)age / state.fallDuration, 0.0F, 1.0F);
      double size = state.mastered ? (Double)cfg().cubeJudgementSingleSizeMastered.get() : (Double)cfg().cubeJudgementSingleSize.get();
      Vec3 cubeCenter = fallingPoint(base, 0, fallProgress, state.seed, false, state.mastered, state.groundY, state.hasGround, size);
      if (age % 12 == 0 && fallProgress < 0.9F) {
         level.sendParticles(ParticleTypes.REVERSE_PORTAL, cubeCenter.x, cubeCenter.y, cubeCenter.z, 8, 0.7, 0.25, 0.7, 0.025);
      }

      AABB hitbox = cubeBox(cubeCenter, size).inflate(0.15);

      for (LivingEntity victim : level.getEntitiesOfClass(LivingEntity.class, hitbox, e -> validEnemy(owner, e))) {
         Integer last = state.recentHits.get(victim.getUUID());
         if (last == null || age - last >= (Integer)cfg().cubeJudgementSingleRecentHitTicks.get()) {
            hit(level, owner, victim, state, size, true);
            state.recentHits.put(victim.getUUID(), age);
         }
      }

      if (!state.impactedOnce && fallProgress >= 1.0F) {
         state.impactedOnce = true;
         groundImpact(level, cubeCenter, size, true);
         hitImpactArea(level, owner, state, cubeCenter, size, true);
      }
   }

   private static void tickRain(ServerLevel level, LivingEntity owner, AsmodayFallingCubeJudgement.JudgementState state, int age) {
      int maxHitsThisTick = state.mastered
         ? (Integer)cfg().cubeJudgementRainMaxHitsPerTickMastered.get()
         : (Integer)cfg().cubeJudgementRainMaxHitsPerTick.get();
      int hitsThisTick = 0;
      boolean anyAlive = false;

      for (int i = 0; i < state.cubes; i++) {
         int delay = i * (Integer)cfg().cubeJudgementRainCubeDelayTicks.get()
            + (int)(hash(state.seed, i * 61) * ((Integer)cfg().cubeJudgementRainCubeDelayRandomTicks.get()).intValue());
         float local = age - delay;
         if (local < 0.0F) {
            anyAlive = true;
         } else {
            boolean hasGround = state.cubeHasGround.getOrDefault(i, false);
            int lifeAfterFall = hasGround ? (Integer)cfg().cubeJudgementGroundDespawnTicks.get() : (Integer)cfg().cubeJudgementNoGroundDespawnTicks.get();
            if (!(local > state.fallDuration + lifeAfterFall)) {
               anyAlive = true;
               float fallProgress = Mth.clamp(local / state.fallDuration, 0.0F, 1.0F);
               Vec3 base = rainBase(state.center, i, state.seed, state.mastered);
               double size = rainCubeSize(i, state.mastered);
               double groundY = state.cubeGroundY.getOrDefault(i, Double.NaN);
               Vec3 cubeCenter = fallingPoint(base, i, fallProgress, state.seed, true, state.mastered, groundY, hasGround, size);
               if (age % 10 == 0 && i % 3 == 0 && fallProgress < 0.92F) {
                  level.sendParticles(ParticleTypes.REVERSE_PORTAL, cubeCenter.x, cubeCenter.y, cubeCenter.z, 4, 0.45, 0.18, 0.45, 0.02);
               }

               AABB hitbox = cubeBox(cubeCenter, size).inflate(0.1);

               for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, hitbox, e -> validEnemy(owner, e))) {
                  Integer last = state.recentHits.get(target.getUUID());
                  if (last == null || age - last >= (Integer)cfg().cubeJudgementRainRecentHitTicks.get()) {
                     hit(level, owner, target, state, size, false);
                     state.recentHits.put(target.getUUID(), age);
                     if (++hitsThisTick >= maxHitsThisTick) {
                        break;
                     }
                  }
               }

               if (!state.impactedCubes.containsKey(i) && fallProgress >= 1.0F) {
                  state.impactedCubes.put(i, true);
                  groundImpact(level, cubeCenter, size, false);
                  hitImpactArea(level, owner, state, cubeCenter, size, false);
               }

               if (hitsThisTick >= maxHitsThisTick) {
                  break;
               }
            }
         }
      }

      if (!anyAlive && age > state.fallDuration + (Integer)cfg().cubeJudgementGroundDespawnTicks.get() + 25) {
         state.forceExpired = true;
      }
   }

   private static void hitImpactArea(
      ServerLevel level, LivingEntity owner, AsmodayFallingCubeJudgement.JudgementState state, Vec3 cubeCenter, double size, boolean single
   ) {
      double radius = single ? size * 0.95 : size * 0.62;
      AABB area = new AABB(cubeCenter, cubeCenter).inflate(radius, radius * 0.55, radius);
      int hit = 0;
      int max = single ? (Integer)cfg().cubeJudgementSingleMaxImpactTargets.get() : (Integer)cfg().cubeJudgementRainMaxImpactTargets.get();

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area, e -> validEnemy(owner, e))) {
         hit(level, owner, target, state, size, single);
         if (++hit >= max) {
            return;
         }
      }
   }

   private static void hit(
      ServerLevel level, LivingEntity owner, LivingEntity target, AsmodayFallingCubeJudgement.JudgementState state, double cubeSize, boolean single
   ) {
      if (!(target instanceof Player player && player.getAbilities().invulnerable)) {
         DamageSource source = TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.SPACE_ELEMENTAL, owner);
         float base = single
            ? (
               state.mastered
                  ? ((Double)cfg().cubeJudgementSingleDamageMastered.get()).floatValue()
                  : ((Double)cfg().cubeJudgementSingleDamage.get()).floatValue()
            )
            : (state.mastered ? ((Double)cfg().cubeJudgementRainDamageMastered.get()).floatValue() : ((Double)cfg().cubeJudgementRainDamage.get()).floatValue());
         float sizeBonus = (float)(
            cubeSize * single ? (Double)cfg().cubeJudgementSingleSizeDamageMultiplier.get() : (Double)cfg().cubeJudgementRainSizeDamageMultiplier.get()
         );
         float percent = target instanceof Player
            ? target.getMaxHealth()
               * (
                  single
                     ? ((Double)cfg().cubeJudgementSinglePlayerMaxHealthDamage.get()).floatValue()
                     : ((Double)cfg().cubeJudgementRainPlayerMaxHealthDamage.get()).floatValue()
               )
            : target.getMaxHealth()
               * (
                  single
                     ? ((Double)cfg().cubeJudgementSingleMobMaxHealthDamage.get()).floatValue()
                     : ((Double)cfg().cubeJudgementRainMobMaxHealthDamage.get()).floatValue()
               );
         float damage = base + sizeBonus + percent;
         if (target instanceof Player) {
            damage *= single
               ? ((Double)cfg().cubeJudgementSinglePlayerDamageMultiplier.get()).floatValue()
               : ((Double)cfg().cubeJudgementRainPlayerDamageMultiplier.get()).floatValue();
         }

         target.invulnerableTime = 0;
         target.hurt(source, damage);
         target.invulnerableTime = 0;
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE),
               single ? (Integer)cfg().cubeJudgementSingleSpatialBlockadeTicks.get() : (Integer)cfg().cubeJudgementRainSpatialBlockadeTicks.get(),
               single ? (Integer)cfg().cubeJudgementSingleSpatialBlockadeAmplifier.get() : (Integer)cfg().cubeJudgementRainSpatialBlockadeAmplifier.get(),
               false,
               false,
               true
            )
         );
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE),
               single ? (Integer)cfg().cubeJudgementSingleMagicInterferenceTicks.get() : (Integer)cfg().cubeJudgementRainMagicInterferenceTicks.get(),
               single ? (Integer)cfg().cubeJudgementSingleMagicInterferenceAmplifier.get() : (Integer)cfg().cubeJudgementRainMagicInterferenceAmplifier.get(),
               false,
               false,
               true
            )
         );
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE),
               single ? (Integer)cfg().cubeJudgementSingleMovementInterferenceTicks.get() : (Integer)cfg().cubeJudgementRainMovementInterferenceTicks.get(),
               single
                  ? (Integer)cfg().cubeJudgementSingleMovementInterferenceAmplifier.get()
                  : (Integer)cfg().cubeJudgementRainMovementInterferenceAmplifier.get(),
               false,
               false,
               true
            )
         );
         Vec3 p = target.position().add(0.0, target.getBbHeight() * 0.55, 0.0);
         level.sendParticles(ParticleTypes.REVERSE_PORTAL, p.x, p.y, p.z, single ? 46 : 22, 0.38, 0.25, 0.38, 0.16);
         level.sendParticles(ParticleTypes.DRAGON_BREATH, p.x, p.y, p.z, single ? 24 : 10, 0.22, 0.16, 0.22, 0.04);
         level.playSound(
            null,
            target.blockPosition(),
            (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(),
            SoundSource.PLAYERS,
            single ? 0.95F : 0.58F,
            single ? 0.1F : 0.16F
         );
         level.playSound(null, target.blockPosition(), SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS, single ? 0.86F : 0.5F, single ? 0.3F : 0.42F);
      }
   }

   private static void groundImpact(ServerLevel level, Vec3 pos, double size, boolean single) {
      double spread = single ? size * 0.62 : size * 0.34;
      level.sendParticles(ParticleTypes.EXPLOSION, pos.x, pos.y, pos.z, single ? 4 : 1, 0.0, 0.0, 0.0, 0.0);
      level.sendParticles(ParticleTypes.REVERSE_PORTAL, pos.x, pos.y, pos.z, single ? 120 : 42, spread, 0.28, spread, single ? 0.26 : 0.16);
      level.sendParticles(ParticleTypes.DRAGON_BREATH, pos.x, pos.y + 0.1, pos.z, single ? 72 : 24, spread * 0.74, 0.16, spread * 0.74, 0.05);
      level.sendParticles(ParticleTypes.SMOKE, pos.x, pos.y + 0.05, pos.z, single ? 58 : 20, spread * 0.72, 0.08, spread * 0.72, 0.035);
      level.sendParticles(ParticleTypes.LARGE_SMOKE, pos.x, pos.y + 0.12, pos.z, single ? 18 : 6, spread * 0.48, 0.05, spread * 0.48, 0.015);
      level.sendParticles(ParticleTypes.FLAME, pos.x, pos.y + 0.12, pos.z, single ? 18 : 6, spread * 0.42, 0.05, spread * 0.42, 0.025);
      level.playSound(
         null, pos.x, pos.y, pos.z, (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.PLAYERS, single ? 1.45F : 0.82F, single ? 0.06F : 0.11F
      );
      level.playSound(
         null, pos.x, pos.y, pos.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, single ? 1.05F : 0.46F, single ? 0.25F : 0.42F
      );
      level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS, single ? 1.2F : 0.6F, single ? 0.26F : 0.38F);
      level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, single ? 0.65F : 0.22F, single ? 0.2F : 0.3F);
   }

   private static void send(
      ServerLevel level,
      LivingEntity owner,
      LivingEntity target,
      Vec3 origin,
      Vec3 center,
      int cubes,
      int seed,
      int duration,
      boolean mastered,
      boolean shifted
   ) {
      int targetId = target == null ? -1 : target.getId();
      AsmodayFallingCubePayload payload = new AsmodayFallingCubePayload(
         owner.getId() * 65536 + owner.tickCount * 37 + seed,
         owner.getId(),
         targetId,
         origin.x,
         origin.y,
         origin.z,
         center.x,
         center.y,
         center.z,
         cubes,
         seed,
         duration,
         mastered,
         shifted
      );
      AABB watchers = owner.getBoundingBox().inflate((Double)cfg().cubeJudgementWatchRange.get());

      for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, watchers, p -> p != null && !p.isSpectator())) {
         NetworkManager.sendToPlayer(player, payload);
      }
   }

   private static double findGroundY(ServerLevel level, Vec3 base, double startY, int maxDown) {
      int x = Mth.floor(base.x);
      int z = Mth.floor(base.z);
      int top = Mth.floor(startY);
      int bottom = Math.max(level.getMinBuildHeight(), top - maxDown);

      for (int y = top; y >= bottom; y--) {
         BlockPos pos = new BlockPos(x, y, z);
         BlockState state = level.getBlockState(pos);
         if (!state.isAir() && state.getFluidState().isEmpty() && state.isCollisionShapeFullBlock(level, pos)) {
            return y + 1.0;
         }
      }

      return Double.NaN;
   }

   private static Vec3 rainBase(Vec3 center, int index, int seed, boolean mastered) {
      double radius = mastered ? (Double)cfg().cubeJudgementRainRadiusMastered.get() : (Double)cfg().cubeJudgementRainRadius.get();
      double a = (Math.PI * 2) * hash(seed, index * 91 + 7);
      double r = radius * Math.sqrt(hash(seed, index * 67 + 17));
      return center.add(Math.cos(a) * r, 0.0, Math.sin(a) * r);
   }

   private static Vec3 fallingPoint(
      Vec3 base, int index, float progress, int seed, boolean rain, boolean mastered, double groundY, boolean hasGround, double size
   ) {
      float slow = progress * progress * (3.0F - 2.0F * progress);
      double startHeight = rain
         ? (mastered ? (Double)cfg().cubeJudgementRainStartHeightMastered.get() : (Double)cfg().cubeJudgementRainStartHeight.get())
         : (mastered ? (Double)cfg().cubeJudgementSingleStartHeightMastered.get() : (Double)cfg().cubeJudgementSingleStartHeight.get());
      double fallbackEndY = base.y
         + rain
            ? (mastered ? (Double)cfg().cubeJudgementRainFallbackEndYOffsetMastered.get() : (Double)cfg().cubeJudgementRainFallbackEndYOffset.get())
            : (mastered ? (Double)cfg().cubeJudgementSingleFallbackEndYOffsetMastered.get() : (Double)cfg().cubeJudgementSingleFallbackEndYOffset.get());
      double endY = hasGround ? groundY + size * 0.5 : fallbackEndY;
      double sway = progress >= 1.0F
         ? 0.0
         : Math.sin(progress * Math.PI) * rain ? (Double)cfg().cubeJudgementRainSway.get() : (Double)cfg().cubeJudgementSingleSway.get();
      double ax = (hash(seed, index * 41 + 1) - 0.5) * sway;
      double az = (hash(seed, index * 43 + 9) - 0.5) * sway;
      return new Vec3(base.x + ax, Mth.lerp(slow, base.y + startHeight, endY), base.z + az);
   }

   private static AABB cubeBox(Vec3 center, double size) {
      double h = size * 0.5;
      return new AABB(center.x - h, center.y - h, center.z - h, center.x + h, center.y + h, center.z + h);
   }

   private static double rainCubeSize(int index, boolean mastered) {
      double base = mastered ? (Double)cfg().cubeJudgementRainSizeMastered.get() : (Double)cfg().cubeJudgementRainSize.get();

      return base + switch (index % 6) {
         case 0 -> mastered ? (Double)cfg().cubeJudgementRainBigSizeBonusMastered.get() : (Double)cfg().cubeJudgementRainBigSizeBonus.get();
         case 1 -> mastered ? (Double)cfg().cubeJudgementRainMediumSizeBonusMastered.get() : (Double)cfg().cubeJudgementRainMediumSizeBonus.get();
         case 2 -> cfg().cubeJudgementRainSmallSizeBonus.get();
         default -> 0.0;
      };
   }

   public static boolean validEnemy(LivingEntity owner, LivingEntity target) {
      if (target == owner) {
         return false;
      } else {
         return target.isAlive() && !target.isSpectator() ? !target.isAlliedTo(owner) : false;
      }
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

   private static final class JudgementState {
      private final UUID owner;
      private final UUID target;
      private final ResourceKey<Level> dimension;
      private final Vec3 origin;
      private final Vec3 center;
      private final int seed;
      private final int cubes;
      private final boolean mastered;
      private final boolean shifted;
      private final int startTick;
      private final int fallDuration;
      private final int totalDuration;
      private final double groundY;
      private final boolean hasGround;
      private boolean impactedOnce;
      private boolean forceExpired;
      private final Map<UUID, Integer> recentHits = new ConcurrentHashMap<>();
      private final Map<Integer, Boolean> impactedCubes = new ConcurrentHashMap<>();
      private final Map<Integer, Double> cubeGroundY = new ConcurrentHashMap<>();
      private final Map<Integer, Boolean> cubeHasGround = new ConcurrentHashMap<>();

      private JudgementState(
         UUID owner,
         UUID target,
         ResourceKey<Level> dimension,
         Vec3 origin,
         Vec3 center,
         int seed,
         int cubes,
         boolean mastered,
         boolean shifted,
         int startTick,
         int fallDuration,
         int totalDuration,
         double groundY,
         boolean hasGround
      ) {
         this.owner = owner;
         this.target = target;
         this.dimension = dimension;
         this.origin = origin;
         this.center = center;
         this.seed = seed;
         this.cubes = cubes;
         this.mastered = mastered;
         this.shifted = shifted;
         this.startTick = startTick;
         this.fallDuration = fallDuration;
         this.totalDuration = totalDuration;
         this.groundY = groundY;
         this.hasGround = hasGround;
      }

      private static AsmodayFallingCubeJudgement.JudgementState single(
         UUID owner,
         UUID target,
         ResourceKey<Level> dimension,
         Vec3 origin,
         Vec3 center,
         int seed,
         boolean mastered,
         int startTick,
         double groundY,
         boolean hasGround
      ) {
         int total = (Integer)AsmodayFallingCubeJudgement.cfg().cubeJudgementSingleFallDurationTicks.get()
            + hasGround
               ? (Integer)AsmodayFallingCubeJudgement.cfg().cubeJudgementGroundDespawnTicks.get()
               : (Integer)AsmodayFallingCubeJudgement.cfg().cubeJudgementNoGroundDespawnTicks.get();
         return new AsmodayFallingCubeJudgement.JudgementState(
            owner,
            target,
            dimension,
            origin,
            center,
            seed,
            1,
            mastered,
            false,
            startTick,
            (Integer)AsmodayFallingCubeJudgement.cfg().cubeJudgementSingleFallDurationTicks.get(),
            total,
            groundY,
            hasGround
         );
      }

      private static AsmodayFallingCubeJudgement.JudgementState rain(
         UUID owner, ResourceKey<Level> dimension, Vec3 origin, Vec3 center, int seed, int cubes, boolean mastered, int startTick
      ) {
         int total = (Integer)AsmodayFallingCubeJudgement.cfg().cubeJudgementRainFallDurationTicks.get()
            + (Integer)AsmodayFallingCubeJudgement.cfg().cubeJudgementNoGroundDespawnTicks.get()
            + (Integer)AsmodayFallingCubeJudgement.cfg().cubeJudgementRainExtraLifetimeTicks.get();
         return new AsmodayFallingCubeJudgement.JudgementState(
            owner,
            null,
            dimension,
            origin,
            center,
            seed,
            cubes,
            mastered,
            true,
            startTick,
            (Integer)AsmodayFallingCubeJudgement.cfg().cubeJudgementRainFallDurationTicks.get(),
            total,
            Double.NaN,
            false
         );
      }
   }
}
