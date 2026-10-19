package twilightforest.enums;

import java.util.Locale;
import net.minecraft.util.StringRepresentable;

public enum TowerDeviceVariant implements StringRepresentable {
   BUILDER_INACTIVE,
   BUILDER_ACTIVE,
   BUILDER_TIMEOUT;

   public String getSerializedName() {
      return this.name().toLowerCase(Locale.ROOT);
   }
}
