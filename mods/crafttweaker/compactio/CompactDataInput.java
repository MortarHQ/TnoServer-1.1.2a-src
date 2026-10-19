package compactio;

public interface CompactDataInput extends AutoCloseable {
   boolean readBool();

   int readByte();

   byte readSByte();

   short readShort();

   int readUShort();

   int readInt();

   int readUInt();

   long readLong();

   long readULong();

   int readVarInt();

   int readVarUInt();

   long readVarLong();

   long readVarULong();

   float readFloat();

   double readDouble();

   char readChar();

   String readString();

   byte[] readBytes();

   byte[] readRawBytes(int var1);

   boolean[] readBoolArray();

   byte[] readByteArray();

   byte[] readSByteArray();

   short[] readShortArray();

   short[] readShortArrayRaw(int var1);

   short[] readUShortArray();

   short[] readUShortArrayRaw(int var1);

   int[] readVarIntArray();

   int[] readVarIntArrayRaw(int var1);

   int[] readVarUIntArray();

   int[] readVarUIntArrayRaw(int var1);

   int[] readIntArray();

   int[] readIntArrayRaw(int var1);

   int[] readUIntArray();

   int[] readUIntArrayRaw(int var1);

   long[] readVarLongArray();

   long[] readVarLongArrayRaw(int var1);

   long[] readVarULongArray();

   long[] readVarULongArrayRaw(int var1);

   long[] readLongArray();

   long[] readLongArrayRaw(int var1);

   long[] readULongArray();

   long[] readULongArrayRaw(int var1);

   float[] readFloatArray();

   float[] readFloatArrayRaw(int var1);

   double[] readDoubleArray();

   double[] readDoubleArrayRaw(int var1);

   String[] readStringArray();

   String[] readStringArrayRaw(int var1);

   void skip(int var1);

   boolean hasMore();

   @Override
   void close();
}
