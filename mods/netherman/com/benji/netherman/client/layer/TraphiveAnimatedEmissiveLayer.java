package com.benji.netherman.client.layer;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.block.entity.TraphiveBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class TraphiveAnimatedEmissiveLayer extends GeoRenderLayer<TraphiveBlockEntity> {
   private static final ResourceLocation[] FRAMES = new ResourceLocation[7];

   public TraphiveAnimatedEmissiveLayer(GeoBlockRenderer<TraphiveBlockEntity> entityRendererIn) {
      super(entityRendererIn);
   }

   public void render(
      PoseStack poseStack,
      TraphiveBlockEntity animatable,
      BakedGeoModel bakedModel,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      if (animatable.getLevel() != null) {
         float time = (float)animatable.getLevel().getGameTime() + partialTick;
         float speed = 3.0F;
         float exactFrame = time / speed;
         int currentFrame = (int)Math.floor(exactFrame) % 7;
         int nextFrame = (currentFrame + 1) % 7;
         float blendFactor = exactFrame - (float)Math.floor(exactFrame);
         RenderType currentRenderType = RenderType.eyes(FRAMES[currentFrame]);
         VertexConsumer currentBuffer = bufferSource.getBuffer(currentRenderType);
         int currentAlpha = (int)((1.0F - blendFactor) * 255.0F);
         int currentColor = currentAlpha << 24 | 16777215;
         this.getRenderer()
            .reRender(bakedModel, poseStack, bufferSource, animatable, currentRenderType, currentBuffer, partialTick, 15728880, packedOverlay, currentColor);
         RenderType nextRenderType = RenderType.eyes(FRAMES[nextFrame]);
         VertexConsumer nextBuffer = bufferSource.getBuffer(nextRenderType);
         int nextAlpha = (int)(blendFactor * 255.0F);
         int nextColor = nextAlpha << 24 | 16777215;
         this.getRenderer()
            .reRender(bakedModel, poseStack, bufferSource, animatable, nextRenderType, nextBuffer, partialTick, 15728880, packedOverlay, nextColor);
      }
   }

   static {
      for (int i = 0; i < 7; i++) {
         FRAMES[i] = NetherExp.location("textures/block/traphive_emissive_" + i + ".png");
      }
   }
}
