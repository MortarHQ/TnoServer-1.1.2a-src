package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Portal_Abyss_Blast_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public class Portal_Abyss_Blast_Renderer extends EntityRenderer<Portal_Abyss_Blast_Entity> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/abyss_laser_beam.png");
   private static final float TEXTURE_WIDTH = 256.0F;
   private static final float TEXTURE_HEIGHT = 32.0F;
   private static final float START_RADIUS = 2.0F;
   private static final float END_RADIUS = 2.0F;
   private static final float BEAM_RADIUS = 2.0F;
   private boolean clearerView = false;

   public Portal_Abyss_Blast_Renderer(Context mgr) {
      super(mgr);
   }

   public ResourceLocation getTextureLocation(Portal_Abyss_Blast_Entity entity) {
      return TEXTURE;
   }

   public boolean shouldRender(Portal_Abyss_Blast_Entity solarBeam, Frustum camera, double camX, double camY, double camZ) {
      return true;
   }

   public void render(Portal_Abyss_Blast_Entity solarBeam, float entityYaw, float delta, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
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
   }

   private void renderFlatQuad(int frame, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
      float minU = 0.0F + 0.0625F * frame;
      float minV = 0.0F;
      float maxU = minU + 0.0625F;
      float maxV = minV + 0.5F;
      Pose matrixstack$entry = matrixStackIn.last();
      this.drawVertex(matrixstack$entry, builder, -2.0F, -2.0F, 0.0F, minU, minV, 1.0F, packedLightIn);
      this.drawVertex(matrixstack$entry, builder, -2.0F, 2.0F, 0.0F, minU, maxV, 1.0F, packedLightIn);
      this.drawVertex(matrixstack$entry, builder, 2.0F, 2.0F, 0.0F, maxU, maxV, 1.0F, packedLightIn);
      this.drawVertex(matrixstack$entry, builder, 2.0F, -2.0F, 0.0F, maxU, minV, 1.0F, packedLightIn);
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
         sideQuat.mul(new Quaternionf().rotationX((float) (Math.PI / 2)));
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
      this.drawVertex(matrixstack$entry, builder, -2.0F, offset, 0.0F, minU, minV, 1.0F, packedLightIn);
      this.drawVertex(matrixstack$entry, builder, -2.0F, length, 0.0F, minU, maxV, 1.0F, packedLightIn);
      this.drawVertex(matrixstack$entry, builder, 2.0F, length, 0.0F, maxU, maxV, 1.0F, packedLightIn);
      this.drawVertex(matrixstack$entry, builder, 2.0F, offset, 0.0F, maxU, minV, 1.0F, packedLightIn);
   }

   private void renderBeam(float length, float yaw, float pitch, int frame, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
      matrixStackIn.pushPose();
      matrixStackIn.mulPose(new Quaternionf().rotationX((float) (Math.PI / 2)));
      matrixStackIn.mulPose(new Quaternionf().rotationZ((yaw - 90.0F) * (float) (Math.PI / 180.0)));
      matrixStackIn.mulPose(new Quaternionf().rotationX(-pitch * (float) (Math.PI / 180.0)));
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
