package com.github.L_Ender.cataclysm.client.animation;

import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class AnimationUtils {
   public static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

   public static Vec3 calculateViewVector(float xRot, float yRot) {
      float f = xRot * (float) (Math.PI / 180.0);
      float f1 = -yRot * (float) (Math.PI / 180.0);
      float f2 = Mth.cos(f1);
      float f3 = Mth.sin(f1);
      float f4 = Mth.cos(f);
      float f5 = Mth.sin(f);
      return new Vec3(f3 * f4, -f5, f2 * f4);
   }

   public static void animateAmplitude(HierarchicalModel<?> model, AnimationState anim, AnimationDefinition animDef, float tick, float speed, float amplitude) {
      anim.updateTime(tick, speed);
      anim.ifStarted(p_233392_ -> KeyframeAnimations.animate(model, animDef, p_233392_.getAccumulatedTime(), amplitude, ANIMATION_VECTOR_CACHE));
   }

   public static void animateWalkAmplitude(
      HierarchicalModel<?> model, AnimationDefinition animDef, float limbSwing, float limbSwingAmount, float speed, float amplitude
   ) {
      long i = (long)(limbSwing * 50.0F * speed);
      float f = limbSwingAmount * amplitude;
      KeyframeAnimations.animate(model, animDef, i, f, ANIMATION_VECTOR_CACHE);
   }

   public static void progressPositionPrev(ModelPart model, float progress, float rotX, float rotY, float rotZ, float divisor) {
      model.x += progress * rotX / divisor;
      model.y += progress * rotY / divisor;
      model.z += progress * rotZ / divisor;
   }

   public static void progressRotationPrev(ModelPart model, float progress, float x, float y, float z, float divisor) {
      model.xRot += progress * x / divisor;
      model.yRot += progress * y / divisor;
      model.zRot += progress * z / divisor;
   }

   public static AnimationChannel molangRotationChannel(
      float length, float step, FloatUnaryOperator degXFunc, FloatUnaryOperator degYFunc, FloatUnaryOperator degZFunc
   ) {
      List<Keyframe> frames = new ArrayList<>();
      float t = 0.0F;

      while (t < length - 1.0E-6F) {
         float degX = degXFunc.apply(t);
         float degY = degYFunc.apply(t);
         float degZ = degZFunc.apply(t);
         frames.add(new Keyframe(t, KeyframeAnimations.degreeVec(degX, degY, degZ), Interpolations.CATMULLROM));
         t += step;
      }

      frames.add(
         new Keyframe(length, KeyframeAnimations.degreeVec(degXFunc.apply(length), degYFunc.apply(length), degZFunc.apply(length)), Interpolations.CATMULLROM)
      );
      return new AnimationChannel(Targets.ROTATION, frames.toArray(new Keyframe[0]));
   }

   public static AnimationChannel molangPostionChannel(
      float length, float step, FloatUnaryOperator degXFunc, FloatUnaryOperator degYFunc, FloatUnaryOperator degZFunc
   ) {
      List<Keyframe> frames = new ArrayList<>();
      float t = 0.0F;

      while (t < length - 1.0E-6F) {
         float degX = degXFunc.apply(t);
         float degY = degYFunc.apply(t);
         float degZ = degZFunc.apply(t);
         frames.add(new Keyframe(t, KeyframeAnimations.posVec(degX, degY, degZ), Interpolations.CATMULLROM));
         t += step;
      }

      frames.add(
         new Keyframe(length, KeyframeAnimations.posVec(degXFunc.apply(length), degYFunc.apply(length), degZFunc.apply(length)), Interpolations.CATMULLROM)
      );
      return new AnimationChannel(Targets.POSITION, frames.toArray(new Keyframe[0]));
   }
}
