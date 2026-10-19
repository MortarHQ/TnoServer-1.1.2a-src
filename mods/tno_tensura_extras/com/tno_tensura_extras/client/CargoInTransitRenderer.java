package com.tno_tensura_extras.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.tno_tensura_extras.block.entity.CargoInTransitBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;

public class CargoInTransitRenderer implements BlockEntityRenderer<CargoInTransitBlockEntity> {
   private final BlockRenderDispatcher dispatcher;

   public CargoInTransitRenderer(Context context) {
      this.dispatcher = context.getBlockRenderDispatcher();
   }

   public void render(CargoInTransitBlockEntity cargo, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
      BlockState state = cargo.getCargoState();
      if (!state.isAir() && state.getRenderShape() != RenderShape.INVISIBLE && cargo.isVisible()) {
         poseStack.pushPose();
         poseStack.translate(0.0, cargo.getOffset(partialTick), 0.0);
         if (state.getRenderShape() == RenderShape.ENTITYBLOCK_ANIMATED && state.hasProperty(ChestBlock.FACING)) {
            Direction facing = (Direction)state.getValue(ChestBlock.FACING);
            poseStack.translate(0.5, 0.0, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(-facing.toYRot()));
            poseStack.translate(-0.5, 0.0, -0.5);
         }

         this.dispatcher.renderSingleBlock(state, poseStack, buffer, packedLight, packedOverlay, ModelData.EMPTY, null);
         poseStack.popPose();
      }
   }
}
