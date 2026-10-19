package com.bobmowzie.mowziesmobs.server.world;

import com.bobmowzie.mowziesmobs.server.world.feature.structure.StructureTypeHandler;
import com.bobmowzie.mowziesmobs.server.world.spawn.SpawnHandler;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifier.Phase;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class MobSpawnBiomeModifier implements BiomeModifier {
   private static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<? extends BiomeModifier>> SERIALIZER = DeferredHolder.create(
      NeoForgeRegistries.BIOME_MODIFIER_SERIALIZERS.key(), ResourceLocation.fromNamespaceAndPath("mowziesmobs", "mowzie_mob_spawns")
   );

   public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
      if (phase == Phase.ADD) {
         SpawnHandler.addBiomeSpawns(biome, builder);
         StructureTypeHandler.addBiomeSpawns(biome);
      }
   }

   public MapCodec<? extends BiomeModifier> codec() {
      return (MapCodec<? extends BiomeModifier>)SERIALIZER.get();
   }

   public static MapCodec<MobSpawnBiomeModifier> makeCodec() {
      return MapCodec.unit(MobSpawnBiomeModifier::new);
   }
}
