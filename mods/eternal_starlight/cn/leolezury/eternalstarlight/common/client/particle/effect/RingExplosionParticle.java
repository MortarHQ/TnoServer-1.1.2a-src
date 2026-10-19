package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.particle.RingExplosionParticleOptions;
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

public class RingExplosionParticle extends SimpleAnimatedParticle {
   private final float scale;

   protected RingExplosionParticle(
      ClientLevel clientLevel, double x, double y, double z, double dx, double dy, double dz, int fromColor, int toColor, float scale, SpriteSet spriteSet
   ) {
      super(clientLevel, x, y, z, spriteSet, 0.0F);
      this.xd = dx;
      this.yd = dy;
      this.zd = dz;
      this.quadSize = 1.5F;
      this.lifetime = 60;
      this.setColor(fromColor);
      this.setFadeColor(toColor);
      this.scale = scale;
      this.setSpriteFromAge(spriteSet);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.CUSTOM;
   }

   public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
      this.alpha = Easing.OUT_CUBIC.interpolate(Math.min((this.age + partialTicks) / this.lifetime, 1.0F), 1.0F, 0.0F);
      Quaternionf quaternionf = new Quaternionf();
      quaternionf.rotateX((float) (Math.PI / 2));
      VertexConsumer vertexConsumer = ESClientHandler.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.PARTICLE_NO_DEPTH);
      this.renderRotatedQuad(vertexConsumer, camera, quaternionf, partialTicks);
      quaternionf = new Quaternionf();
      quaternionf.rotateY((float) -Math.PI).rotateX((float) (-Math.PI / 2));
      this.renderRotatedQuad(vertexConsumer, camera, quaternionf, partialTicks);
   }

   public float getQuadSize(float partialTicks) {
      return this.quadSize * Easing.OUT_QUINT.interpolate((this.age + partialTicks) / this.lifetime, this.scale / 10.0F, this.scale);
   }

   public static class Provider implements ParticleProvider<RingExplosionParticleOptions> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(RingExplosionParticleOptions options, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
         return new RingExplosionParticle(
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
            this.sprites
         );
      }
   }
}
