package net.pixeldreamstudios.mythika.worldgen;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.MapCodec;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.pixeldreamstudios.mythika.registry.StructureRegistry;

public class CyclopsCaveStructure extends Structure {
   public static final MapCodec<CyclopsCaveStructure> CODEC = simpleCodec(CyclopsCaveStructure::new);
   private static final int HEADROOM = 6;

   public CyclopsCaveStructure(StructureSettings settings) {
      super(settings);
   }

   protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
      WorldgenRandom random = context.random();
      int seed = random.nextInt();
      int side = CyclopsCaveLayout.rollSide(random);
      ChunkPos chunkPos = context.chunkPos();
      int fromX = chunkPos.getMiddleBlockX();
      int fromZ = chunkPos.getMiddleBlockZ();
      CyclopsCaveStructure.Approach best = null;

      for (int turn = 0; turn < 4; turn++) {
         CyclopsCaveStructure.Approach approach = this.solve(context, seed, side, fromX, fromZ, Direction.from2DDataValue(seed + turn));
         if (approach != null && (best == null || approach.better(best))) {
            best = approach;
         }
      }

      if (best != null && best.misfit() <= 18) {
         Direction inward = best.inward();
         int mouthX = fromX + inward.getStepX() * best.mouthD();
         int mouthZ = fromZ + inward.getStepZ() * best.mouthD();
         CyclopsCaveLayout layout = CyclopsCaveLayout.of(seed, side, best.reach());
         StructurePiecesBuilder builder = new StructurePiecesBuilder();
         int skyY = context.chunkGenerator()
            .getBaseHeight(
               mouthX + inward.getStepX() * best.reach(),
               mouthZ + inward.getStepZ() * best.reach(),
               Types.WORLD_SURFACE_WG,
               context.heightAccessor(),
               context.randomState()
            );
         builder.addPiece(new CyclopsCavePiece(caveBox(layout, best, mouthX, mouthZ), best.mouthY(), mouthX, mouthZ, skyY, inward, layout));
         return Optional.of(new GenerationStub(new BlockPos(mouthX, best.mouthY(), mouthZ), Either.right(builder)));
      } else {
         return Optional.empty();
      }
   }

   private CyclopsCaveStructure.Approach solve(GenerationContext context, int seed, int side, int fromX, int fromZ, Direction inward) {
      Direction across = inward.getClockWise();
      int width = CyclopsCaveLayout.probeWidth();
      int[] terrain = new int[CyclopsCaveLayout.probeDepth() * width];

      for (int i = 0; i < CyclopsCaveLayout.probeDepth(); i++) {
         for (int j = 0; j < width; j++) {
            int d = i * 6;
            int p = CyclopsCaveLayout.probeP(j);
            terrain[i * width + j] = context.chunkGenerator()
               .getBaseHeight(
                  fromX + inward.getStepX() * d + across.getStepX() * p,
                  fromZ + inward.getStepZ() * d + across.getStepZ() * p,
                  Types.WORLD_SURFACE_WG,
                  context.heightAccessor(),
                  context.randomState()
               );
         }
      }

      CyclopsCaveStructure.Approach best = null;
      int seaLevel = context.chunkGenerator().getSeaLevel();

      for (int m = 0; m < 5; m++) {
         int mouthY = terrain[m * width + 1];
         if (mouthY > seaLevel + 1) {
            int reach = CyclopsCaveLayout.solveReach(seed, side, mouthY, terrain, m);
            int crest = mouthY;

            for (int height : terrain) {
               crest = Math.max(crest, height);
            }

            CyclopsCaveStructure.Approach candidate = new CyclopsCaveStructure.Approach(
               inward, m * 6, mouthY, reach, CyclopsCaveLayout.coverMisfit(seed, side, reach, mouthY, terrain, m), crest
            );
            if (best == null || candidate.better(best)) {
               best = candidate;
            }
         }
      }

      return best;
   }

   private static BoundingBox caveBox(CyclopsCaveLayout layout, CyclopsCaveStructure.Approach approach, int mouthX, int mouthZ) {
      Direction inward = approach.inward();
      Direction across = inward.getClockWise();
      int far = layout.maxD();
      int near = -9;
      int half = 24;
      int x1 = mouthX + inward.getStepX() * near + across.getStepX() * -half;
      int z1 = mouthZ + inward.getStepZ() * near + across.getStepZ() * -half;
      int x2 = mouthX + inward.getStepX() * far + across.getStepX() * half;
      int z2 = mouthZ + inward.getStepZ() * far + across.getStepZ() * half;
      return BoundingBox.fromCorners(
         new BlockPos(x1, approach.mouthY() - 2 - 2, z1), new BlockPos(x2, Math.max(approach.crest(), approach.mouthY() + 16) + 6, z2)
      );
   }

   public StructureType<?> type() {
      return StructureRegistry.CYCLOPS_CAVE.get();
   }

   private record Approach(Direction inward, int mouthD, int mouthY, int reach, int misfit, int crest) {
      boolean better(CyclopsCaveStructure.Approach other) {
         return this.misfit != other.misfit ? this.misfit < other.misfit : this.reach < other.reach;
      }
   }
}
