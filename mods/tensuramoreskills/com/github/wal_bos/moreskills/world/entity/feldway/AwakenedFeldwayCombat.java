package com.github.wal_bos.moreskills.world.entity.feldway;

import com.github.wal_bos.moreskills.configs.EntityConfigs;
import com.github.wal_bos.moreskills.handler.AsmodayBlackHoleHandler;
import com.github.wal_bos.moreskills.net.msg.FullCowlLightningPayload;
import com.github.wal_bos.moreskills.net.msg.GearshiftTrailPayload;
import com.github.wal_bos.moreskills.world.entity.FeldwayAwakenedEntity;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.tensura.damage.TensuraDamageSource;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.util.AttributeHelper;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class AwakenedFeldwayCombat {
   public static final int ATTACK_ASMODAY_BLACK_HOLE = 1;
   public static final int ATTACK_LIGHTNING_SHOT = 2;
   public static final int ATTACK_MIRAGE_EXECUTION = 3;
   public static final int ATTACK_AXIS_JUDGMENT = 4;
   public static final int ATTACK_AVATAR_OBLIVION = 5;
   public static final int ATTACK_SERAPHIC_GUILLOTINE = 6;
   public static final int ATTACK_CHRONO_JUDGEMENT_CLOCK = 7;
   public static final int ATTACK_IMPERIAL_HALO_SHOT = 80;
   private static final ResourceLocation FELDWAY_LIGHTNING_DEGRADATION = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "feldway_lightning_degradation"
   );
   private static final ResourceLocation FELDWAY_MIRAGE_DEGRADATION = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "feldway_mirage_degradation");
   private static final double SEND_RANGE = (Double)cfg().combatSendRange.get();
   private static final double LIGHTNING_RANGE = (Double)cfg().lightningRange.get();
   private static final double LIGHTNING_RADIUS = (Double)cfg().lightningRadius.get();
   private static final float LIGHTNING_DAMAGE = ((Double)cfg().lightningDamage.get()).floatValue();
   private static final float LIGHTNING_CORE_DAMAGE = ((Double)cfg().lightningCoreDamage.get()).floatValue();
   private static final int MIRAGE_CLONE_COUNT = (Integer)cfg().mirageCloneCount.get();
   private static final int MIRAGE_SPAWN_INTERVAL = (Integer)cfg().mirageSpawnInterval.get();
   private static final int MIRAGE_ATTACK_TIME = (Integer)cfg().mirageAttackTime.get();
   private static final int MIRAGE_FINISH_TIME = (Integer)cfg().mirageFinishTime.get();
   private static final double MIRAGE_RADIUS = (Double)cfg().mirageRadius.get();
   private static final double MIRAGE_LINE_RADIUS = (Double)cfg().mirageLineRadius.get();
   private static final float MIRAGE_SLASH_DAMAGE = ((Double)cfg().mirageSlashDamage.get()).floatValue();
   private static final float MIRAGE_EXECUTION_DAMAGE = ((Double)cfg().mirageExecutionDamage.get()).floatValue();
   private static final float MIRAGE_SPIRITUAL_DAMAGE = ((Double)cfg().mirageSpiritualDamage.get()).floatValue();

   private static EntityConfigs.AwakenedFeldwayConfig cfg() {
      return EntityConfigs.ENTITY.awakenedFeldway;
   }

   private AwakenedFeldwayCombat() {
   }

   public static void tickAttack(FeldwayAwakenedEntity boss) {
      int id = boss.getAttackId();
      if (id != 0) {
         int t = boss.getAttackTicks();
         if (AwakenedImperialHaloShot.is(id)) {
            AwakenedImperialHaloShot.tick(boss);
         } else if (id == 1) {
            tickAsmodayBlackHole(boss, t);
         } else if (id == 2) {
            tickLightningShot(boss, t);
         } else if (id == 3) {
            tickMirageExecution(boss, t);
         } else if (id == 4) {
            AxisJudgmentAttack.tick(boss, t);
         } else if (id == 5) {
            AvatarOblivionAttack.tick(boss, t);
         } else if (id == 6) {
            SeraphicGuillotineAttack.tick(boss, t);
         } else if (id == 7) {
            ChronoJudgementClockAttack.tick(boss, t);
         } else {
            boss.clearAttack();
            boss.setGlobalCooldown(8);
         }
      }
   }

   public static void tickSideAttacks(FeldwayAwakenedEntity boss, LivingEntity target) {
      if (boss.level() instanceof ServerLevel level) {
         if (target != null && target.isAlive() && boss.isValidEnemy(target)) {
            if (boss.getSideAttackCooldown() <= 0) {
               int id = boss.getAttackId();
               int t = boss.getAttackTicks();
               if (!AwakenedImperialHaloShot.is(id)) {
                  if (id == 4 && t > 34 && t < AxisJudgmentAttack.DURATION - 18) {
                     if (t % 26 == 0) {
                        sendFullCowl(level, boss, 12, 70);
                        fireLightning(level, boss, target, 0.22, 0.035);
                        boss.setSideAttackCooldown(22);
                     }
                  } else if (id == 7 && t > 28 && t < ChronoJudgementClockAttack.DURATION - 24) {
                     if (t % 34 == 0) {
                        sendFullCowl(level, boss, 10, 80);
                        fireLightning(level, boss, target, 0.1, 0.025);
                        boss.setSideAttackCooldown(30);
                     }
                  } else if (id == 3 && t > 18 && t < MIRAGE_ATTACK_TIME - 10) {
                     if (t % 28 == 0) {
                        fireLightning(level, boss, target, 0.2, 0.03);
                        boss.setSideAttackCooldown(24);
                     }
                  } else {
                     if (id == 6 && t > 22 && t < SeraphicGuillotineAttack.DURATION - 20 && t % 32 == 0) {
                        sendFullCowl(level, boss, 10, 80);
                        fireLightning(level, boss, target, 0.12, 0.025);
                        boss.setSideAttackCooldown(28);
                     }
                  }
               }
            }
         }
      }
   }

   private static void tickAsmodayBlackHole(FeldwayAwakenedEntity boss, int t) {
      if (boss.level() instanceof ServerLevel sl) {
         LivingEntity target = getTarget(boss);
         if (target == null) {
            boss.clearAttack();
            boss.setGlobalCooldown((Integer)cfg().asmodayCooldownNoTarget.get());
         } else {
            boss.getLookControl().setLookAt(target, 90.0F, 90.0F);
            if (t == 1) {
               sl.playSound(null, boss.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.HOSTILE, 7.0F, 0.36F);
               sl.playSound(null, boss.blockPosition(), SoundEvents.WARDEN_ROAR, SoundSource.HOSTILE, 6.0F, 0.32F);
               sl.playSound(null, boss.blockPosition(), SoundEvents.BEACON_POWER_SELECT, SoundSource.HOSTILE, 5.0F, 0.4F);
            } else if (t == (Integer)cfg().asmodaySpawnTick.get()) {
               if (!boss.hasUsedAwakenedBlackHole() && !AsmodayBlackHoleHandler.hasActiveBlackHole(boss)) {
                  Vec3 spawnPos = getBlackHoleSpawnPos(boss, target);
                  AsmodayBlackHoleHandler.spawn(
                     sl, boss, spawnPos, ((Double)cfg().asmodayBlackHoleRadius.get()).floatValue(), (Integer)cfg().asmodayBlackHoleLifeTicks.get(), true
                  );
                  boss.markAwakenedBlackHoleUsed();
                  sl.playSound(null, spawnPos.x, spawnPos.y, spawnPos.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 10.0F, 0.22F);
                  sl.playSound(null, spawnPos.x, spawnPos.y, spawnPos.z, SoundEvents.END_PORTAL_SPAWN, SoundSource.HOSTILE, 8.0F, 0.34F);
               } else {
                  boss.clearAttack();
                  boss.setGlobalCooldown((Integer)cfg().asmodayCooldownIfActive.get());
               }
            } else {
               if (t >= (Integer)cfg().asmodayEndTick.get()) {
                  boss.clearAttack();
                  boss.setGlobalCooldown((Integer)cfg().asmodayCooldownAfterCast.get());
               }
            }
         }
      } else {
         boss.clearAttack();
      }
   }

   private static void tickLightningShot(FeldwayAwakenedEntity boss, int t) {
      if (boss.level() instanceof ServerLevel sl) {
         LivingEntity target = getTarget(boss);
         if (target == null) {
            boss.clearAttack();
            boss.setGlobalCooldown(6);
         } else {
            boss.getLookControl().setLookAt(target, 90.0F, 90.0F);
            if (t == 1) {
               sendFullCowl(sl, boss, 18, 100);
               fireLightning(sl, boss, target, 0.2, 0.035);
            } else if (t == 3) {
               fireLightning(sl, boss, target, 0.2, 0.035);
            } else {
               if (t >= 4) {
                  boss.clearAttack();
                  boss.setGlobalCooldown(5);
               }
            }
         }
      } else {
         boss.clearAttack();
      }
   }

   private static void tickMirageExecution(FeldwayAwakenedEntity boss, int t) {
      if (boss.level() instanceof ServerLevel sl) {
         LivingEntity target = getTarget(boss);
         if (target == null) {
            boss.clearAttack();
            boss.setGlobalCooldown(20);
         } else {
            boss.getLookControl().setLookAt(target, 90.0F, 90.0F);
            orbitDuringMirage(boss, target, t);
            if (t == 1) {
               sendGearshift(sl, boss, target, MIRAGE_FINISH_TIME + 26, 5);
               sl.playSound(null, boss.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.HOSTILE, 5.0F, 0.42F);
               sl.playSound(null, boss.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.HOSTILE, 4.0F, 1.55F);
               sl.playSound(null, boss.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 2.8F, 1.85F);
            } else if (t >= 8 && t <= 48 && t % MIRAGE_SPAWN_INTERVAL == 0) {
               playMirageSpawnCue(sl, boss, target, t / MIRAGE_SPAWN_INTERVAL - 1);
            } else if (t == MIRAGE_ATTACK_TIME) {
               simultaneousMirageLunge(sl, boss, target);
            } else if (t == MIRAGE_ATTACK_TIME + 6) {
               executeMirageBurst(sl, boss, target);
            } else {
               if (t >= MIRAGE_FINISH_TIME) {
                  boss.clearAttack();
                  boss.setGlobalCooldown(36);
               }
            }
         }
      } else {
         boss.clearAttack();
      }
   }

   private static void orbitDuringMirage(FeldwayAwakenedEntity boss, LivingEntity target, int t) {
      double angle = t * 0.165;
      double radius = 13.0 + Math.sin(t * 0.08) * 2.6;
      double y = target.getY() + 5.5 + Math.sin(t * 0.11) * 1.4;
      double x = target.getX() + Math.cos(angle) * radius;
      double z = target.getZ() + Math.sin(angle) * radius;
      boss.getMoveControl().setWantedPosition(x, y, z, 2.35);
   }

   private static void playMirageSpawnCue(ServerLevel level, FeldwayAwakenedEntity boss, LivingEntity target, int index) {
      Vec3 center = target.position().add(0.0, target.getBbHeight() * 0.52, 0.0);
      Vec3 spawn = getMiragePosition(center, boss, index, 0.0);
      level.playSound(null, spawn.x, spawn.y, spawn.z, SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 2.5F, 1.56F + index * 0.035F);
      level.playSound(null, spawn.x, spawn.y, spawn.z, SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.HOSTILE, 2.4F, 0.78F + index * 0.035F);
      level.playSound(null, center.x, center.y, center.z, SoundEvents.BEACON_AMBIENT, SoundSource.HOSTILE, 1.4F, 1.55F);
   }

   private static void simultaneousMirageLunge(ServerLevel level, FeldwayAwakenedEntity boss, LivingEntity target) {
      applyMirageDegradation(boss);
      Vec3 center = target.position().add(0.0, target.getBbHeight() * 0.52, 0.0);
      DamageSource source = ((TensuraDamageSource)TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.SPACE_ELEMENTAL, boss))
         .tensura$setResistanceBypassLevel(1.0F);

      for (int i = 0; i < MIRAGE_CLONE_COUNT; i++) {
         Vec3 start = getMiragePosition(center, boss, i, 0.0);
         Vec3 opposite = getMiragePosition(center, boss, i, Math.PI);
         Vec3 inward = center.subtract(start);
         if (inward.lengthSqr() < 0.001) {
            inward = new Vec3(1.0, 0.0, 0.0);
         }

         inward = inward.normalize();
         Vec3 tangent = new Vec3(-inward.z, 0.0, inward.x);
         if (tangent.lengthSqr() < 0.001) {
            tangent = new Vec3(1.0, 0.0, 0.0);
         }

         tangent = tangent.normalize();
         double curve = (i % 2 == 0 ? 1.0 : -1.0) * (2.2 + boss.getRandom().nextDouble() * 2.8);
         Vec3 mid = center.add(tangent.scale(curve)).add(0.0, boss.getRandom().nextDouble() * 2.0 - 1.0, 0.0);
         Vec3 end = opposite.add(inward.scale(4.5)).add(0.0, -0.7 + boss.getRandom().nextDouble() * 1.1, 0.0);
         damageLine(level, boss, source, start, mid, MIRAGE_LINE_RADIUS, MIRAGE_SLASH_DAMAGE, true);
         damageLine(level, boss, source, mid, end, MIRAGE_LINE_RADIUS, MIRAGE_SLASH_DAMAGE * 0.94F, true);
      }

      Vec3 bossJump = getMiragePosition(center, boss, boss.getRandom().nextInt(MIRAGE_CLONE_COUNT), 0.0);
      Vec3 toCenter = center.subtract(bossJump);
      if (toCenter.lengthSqr() > 0.001) {
         bossJump = bossJump.add(toCenter.normalize().scale(3.0));
      }

      boss.teleportTo(bossJump.x, bossJump.y, bossJump.z);
      boss.getLookControl().setLookAt(target, 90.0F, 90.0F);
      level.playSound(null, center.x, center.y, center.z, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.HOSTILE, 5.2F, 0.62F);
      level.playSound(null, center.x, center.y, center.z, (SoundEvent)SoundEvents.TRIDENT_RIPTIDE_3.value(), SoundSource.HOSTILE, 4.2F, 1.42F);
      level.playSound(null, center.x, center.y, center.z, SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 4.6F, 1.95F);
      level.playSound(null, center.x, center.y, center.z, SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.HOSTILE, 3.8F, 1.5F);
   }

   private static Vec3 getMiragePosition(Vec3 center, FeldwayAwakenedEntity boss, int index, double offset) {
      double base = index * Math.PI * 2.0 / MIRAGE_CLONE_COUNT;
      double wobble = Math.sin((boss.tickCount + index * 31.0) * 0.075) * 0.08;
      double angle = base + offset + wobble;
      double radius = MIRAGE_RADIUS + index % 3 * 2.0;
      return center.add(Math.cos(angle) * radius, 2.0 + Math.sin(index * 1.91) * 2.2, Math.sin(angle) * radius);
   }

   private static void executeMirageBurst(ServerLevel level, FeldwayAwakenedEntity boss, LivingEntity target) {
      applyMirageDegradation(boss);
      Vec3 center = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
      DamageSource source = ((TensuraDamageSource)TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.LIGHTNING_ELEMENTAL, boss))
         .tensura$setResistanceBypassLevel(1.0F);

      for (int i = 0; i < 14; i++) {
         double angle = i * Math.PI * 2.0 / 14.0 + boss.getRandom().nextDouble() * 0.16;
         Vec3 side = new Vec3(Math.cos(angle), 0.0, Math.sin(angle)).normalize();
         Vec3 start = center.add(side.scale(MIRAGE_RADIUS + 9.0)).add(0.0, 2.0 + boss.getRandom().nextDouble() * 3.2, 0.0);
         Vec3 end = center.subtract(side.scale(4.6)).add(0.0, boss.getRandom().nextDouble() * 1.6 - 0.8, 0.0);
         damageLine(level, boss, source, start, end, MIRAGE_LINE_RADIUS + 1.1, MIRAGE_EXECUTION_DAMAGE, true);
      }

      AABB box = new AABB(center, center).inflate(11.0);

      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy)) {
         double distance = living.position().add(0.0, living.getBbHeight() * 0.5, 0.0).distanceTo(center);
         if (!(distance > 11.0 + living.getBbWidth())) {
            living.invulnerableTime = 0;
            living.hurt(source, MIRAGE_EXECUTION_DAMAGE);
            living.invulnerableTime = 0;
            boss.hurtSpiritual(living, MIRAGE_SPIRITUAL_DAMAGE);
            Vec3 knock = living.position().subtract(center);
            if (knock.lengthSqr() < 0.001) {
               knock = new Vec3(0.0, 1.0, 0.0);
            }

            living.setDeltaMovement(living.getDeltaMovement().add(knock.normalize().scale(0.32)).add(0.0, 0.2, 0.0));
            living.hurtMarked = true;
         }
      }

      level.playSound(null, center.x, center.y, center.z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.HOSTILE, 5.0F, 1.34F);
      level.playSound(null, center.x, center.y, center.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 4.4F, 0.82F);
      level.playSound(null, center.x, center.y, center.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE, 3.8F, 1.24F);
   }

   private static void fireLightning(ServerLevel level, FeldwayAwakenedEntity boss, LivingEntity target, double horizontalPush, double verticalPush) {
      applyLightningDegradation(boss);
      Vec3 start = boss.position().add(0.0, boss.getBbHeight() * 0.72, 0.0);
      Vec3 targetCenter = target.position().add(0.0, target.getBbHeight() * 0.55, 0.0);
      Vec3 direction = targetCenter.subtract(start);
      if (direction.lengthSqr() < 0.001) {
         direction = boss.getLookAngle();
      }

      direction = direction.normalize();
      Vec3 end = start.add(direction.scale(LIGHTNING_RANGE));
      LivingEntity direct = findDirectTarget(level, boss, start, end);
      if (direct != null) {
         end = direct.position().add(0.0, direct.getBbHeight() * 0.55, 0.0);
      }

      DamageSource source = ((TensuraDamageSource)TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.LIGHTNING_ELEMENTAL, boss))
         .tensura$setResistanceBypassLevel(1.0F);
      damageLine(level, boss, source, start, end, LIGHTNING_RADIUS, LIGHTNING_DAMAGE, false, horizontalPush, verticalPush);
      level.playSound(null, boss.blockPosition(), (SoundEvent)SoundEvents.TRIDENT_THUNDER.value(), SoundSource.HOSTILE, 3.7F, 1.42F);
      level.playSound(null, boss.blockPosition(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.HOSTILE, 2.0F, 1.7F);
      level.playSound(null, boss.blockPosition(), SoundEvents.BEACON_POWER_SELECT, SoundSource.HOSTILE, 2.2F, 1.8F);
   }

   private static void damageLine(
      ServerLevel level, FeldwayAwakenedEntity boss, DamageSource source, Vec3 start, Vec3 end, double radius, float baseDamage, boolean spiritual
   ) {
      damageLine(level, boss, source, start, end, radius, baseDamage, spiritual, spiritual ? 0.22 : 0.12, spiritual ? 0.08 : 0.035);
   }

   private static void damageLine(
      ServerLevel level,
      FeldwayAwakenedEntity boss,
      DamageSource source,
      Vec3 start,
      Vec3 end,
      double radius,
      float baseDamage,
      boolean spiritual,
      double horizontalPush,
      double verticalPush
   ) {
      AABB box = new AABB(start, end).inflate(radius + 2.0);

      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy)) {
         Vec3 center = living.position().add(0.0, living.getBbHeight() * 0.5, 0.0);
         double distance = distanceToSegment(center, start, end);
         if (!(distance > radius + living.getBbWidth() * 0.5)) {
            double close = 1.0 - Math.min(1.0, distance / radius);
            float damage = baseDamage + (float)(LIGHTNING_CORE_DAMAGE * close * 0.34);
            living.invulnerableTime = 0;
            living.hurt(source, damage);
            living.invulnerableTime = 0;
            if (spiritual) {
               boss.hurtSpiritual(living, MIRAGE_SPIRITUAL_DAMAGE * (0.35F + (float)close * 0.45F));
            }

            Vec3 push = end.subtract(start);
            if (push.lengthSqr() > 0.001) {
               living.setDeltaMovement(living.getDeltaMovement().add(push.normalize().scale(horizontalPush)).add(0.0, verticalPush, 0.0));
               living.hurtMarked = true;
            }
         }
      }
   }

   private static LivingEntity findDirectTarget(ServerLevel level, FeldwayAwakenedEntity boss, Vec3 start, Vec3 end) {
      AABB box = new AABB(start, end).inflate(LIGHTNING_RADIUS);
      List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy);
      LivingEntity best = null;
      double bestDist = Double.MAX_VALUE;

      for (LivingEntity living : list) {
         Vec3 center = living.position().add(0.0, living.getBbHeight() * 0.5, 0.0);
         double distance = distanceToSegment(center, start, end);
         if (!(distance > LIGHTNING_RADIUS + living.getBbWidth() * 0.5)) {
            double distFromStart = center.distanceToSqr(start);
            if (distFromStart < bestDist) {
               bestDist = distFromStart;
               best = living;
            }
         }
      }

      return best;
   }

   private static void sendFullCowl(ServerLevel level, LivingEntity entity, int duration, int percent) {
      FullCowlLightningPayload payload = new FullCowlLightningPayload(entity.getId(), duration, percent, true, true);
      AABB area = entity.getBoundingBox().inflate(SEND_RANGE);

      for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, area)) {
         NetworkManager.sendToPlayer(player, payload);
      }
   }

   private static void sendGearshift(ServerLevel level, LivingEntity entity, LivingEntity target, int duration, int gear) {
      GearshiftTrailPayload payload = new GearshiftTrailPayload(entity.getId(), target.getId(), duration, gear);
      AABB area = entity.getBoundingBox().inflate(SEND_RANGE);

      for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, area)) {
         NetworkManager.sendToPlayer(player, payload);
      }
   }

   private static void applyLightningDegradation(LivingEntity entity) {
      AttributeHelper.addPermanentAttributeIfHigher(
         entity,
         TensuraAttributes.RESISTANCE_DEGRADATION,
         FELDWAY_LIGHTNING_DEGRADATION,
         (Double)cfg().lightningResistanceDegradation.get(),
         Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttributeIfHigher(
         entity,
         TensuraAttributes.LIGHTNING_RESIST_DEGRADATION,
         FELDWAY_LIGHTNING_DEGRADATION,
         (Double)cfg().lightningElementDegradation.get(),
         Operation.ADD_VALUE
      );
   }

   private static void applyMirageDegradation(LivingEntity entity) {
      AttributeHelper.addPermanentAttributeIfHigher(
         entity, TensuraAttributes.RESISTANCE_DEGRADATION, FELDWAY_MIRAGE_DEGRADATION, (Double)cfg().mirageResistanceDegradation.get(), Operation.ADD_VALUE
      );
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

   private static LivingEntity getTarget(FeldwayAwakenedEntity boss) {
      LivingEntity target = boss.resolveLockedTarget();
      if (target == null) {
         target = boss.getTarget();
      }

      return target != null && target.isAlive() && boss.isValidEnemy(target) ? target : null;
   }

   private static Vec3 getBlackHoleSpawnPos(FeldwayAwakenedEntity boss, LivingEntity target) {
      Vec3 targetCenter = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
      Vec3 bossCenter = boss.position().add(0.0, boss.getBbHeight() * 0.5, 0.0);
      Vec3 dir = targetCenter.subtract(bossCenter);
      if (dir.lengthSqr() < 0.001) {
         dir = boss.getLookAngle();
      }

      dir = new Vec3(dir.x, 0.0, dir.z);
      if (dir.lengthSqr() < 0.001) {
         dir = new Vec3(1.0, 0.0, 0.0);
      }

      dir = dir.normalize();
      Vec3 side = new Vec3(-dir.z, 0.0, dir.x).normalize();
      double sideAmount = boss.getRandom().nextBoolean() ? (Double)cfg().asmodaySideOffset.get() : -(Double)cfg().asmodaySideOffset.get();
      return targetCenter.add(dir.scale((Double)cfg().asmodayForwardOffset.get()))
         .add(side.scale(sideAmount))
         .add(0.0, (Double)cfg().asmodayYOffset.get(), 0.0);
   }
}
