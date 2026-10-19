package com.moepus.byepregen.PaletteContainer.ArenaPelette.Codecs;

import com.moepus.byepregen.PaletteContainer.ArenaPelette.ArenaBlockStatePalettedContainer;
import com.moepus.byepregen.PaletteContainer.ArenaPelette.Layout;
import java.util.Arrays;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.BitStorage;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.Palette;
import net.minecraft.world.level.chunk.PalettedContainer.Data;

public final class StateImporter {
   private static final int MAX_LOCAL_PALETTE_BITS = 8;

   private StateImporter() {
   }

   public static void importPackedPalette(ArenaBlockStatePalettedContainer container, BlockState[] paletteStates, long[] packedStorage) {
      if (paletteStates != null && packedStorage != null) {
         container.releaseRawIds();
         int[] rawIds = new int[paletteStates.length];

         for (int i = 0; i < paletteStates.length; i++) {
            rawIds[i] = ArenaBlockStatePalettedContainer.rawId(paletteStates[i]);
         }

         if (!tryImportPacked4BitPalette(container, rawIds, packedStorage, rawIds.length)) {
            int[] pageRawIds = new int[17];
            StateImporter.RawIdSource source = index -> packedRawIdAt(rawIds, packedStorage, index);

            for (int page = 0; page < 4; page++) {
               if (!importPage(container, page, source, pageRawIds)) {
                  importPagesToDense(container, page, source);
                  return;
               }
            }

            container.tryPromoteFullUniformSection();
         }
      }
   }

   public static boolean importVanillaPackedRawIds(ArenaBlockStatePalettedContainer container, int[] paletteRawIds, long[] packedStorage) {
      if (paletteRawIds != null && paletteRawIds.length != 0) {
         container.releaseRawIds();
         if (paletteRawIds.length == 1) {
            container.setUniformSection(sanitizeRawId(paletteRawIds[0]));
            return true;
         }

         int bits = vanillaSerializedBits(paletteRawIds.length);
         if (packedStorage != null && packedStorage.length == packedLength(bits)) {
            if (bits == 4 && tryImportPacked4BitPalette(container, paletteRawIds, packedStorage, paletteRawIds.length)) {
               return true;
            }

            int[] pageRawIds = new int[17];
            StateImporter.VanillaPackedRawIdSource source = new StateImporter.VanillaPackedRawIdSource(paletteRawIds, packedStorage, bits);

            for (int page = 0; page < 4; page++) {
               if (!importPage(container, page, source, pageRawIds)) {
                  importPagesToDense(container, page, source);
                  return source.isValid();
               }

               if (!source.isValid()) {
                  return false;
               }
            }

            container.tryPromoteFullUniformSection();
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   static void importData(ArenaBlockStatePalettedContainer container, Data<BlockState> data) {
      container.releaseRawIds();
      Palette<BlockState> palette = data.palette();
      BitStorage storage = data.storage();
      int bits = storage.getBits();
      if (bits == 0) {
         container.setUniformSection(ArenaBlockStatePalettedContainer.rawId((BlockState)palette.valueFor(0)));
      } else {
         int[] localToRaw = new int[1 << bits];
         int[] pageRawIds = new int[17];
         StateImporter.RawIdSource source = index -> rawIdForLocal(palette, localToRaw, storage.get(index));

         for (int page = 0; page < 4; page++) {
            if (!importPage(container, page, source, pageRawIds)) {
               importPagesToDense(container, page, source);
               return;
            }
         }

         container.tryPromoteFullUniformSection();
      }
   }

   public static void importNetworkData(ArenaBlockStatePalettedContainer container, FriendlyByteBuf buffer) {
      container.releaseRawIds();
      int bits = buffer.readByte();
      if (bits == 0) {
         container.setUniformSection(sanitizeRawId(buffer.readVarInt()));
         discardLongArray(buffer);
      } else {
         if (bits <= 8) {
            importNetworkLocalPalette(container, buffer, bits);
         } else {
            importNetworkGlobalPalette(container, buffer, bits);
         }
      }
   }

   private static void importNetworkLocalPalette(ArenaBlockStatePalettedContainer container, FriendlyByteBuf buffer, int bits) {
      int paletteSize = buffer.readVarInt();
      int[] paletteRawIds = new int[paletteSize];

      for (int i = 0; i < paletteSize; i++) {
         paletteRawIds[i] = sanitizeRawId(buffer.readVarInt());
      }

      int storageLength = buffer.readVarInt();
      if (bits == 4 && paletteSize <= 16 && storageLength == packedLength(bits)) {
         importNetworkPacked4BitPalette(container, paletteRawIds, buffer);
      } else {
         long[] packedStorage = readLongArray(buffer, storageLength);
         importFromSource(container, new StateImporter.VanillaPackedRawIdSource(paletteRawIds, packedStorage, bits));
      }
   }

   private static void importNetworkGlobalPalette(ArenaBlockStatePalettedContainer container, FriendlyByteBuf buffer, int bits) {
      long[] packedStorage = readLongArray(buffer, buffer.readVarInt());
      importFromSource(container, new StateImporter.GlobalPackedRawIdSource(packedStorage, bits));
   }

   private static void importNetworkPacked4BitPalette(ArenaBlockStatePalettedContainer container, int[] paletteRawIds, FriendlyByteBuf buffer) {
      int[] arena = container.ensureArena();
      int defaultRawId = sanitizeRawId(paletteRawIds[0]);

      for (int page = 0; page < 4; page++) {
         int base = Layout.pageBase(page);
         clearPage(arena, base, defaultRawId);
         fillPagePalette(arena, base, paletteRawIds, paletteRawIds.length);
         readPacked4BitPage(arena, base, buffer, paletteRawIds.length);
      }

      container.tryPromoteFullUniformSection();
   }

   private static boolean tryImportPacked4BitPalette(ArenaBlockStatePalettedContainer container, int[] paletteRawIds, long[] packedStorage, int paletteSize) {
      if (paletteSize <= 1 || paletteSize > 16 || packedStorage.length != packedLength(4)) {
         return false;
      }

      if (paletteSize < 16 && hasInvalidLocalId(packedStorage, paletteSize)) {
         return false;
      }

      int[] arena = container.ensureArena();
      int defaultRawId = sanitizeRawId(paletteRawIds[0]);

      for (int page = 0; page < 4; page++) {
         int base = Layout.pageBase(page);
         clearPage(arena, base, defaultRawId);
         fillPagePalette(arena, base, paletteRawIds, paletteSize);
         copyPacked4BitPage(arena, base, packedStorage, page);
      }

      container.tryPromoteFullUniformSection();
      return true;
   }

   private static void fillPagePalette(int[] arena, int base, int[] paletteRawIds, int paletteSize) {
      for (int i = 1; i < paletteSize; i++) {
         arena[base + 129 + i - 1] = sanitizeRawId(paletteRawIds[i]) + 1;
      }
   }

   private static void copyPacked4BitPage(int[] arena, int base, long[] packedStorage, int page) {
      int sourceY = page << 2;

      for (int y = 0; y < 4; y++) {
         int sourceRow = sourceY + y << 4;
         int targetRow = y << 5;

         for (int z = 0; z < 16; z++) {
            long packed = packedStorage[sourceRow + z];
            int target = base + targetRow + (z << 1);
            arena[target] = (int)packed;
            arena[target + 1] = (int)(packed >>> 32);
         }
      }
   }

   private static void readPacked4BitPage(int[] arena, int base, FriendlyByteBuf buffer, int paletteSize) {
      for (int y = 0; y < 4; y++) {
         int targetRow = y << 5;

         for (int z = 0; z < 16; z++) {
            long packed = sanitizePacked4BitWord(buffer.readLong(), paletteSize);
            int target = base + targetRow + (z << 1);
            arena[target] = (int)packed;
            arena[target + 1] = (int)(packed >>> 32);
         }
      }
   }

   private static long sanitizePacked4BitWord(long packed, int paletteSize) {
      if (paletteSize == 16) {
         return packed;
      }

      long sanitized = 0L;
      long word = packed;

      for (int i = 0; i < 16; i++) {
         int localId = (int)word & 15;
         if (localId < paletteSize) {
            sanitized |= (long)localId << (i << 2);
         }

         word >>>= 4;
      }

      return sanitized;
   }

   private static boolean hasInvalidLocalId(long[] packedStorage, int paletteSize) {
      for (long packed : packedStorage) {
         long word = packed;

         for (int i = 0; i < 16; i++) {
            if (((int)word & 15) >= paletteSize) {
               return true;
            }

            word >>>= 4;
         }
      }

      return false;
   }

   private static boolean importPage(ArenaBlockStatePalettedContainer container, int page, StateImporter.RawIdSource source, int[] pageRawIds) {
      int uniqueCount = 0;

      for (int local = 0; local < 1024; local++) {
         int index = Layout.sectionIndex(page, local);
         uniqueCount = addUniqueRawId(pageRawIds, uniqueCount, source.rawId(index));
         if (uniqueCount > 16) {
            container.promoteToDense();
            return false;
         }
      }

      int defaultRawId = pageRawIds[0];
      if (uniqueCount == 1 && container.isUniformRawId(defaultRawId)) {
         return true;
      }

      int[] arena = container.ensureArena();
      int base = Layout.pageBase(page);
      clearPage(arena, base, defaultRawId);

      for (int i = 1; i < uniqueCount; i++) {
         arena[base + 129 + i - 1] = pageRawIds[i] + 1;
      }

      for (int local = 0; local < 1024; local++) {
         int index = Layout.sectionIndex(page, local);
         int rawId = source.rawId(index);
         if (rawId != defaultRawId) {
            Layout.setLocalPaletteIndex(arena, base, local, paletteIndexOf(pageRawIds, uniqueCount, rawId));
         }
      }

      return true;
   }

   private static void importFromSource(ArenaBlockStatePalettedContainer container, StateImporter.RawIdSource source) {
      int[] pageRawIds = new int[17];

      for (int page = 0; page < 4; page++) {
         if (!importPage(container, page, source, pageRawIds)) {
            importPagesToDense(container, page, source);
            return;
         }
      }

      container.tryPromoteFullUniformSection();
   }

   private static void discardLongArray(FriendlyByteBuf buffer) {
      int length = buffer.readVarInt();

      for (int i = 0; i < length; i++) {
         buffer.readLong();
      }
   }

   private static long[] readLongArray(FriendlyByteBuf buffer, int length) {
      long[] values = new long[length];

      for (int i = 0; i < length; i++) {
         values[i] = buffer.readLong();
      }

      return values;
   }

   private static void importPagesToDense(ArenaBlockStatePalettedContainer container, int firstPage, StateImporter.RawIdSource source) {
      for (int page = firstPage; page < 4; page++) {
         for (int local = 0; local < 1024; local++) {
            container.setRawId(Layout.sectionIndex(page, local), source.rawId(Layout.sectionIndex(page, local)));
         }
      }
   }

   private static void clearPage(int[] arena, int base, int defaultRawId) {
      Arrays.fill(arena, base, base + 144, 0);
      arena[base + 128] = defaultRawId;
   }

   private static int addUniqueRawId(int[] rawIds, int uniqueCount, int rawId) {
      for (int i = 0; i < uniqueCount; i++) {
         if (rawIds[i] == rawId) {
            return uniqueCount;
         }
      }

      if (uniqueCount == rawIds.length) {
         return uniqueCount + 1;
      }

      rawIds[uniqueCount] = rawId;
      return uniqueCount + 1;
   }

   private static int paletteIndexOf(int[] paletteRawIds, int paletteSize, int rawId) {
      for (int i = 0; i < paletteSize; i++) {
         if (paletteRawIds[i] == rawId) {
            return i;
         }
      }

      return 0;
   }

   private static int sanitizeRawId(int rawId) {
      return rawId < 0 ? ArenaBlockStatePalettedContainer.AIR_RAW_ID : rawId;
   }

   private static int rawIdForLocal(Palette<BlockState> palette, int[] localToRaw, int localId) {
      int marker = localId < localToRaw.length ? localToRaw[localId] : 0;
      if (marker != 0) {
         return marker - 1;
      }

      int rawId = ArenaBlockStatePalettedContainer.rawId((BlockState)palette.valueFor(localId));
      if (localId < localToRaw.length) {
         localToRaw[localId] = rawId + 1;
      }

      return rawId;
   }

   private static int packedRawIdAt(int[] rawIds, long[] packedStorage, int sectionIndex) {
      int yz = sectionIndex >> 4;
      int x = sectionIndex & 15;
      int paletteIndex = (int)(packedStorage[yz] >>> (x << 2) & 15L);
      return rawIds[paletteIndex];
   }

   private static int vanillaSerializedBits(int paletteSize) {
      int bits = 32 - Integer.numberOfLeadingZeros(paletteSize - 1);
      return Math.max(4, bits);
   }

   private static int packedLength(int bits) {
      int valuesPerLong = 64 / bits;
      return (4096 + valuesPerLong - 1) / valuesPerLong;
   }

   private static final class GlobalPackedRawIdSource implements StateImporter.RawIdSource {
      private final long[] packedStorage;
      private final long mask;
      private final int bits;
      private final int valuesPerLong;

      private GlobalPackedRawIdSource(long[] packedStorage, int bits) {
         this.packedStorage = packedStorage;
         this.bits = bits;
         this.valuesPerLong = 64 / bits;
         this.mask = (1L << bits) - 1L;
      }

      @Override
      public int rawId(int sectionIndex) {
         int cell = sectionIndex / this.valuesPerLong;
         int shift = (sectionIndex - cell * this.valuesPerLong) * this.bits;
         return StateImporter.sanitizeRawId((int)(this.packedStorage[cell] >>> shift & this.mask));
      }
   }

   @FunctionalInterface
   private interface RawIdSource {
      int rawId(int var1);
   }

   private static final class VanillaPackedRawIdSource implements StateImporter.RawIdSource {
      private final int[] paletteRawIds;
      private final long[] packedStorage;
      private final long mask;
      private final int bits;
      private final int valuesPerLong;
      private boolean valid = true;

      private VanillaPackedRawIdSource(int[] paletteRawIds, long[] packedStorage, int bits) {
         this.paletteRawIds = paletteRawIds;
         this.packedStorage = packedStorage;
         this.bits = bits;
         this.valuesPerLong = 64 / bits;
         this.mask = (1L << bits) - 1L;
      }

      @Override
      public int rawId(int sectionIndex) {
         int cell = sectionIndex / this.valuesPerLong;
         int shift = (sectionIndex - cell * this.valuesPerLong) * this.bits;
         int localId = (int)(this.packedStorage[cell] >>> shift & this.mask);
         if (localId >= this.paletteRawIds.length) {
            this.valid = false;
            return ArenaBlockStatePalettedContainer.AIR_RAW_ID;
         } else {
            return StateImporter.sanitizeRawId(this.paletteRawIds[localId]);
         }
      }

      private boolean isValid() {
         return this.valid;
      }
   }
}
