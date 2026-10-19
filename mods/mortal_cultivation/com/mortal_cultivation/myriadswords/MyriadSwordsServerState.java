package com.mortal_cultivation.myriadswords;

import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.StartTracking;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public final class MyriadSwordsServerState {
   private static final double TARGET_RANGE = 64.0;
   private static final double TARGET_AIM_RADIUS = 2.75;
   private static final double LOCK_AIM_RADIUS = 4.0;
   private static final int TARGET_LOSS_GRACE_TICKS = 8;
   private static final float DAMAGE_PER_STRIKE = 8.0F;
   private static final int TARGET_IMPACT_DELAY_TICKS = 18;
   private static final int DAMAGE_INTERVAL_TICKS = 2;
   private static final Map<UUID, MyriadSwordsServerState.CastState> ACTIVE_CASTS = new ConcurrentHashMap<>();

   private MyriadSwordsServerState() {
   }

   public static void tick(ServerPlayer caster) {
      if (!caster.isAlive()) {
         stop(caster);
      } else {
         UUID casterId = caster.getUUID();
         MyriadSwordsServerState.CastState state = ACTIVE_CASTS.get(casterId);
         LivingEntity target = resolveTarget(caster, state);
         int targetEntityId = target == null ? -1 : target.getId();
         if (state == null) {
            state = new MyriadSwordsServerState.CastState(caster.getRandom().nextInt(), caster.level().getGameTime(), targetEntityId);
            ACTIVE_CASTS.put(casterId, state);
            syncToTrackingAndSelf(caster, state, true);
         } else if (state.targetEntityId != targetEntityId) {
            state.targetEntityId = targetEntityId;
            state.targetLockTicks = 0;
            syncToTrackingAndSelf(caster, state, true);
         }

         if (target == null) {
            state.targetLockTicks = 0;
         } else {
            long castAge = Math.max(0L, caster.level().getGameTime() - state.startGameTime);
            boolean swordAtCrosshair = castAge >= 16L && (castAge - 16L) % 2L == 0L;
            if (state.targetLockTicks >= 18 && swordAtCrosshair) {
               strike(caster, target);
            }

            if (state.targetLockTicks < Integer.MAX_VALUE) {
               state.targetLockTicks++;
            }
         }
      }
   }

   public static void stop(ServerPlayer caster) {
      MyriadSwordsServerState.CastState state = ACTIVE_CASTS.remove(caster.getUUID());
      if (state != null) {
         MyriadSwordsNetwork.sendToTrackingAndSelf(caster, new MyriadSwordsStatePayload(caster.getUUID(), false, state.seed, state.startGameTime, -1));
      }
   }

   @Nullable
   private static LivingEntity resolveTarget(ServerPlayer caster, @Nullable MyriadSwordsServerState.CastState state) {
      LivingEntity aimedTarget = findTarget(caster, 2.75);
      if (state != null && state.targetEntityId >= 0) {
         if (!(caster.level().getEntity(state.targetEntityId) instanceof LivingEntity lockedTarget && isValidTarget(caster, lockedTarget))) {
            state.targetMissingTicks = 0;
            return aimedTarget;
         } else if (isTargetWithinAim(caster, lockedTarget, 4.0)) {
            state.targetMissingTicks = 0;
            return lockedTarget;
         } else if (aimedTarget == null && state.targetMissingTicks < 8) {
            state.targetMissingTicks++;
            return lockedTarget;
         } else {
            state.targetMissingTicks = 0;
            return aimedTarget;
         }
      } else {
         return aimedTarget;
      }
   }

   @Nullable
   private static LivingEntity findTarget(ServerPlayer caster, double aimRadius) {
      Vec3 start = caster.getEyePosition();
      Vec3 look = safeNormalize(caster.getLookAngle(), new Vec3(0.0, 0.0, 1.0));
      Vec3 end = start.add(look.scale(64.0));
      BlockHitResult blockHit = caster.level().clip(new ClipContext(start, end, Block.COLLIDER, Fluid.NONE, caster));
      if (blockHit.getType() != Type.MISS) {
         end = blockHit.getLocation();
      }

      Vec3 ray = end.subtract(start);
      AABB searchBox = caster.getBoundingBox().expandTowards(ray).inflate(aimRadius + 1.0);
      LivingEntity bestTarget = null;
      double bestScore = Double.MAX_VALUE;

      for (LivingEntity candidate : caster.level().getEntitiesOfClass(LivingEntity.class, searchBox, entity -> isValidTarget(caster, entity))) {
         if (isTargetWithinAim(caster, candidate, aimRadius)) {
            Vec3 targetCenter = candidate.getBoundingBox().getCenter();
            double distance = start.distanceTo(targetCenter);
            double score = distance * 0.01 + distanceToRaySqr(candidate.getBoundingBox(), start, look, end);
            if (score < bestScore) {
               bestScore = score;
               bestTarget = candidate;
            }
         }
      }

      return bestTarget;
   }

   private static boolean isTargetWithinAim(ServerPlayer caster, LivingEntity target, double aimRadius) {
      Vec3 start = caster.getEyePosition();
      Vec3 look = safeNormalize(caster.getLookAngle(), new Vec3(0.0, 0.0, 1.0));
      Vec3 targetCenter = target.getBoundingBox().getCenter();
      Vec3 toTarget = targetCenter.subtract(start);
      double projection = toTarget.dot(look);
      if (!(projection < 0.0) && !(projection > 64.0)) {
         Vec3 closestPoint = start.add(look.scale(projection));
         double targetRadius = aimRadius + target.getBbWidth() * 0.5;
         if (distanceToBoxSqr(target.getBoundingBox(), closestPoint) > targetRadius * targetRadius) {
            return false;
         }

         BlockHitResult blockHit = caster.level().clip(new ClipContext(start, targetCenter, Block.COLLIDER, Fluid.NONE, caster));
         return blockHit.getType() == Type.MISS || blockHit.getLocation().distanceToSqr(start) >= targetCenter.distanceToSqr(start) - 0.25;
      } else {
         return false;
      }
   }

   private static double distanceToRaySqr(AABB box, Vec3 start, Vec3 look, Vec3 end) {
      Vec3 center = box.getCenter();
      double projection = Math.max(0.0, Math.min(start.distanceTo(end), center.subtract(start).dot(look)));
      return distanceToBoxSqr(box, start.add(look.scale(projection)));
   }

   private static double distanceToBoxSqr(AABB box, Vec3 point) {
      double dx = Math.max(Math.max(box.minX - point.x, 0.0), point.x - box.maxX);
      double dy = Math.max(Math.max(box.minY - point.y, 0.0), point.y - box.maxY);
      double dz = Math.max(Math.max(box.minZ - point.z, 0.0), point.z - box.maxZ);
      return dx * dx + dy * dy + dz * dz;
   }

   private static Vec3 safeNormalize(Vec3 value, Vec3 fallback) {
      return value.lengthSqr() > 1.0E-8 ? value.normalize() : fallback;
   }

   private static boolean isValidTarget(ServerPlayer caster, Entity candidate) {
      if (candidate instanceof LivingEntity living && living != caster && living.isAlive() && living.isPickable() && !living.isSpectator()) {
         return !(living instanceof Player player && (player.getAbilities().instabuild || player.getAbilities().invulnerable))
            ? !SubordinateHelper.isAlly(caster, living)
            : false;
      } else {
         return false;
      }
   }

   private static void strike(ServerPlayer caster, LivingEntity target) {
      if (isValidTarget(caster, target)) {
         target.invulnerableTime = 0;
         target.hurt(caster.damageSources().playerAttack(caster), 8.0F);
         target.invulnerableTime = 0;
      }
   }

   private static void syncToTrackingAndSelf(ServerPlayer caster, MyriadSwordsServerState.CastState state, boolean active) {
      MyriadSwordsNetwork.sendToTrackingAndSelf(
         caster, new MyriadSwordsStatePayload(caster.getUUID(), active, state.seed, state.startGameTime, state.targetEntityId)
      );
   }

   @SubscribeEvent
   public static void onStartTracking(StartTracking event) {
      if (event.getEntity() instanceof ServerPlayer observer && event.getTarget() instanceof ServerPlayer caster) {
         MyriadSwordsServerState.CastState state = ACTIVE_CASTS.get(caster.getUUID());
         if (state != null) {
            MyriadSwordsNetwork.sendToPlayer(
               observer, new MyriadSwordsStatePayload(caster.getUUID(), true, state.seed, state.startGameTime, state.targetEntityId)
            );
         }
      }
   }

   @SubscribeEvent
   public static void onLivingDeath(LivingDeathEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         stop(player);
      }
   }

   @SubscribeEvent
   public static void onPlayerLogout(PlayerLoggedOutEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         stop(player);
      }
   }

   @SubscribeEvent
   public static void onPlayerChangedDimension(PlayerChangedDimensionEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         stop(player);
      }
   }

   @SubscribeEvent
   public static void onServerStopped(ServerStoppedEvent event) {
      ACTIVE_CASTS.clear();
   }

   private static final class CastState {
      private final int seed;
      private final long startGameTime;
      private int targetEntityId;
      private int targetLockTicks;
      private int targetMissingTicks;

      private CastState(int seed, long startGameTime, int targetEntityId) {
         this.seed = seed;
         this.startGameTime = startGameTime;
         this.targetEntityId = targetEntityId;
      }
   }
}
