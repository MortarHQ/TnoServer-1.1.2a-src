package dev.shadowako.trlagbegon.hook;

import dev.shadowako.trlagbegon.util.GiantBlockIterationLimiter;
import net.minecraft.core.BlockPos;

public final class IGiantMobBlockScanHooks {
   private IGiantMobBlockScanHooks() {
   }

   public static Iterable<BlockPos> trlag$breakScan(int a, int b, int c, int d, int e, int f) {
      return GiantBlockIterationLimiter.limit(BlockPos.betweenClosed(a, b, c, d, e, f), 256);
   }

   public static Iterable<BlockPos> trlag$digScan(int a, int b, int c, int d, int e, int f) {
      return GiantBlockIterationLimiter.limit(BlockPos.betweenClosed(a, b, c, d, e, f), 192);
   }

   public static Iterable<BlockPos> trlag$fluidScan(int a, int b, int c, int d, int e, int f) {
      return GiantBlockIterationLimiter.limit(BlockPos.betweenClosed(a, b, c, d, e, f), 256);
   }
}
