package net.regions_unexplored.config.state.common;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.lithostitched.api.worldgen.biomeinjector.BiomeInjector;
import dev.worldgen.lithostitched.api.worldgen.biomeinjector.ParameterBuilder;
import dev.worldgen.lithostitched.api.worldgen.biomeinjector.BiomeInjector.ClimateParameter;
import dev.worldgen.lithostitched.api.worldgen.biomeinjector.BiomeInjector.InjectorBuilder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate.Parameter;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.regions_unexplored.config.RUConfigHandler;
import net.regions_unexplored.config.json5.CommentedMapCodec;
import net.regions_unexplored.registry.data.RUBiomes;
import net.regions_unexplored.registry.data.RUDensityFunctions;

public class BiomeTarget {
   public static final Codec<BiomeTarget> SPECIAL_CODEC = CommentedMapCodec.commented(
         Codec.BOOL, "special_enabled", "This biome has special placement rules. It can only be enabled or disabled."
      )
      .codec()
      .xmap(BiomeTarget::new, target -> target.specialEnabled.orElse(false));
   public static final Codec<BiomeTarget> FULL_CODEC = RecordCodecBuilder.create(
      i -> i.group(
            ResourceKey.codec(Registries.DIMENSION).lenientOptionalFieldOf("dimension", Level.OVERWORLD).forGetter(t -> t.dimension),
            Codec.STRING.lenientOptionalFieldOf("group").forGetter(t -> t.group),
            Codec.BOOL.fieldOf("enabled").forGetter(t -> t.enabled.orElse(false)),
            CommentedMapCodec.lenientOptionalCommented(ExtraCodecs.NON_NEGATIVE_INT, "weight", "By default, all other biomes have a weight of 100.")
               .forGetter(t -> t.weight),
            ResourceKey.codec(Registries.BIOME).listOf().lenientOptionalFieldOf("can_replace").forGetter(t -> t.canReplace),
            CommentedMapCodec.lenientOptionalCommented(
                  Codec.unboundedMap(ClimateParameter.CODEC, BiomeTarget.DoubleRange.CODEC), "parameters", "Advanced feature! Edit this with caution."
               )
               .forGetter(t -> t.parameters)
         )
         .apply(i, BiomeTarget::new)
   );
   public static final Codec<BiomeTarget> CODEC = Codec.either(SPECIAL_CODEC, FULL_CODEC)
      .xmap(Either::unwrap, target -> target.specialEnabled.isPresent() ? Either.left(target) : Either.right(target));
   public ResourceKey<Level> dimension;
   public Optional<String> group = Optional.empty();
   public Optional<Boolean> specialEnabled = Optional.empty();
   public Optional<Boolean> enabled = Optional.empty();
   private Optional<Integer> weight = Optional.empty();
   public Integer baseWeight = null;
   public Optional<List<ResourceKey<Biome>>> canReplace = Optional.empty();
   public Optional<Map<ClimateParameter, BiomeTarget.DoubleRange>> parameters = Optional.empty();

   public BiomeTarget() {
      this.dimension = Level.OVERWORLD;
      this.enabled = Optional.of(true);
   }

   public BiomeTarget(boolean specialEnabled) {
      this.dimension = Level.OVERWORLD;
      this.specialEnabled = Optional.of(specialEnabled);
   }

   public BiomeTarget(
      ResourceKey<Level> dimension,
      Optional<String> group,
      boolean enabled,
      Optional<Integer> weight,
      Optional<List<ResourceKey<Biome>>> canReplace,
      Optional<Map<ClimateParameter, BiomeTarget.DoubleRange>> parameters
   ) {
      this.dimension = dimension;
      this.group = group;
      this.enabled = Optional.of(enabled);
      this.weight = weight;
      weight.ifPresent(i -> this.baseWeight = i);
      this.canReplace = canReplace;
      this.parameters = parameters;
   }

   public Optional<Integer> getWeight() {
      return this.weight.map(w -> (int)(w.intValue() * RUConfigHandler.COMMON.biomePlacements.weightMultiplier));
   }

   public static BiomeTarget ofSpecial() {
      return new BiomeTarget(true);
   }

   public static BiomeTarget ofGroupToggle(String group, ResourceKey<Biome> canReplace) {
      BiomeTarget target = new BiomeTarget();
      target.group = Optional.of(group);
      target.canReplace = Optional.of(List.of(canReplace));
      return target;
   }

   public static BiomeTarget ofGroupToggle(String group, ResourceKey<Biome> canReplace, Map<ClimateParameter, BiomeTarget.DoubleRange> parameters) {
      BiomeTarget target = ofGroupToggle(group, canReplace);
      target.parameters = Optional.of(parameters);
      return target;
   }

   public static BiomeTarget ofToggle(List<ResourceKey<Biome>> canReplace, Map<ClimateParameter, BiomeTarget.DoubleRange> parameters) {
      BiomeTarget target = new BiomeTarget();
      target.canReplace = Optional.of(canReplace);
      target.parameters = Optional.of(parameters);
      return target;
   }

   @SafeVarargs
   public static BiomeTarget ofWeighted(int weight, ResourceKey<Biome>... canReplace) {
      BiomeTarget target = new BiomeTarget();
      target.weight = Optional.of(weight);
      target.baseWeight = weight;
      target.canReplace = Optional.of(List.of(canReplace));
      return target;
   }

   public static BiomeTarget ofWeighted(int weight, ResourceKey<Biome> canReplace, Map<ClimateParameter, BiomeTarget.DoubleRange> parameters) {
      BiomeTarget target = ofWeighted(weight, canReplace);
      target.parameters = Optional.of(parameters);
      return target;
   }

   public static BiomeTarget nether(int weight, ResourceKey<Biome> canReplace) {
      BiomeTarget target = ofWeighted(weight, canReplace);
      target.dimension = Level.NETHER;
      return target;
   }

   public static HolderSet<Biome> getTargets(Registry<Biome> registry, List<ResourceKey<Biome>> targets) {
      return HolderSet.direct(targets.stream().<Optional>map(registry::getHolder).filter(Optional::isPresent).map(Optional::get).toList());
   }

   public static BiomeInjector createSpecialInjector(RegistryAccess registries, Holder<Biome> biome, BiomeTarget target) {
      InjectorBuilder builder = BiomeInjector.builder(target.dimension);
      if (biome.is(RUBiomes.PRISMACHASM)) {
         return builder.addPoints(
            new ParameterList(
               List.of(
                  new Pair(
                     new ParameterPoint(
                        Parameter.span(-1.0F, 1.0F),
                        Parameter.span(-1.0F, -0.8F),
                        Parameter.span(-1.2F, 1.0F),
                        Parameter.span(0.0F, 1.0F),
                        Parameter.span(0.2F, 0.9F),
                        Parameter.span(-1.0F, 1.0F),
                        0L
                     ),
                     biome
                  )
               )
            )
         );
      } else if (biome.is(RUBiomes.REDSTONE_CAVES)) {
         return builder.addPoints(
            new ParameterList(
               List.of(
                  new Pair(
                     new ParameterPoint(
                        Parameter.span(-1.0F, 1.0F),
                        Parameter.span(-1.0F, -0.8F),
                        Parameter.span(-1.2F, 1.0F),
                        Parameter.span(-1.0F, 0.0F),
                        Parameter.span(0.2F, 0.9F),
                        Parameter.span(-1.0F, 1.0F),
                        0L
                     ),
                     biome
                  )
               )
            )
         );
      } else if (biome.is(RUBiomes.INFERNO)) {
         Registry<DensityFunction> dfs = registries.registryOrThrow(Registries.DENSITY_FUNCTION);
         return builder.forcePlacement(
            biome,
            ParameterBuilder.create()
               .climateMin(ClimateParameter.DEPTH, 0.2)
               .densityFunctionMin(dfs.getHolderOrThrow(RUDensityFunctions.INFERNO_WEIGHT), 0.001)
         );
      } else {
         return biome.is(RUBiomes.CHALK_CLIFFS)
            ? builder.forcePlacement(
               biome,
               ParameterBuilder.create()
                  .climateRange(ClimateParameter.CONTINENTALNESS, -0.19, -0.11)
                  .climateMax(ClimateParameter.EROSION, -0.6)
                  .climateRange(ClimateParameter.TEMPERATURE, -0.1, 0.2)
            )
            : null;
      }
   }

   public ParameterBuilder getParameters() {
      ParameterBuilder builder = ParameterBuilder.create();
      if (this.parameters.isEmpty()) {
         return builder;
      }

      for (Entry<ClimateParameter, BiomeTarget.DoubleRange> entry : this.parameters.get().entrySet()) {
         BiomeTarget.DoubleRange range = entry.getValue();
         builder.climateRange(entry.getKey(), range.min(), range.max());
      }

      return builder;
   }

   public void setCanGenerate(boolean canGenerate) {
      if (this.specialEnabled.isPresent()) {
         this.specialEnabled = Optional.of(canGenerate);
      }

      if (this.enabled.isPresent()) {
         this.enabled = Optional.of(canGenerate);
      }
   }

   public boolean canGenerate() {
      return this.specialEnabled.orElseGet(() -> this.enabled.orElse(false));
   }

   public record DoubleRange(double min, double max) {
      public static final Codec<BiomeTarget.DoubleRange> CODEC = RecordCodecBuilder.create(
         i -> i.group(
               Codec.DOUBLE.lenientOptionalFieldOf("min", -Double.MAX_VALUE).forGetter(BiomeTarget.DoubleRange::min),
               Codec.DOUBLE.lenientOptionalFieldOf("max", Double.MAX_VALUE).forGetter(BiomeTarget.DoubleRange::max)
            )
            .apply(i, BiomeTarget.DoubleRange::new)
      );

      public static BiomeTarget.DoubleRange below(double value) {
         return new BiomeTarget.DoubleRange(-Double.MAX_VALUE, value);
      }

      public static BiomeTarget.DoubleRange between(double min, double max) {
         return new BiomeTarget.DoubleRange(min, max);
      }

      public static BiomeTarget.DoubleRange above(double value) {
         return new BiomeTarget.DoubleRange(value, Double.MAX_VALUE);
      }
   }
}
