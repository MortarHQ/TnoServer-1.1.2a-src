package com.blamejared.crafttweaker.api.loot.table;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.table.LootTableManager")
@Document("vanilla/api/loot/table/LootTableManager")
public class LootTableManager {
   public static final LootTableManager INSTANCE = new LootTableManager();

   private LootTableManager() {
   }

   @ZenCodeType.Method
   public LootTable getTable(ResourceLocation name) {
      return (LootTable)this.getLootData().get(name);
   }

   @ZenCodeType.Getter("ids")
   public Set<ResourceLocation> getIds() {
      return this.getLootData().keySet();
   }

   private Registry<LootTable> getLootData() {
      if (!CraftTweakerAPI.getAccessibleElementsProvider().server().hasRegistryAccess()) {
         throw new IllegalStateException("Unable to get loot tables! Make sure that this method is only called from the server side!");
      } else {
         return CraftTweakerAPI.getAccessibleElementsProvider().server().registryAccess().registryOrThrow(Registries.LOOT_TABLE);
      }
   }
}
