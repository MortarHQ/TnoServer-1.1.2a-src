package com.finderfeed.fdlib.util;

import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class FDUtil {
   public static void printVector3f(Vector3f vector3f) {
      System.out.println("X: " + vector3f.x + " Y: " + vector3f.y + " Z: " + vector3f.z);
   }

   public static int encodeDirection(Vec3 direction) {
      direction = direction.normalize();
      int dx = (int)Math.round((direction.x + 1.0) / 2.0 * 255.0);
      int dy = (int)Math.round((direction.y + 1.0) / 2.0 * 255.0);
      int dz = (int)Math.round((direction.z + 1.0) / 2.0 * 255.0);
      int data = 0;
      data += dx;
      data += dy << 8;
      return data + (dz << 16);
   }

   public static Vec3 decodeDirection(int data) {
      int dx = data & 0xFF;
      int dy = (data & 0xFF00) >> 8;
      int dz = data >> 16;
      return new Vec3(dx / 255.0F * 2.0F - 1.0F, dy / 255.0F * 2.0F - 1.0F, dz / 255.0F * 2.0F - 1.0F);
   }
}
