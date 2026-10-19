package com.chronodawn.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class ChronoShieldEchoParticle extends TextureSheetParticle {
   protected ChronoShieldEchoParticle(ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
      super(level, x, y, z, velocityX, velocityY, velocityZ);
      this.rCol = 0.55F;
      this.gCol = 0.35F;
      this.bCol = 0.95F;
      this.lifetime = 25;
      this.xd = velocityX;
      this.yd = velocityY;
      this.zd = velocityZ;
      this.scale(1.2F);
      this.hasPhysics = false;
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      } else {
         this.yd -= 0.004;
         this.move(this.xd, this.yd, this.zd);
         this.xd *= 0.92;
         this.yd *= 0.92;
         this.zd *= 0.92;
         this.alpha = 1.0F - (float)this.age / this.lifetime;
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
         ChronoShieldEchoParticle particle = new ChronoShieldEchoParticle(level, x, y, z, velocityX, velocityY, velocityZ);
         particle.pickSprite(this.sprites);
         return particle;
      }
   }
}
