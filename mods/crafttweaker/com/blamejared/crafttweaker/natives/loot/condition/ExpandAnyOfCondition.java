package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition.Builder;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/AnyOfCondition")
@NativeTypeRegistration(value = AnyOfCondition.class, zenCodeName = "crafttweaker.api.loot.condition.AnyOfCondition")
public final class ExpandAnyOfCondition {
   @ZenCodeType.StaticExpansionMethod
   public Builder create() {
      return new Builder(new net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder[0]);
   }
}
