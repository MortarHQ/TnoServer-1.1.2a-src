package com.mortal_cultivation.worldgen;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class SpiritSpringLayout {
   private SpiritSpringLayout() {
   }

   public static Map<SpiritSpringLayout.Cell, Integer> water() {
      Map<SpiritSpringLayout.Cell, Integer> water = new LinkedHashMap<>();

      for (int along = -11; along <= 11; along++) {
         int center = bend(along);
         int height = along < -6 ? 0 : (along < 6 ? -1 : (along < 8 ? -2 : -3));

         for (int across = center; across <= center + 1; across++) {
            water.put(new SpiritSpringLayout.Cell(along, across), height);
         }
      }

      pool(water, -3, 0, 4, -1);
      pool(water, 9, 0, 3, -3);
      return Map.copyOf(water);
   }

   private static void pool(Map<SpiritSpringLayout.Cell, Integer> water, int along, int across, int radius, int height) {
      for (int x = -radius; x <= radius; x++) {
         for (int z = -radius; z <= radius; z++) {
            if (x * x + z * z <= radius * radius - 1) {
               water.merge(new SpiritSpringLayout.Cell(along + x, across + z), height, Math::min);
            }
         }
      }
   }

   public static Map<SpiritSpringLayout.Cell, Integer> banks(Map<SpiritSpringLayout.Cell, Integer> water) {
      Map<SpiritSpringLayout.Cell, Integer> banks = new LinkedHashMap<>();

      for (Entry<SpiritSpringLayout.Cell, Integer> entry : water.entrySet()) {
         for (SpiritSpringLayout.Cell neighbor : entry.getKey().neighbors()) {
            if (!water.containsKey(neighbor)) {
               banks.merge(neighbor, entry.getValue(), Math::max);
            }
         }
      }

      return Map.copyOf(banks);
   }

   public static int bend(int along) {
      return (int)Math.round(Math.sin((along + 7) * 0.22) * 1.2);
   }

   public record Cell(int along, int across) {
      public SpiritSpringLayout.Cell[] neighbors() {
         return new SpiritSpringLayout.Cell[]{
            new SpiritSpringLayout.Cell(this.along + 1, this.across),
            new SpiritSpringLayout.Cell(this.along - 1, this.across),
            new SpiritSpringLayout.Cell(this.along, this.across + 1),
            new SpiritSpringLayout.Cell(this.along, this.across - 1)
         };
      }
   }
}
