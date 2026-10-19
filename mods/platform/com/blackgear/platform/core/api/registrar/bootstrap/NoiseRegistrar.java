package com.blackgear.platform.core.api.registrar.bootstrap;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

public class NoiseRegistrar extends BootstrapRegistrar<NoiseParameters> {
   private NoiseRegistrar(ResourceKey<? extends Registry<NoiseParameters>> registry, String modId) {
      super(registry, modId);
   }

   public static NoiseRegistrar create(String modId) {
      return new NoiseRegistrar(Registries.NOISE, modId);
   }

   public ResourceKey<NoiseParameters> register(String name, int firstOctave, double firstAmplitude, double... amplitudes) {
      return this.register(name, context -> new NoiseParameters(firstOctave, firstAmplitude, amplitudes));
   }
}
