package com.yungnickyoung.minecraft.yungscavebiomes.world;

import com.mojang.datafixers.util.Pair;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.world.terrablender.CaveBiomeRegionParameters;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import terrablender.api.Region;
import terrablender.api.RegionType;

public class CaveBiomeRegion extends Region {
   public CaveBiomeRegion(ResourceLocation name, RegionType type, int weight) {
      super(name, type, weight);
   }

   public void addBiomes(Registry<Biome> registry, Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
      this.addModifiedVanillaOverworldBiomes(mapper, b -> b.replaceParameter(CaveBiomeRegionParameters.DRIPSTONE_OLD, CaveBiomeRegionParameters.DRIPSTONE_NEW));
      this.addBiome(mapper, CaveBiomeRegionParameters.FROSTED_CAVES, BiomeModule.FROSTED_CAVES);
      this.addBiome(mapper, CaveBiomeRegionParameters.LOST_CAVES, BiomeModule.LOST_CAVES);
      if (YungsCaveBiomesCommon.MARBLE_CAVES_ENABLED) {
      }
   }
}
