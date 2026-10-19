package com.mortal_cultivation.worldgen;

import com.mojang.datafixers.util.Pair;
import com.mortal_cultivation.registry.WorldgenRegistry;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate.Parameter;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import terrablender.api.Region;
import terrablender.api.RegionType;

public final class SpiritForestRegion extends Region {
   public SpiritForestRegion() {
      super(ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "spirit_forest"), RegionType.OVERWORLD, 1);
   }

   public void addBiomes(Registry<Biome> registry, Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
      this.addModifiedVanillaOverworldBiomes(
         pair -> {
            mapper.accept(pair);
            ParameterPoint point = (ParameterPoint)pair.getFirst();
            if (((ResourceKey)pair.getSecond()).equals(WorldgenRegistry.SPIRIT_FOREST) && point.depth().min() == 0L) {
               mapper.accept(
                  Pair.of(
                     new ParameterPoint(
                        point.temperature(),
                        point.humidity(),
                        point.continentalness(),
                        point.erosion(),
                        Parameter.span(-2.0F, -0.95F),
                        point.weirdness(),
                        point.offset()
                     ),
                     WorldgenRegistry.SKY_SPIRIT_FOREST
                  )
               );
            }
         },
         builder -> builder.replaceBiome(Biomes.FOREST, WorldgenRegistry.SPIRIT_FOREST)
      );
   }
}
