package io.github.Memoires.mysticism.neoforge.data.loot;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class MysticismLootProvider extends LootTableProvider {
   public MysticismLootProvider(PackOutput packOutput, CompletableFuture<Provider> provider) {
      super(packOutput, Set.of(), List.of(new SubProviderEntry(MysticismBlockLootProvider::new, LootContextParamSets.BLOCK)), provider);
   }
}
