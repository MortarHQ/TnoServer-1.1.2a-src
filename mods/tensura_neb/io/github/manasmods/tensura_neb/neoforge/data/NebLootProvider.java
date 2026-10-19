package io.github.manasmods.tensura_neb.neoforge.data;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class NebLootProvider extends LootTableProvider {
   public NebLootProvider(PackOutput packOutput, CompletableFuture<Provider> provider) {
      super(packOutput, Set.of(), List.of(new SubProviderEntry(NebEntityLootProvider::new, LootContextParamSets.ENTITY)), provider);
   }
}
