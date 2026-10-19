package com.blackgear.vanillabackport.client.level.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NoxiousGasParticle extends BaseAshSmokeParticle {
   private final float fadeOutStartingPoint;

   public NoxiousGasParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float scale, SpriteSet sprites) {
      super(level, x, y, z, 0.1F, 0.1F, 0.1F, xSpeed, ySpeed, zSpeed, scale, sprites, 0.3F, 5, -0.02F, true);
      this.rCol = 1.0F;
      this.gCol = 1.0F;
      this.bCol = 1.0F;
      this.lifetime = (int)(6.0 / (this.random.nextFloat() * 0.5 + 0.5) * scale);
      this.fadeOutStartingPoint = this.lifetime / 2.0F;
   }

   public void tick() {
      super.tick();
      if (this.age > this.fadeOutStartingPoint) {
         float framesSinceFadeOutStart = this.age - this.fadeOutStartingPoint;
         this.setAlpha((this.lifetime - framesSinceFadeOutStart) / this.lifetime);
      }
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   @OnlyIn(Dist.CLIENT)
   public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new NoxiousGasParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, 3.0F, this.sprites);
      }
   }
}
