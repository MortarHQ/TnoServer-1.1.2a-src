package com.github.wal_bos.moreskills.world.entity.feldway;

import com.github.wal_bos.moreskills.configs.EntityConfigs;
import com.github.wal_bos.moreskills.net.msg.SeraphicGuillotinePayload;
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
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class SeraphicGuillotineAttack {
   public static final int DURATION = (Integer)cfg().seraphicDuration.get();
   private static final ResourceLocation SERAPHIC_DEGRADATION = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "feldway_seraphic_guillotine_degradation"
   );
   private static final double SEND_RANGE = (Double)cfg().seraphicSendRange.get();
   private static final double PLANE_HALF_LENGTH = (Double)cfg().seraphicPlaneHalfLength.get();
   private static final double PLANE_HALF_HEIGHT = (Double)cfg().seraphicPlaneHalfHeight.get();
   private static final double PLANE_RADIUS = (Double)cfg().seraphicPlaneRadius.get();
   private static final double FINAL_RADIUS = (Double)cfg().seraphicFinalRadius.get();
   private static final float MARK_DAMAGE = ((Double)cfg().seraphicMarkDamage.get()).floatValue();
   private static final float SLASH_DAMAGE = ((Double)cfg().seraphicSlashDamage.get()).floatValue();
   private static final float FINAL_DAMAGE = ((Double)cfg().seraphicFinalDamage.get()).floatValue();
   private static final float SPIRITUAL_DAMAGE = ((Double)cfg().seraphicSpiritualDamage.get()).floatValue();
   private static final Map<UUID, SeraphicGuillotineAttack.SeraphicState> STATES = new HashMap<>();

   private static EntityConfigs.AwakenedFeldwayConfig cfg() {
      return EntityConfigs.ENTITY.awakenedFeldway;
   }

   private SeraphicGuillotineAttack() {
   }

   public static void tick(FeldwayAwakenedEntity boss, int t) {
      if (boss.level() instanceof ServerLevel level) {
         LivingEntity target = getTarget(boss);
         if (target == null) {
            STATES.remove(boss.getUUID());
            boss.clearAttack();
            boss.setGlobalCooldown((Integer)cfg().seraphicCooldownNoTarget.get());
         } else {
            SeraphicGuillotineAttack.SeraphicState state = STATES.get(boss.getUUID());
            if (t != 1 && state != null) {
               boss.getLookControl().setLookAt(target, 90.0F, 90.0F);
               holdExecutionPosition(boss, state, target, t);
               pullTowardPlane(level, boss, state, t);
               if (t == 30 || t == 38 || t == 46) {
                  markDamage(level, boss, state, t);
               } else if (t == 58) {
                  firstCut(level, boss, state);
               } else if (t == 70 || t == 76 || t == 82) {
                  delayedCut(level, boss, state, t);
               } else if (t == 92) {
                  finalGuillotine(level, boss, state);
               } else if (t == 104) {
                  aftermath(level, boss, state);
               } else {
                  if (t >= DURATION) {
                     STATES.remove(boss.getUUID());
                     boss.clearAttack();
                     boss.setGlobalCooldown((Integer)cfg().seraphicCooldownEnd.get());
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
      Vec3 targetCenter = target.position().add(0.0, target.getBbHeight() * 0.52, 0.0);
      Vec3 bossCenter = boss.position().add(0.0, boss.getBbHeight() * 0.5, 0.0);
      Vec3 dir = targetCenter.subtract(bossCenter);
      if (dir.lengthSqr() < 0.001) {
         dir = boss.getLookAngle();
      }

      dir = new Vec3(dir.x, 0.0, dir.z);
      if (dir.lengthSqr() < 0.001) {
         dir = new Vec3(0.0, 0.0, 1.0);
      }

      dir = dir.normalize();
      int seed = boss.getRandom().nextInt();
      SeraphicGuillotineAttack.SeraphicState state = new SeraphicGuillotineAttack.SeraphicState(targetCenter, dir, seed);
      STATES.put(boss.getUUID(), state);
      Vec3 teleport = targetCenter.subtract(dir.scale(20.0)).add(0.0, 15.0, 0.0);
      boss.teleportTo(teleport.x, teleport.y, teleport.z);
      boss.getLookControl().setLookAt(target, 90.0F, 90.0F);
      SeraphicGuillotinePayload payload = new SeraphicGuillotinePayload(
         boss.getId(), target.getId(), state.center.x, state.center.y, state.center.z, state.dir.x, state.dir.z, DURATION, seed
      );
      AABB area = new AABB(state.center, state.center).inflate(SEND_RANGE);

      for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, area)) {
         NetworkManager.sendToPlayer(player, payload);
      }

      level.playSound(null, state.center.x, state.center.y, state.center.z, SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 5.8F, 0.62F);
      level.playSound(null, state.center.x, state.center.y, state.center.z, SoundEvents.BEACON_ACTIVATE, SoundSource.HOSTILE, 6.2F, 0.42F);
      level.playSound(null, state.center.x, state.center.y, state.center.z, SoundEvents.END_PORTAL_SPAWN, SoundSource.HOSTILE, 5.4F, 0.34F);
      level.playSound(null, state.center.x, state.center.y, state.center.z, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.HOSTILE, 6.0F, 0.25F);
   }

   private static void holdExecutionPosition(FeldwayAwakenedEntity boss, SeraphicGuillotineAttack.SeraphicState state, LivingEntity target, int t) {
      Vec3 hover = state.center.subtract(state.dir.scale(18.0)).add(0.0, 13.0 + Math.sin(t * 0.09) * 1.0, 0.0);
      boss.getMoveControl().setWantedPosition(hover.x, hover.y, hover.z, 2.25);
   }

   private static void pullTowardPlane(ServerLevel level, FeldwayAwakenedEntity boss, SeraphicGuillotineAttack.SeraphicState state, int t) {
      AABB box = new AABB(state.center, state.center).inflate(PLANE_HALF_LENGTH + 16.0, PLANE_HALF_HEIGHT, PLANE_HALF_LENGTH + 16.0);
      List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy);
      Vec3 side = side(state.dir);

      for (LivingEntity living : list) {
         Vec3 c = living.position().add(0.0, living.getBbHeight() * 0.5, 0.0);
         Vec3 rel = c.subtract(state.center);
         double along = rel.dot(side);
         double forward = rel.dot(state.dir);
         if (!(Math.abs(along) > PLANE_HALF_LENGTH + 12.0) && !(Math.abs(forward) > 18.0)) {
            Vec3 correction = state.dir.scale(-forward * 0.035);
            living.setDeltaMovement(living.getDeltaMovement().add(correction).multiply(0.92, 0.98, 0.92));
            living.hurtMarked = true;
         }
      }

      if (t % 16 == 0) {
         level.playSound(
            null,
            state.center.x,
            state.center.y,
            state.center.z,
            SoundEvents.BEACON_AMBIENT,
            SoundSource.HOSTILE,
            2.0F,
            0.78F + boss.getRandom().nextFloat() * 0.18F
         );
      }
   }

   private static void markDamage(ServerLevel level, FeldwayAwakenedEntity boss, SeraphicGuillotineAttack.SeraphicState state, int t) {
      DamageSource source = source(level, boss, TensuraDamageTypes.HOLY_DAMAGE);
      damagePlane(level, boss, source, state, PLANE_RADIUS * 0.72, MARK_DAMAGE, SPIRITUAL_DAMAGE * 0.18F, 0.1, false);
      level.playSound(
         null, state.center.x, state.center.y, state.center.z, SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.HOSTILE, 3.2F, 0.66F + (t - 30) * 0.025F
      );
      level.playSound(null, state.center.x, state.center.y, state.center.z, SoundEvents.BEACON_POWER_SELECT, SoundSource.HOSTILE, 2.8F, 1.25F);
   }

   private static void firstCut(ServerLevel level, FeldwayAwakenedEntity boss, SeraphicGuillotineAttack.SeraphicState state) {
      DamageSource source = source(level, boss, TensuraDamageTypes.SPACE_ELEMENTAL);
      damagePlane(level, boss, source, state, PLANE_RADIUS, SLASH_DAMAGE, SPIRITUAL_DAMAGE * 0.62F, 0.32, false);
      cutBlocks(level, boss, state, 3.0, false);
      level.playSound(null, state.center.x, state.center.y, state.center.z, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.HOSTILE, 7.0F, 0.36F);
      level.playSound(null, state.center.x, state.center.y, state.center.z, (SoundEvent)SoundEvents.TRIDENT_RIPTIDE_3.value(), SoundSource.HOSTILE, 5.2F, 1.18F);
      level.playSound(null, state.center.x, state.center.y, state.center.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 4.6F, 0.78F);
   }

   private static void delayedCut(ServerLevel level, FeldwayAwakenedEntity boss, SeraphicGuillotineAttack.SeraphicState state, int t) {
      DamageSource source = source(level, boss, TensuraDamageTypes.SPACE_ELEMENTAL);
      double angleOffset = t == 70 ? 0.6911503837897545 : (t == 76 ? -0.6911503837897545 : Math.PI / 2);
      Vec3 rotated = rotateFlat(state.dir, angleOffset);
      SeraphicGuillotineAttack.SeraphicState cut = new SeraphicGuillotineAttack.SeraphicState(state.center, rotated, state.seed + t);
      damagePlane(level, boss, source, cut, PLANE_RADIUS * 0.82, SLASH_DAMAGE * 0.72F, SPIRITUAL_DAMAGE * 0.38F, 0.18, false);
      cutBlocks(level, boss, cut, 2.0, false);
      level.playSound(
         null, state.center.x, state.center.y, state.center.z, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.HOSTILE, 4.8F, 0.58F + (t - 70) * 0.015F
      );
      level.playSound(null, state.center.x, state.center.y, state.center.z, SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.HOSTILE, 3.2F, 1.45F);
   }

   private static void finalGuillotine(ServerLevel level, FeldwayAwakenedEntity boss, SeraphicGuillotineAttack.SeraphicState state) {
      applyDegradation(boss);
      DamageSource source = source(level, boss, TensuraDamageTypes.HOLY_DAMAGE);
      damagePlane(level, boss, source, state, FINAL_RADIUS, FINAL_DAMAGE, SPIRITUAL_DAMAGE, 0.42, true);
      cutBlocks(level, boss, state, 5.0, true);
      level.playSound(null, state.center.x, state.center.y, state.center.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 8.4F, 0.42F);
      level.playSound(null, state.center.x, state.center.y, state.center.z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.HOSTILE, 8.0F, 0.74F);
      level.playSound(null, state.center.x, state.center.y, state.center.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE, 7.0F, 0.7F);
      level.playSound(null, state.center.x, state.center.y, state.center.z, SoundEvents.END_PORTAL_SPAWN, SoundSource.HOSTILE, 6.4F, 0.28F);
   }

   private static void aftermath(ServerLevel level, FeldwayAwakenedEntity boss, SeraphicGuillotineAttack.SeraphicState state) {
      DamageSource source = source(level, boss, TensuraDamageTypes.HOLY_DAMAGE);
      damagePlane(level, boss, source, state, FINAL_RADIUS + 4.0, 7400.0F, SPIRITUAL_DAMAGE * 0.28F, 0.2, false);
      level.playSound(null, state.center.x, state.center.y, state.center.z, SoundEvents.BEACON_DEACTIVATE, SoundSource.HOSTILE, 4.8F, 0.5F);
      level.playSound(null, state.center.x, state.center.y, state.center.z, SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.HOSTILE, 4.0F, 0.4F);
   }

   private static void damagePlane(
      ServerLevel level,
      FeldwayAwakenedEntity boss,
      DamageSource source,
      SeraphicGuillotineAttack.SeraphicState state,
      double radius,
      float damage,
      float spiritual,
      double push,
      boolean finisher
   ) {
      Vec3 side = side(state.dir);
      AABB box = new AABB(state.center, state.center).inflate(PLANE_HALF_LENGTH + 8.0, PLANE_HALF_HEIGHT, PLANE_HALF_LENGTH + 8.0);

      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy)) {
         Vec3 center = living.position().add(0.0, living.getBbHeight() * 0.5, 0.0);
         Vec3 rel = center.subtract(state.center);
         double forward = Math.abs(rel.dot(state.dir));
         double along = Math.abs(rel.dot(side));
         double height = Math.abs(rel.y);
         if (!(forward > radius + living.getBbWidth() * 0.5) && !(along > PLANE_HALF_LENGTH) && !(height > PLANE_HALF_HEIGHT)) {
            double close = 1.0 - Math.min(1.0, forward / Math.max(0.001, radius));
            double centerBonus = 1.0 - Math.min(1.0, along / PLANE_HALF_LENGTH);
            float finalDamage = damage + (float)(damage * close * 0.45) + (float)(damage * centerBonus * (finisher ? 0.42 : 0.18));
            living.invulnerableTime = 0;
            living.hurt(source, finalDamage);
            living.invulnerableTime = 0;
            if (spiritual > 0.0F) {
               boss.hurtSpiritual(living, spiritual * (0.45F + (float)close * 0.55F));
            }

            Vec3 knock = state.dir.scale(rel.dot(state.dir) >= 0.0 ? 1.0 : -1.0);
            living.setDeltaMovement(living.getDeltaMovement().add(knock.scale(push)).add(0.0, finisher ? 0.18 : 0.08, 0.0));
            living.hurtMarked = true;
         }
      }
   }

   private static void cutBlocks(ServerLevel level, FeldwayAwakenedEntity boss, SeraphicGuillotineAttack.SeraphicState state, double radius, boolean finisher) {
      Vec3 side = side(state.dir);
      int halfLength = finisher ? 50 : 36;
      int halfHeight = finisher ? 32 : 24;
      int r = Math.max(1, (int)Math.ceil(radius));

      for (int l = -halfLength; l <= halfLength; l += finisher ? 2 : 3) {
         for (int h = -8; h <= halfHeight; h += finisher ? 2 : 3) {
            Vec3 center = state.center.add(side.scale(l)).add(0.0, h, 0.0);
            BlockPos base = BlockPos.containing(center);

            for (int f = -r; f <= r; f++) {
               for (int y = -r; y <= r; y++) {
                  double d = Math.sqrt(f * f + y * y);
                  if (!(d > radius) && (finisher || !(d > radius * 0.65) || !(boss.getRandom().nextFloat() > 0.45F))) {
                     BlockPos pos = base.offset((int)Math.round(state.dir.x * f), y, (int)Math.round(state.dir.z * f));
                     BlockState blockState = level.getBlockState(pos);
                     if (!blockState.isAir() && blockState.getFluidState().isEmpty() && !(blockState.getDestroySpeed(level, pos) < 0.0F)) {
                        level.destroyBlock(pos, false, boss);
                     }
                  }
               }
            }
         }
      }
   }

   private static DamageSource source(ServerLevel level, FeldwayAwakenedEntity boss, ResourceKey<DamageType> type) {
      return ((TensuraDamageSource)TensuraDamageTypes.getEntityDamageSource(level, type, boss)).tensura$setResistanceBypassLevel(1.0F);
   }

   private static void applyDegradation(LivingEntity entity) {
      AttributeHelper.addPermanentAttributeIfHigher(
         entity, TensuraAttributes.RESISTANCE_DEGRADATION, SERAPHIC_DEGRADATION, (Double)cfg().seraphicResistanceDegradation.get(), Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttributeIfHigher(
         entity, TensuraAttributes.LIGHT_RESIST_DEGRADATION, SERAPHIC_DEGRADATION, (Double)cfg().seraphicLightDegradation.get(), Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttributeIfHigher(
         entity, TensuraAttributes.SPACE_RESIST_DEGRADATION, SERAPHIC_DEGRADATION, (Double)cfg().seraphicSpaceDegradation.get(), Operation.ADD_VALUE
      );
   }

   private static Vec3 side(Vec3 dir) {
      Vec3 side = new Vec3(-dir.z, 0.0, dir.x);
      if (side.lengthSqr() < 0.001) {
         side = new Vec3(1.0, 0.0, 0.0);
      }

      return side.normalize();
   }

   private static Vec3 rotateFlat(Vec3 dir, double angle) {
      double cos = Math.cos(angle);
      double sin = Math.sin(angle);
      Vec3 rotated = new Vec3(dir.x * cos - dir.z * sin, 0.0, dir.x * sin + dir.z * cos);
      return rotated.lengthSqr() < 0.001 ? dir : rotated.normalize();
   }

   private static LivingEntity getTarget(FeldwayAwakenedEntity boss) {
      LivingEntity target = boss.resolveLockedTarget();
      if (target == null) {
         target = boss.getTarget();
      }

      return target != null && target.isAlive() && boss.isValidEnemy(target) ? target : null;
   }

   private record SeraphicState(Vec3 center, Vec3 dir, int seed) {
   }
}
