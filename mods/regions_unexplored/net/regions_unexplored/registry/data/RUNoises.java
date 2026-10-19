package net.regions_unexplored.registry.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;
import net.regions_unexplored.RegionsUnexplored;

public interface RUNoises {
   @Deprecated(forRemoval = true)
   ResourceKey<NoiseParameters> WEIGHTED = key("weighted");
   ResourceKey<NoiseParameters> SHIELD = key("shield");
   ResourceKey<NoiseParameters> SURFACE_MEDIUM = key("surface_medium");
   ResourceKey<NoiseParameters> TREE_DENSITY = key("tree_density");
   ResourceKey<NoiseParameters> FLOWER_DENSITY = key("flower_density");

   static ResourceKey<NoiseParameters> key(String name) {
      return RegionsUnexplored.key(Registries.NOISE, name);
   }
}
