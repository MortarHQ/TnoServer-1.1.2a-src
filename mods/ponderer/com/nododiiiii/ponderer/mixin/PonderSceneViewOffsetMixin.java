package com.nododiiiii.ponderer.mixin;

import com.nododiiiii.ponderer.ponder.PonderSceneViewOffsetAccess;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.ponder.foundation.PonderScene;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PonderScene.class)
public class PonderSceneViewOffsetMixin implements PonderSceneViewOffsetAccess {
   @Unique
   private final LerpedFloat ponderer$viewOffsetX = LerpedFloat.linear().startWithValue(0.0);
   @Unique
   private final LerpedFloat ponderer$viewOffsetY = LerpedFloat.linear().startWithValue(0.0);
   @Unique
   private final LerpedFloat ponderer$viewOffsetZ = LerpedFloat.linear().startWithValue(0.0);
   @Unique
   private final LerpedFloat ponderer$scaleOverride = LerpedFloat.linear().startWithValue(1.0);
   @Unique
   private boolean ponderer$scaleOverrideActive = false;
   @Unique
   private float ponderer$defaultScale = Float.NaN;

   @Override
   public LerpedFloat ponderer$getViewOffsetX() {
      return this.ponderer$viewOffsetX;
   }

   @Override
   public LerpedFloat ponderer$getViewOffsetY() {
      return this.ponderer$viewOffsetY;
   }

   @Override
   public LerpedFloat ponderer$getViewOffsetZ() {
      return this.ponderer$viewOffsetZ;
   }

   @Override
   public LerpedFloat ponderer$getScaleOverride() {
      return this.ponderer$scaleOverride;
   }

   @Override
   public boolean ponderer$isScaleOverrideActive() {
      return this.ponderer$scaleOverrideActive;
   }

   @Override
   public void ponderer$setScaleOverrideActive(boolean active) {
      this.ponderer$scaleOverrideActive = active;
   }

   @Override
   public void ponderer$resetViewOffset() {
      this.ponderer$viewOffsetX.startWithValue(0.0);
      this.ponderer$viewOffsetY.startWithValue(0.0);
      this.ponderer$viewOffsetZ.startWithValue(0.0);
      this.ponderer$scaleOverride.startWithValue(1.0);
      this.ponderer$scaleOverrideActive = false;
   }

   @Override
   public float ponderer$getDefaultScale() {
      return this.ponderer$defaultScale;
   }

   @Override
   public void ponderer$setDefaultScale(float value) {
      this.ponderer$defaultScale = value;
   }

   @Inject(method = "tick", at = @At("HEAD"), remap = false)
   private void ponderer$tickViewOffset(CallbackInfo ci) {
      this.ponderer$viewOffsetX.tickChaser();
      this.ponderer$viewOffsetY.tickChaser();
      this.ponderer$viewOffsetZ.tickChaser();
      this.ponderer$scaleOverride.tickChaser();
   }
}
