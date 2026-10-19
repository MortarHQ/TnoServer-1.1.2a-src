package com.github.wal_bos.moreskills.world.entity.feldway;

import com.github.wal_bos.moreskills.configs.EntityConfigs;
import com.github.wal_bos.moreskills.net.msg.AxisJudgmentPayload;
import com.github.wal_bos.moreskills.world.entity.FeldwayAwakenedEntity;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.tensura.damage.TensuraDamageSource;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.util.AttributeHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class AxisJudgmentAttack {
   public static final int DURATION = (Integer)cfg().axisDuration.get();
   private static final ResourceLocation AXIS_DEGRADATION = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "feldway_axis_judgment_degradation");
   private static final double SEND_RANGE = (Double)cfg().axisSendRange.get();
   private static final double LOCK_RADIUS = (Double)cfg().axisLockRadius.get();
   private static final double WALL_THICKNESS = (Double)cfg().axisWallThickness.get();
   private static final double FLOOR_OFFSET = (Double)cfg().axisFloorOffset.get();
   private static final double CEILING_HEIGHT = (Double)cfg().axisCeilingHeight.get();
   private static final double SMALL_RADIUS = (Double)cfg().axisSmallRadius.get();
   private static final double CROSS_RADIUS = (Double)cfg().axisCrossRadius.get();
   private static final double FINAL_RADIUS = (Double)cfg().axisFinalRadius.get();
   private static final float SMALL_DAMAGE = ((Double)cfg().axisSmallDamage.get()).floatValue();
   private static final float CROSS_DAMAGE = ((Double)cfg().axisCrossDamage.get()).floatValue();
   private static final float FINAL_DAMAGE = ((Double)cfg().axisFinalDamage.get()).floatValue();
   private static final float SPIRITUAL_DAMAGE = ((Double)cfg().axisSpiritualDamage.get()).floatValue();
   private static final Map<UUID, AxisJudgmentAttack.AxisState> STATES = new HashMap<>();

   private static EntityConfigs.AwakenedFeldwayConfig cfg() {
      return EntityConfigs.ENTITY.awakenedFeldway;
   }

   private AxisJudgmentAttack() {
   }

   public static void tick(FeldwayAwakenedEntity boss, int t) {
      if (boss.level() instanceof ServerLevel level) {
         LivingEntity target = getTarget(boss);
         if (target == null) {
            STATES.remove(boss.getUUID());
            boss.clearAttack();
            boss.setGlobalCooldown((Integer)cfg().axisCooldownNoTarget.get());
         } else {
            AxisJudgmentAttack.AxisState state = STATES.get(boss.getUUID());
            if (t != 1 && state != null) {
               boss.getLookControl().setLookAt(target, 90.0F, 90.0F);
               orbit(boss, state.center, t);
               lockArena(level, boss, state.center, t);
               if (t == 36 || t == 50 || t == 64 || t == 78 || t == 92) {
                  strikeSmall(level, boss, state, getSmallIndex(t));
               } else if (t == 104 || t == 116) {
                  strikeCross(level, boss, state, t == 104 ? 0 : 1);
               } else if (t == 130) {
                  strikeFinal(level, boss, state);
               } else if (t == 142) {
                  finalShock(level, boss, state);
               } else {
                  if (t >= DURATION) {
                     STATES.remove(boss.getUUID());
                     boss.clearAttack();
                     boss.setGlobalCooldown((Integer)cfg().axisCooldownEnd.get());
                  }
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
      Vec3 center = center(target);
      int seed = boss.getRandom().nextInt();
      AxisJudgmentAttack.AxisState state = new AxisJudgmentAttack.AxisState(center, seed);
      STATES.put(boss.getUUID(), state);
      AxisJudgmentPayload payload = new AxisJudgmentPayload(boss.getId(), target.getId(), center.x, center.y, center.z, (float)LOCK_RADIUS, DURATION, seed);
      AABB area = new AABB(center, center).inflate(SEND_RANGE);

      for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, area)) {
         NetworkManager.sendToPlayer(player, payload);
      }

      level.playSound(null, center.x, center.y, center.z, SoundEvents.BEACON_ACTIVATE, SoundSource.HOSTILE, 5.8F, 0.48F);
      level.playSound(null, center.x, center.y, center.z, SoundEvents.END_PORTAL_SPAWN, SoundSource.HOSTILE, 5.0F, 0.48F);
      level.playSound(null, center.x, center.y, center.z, SoundEvents.WARDEN_ROAR, SoundSource.HOSTILE, 4.4F, 0.4F);
      level.playSound(null, center.x, center.y, center.z, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.HOSTILE, 5.2F, 0.32F);
   }

   private static int getSmallIndex(int t) {
      if (t == 36) {
         return 0;
      } else if (t == 50) {
         return 1;
      } else if (t == 64) {
         return 2;
      } else {
         return t == 78 ? 3 : 4;
      }
   }

   private static void orbit(FeldwayAwakenedEntity boss, Vec3 center, int t) {
      double angle = t * 0.21;
      double radius = 18.0 + Math.sin(t * 0.07) * 3.0;
      double y = center.y + 8.0 + Math.sin(t * 0.12) * 1.7;
      double x = center.x + Math.cos(angle) * radius;
      double z = center.z + Math.sin(angle) * radius;
      boss.getMoveControl().setWantedPosition(x, y, z, 2.45);
   }

   private static void lockArena(ServerLevel level, FeldwayAwakenedEntity boss, Vec3 center, int t) {
      AABB box = new AABB(center, center).inflate(LOCK_RADIUS + 10.0, CEILING_HEIGHT + 18.0, LOCK_RADIUS + 10.0);
      List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy);
      double floorY = center.y + FLOOR_OFFSET;
      double ceilingY = center.y + CEILING_HEIGHT;

      for (LivingEntity living : list) {
         Vec3 pos = living.position();
         Vec3 flat = new Vec3(pos.x - center.x, 0.0, pos.z - center.z);
         double dist = flat.length();
         double newX = pos.x;
         double newY = pos.y;
         double newZ = pos.z;
         boolean teleport = false;
         if (dist > LOCK_RADIUS) {
            Vec3 dir = dist <= 0.001 ? new Vec3(1.0, 0.0, 0.0) : flat.normalize();
            newX = center.x + dir.x * (LOCK_RADIUS - 1.0);
            newZ = center.z + dir.z * (LOCK_RADIUS - 1.0);
            living.setDeltaMovement(living.getDeltaMovement().multiply(0.035, 0.72, 0.035).add(dir.scale(-0.62)).add(0.0, 0.08, 0.0));
            teleport = true;
         } else if (dist > LOCK_RADIUS - WALL_THICKNESS) {
            Vec3 dir = dist <= 0.001 ? new Vec3(1.0, 0.0, 0.0) : flat.normalize();
            living.setDeltaMovement(living.getDeltaMovement().add(dir.scale(-0.18)).add(0.0, 0.02, 0.0));
            living.hurtMarked = true;
         }

         if (pos.y < floorY) {
            newY = floorY;
            living.setDeltaMovement(living.getDeltaMovement().multiply(0.8, 0.0, 0.8).add(0.0, 0.26, 0.0));
            teleport = true;
         } else if (pos.y + living.getBbHeight() > ceilingY) {
            newY = ceilingY - living.getBbHeight();
            living.setDeltaMovement(living.getDeltaMovement().multiply(0.8, 0.0, 0.8).add(0.0, -0.22, 0.0));
            teleport = true;
         }

         if (teleport) {
            living.teleportTo(newX, newY, newZ);
            living.hurtMarked = true;
         }
      }

      if (t % 12 == 0) {
         level.playSound(
            null, center.x, center.y, center.z, SoundEvents.BEACON_AMBIENT, SoundSource.HOSTILE, 2.0F, 0.95F + boss.getRandom().nextFloat() * 0.18F
         );
      }
   }

   private static void strikeSmall(ServerLevel level, FeldwayAwakenedEntity boss, AxisJudgmentAttack.AxisState state, int index) {
      applyDegradation(boss);
      Vec3 pos = getSmallBeamPos(state, index);
      DamageSource source = source(level, boss, TensuraDamageTypes.HOLY_DAMAGE);
      damageCylinder(level, boss, source, pos, SMALL_RADIUS, 34.0, SMALL_DAMAGE, SPIRITUAL_DAMAGE * 0.28F, 0.8);
      level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.HOSTILE, 3.2F, 1.45F);
      level.playSound(null, pos.x, pos.y, pos.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE, 1.8F, 1.55F);
      level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.HOSTILE, 2.0F, 0.7F);
   }

   private static void strikeCross(ServerLevel level, FeldwayAwakenedEntity boss, AxisJudgmentAttack.AxisState state, int index) {
      applyDegradation(boss);
      Vec3 center = state.center;
      DamageSource source = source(level, boss, TensuraDamageTypes.SPACE_ELEMENTAL);
      double angle = index * Math.PI * 0.5 + state.seed * 1.0E-4;
      Vec3 axis = new Vec3(Math.cos(angle), 0.0, Math.sin(angle)).normalize();
      Vec3 side = new Vec3(-axis.z, 0.0, axis.x).normalize();
      damageLine(
         level,
         boss,
         source,
         center.subtract(axis.scale(LOCK_RADIUS)).add(0.0, 1.1, 0.0),
         center.add(axis.scale(LOCK_RADIUS)).add(0.0, 1.1, 0.0),
         CROSS_RADIUS,
         CROSS_DAMAGE,
         SPIRITUAL_DAMAGE * 0.42F
      );
      damageLine(
         level,
         boss,
         source,
         center.subtract(side.scale(LOCK_RADIUS)).add(0.0, 1.9, 0.0),
         center.add(side.scale(LOCK_RADIUS)).add(0.0, 1.9, 0.0),
         CROSS_RADIUS,
         CROSS_DAMAGE,
         SPIRITUAL_DAMAGE * 0.42F
      );
      level.playSound(null, center.x, center.y, center.z, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.HOSTILE, 5.4F, 0.55F);
      level.playSound(null, center.x, center.y, center.z, (SoundEvent)SoundEvents.TRIDENT_RIPTIDE_3.value(), SoundSource.HOSTILE, 4.0F, 1.3F);
      level.playSound(null, center.x, center.y, center.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 2.6F, 1.2F);
   }

   private static void strikeFinal(ServerLevel level, FeldwayAwakenedEntity boss, AxisJudgmentAttack.AxisState state) {
      applyDegradation(boss);
      Vec3 center = getFinalBeamPos(state);
      DamageSource source = source(level, boss, TensuraDamageTypes.HOLY_DAMAGE);
      damageCylinder(level, boss, source, center, FINAL_RADIUS, 58.0, FINAL_DAMAGE, SPIRITUAL_DAMAGE, 1.35);
      level.playSound(null, center.x, center.y, center.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 5.6F, 0.72F);
      level.playSound(null, center.x, center.y, center.z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.HOSTILE, 5.8F, 1.05F);
      level.playSound(null, center.x, center.y, center.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE, 4.6F, 0.88F);
      level.playSound(null, center.x, center.y, center.z, SoundEvents.END_PORTAL_SPAWN, SoundSource.HOSTILE, 4.0F, 0.48F);
   }

   private static void finalShock(ServerLevel level, FeldwayAwakenedEntity boss, AxisJudgmentAttack.AxisState state) {
      Vec3 center = state.center;
      DamageSource source = source(level, boss, TensuraDamageTypes.HOLY_DAMAGE);
      AABB box = new AABB(center, center).inflate(LOCK_RADIUS + 2.0, 8.0, LOCK_RADIUS + 2.0);

      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy)) {
         Vec3 livingCenter = living.position().add(0.0, living.getBbHeight() * 0.5, 0.0);
         double dist = horizontalDistance(livingCenter, center);
         if (!(dist > LOCK_RADIUS + living.getBbWidth())) {
            double close = 1.0 - Math.min(1.0, dist / LOCK_RADIUS);
            float damage = 3800.0F + (float)(5400.0 * close);
            living.invulnerableTime = 0;
            living.hurt(source, damage);
            living.invulnerableTime = 0;
            boss.hurtSpiritual(living, SPIRITUAL_DAMAGE * 0.22F * (0.4F + (float)close * 0.6F));
            Vec3 push = living.position().subtract(center);
            if (push.lengthSqr() < 0.001) {
               push = new Vec3(0.0, 1.0, 0.0);
            }

            living.setDeltaMovement(living.getDeltaMovement().add(push.normalize().scale(0.95 + close * 0.4)).add(0.0, 0.65, 0.0));
            living.hurtMarked = true;
         }
      }

      level.playSound(null, center.x, center.y, center.z, SoundEvents.BEACON_DEACTIVATE, SoundSource.HOSTILE, 4.2F, 0.62F);
      level.playSound(null, center.x, center.y, center.z, SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.HOSTILE, 3.6F, 0.9F);
   }

   private static void damageCylinder(
      ServerLevel level, FeldwayAwakenedEntity boss, DamageSource source, Vec3 center, double radius, double height, float damage, float spiritual, double lift
   ) {
      AABB box = new AABB(center, center).inflate(radius + 2.0, height, radius + 2.0);

      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy)) {
         Vec3 livingCenter = living.position().add(0.0, living.getBbHeight() * 0.5, 0.0);
         double dist = horizontalDistance(livingCenter, center);
         if (!(dist > radius + living.getBbWidth() * 0.5)) {
            double close = 1.0 - Math.min(1.0, dist / radius);
            float finalDamage = damage + (float)(damage * 0.34 * close);
            living.invulnerableTime = 0;
            living.hurt(source, finalDamage);
            living.invulnerableTime = 0;
            if (spiritual > 0.0F) {
               boss.hurtSpiritual(living, spiritual * (0.55F + (float)close * 0.45F));
            }

            Vec3 push = living.position().subtract(center);
            if (push.lengthSqr() < 0.001) {
               push = new Vec3(0.0, 1.0, 0.0);
            }

            living.setDeltaMovement(living.getDeltaMovement().add(push.normalize().scale(0.5 + close * 0.35)).add(0.0, lift, 0.0));
            living.hurtMarked = true;
         }
      }
   }

   private static void damageLine(
      ServerLevel level, FeldwayAwakenedEntity boss, DamageSource source, Vec3 start, Vec3 end, double radius, float damage, float spiritual
   ) {
      AABB box = new AABB(start, end).inflate(radius + 2.0, 7.0, radius + 2.0);

      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy)) {
         Vec3 livingCenter = living.position().add(0.0, living.getBbHeight() * 0.5, 0.0);
         double dist = distanceToSegmentFlat(livingCenter, start, end);
         if (!(dist > radius + living.getBbWidth() * 0.5)) {
            double close = 1.0 - Math.min(1.0, dist / radius);
            float finalDamage = damage + (float)(damage * 0.28 * close);
            living.invulnerableTime = 0;
            living.hurt(source, finalDamage);
            living.invulnerableTime = 0;
            if (spiritual > 0.0F) {
               boss.hurtSpiritual(living, spiritual * (0.5F + (float)close * 0.5F));
            }

            Vec3 push = living.position().subtract(start.add(end).scale(0.5));
            if (push.lengthSqr() < 0.001) {
               push = end.subtract(start);
            }

            living.setDeltaMovement(living.getDeltaMovement().add(push.normalize().scale(0.7 + close * 0.25)).add(0.0, 0.35, 0.0));
            living.hurtMarked = true;
         }
      }
   }

   private static Vec3 getSmallBeamPos(AxisJudgmentAttack.AxisState state, int index) {
      double[] radii = new double[]{0.0, 0.48, 0.72, 0.55, 0.82};
      double[] angles = new double[]{0.0, 0.2, 2.35, 4.05, 5.35};
      int safe = Math.max(0, Math.min(index, radii.length - 1));
      double radius = LOCK_RADIUS * radii[safe];
      double angle = angles[safe] + state.seed * 1.0E-4;
      return state.center.add(Math.cos(angle) * radius, 0.0, Math.sin(angle) * radius);
   }

   private static Vec3 getFinalBeamPos(AxisJudgmentAttack.AxisState state) {
      double angle = 1.1 + state.seed * 1.3E-4;
      double radius = LOCK_RADIUS * 0.44;
      return state.center.add(Math.cos(angle) * radius, 0.0, Math.sin(angle) * radius);
   }

   private static DamageSource source(ServerLevel level, FeldwayAwakenedEntity boss, ResourceKey<DamageType> type) {
      return ((TensuraDamageSource)TensuraDamageTypes.getEntityDamageSource(level, type, boss)).tensura$setResistanceBypassLevel(0.75F);
   }

   private static void applyDegradation(LivingEntity entity) {
      AttributeHelper.addPermanentAttributeIfHigher(
         entity, TensuraAttributes.RESISTANCE_DEGRADATION, AXIS_DEGRADATION, (Double)cfg().axisResistanceDegradation.get(), Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttributeIfHigher(
         entity, TensuraAttributes.LIGHT_RESIST_DEGRADATION, AXIS_DEGRADATION, (Double)cfg().axisLightDegradation.get(), Operation.ADD_VALUE
      );
   }

   private static double horizontalDistance(Vec3 a, Vec3 b) {
      double x = a.x - b.x;
      double z = a.z - b.z;
      return Math.sqrt(x * x + z * z);
   }

   private static double distanceToSegmentFlat(Vec3 point, Vec3 start, Vec3 end) {
      Vec3 p = new Vec3(point.x, 0.0, point.z);
      Vec3 a = new Vec3(start.x, 0.0, start.z);
      Vec3 b = new Vec3(end.x, 0.0, end.z);
      Vec3 line = b.subtract(a);
      double len = line.lengthSqr();
      if (len <= 1.0E-6) {
         return p.distanceTo(a);
      }

      double t = p.subtract(a).dot(line) / len;
      t = Math.max(0.0, Math.min(1.0, t));
      Vec3 closest = a.add(line.scale(t));
      return p.distanceTo(closest);
   }

   private static Vec3 center(LivingEntity target) {
      return target.position().add(0.0, target.getBbHeight() * 0.08, 0.0);
   }

   private static LivingEntity getTarget(FeldwayAwakenedEntity boss) {
      LivingEntity target = boss.resolveLockedTarget();
      if (target == null) {
         target = boss.getTarget();
      }

      return target != null && target.isAlive() && boss.isValidEnemy(target) ? target : null;
   }

   private record AxisState(Vec3 center, int seed) {
   }
}
