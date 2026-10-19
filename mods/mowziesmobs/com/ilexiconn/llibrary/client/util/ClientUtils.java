package com.ilexiconn.llibrary.client.util;

import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix3f;
import org.joml.Vector3f;

public class ClientUtils {
   private static long lastUpdate = System.currentTimeMillis();

   public static void updateLast() {
      lastUpdate = System.currentTimeMillis();
   }

   public static float updateValue(float current, float target) {
      return updateValue(current, target, 0.5F);
   }

   public static float updateValue(float current, float target, float factor) {
      float times = (float)(System.currentTimeMillis() - lastUpdate) / 16.666666F;
      float off;
      off = !((off = target - current) > 0.01F) && !(off < -0.01F) ? 0.0F : off * (float)Math.pow(factor, times);
      return target - off;
   }

   public static float interpolate(float prev, float current, float partialTicks) {
      return prev + partialTicks * (current - prev);
   }

   public static float interpolateRotation(float prev, float current, float partialTicks) {
      float shortest = ((current - prev) % 360.0F + 540.0F) % 360.0F - 180.0F;
      return prev + shortest * partialTicks;
   }

   public static VertexConsumer transformNormals(VertexConsumer consumer, Matrix3f normals, float x, float y, float z) {
      Vector3f transformed = transformNormals(normals, x, y, z);
      return consumer.setNormal(transformed.x(), transformed.y(), transformed.z());
   }

   public static Vector3f transformNormals(Matrix3f normals, float x, float y, float z) {
      Vector3f input = new Vector3f(x, y, z);
      return normals.transform(input);
   }
}
