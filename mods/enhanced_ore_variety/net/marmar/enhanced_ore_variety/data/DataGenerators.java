package net.marmar.enhanced_ore_variety.data;

import java.util.concurrent.CompletableFuture;
import net.marmar.enhanced_ore_variety.data.lang.EnglishLangProvider;
import net.marmar.enhanced_ore_variety.data.lang.Spanish1LangProvider;
import net.marmar.enhanced_ore_variety.data.lang.Spanish2LangProvider;
import net.marmar.enhanced_ore_variety.data.loot.EOVLootTableProvider;
import net.marmar.enhanced_ore_variety.data.model.EOVBlockstateProvider;
import net.marmar.enhanced_ore_variety.data.recipe.EOVRecipeProvider;
import net.marmar.enhanced_ore_variety.data.tag.EOVBlockTagGenerator;
import net.marmar.enhanced_ore_variety.data.tag.EOVItemTagGenerator;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = "enhanced_ore_variety", bus = Bus.MOD)
public class DataGenerators {
   @SubscribeEvent
   public static void gatherData(GatherDataEvent event) {
      DataGenerator generator = event.getGenerator();
      PackOutput packOutput = generator.getPackOutput();
      ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
      CompletableFuture<Provider> lookupProvider = event.getLookupProvider();
      generator.addProvider(event.includeServer(), EOVLootTableProvider.create(packOutput, lookupProvider));
      generator.addProvider(event.includeServer(), new EOVRecipeProvider(packOutput, lookupProvider));
      EOVBlockTagGenerator blockTagGenerator = (EOVBlockTagGenerator)generator.addProvider(
         event.includeServer(), new EOVBlockTagGenerator(packOutput, lookupProvider, existingFileHelper)
      );
      generator.addProvider(event.includeServer(), new EOVItemTagGenerator(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));
      generator.addProvider(event.includeClient(), new EOVBlockstateProvider(packOutput, existingFileHelper));
      generator.addProvider(event.includeServer(), new WorldGenProvider(packOutput, lookupProvider));
      generator.addProvider(event.includeClient(), new EnglishLangProvider(packOutput));
      generator.addProvider(event.includeClient(), new Spanish2LangProvider(packOutput, "es_ar"));
      generator.addProvider(event.includeClient(), new Spanish2LangProvider(packOutput, "es_cl"));
      generator.addProvider(event.includeClient(), new Spanish2LangProvider(packOutput, "es_ec"));
      generator.addProvider(event.includeClient(), new Spanish2LangProvider(packOutput, "es_mx"));
      generator.addProvider(event.includeClient(), new Spanish1LangProvider(packOutput, "es_es"));
      generator.addProvider(event.includeClient(), new Spanish2LangProvider(packOutput, "es_uy"));
      generator.addProvider(event.includeClient(), new Spanish1LangProvider(packOutput, "es_ve"));
   }
}
