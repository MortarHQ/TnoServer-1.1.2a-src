package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.FluidPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.advancements.critereon.FluidPredicate.Builder;
import net.minecraft.world.level.material.Fluid;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/builder/FluidPredicateBuilder")
@NativeTypeRegistration(value = Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.FluidPredicateBuilder")
public class ExpandFluidPredicateBuilder {
   @ZenCodeType.Method
   public static Builder blocks(Builder internal, Fluid fluid) {
      return internal.of(fluid);
   }

   @ZenCodeType.Method
   public static Builder tag(Builder internal, KnownTag<Fluid> tag) {
      return internal.of(tag.getTagKey());
   }

   @ZenCodeType.Method
   public static Builder properties(Builder internal, StatePropertiesPredicate predicate) {
      return internal.setProperties(predicate);
   }

   @ZenCodeType.Method
   public static FluidPredicate build(Builder internal) {
      return internal.build();
   }
}
