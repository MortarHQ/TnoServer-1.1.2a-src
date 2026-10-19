package net.p3pp3rf1y.sophisticatedbackpacks.data;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class SBLootTableProvider extends LootTableProvider {
   SBLootTableProvider(PackOutput packOutput, CompletableFuture<Provider> registries) {
      super(
         packOutput,
         SBInjectLootSubProvider.ALL_TABLES,
         List.of(
            new SubProviderEntry(SBPBlockLootSubProvider::new, LootContextParamSets.BLOCK),
            new SubProviderEntry(SBInjectLootSubProvider::new, LootContextParamSets.CHEST)
         ),
         registries
      );
   }
}
