package net.mcreator.rpgstylemorebosses.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mcreator.rpgstylemorebosses.entity.BlockBarrierEntity;
import net.mcreator.rpgstylemorebosses.entity.layer.BlockBarrierLayer;
import net.mcreator.rpgstylemorebosses.entity.model.BlockBarrierModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BlockBarrierRenderer extends GeoEntityRenderer<BlockBarrierEntity> {
   public BlockBarrierRenderer(Context renderManager) {
      super(renderManager, new BlockBarrierModel());
      this.shadowRadius = 0.0F;
      this.addRenderLayer(new BlockBarrierLayer(this));
   }

   public RenderType getRenderType(BlockBarrierEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
      return RenderType.entityTranslucent(this.getTextureLocation(animatable));
   }

   public void preRender(
      PoseStack poseStack,
      BlockBarrierEntity entity,
      BakedGeoModel model,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int color
   ) {
      float scale = 1.0F;
      this.scaleHeight = scale;
      this.scaleWidth = scale;
      super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
   }
}
