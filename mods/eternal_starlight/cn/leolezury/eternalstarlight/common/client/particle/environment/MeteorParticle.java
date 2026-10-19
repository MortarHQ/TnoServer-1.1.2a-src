package cn.leolezury.eternalstarlight.common.client.particle.environment;

import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.client.visual.TrailRenderer;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class MeteorParticle extends TextureSheetParticle {
   private final TrailEffect effect = new TrailEffect(0.8F, 15.0F);

   protected MeteorParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
      super(level, x, y, z);
      this.xd = 0.0;
      this.yd = -2.0;
      this.zd = 0.0;
      this.lifetime = 400;
      this.pickSprite(spriteSet);
   }

   public void tick() {
      super.tick();
      this.level.addParticle(ESExplosionParticleOptions.AETHERSENT, true, this.x, this.y, this.z, 0.0, 0.0, 0.0);
      this.effect.update(new Vec3(this.xo, this.yo, this.zo));
      if (this.onGround) {
         this.effect.setLength(Math.max(this.effect.getLength() - 0.75F, 0.0F));
         if (this.effect.getLength() <= 0.0F) {
            this.remove();
         }
      }

      this.xd = 0.0;
      this.yd = -2.0;
      this.zd = 0.0;
   }

   public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
      PoseStack stack = new PoseStack();
      float x = (float)Mth.lerp(partialTicks, this.xo, this.x);
      float y = (float)Mth.lerp(partialTicks, this.yo, this.y);
      float z = (float)Mth.lerp(partialTicks, this.zo, this.z);
      stack.pushPose();
      stack.translate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);
      this.effect.prepareRender(new Vec3(x, y, z), partialTicks);
      TrailRenderer.render(
         this.effect,
         ESClientHandler.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.PARTICLE_ADDITIVE_GLOW),
         stack,
         TrailEffect.TrailOffsetFunction.FACE_CAMERA,
         false,
         true,
         0.5647059F,
         0.36862746F,
         0.65882355F,
         2.0F,
         this.getU0(),
         this.getU1(),
         this.getV0(),
         this.getV1(),
         15728880
      );
      stack.popPose();
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.CUSTOM;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new MeteorParticle(level, x, y, z, this.sprites);
      }
   }
}
