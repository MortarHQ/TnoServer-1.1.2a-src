package net.marmar.enhanced_ore_variety.data;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.marmar.enhanced_ore_variety.world.EOVBiomeModifiers;
import net.marmar.enhanced_ore_variety.world.EOVConfiguredFeatures;
import net.marmar.enhanced_ore_variety.world.EOVPlacedFeatures;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

public class WorldGenProvider extends DatapackBuiltinEntriesProvider {
   public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
      .add(Registries.CONFIGURED_FEATURE, EOVConfiguredFeatures::bootstrap)
      .add(Registries.PLACED_FEATURE, EOVPlacedFeatures::bootstrap)
      .add(Keys.BIOME_MODIFIERS, EOVBiomeModifiers::bootstrap);

   public WorldGenProvider(PackOutput output, CompletableFuture<Provider> registries) {
      super(output, registries, BUILDER, Set.of("enhanced_ore_variety"));
   }
}
