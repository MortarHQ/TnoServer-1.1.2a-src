package com.moepus.biomespy.mixin.compat.terrablender;

import com.moepus.biomespy.biome.BiomeEnvelope;
import com.moepus.biomespy.compat.terrablender.IParameterListExtendedInfo;
import com.mojang.datafixers.util.Pair;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.worldgen.IExtendedParameterList;

@Mixin(ParameterList.class)
public abstract class ParameterListMixin<T> implements IParameterListExtendedInfo, IExtendedParameterList<T> {
   @Shadow
   @Final
   private List<Pair<ParameterPoint, T>> values;
   @Unique
   private boolean biomeSpy$initialized = false;
   @Unique
   private HashMap<T, BiomeEnvelope>[] biomeSpy$uniqueTrees;

   @Override
   public void biomeSpy$gatherExtendedInfo(RegistryAccess registryAccess, RegionType regionType, long seed) {
      if (!this.biomeSpy$initialized) {
         this.biomeSpy$initialized = true;
         this.biomeSpy$uniqueTrees = new HashMap[Regions.getCount(regionType)];
         Registry<Biome> biomeRegistry = registryAccess.registryOrThrow(Registries.BIOME);

         for (Region region : Regions.get(regionType)) {
            int regionIndex = Regions.getIndex(regionType, region.getName());
            if (regionIndex == 0) {
               HashMap<T, BiomeEnvelope> biomeEnvelopeHashMap = new HashMap<>();

               for (Pair<ParameterPoint, T> p : this.values) {
                  T biome = (T)p.getSecond();
                  biomeEnvelopeHashMap.computeIfAbsent(biome, k -> new BiomeEnvelope()).add((ParameterPoint)p.getFirst());
               }

               this.biomeSpy$uniqueTrees[0] = biomeEnvelopeHashMap;
            } else {
               HashMap<T, BiomeEnvelope> biomeEnvelopeHashMap = new HashMap<>();
               region.addBiomes(biomeRegistry, pair -> {
                  Optional<Reference<Biome>> biomex = biomeRegistry.getHolder((ResourceKey)pair.getSecond());
                  if (biomex.isPresent()) {
                     Holder<Biome> biomeHolder = (Holder<Biome>)biomex.get();
                     biomeEnvelopeHashMap.computeIfAbsent((T)biomeHolder, k -> new BiomeEnvelope()).add((ParameterPoint)pair.getFirst());
                  }
               });
               this.biomeSpy$uniqueTrees[regionIndex] = biomeEnvelopeHashMap;
            }
         }
      }
   }

   @Override
   public BiomeEnvelope biomeSpy$getEnvelopeForBiomes(List<Holder<Biome>> biomes, int regionIndex) {
      BiomeEnvelope combinedEnvelope = new BiomeEnvelope();
      combinedEnvelope.impossible = true;
      HashMap<T, BiomeEnvelope> biomeEnvelopeHashMap = this.biomeSpy$uniqueTrees[regionIndex];

      for (Holder<Biome> biome : biomes) {
         BiomeEnvelope envelope = biomeEnvelopeHashMap.get(biome);
         if (envelope != null) {
            combinedEnvelope.add(envelope);
            combinedEnvelope.impossible = false;
         }
      }

      return combinedEnvelope;
   }

   @Override
   public void biomeSpy$visitAllEnvelopes(BiConsumer<Integer, HashMap<Holder<Biome>, BiomeEnvelope>> visitor) {
      for (int i = 0; i < this.biomeSpy$uniqueTrees.length; i++) {
         visitor.accept(i, this.biomeSpy$uniqueTrees[i]);
      }
   }

   @Override
   public boolean biomeSpy$terraBlenderEnabled() {
      return this.biomeSpy$initialized;
   }

   public ParameterList<T> clone() {
      try {
         return (ParameterList<T>)super.clone();
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError();
      }
   }
}
