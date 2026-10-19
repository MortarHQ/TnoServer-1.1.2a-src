package com.github.L_Ender.cataclysm.util;

import io.netty.buffer.ByteBuf;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

public class CMMathUtil {
   public static float approachSmooth(float current, float previous, float desired, float desiredSpeed, float deltaSpeed) {
      float prevSpeed = current - previous;
      desiredSpeed = Mth.abs(desiredSpeed);
      desiredSpeed = current < desired ? desiredSpeed : -desiredSpeed;
      float speed = Mth.approach(prevSpeed, desiredSpeed, deltaSpeed);
      float speedApproachReduction = (float)(
         1.0 - Math.pow(Mth.clamp(-Mth.abs(current - desired) / Mth.abs(2.0F * desiredSpeed / deltaSpeed) + 1.0F, 0.0F, 1.0F), 4.0)
      );
      speed *= speedApproachReduction;
      return current < desired ? Mth.clamp(current + speed, current, desired) : Mth.clamp(current + speed, desired, current);
   }

   public static float approachDegreesSmooth(float current, float previous, float desired, float desiredSpeed, float deltaSpeed) {
      float desiredDifference = Mth.degreesDifference(current, desired);
      float previousDifference = Mth.degreesDifference(current, previous);
      return approachSmooth(current, current + previousDifference, current + desiredDifference, desiredSpeed, deltaSpeed);
   }

   public static Quaternionf quatFromRotationXYZ(float x, float y, float z, boolean degrees) {
      if (degrees) {
         x *= (float) (Math.PI / 180.0);
         y *= (float) (Math.PI / 180.0);
         z *= (float) (Math.PI / 180.0);
      }

      return new Quaternionf().rotationXYZ(x, y, z);
   }

   public static Vec3 getOffsetPos(Entity entity, double offsetX, double offsetY, double offsetZ, float rotationX, float rotationY) {
      Vec3 Vec3 = new Vec3(offsetZ, offsetY, offsetX)
         .zRot(rotationX * (float) (Math.PI / 180.0))
         .yRot(-rotationY * (float) (Math.PI / 180.0) - (float) (Math.PI / 2));
      return entity.position().add(Vec3.x, Vec3.y, Vec3.z);
   }

   public static Vec3 getOffsetMotion(Entity entity, double offsetX, double offsetY, double offsetZ, float rotationX, float rotationY) {
      return new Vec3(offsetZ, offsetY, offsetX)
         .zRot(rotationX * (float) (Math.PI / 180.0))
         .yRot(-rotationY * (float) (Math.PI / 180.0) - (float) (Math.PI / 2));
   }

   public static float smin(float a, float b, float k) {
      float h = Math.max(k - Math.abs(a - b), 0.0F) / k;
      return Math.min(a, b) - h * h * k * 0.25F;
   }

   public static float getAngle(Vec2 a, Vec2 b) {
      return getAngle(a.x, a.y, b.x, b.y);
   }

   public static float getAngle(double ax, double ay, double bx, double by) {
      return (float)Math.atan2(by - ay, bx - ax) + (float) Math.PI;
   }

   public static float cullAnimationTick(int tick, float amplitude, float partialTick, int startOffset, int endAt) {
      float i = Mth.clamp(tick + partialTick - startOffset, 0.0F, endAt);
      float f = (float)Math.sin(i / endAt * Math.PI) * amplitude;
      return smin(f, 1.0F, 0.1F);
   }

   public static Optional<Vec3> readVec3(CompoundTag tag, String key) {
      ListTag listTag = tag.getList(key, 6);
      if (listTag.size() == 3) {
         double x = listTag.getDouble(0);
         double y = listTag.getDouble(1);
         double z = listTag.getDouble(2);
         return Optional.of(new Vec3(x, y, z));
      } else {
         return Optional.empty();
      }
   }

   public static ListTag writeVec3(Vec3 pos) {
      ListTag listTag = new ListTag();
      listTag.add(DoubleTag.valueOf(pos.x));
      listTag.add(DoubleTag.valueOf(pos.y));
      listTag.add(DoubleTag.valueOf(pos.z));
      return listTag;
   }

   public static Vec3 readVec3(ByteBuf buf) {
      return new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
   }

   public static void writeVec3(ByteBuf buf, Vec3 vec3) {
      buf.writeDouble(vec3.x());
      buf.writeDouble(vec3.y());
      buf.writeDouble(vec3.z());
   }
}
