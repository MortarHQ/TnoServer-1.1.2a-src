package xueluoanping.swayinggarden.data;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import xueluoanping.swayinggarden.SwayingGarden;

public class start {
   public static final String MODID = "swaying_garden";

   public static void dataGen(GatherDataEvent event) {
      DataGenerator generator = event.getGenerator();
      ExistingFileHelper helper = event.getExistingFileHelper();
      PackOutput packOutput = generator.getPackOutput();
      CompletableFuture<Provider> lookupProvider = event.getLookupProvider();
      if (event.includeServer()) {
         SwayingGarden.logger("Generate We Data!!!");
      }

      if (event.includeClient()) {
      }
   }
}
