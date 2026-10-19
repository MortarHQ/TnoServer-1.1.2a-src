package servercore_libs.net.kyori.adventure.text.minimessage.tag.standard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.adventure.text.format.NamedTextColor;
import servercore_libs.net.kyori.adventure.text.format.TextColor;
import servercore_libs.net.kyori.adventure.text.minimessage.Context;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Inserting;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import servercore_libs.net.kyori.examination.Examinable;
import servercore_libs.net.kyori.examination.ExaminableProperty;

public final class TransitionTag implements Inserting, Examinable {
   public static final String TRANSITION = "transition";
   private final TextColor[] colors;
   private final float phase;
   private final boolean negativePhase;
   static final TagResolver RESOLVER = TagResolver.resolver("transition", TransitionTag::create);

   static Tag create(final ArgumentQueue args, final Context ctx) {
      float phase = 0.0F;
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
                  phase = (float)possiblePhase.getAsDouble();
                  if (phase < -1.0F || phase > 1.0F) {
                     throw ctx.newException(String.format("Gradient phase is out of range (%s). Must be in the range [-1.0f, 1.0f] (inclusive).", phase), args);
                  }

                  if (textColors.size() < 2) {
                     throw ctx.newException("Invalid transition, not enough colors. Transitions must have at least two colors.", args);
                  }
                  break;
               }
            }

            String argValue = arg.value();
            TextColor parsedColor;
            if (argValue.charAt(0) == '#') {
               parsedColor = TextColor.fromHexString(argValue);
            } else {
               parsedColor = NamedTextColor.NAMES.value(arg.lowerValue());
            }

            if (parsedColor == null) {
               throw ctx.newException(String.format("Unable to parse a color from '%s'. Please use named colors or hex (#RRGGBB) colors.", argValue), args);
            }

            textColors.add(parsedColor);
         }
      }

      return new TransitionTag(phase, textColors);
   }

   private TransitionTag(final float phase, final List<TextColor> colors) {
      if (phase < 0.0F) {
         this.negativePhase = true;
         this.phase = 1.0F + phase;
         Collections.reverse(colors);
      } else {
         this.negativePhase = false;
         this.phase = phase;
      }

      if (colors.isEmpty()) {
         this.colors = new TextColor[]{TextColor.color(16777215), TextColor.color(0)};
      } else {
         this.colors = colors.toArray(new TextColor[0]);
      }
   }

   @NotNull
   @Override
   public Component value() {
      return Component.text("", this.color());
   }

   private TextColor color() {
      float steps = 1.0F / (this.colors.length - 1);

      for (int colorIndex = 1; colorIndex < this.colors.length; colorIndex++) {
         float val = colorIndex * steps;
         if (val >= this.phase) {
            float factor = 1.0F + (this.phase - val) * (this.colors.length - 1);
            if (this.negativePhase) {
               return TextColor.lerp(1.0F - factor, this.colors[colorIndex], this.colors[colorIndex - 1]);
            }

            return TextColor.lerp(factor, this.colors[colorIndex - 1], this.colors[colorIndex]);
         }
      }

      return this.colors[0];
   }

   @NotNull
   @Override
   public Stream<? extends ExaminableProperty> examinableProperties() {
      return Stream.of(ExaminableProperty.of("phase", this.phase), ExaminableProperty.of("colors", this.colors));
   }

   @Override
   public boolean equals(final Object other) {
      if (this == other) {
         return true;
      } else if (other != null && this.getClass() == other.getClass()) {
         TransitionTag that = (TransitionTag)other;
         return this.phase == that.phase && Arrays.equals(this.colors, that.colors);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int result = Objects.hash(this.phase);
      return 31 * result + Arrays.hashCode(this.colors);
   }
}
