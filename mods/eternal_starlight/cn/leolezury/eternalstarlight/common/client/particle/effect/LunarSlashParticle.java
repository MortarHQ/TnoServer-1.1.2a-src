package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
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
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

public class LunarSlashParticle extends TextureSheetParticle {
   private static final int DIVISION = 72;
   private static final float CELL = 2.5F;
   private final float yRot;
   private final float zRot;

   protected LunarSlashParticle(ClientLevel clientLevel, double x, double y, double z, double dx, double dy, double dz, SpriteSet spriteSet) {
      super(clientLevel, x, y, z);
      this.stoppedByCollision = true;
      this.lifetime = (int)(this.random.nextFloat() * 5.0F + 10.0F);
      this.yRot = ESMathUtil.positionToYaw(new Vec3(dx, dy, dz)) * (float) (Math.PI / 180.0);
      this.zRot = (float) (Math.PI * 2) * this.random.nextFloat();
      this.pickSprite(spriteSet);
   }

   public void move(double d, double e, double f) {
      super.move(d, e, f);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public void render(VertexConsumer consumer, Camera camera, float partialTick) {
      float progress = Math.min(this.age + partialTick, this.lifetime) / this.lifetime;
      double currentX = Mth.lerp(partialTick, this.xo, this.x);
      double currentY = Mth.lerp(partialTick, this.yo, this.y);
      double currentZ = Mth.lerp(partialTick, this.zo, this.z);
      Vec3 camPos = camera.getPosition();
      PoseStack stack = new PoseStack();
      stack.pushPose();
      stack.translate(currentX - camPos.x, currentY - camPos.y, currentZ - camPos.z);
      stack.mulPose(new Quaternionf().rotationX((float) (Math.PI / 2)));
      stack.mulPose(new Quaternionf().rotationZ(this.yRot));
      stack.mulPose(new Quaternionf().rotationX(this.zRot));
      VertexConsumer vertexConsumer = ESClientHandler.DELAYED_BUFFER_SOURCE.getBuffer(ESRenderType.PARTICLE_ADDITIVE_GLOW);
      float lastYawOffset = 0.0F;
      Vec3 lastInnerPos = Vec3.ZERO;
      Vec3 lastOuterPos = Vec3.ZERO;

      for (int i = 0; i < 36; i++) {
         float yawOffset = progress * 270.0F - i * 2.5F;
         Vec3 innerPos = ESMathUtil.rotationToPosition(1.0F, 0.0F, -90.0F + yawOffset);
         Vec3 outerPos = ESMathUtil.rotationToPosition(1.0F + Mth.sin(yawOffset / 180.0F * (float) Math.PI) * 1.25F, 0.0F, -90.0F + yawOffset);
         if (i != 0 && lastYawOffset > 0.0F && yawOffset < 180.0F) {
            Vec3 renderLastInnerPos = lastInnerPos;
            Vec3 renderLastOuterPos = lastOuterPos;
            Vec3 renderInnerPos = innerPos;
            Vec3 renderOuterPos = outerPos;
            if (yawOffset < 0.0F) {
               renderInnerPos = ESMathUtil.rotationToPosition(1.0F, 0.0F, -90.0F);
               renderOuterPos = renderInnerPos;
            }

            if (lastYawOffset > 180.0F) {
               renderLastInnerPos = ESMathUtil.rotationToPosition(1.0F, 0.0F, 90.0F);
               renderLastOuterPos = renderLastInnerPos;
            }

            Pose pose = stack.last();
            float u0 = this.getU0();
            float u1 = this.getU1();
            float v0 = this.getV0();
            float v1 = this.getV1();
            vertexConsumer.addVertex(pose, renderLastInnerPos.toVector3f())
               .setColor(0.5019608F, 0.6117647F, 0.9411765F, 1.0F - (i - 1) / 36.0F)
               .setUv(u0, v0)
               .setLight(15728880);
            vertexConsumer.addVertex(pose, renderLastOuterPos.toVector3f())
               .setColor(0.5019608F, 0.6117647F, 0.9411765F, 1.0F - (i - 1) / 36.0F)
               .setUv(u0, v1)
               .setLight(15728880);
            vertexConsumer.addVertex(pose, renderOuterPos.toVector3f())
               .setColor(0.5019608F, 0.6117647F, 0.9411765F, 1.0F - i / 36.0F)
               .setUv(u1, v1)
               .setLight(15728880);
            vertexConsumer.addVertex(pose, renderInnerPos.toVector3f())
               .setColor(0.5019608F, 0.6117647F, 0.9411765F, 1.0F - i / 36.0F)
               .setUv(u1, v0)
               .setLight(15728880);
         }

         lastYawOffset = yawOffset;
         lastInnerPos = innerPos;
         lastOuterPos = outerPos;
      }

      stack.popPose();
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(SimpleParticleType options, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
         return new LunarSlashParticle(level, x, y, z, dx, dy, dz, this.sprites);
      }
   }
}
