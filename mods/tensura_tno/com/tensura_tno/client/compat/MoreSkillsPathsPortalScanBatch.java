package com.tensura_tno.client.compat;

import java.util.Iterator;
import java.util.NoSuchElementException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;

public final class MoreSkillsPathsPortalScanBatch {
   private MoreSkillsPathsPortalScanBatch() {
   }

   public static Iterable<BlockPos> iterable(BlockPos anchor, int[] columnX, int[] columnZ, int verticalDiameter, int verticalRadius, int start, int end) {
      return () -> new MoreSkillsPathsPortalScanBatch.BatchIterator(anchor, columnX, columnZ, verticalDiameter, verticalRadius, start, end);
   }

   private static final class BatchIterator implements Iterator<BlockPos> {
      private final BlockPos anchor;
      private final int[] columnX;
      private final int[] columnZ;
      private final int verticalDiameter;
      private final int verticalRadius;
      private final int end;
      private final MutableBlockPos cursor = new MutableBlockPos();
      private int index;

      private BatchIterator(BlockPos anchor, int[] columnX, int[] columnZ, int verticalDiameter, int verticalRadius, int start, int end) {
         this.anchor = anchor;
         this.columnX = columnX;
         this.columnZ = columnZ;
         this.verticalDiameter = verticalDiameter;
         this.verticalRadius = verticalRadius;
         this.index = start;
         this.end = end;
      }

      @Override
      public boolean hasNext() {
         return this.index < this.end;
      }

      public BlockPos next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         }

         int positionIndex = this.index++;
         int columnIndex = positionIndex / this.verticalDiameter;
         int yOffset = positionIndex % this.verticalDiameter - this.verticalRadius;
         return this.cursor.set(this.anchor.getX() + this.columnX[columnIndex], this.anchor.getY() + yOffset, this.anchor.getZ() + this.columnZ[columnIndex]);
      }
   }
}
