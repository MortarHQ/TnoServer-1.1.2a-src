package de.ellpeck.naturesaura.blocks.tiles.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityNatureAltar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class RenderNatureAltar implements BlockEntityRenderer<BlockEntityNatureAltar> {
   public RenderNatureAltar(Context context) {
   }

   public void render(
      BlockEntityNatureAltar tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn
   ) {
      ItemStack stack = tileEntityIn.items.getStackInSlot(0);
      if (!stack.isEmpty()) {
         matrixStackIn.pushPose();
         float time = tileEntityIn.bobTimer + partialTicks;
         float bob = (float)Math.sin(time / 10.0F) * 0.1F;
         matrixStackIn.translate(0.5F, 1.2F + bob, 0.5F);
         matrixStackIn.mulPose(Axis.YP.rotationDegrees(time * 3.0F % 360.0F));
         Minecraft.getInstance()
            .getItemRenderer()
            .renderStatic(stack, ItemDisplayContext.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, tileEntityIn.getLevel(), 0);
         matrixStackIn.popPose();
      }
   }
}
