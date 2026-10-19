package cn.star.structblend.terrain;

import java.util.OptionalInt;
import java.util.function.Predicate;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.Heightmap.Types;

public final class PairedHeightQuery {
   public static final boolean ENABLED = Boolean.parseBoolean(System.getProperty("structblend.pairedHeightSampling", "true"));
   private static final ThreadLocal<PairedHeightQuery.Request> ACTIVE = new ThreadLocal<>();

   private PairedHeightQuery() {
   }

   public static PairedHeightQuery.Request open(NoiseBasedChunkGenerator generator, LevelHeightAccessor heights, RandomState randomState, int x, int z) {
      if (ENABLED && generator.getClass() == NoiseBasedChunkGenerator.class) {
         PairedHeightQuery.Request request = new PairedHeightQuery.Request(generator, heights, randomState, x, z, ACTIVE.get());
         ACTIVE.set(request);
         return request;
      } else {
         return null;
      }
   }

   public static PairedHeightQuery.Request current(Object generator, LevelHeightAccessor heights, RandomState randomState, int x, int z) {
      if (!ENABLED) {
         return null;
      }

      PairedHeightQuery.Request request = ACTIVE.get();
      return request != null
            && !request.computing
            && request.generator == generator
            && request.heights == heights
            && request.randomState == randomState
            && request.x == x
            && request.z == z
         ? request
         : null;
   }

   public static final class Request implements AutoCloseable {
      private final NoiseBasedChunkGenerator generator;
      private final LevelHeightAccessor heights;
      private final RandomState randomState;
      private final int x;
      private final int z;
      private final PairedHeightQuery.Request previous;
      private final Predicate<BlockState> surfacePredicate = Types.WORLD_SURFACE_WG.isOpaque();
      private final Predicate<BlockState> floorPredicate = Types.OCEAN_FLOOR_WG.isOpaque();
      private boolean attempted;
      private boolean computing;
      private OptionalInt surfaceHeight;
      private OptionalInt floorHeight;

      private Request(
         NoiseBasedChunkGenerator generator, LevelHeightAccessor heights, RandomState randomState, int x, int z, PairedHeightQuery.Request previous
      ) {
         this.generator = generator;
         this.heights = heights;
         this.randomState = randomState;
         this.x = x;
         this.z = z;
         this.previous = previous;
      }

      public boolean begin(Predicate<BlockState> predicate) {
         if (!this.attempted && predicate == this.surfacePredicate) {
            this.attempted = true;
            this.computing = true;
            return true;
         } else {
            return false;
         }
      }

      public void complete(ColumnHeightScan<BlockState> scan, OptionalInt terminalHeight) {
         if (scan.accepts(terminalHeight)) {
            this.surfaceHeight = scan.surfaceHeight();
            this.floorHeight = scan.floorHeight();
            if (HeightQueryDiagnostics.ENABLED) {
               HeightQueryDiagnostics.pairedScanCompleted();
            }
         } else if (HeightQueryDiagnostics.ENABLED) {
            HeightQueryDiagnostics.pairedScanRejected();
         }
      }

      public void endComputation() {
         this.computing = false;
      }

      public OptionalInt resultFor(Predicate<BlockState> predicate) {
         if (predicate == this.surfacePredicate) {
            return this.surfaceHeight;
         } else {
            return predicate == this.floorPredicate ? this.floorHeight : null;
         }
      }

      public boolean wasPaired() {
         return this.surfaceHeight != null;
      }

      @Override
      public void close() {
         if (this.previous == null) {
            PairedHeightQuery.ACTIVE.remove();
         } else {
            PairedHeightQuery.ACTIVE.set(this.previous);
         }
      }
   }
}
