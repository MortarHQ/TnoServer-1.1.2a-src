package com.mortal_cultivation.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mortal_cultivation.client.model.ZhaoYingZhuItemGeoModel;
import com.mortal_cultivation.client.model.ZhaoYingZhuModel;
import com.mortal_cultivation.item.ZhaoYingZhuItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public final class ZhaoYingZhuItemRenderer extends GeoItemRenderer<ZhaoYingZhuItem> {
   public static final ModelResourceLocation ICON_MODEL = ModelResourceLocation.standalone(
      ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "item/zhao_ying_zhu_icon")
   );
   public static final ModelResourceLocation STORED_ICON_MODEL = ModelResourceLocation.standalone(
      ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "item/zhao_ying_zhu_stored")
   );

   public ZhaoYingZhuItemRenderer() {
      super(new ZhaoYingZhuItemGeoModel());
      this.addRenderLayer(new ZhaoYingZhuItemGlowLayer(this));
   }

   public ResourceLocation getTextureLocation(ZhaoYingZhuItem animatable) {
      return this.currentItemStack != null && ZhaoYingZhuItem.hasStoredRunes(this.currentItemStack)
         ? ZhaoYingZhuModel.STORED_TEXTURE
         : super.getTextureLocation(animatable);
   }

   public void renderByItem(
      ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay
   ) {
      if (isHandContext(context)) {
         super.renderByItem(stack, context, poseStack, bufferSource, packedLight, packedOverlay);
      } else {
         boolean leftHand = context == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || context == ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
         ModelResourceLocation iconModel = ZhaoYingZhuItem.hasStoredRunes(stack) ? STORED_ICON_MODEL : ICON_MODEL;
         Minecraft minecraft = Minecraft.getInstance();
         poseStack.pushPose();
         poseStack.translate(0.5F, 0.5F, 0.5F);
         minecraft.getItemRenderer()
            .render(stack, context, leftHand, poseStack, bufferSource, 15728880, packedOverlay, minecraft.getModelManager().getModel(iconModel));
         poseStack.popPose();
      }
   }

   private static boolean isHandContext(ItemDisplayContext context) {
      return context == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND
         || context == ItemDisplayContext.FIRST_PERSON_LEFT_HAND
         || context == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND
         || context == ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
   }
}
