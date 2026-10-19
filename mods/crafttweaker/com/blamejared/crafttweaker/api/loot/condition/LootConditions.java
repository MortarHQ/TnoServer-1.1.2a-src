package com.blamejared.crafttweaker.api.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Arrays;
import java.util.function.Predicate;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.condition.LootConditions")
@Document("vanilla/api/loot/condition/LootConditions")
public class LootConditions {
   private final Predicate<LootContext> gather;

   private LootConditions(Predicate<LootContext> gather) {
      this.gather = gather;
   }

   @ZenCodeType.Method
   public static LootConditions none() {
      return new LootConditions(it -> true);
   }

   @ZenCodeType.Method
   public static LootConditions randomlyIn(double percentageChance) {
      return new LootConditions(it -> it.getRandom().nextDouble() <= percentageChance);
   }

   @ZenCodeType.Method
   public static LootConditions only(LootItemCondition condition) {
      return new LootConditions(condition);
   }

   @ZenCodeType.Method
   public static LootConditions only(Builder builder) {
      return only(builder.build());
   }

   @ZenCodeType.Method
   public static LootConditions allOf(LootItemCondition... conditions) {
      return new LootConditions(AllOfCondition.allOf(Arrays.asList(conditions)));
   }

   @ZenCodeType.Method
   public static LootConditions allOf(Builder... builders) {
      return allOf(Arrays.stream(builders).map(Builder::build).toArray(LootItemCondition[]::new));
   }

   @ZenCodeType.Method
   public static LootConditions anyOf(Builder... builders) {
      return new LootConditions(AnyOfCondition.anyOf(builders).build());
   }

   @ZenCodeType.Method
   public static LootConditions noneOf(LootItemCondition... conditions) {
      return allOf(conditions).flip();
   }

   @ZenCodeType.Method
   public static LootConditions noneOf(Builder... builders) {
      return noneOf(Arrays.stream(builders).map(Builder::build).toArray(LootItemCondition[]::new));
   }

   @ZenCodeType.Method
   public static LootConditions notAllOf(Builder... builders) {
      return anyOf(builders).flip();
   }

   public Predicate<LootContext> gather() {
      return this.gather;
   }

   private LootConditions flip() {
      return new LootConditions(this.gather.negate());
   }
}
