package com.mortal_cultivation.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mortal_cultivation.entity.SpiritMonkeyEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public final class SpiritMonkeyHeldItemLayer extends GeoRenderLayer<SpiritMonkeyEntity> {
   public SpiritMonkeyHeldItemLayer(GeoRenderer<SpiritMonkeyEntity> renderer) {
      super(renderer);
   }

   public void renderForBone(
      PoseStack poseStack,
      SpiritMonkeyEntity monkey,
      GeoBone bone,
      RenderType renderType,
      MultiBufferSource buffers,
      VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      ItemStack heldItem = monkey.getMainHandItem();
      if (bone.getName().equals("forearm_right") && !heldItem.isEmpty() && !monkey.isInvisible()) {
         poseStack.pushPose();
         poseStack.translate(bone.getPivotX() / 16.0F, (bone.getPivotY() - 5.0F) / 16.0F, (bone.getPivotZ() - 1.9F) / 16.0F);
         poseStack.mulPose(Axis.YP.rotationDegrees(15.0F));
         poseStack.scale(0.42F, 0.42F, 0.42F);
         Minecraft.getInstance()
            .getItemRenderer()
            .renderStatic(monkey, heldItem, ItemDisplayContext.NONE, false, poseStack, buffers, monkey.level(), packedLight, packedOverlay, monkey.getId());
         poseStack.popPose();
         buffers.getBuffer(renderType);
      }
   }
}
