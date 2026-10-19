package com.moepus.byepregen.PaletteContainer.ArenaPelette.Codecs;

import com.moepus.byepregen.PaletteContainer.ArenaPelette.ArenaBlockStatePalettedContainer;
import it.unimi.dsi.fastutil.ints.IntIterator;
import java.util.Arrays;

final class PaletteScratch {
   private static final int INITIAL_PALETTE_CAPACITY = 64;
   private static final int INITIAL_LOOKUP_CAPACITY = 128;
   private static final int HASH_MULTIPLIER = -1640531527;
   private int[] lookupKeys;
   private int[] lookupValues;
   private int[] paletteRawIds = new int[64];
   private byte[] pageLocalIds;
   private int paletteSize;

   int size() {
      return this.paletteSize;
   }

   int rawId(int localId) {
      return this.paletteRawIds[localId];
   }

   byte[] pageLocalIds() {
      return this.pageLocalIds;
   }

   void collectRawIds(ArenaBlockStatePalettedContainer container) {
      if (!container.hasDenseIds()) {
         container.forEachRawId(this::addRawId);
      } else {
         IntIterator var2 = container.denseRawIdCounts().keySet().iterator();

         while (var2.hasNext()) {
            int rawId = (Integer)var2.next();
            this.localId(Math.max(rawId, 0));
         }
      }
   }

   void collectPagePalette(ArenaBlockStatePalettedContainer container) {
      this.ensurePageLocalIds();

      for (int page = 0; page < 4; page++) {
         this.collectPage(container, page);
      }
   }

   void collectPagePaletteRawIds(ArenaBlockStatePalettedContainer container) {
      for (int page = 0; page < 4; page++) {
         int base = container.arenaPageBase(page);

         for (int mask = container.arenaLivePaletteMask(base); mask != 0; mask &= mask - 1) {
            int paletteIndex = Integer.numberOfTrailingZeros(mask);
            this.addPaletteRawIdWithoutLookup(container.arenaPaletteRawId(base, paletteIndex));
         }
      }
   }

   int localIdFor(int rawId) {
      this.ensureLookup();
      int slot = this.findSlot(Math.max(rawId, 0));
      int marker = this.lookupValues[slot];
      return marker == 0 ? 0 : marker - 1;
   }

   void clear() {
      if (this.lookupValues != null) {
         Arrays.fill(this.lookupValues, 0);
      }

      if (this.pageLocalIds != null) {
         Arrays.fill(this.pageLocalIds, (byte)0);
      }

      this.paletteSize = 0;
   }

   private void collectPage(ArenaBlockStatePalettedContainer container, int page) {
      int base = container.arenaPageBase(page);
      int mask = container.arenaLivePaletteMask(base);
      int offset = page * 16;

      while (mask != 0) {
         int paletteIndex = Integer.numberOfTrailingZeros(mask);
         int rawId = container.arenaPaletteRawId(base, paletteIndex);
         this.pageLocalIds[offset + paletteIndex] = (byte)this.addPaletteRawIdWithoutLookup(rawId);
         mask &= mask - 1;
      }
   }

   private void addRawId(int sectionIndex, int rawId) {
      this.localId(Math.max(rawId, 0));
   }

   private int addPaletteRawIdWithoutLookup(int rawId) {
      rawId = Math.max(rawId, 0);

      for (int i = 0; i < this.paletteSize; i++) {
         if (this.paletteRawIds[i] == rawId) {
            return i;
         }
      }

      int localId = this.paletteSize++;
      this.ensurePaletteCapacity(this.paletteSize);
      this.paletteRawIds[localId] = rawId;
      return localId;
   }

   private int localId(int rawId) {
      this.ensureLookup();
      int slot = this.findSlot(rawId);
      int marker = this.lookupValues[slot];
      if (marker != 0) {
         return marker - 1;
      }

      int localId = this.paletteSize;
      this.ensurePaletteCapacity(localId + 1);
      if ((localId + 1) * 2 > this.lookupKeys.length) {
         this.growLookup();
         slot = this.findSlot(rawId);
      }

      this.paletteSize = localId + 1;
      this.lookupKeys[slot] = rawId;
      this.lookupValues[slot] = localId + 1;
      this.paletteRawIds[localId] = rawId;
      return localId;
   }

   private void ensureLookup() {
      if (this.lookupKeys == null) {
         this.lookupKeys = new int[128];
         this.lookupValues = new int[128];
      }
   }

   private void ensurePageLocalIds() {
      if (this.pageLocalIds == null) {
         this.pageLocalIds = new byte[64];
      }
   }

   private void ensurePaletteCapacity(int required) {
      if (this.paletteRawIds.length < required) {
         int newLength = this.paletteRawIds.length;

         do {
            newLength *= 2;
         } while (newLength < required);

         this.paletteRawIds = Arrays.copyOf(this.paletteRawIds, newLength);
      }
   }

   private void growLookup() {
      this.lookupKeys = new int[this.lookupKeys.length * 2];
      this.lookupValues = new int[this.lookupValues.length * 2];

      for (int i = 0; i < this.paletteSize; i++) {
         int slot = this.findSlot(this.paletteRawIds[i]);
         this.lookupKeys[slot] = this.paletteRawIds[i];
         this.lookupValues[slot] = i + 1;
      }
   }

   private int findSlot(int rawId) {
      int slot = mix(rawId) & this.lookupKeys.length - 1;

      while (this.lookupValues[slot] != 0 && this.lookupKeys[slot] != rawId) {
         slot = slot + 1 & this.lookupKeys.length - 1;
      }

      return slot;
   }

   private static int mix(int value) {
      int hash = value * -1640531527;
      return hash ^ hash >>> 16;
   }
}
