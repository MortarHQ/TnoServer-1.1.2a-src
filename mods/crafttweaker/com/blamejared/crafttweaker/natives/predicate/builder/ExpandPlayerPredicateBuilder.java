package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.Map;
import net.minecraft.advancements.critereon.GameTypePredicate;
import net.minecraft.advancements.critereon.PlayerPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.advancements.critereon.PlayerPredicate.Builder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/builder/PlayerPredicateBuilder")
@NativeTypeRegistration(value = Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.PlayerPredicateBuilder")
public final class ExpandPlayerPredicateBuilder {
   @ZenCodeType.Method
   public static Builder level(Builder internal, Ints level) {
      return internal.setLevel(level);
   }

   @ZenCodeType.Method
   public static Builder statistic(Builder internal, ResourceLocation type, ResourceLocation name, Ints value) {
      StatType<?> statType = (StatType<?>)BuiltInRegistries.STAT_TYPE.getOrThrow(ResourceKey.create(Registries.STAT_TYPE, type));
      Reference<?> holder = statType.getRegistry().getHolderOrThrow(GenericUtil.uncheck(ResourceKey.create(statType.getRegistry().key(), name)));
      return internal.addStat(GenericUtil.uncheck(statType), holder, value);
   }

   @ZenCodeType.Method
   public static Builder statistic(Builder internal, ResourceLocation type, String name, Ints value) {
      return statistic(internal, type, ResourceLocation.parse(name), value);
   }

   @ZenCodeType.Method
   public static Builder statistic(Builder internal, String type, ResourceLocation name, Ints value) {
      return statistic(internal, ResourceLocation.parse(type), name, value);
   }

   @ZenCodeType.Method
   public static Builder statistic(Builder internal, String type, String name, Ints value) {
      return statistic(internal, type, ResourceLocation.parse(name), value);
   }

   @ZenCodeType.Method
   public static Builder recipe(Builder internal, ResourceLocation name, @ZenCodeType.OptionalBoolean(true) boolean unlocked) {
      return internal.addRecipe(name, unlocked);
   }

   @ZenCodeType.Method
   public static Builder recipe(Builder internal, String name, @ZenCodeType.OptionalBoolean(true) boolean unlocked) {
      return recipe(internal, ResourceLocation.parse(name), unlocked);
   }

   @ZenCodeType.Method
   public static Builder gameType(Builder internal, GameTypePredicate predicate) {
      return internal.setGameType(predicate);
   }

   @ZenCodeType.Method
   public static Builder lookingAt(Builder internal, net.minecraft.advancements.critereon.EntityPredicate.Builder predicate) {
      return internal.setLookingAt(predicate);
   }

   @ZenCodeType.Method
   public static Builder advancement(Builder internal, ResourceLocation name, @ZenCodeType.OptionalBoolean(true) boolean completed) {
      return internal.checkAdvancementDone(name, completed);
   }

   @ZenCodeType.Method
   public static Builder advancement(Builder internal, String name, @ZenCodeType.OptionalBoolean(true) boolean completed) {
      return advancement(internal, ResourceLocation.parse(name), completed);
   }

   @ZenCodeType.Method
   public static Builder advancementCriteria(Builder internal, ResourceLocation name, Map<String, Boolean> criteria) {
      return internal.checkAdvancementCriterions(name, criteria);
   }

   @ZenCodeType.Method
   public static Builder advancementCriteria(Builder internal, String name, Map<String, Boolean> criteria) {
      return advancementCriteria(internal, ResourceLocation.parse(name), criteria);
   }

   @ZenCodeType.Method
   public static Builder advancementCriterion(Builder internal, ResourceLocation name, String criterion, @ZenCodeType.OptionalBoolean(true) boolean completed) {
      return advancementCriteria(internal, name, Map.of(criterion, completed));
   }

   @ZenCodeType.Method
   public static Builder advancementCriterion(Builder internal, String name, String criterion, @ZenCodeType.OptionalBoolean(true) boolean completed) {
      return advancementCriterion(internal, ResourceLocation.parse(name), criterion, completed);
   }

   @ZenCodeType.Method
   public static PlayerPredicate build(Builder internal) {
      return internal.build();
   }
}
