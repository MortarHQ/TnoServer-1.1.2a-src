package com.blamejared.crafttweaker.natives.loot.condition.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition.Builder;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/builder/AllOfConditionBuilder")
@NativeTypeRegistration(value = Builder.class, zenCodeName = "crafttweaker.api.loot.condition.builder.AllOfConditionBuilder")
public final class ExpandAllOfConditionBuilder {
   @ZenCodeType.Method
   public static Builder and(Builder internal, net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder condition) {
      return internal.and(condition);
   }
}
