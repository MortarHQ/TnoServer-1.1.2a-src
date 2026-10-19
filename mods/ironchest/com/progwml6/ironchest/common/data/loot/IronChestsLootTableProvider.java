package com.progwml6.ironchest.common.data.loot;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class IronChestsLootTableProvider extends LootTableProvider {
   public IronChestsLootTableProvider(PackOutput pOutput, CompletableFuture<Provider> provider) {
      super(pOutput, Collections.emptySet(), List.of(new SubProviderEntry(IronChestsBlockLoot::new, LootContextParamSets.BLOCK)), provider);
   }
}
