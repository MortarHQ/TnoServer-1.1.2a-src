package com.iafenvoy.iceandfire.render.entity.feature;

import com.iafenvoy.iceandfire.entity.HydraEntity;
import com.iafenvoy.iceandfire.render.entity.HydraEntityRenderer;
import com.iafenvoy.iceandfire.render.model.HydraBodyModel;
import com.iafenvoy.iceandfire.render.model.HydraHeadModel;
import com.iafenvoy.uranus.client.model.AdvancedModelBox;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class HydraHeadFeatureRenderer extends RenderLayer<HydraEntity, HydraBodyModel> {
   public static final ResourceLocation TEXTURE_STONE = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/hydra/stone.png");
   private static final float[][] TRANSLATE = new float[][]{
      {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F},
      {-0.15F, 0.15F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F},
      {-0.3F, 0.0F, 0.3F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F},
      {-0.4F, -0.1F, 0.1F, 0.4F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F},
      {-0.5F, -0.2F, 0.0F, 0.2F, 0.5F, 0.0F, 0.0F, 0.0F, 0.0F},
      {-0.7F, -0.4F, -0.2F, 0.2F, 0.4F, 0.7F, 0.0F, 0.0F, 0.0F},
      {-0.7F, -0.4F, -0.2F, 0.0F, 0.2F, 0.4F, 0.7F, 0.0F, 0.0F},
      {-0.6F, -0.4F, -0.2F, -0.1F, 0.1F, 0.2F, 0.4F, 0.6F, 0.0F},
      {-0.6F, -0.4F, -0.2F, -0.1F, 0.0F, 0.1F, 0.2F, 0.4F, 0.6F}
   };
   private static final float[][] ROTATE = new float[][]{
      {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F},
      {10.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F},
      {10.0F, 0.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F},
      {25.0F, 10.0F, -10.0F, -25.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F},
      {30.0F, 15.0F, 0.0F, -15.0F, -30.0F, 0.0F, 0.0F, 0.0F, 0.0F},
      {40.0F, 25.0F, 5.0F, -5.0F, -25.0F, -40.0F, 0.0F, 0.0F, 0.0F},
      {40.0F, 30.0F, 15.0F, 0.0F, -15.0F, -30.0F, -40.0F, 0.0F, 0.0F},
      {45.0F, 30.0F, 20.0F, 5.0F, -5.0F, -20.0F, -30.0F, -45.0F, 0.0F},
      {50.0F, 37.0F, 25.0F, 15.0F, 0.0F, -15.0F, -25.0F, -37.0F, -50.0F}
   };
   private static final HydraHeadModel[] modelArr = new HydraHeadModel[9];
   private final HydraEntityRenderer renderer;

   public HydraHeadFeatureRenderer(HydraEntityRenderer renderer) {
      super(renderer);
      this.renderer = renderer;
   }

   public static void renderHydraHeads(
      HydraBodyModel model,
      boolean stone,
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      HydraEntity hydra,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      matrixStackIn.pushPose();
      int heads = hydra.getHeadCount();
      translateToBody(model, matrixStackIn);
      RenderType type = RenderType.entityCutout(stone ? TEXTURE_STONE : getHeadTexture(hydra));

      for (int head = 1; head <= heads; head++) {
         matrixStackIn.pushPose();
         float bodyWidth = 0.5F;
         matrixStackIn.translate(TRANSLATE[heads - 1][head - 1] * bodyWidth, 0.0F, 0.0F);
         matrixStackIn.mulPose(Axis.YP.rotationDegrees(ROTATE[heads - 1][head - 1]));
         modelArr[head - 1].setAngles(hydra, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         modelArr[head - 1].renderToBuffer(matrixStackIn, bufferIn.getBuffer(type), packedLightIn, LivingEntityRenderer.getOverlayCoords(hydra, 0.0F), -1);
         matrixStackIn.popPose();
      }

      matrixStackIn.popPose();
   }

   public static ResourceLocation getHeadTexture(HydraEntity gorgon) {
      return switch (gorgon.getVariant()) {
         case 1 -> HydraEntityRenderer.TEXUTURE_1;
         case 2 -> HydraEntityRenderer.TEXUTURE_2;
         default -> HydraEntityRenderer.TEXUTURE_0;
      };
   }

   protected static void translateToBody(HydraBodyModel model, PoseStack stack) {
      postRender(model.BodyUpper, stack);
   }

   protected static void postRender(AdvancedModelBox renderer, PoseStack matrixStackIn) {
      if (renderer.rotateAngleX != 0.0F || renderer.rotateAngleY != 0.0F || renderer.rotateAngleZ != 0.0F) {
         matrixStackIn.translate(renderer.rotationPointX * 0.0625F, renderer.rotationPointY * 0.0625F, renderer.rotateAngleZ * 0.0625F);
         if (renderer.rotateAngleZ != 0.0F) {
            matrixStackIn.mulPose(Axis.ZP.rotation(renderer.rotateAngleZ));
         }

         if (renderer.rotateAngleY != 0.0F) {
            matrixStackIn.mulPose(Axis.YP.rotation(renderer.rotateAngleY));
         }

         if (renderer.rotateAngleX != 0.0F) {
            matrixStackIn.mulPose(Axis.XP.rotation(renderer.rotateAngleX));
         }
      } else if (renderer.rotationPointX != 0.0F || renderer.rotationPointY != 0.0F) {
         matrixStackIn.translate(renderer.rotationPointX * 0.0625F, renderer.rotationPointY * 0.0625F, renderer.rotateAngleZ * 0.0625F);
      }
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      HydraEntity entity,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (!entity.isInvisible()) {
         renderHydraHeads(
            (HydraBodyModel)this.renderer.getModel(),
            false,
            matrixStackIn,
            bufferIn,
            packedLightIn,
            entity,
            limbSwing,
            limbSwingAmount,
            partialTicks,
            ageInTicks,
            netHeadYaw,
            headPitch
         );
      }
   }

   public ResourceLocation getTexture(HydraEntity gorgon) {
      return switch (gorgon.getVariant()) {
         case 1 -> HydraEntityRenderer.TEXUTURE_1;
         case 2 -> HydraEntityRenderer.TEXUTURE_2;
         default -> HydraEntityRenderer.TEXUTURE_0;
      };
   }

   static {
      for (int i = 0; i < modelArr.length; i++) {
         modelArr[i] = new HydraHeadModel(i);
      }
   }
}
