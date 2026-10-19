package net.mebahelsapi.util;

import java.util.EnumSet;
import java.util.Set;
import net.minecraft.class_14;
import net.minecraft.class_7;
import net.minecraft.class_9316;
import net.minecraft.class_2338.class_2339;

public class LargeEntityPathNodeMaker extends class_14 {
   public Set<class_7> method_57090(class_9316 context, int x, int y, int z) {
      Set<class_7> nearbyTypes = EnumSet.noneOf(class_7.class);
      int xSize = Math.max(2, this.field_31);
      int ySize = Math.max(4, this.field_30);
      int zSize = Math.max(2, this.field_28);
      class_2339 mutablePos = new class_2339();

      for (int dx = 0; dx < xSize; dx++) {
         for (int dy = 0; dy < ySize; dy++) {
            for (int dz = 0; dz < zSize; dz++) {
               int px = x + dx;
               int py = y + dy;
               int pz = z + dz;
               mutablePos.method_10103(px, py, pz);
               class_7 nodeType = this.method_17(context, px, py, pz);
               nodeType = class_14.method_59(context, px, py, pz, nodeType);
               nearbyTypes.add(nodeType);
            }
         }
      }

      return nearbyTypes;
   }
}
