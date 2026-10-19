package cn.star.structblend.terrain;

import java.util.OptionalInt;
import java.util.function.Predicate;

public final class ColumnHeightScan<T> implements Predicate<T> {
   private final Predicate<T> surfacePredicate;
   private final Predicate<T> floorPredicate;
   private final int topExclusive;
   private final int scanSize;
   private int visited;
   private int surfaceIndex = -1;
   private int floorIndex = -1;

   public ColumnHeightScan(Predicate<T> surfacePredicate, Predicate<T> floorPredicate, int topExclusive, int scanSize) {
      this.surfacePredicate = surfacePredicate;
      this.floorPredicate = floorPredicate;
      this.topExclusive = topExclusive;
      this.scanSize = scanSize;
   }

   @Override
   public boolean test(T state) {
      int index = this.visited++;
      if (this.surfaceIndex < 0 && this.surfacePredicate.test(state)) {
         this.surfaceIndex = index;
      }

      if (this.floorIndex < 0 && this.floorPredicate.test(state)) {
         this.floorIndex = index;
      }

      return this.surfaceIndex >= 0 && this.floorIndex >= 0;
   }

   public boolean accepts(OptionalInt terminalHeight) {
      if (this.visited > this.scanSize) {
         return false;
      }

      boolean bothFound = this.surfaceIndex >= 0 && this.floorIndex >= 0;
      return terminalHeight.isPresent()
         ? bothFound && this.visited > 0 && terminalHeight.getAsInt() == this.topExclusive - this.visited + 1
         : !bothFound && this.visited == this.scanSize;
   }

   public OptionalInt surfaceHeight() {
      return this.surfaceIndex < 0 ? OptionalInt.empty() : OptionalInt.of(this.topExclusive - this.surfaceIndex);
   }

   public OptionalInt floorHeight() {
      return this.floorIndex < 0 ? OptionalInt.empty() : OptionalInt.of(this.topExclusive - this.floorIndex);
   }

   public int visited() {
      return this.visited;
   }
}
