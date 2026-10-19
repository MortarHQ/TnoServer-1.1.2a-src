package com.mongoose.royalvariations.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ExplodeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class EndPoofParticle extends ExplodeParticle {
   public EndPoofParticle(
      ClientLevel p_106576_, double p_106577_, double p_106578_, double p_106579_, double p_106580_, double p_106581_, double p_106582_, SpriteSet p_106583_
   ) {
      super(p_106576_, p_106577_, p_106578_, p_106579_, p_106580_, p_106581_, p_106582_, p_106583_);
   }

   public int getLightColor(float partialTick) {
      return 15728880;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet p_106588_) {
         this.sprites = p_106588_;
      }

      public Particle createParticle(
         SimpleParticleType p_106599_,
         ClientLevel p_106600_,
         double p_106601_,
         double p_106602_,
         double p_106603_,
         double p_106604_,
         double p_106605_,
         double p_106606_
      ) {
         return new EndPoofParticle(p_106600_, p_106601_, p_106602_, p_106603_, p_106604_, p_106605_, p_106606_, this.sprites);
      }
   }
}
