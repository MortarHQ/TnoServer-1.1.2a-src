package de.ellpeck.naturesaura.blocks.tiles.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityOfferingTable;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class RenderOfferingTable implements BlockEntityRenderer<BlockEntityOfferingTable> {
   private final Random rand = new Random();

   public RenderOfferingTable(Context context) {
   }

   public void render(
      BlockEntityOfferingTable tileEntityOfferingTable,
      float v,
      PoseStack matrixStack,
      MultiBufferSource iRenderTypeBuffer,
      int combinedLightIn,
      int combinedOverlayIn
   ) {
      ItemStack stack = tileEntityOfferingTable.items.getStackInSlot(0);
      if (!stack.isEmpty()) {
         this.rand.setSeed(Item.getId(stack.getItem()) + stack.getDamageValue());
         int amount = Mth.ceil(stack.getCount() / 2.0F);

         for (int i = 0; i < amount; i++) {
            matrixStack.pushPose();
            Item item = stack.getItem();
            float scale;
            float yOff;
            if (item instanceof BlockItem) {
               scale = 0.5F;
               yOff = 0.08F;
            } else {
               scale = 0.35F;
               yOff = 0.0F;
            }

            matrixStack.translate(0.35F + this.rand.nextFloat() * 0.3F, 0.9F + yOff + i * 0.001F, 0.35F + this.rand.nextFloat() * 0.3F);
            matrixStack.mulPose(Axis.YP.rotationDegrees(this.rand.nextFloat() * 360.0F));
            matrixStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            matrixStack.scale(scale, scale, scale);
            Minecraft.getInstance()
               .getItemRenderer()
               .renderStatic(
                  stack, ItemDisplayContext.GROUND, combinedLightIn, combinedOverlayIn, matrixStack, iRenderTypeBuffer, tileEntityOfferingTable.getLevel(), 0
               );
            matrixStack.popPose();
         }
      }
   }
}
