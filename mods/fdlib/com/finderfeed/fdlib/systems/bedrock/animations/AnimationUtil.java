package com.finderfeed.fdlib.systems.bedrock.animations;

import com.finderfeed.fdlib.data_structures.IntRangedList;
import com.finderfeed.fdlib.util.math.FDMathUtil;
import java.util.List;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

public class AnimationUtil {
   public static Vector3f catmullRomThroughKeyFrames(AnimationContext context, IntRangedList<KeyFrame> keyFrames, float time, float partialTick) {
      Animation.LoopMode loopMode = context.getCurrentLoopMode();
      if (loopMode == Animation.LoopMode.LOOP && keyFrames.size() > 2 && !context.getAnimation().isTransition()) {
         int animTime = context.getAnimation().getAnimTime();
         List<KeyFrame> frames = keyFrames.getValues((int)Math.floor(time), 1, 2);
         KeyFrame previous = frames.get(0);
         KeyFrame current = frames.get(1);
         KeyFrame next = frames.get(2);
         KeyFrame next2 = frames.get(3);
         int state = 0;
         if (next2 == null) {
            if (next != null) {
               state = 1;
               next2 = keyFrames.get(1);
            } else {
               state = 2;
               next2 = keyFrames.get(2);
            }
         }

         if (next == null) {
            next = keyFrames.get(1);
         }

         if (previous == null) {
            state = -1;
            KeyFrame last = keyFrames.getLast();
            if (last.time == animTime) {
               previous = keyFrames.get(keyFrames.size() - 2);
            } else {
               previous = last;
            }
         }

         float p;
         switch (state) {
            case 2: {
               float localTime = 0.0F;
               float timeBetween = next.time;
               p = Mth.clamp(localTime / timeBetween, 0.0F, 1.0F);
               break;
            }
            default: {
               float localTime = time - current.time;
               float timeBetween = next.time - current.time;
               p = Mth.clamp(localTime / timeBetween, 0.0F, 1.0F);
            }
         }

         return FDMathUtil.catmullrom(previous.getPostValue(context), current.getPostValue(context), next.getPostValue(context), next2.getPostValue(context), p);
      } else {
         List<KeyFrame> frames = keyFrames.getValues((int)Math.floor(time), 1, 2);
         KeyFrame current = frames.get(1);
         KeyFrame next = frames.get(2);
         if (next == null) {
            return current.getPostValue(context);
         }

         float localTime = time - current.time;
         float timeBetween = next.time - current.time;
         float p = Mth.clamp(localTime / timeBetween, 0.0F, 1.0F);
         KeyFrame f1 = frames.get(0);
         KeyFrame f4 = frames.get(3);
         return FDMathUtil.catmullrom(
            f1 != null ? f1.getPostValue(context) : null,
            current.getPostValue(context),
            next.getPostValue(context),
            f4 != null ? f4.getPostValue(context) : null,
            p
         );
      }
   }
}
