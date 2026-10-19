package com.blamejared.crafttweaker.api.loot;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.loot.modifier.LootModifierManager;
import com.blamejared.crafttweaker.api.loot.table.LootTableManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.LootManager")
@Document("vanilla/api/loot/LootManager")
public class LootManager {
   @ZenCodeGlobals.Global("loot")
   public static final LootManager INSTANCE = new LootManager();

   private LootManager() {
   }

   @ZenCodeType.Getter("modifiers")
   public LootModifierManager getModifierManager() {
      return LootModifierManager.INSTANCE;
   }

   @ZenCodeType.Getter("tables")
   public LootTableManager getTableManager() {
      return LootTableManager.INSTANCE;
   }
}
