package net.p3pp3rf1y.sophisticatedcore.client.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.List;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.p3pp3rf1y.sophisticatedcore.util.VoxelOutliner;

public class BlockHighlightRenderHelper {
   public static final RenderType OUTLINE_QUADS = RenderType.create(
      "storage_outline_quads",
      DefaultVertexFormat.POSITION_COLOR,
      Mode.QUADS,
      256,
      false,
      false,
      CompositeState.builder()
         .setShaderState(RenderStateShard.POSITION_COLOR_SHADER)
         .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
         .setCullState(RenderStateShard.NO_CULL)
         .setTransparencyState(RenderStateShard.NO_TRANSPARENCY)
         .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
         .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
         .setOutputState(RenderStateShard.MAIN_TARGET)
         .createCompositeState(true)
   );

   public static void renderThickEdges(PoseStack poseStack, MultiBufferSource bufferSource, int color, List<VoxelOutliner.Edge> edges, BlockPos originPos) {
      renderThickEdges(poseStack, bufferSource, color, edges, originPos.getX(), originPos.getY(), originPos.getZ());
   }

   public static void renderThickEdges(
      PoseStack poseStack, MultiBufferSource bufferSource, int color, List<VoxelOutliner.Edge> edges, double originX, double originY, double originZ
   ) {
      VertexConsumer vertexConsumer = bufferSource.getBuffer(OUTLINE_QUADS);
      int red = color >> 16 & 0xFF;
      int green = color >> 8 & 0xFF;
      int blue = color & 0xFF;
      Pose pose = poseStack.last();
      edges.forEach(edge -> emitThickLineOrtho(vertexConsumer, pose, edge.a(), edge.b(), 0.03125F, red, green, blue, 255, originX, originY, originZ));
   }

   public static void emitThickLineOrtho(
      VertexConsumer vc, Pose pose, Vec3 a, Vec3 b, float thickness, int r, int g, int bl, int alpha, double originX, double originY, double originZ
   ) {
      float rh = thickness * 0.5F;
      Vec3 d = b.subtract(a);
      Vec3 u = new Vec3(Math.signum(d.x), Math.signum(d.y), Math.signum(d.z));
      Vec3 aEx = a.subtract(u.scale(rh));
      Vec3 bEx = b.add(u.scale(rh));
      Vec3 v;
      Vec3 w;
      if (u.x != 0.0) {
         v = new Vec3(0.0, 1.0, 0.0);
         w = new Vec3(0.0, 0.0, 1.0);
      } else if (u.y != 0.0) {
         v = new Vec3(1.0, 0.0, 0.0);
         w = new Vec3(0.0, 0.0, 1.0);
      } else {
         v = new Vec3(1.0, 0.0, 0.0);
         w = new Vec3(0.0, 1.0, 0.0);
      }

      Vec3 vOff = v.scale(rh);
      Vec3 wOff = w.scale(rh);
      Vec3 aVpWm = aEx.add(vOff).subtract(wOff);
      Vec3 aVpWp = aEx.add(vOff).add(wOff);
      Vec3 aVmWp = aEx.subtract(vOff).add(wOff);
      Vec3 aVmWm = aEx.subtract(vOff).subtract(wOff);
      Vec3 bVpWm = bEx.add(vOff).subtract(wOff);
      Vec3 bVpWp = bEx.add(vOff).add(wOff);
      Vec3 bVmWp = bEx.subtract(vOff).add(wOff);
      Vec3 bVmWm = bEx.subtract(vOff).subtract(wOff);
      emitQuad(vc, pose, aVpWm, aVpWp, bVpWp, bVpWm, r, g, bl, alpha, originX, originY, originZ);
      emitQuad(vc, pose, aVmWp, aVmWm, bVmWm, bVmWp, r, g, bl, alpha, originX, originY, originZ);
      emitQuad(vc, pose, aVmWp, aVpWp, bVpWp, bVmWp, r, g, bl, alpha, originX, originY, originZ);
      emitQuad(vc, pose, aVpWm, aVmWm, bVmWm, bVpWm, r, g, bl, alpha, originX, originY, originZ);
   }

   private static void emitQuad(
      VertexConsumer vc, Pose pose, Vec3 p0, Vec3 p1, Vec3 p2, Vec3 p3, int r, int g, int b, int a, double originX, double originY, double originZ
   ) {
      add(vc, pose, p0, r, g, b, a, originX, originY, originZ);
      add(vc, pose, p1, r, g, b, a, originX, originY, originZ);
      add(vc, pose, p2, r, g, b, a, originX, originY, originZ);
      add(vc, pose, p3, r, g, b, a, originX, originY, originZ);
   }

   private static void add(VertexConsumer vc, Pose pose, Vec3 p, int r, int g, int b, int a, double originX, double originY, double originZ) {
      vc.addVertex(pose, (float)(p.x - originX), (float)(p.y - originY), (float)(p.z - originZ)).setColor(r, g, b, a);
   }
}
