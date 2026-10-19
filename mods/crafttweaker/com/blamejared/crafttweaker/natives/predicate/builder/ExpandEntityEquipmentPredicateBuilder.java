package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate.Builder;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/builder/EntityEquipmentPredicateBuilder")
@NativeTypeRegistration(value = Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.EntityEquipmentPredicateBuilder")
public final class ExpandEntityEquipmentPredicateBuilder {
   @ZenCodeType.Method
   public static Builder head(Builder internal, net.minecraft.advancements.critereon.ItemPredicate.Builder predicate) {
      return internal.head(predicate);
   }

   @ZenCodeType.Method
   public static Builder chest(Builder internal, net.minecraft.advancements.critereon.ItemPredicate.Builder predicate) {
      return internal.chest(predicate);
   }

   @ZenCodeType.Method
   public static Builder legs(Builder internal, net.minecraft.advancements.critereon.ItemPredicate.Builder predicate) {
      return internal.legs(predicate);
   }

   @ZenCodeType.Method
   public static Builder feet(Builder internal, net.minecraft.advancements.critereon.ItemPredicate.Builder predicate) {
      return internal.feet(predicate);
   }

   @ZenCodeType.Method
   public static Builder mainHand(Builder internal, net.minecraft.advancements.critereon.ItemPredicate.Builder predicate) {
      return internal.mainhand(predicate);
   }

   @ZenCodeType.Method
   public static Builder offHand(Builder internal, net.minecraft.advancements.critereon.ItemPredicate.Builder predicate) {
      return internal.offhand(predicate);
   }

   @ZenCodeType.Method
   public static EntityEquipmentPredicate build(Builder internal) {
      return internal.build();
   }
}
