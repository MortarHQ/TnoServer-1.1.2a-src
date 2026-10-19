package com.github.b4ndithelps.tennogamenolife.client.renderer;

import com.github.b4ndithelps.tennogamenolife.client.model.SickleModel;
import com.github.b4ndithelps.tennogamenolife.client.renderer.layer.SickleBladeGlowLayer;
import com.github.b4ndithelps.tennogamenolife.item.SickleItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public final class SickleRenderer extends GeoItemRenderer<SickleItem> {
   public SickleRenderer() {
      super(new SickleModel());
      this.useAlternateGuiLighting();
      this.addRenderLayer(new SickleBladeGlowLayer(this));
   }

   public void preRender(
      PoseStack poseStack,
      SickleItem animatable,
      BakedGeoModel model,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
      if (!isReRender) {
         poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
      }
   }
}
