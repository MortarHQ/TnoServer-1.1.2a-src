package com.moepus.biomespy.compat.terrablender;

import com.moepus.biomespy.biome.BiomeEnvelope;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.biome.Biome;
import terrablender.api.RegionType;

public interface IParameterListExtendedInfo {
   void biomeSpy$gatherExtendedInfo(RegistryAccess var1, RegionType var2, long var3);

   BiomeEnvelope biomeSpy$getEnvelopeForBiomes(List<Holder<Biome>> var1, int var2);

   void biomeSpy$visitAllEnvelopes(BiConsumer<Integer, HashMap<Holder<Biome>, BiomeEnvelope>> var1);

   boolean biomeSpy$terraBlenderEnabled();
}
