package servercore_libs.net.kyori.adventure.text.minimessage.tag;

import java.util.Arrays;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.adventure.text.format.Style;
import servercore_libs.net.kyori.adventure.text.format.StyleBuilderApplicable;
import servercore_libs.net.kyori.examination.ExaminableProperty;

final class StylingTagImpl extends AbstractTag implements Inserting {
   private final StyleBuilderApplicable[] styles;

   StylingTagImpl(final StyleBuilderApplicable[] styles) {
      this.styles = styles;
   }

   @NotNull
   @Override
   public Component value() {
      return Component.text("", Style.style(this.styles));
   }

   @Override
   public int hashCode() {
      return 31 + Arrays.hashCode(this.styles);
   }

   @Override
   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      }

      if (!(other instanceof StylingTagImpl)) {
         return false;
      }

      StylingTagImpl that = (StylingTagImpl)other;
      return Arrays.equals(this.styles, that.styles);
   }

   @NotNull
   @Override
   public Stream<? extends ExaminableProperty> examinableProperties() {
      return Stream.of(ExaminableProperty.of("styles", this.styles));
   }
}
