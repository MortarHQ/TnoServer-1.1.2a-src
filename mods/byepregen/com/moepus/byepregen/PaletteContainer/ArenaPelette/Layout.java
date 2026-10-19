package com.moepus.byepregen.PaletteContainer.ArenaPelette;

public final class Layout {
   public static final int SECTION_SIZE = 4096;
   private static final int PAGE_HEIGHT = 4;
   public static final int PAGE_COUNT = 4;
   public static final int PAGE_SIZE = 1024;
   public static final int BITS_PER_ENTRY = 4;
   private static final int ENTRIES_PER_WORD = 8;
   public static final int INDEX_WORDS_PER_PAGE = 128;
   public static final int PAGE_PALETTE_SIZE = 16;
   public static final int PAGE_DEFAULT_OFFSET = 128;
   public static final int EXTRA_PALETTE_OFFSET = 129;
   static final int EXTRA_PALETTE_SIZE = 15;
   public static final int PAGE_STRIDE = 144;
   static final int ARENA_INTS = 576;
   static final int PALETTE_INDEX_MASK = 15;

   private Layout() {
   }

   static int pageLivePaletteMask(int[] arena, int base) {
      int seen = 0;

      for (int wordIndex = 0; wordIndex < 128; wordIndex++) {
         int word = arena[base + wordIndex];
         seen |= 1 << (word & 15);
         seen |= 1 << (word >>> 4 & 15);
         seen |= 1 << (word >>> 8 & 15);
         seen |= 1 << (word >>> 12 & 15);
         seen |= 1 << (word >>> 16 & 15);
         seen |= 1 << (word >>> 20 & 15);
         seen |= 1 << (word >>> 24 & 15);
         seen |= 1 << (word >>> 28 & 15);
      }

      return seen;
   }

   public static int localPaletteIndex(int[] arena, int base, int local) {
      int word = arena[base + (local >>> 3)];
      int shift = (local & 7) << 2;
      return word >>> shift & 15;
   }

   public static void setLocalPaletteIndex(int[] arena, int base, int local, int paletteIndex) {
      int wordIndex = base + (local >>> 3);
      int shift = (local & 7) << 2;
      int oldWord = arena[wordIndex];
      arena[wordIndex] = oldWord & ~(15 << shift) | (paletteIndex & 15) << shift;
   }

   static int pageIndexFromSectionIndex(int sectionIndex) {
      return sectionIndex >>> 10;
   }

   static int pageLocalIndexFromSectionIndex(int sectionIndex) {
      int y = sectionIndex >>> 8;
      return (y & 3) << 8 | sectionIndex & 0xFF;
   }

   public static int sectionIndex(int page, int local) {
      int y = page << 2 | local >>> 8;
      return y << 8 | local & 0xFF;
   }

   public static int pageBase(int page) {
      return page * 144;
   }

   public static int localIndex(int x, int y, int z) {
      return y << 8 | z << 4 | x;
   }
}
