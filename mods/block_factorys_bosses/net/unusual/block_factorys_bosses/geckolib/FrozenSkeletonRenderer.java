package net.unusual.block_factorys_bosses.geckolib;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.boss.yeti.FrozenSkeletonEntity;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FrozenSkeletonRenderer extends GeoEntityRenderer<FrozenSkeletonEntity> {
   public FrozenSkeletonRenderer(Context context) {
      super(context, new CustomEntityGeoModel(BossesRise.prefix("frozen_skeleton")));
   }

   public RenderType getRenderType(FrozenSkeletonEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
      return RenderType.entityTranslucent(texture);
   }

   public void defaultRender(
      PoseStack poseStack,
      FrozenSkeletonEntity animatable,
      MultiBufferSource bufferSource,
      @Nullable RenderType renderType,
      @Nullable VertexConsumer buffer,
      float yaw,
      float partialTick,
      int packedLight
   ) {
      poseStack.pushPose();
      float yRot = animatable.getViewYRot(partialTick);
      if ((Integer)animatable.getEntityData().get(FrozenSkeletonEntity.DATA_HIT_ANIMATION_TIME) > 0) {
         yRot += (float)(Math.cos(animatable.tickCount * 3.25) * Math.PI * 0.4F);
      }

      poseStack.mulPose(Axis.YP.rotationDegrees(-yRot));
      super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
      poseStack.popPose();
   }
}
