package com.blorb.morerelics.particles;

import java.util.Random;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class VaryingSizedAnimationParticle extends TextureSheetParticle {
   private final SpriteSet spriteSet;
   private final int randomOffset;

   protected VaryingSizedAnimationParticle(
      ClientLevel level, double x, double y, double z, SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed, int lifetime
   ) {
      super(level, x, y, z, xSpeed, ySpeed, zSpeed);
      Random rand = new Random();
      this.friction = 0.8F;
      this.randomOffset = rand.nextInt(10) - 5;
      this.spriteSet = spriteSet;
      this.lifetime = lifetime + this.randomOffset;
      this.setSpriteFromAge(spriteSet);
      this.scale(5.0F + (rand.nextFloat(6.0F) - 2.0F));
      float tint = rand.nextFloat(2.0F) / 10.0F;
      this.setColor(1.0F - tint, 1.0F - tint, 1.0F - tint);
   }

   public void tick() {
      super.tick();
      this.setSpriteFromAge(this.spriteSet);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;
      private final int dynamicLifetime;

      public Provider(SpriteSet spriteSet, int lifetime) {
         this.spriteSet = spriteSet;
         this.dynamicLifetime = lifetime;
      }

      @Nullable
      public Particle createParticle(
         SimpleParticleType simpleParticleType, ClientLevel clientLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed
      ) {
         return new VaryingSizedAnimationParticle(clientLevel, pX, pY, pZ, this.spriteSet, pXSpeed, pYSpeed, pZSpeed, this.dynamicLifetime);
      }
   }
}
