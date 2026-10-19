package net.unusual.block_factorys_bosses.geckolib.boss.kraken;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class KrakenRenderer extends GeoEntityRenderer<KrakenEntity> {
   public KrakenRenderer(Context context) {
      super(context, new KrakenModel());
   }

   public void preRender(
      PoseStack poseStack,
      KrakenEntity animatable,
      BakedGeoModel model,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      if (!isReRender) {
         ((GeoBone)model.getBone("root").orElseThrow()).setHidden(animatable.isHidden());
      }

      super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
   }
}
