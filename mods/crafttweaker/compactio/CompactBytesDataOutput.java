package compactio;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public final class CompactBytesDataOutput implements CompactDataOutput, AutoCloseable {
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
   private byte[] data = new byte[16];
   private int length = 0;

   private void reserve(int bytes) {
      while (this.length + bytes > this.data.length) {
         this.data = Arrays.copyOf(this.data, 2 * this.data.length);
      }
   }

   public byte[] asByteArray() {
      return Arrays.copyOf(this.data, this.length);
   }

   @Override
   public void close() {
   }

   @Override
   public void writeBool(boolean value) {
      this.writeByte(value ? 1 : 0);
   }

   @Override
   public void writeByte(int value) {
      this.reserve(1);
      this.data[this.length++] = (byte)value;
   }

   @Override
   public void writeSByte(byte value) {
      this.writeByte(value);
   }

   @Override
   public void writeShort(short value) {
      this.writeUShort(value);
   }

   @Override
   public void writeUShort(int value) {
      this.reserve(2);
      this.data[this.length++] = (byte)(value >> 8);
      this.data[this.length++] = (byte)value;
   }

   @Override
   public void writeInt(int value) {
      this.writeUInt(value);
   }

   @Override
   public void writeUInt(int value) {
      this.reserve(4);
      this.data[this.length++] = (byte)(value >>> 24);
      this.data[this.length++] = (byte)(value >>> 16);
      this.data[this.length++] = (byte)(value >>> 8);
      this.data[this.length++] = (byte)value;
   }

   @Override
   public void writeLong(long value) {
      this.writeULong(value);
   }

   @Override
   public void writeULong(long value) {
      this.reserve(8);
      this.data[this.length++] = (byte)(value >>> 56);
      this.data[this.length++] = (byte)(value >>> 48);
      this.data[this.length++] = (byte)(value >>> 40);
      this.data[this.length++] = (byte)(value >>> 32);
      this.data[this.length++] = (byte)(value >>> 24);
      this.data[this.length++] = (byte)(value >>> 16);
      this.data[this.length++] = (byte)(value >>> 8);
      this.data[this.length++] = (byte)value;
   }

   @Override
   public void writeVarInt(int value) {
      this.writeVarUInt(value < 0 ? (1 - value << 1) + 1 : value << 1);
   }

   @Override
   public void writeVarUInt(int value) {
      this.reserve(5);
      if (Integer.compareUnsigned(value, 128) < 0) {
         this.data[this.length++] = (byte)(value & 127);
      } else if (Integer.compareUnsigned(value, 16384) < 0) {
         this.data[this.length++] = (byte)(value & 127 | 128);
         this.data[this.length++] = (byte)(value >>> 7 & 127);
      } else if (Integer.compareUnsigned(value, 2097152) < 0) {
         this.data[this.length++] = (byte)(value & 127 | 128);
         this.data[this.length++] = (byte)(value >>> 7 & 127 | 128);
         this.data[this.length++] = (byte)(value >>> 14 & 127);
      } else if (Integer.compareUnsigned(value, 268435456) < 0) {
         this.data[this.length++] = (byte)(value & 127 | 128);
         this.data[this.length++] = (byte)(value >>> 7 & 127 | 128);
         this.data[this.length++] = (byte)(value >>> 14 & 127 | 128);
         this.data[this.length++] = (byte)(value >>> 21 & 127);
      } else {
         this.data[this.length++] = (byte)(value & 127 | 128);
         this.data[this.length++] = (byte)(value >>> 7 & 127 | 128);
         this.data[this.length++] = (byte)(value >>> 14 & 127 | 128);
         this.data[this.length++] = (byte)(value >>> 21 & 127 | 128);
         this.data[this.length++] = (byte)(value >>> 28 & 127);
      }
   }

   @Override
   public void writeVarLong(long value) {
      this.writeVarULong(value < 0L ? (1L - value << 1) + 1L : value << 1);
   }

   @Override
   public void writeVarULong(long value) {
      this.reserve(9);
      if (Long.compareUnsigned(value, 128L) < 0) {
         this.data[this.length++] = (byte)(value & 127L);
      } else if (Long.compareUnsigned(value, 16384L) < 0) {
         this.data[this.length++] = (byte)(value & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 7 & 127L);
      } else if (Long.compareUnsigned(value, 2097152L) < 0) {
         this.data[this.length++] = (byte)(value & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 7 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 14 & 127L);
      } else if (Long.compareUnsigned(value, 268435456L) < 0) {
         this.data[this.length++] = (byte)(value & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 7 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 14 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 21 & 127L);
      } else if (Long.compareUnsigned(value, 34359738368L) < 0) {
         this.data[this.length++] = (byte)(value & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 7 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 14 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 21 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 28 & 127L);
      } else if (Long.compareUnsigned(value, 4398046511104L) < 0) {
         this.data[this.length++] = (byte)(value & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 7 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 14 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 21 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 28 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 35 & 127L);
      } else if (Long.compareUnsigned(value, 562949953421312L) < 0) {
         this.data[this.length++] = (byte)(value & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 7 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 14 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 21 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 28 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 35 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 42 & 127L);
      } else if (Long.compareUnsigned(value, 72057594037927936L) < 0) {
         this.data[this.length++] = (byte)(value & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 7 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 14 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 21 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 28 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 35 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 42 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 49 & 127L);
      } else {
         this.data[this.length++] = (byte)(value & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 7 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 14 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 21 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 28 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 35 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 42 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 49 & 127L | 128L);
         this.data[this.length++] = (byte)(value >>> 56);
      }
   }

   @Override
   public void writeFloat(float value) {
      this.writeUInt(Float.floatToRawIntBits(value));
   }

   @Override
   public void writeDouble(double value) {
      this.writeULong(Double.doubleToRawLongBits(value));
   }

   @Override
   public void writeChar(char value) {
      this.writeVarUInt(value);
   }

   @Override
   public void writeBytes(byte[] data) {
      if (Integer.compareUnsigned(data.length, -1) >= 0) {
         throw new AssertionError("Array length cannot exceed uint limit");
      }

      this.writeVarUInt(data.length);
      this.writeRawBytes(data);
   }

   @Override
   public void writeBytes(byte[] data, int offset, int length) {
      if (Integer.compareUnsigned(length, -1) >= 0) {
         throw new AssertionError("Array length cannot exceed uint limit");
      }

      this.writeVarUInt(length);
      this.writeRawBytes(data, offset, length);
   }

   @Override
   public void writeString(String str) {
      this.writeBytes(str.getBytes(StandardCharsets.UTF_8));
   }

   @Override
   public void writeRawBytes(byte[] value) {
      this.reserve(value.length);
      System.arraycopy(value, 0, this.data, this.length, value.length);
      this.length += value.length;
   }

   @Override
   public void writeRawBytes(byte[] value, int offset, int length) {
      this.reserve(value.length);
      System.arraycopy(value, offset, this.data, this.length, value.length);
      this.length += length;
   }

   @Override
   public void writeBoolArray(boolean[] data) {
      if (Integer.compareUnsigned(data.length, -1) >= 0) {
         throw new AssertionError("Array length cannot exceed uint limit");
      }

      this.writeVarUInt(data.length);

      int i;
      for (i = 0; i < data.length - 7; i += 8) {
         int bvalue = 0;
         if (data[i + 0]) {
            bvalue |= 1;
         }

         if (data[i + 1]) {
            bvalue |= 2;
         }

         if (data[i + 2]) {
            bvalue += 4;
         }

         if (data[i + 3]) {
            bvalue += 8;
         }

         if (data[i + 4]) {
            bvalue += 16;
         }

         if (data[i + 5]) {
            bvalue += 32;
         }

         if (data[i + 6]) {
            bvalue += 64;
         }

         if (data[i + 7]) {
            bvalue += 128;
         }

         this.writeByte(bvalue);
      }

      if (i < data.length) {
         int bvalue = 0;
         int limitForOffset = data.length % 7;

         for (int offset = 0; offset < limitForOffset; offset++) {
            if (data[i + offset]) {
               bvalue += 1 << i;
            }
         }

         this.writeByte(bvalue);
      }
   }

   @Override
   public void writeByteArray(byte[] data) {
      if (Integer.compareUnsigned(data.length, -1) >= 0) {
         throw new AssertionError("Array length cannot exceed uint limit");
      }

      this.writeBytes(data);
   }

   @Override
   public void writeSByteArray(byte[] data) {
      if (Integer.compareUnsigned(data.length, -1) >= 0) {
         throw new AssertionError("Array length cannot exceed uint limit");
      }

      this.writeBytes(data);
   }

   @Override
   public void writeShortArray(short[] data) {
      if (Integer.compareUnsigned(data.length, -1) >= 0) {
         throw new AssertionError("Array length cannot exceed uint limit");
      }

      this.writeVarUInt(data.length);
      this.writeShortArrayRaw(data);
   }

   @Override
   public void writeShortArrayRaw(short[] data) {
      for (short element : data) {
         this.writeShort(element);
      }
   }

   @Override
   public void writeUShortArray(short[] data) {
      if (Integer.compareUnsigned(data.length, -1) >= 0) {
         throw new AssertionError("Array length cannot exceed uint limit");
      }

      this.writeShortArray(data);
   }

   @Override
   public void writeUShortArrayRaw(short[] data) {
      this.writeShortArrayRaw(data);
   }

   @Override
   public void writeVarIntArray(int[] data) {
      if (Integer.compareUnsigned(data.length, -1) >= 0) {
         throw new AssertionError("Array length cannot exceed uint limit");
      }

      this.writeVarUInt(data.length);
      this.writeVarIntArrayRaw(data);
   }

   @Override
   public void writeVarIntArrayRaw(int[] data) {
      for (int element : data) {
         this.writeVarInt(element);
      }
   }

   @Override
   public void writeVarUIntArray(int[] data) {
      if (Integer.compareUnsigned(data.length, -1) >= 0) {
         throw new AssertionError("Array length cannot exceed uint limit");
      }

      this.writeVarUInt(data.length);
      this.writeVarUIntArrayRaw(data);
   }

   @Override
   public void writeVarUIntArrayRaw(int[] data) {
      for (int element : data) {
         this.writeVarUInt(element);
      }
   }

   @Override
   public void writeIntArray(int[] data) {
      if (Integer.compareUnsigned(data.length, -1) >= 0) {
         throw new AssertionError("Array length cannot exceed uint limit");
      }

      this.writeVarUInt(data.length);
      this.writeIntArrayRaw(data);
   }

   @Override
   public void writeIntArrayRaw(int[] data) {
      for (int element : data) {
         this.writeInt(element);
      }
   }

   @Override
   public void writeUIntArray(int[] data) {
      if (Integer.compareUnsigned(data.length, -1) >= 0) {
         throw new AssertionError("Array length cannot exceed uint limit");
      }

      this.writeIntArray(data);
   }

   @Override
   public void writeUIntArrayRaw(int[] data) {
      this.writeIntArrayRaw(data);
   }

   @Override
   public void writeVarLongArray(long[] data) {
      if (Integer.compareUnsigned(data.length, -1) >= 0) {
         throw new AssertionError("Array length cannot exceed uint limit");
      }

      this.writeVarUInt(data.length);
      this.writeVarLongArrayRaw(data);
   }

   @Override
   public void writeVarLongArrayRaw(long[] data) {
      for (long element : data) {
         this.writeVarLong(element);
      }
   }

   @Override
   public void writeVarULongArray(long[] data) {
      if (Integer.compareUnsigned(data.length, -1) >= 0) {
         throw new AssertionError("Array length cannot exceed uint limit");
      }

      this.writeVarUInt(data.length);
      this.writeVarULongArrayRaw(data);
   }

   @Override
   public void writeVarULongArrayRaw(long[] data) {
      for (long element : data) {
         this.writeVarULong(element);
      }
   }

   @Override
   public void writeLongArray(long[] data) {
      if (Integer.compareUnsigned(data.length, -1) >= 0) {
         throw new AssertionError("Array length cannot exceed uint limit");
      }

      this.writeVarUInt(data.length);
      this.writeLongArrayRaw(data);
   }

   @Override
   public void writeLongArrayRaw(long[] data) {
      for (long element : data) {
         this.writeLong(element);
      }
   }

   @Override
   public void writeULongArray(long[] data) {
      if (Integer.compareUnsigned(data.length, -1) >= 0) {
         throw new AssertionError("Array length cannot exceed uint limit");
      }

      this.writeLongArray(data);
   }

   @Override
   public void writeULongArrayRaw(long[] data) {
      this.writeLongArrayRaw(data);
   }

   @Override
   public void writeFloatArray(float[] data) {
      if (Integer.compareUnsigned(data.length, -1) >= 0) {
         throw new AssertionError("Array length cannot exceed uint limit");
      }

      this.writeVarUInt(data.length);
      this.writeFloatArrayRaw(data);
   }

   @Override
   public void writeFloatArrayRaw(float[] data) {
      for (float element : data) {
         this.writeFloat(element);
      }
   }

   @Override
   public void writeDoubleArray(double[] data) {
      if (Integer.compareUnsigned(data.length, -1) >= 0) {
         throw new AssertionError("Array length cannot exceed uint limit");
      }

      this.writeVarUInt(data.length);
      this.writeDoubleArrayRaw(data);
   }

   @Override
   public void writeDoubleArrayRaw(double[] data) {
      for (double element : data) {
         this.writeDouble(element);
      }
   }

   @Override
   public void writeStringArray(String[] data) {
      if (Integer.compareUnsigned(data.length, -1) >= 0) {
         throw new AssertionError("Array length cannot exceed uint limit");
      }

      this.writeVarUInt(data.length);
      this.writeStringArrayRaw(data);
   }

   @Override
   public void writeStringArrayRaw(String[] data) {
      for (String element : data) {
         this.writeString(element);
      }
   }

   @Override
   public void flush() {
   }
}
