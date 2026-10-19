package team.creative.creativecore.common.util.math.box;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Vector3d;
import team.creative.creativecore.common.util.math.base.Axis;
import team.creative.creativecore.common.util.math.base.Facing;
import team.creative.creativecore.common.util.math.matrix.Matrix3;
import team.creative.creativecore.common.util.math.transformation.Rotation;
import team.creative.creativecore.common.util.math.vec.Vec3d;
import team.creative.creativecore.common.util.math.vec.Vec3f;

public class AlignedBox {
   public float minX;
   public float minY;
   public float minZ;
   public float maxX;
   public float maxY;
   public float maxZ;

   public AlignedBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
      this.minX = minX;
      this.minY = minY;
      this.minZ = minZ;
      this.maxX = maxX;
      this.maxY = maxY;
      this.maxZ = maxZ;
   }

   public AlignedBox(AABB box) {
      this((float)box.minX, (float)box.minY, (float)box.minZ, (float)box.maxX, (float)box.maxY, (float)box.maxZ);
   }

   public AlignedBox() {
      this(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public AlignedBox(AlignedBox cube) {
      this(cube.minX, cube.minY, cube.minZ, cube.maxX, cube.maxY, cube.maxZ);
   }

   public void add(float x, float y, float z) {
      this.minX += x;
      this.minY += y;
      this.minZ += z;
      this.maxX += x;
      this.maxY += y;
      this.maxZ += z;
   }

   public void sub(float x, float y, float z) {
      this.minX -= x;
      this.minY -= y;
      this.minZ -= z;
      this.maxX -= x;
      this.maxY -= y;
      this.maxZ -= z;
   }

   public void add(Vector3d vec) {
      this.add((float)vec.x, (float)vec.y, (float)vec.z);
   }

   public void sub(Vector3d vec) {
      this.sub((float)vec.x, (float)vec.y, (float)vec.z);
   }

   public void add(Vec3i vec) {
      this.add(vec.getX(), vec.getY(), vec.getZ());
   }

   public void sub(Vec3i vec) {
      this.sub(vec.getX(), vec.getY(), vec.getZ());
   }

   public void scale(float scale) {
      this.minX *= scale;
      this.minY *= scale;
      this.minZ *= scale;
      this.maxX *= scale;
      this.maxY *= scale;
      this.maxZ *= scale;
   }

   public float getSize(Axis axis) {
      return switch (axis) {
         case X -> this.maxX - this.minX;
         case Y -> this.maxY - this.minY;
         case Z -> this.maxZ - this.minZ;
      };
   }

   public Vec3d getSize() {
      return new Vec3d(this.maxX - this.minX, this.maxY - this.minY, this.maxZ - this.minZ);
   }

   public Vec3d getCenter() {
      return new Vec3d((this.maxX + this.minX) * 0.5, (this.maxY + this.minY) * 0.5, (this.maxZ + this.minZ) * 0.5);
   }

   @Override
   public String toString() {
      return "cube[" + this.minX + ", " + this.minY + ", " + this.minZ + " -> " + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
   }

   public Vec3f getCorner(BoxCorner corner) {
      return new Vec3f(this.get(corner.x), this.get(corner.y), this.get(corner.z));
   }

   public AABB getBB() {
      return new AABB(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
   }

   public AABB getBB(BlockPos pos) {
      return new AABB(
         this.minX + pos.getX(), this.minY + pos.getY(), this.minZ + pos.getZ(), this.maxX + pos.getX(), this.maxY + pos.getY(), this.maxZ + pos.getZ()
      );
   }

   public VoxelShape voxelShape() {
      return Shapes.box(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
   }

   public VoxelShape voxelShape(BlockPos pos) {
      return Shapes.box(
         this.minX + pos.getX(), this.minY + pos.getY(), this.minZ + pos.getZ(), this.maxX + pos.getX(), this.maxY + pos.getY(), this.maxZ + pos.getZ()
      );
   }

   public void rotate(Rotation rotation, Vec3f center) {
      Vec3f low = new Vec3f(this.minX, this.minY, this.minZ);
      Vec3f high = new Vec3f(this.maxX, this.maxY, this.maxZ);
      low.sub(center);
      high.sub(center);
      rotation.getMatrix().transform(low);
      rotation.getMatrix().transform(high);
      low.add(center);
      high.add(center);
      this.set(low.x, low.y, low.z, high.x, high.y, high.z);
   }

   public void rotate(Matrix3 matrix, Vec3f center) {
      Vec3f low = new Vec3f(this.minX, this.minY, this.minZ);
      Vec3f high = new Vec3f(this.maxX, this.maxY, this.maxZ);
      low.sub(center);
      high.sub(center);
      matrix.transform(low);
      matrix.transform(high);
      low.add(center);
      high.add(center);
      this.set(low.x, low.y, low.z, high.x, high.y, high.z);
   }

   public void set(float x, float y, float z, float x2, float y2, float z2) {
      this.minX = Math.min(x, x2);
      this.minY = Math.min(y, y2);
      this.minZ = Math.min(z, z2);
      this.maxX = Math.max(x, x2);
      this.maxY = Math.max(y, y2);
      this.maxZ = Math.max(z, z2);
   }

   public BlockPos getOffset() {
      return BlockPos.containing(this.minX, this.minY, this.minZ);
   }

   public float get(Facing facing) {
      return switch (facing) {
         case EAST -> this.maxX;
         case WEST -> this.minX;
         case UP -> this.maxY;
         case DOWN -> this.minY;
         case SOUTH -> this.maxZ;
         case NORTH -> this.minZ;
      };
   }

   public void set(Facing facing, float value) {
      switch (facing) {
         case EAST:
            this.maxX = value;
            break;
         case WEST:
            this.minX = value;
            break;
         case UP:
            this.maxY = value;
            break;
         case DOWN:
            this.minY = value;
            break;
         case SOUTH:
            this.maxZ = value;
            break;
         case NORTH:
            this.minZ = value;
      }
   }

   public void setMin(Axis axis, float value) {
      switch (axis) {
         case X:
            this.minX = value;
            break;
         case Y:
            this.minY = value;
            break;
         case Z:
            this.minZ = value;
      }
   }

   public float getMin(Axis axis) {
      return switch (axis) {
         case X -> this.minX;
         case Y -> this.minY;
         case Z -> this.minZ;
      };
   }

   public void setMax(Axis axis, float value) {
      switch (axis) {
         case X:
            this.maxX = value;
            break;
         case Y:
            this.maxY = value;
            break;
         case Z:
            this.maxZ = value;
      }
   }

   public float getMax(Axis axis) {
      return switch (axis) {
         case X -> this.maxX;
         case Y -> this.maxY;
         case Z -> this.maxZ;
      };
   }

   public void grow(Axis axis, float value) {
      value /= 2.0F;
      this.setMin(axis, this.getMin(axis) - value);
      this.setMax(axis, this.getMax(axis) + value);
   }

   public void shrink(Axis axis, float value) {
      value /= 2.0F;
      this.setMin(axis, this.getMin(axis) + value);
      this.setMax(axis, this.getMax(axis) - value);
   }
}
