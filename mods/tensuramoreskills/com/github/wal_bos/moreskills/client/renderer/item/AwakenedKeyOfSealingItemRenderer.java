package com.github.wal_bos.moreskills.client.renderer.item;

import com.github.wal_bos.moreskills.client.model.item.AwakenedKeyOfSealingItemModel;
import com.github.wal_bos.moreskills.item.AwakenedKeyOfSealingItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class AwakenedKeyOfSealingItemRenderer extends GeoItemRenderer<AwakenedKeyOfSealingItem> {
   public AwakenedKeyOfSealingItemRenderer() {
      super(new AwakenedKeyOfSealingItemModel());
   }

   public void renderByItem(
      ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay
   ) {
      poseStack.pushPose();
      if (transformType == ItemDisplayContext.GUI) {
         poseStack.translate(0.0, -0.15, 0.0);
         poseStack.mulPose(Axis.XP.rotationDegrees(25.0F));
         poseStack.mulPose(Axis.YP.rotationDegrees(-35.0F));
         poseStack.scale(0.35F, 0.35F, 0.35F);
      } else {
         poseStack.translate(0.37, -2.25, 1.05);
         poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
         poseStack.scale(0.35F, 0.35F, 0.35F);
         poseStack.translate(0.0, -2.0, -8.15);
      }

      super.renderByItem(stack, transformType, poseStack, bufferSource, packedLight, packedOverlay);
      poseStack.popPose();
   }
}
