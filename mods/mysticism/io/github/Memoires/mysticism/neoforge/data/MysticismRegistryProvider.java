package io.github.Memoires.mysticism.neoforge.data;

import io.github.Memoires.mysticism.damage.MysticismDamageTypes;
import io.github.Memoires.mysticism.data.chunk.MysticismBiomeMagiculeModifiers;
import io.github.Memoires.mysticism.data.chunk.MysticismLevelMagiculeModifiers;
import io.github.Memoires.mysticism.data.existence.MysticismEntityExistenceData;
import io.github.Memoires.mysticism.data.existence.MysticismGearExistenceData;
import io.github.Memoires.mysticism.enchantment.MysticismEnchantments;
import io.github.Memoires.mysticism.registry.dimension.MysticismDimensions;
import io.github.Memoires.mysticism.registry.dimension.MysticismNoises;
import io.github.Memoires.mysticism.registry.world.MysticismBiomes;
import io.github.Memoires.mysticism.registry.world.MysticismConfiguredFeatures;
import io.github.Memoires.mysticism.registry.world.MysticismPlacedFeatures;
import io.github.Memoires.mysticism.world.dimension.MysticismNoiseGeneratorSettings;
import io.github.manasmods.tensura.registry.data.TensuraCustomData;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

public class MysticismRegistryProvider extends DatapackBuiltinEntriesProvider {
   public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
      .add(TensuraCustomData.ENTITY_EXISTENCE, MysticismEntityExistenceData::bootstrap)
      .add(Registries.NOISE_SETTINGS, MysticismNoiseGeneratorSettings::bootstrap)
      .add(Registries.ENCHANTMENT, MysticismEnchantments::bootstrap)
      .add(Registries.CONFIGURED_FEATURE, MysticismConfiguredFeatures::bootstrap)
      .add(Registries.PLACED_FEATURE, MysticismPlacedFeatures::bootstrap)
      .add(Registries.DIMENSION_TYPE, MysticismDimensions::bootstrapDimensionType)
      .add(Registries.LEVEL_STEM, MysticismDimensions::bootstrapLevelStem)
      .add(Registries.NOISE, MysticismNoises::bootstrap)
      .add(Registries.BIOME, MysticismBiomes::bootstrap)
      .add(TensuraCustomData.BIOME_MAGICULE, MysticismBiomeMagiculeModifiers::bootstrap)
      .add(TensuraCustomData.LEVEL_MAGICULE, MysticismLevelMagiculeModifiers::bootstrap)
      .add(Keys.BIOME_MODIFIERS, MysticismBiomeModifiers::bootstrap)
      .add(TensuraCustomData.GEAR_EXISTENCE, MysticismGearExistenceData::bootstrap)
      .add(Registries.DAMAGE_TYPE, MysticismDamageTypes::bootstrap);

   public MysticismRegistryProvider(PackOutput output, CompletableFuture<Provider> registries) {
      super(output, registries, BUILDER, Set.of("tensura", "minecraft", "mysticism"));
   }
}
