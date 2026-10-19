package com.nododiiiii.ponderer.ponder;

import com.nododiiiii.ponderer.mixin.PonderSceneAccessor;
import com.nododiiiii.ponderer.ui.PickState;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.catnip.animation.LerpedFloat.Chaser;
import net.createmod.ponder.foundation.PonderScene;
import net.createmod.ponder.foundation.element.OutlinerElement;
import net.createmod.ponder.foundation.element.TextWindowElement;
import net.createmod.ponder.foundation.instruction.TickingInstruction;
import net.minecraft.world.phys.Vec3;

public class AdjustViewInstruction extends TickingInstruction {
   private final Vec3 center;
   private final boolean useDefaultCenter;
   private final float multiplier;
   private boolean skipDueToPickMode;

   public AdjustViewInstruction(Vec3 center, boolean useDefaultCenter, float multiplier, int duration) {
      super(true, Math.max(0, duration));
      this.center = center;
      this.useDefaultCenter = useDefaultCenter;
      this.multiplier = multiplier;
   }

   protected void firstTick(PonderScene scene) {
      this.skipDueToPickMode = PickState.isActive();
      if (!this.skipDueToPickMode) {
         scene.forEach(TextWindowElement.class, e -> {
            e.setVisible(false);
            e.setFade(0.0F);
         });
         scene.forEach(OutlinerElement.class, e -> {
            e.setVisible(false);
            e.setFade(0.0F);
         });
         if (scene instanceof PonderSceneAccessor accessor && scene instanceof PonderSceneViewOffsetAccess viewOffset) {
            float currentScale = accessor.ponderer$getScaleFactor();
            float defaultScale = viewOffset.ponderer$getDefaultScale();
            if (Float.isNaN(defaultScale)) {
               defaultScale = currentScale;
               viewOffset.ponderer$setDefaultScale(defaultScale);
            }

            float targetScale = Float.isNaN(this.multiplier) ? defaultScale : defaultScale * this.multiplier;
            LerpedFloat lerpX = viewOffset.ponderer$getViewOffsetX();
            LerpedFloat lerpY = viewOffset.ponderer$getViewOffsetY();
            LerpedFloat lerpZ = viewOffset.ponderer$getViewOffsetZ();
            LerpedFloat lerpScale = viewOffset.ponderer$getScaleOverride();
            int basePlateSize = accessor.ponderer$getBasePlateSize();
            int baseOffX = accessor.ponderer$getBasePlateOffsetX();
            int baseOffZ = accessor.ponderer$getBasePlateOffsetZ();
            double baseTranslationX = -basePlateSize / 2.0 - baseOffX;
            double baseTranslationZ = -basePlateSize / 2.0 - baseOffZ;
            double baseTranslationY = -1.0 + scene.getYOffset();
            double targetX;
            double targetY;
            double targetZ;
            if (this.useDefaultCenter) {
               targetX = 0.0;
               targetY = 0.0;
               targetZ = 0.0;
            } else {
               double centerX = alignToBlockCenter(this.center.x);
               double centerY = this.center.y;
               double centerZ = alignToBlockCenter(this.center.z);
               targetX = -centerX - baseTranslationX;
               targetY = -centerY - baseTranslationY;
               targetZ = -centerZ - baseTranslationZ;
            }

            if (!viewOffset.ponderer$isScaleOverrideActive()) {
               lerpScale.startWithValue(currentScale);
            }

            viewOffset.ponderer$setScaleOverrideActive(true);
            if (this.totalTicks == 0) {
               lerpX.startWithValue(targetX);
               lerpY.startWithValue(targetY);
               lerpZ.startWithValue(targetZ);
               lerpScale.startWithValue(targetScale);
               accessor.ponderer$setScaleFactor(targetScale);
            } else {
               int moveTicks = this.totalTicks / 2;
               int zoomTicks = this.totalTicks - moveTicks;
               if (moveTicks > 0) {
                  lerpX.chase(targetX, Math.abs(targetX - lerpX.getValue(0.0F)) / moveTicks, Chaser.LINEAR);
                  lerpY.chase(targetY, Math.abs(targetY - lerpY.getValue(0.0F)) / moveTicks, Chaser.LINEAR);
                  lerpZ.chase(targetZ, Math.abs(targetZ - lerpZ.getValue(0.0F)) / moveTicks, Chaser.LINEAR);
               } else {
                  lerpX.startWithValue(targetX);
                  lerpY.startWithValue(targetY);
                  lerpZ.startWithValue(targetZ);
               }
            }
         }
      }
   }

   public void tick(PonderScene scene) {
      super.tick(scene);
      if (!this.skipDueToPickMode) {
         if (this.totalTicks > 0) {
            if (scene instanceof PonderSceneAccessor accessor && scene instanceof PonderSceneViewOffsetAccess viewOffset) {
               int elapsed = this.totalTicks - this.remainingTicks;
               int moveTicks = this.totalTicks / 2;
               int zoomTicks = this.totalTicks - moveTicks;
               if (zoomTicks > 0 && elapsed == moveTicks + 1) {
                  float currentScale = accessor.ponderer$getScaleFactor();
                  float defaultScale = viewOffset.ponderer$getDefaultScale();
                  float targetScale = Float.isNaN(this.multiplier) ? defaultScale : defaultScale * this.multiplier;
                  LerpedFloat lerpScale = viewOffset.ponderer$getScaleOverride();
                  lerpScale.startWithValue(currentScale);
                  lerpScale.chase(targetScale, Math.abs(targetScale - currentScale) / zoomTicks, Chaser.LINEAR);
               }

               if (viewOffset.ponderer$isScaleOverrideActive()) {
                  accessor.ponderer$setScaleFactor(viewOffset.ponderer$getScaleOverride().getValue(0.0F));
               }
            }
         }
      }
   }

   private static double alignToBlockCenter(double value) {
      return Math.abs(value - Math.rint(value)) < 1.0E-6 ? value + 0.5 : value;
   }
}
