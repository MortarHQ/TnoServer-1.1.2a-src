package net.tslat.smartbrainlib.object;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public record SquareRadius(double xzRadius, double yRadius) {
   public Vec3i toVec3i() {
      return new Vec3i((int)this.xzRadius, (int)this.yRadius, (int)this.xzRadius);
   }

   public BlockPos toBlockPos() {
      return BlockPos.containing(this.xzRadius, this.yRadius, this.xzRadius);
   }

   public Vec3 toVec3() {
      return new Vec3(this.xzRadius, this.yRadius, this.xzRadius);
   }

   public AABB inflateAABB(AABB bounds) {
      return bounds.inflate(this.xzRadius, this.yRadius, this.xzRadius);
   }
}
