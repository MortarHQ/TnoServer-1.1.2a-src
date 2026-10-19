package net.marmar.enhanced_ore_variety.data.loot;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class EOVLootTableProvider {
   public static LootTableProvider create(PackOutput output, CompletableFuture<Provider> LookupProvider) {
      return new LootTableProvider(
         output, Collections.emptySet(), List.of(new SubProviderEntry(EOVBlockLootTables::new, LootContextParamSets.BLOCK)), LookupProvider
      );
   }
}
