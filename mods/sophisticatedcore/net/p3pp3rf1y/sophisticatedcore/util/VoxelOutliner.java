package net.p3pp3rf1y.sophisticatedcore.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class VoxelOutliner {
   private static final int MASK_EAST = 1;
   private static final int MASK_WEST = 2;
   private static final int MASK_UP = 4;
   private static final int MASK_DOWN = 8;
   private static final int MASK_SOUTH = 16;
   private static final int MASK_NORTH = 32;

   private VoxelOutliner() {
   }

   private static int maskOf(Collection<Direction> dirs) {
      int m = 0;

      for (Direction d : dirs) {
         switch (d) {
            case EAST:
               m |= 1;
               break;
            case WEST:
               m |= 2;
               break;
            case UP:
               m |= 4;
               break;
            case DOWN:
               m |= 8;
               break;
            case SOUTH:
               m |= 16;
               break;
            case NORTH:
               m |= 32;
         }
      }

      return m;
   }

   public static List<VoxelOutliner.Edge> computeShapeRenderableEdges(Level level, List<BlockPos> positions) {
      List<VoxelOutliner.Edge> edges = new ArrayList<>();
      positions.forEach(pos -> {
         if (level.isLoaded(pos) && !level.isEmptyBlock(pos)) {
            BlockState state = level.getBlockState(pos);
            VoxelShape shape = state.getShape(level, pos);
            if (state.getBlock() instanceof IDoubleBlock doubleBlock) {
               VoxelShape finalShape = shape;
               shape = doubleBlock.getOtherPosition(state, pos).map(otherPos -> {
                  if (level.isLoaded(otherPos) && !level.isEmptyBlock(otherPos)) {
                     BlockState otherState = level.getBlockState(otherPos);
                     VoxelShape otherShape = otherState.getShape(level, otherPos);
                     otherShape = otherShape.move(otherPos.getX() - pos.getX(), otherPos.getY() - pos.getY(), otherPos.getZ() - pos.getZ());
                     return Shapes.join(finalShape, otherShape, BooleanOp.OR);
                  } else {
                     return finalShape;
                  }
               }).orElse(shape);
            }

            edges.addAll(linesFromVoxelShapeSimplified(shape, pos));
         }
      });
      return edges;
   }

   public static List<VoxelOutliner.Edge> linesFromVoxelShapeSimplified(VoxelShape shape, BlockPos pos) {
      if (shape.isEmpty()) {
         return List.of();
      }

      List<VoxelOutliner.Edge> edges = new ArrayList<>();
      shape.forAllEdges((minX, minY, minZ, maxX, maxY, maxZ) -> {
         Vec3 a = new Vec3(minX, minY, minZ).add(new Vec3(pos.getX(), pos.getY(), pos.getZ()));
         Vec3 b = new Vec3(maxX, maxY, maxZ).add(new Vec3(pos.getX(), pos.getY(), pos.getZ()));
         edges.add(new VoxelOutliner.Edge(a, b));
      });
      return edges.size() <= 12 ? edges : boundsEdgesFromShape(shape, pos);
   }

   private static double f1(VoxelOutliner.Edge e) {
      return switch (e.axis()) {
         case X -> e.a().y;
         case Y -> e.a().x;
         case Z -> e.a().x;
         default -> throw new MatchException(null, null);
      };
   }

   private static double f2(VoxelOutliner.Edge e) {
      return switch (e.axis()) {
         case X -> e.a().z;
         case Y -> e.a().z;
         case Z -> e.a().y;
         default -> throw new MatchException(null, null);
      };
   }

   private static boolean isSilhouette(VoxelOutliner.Edge e, double[] b, double eps) {
      double f1 = f1(e);
      double f2 = f2(e);
      return nearly(f1, b[0], eps) || nearly(f1, b[1], eps) || nearly(f2, b[2], eps) || nearly(f2, b[3], eps);
   }

   private static boolean nearly(double a, double b, double eps) {
      return Math.abs(a - b) <= eps;
   }

   public static List<VoxelOutliner.Edge> computeRenderableEdges(Collection<BlockPos> blocks) {
      HashSet<BlockPos> solid = new HashSet<>(blocks);
      VoxelOutliner.NormalsAccumulator acc = new VoxelOutliner.NormalsAccumulator();

      for (BlockPos p : solid) {
         int x = p.getX();
         int y = p.getY();
         int z = p.getZ();

         for (Direction dir : Direction.values()) {
            if (!solid.contains(p.relative(dir))) {
               switch (dir.getAxis()) {
                  case X:
                     int xf = dir == Direction.EAST ? x + 1 : x;
                     acc.addFaceEdges(
                        dir,
                        new VoxelOutliner.EdgeKey(xf, y, z, xf, y + 1, z),
                        new VoxelOutliner.EdgeKey(xf, y + 1, z, xf, y + 1, z + 1),
                        new VoxelOutliner.EdgeKey(xf, y + 1, z + 1, xf, y, z + 1),
                        new VoxelOutliner.EdgeKey(xf, y, z + 1, xf, y, z)
                     );
                     break;
                  case Y:
                     int yf = dir == Direction.UP ? y + 1 : y;
                     acc.addFaceEdges(
                        dir,
                        new VoxelOutliner.EdgeKey(x, yf, z, x + 1, yf, z),
                        new VoxelOutliner.EdgeKey(x + 1, yf, z, x + 1, yf, z + 1),
                        new VoxelOutliner.EdgeKey(x + 1, yf, z + 1, x, yf, z + 1),
                        new VoxelOutliner.EdgeKey(x, yf, z + 1, x, yf, z)
                     );
                     break;
                  case Z:
                     int zf = dir == Direction.SOUTH ? z + 1 : z;
                     acc.addFaceEdges(
                        dir,
                        new VoxelOutliner.EdgeKey(x, y, zf, x + 1, y, zf),
                        new VoxelOutliner.EdgeKey(x + 1, y, zf, x + 1, y + 1, zf),
                        new VoxelOutliner.EdgeKey(x + 1, y + 1, zf, x, y + 1, zf),
                        new VoxelOutliner.EdgeKey(x, y + 1, zf, x, y, zf)
                     );
               }
            }
         }
      }

      List<VoxelOutliner.EdgeInt> edges = new ArrayList<>();

      for (Entry<VoxelOutliner.EdgeKey, EnumMap<Direction, Integer>> e : acc.edgeToNormals.entrySet()) {
         EnumMap<Direction, Integer> normals = e.getValue();
         int total = normals.values().stream().mapToInt(i -> i).sum();
         if (normals.size() != 1 || total != 2) {
            int edgeMask = maskOf(normals.keySet());
            VoxelOutliner.EdgeKey k = e.getKey();
            edges.add(new VoxelOutliner.EdgeInt(k.x1(), k.y1(), k.z1(), k.x2(), k.y2(), k.z2(), k.axis(), edgeMask));
         }
      }

      edges = mergeColinearEdges(edges);
      List<VoxelOutliner.Edge> out = new ArrayList<>(edges.size());

      for (VoxelOutliner.EdgeInt e : edges) {
         Vec3 a = new Vec3(e.x1, e.y1, e.z1);
         Vec3 b = new Vec3(e.x2, e.y2, e.z2);
         out.add(new VoxelOutliner.Edge(a, b));
      }

      return out;
   }

   private static List<VoxelOutliner.EdgeInt> mergeColinearEdges(List<VoxelOutliner.EdgeInt> edges) {
      record GKey(Axis axis, int a, int b, int mask) {
      }

      Map<GKey, List<VoxelOutliner.EdgeInt>> groups = new HashMap<>();

      for (VoxelOutliner.EdgeInt e : edges) {
         switch (e.axis) {
            case X:
               groups.computeIfAbsent(new GKey(Axis.X, e.y1, e.z1, e.edgeMask), k -> new ArrayList<>()).add(e);
               break;
            case Y:
               groups.computeIfAbsent(new GKey(Axis.Y, e.x1, e.z1, e.edgeMask), k -> new ArrayList<>()).add(e);
               break;
            case Z:
               groups.computeIfAbsent(new GKey(Axis.Z, e.x1, e.y1, e.edgeMask), k -> new ArrayList<>()).add(e);
         }
      }

      List<VoxelOutliner.EdgeInt> merged = new ArrayList<>(edges.size());

      for (Entry<GKey, List<VoxelOutliner.EdgeInt>> ent : groups.entrySet()) {
         List<VoxelOutliner.EdgeInt> list = ent.getValue();
         if (!list.isEmpty()) {
            Axis axis = ent.getKey().axis();
            int mask = ent.getKey().mask();
            switch (axis) {
               case X:
                  list.sort(Comparator.comparingInt(ex -> ex.x1));
                  int y = list.get(0).y1;
                  int z = list.get(0).z1;
                  int s = list.get(0).x1;
                  int epos = list.get(0).x2;
                  int i = 1;

                  for (; i < list.size(); i++) {
                     VoxelOutliner.EdgeInt e = list.get(i);
                     if (e.y1 == y && e.z1 == z && e.x1 == epos) {
                        epos = e.x2;
                     } else {
                        merged.add(new VoxelOutliner.EdgeInt(s, y, z, epos, y, z, Axis.X, mask));
                        y = e.y1;
                        z = e.z1;
                        s = e.x1;
                        epos = e.x2;
                     }
                  }

                  merged.add(new VoxelOutliner.EdgeInt(s, y, z, epos, y, z, Axis.X, mask));
                  break;
               case Y:
                  list.sort(Comparator.comparingInt(ex -> ex.y1));
                  int x = list.get(0).x1;
                  int z = list.get(0).z1;
                  int s = list.get(0).y1;
                  int epos = list.get(0).y2;

                  for (int i = 1; i < list.size(); i++) {
                     VoxelOutliner.EdgeInt e = list.get(i);
                     if (e.x1 == x && e.z1 == z && e.y1 == epos) {
                        epos = e.y2;
                     } else {
                        merged.add(new VoxelOutliner.EdgeInt(x, s, z, x, epos, z, Axis.Y, mask));
                        x = e.x1;
                        z = e.z1;
                        s = e.y1;
                        epos = e.y2;
                     }
                  }

                  merged.add(new VoxelOutliner.EdgeInt(x, s, z, x, epos, z, Axis.Y, mask));
                  break;
               case Z:
                  list.sort(Comparator.comparingInt(ex -> ex.z1));
                  int x = list.get(0).x1;
                  int y = list.get(0).y1;
                  int s = list.get(0).z1;
                  int epos = list.get(0).z2;

                  for (int i = 1; i < list.size(); i++) {
                     VoxelOutliner.EdgeInt e = list.get(i);
                     if (e.x1 == x && e.y1 == y && e.z1 == epos) {
                        epos = e.z2;
                     } else {
                        merged.add(new VoxelOutliner.EdgeInt(x, y, s, x, y, epos, Axis.Z, mask));
                        x = e.x1;
                        y = e.y1;
                        s = e.z1;
                        epos = e.z2;
                     }
                  }

                  merged.add(new VoxelOutliner.EdgeInt(x, y, s, x, y, epos, Axis.Z, mask));
            }
         }
      }

      return merged;
   }

   private static List<VoxelOutliner.Edge> boundsEdgesFromShape(VoxelShape shape, BlockPos pos) {
      return boxEdges(
         shape.min(Axis.X) + pos.getX(),
         shape.min(Axis.Y) + pos.getY(),
         shape.min(Axis.Z) + pos.getZ(),
         shape.max(Axis.X) + pos.getX(),
         shape.max(Axis.Y) + pos.getY(),
         shape.max(Axis.Z) + pos.getZ()
      );
   }

   private static List<VoxelOutliner.Edge> boxEdges(double x0, double y0, double z0, double x1, double y1, double z1) {
      if (!(x1 <= x0) && !(y1 <= y0) && !(z1 <= z0)) {
         ArrayList<VoxelOutliner.Edge> out = new ArrayList<>(12);
         out.add(new VoxelOutliner.Edge(new Vec3(x0, y0, z0), new Vec3(x1, y0, z0)));
         out.add(new VoxelOutliner.Edge(new Vec3(x1, y0, z0), new Vec3(x1, y0, z1)));
         out.add(new VoxelOutliner.Edge(new Vec3(x1, y0, z1), new Vec3(x0, y0, z1)));
         out.add(new VoxelOutliner.Edge(new Vec3(x0, y0, z1), new Vec3(x0, y0, z0)));
         out.add(new VoxelOutliner.Edge(new Vec3(x0, y1, z0), new Vec3(x1, y1, z0)));
         out.add(new VoxelOutliner.Edge(new Vec3(x1, y1, z0), new Vec3(x1, y1, z1)));
         out.add(new VoxelOutliner.Edge(new Vec3(x1, y1, z1), new Vec3(x0, y1, z1)));
         out.add(new VoxelOutliner.Edge(new Vec3(x0, y1, z1), new Vec3(x0, y1, z0)));
         out.add(new VoxelOutliner.Edge(new Vec3(x0, y0, z0), new Vec3(x0, y1, z0)));
         out.add(new VoxelOutliner.Edge(new Vec3(x1, y0, z0), new Vec3(x1, y1, z0)));
         out.add(new VoxelOutliner.Edge(new Vec3(x1, y0, z1), new Vec3(x1, y1, z1)));
         out.add(new VoxelOutliner.Edge(new Vec3(x0, y0, z1), new Vec3(x0, y1, z1)));
         return out;
      } else {
         return List.of();
      }
   }

   public static List<VoxelOutliner.Edge> edgesFromAABB(AABB aabb) {
      return boxEdges(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ);
   }

   public static final class Edge {
      private final Vec3 a;
      private final Vec3 b;
      private final Axis axis;
      private final double length;

      public Edge(int x1, int y1, int z1, int x2, int y2, int z2) {
         this(new Vec3(x1, y1, z1), new Vec3(x2, y2, z2));
      }

      public Edge(Vec3 a, Vec3 b) {
         this.a = a;
         this.b = b;
         if (a.x != b.x) {
            this.axis = Axis.X;
         } else if (a.y != b.y) {
            this.axis = Axis.Y;
         } else {
            this.axis = Axis.Z;
         }

         this.length = a.distanceTo(b);
      }

      public Vec3 a() {
         return this.a;
      }

      public Vec3 b() {
         return this.b;
      }

      public Axis axis() {
         return this.axis;
      }

      public double length() {
         return this.length;
      }
   }

   private record EdgeInt(int x1, int y1, int z1, int x2, int y2, int z2, Axis axis, int edgeMask) {
   }

   private record EdgeKey(int x1, int y1, int z1, int x2, int y2, int z2) {
      EdgeKey {
         if (x1 > x2 || x1 == x2 && (y1 > y2 || y1 == y2 && z1 > z2)) {
            int tx = x1;
            int ty = y1;
            int tz = z1;
            x1 = x2;
            y1 = y2;
            z1 = z2;
            x2 = tx;
            y2 = ty;
            z2 = tz;
         }
      }

      Axis axis() {
         if (this.x1 != this.x2) {
            return Axis.X;
         } else if (this.y1 != this.y2) {
            return Axis.Y;
         } else if (this.z1 != this.z2) {
            return Axis.Z;
         } else {
            throw new IllegalStateException("Non-axis aligned edge");
         }
      }
   }

   private static final class NormalsAccumulator {
      private final Map<VoxelOutliner.EdgeKey, EnumMap<Direction, Integer>> edgeToNormals = new HashMap<>();

      void addFaceEdges(Direction faceNormal, VoxelOutliner.EdgeKey... keys) {
         for (VoxelOutliner.EdgeKey k : keys) {
            EnumMap<Direction, Integer> m = this.edgeToNormals.computeIfAbsent(k, kk -> new EnumMap<>(Direction.class));
            m.merge(faceNormal, 1, Integer::sum);
         }
      }
   }
}
