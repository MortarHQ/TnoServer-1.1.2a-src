package net.unusual.block_factorys_bosses.client.particle.util;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class SubSteadyParticleEmitter extends SubParticleEmitter {
   protected final int spawnRate;
   protected final int max;
   protected float spawn = 0.0F;

   protected SubSteadyParticleEmitter(ClientLevel world, SpriteSet spriteSet, Vec3 vec3, int lifeTime, int spawnRate, int max) {
      super(world, spriteSet, vec3);
      this.lifetime = lifeTime;
      this.spawnRate = spawnRate;
      this.max = max;
   }

   protected SubSteadyParticleEmitter(ClientLevel world, double x, double y, double z, SpriteSet spriteSet, int lifeTime, int spawnRate, int max) {
      this(world, spriteSet, new Vec3(x, y, z), lifeTime, spawnRate, max);
   }

   @Override
   protected void spawnParticles() {
      for (this.spawn = this.spawn + this.spawnRate / 20.0F; this.spawn > 1.0F; this.spawn--) {
         if (this.proxies.size() < this.max) {
            this.spawnSubParticle();
         }
      }
   }
}
