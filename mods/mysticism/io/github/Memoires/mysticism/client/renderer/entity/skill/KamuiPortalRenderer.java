package io.github.Memoires.mysticism.client.renderer.entity.skill;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.Memoires.mysticism.client.model.KamuiPortalModel;
import io.github.Memoires.mysticism.entity.skill.KamuiPortalEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class KamuiPortalRenderer extends GeoEntityRenderer<KamuiPortalEntity> {
   public KamuiPortalRenderer(Context renderManager) {
      super(renderManager, new KamuiPortalModel());
   }

   protected float getShadowRadius(KamuiPortalEntity entity) {
      return 0.0F;
   }

   protected int getBlockLightLevel(KamuiPortalEntity blaze, BlockPos blockPos) {
      return 15;
   }

   public void preRender(
      PoseStack poseStack,
      KamuiPortalEntity animatable,
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
      float scale = animatable.getSize() / 5.0F;
      this.scaleModelForRender(
         this.scaleWidth * scale, this.scaleHeight * scale, poseStack, animatable, model, isReRender, partialTick, 15728880, packedOverlay
      );
      poseStack.mulPose(Axis.YP.rotationDegrees(-animatable.getYRot()));
   }
}
