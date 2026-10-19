package xaero.hud.minimap.radar.icon.cache.id.variant;

import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

public class SaddleVariant {
   private final ResourceLocation texture;
   private final boolean saddled;

   public SaddleVariant(ResourceLocation texture, boolean saddled) {
      this.texture = texture;
      this.saddled = saddled;
   }

   @Override
   public String toString() {
      return this.texture + "%" + this.saddled;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         SaddleVariant that = (SaddleVariant)o;
         return this.saddled == that.saddled && Objects.equals(this.texture, that.texture);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.texture, this.saddled);
   }
}
