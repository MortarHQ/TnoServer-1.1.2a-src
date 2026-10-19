package cn.star.structblend.terrain;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public final class HeightQueryDiagnostics {
   public static final boolean ENABLED = Boolean.getBoolean("structblend.heightDiagnostics");
   private static final int HISTORY_LIMIT = 65536;
   private static final HeightQueryDiagnostics.Totals SURFACE = ENABLED ? new HeightQueryDiagnostics.Totals() : null;
   private static final HeightQueryDiagnostics.Totals FLOOR = ENABLED ? new HeightQueryDiagnostics.Totals() : null;
   private static final LongAdder FLOOR_SKIPS = ENABLED ? new LongAdder() : null;
   private static final LongAdder SMALL_CUT_SKIPS = ENABLED ? new LongAdder() : null;
   private static final LongAdder PAIRED_SCANS = ENABLED ? new LongAdder() : null;
   private static final LongAdder PAIRED_REJECTED = ENABLED ? new LongAdder() : null;
   private static final LongAdder PREDICTION_PASSES = ENABLED ? new LongAdder() : null;
   private static final LongAdder BATCH_FLUSHES = ENABLED ? new LongAdder() : null;
   private static final LongAdder BATCH_SCANNED = ENABLED ? new LongAdder() : null;
   private static final LongAdder BATCH_CACHED = ENABLED ? new LongAdder() : null;
   private static final LongAdder BATCH_FALLBACKS = ENABLED ? new LongAdder() : null;
   private static final LongAdder BATCH_VERIFIED = ENABLED ? new LongAdder() : null;
   private static final LongAdder BATCH_MISMATCHES = ENABLED ? new LongAdder() : null;
   private static final LongAdder BATCH_NANOS = ENABLED ? new LongAdder() : null;
   private static final ThreadLocal<int[]> SUPPRESSED = ENABLED ? ThreadLocal.withInitial(() -> new int[1]) : null;

   private HeightQueryDiagnostics() {
   }

   static void withoutCounters(Runnable action) {
      if (!ENABLED) {
         action.run();
      } else {
         int[] depth = SUPPRESSED.get();
         depth[0]++;

         try {
            action.run();
         } finally {
            depth[0]--;
         }
      }
   }

   private static boolean counting() {
      return ENABLED && SUPPRESSED.get()[0] == 0;
   }

   static void predictionPass() {
      if (ENABLED) {
         PREDICTION_PASSES.increment();
      }
   }

   static void batchFlushed(int scanned, int cached, long nanos) {
      if (ENABLED) {
         BATCH_FLUSHES.increment();
         BATCH_SCANNED.add(scanned);
         BATCH_CACHED.add(cached);
         BATCH_NANOS.add(nanos);
      }
   }

   static void batchFallback() {
      if (ENABLED) {
         BATCH_FALLBACKS.increment();
      }
   }

   static void batchVerified(int columns, int mismatches) {
      if (ENABLED) {
         BATCH_VERIFIED.add(columns);
         BATCH_MISMATCHES.add(mismatches);
      }
   }

   static HeightQueryDiagnostics.Tracker tracker(boolean oceanFloor) {
      return ENABLED ? new HeightQueryDiagnostics.Tracker(oceanFloor ? FLOOR : SURFACE, 65536) : null;
   }

   static void floorReplacementSkipped() {
      if (counting()) {
         FLOOR_SKIPS.increment();
      }
   }

   static void smallCutCheckSkipped() {
      if (counting()) {
         SMALL_CUT_SKIPS.increment();
      }
   }

   static void pairedScanCompleted() {
      if (ENABLED) {
         PAIRED_SCANS.increment();
      }
   }

   static void pairedScanRejected() {
      if (ENABLED) {
         PAIRED_REJECTED.increment();
      }
   }

   public static String summary() {
      return !ENABLED
         ? "disabled"
         : "process-cumulative surface={"
            + SURFACE.summary()
            + "} oceanFloor={"
            + FLOOR.summary()
            + "} floorNoopChecks="
            + FLOOR_SKIPS.sum()
            + " smallCutChecks="
            + SMALL_CUT_SKIPS.sum()
            + " pairedScans="
            + PAIRED_SCANS.sum()
            + " pairedRejected="
            + PAIRED_REJECTED.sum()
            + String.format(
               Locale.ROOT,
               " batch={flushes=%d scanned=%d cached=%d fallbacks=%d predictionPasses=%d verified=%d mismatches=%d scanMs=%.3f}",
               BATCH_FLUSHES.sum(),
               BATCH_SCANNED.sum(),
               BATCH_CACHED.sum(),
               BATCH_FALLBACKS.sum(),
               PREDICTION_PASSES.sum(),
               BATCH_VERIFIED.sum(),
               BATCH_MISMATCHES.sum(),
               BATCH_NANOS.sum() / 1000000.0
            );
   }

   record Key(long position, int minY, int maxY) {
   }

   static final class Totals {
      final LongAdder hits = new LongAdder();
      final LongAdder emptyMisses = new LongAdder();
      final LongAdder conflictMisses = new LongAdder();
      final LongAdder firstTrackedMisses = new LongAdder();
      final LongAdder repeatTrackedMisses = new LongAdder();
      final LongAdder untrackedMisses = new LongAdder();
      final LongAdder concurrentDuplicates = new LongAdder();
      final LongAdder computations = new LongAdder();
      final LongAdder computeNanos = new LongAdder();
      final LongAdder failures = new LongAdder();

      String summary() {
         return String.format(
            Locale.ROOT,
            "hits=%d emptyMisses=%d conflictMisses=%d firstTracked=%d repeatTracked=%d untracked=%d concurrentDuplicates=%d computations=%d failures=%d computeMs=%.3f",
            this.hits.sum(),
            this.emptyMisses.sum(),
            this.conflictMisses.sum(),
            this.firstTrackedMisses.sum(),
            this.repeatTrackedMisses.sum(),
            this.untrackedMisses.sum(),
            this.concurrentDuplicates.sum(),
            this.computations.sum(),
            this.failures.sum(),
            this.computeNanos.sum() / 1000000.0
         );
      }
   }

   static final class Tracker {
      private final HeightQueryDiagnostics.Totals totals;
      private final int historyLimit;
      private final Set<HeightQueryDiagnostics.Key> history = new HashSet<>();
      private final ConcurrentHashMap<HeightQueryDiagnostics.Key, Integer> inFlight = new ConcurrentHashMap<>();

      Tracker(HeightQueryDiagnostics.Totals totals, int historyLimit) {
         this.totals = totals;
         this.historyLimit = historyLimit;
      }

      void hit() {
         this.totals.hits.increment();
      }

      HeightQueryDiagnostics.Key begin(long position, int minY, int maxY, boolean occupiedSlot) {
         (occupiedSlot ? this.totals.conflictMisses : this.totals.emptyMisses).increment();
         HeightQueryDiagnostics.Key key = new HeightQueryDiagnostics.Key(position, minY, maxY);
         synchronized (this.history) {
            if (this.history.contains(key)) {
               this.totals.repeatTrackedMisses.increment();
            } else if (this.history.size() < this.historyLimit) {
               this.history.add(key);
               this.totals.firstTrackedMisses.increment();
            } else {
               this.totals.untrackedMisses.increment();
            }
         }

         this.inFlight.compute(key, (ignored, count) -> {
            if (count != null) {
               this.totals.concurrentDuplicates.increment();
            }

            return count == null ? 1 : count + 1;
         });
         return key;
      }

      void finish(HeightQueryDiagnostics.Key key, long elapsedNanos, boolean success) {
         this.inFlight.compute(key, (ignored, count) -> count != null && count != 1 ? count - 1 : null);
         this.totals.computations.increment();
         this.totals.computeNanos.add(elapsedNanos);
         if (!success) {
            this.totals.failures.increment();
         }
      }

      int historySize() {
         synchronized (this.history) {
            return this.history.size();
         }
      }

      int inFlightSize() {
         return this.inFlight.size();
      }
   }
}
