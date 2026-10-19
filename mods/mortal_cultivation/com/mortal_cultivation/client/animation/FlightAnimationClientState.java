package com.mortal_cultivation.client.animation;

import com.mortal_cultivation.flight.FlightAnimationNetwork;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME, value = Dist.CLIENT)
public final class FlightAnimationClientState {
   private static final String TAKEOFF_ANIMATION = "animation.takeoff";
   private static final String FLYING_ANIMATION = "animation.flying";
   private static final int TAKEOFF_TICKS = 28;
   private static final int TRANSITION_TICKS = 8;
   private static final int POSE_BLEND_TICKS = 8;
   private static final int HOVER_CONFIRM_TICKS = 2;
   private static final int BRAKE_TICKS = 12;
   private static final int BRAKE_BLEND_TICKS = 3;
   private static final int BRAKE_MIN_HOLD_TICKS = 3;
   private static final int FORWARD_INPUT_GRACE_TICKS = 3;
   private static final double BRAKE_PLAYBACK_RATE = 2.3333333333333335;
   private static final double SPEED_EMA_WEIGHT = 0.25;
   private static final double HOVER_ENTER_SPEED = 0.025;
   private static final double HOVER_EXIT_SPEED = 0.06;
   private static final double BRAKE_MIN_SPEED = 0.1;
   private static final double FAST_ENTER_SPEED = 0.3;
   private static final double FAST_EXIT_SPEED = 0.2;
   private static final float MAX_FOV_TENSION = 0.11F;
   private static final float FOV_SMOOTHING = 0.2F;
   private static final Set<UUID> ACTIVE_PLAYERS = ConcurrentHashMap.newKeySet();
   private static final Set<UUID> BRAKE_HELD_PLAYERS = ConcurrentHashMap.newKeySet();
   private static final Map<UUID, FlightAnimationClientState.RuntimeState> RUNTIME_STATES = new HashMap<>();
   private static boolean localForwardHeld;
   private static boolean localBackwardHeld;
   private static int localForwardInputGraceTicks;
   private static int localBrakeSequence;
   private static FlightAnimationClientState.InputDirection localInputDirection = FlightAnimationClientState.InputDirection.NONE;
   private static float previousFovTension;
   private static float currentFovTension;

   private FlightAnimationClientState() {
   }

   public static void setActive(UUID playerId, boolean active) {
      if (active) {
         ACTIVE_PLAYERS.add(playerId);
      } else {
         ACTIVE_PLAYERS.remove(playerId);
         BRAKE_HELD_PLAYERS.remove(playerId);
      }
   }

   public static boolean isActive(UUID playerId) {
      return ACTIVE_PLAYERS.contains(playerId);
   }

   public static boolean isAnimating(UUID playerId) {
      return RUNTIME_STATES.containsKey(playerId);
   }

   public static void setBrakeHeld(UUID playerId, boolean brakeHeld, int sequence) {
      LocalPlayer localPlayer = Minecraft.getInstance().player;
      if (sequence < 0 || localPlayer == null || !playerId.equals(localPlayer.getUUID()) || sequence == localBrakeSequence) {
         FlightAnimationClientState.RuntimeState state = RUNTIME_STATES.get(playerId);
         if (brakeHeld) {
            BRAKE_HELD_PLAYERS.add(playerId);
            if (state != null) {
               state.requestBrakeStart();
            }
         } else {
            BRAKE_HELD_PLAYERS.remove(playerId);
            if (state != null) {
               state.requestResumeFromBrake();
            }
         }
      }
   }

   @Nullable
   public static FlightAnimationClientState.AnimationSample sample(UUID playerId, String bone, float partialTick) {
      FlightAnimationClientState.RuntimeState state = RUNTIME_STATES.get(playerId);
      if (state != null && !FlightAnimationLibrary.getAnimation(state.currentAnimation).isEmpty()) {
         float renderPartialTick = Mth.clamp(partialTick, 0.0F, 1.0F);
         double currentTime = animationTime(state.currentAnimation, state.currentTicks, renderPartialTick, state.currentPlaybackRate);
         FlightAnimationLibrary.BonePose pose = FlightAnimationLibrary.sample(state.currentAnimation, bone, currentTime);
         if (state.previousAnimation != null) {
            double previousTime = animationTime(state.previousAnimation, state.previousTicks, renderPartialTick, state.previousPlaybackRate);
            FlightAnimationLibrary.BonePose previousPose = FlightAnimationLibrary.sample(state.previousAnimation, bone, previousTime);
            float transition = smoothStep((state.transitionTicks + renderPartialTick) / state.transitionDurationTicks);
            pose = lerp(previousPose, pose, transition);
         }

         float weight = smoothStep(Mth.lerp(renderPartialTick, state.previousPoseWeight, state.poseWeight));
         return new FlightAnimationClientState.AnimationSample(pose, Mth.clamp(weight, 0.0F, 1.0F));
      } else {
         return null;
      }
   }

   public static void clear() {
      ACTIVE_PLAYERS.clear();
      BRAKE_HELD_PLAYERS.clear();
      RUNTIME_STATES.clear();
      resetLocalInput();
      previousFovTension = 0.0F;
      currentFovTension = 0.0F;
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onMovementInput(MovementInputUpdateEvent event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (event.getEntity() instanceof LocalPlayer player && player == minecraft.player) {
         Input input = event.getInput();
         boolean forwardHeld = input.up;
         boolean backwardHeld = input.down;
         boolean forwardPressed = forwardHeld && !localForwardHeld;
         boolean backwardPressed = backwardHeld && !localBackwardHeld;
         boolean hadRecentForwardInput = localInputDirection == FlightAnimationClientState.InputDirection.FORWARD || localForwardInputGraceTicks > 0;
         FlightAnimationClientState.InputDirection previousDirection = localInputDirection;
         localInputDirection = resolveInputDirection(forwardHeld, backwardHeld, forwardPressed, backwardPressed, previousDirection);
         if (localInputDirection == FlightAnimationClientState.InputDirection.FORWARD) {
            localForwardInputGraceTicks = 3;
         } else if (localForwardInputGraceTicks > 0) {
            localForwardInputGraceTicks--;
         }

         if (localInputDirection != previousDirection) {
            if (localInputDirection == FlightAnimationClientState.InputDirection.BACKWARD && hadRecentForwardInput) {
               requestLocalBrake(player, true);
            } else if (localInputDirection == FlightAnimationClientState.InputDirection.FORWARD) {
               requestLocalBrake(player, false);
            }
         }

         localForwardHeld = forwardHeld;
         localBackwardHeld = backwardHeld;
      }
   }

   @SubscribeEvent
   public static void onClientTick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level == null) {
         updateFovTension(null);
      } else {
         Set<UUID> loadedPlayers = new HashSet<>();

         for (AbstractClientPlayer player : minecraft.level.players()) {
            UUID playerId = player.getUUID();
            loadedPlayers.add(playerId);
            updatePlayerAnimation(player, isActive(playerId));
         }

         RUNTIME_STATES.keySet().removeIf(playerIdx -> !loadedPlayers.contains(playerIdx));
         BRAKE_HELD_PLAYERS.removeIf(playerIdx -> !loadedPlayers.contains(playerIdx));
         FlightAnimationClientState.RuntimeState localState = minecraft.player == null ? null : RUNTIME_STATES.get(minecraft.player.getUUID());
         updateFovTension(localState);
      }
   }

   @SubscribeEvent
   public static void onComputeFov(ComputeFovModifierEvent event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.player != null
         && event.getPlayer() == minecraft.player
         && (isActive(minecraft.player.getUUID()) || !(previousFovTension <= 0.0F) || !(currentFovTension <= 0.0F))) {
         float partialTick = minecraft.getTimer().getGameTimeDeltaPartialTick(false);
         float tension = Mth.lerp(partialTick, previousFovTension, currentFovTension);
         event.setNewFovModifier(event.getNewFovModifier() * (1.0F + 0.11F * tension));
      }
   }

   @SubscribeEvent
   public static void onClientLogout(LoggingOut event) {
      clear();
   }

   private static void requestLocalBrake(LocalPlayer player, boolean brakeHeld) {
      UUID playerId = player.getUUID();
      FlightAnimationClientState.RuntimeState state = RUNTIME_STATES.get(playerId);
      if (brakeHeld) {
         if (!isActive(playerId) || state == null || !state.isBraking() && !state.canBeginBrake()) {
            return;
         }

         if (BRAKE_HELD_PLAYERS.add(playerId)) {
            state.requestBrakeStart();
            FlightAnimationNetwork.requestBrakeState(nextLocalBrakeSequence(), true);
         }
      } else if (BRAKE_HELD_PLAYERS.remove(playerId)) {
         if (state != null) {
            state.requestResumeFromBrake();
         }

         FlightAnimationNetwork.requestBrakeState(nextLocalBrakeSequence(), false);
      }
   }

   private static int nextLocalBrakeSequence() {
      localBrakeSequence = localBrakeSequence == Integer.MAX_VALUE ? 1 : localBrakeSequence + 1;
      return localBrakeSequence;
   }

   private static FlightAnimationClientState.InputDirection resolveInputDirection(
      boolean forwardHeld, boolean backwardHeld, boolean forwardPressed, boolean backwardPressed, FlightAnimationClientState.InputDirection previousDirection
   ) {
      if (backwardPressed && !forwardPressed) {
         return FlightAnimationClientState.InputDirection.BACKWARD;
      } else if (forwardPressed && !backwardPressed) {
         return FlightAnimationClientState.InputDirection.FORWARD;
      } else if (forwardHeld && !backwardHeld) {
         return FlightAnimationClientState.InputDirection.FORWARD;
      } else if (backwardHeld && !forwardHeld) {
         return FlightAnimationClientState.InputDirection.BACKWARD;
      } else {
         return !forwardHeld && !backwardHeld ? FlightAnimationClientState.InputDirection.NONE : previousDirection;
      }
   }

   private static void resetLocalInput() {
      localForwardHeld = false;
      localBackwardHeld = false;
      localForwardInputGraceTicks = 0;
      localBrakeSequence = 0;
      localInputDirection = FlightAnimationClientState.InputDirection.NONE;
   }

   private static void updatePlayerAnimation(AbstractClientPlayer player, boolean active) {
      UUID playerId = player.getUUID();
      FlightAnimationClientState.RuntimeState state = RUNTIME_STATES.get(playerId);
      double horizontalSpeed = movementSpeed(player);
      if (state == null) {
         if (active) {
            state = new FlightAnimationClientState.RuntimeState(horizontalSpeed);
            if (BRAKE_HELD_PLAYERS.contains(playerId)) {
               state.holdBrakeAtRest();
            }

            RUNTIME_STATES.put(playerId, state);
         }
      } else {
         state.advanceAnimationClocks();
         if (!active) {
            state.cancelPendingBrakeStart();
            if (!state.stopping) {
               state.beginStopping();
            }

            state.updatePoseWeight(false);
            if (state.isFullyFadedOut()) {
               RUNTIME_STATES.remove(playerId);
            }
         } else {
            if (state.stopping) {
               state.restart(horizontalSpeed);
            }

            state.applyPendingBrakeStart();
            updateSpeed(state, horizontalSpeed);
            if (state.phase == FlightAnimationClientState.FlightPhase.BRAKING) {
               state.updateBraking();
            } else {
               boolean cruiseSuppressed = player == Minecraft.getInstance().player && localInputDirection == FlightAnimationClientState.InputDirection.BACKWARD;
               if (state.phase == FlightAnimationClientState.FlightPhase.HOVER && state.moving && !state.hasTakenOff && !cruiseSuppressed) {
                  state.beginTakeoff();
               }

               if (state.phase != FlightAnimationClientState.FlightPhase.TAKEOFF) {
                  updateCruisePhase(state, cruiseSuppressed);
               } else {
                  if (!state.moving || cruiseSuppressed) {
                     updateCruisePhase(state, cruiseSuppressed);
                  } else if (state.currentTicks >= 28.0) {
                     state.hasTakenOff = true;
                     updateCruisePhase(state, false);
                  } else {
                     state.updatePoseWeight(true);
                  }
               }
            }
         }
      }
   }

   private static void updateCruisePhase(FlightAnimationClientState.RuntimeState state, boolean cruiseSuppressed) {
      FlightAnimationClientState.FlightPhase targetPhase;
      if (state.moving && !cruiseSuppressed) {
         targetPhase = state.fastFlight ? FlightAnimationClientState.FlightPhase.FAST : FlightAnimationClientState.FlightPhase.NORMAL;
      } else {
         targetPhase = FlightAnimationClientState.FlightPhase.HOVER;
      }

      if (targetPhase == FlightAnimationClientState.FlightPhase.HOVER) {
         state.phase = FlightAnimationClientState.FlightPhase.HOVER;
         if (cruiseSuppressed) {
            state.hasTakenOff = false;
         }
      } else if (state.phase != targetPhase) {
         state.switchOrResumeAnimation("animation.flying", targetPhase);
      }

      state.updatePoseWeight(targetPhase != FlightAnimationClientState.FlightPhase.HOVER);
   }

   private static void updateSpeed(FlightAnimationClientState.RuntimeState state, double movementSpeed) {
      state.smoothedMovementSpeed = state.smoothedMovementSpeed + 0.25 * (movementSpeed - state.smoothedMovementSpeed);
      if (movementSpeed <= 0.025) {
         if (++state.stationaryTicks >= 2) {
            state.moving = false;
         }
      } else {
         state.stationaryTicks = 0;
         if (!state.moving && movementSpeed >= 0.06) {
            state.moving = true;
         }
      }

      if (!state.moving) {
         state.fastFlight = false;
      } else {
         if (state.fastFlight) {
            if (state.smoothedMovementSpeed <= 0.2) {
               state.fastFlight = false;
            }
         } else if (state.smoothedMovementSpeed >= 0.3) {
            state.fastFlight = true;
         }
      }
   }

   private static double movementSpeed(AbstractClientPlayer player) {
      double xMovement = player.getX() - player.xOld;
      double zMovement = player.getZ() - player.zOld;
      return Math.sqrt(xMovement * xMovement + zMovement * zMovement);
   }

   private static double animationTime(String animation, double cursorTicks, float partialTick, double playbackRate) {
      FlightAnimationLibrary.AnimationClip clip = FlightAnimationLibrary.getAnimation(animation).orElse(null);
      if (clip == null) {
         return 0.0;
      }

      double lengthTicks = clip.lengthSeconds() * 20.0;
      if (lengthTicks <= 0.0) {
         return 0.0;
      }

      double sampledTicks = cursorTicks + playbackRate * partialTick;
      if (clip.looping()) {
         sampledTicks %= lengthTicks;
         if (sampledTicks < 0.0) {
            sampledTicks += lengthTicks;
         }
      } else {
         sampledTicks = Mth.clamp(sampledTicks, 0.0, lengthTicks);
      }

      return sampledTicks / 20.0;
   }

   private static double advanceAnimationCursor(String animation, double cursorTicks, double playbackRate) {
      double nextCursor = cursorTicks + playbackRate;
      FlightAnimationLibrary.AnimationClip clip = FlightAnimationLibrary.getAnimation(animation).orElse(null);
      return clip != null && !clip.looping() ? Mth.clamp(nextCursor, 0.0, clip.lengthSeconds() * 20.0) : nextCursor;
   }

   private static void updateFovTension(@Nullable FlightAnimationClientState.RuntimeState localState) {
      previousFovTension = currentFovTension;
      float target = 0.0F;
      if (localState != null
         && !localState.stopping
         && localState.moving
         && localState.phase != FlightAnimationClientState.FlightPhase.BRAKING
         && localInputDirection != FlightAnimationClientState.InputDirection.BACKWARD) {
         target = (float)Mth.clamp((localState.smoothedMovementSpeed - 0.2) / 0.09999999999999998, 0.0, 1.0);
         target *= localState.poseWeight;
      }

      currentFovTension = currentFovTension + (target - currentFovTension) * 0.2F;
      if (Math.abs(currentFovTension) < 1.0E-4F) {
         currentFovTension = 0.0F;
      }
   }

   private static FlightAnimationLibrary.BonePose lerp(FlightAnimationLibrary.BonePose start, FlightAnimationLibrary.BonePose end, float progress) {
      return new FlightAnimationLibrary.BonePose(
         lerp(start.rotationDegrees(), end.rotationDegrees(), progress), lerp(start.position(), end.position(), progress)
      );
   }

   private static Vec3 lerp(Vec3 start, Vec3 end, float progress) {
      return new Vec3(Mth.lerp(progress, start.x, end.x), Mth.lerp(progress, start.y, end.y), Mth.lerp(progress, start.z, end.z));
   }

   private static float smoothStep(float progress) {
      float clamped = Mth.clamp(progress, 0.0F, 1.0F);
      return clamped * clamped * (3.0F - 2.0F * clamped);
   }

   public record AnimationSample(FlightAnimationLibrary.BonePose pose, float weight) {
   }

   private enum FlightPhase {
      TAKEOFF,
      BRAKING,
      HOVER,
      NORMAL,
      FAST;
   }

   private enum InputDirection {
      NONE,
      FORWARD,
      BACKWARD;
   }

   private static final class RuntimeState {
      private String currentAnimation = "animation.takeoff";
      private double currentTicks;
      private double currentPlaybackRate = 1.0;
      private String previousAnimation;
      private double previousTicks;
      private double previousPlaybackRate = 1.0;
      private int transitionTicks = 8;
      private int transitionDurationTicks = 8;
      private float previousPoseWeight;
      private float poseWeight;
      private double smoothedMovementSpeed;
      private int stationaryTicks;
      private int brakeElapsedTicks;
      private boolean moving;
      private boolean fastFlight;
      private boolean hasTakenOff;
      private boolean stopping;
      private boolean resumeRequested;
      private boolean brakeStartRequested;
      private FlightAnimationClientState.FlightPhase phase = FlightAnimationClientState.FlightPhase.TAKEOFF;

      private RuntimeState(double movementSpeed) {
         this.smoothedMovementSpeed = movementSpeed;
         this.moving = movementSpeed >= 0.06;
         this.fastFlight = movementSpeed >= 0.3;
         if (!this.moving) {
            this.currentAnimation = "animation.flying";
            this.stationaryTicks = 2;
            this.phase = FlightAnimationClientState.FlightPhase.HOVER;
         }
      }

      private void advanceAnimationClocks() {
         this.currentTicks = FlightAnimationClientState.advanceAnimationCursor(this.currentAnimation, this.currentTicks, this.currentPlaybackRate);
         this.previousPoseWeight = this.poseWeight;
         if (this.phase == FlightAnimationClientState.FlightPhase.BRAKING) {
            this.brakeElapsedTicks++;
         }

         if (this.previousAnimation != null) {
            this.previousTicks = FlightAnimationClientState.advanceAnimationCursor(this.previousAnimation, this.previousTicks, this.previousPlaybackRate);
            if (++this.transitionTicks >= this.transitionDurationTicks) {
               this.clearPreviousAnimation();
            }
         }
      }

      private void switchOrResumeAnimation(String animation, FlightAnimationClientState.FlightPhase newPhase) {
         if (this.currentAnimation.equals(animation)) {
            this.currentPlaybackRate = 1.0;
            this.phase = newPhase;
         } else if (this.previousAnimation == null) {
            this.switchAnimation(animation, newPhase, this.mappedLoopTicks(animation), 1.0, 8);
         }
      }

      private void switchAnimation(
         String animation, FlightAnimationClientState.FlightPhase newPhase, double startTicks, double playbackRate, int transitionDuration
      ) {
         this.previousAnimation = this.currentAnimation;
         this.previousTicks = this.currentTicks;
         this.previousPlaybackRate = this.currentPlaybackRate;
         this.transitionTicks = 0;
         this.transitionDurationTicks = Math.max(1, transitionDuration);
         this.currentAnimation = animation;
         this.currentTicks = Math.max(0.0, startTicks);
         this.currentPlaybackRate = playbackRate;
         this.phase = newPhase;
      }

      private double mappedLoopTicks(String targetAnimation) {
         FlightAnimationLibrary.AnimationClip sourceClip = FlightAnimationLibrary.getAnimation(this.currentAnimation).orElse(null);
         FlightAnimationLibrary.AnimationClip targetClip = FlightAnimationLibrary.getAnimation(targetAnimation).orElse(null);
         if (sourceClip != null && targetClip != null && sourceClip.looping() && targetClip.looping()) {
            double sourceLengthTicks = sourceClip.lengthSeconds() * 20.0;
            double targetLengthTicks = targetClip.lengthSeconds() * 20.0;
            if (!(sourceLengthTicks <= 0.0) && !(targetLengthTicks <= 0.0)) {
               double loopTicks = this.currentTicks % sourceLengthTicks;
               if (loopTicks < 0.0) {
                  loopTicks += sourceLengthTicks;
               }

               return loopTicks / sourceLengthTicks * targetLengthTicks;
            } else {
               return 0.0;
            }
         } else {
            return 0.0;
         }
      }

      private boolean canBeginBrake() {
         return !this.stopping
            && this.moving
            && this.smoothedMovementSpeed >= 0.1
            && (
               this.phase == FlightAnimationClientState.FlightPhase.TAKEOFF
                  || this.phase == FlightAnimationClientState.FlightPhase.NORMAL
                  || this.phase == FlightAnimationClientState.FlightPhase.FAST
            );
      }

      private boolean isBraking() {
         return this.phase == FlightAnimationClientState.FlightPhase.BRAKING;
      }

      private void requestBrakeStart() {
         if (this.phase == FlightAnimationClientState.FlightPhase.BRAKING) {
            this.resumeRequested = false;
            if (this.currentTicks > 0.0) {
               this.currentPlaybackRate = -2.3333333333333335;
            }
         } else {
            this.brakeStartRequested = true;
         }
      }

      private void applyPendingBrakeStart() {
         if (this.brakeStartRequested) {
            this.brakeStartRequested = false;
            if (this.phase == FlightAnimationClientState.FlightPhase.HOVER && this.poseWeight <= 1.0E-4F) {
               this.holdBrakeAtRest();
            } else {
               this.beginBrake();
            }
         }
      }

      private void cancelPendingBrakeStart() {
         this.brakeStartRequested = false;
      }

      private void beginBrake() {
         if (this.phase != FlightAnimationClientState.FlightPhase.BRAKING) {
            this.brakeElapsedTicks = 0;
            this.resumeRequested = false;
            this.brakeStartRequested = false;
            this.hasTakenOff = false;
            if (this.currentAnimation.equals("animation.takeoff")) {
               this.currentTicks = Mth.clamp(this.currentTicks, 0.0, 28.0);
               this.currentPlaybackRate = -2.3333333333333335;
               this.phase = FlightAnimationClientState.FlightPhase.BRAKING;
            } else {
               this.switchAnimation("animation.takeoff", FlightAnimationClientState.FlightPhase.BRAKING, 28.0, -2.3333333333333335, 3);
            }
         }
      }

      private void holdBrakeAtRest() {
         this.clearPreviousAnimation();
         this.currentAnimation = "animation.takeoff";
         this.currentTicks = 0.0;
         this.currentPlaybackRate = 0.0;
         this.phase = FlightAnimationClientState.FlightPhase.BRAKING;
         this.poseWeight = 0.0F;
         this.previousPoseWeight = 0.0F;
         this.brakeElapsedTicks = 3;
         this.resumeRequested = false;
         this.brakeStartRequested = false;
         this.hasTakenOff = false;
      }

      private void requestResumeFromBrake() {
         this.brakeStartRequested = false;
         if (this.phase == FlightAnimationClientState.FlightPhase.BRAKING) {
            this.resumeRequested = true;
         }
      }

      private void updateBraking() {
         if (this.resumeRequested && this.brakeElapsedTicks >= 3) {
            this.resumeTakeoffFromBrake();
            this.updatePoseWeight(true);
         } else {
            if (this.currentTicks <= 0.0) {
               this.currentTicks = 0.0;
               this.currentPlaybackRate = 0.0;
               this.updatePoseWeight(false);
            } else {
               this.updatePoseWeight(true);
            }
         }
      }

      private void resumeTakeoffFromBrake() {
         this.currentAnimation = "animation.takeoff";
         this.currentTicks = Mth.clamp(this.currentTicks, 0.0, 28.0);
         this.currentPlaybackRate = 1.0;
         this.phase = FlightAnimationClientState.FlightPhase.TAKEOFF;
         this.brakeElapsedTicks = 0;
         this.resumeRequested = false;
         this.brakeStartRequested = false;
         this.hasTakenOff = false;
      }

      private void beginStopping() {
         this.stopping = true;
      }

      private void beginTakeoff() {
         this.resumeRequested = false;
         this.brakeStartRequested = false;
         this.brakeElapsedTicks = 0;
         if (!this.currentAnimation.equals("animation.takeoff")) {
            if (this.poseWeight <= 1.0E-4F) {
               this.clearPreviousAnimation();
               this.currentAnimation = "animation.takeoff";
               this.currentTicks = 0.0;
               this.currentPlaybackRate = 1.0;
               this.phase = FlightAnimationClientState.FlightPhase.TAKEOFF;
            } else {
               this.switchAnimation("animation.takeoff", FlightAnimationClientState.FlightPhase.TAKEOFF, 0.0, 1.0, 8);
            }
         } else {
            if (this.poseWeight <= 1.0E-4F || this.currentTicks >= 28.0) {
               this.clearPreviousAnimation();
               this.currentTicks = 0.0;
            }

            this.currentPlaybackRate = 1.0;
            this.phase = FlightAnimationClientState.FlightPhase.TAKEOFF;
         }
      }

      private void restart(double movementSpeed) {
         this.stopping = false;
         this.smoothedMovementSpeed = movementSpeed;
         this.moving = movementSpeed >= 0.06;
         this.fastFlight = movementSpeed >= 0.3;
         this.hasTakenOff = false;
         this.resumeRequested = false;
         this.brakeElapsedTicks = 0;
         this.stationaryTicks = this.moving ? 0 : 2;
         this.clearPreviousAnimation();
         if (this.moving) {
            this.currentAnimation = "animation.takeoff";
            this.currentTicks = 0.0;
            this.currentPlaybackRate = 1.0;
            this.phase = FlightAnimationClientState.FlightPhase.TAKEOFF;
         } else {
            this.currentAnimation = "animation.flying";
            this.currentTicks = 0.0;
            this.currentPlaybackRate = 1.0;
            this.phase = FlightAnimationClientState.FlightPhase.HOVER;
         }
      }

      private void clearPreviousAnimation() {
         this.previousAnimation = null;
         this.previousTicks = 0.0;
         this.previousPlaybackRate = 1.0;
         this.transitionTicks = 8;
         this.transitionDurationTicks = 8;
      }

      private void updatePoseWeight(boolean visible) {
         float targetWeight = visible ? 1.0F : 0.0F;
         float step = 0.125F;
         if (this.poseWeight < targetWeight) {
            this.poseWeight = Math.min(targetWeight, this.poseWeight + step);
         } else if (this.poseWeight > targetWeight) {
            this.poseWeight = Math.max(targetWeight, this.poseWeight - step);
         }
      }

      private boolean isFullyFadedOut() {
         return this.previousPoseWeight <= 1.0E-4F && this.poseWeight <= 1.0E-4F;
      }
   }
}
