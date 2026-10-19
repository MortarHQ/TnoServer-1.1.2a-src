package com.mortal_cultivation.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mortal_cultivation.block.entity.RuneTableBlockEntity;
import com.mortal_cultivation.client.model.RuneTableOrbGeoModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.Color;

public final class RuneTableOrbLayer extends GeoRenderLayer<RuneTableBlockEntity> {
   private static final float GLOW_STRENGTH = 0.55F;
   private static final float HOVER_Y = 1.29F;
   private static final float LANDED_Y = 1.02F;
   private static final float BOB_AMPLITUDE = 0.025F;
   private static final float ORB_SCALE = 0.85F;
   private final RuneTableOrbGeoModel orbModel = new RuneTableOrbGeoModel();

   public RuneTableOrbLayer(GeoRenderer<RuneTableBlockEntity> renderer) {
      super(renderer);
   }

   public GeoModel<RuneTableBlockEntity> getGeoModel() {
      return this.orbModel;
   }

   public void render(
      PoseStack poseStack,
      RuneTableBlockEntity table,
      BakedGeoModel bakedModel,
      @Nullable RenderType renderType,
      MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      if (table.shouldRenderOrb()) {
         float time = (float)(table.getLevel() == null ? 0L : table.getLevel().getGameTime()) + partialTick;
         boolean absorbing = table.isAbsorbingStoredRuneOrb();
         float liftProgress = absorbing ? smoothStep(table.getLiftProgress(partialTick)) : 0.0F;
         float storedWeight = absorbing ? 1.0F - smoothStep(table.getAbsorptionProgress(partialTick)) : 0.0F;
         float recoveredWeight = 1.0F - storedWeight;
         float y;
         float yaw;
         if (absorbing) {
            float hoverY = 1.29F + Mth.sin(time * 0.08F) * 0.025F;
            y = Mth.lerp(liftProgress, 1.02F, hoverY);
            yaw = table.getLandingYaw() + table.getOrbRotationElapsed(partialTick);
         } else {
            float landingProgress = smoothStep(table.getLandingProgress(partialTick));
            long landingStart = table.getLandingStartGameTime();
            float startTime = landingStart >= 0L ? (float)landingStart : time;
            float startY = 1.29F + Mth.sin(startTime * 0.08F) * 0.025F;
            y = Mth.lerp(landingProgress, startY, 1.02F);
            yaw = table.getLandingYaw();
         }

         BakedGeoModel orbBakedModel = this.getDefaultBakedModel(table);
         poseStack.pushPose();
         poseStack.translate(0.0F, y, 0.0F);
         poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
         poseStack.scale(0.85F, 0.85F, 0.85F);
         this.renderBase(orbBakedModel, poseStack, bufferSource, table, RuneTableOrbGeoModel.BASE_ORB_TEXTURE, partialTick);
         if (storedWeight > 0.001F) {
            RenderType storedType = RenderType.entityTranslucent(RuneTableOrbGeoModel.STORED_ORB_TEXTURE, false);
            int storedColor = Color.ofRGBA(1.0F, 1.0F, 1.0F, storedWeight).argbInt();
            this.getRenderer()
               .reRender(
                  orbBakedModel,
                  poseStack,
                  bufferSource,
                  table,
                  storedType,
                  bufferSource.getBuffer(storedType),
                  partialTick,
                  15728880,
                  OverlayTexture.NO_OVERLAY,
                  storedColor
               );
         }

         this.renderGlow(orbBakedModel, poseStack, bufferSource, table, RuneTableOrbGeoModel.BASE_ORB_TEXTURE, recoveredWeight, partialTick);
         this.renderGlow(orbBakedModel, poseStack, bufferSource, table, RuneTableOrbGeoModel.STORED_ORB_TEXTURE, storedWeight, partialTick);
         poseStack.popPose();
      }
   }

   private void renderBase(
      BakedGeoModel bakedModel, PoseStack poseStack, MultiBufferSource bufferSource, RuneTableBlockEntity table, ResourceLocation texture, float partialTick
   ) {
      RenderType baseType = RenderType.entityCutoutNoCull(texture, false);
      this.getRenderer()
         .reRender(
            bakedModel,
            poseStack,
            bufferSource,
            table,
            baseType,
            bufferSource.getBuffer(baseType),
            partialTick,
            15728880,
            OverlayTexture.NO_OVERLAY,
            Color.WHITE.argbInt()
         );
   }

   private void renderGlow(
      BakedGeoModel bakedModel,
      PoseStack poseStack,
      MultiBufferSource bufferSource,
      RuneTableBlockEntity table,
      ResourceLocation texture,
      float weight,
      float partialTick
   ) {
      if (!(weight <= 0.001F)) {
         RenderType glowType = RenderType.eyes(texture);
         float intensity = 0.55F * weight;
         int glowColor = Color.ofRGBA(intensity, intensity, intensity, 1.0F).argbInt();
         this.getRenderer()
            .reRender(
               bakedModel,
               poseStack,
               bufferSource,
               table,
               glowType,
               bufferSource.getBuffer(glowType),
               partialTick,
               15728880,
               OverlayTexture.NO_OVERLAY,
               glowColor
            );
      }
   }

   private static float smoothStep(float progress) {
      float clamped = Mth.clamp(progress, 0.0F, 1.0F);
      return clamped * clamped * (3.0F - 2.0F * clamped);
   }
}
