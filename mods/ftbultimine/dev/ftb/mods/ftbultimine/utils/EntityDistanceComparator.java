package dev.ftb.mods.ftbultimine.utils;

import java.util.Comparator;
import net.minecraft.core.BlockPos;

public class EntityDistanceComparator implements Comparator<BlockPos> {
   private final int x;
   private final int y;
   private final int z;

   public EntityDistanceComparator(BlockPos p) {
      this.x = p.getX();
      this.y = p.getY();
      this.z = p.getZ();
   }

   private int distSq(BlockPos p) {
      int dx = p.getX() - this.x;
      int dy = p.getY() - this.y;
      int dz = p.getZ() - this.z;
      return dx * dx + dy * dy + dz * dz;
   }

   public int compare(BlockPos o1, BlockPos o2) {
      return this.distSq(o1) - this.distSq(o2);
   }
}
