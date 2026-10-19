package com.blamejared.crafttweaker.natives.util.direction;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.Iterator;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.Plane;
import net.minecraft.util.RandomSource;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/direction/Plane")
@NativeTypeRegistration(value = Plane.class, zenCodeName = "crafttweaker.api.util.direction.Plane")
@BracketEnum("minecraft:direction/plane")
public class ExpandPlane {
   @ZenCodeType.Method
   public static Direction getRandomDirection(Plane internal, RandomSource random) {
      return internal.getRandomDirection(random);
   }

   @ZenCodeType.Method
   public static Axis getRandomAxis(Plane internal, RandomSource random) {
      return internal.getRandomAxis(random);
   }

   @ZenCodeType.Method
   public static boolean test(Plane internal, @ZenCodeType.Nullable Direction direction) {
      return internal.test(direction);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("iterator")
   public static Iterator<Direction> iterator(Plane internal) {
      return internal.iterator();
   }
}
