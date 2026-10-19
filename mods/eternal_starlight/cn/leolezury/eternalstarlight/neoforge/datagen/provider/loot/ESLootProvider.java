package cn.leolezury.eternalstarlight.neoforge.datagen.provider.loot;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class ESLootProvider extends LootTableProvider {
   public ESLootProvider(PackOutput packOutput, CompletableFuture<Provider> provider) {
      super(
         packOutput,
         Set.of(),
         List.of(
            new SubProviderEntry(ESBlockLootSubProvider::new, LootContextParamSets.BLOCK),
            new SubProviderEntry(ESEntityLootSubProvider::new, LootContextParamSets.ENTITY),
            new SubProviderEntry(ESChestLootSubProvider::new, LootContextParamSets.CHEST),
            new SubProviderEntry(ESBarteringLootSubProvider::new, LootContextParamSets.PIGLIN_BARTER),
            new SubProviderEntry(ESGiftLootSubProvider::new, LootContextParamSets.GIFT),
            new SubProviderEntry(ESFishingLootSubProvider::new, LootContextParamSets.FISHING),
            new SubProviderEntry(ESBossLootSubProvider::new, LootContextParamSets.EMPTY)
         ),
         provider
      );
   }
}
