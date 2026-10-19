package terrablender.api;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedEntry.Wrapper;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import terrablender.core.TerraBlender;

public class EndBiomeRegistry {
   private static final List<Wrapper<ResourceKey<Biome>>> highlandsBiomes = new ArrayList<>();
   private static final List<Wrapper<ResourceKey<Biome>>> midlandsBiomes = new ArrayList<>();
   private static final List<Wrapper<ResourceKey<Biome>>> edgeBiomes = new ArrayList<>();
   private static final List<Wrapper<ResourceKey<Biome>>> islandBiomes = new ArrayList<>();

   public static void registerHighlandsBiome(ResourceKey<Biome> biome, int weight) {
      highlandsBiomes.add(WeightedEntry.wrap(biome, weight));
   }

   public static void registerMidlandsBiome(ResourceKey<Biome> biome, int weight) {
      midlandsBiomes.add(WeightedEntry.wrap(biome, weight));
   }

   public static void registerEdgeBiome(ResourceKey<Biome> biome, int weight) {
      edgeBiomes.add(WeightedEntry.wrap(biome, weight));
   }

   public static void registerIslandBiome(ResourceKey<Biome> biome, int weight) {
      islandBiomes.add(WeightedEntry.wrap(biome, weight));
   }

   public static List<Wrapper<ResourceKey<Biome>>> getHighlandsBiomes() {
      return ImmutableList.copyOf(highlandsBiomes);
   }

   public static List<Wrapper<ResourceKey<Biome>>> getMidlandsBiomes() {
      return ImmutableList.copyOf(midlandsBiomes);
   }

   public static List<Wrapper<ResourceKey<Biome>>> getEdgeBiomes() {
      return ImmutableList.copyOf(edgeBiomes);
   }

   public static List<Wrapper<ResourceKey<Biome>>> getIslandBiomes() {
      return ImmutableList.copyOf(islandBiomes);
   }

   static {
      registerHighlandsBiome(Biomes.END_HIGHLANDS, TerraBlender.CONFIG.vanillaEndHighlandsWeight);
      registerMidlandsBiome(Biomes.END_MIDLANDS, TerraBlender.CONFIG.vanillaEndMidlandsWeight);
      registerEdgeBiome(Biomes.END_BARRENS, TerraBlender.CONFIG.vanillaEndBarrensWeight);
      registerIslandBiome(Biomes.SMALL_END_ISLANDS, TerraBlender.CONFIG.vanillaSmallEndIslandsWeight);
   }
}
