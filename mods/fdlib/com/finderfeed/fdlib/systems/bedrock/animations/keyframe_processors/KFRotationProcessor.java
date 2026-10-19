package com.finderfeed.fdlib.systems.bedrock.animations.keyframe_processors;

import com.finderfeed.fdlib.systems.bedrock.animations.AnimationContext;
import com.finderfeed.fdlib.systems.bedrock.animations.AnimationUtil;
import com.finderfeed.fdlib.systems.bedrock.animations.InterpolationMode;
import com.finderfeed.fdlib.systems.bedrock.animations.KeyFrame;
import com.finderfeed.fdlib.systems.bedrock.models.FDModelPart;
import com.finderfeed.fdlib.util.math.FDMathUtil;
import java.util.List;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

public class KFRotationProcessor extends KeyFrameProcessor {
   public KFRotationProcessor(List<KeyFrame> keyFrames, boolean sorted) {
      super(keyFrames, sorted);
   }

   @Override
   public void applyTransformations(AnimationContext context, FDModelPart model, float time) {
      Vector3f v = this.getCurrentTransformation(context, time);
      model.addXRot(v.x);
      model.addYRot(v.y);
      model.addZRot(v.z);
   }

   @Override
   public Vector3f getCurrentTransformation(AnimationContext context, float time) {
      List<KeyFrame> currentAndNext = this.getKeyFrames().getValues((int)Math.floor(time), 0, 1);
      KeyFrame current = currentAndNext.get(0);
      KeyFrame next = currentAndNext.get(1);
      if (current.interpolationMode == InterpolationMode.LINEAR && (next == null || next.interpolationMode == InterpolationMode.LINEAR)) {
         float actualTime = time;
         Vector3f v1 = null;
         if (actualTime < current.time) {
            v1 = current.getPreValue(context);
            if (v1 == null) {
               v1 = current.getPostValue(context);
            }
         } else {
            v1 = current.getPostValue(context);
         }

         if (next != null) {
            Vector3f v2 = next.getPreValue(context);
            if (v2 == null) {
               v2 = next.getPostValue(context);
            }

            float p = (time - current.time) / (next.time - current.time);
            p = Mth.clamp(p, 0.0F, 1.0F);
            return FDMathUtil.interpolateVectors(v1, v2, p);
         } else {
            return v1;
         }
      } else {
         return AnimationUtil.catmullRomThroughKeyFrames(context, this.getKeyFrames(), time, 0.0F);
      }
   }
}
