package team.lodestar.lodestone.datagen;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = "lodestone")
public class DataGenerators {
   @SubscribeEvent
   public static void gatherData(GatherDataEvent event) {
      CompletableFuture<Provider> lookupProvider = event.getLookupProvider();
      PackOutput packOutput = event.getGenerator().getPackOutput();
      ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
      boolean includeClient = event.includeClient();
      boolean includeServer = event.includeServer();
      LodestoneBlockTagDatagen blockTagDatagen = new LodestoneBlockTagDatagen(packOutput, lookupProvider, existingFileHelper);
      LodestoneLangDatagen langDatagen = new LodestoneLangDatagen(packOutput);
      LodestoneItemTagDatagen itemTagDatagen = new LodestoneItemTagDatagen(packOutput, lookupProvider, blockTagDatagen.contentsGetter(), existingFileHelper);
      LodestoneDamageTypeDatagen damageTypeDatagen = new LodestoneDamageTypeDatagen(packOutput, lookupProvider, existingFileHelper);
      event.getGenerator().addProvider(includeClient, langDatagen);
      event.getGenerator().addProvider(includeServer, blockTagDatagen);
      event.getGenerator().addProvider(includeServer, itemTagDatagen);
      event.getGenerator().addProvider(includeServer, damageTypeDatagen);
   }
}
