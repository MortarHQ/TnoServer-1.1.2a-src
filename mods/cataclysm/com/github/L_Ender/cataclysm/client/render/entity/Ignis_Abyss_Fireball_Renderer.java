package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Ignis_Fireball_Model;
import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.entity.projectile.Ignis_Abyss_Fireball_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class Ignis_Abyss_Fireball_Renderer extends EntityRenderer<Ignis_Abyss_Fireball_Entity> {
   private static final ResourceLocation IGNIS_FIRE_BALL = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/ignis_fireball_abyss.png");
   private static final ResourceLocation TRAIL_TEXTURE = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/particle/storm.png");
   public Ignis_Fireball_Model model;
   private final RandomSource random = RandomSource.create();

   public Ignis_Abyss_Fireball_Renderer(Context manager) {
      super(manager);
      this.model = new Ignis_Fireball_Model();
   }

   protected int getBlockLightLevel(Ignis_Abyss_Fireball_Entity entity, BlockPos pos) {
      return 15;
   }

   public void render(
      Ignis_Abyss_Fireball_Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn
   ) {
      matrixStackIn.pushPose();
      float f = this.rotLerp(entityIn.yRotO, entityIn.getYRot(), partialTicks);
      float f1 = Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot());
      float f2 = entityIn.tickCount + partialTicks;
      matrixStackIn.translate(0.0, 0.3F, 0.0);
      matrixStackIn.mulPose(Axis.YP.rotationDegrees(Mth.sin(f2 * 0.1F) * 180.0F));
      matrixStackIn.mulPose(Axis.XP.rotationDegrees(Mth.cos(f2 * 0.1F) * 180.0F));
      matrixStackIn.mulPose(Axis.ZP.rotationDegrees(Mth.sin(f2 * 0.15F) * 360.0F));
      this.model.setupAnim(entityIn, 0.0F, 0.0F, 0.0F, f, f1);
      VertexConsumer VertexConsumer = bufferIn.getBuffer(this.model.renderType(this.getTextureLocation(entityIn)));
      this.model.renderToBuffer(matrixStackIn, VertexConsumer, packedLightIn, OverlayTexture.NO_OVERLAY);
      matrixStackIn.popPose();
      if (entityIn.hasTrail()) {
         double x = Mth.lerp(partialTicks, entityIn.xOld, entityIn.getX());
         double y = Mth.lerp(partialTicks, entityIn.yOld, entityIn.getY());
         double z = Mth.lerp(partialTicks, entityIn.zOld, entityIn.getZ());
         float ran = 0.04F;
         float r = 0.8901961F + this.random.nextFloat() * ran;
         float g = 0.25882354F + this.random.nextFloat() * ran;
         float b = 0.9607843F + this.random.nextFloat() * ran;
         matrixStackIn.pushPose();
         matrixStackIn.translate(-x, -y, -z);
         this.renderTrail(entityIn, partialTicks, matrixStackIn, bufferIn, r, g, b, 1.0F, packedLightIn);
         matrixStackIn.popPose();
      }

      super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   public ResourceLocation getTextureLocation(Ignis_Abyss_Fireball_Entity entity) {
      return IGNIS_FIRE_BALL;
   }

   private void renderTrail(
      Ignis_Abyss_Fireball_Entity entityIn,
      float partialTicks,
      PoseStack poseStack,
      MultiBufferSource bufferIn,
      float trailR,
      float trailG,
      float trailB,
      float trailA,
      int packedLightIn
   ) {
      int sampleSize = 10;
      float trailWidth = 0.2F;
      Pose lastPose = poseStack.last();
      Matrix4f matrix = lastPose.pose();
      VertexConsumer vertexconsumer = bufferIn.getBuffer(CMRenderTypes.getLightTrailEffect(TRAIL_TEXTURE));
      Vec3 drawFrom = entityIn.getTrailPosition(0, partialTicks);
      Vec3 cameraPos = this.entityRenderDispatcher.camera.getPosition();

      for (int i = 0; i < sampleSize; i++) {
         Vec3 sample = entityIn.getTrailPosition(i + 1, partialTicks);
         float u1 = (float)i / sampleSize;
         float u2 = u1 + 1.0F / sampleSize;
         Vec3 forward = sample.subtract(drawFrom);
         if (forward.lengthSqr() != 0.0) {
            Vec3 toCamera = cameraPos.subtract(drawFrom);
            Vec3 side = forward.cross(toCamera).normalize();
            Vec3 offset = side.scale(trailWidth / 2.0F);
            this.addVertex(vertexconsumer, matrix, drawFrom.add(offset), trailR, trailG, trailB, u1, 0.0F, packedLightIn);
            this.addVertex(vertexconsumer, matrix, drawFrom.add(offset.scale(-1.0)), trailR, trailG, trailB, u1, 1.0F, packedLightIn);
            this.addVertex(vertexconsumer, matrix, sample.add(offset.scale(-1.0)), trailR, trailG, trailB, u2, 1.0F, packedLightIn);
            this.addVertex(vertexconsumer, matrix, sample.add(offset), trailR, trailG, trailB, u2, 0.0F, packedLightIn);
            drawFrom = sample;
         }
      }
   }

   private void addVertex(VertexConsumer consumer, Matrix4f matrix, Vec3 pos, float r, float g, float b, float u, float v, int light) {
      consumer.addVertex(matrix, (float)pos.x, (float)pos.y, (float)pos.z)
         .setColor(r, g, b, 1.0F)
         .setUv(u, v)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(light)
         .setNormal(0.0F, 1.0F, 0.0F);
   }

   private float rotLerp(float prevRotation, float rotation, float partialTicks) {
      float f = rotation - prevRotation;

      while (f < -180.0F) {
         f += 360.0F;
      }

      while (f >= 180.0F) {
         f -= 360.0F;
      }

      return prevRotation + partialTicks * f;
   }
}
