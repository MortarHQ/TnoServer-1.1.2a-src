package com.github.L_Ender.cataclysm.client.particle;

import com.github.L_Ender.cataclysm.client.particle.Options.CustomPoofParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Custom_Poof_Particle extends TextureSheetParticle {
   private final SpriteSet sprites;
   private float initialR;
   private float initialG;
   private float initialB;

   protected Custom_Poof_Particle(
      ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites, float r, float g, float b, float gravity
   ) {
      super(level, x, y, z);
      this.gravity = gravity;
      this.friction = 0.9F;
      this.sprites = sprites;
      this.xd = xSpeed;
      this.yd = ySpeed;
      this.zd = zSpeed;
      this.rCol = r / 255.0F;
      this.gCol = g / 255.0F;
      this.bCol = b / 255.0F;
      this.initialR = r / 255.0F;
      this.initialG = g / 255.0F;
      this.initialB = b / 255.0F;
      this.quadSize = 0.25F + level.random.nextFloat() * 0.1F;
      this.lifetime = (int)(24.0 / (this.random.nextFloat() * 0.8 + 0.2)) + 2;
      this.setSpriteFromAge(sprites);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public int getLightColor(float p_106821_) {
      return 240;
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      } else {
         this.yd = this.yd - 0.04 * this.gravity;
         this.move(this.xd, this.yd, this.zd);
         if (this.speedUpWhenYMotionIsBlocked && this.y == this.yo) {
            this.xd *= 1.1;
            this.zd *= 1.1;
         }

         this.xd = this.xd * this.friction;
         this.yd = this.yd * this.friction;
         this.zd = this.zd * this.friction;
         if (this.onGround) {
            this.xd *= 0.7F;
            this.zd *= 0.7F;
         }

         float progress = (float)this.age / this.lifetime;
         this.rCol = Mth.lerp(progress, this.initialR, 1.0F);
         this.gCol = Mth.lerp(progress, this.initialG, 1.0F);
         this.bCol = Mth.lerp(progress, this.initialB, 1.0F);
         this.alpha = Mth.lerp(progress, 1.0F, 0.5F);
      }

      this.setSpriteFromAge(this.sprites);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<CustomPoofParticleOptions> {
      private final SpriteSet sprites;

      public Provider(SpriteSet sprites) {
         this.sprites = sprites;
      }

      public Particle createParticle(
         CustomPoofParticleOptions type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         return new Custom_Poof_Particle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites, type.r(), type.g(), type.b(), type.gravity());
      }
   }
}
