package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.DistancePredicate;
import net.minecraft.advancements.critereon.MinMaxBounds.Doubles;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/DistancePredicate")
@NativeTypeRegistration(value = DistancePredicate.class, zenCodeName = "crafttweaker.api.predicate.DistancePredicate")
public final class ExpandDistancePredicate {
   @ZenCodeType.StaticExpansionMethod
   public static DistancePredicate create(Doubles x, Doubles y, Doubles z, Doubles horizontal, Doubles absolute) {
      return new DistancePredicate(x, y, z, horizontal, absolute);
   }

   @ZenCodeType.StaticExpansionMethod
   public static DistancePredicate verticalDistance(Doubles bounds) {
      return DistancePredicate.vertical(bounds);
   }

   @ZenCodeType.StaticExpansionMethod
   public static DistancePredicate horizontalDistance(Doubles bounds) {
      return DistancePredicate.horizontal(bounds);
   }

   @ZenCodeType.StaticExpansionMethod
   public static DistancePredicate absoluteDistance(Doubles bounds) {
      return DistancePredicate.absolute(bounds);
   }

   @ZenCodeType.StaticExpansionMethod
   public static DistancePredicate xyz(Doubles x, Doubles y, Doubles z) {
      return create(x, y, z, Doubles.ANY, Doubles.ANY);
   }
}
