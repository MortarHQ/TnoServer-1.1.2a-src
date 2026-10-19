package com.github.L_Ender.cataclysm.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;

public class SoulLavaParticle extends TextureSheetParticle {
   SoulLavaParticle(ClientLevel p_107074_, double p_107075_, double p_107076_, double p_107077_) {
      super(p_107074_, p_107075_, p_107076_, p_107077_, 0.0, 0.0, 0.0);
      this.gravity = 0.75F;
      this.friction = 0.999F;
      this.xd *= 0.8F;
      this.yd *= 0.8F;
      this.zd *= 0.8F;
      this.yd = this.random.nextFloat() * 0.4F + 0.05F;
      this.quadSize = this.quadSize * (this.random.nextFloat() * 2.0F + 0.2F);
      this.lifetime = (int)(16.0 / (Math.random() * 0.8 + 0.2));
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public int getLightColor(float p_107086_) {
      int i = super.getLightColor(p_107086_);
      int j = 240;
      int k = i >> 16 & 0xFF;
      return 240 | k << 16;
   }

   public float getQuadSize(float p_107089_) {
      float f = (this.age + p_107089_) / this.lifetime;
      return this.quadSize * (1.0F - f * f);
   }

   public void tick() {
      super.tick();
      if (!this.removed) {
         float f = (float)this.age / this.lifetime;
         if (this.random.nextFloat() > f) {
            this.level.addParticle(ParticleTypes.SMOKE, this.x, this.y, this.z, this.xd, this.yd, this.zd);
         }
      }
   }

   public static class Factory implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public Factory(SpriteSet p_107092_) {
         this.sprite = p_107092_;
      }

      public Particle createParticle(
         SimpleParticleType p_107103_,
         ClientLevel p_107104_,
         double p_107105_,
         double p_107106_,
         double p_107107_,
         double p_107108_,
         double p_107109_,
         double p_107110_
      ) {
         SoulLavaParticle lavaparticle = new SoulLavaParticle(p_107104_, p_107105_, p_107106_, p_107107_);
         lavaparticle.pickSprite(this.sprite);
         return lavaparticle;
      }
   }
}
