package com.mortal_cultivation.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mortal_cultivation.client.model.ThunderTalismanModel;
import com.mortal_cultivation.entity.ThunderTalismanEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ThunderTalismanRenderer extends EntityRenderer<ThunderTalismanEntity> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/entity/the_five_thunder_talism.png");
   private final ThunderTalismanModel<ThunderTalismanEntity> model;

   public ThunderTalismanRenderer(Context context) {
      super(context);
      this.model = new ThunderTalismanModel(context.bakeLayer(ThunderTalismanModel.LAYER_LOCATION));
   }

   public ResourceLocation getTextureLocation(ThunderTalismanEntity entity) {
      return TEXTURE;
   }

   public void render(ThunderTalismanEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      poseStack.pushPose();
      poseStack.scale(0.8F, 0.8F, 0.8F);
      this.model.setupAnim(entity, 0.0F, 0.0F, entity.tickCount + partialTicks, 0.0F, 0.0F);
      this.model
         .renderToBuffer(poseStack, buffer.getBuffer(this.model.renderType(this.getTextureLocation(entity))), packedLight, OverlayTexture.NO_OVERLAY, -1);
      poseStack.popPose();
      super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
   }
}
