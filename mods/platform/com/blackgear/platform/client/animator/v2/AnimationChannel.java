package com.blackgear.platform.client.animator.v2;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public record AnimationChannel(AnimationChannel.Target target, Keyframe... keyframes) {
   @OnlyIn(Dist.CLIENT)
   public interface Interpolation {
      Vector3f apply(Vector3f var1, AnimationChannel.Query var2, float var3, Keyframe[] var4, int var5, int var6, float var7);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Interpolations {
      public static final AnimationChannel.Interpolation LINEAR = (result, query, progress, keyframes, startIndex, endIndex, scale) -> {
         Vector3f startTarget = keyframes[startIndex].target().resolve(query);
         Vector3f endTarget = keyframes[endIndex].target().resolve(query);
         return startTarget.lerp(endTarget, progress, result).mul(scale);
      };
      public static final AnimationChannel.Interpolation CATMULLROM = (result, query, progress, keyframes, startIndex, endIndex, scale) -> {
         Vector3f beforeStart = keyframes[Math.max(0, startIndex - 1)].target().resolve(query);
         Vector3f start = keyframes[startIndex].target().resolve(query);
         Vector3f end = keyframes[endIndex].target().resolve(query);
         Vector3f afterEnd = keyframes[Math.min(keyframes.length - 1, endIndex + 1)].target().resolve(query);
         result.set(
            Mth.catmullrom(progress, beforeStart.x(), start.x(), end.x(), afterEnd.x()) * scale,
            Mth.catmullrom(progress, beforeStart.y(), start.y(), end.y(), afterEnd.y()) * scale,
            Mth.catmullrom(progress, beforeStart.z(), start.z(), end.z(), afterEnd.z()) * scale
         );
         return result;
      };
   }

   @OnlyIn(Dist.CLIENT)
   public interface Query {
      float animTime();
   }

   @OnlyIn(Dist.CLIENT)
   public interface Target {
      void apply(ModelPart var1, Vector3f var2);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Targets {
      public static final AnimationChannel.Target POSITION = ModelPart::offsetPos;
      public static final AnimationChannel.Target ROTATION = ModelPart::offsetRotation;
      public static final AnimationChannel.Target SCALE = ModelPart::offsetScale;
   }

   @OnlyIn(Dist.CLIENT)
   public interface Value {
      Vector3f resolve(AnimationChannel.Query var1);
   }
}
