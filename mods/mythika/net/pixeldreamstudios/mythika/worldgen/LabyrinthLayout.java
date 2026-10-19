package net.pixeldreamstudios.mythika.worldgen;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import net.minecraft.util.RandomSource;

public final class LabyrinthLayout {
   public static final int CORRIDOR = 5;
   public static final int WALL = 2;
   public static final int PITCH = 7;
   public static final int CORE = 3;
   public static final int INTERIOR = 7;
   public static final int CORE_RISE = 5;
   public static final int CHAMBER_HEIGHT = 12;
   public static final int LIGHT_REACH = 2;
   public static final int LANTERN_HEIGHT = 4;
   public static final int CHAMBER_LANTERN_HEIGHT = 7;
   public static final int DAIS_RADIUS = 3;
   public static final int APRON = 3;
   public static final int RAMP = 8;
   private static final int[] DX = new int[]{0, 1, 0, -1};
   private static final int[] DZ = new int[]{-1, 0, 1, 0};
   private static final int[] BIT = new int[]{1, 2, 4, 8};
   private static final float BRAID_CHANCE = 0.4F;
   private final int cells;
   private final int coreMin;
   private final int coreMax;
   private final byte[] open;

   public static int daisHalfSteps(int r) {
      return Math.max(0, 4 - r);
   }

   public static int treadBase(int top, int step) {
      return top - (step + 1) / 2;
   }

   public static boolean treadHasSlab(int step) {
      return step % 2 == 1;
   }

   public static int stairTop(int floorY, int steps) {
      return floorY + steps / 2 - 1;
   }

   public static int portalY(int floorY, int steps) {
      return floorY + steps / 2;
   }

   public static int entranceMisfit(int floorY, int[] ground, int steps) {
      int highest = Integer.MIN_VALUE;

      for (int ahead = 0; ahead <= 3; ahead++) {
         highest = Math.max(highest, ground[steps + ahead]);
      }

      int over = portalY(floorY, steps) - highest;
      return over < 0 ? -4 * over : over;
   }

   public static int solveEntranceSteps(int floorY, int[] ground, int maxSteps) {
      int bestSteps = 0;
      long best = Long.MAX_VALUE;

      for (int steps = 0; steps <= maxSteps; steps += 2) {
         int misfit = entranceMisfit(floorY, ground, steps);
         long score = misfit * 1000L + (misfit == 0 ? steps : maxSteps - steps);
         if (score < best) {
            best = score;
            bestSteps = steps;
         }
      }

      return bestSteps;
   }

   private LabyrinthLayout(int cells, byte[] open) {
      this.cells = cells;
      this.coreMin = (cells - 3) / 2;
      this.coreMax = this.coreMin + 3 - 1;
      this.open = open;
   }

   public static LabyrinthLayout generate(int cells, RandomSource random) {
      LabyrinthLayout layout = new LabyrinthLayout(cells, new byte[cells * cells]);
      layout.carve(random);
      layout.braid(random);
      return layout;
   }

   public static LabyrinthLayout fromBytes(int cells, byte[] open) {
      return new LabyrinthLayout(cells, open);
   }

   public byte[] toBytes() {
      return (byte[])this.open.clone();
   }

   public int cells() {
      return this.cells;
   }

   public int span() {
      return 7 * this.cells + 2;
   }

   public static int cellOrigin(int cell) {
      return 2 + 7 * cell;
   }

   public int coreMin() {
      return this.coreMin;
   }

   public int coreMax() {
      return this.coreMax;
   }

   public boolean isCore(int cx, int cz) {
      return cx >= this.coreMin && cx <= this.coreMax && cz >= this.coreMin && cz <= this.coreMax;
   }

   public boolean isDeadEnd(int cx, int cz) {
      return Integer.bitCount(this.open[cx + cz * this.cells]) == 1;
   }

   public int deadEndExit(int cx, int cz) {
      return Integer.numberOfTrailingZeros(this.open[cx + cz * this.cells]);
   }

   public static int stepX(int dir) {
      return DX[dir];
   }

   public static int stepZ(int dir) {
      return DZ[dir];
   }

   public boolean[] carveMask() {
      int span = this.span();
      boolean[] mask = new boolean[span * span];

      for (int cz = 0; cz < this.cells; cz++) {
         for (int cx = 0; cx < this.cells; cx++) {
            int x0 = cellOrigin(cx);
            int z0 = cellOrigin(cz);
            fill(mask, span, x0, z0, x0 + 5 - 1, z0 + 5 - 1);
            if ((this.open[cx + cz * this.cells] & BIT[1]) != 0) {
               fill(mask, span, x0 + 5, z0, x0 + 5 + 2 - 1, z0 + 5 - 1);
            }

            if ((this.open[cx + cz * this.cells] & BIT[2]) != 0) {
               fill(mask, span, x0, z0 + 5, x0 + 5 - 1, z0 + 5 + 2 - 1);
            }
         }
      }

      return mask;
   }

   private static void fill(boolean[] mask, int span, int x0, int z0, int x1, int z1) {
      for (int z = z0; z <= z1; z++) {
         for (int x = x0; x <= x1; x++) {
            mask[x + z * span] = true;
         }
      }
   }

   private void carve(RandomSource random) {
      boolean[] visited = new boolean[this.cells * this.cells];
      Deque<Integer> stack = new ArrayDeque<>();

      for (int cz = this.coreMin; cz <= this.coreMax; cz++) {
         for (int cx = this.coreMin; cx <= this.coreMax; cx++) {
            visited[cx + cz * this.cells] = true;
            if (cx < this.coreMax) {
               this.openBoth(cx, cz, 1);
            }

            if (cz < this.coreMax) {
               this.openBoth(cx, cz, 2);
            }
         }
      }

      int[] seeds = new int[9];

      for (int i = 0; i < seeds.length; i++) {
         seeds[i] = this.coreMin + i % 3 + (this.coreMin + i / 3) * this.cells;
      }

      for (int i = seeds.length - 1; i > 0; i--) {
         int j = random.nextInt(i + 1);
         int swap = seeds[i];
         seeds[i] = seeds[j];
         seeds[j] = swap;
      }

      for (int seed : seeds) {
         stack.addLast(seed);
      }

      int[] candidates = new int[4];

      while (!stack.isEmpty()) {
         int cell = stack.peekLast();
         int cx = cell % this.cells;
         int cz = cell / this.cells;
         int count = 0;

         for (int dir = 0; dir < 4; dir++) {
            int nx = cx + DX[dir];
            int nz = cz + DZ[dir];
            if (this.inBounds(nx, nz) && !visited[nx + nz * this.cells]) {
               candidates[count++] = dir;
            }
         }

         if (count == 0) {
            stack.removeLast();
         } else {
            int dir = candidates[random.nextInt(count)];
            int next = cx + DX[dir] + (cz + DZ[dir]) * this.cells;
            this.openBoth(cx, cz, dir);
            visited[next] = true;
            stack.addLast(next);
         }
      }
   }

   private void braid(RandomSource random) {
      int[] candidates = new int[4];

      for (int cz = 0; cz < this.cells; cz++) {
         for (int cx = 0; cx < this.cells; cx++) {
            if (this.isDeadEnd(cx, cz) && !(random.nextFloat() >= 0.4F)) {
               int count = 0;

               for (int dir = 0; dir < 4; dir++) {
                  int nx = cx + DX[dir];
                  int nz = cz + DZ[dir];
                  if (this.inBounds(nx, nz) && (this.open[cx + cz * this.cells] & BIT[dir]) == 0) {
                     candidates[count++] = dir;
                  }
               }

               if (count > 0) {
                  this.openBoth(cx, cz, candidates[random.nextInt(count)]);
               }
            }
         }
      }
   }

   public int cellIndex(int cx, int cz) {
      return cx + cz * this.cells;
   }

   public int[] graphDistances(int start) {
      int[] distance = new int[this.cells * this.cells];
      Arrays.fill(distance, -1);
      distance[start] = 0;
      Deque<Integer> queue = new ArrayDeque<>();
      queue.add(start);

      while (!queue.isEmpty()) {
         int cell = queue.poll();
         int cx = cell % this.cells;
         int cz = cell / this.cells;

         for (int dir = 0; dir < 4; dir++) {
            if ((this.open[cell] & BIT[dir]) != 0) {
               int next = cx + DX[dir] + (cz + DZ[dir]) * this.cells;
               if (distance[next] < 0) {
                  distance[next] = distance[cell] + 1;
                  queue.add(next);
               }
            }
         }
      }

      return distance;
   }

   public int cellAtDistance(int start, int steps) {
      int[] distance = this.graphDistances(start);
      int best = start;
      int bestGap = Integer.MAX_VALUE;

      for (int cell = 0; cell < distance.length; cell++) {
         if (distance[cell] >= 0 && !this.isCore(cell % this.cells, cell / this.cells)) {
            int gap = Math.abs(distance[cell] - steps);
            if (gap < bestGap) {
               bestGap = gap;
               best = cell;
            }
         }
      }

      return best;
   }

   public boolean[] lightCells() {
      int total = this.cells * this.cells;
      boolean[] lit = new boolean[total];
      boolean[] covered = new boolean[total];

      for (int cz = this.coreMin; cz <= this.coreMax; cz++) {
         for (int cx = this.coreMin; cx <= this.coreMax; cx++) {
            covered[this.cellIndex(cx, cz)] = true;
         }
      }

      for (int cell = 0; cell < total; cell++) {
         if (!covered[cell]) {
            lit[cell] = true;
            int[] distance = this.graphDistances(cell);

            for (int other = 0; other < total; other++) {
               if (distance[other] >= 0 && distance[other] <= 2) {
                  covered[other] = true;
               }
            }
         }
      }

      return lit;
   }

   public int[] floorLight(boolean[] litCells) {
      return this.floorLight(litCells, this.carveMask());
   }

   private int[] floorLight(boolean[] litCells, boolean[] mask) {
      int span = this.span();
      int tall = 12;
      int coreLow = cellOrigin(this.coreMin);
      int coreHigh = cellOrigin(this.coreMax) + 5 - 1;
      int half = 2;
      int[] level = new int[span * span * tall];
      Deque<Integer> queue = new ArrayDeque<>();

      for (int cz = 0; cz < this.cells; cz++) {
         for (int cx = 0; cx < this.cells; cx++) {
            boolean core = this.isCore(cx, cz);
            if (core || litCells[this.cellIndex(cx, cz)]) {
               int at = lightIndex(cellOrigin(cx) + half, core ? 7 : 4, cellOrigin(cz) + half, span, tall);
               level[at] = 15;
               queue.add(at);
            }
         }
      }

      while (!queue.isEmpty()) {
         int at = queue.poll();
         int y = at % tall;
         int x = at / tall % span;
         int z = at / tall / span;
         int next = level[at] - 1;
         if (next > 0) {
            for (int dir = 0; dir < 6; dir++) {
               int nx = x + (dir < 4 ? DX[dir] : 0);
               int nz = z + (dir < 4 ? DZ[dir] : 0);
               int ny = y + (dir == 4 ? 1 : (dir == 5 ? -1 : 0));
               if (nx >= 0 && nx < span && nz >= 0 && nz < span && ny >= 0 && ny < tall) {
                  boolean core = nx >= coreLow && nx <= coreHigh && nz >= coreLow && nz <= coreHigh;
                  if (mask[nx + nz * span] && ny < (core ? 12 : 7)) {
                     int into = lightIndex(nx, ny, nz, span, tall);
                     if (level[into] < next) {
                        level[into] = next;
                        queue.add(into);
                     }
                  }
               }
            }
         }
      }

      int[] floor = new int[span * span];

      for (int i = 0; i < floor.length; i++) {
         floor[i] = mask[i] ? level[lightIndex(i % span, 0, i / span, span, tall)] : -1;
      }

      return floor;
   }

   private static int lightIndex(int x, int y, int z, int span, int tall) {
      return y + x * tall + z * tall * span;
   }

   private void openBoth(int cx, int cz, int dir) {
      this.open[cx + cz * this.cells] = (byte)(this.open[cx + cz * this.cells] | (byte)BIT[dir]);
      int nx = cx + DX[dir];
      int nz = cz + DZ[dir];
      this.open[nx + nz * this.cells] = (byte)(this.open[nx + nz * this.cells] | (byte)BIT[(dir + 2) % 4]);
   }

   private boolean inBounds(int cx, int cz) {
      return cx >= 0 && cx < this.cells && cz >= 0 && cz < this.cells;
   }
}
