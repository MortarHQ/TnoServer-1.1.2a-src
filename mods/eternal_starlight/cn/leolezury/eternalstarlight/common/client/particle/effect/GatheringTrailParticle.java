package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.client.visual.TrailRenderer;
import cn.leolezury.eternalstarlight.common.particle.GatheringTrailParticleOptions;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
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
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class GatheringTrailParticle extends TextureSheetParticle {
   private final TrailEffect effect;
   private final Vec3 destPos;
   private final float pitchSpeed;
   private final float yawSpeed;
   private final float radiusSpeed;
   private float pitch;
   private float yaw;
   private float radius;

   protected GatheringTrailParticle(
      ClientLevel level,
      double x,
      double y,
      double z,
      double dx,
      double dy,
      double dz,
      float trailWidth,
      float trailLength,
      float speedScale,
      float rotSpeedScale,
      SpriteSet spriteSet
   ) {
      super(level, x, y, z);
      this.lifetime = 200;
      this.effect = new TrailEffect(trailWidth, trailLength);
      this.destPos = new Vec3(x + dx, y + dy, z + dz);
      this.pitch = ESMathUtil.positionToPitch(-dx, -dy, -dz);
      this.yaw = ESMathUtil.positionToYaw(-dx, -dz);
      this.radius = (float)Math.sqrt(dx * dx + dy * dy + dz * dz);
      this.pitchSpeed = (this.random.nextBoolean() ? 1 : -1) * (2.0F + this.random.nextFloat() * 3.0F) * rotSpeedScale;
      this.yawSpeed = (this.random.nextBoolean() ? 1 : -1) * (2.0F + this.random.nextFloat() * 3.0F) * rotSpeedScale;
      this.radiusSpeed = this.radius / 20.0F * (0.8F + this.random.nextFloat() * 0.4F) * speedScale;
      this.pickSprite(spriteSet);
   }

   public void tick() {
      this.pitch = this.pitch + this.pitchSpeed;
      this.yaw = this.yaw + this.yawSpeed;
      this.radius = this.radius - this.radiusSpeed;
      if (this.radius < 0.0F) {
         this.radius = 0.0F;
      }

      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      Vec3 pos = ESMathUtil.rotationToPosition(this.destPos, this.radius, this.pitch, this.yaw);
      this.x = pos.x();
      this.y = pos.y();
      this.z = pos.z();
      if (this.age > 0) {
         this.effect.update(new Vec3(this.xo, this.yo, this.zo));
      }

      if (this.age++ >= this.lifetime) {
         this.remove();
      }

      if (this.radius <= 0.0F) {
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

   public static class Provider implements ParticleProvider<GatheringTrailParticleOptions> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(
         GatheringTrailParticleOptions options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         return new GatheringTrailParticle(
            level, x, y, z, xSpeed, ySpeed, zSpeed, options.trailWidth(), options.trailLength(), options.speedScale(), options.rotSpeedScale(), this.sprites
         );
      }
   }
}
