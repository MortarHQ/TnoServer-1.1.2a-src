package com.mortal_cultivation.worldgen;

public final class JiuyouColdPoolLayout {
   public static final int RADIUS = 27;
   public static final int MIN_Y = -13;
   public static final int MAX_Y = 16;
   public static final int WATER_Y = 0;
   private final long seed;
   private final double rotation;
   private final double radiusX;
   private final double radiusZ;

   public JiuyouColdPoolLayout(long seed) {
      this.seed = seed;
      this.rotation = this.sample(1L, 0, 0) * Math.PI * 2.0;
      this.radiusX = 21.0 + this.sample(2L, 0, 0) * 2.0;
      this.radiusZ = 17.0 + this.sample(3L, 0, 0) * 2.0;
   }

   public JiuyouColdPoolLayout.Column column(int x, int z) {
      double rx = x * Math.cos(this.rotation) - z * Math.sin(this.rotation);
      double rz = x * Math.sin(this.rotation) + z * Math.cos(this.rotation);
      double angle = Math.atan2(rz, rx);
      double edge = 1.0 + 0.045 * Math.sin(angle * 3.0 + this.rotation) + 0.025 * Math.cos(angle * 5.0 - this.rotation);
      double radius = Math.sqrt(rx * rx / (this.radiusX * this.radiusX) + rz * rz / (this.radiusZ * this.radiusZ)) / edge;
      if (radius > 1.09) {
         return null;
      }

      double r = Math.min(radius, 1.0);
      double ripple = 0.45 * Math.sin(x * 0.31 + this.rotation) + 0.35 * Math.cos(z * 0.27 - this.rotation);
      int floor = (int)Math.floor(-9.5 + 13.5 * Math.pow(r, 1.9) + ripple);
      int ceiling = 5 + (int)Math.floor(8.0 * Math.sqrt(Math.max(0.0, 1.0 - r * r)) + ripple);
      return new JiuyouColdPoolLayout.Column(floor, Math.max(floor + 3, ceiling), radius);
   }

   public JiuyouColdPoolLayout.Cell cell(int x, int y, int z) {
      return cell(this.column(x, z), y);
   }

   public static JiuyouColdPoolLayout.Cell cell(JiuyouColdPoolLayout.Column c, int y) {
      if (c == null || y < c.floor() - 2 || y > c.ceiling() + 2) {
         return JiuyouColdPoolLayout.Cell.UNCHANGED;
      } else if (!(c.radius() >= 1.0) && y > c.floor() && y < c.ceiling()) {
         return y <= 0 ? JiuyouColdPoolLayout.Cell.WATER : JiuyouColdPoolLayout.Cell.AIR;
      } else {
         return JiuyouColdPoolLayout.Cell.ROCK;
      }
   }

   public boolean biomeContains(int x, int y, int z) {
      JiuyouColdPoolLayout.Column c = this.column(x, z);
      return c != null && c.radius() <= 1.04 && y >= c.floor() - 2 && y <= c.ceiling() + 2;
   }

   public double sample(long salt, int x, int z) {
      long value = this.seed ^ salt ^ x * 341873128712L ^ z * 132897987541L;
      value = (value ^ value >>> 30) * -4658895280553007687L;
      value = (value ^ value >>> 27) * -7723592293110705685L;
      value ^= value >>> 31;
      return (value >>> 11) * 1.110223E-16F;
   }

   public enum Cell {
      UNCHANGED,
      ROCK,
      WATER,
      AIR;
   }

   public record Column(int floor, int ceiling, double radius) {
   }
}
