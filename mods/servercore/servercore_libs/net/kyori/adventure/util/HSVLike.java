package servercore_libs.net.kyori.adventure.util;

import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import servercore_libs.net.kyori.examination.Examinable;
import servercore_libs.net.kyori.examination.ExaminableProperty;

public interface HSVLike extends Examinable {
   @NotNull
   static HSVLike hsvLike(final float h, final float s, final float v) {
      return new HSVLikeImpl(h, s, v);
   }

   @Deprecated
   @ScheduledForRemoval(inVersion = "5.0.0")
   @NotNull
   static HSVLike of(final float h, final float s, final float v) {
      return new HSVLikeImpl(h, s, v);
   }

   @NotNull
   static HSVLike fromRGB(final @Range(from = 0L, to = 255L) int red, final @Range(from = 0L, to = 255L) int green, final @Range(from = 0L, to = 255L) int blue) {
      float r = red / 255.0F;
      float g = green / 255.0F;
      float b = blue / 255.0F;
      float min = Math.min(r, Math.min(g, b));
      float max = Math.max(r, Math.max(g, b));
      float delta = max - min;
      float s;
      if (max != 0.0F) {
         s = delta / max;
      } else {
         s = 0.0F;
      }

      if (s == 0.0F) {
         return new HSVLikeImpl(0.0F, s, max);
      }

      float h;
      if (r == max) {
         h = (g - b) / delta;
      } else if (g == max) {
         h = 2.0F + (b - r) / delta;
      } else {
         h = 4.0F + (r - g) / delta;
      }

      h *= 60.0F;
      if (h < 0.0F) {
         h += 360.0F;
      }

      return new HSVLikeImpl(h / 360.0F, s, max);
   }

   float h();

   float s();

   float v();

   @NotNull
   @Override
   default Stream<? extends ExaminableProperty> examinableProperties() {
      return Stream.of(ExaminableProperty.of("h", this.h()), ExaminableProperty.of("s", this.s()), ExaminableProperty.of("v", this.v()));
   }
}
