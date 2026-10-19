package com.mortal_cultivation.worldgen;

import java.util.Random;

public final class SpiritForestLandscapeLayout {
   public static final int CELL_SIZE = 64;
   public static final int CLEARING_RADIUS = 12;

   private SpiritForestLandscapeLayout() {
   }

   public static SpiritForestLandscapeLayout.Site site(long worldSeed, int cellX, int cellZ) {
      long seed = SpiritSkyIslandLayout.mix(worldSeed ^ 7336927629141047833L ^ cellX * 341873128712L ^ cellZ * 132897987541L);
      Random random = new Random(seed);
      int x = cellX * 64 + 32 + random.nextInt(25) - 12;
      int z = cellZ * 64 + 32 + random.nextInt(25) - 12;
      int choice = random.nextInt(20);
      SpiritForestLandscapeLayout.Kind kind = choice < 8
         ? SpiritForestLandscapeLayout.Kind.CHERRY_GLADE
         : (choice < 18 ? SpiritForestLandscapeLayout.Kind.SPRING : SpiritForestLandscapeLayout.Kind.RUIN);
      return new SpiritForestLandscapeLayout.Site(x, z, kind, random.nextInt(4), seed);
   }

   public static int clearingDistanceSquared(long worldSeed, int x, int z) {
      int distance = Integer.MAX_VALUE;
      int cellX = Math.floorDiv(x, 64);
      int cellZ = Math.floorDiv(z, 64);

      for (int dx = -1; dx <= 1; dx++) {
         for (int dz = -1; dz <= 1; dz++) {
            SpiritForestLandscapeLayout.Site site = site(worldSeed, cellX + dx, cellZ + dz);
            if (site.kind == SpiritForestLandscapeLayout.Kind.CHERRY_GLADE) {
               int sx = site.x - x;
               int sz = site.z - z;
               distance = Math.min(distance, sx * sx + sz * sz);
            }
         }
      }

      return distance;
   }

   public static boolean isOpenSpace(long worldSeed, int x, int z) {
      int cellX = Math.floorDiv(x, 64);
      int cellZ = Math.floorDiv(z, 64);

      for (int dx = -1; dx <= 1; dx++) {
         for (int dz = -1; dz <= 1; dz++) {
            SpiritForestLandscapeLayout.Site site = site(worldSeed, cellX + dx, cellZ + dz);

            int radius = switch (site.kind) {
               case CHERRY_GLADE -> 12;
               case SPRING -> 12;
               case RUIN -> 6;
            };
            int sx = site.x - x;
            int sz = site.z - z;
            if (sx * sx + sz * sz < radius * radius) {
               return true;
            }
         }
      }

      return false;
   }

   public enum Kind {
      CHERRY_GLADE,
      SPRING,
      RUIN;
   }

   public record Site(int x, int z, SpiritForestLandscapeLayout.Kind kind, int direction, long seed) {
      public int worldX(int along, int across) {
         return this.x + switch (this.direction) {
            case 0 -> along;
            case 1 -> -across;
            case 2 -> -along;
            default -> across;
         };
      }

      public int worldZ(int along, int across) {
         return this.z + switch (this.direction) {
            case 0 -> across;
            case 1 -> along;
            case 2 -> -across;
            default -> -along;
         };
      }
   }
}
