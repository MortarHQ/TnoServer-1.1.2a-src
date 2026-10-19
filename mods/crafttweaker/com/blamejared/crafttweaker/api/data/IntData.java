package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Objects;
import net.minecraft.nbt.IntTag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.data.IntData")
@ZenRegister
@Document("vanilla/api/data/IntData")
public class IntData implements IData {
   private final IntTag internal;

   public IntData(IntTag internal) {
      this.internal = internal;
   }

   @ZenCodeType.Constructor
   public IntData(int internal) {
      this.internal = IntTag.valueOf(internal);
   }

   @Override
   public IData add(IData other) {
      return this.of(this.asInt() + other.asInt());
   }

   @Override
   public IData sub(IData other) {
      return this.of(this.asInt() - other.asInt());
   }

   @Override
   public IData mul(IData other) {
      return this.of(this.asInt() * other.asInt());
   }

   @Override
   public IData div(IData other) {
      return this.of(this.asInt() / other.asInt());
   }

   @Override
   public IData mod(IData other) {
      return this.of(this.asInt() % other.asInt());
   }

   @Override
   public IData or(IData other) {
      return this.of(this.asInt() | other.asInt());
   }

   @Override
   public IData and(IData other) {
      return this.of(this.asInt() & other.asInt());
   }

   @Override
   public IData xor(IData other) {
      return this.of(this.asInt() ^ other.asInt());
   }

   @Override
   public IData neg() {
      return this.of(-this.asInt());
   }

   @Override
   public boolean contains(IData other) {
      return this.asInt() == other.asInt();
   }

   @Override
   public int compareTo(@NotNull IData other) {
      return Integer.compare(this.asInt(), other.asInt());
   }

   @Override
   public boolean equalTo(IData other) {
      return this.asInt() == other.asInt();
   }

   @Override
   public IData shl(IData other) {
      return this.of(this.asInt() << other.asInt());
   }

   @Override
   public IData shr(IData other) {
      return this.of(this.asInt() >> other.asInt());
   }

   @Override
   public boolean asBool() {
      return this.asInt() == 1;
   }

   @Override
   public byte asByte() {
      return (byte)this.asInt();
   }

   @Override
   public short asShort() {
      return (short)this.asInt();
   }

   @Override
   public int asInt() {
      return this.getInternal().getAsInt();
   }

   @Override
   public long asLong() {
      return this.asInt();
   }

   @Override
   public float asFloat() {
      return this.asInt();
   }

   @Override
   public double asDouble() {
      return this.asInt();
   }

   public IntTag getInternal() {
      return this.internal;
   }

   @Override
   public IData copy() {
      return new IntData(this.getInternal());
   }

   @Override
   public IData copyInternal() {
      return this.copy();
   }

   @Override
   public <T> T accept(DataVisitor<T> visitor) {
      return visitor.visitInt(this);
   }

   @Override
   public IData.Type getType() {
      return IData.Type.INT;
   }

   private IntData of(int value) {
      return new IntData(IntTag.valueOf(value));
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         IntData iData = (IntData)o;
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
