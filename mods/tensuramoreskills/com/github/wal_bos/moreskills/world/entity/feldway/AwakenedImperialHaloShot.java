package com.github.wal_bos.moreskills.world.entity.feldway;

import com.github.wal_bos.moreskills.configs.EntityConfigs;
import com.github.wal_bos.moreskills.net.msg.ImperialHaloShotPayload;
import com.github.wal_bos.moreskills.world.entity.FeldwayAwakenedEntity;
import dev.architectury.networking.NetworkManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class AwakenedImperialHaloShot {
   public static final int ATTACK_ID = 80;
   private static final int END_TICK = (Integer)cfg().imperialEndTick.get();
   private static final int COOLDOWN = (Integer)cfg().imperialCooldownTicks.get();
   private static final int FIRST_FIRE_TICK = (Integer)cfg().imperialFirstFireTick.get();
   private static final int FIRE_INTERVAL = (Integer)cfg().imperialFireInterval.get();
   private static final int SERVER_MISSILE_STAGGER = (Integer)cfg().imperialMissileStagger.get();
   private static final int SERVER_MISSILE_LIFE = (Integer)cfg().imperialMissileLife.get();
   private static final int CLIENT_DURATION = (Integer)cfg().imperialClientDuration.get();
   private static final double WATCH_RANGE = (Double)cfg().imperialWatchRange.get();
   private static final double HIT_RADIUS = (Double)cfg().imperialHitRadius.get();
   private static final double GRAZE_RADIUS = (Double)cfg().imperialGrazeRadius.get();
   private static final Map<UUID, AwakenedImperialHaloShot.ShotState> STATES = new ConcurrentHashMap<>();

   private static EntityConfigs.AwakenedFeldwayConfig cfg() {
      return EntityConfigs.ENTITY.awakenedFeldway;
   }

   private AwakenedImperialHaloShot() {
   }

   public static boolean is(int attackId) {
      return attackId == 80;
   }

   public static void begin(FeldwayAwakenedEntity boss) {
      boss.setAttack(80);
      STATES.remove(boss.getUUID());
      STATES.put(boss.getUUID(), new AwakenedImperialHaloShot.ShotState());
   }

   public static void tick(FeldwayAwakenedEntity boss) {
      LivingEntity target = boss.resolveLockedTarget();
      if (target == null) {
         target = boss.getTarget();
      }

      if (target != null && target.isAlive() && boss.isValidEnemy(target)) {
         AwakenedImperialHaloShot.ShotState state = STATES.computeIfAbsent(boss.getUUID(), id -> new AwakenedImperialHaloShot.ShotState());
         boss.getLookControl().setLookAt(target, 90.0F, 90.0F);
         int t = boss.getAttackTicks();
         tickServerMissiles(boss, target, state);
         if (t >= FIRST_FIRE_TICK && t <= 37 && (t - FIRST_FIRE_TICK) % FIRE_INTERVAL == 0) {
            fireVolley(boss, target, state, t);
         }

         if (t >= END_TICK && state.missiles.isEmpty()) {
            STATES.remove(boss.getUUID());
            boss.clearAttack();
            boss.setGlobalCooldown(COOLDOWN);
         }
      } else {
         STATES.remove(boss.getUUID());
         boss.clearAttack();
         boss.setGlobalCooldown(COOLDOWN);
      }
   }

   private static void fireVolley(FeldwayAwakenedEntity boss, LivingEntity target, AwakenedImperialHaloShot.ShotState state, int attackTick) {
      if (boss.level() instanceof ServerLevel sl) {
         int var17 = (Integer)cfg().imperialMinRings.get() + boss.getRandom().nextInt(Math.max(1, (Integer)cfg().imperialExtraRings.get()));
         int seed = boss.getRandom().nextInt();
         int packetId = boss.getId() * 65536 + boss.tickCount * 16 + attackTick;
         Vec3 caster = boss.position().add(0.0, boss.getBbHeight() * 0.62, 0.0);
         Vec3 targetPos = target.position().add(0.0, target.getBbHeight() * 0.55, 0.0);
         ImperialHaloShotPayload payload = new ImperialHaloShotPayload(
            packetId, boss.getId(), target.getId(), caster.x, caster.y, caster.z, targetPos.x, targetPos.y, targetPos.z, var17, seed, CLIENT_DURATION
         );
         AABB box = boss.getBoundingBox().inflate(WATCH_RANGE);

         for (ServerPlayer player : sl.getEntitiesOfClass(ServerPlayer.class, box, p -> p != null && !p.isSpectator())) {
            NetworkManager.sendToPlayer(player, payload);
         }

         if (attackTick == 1) {
            sl.playSound(null, boss.blockPosition(), SoundEvents.BEACON_POWER_SELECT, SoundSource.HOSTILE, 2.35F, 1.72F);
         }

         sl.playSound(null, boss.blockPosition(), SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.HOSTILE, 1.18F, 1.72F + boss.getRandom().nextFloat() * 0.16F);
         sl.playSound(null, target.blockPosition(), (SoundEvent)SoundEvents.TRIDENT_THROW.value(), SoundSource.HOSTILE, 0.66F, 1.8F);
         AwakenedImperialHaloShot.Basis basis = basis(caster, targetPos);

         for (int i = 0; i < var17; i++) {
            int fireTick = boss.tickCount + 12 + i * SERVER_MISSILE_STAGGER;
            Vec3 socket = socket(caster, basis, 12.0F + i * SERVER_MISSILE_STAGGER, i, var17, seed);
            Vec3 vel = basis.forward
               .scale(0.5)
               .add(basis.right.scale((hash(seed, i + 41) - 0.5F) * 0.42))
               .add(basis.up.scale((hash(seed, i + 83) - 0.5F) * 0.36));
            state.missiles.add(new AwakenedImperialHaloShot.ServerMissile(fireTick, var17, i, socket, vel));
         }
      }
   }

   private static void tickServerMissiles(FeldwayAwakenedEntity boss, LivingEntity target, AwakenedImperialHaloShot.ShotState state) {
      if (boss.level() instanceof ServerLevel sl) {
         Iterator var20 = state.missiles.iterator();

         while (var20.hasNext()) {
            AwakenedImperialHaloShot.ServerMissile missile = (AwakenedImperialHaloShot.ServerMissile)var20.next();
            if (boss.tickCount >= missile.fireTick) {
               if (target.isAlive() && boss.isValidEnemy(target)) {
                  Vec3 caster = boss.position().add(0.0, boss.getBbHeight() * 0.62, 0.0);
                  Vec3 targetCenter = target.position().add(0.0, target.getBbHeight() * 0.55, 0.0);
                  AwakenedImperialHaloShot.Basis basis = basis(caster, targetCenter);
                  missile.age++;
                  missile.prevPos = missile.pos;
                  Vec3 liveTarget = homingTarget(targetCenter, basis, boss.getAttackTicks() + missile.index * SERVER_MISSILE_STAGGER, missile.index);
                  Vec3 toTarget = liveTarget.subtract(missile.pos);
                  double dist = toTarget.length();
                  if (dist > 0.001) {
                     Vec3 wanted = toTarget.normalize();
                     double speed = Mth.clamp(
                        (Double)cfg().imperialMissileBaseSpeed.get() + missile.age * (Double)cfg().imperialMissileSpeedGain.get(),
                        (Double)cfg().imperialMissileBaseSpeed.get(),
                        (Double)cfg().imperialMissileMaxSpeed.get()
                     );
                     double blend = Mth.clamp(0.07 + missile.age * 0.0032, 0.07, 0.23);
                     Vec3 swirl = basis.right
                        .scale(Math.sin((boss.tickCount + missile.index * 13.0) * 0.37) * 0.052)
                        .add(basis.up.scale(Math.cos((boss.tickCount + missile.index * 17.0) * 0.33) * 0.045));
                     Vec3 desiredVelocity = wanted.scale(speed).add(swirl);
                     missile.vel = missile.vel.scale(1.0 - blend).add(desiredVelocity.scale(blend));
                     if (missile.vel.length() > speed) {
                        missile.vel = missile.vel.normalize().scale(speed);
                     }

                     missile.pos = missile.pos.add(missile.vel);
                  }

                  if (segmentHits(target, missile.prevPos, missile.pos, HIT_RADIUS)) {
                     DamageSource source = boss.holySource(true, true);
                     float holy = ((Double)cfg().imperialBaseDamage.get()).floatValue()
                        + missile.ringCount * ((Double)cfg().imperialDamagePerRing.get()).floatValue();
                     float spiritual = ((Double)cfg().imperialBaseSpiritualDamage.get()).floatValue()
                        + missile.ringCount * ((Double)cfg().imperialSpiritualDamagePerRing.get()).floatValue();
                     target.invulnerableTime = 0;
                     target.hurt(source, holy);
                     target.invulnerableTime = 0;
                     boss.hurtSpiritual(target, spiritual);
                     sl.playSound(null, target.blockPosition(), SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.HOSTILE, 0.72F, 1.95F);
                     sl.playSound(null, target.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.HOSTILE, 0.42F, 1.85F);
                     var20.remove();
                  } else {
                     if (segmentHits(target, missile.prevPos, missile.pos, GRAZE_RADIUS) && !missile.grazed) {
                        missile.grazed = true;
                        sl.playSound(null, target.blockPosition(), SoundEvents.AMETHYST_CLUSTER_HIT, SoundSource.HOSTILE, 0.38F, 2.0F);
                     }

                     if (missile.age > SERVER_MISSILE_LIFE || missile.pos.distanceToSqr(targetCenter) > 1296.0 && missile.age > 18) {
                        var20.remove();
                     }
                  }
               } else {
                  var20.remove();
               }
            }
         }
      }
   }

   private static boolean segmentHits(LivingEntity target, Vec3 start, Vec3 end, double inflate) {
      AABB box = target.getBoundingBox().inflate(inflate);
      return !box.contains(start) && !box.contains(end) ? box.clip(start, end).isPresent() : true;
   }

   private static AwakenedImperialHaloShot.Basis basis(Vec3 caster, Vec3 target) {
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
      return new AwakenedImperialHaloShot.Basis(forward, right, up);
   }

   private static Vec3 socket(Vec3 caster, AwakenedImperialHaloShot.Basis basis, float t, int i, int rings, int seed) {
      float base = (float)((Math.PI * 2) * i / rings);
      float wobble = hash(seed, i) * 0.7F;
      float spin = t * 0.145F * ((i & 1) == 0 ? 1.0F : -1.0F);
      float a = base + wobble + spin;
      double orbit = 1.88 + i % 4 * 0.35;
      double yWave = Math.sin(t * 0.18 + i * 1.41) * 0.42;
      return caster.add(basis.right.scale(Math.cos(a) * orbit))
         .add(basis.up.scale(Math.sin(a) * 0.92 + yWave))
         .subtract(basis.forward.scale(0.32 + i % 3 * 0.12));
   }

   private static Vec3 homingTarget(Vec3 target, AwakenedImperialHaloShot.Basis basis, float t, int i) {
      double r = 0.4 + i % 3 * 0.12;
      return target.add(basis.right.scale(Math.sin(t * 0.075 + i * 1.8) * r)).add(basis.up.scale(Math.cos(t * 0.085 + i * 1.3) * r * 0.72));
   }

   private static float hash(int seed, int index) {
      int x = seed ^ index * 1103515245;
      x ^= x << 13;
      x ^= x >>> 17;
      x ^= x << 5;
      return (x & 1023) / 1023.0F;
   }

   private record Basis(Vec3 forward, Vec3 right, Vec3 up) {
   }

   private static final class ServerMissile {
      private final int fireTick;
      private final int ringCount;
      private final int index;
      private int age;
      private boolean grazed;
      private Vec3 pos;
      private Vec3 prevPos;
      private Vec3 vel;

      private ServerMissile(int fireTick, int ringCount, int index, Vec3 pos, Vec3 vel) {
         this.fireTick = fireTick;
         this.ringCount = ringCount;
         this.index = index;
         this.pos = pos;
         this.prevPos = pos;
         this.vel = vel;
      }
   }

   private static final class ShotState {
      private final List<AwakenedImperialHaloShot.ServerMissile> missiles = new ArrayList<>();
   }
}
