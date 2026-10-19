package com.almostreliable.lootjs.loot.table;

import com.almostreliable.lootjs.core.entry.LootEntry;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;

public interface LootEntryAppender {
   default LootEntryAppender addCustomEntry(JsonObject json) {
      LootPoolEntryContainer vanilla = (LootPoolEntryContainer)LootPoolEntries.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();
      return this.addEntry(LootEntry.ofVanilla(vanilla));
   }

   LootEntryAppender addEntry(LootEntry var1);
}
