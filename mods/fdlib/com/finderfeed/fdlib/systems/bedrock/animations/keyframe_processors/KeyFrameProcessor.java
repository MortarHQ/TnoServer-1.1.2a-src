package com.finderfeed.fdlib.systems.bedrock.animations.keyframe_processors;

import com.finderfeed.fdlib.data_structures.IntRangedList;
import com.finderfeed.fdlib.systems.bedrock.animations.AnimationContext;
import com.finderfeed.fdlib.systems.bedrock.animations.KeyFrame;
import com.finderfeed.fdlib.systems.bedrock.models.FDModelPart;
import java.util.List;
import org.joml.Vector3f;

public abstract class KeyFrameProcessor {
   private IntRangedList<KeyFrame> keyFrames;

   public KeyFrameProcessor(List<KeyFrame> keyFrames, boolean sorted) {
      if (keyFrames != null && !keyFrames.isEmpty()) {
         this.keyFrames = new IntRangedList<>(keyFrames, sorted, keyFrame -> keyFrame.time);
      }
   }

   public abstract void applyTransformations(AnimationContext var1, FDModelPart var2, float var3);

   public abstract Vector3f getCurrentTransformation(AnimationContext var1, float var2);

   public IntRangedList<KeyFrame> getKeyFrames() {
      return this.keyFrames;
   }

   public boolean isActive() {
      return this.keyFrames != null;
   }
}
