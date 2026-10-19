package com.finderfeed.raids_enhanced.content.particles.explosion_particle;

import com.finderfeed.raids_enhanced.content.particles.AnimatedSpriteParticle;
import com.finderfeed.raids_enhanced.content.particles.SimpleTexturedParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import org.jetbrains.annotations.Nullable;

public class RExplosionParticle extends AnimatedSpriteParticle {
   public RExplosionParticle(
      SpriteSet spriteSet, SimpleTexturedParticleOptions options, ClientLevel level, double x, double y, double z, double xd, double yd, double zd
   ) {
      super(spriteSet, options, level, x, y, z, xd, yd, zd);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_LIT;
   }

   protected int getLightColor(float p_107249_) {
      return 15728880;
   }

   public static class Factory implements ParticleProvider<SimpleTexturedParticleOptions> {
      private SpriteSet spriteSet;

      public Factory(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      @Nullable
      public Particle createParticle(
         SimpleTexturedParticleOptions options,
         ClientLevel p_107422_,
         double p_107423_,
         double p_107424_,
         double p_107425_,
         double p_107426_,
         double p_107427_,
         double p_107428_
      ) {
         return new RExplosionParticle(this.spriteSet, options, p_107422_, p_107423_, p_107424_, p_107425_, p_107426_, p_107427_, p_107428_);
      }
   }
}
