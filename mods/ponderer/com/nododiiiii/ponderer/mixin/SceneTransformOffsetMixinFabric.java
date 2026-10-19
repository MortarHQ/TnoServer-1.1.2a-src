package com.nododiiiii.ponderer.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.nododiiiii.ponderer.ponder.PonderSceneViewOffsetAccess;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.ponder.foundation.PonderScene;
import net.createmod.ponder.foundation.PonderScene.SceneTransform;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SceneTransform.class)
public class SceneTransformOffsetMixinFabric {
   @Shadow(remap = false)
   @Final
   private PonderScene this$0;
   @Unique
   private boolean ponderer$handledTwoArgApply;
   @Unique
   private float ponderer$storedBaseScale = Float.NaN;

   @Inject(method = "apply(Lcom/mojang/blaze3d/vertex/PoseStack;)Lcom/mojang/blaze3d/vertex/PoseStack;", at = @At("HEAD"), require = 0, remap = false)
   private void ponderer$beginOneArgApply(PoseStack ms, CallbackInfoReturnable<PoseStack> cir) {
      this.ponderer$handledTwoArgApply = false;
      this.ponderer$applyScaleOverride(AnimationTickHolder.getPartialTicks(this.this$0.getWorld()));
   }

   @Inject(method = "apply(Lcom/mojang/blaze3d/vertex/PoseStack;)Lcom/mojang/blaze3d/vertex/PoseStack;", at = @At("TAIL"), require = 0, remap = false)
   private void ponderer$applyViewOffsetOneArg(PoseStack ms, CallbackInfoReturnable<PoseStack> cir) {
      if (!this.ponderer$handledTwoArgApply) {
         float pt = AnimationTickHolder.getPartialTicks(this.this$0.getWorld());
         this.ponderer$applyViewOffsetCommon(ms, pt);
      }
   }

   @Inject(method = "apply(Lnet/minecraft/class_4587;F)Lnet/minecraft/class_4587;", at = @At("TAIL"), require = 0, remap = false)
   private void ponderer$applyViewOffsetTwoArg(PoseStack ms, float pt, CallbackInfoReturnable<PoseStack> cir) {
      this.ponderer$handledTwoArgApply = true;
      this.ponderer$applyScaleOverride(pt);
      this.ponderer$applyViewOffsetCommon(ms, pt);
   }

   @Inject(method = "apply(Lcom/mojang/blaze3d/vertex/PoseStack;)Lcom/mojang/blaze3d/vertex/PoseStack;", at = @At("RETURN"), require = 0, remap = false)
   private void ponderer$endOneArgApply(PoseStack ms, CallbackInfoReturnable<PoseStack> cir) {
      this.ponderer$handledTwoArgApply = false;
   }

   private void ponderer$applyScaleOverride(float pt) {
      PonderSceneViewOffsetAccess access = (PonderSceneViewOffsetAccess)this.this$0;
      PonderSceneAccessor scene = (PonderSceneAccessor)this.this$0;
      if (access.ponderer$isScaleOverrideActive()) {
         if (Float.isNaN(this.ponderer$storedBaseScale)) {
            this.ponderer$storedBaseScale = scene.ponderer$getScaleFactor();
         }

         scene.ponderer$setScaleFactor(access.ponderer$getScaleOverride().getValue(pt));
      } else {
         if (!Float.isNaN(this.ponderer$storedBaseScale)) {
            scene.ponderer$setScaleFactor(this.ponderer$storedBaseScale);
            this.ponderer$storedBaseScale = Float.NaN;
         }
      }
   }

   private void ponderer$applyViewOffsetCommon(PoseStack ms, float pt) {
      PonderSceneViewOffsetAccess access = (PonderSceneViewOffsetAccess)this.this$0;
      float ox = access.ponderer$getViewOffsetX().getValue(pt);
      float oy = access.ponderer$getViewOffsetY().getValue(pt);
      float oz = access.ponderer$getViewOffsetZ().getValue(pt);
      if (ox != 0.0F || oy != 0.0F || oz != 0.0F) {
         ms.translate(ox, oy, oz);
      }
   }
}
