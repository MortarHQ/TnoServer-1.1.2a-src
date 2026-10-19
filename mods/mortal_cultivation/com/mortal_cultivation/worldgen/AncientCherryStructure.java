package com.mortal_cultivation.worldgen;

import com.mojang.serialization.MapCodec;
import com.mortal_cultivation.registry.WorldgenRegistry;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;

public final class AncientCherryStructure extends Structure {
   public static final MapCodec<AncientCherryStructure> CODEC = simpleCodec(AncientCherryStructure::new);

   public AncientCherryStructure(StructureSettings settings) {
      super(settings);
   }

   protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
      int x = context.chunkPos().getMiddleBlockX();
      int z = context.chunkPos().getMiddleBlockZ();
      int y = surface(context, x, z);
      if (y + 104 < context.heightAccessor().getMaxBuildHeight() - 1
         && y + -16 > context.heightAccessor().getMinBuildHeight()
         && context.validBiome()
            .test(context.biomeSource().getNoiseBiome(QuartPos.fromBlock(x), QuartPos.fromBlock(y), QuartPos.fromBlock(z), context.randomState().sampler()))) {
         for (int dx = -20; dx <= 20; dx += 20) {
            for (int dz = -20; dz <= 20; dz += 20) {
               int ground = surface(context, x + dx, z + dz);
               if (Math.abs(ground - y) > 6) {
                  return Optional.empty();
               }

               BlockState soil = context.chunkGenerator().getBaseColumn(x + dx, z + dz, context.heightAccessor(), context.randomState()).getBlock(ground - 1);
               if (!soil.getFluidState().isEmpty() || !soil.is(BlockTags.DIRT) && !soil.is(BlockTags.BASE_STONE_OVERWORLD)) {
                  return Optional.empty();
               }
            }
         }

         BlockPos origin = new BlockPos(x, y, z);
         long seed = context.random().nextLong();
         return Optional.of(new GenerationStub(origin, pieces -> pieces.addPiece(new AncientCherryPiece(origin, seed))));
      } else {
         return Optional.empty();
      }
   }

   private static int surface(GenerationContext context, int x, int z) {
      return context.chunkGenerator().getFirstFreeHeight(x, z, Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
   }

   public static boolean inClearing(WorldGenLevel level, BlockPos pos) {
      if (level instanceof WorldGenRegion region) {
         Structure structure = (Structure)level.registryAccess()
            .registryOrThrow(Registries.STRUCTURE)
            .get(WorldgenRegistry.ANCIENT_CHERRY_TREE_STRUCTURE.location());
         if (structure == null) {
            return false;
         }

         StructureStart start = level.getLevel().structureManager().forWorldGenRegion(region).getStructureAt(pos, structure);
         if (!start.isValid()) {
            return false;
         }

         BlockPos center = ((StructurePiece)start.getPieces().getFirst()).getLocatorPosition();
         long dx = (long)pos.getX() - center.getX();
         long dz = (long)pos.getZ() - center.getZ();
         return dx * dx + dz * dz < 900L;
      } else {
         return false;
      }
   }

   public StructureType<?> type() {
      return (StructureType<?>)WorldgenRegistry.ANCIENT_CHERRY_TREE.get();
   }
}
