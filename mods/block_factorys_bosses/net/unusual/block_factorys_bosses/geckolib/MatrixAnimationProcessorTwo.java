package net.unusual.block_factorys_bosses.geckolib;

import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.entity.boss.knight.UnderworldKnightEntity;
import net.unusual.block_factorys_bosses.mixins.accessor.AnimatableManagerAccessor;
import net.unusual.block_factorys_bosses.mixins.accessor.AnimationControllerAccessor;
import net.unusual.block_factorys_bosses.mixins.accessor.AnimationProcessorAccessor;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3d;
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
public class MatrixAnimationProcessorTwo<T extends GeoAnimatable> extends AnimationProcessor<T> {
   public MatrixAnimationProcessorTwo(GeoModel<T> model) {
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
            EasingType easingType = (EasingType)((AnimationControllerAccessor)controller).getOverrideEasingTypeFunction().apply(animatable);
            if (!(animatable instanceof UnderworldKnightEntity) || !bone.getName().startsWith("leg") && !bone.getName().startsWith("camera")) {
               MatrixAnimationProcessorTwo.MatrixPoint point = process(boneAnimation);
               if (point != null) {
                  double lerp = EasingType.lerpWithOverride(point.lerpPoint, easingType);
                  Matrix3d result = new Matrix3d();

                  for (int i = 0; i < 3; i++) {
                     for (int j = 0; j < 3; j++) {
                        result.set(i, j, (1.0 - lerp) * point.start.get(i, j) + lerp * point.end.get(i, j));
                     }
                  }

                  Vec3 reEnd = rotationMatrixToEuler(result);
                  bone.setRotX((float)reEnd.x + initialSnapshot.getRotX());
                  bone.setRotY((float)reEnd.y + initialSnapshot.getRotY());
                  bone.setRotZ((float)reEnd.z + initialSnapshot.getRotZ());
                  snapshot.updateRotation(bone.getRotX(), bone.getRotY(), bone.getRotZ());
                  snapshot.startRotAnim();
                  bone.markRotationAsChanged();
               }
            } else {
               AnimationPoint rotXPoint = (AnimationPoint)boneAnimation.rotationXQueue().poll();
               AnimationPoint rotYPoint = (AnimationPoint)boneAnimation.rotationYQueue().poll();
               AnimationPoint rotZPoint = (AnimationPoint)boneAnimation.rotationZQueue().poll();
               if (rotXPoint != null && rotYPoint != null && rotZPoint != null) {
                  bone.setRotX((float)EasingType.lerpWithOverride(rotXPoint, easingType) + initialSnapshot.getRotX());
                  bone.setRotY((float)EasingType.lerpWithOverride(rotYPoint, easingType) + initialSnapshot.getRotY());
                  bone.setRotZ((float)EasingType.lerpWithOverride(rotZPoint, easingType) + initialSnapshot.getRotZ());
                  snapshot.updateRotation(bone.getRotX(), bone.getRotY(), bone.getRotZ());
                  snapshot.startRotAnim();
                  bone.markRotationAsChanged();
               }
            }

            AnimationPoint posXPoint = (AnimationPoint)boneAnimation.positionXQueue().poll();
            AnimationPoint posYPoint = (AnimationPoint)boneAnimation.positionYQueue().poll();
            AnimationPoint posZPoint = (AnimationPoint)boneAnimation.positionZQueue().poll();
            AnimationPoint scaleXPoint = (AnimationPoint)boneAnimation.scaleXQueue().poll();
            AnimationPoint scaleYPoint = (AnimationPoint)boneAnimation.scaleYQueue().poll();
            AnimationPoint scaleZPoint = (AnimationPoint)boneAnimation.scaleZQueue().poll();
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
   private static MatrixAnimationProcessorTwo.MatrixPoint process(BoneAnimationQueue boneAnimation) {
      AnimationPoint xPoint = (AnimationPoint)boneAnimation.rotationXQueue().poll();
      AnimationPoint yPoint = (AnimationPoint)boneAnimation.rotationYQueue().poll();
      AnimationPoint zPoint = (AnimationPoint)boneAnimation.rotationZQueue().poll();
      if (xPoint != null && yPoint != null && zPoint != null) {
         Vec3 start = new Vec3(xPoint.animationStartValue(), yPoint.animationStartValue(), zPoint.animationStartValue());
         Vec3 end = new Vec3(xPoint.animationEndValue(), yPoint.animationEndValue(), zPoint.animationEndValue());
         Matrix3d startMatrix = eulerToRotationMatrix(start);
         Matrix3d endMatrix = eulerToRotationMatrix(end);
         return new MatrixAnimationProcessorTwo.MatrixPoint(
            new AnimationPoint(xPoint.keyFrame(), xPoint.currentTick(), xPoint.transitionLength(), 0.0, 1.0), startMatrix, endMatrix
         );
      } else {
         return null;
      }
   }

   public static Matrix3d eulerToRotationMatrix(Vec3 angles) {
      float a = (float)Math.cos(angles.x);
      float b = (float)Math.sin(angles.x);
      float c = (float)Math.cos(angles.y);
      float d = (float)Math.sin(angles.y);
      float e = (float)Math.cos(angles.z);
      float f = (float)Math.sin(angles.z);
      return new Matrix3d(c * e, a * f + b * e * d, b * f - a * e * d, -c * f, a * e - b * f * d, b * e + a * f * d, d, -b * c, a * c);
   }

   public static Vec3 rotationMatrixToEuler(Matrix3d matrix) {
      double y = Math.asin(Math.clamp(matrix.m20, -1.0, 1.0));
      double x;
      double z;
      if (Math.abs(matrix.m20) < 0.9999999) {
         x = Math.atan2(-matrix.m21, matrix.m22);
         z = Math.atan2(-matrix.m10, matrix.m00);
      } else {
         x = Math.atan2(matrix.m12, matrix.m11);
         z = 0.0;
      }

      return new Vec3(x, y, z);
   }

   private record MatrixPoint(AnimationPoint lerpPoint, Matrix3d start, Matrix3d end) {
   }
}
