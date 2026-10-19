package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition.Builder;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/AllOfCondition")
@NativeTypeRegistration(value = AllOfCondition.class, zenCodeName = "crafttweaker.api.loot.condition.AllOfCondition")
public final class ExpandAllOfCondition {
   @ZenCodeType.StaticExpansionMethod
   public Builder create() {
      return new Builder(new net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder[0]);
   }
}
