package com.tensura_tno.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tensura_tno.client.model.RimuruPlushItemModel;
import com.tensura_tno.item.RimuruPlushItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public final class RimuruPlushItemRenderer extends GeoItemRenderer<RimuruPlushItem> {
   private final float itemScale;
   private final double guiCentreY;
   private final double guiCentreZ;
   private final double heldCentreY;
   private final double centreX;

   public RimuruPlushItemRenderer() {
      this(new RimuruPlushItemModel(), 0.55F, 8.625, 0.3, 12.325, 0.0);
   }

   public RimuruPlushItemRenderer(RimuruPlushItemModel model, float scale, double guiCentreY, double guiCentreZ, double heldCentreY, double centreX) {
      super(model);
      this.itemScale = scale;
      this.guiCentreY = guiCentreY;
      this.guiCentreZ = guiCentreZ;
      this.heldCentreY = heldCentreY;
      this.centreX = centreX;
   }

   public void preRender(
      PoseStack poseStack,
      RimuruPlushItem item,
      BakedGeoModel model,
      MultiBufferSource buffers,
      VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      super.preRender(poseStack, item, model, buffers, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
      if (!isReRender) {
         poseStack.translate(0.0, -0.01, 0.0);
         poseStack.scale(this.itemScale, this.itemScale, this.itemScale);
         poseStack.translate(-this.centreX / 16.0, 0.0, 0.0);
         if (this.renderPerspective == ItemDisplayContext.GUI) {
            poseStack.translate(0.0, -this.guiCentreY / 16.0, -this.guiCentreZ / 16.0);
         } else {
            poseStack.translate(0.0, -this.heldCentreY / 16.0, 0.0);
         }
      }
   }
}
