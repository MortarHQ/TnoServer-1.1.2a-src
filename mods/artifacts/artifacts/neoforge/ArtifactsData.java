package artifacts.neoforge;

import artifacts.neoforge.data.Advancements;
import artifacts.neoforge.data.ConfiguredFeatures;
import artifacts.neoforge.data.DataMaps;
import artifacts.neoforge.data.ItemModels;
import artifacts.neoforge.data.Language;
import artifacts.neoforge.data.LootModifiers;
import artifacts.neoforge.data.LootTables;
import artifacts.neoforge.data.PlacedFeatures;
import artifacts.neoforge.data.SoundDefinitions;
import artifacts.neoforge.data.tags.BlockTags;
import artifacts.neoforge.data.tags.DamageTypeTags;
import artifacts.neoforge.data.tags.EntityTypeTags;
import artifacts.neoforge.data.tags.GameEventTags;
import artifacts.neoforge.data.tags.ItemTags;
import artifacts.neoforge.data.tags.MobEffectTags;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class ArtifactsData {
   public static void gatherData(GatherDataEvent event) {
      DataGenerator generator = event.getGenerator();
      ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
      PackOutput packOutput = event.getGenerator().getPackOutput();
      CompletableFuture<Provider> lookupProvider = event.getLookupProvider();
      BlockTags blockTags = new BlockTags(packOutput, lookupProvider, existingFileHelper);
      LootModifiers lootModifiers = new LootModifiers(packOutput, lookupProvider);
      generator.addProvider(event.includeServer(), blockTags);
      generator.addProvider(event.includeServer(), new ItemTags(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
      generator.addProvider(event.includeServer(), lootModifiers);
      generator.addProvider(event.includeServer(), new LootTables(packOutput, existingFileHelper, lootModifiers, lookupProvider));
      generator.addProvider(event.includeServer(), new EntityTypeTags(packOutput, lookupProvider, existingFileHelper));
      generator.addProvider(event.includeServer(), new MobEffectTags(packOutput, lookupProvider, existingFileHelper));
      generator.addProvider(event.includeServer(), new DamageTypeTags(packOutput, lookupProvider, existingFileHelper));
      generator.addProvider(event.includeServer(), new GameEventTags(packOutput, lookupProvider, existingFileHelper));
      generator.addProvider(event.includeServer(), new SoundDefinitions(packOutput, existingFileHelper));
      generator.addProvider(event.includeServer(), new Advancements(packOutput, lookupProvider, existingFileHelper));
      generator.addProvider(event.includeServer(), new DataMaps(packOutput, lookupProvider));
      generator.addProvider(event.includeClient(), new Language(packOutput));
      generator.addProvider(event.includeClient(), new ItemModels(packOutput, existingFileHelper));
      generator.addProvider(
         event.includeServer(),
         new DatapackBuiltinEntriesProvider(generator.getPackOutput(), event.getLookupProvider(), createLevelProvider(), Set.of("artifacts"))
      );
   }

   public static RegistrySetBuilder createLevelProvider() {
      RegistrySetBuilder builder = new RegistrySetBuilder();
      builder.add(Registries.CONFIGURED_FEATURE, ConfiguredFeatures::create);
      builder.add(Registries.PLACED_FEATURE, PlacedFeatures::create);
      return builder;
   }
}
