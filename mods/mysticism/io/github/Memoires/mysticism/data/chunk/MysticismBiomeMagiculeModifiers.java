package io.github.Memoires.mysticism.data.chunk;

import io.github.Memoires.mysticism.registry.world.MysticismBiomes;
import io.github.manasmods.tensura.data.chunk.BiomeMagiculeModifier;
import io.github.manasmods.tensura.data.chunk.DataPackMagiculeModifier;
import io.github.manasmods.tensura.data.chunk.DataPackMagiculeModifier.Mode;
import io.github.manasmods.tensura.registry.data.TensuraCustomData;
import java.util.List;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class MysticismBiomeMagiculeModifiers {
   public static void bootstrap(BootstrapContext<BiomeMagiculeModifier> context) {
      registerDefault(context, MysticismBiomes.FIRE_BIOME.location(), 3500.0, 50.0);
      registerDefault(context, MysticismBiomes.WATER_BIOME.location(), 1500.0, 50.0);
      registerDefault(context, MysticismBiomes.EARTH_BIOME.location(), 500.0, 50.0);
      registerDefault(context, MysticismBiomes.WIND_BIOME.location(), 2500.0, 50.0);
      registerDefault(context, MysticismBiomes.SPACE_BIOME.location(), 3500.0, 50.0);
      registerDefault(context, MysticismBiomes.LIGHT_BIOME.location(), 1500.0, 50.0);
      registerDefault(context, MysticismBiomes.DARKNESS_BIOME.location(), 500.0, 50.0);
   }

   private static void registerDefault(BootstrapContext<BiomeMagiculeModifier> context, ResourceLocation biomeId, double base, double regen) {
      register(
         context,
         new BiomeMagiculeModifier(biomeId, 1, List.of(new DataPackMagiculeModifier(Mode.ADD, base)), List.of(new DataPackMagiculeModifier(Mode.ADD, regen)))
      );
   }

   public static void register(BootstrapContext<BiomeMagiculeModifier> context, BiomeMagiculeModifier data) {
      ResourceKey<BiomeMagiculeModifier> key = ResourceKey.create(TensuraCustomData.BIOME_MAGICULE, data.biomeId());
      context.register(key, data);
   }
}
