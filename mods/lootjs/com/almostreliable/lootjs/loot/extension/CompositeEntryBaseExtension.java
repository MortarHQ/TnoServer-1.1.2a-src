package com.almostreliable.lootjs.loot.extension;

import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.List;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;

public interface CompositeEntryBaseExtension {
   List<LootPoolEntryContainer> lootjs$getEntries();

   @HideFromJS
   void lootjs$recompose();
}
