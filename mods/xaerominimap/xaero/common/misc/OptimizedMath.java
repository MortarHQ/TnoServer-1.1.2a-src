package xaero.common.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class OptimizedMath {
   public static final Vector3f XP = new Vector3f(1.0F, 0.0F, 0.0F);
   public static final Vector3f YP = new Vector3f(0.0F, 1.0F, 0.0F);
   public static final Vector3f ZP = new Vector3f(0.0F, 0.0F, 1.0F);

   public static int myFloor(double d) {
      int asInt = (int)d;
      if (asInt != d && d < 0.0) {
         asInt--;
      }

      return asInt;
   }

   public static void rotatePose(PoseStack poseStack, float degrees, Vector3fc vector) {
      Pose pose = poseStack.last();
      pose.pose().rotate(degrees * (float) (Math.PI / 180.0), vector);
      pose.normal().rotate(degrees * (float) (Math.PI / 180.0), vector);
   }

   public static void rotateMatrix(Matrix4f matrix, float degrees, Vector3fc vector) {
      matrix.rotate(degrees * (float) (Math.PI / 180.0), vector);
   }
}
