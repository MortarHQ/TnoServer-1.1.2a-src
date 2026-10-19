package com.blamejared.crafttweaker.impl.command.type.conflict;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator.OfLong;

final class RecipeLongIterator implements OfLong {
   private final int size;
   private int currentLeft;
   private int currentRight;
   private boolean kill;

   RecipeLongIterator(int size) {
      this.size = size;
      this.currentLeft = 0;
      this.currentRight = this.currentLeft + 1;
      this.kill = size <= 1;
   }

   @Override
   public long nextLong() {
      if (this.kill) {
         throw new NoSuchElementException();
      }

      long current = make(this.currentLeft, this.currentRight);
      this.currentRight++;
      if (this.currentRight >= this.size) {
         this.currentLeft++;
         if (this.currentLeft >= this.size - 1) {
            this.kill = true;
         }

         this.currentRight = this.currentLeft + 1;
      }

      return current;
   }

   @Override
   public boolean hasNext() {
      return !this.kill;
   }

   long estimateLength() {
      return this.size * (this.size - 1L) / 2L;
   }

   static long make(int left, int right) {
      return (long)left << 32 | right;
   }

   static int first(long val) {
      return (int)(val >> 32);
   }

   static int second(long val) {
      return (int)val;
   }
}
