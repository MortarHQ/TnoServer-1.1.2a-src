package team.creative.creativecore.common.util.math.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import org.joml.Vector3f;
import team.creative.creativecore.common.util.math.matrix.IntMatrix3;
import team.creative.creativecore.common.util.math.matrix.IntMatrix3c;
import team.creative.creativecore.common.util.math.vec.Vec3d;
import team.creative.creativecore.common.util.math.vec.Vec3f;

public enum Axis {
   X(new IntMatrix3(-1, 0, 0, 0, 1, 0, 0, 0, 1)) {
      @Override
      public double get(double x, double y, double z) {
         return x;
      }

      @Override
      public float get(float x, float y, float z) {
         return x;
      }

      @Override
      public int get(int x, int y, int z) {
         return x;
      }

      @Override
      public <T> T get(T x, T y, T z) {
         return x;
      }

      @Override
      public int get(Vec3i vec) {
         return vec.getX();
      }

      @Override
      public Vec3i set(Vec3i vec, int value) {
         return new Vec3i(value, vec.getY(), vec.getZ());
      }

      @Override
      public BlockPos set(BlockPos pos, int value) {
         return new BlockPos(value, pos.getY(), pos.getZ());
      }

      @Override
      public void set(MutableBlockPos pos, int value) {
         pos.setX(value);
      }

      @Override
      public SectionPos set(SectionPos pos, int value) {
         return SectionPos.of(value, pos.getY(), pos.getZ());
      }

      @Override
      public int get(ChunkPos pos) {
         return pos.x;
      }

      @Override
      public ChunkPos set(ChunkPos pos, int value) {
         return new ChunkPos(value, pos.z);
      }

      @Override
      public double get(Vec3 vec) {
         return vec.x;
      }

      @Override
      public Vec3 set(Vec3 vec, double value) {
         return new Vec3(value, vec.y, vec.z);
      }

      @Override
      public double get(Vector3d vec) {
         return vec.x;
      }

      @Override
      public void set(Vector3d vec, double value) {
         vec.x = value;
      }

      @Override
      public float get(Vector3f vec) {
         return vec.x;
      }

      @Override
      public void set(Vector3f vec, float value) {
         vec.x = value;
      }

      @Override
      public Axis one() {
         return Axis.Y;
      }

      @Override
      public Axis two() {
         return Axis.Z;
      }

      @Override
      public Facing facing(boolean positive) {
         return positive ? Facing.EAST : Facing.WEST;
      }

      @Override
      public net.minecraft.core.Direction.Axis toVanilla() {
         return net.minecraft.core.Direction.Axis.X;
      }

      @Override
      public Vec3i mirror(Vec3i vec) {
         return new Vec3i(-vec.getX(), vec.getY(), vec.getZ());
      }

      @Override
      public BlockPos mirror(BlockPos vec) {
         return new BlockPos(-vec.getX(), vec.getY(), vec.getZ());
      }
   },
   Y(new IntMatrix3(1, 0, 0, 0, -1, 0, 0, 0, 1)) {
      @Override
      public double get(double x, double y, double z) {
         return y;
      }

      @Override
      public float get(float x, float y, float z) {
         return y;
      }

      @Override
      public int get(int x, int y, int z) {
         return y;
      }

      @Override
      public <T> T get(T x, T y, T z) {
         return y;
      }

      @Override
      public int get(Vec3i vec) {
         return vec.getY();
      }

      @Override
      public Vec3i set(Vec3i vec, int value) {
         return new Vec3i(vec.getX(), value, vec.getZ());
      }

      @Override
      public BlockPos set(BlockPos pos, int value) {
         return new BlockPos(pos.getX(), value, pos.getZ());
      }

      @Override
      public void set(MutableBlockPos pos, int value) {
         pos.setY(value);
      }

      @Override
      public SectionPos set(SectionPos pos, int value) {
         return SectionPos.of(pos.getX(), value, pos.getZ());
      }

      @Override
      public int get(ChunkPos pos) {
         throw new UnsupportedOperationException();
      }

      @Override
      public ChunkPos set(ChunkPos pos, int value) {
         throw new UnsupportedOperationException();
      }

      @Override
      public double get(Vec3 vec) {
         return vec.y;
      }

      @Override
      public Vec3 set(Vec3 vec, double value) {
         return new Vec3(vec.x, value, vec.z);
      }

      @Override
      public double get(Vector3d vec) {
         return vec.y;
      }

      @Override
      public void set(Vector3d vec, double value) {
         vec.y = value;
      }

      @Override
      public float get(Vector3f vec) {
         return vec.y;
      }

      @Override
      public void set(Vector3f vec, float value) {
         vec.y = value;
      }

      @Override
      public Axis one() {
         return Axis.Z;
      }

      @Override
      public Axis two() {
         return Axis.X;
      }

      @Override
      public Facing facing(boolean positive) {
         return positive ? Facing.UP : Facing.DOWN;
      }

      @Override
      public net.minecraft.core.Direction.Axis toVanilla() {
         return net.minecraft.core.Direction.Axis.Y;
      }

      @Override
      public Vec3i mirror(Vec3i vec) {
         return new Vec3i(vec.getX(), -vec.getY(), vec.getZ());
      }

      @Override
      public BlockPos mirror(BlockPos vec) {
         return new BlockPos(vec.getX(), -vec.getY(), vec.getZ());
      }
   },
   Z(new IntMatrix3(1, 0, 0, 0, 1, 0, 0, 0, -1)) {
      @Override
      public double get(double x, double y, double z) {
         return z;
      }

      @Override
      public float get(float x, float y, float z) {
         return z;
      }

      @Override
      public int get(int x, int y, int z) {
         return z;
      }

      @Override
      public <T> T get(T x, T y, T z) {
         return z;
      }

      @Override
      public int get(Vec3i vec) {
         return vec.getZ();
      }

      @Override
      public Vec3i set(Vec3i vec, int value) {
         return new Vec3i(vec.getX(), vec.getY(), value);
      }

      @Override
      public BlockPos set(BlockPos pos, int value) {
         return new BlockPos(pos.getX(), pos.getY(), value);
      }

      @Override
      public void set(MutableBlockPos pos, int value) {
         pos.setZ(value);
      }

      @Override
      public SectionPos set(SectionPos pos, int value) {
         return SectionPos.of(pos.getX(), pos.getY(), value);
      }

      @Override
      public int get(ChunkPos pos) {
         return pos.z;
      }

      @Override
      public ChunkPos set(ChunkPos pos, int value) {
         return new ChunkPos(pos.x, value);
      }

      @Override
      public double get(Vec3 vec) {
         return vec.z;
      }

      @Override
      public Vec3 set(Vec3 vec, double value) {
         return new Vec3(vec.x, vec.y, value);
      }

      @Override
      public double get(Vector3d vec) {
         return vec.z;
      }

      @Override
      public void set(Vector3d vec, double value) {
         vec.z = value;
      }

      @Override
      public float get(Vector3f vec) {
         return vec.z;
      }

      @Override
      public void set(Vector3f vec, float value) {
         vec.z = value;
      }

      @Override
      public Axis one() {
         return Axis.X;
      }

      @Override
      public Axis two() {
         return Axis.Y;
      }

      @Override
      public Facing facing(boolean positive) {
         return positive ? Facing.SOUTH : Facing.NORTH;
      }

      @Override
      public net.minecraft.core.Direction.Axis toVanilla() {
         return net.minecraft.core.Direction.Axis.Z;
      }

      @Override
      public Vec3i mirror(Vec3i vec) {
         return new Vec3i(vec.getX(), vec.getY(), -vec.getZ());
      }

      @Override
      public BlockPos mirror(BlockPos vec) {
         return new BlockPos(vec.getX(), vec.getY(), -vec.getZ());
      }
   };

   private final IntMatrix3c matrix;

   public static Axis get(net.minecraft.core.Direction.Axis axis) {
      return switch (axis) {
         case X -> X;
         case Y -> Y;
         case Z -> Z;
         default -> throw new IllegalArgumentException();
      };
   }

   public static Axis third(Axis one, Axis two) {
      return switch (one) {
         case X -> two == Y ? Z : Y;
         case Y -> two == X ? Z : X;
         case Z -> two == Y ? X : Y;
      };
   }

   public static Axis getMirrorAxis(Mirror mirrorIn) {
      return switch (mirrorIn) {
         case FRONT_BACK -> X;
         case LEFT_RIGHT -> Z;
         default -> null;
      };
   }

   Axis(IntMatrix3c matrix) {
      this.matrix = matrix;
   }

   public IntMatrix3c getMatrix() {
      return this.matrix;
   }

   public abstract Axis one();

   public abstract Axis two();

   public abstract Facing facing(boolean var1);

   public abstract double get(double var1, double var3, double var5);

   public abstract float get(float var1, float var2, float var3);

   public abstract int get(int var1, int var2, int var3);

   public abstract int get(Vec3i var1);

   public abstract Vec3i set(Vec3i var1, int var2);

   public abstract BlockPos set(BlockPos var1, int var2);

   public abstract void set(MutableBlockPos var1, int var2);

   public abstract SectionPos set(SectionPos var1, int var2);

   public abstract int get(ChunkPos var1);

   public abstract ChunkPos set(ChunkPos var1, int var2);

   public abstract double get(Vec3 var1);

   public abstract Vec3 set(Vec3 var1, double var2);

   public abstract double get(Vector3d var1);

   public abstract void set(Vector3d var1, double var2);

   public abstract float get(Vector3f var1);

   public abstract void set(Vector3f var1, float var2);

   public abstract <T> T get(T var1, T var2, T var3);

   public abstract net.minecraft.core.Direction.Axis toVanilla();

   public Facing mirror(Facing facing) {
      return facing.axis == this ? facing.opposite() : facing;
   }

   public Direction mirror(Direction facing) {
      return facing.getAxis() == this.toVanilla() ? facing.getOpposite() : facing;
   }

   public abstract Vec3i mirror(Vec3i var1);

   public abstract BlockPos mirror(BlockPos var1);

   public void mirror(Vec3d vec) {
      vec.set(this, -vec.get(this));
   }

   public void mirror(Vec3f vec) {
      vec.set(this, -vec.get(this));
   }

   public Axis transform(IntMatrix3c matrix) {
      return this.facing(true).transform(matrix).axis;
   }
}
