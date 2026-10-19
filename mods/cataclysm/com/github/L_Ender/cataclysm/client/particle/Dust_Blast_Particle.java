package com.github.L_Ender.cataclysm.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class Dust_Blast_Particle extends TextureSheetParticle {
   private final SpriteSet sprites;

   protected Dust_Blast_Particle(ClientLevel level, double xCoord, double yCoord, double zCoord, SpriteSet spriteSet, double xd, double yd, double zd) {
      super(level, xCoord, yCoord, zCoord, xd, yd, zd);
      this.sprites = spriteSet;
      this.setSpriteFromAge(this.sprites);
      this.alpha = 0.9F;
      this.quadSize *= 4.0F;
      this.gravity = 0.0F;
      this.lifetime = 4;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public float getQuadSize(float scaleFactor) {
      float f = (this.age + scaleFactor) / this.lifetime;
      return this.quadSize * (1.0F - f * f * 0.5F);
   }

   public void tick() {
      super.tick();
      this.setSpriteFromAge(this.sprites);
   }

   public static class Factory implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Factory(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
         return new Dust_Blast_Particle(level, x, y, z, this.sprites, dx, dy, dz);
      }
   }
}
