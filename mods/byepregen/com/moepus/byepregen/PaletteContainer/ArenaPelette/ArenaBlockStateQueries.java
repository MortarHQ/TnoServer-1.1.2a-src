package com.moepus.byepregen.PaletteContainer.ArenaPelette;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.PalettedContainer.CountConsumer;

final class ArenaBlockStateQueries {
   private ArenaBlockStateQueries() {
   }

   static void getAll(ArenaBlockStatePalettedContainer container, Consumer<BlockState> consumer) {
      if (container.isUniform()) {
         consumer.accept(Block.stateById(container.uniformRawId()));
      } else if (container.hasPagePalettes()) {
         getAllPagePaletteStates(container, consumer);
      } else if (!container.hasDenseIds()) {
         IntSet seen = new IntOpenHashSet();
         forEachRawId(container, (sectionIndex, rawIdx) -> {
            if (seen.add(rawIdx)) {
               consumer.accept(Block.stateById(rawIdx));
            }
         });
      } else {
         IntIterator seen = container.denseRawIdCounts().keySet().iterator();

         while (seen.hasNext()) {
            int rawId = (Integer)seen.next();
            consumer.accept(Block.stateById(rawId));
         }
      }
   }

   static boolean maybeHas(ArenaBlockStatePalettedContainer container, Predicate<BlockState> predicate) {
      if (container.isUniform()) {
         return predicate.test(Block.stateById(container.uniformRawId()));
      }

      if (container.hasPagePalettes()) {
         return maybeHasPagePaletteState(container, predicate);
      }

      IntIterator var2 = container.denseRawIdCounts().keySet().iterator();

      while (var2.hasNext()) {
         int rawId = (Integer)var2.next();
         if (predicate.test(Block.stateById(rawId))) {
            return true;
         }
      }

      return false;
   }

   static void count(ArenaBlockStatePalettedContainer container, CountConsumer<BlockState> consumer) {
      if (container.isUniform()) {
         consumer.accept(Block.stateById(container.uniformRawId()), 4096);
      } else if (container.hasDenseIds()) {
         container.denseRawIdCounts().int2IntEntrySet().forEach(entry -> consumer.accept(Block.stateById(entry.getIntKey()), entry.getIntValue()));
      } else {
         Int2IntOpenHashMap counts = new Int2IntOpenHashMap();
         countRawIds(container, counts);
         counts.int2IntEntrySet().forEach(entry -> consumer.accept(Block.stateById(entry.getIntKey()), entry.getIntValue()));
      }
   }

   static void forEachRawId(ArenaBlockStatePalettedContainer container, ArenaBlockStateQueries.RawIdConsumer consumer) {
      for (int i = 0; i < 4096; i++) {
         consumer.accept(i, container.rawIdAt(i));
      }
   }

   static void countRawIds(ArenaBlockStatePalettedContainer container, Int2IntOpenHashMap counts) {
      if (container.hasDenseIds()) {
         container.denseRawIdCounts().int2IntEntrySet().forEach(entry -> counts.addTo(entry.getIntKey(), entry.getIntValue()));
      } else if (!container.hasPagePalettes()) {
         forEachRawId(container, (sectionIndex, rawId) -> counts.addTo(rawId, 1));
      } else {
         int[] paletteCounts = new int[16];

         for (int page = 0; page < 4; page++) {
            Arrays.fill(paletteCounts, 0);
            int base = container.arenaPageBase(page);
            countPagePaletteIndexes(container, base, paletteCounts);
            addPagePaletteCounts(container, base, paletteCounts, counts);
         }
      }
   }

   private static void countPagePaletteIndexes(ArenaBlockStatePalettedContainer container, int base, int[] paletteCounts) {
      for (int wordIndex = 0; wordIndex < 128; wordIndex++) {
         int word = container.arenaPaletteWord(base, wordIndex);
         paletteCounts[word & 15]++;
         paletteCounts[word >>> 4 & 15]++;
         paletteCounts[word >>> 8 & 15]++;
         paletteCounts[word >>> 12 & 15]++;
         paletteCounts[word >>> 16 & 15]++;
         paletteCounts[word >>> 20 & 15]++;
         paletteCounts[word >>> 24 & 15]++;
         paletteCounts[word >>> 28 & 15]++;
      }
   }

   private static void addPagePaletteCounts(ArenaBlockStatePalettedContainer container, int base, int[] paletteCounts, Int2IntOpenHashMap counts) {
      for (int paletteIndex = 0; paletteIndex < 16; paletteIndex++) {
         int count = paletteCounts[paletteIndex];
         if (count != 0) {
            counts.addTo(container.arenaPaletteRawId(base, paletteIndex), count);
         }
      }
   }

   private static void getAllPagePaletteStates(ArenaBlockStatePalettedContainer container, Consumer<BlockState> consumer) {
      IntSet seen = new IntOpenHashSet();

      for (int page = 0; page < 4; page++) {
         int base = container.arenaPageBase(page);
         int livePaletteMask = container.arenaLivePaletteMask(base);

         for (int paletteIndex = 0; paletteIndex < 16; paletteIndex++) {
            if ((livePaletteMask & 1 << paletteIndex) != 0) {
               int rawId = container.arenaPaletteRawId(base, paletteIndex);
               if (seen.add(rawId)) {
                  consumer.accept(Block.stateById(rawId));
               }
            }
         }
      }
   }

   private static boolean maybeHasPagePaletteState(ArenaBlockStatePalettedContainer container, Predicate<BlockState> predicate) {
      IntSet seen = new IntOpenHashSet();

      for (int page = 0; page < 4; page++) {
         int base = container.arenaPageBase(page);

         for (int paletteIndex = 0; paletteIndex < 16; paletteIndex++) {
            int rawId = container.arenaPaletteRawId(base, paletteIndex);
            if (rawId >= 0 && seen.add(rawId) && predicate.test(Block.stateById(rawId))) {
               return true;
            }
         }
      }

      return false;
   }

   @FunctionalInterface
   public interface RawIdConsumer {
      void accept(int var1, int var2);
   }
}
