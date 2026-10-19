package it.hurts.sskirillss.relics.client.renderer.tiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.hurts.sskirillss.relics.tiles.ResearchingTableTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;

public class ResearchingTableRenderer implements BlockEntityRenderer<ResearchingTableTile> {
   public ResearchingTableRenderer(Context context) {
   }

   public void render(
      ResearchingTableTile tileEntity, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay
   ) {
      ItemStack stack = tileEntity.getStack();
      if (stack != null && !stack.isEmpty()) {
         matrixStack.pushPose();
         matrixStack.translate(0.5F, 0.96F, 0.5F);
         matrixStack.scale(1.25F, 1.25F, 1.25F);
         matrixStack.mulPose(((Direction)tileEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING)).getRotation());
         matrixStack.mulPose(Axis.ZN.rotationDegrees(180.0F));
         matrixStack.translate(0.0F, -0.125F, 0.0F);
         ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
         itemRenderer.render(
            stack,
            ItemDisplayContext.GROUND,
            true,
            matrixStack,
            buffer,
            combinedLight,
            combinedOverlay,
            itemRenderer.getModel(stack, tileEntity.getLevel(), null, 0)
         );
         matrixStack.popPose();
      }
   }
}
