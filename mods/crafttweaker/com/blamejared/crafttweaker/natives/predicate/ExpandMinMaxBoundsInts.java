package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/IntMinMaxBoundsPredicate")
@NativeTypeRegistration(value = Ints.class, zenCodeName = "crafttweaker.api.predicate.IntMinMaxBoundsPredicate")
public final class ExpandMinMaxBoundsInts {
   @ZenCodeType.StaticExpansionMethod
   public static Ints any() {
      return Ints.ANY;
   }

   @ZenCodeType.StaticExpansionMethod
   public static Ints exactly(int value) {
      return Ints.exactly(value);
   }

   @ZenCodeType.StaticExpansionMethod
   public static Ints between(int min, int max) {
      return Ints.between(min, max);
   }

   @ZenCodeType.StaticExpansionMethod
   public static Ints atLeast(int min) {
      return Ints.atLeast(min);
   }

   @ZenCodeType.StaticExpansionMethod
   public static Ints atMost(int max) {
      return Ints.atMost(max);
   }

   @ZenCodeType.Method
   public static boolean matches(Ints internal, int value) {
      return internal.matches(value);
   }

   @ZenCodeType.Method
   public static boolean matchesSqr(Ints internal, int value) {
      return internal.matchesSqr(value);
   }
}
