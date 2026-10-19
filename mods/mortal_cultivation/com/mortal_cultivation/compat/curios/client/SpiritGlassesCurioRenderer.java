package com.mortal_cultivation.compat.curios.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public final class SpiritGlassesCurioRenderer implements ICurioRenderer {
   public <T extends LivingEntity, M extends EntityModel<T>> void render(
      ItemStack stack,
      SlotContext slotContext,
      PoseStack poseStack,
      RenderLayerParent<T, M> renderLayerParent,
      MultiBufferSource bufferSource,
      int packedLight,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (renderLayerParent.getModel() instanceof HeadedModel headedModel) {
         LivingEntity var16 = slotContext.entity();
         poseStack.pushPose();
         boolean villagerHead = var16 instanceof Villager || var16 instanceof ZombieVillager;
         if (var16.isBaby() && !(var16 instanceof Villager)) {
            poseStack.translate(0.0F, 0.03125F, 0.0F);
            poseStack.scale(0.7F, 0.7F, 0.7F);
            poseStack.translate(0.0F, 1.0F, 0.0F);
         }

         headedModel.getHead().translateAndRotate(poseStack);
         CustomHeadLayer.translateToHead(poseStack, villagerHead);
         Minecraft.getInstance()
            .getEntityRenderDispatcher()
            .getItemInHandRenderer()
            .renderItem(var16, stack, ItemDisplayContext.HEAD, false, poseStack, bufferSource, packedLight);
         poseStack.popPose();
      }
   }
}
