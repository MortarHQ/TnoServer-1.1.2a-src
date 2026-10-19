package net.unusual.block_factorys_bosses.data.gen;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.util.ProblemReporter.Collector;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.unusual.block_factorys_bosses.data.gen.loot.BossesRiseLootBlockGen;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseLootGen extends LootTableProvider {
   public BossesRiseLootGen(PackOutput output, CompletableFuture<Provider> provider) {
      super(output, Set.of(), List.of(new SubProviderEntry(BossesRiseLootBlockGen::new, LootContextParamSets.BLOCK)), provider);
   }

   protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext, Collector problemreporter$collector) {
   }
}
