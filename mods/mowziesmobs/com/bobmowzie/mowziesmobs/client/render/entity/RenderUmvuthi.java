package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelUmvuthi;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.render.MMRenderType;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.GeckoSunblockLayer;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.UmvuthiSunLayer;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import com.ilexiconn.llibrary.client.util.ClientUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3d;

public class RenderUmvuthi extends MowzieGeoEntityRenderer<EntityUmvuthi> {
   public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/entity/umvuthi.png");
   public static final ResourceLocation SUN = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/effects/sun_effect.png");
   public static final float BURST_RADIUS = 3.5F;
   public static final int BURST_FRAME_COUNT = 10;
   public static final int BURST_START_FRAME = 12;
   private MultiBufferSource source;
   private EntityUmvuthi entity;

   public RenderUmvuthi(Context mgr) {
      super(mgr, new ModelUmvuthi());
      this.addRenderLayer(new FrozenRenderHandler.GeckoLayerFrozen(this, mgr));
      this.addRenderLayer(new GeckoSunblockLayer(this, mgr));
      this.addRenderLayer(new UmvuthiSunLayer(this));
      this.shadowRadius = 1.0F;
   }

   public ResourceLocation getTextureLocation(EntityUmvuthi entity) {
      return this.getMowzieGeoModel().getTextureResource(entity);
   }

   public void render(EntityUmvuthi umvuthi, float entityYaw, float delta, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      this.source = bufferIn;
      this.entity = umvuthi;
      if (!umvuthi.isInvisible()
         && umvuthi.getActiveAbilityType() == EntityUmvuthi.SOLAR_FLARE_ABILITY
         && umvuthi.getActiveAbility().getTicksInUse() > 12
         && umvuthi.getActiveAbility().getTicksInUse() < 21) {
         matrixStackIn.pushPose();
         Quaternionf quat = this.entityRenderDispatcher.cameraOrientation();
         matrixStackIn.mulPose(quat);
         matrixStackIn.translate(0.0F, 1.0F, 0.0F);
         matrixStackIn.scale(0.8F, 0.8F, 0.8F);
         VertexConsumer ivertexbuilder = bufferIn.getBuffer(MMRenderType.getSolarFlare(RenderSunstrike.TEXTURE));
         Pose matrixstack$entry = matrixStackIn.last();
         Matrix4f matrix4f = matrixstack$entry.pose();
         Matrix3f matrix3f = matrixstack$entry.normal();
         drawBurst(matrix4f, matrix3f, ivertexbuilder, umvuthi.getActiveAbility().getTicksInUse() - 12 + delta, packedLightIn);
         matrixStackIn.popPose();
      }

      super.render(umvuthi, entityYaw, delta, matrixStackIn, bufferIn, packedLightIn);
   }

   public void renderUpdates(EntityUmvuthi umvuthi, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      super.renderUpdates(this.entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
      MowzieGeoBone sunRender = this.getMowzieGeoModel().getMowzieBone("sun_render");
      Vector3d sunRenderPos = sunRender.getWorldPosition();
      if (umvuthi.headPos != null && umvuthi.headPos.length > 0) {
         umvuthi.headPos[0] = new Vec3(sunRenderPos.x, sunRenderPos.y, sunRenderPos.z);
      }

      if (umvuthi.getActiveAbilityType() == EntityUmvuthi.SUPERNOVA_ABILITY && umvuthi.betweenHandPos != null && umvuthi.betweenHandPos.length > 0) {
         Vector3d novaRenderPos = this.getMowzieGeoModel().getMowzieBone("superNovaCenter").getWorldPosition();
         float blendStart = 4.0F;
         float blendDuration = 4.0F;
         int ticksInUse = umvuthi.getActiveAbility().getTicksInUse();
         if (ticksInUse <= blendDuration + blendStart) {
            Vec3 sunRenderPosVec3 = new Vec3(sunRenderPos.x, sunRenderPos.y, sunRenderPos.z);
            Vec3 novaRenderPosVec3 = new Vec3(novaRenderPos.x, novaRenderPos.y, novaRenderPos.z);
            float alpha = (umvuthi.getActiveAbility().getTicksInUse() + partialTick - blendStart) / blendDuration;
            alpha = Math.max(0.0F, alpha);
            Vec3 newPos = novaRenderPosVec3.add(sunRenderPosVec3.subtract(novaRenderPosVec3).scale(1.0 - alpha));
            novaRenderPos.set(newPos.x, newPos.y, newPos.z);
         }

         umvuthi.betweenHandPos[0] = new Vec3(novaRenderPos.x, novaRenderPos.y, novaRenderPos.z);
      }

      if (!Minecraft.getInstance().isPaused()) {
         MowzieGeoBone mask = this.getMowzieGeoModel().getMowzieBone("maskTwitcher");
         umvuthi.updateRattleSound(mask.getRotZ());
      }
   }

   public boolean shouldRender(EntityUmvuthi umvuthi, Frustum frustum, double p_114493_, double p_114494_, double p_114495_) {
      boolean result = super.shouldRender(umvuthi, frustum, p_114493_, p_114494_, p_114495_);
      if (!result) {
         umvuthi.headPos[0] = umvuthi.position().add(0.0, umvuthi.getEyeHeight(), 0.0);
      }

      return result;
   }

   public static void drawBurst(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer builder, float tick, int packedLightIn) {
      int dissapateFrame = 6;
      float firstSpeed = 2.0F;
      float secondSpeed = 1.0F;
      int frame = (int)(tick * firstSpeed) <= dissapateFrame
         ? (int)(tick * firstSpeed)
         : (int)(dissapateFrame + (tick - dissapateFrame / firstSpeed) * secondSpeed);
      if (frame > 10) {
         frame = 10;
      }

      float minU = 0.0625F * frame;
      float maxU = minU + 0.0625F;
      float minV = 0.5F;
      float maxV = minV + 0.5F;
      float offset = 0.219F * (frame % 2);
      float opacity = tick < 8.0F ? 0.8F : 0.4F;
      drawVertex(matrix4f, matrix3f, builder, -3.5F + offset, -3.5F + offset, 0.0F, minU, minV, opacity, packedLightIn);
      drawVertex(matrix4f, matrix3f, builder, -3.5F + offset, 3.5F + offset, 0.0F, minU, maxV, opacity, packedLightIn);
      drawVertex(matrix4f, matrix3f, builder, 3.5F + offset, 3.5F + offset, 0.0F, maxU, maxV, opacity, packedLightIn);
      drawVertex(matrix4f, matrix3f, builder, 3.5F + offset, -3.5F + offset, 0.0F, maxU, minV, opacity, packedLightIn);
   }

   public static void drawVertex(
      Matrix4f matrix,
      Matrix3f normals,
      VertexConsumer vertexBuilder,
      float offsetX,
      float offsetY,
      float offsetZ,
      float textureX,
      float textureY,
      float alpha,
      int packedLightIn
   ) {
      VertexConsumer vertex = vertexBuilder.addVertex(matrix, offsetX, offsetY, offsetZ)
         .setColor(1.0F, 1.0F, 1.0F, 1.0F * alpha)
         .setUv(textureX, textureY)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(packedLightIn);
      ClientUtils.transformNormals(vertex, normals, 1.0F, 0.0F, 1.0F);
   }
}
