package com.github.wal_bos.moreskills.world.entity.feldway;

import com.github.wal_bos.moreskills.configs.EntityConfigs;
import com.github.wal_bos.moreskills.net.msg.FeldwayAvatarBeamPayload;
import com.github.wal_bos.moreskills.world.entity.FeldwayAwakenedEntity;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.tensura.damage.TensuraDamageSource;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.util.AttributeHelper;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class AvatarOblivionAttack {
   public static final int DURATION = (Integer)cfg().avatarDuration.get();
   private static final ResourceLocation AVATAR_DEGRADATION = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "feldway_avatar_oblivion_degradation");
   private static final double SEND_RANGE = (Double)cfg().avatarSendRange.get();
   private static final double BEAM_RANGE = (Double)cfg().avatarBeamRange.get();
   private static final double BEAM_RADIUS = (Double)cfg().avatarBeamRadius.get();
   private static final double FINAL_RADIUS = (Double)cfg().avatarFinalRadius.get();
   private static final double CORE_RADIUS = (Double)cfg().avatarCoreRadius.get();
   private static final float BEAM_DAMAGE = ((Double)cfg().avatarBeamDamage.get()).floatValue();
   private static final float FINAL_DAMAGE = ((Double)cfg().avatarFinalDamage.get()).floatValue();
   private static final float CORE_DAMAGE = ((Double)cfg().avatarCoreDamage.get()).floatValue();
   private static final float SPIRITUAL_DAMAGE = ((Double)cfg().avatarSpiritualDamage.get()).floatValue();
   private static final Map<UUID, AvatarOblivionAttack.AvatarState> STATES = new HashMap<>();

   private static EntityConfigs.AwakenedFeldwayConfig cfg() {
      return EntityConfigs.ENTITY.awakenedFeldway;
   }

   private AvatarOblivionAttack() {
   }

   public static void tick(FeldwayAwakenedEntity boss, int t) {
      if (boss.level() instanceof ServerLevel level) {
         LivingEntity target = getTarget(boss);
         if (target == null) {
            STATES.remove(boss.getUUID());
            boss.clearAttack();
            boss.setGlobalCooldown((Integer)cfg().avatarCooldownNoTarget.get());
         } else {
            AvatarOblivionAttack.AvatarState state = STATES.get(boss.getUUID());
            if (t != 1 && state != null) {
               boss.getLookControl().setLookAt(target, 90.0F, 90.0F);
               holdPosition(boss, target, t);
               if (t == 24) {
                  level.playSound(null, boss.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.HOSTILE, 5.2F, 0.36F);
                  level.playSound(null, boss.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.HOSTILE, 5.0F, 0.22F);
               } else if (t == 44) {
                  level.playSound(null, boss.blockPosition(), SoundEvents.BEACON_POWER_SELECT, SoundSource.HOSTILE, 7.0F, 0.32F);
                  level.playSound(null, boss.blockPosition(), SoundEvents.WARDEN_SONIC_CHARGE, SoundSource.HOSTILE, 7.0F, 0.46F);
               } else if (t == 62 || t == 66 || t == 70) {
                  firePulse(level, boss, target, state, t, false);
               } else if (t == 76) {
                  firePulse(level, boss, target, state, t, true);
               } else if (t != 86 && t != 94) {
                  if (t >= DURATION) {
                     STATES.remove(boss.getUUID());
                     boss.clearAttack();
                     boss.setGlobalCooldown((Integer)cfg().avatarCooldownEnd.get());
                  }
               } else {
                  aftershock(level, boss, target, state, t);
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
      Vec3 start = getLockedBeamStart(boss, target);
      Vec3 end = getBeamEnd(target, start);
      int seed = boss.getRandom().nextInt();
      STATES.put(boss.getUUID(), new AvatarOblivionAttack.AvatarState(start, seed));
      FeldwayAvatarBeamPayload payload = new FeldwayAvatarBeamPayload(
         boss.getId(), target.getId(), start.x, start.y, start.z, end.x, end.y, end.z, DURATION, seed
      );
      AABB area = new AABB(start, end).inflate(SEND_RANGE);

      for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, area)) {
         NetworkManager.sendToPlayer(player, payload);
      }

      level.playSound(null, boss.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.HOSTILE, 9.0F, 0.26F);
      level.playSound(null, boss.blockPosition(), SoundEvents.WARDEN_ROAR, SoundSource.HOSTILE, 8.0F, 0.24F);
      level.playSound(null, boss.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.HOSTILE, 7.0F, 0.38F);
      level.playSound(null, boss.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.HOSTILE, 7.0F, 0.2F);
   }

   private static void firePulse(
      ServerLevel level, FeldwayAwakenedEntity boss, LivingEntity target, AvatarOblivionAttack.AvatarState state, int t, boolean finisher
   ) {
      applyDegradation(boss);
      Vec3 start = state.start;
      Vec3 end = getBeamEnd(target, start);
      DamageSource source = source(level, boss);
      double radius = finisher ? FINAL_RADIUS : BEAM_RADIUS;
      float damage = finisher ? FINAL_DAMAGE : BEAM_DAMAGE;
      float spiritual = finisher ? SPIRITUAL_DAMAGE * 1.45F : SPIRITUAL_DAMAGE * 0.72F;
      damageBeam(level, boss, source, start, end, radius, damage, spiritual, finisher);
      destroyBeamBlocks(level, boss, start, end, finisher ? 7.0 : 4.25, finisher);
      level.playSound(null, start.x, start.y, start.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, finisher ? 9.5F : 7.5F, finisher ? 0.34F : 0.58F);
      level.playSound(
         null, start.x, start.y, start.z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.HOSTILE, finisher ? 9.0F : 7.0F, finisher ? 0.55F : 0.95F
      );
      level.playSound(null, start.x, start.y, start.z, SoundEvents.END_PORTAL_SPAWN, SoundSource.HOSTILE, finisher ? 6.0F : 3.5F, finisher ? 0.28F : 0.48F);
      level.playSound(
         null, end.x, end.y, end.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE, finisher ? 7.4F : 5.4F, finisher ? 0.62F : 0.9F
      );
   }

   private static void aftershock(ServerLevel level, FeldwayAwakenedEntity boss, LivingEntity target, AvatarOblivionAttack.AvatarState state, int t) {
      Vec3 start = state.start;
      Vec3 end = getBeamEnd(target, start);
      DamageSource source = source(level, boss);
      float scale = t == 94 ? 1.0F : 0.72F;
      damageBeam(level, boss, source, start, end, FINAL_RADIUS + 5.5, 7600.0F * scale, SPIRITUAL_DAMAGE * 0.5F * scale, false);
      destroyBeamBlocks(level, boss, start, end, t == 94 ? 5.0 : 3.0, false);
      level.playSound(null, end.x, end.y, end.z, SoundEvents.BEACON_DEACTIVATE, SoundSource.HOSTILE, 5.8F, 0.42F);
      level.playSound(null, end.x, end.y, end.z, SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.HOSTILE, 6.0F, 0.62F);
   }

   private static void holdPosition(FeldwayAwakenedEntity boss, LivingEntity target, int t) {
      Vec3 targetCenter = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
      Vec3 bossCenter = boss.position().add(0.0, boss.getBbHeight() * 0.5, 0.0);
      Vec3 dir = bossCenter.subtract(targetCenter);
      if (dir.lengthSqr() < 0.001) {
         dir = target.getLookAngle().scale(-1.0);
      }

      dir = new Vec3(dir.x, 0.0, dir.z);
      if (dir.lengthSqr() < 0.001) {
         dir = new Vec3(1.0, 0.0, 0.0);
      }

      dir = dir.normalize();
      double hover = 13.5 + Math.sin(t * 0.07) * 1.0;
      Vec3 wanted = targetCenter.add(dir.scale(36.0)).add(0.0, hover, 0.0);
      boss.getMoveControl().setWantedPosition(wanted.x, wanted.y, wanted.z, 1.55);
   }

   private static void damageBeam(
      ServerLevel level,
      FeldwayAwakenedEntity boss,
      DamageSource source,
      Vec3 start,
      Vec3 end,
      double radius,
      float baseDamage,
      float spiritual,
      boolean finisher
   ) {
      AABB box = new AABB(start, end).inflate(radius + 5.0);

      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy)) {
         Vec3 center = living.position().add(0.0, living.getBbHeight() * 0.5, 0.0);
         double distance = distanceToSegment(center, start, end);
         if (!(distance > radius + living.getBbWidth() * 0.5)) {
            double close = 1.0 - Math.min(1.0, distance / radius);
            double core = 1.0 - Math.min(1.0, distance / CORE_RADIUS);
            float damage = baseDamage + (float)(CORE_DAMAGE * Math.max(0.0, core) * (finisher ? 1.0 : 0.62)) + (float)(baseDamage * close * 0.42);
            living.invulnerableTime = 0;
            living.hurt(source, damage);
            living.invulnerableTime = 0;
            if (spiritual > 0.0F) {
               boss.hurtSpiritual(living, spiritual * (0.45F + (float)close * 0.55F));
            }

            Vec3 push = end.subtract(start);
            if (push.lengthSqr() > 0.001) {
               double horizontal = finisher ? 0.22 : 0.12;
               double vertical = finisher ? 0.08 : 0.04;
               living.setDeltaMovement(living.getDeltaMovement().add(push.normalize().scale(horizontal)).add(0.0, vertical, 0.0));
               living.hurtMarked = true;
            }
         }
      }
   }

   private static void destroyBeamBlocks(ServerLevel level, FeldwayAwakenedEntity boss, Vec3 start, Vec3 end, double radius, boolean finisher) {
      Vec3 line = end.subtract(start);
      double length = line.length();
      if (!(length < 0.001)) {
         Vec3 dir = line.normalize();
         int steps = Math.min(finisher ? 80 : 56, Math.max(12, (int)(length / 3.0)));
         int r = Math.max(1, (int)Math.ceil(radius));

         for (int i = 0; i <= steps; i++) {
            double p = (double)i / steps;
            Vec3 center = start.add(dir.scale(length * p));
            BlockPos base = BlockPos.containing(center);

            for (int x = -r; x <= r; x++) {
               for (int y = -r; y <= r; y++) {
                  for (int z = -r; z <= r; z++) {
                     double dist = Math.sqrt(x * x + y * y + z * z);
                     if (!(dist > radius) && (finisher || !(dist > radius * 0.72) || !(boss.getRandom().nextFloat() > 0.35F))) {
                        BlockPos pos = base.offset(x, y, z);
                        BlockState state = level.getBlockState(pos);
                        if (!state.isAir() && state.getFluidState().isEmpty() && !(state.getDestroySpeed(level, pos) < 0.0F)) {
                           level.destroyBlock(pos, false, boss);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static Vec3 getLockedBeamStart(FeldwayAwakenedEntity boss, LivingEntity target) {
      Vec3 bossCenter = boss.position().add(0.0, boss.getBbHeight() * 0.72, 0.0);
      Vec3 targetCenter = target.position().add(0.0, target.getBbHeight() * 0.55, 0.0);
      Vec3 dir = targetCenter.subtract(bossCenter);
      if (dir.lengthSqr() < 0.001) {
         dir = boss.getLookAngle();
      }

      dir = new Vec3(dir.x, 0.0, dir.z);
      if (dir.lengthSqr() < 0.001) {
         dir = new Vec3(0.0, 0.0, 1.0);
      }

      dir = dir.normalize();
      return bossCenter.add(dir.scale(12.0)).add(0.0, 27.0, 0.0);
   }

   private static Vec3 getBeamEnd(LivingEntity target, Vec3 start) {
      Vec3 targetCenter = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
      Vec3 direction = targetCenter.subtract(start);
      if (direction.lengthSqr() < 0.001) {
         direction = new Vec3(0.0, -0.15, 1.0);
      }

      direction = direction.normalize();
      return start.add(direction.scale(BEAM_RANGE));
   }

   private static DamageSource source(ServerLevel level, FeldwayAwakenedEntity boss) {
      return ((TensuraDamageSource)TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.HOLY_DAMAGE, boss))
         .tensura$setResistanceBypassLevel(1.0F);
   }

   private static void applyDegradation(LivingEntity entity) {
      AttributeHelper.addPermanentAttributeIfHigher(
         entity, TensuraAttributes.RESISTANCE_DEGRADATION, AVATAR_DEGRADATION, (Double)cfg().avatarResistanceDegradation.get(), Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttributeIfHigher(
         entity, TensuraAttributes.LIGHT_RESIST_DEGRADATION, AVATAR_DEGRADATION, (Double)cfg().avatarLightDegradation.get(), Operation.ADD_VALUE
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

   private record AvatarState(Vec3 start, int seed) {
   }
}
