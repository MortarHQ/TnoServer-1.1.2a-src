package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Objects;
import net.minecraft.nbt.DoubleTag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.data.DoubleData")
@ZenRegister
@Document("vanilla/api/data/DoubleData")
public class DoubleData implements IData {
   private final DoubleTag internal;

   public DoubleData(DoubleTag internal) {
      this.internal = internal;
   }

   @ZenCodeType.Constructor
   public DoubleData(double internal) {
      this.internal = DoubleTag.valueOf(internal);
   }

   @Override
   public IData add(IData other) {
      return this.of(this.asDouble() + other.asDouble());
   }

   @Override
   public IData sub(IData other) {
      return this.of(this.asDouble() - other.asDouble());
   }

   @Override
   public IData mul(IData other) {
      return this.of(this.asDouble() * other.asDouble());
   }

   @Override
   public IData div(IData other) {
      return this.of(this.asDouble() / other.asDouble());
   }

   @Override
   public IData mod(IData other) {
      return this.of(this.asDouble() % other.asDouble());
   }

   @Override
   public IData neg() {
      return this.of(-this.asDouble());
   }

   @Override
   public boolean contains(IData other) {
      return this.asDouble() == other.asDouble();
   }

   @Override
   public int compareTo(@NotNull IData other) {
      return Double.compare(this.asDouble(), other.asDouble());
   }

   @Override
   public boolean equalTo(IData other) {
      return this.asDouble() == other.asDouble();
   }

   @Override
   public boolean asBool() {
      return this.asDouble() == 1.0;
   }

   @Override
   public byte asByte() {
      return (byte)this.asDouble();
   }

   @Override
   public short asShort() {
      return (short)this.asDouble();
   }

   @Override
   public int asInt() {
      return (int)this.asDouble();
   }

   @Override
   public long asLong() {
      return (long)this.asDouble();
   }

   @Override
   public float asFloat() {
      return (float)this.asDouble();
   }

   @Override
   public double asDouble() {
      return this.getInternal().getAsDouble();
   }

   private DoubleData of(double value) {
      return new DoubleData(DoubleTag.valueOf(value));
   }

   public DoubleTag getInternal() {
      return this.internal;
   }

   @Override
   public IData copy() {
      return new DoubleData(this.getInternal());
   }

   @Override
   public IData copyInternal() {
      return this.copy();
   }

   @Override
   public <T> T accept(DataVisitor<T> visitor) {
      return visitor.visitDouble(this);
   }

   @Override
   public IData.Type getType() {
      return IData.Type.DOUBLE;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         DoubleData iData = (DoubleData)o;
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
