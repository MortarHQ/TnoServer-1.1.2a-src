package com.bobmowzie.mowziesmobs.client.render.entity.layer;

import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;
import software.bernie.geckolib.util.RenderUtil;

public class GeckoItemlayer<T extends MowzieGeckoEntity> extends BlockAndItemGeoLayer<T> {
   protected Matrix4f dispatchedMat = new Matrix4f();
   protected Matrix4f renderEarlyMat = new Matrix4f();
   private MowzieGeckoEntity entity;
   private String boneName;
   protected ItemStack renderedItem;

   public GeckoItemlayer(GeoRenderer<T> rendererIn, String boneName, ItemStack renderedItem) {
      super(rendererIn);
      this.boneName = boneName;
      this.renderedItem = renderedItem;
   }

   protected ItemStack getStackForBone(GeoBone bone, T animatable) {
      return !bone.isHidden() && bone.getName().equals(this.boneName) ? this.renderedItem : null;
   }

   protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, MowzieGeckoEntity animatable) {
      String var4 = bone.getName();
      byte var5 = -1;
      var4.hashCode();
      switch (var5) {
         default:
            return ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
      }
   }

   public void renderForBone(
      PoseStack poseStack,
      T animatable,
      GeoBone bone,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      ItemStack stack = this.getStackForBone(bone, animatable);
      BlockState blockState = this.getBlockForBone(bone, animatable);
      if (stack != null || blockState != null) {
         poseStack.pushPose();
         RenderUtil.translateToPivotPoint(poseStack, bone);
         if (stack != null) {
            this.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
         }

         if (blockState != null) {
            this.renderBlockForBone(poseStack, bone, blockState, animatable, bufferSource, partialTick, packedLight, packedOverlay);
         }

         buffer = bufferSource.getBuffer(renderType);
         poseStack.popPose();
      }
   }

   public Vec3 getRenderOffset(MowzieGeckoEntity p_114483_, float p_114484_) {
      return Vec3.ZERO;
   }
}
