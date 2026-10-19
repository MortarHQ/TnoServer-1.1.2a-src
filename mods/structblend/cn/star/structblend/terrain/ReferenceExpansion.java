package cn.star.structblend.terrain;

public final class ReferenceExpansion {
   private ReferenceExpansion() {
   }

   public static boolean affectsResult(int minX, int maxX, int minZ, int maxZ, int chunkMinX, int chunkMinZ, int maxRadius) {
      return !intersects(minX, maxX, minZ, maxZ, chunkMinX, chunkMinZ, 0) && intersects(minX, maxX, minZ, maxZ, chunkMinX, chunkMinZ, maxRadius);
   }

   static boolean intersects(int minX, int maxX, int minZ, int maxZ, int chunkMinX, int chunkMinZ, int radius) {
      return maxX + radius >= chunkMinX && minX - radius <= chunkMinX + 15 && maxZ + radius >= chunkMinZ && minZ - radius <= chunkMinZ + 15;
   }
}
