package net.pixeldreamstudios.mythika.worldgen;

import net.minecraft.util.RandomSource;

public final class CyclopsCaveLayout {
   public static final int NONE = Integer.MIN_VALUE;
   public static final int APPROACH_D = -3;
   public static final int TUNNEL_HALF = 4;
   public static final int TUNNEL_HEIGHT = 8;
   public static final int MIN_REACH = 20;
   public static final int MAX_REACH = 56;
   public static final int REACH_STEP = 4;
   public static final int CHAMBER_R = 13;
   public static final int CHAMBER_HEIGHT = 16;
   public static final int LOBE_OFFSET = 15;
   public static final int LOBE_R = 7;
   public static final int LOBE_HEIGHT = 7;
   public static final int FOLD_GATE = 10;
   public static final int SHELF_AHEAD = 5;
   public static final int SHELF_HALF = 7;
   public static final int SHELF_HALF_STEPS = 4;
   public static final int RIND = 2;
   public static final int COVER = 4;
   public static final int STONE_D = -5;
   public static final int STONE_OFFSET = 8;
   public static final int STONE_R = 3;
   public static final int MIN_D = -9;
   public static final int HALF_P = 24;
   public static final int MAX_MISFIT = 18;
   public static final int PROBE_D_STRIDE = 6;
   public static final int PROBE_P_STRIDE = 14;
   public static final int PROBE_HALF = 1;
   public static final int MOUTH_TRIES = 5;
   private static final int WIDTH = 49;
   private final int seed;
   private final int side;
   private final int reach;
   private final int[] ceiling;

   private CyclopsCaveLayout(int seed, int side, int reach) {
      this.seed = seed;
      this.side = side;
      this.reach = reach;
      this.ceiling = new int[(maxD(reach) - -9 + 1) * 49];

      for (int d = -9; d <= maxD(reach); d++) {
         for (int p = -24; p <= 24; p++) {
            this.ceiling[this.index(d, p)] = ceilingAt(seed, side, reach, d, p);
         }
      }
   }

   public static CyclopsCaveLayout of(int seed, int side, int reach) {
      return new CyclopsCaveLayout(seed, side, reach);
   }

   public static int rollSide(RandomSource random) {
      return random.nextBoolean() ? 1 : -1;
   }

   public int seed() {
      return this.seed;
   }

   public int side() {
      return this.side;
   }

   public int reach() {
      return this.reach;
   }

   public int maxD() {
      return maxD(this.reach);
   }

   public int chamberD() {
      return this.reach;
   }

   public int foldP() {
      return this.side * 15;
   }

   public int larderP() {
      return -this.side * 15;
   }

   public int gateP() {
      return this.side * 10;
   }

   public int doorStoneP() {
      return -this.side * 8;
   }

   public int shelfD() {
      return this.reach + 5;
   }

   public static int shelfAir() {
      return 2;
   }

   public static int maxD(int reach) {
      return reach + 13 + 2;
   }

   public static int galleryRun(int reach) {
      return Math.max(1, reach - 13);
   }

   public static int halfFloor(int reach, int d, int p) {
      if (Math.abs(p) > 7) {
         return 0;
      }

      int up = d - (reach + 5);
      return up < 0 ? 0 : Math.min(up + 1, 4);
   }

   public static int airAt(int reach, int d, int p) {
      return (halfFloor(reach, d, p) + 1) / 2;
   }

   public static int floorTopAt(int reach, int d, int p) {
      return halfFloor(reach, d, p) / 2 - 1;
   }

   public static boolean treadSlabAt(int reach, int d, int p) {
      return halfFloor(reach, d, p) % 2 == 1;
   }

   public static int ceilingAt(int seed, int side, int reach, int d, int p) {
      int best = Integer.MIN_VALUE;
      if (d >= -3 && d <= reach) {
         int half = 4 + jitter(seed, d, p, 1);
         if (Math.abs(p) <= half) {
            int arch = arc(8, Math.abs(p), half);
            best = Math.max(best, Math.max(arch, 4) - 1);
         }
      }

      best = Math.max(best, dome(seed, reach, 0, 13, 16, d, p, 2));
      best = Math.max(best, dome(seed, reach, side * 15, 7, 7, d, p, 3));
      return Math.max(best, dome(seed, reach, -side * 15, 7, 7, d, p, 4));
   }

   private static int dome(int seed, int cd, int cp, int radius, int height, int d, int p, int salt) {
      double r = Math.sqrt((double)(d - cd) * (d - cd) + (double)(p - cp) * (p - cp)) + 0.6 * jitter(seed, d, p, salt);
      if (r > radius) {
         return Integer.MIN_VALUE;
      }

      int h = arc(height, r, radius);
      return h < 4 ? Integer.MIN_VALUE : h - 1;
   }

   private static int arc(int height, double offset, double half) {
      double t = offset / (half + 0.5);
      return (int)Math.round(height * Math.sqrt(Math.max(0.0, 1.0 - t * t)));
   }

   private static int jitter(int seed, int d, int p, int salt) {
      return Variance.at(d, seed, p, salt) % 3 - 1;
   }

   public static int coverAt(int reach, int d) {
      if (d < 0) {
         return 0;
      }

      int run = galleryRun(reach);
      return d >= run ? 4 : 1 + 3 * d / run;
   }

   public int ceiling(int d, int p) {
      return this.inside(d, p) ? this.ceiling[this.index(d, p)] : Integer.MIN_VALUE;
   }

   public boolean carved(int d, int p) {
      return this.ceiling(d, p) != Integer.MIN_VALUE;
   }

   public int air(int d, int p) {
      return airAt(this.reach, d, p);
   }

   public int floorTop(int d, int p) {
      return floorTopAt(this.reach, d, p);
   }

   public boolean treadSlab(int d, int p) {
      return treadSlabAt(this.reach, d, p);
   }

   public int headroom(int d, int p) {
      int top = this.ceiling(d, p);
      return top == Integer.MIN_VALUE ? 0 : top - this.air(d, p) + 1;
   }

   public boolean isGate(int d, int p) {
      return p == this.gateP() && this.carved(d, p);
   }

   public int rindLo(int d, int p) {
      if (d < 0) {
         return Integer.MAX_VALUE;
      }

      int lo = Integer.MAX_VALUE;

      for (int dd = -2; dd <= 2; dd++) {
         for (int dp = -2; dp <= 2; dp++) {
            if (this.carved(d + dd, p + dp)) {
               lo = Math.min(lo, this.floorTop(d + dd, p + dp) - 2);
            }
         }
      }

      return lo;
   }

   public int rindHi(int d, int p) {
      if (d < 0) {
         return Integer.MIN_VALUE;
      }

      int hi = Integer.MIN_VALUE;

      for (int dd = -2; dd <= 2; dd++) {
         for (int dp = -2; dp <= 2; dp++) {
            if (this.carved(d + dd, p + dp)) {
               hi = Math.max(hi, this.ceiling(d + dd, p + dp) + coverAt(this.reach, d + dd));
            }
         }
      }

      return hi;
   }

   public boolean hasRind(int d, int p) {
      return this.rindHi(d, p) != Integer.MIN_VALUE;
   }

   private boolean inside(int d, int p) {
      return d >= -9 && d <= maxD(this.reach) && p >= -24 && p <= 24;
   }

   private int index(int d, int p) {
      return (d - -9) * 49 + p + 24;
   }

   public static int probeDepth() {
      return 4 + maxD(56) / 6 + 1;
   }

   public static int probeWidth() {
      return 3;
   }

   public static int probeP(int j) {
      return (j - 1) * 14;
   }

   public static int coverMisfit(int seed, int side, int reach, int mouthY, int[] terrain, int from) {
      int missing = 0;

      for (int i = from; i < probeDepth(); i++) {
         int d = (i - from) * 6;
         if (d > maxD(reach)) {
            break;
         }

         if (d != 0) {
            for (int j = 0; j < probeWidth(); j++) {
               int top = ceilingAt(seed, side, reach, d, probeP(j));
               if (top != Integer.MIN_VALUE) {
                  int needed = mouthY + top + coverAt(reach, d) + 1;
                  missing += Math.max(0, needed - terrain[i * probeWidth() + j]);
               }
            }
         }
      }

      return missing;
   }

   public static int solveReach(int seed, int side, int mouthY, int[] terrain, int from) {
      int bestReach = 20;
      long best = Long.MAX_VALUE;

      for (int reach = 20; reach <= 56; reach += 4) {
         long score = coverMisfit(seed, side, reach, mouthY, terrain, from) * 1000L + reach;
         if (score < best) {
            best = score;
            bestReach = reach;
         }
      }

      return bestReach;
   }
}
