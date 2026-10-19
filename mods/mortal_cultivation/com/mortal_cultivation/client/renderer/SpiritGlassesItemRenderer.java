package com.mortal_cultivation.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mortal_cultivation.client.model.SpiritGlassesGeoModel;
import com.mortal_cultivation.item.SpiritGlassesItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public final class SpiritGlassesItemRenderer extends GeoItemRenderer<SpiritGlassesItem> {
   public static final ModelResourceLocation ICON_MODEL = ModelResourceLocation.standalone(
      ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "item/spirit_glasses_icon")
   );

   public SpiritGlassesItemRenderer() {
      super(new SpiritGlassesGeoModel());
   }

   public void renderByItem(
      ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay
   ) {
      if (context == ItemDisplayContext.HEAD) {
         super.renderByItem(stack, context, poseStack, bufferSource, packedLight, packedOverlay);
      } else {
         boolean leftHand = context == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || context == ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
         Minecraft minecraft = Minecraft.getInstance();
         poseStack.pushPose();
         poseStack.translate(0.5F, 0.5F, 0.5F);
         minecraft.getItemRenderer()
            .render(stack, context, leftHand, poseStack, bufferSource, packedLight, packedOverlay, minecraft.getModelManager().getModel(ICON_MODEL));
         poseStack.popPose();
      }
   }
}
