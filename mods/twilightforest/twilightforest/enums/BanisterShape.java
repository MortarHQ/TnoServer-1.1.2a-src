package twilightforest.enums;

import java.util.Locale;
import net.minecraft.util.StringRepresentable;

public enum BanisterShape implements StringRepresentable {
   SHORT,
   TALL,
   CONNECTED;

   public String getSerializedName() {
      return this.name().toLowerCase(Locale.ROOT);
   }
}
