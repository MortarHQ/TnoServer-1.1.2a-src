package net.regions_unexplored.worldgen.processorcondition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.lithostitched.api.worldgen.processorcondition.ProcessorCondition;
import dev.worldgen.lithostitched.api.worldgen.processorcondition.ProcessorCondition.Data;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.QuartPos;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

public record MatchingBiomesCondition(HolderSet<Biome> biomes) implements ProcessorCondition {
   public static final MapCodec<MatchingBiomesCondition> CODEC = RecordCodecBuilder.mapCodec(
      i -> i.group(Biome.LIST_CODEC.fieldOf("biomes").forGetter(MatchingBiomesCondition::biomes)).apply(i, MatchingBiomesCondition::new)
   );

   public boolean test(WorldGenLevel level, Data data, StructurePlaceSettings placeSettings, RandomSource random) {
      if (level.getChunkSource() instanceof ServerChunkCache source) {
         BlockPos var8 = data.pos();
         Holder biome = source.getGenerator()
            .getBiomeSource()
            .getNoiseBiome(QuartPos.fromBlock(var8.getX()), QuartPos.fromBlock(var8.getY()), QuartPos.fromBlock(var8.getZ()), source.randomState().sampler());
         return this.biomes.contains(biome);
      } else {
         return false;
      }
   }

   public MapCodec<? extends ProcessorCondition> codec() {
      return CODEC;
   }
}
