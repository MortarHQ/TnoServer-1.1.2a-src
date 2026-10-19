package com.cerbon.bosses_of_mass_destruction.entity.ai.valid_direction;

import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class CanMoveThrough implements IValidDirection {
   private final Entity entity;
   private final double reactionDistance;

   public CanMoveThrough(Entity entity, double reactionDistance) {
      this.entity = entity;
      this.reactionDistance = reactionDistance;
   }

   @Override
   public boolean isValidDirection(Vec3 normedDirection) {
      Vec3 reactionDirection = normedDirection.scale(this.reactionDistance).add(this.entity.getDeltaMovement());
      Vec3 target = this.entity.position().add(reactionDirection);
      boolean noBlockCollisions = MathUtils.willAABBFit(
         this.entity.getBoundingBox(), reactionDirection, box -> !this.entity.level().noCollision(this.entity, box)
      );
      ClipContext context = new ClipContext(this.entity.position().add(normedDirection.scale(1.0)), target, Block.COLLIDER, Fluid.ANY, this.entity);
      HitResult blockCollision = this.entity.level().clip(context);
      boolean noFluidCollisions = blockCollision.getType() == Type.MISS;
      return noFluidCollisions && noBlockCollisions;
   }
}
