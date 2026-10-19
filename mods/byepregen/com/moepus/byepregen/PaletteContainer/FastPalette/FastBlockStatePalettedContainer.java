package com.moepus.byepregen.PaletteContainer.FastPalette;

import com.moepus.byepregen.PaletteContainer.BlockStatePackedDataBuilder;
import net.minecraft.core.IdMap;
import net.minecraft.util.BitStorage;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.GlobalPalette;
import net.minecraft.world.level.chunk.Palette;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.chunk.PalettedContainer.Data;
import net.minecraft.world.level.chunk.PalettedContainer.Strategy;
import net.minecraft.world.level.chunk.PalettedContainerRO.PackedData;
import org.jetbrains.annotations.NotNull;

public final class FastBlockStatePalettedContainer extends FastPalettedContainer<BlockState> {
   private static final int SECTION_SIZE = 4096;
   private static final int MAX_LOCAL_PALETTE_BITS = 8;

   public FastBlockStatePalettedContainer(IdMap<BlockState> idList, BlockState defaultValue, Strategy strategy) {
      super(idList, defaultValue, strategy);
   }

   boolean importVanillaPackedRawIds(int[] paletteRawIds, long[] packedStorage) {
      if (paletteRawIds != null && paletteRawIds.length != 0) {
         int bits = vanillaSerializedBits(paletteRawIds.length);
         Data<BlockState> data = this.createOrReuseData(null, bits);
         int[] localIds = fillPalette(data.palette(), paletteRawIds);
         if (bits != 0 && !this.importStorage(data, paletteRawIds, localIds, packedStorage, bits)) {
            return false;
         }

         this.data = data;
         this.byepregen$updateFastData(data);
         return true;
      } else {
         return false;
      }
   }

   private boolean importStorage(Data<BlockState> data, int[] paletteRawIds, int[] localIds, long[] packedStorage, int bits) {
      if (packedStorage == null || packedStorage.length != packedLength(bits)) {
         return false;
      } else {
         return bits <= 8
            ? writeLocalStorage(data.storage(), paletteRawIds.length, localIds, packedStorage, bits)
            : writeGlobalStorage(data.storage(), paletteRawIds, packedStorage, bits);
      }
   }

   @NotNull
   public BlockState get(int x, int y, int z) {
      return this.getFast(y << 8 | z << 4 | x);
   }

   @NotNull
   protected BlockState get(int index) {
      return this.getFast(index);
   }

   @NotNull
   public BlockState getAndSet(int x, int y, int z, @NotNull BlockState state) {
      return this.getAndSetFast(y << 8 | z << 4 | x, state);
   }

   @NotNull
   public BlockState getAndSetUnchecked(int x, int y, int z, @NotNull BlockState state) {
      return this.getAndSetFast(y << 8 | z << 4 | x, state);
   }

   public void set(int x, int y, int z, BlockState state) {
      this.setFast(y << 8 | z << 4 | x, state);
   }

   public @NotNull PackedData<BlockState> pack(@NotNull IdMap<BlockState> idMap, @NotNull Strategy strategy) {
      if (strategy != Strategy.SECTION_STATES) {
         return super.pack(idMap, strategy);
      }

      BitStorage storage = this.fastData.storage();
      if (storage.getBits() == 0) {
         return BlockStatePackedDataBuilder.packSingle((BlockState)this.fastData.palette().valueFor(0));
      }

      Palette<BlockState> palette = this.fastData.palette();
      return palette instanceof GlobalPalette
         ? BlockStatePackedDataBuilder.pack(storage::get)
         : BlockStatePackedDataBuilder.pack(new FastBlockStatePalettedContainer.LocalPaletteRawIdGetter(storage, palette));
   }

   @NotNull
   public PalettedContainer<BlockState> recreate() {
      return new FastBlockStatePalettedContainer(this.registry, (BlockState)this.fastData.palette().valueFor(0), this.strategy);
   }

   private static int[] fillPalette(Palette<BlockState> palette, int[] paletteRawIds) {
      int[] localIds = new int[paletteRawIds.length];
      if (palette instanceof GlobalPalette) {
         System.arraycopy(paletteRawIds, 0, localIds, 0, paletteRawIds.length);
         return localIds;
      }

      for (int i = 0; i < paletteRawIds.length; i++) {
         localIds[i] = palette.idFor(Block.stateById(paletteRawIds[i]));
      }

      return localIds;
   }

   private static boolean writeLocalStorage(BitStorage storage, int paletteSize, int[] localIds, long[] packedStorage, int serializedBits) {
      long[] output = storage.getRaw();
      int outputBits = storage.getBits();

      for (int index = 0; index < 4096; index++) {
         int localId = packedValueAt(packedStorage, serializedBits, index);
         if (localId >= paletteSize) {
            return false;
         }

         setPackedValue(output, outputBits, index, localIds[localId]);
      }

      return true;
   }

   private static boolean writeGlobalStorage(BitStorage storage, int[] paletteRawIds, long[] packedStorage, int serializedBits) {
      long[] output = storage.getRaw();
      int outputBits = storage.getBits();

      for (int index = 0; index < 4096; index++) {
         int localId = packedValueAt(packedStorage, serializedBits, index);
         if (localId >= paletteRawIds.length) {
            return false;
         }

         setPackedValue(output, outputBits, index, paletteRawIds[localId]);
      }

      return true;
   }

   private static int packedValueAt(long[] packedStorage, int bits, int index) {
      int valuesPerLong = 64 / bits;
      int cell = index / valuesPerLong;
      int shift = (index - cell * valuesPerLong) * bits;
      return (int)(packedStorage[cell] >>> shift & (1L << bits) - 1L);
   }

   private static void setPackedValue(long[] packedStorage, int bits, int index, int value) {
      int valuesPerLong = 64 / bits;
      int cell = index / valuesPerLong;
      int shift = (index - cell * valuesPerLong) * bits;
      packedStorage[cell] |= (value & (1L << bits) - 1L) << shift;
   }

   private static int vanillaSerializedBits(int paletteSize) {
      if (paletteSize == 1) {
         return 0;
      }

      int bits = 32 - Integer.numberOfLeadingZeros(paletteSize - 1);
      return Math.max(4, bits);
   }

   private static int packedLength(int bits) {
      int valuesPerLong = 64 / bits;
      return (4096 + valuesPerLong - 1) / valuesPerLong;
   }

   private static final class LocalPaletteRawIdGetter implements BlockStatePackedDataBuilder.RawIdGetter {
      private final BitStorage storage;
      private final Palette<BlockState> palette;
      private int[] localToRaw = new int[16];

      private LocalPaletteRawIdGetter(BitStorage storage, Palette<BlockState> palette) {
         this.storage = storage;
         this.palette = palette;
      }

      @Override
      public int rawId(int sectionIndex) {
         int localId = this.storage.get(sectionIndex);
         this.ensureLocalCapacity(localId);
         int marker = this.localToRaw[localId];
         if (marker == 0) {
            marker = BlockStatePackedDataBuilder.rawId((BlockState)this.palette.valueFor(localId)) + 1;
            this.localToRaw[localId] = marker;
         }

         return marker - 1;
      }

      private void ensureLocalCapacity(int localId) {
         if (localId >= this.localToRaw.length) {
            int newSize = this.localToRaw.length;

            do {
               newSize *= 2;
            } while (localId >= newSize);

            int[] newLocalToRaw = new int[newSize];
            System.arraycopy(this.localToRaw, 0, newLocalToRaw, 0, this.localToRaw.length);
            this.localToRaw = newLocalToRaw;
         }
      }
   }
}
