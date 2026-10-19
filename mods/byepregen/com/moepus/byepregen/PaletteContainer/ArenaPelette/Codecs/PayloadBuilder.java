package com.moepus.byepregen.PaletteContainer.ArenaPelette.Codecs;

import com.moepus.byepregen.PaletteContainer.ArenaPelette.ArenaBlockStatePalettedContainer;
import com.moepus.byepregen.gcfree.BlockStateNbtCache;
import com.moepus.byepregen.gcfree.NbtWriter;

public final class PayloadBuilder {
   private static final byte[] PALETTE = NbtWriter.asciiName("palette");
   private static final byte[] DATA = NbtWriter.asciiName("data");

   private PayloadBuilder() {
   }

   public static byte[] uniform(int rawId) {
      byte[] payload = new byte[uniformPayloadSize(rawId)];
      PayloadWriter writer = new PayloadWriter(payload);
      writePaletteHeader(writer, 1);
      writePaletteEntry(writer, rawId);
      writer.writeByte(0);
      writer.finish();
      return payload;
   }

   public static byte[] packed(SerializationScratch scratch, ArenaBlockStatePalettedContainer container) {
      int paletteSize = scratch.paletteSize();
      int packedLength = paletteSize > 1 ? packedLength(paletteSize) : 0;
      byte[] payload = new byte[packedPayloadSize(scratch, packedLength)];
      PayloadWriter writer = new PayloadWriter(payload);
      writePalette(writer, scratch);
      if (paletteSize > 1) {
         writeData(writer, scratch, container, packedLength);
      }

      writer.writeByte(0);
      writer.finish();
      return payload;
   }

   private static int uniformPayloadSize(int rawId) {
      return namedHeaderSize(PALETTE) + listHeaderSize() + paletteEntrySize(rawId) + 1;
   }

   private static int packedPayloadSize(SerializationScratch scratch, int packedLength) {
      int size = namedHeaderSize(PALETTE) + listHeaderSize();

      for (int i = 0; i < scratch.paletteSize(); i++) {
         size += paletteEntrySize(scratch.paletteRawId(i));
      }

      if (packedLength > 0) {
         size += namedHeaderSize(DATA) + 4 + packedLength * 8;
      }

      return size + 1;
   }

   private static void writePalette(PayloadWriter writer, SerializationScratch scratch) {
      int size = scratch.paletteSize();
      writePaletteHeader(writer, size);

      for (int i = 0; i < size; i++) {
         writePaletteEntry(writer, scratch.paletteRawId(i));
      }
   }

   private static void writeData(PayloadWriter writer, SerializationScratch scratch, ArenaBlockStatePalettedContainer container, int packedLength) {
      writer.writeNamedType(12, DATA);
      writer.writeInt(packedLength);
      int actualLength = scratch.beginSectionPack();
      if (actualLength != packedLength) {
         throw new IllegalStateException("Packed data length changed during block state encode");
      }

      scratch.writePayloadData(writer, container);
   }

   private static void writePaletteHeader(PayloadWriter writer, int size) {
      writer.writeNamedType(9, PALETTE);
      writer.writeByte(10);
      writer.writeInt(size);
   }

   private static void writePaletteEntry(PayloadWriter writer, int rawId) {
      writer.writeBytes(BlockStateNbtCache.rawIdEntryBytes(rawId));
      writer.writeByte(0);
   }

   private static int paletteEntrySize(int rawId) {
      return BlockStateNbtCache.rawIdEntryBytes(rawId).length + 1;
   }

   private static int namedHeaderSize(byte[] name) {
      return 1 + name.length;
   }

   private static int listHeaderSize() {
      return 5;
   }

   private static int packedLength(int paletteSize) {
      int bits = SerializationScratch.localPaletteBits(paletteSize);
      return SerializationScratch.packedLength(bits);
   }
}
