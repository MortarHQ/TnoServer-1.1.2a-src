package com.mortal_cultivation.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public final class SpiritMistParticle extends TextureSheetParticle {
   private final SpriteSet sprites;

   private SpiritMistParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
      super(level, x, y, z);
      this.sprites = sprites;
      this.xd = xSpeed;
      this.yd = ySpeed;
      this.zd = zSpeed;
      this.hasPhysics = false;
      this.friction = 1.0F;
      this.quadSize = 2.6F + this.random.nextFloat() * 1.6F;
      this.lifetime = 90 + this.random.nextInt(50);
      this.rCol = 0.82F;
      this.gCol = 0.94F;
      this.bCol = 0.93F;
      this.alpha = 0.0F;
      this.setSpriteFromAge(sprites);
   }

   public void tick() {
      super.tick();
      this.alpha = (float)Math.sin(Math.PI * this.age / this.lifetime) * 0.055F;
      this.setSpriteFromAge(this.sprites);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new SpiritMistParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
      }
   }
}
