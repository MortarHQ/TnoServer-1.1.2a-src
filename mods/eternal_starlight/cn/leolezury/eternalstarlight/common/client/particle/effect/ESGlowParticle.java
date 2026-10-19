package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.particle.ESGlowParticleOptions;
import cn.leolezury.eternalstarlight.common.util.Color;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;

public class ESGlowParticle extends TextureSheetParticle {
   protected SpriteSet sprites;
   private final float rotSpeed;
   private float fadeR;
   private float fadeG;
   private float fadeB;

   protected ESGlowParticle(
      ClientLevel level,
      float lifeMultiplier,
      double x,
      double y,
      double z,
      double dx,
      double dy,
      double dz,
      int fromColor,
      int toColor,
      float alpha,
      SpriteSet spriteSet
   ) {
      super(level, x, y, z, dx, dy, dz);
      this.xd = dx;
      this.yd = dy;
      this.zd = dz;
      this.rotSpeed = (float)(Math.random() * 0.05F + 0.075F);
      this.lifetime = (int)(((int)(Math.random() * 20.0) + 40) * lifeMultiplier);
      this.alpha = alpha;
      this.setColor(fromColor);
      this.setFadeColor(toColor);
      this.setSpriteFromAge(spriteSet);
      this.sprites = spriteSet;
   }

   public void setColor(int i) {
      float f = ((i & 0xFF0000) >> 16) / 255.0F;
      float g = ((i & 0xFF00) >> 8) / 255.0F;
      float h = (i & 0xFF) / 255.0F;
      this.setColor(f, g, h);
   }

   public void setFadeColor(int i) {
      this.fadeR = ((i & 0xFF0000) >> 16) / 255.0F;
      this.fadeG = ((i & 0xFF00) >> 8) / 255.0F;
      this.fadeB = (i & 0xFF) / 255.0F;
   }

   public void tick() {
      super.tick();
      this.oRoll = this.roll;
      this.roll = this.roll + this.rotSpeed;
      this.setSpriteFromAge(this.sprites);
      if (this.age > this.lifetime / 2) {
         this.rCol = this.rCol + (this.fadeR - this.rCol) * 0.2F;
         this.gCol = this.gCol + (this.fadeG - this.gCol) * 0.2F;
         this.bCol = this.bCol + (this.fadeB - this.bCol) * 0.2F;
      }
   }

   public int getLightColor(float f) {
      return 15728880;
   }

   public void render(VertexConsumer vertexConsumer, Camera camera, float f) {
      float a = this.alpha;
      float progress = Math.min(this.age + f, this.lifetime) / this.lifetime;
      this.alpha = Mth.lerp((float)Math.pow(Math.abs(progress - 0.5) * 2.0, 5.0), this.alpha, 0.0F);
      super.render(ESClientHandler.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.PARTICLE_ADDITIVE_GLOW), camera, f);
      this.alpha = a;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.CUSTOM;
   }

   public static class Provider implements ParticleProvider<ESGlowParticleOptions> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(
         ESGlowParticleOptions options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         return new ESGlowParticle(
            level,
            options.lifeScale(),
            x,
            y,
            z,
            xSpeed,
            ySpeed,
            zSpeed,
            Color.rgbd(options.fromColor().x, options.fromColor().y, options.fromColor().z).rgb(),
            Color.rgbd(options.toColor().x, options.toColor().y, options.toColor().z).rgb(),
            options.alpha(),
            this.sprites
         );
      }
   }
}
