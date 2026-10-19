package com.moepus.byepregen.PaletteContainer.ArenaPelette;

import com.moepus.byepregen.PaletteContainer.ArenaPelette.Codecs.SerializationScratch;
import com.moepus.byepregen.PaletteContainer.FastPalette.FastBlockStatePalettedContainer;
import com.moepus.byepregen.mixin.LevelChunkSectionAccessor;
import net.minecraft.util.BitStorage;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.GlobalPalette;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.Palette;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.chunk.PalettedContainer.Data;
import net.minecraft.world.level.chunk.PalettedContainer.Strategy;

public final class ArenaSectionMaterializer {
   private static final BlockState AIR = Blocks.AIR.defaultBlockState();
   private static final int MIN_LOCAL_PALETTE_BITS = 4;
   private static final int MAX_LOCAL_PALETTE_BITS = 8;
   private static final int GLOBAL_PALETTE_BITS = 9;

   private ArenaSectionMaterializer() {
   }

   public static void materializeChunk(ChunkAccess chunk) {
      materializeChunkToFast(chunk);
   }

   public static void materializeChunkToFast(ChunkAccess chunk) {
      for (LevelChunkSection section : chunk.getSections()) {
         if (section.getStates() instanceof ArenaBlockStatePalettedContainer arenaContainer) {
            ((LevelChunkSectionAccessor)section).byepregen$setStates(materialize(arenaContainer));
            arenaContainer.releaseRawIds();
         }
      }
   }

   public static void materializeChunkToVanilla(ChunkAccess chunk) {
      for (LevelChunkSection section : chunk.getSections()) {
         if (section.getStates() instanceof ArenaBlockStatePalettedContainer arenaContainer) {
            ((LevelChunkSectionAccessor)section).byepregen$setStates(materializeVanilla(arenaContainer));
            arenaContainer.releaseRawIds();
         }
      }
   }

   public static FastBlockStatePalettedContainer materialize(ArenaBlockStatePalettedContainer container) {
      if (container.isUniform()) {
         return createFastSingle(Block.stateById(container.uniformRawId()));
      }

      SerializationScratch scratch = SerializationScratch.get();

      try {
         scratch.collect(container);
         if (scratch.paletteSize() == 1) {
            return createFastSingle(Block.stateById(scratch.paletteRawId(0)));
         }

         FastBlockStatePalettedContainer materialized = createFastSingle(AIR);
         Data<BlockState> data = createMaterializedData(materialized, scratch, container);
         materialized.data = data;
         materialized.byepregen$updateFastData(data);
         return materialized;
      } finally {
         scratch.clear();
      }
   }

   private static PalettedContainer<BlockState> materializeVanilla(ArenaBlockStatePalettedContainer container) {
      if (container.isUniform()) {
         return createVanillaSingle(Block.stateById(container.uniformRawId()));
      }

      SerializationScratch scratch = SerializationScratch.get();

      try {
         scratch.collect(container);
         if (scratch.paletteSize() == 1) {
            return createVanillaSingle(Block.stateById(scratch.paletteRawId(0)));
         }

         PalettedContainer<BlockState> materialized = createVanillaSingle(AIR);
         materialized.data = createMaterializedData(materialized, scratch, container);
         return materialized;
      } finally {
         scratch.clear();
      }
   }

   private static FastBlockStatePalettedContainer createFastSingle(BlockState state) {
      return new FastBlockStatePalettedContainer(Block.BLOCK_STATE_REGISTRY, state, Strategy.SECTION_STATES);
   }

   private static PalettedContainer<BlockState> createVanillaSingle(BlockState state) {
      return new PalettedContainer(Block.BLOCK_STATE_REGISTRY, state, Strategy.SECTION_STATES);
   }

   private static Data<BlockState> createMaterializedData(
      PalettedContainer<BlockState> materialized, SerializationScratch scratch, ArenaBlockStatePalettedContainer container
   ) {
      Data<BlockState> data = materialized.createOrReuseData(null, storageBits(scratch.paletteSize()));
      fillPalette(data.palette(), scratch);
      writeStorage(data.storage(), scratch, container);
      return data;
   }

   private static int storageBits(int paletteSize) {
      int bits = 32 - Integer.numberOfLeadingZeros(paletteSize - 1);
      return bits > 8 ? 9 : Math.max(4, bits);
   }

   private static void fillPalette(Palette<BlockState> palette, SerializationScratch scratch) {
      if (!(palette instanceof GlobalPalette)) {
         int size = scratch.paletteSize();

         for (int i = 0; i < size; i++) {
            palette.idFor(Block.stateById(scratch.paletteRawId(i)));
         }
      }
   }

   private static void writeStorage(BitStorage storage, SerializationScratch scratch, ArenaBlockStatePalettedContainer container) {
      int bits = storage.getBits();
      if (bits != 0) {
         long[] output = storage.getRaw();
         int valuesPerLong = 64 / bits;
         long mask = (1L << bits) - 1L;
         boolean globalPalette = scratch.paletteSize() > 256;
         if (container.hasPagePalettes() && !globalPalette) {
            writePagePaletteStorage(output, valuesPerLong, mask, bits, scratch, container);
         } else {
            for (int index = 0; index < 4096; index++) {
               int rawId = Math.max(container.rawIdAt(index), 0);
               int id = globalPalette ? rawId : scratch.localIdFor(rawId);
               writePackedId(output, valuesPerLong, mask, bits, index, id);
            }
         }
      }
   }

   private static void writePagePaletteStorage(
      long[] output, int valuesPerLong, long mask, int bits, SerializationScratch scratch, ArenaBlockStatePalettedContainer container
   ) {
      for (int page = 0; page < 4; page++) {
         int base = container.arenaPageBase(page);
         int pageLocalIdOffset = page * 16;
         int sectionIndex = page << 10;

         for (int wordIndex = 0; wordIndex < 128; wordIndex++) {
            int word = container.arenaPaletteWord(base, wordIndex);
            writePackedId(output, valuesPerLong, mask, bits, sectionIndex++, scratch.pageLocalIdAtOffset(pageLocalIdOffset, word & 15));
            writePackedId(output, valuesPerLong, mask, bits, sectionIndex++, scratch.pageLocalIdAtOffset(pageLocalIdOffset, word >>> 4 & 15));
            writePackedId(output, valuesPerLong, mask, bits, sectionIndex++, scratch.pageLocalIdAtOffset(pageLocalIdOffset, word >>> 8 & 15));
            writePackedId(output, valuesPerLong, mask, bits, sectionIndex++, scratch.pageLocalIdAtOffset(pageLocalIdOffset, word >>> 12 & 15));
            writePackedId(output, valuesPerLong, mask, bits, sectionIndex++, scratch.pageLocalIdAtOffset(pageLocalIdOffset, word >>> 16 & 15));
            writePackedId(output, valuesPerLong, mask, bits, sectionIndex++, scratch.pageLocalIdAtOffset(pageLocalIdOffset, word >>> 20 & 15));
            writePackedId(output, valuesPerLong, mask, bits, sectionIndex++, scratch.pageLocalIdAtOffset(pageLocalIdOffset, word >>> 24 & 15));
            writePackedId(output, valuesPerLong, mask, bits, sectionIndex++, scratch.pageLocalIdAtOffset(pageLocalIdOffset, word >>> 28 & 15));
         }
      }
   }

   private static void writePackedId(long[] output, int valuesPerLong, long mask, int bits, int index, int id) {
      int cell = index / valuesPerLong;
      int shift = (index - cell * valuesPerLong) * bits;
      output[cell] |= (id & mask) << shift;
   }
}
