package com.mortal_cultivation.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mortal_cultivation.registry.WorldgenRegistry;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;

public final class SteleGrottoStructure extends Structure {
   public static final MapCodec<SteleGrottoStructure> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(settingsCodec(instance), HeightProvider.CODEC.fieldOf("height").forGetter(structure -> structure.height))
         .apply(instance, SteleGrottoStructure::new)
   );
   private final HeightProvider height;

   public SteleGrottoStructure(StructureSettings settings, HeightProvider height) {
      super(settings);
      this.height = height;
   }

   protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
      int centerX = context.chunkPos().getMiddleBlockX();
      int centerZ = context.chunkPos().getMiddleBlockZ();
      int centerY = this.height.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
      int lowestSurface = Integer.MAX_VALUE;
      int radius = 21;
      int[][] surfaceChecks = new int[][]{{0, 0}, {-radius, 0}, {radius, 0}, {0, -radius}, {0, radius}};

      for (int[] offset : surfaceChecks) {
         int surface = context.chunkGenerator()
            .getFirstOccupiedHeight(centerX + offset[0], centerZ + offset[1], Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
         lowestSurface = Math.min(lowestSurface, surface);
      }

      if (centerY + 10 + 8 < lowestSurface && centerY - 10 > context.heightAccessor().getMinBuildHeight() + 1) {
         BlockPos center = new BlockPos(centerX, centerY, centerZ);
         long layoutSeed = context.random().nextLong();
         return Optional.of(new GenerationStub(center, pieces -> pieces.addPiece(new SteleGrottoPiece(center, layoutSeed))));
      } else {
         return Optional.empty();
      }
   }

   public StructureType<?> type() {
      return (StructureType<?>)WorldgenRegistry.STELE_GROTTO.get();
   }
}
