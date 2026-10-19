package com.blorb.morerelics.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class OrbitingParticle extends TextureSheetParticle {
   private final SpriteSet spriteSet;
   private final double centerX;
   private final double centerY;
   private final double centerZ;
   private final double radius;
   private double angle;
   private final double angularSpeed;

   public OrbitingParticle(
      ClientLevel level, double cx, double cy, double cz, double radius, double initialAngle, double angularSpeed, SpriteSet spriteSet, int lifetime
   ) {
      super(level, cx, cy, cz);
      this.centerX = cx;
      this.centerY = cy;
      this.centerZ = cz;
      this.radius = radius;
      this.angle = initialAngle;
      this.angularSpeed = angularSpeed;
      this.spriteSet = spriteSet;
      this.setSpriteFromAge(spriteSet);
      this.lifetime = lifetime;
      this.x = this.centerX + radius * Math.cos(this.angle);
      this.z = this.centerZ + radius * Math.sin(this.angle);
      this.lifetime = 40 + level.random.nextInt(20);
      this.quadSize = 0.3F + level.random.nextFloat() * 0.2F;
   }

   public void tick() {
      super.tick();
      this.setSpriteFromAge(this.spriteSet);
      this.angle = this.angle + this.angularSpeed;
      this.x = this.centerX + this.radius * Math.cos(this.angle);
      this.z = this.centerZ + this.radius * Math.sin(this.angle);
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
         return new OrbitingParticle(clientLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.spriteSet, this.dynamicLifetime);
      }
   }
}
