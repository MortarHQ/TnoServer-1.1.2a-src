package io.github.manasmods.tensura_iaf.neoforge;

import io.github.manasmods.tensura_iaf.TensuraIaf;
import io.github.manasmods.tensura_iaf.neoforge.data.IafBlockTagProvider;
import io.github.manasmods.tensura_iaf.neoforge.data.IafEntityTypeTagProvider;
import io.github.manasmods.tensura_iaf.neoforge.data.IafItemTagProvider;
import io.github.manasmods.tensura_iaf.neoforge.data.IafRegistryProvider;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod("tensura_iaf")
public final class TensuraIafNeoForge {
   public TensuraIafNeoForge(IEventBus bus) {
      TensuraIaf.init();
      bus.addListener(this::gatherData);
   }

   public void gatherData(GatherDataEvent event) {
      DataGenerator generator = event.getGenerator();
      PackOutput output = generator.getPackOutput();
      CompletableFuture<Provider> lookupProvider = event.getLookupProvider();
      ExistingFileHelper helper = event.getExistingFileHelper();
      IafBlockTagProvider blockTagsProvider = new IafBlockTagProvider(output, lookupProvider, helper);
      generator.addProvider(event.includeServer(), blockTagsProvider);
      generator.addProvider(event.includeServer(), new IafItemTagProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), helper));
      generator.addProvider(event.includeServer(), new IafEntityTypeTagProvider(output, lookupProvider, helper));
      DatapackBuiltinEntriesProvider registryProvider = new IafRegistryProvider(output, lookupProvider);
      generator.addProvider(event.includeServer(), registryProvider);
   }
}
