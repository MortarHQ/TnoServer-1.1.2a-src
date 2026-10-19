package com.github.wal_bos.moreskills.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class JudgementSmokeParticle extends TextureSheetParticle {
   private final SpriteSet sprites;
   private final boolean crown;
   private final float startSize;
   private final float endSize;
   private final float spin;
   private final double drift;
   private final double baseYSpeed;

   protected JudgementSmokeParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, SpriteSet sprites) {
      super(level, x, y, z, xd, yd, zd);
      this.sprites = sprites;
      this.crown = yd > 0.35;
      this.gravity = 0.0F;
      this.friction = 0.965F;
      this.hasPhysics = false;
      this.spin = (this.random.nextFloat() - 0.5F) * (this.crown ? 0.22F : 0.12F);
      this.drift = this.crown ? 0.028 : 0.012;
      this.baseYSpeed = this.crown ? -0.01 : -0.05;
      this.xd = xd + (this.random.nextDouble() - 0.5) * (this.crown ? 0.03 : 0.012);
      this.yd = this.baseYSpeed;
      this.zd = zd + (this.random.nextDouble() - 0.5) * (this.crown ? 0.03 : 0.012);
      this.startSize = this.crown ? 5.8F + this.random.nextFloat() * 1.8F : 2.6F + this.random.nextFloat() * 0.9F;
      this.endSize = this.crown ? this.startSize * (1.95F + this.random.nextFloat() * 0.2F) : this.startSize * (1.4F + this.random.nextFloat() * 0.1F);
      this.quadSize = this.startSize;
      this.lifetime = this.crown ? 52 + this.random.nextInt(10) : 34 + this.random.nextInt(8);
      this.roll = this.random.nextFloat() * (float) (Math.PI * 2);
      this.oRoll = this.roll;
      if (this.crown) {
         this.rCol = 1.0F;
         this.gCol = 0.98F;
         this.bCol = 0.56F;
         this.alpha = 0.98F;
      } else {
         this.rCol = 1.0F;
         this.gCol = 0.9F;
         this.bCol = 0.26F;
         this.alpha = 0.9F;
      }

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
         float invLife = 1.0F - life;
         double ang = this.age * (this.crown ? 0.32 : 0.22) + this.roll;
         double swirl = this.drift * invLife;
         this.xd = this.xd * this.friction + Math.cos(ang) * swirl;
         this.zd = this.zd * this.friction + Math.sin(ang) * swirl;
         this.yd = this.yd * 0.992 + (this.crown ? 3.5E-4 : -1.5E-4);
         this.move(this.xd, this.yd, this.zd);
         this.oRoll = this.roll;
         this.roll = this.roll + this.spin * (0.35F + invLife * 0.65F);
         this.setSpriteFromAge(this.sprites);
         this.quadSize = this.startSize + (this.endSize - this.startSize) * life;
         if (this.crown) {
            if (life < 0.12F) {
               this.alpha = 0.2F + life / 0.12F * 0.78F;
            } else {
               this.alpha = Math.max(0.0F, 0.98F - (life - 0.12F) / 0.88F * 0.82F);
            }

            this.rCol = 1.0F;
            this.gCol = 0.94F + invLife * 0.04F;
            this.bCol = 0.38F + invLife * 0.24F;
         } else {
            if (life < 0.1F) {
               this.alpha = 0.15F + life / 0.1F * 0.75F;
            } else {
               this.alpha = Math.max(0.0F, 0.9F - (life - 0.1F) / 0.9F * 0.78F);
            }

            this.rCol = 1.0F;
            this.gCol = 0.84F + invLife * 0.08F;
            this.bCol = 0.14F + invLife * 0.16F;
         }

         if (this.alpha <= 0.01F) {
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
         return new JudgementSmokeParticle(level, x, y, z, xd, yd, zd, this.sprites);
      }
   }
}
