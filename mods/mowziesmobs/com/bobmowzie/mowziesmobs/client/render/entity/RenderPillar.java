package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelPillar;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoModel;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityGeomancyBase;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityPillar;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.cache.object.GeoCube;
import software.bernie.geckolib.util.RenderUtil;

public class RenderPillar extends RenderGeomancyBase<EntityPillar> {
   private static final ResourceLocation TEXTURE_DIRT = ResourceLocation.withDefaultNamespace("textures/block/dirt.png");

   public RenderPillar(Context mgr) {
      super(mgr, new ModelPillar());
   }

   public ResourceLocation getTextureLocation(EntityPillar entity) {
      return TEXTURE_DIRT;
   }

   public void preRender(
      PoseStack poseStack,
      EntityPillar animatable,
      BakedGeoModel model,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int color
   ) {
      super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
      EntityGeomancyBase.GeomancyTier tier = this.getEntity().getTier();
      MowzieGeoModel<EntityPillar> mowzieModel = (MowzieGeoModel<EntityPillar>)this.getGeoModel();
      MowzieGeoBone tier1 = mowzieModel.getMowzieBone("tier1");
      MowzieGeoBone tier2 = mowzieModel.getMowzieBone("tier2");
      MowzieGeoBone tier3 = mowzieModel.getMowzieBone("tier3");
      MowzieGeoBone tier4 = mowzieModel.getMowzieBone("tier4");
      MowzieGeoBone tier5 = mowzieModel.getMowzieBone("tier5");
      tier1.setChildrenHidden(true);
      tier2.setChildrenHidden(true);
      tier3.setChildrenHidden(true);
      tier4.setChildrenHidden(true);
      tier5.setChildrenHidden(true);
      if (tier == EntityGeomancyBase.GeomancyTier.NONE) {
         tier1.setChildrenHidden(false);
      } else if (tier == EntityGeomancyBase.GeomancyTier.SMALL) {
         tier2.setChildrenHidden(false);
      } else if (tier == EntityGeomancyBase.GeomancyTier.MEDIUM) {
         tier3.setChildrenHidden(false);
      } else if (tier == EntityGeomancyBase.GeomancyTier.LARGE) {
         tier4.setChildrenHidden(false);
      } else {
         tier5.setChildrenHidden(false);
      }
   }

   public void render(EntityPillar pillar, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      float height = pillar.prevPrevHeight + (pillar.prevHeight - pillar.prevPrevHeight) * partialTick;
      poseStack.translate(0.0F, height - 0.5F, 0.0F);
      int numRenders = (int)Math.ceil(pillar.getHeight()) + 1;

      for (int i = 0; i < numRenders; i++) {
         poseStack.translate(0.0F, -1.0F, 0.0F);
         super.render(pillar, entityYaw, partialTick, poseStack, bufferSource, packedLight);
      }
   }

   public void renderRecursively(
      PoseStack poseStack,
      EntityPillar animatable,
      GeoBone bone,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int color
   ) {
      poseStack.pushPose();
      RenderUtil.translateMatrixToBone(poseStack, bone);
      RenderUtil.translateToPivotPoint(poseStack, bone);
      RenderUtil.rotateMatrixAroundBone(poseStack, bone);
      RenderUtil.scaleMatrixForBone(poseStack, bone);
      this.renderCubesOfBone(poseStack, bone, buffer, packedLight, packedOverlay, color);
      this.renderChildBones(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
      poseStack.popPose();
   }

   public void renderCube(PoseStack poseStack, GeoCube cube, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
      poseStack.translate(-0.5, 0.5, -0.5);
      BlockRenderDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRenderer();
      blockrendererdispatcher.renderSingleBlock(this.getEntity().getBlock(), poseStack, this.getCurrentMultiBufferSource(), packedLight, packedOverlay);
   }
}
