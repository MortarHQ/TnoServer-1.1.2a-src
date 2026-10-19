package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.mixin.common.access.predicate.AccessStatePropertiesPredicateBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder;
import net.minecraft.advancements.critereon.StatePropertiesPredicate.PropertyMatcher;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/builder/StatePropertiesPredicateBuilder")
@NativeTypeRegistration(value = Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.StatePropertiesPredicateBuilder")
public final class ExpandStatePropertiesPredicateBuilder {
   @ZenCodeType.Method
   public static StatePropertiesPredicate build(Builder internal) {
      return (StatePropertiesPredicate)internal.build().orElseThrow(() -> new RuntimeException("Error while building StatePropertiesPredicate!"));
   }

   private static Builder matching(Builder internal, PropertyMatcher matcher) {
      ((AccessStatePropertiesPredicateBuilder)internal).crafttweaker$getMatchers().add(matcher);
      return internal;
   }
}
