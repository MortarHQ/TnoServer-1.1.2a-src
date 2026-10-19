package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.particle.RingParticleOptions;
import cn.leolezury.eternalstarlight.common.util.Color;
import cn.leolezury.eternalstarlight.common.util.Easing;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import org.joml.Quaternionf;

public class RingParticle extends SimpleAnimatedParticle {
   private final float scale;
   private final boolean gathering;

   protected RingParticle(
      ClientLevel clientLevel,
      double x,
      double y,
      double z,
      double dx,
      double dy,
      double dz,
      int fromColor,
      int toColor,
      float scale,
      float lifeScale,
      boolean gathering,
      SpriteSet spriteSet
   ) {
      super(clientLevel, x, y, z, spriteSet, 0.0F);
      this.xd = dx;
      this.yd = dy;
      this.zd = dz;
      this.quadSize = 1.5F;
      this.lifetime = Math.round(60.0F * lifeScale);
      this.setColor(fromColor);
      this.setFadeColor(toColor);
      this.scale = scale;
      this.gathering = gathering;
      this.setSpriteFromAge(spriteSet);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.CUSTOM;
   }

   public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
      if (this.gathering) {
         this.alpha = Easing.OUT_CUBIC.interpolate(Math.min((this.age + partialTicks) / this.lifetime, 1.0F), 0.0F, 1.0F);
      } else {
         this.alpha = Easing.OUT_CUBIC.interpolate(Math.min((this.age + partialTicks) / this.lifetime, 1.0F), 1.0F, 0.0F);
      }

      Quaternionf rotation = new Quaternionf(camera.rotation());
      VertexConsumer vertexConsumer = ESClientHandler.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.PARTICLE_NO_DEPTH);
      this.renderRotatedQuad(vertexConsumer, camera, rotation, partialTicks);
   }

   public float getQuadSize(float partialTicks) {
      return this.quadSize
         * (
            this.gathering
               ? Easing.IN_OUT_QUAD.interpolate((this.age + partialTicks) / this.lifetime, this.scale, 0.0F)
               : Easing.IN_OUT_QUAD.interpolate((this.age + partialTicks) / this.lifetime, 0.0F, this.scale)
         );
   }

   public static class Provider implements ParticleProvider<RingParticleOptions> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(RingParticleOptions options, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
         return new RingParticle(
            level,
            x,
            y,
            z,
            dx,
            dy,
            dz,
            Color.rgbd(options.fromColor().x, options.fromColor().y, options.fromColor().z).rgb(),
            Color.rgbd(options.toColor().x, options.toColor().y, options.toColor().z).rgb(),
            options.scale(),
            options.lifeScale(),
            options.gathering(),
            this.sprites
         );
      }
   }
}
