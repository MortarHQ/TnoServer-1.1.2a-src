package net.pixeldreamstudios.mythika.neoforge;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifier.Phase;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import net.pixeldreamstudios.mythika.registry.SpawnRegistry;

public record ConfigSpawnBiomeModifier() implements BiomeModifier {
   public static final MapCodec<ConfigSpawnBiomeModifier> CODEC = MapCodec.unit(ConfigSpawnBiomeModifier::new);

   public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
      if (phase == Phase.ADD) {
         SpawnRegistry.forEachSpawn((biomes, category, entityType, weight, minCount, maxCount) -> {
            if (biome.is(biomes)) {
               builder.getMobSpawnSettings().addSpawn(category, new SpawnerData(entityType, weight, minCount, maxCount));
            }
         });
      }
   }

   public MapCodec<? extends BiomeModifier> codec() {
      return CODEC;
   }
}
