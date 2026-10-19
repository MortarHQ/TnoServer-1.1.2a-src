package com.finderfeed.raids_enhanced.mixin;

import com.finderfeed.raids_enhanced.content.entities.player_blimp.PlayerBlimpEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {
   @Inject(method = "updatePlayerPose", at = @At("HEAD"), cancellable = true)
   public void updatePlayerPose(CallbackInfo ci) {
      Player player = (Player)this;
      if (player.getVehicle() instanceof PlayerBlimpEntity playerBlimp) {
         player.setPose(Pose.STANDING);
         ci.cancel();
      }
   }

   @Inject(method = "wantsToStopRiding", at = @At("HEAD"), cancellable = true)
   public void wantsToStopRiding(CallbackInfoReturnable<Boolean> cir) {
      if (((Player)this).getVehicle() instanceof PlayerBlimpEntity blimpEntity) {
         cir.setReturnValue(false);
      }
   }
}
