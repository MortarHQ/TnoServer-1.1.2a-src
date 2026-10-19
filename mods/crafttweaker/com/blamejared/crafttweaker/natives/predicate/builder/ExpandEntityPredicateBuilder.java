package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.DistancePredicate;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.advancements.critereon.EntityPredicate.Builder;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/builder/EntityPredicateBuilder")
@NativeTypeRegistration(value = Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.EntityPredicateBuilder")
public final class ExpandEntityPredicateBuilder {
   @ZenCodeType.Method
   public static Builder entityType(Builder internal, EntityTypePredicate predicate) {
      return internal.entityType(predicate);
   }

   @ZenCodeType.Method
   public static Builder distance(Builder internal, DistancePredicate predicate) {
      return internal.distance(predicate);
   }

   @ZenCodeType.Method
   public static Builder located(Builder internal, net.minecraft.advancements.critereon.LocationPredicate.Builder predicate) {
      return internal.located(predicate);
   }

   @ZenCodeType.Method
   public static Builder steppingOn(Builder internal, net.minecraft.advancements.critereon.LocationPredicate.Builder predicate) {
      return internal.steppingOn(predicate);
   }

   @ZenCodeType.Method
   public static Builder effects(Builder internal, net.minecraft.advancements.critereon.MobEffectsPredicate.Builder predicate) {
      return internal.effects(predicate);
   }

   @ZenCodeType.Method
   public static Builder nbt(Builder internal, NbtPredicate predicate) {
      return internal.nbt(predicate);
   }

   @ZenCodeType.Method
   public static Builder flags(Builder internal, net.minecraft.advancements.critereon.EntityFlagsPredicate.Builder predicate) {
      return internal.flags(predicate);
   }

   @ZenCodeType.Method
   public static Builder equipment(Builder internal, EntityEquipmentPredicate predicate) {
      return internal.equipment(predicate);
   }

   @ZenCodeType.Method
   public static Builder equipment(Builder internal, net.minecraft.advancements.critereon.EntityEquipmentPredicate.Builder predicate) {
      return equipment(internal, predicate.build());
   }

   @ZenCodeType.Method
   public static Builder subPredicate(Builder internal, EntitySubPredicate predicate) {
      return internal.subPredicate(predicate);
   }

   @ZenCodeType.Method
   public static Builder subPredicate(Builder internal, net.minecraft.advancements.critereon.PlayerPredicate.Builder predicate) {
      return subPredicate(internal, predicate.build());
   }

   @ZenCodeType.Method
   public static Builder vehicle(Builder internal, Builder predicate) {
      return internal.vehicle(predicate);
   }

   @ZenCodeType.Method
   public static Builder passenger(Builder internal, Builder predicate) {
      return internal.passenger(predicate);
   }

   @ZenCodeType.Method
   public static Builder target(Builder internal, Builder predicate) {
      return internal.targetedEntity(predicate);
   }

   @ZenCodeType.Method
   public static Builder team(Builder internal, String team) {
      return internal.team(team);
   }

   @ZenCodeType.Method
   public static EntityPredicate build(Builder internal) {
      return internal.build();
   }
}
