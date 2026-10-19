package de.ellpeck.naturesaura.blocks.tiles.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityWoodStand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class RenderWoodStand implements BlockEntityRenderer<BlockEntityWoodStand> {
   public RenderWoodStand(Context context) {
   }

   public void render(BlockEntityWoodStand tileEntityWoodStand, float v, PoseStack matrixStack, MultiBufferSource iRenderTypeBuffer, int i, int i1) {
      ItemStack stack = tileEntityWoodStand.items.getStackInSlot(0);
      if (!stack.isEmpty()) {
         matrixStack.pushPose();
         if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock().defaultBlockState().isSolid()) {
            matrixStack.translate(0.5F, 0.755F, 0.5F);
            float scale = 0.95F;
            matrixStack.scale(scale, scale, scale);
         } else {
            matrixStack.translate(0.5F, 0.825F, 0.4F);
            float scale = 0.75F;
            matrixStack.scale(scale, scale, scale);
            matrixStack.mulPose(Axis.XP.rotationDegrees(90.0F));
         }

         Minecraft.getInstance()
            .getItemRenderer()
            .renderStatic(stack, ItemDisplayContext.GROUND, i, i1, matrixStack, iRenderTypeBuffer, tileEntityWoodStand.getLevel(), 0);
         matrixStack.popPose();
      }
   }
}
