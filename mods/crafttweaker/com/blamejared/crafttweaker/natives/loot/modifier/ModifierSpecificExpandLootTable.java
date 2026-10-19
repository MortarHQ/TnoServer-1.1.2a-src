package com.blamejared.crafttweaker.natives.loot.modifier;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.loot.LootManager;
import com.blamejared.crafttweaker.api.loot.condition.LootConditions;
import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import com.blamejared.crafttweaker.impl.loot.condition.LootTableIdCondition;
import com.blamejared.crafttweaker.natives.loot.table.ExpandLootTable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.level.storage.loot.LootTable;
import org.openzen.zencode.java.ZenCodeType;

@Document("vanilla/api/loot/modifier/LootTableLootModifiers")
@ZenCodeType.Expansion("crafttweaker.api.loot.LootTable")
@ZenRegister
public final class ModifierSpecificExpandLootTable {
   @ZenCodeType.Method
   public static void addLootModifier(LootTable internal, String name, ILootModifier modifier) {
      LootManager.INSTANCE.getModifierManager().register(name, LootConditions.only(LootTableIdCondition.builder(ExpandLootTable.getId(internal))), modifier);
   }
}
