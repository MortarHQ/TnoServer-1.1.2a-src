package com.moepus.biomespy.platform.services;

import com.moepus.biomespy.biome.BiomeEnvelope;
import com.moepus.biomespy.biome.BiomeEnvelopeSelector;
import java.util.Collection;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.Climate.ParameterList;

public interface IPlatformHelper {
   String getPlatformName();

   boolean isModLoaded(String var1);

   boolean hasLoadingMod(String var1);

   boolean isDevelopmentEnvironment();

   default String getEnvironmentName() {
      return this.isDevelopmentEnvironment() ? "development" : "production";
   }

   default void initPlatformSpecificBiomeEnvelope(
      BiomeEnvelopeSelector envelopeMap, Collection<Holder<Biome>> biomes, ParameterList<Holder<Biome>> parameters, MultiNoiseBiomeSource biomeSource
   ) {
   }

   default BiomeEnvelope getPlatformSpecificBiomeEnvelope(BiomeEnvelopeSelector envelopeMap, ParameterList<Holder<Biome>> parameters, int qx, int qy, int qz) {
      return null;
   }
}
