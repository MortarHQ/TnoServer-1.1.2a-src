package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.nbt.StringTag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.data.StringData")
@ZenRegister
@Document("vanilla/api/data/StringData")
public class StringData implements IData {
   private final StringTag internal;

   public StringData(StringTag internal) {
      this.internal = internal;
   }

   @ZenCodeType.Constructor
   public StringData(String internal) {
      this.internal = StringTag.valueOf(internal);
   }

   @Override
   public IData add(IData other) {
      return this.of(this.getAsString() + other.getAsString());
   }

   @Override
   public IData cat(IData other) {
      return this.of(this.getAsString() + other.getAsString());
   }

   @Override
   public boolean contains(IData other) {
      return this.getAsString().contains(other.getAsString());
   }

   @Override
   public int compareTo(@NotNull IData other) {
      return this.getAsString().compareTo(other.getAsString());
   }

   @Override
   public boolean equalTo(IData other) {
      return this.getAsString().equals(other.getAsString());
   }

   @Override
   public boolean asBool() {
      return Boolean.parseBoolean(this.getAsString());
   }

   @Override
   public byte asByte() {
      return this.safely(Byte::parseByte, (s, e) -> new UnsupportedOperationException("StringData '%s' cannot be cast to a byte!".formatted(s), e));
   }

   @Override
   public short asShort() {
      return this.safely(Short::parseShort, (s, e) -> new UnsupportedOperationException("StringData '%s' cannot be cast to a short!".formatted(s), e));
   }

   @Override
   public int asInt() {
      return this.safely(Integer::parseInt, (s, e) -> new UnsupportedOperationException("StringData '%s' cannot be cast to a int!".formatted(s), e));
   }

   @Override
   public long asLong() {
      return this.safely(Long::parseLong, (s, e) -> new UnsupportedOperationException("StringData '%s' cannot be cast to a long!".formatted(s), e));
   }

   @Override
   public float asFloat() {
      return this.safely(Float::parseFloat, (s, e) -> new UnsupportedOperationException("StringData '%s' cannot be cast to a float!".formatted(s), e));
   }

   @Override
   public double asDouble() {
      return this.safely(Double::parseDouble, (s, e) -> new UnsupportedOperationException("StringData '%s' cannot be cast to a double!".formatted(s), e));
   }

   @Override
   public int length() {
      return this.getAsString().length();
   }

   public StringTag getInternal() {
      return this.internal;
   }

   @Override
   public IData copy() {
      return new StringData(this.getInternal());
   }

   @Override
   public IData copyInternal() {
      return this.copy();
   }

   @Override
   public <T> T accept(DataVisitor<T> visitor) {
      return visitor.visitString(this);
   }

   @Override
   public IData.Type getType() {
      return IData.Type.STRING;
   }

   private StringData of(String value) {
      return new StringData(StringTag.valueOf(value));
   }

   private <T> T safely(Function<String, T> func, BiFunction<String, Exception, UnsupportedOperationException> error) {
      String internalValue = this.getAsString();

      try {
         return func.apply(internalValue);
      } catch (Exception e) {
         throw (UnsupportedOperationException)error.apply(internalValue, e);
      }
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         StringData iData = (StringData)o;
         return Objects.equals(this.getInternal(), iData.getInternal());
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getInternal());
   }

   @Override
   public String toString() {
      return this.getAsString();
   }
}
