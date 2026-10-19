package com.moepus.byepregen.PaletteContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.PalettedContainerRO.PackedData;

public final class BlockStatePackedDataBuilder {
   private static final int SECTION_SIZE = 4096;
   private static final int MIN_SECTION_STATE_BITS = 4;
   private static final ThreadLocal<BlockStatePackedDataBuilder.Scratch> SCRATCH = ThreadLocal.withInitial(BlockStatePackedDataBuilder.Scratch::new);

   private BlockStatePackedDataBuilder() {
   }

   public static PackedData<BlockState> pack(BlockStatePackedDataBuilder.RawIdGetter rawIds) {
      BlockStatePackedDataBuilder.Scratch scratch = SCRATCH.get();

      for (int i = 0; i < 4096; i++) {
         scratch.addRawId(rawIds.rawId(i), i);
      }

      return scratch.finish();
   }

   public static PackedData<BlockState> packSingle(BlockState state) {
      return new PackedData(List.of(state), Optional.empty());
   }

   public static int rawId(BlockState state) {
      int id = state == null ? 0 : Block.BLOCK_STATE_REGISTRY.getId(state);
      return id < 0 ? 0 : id;
   }

   private static int serializedBits(int paletteSize) {
      if (paletteSize == 1) {
         return 0;
      }

      int bits = 32 - Integer.numberOfLeadingZeros(paletteSize - 1);
      return Math.max(4, bits);
   }

   private static long[] packLocalIds(int[] localIds, int bits) {
      int valuesPerLong = 64 / bits;
      long mask = (1L << bits) - 1L;
      long[] packed = new long[(4096 + valuesPerLong - 1) / valuesPerLong];

      for (int i = 0; i < 4096; i++) {
         int cell = i / valuesPerLong;
         int bitOffset = (i - cell * valuesPerLong) * bits;
         packed[cell] |= (localIds[i] & mask) << bitOffset;
      }

      return packed;
   }

   @FunctionalInterface
   public interface RawIdGetter {
      int rawId(int var1);
   }

   private static final class Scratch {
      private int[] rawToLocal = new int[Math.max(1024, Block.BLOCK_STATE_REGISTRY.size())];
      private final int[] touchedRawIds = new int[4096];
      private final int[] paletteRawIds = new int[4096];
      private final int[] localIds = new int[4096];
      private int touchedCount;
      private int paletteSize;

      private void addRawId(int rawId, int sectionIndex) {
         if (rawId < 0) {
            rawId = 0;
         }

         this.ensureRawCapacity(rawId);
         this.localIds[sectionIndex] = this.localId(rawId);
      }

      private PackedData<BlockState> finish() {
         try {
            List<BlockState> paletteEntries = this.createPaletteEntries();
            int bits = BlockStatePackedDataBuilder.serializedBits(this.paletteSize);
            if (bits == 0) {
               return new PackedData(paletteEntries, Optional.empty());
            }

            long[] packed = BlockStatePackedDataBuilder.packLocalIds(this.localIds, bits);
            return new PackedData(paletteEntries, Optional.of(Arrays.stream(packed)));
         } finally {
            this.clear();
         }
      }

      private int localId(int rawId) {
         int marker = this.rawToLocal[rawId];
         if (marker != 0) {
            return marker - 1;
         }

         int localId = this.paletteSize++;
         this.rawToLocal[rawId] = localId + 1;
         this.touchedRawIds[this.touchedCount++] = rawId;
         this.paletteRawIds[localId] = rawId;
         return localId;
      }

      private List<BlockState> createPaletteEntries() {
         List<BlockState> entries = new ArrayList<>(this.paletteSize);

         for (int i = 0; i < this.paletteSize; i++) {
            entries.add(Block.stateById(this.paletteRawIds[i]));
         }

         return entries;
      }

      private void clear() {
         for (int i = 0; i < this.touchedCount; i++) {
            this.rawToLocal[this.touchedRawIds[i]] = 0;
         }

         this.touchedCount = 0;
         this.paletteSize = 0;
      }

      private void ensureRawCapacity(int rawId) {
         if (rawId >= this.rawToLocal.length) {
            int newSize = this.rawToLocal.length;

            do {
               newSize *= 2;
            } while (rawId >= newSize);

            this.rawToLocal = Arrays.copyOf(this.rawToLocal, newSize);
         }
      }
   }
}
