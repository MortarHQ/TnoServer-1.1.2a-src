package com.foxablazeultimate.mixin.client;

import com.foxablazeultimate.client.VelgryndGlintRenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BufferSource.class)
public abstract class MixinBufferSourceVelgryndGlint {
   @Inject(method = "endBatch(Lnet/minecraft/client/renderer/RenderType;)V", at = @At("TAIL"))
   private void foxablazeultimate$flushChargedWeaponGlint(RenderType type, CallbackInfo callback) {
      RenderType replacement = VelgryndGlintRenderState.replacement(type);
      if (replacement != null) {
         ((BufferSource)this).endBatch(replacement);
      }

      RenderType voidGlint = VelgryndGlintRenderState.azathothReplacement(type);
      if (voidGlint != null) {
         ((BufferSource)this).endBatch(voidGlint);
      }
   }
}
