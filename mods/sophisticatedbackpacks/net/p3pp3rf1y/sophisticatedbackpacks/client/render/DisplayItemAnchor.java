package net.p3pp3rf1y.sophisticatedbackpacks.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.client.model.IQuadTransformer;
import org.joml.Quaternionf;

public final class DisplayItemAnchor {
   public final double centerX;
   public final double centerY;
   public final double centerZ;
   public final float scale;
   public final Quaternionf facingRotation;
   public final float depthOffset;

   private DisplayItemAnchor(double centerX, double centerY, double centerZ, float scale, Quaternionf facingRotation, float depthOffset) {
      this.centerX = centerX;
      this.centerY = centerY;
      this.centerZ = centerZ;
      this.scale = scale;
      this.facingRotation = facingRotation;
      this.depthOffset = depthOffset;
   }

   public static DisplayItemAnchor fromQuad(BakedQuad quad) {
      int[] vertices = quad.getVertices();
      float minX = Float.POSITIVE_INFINITY;
      float minY = Float.POSITIVE_INFINITY;
      float minZ = Float.POSITIVE_INFINITY;
      float maxX = Float.NEGATIVE_INFINITY;
      float maxY = Float.NEGATIVE_INFINITY;
      float maxZ = Float.NEGATIVE_INFINITY;

      for (int i = 0; i < 4; i++) {
         int base = i * IQuadTransformer.STRIDE;
         float x = Float.intBitsToFloat(vertices[base]);
         float y = Float.intBitsToFloat(vertices[base + 1]);
         float z = Float.intBitsToFloat(vertices[base + 2]);
         minX = Math.min(minX, x);
         minY = Math.min(minY, y);
         minZ = Math.min(minZ, z);
         maxX = Math.max(maxX, x);
         maxY = Math.max(maxY, y);
         maxZ = Math.max(maxZ, z);
      }

      double centerX = (minX + maxX) * 0.5;
      double centerY = (minY + maxY) * 0.5;
      double centerZ = (minZ + maxZ) * 0.5;
      Direction face = quad.getDirection();
      float sizeU;
      float sizeV;
      switch (face.getAxis()) {
         case X:
            sizeU = maxZ - minZ;
            sizeV = maxY - minY;
            break;
         case Y:
            sizeU = maxX - minX;
            sizeV = maxZ - minZ;
            break;
         default:
            sizeU = maxX - minX;
            sizeV = maxY - minY;
      }

      float scale = Math.min(sizeU, sizeV);

      Quaternionf facingRotation = switch (face) {
         case NORTH -> Axis.YP.rotationDegrees(0.0F);
         case SOUTH -> Axis.YP.rotationDegrees(180.0F);
         case WEST -> Axis.YP.rotationDegrees(-90.0F);
         case EAST -> Axis.YP.rotationDegrees(90.0F);
         case UP -> Axis.XP.rotationDegrees(-90.0F);
         case DOWN -> Axis.XP.rotationDegrees(90.0F);
         default -> throw new MatchException(null, null);
      };
      float depthOffset = 0.0015F;
      return new DisplayItemAnchor(centerX, centerY, centerZ, scale, facingRotation, depthOffset);
   }

   public void applyTransform(PoseStack poseStack) {
      poseStack.translate(this.centerX, this.centerY, this.centerZ);
      poseStack.mulPose(this.facingRotation);
      poseStack.translate(0.0, 0.0, this.depthOffset);
      poseStack.scale(this.scale, this.scale, this.scale);
   }
}
