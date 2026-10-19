package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import twilightforest.entity.boss.SnowQueenIceShield;

public class SnowQueenIceShieldRenderer<T extends SnowQueenIceShield> extends EntityRenderer<T> {
   public SnowQueenIceShieldRenderer(Context context) {
      super(context);
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
      BlockState blockstate = Blocks.PACKED_ICE.defaultBlockState();
      if (blockstate.getRenderShape() == RenderShape.MODEL) {
         Level level = entity.level();
         if (blockstate.getRenderShape() != RenderShape.INVISIBLE) {
            stack.pushPose();
            BlockPos blockpos = BlockPos.containing(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());
            stack.translate(-0.5, 0.0, -0.5);
            BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
            BakedModel model = dispatcher.getBlockModel(blockstate);

            for (RenderType renderType : model.getRenderTypes(blockstate, RandomSource.create(blockstate.getSeed(entity.blockPosition())), ModelData.EMPTY)) {
               dispatcher.getModelRenderer()
                  .tesselateBlock(
                     level,
                     model,
                     blockstate,
                     blockpos,
                     stack,
                     buffer.getBuffer(renderType),
                     false,
                     RandomSource.create(),
                     blockstate.getSeed(entity.blockPosition()),
                     OverlayTexture.NO_OVERLAY,
                     ModelData.EMPTY,
                     renderType
                  );
            }

            stack.popPose();
            super.render(entity, entityYaw, partialTicks, stack, buffer, light);
         }
      }
   }

   public ResourceLocation getTextureLocation(T entity) {
      return InventoryMenu.BLOCK_ATLAS;
   }
}
