package io.github.Memoires.mysticism.registry.world;

import io.github.Memoires.mysticism.registry.block.MysticismBlocks;
import io.github.Memoires.mysticism.world.features.ErodedRockMoundFeature;
import io.github.Memoires.mysticism.world.features.FireBlockBlobFeature;
import io.github.Memoires.mysticism.world.features.FloatingDebrisSpatialFeature;
import io.github.Memoires.mysticism.world.features.KamuiCubeFeature;
import io.github.Memoires.mysticism.world.features.LavaLakeFeature;
import io.github.Memoires.mysticism.world.features.StoneMountainFeature;
import io.github.Memoires.mysticism.world.features.WaterBlobBlockFeature;
import io.github.Memoires.mysticism.world.features.WaterCoarseDirtLakeFeature;
import io.github.Memoires.mysticism.world.features.WaterPodzolLakeFeature;
import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature.Configuration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class MysticismConfiguredFeatures {
   public static final ResourceKey<ConfiguredFeature<?, ?>> FIRE_BLOB = create("fire_blob");
   public static final ResourceKey<ConfiguredFeature<?, ?>> WATER_BLOB = create("water_blob");
   public static final ResourceKey<ConfiguredFeature<?, ?>> EARTH_BLOB = create("earth_blob");
   public static final ResourceKey<ConfiguredFeature<?, ?>> WIND_BLOB = create("wind_blob");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SPATIAL_BLOB = create("spatial_blob");
   public static final ResourceKey<ConfiguredFeature<?, ?>> CHORUS = create("chorus");
   public static final ResourceKey<ConfiguredFeature<?, ?>> LAVA_LAKE = create("lava_lake");
   public static final ResourceKey<ConfiguredFeature<?, ?>> PODZOL_LAKE = create("podzol_lake");
   public static final ResourceKey<ConfiguredFeature<?, ?>> COARSE_LAKE = create("coarse_lake");
   public static final ResourceKey<ConfiguredFeature<?, ?>> ICE_ORE = create("ice_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> KAMUI_CUBE = create("kamui_cube");

   public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
      FeatureUtils.register(context, FIRE_BLOB, (FireBlockBlobFeature)MysticismFeatures.FIRE_BLOB.get(), new NoneFeatureConfiguration());
      FeatureUtils.register(context, WATER_BLOB, (WaterBlobBlockFeature)MysticismFeatures.WATER_BLOB.get(), new NoneFeatureConfiguration());
      FeatureUtils.register(context, EARTH_BLOB, (ErodedRockMoundFeature)MysticismFeatures.EARTH_BLOB.get(), new NoneFeatureConfiguration());
      FeatureUtils.register(context, WIND_BLOB, (StoneMountainFeature)MysticismFeatures.WIND_BLOB.get(), new NoneFeatureConfiguration());
      FeatureUtils.register(context, SPATIAL_BLOB, (FloatingDebrisSpatialFeature)MysticismFeatures.SPATIAL_BLOB.get(), new NoneFeatureConfiguration());
      FeatureUtils.register(context, CHORUS, Feature.CHORUS_PLANT, new NoneFeatureConfiguration());
      FeatureUtils.register(
         context,
         LAVA_LAKE,
         (LavaLakeFeature)MysticismFeatures.LAVA_LAKE.get(),
         new Configuration(BlockStateProvider.simple(Blocks.LAVA.defaultBlockState()), BlockStateProvider.simple(Blocks.MAGMA_BLOCK.defaultBlockState()))
      );
      FeatureUtils.register(
         context,
         PODZOL_LAKE,
         (WaterPodzolLakeFeature)MysticismFeatures.PODZOL_LAKE.get(),
         new Configuration(BlockStateProvider.simple(Blocks.WATER.defaultBlockState()), BlockStateProvider.simple(Blocks.PODZOL.defaultBlockState()))
      );
      FeatureUtils.register(
         context,
         COARSE_LAKE,
         (WaterCoarseDirtLakeFeature)MysticismFeatures.COARSE_LAKE.get(),
         new Configuration(BlockStateProvider.simple(Blocks.WATER.defaultBlockState()), BlockStateProvider.simple(Blocks.COARSE_DIRT.defaultBlockState()))
      );
      FeatureUtils.register(context, KAMUI_CUBE, (KamuiCubeFeature)MysticismFeatures.KAMUI_CUBE.get(), new NoneFeatureConfiguration());
      FeatureUtils.register(
         context,
         ICE_ORE,
         Feature.ORE,
         new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.ICE), ((Block)MysticismBlocks.ICE_ORE.get()).defaultBlockState())), 3)
      );
   }

   public static ResourceKey<ConfiguredFeature<?, ?>> create(String name) {
      return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath("mysticism", name));
   }
}
