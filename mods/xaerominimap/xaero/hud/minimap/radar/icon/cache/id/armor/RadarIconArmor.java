package xaero.hud.minimap.radar.icon.cache.id.armor;

import java.util.Objects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimPattern;

public class RadarIconArmor {
   private final Item armor;
   private final TrimMaterial trimMaterial;
   private final TrimPattern trimPattern;

   public RadarIconArmor(Item armor, TrimMaterial trimMaterial, TrimPattern trimPattern) {
      this.armor = armor;
      this.trimMaterial = trimMaterial;
      this.trimPattern = trimPattern;
   }

   @Override
   public String toString() {
      return "RadarIconArmor{" + this.armor + ", " + this.trimMaterial + ", " + this.trimPattern + "}";
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         RadarIconArmor that = (RadarIconArmor)o;
         return this.armor.equals(that.armor) && Objects.equals(this.trimMaterial, that.trimMaterial) && Objects.equals(this.trimPattern, that.trimPattern);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.armor, this.trimMaterial, this.trimPattern);
   }
}
