package net.p3pp3rf1y.sophisticatedcore.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.p3pp3rf1y.sophisticatedcore.util.Easing;
import net.p3pp3rf1y.sophisticatedcore.util.IDoubleBlock;
import net.p3pp3rf1y.sophisticatedcore.util.VoxelOutliner;

public class BlockHighlightRenderer {
   public static final int HIGHLIGHT_DURATION = 40;
   private static Map<Integer, List<BlockPos>> highlightedPositions = new HashMap<>();
   private static long highlightExpireTime = 0L;
   @Nullable
   private static Map<Integer, List<BlockHighlightRenderer.HighlightedBlock>> cachedHighlightedBlocks = new HashMap<>();

   public static void addHighlightedPositions(Map<Integer, List<BlockPos>> highlightPositions) {
      highlightPositions.forEach(
         (color, positions) -> highlightedPositions.computeIfAbsent(color, k -> new ArrayList<>()).addAll((Collection<? extends BlockPos>)positions)
      );
      highlightExpireTime = Minecraft.getInstance().level.getGameTime() + 40L;
      cachedHighlightedBlocks = null;
   }

   public static void render(PoseStack poseStack, float partialTick, Vec3 cameraPos) {
      Minecraft mc = Minecraft.getInstance();
      if (highlightExpireTime < mc.level.getGameTime()) {
         if (!highlightedPositions.isEmpty()) {
            highlightedPositions.clear();
            cachedHighlightedBlocks = null;
         }
      } else {
         BufferSource buffer = mc.renderBuffers().bufferSource();
         if (cachedHighlightedBlocks == null) {
            cachedHighlightedBlocks = new HashMap<>();
            highlightedPositions.forEach(
               (color, positions) -> cachedHighlightedBlocks.put(
                  color, positions.stream().map(pos -> getHighlightedBlock(mc, pos)).filter(Objects::nonNull).toList()
               )
            );
         }

         cachedHighlightedBlocks.forEach(
            (color, highlightedBlocks) -> highlightedBlocks.forEach(bh -> renderHighlightedBlock(poseStack, partialTick, cameraPos, bh, mc, buffer, color))
         );
      }
   }

   private static void renderHighlightedBlock(
      PoseStack poseStack, float partialTick, Vec3 cameraPos, BlockHighlightRenderer.HighlightedBlock bh, Minecraft mc, BufferSource buffer, int color
   ) {
      poseStack.pushPose();
      poseStack.translate(bh.pos.getX() - cameraPos.x(), bh.pos.getY() - cameraPos.y(), bh.pos.getZ() - cameraPos.z());
      poseStack.translate(bh.pivot.x, bh.pivot.y, bh.pivot.z);
      float scale = 1.0F + Easing.EASE_IN_OUT_CUBIC.ease((float)tri01(mc.level.getGameTime(), 15.0, partialTick)) * 0.05F;
      poseStack.scale(scale, scale, scale);
      poseStack.translate(-bh.pivot.x, -bh.pivot.y, -bh.pivot.z);
      BlockHighlightRenderHelper.renderThickEdges(poseStack, buffer, color, bh.edges(), bh.pos().getX(), bh.pos().getY(), bh.pos().getZ());
      poseStack.popPose();
   }

   private static BlockHighlightRenderer.HighlightedBlock getHighlightedBlock(Minecraft mc, BlockPos pos) {
      ClientLevel level = mc.level;
      if (level.isLoaded(pos) && !level.isEmptyBlock(pos)) {
         BlockState state = level.getBlockState(pos);
         VoxelShape shape = state.getShape(level, pos);
         if (state.getBlock() instanceof IDoubleBlock doubleBlock) {
            VoxelShape finalShape = shape;
            shape = doubleBlock.getOtherPosition(state, pos).map(otherPosx -> {
               if (level.isLoaded(otherPosx) && !level.isEmptyBlock(otherPosx)) {
                  BlockState otherStatex = level.getBlockState(otherPosx);
                  VoxelShape otherShapex = otherStatex.getShape(level, otherPosx);
                  otherShapex = otherShapex.move(otherPosx.getX() - pos.getX(), otherPosx.getY() - pos.getY(), otherPosx.getZ() - pos.getZ());
                  return Shapes.join(finalShape, otherShapex, BooleanOp.OR);
               } else {
                  return finalShape;
               }
            }).orElse(shape);
         } else if (state.getBlock() instanceof ChestBlock && state.getValue(ChestBlock.TYPE) != ChestType.SINGLE) {
            Direction connectedDir = ChestBlock.getConnectedDirection(state);
            BlockPos otherPos = pos.relative(connectedDir);
            if (level.isLoaded(otherPos) && !level.isEmptyBlock(otherPos)) {
               BlockState otherState = level.getBlockState(otherPos);
               VoxelShape otherShape = otherState.getShape(level, otherPos);
               otherShape = otherShape.move(otherPos.getX() - pos.getX(), otherPos.getY() - pos.getY(), otherPos.getZ() - pos.getZ());
               shape = Shapes.join(shape, otherShape, BooleanOp.OR);
            }
         }

         return new BlockHighlightRenderer.HighlightedBlock(pos, VoxelOutliner.linesFromVoxelShapeSimplified(shape, pos), shape.bounds().getCenter());
      } else {
         return null;
      }
   }

   public static double tri01(double ticks, double periodTicks, double phaseOffsetTicks) {
      if (periodTicks <= 0.0) {
         return 0.0;
      }

      double phase = (ticks + phaseOffsetTicks) % periodTicks / (periodTicks - 1.0);
      return 1.0 - Math.abs(2.0 * phase - 1.0);
   }

   private record HighlightedBlock(BlockPos pos, List<VoxelOutliner.Edge> edges, Vec3 pivot) {
   }
}
