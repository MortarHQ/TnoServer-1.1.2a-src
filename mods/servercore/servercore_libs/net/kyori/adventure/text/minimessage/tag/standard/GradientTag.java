package servercore_libs.net.kyori.adventure.text.minimessage.tag.standard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import servercore_libs.net.kyori.adventure.text.format.TextColor;
import servercore_libs.net.kyori.adventure.text.minimessage.Context;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import servercore_libs.net.kyori.examination.ExaminableProperty;

final class GradientTag extends AbstractColorChangingTag {
   private static final String GRADIENT = "gradient";
   static final TagResolver RESOLVER = TagResolver.resolver("gradient", GradientTag::create);
   private int index = 0;
   private double multiplier = 1.0;
   private final TextColor[] colors;
   private @Range(from = -1L, to = 1L) double phase;

   static Tag create(final ArgumentQueue args, final Context ctx) {
      double phase = 0.0;
      List<TextColor> textColors;
      if (!args.hasNext()) {
         textColors = Collections.emptyList();
      } else {
         textColors = new ArrayList<>();

         while (args.hasNext()) {
            Tag.Argument arg = args.pop();
            if (!args.hasNext()) {
               OptionalDouble possiblePhase = arg.asDouble();
               if (possiblePhase.isPresent()) {
                  phase = possiblePhase.getAsDouble();
                  if (phase < -1.0 || phase > 1.0) {
                     throw ctx.newException(String.format("Gradient phase is out of range (%s). Must be in the range [-1.0, 1.0] (inclusive).", phase), args);
                  }

                  if (textColors.size() == 1) {
                     throw ctx.newException("Invalid gradient, not enough colors. Gradients must have at least two colors.", args);
                  }
                  break;
               }
            }

            TextColor parsedColor = ColorTagResolver.resolveColor(arg.value(), ctx);
            textColors.add(parsedColor);
         }
      }

      return new GradientTag(phase, textColors);
   }

   private GradientTag(final double phase, final List<TextColor> colors) {
      if (colors.isEmpty()) {
         this.colors = new TextColor[]{TextColor.color(16777215), TextColor.color(0)};
      } else {
         this.colors = colors.toArray(new TextColor[0]);
      }

      if (phase < 0.0) {
         this.phase = 1.0 + phase;
         Collections.reverse(Arrays.asList(this.colors));
      } else {
         this.phase = phase;
      }
   }

   @Override
   protected void init() {
      this.multiplier = this.size() == 1 ? 0.0 : (double)(this.colors.length - 1) / (this.size() - 1);
      this.phase = this.phase * (this.colors.length - 1);
      this.index = 0;
   }

   @Override
   protected void advanceColor() {
      this.index++;
   }

   @Override
   protected TextColor color() {
      double position = this.index * this.multiplier + this.phase;
      int lowUnclamped = (int)Math.floor(position);
      int high = (int)Math.ceil(position) % this.colors.length;
      int low = lowUnclamped % this.colors.length;
      return TextColor.lerp((float)position - lowUnclamped, this.colors[low], this.colors[high]);
   }

   @NotNull
   @Override
   public Stream<? extends ExaminableProperty> examinableProperties() {
      return Stream.of(ExaminableProperty.of("phase", this.phase), ExaminableProperty.of("colors", this.colors));
   }

   @Override
   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      } else if (other != null && this.getClass() == other.getClass()) {
         GradientTag that = (GradientTag)other;
         return this.index == that.index && this.phase == that.phase && Arrays.equals(this.colors, that.colors);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int result = Objects.hash(this.index, this.phase);
      return 31 * result + Arrays.hashCode(this.colors);
   }
}
