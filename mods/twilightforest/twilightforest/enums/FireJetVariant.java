package twilightforest.enums;

import java.util.Locale;
import net.minecraft.util.StringRepresentable;

public enum FireJetVariant implements StringRepresentable {
   IDLE,
   POPPING,
   FLAME,
   TIMEOUT;

   @Override
   public String toString() {
      return this.getSerializedName();
   }

   public String getSerializedName() {
      return this.name().toLowerCase(Locale.ROOT);
   }
}
