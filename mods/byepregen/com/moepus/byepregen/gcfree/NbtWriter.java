package com.moepus.byepregen.gcfree;

import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import java.io.DataOutput;
import java.io.IOException;
import java.io.UTFDataFormatException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import net.minecraft.nbt.Tag;
import sun.misc.Unsafe;

public final class NbtWriter implements DataOutput {
   private static final int DEFAULT_CAPACITY = 65536;
   private static final int GROWTH_FACTOR = 2;
   private static final Unsafe UNSAFE = loadUnsafe();
   private static final long BYTE_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(byte[].class);
   private long buffer;
   private long capacity;
   private long offset;

   public NbtWriter() {
      this(65536);
   }

   public NbtWriter(int initialCapacity) {
      this.capacity = Math.max(1, initialCapacity);
      this.buffer = UNSAFE.allocateMemory(this.capacity);
   }

   public void startRootCompound() {
      this.writeByte(10);
      this.writeShort(0);
   }

   public void compoundEntryStart() {
   }

   public void finishCompound() {
      this.writeByte(0);
   }

   public void putBoolean(byte[] name, boolean value) {
      this.putByte(name, (byte)(value ? 1 : 0));
   }

   public void putByte(byte[] name, byte value) {
      this.writeNamedType(1, name);
      this.writeByte(value);
   }

   public void putShort(byte[] name, short value) {
      this.writeNamedType(2, name);
      this.writeShort(value);
   }

   public void putInt(byte[] name, int value) {
      this.writeNamedType(3, name);
      this.writeInt(value);
   }

   public void putLong(byte[] name, long value) {
      this.writeNamedType(4, name);
      this.writeLong(value);
   }

   public void putString(byte[] name, byte[] value) {
      this.writeNamedType(8, name);
      this.writeBytesRaw(value);
   }

   public void putString(byte[] name, String value) {
      this.putString(name, stringBytes(value));
   }

   public void startCompound(byte[] name) {
      this.writeNamedType(10, name);
   }

   public void startFixedList(byte[] name, int size, byte type) {
      this.writeNamedType(9, name);
      this.writeByte(type);
      this.writeInt(size);
   }

   public long startList(byte[] name, byte type) {
      this.writeNamedType(9, name);
      this.writeByte(type);
      long lengthOffset = this.offset;
      this.writeInt(0);
      return lengthOffset;
   }

   public void finishList(long lengthOffset, int size) {
      this.putIntAt(lengthOffset, size);
   }

   public void startFixedListEntry(int size, byte type) {
      this.writeByte(type);
      this.writeInt(size);
   }

   public void putByteArray(byte[] name, byte[] value) {
      this.writeNamedType(7, name);
      this.writeInt(value.length);
      this.write(value, 0, value.length);
   }

   public void putByteArrayFilled(byte[] name, int length, byte value) {
      this.writeNamedType(7, name);
      this.writeInt(length);
      this.ensureCapacity(length);
      UNSAFE.setMemory(this.address(), length, value);
      this.offset += length;
   }

   public void putIntArray(byte[] name, int[] value) {
      this.writeNamedType(11, name);
      this.writeInt(value.length);
      this.ensureCapacity(value.length * 4);

      for (int entry : value) {
         this.insertInt(entry);
      }
   }

   public void putLongArray(byte[] name, long[] value) {
      this.startLongArray(name, value.length);
      this.writeLongArrayPayload(value, value.length);
   }

   public void putLongArray(byte[] name, long[] value, int length) {
      this.startLongArray(name, length);
      this.writeLongArrayPayload(value, length);
   }

   public void putLongArray(byte[] name, LongCollection value) {
      this.startLongArray(name, value.size());
      LongIterator iterator = value.iterator();

      while (iterator.hasNext()) {
         this.writeLongArrayEntry(iterator.nextLong());
      }
   }

   public void startLongArray(byte[] name, int length) {
      this.writeNamedType(12, name);
      this.writeInt(length);
      this.ensureCapacity(length * 8L);
   }

   public void writeLongArrayEntry(long entry) {
      this.insertLong(entry);
   }

   public void putDoubles(byte[] name, double[] value) {
      this.startFixedList(name, value.length, (byte)6);

      for (double entry : value) {
         this.writeDouble(entry);
      }
   }

   public void putTag(byte[] name, Tag tag) {
      this.writeNamedType(tag.getId(), name);
      this.writeTagPayload(tag);
   }

   public void putTagEntry(Tag tag) {
      this.writeTagPayload(tag);
   }

   public byte[] toByteArray() {
      if (this.buffer == 0L) {
         throw new IllegalStateException("NbtWriter has been released");
      }

      int length = Math.toIntExact(this.offset);
      byte[] bytes = new byte[length];
      UNSAFE.copyMemory(null, this.buffer, bytes, BYTE_ARRAY_OFFSET, length);
      return bytes;
   }

   public RawChunkData toRawChunkData(int maxSlackBytes) {
      byte[] bytes = this.toByteArray();
      return new RawChunkData(bytes, bytes.length);
   }

   public void release() {
      if (this.buffer != 0L) {
         UNSAFE.freeMemory(this.buffer);
         this.buffer = 0L;
      }
   }

   @Override
   public void write(int value) {
      this.writeByte(value);
   }

   @Override
   public void write(byte[] value) {
      this.write(value, 0, value.length);
   }

   @Override
   public void write(byte[] value, int off, int len) {
      this.ensureCapacity(len);
      UNSAFE.copyMemory(value, BYTE_ARRAY_OFFSET + off, null, this.address(), len);
      this.offset += len;
   }

   @Override
   public void writeBoolean(boolean value) {
      this.writeByte(value ? 1 : 0);
   }

   @Override
   public void writeByte(int value) {
      this.ensureCapacity(1L);
      this.insertByte(value);
   }

   @Override
   public void writeShort(int value) {
      this.ensureCapacity(2L);
      this.insertShort(value);
   }

   @Override
   public void writeChar(int value) {
      this.writeShort(value);
   }

   @Override
   public void writeInt(int value) {
      this.ensureCapacity(4L);
      this.insertInt(value);
   }

   @Override
   public void writeLong(long value) {
      this.ensureCapacity(8L);
      this.insertLong(value);
   }

   @Override
   public void writeFloat(float value) {
      this.writeInt(Float.floatToRawIntBits(value));
   }

   @Override
   public void writeDouble(double value) {
      this.writeLong(Double.doubleToRawLongBits(value));
   }

   @Override
   public void writeBytes(String value) {
      this.write(value.getBytes(StandardCharsets.ISO_8859_1));
   }

   @Override
   public void writeChars(String value) {
      for (int i = 0; i < value.length(); i++) {
         this.writeChar(value.charAt(i));
      }
   }

   @Override
   public void writeUTF(String value) throws IOException {
      this.writeBytesRaw(stringBytes(value));
   }

   public static byte[] asciiName(String value) {
      byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

      for (byte b : bytes) {
         if (b <= 0) {
            throw new IllegalArgumentException("NBT name is not ascii-safe: " + value);
         }
      }

      return wrapUtfBytes(bytes);
   }

   public static byte[] stringBytes(String value) {
      byte[] bytes = new byte[value.length() * 3 + 2];
      int index = 2;

      for (int i = 0; i < value.length(); i++) {
         index = writeUtfChar(bytes, index, value.charAt(i));
      }

      int length = index - 2;
      if (length > 65535) {
         throw new RuntimeException(new UTFDataFormatException("String too large"));
      }

      bytes[0] = (byte)(length >>> 8);
      bytes[1] = (byte)length;
      return Arrays.copyOf(bytes, index);
   }

   private void writeLongArrayPayload(long[] value, int length) {
      for (int i = 0; i < length; i++) {
         this.writeLongArrayEntry(value[i]);
      }
   }

   private void writeNamedType(int type, byte[] name) {
      this.ensureCapacity(1L + name.length);
      this.insertByte(type);
      this.insertBytes(name);
   }

   private void writeBytesRaw(byte[] bytes) {
      this.write(bytes, 0, bytes.length);
   }

   private void writeTagPayload(Tag tag) {
      try {
         tag.write(this);
      } catch (IOException e) {
         throw new IllegalStateException("Unexpected in-memory NBT write failure", e);
      }
   }

   private void putIntAt(long index, int value) {
      UNSAFE.putInt(this.buffer + index, Integer.reverseBytes(value));
   }

   private void ensureCapacity(long extra) {
      if (this.buffer == 0L) {
         throw new IllegalStateException("NbtWriter has been released");
      }

      long required = this.offset + extra;
      if (required > this.capacity) {
         do {
            this.capacity *= 2L;
         } while (required > this.capacity);

         this.buffer = UNSAFE.reallocateMemory(this.buffer, this.capacity);
      }
   }

   private long address() {
      return this.buffer + this.offset;
   }

   private void insertByte(int value) {
      UNSAFE.putByte(this.address(), (byte)value);
      this.offset++;
   }

   private void insertShort(int value) {
      UNSAFE.putShort(this.address(), Short.reverseBytes((short)value));
      this.offset += 2L;
   }

   private void insertInt(int value) {
      UNSAFE.putInt(this.address(), Integer.reverseBytes(value));
      this.offset += 4L;
   }

   private void insertLong(long value) {
      UNSAFE.putLong(this.address(), Long.reverseBytes(value));
      this.offset += 8L;
   }

   private void insertBytes(byte[] value) {
      UNSAFE.copyMemory(value, BYTE_ARRAY_OFFSET, null, this.address(), value.length);
      this.offset += value.length;
   }

   private static int writeUtfChar(byte[] bytes, int index, char c) {
      if (c >= 1 && c <= 127) {
         bytes[index++] = (byte)c;
      } else if (c <= 2047) {
         bytes[index++] = (byte)(192 | c >> 6);
         bytes[index++] = (byte)(128 | c & 63);
      } else {
         bytes[index++] = (byte)(224 | c >> '\f');
         bytes[index++] = (byte)(128 | c >> 6 & 63);
         bytes[index++] = (byte)(128 | c & 63);
      }

      return index;
   }

   private static byte[] wrapUtfBytes(byte[] bytes) {
      byte[] wrapped = new byte[bytes.length + 2];
      wrapped[0] = (byte)(bytes.length >>> 8);
      wrapped[1] = (byte)bytes.length;
      System.arraycopy(bytes, 0, wrapped, 2, bytes.length);
      return wrapped;
   }

   private static Unsafe loadUnsafe() {
      try {
         Field field = Unsafe.class.getDeclaredField("theUnsafe");
         field.setAccessible(true);
         return (Unsafe)field.get(null);
      } catch (ReflectiveOperationException exception) {
         throw new ExceptionInInitializerError(exception);
      }
   }
}
