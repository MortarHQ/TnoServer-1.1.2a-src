package com.iafenvoy.iceandfire.render.entity.feature;

import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import com.iafenvoy.uranus.client.model.AdvancedModelBox;
import com.iafenvoy.uranus.client.model.TabulaModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.Optional;
import java.util.stream.StreamSupport;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class DragonBannerFeatureRenderer<T extends DragonBaseEntity> extends RenderLayer<T, TabulaModel<T>> {
   private final RenderLayerParent<T, TabulaModel<T>> renderer;

   public DragonBannerFeatureRenderer(RenderLayerParent<T, TabulaModel<T>> renderIn) {
      super(renderIn);
      this.renderer = renderIn;
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      T entity,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      ItemStack itemstack = entity.getItemInHand(InteractionHand.OFF_HAND);
      matrixStackIn.pushPose();
      if (!itemstack.isEmpty() && itemstack.getItem() instanceof BannerItem) {
         float f = entity.getRenderSize() / 3.0F;
         float f2 = 1.0F / f;
         matrixStackIn.pushPose();
         Optional<AdvancedModelBox> optional = StreamSupport.<AdvancedModelBox>stream(
               ((TabulaModel)this.renderer.getModel()).getAllParts().spliterator(), false
            )
            .filter(cube -> cube.boxName.equals("BodyUpper"))
            .findFirst();
         optional.ifPresent(box -> this.postRender(box, matrixStackIn));
         matrixStackIn.translate(0.0F, -0.2F, 0.4F);
         matrixStackIn.mulPose(Axis.XP.rotationDegrees(180.0F));
         matrixStackIn.pushPose();
         matrixStackIn.scale(f2, f2, f2);
         Minecraft.getInstance()
            .getItemRenderer()
            .renderStatic(
               itemstack, ItemDisplayContext.NONE, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn, Minecraft.getInstance().level, 0
            );
         matrixStackIn.popPose();
         matrixStackIn.popPose();
      }

      matrixStackIn.popPose();
   }

   protected void postRender(AdvancedModelBox renderer, PoseStack matrixStackIn) {
      if (renderer.rotateAngleX != 0.0F || renderer.rotateAngleY != 0.0F || renderer.rotateAngleZ != 0.0F) {
         matrixStackIn.translate(renderer.rotationPointX * 0.0625F, renderer.rotationPointY * 0.0625F, renderer.rotationPointZ * 0.0625F);
         if (renderer.rotateAngleZ != 0.0F) {
            matrixStackIn.mulPose(Axis.ZP.rotation(renderer.rotateAngleZ));
         }

         if (renderer.rotateAngleY != 0.0F) {
            matrixStackIn.mulPose(Axis.YP.rotation(renderer.rotateAngleY));
         }

         if (renderer.rotateAngleX != 0.0F) {
            matrixStackIn.mulPose(Axis.XP.rotation(renderer.rotateAngleX));
         }
      } else if (renderer.rotationPointX != 0.0F || renderer.rotationPointY != 0.0F || renderer.offsetZ != 0.0F) {
         matrixStackIn.translate(renderer.rotationPointX * 0.0625F, renderer.rotationPointY * 0.0625F, renderer.rotationPointZ * 0.0625F);
      }
   }
}
