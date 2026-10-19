package xaero.map.file.worldsave.biome;

import net.minecraft.nbt.ListTag;
import net.minecraft.util.BitStorage;
import net.minecraft.util.Mth;
import net.minecraft.util.SimpleBitStorage;
import net.minecraft.util.SimpleBitStorage.InitializationException;

public class WorldDataReaderSectionBiomeData {
   private final ListTag paletteTag;
   private final long[] biomesLongArray;
   private BitStorage biomesBitArray;
   private boolean triedReadingData;

   public WorldDataReaderSectionBiomeData(ListTag paletteTag, long[] biomesLongArray) {
      this.paletteTag = paletteTag;
      this.biomesLongArray = biomesLongArray;
   }

   public boolean hasDifferentBiomes() {
      return this.biomesLongArray != null;
   }

   public String get(int quadX, int sectionQuadY, int quadZ) {
      if (!this.hasDifferentBiomes()) {
         return this.paletteTag.isEmpty() ? null : this.paletteTag.get(0).getAsString();
      }

      if (!this.triedReadingData && this.biomesBitArray == null && this.biomesLongArray != null) {
         this.triedReadingData = true;
         int bits = Mth.ceillog2(this.paletteTag.size());

         try {
            this.biomesBitArray = new SimpleBitStorage(bits, 64, this.biomesLongArray);
         } catch (InitializationException var6) {
         }
      }

      if (this.biomesBitArray == null) {
         return this.paletteTag.isEmpty() ? null : this.paletteTag.get(0).getAsString();
      }

      int pos3D = sectionQuadY << 4 | quadZ << 2 | quadX;
      int biomePaletteIndex = this.biomesBitArray.get(pos3D);
      return biomePaletteIndex >= this.paletteTag.size() ? null : this.paletteTag.getString(biomePaletteIndex);
   }
}
