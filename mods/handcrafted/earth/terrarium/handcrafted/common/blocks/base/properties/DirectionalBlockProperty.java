package earth.terrarium.handcrafted.common.blocks.base.properties;

import java.util.Locale;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum DirectionalBlockProperty implements StringRepresentable {
   SINGLE,
   LEFT,
   MIDDLE,
   RIGHT;

   @NotNull
   public String getSerializedName() {
      return this.name().toLowerCase(Locale.ROOT);
   }

   @Override
   public String toString() {
      return this.getSerializedName();
   }
}
