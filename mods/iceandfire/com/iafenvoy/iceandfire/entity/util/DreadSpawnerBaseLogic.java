package com.iafenvoy.iceandfire.entity.util;

import com.iafenvoy.iceandfire.registry.IafParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;

public abstract class DreadSpawnerBaseLogic extends BaseSpawner {
   private short spawnDelay = 20;
   private double spin;
   private double oSpin;

   public void clientTick(Level world, BlockPos pos) {
      if (!this.isPlayerInRange(world, pos)) {
         this.oSpin = this.spin;
      } else {
         double d0 = pos.getX() + world.random.nextDouble();
         double d1 = pos.getY() + world.random.nextDouble();
         double d2 = pos.getZ() + world.random.nextDouble();
         world.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0, 0.0, 0.0);
         world.addParticle((ParticleOptions)IafParticles.DREAD_TORCH.get(), d0, d1, d2, 0.0, 0.0, 0.0);
         if (this.spawnDelay > 0) {
            this.spawnDelay--;
         }

         this.oSpin = this.spin;
         this.spin = (this.spin + 1000.0F / (this.spawnDelay + 200.0F)) % 360.0;
      }
   }

   private boolean isPlayerInRange(Level world, BlockPos pos) {
      return world.hasNearbyAlivePlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 20.0);
   }

   public double getSpin() {
      return this.spin;
   }

   public double getoSpin() {
      return this.oSpin;
   }
}
