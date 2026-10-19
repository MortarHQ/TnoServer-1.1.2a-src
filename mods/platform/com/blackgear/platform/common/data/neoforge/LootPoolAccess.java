package com.blackgear.platform.common.data.neoforge;

import java.util.List;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;

public interface LootPoolAccess {
   List<LootPoolEntryContainer> getEntries();

   void setEntries(List<LootPoolEntryContainer> var1);
}
