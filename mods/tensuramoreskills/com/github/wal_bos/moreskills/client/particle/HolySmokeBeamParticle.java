package com.github.wal_bos.moreskills.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class HolySmokeBeamParticle extends TextureSheetParticle {
   private final SpriteSet sprites;
   private final boolean crown;
   private final float startSize;
   private final float endSize;
   private final float rollSpeed;
   private final double descentSpeed;

   protected HolySmokeBeamParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, SpriteSet sprites) {
      super(level, x, y, z, xd, yd, zd);
      this.sprites = sprites;
      this.crown = yd > 0.35;
      this.gravity = 0.0F;
      this.friction = 0.998F;
      this.hasPhysics = false;
      this.rollSpeed = (this.random.nextFloat() - 0.5F) * 0.0025F;
      this.descentSpeed = this.crown ? -0.125 - this.random.nextDouble() * 0.018 : -0.085 - this.random.nextDouble() * 0.01;
      this.xd = (this.random.nextDouble() - 0.5) * (this.crown ? 2.0E-4 : 8.0E-5);
      this.yd = this.descentSpeed;
      this.zd = (this.random.nextDouble() - 0.5) * (this.crown ? 2.0E-4 : 8.0E-5);
      this.startSize = this.crown ? 11.2F + this.random.nextFloat() * 1.3F : 6.8F + this.random.nextFloat() * 0.9F;
      this.endSize = this.crown ? this.startSize * (1.08F + this.random.nextFloat() * 0.04F) : this.startSize * (1.03F + this.random.nextFloat() * 0.03F);
      this.quadSize = this.startSize;
      this.lifetime = this.crown ? 54 + this.random.nextInt(10) : 42 + this.random.nextInt(8);
      this.roll = this.random.nextFloat() * (float) (Math.PI * 2);
      this.oRoll = this.roll;
      this.rCol = 1.0F;
      this.gCol = 1.0F;
      this.bCol = 1.0F;
      this.alpha = this.crown ? 0.985F : 0.975F;
      this.setSpriteFromAge(this.sprites);
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      } else {
         float life = (float)this.age / this.lifetime;
         this.xd = this.xd * this.friction;
         this.zd = this.zd * this.friction;
         this.yd = this.descentSpeed;
         this.move(this.xd, this.yd, this.zd);
         this.oRoll = this.roll;
         this.roll = this.roll + this.rollSpeed;
         this.setSpriteFromAge(this.sprites);
         this.quadSize = this.startSize + (this.endSize - this.startSize) * life;
         if (this.crown) {
            if (life < 0.06F) {
               this.alpha = 0.15F + life / 0.06F * 0.84F;
            } else {
               this.alpha = Math.max(0.0F, 0.99F - (life - 0.06F) / 0.94F * 0.16F);
            }
         } else if (life < 0.04F) {
            this.alpha = 0.18F + life / 0.04F * 0.8F;
         } else {
            this.alpha = Math.max(0.0F, 0.98F - (life - 0.04F) / 0.96F * 0.12F);
         }

         this.rCol = 0.995F;
         this.gCol = 0.997F;
         this.bCol = 1.0F;
         if (this.alpha <= 0.02F) {
            this.remove();
         }
      }
   }

   public int getLightColor(float partialTick) {
      return 15728880;
   }

   public float getQuadSize(float partialTick) {
      return this.quadSize;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public boolean shouldCull() {
      return false;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet sprites) {
         this.sprites = sprites;
      }

      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
         return new HolySmokeBeamParticle(level, x, y, z, xd, yd, zd, this.sprites);
      }
   }
}
