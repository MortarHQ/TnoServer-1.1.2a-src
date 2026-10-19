package net.regions_unexplored.lithostitched;

import dev.worldgen.lithostitched.api.event.AddBiomeInjectorsEvent;
import dev.worldgen.lithostitched.api.event.AddRegionsEvent;
import dev.worldgen.lithostitched.api.event.AddWorldgenModifiersEvent;
import dev.worldgen.lithostitched.api.event.AddRegionsEvent.RegionConsumer;
import dev.worldgen.lithostitched.api.util.InjectionType;
import dev.worldgen.lithostitched.api.worldgen.biomeinjector.BiomeInjector;
import dev.worldgen.lithostitched.api.worldgen.biomeinjector.ParameterBuilder;
import dev.worldgen.lithostitched.api.worldgen.densityfunction.LithostitchedDensityFunctions;
import dev.worldgen.lithostitched.api.worldgen.modifier.WorldgenModifier;
import dev.worldgen.lithostitched.api.worldgen.util.NoiseRouterTarget;
import java.util.Map.Entry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.regions_unexplored.RegionsUnexplored;
import net.regions_unexplored.config.RUConfigHandler;
import net.regions_unexplored.config.state.common.BiomeTarget;
import net.regions_unexplored.registry.data.RUBiomes;
import net.regions_unexplored.registry.data.RUDensityFunctions;
import net.regions_unexplored.registry.data.RURegions;
import net.regions_unexplored.world.surface.RUSurfaceRuleBuilder;

public class RULithostitched {
   public static void init() {
      AddWorldgenModifiersEvent.EVENT
         .register(
            (AddWorldgenModifiersEvent)(registries, consumer) -> {
               consumer.accept(
                  RegionsUnexplored.id("surface_rule/nether"),
                  WorldgenModifier.builder().addSurfaceRule(Level.NETHER, InjectionType.PREPEND, RUSurfaceRuleBuilder.nether())
               );
               Registry<Biome> biomes = registries.registryOrThrow(Registries.BIOME);
               Registry<PlacedFeature> features = registries.registryOrThrow(Registries.PLACED_FEATURE);
               consumer.accept(
                  RegionsUnexplored.id("inferno/no_water_springs"),
                  WorldgenModifier.builder()
                     .removeFeatures(
                        biomes.getHolderOrThrow(RUBiomes.INFERNO), features.getHolderOrThrow(MiscOverworldPlacements.SPRING_WATER), Decoration.FLUID_SPRINGS
                     )
               );
               Registry<DensityFunction> dfs = registries.registryOrThrow(Registries.DENSITY_FUNCTION);
               consumer.accept(
                  RegionsUnexplored.id("inferno/no_aquifers"),
                  WorldgenModifier.builder()
                     .wrapNoiseRouter(
                        Level.OVERWORLD,
                        NoiseRouterTarget.FLUID_LEVEL_FLOODEDNESS,
                        DensityFunctions.rangeChoice(
                           (DensityFunction)dfs.getOrThrow(RUDensityFunctions.INFERNO_WEIGHT),
                           0.001,
                           64.0,
                           DensityFunctions.constant(0.0),
                           LithostitchedDensityFunctions.wrappedMarker()
                        )
                     )
               );
            }
         );
      AddRegionsEvent.EVENT.register((AddRegionsEvent)(registries, consumer) -> {
         Registry<Biome> registry = registries.registryOrThrow(Registries.BIOME);

         for (Entry<String, BiomeTarget> entry : RUConfigHandler.COMMON.biomeGroups.groups.entrySet()) {
            addRegion(consumer, registry, entry.getKey(), entry.getValue());
         }

         for (Entry<ResourceKey<Biome>, BiomeTarget> entry : RUConfigHandler.COMMON.biomePlacements.placements.entrySet()) {
            addRegion(consumer, registry, entry.getKey().location().getPath(), entry.getValue());
         }
      });
      AddBiomeInjectorsEvent.EVENT
         .register(
            (AddBiomeInjectorsEvent)(registries, consumer) -> {
               Registry<Biome> registry = registries.registryOrThrow(Registries.BIOME);

               for (Entry<ResourceKey<Biome>, BiomeTarget> entry : RUConfigHandler.COMMON.biomePlacements.placements.entrySet()) {
                  ResourceKey<Biome> biome = entry.getKey();
                  BiomeTarget target = entry.getValue();
                  if (target.canGenerate()) {
                     BiomeInjector injector;
                     if (target.getWeight().orElse(0) > 0 && target.canReplace.isPresent()) {
                        injector = BiomeInjector.builder(target.dimension)
                           .replacePartially(
                              BiomeTarget.getTargets(registry, target.canReplace.get()),
                              registry.getHolderOrThrow(biome),
                              target.getParameters().region(RURegions.key(biome))
                           );
                     } else if (target.canReplace.isPresent()) {
                        ParameterBuilder parameters = target.getParameters();
                        target.group.ifPresent(g -> parameters.region(RURegions.key(g)));
                        injector = BiomeInjector.builder(target.dimension)
                           .replacePartially(BiomeTarget.getTargets(registry, target.canReplace.get()), registry.getHolderOrThrow(biome), parameters);
                     } else {
                        injector = BiomeTarget.createSpecialInjector(registries, registry.getHolderOrThrow(biome), target);
                     }

                     if (injector != null) {
                        consumer.accept(biome.location(), injector);
                     }
                  }
               }
            }
         );
   }

   private static void addRegion(RegionConsumer consumer, Registry<Biome> registry, String name, BiomeTarget target) {
      int weight = target.getWeight().orElse(0);
      if (weight > 0 && !target.canReplace.isEmpty()) {
         consumer.accept(RURegions.key(name), target.dimension, BiomeTarget.getTargets(registry, target.canReplace.get()), weight);
      }
   }
}
