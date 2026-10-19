package io.github.Memoires.mysticism.client.renderer.entity.skill;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.Memoires.mysticism.client.model.ProviderHaloModel;
import io.github.Memoires.mysticism.entity.skill.ProviderHaloEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ProviderHaloRenderer extends GeoEntityRenderer<ProviderHaloEntity> {
   public ProviderHaloRenderer(Context renderManager) {
      super(renderManager, new ProviderHaloModel());
      this.shadowRadius = 0.0F;
   }

   public void scaleModelForRender(
      float widthScale,
      float heightScale,
      PoseStack poseStack,
      ProviderHaloEntity animatable,
      BakedGeoModel model,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      poseStack.scale(2.0F, 2.0F, 2.0F);
   }

   public void preRender(
      PoseStack poseStack,
      ProviderHaloEntity e,
      BakedGeoModel model,
      @Nullable MultiBufferSource bs,
      @Nullable VertexConsumer buf,
      boolean isReRender,
      float pt,
      int light,
      int overlay,
      int color
   ) {
      super.preRender(poseStack, e, model, bs, buf, isReRender, pt, light, overlay, color);
      poseStack.mulPose(Axis.YP.rotationDegrees(e.getSpawnYaw()));
   }
}
