package twilightforest.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.client.RenderTypeHelper;
import net.neoforged.neoforge.client.model.data.ModelData;
import twilightforest.block.OminousCandleBlock;
import twilightforest.block.entity.OminousCandleBlockEntity;

public class OminousCandleRenderer<T extends OminousCandleBlockEntity> implements BlockEntityRenderer<T> {
   private final Minecraft minecraft;
   protected final BlockRenderDispatcher blockRenderer;
   protected static final float WOBBLE_AMPLITUDE = 0.125F;

   public OminousCandleRenderer(Context context) {
      this.blockRenderer = context.getBlockRenderDispatcher();
      this.minecraft = Minecraft.getInstance();
   }

   public int getViewDistance() {
      return 256;
   }

   public void render(T blockEntity, float partialTick, PoseStack stack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
      stack.pushPose();
      stack.translate(0.5, 0.0, 0.5);
      stack.mulPose(Axis.YP.rotationDegrees(180.0F));
      BlockState state = blockEntity.getBlockState();
      long time = 0L;
      if (blockEntity.getLevel() != null) {
         time = blockEntity.getLevel().getGameTime();
      }

      BlockState candle = (BlockState)(state.getBlock() instanceof OminousCandleBlock candleBlock ? candleBlock.candle : Blocks.CANDLE)
         .defaultBlockState()
         .setValue(CandleBlock.LIT, true);
      int color = this.blockRenderer.blockColors.getColor(state, null, null, 0);
      float r = (color >> 16 & 0xFF) / 255.0F;
      float g = (color >> 8 & 0xFF) / 255.0F;
      float b = (color & 0xFF) / 255.0F;
      BakedModel bakedModel = this.blockRenderer.getBlockModel(candle);
      int candles = (Integer)state.getValue(OminousCandleBlock.CANDLES);
      List<Vec2> vec2s = (List<Vec2>)OminousCandleBlock.CANDLE_OFFSETS.get(candles);

      for (int i = 0; i < candles; i++) {
         stack.pushPose();
         double targetHeight = OminousCandleBlock.getCurrentY(time, partialTick, blockEntity.getBlockPos(), i + 1);
         double yHeight = this.exponentialDecay(blockEntity.getVisualHeight(i), targetHeight, 0.05);
         blockEntity.setVisualHeightScalar(yHeight, i);
         stack.translate(-vec2s.get(i).x, yHeight, -vec2s.get(i).y);

         for (RenderType rt : bakedModel.getRenderTypes(state, RandomSource.create(42L), ModelData.EMPTY)) {
            this.blockRenderer
               .getModelRenderer()
               .renderModel(
                  stack.last(),
                  buffer.getBuffer(RenderTypeHelper.getEntityRenderType(rt, false)),
                  state,
                  bakedModel,
                  r,
                  g,
                  b,
                  packedLight,
                  packedOverlay,
                  ModelData.EMPTY,
                  rt
               );
         }

         stack.popPose();
      }

      stack.popPose();
   }

   private double exponentialDecay(double prevValue, double targetValue, double decayFactor) {
      return targetValue + (prevValue - targetValue) * Math.exp(-decayFactor * this.minecraft.getTimer().getGameTimeDeltaTicks());
   }
}
