package nadiendev.constructionwand.data;

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
public class ModData {
   @SubscribeEvent
   public static void gatherData(GatherDataEvent event) {
      DataGenerator generator = event.getGenerator();
      PackOutput packOutput = generator.getPackOutput();
      ExistingFileHelper fileHelper = event.getExistingFileHelper();
      CompletableFuture<Provider> lookupProvider = event.getLookupProvider();
      if (event.includeServer()) {
         generator.addProvider(true, new RecipeGenerator(packOutput, lookupProvider));
         generator.addProvider(true, new AdvancementGenerator(packOutput, lookupProvider, fileHelper));
      }

      if (event.includeClient()) {
         generator.addProvider(true, new ItemModelGenerator(packOutput, fileHelper));
         generator.addProvider(true, new LanguageGenerator(packOutput));
         generator.addProvider(true, new LanguageGenerator.ESAR(packOutput));
         generator.addProvider(true, new LanguageGenerator.ESCL(packOutput));
         generator.addProvider(true, new LanguageGenerator.ESCO(packOutput));
         generator.addProvider(true, new LanguageGenerator.ESES(packOutput));
         generator.addProvider(true, new LanguageGenerator.ESMX(packOutput));
         generator.addProvider(true, new LanguageGenerator.JAJP(packOutput));
         generator.addProvider(true, new LanguageGenerator.KOKR(packOutput));
         generator.addProvider(true, new LanguageGenerator.PTBR(packOutput));
         generator.addProvider(true, new LanguageGenerator.RURU(packOutput));
         generator.addProvider(true, new LanguageGenerator.SVSE(packOutput));
         generator.addProvider(true, new LanguageGenerator.TRTR(packOutput));
         generator.addProvider(true, new LanguageGenerator.ZHCN(packOutput));
         generator.addProvider(true, new LanguageGenerator.DEDE(packOutput));
      }
   }
}
