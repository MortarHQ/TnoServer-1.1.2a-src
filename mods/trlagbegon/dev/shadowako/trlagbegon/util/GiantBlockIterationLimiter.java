package dev.shadowako.trlagbegon.util;

import java.util.Iterator;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

public final class GiantBlockIterationLimiter {
   public static final int MAX_BREAK = 256;
   public static final int MAX_DIG = 192;
   public static final int MAX_FLUID = 256;

   private GiantBlockIterationLimiter() {
   }

   @NotNull
   public static Iterable<BlockPos> limit(Iterable<BlockPos> full, int max) {
      return () -> new Iterator<BlockPos>() {
         private final Iterator<BlockPos> it = full.iterator();
         private int n;

         @Override
         public boolean hasNext() {
            return this.n < max && this.it.hasNext();
         }

         public BlockPos next() {
            this.n++;
            return this.it.next();
         }
      };
   }
}
