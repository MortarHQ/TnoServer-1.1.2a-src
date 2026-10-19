package net.unusual.block_factorys_bosses.util;

import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction.Axis;
import org.joml.Vector3f;
import software.bernie.geckolib.animation.EasingType;
import software.bernie.geckolib.animation.keyframe.AnimationPoint;
import software.bernie.geckolib.animation.keyframe.Keyframe;
import software.bernie.geckolib.animation.keyframe.KeyframeLocation;
import software.bernie.geckolib.animation.keyframe.KeyframeStack;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.value.Constant;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class AnimationUtil {
   @Nullable
   public static Vector3f getAnimationPointAtTick(KeyframeStack<Keyframe<MathValue>> keyFrames, double tick, boolean isRotation) {
      if (keyFrames.xKeyframes().isEmpty()) {
         return null;
      }

      Vector3f vec3f = new Vector3f();

      for (Axis axis : Axis.values()) {
         List<Keyframe<MathValue>> frames = switch (axis) {
            case X -> keyFrames.xKeyframes();
            case Y -> keyFrames.yKeyframes();
            case Z -> keyFrames.zKeyframes();
            default -> throw new MatchException(null, null);
         };
         KeyframeLocation<Keyframe<MathValue>> location = getCurrentKeyFrameLocation(frames, tick);
         Keyframe<MathValue> currentFrame = location.keyframe();
         double startValue = currentFrame.startValue().get();
         double endValue = currentFrame.endValue().get();
         if (isRotation) {
            if (!(currentFrame.startValue() instanceof Constant)) {
               startValue = Math.toRadians(startValue);
               if (axis == Axis.X || axis == Axis.Y) {
                  startValue *= -1.0;
               }
            }

            if (!(currentFrame.endValue() instanceof Constant)) {
               endValue = Math.toRadians(endValue);
               if (axis == Axis.X || axis == Axis.Y) {
                  endValue *= -1.0;
               }
            }
         }

         float var = (float)EasingType.lerpWithOverride(
            new AnimationPoint(currentFrame, location.startTick(), currentFrame.length(), startValue, endValue), null
         );
         vec3f.setComponent(axis.ordinal(), var);
      }

      return vec3f;
   }

   private static KeyframeLocation<Keyframe<MathValue>> getCurrentKeyFrameLocation(List<Keyframe<MathValue>> frames, double ageInTicks) {
      double totalFrameTime = 0.0;

      for (Keyframe<MathValue> frame : frames) {
         totalFrameTime += frame.length();
         if (totalFrameTime > ageInTicks) {
            return new KeyframeLocation(frame, ageInTicks - (totalFrameTime - frame.length()));
         }
      }

      return new KeyframeLocation(frames.getLast(), ageInTicks);
   }
}
