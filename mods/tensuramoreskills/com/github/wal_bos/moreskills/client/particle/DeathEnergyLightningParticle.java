package com.github.wal_bos.moreskills.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class DeathEnergyLightningParticle extends TextureSheetParticle {
   private final SpriteSet sprites;
   private final float startSize;

   protected DeathEnergyLightningParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, SpriteSet sprites) {
      super(level, x, y, z, xd, yd, zd);
      this.sprites = sprites;
      this.gravity = 0.0F;
      this.friction = 0.96F;
      this.xd = xd;
      this.yd = yd;
      this.zd = zd;
      this.startSize = 7.0F + this.random.nextFloat() * 3.0F;
      this.quadSize = this.startSize;
      this.lifetime = 30 + this.random.nextInt(8);
      this.hasPhysics = false;
      this.setSpriteFromAge(sprites);
      this.rCol = 1.0F;
      this.gCol = 0.2F;
      this.bCol = 0.85F;
      this.alpha = 0.96F;
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      } else {
         this.move(this.xd, this.yd, this.zd);
         this.xd = this.xd * this.friction;
         this.yd = this.yd * this.friction;
         this.zd = this.zd * this.friction;
         this.setSpriteFromAge(this.sprites);
         float life = (float)this.age / this.lifetime;
         if (life < 0.12F) {
            this.alpha = 0.45F + life * 4.2F;
         } else if (life < 0.75F) {
            this.alpha = 0.95F;
         } else {
            this.alpha = Math.max(0.0F, 1.0F - (life - 0.75F) * 4.0F);
         }

         this.quadSize = this.startSize * (1.45F - life * 0.4F);
      }
   }

   public int getLightColor(float partialTick) {
      return 15728880;
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
         return new DeathEnergyLightningParticle(level, x, y, z, xd, yd, zd, this.sprites);
      }
   }
}
