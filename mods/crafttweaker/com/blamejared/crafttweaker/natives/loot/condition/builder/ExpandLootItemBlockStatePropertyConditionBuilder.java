package com.blamejared.crafttweaker.natives.loot.condition.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition.Builder;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/builder/BlockStatePropertyLootConditionBuilder")
@NativeTypeRegistration(value = Builder.class, zenCodeName = "crafttweaker.api.loot.condition.builder.BlockStatePropertyLootConditionBuilder")
public final class ExpandLootItemBlockStatePropertyConditionBuilder {
   @ZenCodeType.Method
   public static Builder properties(Builder builder, net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder predicate) {
      return builder.setProperties(predicate);
   }
}
