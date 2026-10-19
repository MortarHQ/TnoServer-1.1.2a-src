package com.github.L_Ender.cataclysm.client.particle;

import com.github.L_Ender.cataclysm.client.particle.Options.LightningZapParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Lightning_Zap_Particle extends TextureSheetParticle {
   private final SpriteSet sprites;
   private int getR;
   private int getG;
   private int getB;

   protected Lightning_Zap_Particle(
      ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites, int r, int g, int b, float gravity
   ) {
      super(level, x, y, z);
      this.gravity = gravity;
      this.friction = 0.1F;
      this.sprites = sprites;
      this.xd = xSpeed;
      this.yd = ySpeed;
      this.zd = zSpeed;
      this.rCol = r / 255.0F;
      this.gCol = g / 255.0F;
      this.bCol = b / 255.0F;
      this.getR = r;
      this.getG = g;
      this.getB = b;
      this.quadSize = 0.25F + level.random.nextFloat() * 0.15F;
      this.lifetime = 6;
      this.setSpriteFromAge(sprites);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public int getLightColor(float p_106821_) {
      return 240;
   }

   public void tick() {
      super.tick();
      if (this.age == 3) {
         this.rCol = 1.0F;
         this.gCol = 1.0F;
         this.bCol = 1.0F;
      } else {
         this.rCol = this.getR / 255.0F;
         this.gCol = this.getG / 255.0F;
         this.bCol = this.getB / 255.0F;
      }

      this.setSpriteFromAge(this.sprites);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<LightningZapParticleOptions> {
      private final SpriteSet sprites;

      public Provider(SpriteSet sprites) {
         this.sprites = sprites;
      }

      public Particle createParticle(
         LightningZapParticleOptions type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         return new Lightning_Zap_Particle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites, type.r(), type.g(), type.b(), type.gravity());
      }
   }
}
