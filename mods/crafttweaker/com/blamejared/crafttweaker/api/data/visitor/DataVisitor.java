package com.blamejared.crafttweaker.api.data.visitor;

import com.blamejared.crafttweaker.api.data.BoolData;
import com.blamejared.crafttweaker.api.data.ByteArrayData;
import com.blamejared.crafttweaker.api.data.ByteData;
import com.blamejared.crafttweaker.api.data.DoubleData;
import com.blamejared.crafttweaker.api.data.EmptyData;
import com.blamejared.crafttweaker.api.data.FloatData;
import com.blamejared.crafttweaker.api.data.IntArrayData;
import com.blamejared.crafttweaker.api.data.IntData;
import com.blamejared.crafttweaker.api.data.ListData;
import com.blamejared.crafttweaker.api.data.LongArrayData;
import com.blamejared.crafttweaker.api.data.LongData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.ShortData;
import com.blamejared.crafttweaker.api.data.StringData;

public interface DataVisitor<T> {
   T visitBool(BoolData var1);

   T visitByteArray(ByteArrayData var1);

   T visitByte(ByteData var1);

   T visitDouble(DoubleData var1);

   T visitFloat(FloatData var1);

   T visitIntArray(IntArrayData var1);

   T visitInt(IntData var1);

   T visitList(ListData var1);

   T visitLongArray(LongArrayData var1);

   T visitLong(LongData var1);

   T visitMap(MapData var1);

   T visitShort(ShortData var1);

   T visitString(StringData var1);

   T visitEmpty(EmptyData var1);
}
