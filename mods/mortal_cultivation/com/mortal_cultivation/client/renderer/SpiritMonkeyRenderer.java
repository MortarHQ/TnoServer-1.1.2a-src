package com.mortal_cultivation.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mortal_cultivation.client.model.SpiritMonkeyModel;
import com.mortal_cultivation.entity.SpiritMonkeyEntity;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public final class SpiritMonkeyRenderer extends GeoEntityRenderer<SpiritMonkeyEntity> {
   public SpiritMonkeyRenderer(Context context) {
      super(context, new SpiritMonkeyModel());
      this.shadowRadius = 0.35F;
      this.addRenderLayer(new SpiritMonkeyHeldItemLayer(this));
   }

   protected float getShadowRadius(SpiritMonkeyEntity monkey) {
      return this.shadowRadius * monkey.getAgeScale() * monkey.getScale();
   }

   public void preRender(
      PoseStack poseStack,
      SpiritMonkeyEntity monkey,
      BakedGeoModel model,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      super.preRender(poseStack, monkey, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
      float ageScale = monkey.getAgeScale();
      poseStack.scale(ageScale, ageScale, ageScale);
   }
}
