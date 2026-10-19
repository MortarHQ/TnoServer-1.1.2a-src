package com.blorb.morerelics.mixin;

import com.blorb.morerelics.effects.Nullification;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {
   @Inject(method = "renderNameTag", at = @At("HEAD"), cancellable = true)
   private void renderNameTagMixin(
      AbstractClientPlayer entity,
      Component displayName,
      PoseStack poseStack,
      MultiBufferSource bufferSource,
      int packedLight,
      float partialTick,
      CallbackInfo ci
   ) {
      if (Nullification.isRendering) {
         ci.cancel();
      }
   }
}
