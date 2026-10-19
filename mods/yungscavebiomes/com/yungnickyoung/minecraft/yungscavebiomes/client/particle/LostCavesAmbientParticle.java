package com.yungnickyoung.minecraft.yungscavebiomes.client.particle;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class LostCavesAmbientParticle extends TextureSheetParticle {
   private final float rotSpeed;
   private final SpriteSet sprites;

   LostCavesAmbientParticle(ClientLevel clientLevel, double xo, double yo, double zo, float r, float g, float b, SpriteSet spriteSet) {
      super(clientLevel, xo, yo, zo);
      this.sprites = spriteSet;
      this.rCol = r;
      this.gCol = g;
      this.bCol = b;
      this.quadSize *= 0.67499995F;
      int k = Mth.randomBetweenInclusive(clientLevel.getRandom(), 32, 64);
      this.lifetime = (int)Math.max(k, 1.0F);
      this.age = Mth.randomBetweenInclusive(clientLevel.getRandom(), 0, 16);
      this.setSpriteFromAge(spriteSet);
      this.rotSpeed = ((float)Math.random() - 0.5F) * 0.1F;
      this.roll = (float)Math.random() * (float) (Math.PI * 2);
      this.setParticleSpeed(
         Mth.lerp(clientLevel.random.nextDouble(), -0.05, 0.05),
         Mth.lerp(clientLevel.random.nextDouble(), -0.05, 0.05),
         Mth.lerp(clientLevel.random.nextDouble(), -0.05, 0.05)
      );
   }

   @NotNull
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public float getQuadSize(float f) {
      return this.quadSize * Mth.clamp((this.age + f) / this.lifetime * 32.0F, 0.0F, 1.0F);
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      } else {
         this.setSpriteFromAge(this.sprites);
         this.oRoll = this.roll;
         this.roll = this.roll + (float) Math.PI * this.rotSpeed * 2.0F;
         if (this.onGround) {
            this.roll = 0.0F;
            this.oRoll = 0.0F;
         }

         this.move(this.xd, this.yd, this.zd);
      }
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public Provider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      @ParametersAreNonnullByDefault
      public Particle createParticle(SimpleParticleType type, ClientLevel clientLevel, double xo, double yo, double zo, double dx, double dy, double dz) {
         int color = 13743234;
         float r = (color >> 16 & 0xFF) / 255.0F;
         float g = (color >> 8 & 0xFF) / 255.0F;
         float b = (color & 0xFF) / 255.0F;
         return new LostCavesAmbientParticle(clientLevel, xo, yo, zo, r, g, b, this.spriteSet);
      }
   }
}
