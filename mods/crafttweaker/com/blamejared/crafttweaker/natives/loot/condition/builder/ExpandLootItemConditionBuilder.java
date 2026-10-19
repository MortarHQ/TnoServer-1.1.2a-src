package com.blamejared.crafttweaker.natives.loot.condition.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/builder/LootConditionBuilder")
@NativeTypeRegistration(value = Builder.class, zenCodeName = "crafttweaker.api.loot.condition.builder.LootConditionBuilder")
public final class ExpandLootItemConditionBuilder {
   @ZenCodeType.Method
   public static LootItemCondition build(Builder internal) {
      return internal.build();
   }

   @ZenCodeType.Method
   public static net.minecraft.world.level.storage.loot.predicates.AnyOfCondition.Builder or(Builder internal, Builder other) {
      return internal.or(other);
   }

   @ZenCodeType.Method
   public static net.minecraft.world.level.storage.loot.predicates.AllOfCondition.Builder and(Builder internal, Builder other) {
      return internal.and(other);
   }

   @ZenCodeType.Method
   public static Builder invert(Builder internal) {
      return internal.invert();
   }
}
