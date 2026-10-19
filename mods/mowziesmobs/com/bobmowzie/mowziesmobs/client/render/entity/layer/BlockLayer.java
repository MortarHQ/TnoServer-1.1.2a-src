package com.bobmowzie.mowziesmobs.client.render.entity.layer;

import com.bobmowzie.mowziesmobs.client.model.tools.BlockModelRenderer;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import com.ilexiconn.llibrary.client.model.tools.BasicModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class BlockLayer<T extends Entity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private final AdvancedModelRenderer root;

   public BlockLayer(RenderLayerParent<T, M> renderer, AdvancedModelRenderer root) {
      super(renderer);
      this.root = root;
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
      matrixStackIn.pushPose();
      int packedOverlay = 0;
      if (entity instanceof LivingEntity) {
         LivingEntityRenderer.getOverlayCoords((LivingEntity)entity, 0.0F);
      }

      BlockRenderDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRenderer();
      processModelRenderer(this.root, matrixStackIn, bufferIn, packedLightIn, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F, blockrendererdispatcher);
      matrixStackIn.popPose();
   }

   public static void processModelRenderer(
      AdvancedModelRenderer modelRenderer,
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      int packedOverlayIn,
      float red,
      float green,
      float blue,
      float alpha,
      BlockRenderDispatcher dispatcher
   ) {
      if (modelRenderer.showModel && (modelRenderer instanceof BlockModelRenderer || !modelRenderer.childModels.isEmpty())) {
         matrixStackIn.pushPose();
         modelRenderer.translateRotate(matrixStackIn);
         if (!modelRenderer.isHidden() && modelRenderer instanceof BlockModelRenderer blockModelRenderer) {
            dispatcher.renderSingleBlock(blockModelRenderer.getBlockState(), matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
         }

         ObjectListIterator var12 = modelRenderer.childModels.iterator();

         while (var12.hasNext()) {
            BasicModelRenderer child = (BasicModelRenderer)var12.next();
            if (child instanceof AdvancedModelRenderer) {
               processModelRenderer((AdvancedModelRenderer)child, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha, dispatcher);
            }
         }

         matrixStackIn.popPose();
      }
   }
}
