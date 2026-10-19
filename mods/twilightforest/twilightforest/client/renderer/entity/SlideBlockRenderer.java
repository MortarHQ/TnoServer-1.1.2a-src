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
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.RenderTypeHelper;
import net.neoforged.neoforge.client.model.data.ModelData;
import twilightforest.entity.SlideBlock;

public class SlideBlockRenderer extends EntityRenderer<SlideBlock> {
   public SlideBlockRenderer(Context context) {
      super(context);
      this.shadowRadius = 0.0F;
   }

   public void render(SlideBlock entity, float yaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
      BlockState blockstate = entity.getBlockState();
      if (blockstate.getRenderShape() == RenderShape.MODEL) {
         Level level = entity.level();
         if (blockstate != level.getBlockState(entity.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
            stack.pushPose();
            BlockPos blockpos = BlockPos.containing(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());
            if (blockstate.getProperties().contains(RotatedPillarBlock.AXIS)) {
               Axis axis = (Axis)blockstate.getValue(RotatedPillarBlock.AXIS);
               float angle = (entity.tickCount + partialTicks) * 60.0F;
               stack.translate(0.0, 0.5, 0.0);
               if (axis == Axis.Y) {
                  stack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(angle));
               } else if (axis == Axis.X) {
                  stack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(angle));
               } else if (axis == Axis.Z) {
                  stack.mulPose(com.mojang.math.Axis.ZP.rotationDegrees(angle));
               }

               stack.translate(-0.5, -0.5, -0.5);
            }

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
                     buffer.getBuffer(RenderTypeHelper.getMovingBlockRenderType(renderType)),
                     false,
                     RandomSource.create(),
                     blockstate.getSeed(entity.blockPosition()),
                     OverlayTexture.NO_OVERLAY,
                     ModelData.EMPTY,
                     renderType
                  );
            }

            stack.popPose();
            super.render(entity, yaw, partialTicks, stack, buffer, light);
         }
      }
   }

   public ResourceLocation getTextureLocation(SlideBlock entity) {
      return InventoryMenu.BLOCK_ATLAS;
   }
}
