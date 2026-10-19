package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/InvertedLootCondition")
@NativeTypeRegistration(value = InvertedLootItemCondition.class, zenCodeName = "crafttweaker.api.loot.condition.InvertedLootCondition")
public final class ExpandInvertedLootItemCondition {
   @ZenCodeType.StaticExpansionMethod
   public static Builder create(Builder builder) {
      return InvertedLootItemCondition.invert(builder);
   }
}
