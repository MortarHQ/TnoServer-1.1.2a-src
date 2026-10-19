package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public final class TuffGolemHeldItemFeatureRenderer<T extends TuffGolemEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private final ItemInHandRenderer heldItemRenderer;

   public TuffGolemHeldItemFeatureRenderer(RenderLayerParent<T, M> context, ItemInHandRenderer heldItemRenderer) {
      super(context);
      this.heldItemRenderer = heldItemRenderer;
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource bufferSource,
      int packedLight,
      TuffGolemEntity tuffGolem,
      float limbAngle,
      float limbDistance,
      float tickDelta,
      float animationProgress,
      float headYaw,
      float headPitch
   ) {
      if (!tuffGolem.isDeadOrDying() && tuffGolem.isHoldingItem()) {
         ItemStack itemStack = tuffGolem.getItemBySlot(EquipmentSlot.MAINHAND);
         float yItemOffset = 0.4F;
         float levitationOffset = Mth.sin((tuffGolem.tickCount + tickDelta) / 10.0F + (float) Math.PI) * 0.05F + 0.05F;
         float yOffset = levitationOffset + (1.0F - yItemOffset * 0.7F);
         float rotationAngle = (float)Math.toDegrees(animationProgress * 0.05F % (float) (Math.PI * 2));
         poseStack.pushPose();
         poseStack.translate(0.0, yOffset, -0.575);
         poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
         poseStack.mulPose(Axis.YP.rotationDegrees(rotationAngle));
         this.heldItemRenderer.renderItem(tuffGolem, itemStack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight);
         poseStack.popPose();
      }
   }
}
