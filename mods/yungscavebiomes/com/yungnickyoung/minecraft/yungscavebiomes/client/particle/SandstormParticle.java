package com.yungnickyoung.minecraft.yungscavebiomes.client.particle;

import com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm.ISandstormClientDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm.SandstormClientData;
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
import org.joml.Vector3f;

public class SandstormParticle extends TextureSheetParticle {
   private final float rotSpeed;
   private final SpriteSet sprites;
   private Vector3f particleSpeedVector;

   SandstormParticle(ClientLevel clientLevel, double xo, double yo, double zo, float r, float g, float b, SpriteSet spriteSet) {
      super(clientLevel, xo, yo, zo);
      this.sprites = spriteSet;
      this.rCol = r;
      this.gCol = g;
      this.bCol = b;
      this.lifetime = Mth.randomBetweenInclusive(clientLevel.getRandom(), 32, 48);
      this.age = Mth.randomBetweenInclusive(clientLevel.getRandom(), 0, 32);
      this.setSpriteFromAge(spriteSet);
      this.rotSpeed = (float)Math.random() * 0.15F + 0.05F;
      this.roll = (float)Math.random() * (float) (Math.PI * 2);
      this.gravity = 0.0F;
      this.particleSpeedVector = new Vector3f();
      this.updateSpeed();
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
         this.updateSpeed();
      }
   }

   private void updateSpeed() {
      SandstormClientData sandstormClientData = ((ISandstormClientDataProvider)this.level).getSandstormClientData();
      this.particleSpeedVector = sandstormClientData.getSandstormParticleSpeedVector(this.xo, this.yo, this.zo, this.particleSpeedVector);
      this.setParticleSpeed(this.particleSpeedVector.x(), this.particleSpeedVector.y(), this.particleSpeedVector.z());
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
         return new SandstormParticle(clientLevel, xo, yo, zo, r, g, b, this.spriteSet);
      }
   }
}
