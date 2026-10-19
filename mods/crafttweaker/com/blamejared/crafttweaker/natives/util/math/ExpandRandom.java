package com.blamejared.crafttweaker.natives.util.math;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.Random;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/math/Random")
@NativeTypeRegistration(value = Random.class, zenCodeName = "crafttweaker.api.util.math.Random")
public class ExpandRandom {
   @ZenCodeType.Method
   public static int nextInt(Random internal) {
      return internal.nextInt();
   }

   @ZenCodeType.Method
   public static int nextInt(Random internal, int bound) {
      return internal.nextInt(bound);
   }

   @ZenCodeType.Method
   public static boolean nextBoolean(Random internal) {
      return internal.nextBoolean();
   }

   @ZenCodeType.Method
   public static double nextDouble(Random internal) {
      return internal.nextDouble();
   }

   @ZenCodeType.Method
   public static float nextFloat(Random internal) {
      return internal.nextFloat();
   }
}
