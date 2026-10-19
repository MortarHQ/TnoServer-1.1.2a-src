package com.blamejared.crafttweaker.natives.util.math;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.AxisCycle;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/math/MutableBlockPos")
@NativeTypeRegistration(value = MutableBlockPos.class, zenCodeName = "crafttweaker.api.util.math.MutableBlockPos")
public class ExpandMutableBlockPos {
   @ZenCodeType.Method
   public static MutableBlockPos setValue(MutableBlockPos internal, int x, int y, int z) {
      return internal.set(x, y, z);
   }

   @ZenCodeType.Method
   public static MutableBlockPos setValue(MutableBlockPos internal, double x, double y, double z) {
      return internal.set(x, y, z);
   }

   @ZenCodeType.Method
   public static MutableBlockPos setValue(MutableBlockPos internal, Vec3i value) {
      return internal.set(value);
   }

   @ZenCodeType.Method
   public static MutableBlockPos setValue(MutableBlockPos internal, long value) {
      return internal.set(value);
   }

   @ZenCodeType.Method
   public static MutableBlockPos setValue(MutableBlockPos internal, AxisCycle axisCycle, int x, int y, int z) {
      return internal.set(axisCycle, x, y, z);
   }

   @ZenCodeType.Method
   public static MutableBlockPos setWithOffset(MutableBlockPos internal, Vec3i other, Direction direction) {
      return internal.setWithOffset(other, direction);
   }

   @ZenCodeType.Method
   public static MutableBlockPos setWithOffset(MutableBlockPos internal, Vec3i other, int x, int y, int z) {
      return internal.setWithOffset(other, x, y, z);
   }

   @ZenCodeType.Method
   public static MutableBlockPos setWithOffset(MutableBlockPos internal, Vec3i other, Vec3i offset) {
      return internal.setWithOffset(other, offset);
   }

   @ZenCodeType.Method
   public static MutableBlockPos move(MutableBlockPos internal, Direction direction) {
      return internal.move(direction);
   }

   @ZenCodeType.Method
   public static MutableBlockPos move(MutableBlockPos internal, Direction direction, int distance) {
      return internal.move(direction, distance);
   }

   @ZenCodeType.Method
   public static MutableBlockPos move(MutableBlockPos internal, int x, int y, int z) {
      return internal.move(x, y, z);
   }

   @ZenCodeType.Method
   public static MutableBlockPos move(MutableBlockPos internal, Vec3i other) {
      return internal.move(other);
   }

   @ZenCodeType.Method
   public static MutableBlockPos clamp(MutableBlockPos internal, Axis axis, int min, int max) {
      return internal.clamp(axis, min, max);
   }

   @ZenCodeType.Method
   public static MutableBlockPos setX(MutableBlockPos internal, int x) {
      return internal.setX(x);
   }

   @ZenCodeType.Method
   public static MutableBlockPos setY(MutableBlockPos internal, int y) {
      return internal.setY(y);
   }

   @ZenCodeType.Method
   public static MutableBlockPos setZ(MutableBlockPos internal, int z) {
      return internal.setZ(z);
   }

   @ZenCodeType.Method
   public static MutableBlockPos mutable(MutableBlockPos internal) {
      return internal.mutable();
   }
}
