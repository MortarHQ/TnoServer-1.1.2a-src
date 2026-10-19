package xaero.hud.minimap.radar.icon.cache.id.variant;

import java.util.Objects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Crackiness.Level;

public class IronGolemVariant {
   private final ResourceLocation texture;
   private final Level cracks;

   public IronGolemVariant(ResourceLocation texture, Level cracks) {
      this.texture = texture;
      this.cracks = cracks;
   }

   @Override
   public String toString() {
      return this.texture + "%" + this.cracks;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         IronGolemVariant that = (IronGolemVariant)o;
         return Objects.equals(this.texture, that.texture) && this.cracks == that.cracks;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.texture, this.cracks);
   }
}
