package com.iafenvoy.iceandfire.render.block;

import com.iafenvoy.iceandfire.item.block.entity.DreadSpawnerBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BaseSpawner;

public class DreadSpawnerBlockEntityRenderer<T extends DreadSpawnerBlockEntity> implements BlockEntityRenderer<T> {
   public DreadSpawnerBlockEntityRenderer(Context context) {
   }

   public void render(
      DreadSpawnerBlockEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn
   ) {
      matrixStackIn.pushPose();
      matrixStackIn.translate(0.5, 0.0, 0.5);
      BaseSpawner spawnerLogic = tileEntityIn.getLogic();
      Entity entity = spawnerLogic.getOrCreateDisplayEntity(tileEntityIn.getLevel(), tileEntityIn.getBlockPos());
      if (entity != null) {
         float f = 0.53125F;
         float f1 = Math.max(entity.getBbWidth(), entity.getBbHeight());
         if (f1 > 1.0F) {
            f /= f1;
         }

         matrixStackIn.translate(0.0, 0.4F, 0.0);
         matrixStackIn.mulPose(Axis.YP.rotationDegrees((float)Mth.lerp(partialTicks, spawnerLogic.getoSpin(), spawnerLogic.getSpin()) * 10.0F));
         matrixStackIn.translate(0.0, -0.2F, 0.0);
         matrixStackIn.mulPose(Axis.XP.rotationDegrees(-30.0F));
         matrixStackIn.scale(f, f, f);
         Minecraft.getInstance().getEntityRenderDispatcher().render(entity, 0.0, 0.0, 0.0, 0.0F, partialTicks, matrixStackIn, bufferIn, combinedLightIn);
      }

      matrixStackIn.popPose();
   }
}
