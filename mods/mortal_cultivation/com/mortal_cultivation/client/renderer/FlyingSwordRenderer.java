package com.mortal_cultivation.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mortal_cultivation.entity.FlyingSwordEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class FlyingSwordRenderer extends EntityRenderer<FlyingSwordEntity> {
   private final ItemRenderer itemRenderer;

   public FlyingSwordRenderer(Context context) {
      super(context);
      this.itemRenderer = context.getItemRenderer();
      this.shadowRadius = 0.2F;
   }

   public void render(FlyingSwordEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      ItemStack sword = entity.getSwordStack();
      if (!sword.isEmpty()) {
         boolean oversizedSword = entity.isOversizedSword();
         ItemDisplayContext displayContext = oversizedSword ? ItemDisplayContext.NONE : ItemDisplayContext.GUI;
         float modelScale = oversizedSword ? 2.5F : 1.3F;
         poseStack.pushPose();
         poseStack.translate(0.0, 0.08, 0.0);
         poseStack.mulPose(Axis.YP.rotationDegrees(-Mth.rotLerp(partialTick, entity.yRotO, entity.getYRot())));
         poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot())));
         poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
         poseStack.mulPose(Axis.ZP.rotationDegrees(45.0F));
         poseStack.scale(modelScale, modelScale, modelScale);
         this.itemRenderer.renderStatic(sword, displayContext, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.level(), entity.getId());
         poseStack.popPose();
         super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
      }
   }

   public ResourceLocation getTextureLocation(FlyingSwordEntity entity) {
      return InventoryMenu.BLOCK_ATLAS;
   }
}
