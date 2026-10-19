package net.pixeldreamstudios.mythika.worldgen;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.Plane;
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

public class LabyrinthStructure extends Structure {
   public static final MapCodec<LabyrinthStructure> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(settingsCodec(instance), Codec.intRange(5, 25).optionalFieldOf("cells", 11).forGetter(structure -> structure.cells))
         .apply(instance, LabyrinthStructure::new)
   );
   private static final int COVER = 6;
   private static final int MAX_CLIMB = 48;
   private final int cells;

   public LabyrinthStructure(StructureSettings settings, int cells) {
      super(settings);
      this.cells = cells;
   }

   protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
      WorldgenRandom random = context.random();
      LabyrinthLayout layout = LabyrinthLayout.generate(this.cells, random);
      int span = layout.span();
      ChunkPos chunkPos = context.chunkPos();
      int originX = chunkPos.getMinBlockX();
      int originZ = chunkPos.getMinBlockZ();
      int floorY = Math.max(getLowestY(context, span, span) - 6 - 12, context.heightAccessor().getMinBuildHeight() + 4);
      int ceilingY = floorY + 12;
      LabyrinthStructure.Approach best = null;

      for (Direction candidate : Plane.HORIZONTAL) {
         LabyrinthStructure.Approach approach = this.solveApproach(context, layout, originX, originZ, floorY, candidate);
         if (best == null || approach.better(best)) {
            best = approach;
         }
      }

      int mid = this.cells / 2;
      Direction outward = best.outward();
      int entranceCell = outward.getAxis() == Axis.Z
         ? layout.cellIndex(mid, outward == Direction.NORTH ? 0 : this.cells - 1)
         : layout.cellIndex(outward == Direction.WEST ? 0 : this.cells - 1, mid);
      BoundingBox maze = new BoundingBox(originX, floorY - 1, originZ, originX + span - 1, ceilingY, originZ + span - 1);
      StructurePiecesBuilder builder = new StructurePiecesBuilder();
      builder.addPiece(new LabyrinthPiece(maze, floorY, layout, entranceCell));
      builder.addPiece(
         new LabyrinthEntrancePiece(this.entranceBox(best, floorY), floorY, best.mouth().getX(), best.mouth().getZ(), outward, best.steps(), best.profile())
      );
      BlockPos centre = new BlockPos(originX + span / 2, floorY, originZ + span / 2);
      return Optional.of(new GenerationStub(centre, Either.right(builder)));
   }

   private LabyrinthStructure.Approach solveApproach(GenerationContext context, LabyrinthLayout layout, int originX, int originZ, int floorY, Direction outward) {
      BlockPos mouth = this.mouthOf(outward, layout, originX, originZ);
      int reach = 107;
      int[] ground = new int[reach + 1];

      for (int i = 0; i <= reach; i++) {
         int d = 19 + i;
         ground[i] = context.chunkGenerator()
            .getBaseHeight(
               mouth.getX() + outward.getStepX() * d,
               mouth.getZ() + outward.getStepZ() * d,
               Types.WORLD_SURFACE_WG,
               context.heightAccessor(),
               context.randomState()
            );
      }

      int steps = LabyrinthLayout.solveEntranceSteps(floorY, ground, 96);
      int[] profile = new int[steps + 3 + 8 + 1];
      System.arraycopy(ground, 0, profile, 0, profile.length);
      return new LabyrinthStructure.Approach(outward, mouth, steps, LabyrinthLayout.entranceMisfit(floorY, ground, steps), profile);
   }

   private BlockPos mouthOf(Direction outward, LabyrinthLayout layout, int originX, int originZ) {
      int span = layout.span();
      int centre = span / 2;
      int inner = 1;

      return switch (outward) {
         case NORTH -> new BlockPos(originX + centre, 0, originZ + inner);
         case SOUTH -> new BlockPos(originX + centre, 0, originZ + span - 1 - inner);
         case WEST -> new BlockPos(originX + inner, 0, originZ + centre);
         default -> new BlockPos(originX + span - 1 - inner, 0, originZ + centre);
      };
   }

   private BoundingBox entranceBox(LabyrinthStructure.Approach approach, int floorY) {
      BlockPos mouth = approach.mouth();
      int side = 6;
      int top = LabyrinthLayout.portalY(floorY, approach.steps()) + 10;

      for (int height : approach.profile()) {
         top = Math.max(top, height + 2);
      }

      BlockPos far = mouth.relative(approach.outward(), LabyrinthEntrancePiece.outwardExtent(approach.steps()));
      return BoundingBox.fromCorners(new BlockPos(mouth.getX(), floorY - 2, mouth.getZ()), new BlockPos(far.getX(), top, far.getZ())).inflatedBy(side, 0, side);
   }

   public StructureType<?> type() {
      return StructureRegistry.LABYRINTH.get();
   }

   private record Approach(Direction outward, BlockPos mouth, int steps, int misfit, int[] profile) {
      boolean better(LabyrinthStructure.Approach other) {
         return this.misfit != other.misfit ? this.misfit < other.misfit : this.steps < other.steps;
      }
   }
}
