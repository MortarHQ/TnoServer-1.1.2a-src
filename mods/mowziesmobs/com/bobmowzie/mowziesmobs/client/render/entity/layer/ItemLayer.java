package com.bobmowzie.mowziesmobs.client.render.entity.layer;

import com.bobmowzie.mowziesmobs.client.render.MowzieRenderUtils;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ItemLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private AdvancedModelRenderer modelRenderer;
   private ItemStack itemstack;
   private ItemDisplayContext transformType;

   public ItemLayer(RenderLayerParent<T, M> renderer, AdvancedModelRenderer modelRenderer, ItemStack itemstack, ItemDisplayContext transformType) {
      super(renderer);
      this.itemstack = itemstack;
      this.modelRenderer = modelRenderer;
      this.transformType = transformType;
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      T entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (this.modelRenderer.showModel && !this.modelRenderer.isHidden()) {
         matrixStackIn.pushPose();
         MowzieRenderUtils.matrixStackFromModel(matrixStackIn, this.getModelRenderer());
         Minecraft.getInstance()
            .getEntityRenderDispatcher()
            .getItemInHandRenderer()
            .renderItem(entitylivingbaseIn, this.getItemstack(), this.transformType, false, matrixStackIn, bufferIn, packedLightIn);
         matrixStackIn.popPose();
      }
   }

   public ItemStack getItemstack() {
      return this.itemstack;
   }

   public void setItemstack(ItemStack itemstack) {
      this.itemstack = itemstack;
   }

   public AdvancedModelRenderer getModelRenderer() {
      return this.modelRenderer;
   }

   public void setModelRenderer(AdvancedModelRenderer modelRenderer) {
      this.modelRenderer = modelRenderer;
   }
}
