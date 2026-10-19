package org.popcraft.chunky.nbt;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class NBT {
   private NBT() {
   }

   public static Tag load(InputStream inputStream) throws IOException {
      DataInput dataInput = new DataInputStream(inputStream);
      return Tag.load(dataInput);
   }

   public static void save(OutputStream outputStream, Tag tag) throws IOException {
      DataOutput dataOutput = new DataOutputStream(outputStream);
      Tag.save(dataOutput, tag);
   }

   public static Tag loadCompressed(InputStream inputStream) throws IOException {
      try (GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream)) {
         DataInput dataInput = new DataInputStream(gzipInputStream);
         return Tag.load(dataInput);
      }
   }

   public static void saveCompressed(OutputStream outputStream, Tag tag) throws IOException {
      try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream)) {
         DataOutput dataOutput = new DataOutputStream(gzipOutputStream);
         Tag.save(dataOutput, tag);
      }
   }
}
