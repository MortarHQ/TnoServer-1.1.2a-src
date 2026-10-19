package earth.terrarium.handcrafted.common.blocks.base.properties;

import java.util.Locale;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum TrimProperty implements StringRepresentable {
   NORMAL,
   THICC,
   THIN;

   @NotNull
   public String getSerializedName() {
      return this.name().toLowerCase(Locale.ROOT);
   }

   @Override
   public String toString() {
      return this.getSerializedName();
   }
}
