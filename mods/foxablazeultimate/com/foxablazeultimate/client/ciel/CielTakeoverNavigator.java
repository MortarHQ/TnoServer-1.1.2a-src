package com.foxablazeultimate.client.ciel;

import com.foxablazeultimate.ability.skill.ciel.CielTakeoverTerrainTags;
import com.foxablazeultimate.config.CielConfig;
import com.foxablazeultimate.network.RequestCielTerrainActionPayload;
import dev.architectury.networking.NetworkManager;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class CielTakeoverNavigator {
   private static final int NO_TARGET = -1;
   private static final byte BLOCKED = 1;
   private static final byte FLUID = 2;
   private static final byte BREAKABLE = 4;
   private static final byte UNLOADED = 8;
   private static final double EPSILON = 1.0E-4;
   private static final double DIAGONAL_COST = Math.sqrt(2.0);
   private static final double TURN_PENALTY = 0.001;
   private static final int[] NEIGHBOR_OFFSETS = new int[]{1, 0, -1, 0, 0, 1, 0, -1, 1, 1, 1, -1, -1, 1, -1, -1};
   private static final int MAX_ROUTE_POINTS = 128;
   private static final ExecutorService PATH_EXECUTOR = Executors.newSingleThreadExecutor(task -> {
      Thread thread = new Thread(task, "FoxAblaze-Ciel-Pathfinder");
      thread.setDaemon(true);
      thread.setPriority(1);
      return thread;
   });
   private static final AtomicReference<CielTakeoverNavigator.PlanResult> COMPLETED = new AtomicReference<>();
   private static volatile int generation;
   private static Future<?> inFlight;
   private static CielTakeoverNavigator.SnapshotBuilder snapshotBuilder;
   private static LocalPlayer activePlayer;
   private static ClientLevel activeLevel;
   private static ResourceLocation dimension;
   private static int targetId = Integer.MIN_VALUE;
   private static Vec3 plannedTarget = Vec3.ZERO;
   private static List<CielTakeoverNavigator.Waypoint> route = List.of();
   private static int routeIndex;
   private static boolean escapeRoute;
   private static boolean lastPlanConfined;
   private static BlockPos lastBreakSuggestion;
   private static int nextPlanTick;
   private static int failedPlanTicks;
   private static int lastProcessedTick = Integer.MIN_VALUE;
   private static CielTakeoverNavigator.NavigationCommand cachedCommand = CielTakeoverNavigator.NavigationCommand.UNHANDLED;
   private static Vec3 progressAnchor;
   private static int progressTicks;
   private static int routeStartedTick;
   private static int settleUntilTick;
   private static boolean settleSneaking;
   private static CielTakeoverNavigator.TerrainMode terrainMode = CielTakeoverNavigator.TerrainMode.NONE;
   private static CielTakeoverNavigator.PillarPhase pillarPhase = CielTakeoverNavigator.PillarPhase.CENTERING;
   private static BlockPos terrainPosition;
   private static int nextTerrainActionTick;
   private static int terrainAttempts;
   private static int terrainTicks;
   private static int pillarStableTicks;
   private static int terrainRetryAfterTick;

   private CielTakeoverNavigator() {
   }

   public static CielTakeoverNavigator.NavigationCommand tick(LocalPlayer player, LivingEntity target, boolean serverAdvancing) {
      ClientLevel currentLevel = player.clientLevel;
      ResourceLocation currentDimension = currentLevel.dimension().location();
      int currentTargetId = target == null ? -1 : target.getId();
      boolean identityChanged = player != activePlayer || currentLevel != activeLevel;
      if (identityChanged || !currentDimension.equals(dimension)) {
         resetSession(player, currentDimension, currentTargetId, !identityChanged);
      } else if (currentTargetId != targetId) {
         retarget(player, currentTargetId);
      }

      if (lastProcessedTick == player.tickCount) {
         return cachedCommand;
      }

      lastProcessedTick = player.tickCount;
      if (!isPlanningPositionValid(player, target)) {
         suspendPlanning(player);
         cachedCommand = CielTakeoverNavigator.NavigationCommand.HANDLED_IDLE;
         return cachedCommand;
      }

      acceptCompletedPlan(player, currentTargetId);
      advanceSnapshot(player);
      updateProgress(player);
      CielConfig.CielSettings config = CielConfig.settings();
      boolean routeAlmostDone = !route.isEmpty() && route.size() - routeIndex <= 2;
      boolean targetMoved = target != null && plannedTarget.distanceToSqr(target.position()) >= 4.0;
      boolean needsNavigation = serverAdvancing && target != null;
      boolean waitingForTerrainRoute = terrainMode == CielTakeoverNavigator.TerrainMode.WAITING_FOR_ROUTE;
      boolean shouldProbeEscape = waitingForTerrainRoute || target == null || needsNavigation;
      if ((terrainMode == CielTakeoverNavigator.TerrainMode.NONE || waitingForTerrainRoute)
         && shouldProbeEscape
         && player.tickCount >= nextPlanTick
         && (route.isEmpty() || routeAlmostDone || targetMoved || progressTicks >= 10)
         && !requestPlan(player, target, config)) {
         cachedCommand = CielTakeoverNavigator.NavigationCommand.HANDLED_IDLE;
         return cachedCommand;
      }

      if (terrainMode != CielTakeoverNavigator.TerrainMode.NONE) {
         cachedCommand = tickTerrain(player, config);
         return cachedCommand;
      }

      if (player.tickCount < settleUntilTick) {
         cachedCommand = CielTakeoverNavigator.NavigationCommand.idle(settleSneaking);
         return cachedCommand;
      }

      boolean mayFollowRoute = !route.isEmpty() && (needsNavigation || escapeRoute);
      if (mayFollowRoute) {
         CielTakeoverNavigator.NavigationCommand command = followRoute(player, config);
         if (command.handled()) {
            failedPlanTicks = 0;
            cachedCommand = command;
            return command;
         }
      }

      if (lastPlanConfined && route.isEmpty() && !planningActive()) {
         failedPlanTicks++;
         if (player.tickCount >= terrainRetryAfterTick && failedPlanTicks >= Mth.clamp(config.takeoverEscapeDetectionTicks, 5, 200)) {
            beginTerrainEscape(player, config);
            if (terrainMode != CielTakeoverNavigator.TerrainMode.NONE) {
               cachedCommand = tickTerrain(player, config);
               return cachedCommand;
            }
         }
      } else if (!lastPlanConfined) {
         failedPlanTicks = 0;
      }

      cachedCommand = CielTakeoverNavigator.NavigationCommand.UNHANDLED;
      return cachedCommand;
   }

   public static void reset() {
      reset(true);
   }

   public static void reset(boolean notifyServer) {
      if (dimension != null
         || targetId != Integer.MIN_VALUE
         || snapshotBuilder != null
         || inFlight != null
         || terrainMode != CielTakeoverNavigator.TerrainMode.NONE) {
         if (notifyServer && terrainMode == CielTakeoverNavigator.TerrainMode.BREAKING) {
            sendTerrainAction(RequestCielTerrainActionPayload.Action.CANCEL_BREAK, BlockPos.ZERO);
         }

         cancelPlanning();
         activePlayer = null;
         activeLevel = null;
         dimension = null;
         targetId = Integer.MIN_VALUE;
         plannedTarget = Vec3.ZERO;
         route = List.of();
         routeIndex = 0;
         escapeRoute = false;
         lastPlanConfined = false;
         lastBreakSuggestion = null;
         nextPlanTick = 0;
         failedPlanTicks = 0;
         progressAnchor = null;
         progressTicks = 0;
         routeStartedTick = 0;
         settleUntilTick = 0;
         settleSneaking = false;
         terrainRetryAfterTick = 0;
         clearTerrainState();
         lastProcessedTick = Integer.MIN_VALUE;
         cachedCommand = CielTakeoverNavigator.NavigationCommand.UNHANDLED;
      }
   }

   private static void resetSession(LocalPlayer player, ResourceLocation newDimension, int newTargetId, boolean notifyServer) {
      if (notifyServer && terrainMode == CielTakeoverNavigator.TerrainMode.BREAKING) {
         sendTerrainAction(RequestCielTerrainActionPayload.Action.CANCEL_BREAK, BlockPos.ZERO);
      }

      cancelPlanning();
      activePlayer = player;
      activeLevel = player.clientLevel;
      dimension = newDimension;
      targetId = newTargetId;
      route = List.of();
      routeIndex = 0;
      escapeRoute = false;
      lastPlanConfined = false;
      lastBreakSuggestion = null;
      nextPlanTick = 0;
      failedPlanTicks = 0;
      progressAnchor = null;
      progressTicks = 0;
      routeStartedTick = 0;
      settleUntilTick = 0;
      settleSneaking = false;
      terrainRetryAfterTick = 0;
      clearTerrainState();
      lastProcessedTick = Integer.MIN_VALUE;
      cachedCommand = CielTakeoverNavigator.NavigationCommand.UNHANDLED;
   }

   private static void cancelPlanning() {
      generation++;
      if (inFlight != null) {
         inFlight.cancel(true);
      }

      inFlight = null;
      snapshotBuilder = null;
      COMPLETED.set(null);
   }

   private static void retarget(LocalPlayer player, int newTargetId) {
      cancelPlanning();
      targetId = newTargetId;
      plannedTarget = Vec3.ZERO;
      route = List.of();
      routeIndex = 0;
      escapeRoute = false;
      progressAnchor = player.position();
      progressTicks = 0;
      routeStartedTick = 0;
      nextPlanTick = player.tickCount;
      if (terrainMode == CielTakeoverNavigator.TerrainMode.NONE) {
         lastPlanConfined = false;
         lastBreakSuggestion = null;
         failedPlanTicks = 0;
      }

      lastProcessedTick = Integer.MIN_VALUE;
      cachedCommand = CielTakeoverNavigator.NavigationCommand.UNHANDLED;
   }

   private static boolean planningActive() {
      return snapshotBuilder != null || inFlight != null && !inFlight.isDone() || COMPLETED.get() != null;
   }

   private static boolean isPlanningPositionValid(LocalPlayer player, LivingEntity target) {
      try {
         if (!isFiniteEntityState(player)) {
            return false;
         } else {
            ClientLevel level = player.clientLevel;
            int minBuildHeight = level.getMinBuildHeight();
            int maxBuildHeight = level.getMaxBuildHeight();
            AABB playerBox = player.getBoundingBox();
            if (maxBuildHeight <= minBuildHeight || playerBox.maxY <= minBuildHeight || playerBox.minY >= maxBuildHeight) {
               return false;
            } else {
               return !level.isInWorldBounds(feetCell(player)) ? false : target == null || isFiniteEntityState(target) && target.isAlive();
            }
         }
      } catch (RuntimeException exception) {
         return false;
      }
   }

   private static boolean isFiniteEntityState(Entity entity) {
      return entity != null && Double.isFinite(entity.getX()) && Double.isFinite(entity.getY()) && Double.isFinite(entity.getZ())
         ? isFiniteBox(entity.getBoundingBox())
         : false;
   }

   private static boolean isFiniteBox(AABB box) {
      return box != null
         && Double.isFinite(box.minX)
         && Double.isFinite(box.minY)
         && Double.isFinite(box.minZ)
         && Double.isFinite(box.maxX)
         && Double.isFinite(box.maxY)
         && Double.isFinite(box.maxZ)
         && box.minX <= box.maxX
         && box.minY <= box.maxY
         && box.minZ <= box.maxZ;
   }

   private static void suspendPlanning(LocalPlayer player) {
      if (terrainMode == CielTakeoverNavigator.TerrainMode.BREAKING) {
         sendTerrainAction(RequestCielTerrainActionPayload.Action.CANCEL_BREAK, BlockPos.ZERO);
      }

      cancelPlanning();
      route = List.of();
      routeIndex = 0;
      escapeRoute = false;
      lastPlanConfined = false;
      lastBreakSuggestion = null;
      plannedTarget = Vec3.ZERO;
      progressAnchor = null;
      progressTicks = 0;
      failedPlanTicks = 0;
      routeStartedTick = player.tickCount;
      nextPlanTick = player.tickCount + 20;
      terrainRetryAfterTick = player.tickCount + 20;
      clearTerrainState();
   }

   private static boolean requestPlan(LocalPlayer player, LivingEntity target, CielConfig.CielSettings config) {
      if (snapshotBuilder != null) {
         return true;
      }

      if (inFlight != null) {
         if (!inFlight.isDone() || COMPLETED.get() != null) {
            return true;
         }

         inFlight = null;
      }

      int requestedGeneration = ++generation;

      try {
         snapshotBuilder = CielTakeoverNavigator.SnapshotBuilder.create(player, target, config, requestedGeneration);
      } catch (RuntimeException exception) {
         suspendPlanning(player);
         return false;
      }

      plannedTarget = target == null ? player.position() : target.position();
      int interval = target == null ? Mth.clamp(config.takeoverClientIdleProbeTicks, 10, 200) : Mth.clamp(config.takeoverClientPathReplanTicks, 5, 100);
      nextPlanTick = player.tickCount + interval;
      return true;
   }

   private static void advanceSnapshot(LocalPlayer player) {
      if (snapshotBuilder != null) {
         if (!snapshotBuilder.matches(player, targetId, dimension)) {
            snapshotBuilder = null;
         } else {
            int budget = Mth.clamp(CielConfig.settings().takeoverClientSnapshotBudget, 128, 8192);
            if (snapshotBuilder.advance(player, budget)) {
               CielTakeoverNavigator.WorldSnapshot snapshot = snapshotBuilder.finish();
               snapshotBuilder = null;
               inFlight = PATH_EXECUTOR.submit(() -> {
                  CielTakeoverNavigator.PlanResult result = plan(snapshot);
                  if (result != null && !isCancelled(snapshot)) {
                     COMPLETED.set(result);
                  }
               });
            }
         }
      }
   }

   private static void acceptCompletedPlan(LocalPlayer player, int currentTargetId) {
      CielTakeoverNavigator.PlanResult result = COMPLETED.getAndSet(null);
      if (result != null) {
         if (result.generation == generation
            && result.targetId == currentTargetId
            && result.dimension.equals(player.level().dimension().location())
            && !(player.position().distanceToSqr(result.start) > 9.0)) {
            inFlight = null;
            if (!result.complete) {
               nextPlanTick = player.tickCount + 5;
            } else {
               route = result.waypoints;
               routeIndex = 0;
               lastPlanConfined = result.confined;
               lastBreakSuggestion = result.breakSuggestion;
               escapeRoute = currentTargetId == -1 && result.reachedGoal && !route.isEmpty();
               if (currentTargetId == -1 && !escapeRoute) {
                  route = List.of();
               }

               if (currentTargetId != -1 && result.confined && !result.reachedGoal) {
                  route = List.of();
               }

               routeStartedTick = player.tickCount;
               progressAnchor = player.position();
               progressTicks = 0;
               if (terrainMode == CielTakeoverNavigator.TerrainMode.WAITING_FOR_ROUTE) {
                  CielConfig.CielSettings config = CielConfig.settings();
                  if (!route.isEmpty()) {
                     clearTerrainState();
                  } else if (!result.confined) {
                     clearTerrainState();
                     settleUntilTick = player.tickCount + 3;
                     settleSneaking = true;
                     failedPlanTicks = 0;
                     terrainRetryAfterTick = player.tickCount + Mth.clamp(config.takeoverEscapeRetryCooldownTicks, 20, 600);
                  } else {
                     startTerrainAction(player, config);
                  }
               }
            }
         }
      }
   }

   private static CielTakeoverNavigator.NavigationCommand followRoute(LocalPlayer player, CielConfig.CielSettings config) {
      if (escapeRoute && player.tickCount - routeStartedTick > Mth.clamp(config.takeoverEscapeRouteTimeoutTicks, 20, 400)) {
         invalidateRoute(player);
         return CielTakeoverNavigator.NavigationCommand.HANDLED_IDLE;
      }

      double feetY = player.getBoundingBox().minY;

      while (routeIndex < route.size()) {
         CielTakeoverNavigator.Waypoint waypoint = route.get(routeIndex);
         double horizontal = horizontalDistance(player.position(), waypoint.position);
         if (horizontal > 0.3 || Math.abs(feetY - waypoint.position.y) > 0.6) {
            break;
         }

         boolean landingWaypoint = waypoint.kind == CielTakeoverNavigator.MoveKind.JUMP
            || waypoint.kind == CielTakeoverNavigator.MoveKind.FALL
            || routeIndex == route.size() - 1;
         if (landingWaypoint && !player.onGround()) {
            break;
         }

         routeIndex++;
      }

      if (routeIndex >= route.size()) {
         boolean wasEscapeRoute = escapeRoute;
         route = List.of();
         routeIndex = 0;
         escapeRoute = false;
         nextPlanTick = player.tickCount;
         settleUntilTick = player.tickCount + 3;
         settleSneaking = wasEscapeRoute;
         return CielTakeoverNavigator.NavigationCommand.idle(wasEscapeRoute);
      }

      CielTakeoverNavigator.Waypoint waypoint = route.get(routeIndex);
      if (routeSegmentClear(player, waypoint)
         && waypointClear(player, waypoint)
         && (routeIndex + 1 >= route.size() || waypointClear(player, route.get(routeIndex + 1)))) {
         Vec3 delta = waypoint.position.subtract(player.position());
         Vec3 direction = new Vec3(delta.x, 0.0, delta.z);
         if (direction.lengthSqr() < 1.0E-7) {
            direction = Vec3.ZERO;
         } else {
            direction = direction.normalize();
         }

         double horizontal = Math.sqrt(delta.x * delta.x + delta.z * delta.z);
         boolean jumping = waypoint.kind == CielTakeoverNavigator.MoveKind.JUMP && player.onGround() && horizontal <= 1.05;
         return new CielTakeoverNavigator.NavigationCommand(
            true,
            !direction.equals(Vec3.ZERO),
            direction,
            jumping,
            false,
            !escapeRoute && waypoint.kind != CielTakeoverNavigator.MoveKind.JUMP && waypoint.kind != CielTakeoverNavigator.MoveKind.FALL
         );
      } else {
         invalidateRoute(player);
         return CielTakeoverNavigator.NavigationCommand.UNHANDLED;
      }
   }

   private static void invalidateRoute(LocalPlayer player) {
      route = List.of();
      routeIndex = 0;
      escapeRoute = false;
      nextPlanTick = player.tickCount;
      progressTicks = 10;
   }

   private static boolean waypointClear(LocalPlayer player, CielTakeoverNavigator.Waypoint waypoint) {
      double halfWidth = player.getBbWidth() * 0.5 - 1.0E-4;
      AABB standing = new AABB(
         waypoint.position.x - halfWidth,
         waypoint.position.y + 1.0E-4,
         waypoint.position.z - halfWidth,
         waypoint.position.x + halfWidth,
         waypoint.position.y + player.getBbHeight() - 1.0E-4,
         waypoint.position.z + halfWidth
      );
      return player.level().getWorldBorder().isWithinBounds(standing)
         && player.level().noBlockCollision(player, standing)
         && player.level().getEntityCollisions(player, standing).isEmpty();
   }

   private static boolean routeSegmentClear(LocalPlayer player, CielTakeoverNavigator.Waypoint waypoint) {
      double halfWidth = player.getBbWidth() * 0.5 - 1.0E-4;
      double feetY = Math.max(player.getBoundingBox().minY, waypoint.position.y) + 1.0E-4;
      double minX = Math.min(player.getX(), waypoint.position.x) - halfWidth;
      double maxX = Math.max(player.getX(), waypoint.position.x) + halfWidth;
      double minZ = Math.min(player.getZ(), waypoint.position.z) - halfWidth;
      double maxZ = Math.max(player.getZ(), waypoint.position.z) + halfWidth;
      AABB swept = new AABB(minX, feetY, minZ, maxX, feetY + player.getBbHeight() - 1.0E-4, maxZ);
      if (player.level().getWorldBorder().isWithinBounds(swept)
         && player.level().noBlockCollision(player, swept)
         && player.level().getEntityCollisions(player, swept).isEmpty()) {
         double currentFeetY = player.getBoundingBox().minY;
         if (Math.abs(currentFeetY - waypoint.position.y) <= 1.0E-4) {
            return true;
         }

         double verticalX = waypoint.position.y > currentFeetY ? player.getX() : waypoint.position.x;
         double verticalZ = waypoint.position.y > currentFeetY ? player.getZ() : waypoint.position.z;
         AABB vertical = new AABB(
            verticalX - halfWidth,
            Math.min(currentFeetY, waypoint.position.y) + 1.0E-4,
            verticalZ - halfWidth,
            verticalX + halfWidth,
            Math.max(currentFeetY, waypoint.position.y) + player.getBbHeight() - 1.0E-4,
            verticalZ + halfWidth
         );
         return player.level().getWorldBorder().isWithinBounds(vertical)
            && player.level().noBlockCollision(player, vertical)
            && player.level().getEntityCollisions(player, vertical).isEmpty();
      } else {
         return false;
      }
   }

   private static void updateProgress(LocalPlayer player) {
      if (progressAnchor != null && cachedCommand.moving()) {
         double required = Math.max(0.05, CielConfig.settings().takeoverEscapeMinimumProgress);
         if (horizontalDistance(progressAnchor, player.position()) >= required) {
            progressAnchor = player.position();
            progressTicks = 0;
         } else {
            progressTicks++;
         }
      } else {
         progressAnchor = player.position();
         progressTicks = 0;
      }
   }

   private static void beginTerrainEscape(LocalPlayer player, CielConfig.CielSettings config) {
      if (config.takeoverTerrainEscape && player.tickCount >= terrainRetryAfterTick) {
         cancelPlanning();
         startTerrainAction(player, config);
      }
   }

   private static void startTerrainAction(LocalPlayer player, CielConfig.CielSettings config) {
      route = List.of();
      routeIndex = 0;
      escapeRoute = false;
      if (config.takeoverEscapePlaceBlocks && hasPillarBlock(player)) {
         terrainMode = CielTakeoverNavigator.TerrainMode.PILLARING;
         pillarPhase = CielTakeoverNavigator.PillarPhase.CENTERING;
         terrainPosition = feetCell(player);
      } else {
         if (!config.takeoverEscapeBreakBlocks || lastBreakSuggestion == null) {
            failTerrainEscape(player, config);
            return;
         }

         terrainMode = CielTakeoverNavigator.TerrainMode.BREAKING;
         terrainPosition = lastBreakSuggestion;
      }

      nextTerrainActionTick = player.tickCount;
      terrainAttempts = 0;
      terrainTicks = 0;
      pillarStableTicks = 0;
   }

   private static CielTakeoverNavigator.NavigationCommand tickTerrain(LocalPlayer player, CielConfig.CielSettings config) {
      terrainTicks++;
      if (terrainTicks > Mth.clamp(config.takeoverEscapeMaximumTicks, 40, 1200)) {
         if (terrainMode == CielTakeoverNavigator.TerrainMode.BREAKING) {
            sendTerrainAction(RequestCielTerrainActionPayload.Action.CANCEL_BREAK, BlockPos.ZERO);
         }

         failTerrainEscape(player, config);
         return CielTakeoverNavigator.NavigationCommand.HANDLED_IDLE;
      } else {
         return switch (terrainMode) {
            case NONE -> CielTakeoverNavigator.NavigationCommand.UNHANDLED;
            case PILLARING -> tickPillar(player, config);
            case BREAKING -> tickBreaking(player, config);
            case WAITING_FOR_ROUTE -> CielTakeoverNavigator.NavigationCommand.idle(true);
         };
      }
   }

   private static CielTakeoverNavigator.NavigationCommand tickPillar(LocalPlayer player, CielConfig.CielSettings config) {
      if (terrainPosition == null) {
         clearTerrainState();
         return CielTakeoverNavigator.NavigationCommand.UNHANDLED;
      }

      BlockState placed = player.level().getBlockState(terrainPosition);
      if (placed.getCollisionShape(player.level(), terrainPosition).isEmpty() || !(player.getBoundingBox().minY >= terrainPosition.getY() + 0.9)) {
         double centerX = terrainPosition.getX() + 0.5;
         double centerZ = terrainPosition.getZ() + 0.5;
         Vec3 centerDelta = new Vec3(centerX - player.getX(), 0.0, centerZ - player.getZ());
         double tolerance = Mth.clamp(config.takeoverEscapePillarCenterTolerance, 0.05, 0.28);
         if (centerDelta.length() > tolerance) {
            pillarPhase = CielTakeoverNavigator.PillarPhase.CENTERING;
            pillarStableTicks = 0;
            return new CielTakeoverNavigator.NavigationCommand(true, true, centerDelta.normalize(), false, true, false);
         }

         double horizontalSpeed = Math.sqrt(
            player.getDeltaMovement().x * player.getDeltaMovement().x + player.getDeltaMovement().z * player.getDeltaMovement().z
         );
         double motionTolerance = Mth.clamp(config.takeoverEscapePillarMotionTolerance, 0.01, 0.2);
         if (pillarPhase == CielTakeoverNavigator.PillarPhase.CENTERING) {
            if (!player.onGround() || horizontalSpeed > motionTolerance) {
               pillarStableTicks = 0;
               return CielTakeoverNavigator.NavigationCommand.idle(true);
            }

            if (++pillarStableTicks < 2) {
               return CielTakeoverNavigator.NavigationCommand.idle(true);
            }

            pillarPhase = CielTakeoverNavigator.PillarPhase.JUMPING;
         }

         if (pillarPhase == CielTakeoverNavigator.PillarPhase.JUMPING && player.getBoundingBox().minY < terrainPosition.getY() + 1.0) {
            return new CielTakeoverNavigator.NavigationCommand(true, false, Vec3.ZERO, true, true, false);
         }

         pillarPhase = CielTakeoverNavigator.PillarPhase.WAITING_FOR_BLOCK;
         if (player.tickCount >= nextTerrainActionTick) {
            sendTerrainAction(RequestCielTerrainActionPayload.Action.PLACE_PILLAR, terrainPosition);
            terrainAttempts++;
            nextTerrainActionTick = player.tickCount + Mth.clamp(config.takeoverEscapeActionIntervalTicks, 1, 40);
         }

         if (terrainAttempts >= 3 && player.tickCount >= nextTerrainActionTick) {
            if (config.takeoverEscapeBreakBlocks && lastBreakSuggestion != null) {
               terrainMode = CielTakeoverNavigator.TerrainMode.BREAKING;
               terrainPosition = lastBreakSuggestion;
               terrainAttempts = 0;
               terrainTicks = 0;
               nextTerrainActionTick = player.tickCount;
               return tickBreaking(player, config);
            } else {
               failTerrainEscape(player, config);
               return CielTakeoverNavigator.NavigationCommand.HANDLED_IDLE;
            }
         } else {
            if (player.onGround() && player.getBoundingBox().minY < terrainPosition.getY() + 0.9) {
               pillarPhase = CielTakeoverNavigator.PillarPhase.CENTERING;
               pillarStableTicks = 0;
            }

            return CielTakeoverNavigator.NavigationCommand.idle(true);
         }
      } else {
         return player.onGround() ? beginTerrainReplan(player) : CielTakeoverNavigator.NavigationCommand.idle(true);
      }
   }

   private static CielTakeoverNavigator.NavigationCommand tickBreaking(LocalPlayer player, CielConfig.CielSettings config) {
      if (terrainPosition == null) {
         clearTerrainState();
         return CielTakeoverNavigator.NavigationCommand.UNHANDLED;
      }

      BlockState state = player.level().getBlockState(terrainPosition);
      if (state.getCollisionShape(player.level(), terrainPosition).isEmpty()) {
         sendTerrainAction(RequestCielTerrainActionPayload.Action.CANCEL_BREAK, terrainPosition);
         return beginTerrainReplan(player);
      }

      if (player.tickCount >= nextTerrainActionTick) {
         sendTerrainAction(RequestCielTerrainActionPayload.Action.START_BREAK, terrainPosition);
         nextTerrainActionTick = player.tickCount + 20;
      }

      return CielTakeoverNavigator.NavigationCommand.idle(true);
   }

   private static CielTakeoverNavigator.NavigationCommand beginTerrainReplan(LocalPlayer player) {
      cancelPlanning();
      terrainMode = CielTakeoverNavigator.TerrainMode.WAITING_FOR_ROUTE;
      terrainPosition = null;
      nextTerrainActionTick = 0;
      terrainAttempts = 0;
      terrainTicks = 0;
      pillarStableTicks = 0;
      failedPlanTicks = 0;
      nextPlanTick = player.tickCount;
      return CielTakeoverNavigator.NavigationCommand.idle(true);
   }

   private static void failTerrainEscape(LocalPlayer player, CielConfig.CielSettings config) {
      cancelPlanning();
      clearTerrainState();
      failedPlanTicks = 0;
      nextPlanTick = player.tickCount;
      terrainRetryAfterTick = player.tickCount + Mth.clamp(config.takeoverEscapeRetryCooldownTicks, 20, 600);
      settleUntilTick = player.tickCount + 3;
      settleSneaking = true;
   }

   private static boolean hasPillarBlock(LocalPlayer player) {
      Inventory inventory = player.getInventory();

      for (TagKey<Item> priority : CielTakeoverTerrainTags.PILLAR_PRIORITY) {
         for (int slot = 0; slot < 36; slot++) {
            if (isPillarStack(inventory.getItem(slot), priority)) {
               return true;
            }
         }

         if (isPillarStack(inventory.getItem(40), priority)) {
            return true;
         }
      }

      return false;
   }

   private static boolean isPillarStack(ItemStack stack, TagKey<Item> priority) {
      return !stack.isEmpty() && stack.is(priority) && stack.getItem() instanceof BlockItem blockItem && !(blockItem.getBlock() instanceof EntityBlock);
   }

   private static void sendTerrainAction(RequestCielTerrainActionPayload.Action action, BlockPos position) {
      NetworkManager.sendToServer(new RequestCielTerrainActionPayload(action, position));
   }

   private static void clearTerrainState() {
      terrainMode = CielTakeoverNavigator.TerrainMode.NONE;
      pillarPhase = CielTakeoverNavigator.PillarPhase.CENTERING;
      terrainPosition = null;
      nextTerrainActionTick = 0;
      terrainAttempts = 0;
      terrainTicks = 0;
      pillarStableTicks = 0;
   }

   private static BlockPos feetCell(LocalPlayer player) {
      return BlockPos.containing(player.getX(), player.getBoundingBox().minY + 0.01, player.getZ());
   }

   private static CielTakeoverNavigator.PlanResult plan(CielTakeoverNavigator.WorldSnapshot snapshot) {
      if (isCancelled(snapshot)) {
         return null;
      }

      long deadline = System.nanoTime() + snapshot.timeBudgetNanos;
      CielTakeoverNavigator.Graph graph = buildGraph(snapshot, deadline);
      if (graph == null) {
         return isCancelled(snapshot) ? null : CielTakeoverNavigator.PlanResult.incomplete(snapshot);
      }

      if (graph.startIndex < 0) {
         CielTakeoverNavigator.NavNode approximate = new CielTakeoverNavigator.NavNode(
            Mth.floor(snapshot.start.x), Mth.floor(snapshot.start.z), snapshot.start.y
         );
         return new CielTakeoverNavigator.PlanResult(
            snapshot.generation, snapshot.targetId, snapshot.dimension, snapshot.start, true, List.of(), true, false, suggestBreak(snapshot, approximate)
         );
      }

      Boolean confinedResult = isConfined(snapshot, graph, deadline);
      if (confinedResult == null) {
         return isCancelled(snapshot) ? null : CielTakeoverNavigator.PlanResult.incomplete(snapshot);
      }

      boolean confined = confinedResult;
      CielTakeoverNavigator.SearchResult search = search(snapshot, graph, deadline);
      if (isCancelled(snapshot)) {
         return null;
      }

      List<CielTakeoverNavigator.Waypoint> waypoints = reconstruct(snapshot, graph, search);
      BlockPos breakSuggestion = suggestBreak(snapshot, graph.nodes.get(graph.startIndex));
      boolean complete = confined || search.reachedGoal || !search.incomplete || !waypoints.isEmpty();
      return new CielTakeoverNavigator.PlanResult(
         snapshot.generation, snapshot.targetId, snapshot.dimension, snapshot.start, complete, waypoints, confined, search.reachedGoal, breakSuggestion
      );
   }

   private static boolean isCancelled(CielTakeoverNavigator.WorldSnapshot snapshot) {
      return Thread.currentThread().isInterrupted() || snapshot.generation != generation;
   }

   private static CielTakeoverNavigator.Graph buildGraph(CielTakeoverNavigator.WorldSnapshot snapshot, long deadline) {
      List<CielTakeoverNavigator.NavNode> nodes = new ArrayList<>();
      List<Integer>[] columns = new List[snapshot.sizeX * snapshot.sizeZ];

      for (int localX = 0; localX < snapshot.sizeX; localX++) {
         if (isCancelled(snapshot) || System.nanoTime() >= deadline) {
            return null;
         }

         int x = snapshot.minX + localX;

         for (int localZ = 0; localZ < snapshot.sizeZ; localZ++) {
            int z = snapshot.minZ + localZ;
            int columnIndex = localX * snapshot.sizeZ + localZ;
            List<Double> heights = snapshot.surfaceHeights(x, z);
            if (!heights.isEmpty()) {
               List<Integer> column = new ArrayList<>();

               for (double height : heights) {
                  if (snapshot.canStandAt(x + 0.5, height, z + 0.5)) {
                     int nodeIndex = nodes.size();
                     nodes.add(new CielTakeoverNavigator.NavNode(x, z, height));
                     column.add(nodeIndex);
                  }
               }

               if (!column.isEmpty()) {
                  columns[columnIndex] = List.copyOf(column);
               }
            }
         }
      }

      int startIndex = -1;
      double bestDistance = Double.POSITIVE_INFINITY;

      for (int index = 0; index < nodes.size(); index++) {
         CielTakeoverNavigator.NavNode node = nodes.get(index);
         double horizontal = Math.max(Math.abs(node.x + 0.5 - snapshot.start.x), Math.abs(node.z + 0.5 - snapshot.start.z));
         if (!(horizontal > 1.25)) {
            double distance = horizontal * 2.0 + Math.abs(node.y - snapshot.start.y);
            if (distance < bestDistance) {
               bestDistance = distance;
               startIndex = index;
            }
         }
      }

      return new CielTakeoverNavigator.Graph(List.copyOf(nodes), columns, startIndex);
   }

   private static Boolean isConfined(CielTakeoverNavigator.WorldSnapshot snapshot, CielTakeoverNavigator.Graph graph, long deadline) {
      int radius = snapshot.confinedRadius;
      boolean[] visited = new boolean[graph.nodes.size()];
      ArrayDeque<Integer> queue = new ArrayDeque<>();
      visited[graph.startIndex] = true;
      queue.add(graph.startIndex);
      CielTakeoverNavigator.NavNode start = graph.nodes.get(graph.startIndex);
      int visitedCount = 0;

      while (!queue.isEmpty()) {
         if ((visitedCount++ & 63) != 0 || !isCancelled(snapshot) && System.nanoTime() < deadline) {
            int currentIndex = queue.removeFirst();
            CielTakeoverNavigator.NavNode current = graph.nodes.get(currentIndex);
            if (Math.abs(current.x - start.x) < radius && Math.abs(current.z - start.z) < radius) {
               for (int neighbor : neighbors(snapshot, graph, currentIndex)) {
                  CielTakeoverNavigator.NavNode node = graph.nodes.get(neighbor);
                  if (Math.abs(node.x - start.x) <= radius && Math.abs(node.z - start.z) <= radius && !visited[neighbor]) {
                     visited[neighbor] = true;
                     queue.addLast(neighbor);
                  }
               }
               continue;
            }

            return false;
         }

         return null;
      }

      return true;
   }

   private static CielTakeoverNavigator.SearchResult search(CielTakeoverNavigator.WorldSnapshot snapshot, CielTakeoverNavigator.Graph graph, long deadline) {
      int size = graph.nodes.size();
      double[] costs = new double[size];
      Arrays.fill(costs, Double.POSITIVE_INFINITY);
      int[] parents = new int[size];
      Arrays.fill(parents, -1);
      boolean[] closed = new boolean[size];
      PriorityQueue<CielTakeoverNavigator.OpenNode> open = new PriorityQueue<>(Comparator.comparingDouble(CielTakeoverNavigator.OpenNode::score));
      costs[graph.startIndex] = 0.0;
      open.add(new CielTakeoverNavigator.OpenNode(graph.startIndex, heuristic(snapshot, graph.nodes.get(graph.startIndex))));
      int bestIndex = graph.startIndex;
      double bestHeuristic = heuristic(snapshot, graph.nodes.get(bestIndex));
      int visited = 0;

      while (!open.isEmpty() && visited < snapshot.maxVisitedNodes && ((visited & 63) != 0 || !isCancelled(snapshot) && System.nanoTime() < deadline)) {
         CielTakeoverNavigator.OpenNode entry = open.poll();
         int currentIndex = entry.index;
         if (!closed[currentIndex]) {
            closed[currentIndex] = true;
            visited++;
            CielTakeoverNavigator.NavNode current = graph.nodes.get(currentIndex);
            double currentHeuristic = heuristic(snapshot, current);
            if (currentHeuristic < bestHeuristic) {
               bestHeuristic = currentHeuristic;
               bestIndex = currentIndex;
            }

            if (isGoal(snapshot, graph.nodes.get(graph.startIndex), current)) {
               bestIndex = currentIndex;
               return new CielTakeoverNavigator.SearchResult(bestIndex, parents, true, false);
            }

            for (int neighborIndex : neighbors(snapshot, graph, currentIndex)) {
               if (!closed[neighborIndex]) {
                  CielTakeoverNavigator.NavNode neighbor = graph.nodes.get(neighborIndex);
                  double candidate = costs[currentIndex]
                     + movementCost(current, neighbor)
                     + turnPenalty(graph, parents[currentIndex], currentIndex, neighborIndex);
                  if (!(candidate + 1.0E-7 >= costs[neighborIndex])) {
                     costs[neighborIndex] = candidate;
                     parents[neighborIndex] = currentIndex;
                     open.add(new CielTakeoverNavigator.OpenNode(neighborIndex, candidate + heuristic(snapshot, neighbor)));
                  }
               }
            }
         }
      }

      return new CielTakeoverNavigator.SearchResult(bestIndex, parents, false, !open.isEmpty());
   }

   private static int[] neighbors(CielTakeoverNavigator.WorldSnapshot snapshot, CielTakeoverNavigator.Graph graph, int nodeIndex) {
      CielTakeoverNavigator.NavNode from = graph.nodes.get(nodeIndex);
      int[] buffer = new int[32];
      int count = 0;

      for (int offsetIndex = 0; offsetIndex < NEIGHBOR_OFFSETS.length; offsetIndex += 2) {
         int localX = from.x + NEIGHBOR_OFFSETS[offsetIndex] - snapshot.minX;
         int localZ = from.z + NEIGHBOR_OFFSETS[offsetIndex + 1] - snapshot.minZ;
         if (localX >= 0 && localX < snapshot.sizeX && localZ >= 0 && localZ < snapshot.sizeZ) {
            List<Integer> column = graph.columns[localX * snapshot.sizeZ + localZ];
            if (column != null) {
               for (int candidateIndex : column) {
                  CielTakeoverNavigator.NavNode candidate = graph.nodes.get(candidateIndex);
                  double deltaY = candidate.y - from.y;
                  if (!(deltaY > snapshot.maxJump + 1.0E-4) && !(deltaY < -snapshot.maxFall - 1.0E-4) && transitionClear(snapshot, from, candidate)) {
                     if (count == buffer.length) {
                        buffer = Arrays.copyOf(buffer, count * 2);
                     }

                     buffer[count++] = candidateIndex;
                  }
               }
            }
         }
      }

      return Arrays.copyOf(buffer, count);
   }

   private static boolean transitionClear(CielTakeoverNavigator.WorldSnapshot snapshot, CielTakeoverNavigator.NavNode from, CielTakeoverNavigator.NavNode to) {
      double halfWidth = snapshot.playerWidth * 0.5 - 1.0E-4;
      double travelY = Math.max(from.y, to.y) + 1.0E-4;
      double minX = Math.min(from.x + 0.5, to.x + 0.5) - halfWidth;
      double maxX = Math.max(from.x + 0.5, to.x + 0.5) + halfWidth;
      double minZ = Math.min(from.z + 0.5, to.z + 0.5) - halfWidth;
      double maxZ = Math.max(from.z + 0.5, to.z + 0.5) + halfWidth;
      return !snapshot.collides(minX, travelY, minZ, maxX, travelY + snapshot.playerHeight - 1.0E-4, maxZ);
   }

   private static double movementCost(CielTakeoverNavigator.NavNode from, CielTakeoverNavigator.NavNode to) {
      double delta = to.y - from.y;
      double horizontal = from.x != to.x && from.z != to.z ? DIAGONAL_COST : 1.0;
      return horizontal + Math.max(0.0, delta) * 0.45 + Math.max(0.0, -delta) * 0.12;
   }

   private static double turnPenalty(CielTakeoverNavigator.Graph graph, int previousIndex, int currentIndex, int nextIndex) {
      if (previousIndex < 0) {
         return 0.0;
      }

      CielTakeoverNavigator.NavNode previous = graph.nodes.get(previousIndex);
      CielTakeoverNavigator.NavNode current = graph.nodes.get(currentIndex);
      CielTakeoverNavigator.NavNode next = graph.nodes.get(nextIndex);
      int incomingX = Integer.compare(current.x, previous.x);
      int incomingZ = Integer.compare(current.z, previous.z);
      int outgoingX = Integer.compare(next.x, current.x);
      int outgoingZ = Integer.compare(next.z, current.z);
      return incomingX == outgoingX && incomingZ == outgoingZ ? 0.0 : 0.001;
   }

   private static double heuristic(CielTakeoverNavigator.WorldSnapshot snapshot, CielTakeoverNavigator.NavNode node) {
      double dx = node.x + 0.5 - snapshot.goal.x;
      double dz = node.z + 0.5 - snapshot.goal.z;
      double horizontal = Math.sqrt(dx * dx + dz * dz);
      if (snapshot.targetId != -1 && snapshot.targetInsideBounds) {
         horizontal = Math.max(0.0, horizontal - snapshot.stopDistance);
      }

      return horizontal + Math.abs(node.y - snapshot.goal.y) * 0.25;
   }

   private static boolean isGoal(CielTakeoverNavigator.WorldSnapshot snapshot, CielTakeoverNavigator.NavNode start, CielTakeoverNavigator.NavNode node) {
      if (snapshot.targetId == -1) {
         double horizontal = Math.max(Math.abs(node.x - start.x), Math.abs(node.z - start.z));
         return node.y >= start.y + 0.75 && horizontal >= snapshot.confinedRadius;
      } else {
         double dx = node.x + 0.5 - snapshot.goal.x;
         double dz = node.z + 0.5 - snapshot.goal.z;
         double horizontal = Math.sqrt(dx * dx + dz * dz);
         return !snapshot.targetInsideBounds ? horizontal <= 1.0 : horizontal <= snapshot.stopDistance && Math.abs(node.y - snapshot.goal.y) <= 2.0;
      }
   }

   private static List<CielTakeoverNavigator.Waypoint> reconstruct(
      CielTakeoverNavigator.WorldSnapshot snapshot, CielTakeoverNavigator.Graph graph, CielTakeoverNavigator.SearchResult search
   ) {
      if (snapshot.targetId == -1 && !search.reachedGoal) {
         return List.of();
      }

      if (search.destination >= 0 && search.destination != graph.startIndex) {
         List<Integer> reversed = new ArrayList<>();

         int cursor;
         for (cursor = search.destination; cursor >= 0 && cursor != graph.startIndex && reversed.size() < 128; cursor = search.parents[cursor]) {
            reversed.add(cursor);
         }

         if (cursor != graph.startIndex) {
            return List.of();
         }

         List<CielTakeoverNavigator.NavNode> path = new ArrayList<>(reversed.size() + 1);
         path.add(graph.nodes.get(graph.startIndex));

         for (int index = reversed.size() - 1; index >= 0; index--) {
            path.add(graph.nodes.get(reversed.get(index)));
         }

         List<CielTakeoverNavigator.Waypoint> result = new ArrayList<>(path.size() - 1);

         for (int index = 1; index < path.size(); index++) {
            CielTakeoverNavigator.NavNode previous = path.get(index - 1);
            CielTakeoverNavigator.NavNode node = path.get(index);
            CielTakeoverNavigator.MoveKind kind = moveKind(snapshot, previous, node);
            boolean finalNode = index == path.size() - 1;
            boolean straightWalkContinues = !finalNode
               && kind == CielTakeoverNavigator.MoveKind.WALK
               && moveKind(snapshot, node, path.get(index + 1)) == CielTakeoverNavigator.MoveKind.WALK
               && sameCardinalDirection(previous, node, path.get(index + 1));
            if (!straightWalkContinues) {
               result.add(new CielTakeoverNavigator.Waypoint(new Vec3(node.x + 0.5, node.y, node.z + 0.5), kind));
            }
         }

         return List.copyOf(result);
      } else {
         return List.of();
      }
   }

   private static CielTakeoverNavigator.MoveKind moveKind(
      CielTakeoverNavigator.WorldSnapshot snapshot, CielTakeoverNavigator.NavNode from, CielTakeoverNavigator.NavNode to
   ) {
      double deltaY = to.y - from.y;
      if (deltaY > snapshot.maxStep + 1.0E-4) {
         return CielTakeoverNavigator.MoveKind.JUMP;
      } else if (deltaY < -snapshot.maxStep - 1.0E-4) {
         return CielTakeoverNavigator.MoveKind.FALL;
      } else {
         return Math.abs(deltaY) > 1.0E-4 ? CielTakeoverNavigator.MoveKind.STEP : CielTakeoverNavigator.MoveKind.WALK;
      }
   }

   private static boolean sameCardinalDirection(CielTakeoverNavigator.NavNode first, CielTakeoverNavigator.NavNode second, CielTakeoverNavigator.NavNode third) {
      int incomingX = Integer.compare(second.x, first.x);
      int incomingZ = Integer.compare(second.z, first.z);
      return (incomingX == 0 || incomingZ == 0) && incomingX == Integer.compare(third.x, second.x) && incomingZ == Integer.compare(third.z, second.z);
   }

   private static BlockPos suggestBreak(CielTakeoverNavigator.WorldSnapshot snapshot, CielTakeoverNavigator.NavNode start) {
      int feetY = Mth.floor(start.y + 0.01);
      int headY = Mth.floor(start.y + snapshot.playerHeight - 0.01);
      BlockPos overhead = new BlockPos(start.x, headY, start.z);
      if (snapshot.isBreakable(overhead)) {
         return overhead;
      }

      double goalX = snapshot.goal.x - (start.x + 0.5);
      double goalZ = snapshot.goal.z - (start.z + 0.5);
      List<int[]> directions = new ArrayList<>(List.of(new int[]{1, 0}, new int[]{-1, 0}, new int[]{0, 1}, new int[]{0, -1}));
      directions.sort(Comparator.comparingDouble(directionx -> -(directionx[0] * goalX + directionx[1] * goalZ)));

      for (int[] direction : directions) {
         int x = start.x + direction[0];
         int z = start.z + direction[1];

         for (int y = feetY; y <= headY; y++) {
            BlockPos candidate = new BlockPos(x, y, z);
            if (snapshot.isBreakable(candidate)) {
               return candidate;
            }
         }
      }

      return null;
   }

   private static double horizontalDistance(Vec3 first, Vec3 second) {
      double dx = first.x - second.x;
      double dz = first.z - second.z;
      return Math.sqrt(dx * dx + dz * dz);
   }

   private record Graph(List<CielTakeoverNavigator.NavNode> nodes, List<Integer>[] columns, int startIndex) {
   }

   private enum MoveKind {
      WALK,
      STEP,
      JUMP,
      FALL;
   }

   private record NavNode(int x, int z, double y) {
   }

   public record NavigationCommand(boolean handled, boolean moving, Vec3 direction, boolean jumping, boolean sneaking, boolean allowSprinting) {
      public static final CielTakeoverNavigator.NavigationCommand UNHANDLED = new CielTakeoverNavigator.NavigationCommand(
         false, false, Vec3.ZERO, false, false, true
      );
      public static final CielTakeoverNavigator.NavigationCommand HANDLED_IDLE = new CielTakeoverNavigator.NavigationCommand(
         true, false, Vec3.ZERO, false, false, false
      );

      private static CielTakeoverNavigator.NavigationCommand idle(boolean sneaking) {
         return new CielTakeoverNavigator.NavigationCommand(true, false, Vec3.ZERO, false, sneaking, false);
      }
   }

   private record OpenNode(int index, double score) {
   }

   private enum PillarPhase {
      CENTERING,
      JUMPING,
      WAITING_FOR_BLOCK;
   }

   private record PlanResult(
      int generation,
      int targetId,
      ResourceLocation dimension,
      Vec3 start,
      boolean complete,
      List<CielTakeoverNavigator.Waypoint> waypoints,
      boolean confined,
      boolean reachedGoal,
      BlockPos breakSuggestion
   ) {
      private static CielTakeoverNavigator.PlanResult incomplete(CielTakeoverNavigator.WorldSnapshot snapshot) {
         return new CielTakeoverNavigator.PlanResult(
            snapshot.generation, snapshot.targetId, snapshot.dimension, snapshot.start, false, List.of(), false, false, null
         );
      }
   }

   private record SearchResult(int destination, int[] parents, boolean reachedGoal, boolean incomplete) {
   }

   private static final class SnapshotBuilder {
      private final LocalPlayer player;
      private final ClientLevel level;
      private final int generation;
      private final int targetId;
      private final ResourceLocation dimension;
      private final Vec3 start;
      private final Vec3 goal;
      private final boolean targetInsideBounds;
      private final int minX;
      private final int minY;
      private final int minZ;
      private final int sizeX;
      private final int sizeY;
      private final int sizeZ;
      private final byte[] flags;
      private final double[][] boxes;
      private final double[][] entityBoxes;
      private final CollisionContext collisionContext;
      private final double playerWidth;
      private final double playerHeight;
      private final double maxStep;
      private final double maxJump;
      private final double maxFall;
      private final double borderMinX;
      private final double borderMaxX;
      private final double borderMinZ;
      private final double borderMaxZ;
      private final double stopDistance;
      private final int confinedRadius;
      private final int maxVisitedNodes;
      private final long timeBudgetNanos;
      private final double maximumBreakHardness;
      private int cursor;

      private SnapshotBuilder(LocalPlayer player, LivingEntity target, CielConfig.CielSettings config, int generation) {
         ClientLevel level = player.clientLevel;
         int radius = Mth.clamp(config.takeoverEscapeRouteSearchRadius, 3, 16);
         int vertical = Mth.clamp(config.takeoverClientPathVerticalRange, 3, 12);
         BlockPos feet = CielTakeoverNavigator.feetCell(player);
         this.player = player;
         this.level = level;
         this.generation = generation;
         this.targetId = target == null ? -1 : target.getId();
         this.dimension = level.dimension().location();
         this.start = new Vec3(player.getX(), player.getBoundingBox().minY, player.getZ());
         Vec3 requestedGoal;
         if (target == null) {
            Vec3 look = player.getLookAngle();
            requestedGoal = this.start.add(look.x * radius, Math.max(2.0, look.y * radius), look.z * radius);
         } else {
            requestedGoal = target.position();
         }

         this.minX = feet.getX() - radius;
         this.minZ = feet.getZ() - radius;
         int worldMinY = level.getMinBuildHeight();
         int worldMaxY = Math.max(worldMinY, level.getMaxBuildHeight() - 1);
         int boundedFeetY = Mth.clamp(feet.getY(), worldMinY, worldMaxY);
         this.minY = (int)Math.max(worldMinY, (long)boundedFeetY - vertical);
         int maxY = (int)Math.min(worldMaxY, (long)boundedFeetY + vertical);
         this.sizeX = radius * 2 + 1;
         this.sizeZ = radius * 2 + 1;
         this.sizeY = Math.max(1, maxY - this.minY + 1);
         double clampedX = Mth.clamp(requestedGoal.x, this.minX + 0.5, this.minX + this.sizeX - 0.5);
         double clampedZ = Mth.clamp(requestedGoal.z, this.minZ + 0.5, this.minZ + this.sizeZ - 0.5);
         double clampedY = Mth.clamp(requestedGoal.y, this.minY, this.minY + this.sizeY - 1.0);
         this.goal = new Vec3(clampedX, clampedY, clampedZ);
         this.targetInsideBounds = requestedGoal.x >= this.minX
            && requestedGoal.x < this.minX + this.sizeX
            && requestedGoal.z >= this.minZ
            && requestedGoal.z < this.minZ + this.sizeZ
            && requestedGoal.y >= this.minY
            && requestedGoal.y < this.minY + this.sizeY
            && level.getWorldBorder().isWithinBounds(requestedGoal);
         this.flags = new byte[this.sizeX * this.sizeY * this.sizeZ];
         this.boxes = new double[this.flags.length][];
         this.collisionContext = CollisionContext.of(player);
         this.playerWidth = player.getBbWidth();
         this.playerHeight = player.getBbHeight();
         this.maxStep = Mth.clamp(player.maxUpStep(), 0.0, 1.25);
         this.maxJump = Mth.clamp(config.takeoverMaximumAutoJumpHeight, this.maxStep, 1.25);
         this.maxFall = Mth.clamp(config.takeoverClientMaximumDropHeight, 0.0, 8.0);
         this.borderMinX = level.getWorldBorder().getMinX();
         this.borderMaxX = level.getWorldBorder().getMaxX();
         this.borderMinZ = level.getWorldBorder().getMinZ();
         this.borderMaxZ = level.getWorldBorder().getMaxZ();
         this.stopDistance = Math.max(1.0, config.takeoverMeleeStopDistance);
         this.confinedRadius = Mth.clamp(config.takeoverEscapeSearchRadius, 2, radius);
         this.maxVisitedNodes = Mth.clamp(config.takeoverClientPathMaxVisitedNodes, 256, 16384);
         this.timeBudgetNanos = Mth.clamp(config.takeoverClientPathTimeBudgetMillis, 2, 50) * 1000000L;
         this.maximumBreakHardness = Mth.clamp(config.takeoverEscapeMaximumBreakHardness, 0.0, 100.0);
         AABB snapshotBounds = new AABB(this.minX, this.minY, this.minZ, this.minX + this.sizeX, this.minY + this.sizeY, this.minZ + this.sizeZ);
         List<double[]> dynamic = new ArrayList<>();

         for (Entity entity : level.getEntities(
            player, snapshotBounds.inflate(1.0E-7), entityx -> entityx != target && !entityx.isSpectator() && player.canCollideWith(entityx)
         )) {
            dynamic.add(copyBox(entity.getBoundingBox()));
         }

         this.entityBoxes = dynamic.toArray(double[][]::new);
      }

      private static CielTakeoverNavigator.SnapshotBuilder create(LocalPlayer player, LivingEntity target, CielConfig.CielSettings config, int generation) {
         return new CielTakeoverNavigator.SnapshotBuilder(player, target, config, generation);
      }

      private boolean matches(LocalPlayer player, int currentTargetId, ResourceLocation currentDimension) {
         return this.generation == CielTakeoverNavigator.generation
            && this.player == player
            && this.level == player.clientLevel
            && this.targetId == currentTargetId
            && this.dimension.equals(currentDimension)
            && this.dimension.equals(player.level().dimension().location());
      }

      private boolean advance(LocalPlayer player, int budget) {
         if (this.player == player && this.level == player.clientLevel) {
            ClientLevel level = this.level;
            MutableBlockPos mutable = new MutableBlockPos();
            int end = Math.min(this.flags.length, this.cursor + budget);

            while (this.cursor < end) {
               int index = this.cursor++;
               int localX = index % this.sizeX;
               int remainder = index / this.sizeX;
               int localZ = remainder % this.sizeZ;
               int localY = remainder / this.sizeZ;
               int x = this.minX + localX;
               int y = this.minY + localY;
               int z = this.minZ + localZ;
               mutable.set(x, y, z);
               boolean outsideBorder = x + 1.0 <= this.borderMinX || x >= this.borderMaxX || z + 1.0 <= this.borderMinZ || z >= this.borderMaxZ;
               if (level.isInWorldBounds(mutable) && !outsideBorder && level.hasChunkAt(mutable)) {
                  BlockState state = level.getBlockState(mutable);
                  VoxelShape shape = state.getCollisionShape(level, mutable, this.collisionContext);
                  if (!shape.isEmpty()) {
                     this.flags[index] = (byte)(this.flags[index] | 1);
                     List<AABB> localBoxes = shape.toAabbs();
                     double[] copied = new double[localBoxes.size() * 6];
                     int offset = 0;

                     for (AABB box : localBoxes) {
                        copied[offset++] = box.minX + x;
                        copied[offset++] = box.minY + y;
                        copied[offset++] = box.minZ + z;
                        copied[offset++] = box.maxX + x;
                        copied[offset++] = box.maxY + y;
                        copied[offset++] = box.maxZ + z;
                     }

                     this.boxes[index] = copied;
                  }

                  if (!state.getFluidState().isEmpty()) {
                     this.flags[index] = (byte)(this.flags[index] | 2);
                  }

                  if (!state.isAir() && !(state.getBlock() instanceof EntityBlock)) {
                     float hardness = state.getDestroySpeed(level, mutable);
                     if (hardness >= 0.0F && hardness <= this.maximumBreakHardness) {
                        this.flags[index] = (byte)(this.flags[index] | 4);
                     }
                  }
               } else {
                  this.flags[index] = 9;
                  this.boxes[index] = new double[]{x, y, z, x + 1.0, y + 1.0, z + 1.0};
               }
            }

            return this.cursor >= this.flags.length;
         } else {
            return false;
         }
      }

      private CielTakeoverNavigator.WorldSnapshot finish() {
         return new CielTakeoverNavigator.WorldSnapshot(
            this.generation,
            this.targetId,
            this.dimension,
            this.start,
            this.goal,
            this.targetInsideBounds,
            this.minX,
            this.minY,
            this.minZ,
            this.sizeX,
            this.sizeY,
            this.sizeZ,
            this.flags,
            this.boxes,
            this.entityBoxes,
            this.playerWidth,
            this.playerHeight,
            this.maxStep,
            this.maxJump,
            this.maxFall,
            this.borderMinX,
            this.borderMaxX,
            this.borderMinZ,
            this.borderMaxZ,
            this.stopDistance,
            this.confinedRadius,
            this.maxVisitedNodes,
            this.timeBudgetNanos
         );
      }

      private static double[] copyBox(AABB box) {
         return new double[]{box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ};
      }
   }

   private enum TerrainMode {
      NONE,
      PILLARING,
      BREAKING,
      WAITING_FOR_ROUTE;
   }

   private record Waypoint(Vec3 position, CielTakeoverNavigator.MoveKind kind) {
   }

   private static final class WorldSnapshot {
      private final int generation;
      private final int targetId;
      private final ResourceLocation dimension;
      private final Vec3 start;
      private final Vec3 goal;
      private final boolean targetInsideBounds;
      private final int minX;
      private final int minY;
      private final int minZ;
      private final int sizeX;
      private final int sizeY;
      private final int sizeZ;
      private final byte[] flags;
      private final double[][] boxes;
      private final double[][] entityBoxes;
      private final double playerWidth;
      private final double playerHeight;
      private final double maxStep;
      private final double maxJump;
      private final double maxFall;
      private final double borderMinX;
      private final double borderMaxX;
      private final double borderMinZ;
      private final double borderMaxZ;
      private final double stopDistance;
      private final int confinedRadius;
      private final int maxVisitedNodes;
      private final long timeBudgetNanos;

      private WorldSnapshot(
         int generation,
         int targetId,
         ResourceLocation dimension,
         Vec3 start,
         Vec3 goal,
         boolean targetInsideBounds,
         int minX,
         int minY,
         int minZ,
         int sizeX,
         int sizeY,
         int sizeZ,
         byte[] flags,
         double[][] boxes,
         double[][] entityBoxes,
         double playerWidth,
         double playerHeight,
         double maxStep,
         double maxJump,
         double maxFall,
         double borderMinX,
         double borderMaxX,
         double borderMinZ,
         double borderMaxZ,
         double stopDistance,
         int confinedRadius,
         int maxVisitedNodes,
         long timeBudgetNanos
      ) {
         this.generation = generation;
         this.targetId = targetId;
         this.dimension = dimension;
         this.start = start;
         this.goal = goal;
         this.targetInsideBounds = targetInsideBounds;
         this.minX = minX;
         this.minY = minY;
         this.minZ = minZ;
         this.sizeX = sizeX;
         this.sizeY = sizeY;
         this.sizeZ = sizeZ;
         this.flags = flags;
         this.boxes = boxes;
         this.entityBoxes = entityBoxes;
         this.playerWidth = playerWidth;
         this.playerHeight = playerHeight;
         this.maxStep = maxStep;
         this.maxJump = maxJump;
         this.maxFall = maxFall;
         this.borderMinX = borderMinX;
         this.borderMaxX = borderMaxX;
         this.borderMinZ = borderMinZ;
         this.borderMaxZ = borderMaxZ;
         this.stopDistance = stopDistance;
         this.confinedRadius = confinedRadius;
         this.maxVisitedNodes = maxVisitedNodes;
         this.timeBudgetNanos = timeBudgetNanos;
      }

      private List<Double> surfaceHeights(int x, int z) {
         List<Double> heights = new ArrayList<>();
         double centerX = x + 0.5;
         double centerZ = z + 0.5;

         for (int y = this.minY; y < this.minY + this.sizeY; y++) {
            double[] cell = this.cellBoxes(x, y, z);
            if (cell != null) {
               for (int offset = 0; offset < cell.length; offset += 6) {
                  if (!(cell[offset] - 1.0E-4 > centerX)
                     && !(cell[offset + 3] + 1.0E-4 < centerX)
                     && !(cell[offset + 2] - 1.0E-4 > centerZ)
                     && !(cell[offset + 5] + 1.0E-4 < centerZ)) {
                     double height = cell[offset + 4];
                     boolean duplicate = heights.stream().anyMatch(existing -> Math.abs(existing - height) < 0.001);
                     if (!duplicate) {
                        heights.add(height);
                     }
                  }
               }
            }
         }

         heights.sort(Double::compareTo);
         return heights;
      }

      private boolean canStandAt(double centerX, double feetY, double centerZ) {
         double halfWidth = this.playerWidth * 0.5 - 1.0E-4;
         if (this.collides(
            centerX - halfWidth, feetY + 1.0E-4, centerZ - halfWidth, centerX + halfWidth, feetY + this.playerHeight - 1.0E-4, centerZ + halfWidth
         )) {
            return false;
         } else {
            return !this.collides(centerX - halfWidth, feetY - 0.08, centerZ - halfWidth, centerX + halfWidth, feetY + 1.0E-4, centerZ + halfWidth)
               ? false
               : !this.containsFluid(
                  centerX - halfWidth, feetY + 1.0E-4, centerZ - halfWidth, centerX + halfWidth, feetY + this.playerHeight - 1.0E-4, centerZ + halfWidth
               );
         }
      }

      private boolean collides(double minBoxX, double minBoxY, double minBoxZ, double maxBoxX, double maxBoxY, double maxBoxZ) {
         if (!(minBoxX < this.borderMinX) && !(maxBoxX > this.borderMaxX) && !(minBoxZ < this.borderMinZ) && !(maxBoxZ > this.borderMaxZ)) {
            int fromX = Mth.floor(minBoxX + 1.0E-4);
            int toX = Mth.floor(maxBoxX - 1.0E-4);
            int fromY = Mth.floor(minBoxY + 1.0E-4);
            int toY = Mth.floor(maxBoxY - 1.0E-4);
            int fromZ = Mth.floor(minBoxZ + 1.0E-4);
            int toZ = Mth.floor(maxBoxZ - 1.0E-4);

            for (int x = fromX; x <= toX; x++) {
               for (int y = fromY; y <= toY; y++) {
                  for (int z = fromZ; z <= toZ; z++) {
                     if (!this.inBounds(x, y, z)) {
                        return true;
                     }

                     double[] cell = this.cellBoxes(x, y, z);
                     if (cell != null) {
                        for (int offset = 0; offset < cell.length; offset += 6) {
                           if (intersects(cell, offset, minBoxX, minBoxY, minBoxZ, maxBoxX, maxBoxY, maxBoxZ)) {
                              return true;
                           }
                        }
                     }
                  }
               }
            }

            for (double[] entity : this.entityBoxes) {
               if (intersects(entity, 0, minBoxX, minBoxY, minBoxZ, maxBoxX, maxBoxY, maxBoxZ)) {
                  return true;
               }
            }

            return false;
         } else {
            return true;
         }
      }

      private boolean containsFluid(double minBoxX, double minBoxY, double minBoxZ, double maxBoxX, double maxBoxY, double maxBoxZ) {
         for (int x = Mth.floor(minBoxX); x <= Mth.floor(maxBoxX - 1.0E-4); x++) {
            for (int y = Mth.floor(minBoxY); y <= Mth.floor(maxBoxY - 1.0E-4); y++) {
               for (int z = Mth.floor(minBoxZ); z <= Mth.floor(maxBoxZ - 1.0E-4); z++) {
                  int index = this.index(x, y, z);
                  if (index < 0 || (this.flags[index] & 2) != 0) {
                     return true;
                  }
               }
            }
         }

         return false;
      }

      private boolean isBreakable(BlockPos position) {
         int index = this.index(position.getX(), position.getY(), position.getZ());
         return index >= 0 && (this.flags[index] & 13) == 5;
      }

      private double[] cellBoxes(int x, int y, int z) {
         int index = this.index(x, y, z);
         return index < 0 ? null : this.boxes[index];
      }

      private int index(int x, int y, int z) {
         int localX = x - this.minX;
         int localY = y - this.minY;
         int localZ = z - this.minZ;
         return localX >= 0 && localX < this.sizeX && localY >= 0 && localY < this.sizeY && localZ >= 0 && localZ < this.sizeZ
            ? (localY * this.sizeZ + localZ) * this.sizeX + localX
            : -1;
      }

      private boolean inBounds(int x, int y, int z) {
         return this.index(x, y, z) >= 0;
      }

      private static boolean intersects(double[] box, int offset, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
         return box[offset + 3] > minX + 1.0E-4
            && box[offset] < maxX - 1.0E-4
            && box[offset + 4] > minY + 1.0E-4
            && box[offset + 1] < maxY - 1.0E-4
            && box[offset + 5] > minZ + 1.0E-4
            && box[offset + 2] < maxZ - 1.0E-4;
      }
   }
}
