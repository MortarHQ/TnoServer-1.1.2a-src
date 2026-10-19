package com.mortal_cultivation.worldgen;

import com.mojang.serialization.MapCodec;
import com.mortal_cultivation.registry.WorldgenRegistry;
import it.unimi.dsi.fastutil.longs.LongIterator;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;

public final class QilinBambooGroveStructure extends Structure {
   public static final MapCodec<QilinBambooGroveStructure> CODEC = simpleCodec(QilinBambooGroveStructure::new);

   public QilinBambooGroveStructure(StructureSettings settings) {
      super(settings);
   }

   protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
      int x = context.chunkPos().getMiddleBlockX();
      int z = context.chunkPos().getMiddleBlockZ();
      int y = surface(context, x, z);
      if (y >= context.chunkGenerator().getSeaLevel()
         && y - 9 > context.heightAccessor().getMinBuildHeight()
         && y + 48 < context.heightAccessor().getMaxBuildHeight()
         && !overlapsLandmark(context.seed(), x, z)) {
         for (int dx = -14; dx <= 14; dx += 7) {
            int dz = -14;

            while (dz <= 14) {
               int sx = x + dx;
               int sz = z + dz;
               int ground = surface(context, sx, sz);
               if (Math.abs(ground - y) <= 4
                  && context.validBiome()
                     .test(
                        context.biomeSource()
                           .getNoiseBiome(QuartPos.fromBlock(sx), QuartPos.fromBlock(ground), QuartPos.fromBlock(sz), context.randomState().sampler())
                     )) {
                  BlockState soil = context.chunkGenerator().getBaseColumn(sx, sz, context.heightAccessor(), context.randomState()).getBlock(ground - 1);
                  if (soil.getFluidState().isEmpty() && (soil.is(BlockTags.DIRT) || soil.is(BlockTags.BASE_STONE_OVERWORLD))) {
                     dz += 7;
                     continue;
                  }

                  return Optional.empty();
               }

               return Optional.empty();
            }
         }

         BlockPos origin = new BlockPos(x, y, z);
         long seed = context.random().nextLong();
         return Optional.of(new GenerationStub(origin, pieces -> pieces.addPiece(new QilinBambooGrovePiece(origin, seed))));
      } else {
         return Optional.empty();
      }
   }

   private static int surface(GenerationContext context, int x, int z) {
      return context.chunkGenerator().getFirstFreeHeight(x, z, Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
   }

   private static boolean overlapsLandmark(long seed, int x, int z) {
      int cellX = Math.floorDiv(x, 64);
      int cellZ = Math.floorDiv(z, 64);

      for (int dx = -1; dx <= 1; dx++) {
         for (int dz = -1; dz <= 1; dz++) {
            SpiritForestLandscapeLayout.Site site = SpiritForestLandscapeLayout.site(seed, cellX + dx, cellZ + dz);
            long sx = (long)site.x() - x;
            long sz = (long)site.z() - z;
            int clearance = 14 + (site.kind() == SpiritForestLandscapeLayout.Kind.RUIN ? 11 : 16);
            if (sx * sx + sz * sz < clearance * clearance) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean inClearing(WorldGenLevel level, BlockPos pos) {
      if (level instanceof WorldGenRegion region) {
         Structure structure = (Structure)level.registryAccess()
            .registryOrThrow(Registries.STRUCTURE)
            .get(WorldgenRegistry.QILIN_BAMBOO_GROVE_STRUCTURE.location());
         if (structure == null) {
            return false;
         }

         ChunkPos chunk = new ChunkPos(pos);
         if (!region.hasChunk(chunk.x, chunk.z)) {
            return false;
         }

         LongIterator var5 = region.getChunk(chunk.x, chunk.z, ChunkStatus.STRUCTURE_REFERENCES).getReferencesForStructure(structure).iterator();

         while (var5.hasNext()) {
            long reference = (Long)var5.next();
            ChunkPos startChunk = new ChunkPos(reference);
            if (region.hasChunk(startChunk.x, startChunk.z)) {
               StructureStart start = region.getChunk(startChunk.x, startChunk.z, ChunkStatus.STRUCTURE_STARTS).getStartForStructure(structure);
               if (start != null && start.isValid()) {
                  BlockPos origin = ((StructurePiece)start.getPieces().getFirst()).getLocatorPosition();
                  long dx = (long)pos.getX() - origin.getX();
                  long dz = (long)pos.getZ() - origin.getZ();
                  if (dx * dx + dz * dz <= 484L) {
                     return true;
                  }
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public StructureType<?> type() {
      return (StructureType<?>)WorldgenRegistry.QILIN_BAMBOO_GROVE_TYPE.get();
   }
}
