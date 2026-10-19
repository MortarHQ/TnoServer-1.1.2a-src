package compactio;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public final class CompactBytesDataInput implements CompactDataInput, AutoCloseable {
   private static final int P6 = 64;
   private static final int P7 = 128;
   private static final int P13 = 8192;
   private static final int P14 = 16384;
   private static final int P20 = 1048576;
   private static final int P21 = 2097152;
   private static final int P27 = 134217728;
   private static final int P28 = 268435456;
   private static final long P34 = 17179869184L;
   private static final long P35 = 34359738368L;
   private static final long P41 = 2199023255552L;
   private static final long P42 = 4398046511104L;
   private static final long P48 = 281474976710656L;
   private static final long P49 = 562949953421312L;
   private static final long P55 = 36028797018963968L;
   private static final long P56 = 72057594037927936L;
   private final byte[] data;
   private int offset;

   public CompactBytesDataInput(byte[] data) {
      this.data = data;
      this.offset = 0;
   }

   public CompactBytesDataInput(byte[] data, int offset) {
      this.data = data;
      this.offset = offset;
   }

   @Override
   public void close() {
   }

   @Override
   public boolean readBool() {
      return (this.readByte() & 0xFF) != 0;
   }

   @Override
   public int readByte() {
      return this.data[this.offset++];
   }

   @Override
   public byte readSByte() {
      return this.data[this.offset++];
   }

   @Override
   public short readShort() {
      int b0 = this.data[this.offset++] & 255;
      int b1 = this.data[this.offset++] & 255;
      return (short)(b0 << 8 | b1);
   }

   @Override
   public int readUShort() {
      return this.readShort();
   }

   @Override
   public int readInt() {
      int b0 = this.data[this.offset++] & 255;
      int b1 = this.data[this.offset++] & 255;
      int b2 = this.data[this.offset++] & 255;
      int b3 = this.data[this.offset++] & 255;
      return b0 << 24 | b1 << 16 | b2 << 8 | b3;
   }

   @Override
   public int readUInt() {
      return this.readInt();
   }

   @Override
   public long readLong() {
      long i0 = this.readUInt();
      long i1 = this.readUInt();
      return i0 << 32 | i1;
   }

   @Override
   public long readULong() {
      return this.readLong();
   }

   @Override
   public int readVarInt() {
      int value = this.readVarUInt();
      return (value & 1) == 0 ? value >>> 1 : -((value >>> 1) + 1);
   }

   @Override
   public int readVarUInt() {
      int value = this.data[this.offset++] & 255;
      if ((value & 128) == 0) {
         return value;
      }

      value = value & 127 | (this.data[this.offset++] & 255) << 7;
      if ((value & 16384) == 0) {
         return value;
      }

      value = value & 16383 | (this.data[this.offset++] & 255) << 14;
      if ((value & 2097152) == 0) {
         return value;
      }

      value = value & 2097151 | (this.data[this.offset++] & 255) << 21;
      return (value & 268435456) == 0 ? value : value & 268435455 | (this.data[this.offset++] & 0xFF) << 28;
   }

   @Override
   public long readVarLong() {
      long value = this.readVarULong();
      return Long.compareUnsigned(value & 1L, 0L) == 0 ? value >>> 1 : -((value >>> 1) + 1L);
   }

   @Override
   public long readVarULong() {
      long value = this.data[this.offset++] & 255L;
      if (Long.compareUnsigned(value & 128L, 0L) == 0) {
         return value;
      }

      value = value & 127L | (this.data[this.offset++] & 255L) << 7;
      if (Long.compareUnsigned(value & 16384L, 0L) == 0) {
         return value;
      }

      value = value & 16383L | (this.data[this.offset++] & 255L) << 14;
      if (Long.compareUnsigned(value & 2097152L, 0L) == 0) {
         return value;
      }

      value = value & 2097151L | (this.data[this.offset++] & 255L) << 21;
      if (Long.compareUnsigned(value & 268435456L, 0L) == 0) {
         return value;
      }

      value = value & 268435455L | (this.data[this.offset++] & 255L) << 28;
      if (Long.compareUnsigned(value & 34359738368L, 0L) == 0) {
         return value;
      }

      value = value & 34359738367L | (this.data[this.offset++] & 255L) << 35;
      if (Long.compareUnsigned(value & 4398046511104L, 0L) == 0) {
         return value;
      }

      value = value & 4398046511103L | (this.data[this.offset++] & 255L) << 42;
      if (Long.compareUnsigned(value & 562949953421312L, 0L) == 0) {
         return value;
      }

      value = value & 562949953421311L | (this.data[this.offset++] & 255L) << 49;
      return Long.compareUnsigned(value & 72057594037927936L, 0L) == 0 ? value : value & 72057594037927935L | (this.data[this.offset++] & 255L) << 56;
   }

   @Override
   public float readFloat() {
      return Float.intBitsToFloat(this.readUInt());
   }

   @Override
   public double readDouble() {
      return Double.longBitsToDouble(this.readULong());
   }

   @Override
   public char readChar() {
      return (char)this.readVarUInt();
   }

   @Override
   public String readString() {
      return new String(this.readBytes(), StandardCharsets.UTF_8);
   }

   @Override
   public byte[] readBytes() {
      int size = this.readVarUInt();
      return this.readRawBytes(size);
   }

   @Override
   public byte[] readRawBytes(int size) {
      byte[] result = Arrays.copyOfRange(this.data, this.offset, this.offset + size);
      this.offset += size;
      return result;
   }

   @Override
   public boolean[] readBoolArray() {
      int size = this.readVarUInt();
      boolean[] result = new boolean[size];
      int limitForI = (size + 7) / 8;

      for (int i = 0; i < limitForI; i++) {
         int bvalue = this.readByte();
         int remainingBits = result.length - 8 * i;
         if (remainingBits > 0) {
            result[i * 8 + 0] = (bvalue & 1 & 0xFF) > 0;
         }

         if (remainingBits > 1) {
            result[i * 8 + 2] = (bvalue & 4 & 0xFF) > 0;
         }

         if (remainingBits > 3) {
            result[i * 8 + 3] = (bvalue & 8 & 0xFF) > 0;
         }

         if (remainingBits > 4) {
            result[i * 8 + 4] = (bvalue & 16 & 0xFF) > 0;
         }

         if (remainingBits > 5) {
            result[i * 8 + 5] = (bvalue & 32 & 0xFF) > 0;
         }

         if (remainingBits > 6) {
            result[i * 8 + 6] = (bvalue & 64 & 0xFF) > 0;
         }

         if (remainingBits > 7) {
            result[i * 8 + 7] = (bvalue & 128 & 0xFF) > 0;
         }
      }

      return result;
   }

   @Override
   public byte[] readByteArray() {
      return this.readBytes();
   }

   @Override
   public byte[] readSByteArray() {
      return this.readBytes();
   }

   @Override
   public short[] readShortArray() {
      return this.readShortArrayRaw(this.readVarUInt());
   }

   @Override
   public short[] readShortArrayRaw(int length) {
      short[] result = new short[length];
      int limitForI = result.length;

      for (int i = 0; i < limitForI; i++) {
         result[i] = this.readShort();
      }

      return result;
   }

   @Override
   public short[] readUShortArray() {
      return this.readShortArray();
   }

   @Override
   public short[] readUShortArrayRaw(int length) {
      return this.readShortArrayRaw(length);
   }

   @Override
   public int[] readVarIntArray() {
      return this.readVarIntArrayRaw(this.readVarUInt());
   }

   @Override
   public int[] readVarIntArrayRaw(int length) {
      int[] result = new int[length];
      int limitForI = result.length;

      for (int i = 0; i < limitForI; i++) {
         result[i] = this.readVarInt();
      }

      return result;
   }

   @Override
   public int[] readVarUIntArray() {
      return this.readVarUIntArrayRaw(this.readVarUInt());
   }

   @Override
   public int[] readVarUIntArrayRaw(int length) {
      int[] result = new int[length];
      int limitForI = result.length;

      for (int i = 0; i < limitForI; i++) {
         result[i] = this.readVarUInt();
      }

      return result;
   }

   @Override
   public int[] readIntArray() {
      return this.readIntArrayRaw(this.readVarUInt());
   }

   @Override
   public int[] readIntArrayRaw(int length) {
      int[] result = new int[length];
      int limitForI = result.length;

      for (int i = 0; i < limitForI; i++) {
         result[i] = this.readInt();
      }

      return result;
   }

   @Override
   public int[] readUIntArray() {
      return this.readUIntArrayRaw(this.readVarUInt());
   }

   @Override
   public int[] readUIntArrayRaw(int length) {
      int[] result = new int[length];
      int limitForI = result.length;

      for (int i = 0; i < limitForI; i++) {
         result[i] = this.readUInt();
      }

      return result;
   }

   @Override
   public long[] readVarLongArray() {
      return this.readVarLongArrayRaw(this.readVarUInt());
   }

   @Override
   public long[] readVarLongArrayRaw(int length) {
      long[] result = new long[length];
      int limitForI = result.length;

      for (int i = 0; i < limitForI; i++) {
         result[i] = this.readVarLong();
      }

      return result;
   }

   @Override
   public long[] readVarULongArray() {
      return this.readVarULongArrayRaw(this.readVarUInt());
   }

   @Override
   public long[] readVarULongArrayRaw(int length) {
      long[] result = new long[length];
      int limitForI = result.length;

      for (int i = 0; i < limitForI; i++) {
         result[i] = this.readVarULong();
      }

      return result;
   }

   @Override
   public long[] readLongArray() {
      return this.readLongArrayRaw(this.readVarUInt());
   }

   @Override
   public long[] readLongArrayRaw(int length) {
      long[] result = new long[length];
      int limitForI = result.length;

      for (int i = 0; i < limitForI; i++) {
         result[i] = this.readLong();
      }

      return result;
   }

   @Override
   public long[] readULongArray() {
      return this.readLongArray();
   }

   @Override
   public long[] readULongArrayRaw(int length) {
      return this.readLongArrayRaw(length);
   }

   @Override
   public float[] readFloatArray() {
      return this.readFloatArrayRaw(this.readVarUInt());
   }

   @Override
   public float[] readFloatArrayRaw(int length) {
      float[] result = new float[length];
      int limitForI = result.length;

      for (int i = 0; i < limitForI; i++) {
         result[i] = this.readFloat();
      }

      return result;
   }

   @Override
   public double[] readDoubleArray() {
      return this.readDoubleArrayRaw(this.readVarUInt());
   }

   @Override
   public double[] readDoubleArrayRaw(int length) {
      double[] result = new double[length];
      int limitForI = length;

      for (int i = 0; i < limitForI; i++) {
         result[i] = this.readDouble();
      }

      return result;
   }

   @Override
   public String[] readStringArray() {
      return this.readStringArrayRaw(this.readVarUInt());
   }

   @Override
   public String[] readStringArrayRaw(int length) {
      String[] result = new String[length];
      int limitForI = result.length;

      for (int i = 0; i < limitForI; i++) {
         result[i] = this.readString();
      }

      return result;
   }

   @Override
   public void skip(int bytes) {
      this.offset += bytes;
   }

   @Override
   public boolean hasMore() {
      return this.offset < this.data.length;
   }

   public int getOffset() {
      return this.offset;
   }
}
