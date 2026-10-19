package com.moepus.byepregen.PaletteContainer.FastPalette;

import com.moepus.byepregen.PaletteContainer.ArenaPelette.Codecs.NbtReader;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.PalettedContainer.Strategy;

public final class FastBlockStateNbtReader {
   private static final String PALETTE = "palette";
   private static final String DATA = "data";

   private FastBlockStateNbtReader() {
   }

   public static FastBlockStatePalettedContainer read(CompoundTag blockStatesTag) {
      int[] paletteRawIds = NbtReader.readPaletteRawIds(blockStatesTag.getList("palette", 10));
      if (paletteRawIds == null) {
         return null;
      }

      long[] packedStorage = blockStatesTag.contains("data", 12) ? blockStatesTag.getLongArray("data") : null;
      FastBlockStatePalettedContainer container = new FastBlockStatePalettedContainer(
         Block.BLOCK_STATE_REGISTRY, Blocks.AIR.defaultBlockState(), Strategy.SECTION_STATES
      );
      return container.importVanillaPackedRawIds(paletteRawIds, packedStorage) ? container : null;
   }
}
