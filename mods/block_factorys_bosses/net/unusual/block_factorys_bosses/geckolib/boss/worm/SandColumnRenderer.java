package net.unusual.block_factorys_bosses.geckolib.boss.worm;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.unusual.block_factorys_bosses.entity.SandColumnEntity;
import net.unusual.block_factorys_bosses.geckolib.DangerZonesRenderLayer;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SandColumnRenderer extends GeoEntityRenderer<SandColumnEntity> {
   public SandColumnRenderer(Context context) {
      super(context, new SandColumnModel());
      this.addRenderLayer(new DangerZonesRenderLayer(this));
   }

   public void actuallyRender(
      PoseStack poseStack,
      SandColumnEntity animatable,
      BakedGeoModel model,
      @Nullable RenderType renderType,
      MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      if (animatable.isErupting()) {
         super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
      }
   }
}
