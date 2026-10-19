package com.chronodawn.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class ChronoDawnPortalParticle extends TextureSheetParticle {
   private final double xStart;
   private final double yStart;
   private final double zStart;

   protected ChronoDawnPortalParticle(ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
      super(level, x, y, z, velocityX, velocityY, velocityZ);
      this.xd = velocityX;
      this.yd = velocityY;
      this.zd = velocityZ;
      this.xStart = x;
      this.yStart = y;
      this.zStart = z;
      this.x = this.x + (Math.random() * 2.0 - 1.0) * 0.05;
      this.y = this.y + (Math.random() * 2.0 - 1.0) * 0.05;
      this.z = this.z + (Math.random() * 2.0 - 1.0) * 0.05;
      this.xd *= 0.2;
      this.yd *= 0.2;
      this.zd *= 0.2;
      float colorVariation = (float)(Math.random() * 0.2 + 0.8);
      this.rCol = 1.0F * colorVariation;
      this.gCol = 0.7F * colorVariation;
      this.bCol = 0.2F * colorVariation;
      this.quadSize = (float)(Math.random() * 0.1 + 0.05);
      this.lifetime = (int)(Math.random() * 40.0) + 40;
      this.hasPhysics = false;
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      float ageRatio = (float)this.age / this.lifetime;
      float driftX = (float)(this.xStart - this.x);
      float driftY = (float)(this.yStart - this.y);
      float driftZ = (float)(this.zStart - this.z);
      this.xd += driftX * 0.001;
      this.yd += driftY * 0.001;
      this.zd += driftZ * 0.001;
      this.yd += 0.002;
      this.move(this.xd, this.yd, this.zd);
      this.xd *= 0.98;
      this.yd *= 0.98;
      this.zd *= 0.98;
      if (this.age++ >= this.lifetime) {
         this.remove();
      } else {
         this.alpha = 1.0F - ageRatio;
      }
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet sprites) {
         this.sprites = sprites;
      }

      public Particle createParticle(
         SimpleParticleType type, ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ
      ) {
         ChronoDawnPortalParticle particle = new ChronoDawnPortalParticle(level, x, y, z, velocityX, velocityY, velocityZ);
         particle.pickSprite(this.sprites);
         return particle;
      }
   }
}
