package com.mongoose.royalvariations.mixin;

import com.mongoose.royalvariations.common.attachments.RVCapHelper;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
   @Inject(method = "getWhiteOverlayProgress(Lnet/minecraft/world/entity/LivingEntity;F)F", at = @At("HEAD"), cancellable = true)
   public void getWhiteOverlayProgress(T p_115334_, float p_115335_, CallbackInfoReturnable<Float> cir) {
      if (RVCapHelper.isFlashing(p_115334_)) {
         cir.setReturnValue(1.0F);
      }
   }

   @Inject(method = "getOverlayCoords", at = @At("HEAD"), cancellable = true)
   private static void overlay(LivingEntity entity, float f, CallbackInfoReturnable<Integer> cir) {
      if (RVCapHelper.isFlashing(entity)) {
         cir.setReturnValue(OverlayTexture.pack(OverlayTexture.u(1.0F), 10));
      }
   }
}
