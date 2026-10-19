package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.MinMaxBounds.Doubles;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/DoubleMinMaxBoundsPredicate")
@NativeTypeRegistration(value = Doubles.class, zenCodeName = "crafttweaker.api.predicate.DoubleMinMaxBoundsPredicate")
public final class ExpandMinMaxBoundsDoubles {
   @ZenCodeType.StaticExpansionMethod
   public static Doubles any() {
      return Doubles.ANY;
   }

   @ZenCodeType.StaticExpansionMethod
   public static Doubles exactly(double value) {
      return Doubles.exactly(value);
   }

   @ZenCodeType.StaticExpansionMethod
   public static Doubles between(double min, double max) {
      return Doubles.between(min, max);
   }

   @ZenCodeType.StaticExpansionMethod
   public static Doubles atLeast(double min) {
      return Doubles.atLeast(min);
   }

   @ZenCodeType.StaticExpansionMethod
   public static Doubles atMost(double max) {
      return Doubles.atMost(max);
   }

   @ZenCodeType.Method
   public static boolean matches(Doubles internal, double value) {
      return internal.matches(value);
   }

   @ZenCodeType.Method
   public static boolean matchesSqr(Doubles internal, double value) {
      return internal.matchesSqr(value);
   }
}
