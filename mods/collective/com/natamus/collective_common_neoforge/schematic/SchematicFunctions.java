package com.natamus.collective_common_neoforge.schematic;

import it.unimi.dsi.fastutil.io.FastBufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;

public class SchematicFunctions {
   public static CompoundTag readCompressed(InputStream inputStream) throws IOException {
      try (DataInputStream dataInputStream = createDecompressorStream(inputStream)) {
         return NbtIo.read(dataInputStream, NbtAccounter.unlimitedHeap());
      }
   }

   private static DataInputStream createDecompressorStream(InputStream inputStream) throws IOException {
      return new DataInputStream(new FastBufferedInputStream(new GZIPInputStream(inputStream)));
   }
}
