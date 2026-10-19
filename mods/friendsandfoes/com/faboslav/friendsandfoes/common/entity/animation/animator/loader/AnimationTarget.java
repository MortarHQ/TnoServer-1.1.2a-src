package com.faboslav.friendsandfoes.common.entity.animation.animator.loader;

import com.faboslav.friendsandfoes.common.entity.animation.AnimationChannel;
import org.joml.Vector3f;

public record AnimationTarget(AnimationChannel.Target channelTarget, AnimationKeyframeTarget keyframeTarget, AnimationKeyframeTarget inverseKeyframeTarget) {
   public static final AnimationTarget POSITION = new AnimationTarget(AnimationChannel.Target.POSITION, AnimationTarget::posVec, AnimationTarget::posVec);
   public static final AnimationTarget ROTATION = new AnimationTarget(
      AnimationChannel.Target.ROTATION, AnimationTarget::degreeVec, AnimationTarget::inverseDegreeVec
   );
   public static final AnimationTarget SCALE = new AnimationTarget(AnimationChannel.Target.SCALE, AnimationTarget::scaleVec, AnimationTarget::inverseScaleVec);

   private static Vector3f inverseDegreeVec(float x, float y, float z) {
      return new Vector3f(x / (float) (Math.PI / 180.0), y / (float) (Math.PI / 180.0), z / (float) (Math.PI / 180.0));
   }

   private static Vector3f inverseScaleVec(double x, double y, double z) {
      return new Vector3f((float)(x + 1.0), (float)(y + 1.0), (float)(z + 1.0));
   }

   private static Vector3f posVec(float f, float g, float h) {
      return new Vector3f(f, -g, h);
   }

   private static Vector3f degreeVec(float f, float g, float h) {
      return new Vector3f(f * (float) (Math.PI / 180.0), g * (float) (Math.PI / 180.0), h * (float) (Math.PI / 180.0));
   }

   private static Vector3f scaleVec(double d, double e, double f) {
      return new Vector3f((float)(d - 1.0), (float)(e - 1.0), (float)(f - 1.0));
   }

   @Override
   public boolean equals(Object obj) {
      return this == obj;
   }

   @Override
   public int hashCode() {
      return System.identityHashCode(this);
   }
}
