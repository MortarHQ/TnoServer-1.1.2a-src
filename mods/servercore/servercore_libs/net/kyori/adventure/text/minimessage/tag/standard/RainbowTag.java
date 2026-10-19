package servercore_libs.net.kyori.adventure.text.minimessage.tag.standard;

import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.text.format.TextColor;
import servercore_libs.net.kyori.adventure.text.minimessage.Context;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import servercore_libs.net.kyori.adventure.util.HSVLike;
import servercore_libs.net.kyori.examination.ExaminableProperty;

final class RainbowTag extends AbstractColorChangingTag {
   private static final String REVERSE = "!";
   private static final String RAINBOW = "rainbow";
   static final TagResolver RESOLVER = TagResolver.resolver("rainbow", RainbowTag::create);
   private final boolean reversed;
   private final double dividedPhase;
   private int colorIndex = 0;

   static Tag create(final ArgumentQueue args, final Context ctx) {
      boolean reversed = false;
      int phase = 0;
      if (args.hasNext()) {
         String value = args.pop().value();
         if (value.startsWith("!")) {
            reversed = true;
            value = value.substring("!".length());
         }

         if (value.length() > 0) {
            try {
               phase = Integer.parseInt(value);
            } catch (NumberFormatException ex) {
               throw ctx.newException("Expected phase, got " + value);
            }
         }
      }

      return new RainbowTag(reversed, phase);
   }

   private RainbowTag(final boolean reversed, final int phase) {
      this.reversed = reversed;
      this.dividedPhase = phase / 10.0;
   }

   @Override
   protected void init() {
      if (this.reversed) {
         this.colorIndex = this.size() - 1;
      }
   }

   @Override
   protected void advanceColor() {
      if (this.reversed) {
         if (this.colorIndex == 0) {
            this.colorIndex = this.size() - 1;
         } else {
            this.colorIndex--;
         }
      } else {
         this.colorIndex++;
      }
   }

   @Override
   protected TextColor color() {
      float index = this.colorIndex;
      float hue = (float)((index / this.size() + this.dividedPhase) % 1.0);
      return TextColor.color(HSVLike.hsvLike(hue, 1.0F, 1.0F));
   }

   @NotNull
   @Override
   public Stream<? extends ExaminableProperty> examinableProperties() {
      return Stream.of(ExaminableProperty.of("phase", this.dividedPhase));
   }

   @Override
   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      } else if (other != null && this.getClass() == other.getClass()) {
         RainbowTag that = (RainbowTag)other;
         return this.colorIndex == that.colorIndex && this.dividedPhase == that.dividedPhase;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.colorIndex, this.dividedPhase);
   }
}
