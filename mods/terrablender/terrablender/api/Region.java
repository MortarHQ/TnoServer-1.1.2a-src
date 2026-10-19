package terrablender.api;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.Parameter;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import terrablender.worldgen.RegionUtils;

public abstract class Region {
   public static final ResourceKey<Biome> DEFERRED_PLACEHOLDER = ResourceKey.create(
      Registries.BIOME, ResourceLocation.fromNamespaceAndPath("terrablender", "deferred_placeholder")
   );
   private final ResourceLocation name;
   private RegionType type;
   private int weight;

   public Region(ResourceLocation name, RegionType type, int weight) {
      this.name = name;
      this.type = type;
      this.weight = weight;
   }

   public ResourceLocation getName() {
      return this.name;
   }

   public RegionType getType() {
      return this.type;
   }

   public int getWeight() {
      return this.weight;
   }

   public void addBiomes(Registry<Biome> registry, Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
   }

   protected final void addBiome(
      Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper,
      Parameter temperature,
      Parameter humidity,
      Parameter continentalness,
      Parameter erosion,
      Parameter weirdness,
      Parameter depth,
      float offset,
      ResourceKey<Biome> biome
   ) {
      this.addBiome(mapper, Climate.parameters(temperature, humidity, continentalness, erosion, depth, weirdness, offset), biome);
   }

   protected final void addBiome(
      Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper,
      ParameterUtils.Temperature temperature,
      ParameterUtils.Humidity humidity,
      ParameterUtils.Continentalness continentalness,
      ParameterUtils.Erosion erosion,
      ParameterUtils.Weirdness weirdness,
      ParameterUtils.Depth depth,
      float offset,
      ResourceKey<Biome> biome
   ) {
      this.addBiome(
         mapper,
         Climate.parameters(
            temperature.parameter(), humidity.parameter(), continentalness.parameter(), erosion.parameter(), depth.parameter(), weirdness.parameter(), offset
         ),
         biome
      );
   }

   protected final void addBiome(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper, ParameterPoint parameters, ResourceKey<Biome> biome) {
      mapper.accept(Pair.of(parameters, biome));
   }

   protected final void addBiomeSimilar(
      Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper, ResourceKey<Biome> similarVanillaBiome, ResourceKey<Biome> biome
   ) {
      List<ParameterPoint> points = RegionUtils.getVanillaParameterPoints(similarVanillaBiome).stream().collect(ImmutableList.toImmutableList());
      points.forEach(point -> this.addBiome(mapper, point, biome));
   }

   protected final void addModifiedVanillaOverworldBiomes(
      Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper, Consumer<ModifiedVanillaOverworldBuilder> onModify
   ) {
      ModifiedVanillaOverworldBuilder builder = new ModifiedVanillaOverworldBuilder();
      onModify.accept(builder);
      builder.build().forEach(mapper::accept);
   }
}
