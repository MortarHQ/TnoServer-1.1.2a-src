package com.bobmowzie.mowziesmobs.client.render.entity.layer;

import com.bobmowzie.mowziesmobs.client.render.entity.RenderUmvuthi;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import com.ilexiconn.llibrary.client.util.ClientUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.RenderUtil;

public class UmvuthiSunLayer extends GeoRenderLayer<EntityUmvuthi> {
   protected Matrix4f dispatchedMat = new Matrix4f();
   protected Matrix4f renderEarlyMat = new Matrix4f();
   private final Vec3 v1 = new Vec3(-2.0, 1.0, -1.0);
   private final Vec3 v2 = new Vec3(0.0, 1.0, -1.0);
   private final Vec3 v3 = new Vec3(-1.0, 0.0, 1.0);
   private final Vec3 v4 = new Vec3(-1.0, 0.0, -3.0);
   private final Vec3 v5 = new Vec3(-3.0, -1.0, 0.0);
   private final Vec3 v6 = new Vec3(-3.0, -1.0, -2.0);
   private final Vec3 v7 = new Vec3(1.0, -1.0, 0.0);
   private final Vec3 v8 = new Vec3(1.0, -1.0, -2.0);
   private final Vec3 v9 = new Vec3(0.0, -3.0, -1.0);
   private final Vec3 v10 = new Vec3(-2.0, -3.0, -1.0);
   private final Vec3 v11 = new Vec3(-1.0, -2.0, 1.0);
   private final Vec3 v12 = new Vec3(-1.0, -2.0, -3.0);
   private final Vec3[] POS = new Vec3[]{
      this.v1,
      this.v2,
      this.v3,
      this.v1,
      this.v1,
      this.v2,
      this.v4,
      this.v1,
      this.v1,
      this.v5,
      this.v6,
      this.v1,
      this.v2,
      this.v7,
      this.v8,
      this.v2,
      this.v2,
      this.v8,
      this.v4,
      this.v2,
      this.v1,
      this.v4,
      this.v6,
      this.v1,
      this.v1,
      this.v5,
      this.v3,
      this.v1,
      this.v2,
      this.v3,
      this.v7,
      this.v2,
      this.v9,
      this.v7,
      this.v8,
      this.v9,
      this.v5,
      this.v6,
      this.v10,
      this.v5,
      this.v9,
      this.v10,
      this.v11,
      this.v9,
      this.v9,
      this.v10,
      this.v12,
      this.v9,
      this.v4,
      this.v6,
      this.v12,
      this.v4,
      this.v4,
      this.v8,
      this.v12,
      this.v4,
      this.v3,
      this.v5,
      this.v11,
      this.v3,
      this.v3,
      this.v7,
      this.v11,
      this.v3,
      this.v5,
      this.v10,
      this.v11,
      this.v5,
      this.v7,
      this.v9,
      this.v11,
      this.v7,
      this.v8,
      this.v9,
      this.v12,
      this.v8,
      this.v6,
      this.v10,
      this.v12,
      this.v6
   };

   public UmvuthiSunLayer(GeoRenderer<EntityUmvuthi> entityRendererIn) {
      super(entityRendererIn);
   }

   public void renderForBone(
      PoseStack poseStack,
      EntityUmvuthi umvuthi,
      GeoBone bone,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      float partialTicks,
      int packedLight,
      int packedOverlay
   ) {
      super.renderForBone(poseStack, umvuthi, bone, renderType, bufferSource, buffer, partialTicks, packedLight, packedOverlay);
      if (umvuthi.shouldRenderSun()) {
         if (bone.isHidden()) {
            return;
         }

         poseStack.pushPose();
         RenderUtil.translateToPivotPoint(poseStack, bone);
         if (bone.getName().equals("sun_render")) {
            poseStack.translate(0.06, 0.0, -0.0);
            poseStack.scale(0.06F, 0.06F, 0.06F);
            VertexConsumer ivertexbuilder = bufferSource.getBuffer(RenderType.entityTranslucent(RenderUmvuthi.SUN, true));
            Pose matrixstack$entry = poseStack.last();
            Matrix4f matrix4f = matrixstack$entry.pose();
            Matrix3f matrix3f = matrixstack$entry.normal();
            float scaleMult = 1.0F;
            if (umvuthi.getActiveAbilityType() == EntityUmvuthi.SUPERNOVA_ABILITY && umvuthi.getActiveAbility().getTicksInUse() > 90) {
               scaleMult = (umvuthi.getActiveAbility().getTicksInUse() + partialTicks - 90.0F) / 10.0F;
               scaleMult = Mth.clamp(scaleMult, 0.0F, 1.0F);
            }

            this.drawSun(matrix4f, matrix3f, ivertexbuilder, umvuthi.tickCount + partialTicks, scaleMult);
         }

         bufferSource.getBuffer(renderType);
         poseStack.popPose();
      }
   }

   public void render(
      PoseStack poseStack,
      EntityUmvuthi entityLivingBaseIn,
      BakedGeoModel bakedModel,
      RenderType renderType,
      MultiBufferSource bufferIn,
      VertexConsumer buffer,
      float partialTicks,
      int packedLight,
      int packedOverlay
   ) {
   }

   private void drawSun(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer builder, float time, float scaleMultiplier) {
      float scale = (0.9F + (float)Math.sin(time * 4.0F) * 0.07F) * scaleMultiplier;

      for (int i = 0; i < 4; i++) {
         for (Vec3 vec : this.POS) {
            vec = vec.multiply(1.0F + scale * i, 1.0F + scale * i, 1.0F + scale * i);
            VertexConsumer consumer = builder.addVertex(matrix4f, (float)vec.x + scale * i, (float)vec.y + scale * i, (float)vec.z + scale * i)
               .setColor(1.0F, 1.0F, 0.4F, 0.2F)
               .setUv(0.0F, 0.5F)
               .setOverlay(OverlayTexture.NO_OVERLAY)
               .setLight(15728880);
            ClientUtils.transformNormals(consumer, matrix3f, 1.0F, 1.0F, 1.0F);
         }
      }

      for (Vec3 vec : this.POS) {
         VertexConsumer consumer = builder.addVertex(
               matrix4f, (float)vec.x * 1.2F * scaleMultiplier, (float)vec.y * 1.2F * scaleMultiplier, (float)vec.z * 1.2F * scaleMultiplier
            )
            .setColor(1.0F, 1.0F, 1.0F, 1.0F)
            .setUv(0.0F, 0.5F)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(15728880);
         ClientUtils.transformNormals(consumer, matrix3f, 1.0F, 1.0F, 1.0F);
      }
   }
}
