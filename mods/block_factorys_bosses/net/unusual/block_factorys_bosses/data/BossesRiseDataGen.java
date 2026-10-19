package net.unusual.block_factorys_bosses.data;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.unusual.block_factorys_bosses.data.gen.BossesRiseAdvancementGen;
import net.unusual.block_factorys_bosses.data.gen.BossesRiseBlockModelGen;
import net.unusual.block_factorys_bosses.data.gen.BossesRiseCraftingGen;
import net.unusual.block_factorys_bosses.data.gen.BossesRiseDataMapGen;
import net.unusual.block_factorys_bosses.data.gen.BossesRiseLangGen;
import net.unusual.block_factorys_bosses.data.gen.BossesRiseLootGen;
import net.unusual.block_factorys_bosses.data.gen.BossesRiseSoundGen;
import net.unusual.block_factorys_bosses.data.gen.tag.BossesRiseBiomeTagGen;
import net.unusual.block_factorys_bosses.data.gen.tag.BossesRiseBlockTagGen;
import net.unusual.block_factorys_bosses.data.gen.tag.BossesRiseDamageTypeTagGen;
import net.unusual.block_factorys_bosses.data.gen.tag.BossesRiseEntityTagGen;
import net.unusual.block_factorys_bosses.data.gen.tag.BossesRiseItemTagGen;

@EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseDataGen {
   @SubscribeEvent
   public static void gatherData(GatherDataEvent event) {
      DataGenerator generator = event.getGenerator();
      PackOutput output = event.getGenerator().getPackOutput();
      ExistingFileHelper helper = event.getExistingFileHelper();
      DatapackBuiltinEntriesProvider datapackProvider = new BossesRiseRegistryGen(output, event.getLookupProvider());
      CompletableFuture<Provider> provider = datapackProvider.getRegistryProvider();
      generator.addProvider(event.includeServer(), datapackProvider);
      generator.addProvider(event.includeServer(), new BossesRiseLootGen(output, provider));
      generator.addProvider(event.includeServer(), new AdvancementProvider(output, provider, helper, List.of(new BossesRiseAdvancementGen())));
      BossesRiseBlockTagGen blockTagGen = (BossesRiseBlockTagGen)generator.addProvider(
         event.includeServer(), new BossesRiseBlockTagGen(output, provider, helper)
      );
      generator.addProvider(event.includeServer(), new BossesRiseItemTagGen(output, provider, blockTagGen, helper));
      generator.addProvider(event.includeServer(), new BossesRiseEntityTagGen(output, provider, helper));
      generator.addProvider(event.includeServer(), new BossesRiseBiomeTagGen(output, provider, helper));
      generator.addProvider(event.includeServer(), new BossesRiseDamageTypeTagGen(output, provider, helper));
      generator.addProvider(event.includeServer(), new BossesRiseCraftingGen(output, provider));
      generator.addProvider(event.includeServer(), new BossesRiseDataMapGen(output, provider));
      generator.addProvider(event.includeClient(), new BossesRiseBlockModelGen(output, helper));
      generator.addProvider(event.includeClient(), new BossesRiseSoundGen(output, helper));
      generator.addProvider(event.includeClient(), new BossesRiseLangGen(output));
   }
}
