package team.creative.creativecore.common.util.math.base;

import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import team.creative.creativecore.common.util.math.geo.NormalPlaneF;
import team.creative.creativecore.common.util.math.matrix.IntMatrix3c;
import team.creative.creativecore.common.util.math.vec.Vec3f;

public enum Facing {
   DOWN(Axis.Y, false, new Vec3i(0, -1, 0), -1) {
      @Override
      public Facing opposite() {
         return Facing.UP;
      }

      @Override
      public Direction toVanilla() {
         return Direction.DOWN;
      }

      @Override
      public double get(AABB bb) {
         return bb.minY;
      }

      @Override
      public com.mojang.math.Axis rotation() {
         return com.mojang.math.Axis.YN;
      }

      @Override
      public AABB set(AABB bb, double value) {
         return bb.setMinY(value);
      }

      @Override
      public float get(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
         return minY;
      }

      @Override
      public double get(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
         return minY;
      }

      @Override
      public int get(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
         return minY;
      }
   },
   UP(Axis.Y, true, new Vec3i(0, 1, 0), -1) {
      @Override
      public Facing opposite() {
         return Facing.DOWN;
      }

      @Override
      public Direction toVanilla() {
         return Direction.UP;
      }

      @Override
      public double get(AABB bb) {
         return bb.maxY;
      }

      @Override
      public com.mojang.math.Axis rotation() {
         return com.mojang.math.Axis.YP;
      }

      @Override
      public AABB set(AABB bb, double value) {
         return bb.setMaxY(value);
      }

      @Override
      public float get(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
         return maxY;
      }

      @Override
      public double get(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
         return maxY;
      }

      @Override
      public int get(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
         return maxY;
      }
   },
   NORTH(Axis.Z, false, new Vec3i(0, 0, -1), 2) {
      @Override
      public Facing opposite() {
         return SOUTH;
      }

      @Override
      public Direction toVanilla() {
         return Direction.NORTH;
      }

      @Override
      public double get(AABB bb) {
         return bb.minZ;
      }

      @Override
      public com.mojang.math.Axis rotation() {
         return com.mojang.math.Axis.ZN;
      }

      @Override
      public AABB set(AABB bb, double value) {
         return bb.setMinZ(value);
      }

      @Override
      public float get(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
         return minZ;
      }

      @Override
      public double get(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
         return minZ;
      }

      @Override
      public int get(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
         return minZ;
      }
   },
   SOUTH(Axis.Z, true, new Vec3i(0, 0, 1), 0) {
      @Override
      public Facing opposite() {
         return Facing.NORTH;
      }

      @Override
      public Direction toVanilla() {
         return Direction.SOUTH;
      }

      @Override
      public double get(AABB bb) {
         return bb.maxZ;
      }

      @Override
      public com.mojang.math.Axis rotation() {
         return com.mojang.math.Axis.ZP;
      }

      @Override
      public AABB set(AABB bb, double value) {
         return bb.setMaxZ(value);
      }

      @Override
      public float get(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
         return maxZ;
      }

      @Override
      public double get(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
         return maxZ;
      }

      @Override
      public int get(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
         return maxZ;
      }
   },
   WEST(Axis.X, false, new Vec3i(-1, 0, 0), 1) {
      @Override
      public Facing opposite() {
         return Facing.EAST;
      }

      @Override
      public Direction toVanilla() {
         return Direction.WEST;
      }

      @Override
      public double get(AABB bb) {
         return bb.minX;
      }

      @Override
      public com.mojang.math.Axis rotation() {
         return com.mojang.math.Axis.XN;
      }

      @Override
      public AABB set(AABB bb, double value) {
         return bb.setMinX(value);
      }

      @Override
      public float get(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
         return minX;
      }

      @Override
      public double get(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
         return minX;
      }

      @Override
      public int get(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
         return minX;
      }
   },
   EAST(Axis.X, true, new Vec3i(1, 0, 0), 3) {
      @Override
      public Facing opposite() {
         return Facing.WEST;
      }

      @Override
      public Direction toVanilla() {
         return Direction.EAST;
      }

      @Override
      public double get(AABB bb) {
         return bb.maxX;
      }

      @Override
      public com.mojang.math.Axis rotation() {
         return com.mojang.math.Axis.XP;
      }

      @Override
      public AABB set(AABB bb, double value) {
         return bb.setMaxX(value);
      }

      @Override
      public float get(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
         return maxX;
      }

      @Override
      public double get(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
         return maxX;
      }

      @Override
      public int get(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
         return maxX;
      }
   };

   public static final Facing[] VALUES = new Facing[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};
   public static final Facing[] HORIZONTA_VALUES = new Facing[]{SOUTH, WEST, NORTH, EAST};
   public static final String[] FACING_NAMES = new String[]{"down", "up", "north", "south", "west", "east"};
   public static final String[] HORIZONTAL_FACING_NAMES = new String[]{"north", "south", "west", "east"};
   public final String name = this.name().toLowerCase();
   public final Axis axis;
   public final boolean positive;
   public final Vec3i normal;
   public final NormalPlaneF plane;
   public final int horizontalIndex;

   public static Facing get(int index) {
      return VALUES[index];
   }

   public static Facing get(Direction direction) {
      if (direction == null) {
         return null;
      }

      return switch (direction) {
         case DOWN -> DOWN;
         case UP -> UP;
         case NORTH -> NORTH;
         case SOUTH -> SOUTH;
         case WEST -> WEST;
         case EAST -> EAST;
         default -> throw new MatchException(null, null);
      };
   }

   public static Facing get(Axis axis, boolean positive) {
      return switch (axis) {
         case X -> positive ? EAST : WEST;
         case Y -> positive ? UP : DOWN;
         case Z -> positive ? SOUTH : NORTH;
         default -> throw new IllegalArgumentException();
      };
   }

   public static Facing getHorizontal(int index) {
      return HORIZONTA_VALUES[index];
   }

   public static Facing of(Player player) {
      Facing facing = get(player.getDirection());
      if (player.getXRot() > 45.0F) {
         facing = DOWN;
      }

      if (player.getXRot() < -45.0F) {
         facing = UP;
      }

      return facing;
   }

   public static Facing ofNormal(Vec3i vec) {
      if (vec.getX() > 0) {
         return EAST;
      } else if (vec.getX() < 0) {
         return WEST;
      } else if (vec.getY() > 0) {
         return UP;
      } else if (vec.getY() < 0) {
         return DOWN;
      } else if (vec.getZ() > 0) {
         return SOUTH;
      } else if (vec.getZ() < 0) {
         return NORTH;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static Facing direction(Vec3i pos, Vec3i second) {
      if (pos.getX() == second.getX()) {
         if (pos.getY() == second.getY()) {
            return pos.getZ() == second.getZ() + 1 ? SOUTH : NORTH;
         } else {
            return pos.getY() == second.getY() + 1 ? UP : DOWN;
         }
      } else {
         return pos.getX() == second.getX() + 1 ? EAST : WEST;
      }
   }

   public static Facing nearest(Vec3f vec) {
      return nearest(vec.x, vec.y, vec.z);
   }

   public static Facing nearest(float x, float y, float z) {
      if (x == 0.0F && y == 0.0F && z == 0.0F) {
         return null;
      }

      Facing facing = null;
      float distance = Float.MIN_VALUE;

      for (int i = 0; i < VALUES.length; i++) {
         Facing f = VALUES[i];
         float newDistance = x * f.normal.getX() + y * f.normal.getY() + z * f.normal.getZ();
         if (newDistance > distance) {
            distance = newDistance;
            facing = f;
         }
      }

      return facing;
   }

   Facing(Axis axis, boolean positive, Vec3i normal, int horizontalIndex) {
      this.axis = axis;
      this.positive = positive;
      this.normal = normal;
      this.plane = new NormalPlaneF(this);
      this.horizontalIndex = horizontalIndex;
   }

   public int offset() {
      return this.positive ? 1 : -1;
   }

   public int offset(Axis axis) {
      return this.axis == axis ? this.offset() : 0;
   }

   public Component translate() {
      return Component.translatable("facing." + this.name);
   }

   public abstract Facing opposite();

   public abstract Direction toVanilla();

   public Axis one() {
      return this.axis.one();
   }

   public Axis two() {
      return this.axis.two();
   }

   public Axis getUAxis() {
      return switch (this.axis) {
         case X -> Axis.Z;
         case Y -> Axis.X;
         case Z -> Axis.X;
         default -> null;
      };
   }

   public Axis getVAxis() {
      return switch (this.axis) {
         case X -> Axis.Y;
         case Y -> Axis.Z;
         case Z -> Axis.Y;
         default -> null;
      };
   }

   public float getU(float x, float y, float z) {
      return switch (this.axis) {
         case X -> z;
         case Y -> x;
         case Z -> x;
         default -> 0.0F;
      };
   }

   public float getV(float x, float y, float z) {
      return switch (this.axis) {
         case X -> y;
         case Y -> z;
         case Z -> y;
         default -> 0.0F;
      };
   }

   public abstract double get(AABB var1);

   public abstract AABB set(AABB var1, double var2);

   public abstract com.mojang.math.Axis rotation();

   public abstract float get(float var1, float var2, float var3, float var4, float var5, float var6);

   public abstract double get(double var1, double var3, double var5, double var7, double var9, double var11);

   public abstract int get(int var1, int var2, int var3, int var4, int var5, int var6);

   public Facing transform(IntMatrix3c matrix) {
      return ofNormal(matrix.transform(this.normal));
   }
}
