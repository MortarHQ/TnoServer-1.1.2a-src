package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityFlagsPredicate.Builder;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/builder/EntityFlagsPredicateBuilder")
@NativeTypeRegistration(value = Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.EntityFlagsPredicateBuilder")
public final class ExpandEntityFlagsPredicateBuilder {
   @ZenCodeType.Method
   public static Builder onFire(Builder internal, @ZenCodeType.OptionalBoolean(true) Boolean onFire) {
      return internal.setOnFire(onFire);
   }

   @ZenCodeType.Method
   public static Builder crouching(Builder internal, @ZenCodeType.OptionalBoolean(true) Boolean crouching) {
      return internal.setCrouching(crouching);
   }

   @ZenCodeType.Method
   public static Builder sprinting(Builder internal, @ZenCodeType.OptionalBoolean(true) Boolean sprinting) {
      return internal.setSprinting(sprinting);
   }

   @ZenCodeType.Method
   public static Builder swimming(Builder internal, @ZenCodeType.OptionalBoolean(true) Boolean swimming) {
      return internal.setSwimming(swimming);
   }

   @ZenCodeType.Method
   public static Builder baby(Builder internal, @ZenCodeType.OptionalBoolean(true) Boolean baby) {
      return internal.setIsBaby(baby);
   }

   @ZenCodeType.Method
   public static EntityFlagsPredicate build(Builder internal) {
      return internal.build();
   }
}
