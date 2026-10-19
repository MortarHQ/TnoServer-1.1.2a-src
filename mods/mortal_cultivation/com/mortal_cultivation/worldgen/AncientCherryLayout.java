package com.mortal_cultivation.worldgen;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public final class AncientCherryLayout {
   public static final int MAX_RADIUS = 48;
   public static final int MAX_HEIGHT = 104;
   public static final int MIN_Y = -16;
   private static final Comparator<AncientCherryLayout.Point> ORDER = Comparator.comparingInt(AncientCherryLayout.Point::y)
      .thenComparingInt(AncientCherryLayout.Point::x)
      .thenComparingInt(AncientCherryLayout.Point::z);
   private static final int[][] NEIGHBORS = new int[][]{{1, 0, 0}, {-1, 0, 0}, {0, 1, 0}, {0, -1, 0}, {0, 0, 1}, {0, 0, -1}};

   private AncientCherryLayout() {
   }

   public static AncientCherryLayout.Tree create(long seed) {
      Random random = new Random(seed);
      int height = 28 + random.nextInt(3);
      double phase = random.nextDouble() * Math.PI * 2.0;
      AncientCherryLayout.Crown crown = new AncientCherryLayout.Crown(
         phase, 41 + random.nextInt(3), 40 + random.nextInt(3), 29 + random.nextInt(3), 29 + random.nextInt(3)
      );
      Map<AncientCherryLayout.Point, AncientCherryLayout.Axis> logs = new HashMap<>();
      Set<AncientCherryLayout.Point> candidates = new HashSet<>();
      List<AncientCherryLayout.Point> stem = new ArrayList<>();

      for (int y = 0; y <= height; y++) {
         double progress = (double)y / height;
         AncientCherryLayout.Point center = new AncientCherryLayout.Point(
            (int)Math.round((Math.sin(phase + progress * 1.6) - Math.sin(phase)) * 3.0),
            y,
            (int)Math.round((Math.cos(phase + progress * 1.3) - Math.cos(phase)) * 2.0)
         );
         double radius = 4.6 + 3.7 * Math.exp(-y / 10.0) - 1.4 * progress * progress;
         int reach = (int)Math.ceil(radius + 1.5);

         for (int x = -reach; x <= reach; x++) {
            for (int z = -reach; z <= reach; z++) {
               double angle = Math.atan2(z, x);
               double edge = radius + 0.95 * Math.sin(angle * 5.0 + phase + y * 0.055) + 0.4 * Math.sin(angle * 8.0 - y * 0.035);
               if (x * x + z * z <= edge * edge) {
                  logs.put(center.offset(x, 0, z), AncientCherryLayout.Axis.Y);
               }
            }
         }

         if (!stem.isEmpty()) {
            line(logs, stem.getLast(), center);
         }

         stem.add(center);
      }

      for (int i = 0; i < 7; i++) {
         double angle = phase + i * Math.PI * 2.0 / 7.0 + (random.nextDouble() - 0.5) * 0.3;
         int reach = 19 + random.nextInt(6);
         AncientCherryLayout.Point from = stem.get(12 + i % 5).offset((int)Math.round(Math.cos(angle) * 4.0), 0, (int)Math.round(Math.sin(angle) * 4.0));
         AncientCherryLayout.Point bend = radial(angle + 0.12, 9.0, 1);
         AncientCherryLayout.Point tip = radial(angle, reach, -3);
         curve(logs, from, bend, tip, 2.8, 0.7);
         if ((i & 1) == 0) {
            curve(logs, branchPoint(from, bend, tip, 0.68), radial(angle + 0.2, reach - 4, -2), radial(angle + 0.3, reach + 2, -4), 1.4, 0.55);
         }
      }

      List<AncientCherryLayout.Point> anchors = new ArrayList<>();
      anchors.add(stem.getLast());

      for (int i = 0; i < 5; i++) {
         double angle = phase + i * Math.PI * 2.0 / 5.0 + (random.nextDouble() - 0.5) * 0.3;
         int baseY = 14 + random.nextInt(6);
         AncientCherryLayout.Point from = stem.get(baseY);
         AncientCherryLayout.Point tip = crown.tip(angle, 0.7 + random.nextDouble() * 0.08, 0.2);
         AncientCherryLayout.Point bend = new AncientCherryLayout.Point(from.x + (tip.x - from.x) / 3, 28 + random.nextInt(4), from.z + (tip.z - from.z) / 3);
         bough(logs, anchors, from, bend, tip, 3.1, 1.1);
         AncientCherryLayout.Point fork = branchPoint(from, bend, tip, 0.6);
         double forkAngle = angle + ((i & 1) == 0 ? 0.43 : -0.43);
         AncientCherryLayout.Point forkTip = crown.tip(forkAngle, 0.86 + random.nextDouble() * 0.04, 0.2);
         AncientCherryLayout.Point forkBend = new AncientCherryLayout.Point((fork.x + forkTip.x) / 2, fork.y + 4, (fork.z + forkTip.z) / 2);
         bough(logs, anchors, fork, forkBend, forkTip, 2.1, 0.8);
         AncientCherryLayout.Point upper = branchPoint(from, bend, tip, 0.28);
         AncientCherryLayout.Point upperTip = crown.tip(angle - 0.2, 0.4 + random.nextDouble() * 0.12, 0.6);
         bough(logs, anchors, upper, upper.offset(0, 13, 0), upperTip, 2.4, 0.7);
      }

      supportCrown(logs, anchors, candidates, crown, random);
      fillCrown(candidates, crown, seed);
      coverTwigs(logs, candidates, crown);
      candidates.removeAll(logs.keySet());
      Map<AncientCherryLayout.Point, Integer> leaves = supportedLeaves(logs.keySet(), candidates);
      Map<AncientCherryLayout.Point, Boolean> vines = new HashMap<>();
      leaves.keySet()
         .stream()
         .sorted(ORDER)
         .forEach(
            point -> {
               long grain = grain(seed ^ 6006940L, point);
               if (point.y >= 27
                  && point.x * point.x + point.z * point.z >= 441
                  && Math.floorMod(grain, 251) == 0
                  && !leaves.containsKey(point.offset(0, -1, 0))
                  && !logs.containsKey(point.offset(0, -1, 0))) {
                  List<AncientCherryLayout.Point> strand = new ArrayList<>();
                  int length = 2 + Math.floorMod(grain >>> 8, 3);

                  for (int dy = 1; dy <= length; dy++) {
                     AncientCherryLayout.Point next = point.offset(0, -dy, 0);
                     if (next.y < 23 || leaves.containsKey(next) || logs.containsKey(next) || vines.containsKey(next)) {
                        break;
                     }

                     strand.add(next);
                  }

                  for (AncientCherryLayout.Point next : strand) {
                     vines.put(next, next.equals(strand.getLast()));
                  }
               }
            }
         );
      return new AncientCherryLayout.Tree(Map.copyOf(logs), leaves, Map.copyOf(vines));
   }

   public static Map<AncientCherryLayout.Point, Integer> supportedLeaves(Set<AncientCherryLayout.Point> logs, Set<AncientCherryLayout.Point> candidates) {
      Map<AncientCherryLayout.Point, Integer> leaves = new HashMap<>();
      ArrayDeque<AncientCherryLayout.Point> frontier = new ArrayDeque<>(logs);

      while (!frontier.isEmpty()) {
         AncientCherryLayout.Point current = frontier.removeFirst();
         int distance = leaves.getOrDefault(current, 0) + 1;
         if (distance < 7) {
            for (int[] delta : NEIGHBORS) {
               AncientCherryLayout.Point next = current.offset(delta[0], delta[1], delta[2]);
               if (candidates.contains(next) && !logs.contains(next) && !leaves.containsKey(next)) {
                  leaves.put(next, distance);
                  frontier.addLast(next);
               }
            }
         }
      }

      return Map.copyOf(leaves);
   }

   private static void bough(
      Map<AncientCherryLayout.Point, AncientCherryLayout.Axis> logs,
      List<AncientCherryLayout.Point> anchors,
      AncientCherryLayout.Point from,
      AncientCherryLayout.Point bend,
      AncientCherryLayout.Point tip,
      double startRadius,
      double endRadius
   ) {
      curve(logs, from, bend, tip, startRadius, endRadius);

      for (int step = 1; step <= 8; step++) {
         anchors.add(branchPoint(from, bend, tip, step / 8.0));
      }
   }

   private static void supportCrown(
      Map<AncientCherryLayout.Point, AncientCherryLayout.Axis> logs,
      List<AncientCherryLayout.Point> anchors,
      Set<AncientCherryLayout.Point> leaves,
      AncientCherryLayout.Crown crown,
      Random random
   ) {
      List<AncientCherryLayout.Point> buds = new ArrayList<>();

      for (int row = -9; row <= 9; row++) {
         for (int cell = -9; cell <= 9; cell++) {
            int x = (int)Math.round(cell * 5.2 + (row & 1) * 2.6 + (random.nextDouble() - 0.5) * 1.4);
            int z = (int)Math.round(row * 5.2 + (random.nextDouble() - 0.5) * 1.4);
            AncientCherryLayout.Column column = crown.column(x, z);
            if (column != null && column.top - column.bottom >= 7) {
               buds.add(new AncientCherryLayout.Point(x, column.bottom + 3, z));
            }
         }
      }

      buds.sort(Comparator.<AncientCherryLayout.Point>comparingInt(point -> point.x * point.x + point.z * point.z).thenComparing(ORDER));

      for (AncientCherryLayout.Point bud : buds) {
         AncientCherryLayout.Point attachment = anchors.stream().min(Comparator.comparingDouble(point -> distance(point, bud))).orElseThrow();
         AncientCherryLayout.Point bend = new AncientCherryLayout.Point(
            (attachment.x + bud.x) / 2, Math.max(attachment.y, bud.y) + 1, (attachment.z + bud.z) / 2
         );
         curve(logs, attachment, bend, bud, 0.65, 0.55);
         int dx = random.nextInt(3) - 1;
         int dz = random.nextInt(3) - 1;
         AncientCherryLayout.Column column = crown.column(bud.x + dx, bud.z + dz);
         AncientCherryLayout.Point tip = column == null ? bud : new AncientCherryLayout.Point(bud.x + dx, column.top - 2, bud.z + dz);
         AncientCherryLayout.Point middle = bud.offset(random.nextInt(3) - 1, (tip.y - bud.y) / 2, random.nextInt(3) - 1);
         curve(logs, bud, middle, tip, 0.65, 0.5);
         double angle = random.nextDouble() * Math.PI * 2.0;

         for (int i = 0; i < 2; i++) {
            AncientCherryLayout.Point fork = branchPoint(bud, middle, tip, 0.35 + i * 0.35);
            int x = fork.x + (int)Math.round(Math.cos(angle + i * 2.4) * 3.5);
            int z = fork.z + (int)Math.round(Math.sin(angle + i * 2.4) * 3.5);
            AncientCherryLayout.Column edge = crown.column(x, z);
            if (edge != null && edge.top - edge.bottom >= 5) {
               AncientCherryLayout.Point end = new AncientCherryLayout.Point(x, Math.clamp(fork.y + 1, edge.bottom + 2, edge.top - 2), z);
               curve(logs, fork, fork.offset(0, 2, 0), end, 0.5, 0.5);
            }
         }

         anchors.add(bud);
         anchors.add(branchPoint(bud, middle, tip, 0.5));
         anchors.add(tip);
         blossoms(leaves, tip, 4 + random.nextInt(2), 3 + random.nextInt(2), 4 + random.nextInt(2), random.nextLong());
         if (crown.column(bud.x, bud.z).radius > 0.76) {
            blossoms(leaves, bud.offset(0, 1, 0), 4, 3, 4, random.nextLong());
         }
      }
   }

   private static void blossoms(Set<AncientCherryLayout.Point> leaves, AncientCherryLayout.Point center, int rx, int ry, int rz, long seed) {
      for (int x = -rx; x <= rx; x++) {
         for (int y = -ry; y <= ry; y++) {
            for (int z = -rz; z <= rz; z++) {
               double shape = (double)(x * x) / (rx * rx) + (double)(y * y) / (ry * ry) + (double)(z * z) / (rz * rz);
               AncientCherryLayout.Point point = center.offset(x, y, z);
               if (inside(point) && shape <= 1.0 && (shape < 0.8 || (grain(seed, point) & 15L) != 0L)) {
                  leaves.add(point);
               }
            }
         }
      }
   }

   private static void coverTwigs(
      Map<AncientCherryLayout.Point, AncientCherryLayout.Axis> logs, Set<AncientCherryLayout.Point> leaves, AncientCherryLayout.Crown crown
   ) {
      for (AncientCherryLayout.Point point : logs.keySet()) {
         AncientCherryLayout.Column column = crown.column(point.x, point.z);
         if (column != null && point.y >= column.bottom + 2) {
            for (int dx = -2; dx <= 2; dx++) {
               for (int dy = -2; dy <= 2; dy++) {
                  for (int dz = -2; dz <= 2; dz++) {
                     AncientCherryLayout.Point next = point.offset(dx, dy, dz);
                     if (inside(next) && dx * dx + dy * dy + dz * dz <= 4) {
                        leaves.add(next);
                     }
                  }
               }
            }
         }
      }
   }

   private static void fillCrown(Set<AncientCherryLayout.Point> leaves, AncientCherryLayout.Crown crown, long seed) {
      for (int x = -48; x <= 48; x++) {
         for (int z = -48; z <= 48; z++) {
            AncientCherryLayout.Column column = crown.column(x, z);
            if (column != null) {
               for (int y = column.bottom; y <= column.top; y++) {
                  AncientCherryLayout.Point point = new AncientCherryLayout.Point(x, y, z);
                  if (y != column.bottom && y != column.top || (grain(seed, point) & 15L) != 0L) {
                     leaves.add(point);
                  }
               }

               AncientCherryLayout.Point fringe = new AncientCherryLayout.Point(x, column.bottom, z);
               long grain = grain(seed ^ 498367L, fringe);
               if (column.radius > 0.65 && Math.floorMod(grain, 13) == 0) {
                  int length = 1 + Math.floorMod(grain >>> 8, 3);

                  for (int y = 1; y <= length; y++) {
                     leaves.add(fringe.offset(0, -y, 0));
                  }
               }
            }
         }
      }
   }

   private static void curve(
      Map<AncientCherryLayout.Point, AncientCherryLayout.Axis> logs,
      AncientCherryLayout.Point from,
      AncientCherryLayout.Point control,
      AncientCherryLayout.Point to,
      double startRadius,
      double endRadius
   ) {
      int steps = curveSteps(from, control, to);
      AncientCherryLayout.Point previous = from;

      for (int step = 0; step <= steps; step++) {
         double t = (double)step / steps;
         AncientCherryLayout.Point center = bezier(from, control, to, t);
         line(logs, previous, center);
         double radius = startRadius * (1.0 - t) + endRadius * t;
         int reach = (int)Math.ceil(radius);
         AncientCherryLayout.Axis axis = dominant(previous, center);

         for (int x = -reach; x <= reach; x++) {
            for (int y = -reach; y <= reach; y++) {
               for (int z = -reach; z <= reach; z++) {
                  AncientCherryLayout.Point point = center.offset(x, y, z);
                  if (inside(point) && x * x + y * y + z * z <= radius * radius) {
                     logs.putIfAbsent(point, axis);
                  }
               }
            }
         }

         previous = center;
      }
   }

   private static void line(Map<AncientCherryLayout.Point, AncientCherryLayout.Axis> logs, AncientCherryLayout.Point from, AncientCherryLayout.Point to) {
      AncientCherryLayout.Point cursor = from;
      if (inside(cursor)) {
         logs.putIfAbsent(cursor, AncientCherryLayout.Axis.Y);
      }

      while (!cursor.equals(to)) {
         AncientCherryLayout.Axis axis = dominant(cursor, to);

         cursor = switch (axis) {
            case X -> cursor.offset(Integer.signum(to.x - cursor.x), 0, 0);
            case Y -> cursor.offset(0, Integer.signum(to.y - cursor.y), 0);
            case Z -> cursor.offset(0, 0, Integer.signum(to.z - cursor.z));
         };
         if (inside(cursor)) {
            logs.putIfAbsent(cursor, axis);
         }
      }
   }

   private static AncientCherryLayout.Axis dominant(AncientCherryLayout.Point from, AncientCherryLayout.Point to) {
      int x = Math.abs(to.x - from.x);
      int y = Math.abs(to.y - from.y);
      int z = Math.abs(to.z - from.z);
      return x >= y && x >= z && x != 0 ? AncientCherryLayout.Axis.X : (z >= y && z != 0 ? AncientCherryLayout.Axis.Z : AncientCherryLayout.Axis.Y);
   }

   private static int curveSteps(AncientCherryLayout.Point from, AncientCherryLayout.Point control, AncientCherryLayout.Point to) {
      return Math.max(8, (int)Math.ceil((distance(from, control) + distance(control, to)) * 2.0));
   }

   private static AncientCherryLayout.Point branchPoint(
      AncientCherryLayout.Point from, AncientCherryLayout.Point control, AncientCherryLayout.Point to, double fraction
   ) {
      int steps = curveSteps(from, control, to);
      return bezier(from, control, to, (double)Math.round(fraction * steps) / steps);
   }

   private static AncientCherryLayout.Point bezier(AncientCherryLayout.Point a, AncientCherryLayout.Point b, AncientCherryLayout.Point c, double t) {
      double s = 1.0 - t;
      return new AncientCherryLayout.Point(
         (int)Math.round(s * s * a.x + 2.0 * s * t * b.x + t * t * c.x),
         (int)Math.round(s * s * a.y + 2.0 * s * t * b.y + t * t * c.y),
         (int)Math.round(s * s * a.z + 2.0 * s * t * b.z + t * t * c.z)
      );
   }

   private static AncientCherryLayout.Point radial(double angle, double reach, int y) {
      return new AncientCherryLayout.Point((int)Math.round(Math.cos(angle) * reach), y, (int)Math.round(Math.sin(angle) * reach));
   }

   private static double distance(AncientCherryLayout.Point a, AncientCherryLayout.Point b) {
      return Math.sqrt((double)(a.x - b.x) * (a.x - b.x) + (double)(a.y - b.y) * (a.y - b.y) + (double)(a.z - b.z) * (a.z - b.z));
   }

   private static boolean inside(AncientCherryLayout.Point point) {
      return Math.abs(point.x) <= 48 && Math.abs(point.z) <= 48 && point.y >= -16 && point.y <= 104;
   }

   private static long grain(long seed, AncientCherryLayout.Point point) {
      return SpiritSkyIslandLayout.mix(seed ^ point.x * 73428767L ^ point.y * 912931L ^ point.z * 19349663L);
   }

   public enum Axis {
      X,
      Y,
      Z;
   }

   private record Column(int bottom, int top, double radius) {
   }

   private record Crown(double phase, int radiusX, int radiusZ, int base, int rise) {
      private AncientCherryLayout.Column column(int x, int z) {
         double dx = x - Math.sin(this.phase) * 2.0;
         double dz = z - Math.cos(this.phase) * 2.0;
         double radius = Math.sqrt(dx * dx / (this.radiusX * this.radiusX) + dz * dz / (this.radiusZ * this.radiusZ));
         if (radius > 1.08) {
            return null;
         }

         double bottom = Double.POSITIVE_INFINITY;
         double top = Double.NEGATIVE_INFINITY;

         for (int i = -1; i < 7; i++) {
            double cx;
            double cz;
            double cy;
            double rx;
            double rz;
            double ry;
            if (i == -1) {
               cx = Math.cos(this.phase) * 3.0;
               cz = Math.sin(this.phase) * 3.0;
               cy = this.base + 19;
               rx = this.radiusX * 0.59;
               rz = this.radiusZ * 0.59;
               ry = this.rise * 0.43;
            } else {
               double angle = this.phase + i * Math.PI * 2.0 / 7.0 + Math.sin(i * 1.7 + this.phase) * 0.12;
               cx = Math.cos(angle) * this.radiusX * 0.52;
               cz = Math.sin(angle) * this.radiusZ * 0.52;
               cy = this.base + 12 + Math.sin(i * 2.3 + this.phase) * 2.0;
               rx = this.radiusX * (0.47 + Math.sin(i * 1.4 + this.phase) * 0.025);
               rz = this.radiusZ * (0.47 + Math.cos(i * 1.7 + this.phase) * 0.025);
               ry = this.rise * (0.43 + Math.sin(i * 1.9 - this.phase) * 0.025);
            }

            double horizontal = (dx - cx) * (dx - cx) / (rx * rx) + (dz - cz) * (dz - cz) / (rz * rz);
            if (horizontal <= 1.0) {
               double thickness = ry * Math.sqrt(1.0 - horizontal);
               bottom = Math.min(bottom, cy - thickness);
               top = Math.max(top, cy + thickness);
            }
         }

         if (top < bottom) {
            return null;
         }

         double ripple = 0.8 * Math.sin(dx * 0.36 + this.phase) * Math.cos(dz * 0.32 - this.phase);
         return new AncientCherryLayout.Column((int)Math.round(bottom - ripple), (int)Math.round(top + ripple), radius);
      }

      private AncientCherryLayout.Point tip(double angle, double reach, double level) {
         int x;
         int z;
         AncientCherryLayout.Column column;
         do {
            x = (int)Math.round(Math.sin(this.phase) * 2.0 + Math.cos(angle) * this.radiusX * reach);
            z = (int)Math.round(Math.cos(this.phase) * 2.0 + Math.sin(angle) * this.radiusZ * reach);
            column = this.column(x, z);
            reach *= 0.9;
         } while (column == null);

         return new AncientCherryLayout.Point(x, (int)Math.round(column.bottom + (column.top - column.bottom) * level), z);
      }
   }

   public record Point(int x, int y, int z) {
      public AncientCherryLayout.Point offset(int dx, int dy, int dz) {
         return new AncientCherryLayout.Point(this.x + dx, this.y + dy, this.z + dz);
      }

      @Override
      public int hashCode() {
         int hash = this.x * 73428767 ^ this.y * 912931 ^ this.z * 19349663;
         return hash ^ hash >>> 16;
      }
   }

   public record Tree(
      Map<AncientCherryLayout.Point, AncientCherryLayout.Axis> logs,
      Map<AncientCherryLayout.Point, Integer> leaves,
      Map<AncientCherryLayout.Point, Boolean> vines
   ) {
   }
}
