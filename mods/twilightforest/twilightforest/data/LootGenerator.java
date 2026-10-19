package twilightforest.data;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.util.ProblemReporter.Collector;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import twilightforest.loot.TFLootTables;

public class LootGenerator extends LootTableProvider {
   public LootGenerator(PackOutput output, CompletableFuture<Provider> provider) {
      super(
         output,
         TFLootTables.allBuiltin(),
         List.of(
            new SubProviderEntry(BlockLootTables::new, LootContextParamSets.BLOCK),
            new SubProviderEntry(ChestLootTables::new, LootContextParamSets.CHEST),
            new SubProviderEntry(EntityLootTables::new, LootContextParamSets.ENTITY),
            new SubProviderEntry(SpecialLootTables::new, LootContextParamSets.EMPTY)
         ),
         provider
      );
   }

   protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext, Collector problemreporter$collector) {
   }
}
