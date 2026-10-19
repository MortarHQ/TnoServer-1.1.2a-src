package fuzs.puzzleslib.neoforge.api.data.v2.core;

import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class NeoForgeDataProviderContext extends DataProviderContext {
   private final ExistingFileHelper fileHelper;

   public NeoForgeDataProviderContext(String modId, PackOutput packOutput, CompletableFuture<Provider> registries, ExistingFileHelper fileHelper) {
      super(modId, packOutput, registries);
      this.fileHelper = fileHelper;
   }

   public static NeoForgeDataProviderContext fromEvent(GatherDataEvent event, PackOutput packOutput, CompletableFuture<Provider> registries) {
      return new NeoForgeDataProviderContext(event.getModContainer().getModId(), packOutput, registries, event.getExistingFileHelper());
   }

   public NeoForgeDataProviderContext withRegistries(CompletableFuture<Provider> registries) {
      return new NeoForgeDataProviderContext(this.getModId(), this.getPackOutput(), registries, this.fileHelper);
   }

   public ExistingFileHelper getFileHelper() {
      return this.fileHelper;
   }

   @FunctionalInterface
   public interface Factory extends Function<NeoForgeDataProviderContext, DataProvider> {
   }

   @FunctionalInterface
   public interface LegacyFactory extends BiFunction<GatherDataEvent, String, DataProvider> {
   }
}
