package twilightforest.world.registration.biomes;

import it.unimi.dsi.fastutil.doubles.Double2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.doubles.Double2ObjectSortedMap;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Holder.Reference;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import org.jetbrains.annotations.Unmodifiable;
import twilightforest.init.TFBiomes;
import twilightforest.world.components.chunkgenerators.TerrainColumn;

public final class BiomeMaker extends BiomeHelper {
   public static @Unmodifiable List<TerrainColumn> makeBiomeList(HolderGetter<Biome> biomeRegistry, Holder<Biome> undergroundBiome) {
      return List.of(
         biomeColumnWithUnderground(-0.7, 4.2, 1.0, biomeRegistry, TFBiomes.FOREST, undergroundBiome),
         biomeColumnWithUnderground(-0.8, 3.9, 1.0, biomeRegistry, TFBiomes.DENSE_FOREST, undergroundBiome),
         biomeColumnWithUnderground(-0.75, 4.15, 1.0, biomeRegistry, TFBiomes.FIREFLY_FOREST, undergroundBiome),
         biomeColumnWithUnderground(0.05, 1.15, 1.0, biomeRegistry, TFBiomes.CLEARING, undergroundBiome),
         biomeColumnWithUnderground(-0.05, 2.0, 1.0, biomeRegistry, TFBiomes.OAK_SAVANNAH, undergroundBiome),
         biomeColumnWithUnderground(-0.1, 0.001, 1.35, biomeRegistry, TFBiomes.STREAM, undergroundBiome),
         biomeColumnWithUnderground(-2.2, 2.0, 1.0, biomeRegistry, TFBiomes.LAKE, undergroundBiome),
         biomeColumnWithUnderground(0.0, 2.0, 1.0, biomeRegistry, TFBiomes.MUSHROOM_FOREST, undergroundBiome),
         biomeColumnWithUnderground(0.0, 1.75, 1.0, biomeRegistry, TFBiomes.DENSE_MUSHROOM_FOREST, undergroundBiome),
         biomeColumnWithUnderground(-0.5, 4.0, 1.0, biomeRegistry, TFBiomes.ENCHANTED_FOREST, undergroundBiome),
         biomeColumnWithUnderground(0.0, 2.25, 1.0, biomeRegistry, TFBiomes.SPOOKY_FOREST, undergroundBiome),
         biomeColumnWithUnderground(-0.6, 1.7, 1.0, biomeRegistry, TFBiomes.SWAMP, undergroundBiome),
         biomeColumnWithUnderground(0.2, 1.25, 1.0, biomeRegistry, TFBiomes.FIRE_SWAMP, undergroundBiome),
         biomeColumnWithUnderground(0.1, 1.25, 0.5, biomeRegistry, TFBiomes.DARK_FOREST, undergroundBiome),
         biomeColumnWithUnderground(0.1, 1.125, 1.0, biomeRegistry, TFBiomes.DARK_FOREST_CENTER, undergroundBiome),
         biomeColumnWithUnderground(0.0, 2.45, 0.5, biomeRegistry, TFBiomes.SNOWY_FOREST, undergroundBiome),
         biomeColumnWithUnderground(-0.05, 1.75, 1.0, biomeRegistry, TFBiomes.GLACIER, undergroundBiome),
         biomeColumnWithUnderground(3.0, 2.25, 0.135, biomeRegistry, TFBiomes.HIGHLANDS, biomeRegistry.getOrThrow(TFBiomes.HIGHLANDS_UNDERGROUND)),
         biomeColumnToBedrock(5.5, 1.75, 1.15, biomeRegistry, TFBiomes.THORNLANDS),
         biomeColumnToBedrock(12.0, 0.75, 1.0, biomeRegistry, TFBiomes.FINAL_PLATEAU)
      );
   }

   private static TerrainColumn biomeColumnWithUnderground(
      double noiseDepth, double noiseScale, double weight, HolderGetter<Biome> biomeRegistry, ResourceKey<Biome> key, Holder<Biome> undergroundBiome
   ) {
      Reference<Biome> biomeHolder = biomeRegistry.getOrThrow(key);
      biomeHolder.bindKey(key);
      return makeColumn(
         DensityFunctions.constant(noiseDepth), DensityFunctions.constant(noiseScale), DensityFunctions.constant(weight), biomeHolder, treeMap -> {
            treeMap.put(Math.min(noiseDepth - 1.0, -1.0), biomeHolder);
            treeMap.put(Math.min(noiseDepth - 3.0, -3.0), undergroundBiome);
         }
      );
   }

   private static TerrainColumn biomeColumnToBedrock(
      double noiseDepth, double noiseScale, double weight, HolderGetter<Biome> biomeRegistry, ResourceKey<Biome> key
   ) {
      Reference<Biome> biomeHolder = biomeRegistry.getOrThrow(key);
      biomeHolder.bindKey(key);
      return makeColumn(
         DensityFunctions.constant(noiseDepth),
         DensityFunctions.constant(noiseScale),
         DensityFunctions.constant(weight),
         biomeHolder,
         treeMap -> treeMap.put(0.0, biomeHolder)
      );
   }

   private static TerrainColumn makeColumn(
      DensityFunction noiseDepth,
      DensityFunction noiseScale,
      DensityFunction noiseWeight,
      Holder<Biome> biomeHolder,
      Consumer<Double2ObjectSortedMap<Holder<Biome>>> layerBuilder
   ) {
      return new TerrainColumn(
         biomeHolder, (Double2ObjectSortedMap<Holder<Biome>>)Util.make(new Double2ObjectAVLTreeMap(), layerBuilder), noiseDepth, noiseScale, noiseWeight
      );
   }
}
