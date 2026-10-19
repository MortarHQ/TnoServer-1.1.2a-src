package net.blay09.mods.waystones.comparator;

import java.util.Comparator;
import net.blay09.mods.waystones.api.Waystone;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class PreferSameDimensionComparator implements Comparator<Waystone> {
   private final ResourceKey<Level> dimensionId;

   public PreferSameDimensionComparator(ResourceKey<Level> dimensionId) {
      this.dimensionId = dimensionId;
   }

   public int compare(Waystone o1, Waystone o2) {
      if (o1.getDimension().equals(this.dimensionId) && !o2.getDimension().equals(this.dimensionId)) {
         return -1;
      } else {
         return !o1.getDimension().equals(this.dimensionId) && o2.getDimension().equals(this.dimensionId) ? 1 : 0;
      }
   }
}
