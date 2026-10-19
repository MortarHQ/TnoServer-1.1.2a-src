package com.blamejared.crafttweaker.natives.util.direction;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.Plane;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/direction/Axis")
@NativeTypeRegistration(value = Axis.class, zenCodeName = "crafttweaker.api.util.direction.Axis")
@BracketEnum("minecraft:direction/axis")
public class ExpandAxis {
   @ZenCodeType.Method
   @ZenCodeType.Getter("name")
   public static String getName(Axis internal) {
      return internal.getName();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("vertical")
   public static boolean isVertical(Axis internal) {
      return internal.isVertical();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("horizontal")
   public static boolean isHorizontal(Axis internal) {
      return internal.isHorizontal();
   }

   @ZenCodeType.Method
   public static boolean test(Axis internal, @ZenCodeType.Nullable Direction direction) {
      return internal.test(direction);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("plane")
   public static Plane getPlane(Axis internal) {
      return internal.getPlane();
   }

   @ZenCodeType.Method
   public static int choose(Axis internal, int x, int y, int z) {
      return internal.choose(x, y, z);
   }

   @ZenCodeType.Method
   public static double choose(Axis internal, double x, double y, double z) {
      return internal.choose(x, y, z);
   }
}
