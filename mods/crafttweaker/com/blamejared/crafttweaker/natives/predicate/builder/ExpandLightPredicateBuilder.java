package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.LightPredicate;
import net.minecraft.advancements.critereon.LightPredicate.Builder;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/builder/LightPredicateBuilder")
@NativeTypeRegistration(value = Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.LightPredicateBuilder")
public final class ExpandLightPredicateBuilder {
   @ZenCodeType.Method
   public static Builder composite(Builder internal, Ints composite) {
      return internal.setComposite(composite);
   }

   @ZenCodeType.Method
   public static LightPredicate build(Builder internal) {
      return internal.build();
   }
}
