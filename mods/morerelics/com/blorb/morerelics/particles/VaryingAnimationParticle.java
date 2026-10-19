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

public class VaryingAnimationParticle extends TextureSheetParticle {
   private final SpriteSet spriteSet;
   private final int randomOffset;

   protected VaryingAnimationParticle(
      ClientLevel level, double x, double y, double z, SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed, int lifetime
   ) {
      super(level, x, y, z, xSpeed, ySpeed, zSpeed);
      Random rand = new Random();
      this.friction = 0.6F;
      this.randomOffset = rand.nextInt(4) - 2;
      this.spriteSet = spriteSet;
      this.lifetime = lifetime;
      this.setSpriteFromAge(spriteSet);
      this.xd = xSpeed;
      this.yd = ySpeed;
      this.zd = zSpeed;
      this.rCol = 1.0F;
      this.gCol = 1.0F;
      this.bCol = 1.0F;
   }

   public void tick() {
      super.tick();
      this.setSprite(this.spriteSet.get(Math.max(0, Math.min(this.age + this.randomOffset, this.lifetime)), this.lifetime));
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
         return new VaryingAnimationParticle(clientLevel, pX, pY, pZ, this.spriteSet, pXSpeed, pYSpeed, pZSpeed, this.dynamicLifetime);
      }
   }
}
