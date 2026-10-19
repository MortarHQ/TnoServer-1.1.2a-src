package io.github.manasmods.tensura_neb.neoforge;

import io.github.manasmods.tensura_neb.TensuraNeb;
import io.github.manasmods.tensura_neb.neoforge.data.NebAdvancementProvider;
import io.github.manasmods.tensura_neb.neoforge.data.NebBlockTagProvider;
import io.github.manasmods.tensura_neb.neoforge.data.NebEntityTypeTagProvider;
import io.github.manasmods.tensura_neb.neoforge.data.NebItemModelProvider;
import io.github.manasmods.tensura_neb.neoforge.data.NebItemTagProvider;
import io.github.manasmods.tensura_neb.neoforge.data.NebLanguageProvider;
import io.github.manasmods.tensura_neb.neoforge.data.NebLootProvider;
import io.github.manasmods.tensura_neb.neoforge.data.NebRegistryProvider;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod("tensura_neb")
public final class TensuraNebNeoForge {
   public TensuraNebNeoForge(IEventBus bus) {
      TensuraNeb.init();
      bus.addListener(this::gatherData);
   }

   public void gatherData(GatherDataEvent event) {
      DataGenerator generator = event.getGenerator();
      PackOutput output = generator.getPackOutput();
      CompletableFuture<Provider> lookupProvider = event.getLookupProvider();
      ExistingFileHelper helper = event.getExistingFileHelper();
      generator.addProvider(event.includeClient(), new NebItemModelProvider(output, helper));
      generator.addProvider(event.includeClient(), new NebLanguageProvider(output));
      NebBlockTagProvider blockTagsProvider = new NebBlockTagProvider(output, lookupProvider, helper);
      generator.addProvider(event.includeServer(), blockTagsProvider);
      generator.addProvider(event.includeServer(), new NebItemTagProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), helper));
      generator.addProvider(event.includeServer(), new NebEntityTypeTagProvider(output, lookupProvider, helper));
      DatapackBuiltinEntriesProvider registryProvider = new NebRegistryProvider(output, lookupProvider);
      generator.addProvider(event.includeServer(), registryProvider);
      generator.addProvider(event.includeServer(), new NebAdvancementProvider(output, lookupProvider, helper));
      generator.addProvider(event.includeServer(), new NebLootProvider(output, lookupProvider));
   }
}
