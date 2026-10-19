package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

public class ESNoises {
   public static final ResourceKey<NoiseParameters> DEPTH_OFFSET = create("depth_offset");

   public static void bootstrap(BootstrapContext<NoiseParameters> context) {
      register(context, DEPTH_OFFSET, -5, 1.0);
   }

   private static void register(
      BootstrapContext<NoiseParameters> context, ResourceKey<NoiseParameters> key, int firstOctave, double amplitude, double... otherAmplitudes
   ) {
      context.register(key, new NoiseParameters(firstOctave, amplitude, otherAmplitudes));
   }

   private static ResourceKey<NoiseParameters> create(String key) {
      return ResourceKey.create(Registries.NOISE, EternalStarlight.id(key));
   }
}
