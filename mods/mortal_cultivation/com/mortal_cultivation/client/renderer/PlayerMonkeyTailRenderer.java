package com.mortal_cultivation.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mortal_cultivation.client.model.PlayerMonkeyTail;
import com.mortal_cultivation.client.model.PlayerMonkeyTailModel;
import net.minecraft.client.renderer.MultiBufferSource;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoObjectRenderer;

public final class PlayerMonkeyTailRenderer extends GeoObjectRenderer<PlayerMonkeyTail> {
   public PlayerMonkeyTailRenderer() {
      super(new PlayerMonkeyTailModel());
   }

   public long getInstanceId(PlayerMonkeyTail tail) {
      return tail.getInstanceId();
   }

   public void preRender(
      PoseStack poseStack,
      PlayerMonkeyTail tail,
      BakedGeoModel model,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      super.preRender(poseStack, tail, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
      poseStack.translate(-0.5F, -0.51F, -0.5F);
   }
}
