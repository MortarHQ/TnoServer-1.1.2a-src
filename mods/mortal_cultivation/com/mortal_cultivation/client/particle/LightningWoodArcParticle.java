package com.mortal_cultivation.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public final class LightningWoodArcParticle extends TextureSheetParticle {
   private final SpriteSet sprites;

   private LightningWoodArcParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
      super(level, x, y, z);
      this.sprites = sprites;
      this.xd = xSpeed;
      this.yd = ySpeed;
      this.zd = zSpeed;
      this.hasPhysics = false;
      this.friction = 0.95F;
      this.quadSize = 0.07F + this.random.nextFloat() * 0.025F;
      this.lifetime = 8 + this.random.nextInt(5);
      this.roll = this.random.nextFloat() * (float) (Math.PI * 2);
      this.oRoll = this.roll;
      this.setSpriteFromAge(sprites);
   }

   public void tick() {
      super.tick();
      if (!this.removed) {
         this.alpha = this.lifetime - this.age <= 3 ? (this.lifetime - this.age) / 3.0F : 1.0F;
         this.setSpriteFromAge(this.sprites);
      }
   }

   public int getLightColor(float partialTick) {
      return 15728880;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new LightningWoodArcParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
      }
   }
}
