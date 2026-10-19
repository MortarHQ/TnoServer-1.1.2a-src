package xaero.hud.minimap.radar.icon.cache.id.variant;

import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

public class TamableVariant {
   private final ResourceLocation texture;
   private final boolean tame;

   public TamableVariant(ResourceLocation texture, boolean tame) {
      this.texture = texture;
      this.tame = tame;
   }

   @Override
   public String toString() {
      return this.texture + "%" + this.tame;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         TamableVariant that = (TamableVariant)o;
         return this.tame == that.tame && Objects.equals(this.texture, that.texture);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.texture, this.tame);
   }
}
