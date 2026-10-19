package it.hurts.sskirillss.relics.utils.data;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public class AnimationData {
   private final List<Pair<Integer, Integer>> frames = new ArrayList<>();

   public int getLength() {
      int time = 0;

      for (Pair<Integer, Integer> pair : this.frames) {
         time += pair.getRight();
      }

      return time;
   }

   public Pair<Integer, Integer> getFrameByTime(long time) {
      int frames = this.getFrames().size();
      long remainder = time % this.getLength();

      int index;
      for (index = 0; remainder > 0L; index = index + 1 >= frames ? 0 : index + 1) {
         Pair<Integer, Integer> pair = this.getFrames().get(index);
         remainder -= ((Integer)pair.getRight()).intValue();
      }

      return this.getFrames().get(index);
   }

   public static AnimationData builder() {
      return new AnimationData();
   }

   public static AnimationData construct(int texHeight, int patternHeight, int frameTime) {
      AnimationData data = builder();

      for (int i = 0; i < texHeight / patternHeight; i++) {
         data.frame(i, frameTime);
      }

      return data;
   }

   public AnimationData frame(int index, int time) {
      this.frames.add(Pair.of(index, time));
      return this;
   }

   public List<Pair<Integer, Integer>> getFrames() {
      return this.frames;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof AnimationData other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         Object this$frames = this.getFrames();
         Object other$frames = other.getFrames();
         return this$frames == null ? other$frames == null : this$frames.equals(other$frames);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof AnimationData;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $frames = this.getFrames();
      return result * 59 + ($frames == null ? 43 : $frames.hashCode());
   }

   @Override
   public String toString() {
      return "AnimationData(frames=" + this.getFrames() + ")";
   }
}
