package com.mortal_cultivation.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class SpiritSkyIslandLayout {
   public static final int CELL_SIZE = 160;
   public static final int MAX_REACH = 104;

   private SpiritSkyIslandLayout() {
   }

   public static List<SpiritSkyIslandLayout.Site> create(long worldSeed, int cellX, int cellZ) {
      Random random = new Random(mix(worldSeed ^ cellX * 341873128712L ^ cellZ * 132897987541L));
      int x = cellX * 160 + 80 + random.nextInt(17) - 8;
      int z = cellZ * 160 + 80 + random.nextInt(17) - 8;
      int y = 226 + random.nextInt(19);
      double angle = random.nextDouble() * Math.PI * 2.0;
      List<SpiritSkyIslandLayout.Site> sites = new ArrayList<>();
      SpiritSkyIslandLayout.Island main = new SpiritSkyIslandLayout.Island(
         x, y, z, 29 + random.nextInt(9), 23 + random.nextInt(9), 25 + random.nextInt(10), random.nextLong()
      );
      int waterDirection = random.nextInt(4);
      SpiritSkyIslandLayout.Stream mainStream = stream(main, waterDirection, 1 + random.nextInt(3));
      sites.add(new SpiritSkyIslandLayout.Site(main, mainStream));
      SpiritSkyIslandLayout.Outlet landing = mainStream.outlets().getFirst();
      SpiritSkyIslandLayout.Island lower = new SpiritSkyIslandLayout.Island(
         landing.x + mainStream.dx * 4,
         y - 22 - random.nextInt(7),
         landing.z + mainStream.dz * 4,
         13 + random.nextInt(4),
         12 + random.nextInt(4),
         14 + random.nextInt(6),
         random.nextLong()
      );
      lower = separateBelow(main, lower);
      sites.add(new SpiritSkyIslandLayout.Site(lower, receivingStream(lower, mainStream, landing, 1 + random.nextInt(2))));
      double peakDirection = angle + 2.4;
      SpiritSkyIslandLayout.Island peak = new SpiritSkyIslandLayout.Island(
         x + (int)Math.round(Math.cos(peakDirection) * 53.0),
         y + 9 + random.nextInt(8),
         z + (int)Math.round(Math.sin(peakDirection) * 53.0),
         6 + random.nextInt(4),
         7 + random.nextInt(5),
         25 + random.nextInt(10),
         random.nextLong()
      );
      sites.add(new SpiritSkyIslandLayout.Site(peak, null));
      int fragments = 2 + random.nextInt(2);

      for (int i = 0; i < fragments; i++) {
         double direction = angle + i * 1.9 + random.nextDouble() * 0.35;
         int distance = 57 + random.nextInt(13);
         int sx = x + (int)Math.round(Math.cos(direction) * distance);
         int sz = z + (int)Math.round(Math.sin(direction) * distance);
         int sy = y - 15 + random.nextInt(30);
         SpiritSkyIslandLayout.Island island = new SpiritSkyIslandLayout.Island(
            sx, sy, sz, 3 + random.nextInt(4), 4 + random.nextInt(4), 6 + random.nextInt(7), random.nextLong()
         );
         sites.add(new SpiritSkyIslandLayout.Site(island, null));
      }

      return List.copyOf(sites);
   }

   public static long mix(long value) {
      value = (value ^ value >>> 30) * -4658895280553007687L;
      value = (value ^ value >>> 27) * -7723592293110705685L;
      return value ^ value >>> 31;
   }

   private static SpiritSkyIslandLayout.Island separateBelow(SpiritSkyIslandLayout.Island upper, SpiritSkyIslandLayout.Island lower) {
      int drop = 0;

      for (int x = Math.max(upper.minX(), lower.minX()); x <= Math.min(upper.maxX(), lower.maxX()); x++) {
         for (int z = Math.max(upper.minZ(), lower.minZ()); z <= Math.min(upper.maxZ(), lower.maxZ()); z++) {
            SpiritSkyIslandLayout.Column ceiling = upper.column(x, z);
            SpiritSkyIslandLayout.Column floor = lower.column(x, z);
            if (ceiling != null && floor != null) {
               drop = Math.max(drop, floor.top - ceiling.bottom + 5);
            }
         }
      }

      return drop == 0 ? lower : new SpiritSkyIslandLayout.Island(lower.x, lower.y - drop, lower.z, lower.radiusX, lower.radiusZ, lower.depth, lower.seed);
   }

   private static SpiritSkyIslandLayout.Stream stream(SpiritSkyIslandLayout.Island island, int direction, int width) {
      int dx = direction == 0 ? 1 : (direction == 2 ? -1 : 0);
      int dz = direction == 1 ? 1 : (direction == 3 ? -1 : 0);
      List<Integer> edges = edges(island, island.x, island.z, dx, dz, width);
      int shortestEdge = edges.stream().mapToInt(Integer::intValue).min().orElseThrow();
      int start = Math.max(1, shortestEdge - 10);
      return atSurface(island, new SpiritSkyIslandLayout.Stream(island.x, island.z, dx, dz, start, start + 2, List.copyOf(edges), 0));
   }

   private static SpiritSkyIslandLayout.Stream receivingStream(
      SpiritSkyIslandLayout.Island island, SpiritSkyIslandLayout.Stream upstream, SpiritSkyIslandLayout.Outlet landing, int width
   ) {
      List<Integer> edges = edges(island, landing.x, landing.z, upstream.dx, upstream.dz, width);
      return atSurface(island, new SpiritSkyIslandLayout.Stream(landing.x, landing.z, upstream.dx, upstream.dz, -3, 0, List.copyOf(edges), 0));
   }

   private static List<Integer> edges(SpiritSkyIslandLayout.Island island, int x, int z, int dx, int dz, int width) {
      List<Integer> edges = new ArrayList<>();

      for (int lane = 0; lane < width; lane++) {
         int edge = 0;

         while (island.column(x + dx * (edge + 1) - dz * lane, z + dz * (edge + 1) + dx * lane) != null) {
            edge++;
         }

         edges.add(edge);
      }

      return edges;
   }

   private static SpiritSkyIslandLayout.Stream atSurface(SpiritSkyIslandLayout.Island island, SpiritSkyIslandLayout.Stream draft) {
      int waterY = Integer.MAX_VALUE;

      for (int x = island.minX(); x <= island.maxX(); x++) {
         for (int z = island.minZ(); z <= island.maxZ(); z++) {
            SpiritSkyIslandLayout.Column column = island.column(x, z);
            if (column != null && draft.contains(x, z)) {
               waterY = Math.min(waterY, column.top - 1);
            }
         }
      }

      return new SpiritSkyIslandLayout.Stream(draft.x, draft.z, draft.dx, draft.dz, draft.start, draft.poolDistance, draft.edges, waterY);
   }

   public record Column(int top, int bottom) {
   }

   public record Island(int x, int y, int z, int radiusX, int radiusZ, int depth, long seed) {
      public int minX() {
         return this.x - this.reachX();
      }

      public int maxX() {
         return this.x + this.reachX();
      }

      public int minZ() {
         return this.z - this.reachZ();
      }

      public int maxZ() {
         return this.z + this.reachZ();
      }

      private double rotation() {
         return (this.seed >>> 16 & 65535L) * Math.PI / 32768.0;
      }

      private int reachX() {
         double cosine = Math.cos(this.rotation());
         double sine = Math.sin(this.rotation());
         return (int)Math.ceil(Math.sqrt(this.radiusX * this.radiusX * cosine * cosine + this.radiusZ * this.radiusZ * sine * sine) * 1.16);
      }

      private int reachZ() {
         double cosine = Math.cos(this.rotation());
         double sine = Math.sin(this.rotation());
         return (int)Math.ceil(Math.sqrt(this.radiusX * this.radiusX * sine * sine + this.radiusZ * this.radiusZ * cosine * cosine) * 1.16);
      }

      public boolean intersects(int minX, int minZ, int maxX, int maxZ) {
         return this.maxX() + 1 >= minX && this.minX() - 1 <= maxX && this.maxZ() + 1 >= minZ && this.minZ() - 1 <= maxZ;
      }

      public SpiritSkyIslandLayout.Column column(int blockX, int blockZ) {
         double cosine = Math.cos(this.rotation());
         double sine = Math.sin(this.rotation());
         double dx = ((blockX - this.x) * cosine + (blockZ - this.z) * sine) / this.radiusX;
         double dz = (-(blockX - this.x) * sine + (blockZ - this.z) * cosine) / this.radiusZ;
         double phase = (this.seed & 65535L) * Math.PI / 32768.0;
         double angle = Math.atan2(dz, dx);
         double rim = 1.0 + Math.sin(angle * 3.0 + phase) * 0.1 + Math.cos(angle * 5.0 - phase) * 0.05;
         double radial = Math.sqrt(dx * dx + dz * dz) / rim;
         if (radial > 1.0) {
            return null;
         }

         double peak = this.radiusX <= 9 && this.depth >= 24 ? Math.pow(1.0 - radial, 1.3) * 7.0 : 0.0;
         int top = this.y
            + (int)Math.floor(Math.sin((blockX - this.x) * 0.17 + phase) * 1.4 + Math.cos((blockZ - this.z) * 0.19 - phase) * 1.1 + (1.0 - radial) * 1.8 + peak);
         int roughness = (int)(SpiritSkyIslandLayout.mix(this.seed ^ blockX * 73428767L ^ blockZ * 912367L) & 1L);
         int thickness = (this.radiusX < 12 && this.radiusZ < 12 ? 3 : 8) + (int)Math.round(this.depth * Math.pow(1.0 - radial * radial, 1.35)) + roughness;
         return new SpiritSkyIslandLayout.Column(top, top - thickness);
      }
   }

   public record Outlet(int x, int y, int z) {
   }

   public record Site(SpiritSkyIslandLayout.Island island, SpiritSkyIslandLayout.Stream stream) {
   }

   public record Stream(int x, int z, int dx, int dz, int start, int poolDistance, List<Integer> edges, int waterY) {
      public boolean contains(int blockX, int blockZ) {
         int along = (blockX - this.x) * this.dx + (blockZ - this.z) * this.dz;
         int lane = (blockX - this.x) * -this.dz + (blockZ - this.z) * this.dx;
         int poolX = along - this.poolDistance;
         return poolX * poolX + lane * lane <= 9 ? true : lane >= 0 && lane < this.edges.size() && along >= this.start && along <= this.edges.get(lane);
      }

      public List<SpiritSkyIslandLayout.Outlet> outlets() {
         List<SpiritSkyIslandLayout.Outlet> result = new ArrayList<>();

         for (int lane = 0; lane < this.edges.size(); lane++) {
            int distance = this.edges.get(lane) + 1;
            result.add(
               new SpiritSkyIslandLayout.Outlet(this.x + this.dx * distance - this.dz * lane, this.waterY, this.z + this.dz * distance + this.dx * lane)
            );
         }

         return List.copyOf(result);
      }
   }
}
