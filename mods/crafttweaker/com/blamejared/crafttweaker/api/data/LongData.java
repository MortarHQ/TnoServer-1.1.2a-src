package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Objects;
import net.minecraft.nbt.LongTag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.data.LongData")
@ZenRegister
@Document("vanilla/api/data/LongData")
public class LongData implements IData {
   private final LongTag internal;

   public LongData(LongTag internal) {
      this.internal = internal;
   }

   @ZenCodeType.Constructor
   public LongData(long internal) {
      this.internal = LongTag.valueOf(internal);
   }

   @Override
   public IData add(IData other) {
      return this.of(this.asLong() + other.asLong());
   }

   @Override
   public IData sub(IData other) {
      return this.of(this.asLong() - other.asLong());
   }

   @Override
   public IData mul(IData other) {
      return this.of(this.asLong() * other.asLong());
   }

   @Override
   public IData div(IData other) {
      return this.of(this.asLong() / other.asLong());
   }

   @Override
   public IData mod(IData other) {
      return this.of(this.asLong() % other.asLong());
   }

   @Override
   public IData or(IData other) {
      return this.of(this.asLong() | other.asLong());
   }

   @Override
   public IData and(IData other) {
      return this.of(this.asLong() & other.asLong());
   }

   @Override
   public IData xor(IData other) {
      return this.of(this.asLong() ^ other.asLong());
   }

   @Override
   public IData neg() {
      return this.of(-this.asLong());
   }

   @Override
   public boolean contains(IData other) {
      return this.asLong() == other.asLong();
   }

   @Override
   public int compareTo(@NotNull IData other) {
      return Long.compare(this.asLong(), other.asLong());
   }

   @Override
   public boolean equalTo(IData other) {
      return this.asLong() == other.asLong();
   }

   @Override
   public IData shl(IData other) {
      return this.of(this.asLong() << (int)other.asLong());
   }

   @Override
   public IData shr(IData other) {
      return this.of(this.asLong() >> (int)other.asLong());
   }

   @Override
   public boolean asBool() {
      return this.asLong() == 1L;
   }

   @Override
   public byte asByte() {
      return (byte)this.asLong();
   }

   @Override
   public short asShort() {
      return (short)this.asLong();
   }

   @Override
   public int asInt() {
      return (int)this.asLong();
   }

   @Override
   public long asLong() {
      return this.getInternal().getAsLong();
   }

   @Override
   public float asFloat() {
      return (float)this.asLong();
   }

   @Override
   public double asDouble() {
      return this.asLong();
   }

   public LongTag getInternal() {
      return this.internal;
   }

   @Override
   public IData copy() {
      return new LongData(this.getInternal().copy());
   }

   @Override
   public IData copyInternal() {
      return this.copy();
   }

   @Override
   public <T> T accept(DataVisitor<T> visitor) {
      return visitor.visitLong(this);
   }

   @Override
   public IData.Type getType() {
      return IData.Type.LONG;
   }

   private LongData of(long value) {
      return new LongData(LongTag.valueOf(value));
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         LongData iData = (LongData)o;
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
