package com.bobmowzie.mowziesmobs.client.render.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.BiFunction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;
import software.bernie.geckolib.util.RenderUtil;

public class GeckoBlockLayer<T extends GeoAnimatable> extends BlockAndItemGeoLayer<T> {
   public GeckoBlockLayer(GeoRenderer<T> rendererIn, BiFunction<GeoBone, T, BlockState> blockForBone) {
      super(rendererIn, (bone, animatable) -> null, blockForBone);
   }

   public void renderForBone(
      PoseStack poseStack,
      T animatable,
      GeoBone bone,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      ItemStack stack = this.getStackForBone(bone, animatable);
      BlockState blockState = this.getBlockForBone(bone, animatable);
      if (stack != null || blockState != null) {
         poseStack.pushPose();
         RenderUtil.translateToPivotPoint(poseStack, bone);
         if (stack != null) {
            this.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
         }

         if (blockState != null) {
            this.renderBlockForBone(poseStack, bone, blockState, animatable, bufferSource, partialTick, packedLight, packedOverlay);
         }

         buffer = bufferSource.getBuffer(renderType);
         poseStack.popPose();
      }
   }

   protected void renderBlockForBone(
      PoseStack poseStack, GeoBone bone, BlockState state, T animatable, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay
   ) {
      poseStack.pushPose();
      poseStack.translate(-0.5F, 0.0F, -0.5F);
      Minecraft.getInstance()
         .getBlockRenderer()
         .renderSingleBlock(state, poseStack, bufferSource, packedLight, packedOverlay, ModelData.EMPTY, (RenderType)null);
      poseStack.popPose();
   }
}
