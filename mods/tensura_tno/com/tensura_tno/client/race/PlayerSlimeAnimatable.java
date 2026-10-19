package com.tensura_tno.client.race;

import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class PlayerSlimeAnimatable implements GeoAnimatable {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private Player player;
   private final long instanceId;

   public PlayerSlimeAnimatable(Player player) {
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
      controllers.add(new AnimationController(this, "loopController", 3, this::loopController));
   }

   private PlayState loopController(AnimationState<PlayerSlimeAnimatable> state) {
      Player p = this.player;
      if (p == null) {
         return PlayState.STOP;
      }

      boolean moving = isMoving(p);
      String name;
      if (shouldKeepDefaultShape(p)) {
         name = "animation.slime.idle";
      } else if (moving) {
         name = "animation.slime.walk";
      } else {
         name = "animation.slime.idle";
      }

      return state.setAndContinue(RawAnimation.begin().thenLoop(name));
   }

   private static boolean isMoving(Player p) {
      double dx = p.getX() - p.xOld;
      double dz = p.getZ() - p.zOld;
      return dx * dx + dz * dz > 1.0E-4;
   }

   public static boolean shouldKeepDefaultShape(Player player) {
      return player.isSleeping() || player.isInWater() || player.isInWaterOrBubble() || player.isSwimming() || player.isVisuallySwimming();
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public double getTick(Object obj) {
      return this.player != null && this.player.level() != null ? this.player.level().getGameTime() : 0.0;
   }
}
