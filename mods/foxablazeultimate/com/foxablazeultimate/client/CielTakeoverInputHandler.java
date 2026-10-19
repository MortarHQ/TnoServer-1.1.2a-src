package com.foxablazeultimate.client;

import com.foxablazeultimate.ability.skill.ciel.CielEmergencyController;
import com.foxablazeultimate.ability.skill.yogsothoth.YogTimeStopManager;
import com.foxablazeultimate.client.ciel.CielTakeoverNavigator;
import com.foxablazeultimate.compat.shouldersurfing.CielShoulderSurfingBridge;
import com.foxablazeultimate.config.CielConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.CalculatePlayerTurnEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.Clone;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;
import net.neoforged.neoforge.client.event.InputEvent.InteractionKeyMappingTriggered;
import net.neoforged.neoforge.client.event.RenderFrameEvent.Pre;

@EventBusSubscriber(modid = "foxablazeultimate", value = Dist.CLIENT)
public final class CielTakeoverInputHandler {
   private static final int MAX_PROBE_SAMPLES = 12;
   private static final double DIRECTION_EPSILON = 1.0E-8;
   private static final double ROUTE_EPSILON = 1.0E-4;
   private static final CielTakeoverInputHandler.AvoidanceState AVOIDANCE = new CielTakeoverInputHandler.AvoidanceState();

   private CielTakeoverInputHandler() {
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void beforeMovementInput(MovementInputUpdateEvent event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (event.getEntity() instanceof LocalPlayer player && player == minecraft.player) {
         if (!CielEmergencyController.isClientTakeover(player)) {
            resetAvoidance();
            CielTakeoverNavigator.reset();
         } else {
            clearMovementInput(event.getInput());
            player.setShiftKeyDown(false);
            player.setSprinting(false);
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void afterMovementInput(MovementInputUpdateEvent event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (event.getEntity() instanceof LocalPlayer player && player == minecraft.player && CielEmergencyController.isClientTakeover(player)) {
         Input input = event.getInput();
         clearMovementInput(input);
         LivingEntity target = getTakeoverTarget(minecraft, CielEmergencyController.getClientTargetId(player));
         boolean serverAdvancing = CielEmergencyController.shouldClientAdvance(player) && target != null;
         if (target != null) {
            smoothPlayerAim(player, calculateAim(player.getEyePosition(), target.getEyePosition(), player.getYRot()));
         }

         CielTakeoverNavigator.NavigationCommand navigation = CielTakeoverNavigator.tick(player, target, serverAdvancing);
         CielTakeoverInputHandler.MovementDecision movement = null;
         boolean advancing;
         if (navigation.handled()) {
            resetAvoidance();
            advancing = navigation.moving();
            if (advancing) {
               movement = new CielTakeoverInputHandler.MovementDecision(navigation.direction(), navigation.jumping());
            }
         } else if (serverAdvancing) {
            advancing = true;
            movement = chooseMovement(player, target);
         } else {
            advancing = false;
            resetAvoidance();
         }

         if (advancing && movement != null) {
            applyWorldDirectionInput(player, movement.direction(), input);
         }

         input.jumping = navigation.handled() ? navigation.jumping() : advancing && shouldAutoJump(player, target, movement);
         input.shiftKeyDown = navigation.handled() && navigation.sneaking();
         player.setShiftKeyDown(input.shiftKeyDown);
         player.setSprinting(advancing && !input.shiftKeyDown && navigation.allowSprinting() && CielEmergencyController.shouldClientSprint(player));
      }
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void beforeRenderFrame(Pre event) {
      Minecraft minecraft = Minecraft.getInstance();
      LocalPlayer player = minecraft.player;
      if (player == null || !CielEmergencyController.isClientTakeover(player)) {
         resetAvoidance();
         if (player == null) {
            CielTakeoverNavigator.reset(false);
         }
      } else if (!minecraft.isPaused()) {
         LivingEntity target = getTakeoverTarget(minecraft, CielEmergencyController.getClientTargetId(player));
         if (target != null) {
            float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(true);
            CielTakeoverInputHandler.Aim aim = calculateAim(player.getEyePosition(partialTick), target.getEyePosition(partialTick), player.getYRot());
            CielConfig.CielSettings config = CielConfig.settings();
            float frameDeltaTicks = Mth.clamp(event.getPartialTick().getRealtimeDeltaTicks(), 0.0F, 2.0F);
            CielShoulderSurfingBridge.smoothCameraAim(
               aim.yaw(),
               aim.pitch(),
               positive(config.takeoverAimYawDegreesPerTick) * frameDeltaTicks,
               positive(config.takeoverAimPitchDegreesPerTick) * frameDeltaTicks
            );
         }
      }
   }

   @SubscribeEvent
   public static void onInteractionInput(InteractionKeyMappingTriggered event) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && CielEmergencyController.isClientTakeover(player)) {
         event.setSwingHand(false);
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onPlayerTurn(CalculatePlayerTurnEvent event) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && CielEmergencyController.isClientTakeover(player)) {
         event.setMouseSensitivity(-0.3333333333333333);
         event.setCinematicCameraEnabled(false);
      }
   }

   @SubscribeEvent
   public static void onClientLogout(LoggingOut event) {
      resetAvoidance();
      CielTakeoverNavigator.reset(false);
      YogTimeStopManager.setClientState(false, null);
   }

   @SubscribeEvent
   public static void onClientPlayerClone(Clone event) {
      resetAvoidance();
      CielTakeoverNavigator.reset(false);
      YogTimeStopManager.setClientState(false, null);
   }

   private static LivingEntity getTakeoverTarget(Minecraft minecraft, int targetId) {
      if (minecraft.level != null && targetId >= 0) {
         return minecraft.level.getEntity(targetId) instanceof LivingEntity living && living.isAlive() && hasFiniteEntityState(living) ? living : null;
      } else {
         return null;
      }
   }

   private static boolean hasFiniteEntityState(Entity entity) {
      try {
         if (Double.isFinite(entity.getX()) && Double.isFinite(entity.getY()) && Double.isFinite(entity.getZ())) {
            AABB box = entity.getBoundingBox();
            return Double.isFinite(box.minX)
               && Double.isFinite(box.minY)
               && Double.isFinite(box.minZ)
               && Double.isFinite(box.maxX)
               && Double.isFinite(box.maxY)
               && Double.isFinite(box.maxZ)
               && box.minX <= box.maxX
               && box.minY <= box.maxY
               && box.minZ <= box.maxZ;
         } else {
            return false;
         }
      } catch (RuntimeException exception) {
         return false;
      }
   }

   private static void clearMovementInput(Input input) {
      input.leftImpulse = 0.0F;
      input.forwardImpulse = 0.0F;
      input.up = false;
      input.down = false;
      input.left = false;
      input.right = false;
      input.jumping = false;
      input.shiftKeyDown = false;
   }

   private static void applyWorldDirectionInput(LocalPlayer player, Vec3 direction, Input input) {
      double horizontalDistance = Math.sqrt(direction.x * direction.x + direction.z * direction.z);
      if (!(horizontalDistance < 1.0E-6)) {
         double worldX = direction.x / horizontalDistance;
         double worldZ = direction.z / horizontalDistance;
         float yawRadians = player.getYRot() * (float) (Math.PI / 180.0);
         float sin = Mth.sin(yawRadians);
         float cos = Mth.cos(yawRadians);
         input.leftImpulse = (float)(worldX * cos + worldZ * sin);
         input.forwardImpulse = (float)(-worldX * sin + worldZ * cos);
         input.left = input.leftImpulse > 0.01F;
         input.right = input.leftImpulse < -0.01F;
         input.up = input.forwardImpulse > 0.01F;
         input.down = input.forwardImpulse < -0.01F;
      }
   }

   private static void smoothPlayerAim(LocalPlayer player, CielTakeoverInputHandler.Aim aim) {
      CielConfig.CielSettings config = CielConfig.settings();
      float yaw = Mth.approachDegrees(player.getYRot(), aim.yaw(), positive(config.takeoverAimYawDegreesPerTick));
      float pitch = Mth.approach(player.getXRot(), aim.pitch(), positive(config.takeoverAimPitchDegreesPerTick));
      player.setYRot(yaw);
      player.setXRot(pitch);
      player.setYHeadRot(yaw);
   }

   private static CielTakeoverInputHandler.Aim calculateAim(Vec3 viewerEye, Vec3 targetEye, float fallbackYaw) {
      Vec3 offset = targetEye.subtract(viewerEye);
      double horizontalDistance = Math.sqrt(offset.x * offset.x + offset.z * offset.z);
      if (horizontalDistance < 1.0E-8 && Math.abs(offset.y) < 1.0E-8) {
         return new CielTakeoverInputHandler.Aim(fallbackYaw, 0.0F);
      }

      float yaw = horizontalDistance < 1.0E-8 ? fallbackYaw : (float)(Math.toDegrees(Math.atan2(offset.z, offset.x)) - 90.0);
      float pitch = (float)(-Math.toDegrees(Math.atan2(offset.y, horizontalDistance)));
      return new CielTakeoverInputHandler.Aim(yaw, Mth.clamp(pitch, -90.0F, 90.0F));
   }

   private static float positive(double value) {
      return (float)Math.max(0.0, value);
   }

   private static boolean shouldAutoJump(LocalPlayer player, LivingEntity target, CielTakeoverInputHandler.MovementDecision movement) {
      if (target == null) {
         return false;
      } else {
         return player.isInWater() && target.getEyeY() > player.getEyeY() ? true : movement != null && movement.jump() && player.onGround();
      }
   }

   private static CielTakeoverInputHandler.MovementDecision chooseMovement(LocalPlayer player, LivingEntity target) {
      Vec3 direct = horizontalDirection(player.position(), target.position());
      if (direct.lengthSqr() < 1.0E-8) {
         resetAvoidance();
         return new CielTakeoverInputHandler.MovementDecision(Vec3.ZERO, false);
      }

      CielConfig.CielSettings config = CielConfig.settings();
      if (!config.takeoverObstacleAvoidance) {
         resetAvoidance();
         return new CielTakeoverInputHandler.MovementDecision(direct, false);
      }

      if (AVOIDANCE.targetId != target.getId()) {
         int initialSide = ((player.getId() ^ target.getId()) & 1) == 0 ? 1 : -1;
         AVOIDANCE.startTarget(target.getId(), initialSide, player.position());
      }

      boolean forcedRepath = AVOIDANCE.tick(player, config);
      double horizontalDistance = Math.sqrt(player.distanceToSqr(target.getX(), player.getY(), target.getZ()));
      double lookAhead = Mth.clamp(config.takeoverAvoidanceLookAhead, 0.5, 8.0);
      double remaining = horizontalDistance - Math.max(0.0, config.takeoverMeleeStopDistance);
      double probeDistance = Math.min(lookAhead, Math.max(0.5, remaining));
      int repathTicks = Mth.clamp(config.takeoverAvoidanceRepathTicks, 1, 40);
      if (AVOIDANCE.detouring && AVOIDANCE.holdTicks <= 0) {
         AVOIDANCE.repathCooldown = 0;
      }

      if (!forcedRepath && AVOIDANCE.repathCooldown > 0) {
         Vec3 current = AVOIDANCE.detouring ? AVOIDANCE.direction : direct;
         if (AVOIDANCE.jump && !player.onGround()) {
            return new CielTakeoverInputHandler.MovementDecision(current, true);
         }

         CielTakeoverInputHandler.RouteOption immediate = assessDirection(player, current, Math.min(0.8, probeDistance), 0.0, config);
         if (immediate.fullyClear()) {
            AVOIDANCE.jump = immediate.jump();
            return new CielTakeoverInputHandler.MovementDecision(current, immediate.jump());
         }

         AVOIDANCE.repathCooldown = 0;
      }

      CielTakeoverInputHandler.RouteOption directOption = assessDirection(player, direct, probeDistance, 0.0, config);
      if (directOption.fullyClear()) {
         if (AVOIDANCE.detouring && AVOIDANCE.holdTicks > 0) {
            CielTakeoverInputHandler.RouteOption held = assessDirection(player, AVOIDANCE.direction, Math.min(1.25, probeDistance), 0.0, config);
            if (held.fullyClear()) {
               AVOIDANCE.repathCooldown = repathTicks;
               AVOIDANCE.jump = held.jump();
               return new CielTakeoverInputHandler.MovementDecision(AVOIDANCE.direction, held.jump());
            }
         }

         AVOIDANCE.useDirect(directOption.jump(), repathTicks, player.position());
         return new CielTakeoverInputHandler.MovementDecision(direct, directOption.jump());
      } else {
         double narrow = Mth.clamp(Math.abs(config.takeoverAvoidanceNarrowAngleDegrees), 5.0, 85.0);
         double wide = Mth.clamp(Math.max(narrow, Math.abs(config.takeoverAvoidanceWideAngleDegrees)), narrow, 95.0);
         double escape = Mth.clamp(Math.max(wide, Math.abs(config.takeoverAvoidanceEscapeAngleDegrees)), wide, 135.0);
         int preferredSide = AVOIDANCE.preferredSide;
         double[] angles = new double[]{
            preferredSide * narrow, preferredSide * wide, preferredSide * escape, -preferredSide * narrow, -preferredSide * wide, -preferredSide * escape
         };
         CielTakeoverInputHandler.RouteOption best = null;
         double bestScore = Double.NEGATIVE_INFINITY;

         for (double angle : angles) {
            int candidateSide = angle >= 0.0 ? 1 : -1;
            if (!forcedRepath || candidateSide == preferredSide) {
               Vec3 candidate = rotateHorizontal(direct, angle);
               CielTakeoverInputHandler.RouteOption option = assessDirection(player, candidate, probeDistance, angle, config);
               double score = scoreRoute(option, direct, probeDistance, preferredSide);
               if (score > bestScore) {
                  best = option;
                  bestScore = score;
               }
            }
         }

         if (best != null && !best.fullyClear() && best.clearDistance() < 0.1) {
            return new CielTakeoverInputHandler.MovementDecision(Vec3.ZERO, false);
         }

         if (best == null) {
            double fallbackAngle = preferredSide * escape;
            best = new CielTakeoverInputHandler.RouteOption(rotateHorizontal(direct, fallbackAngle), false, 0.0, false, fallbackAngle, true);
         }

         int selectedSide = best.angleDegrees() >= 0.0 ? 1 : -1;
         int configuredHold = Mth.clamp(config.takeoverAvoidanceHoldTicks, 0, 80);
         int holdTicks = best.fullyClear() ? configuredHold : Math.min(configuredHold, Math.max(2, repathTicks));
         AVOIDANCE.useDetour(best.direction(), best.jump(), selectedSide, holdTicks, repathTicks, player.position());
         return new CielTakeoverInputHandler.MovementDecision(best.direction(), best.jump());
      }
   }

   private static CielTakeoverInputHandler.RouteOption assessDirection(
      LocalPlayer player, Vec3 direction, double distance, double angleDegrees, CielConfig.CielSettings config
   ) {
      CielTakeoverInputHandler.RouteProbe probe = probeDirection(player, direction, distance, config);
      if (probe.fullyClear()) {
         return new CielTakeoverInputHandler.RouteOption(direction, false, distance, true, angleDegrees, probe.tight());
      }

      if (probe.blockObstacle() && !probe.entityObstacle()) {
         double stepHeight = Math.max(0.0, player.maxUpStep());
         if (stepHeight > 1.0E-4 && routeClearAtHeight(player, direction, distance, stepHeight, config)) {
            return new CielTakeoverInputHandler.RouteOption(direction, false, distance, true, angleDegrees, probe.tight());
         }

         double jumpHeight = Mth.clamp(config.takeoverMaximumAutoJumpHeight, 0.0, 2.0);
         if (player.onGround() && jumpHeight > stepHeight + 1.0E-4 && routeClearAtHeight(player, direction, distance, jumpHeight, config)) {
            return new CielTakeoverInputHandler.RouteOption(direction, true, distance, true, angleDegrees, probe.tight());
         }
      }

      return new CielTakeoverInputHandler.RouteOption(direction, false, probe.clearDistance(), false, angleDegrees, probe.tight());
   }

   private static CielTakeoverInputHandler.RouteProbe probeDirection(LocalPlayer player, Vec3 direction, double distance, CielConfig.CielSettings config) {
      int samples = probeSamples(config);
      double clearance = Mth.clamp(config.takeoverAvoidanceClearance, 0.0, 0.75);
      AABB base = player.getBoundingBox();
      double previous = 0.0;
      boolean tight = false;

      for (int sample = 1; sample <= samples; sample++) {
         double next = distance * sample / samples;
         Vec3 startOffset = direction.scale(previous);
         Vec3 segmentOffset = direction.scale(next - previous);
         AABB exact = base.move(startOffset).expandTowards(segmentOffset);
         AABB preferred = clearance > 1.0E-4 ? exact.inflate(clearance, 0.0, clearance) : exact;
         if (player.level().getWorldBorder().isWithinBounds(preferred) && player.level().noCollision(player, preferred)) {
            previous = next;
         } else {
            if (!(clearance > 1.0E-4) || !player.level().getWorldBorder().isWithinBounds(exact) || !player.level().noCollision(player, exact)) {
               boolean blockObstacle = !player.level().noBlockCollision(player, exact);
               boolean entityObstacle = !player.level().getEntityCollisions(player, exact).isEmpty();
               return new CielTakeoverInputHandler.RouteProbe(previous, false, blockObstacle, entityObstacle, tight);
            }

            tight = true;
            previous = next;
         }
      }

      return new CielTakeoverInputHandler.RouteProbe(distance, true, false, false, tight);
   }

   private static boolean routeClearAtHeight(LocalPlayer player, Vec3 direction, double distance, double height, CielConfig.CielSettings config) {
      int samples = probeSamples(config);
      AABB base = player.getBoundingBox().move(0.0, height, 0.0);
      double previous = 0.0;

      for (int sample = 1; sample <= samples; sample++) {
         double next = distance * sample / samples;
         AABB segment = base.move(direction.scale(previous)).expandTowards(direction.scale(next - previous));
         if (!player.level().getWorldBorder().isWithinBounds(segment) || !player.level().noCollision(player, segment)) {
            return false;
         }

         previous = next;
      }

      return true;
   }

   private static int probeSamples(CielConfig.CielSettings config) {
      return Mth.clamp(config.takeoverAvoidanceProbeSamples, 1, 12);
   }

   private static double scoreRoute(CielTakeoverInputHandler.RouteOption option, Vec3 direct, double probeDistance, int preferredSide) {
      double score = option.clearDistance() * 4.0;
      if (option.fullyClear()) {
         score += probeDistance * 6.0;
      }

      score += direct.dot(option.direction()) * probeDistance * 0.75;
      if ((option.angleDegrees() >= 0.0 ? 1 : -1) == preferredSide) {
         score += 0.35;
      }

      score -= Math.abs(option.angleDegrees()) * 0.002;
      if (option.tight()) {
         score -= 0.2;
      }

      if (option.jump()) {
         score -= 0.1;
      }

      return score;
   }

   private static Vec3 horizontalDirection(Vec3 from, Vec3 to) {
      double x = to.x - from.x;
      double z = to.z - from.z;
      double length = Math.sqrt(x * x + z * z);
      return length < 1.0E-8 ? Vec3.ZERO : new Vec3(x / length, 0.0, z / length);
   }

   private static Vec3 rotateHorizontal(Vec3 direction, double degrees) {
      double radians = Math.toRadians(degrees);
      double sin = Math.sin(radians);
      double cos = Math.cos(radians);
      return new Vec3(direction.x * cos - direction.z * sin, 0.0, direction.x * sin + direction.z * cos);
   }

   private static void resetAvoidance() {
      AVOIDANCE.reset();
   }

   private record Aim(float yaw, float pitch) {
   }

   private static final class AvoidanceState {
      private int targetId = -1;
      private int preferredSide = 1;
      private Vec3 direction = Vec3.ZERO;
      private boolean detouring;
      private boolean jump;
      private int holdTicks;
      private int repathCooldown;
      private int progressTicks;
      private double progressX;
      private double progressZ;

      private void startTarget(int newTargetId, int side, Vec3 position) {
         this.reset();
         this.targetId = newTargetId;
         this.preferredSide = side;
         this.progressX = position.x;
         this.progressZ = position.z;
      }

      private boolean tick(LocalPlayer player, CielConfig.CielSettings config) {
         if (this.repathCooldown > 0) {
            this.repathCooldown--;
         }

         if (this.holdTicks > 0) {
            this.holdTicks--;
         }

         if (!this.detouring) {
            this.progressTicks = 0;
            this.progressX = player.getX();
            this.progressZ = player.getZ();
            return false;
         }

         int interval = Mth.clamp(config.takeoverAvoidanceStuckCheckTicks, 2, 80);
         if (++this.progressTicks < interval) {
            return false;
         }

         double dx = player.getX() - this.progressX;
         double dz = player.getZ() - this.progressZ;
         double required = Mth.clamp(config.takeoverAvoidanceStuckMinimumDistance, 0.05, 2.0);
         this.progressTicks = 0;
         this.progressX = player.getX();
         this.progressZ = player.getZ();
         if (dx * dx + dz * dz >= required * required) {
            return false;
         }

         this.preferredSide = -this.preferredSide;
         this.direction = Vec3.ZERO;
         this.detouring = false;
         this.jump = false;
         this.holdTicks = 0;
         this.repathCooldown = 0;
         return true;
      }

      private void useDirect(boolean shouldJump, int cooldown, Vec3 position) {
         this.direction = Vec3.ZERO;
         this.detouring = false;
         this.jump = shouldJump;
         this.holdTicks = 0;
         this.repathCooldown = cooldown;
         this.progressTicks = 0;
         this.progressX = position.x;
         this.progressZ = position.z;
      }

      private void useDetour(Vec3 newDirection, boolean shouldJump, int side, int hold, int cooldown, Vec3 position) {
         boolean changedSide = !this.detouring || this.preferredSide != side;
         this.direction = newDirection;
         this.detouring = true;
         this.jump = shouldJump;
         this.preferredSide = side;
         this.holdTicks = hold;
         this.repathCooldown = cooldown;
         if (changedSide) {
            this.progressTicks = 0;
            this.progressX = position.x;
            this.progressZ = position.z;
         }
      }

      private void reset() {
         this.targetId = -1;
         this.preferredSide = 1;
         this.direction = Vec3.ZERO;
         this.detouring = false;
         this.jump = false;
         this.holdTicks = 0;
         this.repathCooldown = 0;
         this.progressTicks = 0;
         this.progressX = 0.0;
         this.progressZ = 0.0;
      }
   }

   private record MovementDecision(Vec3 direction, boolean jump) {
   }

   private record RouteOption(Vec3 direction, boolean jump, double clearDistance, boolean fullyClear, double angleDegrees, boolean tight) {
   }

   private record RouteProbe(double clearDistance, boolean fullyClear, boolean blockObstacle, boolean entityObstacle, boolean tight) {
   }
}
