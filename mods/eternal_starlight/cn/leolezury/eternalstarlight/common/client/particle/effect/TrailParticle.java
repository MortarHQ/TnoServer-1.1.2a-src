package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.client.visual.TrailRenderer;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import com.mojang.blaze3d.systems.RenderSystem;
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

public class TrailParticle extends TextureSheetParticle {
   private final TrailEffect effect = new TrailEffect(0.05F, 0.8F);

   protected TrailParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
      super(level, x, y, z);
      this.friction = 0.99F;
      this.gravity = 0.2F;
      this.lifetime = 200;
      this.xd = xSpeed;
      this.yd = ySpeed;
      this.zd = zSpeed;
      this.pickSprite(spriteSet);
   }

   public void tick() {
      super.tick();
      this.effect.update(new Vec3(this.xo, this.yo, this.zo));
      if (this.onGround) {
         this.effect.setLength(Math.max(this.effect.getLength() - 0.75F, 0.0F));
         if (this.effect.getLength() <= 0.0F) {
            this.remove();
         }
      }
   }

   public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
      PoseStack stack = new PoseStack();
      float x = (float)Mth.lerp(partialTicks, this.xo, this.x);
      float y = (float)Mth.lerp(partialTicks, this.yo, this.y);
      float z = (float)Mth.lerp(partialTicks, this.zo, this.z);
      stack.pushPose();
      stack.translate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);
      this.effect.prepareRender(new Vec3(x, y, z), partialTicks);
      RenderSystem.disableCull();
      TrailRenderer.render(
         this.effect,
         consumer,
         stack,
         TrailEffect.TrailOffsetFunction.FACE_CAMERA,
         true,
         true,
         1.0F,
         1.0F,
         1.0F,
         1.0F,
         this.getU0(),
         this.getU1(),
         this.getV0(),
         this.getV1(),
         15728880
      );
      stack.popPose();
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new TrailParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
      }
   }
}
