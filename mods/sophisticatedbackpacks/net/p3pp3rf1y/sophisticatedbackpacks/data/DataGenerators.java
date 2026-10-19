package net.p3pp3rf1y.sophisticatedbackpacks.data;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DataGenerators {
   private DataGenerators() {
   }

   public static void gatherData(GatherDataEvent evt) {
      DataGenerator generator = evt.getGenerator();
      PackOutput packOutput = generator.getPackOutput();
      CompletableFuture<Provider> registries = evt.getLookupProvider();
      BlockTagsProvider blockTagProvider = new BlockTagsProvider(packOutput, evt.getLookupProvider(), "sophisticatedbackpacks", evt.getExistingFileHelper()) {
         protected void addTags(Provider pProvider) {
         }
      };
      generator.addProvider(evt.includeServer(), blockTagProvider);
      generator.addProvider(
         evt.includeServer(), new ItemTagProvider(packOutput, evt.getLookupProvider(), blockTagProvider.contentsGetter(), evt.getExistingFileHelper())
      );
      generator.addProvider(evt.includeServer(), new SBLootTableProvider(packOutput, registries));
      generator.addProvider(evt.includeServer(), new SBLootModifierProvider(packOutput, registries));
      generator.addProvider(evt.includeServer(), new SBPRecipeProvider(packOutput, registries));
   }
}
