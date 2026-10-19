package xaero.hud.minimap.radar.icon.cache.id.variant;

import java.util.Objects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.TropicalFish.Pattern;
import net.minecraft.world.item.DyeColor;

public class TropicalFishVariant {
   private final ResourceLocation texture;
   private final Pattern pattern;
   private final DyeColor baseColor;
   private final DyeColor patternColor;

   public TropicalFishVariant(ResourceLocation texture, Pattern pattern, DyeColor baseColor, DyeColor patternColor) {
      this.texture = texture;
      this.pattern = pattern;
      this.baseColor = baseColor;
      this.patternColor = patternColor;
   }

   @Override
   public String toString() {
      return this.texture + "%" + this.pattern + "%" + this.baseColor + "%" + this.patternColor;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         TropicalFishVariant that = (TropicalFishVariant)o;
         return Objects.equals(this.texture, that.texture)
            && this.pattern == that.pattern
            && this.baseColor == that.baseColor
            && this.patternColor == that.patternColor;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.texture, this.pattern, this.baseColor, this.patternColor);
   }
}
