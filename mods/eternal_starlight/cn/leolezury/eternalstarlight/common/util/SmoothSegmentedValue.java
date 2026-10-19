package cn.leolezury.eternalstarlight.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record SmoothSegmentedValue(List<SmoothSegmentedValue.Segment> segments) {
   public static SmoothSegmentedValue of(Easing easing, float from, float to, float size) {
      return new SmoothSegmentedValue(List.of(new SmoothSegmentedValue.Segment(easing, from, to, size)));
   }

   public static SmoothSegmentedValue constant(float value) {
      SmoothSegmentedValue.Segment seg = new SmoothSegmentedValue.Segment(v -> v, value, value, 1.0F);
      return new SmoothSegmentedValue(Collections.singletonList(seg));
   }

   public SmoothSegmentedValue add(Easing easing, float from, float to, float size) {
      List<SmoothSegmentedValue.Segment> newSegments = new ArrayList<>(this.segments);
      newSegments.add(new SmoothSegmentedValue.Segment(easing, from, to, size));
      return new SmoothSegmentedValue(Collections.unmodifiableList(newSegments));
   }

   public float calculate(float progress) {
      if (this.segments.isEmpty()) {
         return 0.0F;
      }

      float accumulated = 0.0F;

      for (SmoothSegmentedValue.Segment seg : this.segments) {
         float size = seg.size();
         if (!(size <= 0.0F)) {
            float nextAccumulated = accumulated + size;
            if (progress <= nextAccumulated) {
               float t = (progress - accumulated) / size;
               return seg.easing().interpolate(t, seg.from(), seg.to());
            }

            accumulated = nextAccumulated;
         }
      }

      return this.segments.getLast().to();
   }

   public record Segment(Easing easing, float from, float to, float size) {
      public Segment {
         if (size <= 0.0F) {
            throw new IllegalArgumentException("Segment size must be positive");
         }
      }
   }
}
