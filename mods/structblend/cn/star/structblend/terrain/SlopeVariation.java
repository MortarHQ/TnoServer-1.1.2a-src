package cn.star.structblend.terrain;

final class SlopeVariation {
   private SlopeVariation() {
   }

   static double fillWeight(double t, int worldX, int worldZ) {
      if (t <= 0.0) {
         return 0.0;
      }

      if (t >= 1.0) {
         return 1.0;
      }

      double envelope = t * (1.0 - t);
      double varied = t + 0.8 * noise(worldX, worldZ) * envelope * envelope;
      return varied * varied * (3.0 - 2.0 * varied);
   }

   static double noise(double x, double z) {
      return 0.75 * lattice(x / 16.0, z / 16.0, 7146057691288625177L) + 0.25 * lattice(x / 8.0, z / 8.0, -7046029254386353131L);
   }

   private static double lattice(double x, double z, long salt) {
      int ix = (int)Math.floor(x);
      int iz = (int)Math.floor(z);
      double u = fade(x - ix);
      double v = fade(z - iz);
      double a = lerp(u, value(ix, iz, salt), value(ix + 1, iz, salt));
      double b = lerp(u, value(ix, iz + 1, salt), value(ix + 1, iz + 1, salt));
      return lerp(v, a, b);
   }

   private static double value(int x, int z, long salt) {
      long h = x * -7046029254386353131L ^ z * -4417276706812531889L ^ salt;
      h = (h ^ h >>> 30) * -4658895280553007687L;
      h = (h ^ h >>> 27) * -7723592293110705685L;
      h ^= h >>> 31;
      return (h >>> 11) * 2.220446E-16F - 1.0;
   }

   private static double fade(double t) {
      return t * t * t * (t * (t * 6.0 - 15.0) + 10.0);
   }

   private static double lerp(double t, double a, double b) {
      return a + t * (b - a);
   }
}
