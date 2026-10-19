package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.client.render.etc.LightningBoltData;
import com.github.L_Ender.cataclysm.client.render.etc.LightningRender;
import com.github.L_Ender.cataclysm.entity.projectile.Death_Laser_Beam_Entity;
import com.github.L_Ender.cataclysm.util.CMMathUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector4f;

@OnlyIn(Dist.CLIENT)
public class Death_Laser_beam_Renderer extends EntityRenderer<Death_Laser_Beam_Entity> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/harbinger/death_laser_beam.png");
   private static final float TEXTURE_WIDTH = 256.0F;
   private static final float TEXTURE_HEIGHT = 32.0F;
   private static final float START_RADIUS = 0.75F;
   private static final float BEAM_RADIUS = 0.75F;
   private boolean clearerView = false;
   private Map<UUID, LightningRender> lightningRenderMap = new HashMap<>();

   public Death_Laser_beam_Renderer(Context mgr) {
      super(mgr);
   }

   public ResourceLocation getTextureLocation(Death_Laser_Beam_Entity entity) {
      return TEXTURE;
   }

   public void render(Death_Laser_Beam_Entity solarBeam, float entityYaw, float delta, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      this.clearerView = solarBeam.caster instanceof Player
         && Minecraft.getInstance().player == solarBeam.caster
         && Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON;
      double collidePosX = solarBeam.prevCollidePosX + (solarBeam.collidePosX - solarBeam.prevCollidePosX) * delta;
      double collidePosY = solarBeam.prevCollidePosY + (solarBeam.collidePosY - solarBeam.prevCollidePosY) * delta;
      double collidePosZ = solarBeam.prevCollidePosZ + (solarBeam.collidePosZ - solarBeam.prevCollidePosZ) * delta;
      double posX = solarBeam.xo + (solarBeam.getX() - solarBeam.xo) * delta;
      double posY = solarBeam.yo + (solarBeam.getY() - solarBeam.yo) * delta;
      double posZ = solarBeam.zo + (solarBeam.getZ() - solarBeam.zo) * delta;
      float yaw = solarBeam.prevYaw + (solarBeam.renderYaw - solarBeam.prevYaw) * delta;
      float pitch = solarBeam.prevPitch + (solarBeam.renderPitch - solarBeam.prevPitch) * delta;
      float length = (float)Math.sqrt(Math.pow(collidePosX - posX, 2.0) + Math.pow(collidePosY - posY, 2.0) + Math.pow(collidePosZ - posZ, 2.0));
      int frame = Mth.floor((solarBeam.appear.getTimer() - 1 + delta) * 2.0F);
      if (frame < 0) {
         frame = 6;
      }

      VertexConsumer ivertexbuilder = bufferIn.getBuffer(CMRenderTypes.getGlowingEffect(this.getTextureLocation(solarBeam)));
      this.renderBeam(length, (180.0F / (float)Math.PI) * yaw, (180.0F / (float)Math.PI) * pitch, frame, matrixStackIn, ivertexbuilder, packedLightIn);
      matrixStackIn.pushPose();
      matrixStackIn.translate(collidePosX - posX, collidePosY - posY, collidePosZ - posZ);
      this.renderEnd(frame, solarBeam.blockSide, matrixStackIn, ivertexbuilder, packedLightIn);
      matrixStackIn.popPose();
      this.renderLighting(delta, matrixStackIn, solarBeam, bufferIn);
   }

   private void renderFlatQuad(int frame, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
      float minU = 0.0F + 0.0625F * frame;
      float minV = 0.0F;
      float maxU = minU + 0.0625F;
      float maxV = minV + 0.5F;
      Pose matrixstack$entry = matrixStackIn.last();
      this.drawVertex(matrixstack$entry, builder, -0.75F, -0.75F, 0.0F, minU, minV, 1.0F, packedLightIn);
      this.drawVertex(matrixstack$entry, builder, -0.75F, 0.75F, 0.0F, minU, maxV, 1.0F, packedLightIn);
      this.drawVertex(matrixstack$entry, builder, 0.75F, 0.75F, 0.0F, maxU, maxV, 1.0F, packedLightIn);
      this.drawVertex(matrixstack$entry, builder, 0.75F, -0.75F, 0.0F, maxU, minV, 1.0F, packedLightIn);
   }

   private void renderLighting(float frame, PoseStack poseStack, Death_Laser_Beam_Entity entity, MultiBufferSource buffer) {
      double x = Mth.lerp(frame, entity.xOld, entity.getX());
      double y = Mth.lerp(frame, entity.yOld, entity.getY());
      double z = Mth.lerp(frame, entity.zOld, entity.getZ());
      double x2 = Mth.lerp(frame, entity.prevCollidePosX, entity.collidePosX);
      double y2 = Mth.lerp(frame, entity.prevCollidePosY, entity.collidePosY);
      double z2 = Mth.lerp(frame, entity.prevCollidePosZ, entity.collidePosZ);
      float f1 = 0.0F;
      if (entity.tickCount > 20) {
         poseStack.pushPose();
         poseStack.translate(-x, -y, -z);
         LightningBoltData.BoltRenderInfo RedboltData = new LightningBoltData.BoltRenderInfo(
            0.5F, 0.15F, 0.25F, 0.25F, new Vector4f(1.0F, 0.101960786F, 0.0F, 0.9F), 0.86F
         );
         LightningBoltData bolt1 = new LightningBoltData(RedboltData, new Vec3(x, y, z), new Vec3(x2, y2, z2), 5)
            .size(0.1F)
            .lifespan(1)
            .spawn(LightningBoltData.SpawnFunction.NO_DELAY)
            .fade(LightningBoltData.FadeFunction.NONE);
         LightningBoltData.BoltRenderInfo YellowboltData = new LightningBoltData.BoltRenderInfo(
            0.5F, 0.1F, 0.25F, 0.15F, new Vector4f(0.9764706F, 0.7607843F, 0.16862746F, 0.7F), 0.86F
         );
         LightningBoltData bolt2 = new LightningBoltData(YellowboltData, new Vec3(x, y, z), new Vec3(x2, y2, z2), 5)
            .size(0.07F)
            .lifespan(1)
            .spawn(LightningBoltData.SpawnFunction.NO_DELAY)
            .fade(LightningBoltData.FadeFunction.NONE);
         LightningRender lightningRender = this.getLightingRender(entity.getUUID());
         if (!Minecraft.getInstance().isPaused()) {
            lightningRender.update(entity, bolt1, frame);
            lightningRender.update(entity, bolt2, frame);
         }

         lightningRender.render(frame, poseStack, buffer);
         poseStack.popPose();
      }

      if (entity.isRemoved() && this.lightningRenderMap.containsKey(entity.getUUID())) {
         this.lightningRenderMap.remove(entity.getUUID());
      }
   }

   private LightningRender getLightingRender(UUID uuid) {
      if (this.lightningRenderMap.get(uuid) == null) {
         this.lightningRenderMap.put(uuid, new LightningRender());
      }

      return this.lightningRenderMap.get(uuid);
   }

   private void renderStart(int frame, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
      if (!this.clearerView) {
         matrixStackIn.pushPose();
         Quaternionf quat = this.entityRenderDispatcher.cameraOrientation();
         matrixStackIn.mulPose(quat);
         this.renderFlatQuad(frame, matrixStackIn, builder, packedLightIn);
         matrixStackIn.popPose();
      }
   }

   private void renderEnd(int frame, Direction side, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
      matrixStackIn.pushPose();
      Quaternionf quat = this.entityRenderDispatcher.cameraOrientation();
      matrixStackIn.mulPose(quat);
      this.renderFlatQuad(frame, matrixStackIn, builder, packedLightIn);
      matrixStackIn.popPose();
      if (side != null) {
         matrixStackIn.pushPose();
         Quaternionf sideQuat = side.getRotation();
         sideQuat.mul(CMMathUtil.quatFromRotationXYZ(90.0F, 0.0F, 0.0F, true));
         matrixStackIn.mulPose(sideQuat);
         matrixStackIn.translate(0.0F, 0.0F, -0.01F);
         this.renderFlatQuad(frame, matrixStackIn, builder, packedLightIn);
         matrixStackIn.popPose();
      }
   }

   private void drawBeam(float length, int frame, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
      float minU = 0.0F;
      float minV = 0.5F + 0.03125F * frame;
      float maxU = minU + 0.078125F;
      float maxV = minV + 0.03125F;
      Pose matrixstack$entry = matrixStackIn.last();
      float offset = this.clearerView ? -1.0F : 0.0F;
      this.drawVertex(matrixstack$entry, builder, -0.75F, offset, 0.0F, minU, minV, 1.0F, packedLightIn);
      this.drawVertex(matrixstack$entry, builder, -0.75F, length, 0.0F, minU, maxV, 1.0F, packedLightIn);
      this.drawVertex(matrixstack$entry, builder, 0.75F, length, 0.0F, maxU, maxV, 1.0F, packedLightIn);
      this.drawVertex(matrixstack$entry, builder, 0.75F, offset, 0.0F, maxU, minV, 1.0F, packedLightIn);
   }

   private void renderBeam(float length, float yaw, float pitch, int frame, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
      matrixStackIn.pushPose();
      matrixStackIn.mulPose(CMMathUtil.quatFromRotationXYZ(90.0F, 0.0F, 0.0F, true));
      matrixStackIn.mulPose(CMMathUtil.quatFromRotationXYZ(0.0F, 0.0F, yaw - 90.0F, true));
      matrixStackIn.mulPose(CMMathUtil.quatFromRotationXYZ(-pitch, 0.0F, 0.0F, true));
      matrixStackIn.pushPose();
      if (!this.clearerView) {
         matrixStackIn.mulPose(new Quaternionf().rotationY(Minecraft.getInstance().gameRenderer.getMainCamera().getXRot() + 90.0F));
      }

      this.drawBeam(length, frame, matrixStackIn, builder, packedLightIn);
      matrixStackIn.popPose();
      if (!this.clearerView) {
         matrixStackIn.pushPose();
         matrixStackIn.mulPose(
            new Quaternionf().rotationY((-Minecraft.getInstance().gameRenderer.getMainCamera().getXRot() - 90.0F) * (float) (Math.PI / 180.0))
         );
         this.drawBeam(length, frame, matrixStackIn, builder, packedLightIn);
         matrixStackIn.popPose();
      }

      matrixStackIn.popPose();
   }

   public void drawVertex(
      Pose normals, VertexConsumer vertexBuilder, float offsetX, float offsetY, float offsetZ, float textureX, float textureY, float alpha, int packedLightIn
   ) {
      vertexBuilder.addVertex(normals, offsetX, offsetY, offsetZ)
         .setColor(1.0F, 1.0F, 1.0F, 1.0F * alpha)
         .setUv(textureX, textureY)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(packedLightIn)
         .setNormal(normals, 0.0F, 1.0F, 0.0F);
   }
}
