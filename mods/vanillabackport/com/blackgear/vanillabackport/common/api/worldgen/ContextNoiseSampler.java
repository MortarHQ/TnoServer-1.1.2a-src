package com.blackgear.vanillabackport.common.api.worldgen;

import java.util.function.DoubleSupplier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

public interface ContextNoiseSampler {
   DoubleSupplier getNoiseSampler3D(ResourceKey<NoiseParameters> var1);
}
