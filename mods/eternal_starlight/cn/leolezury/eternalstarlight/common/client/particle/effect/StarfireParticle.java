package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.util.Easing;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class StarfireParticle extends TextureSheetParticle {
   private final SpriteSet sprites;

   protected StarfireParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
      super(level, x, y, z, 0.5 - Math.random(), ySpeed, 0.5 - Math.random());
      this.friction = 0.96F;
      this.gravity = -0.1F;
      this.speedUpWhenYMotionIsBlocked = true;
      this.sprites = sprites;
      this.yd *= 0.2;
      if (xSpeed == 0.0 && zSpeed == 0.0) {
         this.xd *= 0.1;
         this.zd *= 0.1;
      }

      this.quadSize *= 0.75F;
      this.lifetime = (int)(8.0 / (Math.random() * 0.8 + 0.2));
      this.hasPhysics = false;
      this.setSpriteFromAge(sprites);
   }

   public void tick() {
      super.tick();
      this.setSpriteFromAge(this.sprites);
   }

   public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
      this.alpha = Easing.IN_QUINT.interpolate(Math.min((this.age + partialTicks) / this.lifetime, 1.0F), 1.0F, 0.0F);
      super.render(consumer, camera, partialTicks);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public int getLightColor(float f) {
      return 15728880;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public Provider(SpriteSet sprite) {
         this.sprite = sprite;
      }

      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new StarfireParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprite);
      }
   }
}
