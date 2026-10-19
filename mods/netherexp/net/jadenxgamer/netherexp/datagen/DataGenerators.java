package net.jadenxgamer.netherexp.datagen;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = "netherexp")
public class DataGenerators {
   @SubscribeEvent
   public static void gatherData(GatherDataEvent event) {
      DataGenerator generator = event.getGenerator();
      PackOutput output = generator.getPackOutput();
      ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
      CompletableFuture<Provider> lookupProvider = event.getLookupProvider();
      generator.addProvider(event.includeClient(), new JNEBlockModelProvider(output, existingFileHelper));
      generator.addProvider(event.includeClient(), new JNEItemModelProvider(output, existingFileHelper));
      generator.addProvider(event.includeClient(), new JNEBlockStateProvider(output, existingFileHelper));
      generator.addProvider(
         event.includeServer(),
         new LootTableProvider(
            output, Collections.emptySet(), List.of(new SubProviderEntry(JNEBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider
         )
      );
      generator.addProvider(event.includeServer(), new JNEBlockTagProvider(output, lookupProvider, existingFileHelper));
   }
}
