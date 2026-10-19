package xaero.hud.minimap.radar.icon.cache.id;

import java.util.Objects;
import xaero.hud.minimap.radar.icon.cache.id.armor.RadarIconArmor;

public class RadarIconKey {
   private final Object variant;
   private final RadarIconArmor armor;

   public RadarIconKey(Object variant, RadarIconArmor armor) {
      this.variant = variant;
      this.armor = armor;
   }

   public Object getVariant() {
      return this.variant;
   }

   @Override
   public String toString() {
      return "RadarIconKey{" + this.variant + ", " + this.armor + "}";
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         RadarIconKey that = (RadarIconKey)o;
         return this.variant.equals(that.variant) && Objects.equals(this.armor, that.armor);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.variant, this.armor);
   }
}
