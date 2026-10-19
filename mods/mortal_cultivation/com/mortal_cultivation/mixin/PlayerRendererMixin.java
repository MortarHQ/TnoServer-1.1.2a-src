package com.mortal_cultivation.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mortal_cultivation.entity.FlyingSwordEntity;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {
   @Inject(
      method = "setupRotations(Lnet/minecraft/client/player/AbstractClientPlayer;Lcom/mojang/blaze3d/vertex/PoseStack;FFFF)V",
      at = @At("TAIL"),
      remap = false
   )
   private void leanFlyingSwordRider(
      AbstractClientPlayer player, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale, CallbackInfo callback
   ) {
      if (player.getVehicle() instanceof FlyingSwordEntity sword) {
         float speedFactor = Mth.clamp((float)sword.getDeltaMovement().horizontalDistance() / 0.9F, 0.0F, 1.0F);
         float leanDegrees = Mth.lerp(speedFactor, 8.0F, 20.0F);
         poseStack.mulPose(Axis.XP.rotationDegrees(-leanDegrees));
      }
   }
}
