package com.corosus.coroutil.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class CoroUtilEntity {
   public static String getName(Entity ent) {
      return ent != null ? ent.getName().getString() : "nullObject";
   }

   public static boolean canSee(Entity p_70685_1_, BlockPos pos) {
      Vec3 vector3d = new Vec3(p_70685_1_.getX(), p_70685_1_.getEyeY(), p_70685_1_.getZ());
      Vec3 vector3d1 = new Vec3(pos.getX(), pos.getY(), pos.getZ());
      return p_70685_1_.level() == p_70685_1_.level() && !(vector3d1.distanceToSqr(vector3d) > 16384.0)
         ? p_70685_1_.level().clip(new ClipContext(vector3d, vector3d1, Block.COLLIDER, Fluid.NONE, p_70685_1_)).getType() == Type.MISS
         : false;
   }
}
