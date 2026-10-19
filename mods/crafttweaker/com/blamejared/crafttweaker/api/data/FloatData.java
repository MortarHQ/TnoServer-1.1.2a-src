package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Objects;
import net.minecraft.nbt.FloatTag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.data.FloatData")
@ZenRegister
@Document("vanilla/api/data/FloatData")
public class FloatData implements IData {
   private final FloatTag internal;

   public FloatData(FloatTag internal) {
      this.internal = internal;
   }

   @ZenCodeType.Constructor
   public FloatData(float internal) {
      this.internal = FloatTag.valueOf(internal);
   }

   public FloatTag getInternal() {
      return this.internal;
   }

   @Override
   public IData copy() {
      return new FloatData(this.getInternal());
   }

   @Override
   public IData copyInternal() {
      return this.copy();
   }

   @Override
   public <T> T accept(DataVisitor<T> visitor) {
      return visitor.visitFloat(this);
   }

   @Override
   public IData.Type getType() {
      return IData.Type.FLOAT;
   }

   @Override
   public IData add(IData other) {
      return this.of(this.asFloat() + other.asFloat());
   }

   @Override
   public IData sub(IData other) {
      return this.of(this.asFloat() - other.asFloat());
   }

   @Override
   public IData mul(IData other) {
      return this.of(this.asFloat() * other.asFloat());
   }

   @Override
   public IData div(IData other) {
      return this.of(this.asFloat() / other.asFloat());
   }

   @Override
   public IData mod(IData other) {
      return this.of(this.asFloat() % other.asFloat());
   }

   @Override
   public IData neg() {
      return this.of(-this.asFloat());
   }

   @Override
   public boolean contains(IData other) {
      return this.asFloat() == other.asFloat();
   }

   @Override
   public int compareTo(@NotNull IData other) {
      return Float.compare(this.asFloat(), other.asFloat());
   }

   @Override
   public boolean equalTo(IData other) {
      return this.asFloat() == other.asFloat();
   }

   @Override
   public boolean asBool() {
      return this.asFloat() == 1.0F;
   }

   @Override
   public byte asByte() {
      return (byte)this.asFloat();
   }

   @Override
   public short asShort() {
      return (short)this.asFloat();
   }

   @Override
   public int asInt() {
      return (int)this.asFloat();
   }

   @Override
   public long asLong() {
      return (long)this.asFloat();
   }

   @Override
   public float asFloat() {
      return this.getInternal().getAsFloat();
   }

   @Override
   public double asDouble() {
      return this.asFloat();
   }

   private FloatData of(float value) {
      return new FloatData(FloatTag.valueOf(value));
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         FloatData iData = (FloatData)o;
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
