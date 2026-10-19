package servercore_libs.net.kyori.adventure.text.format;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Debug.Renderer;
import servercore_libs.net.kyori.adventure.util.HSVLike;

@Renderer(text = "asHexString()")
final class TextColorImpl implements TextColor {
   private final int value;

   TextColorImpl(final int value) {
      this.value = value;
   }

   @Override
   public int value() {
      return this.value;
   }

   @Override
   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      }

      if (!(other instanceof TextColorImpl)) {
         return false;
      }

      TextColorImpl that = (TextColorImpl)other;
      return this.value == that.value;
   }

   @Override
   public int hashCode() {
      return this.value;
   }

   @Override
   public String toString() {
      return this.asHexString();
   }

   static float distance(@NotNull final HSVLike self, @NotNull final HSVLike other) {
      float hueDistance = 3.0F * Math.min(Math.abs(self.h() - other.h()), 1.0F - Math.abs(self.h() - other.h()));
      float saturationDiff = self.s() - other.s();
      float valueDiff = self.v() - other.v();
      return hueDistance * hueDistance + saturationDiff * saturationDiff + valueDiff * valueDiff;
   }
}
