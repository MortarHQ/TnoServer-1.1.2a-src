package compactio;

public interface CompactDataOutput extends AutoCloseable {
   void writeBool(boolean var1);

   void writeByte(int var1);

   void writeSByte(byte var1);

   void writeShort(short var1);

   void writeUShort(int var1);

   void writeInt(int var1);

   void writeUInt(int var1);

   void writeLong(long var1);

   void writeULong(long var1);

   void writeVarInt(int var1);

   void writeVarUInt(int var1);

   void writeVarLong(long var1);

   void writeVarULong(long var1);

   void writeFloat(float var1);

   void writeDouble(double var1);

   void writeChar(char var1);

   void writeString(String var1);

   void writeBytes(byte[] var1);

   void writeBytes(byte[] var1, int var2, int var3);

   void writeRawBytes(byte[] var1);

   void writeRawBytes(byte[] var1, int var2, int var3);

   void writeBoolArray(boolean[] var1);

   void writeByteArray(byte[] var1);

   void writeSByteArray(byte[] var1);

   void writeShortArray(short[] var1);

   void writeShortArrayRaw(short[] var1);

   void writeUShortArray(short[] var1);

   void writeUShortArrayRaw(short[] var1);

   void writeVarIntArray(int[] var1);

   void writeVarIntArrayRaw(int[] var1);

   void writeVarUIntArray(int[] var1);

   void writeVarUIntArrayRaw(int[] var1);

   void writeIntArray(int[] var1);

   void writeIntArrayRaw(int[] var1);

   void writeUIntArray(int[] var1);

   void writeUIntArrayRaw(int[] var1);

   void writeVarLongArray(long[] var1);

   void writeVarLongArrayRaw(long[] var1);

   void writeVarULongArray(long[] var1);

   void writeVarULongArrayRaw(long[] var1);

   void writeLongArray(long[] var1);

   void writeLongArrayRaw(long[] var1);

   void writeULongArray(long[] var1);

   void writeULongArrayRaw(long[] var1);

   void writeFloatArray(float[] var1);

   void writeFloatArrayRaw(float[] var1);

   void writeDoubleArray(double[] var1);

   void writeDoubleArrayRaw(double[] var1);

   void writeStringArray(String[] var1);

   void writeStringArrayRaw(String[] var1);

   void flush();

   @Override
   void close();
}
