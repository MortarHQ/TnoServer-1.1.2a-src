package net.unusual.block_factorys_bosses.geckolib;

import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.entity.boss.yeti.YetiEntity;
import net.unusual.block_factorys_bosses.mixins.accessor.AnimatableManagerAccessor;
import net.unusual.block_factorys_bosses.mixins.accessor.AnimationControllerAccessor;
import net.unusual.block_factorys_bosses.mixins.accessor.AnimationProcessorAccessor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationProcessor;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.EasingType;
import software.bernie.geckolib.animation.keyframe.AnimationPoint;
import software.bernie.geckolib.animation.keyframe.BoneAnimationQueue;
import software.bernie.geckolib.animation.state.BoneSnapshot;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MatrixAnimationProcessor<T extends GeoAnimatable> extends AnimationProcessor<T> {
   public MatrixAnimationProcessor(GeoModel<T> model) {
      super(model);
   }

   public void tickAnimation(
      T animatable, GeoModel<T> model, AnimatableManager<T> animatableManager, double animTime, AnimationState<T> state, boolean crashWhenCantFindBone
   ) {
      Map<String, BoneSnapshot> boneSnapshots = animatableManager.getBoneSnapshotCollection();

      for (GeoBone bone : this.getRegisteredBones()) {
         if (!boneSnapshots.containsKey(bone.getName())) {
            boneSnapshots.put(bone.getName(), BoneSnapshot.copy(bone.getInitialSnapshot()));
         }
      }

      for (AnimationController<T> controller : animatableManager.getAnimationControllers().values()) {
         if (this.reloadAnimations) {
            controller.forceAnimationReset();
            controller.getBoneAnimationQueues().clear();
         }

         ((AnimationControllerAccessor)controller).setIsJustStarting(animatableManager.isFirstTick());
         state.withController(controller);
         controller.process(model, state, ((AnimationProcessorAccessor)this).getBones(), boneSnapshots, animTime, crashWhenCantFindBone);

         for (BoneAnimationQueue boneAnimation : controller.getBoneAnimationQueues().values()) {
            GeoBone bone = boneAnimation.bone();
            BoneSnapshot snapshot = boneSnapshots.get(bone.getName());
            BoneSnapshot initialSnapshot = bone.getInitialSnapshot();
            MatrixAnimationProcessor.Vec3AnimationPoint pointVector = process(boneAnimation);
            AnimationPoint posXPoint = (AnimationPoint)boneAnimation.positionXQueue().poll();
            AnimationPoint posYPoint = (AnimationPoint)boneAnimation.positionYQueue().poll();
            AnimationPoint posZPoint = (AnimationPoint)boneAnimation.positionZQueue().poll();
            AnimationPoint scaleXPoint = (AnimationPoint)boneAnimation.scaleXQueue().poll();
            AnimationPoint scaleYPoint = (AnimationPoint)boneAnimation.scaleYQueue().poll();
            AnimationPoint scaleZPoint = (AnimationPoint)boneAnimation.scaleZQueue().poll();
            EasingType easingType = (EasingType)((AnimationControllerAccessor)controller).getOverrideEasingTypeFunction().apply(animatable);
            if (pointVector != null) {
               if (animatable instanceof YetiEntity yeti && yeti.isDead()) {
                  bone.setRotX(0.0F + initialSnapshot.getRotX());
                  bone.setRotY(0.0F + initialSnapshot.getRotY());
                  bone.setRotZ(0.0F + initialSnapshot.getRotZ());
               } else {
                  bone.setRotX((float)EasingType.lerpWithOverride(pointVector.x, easingType) + initialSnapshot.getRotX());
                  bone.setRotY((float)EasingType.lerpWithOverride(pointVector.y, easingType) + initialSnapshot.getRotY());
                  bone.setRotZ((float)EasingType.lerpWithOverride(pointVector.z, easingType) + initialSnapshot.getRotZ());
               }

               snapshot.updateRotation(bone.getRotX(), bone.getRotY(), bone.getRotZ());
               snapshot.startRotAnim();
               bone.markRotationAsChanged();
            }

            if (posXPoint != null && posYPoint != null && posZPoint != null) {
               bone.setPosX((float)EasingType.lerpWithOverride(posXPoint, easingType));
               bone.setPosY((float)EasingType.lerpWithOverride(posYPoint, easingType));
               bone.setPosZ((float)EasingType.lerpWithOverride(posZPoint, easingType));
               snapshot.updateOffset(bone.getPosX(), bone.getPosY(), bone.getPosZ());
               snapshot.startPosAnim();
               bone.markPositionAsChanged();
            }

            if (scaleXPoint != null && scaleYPoint != null && scaleZPoint != null) {
               bone.setScaleX((float)EasingType.lerpWithOverride(scaleXPoint, easingType));
               bone.setScaleY((float)EasingType.lerpWithOverride(scaleYPoint, easingType));
               bone.setScaleZ((float)EasingType.lerpWithOverride(scaleZPoint, easingType));
               snapshot.updateScale(bone.getScaleX(), bone.getScaleY(), bone.getScaleZ());
               snapshot.startScaleAnim();
               bone.markScaleAsChanged();
            }
         }
      }

      this.reloadAnimations = false;
      double resetTickLength = animatable.getBoneResetTime();

      for (GeoBone bone : this.getRegisteredBones()) {
         if (!bone.hasRotationChanged()) {
            BoneSnapshot initialSnapshot = bone.getInitialSnapshot();
            BoneSnapshot saveSnapshot = boneSnapshots.get(bone.getName());
            if (saveSnapshot.isRotAnimInProgress()) {
               saveSnapshot.stopRotAnim(animTime);
            }

            double percentageReset = resetTickLength == 0.0 ? 1.0 : Math.min((animTime - saveSnapshot.getLastResetRotationTick()) / resetTickLength, 1.0);
            float initialRotX = initialSnapshot.getRotX();
            float initialRotY = initialSnapshot.getRotY();
            float initialRotZ = initialSnapshot.getRotZ();
            float lastXRot = saveSnapshot.getRotX();
            float lastYRot = saveSnapshot.getRotY();
            float lastZRot = saveSnapshot.getRotZ();
            if (percentageReset == 0.0) {
               if (lastXRot != initialRotX && this.isSuspectedCompletedRotation(lastXRot)) {
                  lastXRot = initialRotX;
                  percentageReset = 1.0;
               }

               if (lastYRot != initialRotY && this.isSuspectedCompletedRotation(lastYRot)) {
                  lastYRot = initialRotY;
                  percentageReset = 1.0;
               }

               if (lastZRot != initialRotZ && this.isSuspectedCompletedRotation(lastZRot)) {
                  lastZRot = initialRotZ;
                  percentageReset = 1.0;
               }
            }

            bone.setRotX((float)Mth.lerp(percentageReset, lastXRot, initialRotX));
            bone.setRotY((float)Mth.lerp(percentageReset, lastYRot, initialRotY));
            bone.setRotZ((float)Mth.lerp(percentageReset, lastZRot, initialRotZ));
            if (percentageReset >= 1.0) {
               saveSnapshot.updateRotation(bone.getRotX(), bone.getRotY(), bone.getRotZ());
            }
         }

         if (!bone.hasPositionChanged()) {
            BoneSnapshot initialSnapshot = bone.getInitialSnapshot();
            BoneSnapshot saveSnapshot = boneSnapshots.get(bone.getName());
            if (saveSnapshot.isPosAnimInProgress()) {
               saveSnapshot.stopPosAnim(animTime);
            }

            double percentageReset = resetTickLength == 0.0 ? 1.0 : Math.min((animTime - saveSnapshot.getLastResetPositionTick()) / resetTickLength, 1.0);
            bone.setPosX((float)Mth.lerp(percentageReset, saveSnapshot.getOffsetX(), initialSnapshot.getOffsetX()));
            bone.setPosY((float)Mth.lerp(percentageReset, saveSnapshot.getOffsetY(), initialSnapshot.getOffsetY()));
            bone.setPosZ((float)Mth.lerp(percentageReset, saveSnapshot.getOffsetZ(), initialSnapshot.getOffsetZ()));
            if (percentageReset >= 1.0) {
               saveSnapshot.updateOffset(bone.getPosX(), bone.getPosY(), bone.getPosZ());
            }
         }

         if (!bone.hasScaleChanged()) {
            BoneSnapshot initialSnapshot = bone.getInitialSnapshot();
            BoneSnapshot saveSnapshot = boneSnapshots.get(bone.getName());
            if (saveSnapshot.isScaleAnimInProgress()) {
               saveSnapshot.stopScaleAnim(animTime);
            }

            double percentageReset = resetTickLength == 0.0 ? 1.0 : Math.min((animTime - saveSnapshot.getLastResetScaleTick()) / resetTickLength, 1.0);
            bone.setScaleX((float)Mth.lerp(percentageReset, saveSnapshot.getScaleX(), initialSnapshot.getScaleX()));
            bone.setScaleY((float)Mth.lerp(percentageReset, saveSnapshot.getScaleY(), initialSnapshot.getScaleY()));
            bone.setScaleZ((float)Mth.lerp(percentageReset, saveSnapshot.getScaleZ(), initialSnapshot.getScaleZ()));
            if (percentageReset >= 1.0) {
               saveSnapshot.updateScale(bone.getScaleX(), bone.getScaleY(), bone.getScaleZ());
            }
         }
      }

      this.getRegisteredBones().forEach(GeoBone::resetStateChanges);
      ((AnimatableManagerAccessor)animatableManager).invokeFinishFirstTick();
   }

   private boolean isSuspectedCompletedRotation(float lastRotation) {
      float rotations = Mth.abs(lastRotation / (float) (Math.PI * 2));
      float partialRotation = 1.0F - (rotations - (int)rotations);
      return partialRotation == 1.0F || partialRotation < 0.026 * rotations;
   }

   @Nullable
   private static MatrixAnimationProcessor.Vec3AnimationPoint process(BoneAnimationQueue boneAnimation) {
      AnimationPoint xPoint = (AnimationPoint)boneAnimation.rotationXQueue().poll();
      AnimationPoint yPoint = (AnimationPoint)boneAnimation.rotationYQueue().poll();
      AnimationPoint zPoint = (AnimationPoint)boneAnimation.rotationZQueue().poll();
      if (xPoint != null && yPoint != null && zPoint != null) {
         Vec3 start = new Vec3(xPoint.animationStartValue(), yPoint.animationStartValue(), zPoint.animationStartValue());
         Vec3 end = new Vec3(xPoint.animationEndValue(), yPoint.animationEndValue(), zPoint.animationEndValue());
         double[][] startMatrix = eulerToRotationMatrix(start);
         double[][] endMatrix = eulerToRotationMatrix(end);
         Vec3 reStart = rotationMatrixToEuler(startMatrix);
         Vec3 reEnd = rotationMatrixToEuler(endMatrix);
         return new MatrixAnimationProcessor.Vec3AnimationPoint(
            new AnimationPoint(xPoint.keyFrame(), xPoint.currentTick(), xPoint.transitionLength(), reStart.x, reEnd.x),
            new AnimationPoint(yPoint.keyFrame(), yPoint.currentTick(), yPoint.transitionLength(), reStart.y, reEnd.y),
            new AnimationPoint(zPoint.keyFrame(), zPoint.currentTick(), zPoint.transitionLength(), reStart.z, reEnd.z)
         );
      } else {
         return null;
      }
   }

   public static double[][] eulerToRotationMatrix(Vec3 angles) {
      double[][] x = new double[][]{{1.0, 0.0, 0.0}, {0.0, Math.cos(angles.x), -Math.sin(angles.x)}, {0.0, Math.sin(angles.x), Math.cos(angles.x)}};
      double[][] y = new double[][]{{Math.cos(angles.y), 0.0, Math.sin(angles.y)}, {0.0, 1.0, 0.0}, {-Math.sin(angles.y), 0.0, Math.cos(angles.y)}};
      double[][] z = new double[][]{{Math.cos(angles.z), -Math.sin(angles.z), 0.0}, {Math.sin(angles.z), Math.cos(angles.z), 0.0}, {0.0, 0.0, 1.0}};
      return multiplyMatrices(multiplyMatrices(z, y), x);
   }

   public static double[][] multiplyMatrices(double[][] a, double[][] b) {
      int aRows = a.length;
      int aCols = a[0].length;
      int bCols = b[0].length;
      double[][] result = new double[aRows][bCols];

      for (int i = 0; i < aRows; i++) {
         for (int j = 0; j < bCols; j++) {
            for (int k = 0; k < aCols; k++) {
               result[i][j] = result[i][j] + a[i][k] * b[k][j];
            }
         }
      }

      return result;
   }

   public static Vec3 rotationMatrixToEuler(double[][] matrix) {
      if (matrix[2][0] < 1.0) {
         return matrix[2][0] > -1.0
            ? new Vec3(Math.atan2(matrix[2][1], matrix[2][2]), Math.asin(-matrix[2][0]), Math.atan2(matrix[1][0], matrix[0][0]))
            : new Vec3(Math.atan2(-matrix[1][2], matrix[1][1]), Math.PI / 2, 0.0);
      } else {
         return new Vec3(Math.atan2(-matrix[1][2], matrix[1][1]), -Math.PI / 2, 0.0);
      }
   }

   private record Vec3AnimationPoint(AnimationPoint x, AnimationPoint y, AnimationPoint z) {
   }
}
