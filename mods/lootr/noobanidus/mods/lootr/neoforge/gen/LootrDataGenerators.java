package noobanidus.mods.lootr.neoforge.gen;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = "lootr")
public class LootrDataGenerators {
   @SubscribeEvent
   public static void gatherData(GatherDataEvent event) {
      if (event.getMods().contains("lootr")) {
         DataGenerator generator = event.getGenerator();
         PackOutput output = event.getGenerator().getPackOutput();
         CompletableFuture<Provider> provider = event.getLookupProvider();
         ExistingFileHelper helper = event.getExistingFileHelper();
         LootrBlockTagProvider blocks;
         generator.addProvider(event.includeServer(), blocks = new LootrBlockTagProvider(output, provider, helper));
         generator.addProvider(event.includeServer(), new LootrItemTagsProvider(output, provider, blocks.contentsGetter(), helper));
         generator.addProvider(event.includeClient(), new LootrAtlasGenerator(output, provider, helper));
         generator.addProvider(true, LootrLootTableProvider.create(output, provider));
         generator.addProvider(event.includeServer(), new LootrEntityTagsProvider(output, provider, helper));
         generator.addProvider(event.includeServer(), new LootrBlockEntityTagsProvider(output, provider, helper));
         generator.addProvider(event.includeServer(), new LootrStructureTagsProvider(output, provider, helper));
         generator.addProvider(event.includeClient(), new LootrLangProvider(output));
         generator.addProvider(event.includeServer(), new AdvancementProvider(output, provider, helper, List.of(new LootrAdvancementGenerator())));
         generator.addProvider(event.includeServer(), new LootrParticleProvider(output, helper));
      }
   }
}
