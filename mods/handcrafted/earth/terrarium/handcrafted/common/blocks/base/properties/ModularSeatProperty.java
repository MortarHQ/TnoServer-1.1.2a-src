package earth.terrarium.handcrafted.common.blocks.base.properties;

import java.util.Locale;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum ModularSeatProperty implements StringRepresentable {
   SINGLE,
   LEFT,
   MIDDLE,
   RIGHT,
   INNER_LEFT,
   INNER_RIGHT,
   OUTER_LEFT,
   OUTER_RIGHT;

   @NotNull
   public String getSerializedName() {
      return this.name().toLowerCase(Locale.ROOT);
   }

   @Override
   public String toString() {
      return this.getSerializedName();
   }

   public String getLocation() {
      return switch (this) {
         case INNER_LEFT, INNER_RIGHT -> "corner";
         case OUTER_LEFT, OUTER_RIGHT -> "corner_inverted";
         default -> this.getSerializedName();
      };
   }
}
