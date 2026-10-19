package it.hurts.sskirillss.relics.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.sskirillss.relics.entities.BlockSimulationEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;

public class BlockSimulationRenderer extends EntityRenderer<BlockSimulationEntity> {
   public BlockSimulationRenderer(Context pContext) {
      super(pContext);
   }

   public void render(BlockSimulationEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
      BlockState blockstate = pEntity.getBlockState();
      if (blockstate != null) {
         if (blockstate.getRenderShape() == RenderShape.MODEL) {
            Level level = pEntity.getCommandSenderWorld();
            pMatrixStack.pushPose();
            BlockPos blockpos = new BlockPos((int)pEntity.getX(), (int)pEntity.getBoundingBox().maxY, (int)pEntity.getZ());
            pMatrixStack.translate(-0.5, 0.0, -0.5);
            BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
            BakedModel model = dispatcher.getBlockModel(blockstate);

            for (RenderType renderType : model.getRenderTypes(blockstate, RandomSource.create(), ModelData.EMPTY)) {
               dispatcher.getModelRenderer()
                  .tesselateBlock(
                     level,
                     model,
                     blockstate,
                     blockpos,
                     pMatrixStack,
                     pBuffer.getBuffer(renderType),
                     false,
                     RandomSource.create(),
                     blockstate.getSeed(blockpos),
                     OverlayTexture.NO_OVERLAY,
                     ModelData.EMPTY,
                     renderType
                  );
            }

            pMatrixStack.popPose();
            super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
         }
      }
   }

   public ResourceLocation getTextureLocation(BlockSimulationEntity pEntity) {
      return TextureAtlas.LOCATION_BLOCKS;
   }
}
