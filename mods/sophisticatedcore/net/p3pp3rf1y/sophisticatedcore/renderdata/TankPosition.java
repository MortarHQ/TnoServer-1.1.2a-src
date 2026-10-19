package net.p3pp3rf1y.sophisticatedcore.renderdata;

import java.util.Locale;
import net.minecraft.util.StringRepresentable;

public enum TankPosition implements StringRepresentable {
   LEFT,
   RIGHT;

   public String getSerializedName() {
      return this.name().toLowerCase(Locale.ENGLISH);
   }
}
