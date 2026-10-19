package com.blamejared.crafttweaker.natives.loot.condition.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition.Builder;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/builder/AnyOfConditionBuilder")
@NativeTypeRegistration(value = Builder.class, zenCodeName = "crafttweaker.api.loot.condition.builder.AnyOfConditionBuilder")
public final class ExpandAnyOfConditionBuilder {
   @ZenCodeType.Method
   public static Builder or(Builder internal, net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder condition) {
      return internal.or(condition);
   }
}
