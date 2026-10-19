package com.mongoose.royalvariations.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;

public class RoyalExplosionParticle extends TextureSheetParticle {
   private final SpriteSet spriteSet;

   protected RoyalExplosionParticle(ClientLevel level, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(level, x, y, z, 0.0, 0.0, 0.0);
      this.setSize(0.2F, 0.2F);
      this.quadSize *= 3.0F;
      this.lifetime = 20;
      this.gravity = 0.0F;
      this.xd = vx * 0.2;
      this.yd = vy * 0.2;
      this.zd = vz * 0.2;
      this.spriteSet = spriteSet;
      this.setSpriteFromAge(spriteSet);
   }

   public int getLightColor(float partialTick) {
      return 15728880;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public void tick() {
      super.tick();
      if (!this.removed) {
         this.setSprite(this.spriteSet.get(this.age / 2 % 11 + 1, 11));
      }
   }

   public static class Provider implements ParticleProvider<RoyalExplosionParticleOption> {
      private final SpriteSet sprite;

      public Provider(SpriteSet p_234008_) {
         this.sprite = p_234008_;
      }

      public Particle createParticle(
         RoyalExplosionParticleOption p_234019_,
         ClientLevel p_234020_,
         double p_234021_,
         double p_234022_,
         double p_234023_,
         double p_234024_,
         double p_234025_,
         double p_234026_
      ) {
         RoyalExplosionParticle shockwaveParticle = new RoyalExplosionParticle(
            p_234020_, p_234021_, p_234022_, p_234023_, p_234024_, p_234025_, p_234026_, this.sprite
         );
         shockwaveParticle.quadSize = p_234019_.getSize();
         shockwaveParticle.setAlpha(1.0F);
         return shockwaveParticle;
      }
   }
}
