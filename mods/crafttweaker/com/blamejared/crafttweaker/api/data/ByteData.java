package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Objects;
import net.minecraft.nbt.ByteTag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.data.ByteData")
@ZenRegister
@Document("vanilla/api/data/ByteData")
public class ByteData implements IData {
   private final ByteTag internal;

   public ByteData(ByteTag internal) {
      this.internal = internal;
   }

   @ZenCodeType.Constructor
   public ByteData(byte internal) {
      this.internal = ByteTag.valueOf(internal);
   }

   @Override
   public IData add(IData other) {
      return this.of(this.asByte() + other.asByte());
   }

   @Override
   public IData sub(IData other) {
      return this.of(this.asByte() - other.asByte());
   }

   @Override
   public IData mul(IData other) {
      return this.of(this.asByte() * other.asByte());
   }

   @Override
   public IData div(IData other) {
      return this.of(this.asByte() / other.asByte());
   }

   @Override
   public IData mod(IData other) {
      return this.of(this.asByte() % other.asByte());
   }

   @Override
   public IData or(IData other) {
      return this.of(this.asByte() | other.asByte());
   }

   @Override
   public IData and(IData other) {
      return this.of(this.asByte() & other.asByte());
   }

   @Override
   public IData xor(IData other) {
      return this.of(this.asByte() ^ other.asByte());
   }

   @Override
   public IData neg() {
      return this.of(-this.asByte());
   }

   @Override
   public boolean contains(IData other) {
      return this.asByte() == other.asByte();
   }

   @Override
   public int compareTo(@NotNull IData other) {
      return Byte.compare(this.asByte(), other.asByte());
   }

   @Override
   public boolean equalTo(IData other) {
      return this.asByte() == other.asByte();
   }

   @Override
   public boolean asBool() {
      return this.asByte() == 1;
   }

   @Override
   public byte asByte() {
      return this.getInternal().getAsByte();
   }

   @Override
   public short asShort() {
      return this.asByte();
   }

   @Override
   public int asInt() {
      return this.asByte();
   }

   @Override
   public long asLong() {
      return this.asByte();
   }

   @Override
   public float asFloat() {
      return this.asByte();
   }

   @Override
   public double asDouble() {
      return this.asByte();
   }

   public ByteTag getInternal() {
      return this.internal;
   }

   @Override
   public IData copy() {
      return new ByteData(this.getInternal());
   }

   @Override
   public IData copyInternal() {
      return this.copy();
   }

   @Override
   public <T> T accept(DataVisitor<T> visitor) {
      return visitor.visitByte(this);
   }

   @Override
   public IData.Type getType() {
      return IData.Type.BYTE;
   }

   private ByteData of(int value) {
      return new ByteData(ByteTag.valueOf((byte)value));
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ByteData iData = (ByteData)o;
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
