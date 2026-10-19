package net.jadenxgamer.netherexp.client.rendering.block_entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.jadenxgamer.netherexp.core.block.entity.DiscernmentGlassBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class DiscernmentGlassBlockRenderer implements BlockEntityRenderer<DiscernmentGlassBlockEntity> {
   private final ItemRenderer itemRenderer;

   public DiscernmentGlassBlockRenderer(Context context) {
      this.itemRenderer = context.getItemRenderer();
   }

   public void render(DiscernmentGlassBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
      ItemStack filterItem = entity.getFilterItem();
      if (!filterItem.isEmpty()) {
         float tick = entity.getLevel() != null ? (float)entity.getLevel().getGameTime() / 10.0F : 0.0F;
         poseStack.pushPose();
         poseStack.translate(0.5F, 0.5F, 0.5F);
         poseStack.scale(0.55F, 0.55F, 0.55F);
         poseStack.mulPose(Axis.YP.rotation(tick % 360.0F));
         this.itemRenderer
            .renderStatic(
               entity.getFilterItem(), ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.getLevel(), packedOverlay
            );
         poseStack.popPose();
      }
   }
}
