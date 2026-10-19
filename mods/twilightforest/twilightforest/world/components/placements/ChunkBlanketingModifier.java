package twilightforest.world.components.placements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import twilightforest.init.TFFeatureModifiers;

public class ChunkBlanketingModifier extends PlacementModifier {
   public static final MapCodec<ChunkBlanketingModifier> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            Codec.floatRange(0.0F, 1.0F).fieldOf("integrity").forGetter(o -> o.integrity),
            Types.CODEC.fieldOf("heightmap").forGetter(o -> o.heightmap),
            RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biome_lock").forGetter(o -> o.biomeRLOptional)
         )
         .apply(instance, ChunkBlanketingModifier::new)
   );
   public final float integrity;
   public final Types heightmap;
   public final Optional<HolderSet<Biome>> biomeRLOptional;

   public ChunkBlanketingModifier(float integrity, Types heightmap, Optional<HolderSet<Biome>> biomeRLOptional) {
      this.integrity = integrity;
      this.heightmap = heightmap;
      this.biomeRLOptional = biomeRLOptional;
   }

   public static ChunkBlanketingModifier addThorns(HolderSet<Biome> thorns) {
      return new ChunkBlanketingModifier(0.7F, Types.OCEAN_FLOOR_WG, Optional.of(thorns));
   }

   public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos placement) {
      ArrayList<BlockPos> coordinates = new ArrayList<>();
      WorldGenLevel level = context.getLevel();
      ChunkAccess chunk = level.getChunk(placement);
      int chunkOriginX = chunk.getPos().getMinBlockX();
      int chunkOriginZ = chunk.getPos().getMinBlockZ();

      for (int zInChunk = 0; zInChunk < 16; zInChunk++) {
         for (int xInChunk = 0; xInChunk < 16; xInChunk++) {
            if (!(random.nextFloat() > this.integrity)) {
               BlockPos pos = new BlockPos(chunkOriginX + xInChunk, chunk.getHeight(this.heightmap, xInChunk, zInChunk) + 1, chunkOriginZ + zInChunk);
               if (this.biomeRLOptional.isEmpty() || this.biomeRLOptional.get().contains(level.getBiome(pos))) {
                  coordinates.add(pos);
               }
            }
         }
      }

      return coordinates.stream();
   }

   public PlacementModifierType<?> type() {
      return (PlacementModifierType<?>)TFFeatureModifiers.CHUNK_BLANKETING.get();
   }
}
