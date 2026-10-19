package com.github.wal_bos.moreskills.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class SunParticle extends TextureSheetParticle {
   private final SpriteSet sprites;
   private final float startSize;
   private final float endSize;
   private final float spinSpeed;
   private final boolean majorSun;

   protected SunParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, SpriteSet sprites) {
      super(level, x, y, z, xd, yd, zd);
      this.sprites = sprites;
      this.gravity = 0.0F;
      this.friction = 0.992F;
      this.hasPhysics = false;
      this.majorSun = Math.abs(xd) + Math.abs(yd) + Math.abs(zd) < 0.001;
      this.xd = xd * 0.01;
      this.yd = yd * 0.01;
      this.zd = zd * 0.01;
      if (this.majorSun) {
         this.startSize = 18.0F + this.random.nextFloat() * 6.0F;
         this.endSize = this.startSize * (1.04F + this.random.nextFloat() * 0.06F);
         this.lifetime = 34 + this.random.nextInt(10);
         this.alpha = 0.96F;
         this.spinSpeed = (this.random.nextFloat() - 0.5F) * 0.018F;
      } else {
         this.startSize = 7.0F + this.random.nextFloat() * 2.5F;
         this.endSize = this.startSize * (1.03F + this.random.nextFloat() * 0.04F);
         this.lifetime = 20 + this.random.nextInt(8);
         this.alpha = 0.88F;
         this.spinSpeed = (this.random.nextFloat() - 0.5F) * 0.032F;
      }

      this.quadSize = this.startSize;
      this.roll = this.random.nextFloat() * (float) (Math.PI * 2);
      this.oRoll = this.roll;
      this.rCol = 0.4F;
      this.gCol = 0.9F;
      this.bCol = 1.0F;
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
         this.move(this.xd, this.yd, this.zd);
         this.oRoll = this.roll;
         this.roll = this.roll + this.spinSpeed;
         this.setSpriteFromAge(this.sprites);
         this.quadSize = this.startSize + (this.endSize - this.startSize) * life;
         if (life < 0.08F) {
            this.alpha = (this.majorSun ? 0.18F : 0.12F) + life / 0.08F * (this.majorSun ? 0.78F : 0.7F);
         } else {
            float fade = this.majorSun ? 0.28F : 0.42F;
            this.alpha = Math.max(0.0F, (this.majorSun ? 0.96F : 0.82F) - (life - 0.08F) / 0.92F * fade);
         }

         if (this.majorSun) {
            this.rCol = 0.42F;
            this.gCol = 0.92F;
            this.bCol = 1.0F;
         } else {
            this.rCol = 0.36F;
            this.gCol = 0.86F;
            this.bCol = 1.0F;
         }

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
         return new SunParticle(level, x, y, z, xd, yd, zd, this.sprites);
      }
   }
}
