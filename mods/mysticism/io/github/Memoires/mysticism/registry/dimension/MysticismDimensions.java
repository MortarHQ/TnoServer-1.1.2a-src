package io.github.Memoires.mysticism.registry.dimension;

import com.mojang.datafixers.util.Pair;
import io.github.Memoires.mysticism.registry.world.MysticismBiomes;
import io.github.Memoires.mysticism.world.dimension.MysticismNoiseGeneratorSettings;
import java.util.List;
import java.util.OptionalLong;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.dimension.DimensionType.MonsterSettings;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class MysticismDimensions {
   public static final ResourceLocation ELEMENTAL_REALM_LOCATION = ResourceLocation.fromNamespaceAndPath("mysticism", "elemental_realm");
   public static final ResourceKey<Level> ELEMENTAL_REALM = ResourceKey.create(Registries.DIMENSION, ELEMENTAL_REALM_LOCATION);
   public static final ResourceKey<LevelStem> ELEMENTAL_REALM_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, ELEMENTAL_REALM_LOCATION);
   public static final ResourceKey<DimensionType> ELEMENTAL_REALM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, ELEMENTAL_REALM_LOCATION);
   public static final ResourceLocation KAMUI_DIMENSION_LOCATION = ResourceLocation.fromNamespaceAndPath("mysticism", "kamui_dimension");
   public static final ResourceKey<Level> KAMUI_DIMENSION = ResourceKey.create(Registries.DIMENSION, KAMUI_DIMENSION_LOCATION);
   public static final ResourceKey<LevelStem> KAMUI_DIMENSION_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, KAMUI_DIMENSION_LOCATION);
   public static final ResourceKey<DimensionType> KAMUI_DIMENSION_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, KAMUI_DIMENSION_LOCATION);

   public static void bootstrapLevelStem(BootstrapContext<LevelStem> context) {
      HolderGetter<Biome> biomeHolderGetter = context.lookup(Registries.BIOME);
      HolderGetter<DimensionType> dimensionTypeHolderGetter = context.lookup(Registries.DIMENSION_TYPE);
      HolderGetter<NoiseGeneratorSettings> noiseSettingsHolderGetter = context.lookup(Registries.NOISE_SETTINGS);
      ParameterList<Holder<Biome>> elementalRealmBiomes = new ParameterList(
         List.of(
            Pair.of(Climate.parameters(1.0F, -0.75F, 0.6F, 0.25F, 0.4F, 0.0F, 0.0F), (Holder)biomeHolderGetter.get(MysticismBiomes.FIRE_BIOME).get()),
            Pair.of(Climate.parameters(-0.25F, 1.0F, 0.25F, -0.25F, -0.5F, 0.0F, 0.0F), (Holder)biomeHolderGetter.get(MysticismBiomes.WATER_BIOME).get()),
            Pair.of(Climate.parameters(0.0F, 0.25F, 1.0F, -0.5F, -0.25F, 0.2F, 0.0F), (Holder)biomeHolderGetter.get(MysticismBiomes.EARTH_BIOME).get()),
            Pair.of(Climate.parameters(0.25F, -0.5F, -0.25F, 1.0F, 0.25F, 0.0F, 0.0F), (Holder)biomeHolderGetter.get(MysticismBiomes.WIND_BIOME).get()),
            Pair.of(Climate.parameters(0.5F, 0.1F, 0.1F, 0.4F, 0.9F, 0.0F, 0.0F), (Holder)biomeHolderGetter.get(MysticismBiomes.SPACE_BIOME).get()),
            Pair.of(Climate.parameters(-0.85F, 0.6F, -0.6F, -0.6F, -0.85F, 0.2F, 0.0F), (Holder)biomeHolderGetter.get(MysticismBiomes.LIGHT_BIOME).get()),
            Pair.of(Climate.parameters(-0.5F, 0.5F, -0.5F, 0.75F, 0.75F, 0.0F, 0.0F), (Holder)biomeHolderGetter.get(MysticismBiomes.DARKNESS_BIOME).get())
         )
      );
      NoiseBasedChunkGenerator elementalRealmChunk = new NoiseBasedChunkGenerator(
         MultiNoiseBiomeSource.createFromList(elementalRealmBiomes),
         noiseSettingsHolderGetter.getOrThrow(MysticismNoiseGeneratorSettings.ELEMENATAL_REALM_NOISE)
      );
      LevelStem elementalRealmStem = new LevelStem(dimensionTypeHolderGetter.getOrThrow(ELEMENTAL_REALM_TYPE), elementalRealmChunk);
      context.register(ELEMENTAL_REALM_LEVEL_STEM, elementalRealmStem);
      BiomeSource biomeSource = new FixedBiomeSource(biomeHolderGetter.getOrThrow(MysticismBiomes.KAMUI_BIOME));
      NoiseBasedChunkGenerator generator = new NoiseBasedChunkGenerator(
         biomeSource, noiseSettingsHolderGetter.getOrThrow(MysticismNoiseGeneratorSettings.KAMUI_DIMENSION_NOISE)
      );
      LevelStem kamuiStem = new LevelStem(dimensionTypeHolderGetter.getOrThrow(KAMUI_DIMENSION_TYPE), generator);
      context.register(KAMUI_DIMENSION_LEVEL_STEM, kamuiStem);
   }

   public static void bootstrapDimensionType(BootstrapContext<DimensionType> context) {
      DimensionType elementalRealmType = new DimensionType(
         OptionalLong.of(6000L),
         true,
         false,
         false,
         true,
         1.0,
         false,
         true,
         -64,
         320,
         320,
         BlockTags.INFINIBURN_END,
         ELEMENTAL_REALM_LOCATION,
         0.0F,
         new MonsterSettings(true, false, UniformInt.of(15, 15), 15)
      );
      context.register(ELEMENTAL_REALM_TYPE, elementalRealmType);
      DimensionType kamuiDimensionType = new DimensionType(
         OptionalLong.of(6000L),
         true,
         false,
         false,
         true,
         10.0,
         false,
         true,
         -64,
         320,
         320,
         BlockTags.INFINIBURN_END,
         KAMUI_DIMENSION_LOCATION,
         0.0F,
         new MonsterSettings(true, false, UniformInt.of(0, 7), 7)
      );
      context.register(KAMUI_DIMENSION_TYPE, kamuiDimensionType);
   }
}
