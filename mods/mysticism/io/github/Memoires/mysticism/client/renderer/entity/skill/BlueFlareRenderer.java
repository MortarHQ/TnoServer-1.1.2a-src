package io.github.Memoires.mysticism.client.renderer.entity.skill;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.Memoires.mysticism.client.model.BlueFlareModel;
import io.github.Memoires.mysticism.entity.magic.field.BlueFlare;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BlueFlareRenderer extends GeoEntityRenderer<BlueFlare> {
   public BlueFlareRenderer(Context renderManager) {
      super(renderManager, new BlueFlareModel());
   }

   protected float getShadowRadius(BlueFlare entity) {
      return 0.0F;
   }

   protected int getBlockLightLevel(BlueFlare fire, BlockPos blockPos) {
      return 15;
   }

   public void preRender(
      PoseStack poseStack,
      BlueFlare animatable,
      BakedGeoModel model,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      this.entityRenderTranslations = new Matrix4f(poseStack.last().pose());
      float scale = 0.11F * animatable.getVisualSize();
      this.scaleModelForRender(
         this.scaleWidth * scale, this.scaleHeight * scale, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay
      );
   }
}
