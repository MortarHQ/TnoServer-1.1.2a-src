package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.LocationPredicate.Builder;
import net.minecraft.advancements.critereon.MinMaxBounds.Doubles;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/LocationPredicate")
@NativeTypeRegistration(value = LocationPredicate.class, zenCodeName = "crafttweaker.api.predicate.LocationPredicate")
public final class ExpandLocationPredicate {
   @ZenCodeType.StaticExpansionMethod
   public static Builder create() {
      return Builder.location();
   }

   @ZenCodeType.StaticExpansionMethod
   public static LocationPredicate at(Doubles x, Doubles y, Doubles z) {
      return create().setX(x).setY(y).setZ(z).build();
   }
}
