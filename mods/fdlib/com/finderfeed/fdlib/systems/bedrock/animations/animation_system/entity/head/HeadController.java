package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.head;

import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimatedObject;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimationSystem;
import com.finderfeed.fdlib.systems.bedrock.models.FDModel;
import com.finderfeed.fdlib.systems.bedrock.models.FDModelPart;
import com.finderfeed.fdlib.util.math.FDMathUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import org.joml.Matrix3d;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class HeadController<T extends Mob & IHasHead<T> & AnimatedObject> {
   private HeadControllerContainer<T> container;
   private FDModel fdModel;
   private T entity;
   private String headBone;
   private float headTransitionSpeed;
   private float currentHeadRotationX = 0.0F;
   private float currentHeadRotationY = 0.0F;
   private float oldHeadRotationX = 0.0F;
   private float oldHeadRotationY = 0.0F;
   private boolean isHeadReversed = false;

   public HeadController(HeadControllerContainer<T> container, FDModel model, String headBone, T entity) {
      this.entity = entity;
      this.headBone = headBone;
      this.fdModel = model;
      this.headTransitionSpeed = entity.getHeadRotSpeed();
      this.container = container;
   }

   public void clientTick() {
      this.oldHeadRotationX = this.currentHeadRotationX;
      this.oldHeadRotationY = this.currentHeadRotationY;
      Vector2f angles = this.getLookAtTargetAngles();
      float maxXRot = this.entity.getMaxHeadXRot();
      float maxYRot = this.entity.getMaxHeadYRot();
      float newY = this.getRotation(this.currentHeadRotationY, Mth.clamp(FDMathUtil.convertMCYRotationToNormal(angles.y), -maxYRot, maxYRot));
      float newX = this.getRotation(this.currentHeadRotationX, Mth.clamp(FDMathUtil.convertMCYRotationToNormal(angles.x), -maxXRot, maxXRot));
      this.currentHeadRotationY = newY;
      this.currentHeadRotationX = newX;
   }

   public void onControllerModeChanged(HeadControllerContainer.Mode oldMode, HeadControllerContainer.Mode newMode) {
      if (newMode == HeadControllerContainer.Mode.LOOK) {
         this.entity.getAnimationSystem().applyAnimations(this.fdModel, 0.0F);
         FDModelPart part = this.fdModel.getModelPart(this.headBone);
         this.currentHeadRotationX = FDMathUtil.convertMCYRotationToNormal(this.currentHeadRotationX + part.getXRot());
         this.currentHeadRotationY = FDMathUtil.convertMCYRotationToNormal(this.currentHeadRotationY + part.getYRot());
         this.oldHeadRotationX = this.currentHeadRotationX;
         this.oldHeadRotationY = this.currentHeadRotationY;
      }
   }

   private Vector2f getLookAtTargetAngles() {
      if (this.container.getControllersMode() == HeadControllerContainer.Mode.ANIMATION) {
         return new Vector2f();
      }

      AnimationSystem system = this.entity.getAnimationSystem();
      this.fdModel.resetTransformations();
      system.applyAnimations(this.fdModel, 0.0F);
      float yRot = this.entity.getYRot();
      this.fdModel.main.addYRot(-yRot);
      FDModelPart head = this.fdModel.getModelPart(this.headBone);
      head.setXRot(head.initRotation.x);
      head.setYRot(head.initRotation.y);
      head.setZRot(head.initRotation.z);
      Matrix4f transform = this.fdModel.getModelPartTransformation(head);
      Vector3f position = transform.transformPosition(new Vector3f());
      Vector3f wposition = position.add((float)this.entity.getX(), (float)this.entity.getY(), (float)this.entity.getZ(), new Vector3f());
      Vector3d playerPosition = new Vector3d(this.container.getWantedX(), this.container.getWantedY(), this.container.getWantedZ());
      Vector3d between = playerPosition.sub(wposition, new Vector3d());
      Vector3d axisX = this.floatToDouble(transform.transformDirection(new Vector3f(1.0F, 0.0F, 0.0F))).normalize();
      Vector3d axisY = this.floatToDouble(transform.transformDirection(new Vector3f(0.0F, 1.0F, 0.0F))).normalize();
      Vector3d axisZ = this.floatToDouble(transform.transformDirection(new Vector3f(0.0F, 0.0F, 1.0F))).normalize();
      Vector3d vec = this.fromOtherBasisToEuclidian(axisX, axisY, axisZ, between);
      float verticalAngle;
      float horizontalAngle;
      if (!this.isHeadReversed()) {
         verticalAngle = (float)Math.toDegrees(Math.atan2(vec.x, vec.z));
         double a = Math.sqrt(vec.z * vec.z + vec.x * vec.x);
         horizontalAngle = -((float)Math.toDegrees(Math.atan2(vec.y, a)));
      } else {
         verticalAngle = 180.0F + (float)Math.toDegrees(Math.atan2(vec.x, vec.z));
         double a = Math.sqrt(vec.z * vec.z + vec.x * vec.x);
         horizontalAngle = (float)Math.toDegrees(Math.atan2(vec.y, a));
      }

      return new Vector2f(horizontalAngle, verticalAngle);
   }

   public float getHeadTransitionSpeed() {
      return this.headTransitionSpeed;
   }

   public T getEntity() {
      return this.entity;
   }

   private float getRotation(float currentRotation, float targetY) {
      if ((!(currentRotation >= 0.0F) || !(targetY >= 0.0F)) && (!(currentRotation < 0.0F) || !(targetY < 0.0F))) {
         if (currentRotation < 0.0F) {
            float dist = -currentRotation + targetY;
            if (dist > 180.0F) {
               currentRotation -= this.getHeadTransitionSpeed();
               if (currentRotation < -180.0F) {
                  currentRotation += 360.0F;
                  return Mth.clamp(currentRotation, targetY, 2.1474836E9F);
               } else {
                  return Mth.clamp(currentRotation, -2.1474836E9F, targetY);
               }
            } else {
               return Mth.clamp(currentRotation + this.getHeadTransitionSpeed(), -2.1474836E9F, targetY);
            }
         } else {
            float dist = -targetY + currentRotation;
            if (dist > 180.0F) {
               currentRotation += this.getHeadTransitionSpeed();
               if (currentRotation > 180.0F) {
                  currentRotation -= 360.0F;
                  return Mth.clamp(currentRotation, -2.1474836E9F, targetY);
               } else {
                  return Mth.clamp(currentRotation, targetY, 2.1474836E9F);
               }
            } else {
               return Mth.clamp(currentRotation - this.getHeadTransitionSpeed(), targetY, 2.1474836E9F);
            }
         }
      } else {
         int direction = targetY - currentRotation > 0.0F ? 1 : -1;
         if (currentRotation >= 0.0F) {
            return direction == 1
               ? Mth.clamp(currentRotation + this.getHeadTransitionSpeed() * direction, -2.1474836E9F, targetY)
               : Mth.clamp(currentRotation + this.getHeadTransitionSpeed() * direction, targetY, 2.1474836E9F);
         } else {
            return direction == 1
               ? Mth.clamp(currentRotation + this.getHeadTransitionSpeed() * direction, -2.1474836E9F, targetY)
               : Mth.clamp(currentRotation + this.getHeadTransitionSpeed() * direction, targetY, 2.1474836E9F);
         }
      }
   }

   public float getCurrentHeadRotationX() {
      return this.currentHeadRotationX;
   }

   public float getCurrentHeadRotationY() {
      return this.currentHeadRotationY;
   }

   public Vector2f getCurrentHeadAngles(float pticks) {
      return new Vector2f(
         FDMathUtil.lerpAround(this.oldHeadRotationX, this.currentHeadRotationX, -180.0F, 180.0F, pticks),
         FDMathUtil.lerpAround(this.oldHeadRotationY, this.currentHeadRotationY, -180.0F, 180.0F, pticks)
      );
   }

   public boolean isHeadReversed() {
      return this.isHeadReversed;
   }

   public void setHeadReversed(boolean headReversed) {
      this.isHeadReversed = headReversed;
   }

   private Vector3d floatToDouble(Vector3f v) {
      return new Vector3d(v.x, v.y, v.z);
   }

   private Vector3d fromEuclidianToOtherBasis(Vector3d nbx, Vector3d nby, Vector3d nbz, Vector3d point) {
      Matrix3d matrix3d = new Matrix3d(nbx.x, nbx.y, nbx.z, nby.x, nby.y, nby.z, nbz.x, nbz.y, nbz.z);
      return matrix3d.transform(point, new Vector3d());
   }

   private Vector3d fromOtherBasisToEuclidian(Vector3d nbx, Vector3d nby, Vector3d nbz, Vector3d point) {
      Matrix3d matrix3d = new Matrix3d(nbx.x, nbx.y, nbx.z, nby.x, nby.y, nby.z, nbz.x, nbz.y, nbz.z).invert();
      return matrix3d.transform(point, new Vector3d());
   }
}
