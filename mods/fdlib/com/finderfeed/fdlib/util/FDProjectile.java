package com.finderfeed.fdlib.util;

import com.finderfeed.fdlib.init.FDParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class FDProjectile extends AbstractHurtingProjectile {
   public FDProjectile(EntityType<? extends AbstractHurtingProjectile> type, Level level) {
      super(type, level);
      this.accelerationPower = 0.0;
   }

   public void tick() {
      super.tick();
   }

   protected boolean shouldBurn() {
      return false;
   }

   protected float getInertia() {
      return 1.0F;
   }

   @Nullable
   protected ParticleOptions getTrailParticle() {
      return (ParticleOptions)FDParticles.INVISIBLE.get();
   }
}
