package com.mongoose.royalvariations.common.world;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifier.Phase;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

public class RVMobSpawnBiomeModifier implements BiomeModifier {
   public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(
      Keys.BIOME_MODIFIER_SERIALIZERS, "royalvariations"
   );
   public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<RVMobSpawnBiomeModifier>> SERIALIZER = BIOME_MODIFIER_SERIALIZERS.register(
      "mob_spawns", () -> MapCodec.unit(RVMobSpawnBiomeModifier.INSTANCE)
   );
   public static final RVMobSpawnBiomeModifier INSTANCE = new RVMobSpawnBiomeModifier();

   public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
      if (phase == Phase.ADD) {
         RVLevelRegistry.addBiomeSpawns(biome, builder);
      }
   }

   public MapCodec<? extends BiomeModifier> codec() {
      return (MapCodec<? extends BiomeModifier>)SERIALIZER.get();
   }

   public static MapCodec<RVMobSpawnBiomeModifier> makeCodec() {
      return MapCodec.unit(RVMobSpawnBiomeModifier::new);
   }
}
