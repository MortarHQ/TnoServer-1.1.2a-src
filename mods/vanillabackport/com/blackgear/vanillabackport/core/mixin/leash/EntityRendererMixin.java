package com.blackgear.vanillabackport.core.mixin.leash;

import com.blackgear.vanillabackport.common.api.leash.LeashFeatureRenderer;
import com.blackgear.vanillabackport.common.api.leash.LeashPhysics;
import com.blackgear.vanillabackport.core.ModChecker;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {
   @Unique
   private LeashFeatureRenderer<T> leashRenderer;
   @Shadow
   @Final
   protected EntityRenderDispatcher entityRenderDispatcher;

   @Inject(method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;)V", at = @At("TAIL"))
   private void vb$init(Context context, CallbackInfo ci) {
      this.leashRenderer = new LeashFeatureRenderer<>(this.entityRenderDispatcher);
   }

   @Inject(
      method = "render(Lnet/minecraft/world/entity/Entity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
      at = @At("HEAD")
   )
   private void renderAdditional(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
      if (!ModChecker.SABLE_LOADED) {
         this.leashRenderer.render(entity, partialTick, poseStack, buffer);
      }
   }

   @Inject(
      method = "renderLeash(Lnet/minecraft/world/entity/Entity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/Entity;)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private <E extends Entity & Leashable> void vb$cancelVanillaLeash(
      E entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, Entity leashHolder, CallbackInfo ci
   ) {
      if (!ModChecker.SABLE_LOADED && LeashPhysics.supportsQuadLeash(entity)) {
         ci.cancel();
      }
   }

   @Inject(
      method = "shouldRender(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/client/renderer/culling/Frustum;DDD)Z",
      at = @At("TAIL"),
      cancellable = true
   )
   private void vb$shouldRender(T entity, Frustum camera, double camX, double camY, double camZ, CallbackInfoReturnable<Boolean> cir) {
      if (!ModChecker.SABLE_LOADED) {
         cir.setReturnValue(this.leashRenderer.shouldRender(entity, camera, (Boolean)cir.getReturnValue()));
      }
   }
}
