package team.creative.creativecore.common.util.math.vec;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class RangedBitSet {
   protected BitSet set;
   protected int min;
   protected int max;

   public RangedBitSet(int min, int max) {
      this.min = min;
      this.max = max;
      if (min >= max) {
         throw new IllegalArgumentException("min has to be smaller than max!");
      }

      this.set = new BitSet(max - min);
   }

   public int getMin() {
      return this.min;
   }

   public int getMax() {
      return this.max;
   }

   public void add(int value) {
      if (value >= this.min && value <= this.max) {
         this.set.set(value - this.min, true);
      }
   }

   public List<RangedBitSet.BitRange> getRanges() {
      List<RangedBitSet.BitRange> ranges = new ArrayList<>();
      int index = 0;

      while (index < this.max - this.min) {
         int nextIndex = this.set.nextSetBit(index + 1);
         if (nextIndex == -1) {
            nextIndex = this.max - this.min;
         }

         ranges.add(new RangedBitSet.BitRange(index + this.min, nextIndex + this.min));
         index = nextIndex;
      }

      return ranges;
   }

   public record BitRange(int min, int max) {
   }
}
