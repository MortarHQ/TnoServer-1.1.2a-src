package com.github.wal_bos.moreskills.world.entity.feldway;

import com.github.wal_bos.moreskills.configs.EntityConfigs;
import com.github.wal_bos.moreskills.net.msg.ChronoJudgementClockPayload;
import com.github.wal_bos.moreskills.world.entity.FeldwayAwakenedEntity;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.tensura.damage.TensuraDamageSource;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.util.AttributeHelper;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class ChronoJudgementClockAttack {
   public static final int DURATION = (Integer)cfg().chronoDuration.get();
   private static final ResourceLocation CHRONO_DEGRADATION = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "feldway_chrono_judgement_degradation");
   private static final int OUT_START = (Integer)cfg().chronoOutStart.get();
   private static final int OUT_DURATION = (Integer)cfg().chronoOutDuration.get();
   private static final int MIDNIGHT = OUT_START + OUT_DURATION;
   private static final int REWIND_DURATION = (Integer)cfg().chronoRewindDuration.get();
   private static final int BURSTS_PER_TICK = (Integer)cfg().chronoBurstsPerTick.get();
   private static final double SEND_RANGE = (Double)cfg().chronoSendRange.get();
   private static final double ORB_HIT_RADIUS = (Double)cfg().chronoOrbHitRadius.get();
   private static final double OUT_RANGE = (Double)cfg().chronoOutRange.get();
   private static final double EXPLOSION_RADIUS = (Double)cfg().chronoExplosionRadius.get();
   private static final float ORB_DAMAGE = ((Double)cfg().chronoOrbDamage.get()).floatValue();
   private static final float REVERSE_ORB_DAMAGE = ((Double)cfg().chronoReverseOrbDamage.get()).floatValue();
   private static final float MIDNIGHT_DAMAGE = ((Double)cfg().chronoMidnightDamage.get()).floatValue();
   private static final float SPIRITUAL_DAMAGE = ((Double)cfg().chronoSpiritualDamage.get()).floatValue();
   private static final Map<UUID, ChronoJudgementClockAttack.ChronoState> STATES = new HashMap<>();

   private static EntityConfigs.AwakenedFeldwayConfig cfg() {
      return EntityConfigs.ENTITY.awakenedFeldway;
   }

   private ChronoJudgementClockAttack() {
   }

   public static void tick(FeldwayAwakenedEntity boss, int t) {
      if (boss.level() instanceof ServerLevel level) {
         LivingEntity target = getTarget(boss);
         if (target == null) {
            STATES.remove(boss.getUUID());
            boss.clearAttack();
            boss.setGlobalCooldown((Integer)cfg().chronoCooldownNoTarget.get());
         } else {
            ChronoJudgementClockAttack.ChronoState state = STATES.get(boss.getUUID());
            if (t != 1 && state != null) {
               state.clockCenter = boss.position().add(0.0, boss.getBbHeight() + 21.0, 0.0);
               state.targetCenter = target.position().add(0.0, target.getBbHeight() * 0.55, 0.0);
               boss.getLookControl().setLookAt(target, 90.0F, 90.0F);
               holdUnderClock(boss, state, target, t);
               if (t >= OUT_START && t < MIDNIGHT && t % 5 == 0) {
                  fireTrueSphereOrbBurst(level, boss, state, t, false);
               }

               if (t == MIDNIGHT) {
                  midnightExplosion(level, boss, state);
               }

               if (t > MIDNIGHT && t <= MIDNIGHT + REWIND_DURATION && t % 5 == 0) {
                  fireTrueSphereOrbBurst(level, boss, state, t, true);
               }

               if (t >= DURATION) {
                  STATES.remove(boss.getUUID());
                  boss.clearAttack();
                  boss.setGlobalCooldown((Integer)cfg().chronoCooldownEnd.get());
               }
            } else {
               begin(level, boss, target);
            }
         }
      } else {
         STATES.remove(boss.getUUID());
         boss.clearAttack();
      }
   }

   private static void begin(ServerLevel level, FeldwayAwakenedEntity boss, LivingEntity target) {
      applyDegradation(boss);
      Vec3 targetCenter = target.position().add(0.0, target.getBbHeight() * 0.55, 0.0);
      Vec3 center = boss.position().add(0.0, boss.getBbHeight() + 21.0, 0.0);
      int seed = boss.getRandom().nextInt();
      ChronoJudgementClockAttack.ChronoState state = new ChronoJudgementClockAttack.ChronoState(center, targetCenter, seed);
      STATES.put(boss.getUUID(), state);
      ChronoJudgementClockPayload payload = new ChronoJudgementClockPayload(boss.getId(), target.getId(), center.x, center.y, center.z, DURATION, seed);
      AABB area = new AABB(center, center).inflate(SEND_RANGE);

      for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, area)) {
         NetworkManager.sendToPlayer(player, payload);
      }

      level.playSound(null, center.x, center.y, center.z, SoundEvents.BEACON_ACTIVATE, SoundSource.HOSTILE, 8.0F, 0.26F);
      level.playSound(null, center.x, center.y, center.z, SoundEvents.END_PORTAL_SPAWN, SoundSource.HOSTILE, 7.4F, 0.3F);
      level.playSound(null, center.x, center.y, center.z, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.HOSTILE, 6.8F, 0.18F);
      level.playSound(null, center.x, center.y, center.z, SoundEvents.WARDEN_HEARTBEAT, SoundSource.HOSTILE, 6.0F, 0.38F);
   }

   private static void holdUnderClock(FeldwayAwakenedEntity boss, ChronoJudgementClockAttack.ChronoState state, LivingEntity target, int t) {
      double angle = t * 0.045;
      double radius = 15.0 + Math.sin(t * 0.075) * 2.8;
      Vec3 hover = target.position().add(Math.cos(angle) * radius, 16.0 + Math.sin(t * 0.1) * 1.6, Math.sin(angle) * radius);
      boss.getMoveControl().setWantedPosition(hover.x, hover.y, hover.z, 2.15);
   }

   private static void fireTrueSphereOrbBurst(
      ServerLevel level, FeldwayAwakenedEntity boss, ChronoJudgementClockAttack.ChronoState state, int t, boolean reverse
   ) {
      applyDegradation(boss);
      int step = reverse ? t - MIDNIGHT : t - OUT_START;
      DamageSource source = ((TensuraDamageSource)TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.HOLY_DAMAGE, boss))
         .tensura$setResistanceBypassLevel(1.0F);

      for (int i = 0; i < BURSTS_PER_TICK; i++) {
         int index = step * BURSTS_PER_TICK + i;
         Vec3 dir = directionFromIndex(index, state.seed);
         double socketRadius = 42.0 + hash(state.seed * 0.018F + index * 2.17F) * 38.0;
         double range = OUT_RANGE + hash(state.seed * 0.022F + index * 4.71F) * 120.0;
         Vec3 socket = state.clockCenter.add(dir.scale(socketRadius));
         Vec3 far = socket.add(dir.scale(range));
         Vec3 start = reverse ? far : socket;
         Vec3 end = reverse ? socket : far;
         damageLine(level, boss, source, start, end, ORB_HIT_RADIUS, reverse ? REVERSE_ORB_DAMAGE : ORB_DAMAGE, reverse);
      }

      if (t % 10 == 0) {
         Vec3 sound = reverse ? state.clockCenter : state.targetCenter;
         level.playSound(
            null, sound.x, sound.y, sound.z, SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.HOSTILE, reverse ? 5.3F : 3.9F, reverse ? 0.4F : 1.48F
         );
         level.playSound(
            null,
            state.clockCenter.x,
            state.clockCenter.y,
            state.clockCenter.z,
            SoundEvents.BEACON_POWER_SELECT,
            SoundSource.HOSTILE,
            reverse ? 5.1F : 3.4F,
            reverse ? 0.5F : 1.84F
         );
      }
   }

   private static void midnightExplosion(ServerLevel level, FeldwayAwakenedEntity boss, ChronoJudgementClockAttack.ChronoState state) {
      applyDegradation(boss);
      DamageSource source = ((TensuraDamageSource)TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.HOLY_DAMAGE, boss))
         .tensura$setResistanceBypassLevel(1.0F);
      Vec3 center = state.targetCenter;
      AABB box = new AABB(center, center).inflate(EXPLOSION_RADIUS);

      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy)) {
         Vec3 livingCenter = living.position().add(0.0, living.getBbHeight() * 0.5, 0.0);
         double distance = livingCenter.distanceTo(center);
         if (!(distance > EXPLOSION_RADIUS + living.getBbWidth())) {
            double close = 1.0 - Math.min(1.0, distance / EXPLOSION_RADIUS);
            float damage = MIDNIGHT_DAMAGE + (float)(MIDNIGHT_DAMAGE * close * 1.05);
            living.invulnerableTime = 0;
            living.hurt(source, damage);
            living.invulnerableTime = 0;
            boss.hurtSpiritual(living, SPIRITUAL_DAMAGE * (0.82F + (float)close * 1.05F));
            Vec3 push = livingCenter.subtract(center);
            if (push.lengthSqr() < 0.001) {
               push = new Vec3(0.0, 1.0, 0.0);
            }

            living.setDeltaMovement(living.getDeltaMovement().add(push.normalize().scale(0.65)).add(0.0, 0.45, 0.0));
            living.hurtMarked = true;
         }
      }

      for (int i = 0; i < 128; i++) {
         Vec3 dir = directionFromIndex(i * 7, state.seed + 99991);
         Vec3 start = center.add(dir.scale(2.0));
         Vec3 end = center.add(dir.scale(EXPLOSION_RADIUS + 74.0));
         damageLine(level, boss, source, start, end, ORB_HIT_RADIUS + 3.5, MIDNIGHT_DAMAGE * 0.52F, true);
      }

      level.playSound(null, center.x, center.y, center.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 11.5F, 0.2F);
      level.playSound(null, center.x, center.y, center.z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.HOSTILE, 10.5F, 0.46F);
      level.playSound(null, center.x, center.y, center.z, SoundEvents.END_PORTAL_SPAWN, SoundSource.HOSTILE, 9.2F, 0.16F);
      level.playSound(null, center.x, center.y, center.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE, 9.0F, 0.56F);
      level.playSound(null, state.clockCenter.x, state.clockCenter.y, state.clockCenter.z, SoundEvents.BEACON_DEACTIVATE, SoundSource.HOSTILE, 8.3F, 0.28F);
   }

   private static void damageLine(
      ServerLevel level, FeldwayAwakenedEntity boss, DamageSource source, Vec3 start, Vec3 end, double radius, float baseDamage, boolean reverse
   ) {
      AABB box = new AABB(start, end).inflate(radius + 3.0);

      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy)) {
         Vec3 center = living.position().add(0.0, living.getBbHeight() * 0.5, 0.0);
         double distance = distanceToSegment(center, start, end);
         if (!(distance > radius + living.getBbWidth() * 0.5)) {
            double close = 1.0 - Math.min(1.0, distance / radius);
            float damage = baseDamage + (float)(baseDamage * close * 0.76);
            living.invulnerableTime = 0;
            living.hurt(source, damage);
            living.invulnerableTime = 0;
            if (reverse) {
               boss.hurtSpiritual(living, SPIRITUAL_DAMAGE * 0.32F * (0.7F + (float)close));
            }

            Vec3 motion = reverse ? start.subtract(end) : end.subtract(start);
            if (motion.lengthSqr() > 0.001) {
               double push = reverse ? -0.46 : 0.34;
               living.setDeltaMovement(living.getDeltaMovement().add(motion.normalize().scale(push)).add(0.0, reverse ? 0.24 : 0.12, 0.0));
               living.hurtMarked = true;
            }
         }
      }
   }

   private static Vec3 directionFromIndex(int index, int seed) {
      float a = hash(seed * 0.013F + index * 5.31F);
      float b = hash(seed * 0.019F + index * 11.77F);
      double yaw = index * 2.399963229728653 + a * 0.75;
      double y = -1.0 + 2.0 * b;
      double radius = Math.sqrt(Math.max(0.0, 1.0 - y * y));
      Vec3 dir = new Vec3(Math.cos(yaw) * radius, y, Math.sin(yaw) * radius);
      return dir.lengthSqr() < 0.001 ? new Vec3(1.0, 0.0, 0.0) : dir.normalize();
   }

   private static double distanceToSegment(Vec3 point, Vec3 start, Vec3 end) {
      Vec3 line = end.subtract(start);
      double len = line.lengthSqr();
      if (len <= 1.0E-6) {
         return point.distanceTo(start);
      }

      double t = point.subtract(start).dot(line) / len;
      t = Math.max(0.0, Math.min(1.0, t));
      Vec3 closest = start.add(line.scale(t));
      return point.distanceTo(closest);
   }

   private static void applyDegradation(LivingEntity entity) {
      AttributeHelper.addPermanentAttributeIfHigher(
         entity, TensuraAttributes.RESISTANCE_DEGRADATION, CHRONO_DEGRADATION, (Double)cfg().chronoResistanceDegradation.get(), Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttributeIfHigher(
         entity, TensuraAttributes.LIGHT_RESIST_DEGRADATION, CHRONO_DEGRADATION, (Double)cfg().chronoLightDegradation.get(), Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttributeIfHigher(
         entity, TensuraAttributes.SPACE_RESIST_DEGRADATION, CHRONO_DEGRADATION, (Double)cfg().chronoSpaceDegradation.get(), Operation.ADD_VALUE
      );
   }

   private static LivingEntity getTarget(FeldwayAwakenedEntity boss) {
      LivingEntity target = boss.resolveLockedTarget();
      if (target == null) {
         target = boss.getTarget();
      }

      return target != null && target.isAlive() && boss.isValidEnemy(target) ? target : null;
   }

   private static float hash(float value) {
      return Mth.frac(Mth.sin(value * 12.9898F) * 43758.547F);
   }

   private static final class ChronoState {
      private final int seed;
      private Vec3 clockCenter;
      private Vec3 targetCenter;

      private ChronoState(Vec3 clockCenter, Vec3 targetCenter, int seed) {
         this.clockCenter = clockCenter;
         this.targetCenter = targetCenter;
         this.seed = seed;
      }
   }
}
