package com.github.L_Ender.cataclysm.client.particle;

import com.github.L_Ender.cataclysm.client.particle.Options.LightningParticleOptions;
import com.github.L_Ender.cataclysm.client.render.etc.LightningBoltData;
import com.github.L_Ender.cataclysm.client.render.etc.LightningRender;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Random;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector4f;

public class LightningParticle extends Particle {
   private double toX;
   private double toY;
   private double toZ;
   private LightningRender lightningRender = new LightningRender();

   public LightningParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int r, int g, int b) {
      super(world, x, y, z);
      this.setSize(1.0F, 1.0F);
      this.gravity = 0.0F;
      this.lifetime = 5 + new Random().nextInt(3);
      this.toX = xSpeed;
      this.toY = ySpeed;
      this.toZ = zSpeed;
      this.rCol = r;
      this.gCol = g;
      this.bCol = b;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.CUSTOM;
   }

   public void render(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
      Vec3 vec3 = camera.getPosition();
      PoseStack posestack = new PoseStack();
      BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
      float f = (float)(Mth.lerp(partialTick, this.xo, this.x) - vec3.x());
      float f1 = (float)(Mth.lerp(partialTick, this.yo, this.y) - vec3.y());
      float f2 = (float)(Mth.lerp(partialTick, this.zo, this.z) - vec3.z());
      float lerpAge = this.age + partialTick;
      float ageProgress = lerpAge / this.lifetime;
      float scale = 1.85F;
      posestack.pushPose();
      posestack.translate(f, f1, f2);
      posestack.scale(scale, scale, scale);
      LightningBoltData.BoltRenderInfo lightningBoltData = new LightningBoltData.BoltRenderInfo(
         0.5F, 0.1F, 0.5F, 0.85F, new Vector4f(this.rCol / 255.0F, this.gCol / 255.0F, this.bCol / 255.0F, (1.0F - ageProgress) * 0.8F), 0.1F
      );
      LightningBoltData bolt = new LightningBoltData(lightningBoltData, Vec3.ZERO, new Vec3(this.toX, this.toY, this.toZ), 4)
         .size(0.05F)
         .lifespan(this.lifetime)
         .spawn(LightningBoltData.SpawnFunction.CONSECUTIVE);
      this.lightningRender.update(this, bolt, partialTick);
      this.lightningRender.render(partialTick, posestack, multibuffersource$buffersource);
      posestack.popPose();
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements ParticleProvider<LightningParticleOptions> {
      public Particle createParticle(
         LightningParticleOptions data, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         return new LightningParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, data.r(), data.g(), data.b());
      }
   }
}
