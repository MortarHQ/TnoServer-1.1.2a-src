package com.tno_tensura_extras.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.tno_tensura_extras.item.MagicCameraItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.cache.object.GeoCube;
import software.bernie.geckolib.cache.object.GeoQuad;
import software.bernie.geckolib.cache.object.GeoVertex;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.RenderUtil;

public class MagicCameraScreenLayer extends GeoRenderLayer<MagicCameraItem> {
   private static final String CAMERA_BONE = "camera";
   private static final float SCREEN_MIN_U = 0.375F;
   private static final float SCREEN_MAX_U = 0.59375F;
   private static final float SCREEN_MIN_V = 0.0F;
   private static final float SCREEN_MAX_V = 0.1875F;
   private static final float UV_EPSILON = 1.0E-4F;
   private static final float SURFACE_OFFSET = 4.8828125E-4F;
   private final MagicCameraRenderer cameraRenderer;

   public MagicCameraScreenLayer(MagicCameraRenderer renderer) {
      super(renderer);
      this.cameraRenderer = renderer;
   }

   public void renderForBone(
      PoseStack poseStack,
      MagicCameraItem animatable,
      GeoBone bone,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      if ("camera".equals(bone.getName()) && this.cameraRenderer.isHandPerspective() && MagicCameraView.isReady()) {
         for (GeoCube cube : bone.getCubes()) {
            for (GeoQuad quad : cube.quads()) {
               if (isScreenQuad(quad)) {
                  renderScreenQuad(poseStack, cube, quad, bufferSource, packedOverlay);
                  return;
               }
            }
         }
      }
   }

   private static boolean isScreenQuad(GeoQuad quad) {
      if (quad.direction() != Direction.EAST) {
         return false;
      }

      float minU = Float.POSITIVE_INFINITY;
      float maxU = Float.NEGATIVE_INFINITY;
      float minV = Float.POSITIVE_INFINITY;
      float maxV = Float.NEGATIVE_INFINITY;

      for (GeoVertex vertex : quad.vertices()) {
         minU = Math.min(minU, vertex.texU());
         maxU = Math.max(maxU, vertex.texU());
         minV = Math.min(minV, vertex.texV());
         maxV = Math.max(maxV, vertex.texV());
      }

      return close(minU, 0.375F) && close(maxU, 0.59375F) && close(minV, 0.0F) && close(maxV, 0.1875F);
   }

   private static boolean close(float first, float second) {
      return Math.abs(first - second) <= 1.0E-4F;
   }

   private static void renderScreenQuad(PoseStack poseStack, GeoCube cube, GeoQuad quad, MultiBufferSource bufferSource, int packedOverlay) {
      poseStack.pushPose();
      RenderUtil.translateToPivotPoint(poseStack, cube);
      RenderUtil.rotateMatrixAroundCube(poseStack, cube);
      RenderUtil.translateAwayFromPivotPoint(poseStack, cube);
      VertexConsumer screenBuffer = bufferSource.getBuffer(RenderType.entityCutoutNoCullZOffset(MagicCameraView.textureLocation()));
      Vector3f normal = quad.normal();
      Pose pose = poseStack.last();

      for (GeoVertex vertex : quad.vertices()) {
         Vector3f position = new Vector3f(vertex.position()).fma(4.8828125E-4F, normal);
         float u = (vertex.texU() - 0.375F) / 0.21875F;
         float v = 1.0F - (vertex.texV() - 0.0F) / 0.1875F;
         screenBuffer.addVertex(pose, position)
            .setColor(255, 255, 255, 255)
            .setUv(u, v)
            .setOverlay(packedOverlay)
            .setLight(15728880)
            .setNormal(pose, normal.x(), normal.y(), normal.z());
      }

      poseStack.popPose();
   }
}
