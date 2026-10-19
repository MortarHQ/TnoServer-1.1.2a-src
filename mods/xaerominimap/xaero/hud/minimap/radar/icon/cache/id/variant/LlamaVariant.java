package xaero.hud.minimap.radar.icon.cache.id.variant;

import java.util.Objects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

public class LlamaVariant {
   private final ResourceLocation texture;
   private final boolean trader;
   private final DyeColor swag;

   public LlamaVariant(ResourceLocation texture, boolean trader, DyeColor swag) {
      this.texture = texture;
      this.trader = trader;
      this.swag = swag;
   }

   @Override
   public String toString() {
      return this.texture + "%" + this.trader + "%" + this.swag;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         LlamaVariant that = (LlamaVariant)o;
         return this.trader == that.trader && Objects.equals(this.texture, that.texture) && this.swag == that.swag;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.texture, this.trader, this.swag);
   }
}
