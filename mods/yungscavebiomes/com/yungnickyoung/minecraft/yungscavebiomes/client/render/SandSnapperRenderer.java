package com.yungnickyoung.minecraft.yungscavebiomes.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.yungnickyoung.minecraft.yungscavebiomes.client.model.SandSnapperModel;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SandSnapperRenderer extends GeoEntityRenderer<SandSnapperEntity> {
   public SandSnapperRenderer(Context context) {
      super(context, new SandSnapperModel());
   }

   public RenderType getRenderType(SandSnapperEntity entity, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
      return RenderType.entityCutoutNoCull(texture);
   }

   public void render(
      SandSnapperEntity entity, float entityYaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight
   ) {
      poseStack.pushPose();
      if (entity.isSubmerged() && !entity.isDiving() && !entity.isEmerging() && !entity.isDiggingDown() && !entity.isDiggingUp()) {
         poseStack.translate(0.0F, -16.0F, 0.0F);
      }

      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
      poseStack.popPose();
   }
}
