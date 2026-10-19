package twilightforest.enums;

import java.util.Locale;
import net.minecraft.util.StringRepresentable;

public enum HugeLilypadPiece implements StringRepresentable {
   NW,
   NE,
   SE,
   SW;

   public String getSerializedName() {
      return this.name().toLowerCase(Locale.ROOT);
   }
}
