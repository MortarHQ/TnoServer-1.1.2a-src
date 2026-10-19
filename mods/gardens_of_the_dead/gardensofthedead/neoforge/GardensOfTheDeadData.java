package gardensofthedead.neoforge;

import gardensofthedead.neoforge.datagen.BiomeProvider;
import gardensofthedead.neoforge.datagen.BiomeTagsProvider;
import gardensofthedead.neoforge.datagen.BlockStateProvider;
import gardensofthedead.neoforge.datagen.BlockTagsProvider;
import gardensofthedead.neoforge.datagen.ConfiguredFeatureProvider;
import gardensofthedead.neoforge.datagen.DataMapProvider;
import gardensofthedead.neoforge.datagen.ItemTagsProvider;
import gardensofthedead.neoforge.datagen.LootTableProvider;
import gardensofthedead.neoforge.datagen.PlacedFeatureProvider;
import gardensofthedead.neoforge.datagen.RecipeProvider;
import gardensofthedead.neoforge.datagen.SoundDefinitionsProvider;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.Cloner.Factory;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DataPackRegistriesHooks;

@EventBusSubscriber(modid = "gardens_of_the_dead", bus = Bus.MOD)
public class GardensOfTheDeadData {
   @SubscribeEvent
   public static void gatherData(GatherDataEvent event) {
      DataGenerator generator = event.getGenerator();
      ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
      PackOutput packOutput = event.getGenerator().getPackOutput();
      CompletableFuture<Provider> lookupProvider = event.getLookupProvider();
      BlockTagsProvider blockTagsProvider = new BlockTagsProvider(packOutput, lookupProvider, existingFileHelper);
      generator.addProvider(event.includeServer(), blockTagsProvider);
      generator.addProvider(event.includeClient(), new BlockStateProvider(packOutput, existingFileHelper));
      generator.addProvider(event.includeServer(), new ItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
      generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, lookupProvider));
      generator.addProvider(event.includeServer(), new RecipeProvider(packOutput, lookupProvider));
      generator.addProvider(event.includeClient(), new SoundDefinitionsProvider(packOutput, existingFileHelper));
      generator.addProvider(event.includeServer(), new DataMapProvider(packOutput, lookupProvider));
      RegistrySetBuilder levelProvider = createLevelProvider();
      generator.addProvider(
         event.includeServer(),
         new DatapackBuiltinEntriesProvider(generator.getPackOutput(), event.getLookupProvider(), levelProvider, Set.of("gardens_of_the_dead"))
      );
      lookupProvider = lookupProvider.thenApply(r -> constructRegistries(r, levelProvider));
      generator.addProvider(event.includeServer(), new BiomeTagsProvider(packOutput, lookupProvider, existingFileHelper));
   }

   private static Provider constructRegistries(Provider original, RegistrySetBuilder datapackEntriesBuilder) {
      HashSet<? extends ResourceKey<? extends Registry<?>>> builderKeys = new HashSet<>(datapackEntriesBuilder.getEntryKeys());
      DataPackRegistriesHooks.getDataPackRegistriesWithDimensions()
         .filter(data -> !builderKeys.contains(data.key()))
         .forEach(data -> datapackEntriesBuilder.add(data.key(), context -> {}));
      Factory factory = new Factory();
      DataPackRegistriesHooks.getDataPackRegistriesWithDimensions().forEach(data -> data.runWithArguments(factory::addCodec));
      return datapackEntriesBuilder.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), original, factory).full();
   }

   public static RegistrySetBuilder createLevelProvider() {
      RegistrySetBuilder builder = new RegistrySetBuilder();
      builder.add(Registries.CONFIGURED_FEATURE, ConfiguredFeatureProvider::create);
      builder.add(Registries.PLACED_FEATURE, PlacedFeatureProvider::create);
      builder.add(Registries.BIOME, BiomeProvider::create);
      return builder;
   }
}
