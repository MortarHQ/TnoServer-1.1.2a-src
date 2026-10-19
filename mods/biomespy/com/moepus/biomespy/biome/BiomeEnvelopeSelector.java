package com.moepus.biomespy.biome;

import com.moepus.biomespy.compat.terrablender.IParameterListExtendedInfo;
import com.moepus.biomespy.compat.terrablender.TerraBiome;
import com.moepus.biomespy.compat.terrablender.TerrablenderCompat;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.biome.Climate.ParameterPoint;

public class BiomeEnvelopeSelector {
   private final Map<Integer, BiomeEnvelope> envelopeMap;
   private final Map<Class<?>, Object> platformData = new HashMap<>();

   public BiomeEnvelopeSelector(Collection<Holder<Biome>> biomes, ParameterList<Holder<Biome>> parameters, MultiNoiseBiomeSource biomeSource) {
      this.envelopeMap = new HashMap<>();
      if (TerrablenderCompat.TERRABLENDER_INSTALLED && ((IParameterListExtendedInfo)parameters).biomeSpy$terraBlenderEnabled()) {
         ((IParameterListExtendedInfo)parameters).biomeSpy$visitAllEnvelopes((index, map) -> {
            BiomeEnvelope combinedEnvelopex = new BiomeEnvelope();
            combinedEnvelopex.impossible = true;

            for (Entry<Holder<Biome>, BiomeEnvelope> entry : map.entrySet()) {
               if (biomes.contains(entry.getKey())) {
                  combinedEnvelopex.impossible = false;
                  combinedEnvelopex.add(entry.getValue());
               }
            }

            this.envelopeMap.put(index, combinedEnvelopex);
         });
      } else {
         BiomeEnvelope combinedEnvelope = new BiomeEnvelope();
         combinedEnvelope.impossible = true;

         for (Pair<ParameterPoint, Holder<Biome>> pair : parameters.values()) {
            if (biomes.contains(pair.getSecond())) {
               combinedEnvelope.impossible = false;
               combinedEnvelope.add((ParameterPoint)pair.getFirst());
            }
         }

         this.envelopeMap.put(0, combinedEnvelope);
      }

      for (Integer i : this.envelopeMap.keySet()) {
         BiomeEnvelope env = this.envelopeMap.get(i);
         if (!env.isValid()) {
            this.envelopeMap.put(i, new BiomeEnvelope());
         }
      }
   }

   public BiomeEnvelope getEnvelope(ParameterList<Holder<Biome>> parameters, int qx, int qy, int qz) {
      if (TerrablenderCompat.TERRABLENDER_INSTALLED && ((IParameterListExtendedInfo)parameters).biomeSpy$terraBlenderEnabled()) {
         int uniqueness = TerraBiome.getUniqueness(parameters, qx, qy, qz);
         return this.envelopeMap.get(uniqueness);
      } else {
         return this.envelopeMap.get(0);
      }
   }

   public <T> void setPlatformData(Class<?> key, T data) {
      this.platformData.put(key, data);
   }

   public Object getPlatformData(Class<?> key) {
      return this.platformData.get(key);
   }
}
