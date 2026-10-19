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
public abstract class SubInstantParticleEmitter extends SubParticleEmitter {
   protected final int count;

   protected SubInstantParticleEmitter(ClientLevel world, SpriteSet spriteSet, Vec3 vec3, int count) {
      super(world, spriteSet, vec3);
      this.lifetime = 1;
      this.count = count;
   }

   protected SubInstantParticleEmitter(ClientLevel world, double x, double y, double z, SpriteSet spriteSet, int count) {
      this(world, spriteSet, new Vec3(x, y, z), count);
   }

   @Override
   protected void spawnParticles() {
      for (int i = 0; i < this.count; i++) {
         this.spawnSubParticle();
      }
   }
}
