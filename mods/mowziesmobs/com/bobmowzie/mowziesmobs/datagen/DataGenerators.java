package com.bobmowzie.mowziesmobs.datagen;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(bus = Bus.MOD)
public class DataGenerators {
   @SubscribeEvent
   public static void gatherData(GatherDataEvent event) {
      DataGenerator generator = event.getGenerator();
      PackOutput output = event.getGenerator().getPackOutput();
      CompletableFuture<Provider> provider = event.getLookupProvider();
      ExistingFileHelper fileHelper = event.getExistingFileHelper();
      MMBlockTags blockTags = new MMBlockTags(output, provider, fileHelper);
      generator.addProvider(event.includeServer(), blockTags);
      generator.addProvider(event.includeServer(), new MMItemTags(output, provider, blockTags.contentsGetter(), fileHelper));
      generator.addProvider(event.includeServer(), new MMEntityTypeTags(output, provider, fileHelper));
      generator.addProvider(event.includeServer(), new MMBiomeTags(output, provider, fileHelper));
      generator.addProvider(event.includeServer(), new MMRecipes(output, provider));
      generator.addProvider(event.includeServer(), new RegistryDataGenerator(output, provider));
   }
}
