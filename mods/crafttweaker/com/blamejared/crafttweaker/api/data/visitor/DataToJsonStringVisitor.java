package com.blamejared.crafttweaker.api.data.visitor;

import com.blamejared.crafttweaker.api.data.BoolData;
import com.blamejared.crafttweaker.api.data.ByteArrayData;
import com.blamejared.crafttweaker.api.data.ByteData;
import com.blamejared.crafttweaker.api.data.DoubleData;
import com.blamejared.crafttweaker.api.data.EmptyData;
import com.blamejared.crafttweaker.api.data.FloatData;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.IntArrayData;
import com.blamejared.crafttweaker.api.data.IntData;
import com.blamejared.crafttweaker.api.data.ListData;
import com.blamejared.crafttweaker.api.data.LongArrayData;
import com.blamejared.crafttweaker.api.data.LongData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.ShortData;
import com.blamejared.crafttweaker.api.data.StringData;
import com.blamejared.crafttweaker.api.util.StringUtil;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public enum DataToJsonStringVisitor implements DataVisitor<String> {
   INSTANCE;

   public String visit(IData data) {
      return data.accept(this);
   }

   private String visitCollection(IData data) {
      return data.asList().stream().map(iData -> iData.accept(INSTANCE)).collect(Collectors.joining(",", "[", "]"));
   }

   public String visitBool(BoolData data) {
      return Boolean.toString(data.asBool());
   }

   public String visitByteArray(ByteArrayData data) {
      return this.visitCollection(data);
   }

   public String visitByte(ByteData data) {
      return String.valueOf(data.asByte());
   }

   public String visitDouble(DoubleData data) {
      return String.valueOf(data.asDouble());
   }

   public String visitFloat(FloatData data) {
      return String.valueOf(data.asFloat());
   }

   public String visitIntArray(IntArrayData data) {
      return this.visitCollection(data);
   }

   public String visitInt(IntData data) {
      return String.valueOf(data.asInt());
   }

   public String visitList(ListData data) {
      return this.visitCollection(data);
   }

   public String visitLongArray(LongArrayData data) {
      return this.visitCollection(data);
   }

   public String visitLong(LongData data) {
      return String.valueOf(data.asLong());
   }

   public String visitMap(MapData data) {
      StringJoiner joiner = new StringJoiner(",", "{", "}");
      data.asMap().forEach((key, value) -> joiner.add("\"%s\" : %s".formatted(key, value.accept(INSTANCE))));
      return joiner.toString();
   }

   public String visitShort(ShortData data) {
      return String.valueOf(data.asShort());
   }

   public String visitString(StringData data) {
      return StringUtil.quoteAndEscape(data.getInternal().getAsString());
   }

   public String visitEmpty(EmptyData data) {
      return "";
   }
}
