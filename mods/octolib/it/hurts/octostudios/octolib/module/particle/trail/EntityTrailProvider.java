package it.hurts.octostudios.octolib.module.particle.trail;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public abstract class EntityTrailProvider<T extends Entity> extends TrailProvider {
   public T entity;

   public EntityTrailProvider(T entity) {
      this.entity = entity;
   }

   @Override
   public Vec3 getTrailPosition(float partialTick) {
      return this.entity.getPosition(partialTick);
   }

   @Override
   public int getTrailUpdateFrequency() {
      return 0;
   }

   @Override
   public boolean isTrailAlive() {
      return this.entity.isAlive();
   }

   @Override
   public boolean isTrailGrowing() {
      return this.entity.tickCount > 0 && this.entity.getDeltaMovement().length() > 0.0;
   }
}
