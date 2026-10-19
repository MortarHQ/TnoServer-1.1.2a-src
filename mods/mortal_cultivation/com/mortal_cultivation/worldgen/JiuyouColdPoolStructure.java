package com.mortal_cultivation.worldgen;

import com.mojang.serialization.MapCodec;
import com.mortal_cultivation.registry.WorldgenRegistry;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;

public final class JiuyouColdPoolStructure extends Structure {
   public static final MapCodec<JiuyouColdPoolStructure> CODEC = simpleCodec(JiuyouColdPoolStructure::new);

   public JiuyouColdPoolStructure(StructureSettings settings) {
      super(settings);
   }

   protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
      int x = context.chunkPos().getMiddleBlockX();
      int z = context.chunkPos().getMiddleBlockZ();
      int y = -24 + context.random().nextInt(33);
      if (y + -13 <= context.heightAccessor().getMinBuildHeight() + 4) {
         return Optional.empty();
      }

      int[][] checks = new int[][]{{0, 0}, {-24, 0}, {24, 0}, {0, -24}, {0, 24}};

      for (int[] check : checks) {
         int cx = x + check[0];
         int cz = z + check[1];
         int surface = context.chunkGenerator().getFirstOccupiedHeight(cx, cz, Types.OCEAN_FLOOR_WG, context.heightAccessor(), context.randomState());
         if (surface < y + 16 + 14
            || !context.validBiome()
               .test(
                  context.biomeSource()
                     .getNoiseBiome(QuartPos.fromBlock(cx), QuartPos.fromBlock(surface), QuartPos.fromBlock(cz), context.randomState().sampler())
               )) {
            return Optional.empty();
         }
      }

      BlockPos center = new BlockPos(x, y, z);
      long seed = context.random().nextLong();
      return Optional.of(new GenerationStub(center, pieces -> pieces.addPiece(new JiuyouColdPoolPiece(center, seed))));
   }

   public Optional<GenerationStub> findValidGenerationPoint(GenerationContext context) {
      return this.findGenerationPoint(context);
   }

   public StructureType<?> type() {
      return (StructureType<?>)WorldgenRegistry.JIUYOU_COLD_POOL.get();
   }
}
