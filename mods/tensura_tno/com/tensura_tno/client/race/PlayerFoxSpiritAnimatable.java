package com.tensura_tno.client.race;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class PlayerFoxSpiritAnimatable implements GeoAnimatable {
   private static final float NORMAL_VISUAL_HEIGHT = 1.45F;
   private static final double SPRINT_ANIMATION_SPEED = 1.5;
   private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.kitsune.idle");
   private static final RawAnimation SIT = RawAnimation.begin().thenLoop("animation.kitsune.sit");
   private static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.kitsune.walk");
   private static final RawAnimation CROUCH_IDLE = RawAnimation.begin().thenLoop("animation.kitsune.crouch_idle");
   private static final RawAnimation CROUCH_WALK = RawAnimation.begin().thenLoop("animation.kitsune.crouch_walk");
   private static final RawAnimation PRONE_IDLE = RawAnimation.begin().thenLoop("animation.kitsune.prone_idle");
   private static final RawAnimation PRONE_WALK = RawAnimation.begin().thenLoop("animation.kitsune.prone_walk");
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private final long instanceId;
   private Player player;
   private boolean wasSleeping;

   public PlayerFoxSpiritAnimatable(Player player) {
      this.player = player;
      this.instanceId = player.getUUID().getMostSignificantBits() ^ player.getUUID().getLeastSignificantBits();
   }

   public void setPlayer(Player player) {
      this.player = player;
   }

   public Player getPlayer() {
      return this.player;
   }

   public long getInstanceId() {
      return this.instanceId;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "loopController", 0, this::loopController));
   }

   private PlayState loopController(AnimationState<PlayerFoxSpiritAnimatable> state) {
      Player p = this.player;
      if (p == null) {
         return PlayState.STOP;
      }

      if (p.isSleeping()) {
         if (!this.wasSleeping) {
            state.getController().forceAnimationReset();
         }

         this.wasSleeping = true;
         state.getController().setAnimationSpeed(0.0);
         return state.setAndContinue(PRONE_IDLE);
      } else {
         if (this.wasSleeping) {
            state.getController().forceAnimationReset();
            this.wasSleeping = false;
         }

         state.getController().setAnimationSpeed(1.0);
         if (shouldSit(p)) {
            return state.setAndContinue(SIT);
         } else if (shouldProne(p)) {
            return state.setAndContinue(shouldUseMovingProne(p) ? PRONE_WALK : PRONE_IDLE);
         } else if (shouldCrouch(p)) {
            return state.setAndContinue(isMoving(p) ? CROUCH_WALK : CROUCH_IDLE);
         } else if (isMoving(p)) {
            state.getController().setAnimationSpeed(p.isSprinting() ? 1.5 : 1.0);
            return state.setAndContinue(WALK);
         } else {
            return state.setAndContinue(IDLE);
         }
      }
   }

   public static boolean shouldSit(Player player) {
      Entity vehicle = player.getVehicle();
      return player.isPassenger() && vehicle != null && vehicle.shouldRiderSit();
   }

   public static boolean shouldCrouch(Player player) {
      return !player.isSleeping() && !shouldProne(player) && !shouldSit(player) && (player.isShiftKeyDown() || isUnderLowCeiling(player));
   }

   private static boolean isUnderLowCeiling(Player player) {
      if (player.level() == null) {
         return false;
      }

      AABB current = player.getBoundingBox();
      double requiredHeight = 1.45F * player.getScale();
      if (current.getYsize() + 1.0E-4 >= requiredHeight) {
         return false;
      }

      AABB standingVisualBox = new AABB(current.minX, current.minY, current.minZ, current.maxX, current.minY + requiredHeight, current.maxZ).deflate(1.0E-7);
      return !player.level().noBlockCollision(player, standingVisualBox);
   }

   public static boolean shouldProne(Player player) {
      return player.isSleeping() || player.isVisuallySwimming() || player.getPose() == Pose.SWIMMING || player.isFallFlying() || player.isAutoSpinAttack();
   }

   private static boolean shouldUseMovingProne(Player player) {
      return !player.isSleeping() && (isMoving(player) || player.isVisuallySwimming() || player.isFallFlying() || player.isAutoSpinAttack());
   }

   private static boolean isMoving(Player player) {
      double dx = player.getX() - player.xOld;
      double dz = player.getZ() - player.zOld;
      return dx * dx + dz * dz > 1.0E-4;
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public double getTick(Object object) {
      return this.player != null && this.player.level() != null ? this.player.level().getGameTime() : 0.0;
   }
}
