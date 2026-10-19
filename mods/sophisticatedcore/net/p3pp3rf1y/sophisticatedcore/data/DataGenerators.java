package net.p3pp3rf1y.sophisticatedcore.data;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DataGenerators {
   private DataGenerators() {
   }

   public static void gatherData(GatherDataEvent evt) {
      DataGenerator generator = evt.getGenerator();
      PackOutput packOutput = generator.getPackOutput();
      CompletableFuture<Provider> registries = evt.getLookupProvider();
      generator.addProvider(evt.includeServer(), new SCFluidTagsProvider(packOutput, registries, evt.getExistingFileHelper()));
      generator.addProvider(evt.includeServer(), new SCRecipeProvider(packOutput, registries));
   }
}
