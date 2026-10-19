package twilightforest.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.block.CandelabraBlock;
import twilightforest.block.LightableBlock;
import twilightforest.block.entity.CandelabraBlockEntity;

public class CandelabraRenderer<T extends CandelabraBlockEntity> implements BlockEntityRenderer<T> {
   public CandelabraRenderer(Context context) {
   }

   public void render(T entity, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light, int overlay) {
      BlockState state = entity.getBlockState();
      Direction direction = (Direction)state.getValue(CandelabraBlock.FACING);

      for (int i = 0; i < entity.getCandles().ordered().size(); i++) {
         stack.pushPose();
         float offset = 0.315F - 0.315F * i;
         if ((Boolean)state.getValue(CandelabraBlock.ON_WALL)) {
            stack.translate(
               -Math.abs(direction.getStepZ()) * offset + direction.getStepX() * 0.25,
               0.44F,
               -Math.abs(direction.getStepX()) * offset + direction.getStepZ() * 0.25
            );
         } else {
            stack.translate(-Math.abs(direction.getStepZ()) * offset, 0.44F, -Math.abs(direction.getStepX()) * offset);
         }

         BlockState candle = entity.getCandle(i).defaultBlockState();
         if (candle.hasProperty(CandleBlock.LIT)) {
            candle = (BlockState)candle.setValue(CandleBlock.LIT, state.getValue(CandelabraBlock.LIGHTING) == LightableBlock.Lighting.NORMAL);
         }

         if (!candle.isAir()) {
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(candle, stack, buffer, light, overlay);
         }

         stack.popPose();
      }
   }
}
