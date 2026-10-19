package com.github.L_Ender.cataclysm.world;

import com.github.L_Ender.cataclysm.init.ModBiomeModifiers;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifier.Phase;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;

public class CMMobSpawnBiomeModifier implements BiomeModifier {
   public static final CMMobSpawnBiomeModifier INSTANCE = new CMMobSpawnBiomeModifier();

   public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
      if (phase == Phase.ADD) {
         CMWorldRegistry.addBiomeSpawns(biome, builder);
      }
   }

   public MapCodec<? extends BiomeModifier> codec() {
      return (MapCodec<? extends BiomeModifier>)ModBiomeModifiers.CM_MOB_SPAWN_BIOME_MODIFIER_TYPE.get();
   }
}
