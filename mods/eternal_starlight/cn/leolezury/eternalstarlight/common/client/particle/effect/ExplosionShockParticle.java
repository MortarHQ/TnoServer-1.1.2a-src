package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.util.Easing;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class ExplosionShockParticle extends TextureSheetParticle {
   private final Vec3 direction;
   private final float length;
   private final float width;
   private final Vector3f fromColor;
   private final Vector3f toColor;

   protected ExplosionShockParticle(
      ClientLevel clientLevel,
      double x,
      double y,
      double z,
      double dx,
      double dy,
      double dz,
      Vector3f fromColor,
      Vector3f toColor,
      float lengthScale,
      float width,
      float lifeScale,
      SpriteSet spriteSet
   ) {
      super(clientLevel, x, y, z, dx, dy, dz);
      this.friction = 0.0F;
      this.lifetime = (int)((this.random.nextFloat() * 7.0F + 7.0F) * lifeScale);
      this.direction = new Vec3(dx, dy, dz).normalize();
      this.length = (this.random.nextFloat() * 1.25F + 1.15F) * lengthScale;
      this.width = width;
      this.fromColor = fromColor;
      this.toColor = toColor;
      this.pickSprite(spriteSet);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public void render(VertexConsumer consumer, Camera camera, float partialTick) {
      Vec3 camPos = camera.getPosition();
      PoseStack stack = new PoseStack();
      stack.pushPose();
      stack.translate(-camPos.x, -camPos.y, -camPos.z);
      double currentX = Mth.lerp(partialTick, this.xo, this.x);
      double currentY = Mth.lerp(partialTick, this.yo, this.y);
      double currentZ = Mth.lerp(partialTick, this.zo, this.z);
      Vec3 sight = camPos.subtract(currentX, currentY, currentZ).scale(-1.0);
      Vec3 start = new Vec3(currentX, currentY, currentZ)
         .add(this.direction.scale(Easing.IN_OUT_QUAD.interpolate(Math.min(this.age + partialTick, this.lifetime) / this.lifetime, 0.0F, this.length * 2.0F)));
      Vec3 end = start.add(
         this.direction
            .scale(
               Easing.IN_OUT_QUAD.interpolate(Mth.abs(Math.min(this.age + partialTick, this.lifetime) / this.lifetime - 0.5F) * 2.0F, this.length / 2.0F, 0.0F)
            )
      );
      Vec3 offset = end.subtract(start);
      Vec3 sideOffset = offset.cross(sight).normalize().scale(this.width / 2.0F);
      Pose pose = stack.last();
      float u0 = this.getU0();
      float u1 = Easing.IN_OUT_QUAD
         .interpolate(Mth.abs(Math.min(this.age + partialTick, this.lifetime) / this.lifetime - 0.5F) * 2.0F, this.getU1(), this.getU0());
      float v0 = this.getV0();
      float v1 = this.getV1();
      consumer.addVertex(pose, start.add(sideOffset).toVector3f())
         .setColor(
            Easing.IN_OUT_QUAD.interpolate(Math.min(this.age + partialTick, this.lifetime) / this.lifetime, this.fromColor.x(), this.toColor.x()),
            Easing.IN_OUT_QUAD.interpolate(Math.min(this.age + partialTick, this.lifetime) / this.lifetime, this.fromColor.y(), this.toColor.y()),
            Easing.IN_OUT_QUAD.interpolate(Math.min(this.age + partialTick, this.lifetime) / this.lifetime, this.fromColor.z(), this.toColor.z()),
            1.0F
         )
         .setUv(u0, v0)
         .setLight(15728880);
      consumer.addVertex(pose, start.add(sideOffset.scale(-1.0)).toVector3f())
         .setColor(
            Easing.IN_OUT_QUAD.interpolate(Math.min(this.age + partialTick, this.lifetime) / this.lifetime, this.fromColor.x(), this.toColor.x()),
            Easing.IN_OUT_QUAD.interpolate(Math.min(this.age + partialTick, this.lifetime) / this.lifetime, this.fromColor.y(), this.toColor.y()),
            Easing.IN_OUT_QUAD.interpolate(Math.min(this.age + partialTick, this.lifetime) / this.lifetime, this.fromColor.z(), this.toColor.z()),
            1.0F
         )
         .setUv(u0, v1)
         .setLight(15728880);
      consumer.addVertex(pose, end.add(sideOffset.scale(-1.0)).toVector3f())
         .setColor(
            Easing.IN_OUT_QUAD.interpolate(Math.min(this.age + partialTick, this.lifetime) / this.lifetime, this.fromColor.x(), this.toColor.x()),
            Easing.IN_OUT_QUAD.interpolate(Math.min(this.age + partialTick, this.lifetime) / this.lifetime, this.fromColor.y(), this.toColor.y()),
            Easing.IN_OUT_QUAD.interpolate(Math.min(this.age + partialTick, this.lifetime) / this.lifetime, this.fromColor.z(), this.toColor.z()),
            1.0F
         )
         .setUv(u1, v1)
         .setLight(15728880);
      consumer.addVertex(pose, end.add(sideOffset).toVector3f())
         .setColor(
            Easing.IN_OUT_QUAD.interpolate(Math.min(this.age + partialTick, this.lifetime) / this.lifetime, this.fromColor.x(), this.toColor.x()),
            Easing.IN_OUT_QUAD.interpolate(Math.min(this.age + partialTick, this.lifetime) / this.lifetime, this.fromColor.y(), this.toColor.y()),
            Easing.IN_OUT_QUAD.interpolate(Math.min(this.age + partialTick, this.lifetime) / this.lifetime, this.fromColor.z(), this.toColor.z()),
            1.0F
         )
         .setUv(u1, v0)
         .setLight(15728880);
      stack.popPose();
   }

   public static class Provider implements ParticleProvider<ExplosionShockParticleOptions> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(ExplosionShockParticleOptions options, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
         return new ExplosionShockParticle(
            level, x, y, z, dx, dy, dz, options.fromColor(), options.toColor(), options.lengthScale(), options.width(), options.lifeScale(), this.sprites
         );
      }
   }
}
