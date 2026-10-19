package de.ellpeck.naturesaura.data;

import de.ellpeck.naturesaura.compat.Compat;
import de.ellpeck.naturesaura.gen.ModFeatures;
import de.ellpeck.naturesaura.reg.IModItem;
import de.ellpeck.naturesaura.reg.ModRegistry;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.Cloner.Factory;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistrySetBuilder.PatchedRegistries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DataPackRegistriesHooks;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

@EventBusSubscriber(bus = Bus.MOD)
public final class ModData {
   @SubscribeEvent
   public static void gatherData(GatherDataEvent event) {
      DataGenerator gen = event.getGenerator();
      PackOutput out = gen.getPackOutput();
      CompletableFuture<Provider> lookup = event.getLookupProvider();
      ExistingFileHelper existing = event.getExistingFileHelper();
      Compat.gatherData(event);
      BlockTagProvider blockTags = new BlockTagProvider(out, lookup, existing);
      gen.addProvider(event.includeServer(), blockTags);
      gen.addProvider(event.includeServer(), new ItemTagProvider(out, lookup, blockTags.contentsGetter(), existing));
      gen.addProvider(
         event.includeServer(), new LootTableProvider(out, Set.of(), List.of(new SubProviderEntry(BlockLootProvider::new, LootContextParamSets.BLOCK)), lookup)
      );
      gen.addProvider(event.includeServer(), new BlockStateGenerator(out, existing));
      gen.addProvider(event.includeServer(), new ItemModelGenerator(out, existing));
      gen.addProvider(
         event.includeServer(), new DatapackBuiltinEntriesProvider(out, CompletableFuture.supplyAsync(ModData::getProvider), Set.of("naturesaura"))
      );
   }

   private static PatchedRegistries getProvider() {
      RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
      registryBuilder.add(Registries.CONFIGURED_FEATURE, ModFeatures.Configured::bootstrap);
      registryBuilder.add(Registries.PLACED_FEATURE, ModFeatures.Placed::bootstrap);
      registryBuilder.add(Keys.BIOME_MODIFIERS, BiomeModifiers::bootstrap);
      registryBuilder.add(Registries.BIOME, context -> {});
      Factory factory = new Factory();
      DataPackRegistriesHooks.getDataPackRegistriesWithDimensions().forEach(data -> data.runWithArguments(factory::addCodec));
      return registryBuilder.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), VanillaRegistries.createLookup(), factory);
   }

   public static Stream<IModItem> getAllModItems() {
      return ModRegistry.ALL_ITEMS.stream().sorted(Comparator.comparing(IModItem::getBaseName));
   }
}
