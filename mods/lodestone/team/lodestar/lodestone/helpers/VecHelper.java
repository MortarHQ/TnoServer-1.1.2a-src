package team.lodestar.lodestone.helpers;

import java.util.ArrayList;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class VecHelper {
   public static Vec3 radialOffset(Vec3 pos, float distance, float current, float total) {
      double angle = current / total * (Math.PI * 2);
      double dx2 = distance * Math.cos(angle % 6.28F);
      double dz2 = distance * Math.sin(angle % 6.28F);
      Vec3 vector = new Vec3(dx2, 0.0, dz2);
      double x = vector.x * distance;
      double z = vector.z * distance;
      return pos.add(new Vec3(x, 0.0, z));
   }

   public static ArrayList<Vec3> rotatingRadialOffsets(Vec3 pos, float distance, float totalPoints, float gameTime, float timePerRotation) {
      return rotatingRadialOffsets(pos, distance, distance, totalPoints, gameTime, timePerRotation);
   }

   public static ArrayList<Vec3> rotatingRadialOffsets(Vec3 pos, float distanceX, float distanceZ, float totalPoints, float gameTime, float timePerRotation) {
      ArrayList<Vec3> positions = new ArrayList<>();

      for (int i = 0; i < totalPoints; i++) {
         positions.add(rotatingRadialOffset(pos, distanceX, distanceZ, i, totalPoints, gameTime, timePerRotation));
      }

      return positions;
   }

   public static Vec3 rotatingRadialOffset(Vec3 pos, float distance, float currentPoint, float totalPoints, float gameTime, float timePerRotation) {
      return rotatingRadialOffset(pos, distance, distance, currentPoint, totalPoints, gameTime, timePerRotation);
   }

   public static Vec3 rotatingRadialOffset(
      Vec3 pos, float distanceX, float distanceZ, float currentPoint, float totalPoints, float gameTime, float timePerRotation
   ) {
      double angle = currentPoint / totalPoints * (Math.PI * 2);
      angle += gameTime % timePerRotation / timePerRotation * (Math.PI * 2);
      double dx2 = distanceX * Math.cos(angle % 6.28F);
      double dz2 = distanceZ * Math.sin(angle % 6.28F);
      Vec3 vector2f = new Vec3(dx2, 0.0, dz2);
      double x = vector2f.x * distanceX;
      double z = vector2f.z * distanceZ;
      return pos.add(x, 0.0, z);
   }

   public static ArrayList<Vec3> blockOutlinePositions(Level level, BlockPos pos) {
      ArrayList<Vec3> arrayList = new ArrayList<>();
      double d0 = 0.5625;
      RandomSource random = level.random;

      for (Direction direction : Direction.values()) {
         BlockPos relative = pos.relative(direction);
         if (!level.getBlockState(relative).isSolidRender(level, relative)) {
            Axis axis = direction.getAxis();
            double d1 = axis == Axis.X ? 0.5 + d0 * direction.getStepX() : random.nextFloat();
            double d2 = axis == Axis.Y ? 0.5 + d0 * direction.getStepY() : random.nextFloat();
            double d3 = axis == Axis.Z ? 0.5 + d0 * direction.getStepZ() : random.nextFloat();
            arrayList.add(new Vec3(pos.getX() + d1, pos.getY() + d2, pos.getZ() + d3));
         }
      }

      return arrayList;
   }

   public static Vec3 axisAlignedPlaneOf(Vec3 vec) {
      vec = vec.normalize();
      return new Vec3(1.0, 1.0, 1.0).subtract(Math.abs(vec.x), Math.abs(vec.y), Math.abs(vec.z));
   }

   public static Vec3 rotate(Vec3 vec, double deg, Axis axis) {
      if (deg == 0.0) {
         return vec;
      } else if (vec == Vec3.ZERO) {
         return vec;
      } else {
         float angle = (float)(deg / 180.0 * Math.PI);
         double sin = Mth.sin(angle);
         double cos = Mth.cos(angle);
         double x = vec.x;
         double y = vec.y;
         double z = vec.z;
         if (axis == Axis.X) {
            return new Vec3(x, y * cos - z * sin, z * cos + y * sin);
         } else if (axis == Axis.Y) {
            return new Vec3(x * cos + z * sin, y, z * cos - x * sin);
         } else {
            return axis == Axis.Z ? new Vec3(x * cos - y * sin, y * cos + x * sin, z) : vec;
         }
      }
   }

   public static Vec3 projectToPlayerView(Vec3 target, float partialTicks) {
      Camera ari = Minecraft.getInstance().gameRenderer.getMainCamera();
      Vec3 camera_pos = ari.getPosition();
      Quaternionf camera_rotation_conj = new Quaternionf(ari.rotation());
      camera_rotation_conj.conjugate();
      Vector3f result3f = new Vector3f((float)(camera_pos.x - target.x), (float)(camera_pos.y - target.y), (float)(camera_pos.z - target.z));
      result3f.rotate(camera_rotation_conj);
      Minecraft mc = Minecraft.getInstance();
      if ((Boolean)mc.options.bobView().get() && mc.getCameraEntity() instanceof Player player) {
         float distwalked_modified = player.walkDist;
         float f = distwalked_modified - player.walkDistO;
         float f1 = -(distwalked_modified + f * partialTicks);
         float f2 = Mth.lerp(partialTicks, player.oBob, player.bob);
         Quaternionf q2 = new Quaternionf(new AxisAngle4f(Math.abs(Mth.cos(f1 * (float) Math.PI - 0.2F) * f2) * 5.0F, VecHelper.Vector3fHelper.XP));
         q2.conjugate();
         result3f.rotate(q2);
         Quaternionf q1 = new Quaternionf(new AxisAngle4f(Math.abs(Mth.sin(f1 * (float) Math.PI) * f2) * 3.0F, VecHelper.Vector3fHelper.ZP));
         q1.conjugate();
         result3f.rotate(q1);
         Vector3f bob_translation = new Vector3f(Mth.sin(f1 * (float) Math.PI) * f2 * 0.5F, -Math.abs(Mth.cos(f1 * (float) Math.PI) * f2), 0.0F);
         result3f.add(new Vector3f(bob_translation.x(), -bob_translation.y(), bob_translation.z()));
      }

      float fov = (float)mc.gameRenderer.getFov(ari, partialTicks, true);
      float half_height = mc.getWindow().getGuiScaledHeight() / 2.0F;
      float scale_factor = half_height / (result3f.z() * (float)Math.tan(Math.toRadians(fov / 2.0F)));
      return new Vec3(-result3f.x() * scale_factor, result3f.y() * scale_factor, result3f.z());
   }

   public static class Vector3fHelper {
      public static Vector3f XP = new Vector3f(1.0F, 0.0F, 0.0F);
      public static Vector3f YP = new Vector3f(0.0F, 1.0F, 0.0F);
      public static Vector3f ZP = new Vector3f(0.0F, 0.0F, 1.0F);
      public static Vector3f XN = new Vector3f(-1.0F, 0.0F, 0.0F);
      public static Vector3f YN = new Vector3f(0.0F, -1.0F, 0.0F);
      public static Vector3f ZN = new Vector3f(0.0F, 0.0F, -1.0F);

      public static Quaternionf rotation(float rotation, Vector3f axis) {
         return new Quaternionf(new AxisAngle4f(rotation, axis));
      }
   }

   public static class Vector4fHelper {
      public static void perspectiveDivide(Vector4f v) {
         v.div(v.w);
      }
   }
}
