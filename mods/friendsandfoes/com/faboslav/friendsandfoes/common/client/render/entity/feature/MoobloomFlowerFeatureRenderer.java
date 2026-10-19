package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.faboslav.friendsandfoes.common.entity.MoobloomEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public final class MoobloomFlowerFeatureRenderer<T extends MoobloomEntity> extends RenderLayer<T, CowModel<T>> {
   private final BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();

   public MoobloomFlowerFeatureRenderer(RenderLayerParent<T, CowModel<T>> featureRendererContext) {
      super(featureRendererContext);
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource bufferSource,
      int packedLight,
      T moobloom,
      float limbSwing,
      float limbSwingAmount,
      float partialTick,
      float ageInTicks,
      float yRot,
      float xRot
   ) {
      int overlay = LivingEntityRenderer.getOverlayCoords(moobloom, 0.0F);
      if (!moobloom.isBaby() && !moobloom.isInvisible()) {
         BushBlock flower = moobloom.getVariant().getFlower();
         BlockState blockState = flower.defaultBlockState();
         if (flower instanceof DoublePlantBlock) {
            blockState = (BlockState)blockState.setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER);
         }

         float scaleFactor = flower instanceof DoublePlantBlock ? 0.6F : 0.8F;
         float yOffset = flower instanceof DoublePlantBlock ? -0.666F : -0.5F;
         Minecraft minecraft = Minecraft.getInstance();
         boolean renderAsModel = minecraft.shouldEntityAppearGlowing(moobloom) && moobloom.isInvisible();
         BakedModel model = this.blockRenderer.getBlockModel(blockState);
         poseStack.pushPose();
         ((CowModel)this.getParentModel()).getHead().translateAndRotate(poseStack);
         poseStack.translate(0.09, -0.6, -0.185);
         poseStack.scale(-scaleFactor, -scaleFactor, scaleFactor);
         poseStack.translate(-0.5, yOffset, -0.5);
         this.renderFlower(poseStack, bufferSource, packedLight, renderAsModel, blockState, overlay, model);
         poseStack.popPose();
         poseStack.pushPose();
         poseStack.translate(0.22, -0.28, -0.06);
         poseStack.scale(-scaleFactor, -scaleFactor, scaleFactor);
         poseStack.translate(-0.5, yOffset, -0.5);
         this.renderFlower(poseStack, bufferSource, packedLight, renderAsModel, blockState, overlay, model);
         poseStack.popPose();
         poseStack.pushPose();
         poseStack.translate(-0.2, -0.22, 0.01);
         poseStack.scale(-scaleFactor, -scaleFactor, scaleFactor);
         poseStack.translate(-0.5, yOffset, -0.5);
         this.renderFlower(poseStack, bufferSource, packedLight, renderAsModel, blockState, overlay, model);
         poseStack.popPose();
         poseStack.pushPose();
         poseStack.translate(0.03, -0.28, 0.47);
         poseStack.scale(-scaleFactor, -scaleFactor, scaleFactor);
         poseStack.translate(-0.5, yOffset, -0.5);
         this.renderFlower(poseStack, bufferSource, packedLight, renderAsModel, blockState, overlay, model);
         poseStack.popPose();
      }
   }

   private void renderFlower(
      PoseStack poseStack, MultiBufferSource multiBufferSource, int light, boolean renderAsModel, BlockState blockState, int overlay, BakedModel model
   ) {
      if (renderAsModel) {
         this.blockRenderer
            .getModelRenderer()
            .renderModel(
               poseStack.last(),
               multiBufferSource.getBuffer(RenderType.outline(TextureAtlas.LOCATION_BLOCKS)),
               blockState,
               model,
               0.0F,
               0.0F,
               0.0F,
               light,
               overlay
            );
      } else {
         this.blockRenderer.renderSingleBlock(blockState, poseStack, multiBufferSource, light, overlay);
      }
   }
}
