package com.tensura_tno.client.race;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.Animation.LoopType;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class PlayerOrcAnimatable implements GeoAnimatable {
   private static final RawAnimation ATTACK = RawAnimation.begin().then("animation.orc.attack", LoopType.PLAY_ONCE);
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private final long instanceId;
   private Player player;
   private AnimationController<PlayerOrcAnimatable> actionController;
   private boolean wasSwinging;
   private int lastSwingTime = Integer.MIN_VALUE;

   public PlayerOrcAnimatable(Player player) {
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

   public void syncPlayerState(float partialTick) {
      Player p = this.player;
      if (p != null) {
         boolean swinging = p.swinging;
         boolean restartedSwing = swinging && this.wasSwinging && p.swingTime < this.lastSwingTime;
         if (swinging && (!this.wasSwinging || restartedSwing)) {
            this.triggerAttackAnimation();
         }

         this.wasSwinging = swinging;
         this.lastSwingTime = swinging ? p.swingTime : Integer.MIN_VALUE;
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "loopController", 0, this::loopController));
      this.actionController = new AnimationController(this, "actionController", 0, state -> PlayState.STOP).triggerableAnim("attack", ATTACK);
      controllers.add(this.actionController);
   }

   private void triggerAttackAnimation() {
      if (this.actionController != null) {
         this.actionController.forceAnimationReset();
         this.actionController.tryTriggerAnimation("attack");
      }
   }

   private PlayState loopController(AnimationState<PlayerOrcAnimatable> state) {
      Player p = this.player;
      if (p == null) {
         return PlayState.STOP;
      }

      String name;
      if (shouldSit(p)) {
         name = "animation.orc.sit";
      } else if (p.isVisuallySwimming()) {
         name = "animation.orc.swim";
      } else if (shouldCrouch(p) && isMoving(p)) {
         name = "animation.orc.crouch_walk";
      } else if (shouldCrouch(p)) {
         name = "animation.orc.new";
      } else if (isMoving(p)) {
         name = p.isSprinting() ? "animation.orc.run" : "animation.orc.walk";
      } else {
         name = "animation.orc.idle";
      }

      return "animation.orc.new".equals(name)
         ? state.setAndContinue(RawAnimation.begin().then(name, LoopType.HOLD_ON_LAST_FRAME))
         : state.setAndContinue(RawAnimation.begin().thenLoop(name));
   }

   public static boolean shouldSit(Player player) {
      Entity vehicle = player.getVehicle();
      return player.isPassenger() && vehicle != null && vehicle.shouldRiderSit();
   }

   public static boolean shouldCrouch(Player player) {
      return player.isShiftKeyDown() && !player.isSleeping() && !player.isVisuallySwimming() && !shouldSit(player);
   }

   private static boolean isMoving(Player p) {
      double dx = p.getX() - p.xOld;
      double dz = p.getZ() - p.zOld;
      return dx * dx + dz * dz > 1.0E-4;
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public double getTick(Object object) {
      return this.player != null && this.player.level() != null ? this.player.level().getGameTime() : 0.0;
   }
}
