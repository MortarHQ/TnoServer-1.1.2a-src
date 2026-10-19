package com.chronodawn.client.renderer;

import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.projectiles.GearProjectileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GearProjectileRenderer extends EntityRenderer<GearProjectileEntity> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/gear_projectile.png");
   private final ItemRenderer itemRenderer;

   public GearProjectileRenderer(Context context) {
      super(context);
      this.itemRenderer = context.getItemRenderer();
   }

   public void render(GearProjectileEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      poseStack.pushPose();
      poseStack.scale(0.5F, 0.5F, 0.5F);
      float rotation = entity.tickCount + partialTick;
      poseStack.mulPose(Axis.YP.rotationDegrees(rotation * 20.0F));
      poseStack.mulPose(Axis.ZP.rotationDegrees(rotation * 15.0F));
      ItemStack stack = new ItemStack(Items.IRON_INGOT);
      this.itemRenderer
         .renderStatic(stack, ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.level(), entity.getId());
      poseStack.popPose();
      super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
   }

   public ResourceLocation getTextureLocation(GearProjectileEntity entity) {
      return TEXTURE;
   }
}
