package io.github.Memoires.mysticism.world.dimension;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class MysticismNoiseGeneratorSettings {
   public static final ResourceKey<NoiseGeneratorSettings> ELEMENATAL_REALM_NOISE = create("elemental_realm");
   public static final ResourceKey<NoiseGeneratorSettings> KAMUI_DIMENSION_NOISE = create("kamui_dimension_realm");

   public static void bootstrap(BootstrapContext<NoiseGeneratorSettings> context) {
      context.register(ELEMENATAL_REALM_NOISE, ElementalRealm.GenerationNoises.elementalRealm(context));
      context.register(KAMUI_DIMENSION_NOISE, KamuiDimension.GenerationNoises.kamuiDimension(context));
   }

   public static ResourceKey<NoiseGeneratorSettings> create(String name) {
      return ResourceKey.create(Registries.NOISE_SETTINGS, ResourceLocation.fromNamespaceAndPath("mysticism", name));
   }
}
