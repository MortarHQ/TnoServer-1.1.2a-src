package com.cerbon.bosses_of_mass_destruction.entity.ai;

import com.cerbon.bosses_of_mass_destruction.entity.util.IEntity;
import net.minecraft.world.phys.Vec3;

public class VelocitySteering implements ISteering {
   private final IEntity entity;
   private final double maxVelocity;
   private final double inverseMass;

   public VelocitySteering(IEntity entity, double maxVelocity, double mass) {
      if (mass == 0.0) {
         throw new IllegalArgumentException("Mass cannot be zero");
      }

      this.entity = entity;
      this.maxVelocity = maxVelocity;
      this.inverseMass = 1.0 / mass;
   }

   @Override
   public Vec3 accelerateTo(Vec3 target) {
      return target.subtract(this.entity.getPos()).normalize().scale(this.maxVelocity).subtract(this.entity.getDeltaMovement()).scale(this.inverseMass);
   }
}
