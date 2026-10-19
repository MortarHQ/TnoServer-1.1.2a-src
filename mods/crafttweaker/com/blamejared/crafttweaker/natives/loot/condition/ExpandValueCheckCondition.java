package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.predicates.ValueCheckCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/ValueCheckLootCondition")
@NativeTypeRegistration(value = ValueCheckCondition.class, zenCodeName = "crafttweaker.api.loot.condition.ValueCheckLootCondition")
public final class ExpandValueCheckCondition {
   @ZenCodeType.StaticExpansionMethod
   public static Builder create(NumberProvider provider, IntRange range) {
      return ValueCheckCondition.hasValue(provider, range);
   }
}
