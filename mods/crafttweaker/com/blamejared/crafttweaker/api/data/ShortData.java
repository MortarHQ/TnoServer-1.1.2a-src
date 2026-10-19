package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Objects;
import net.minecraft.nbt.ShortTag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.data.ShortData")
@ZenRegister
@Document("vanilla/api/data/ShortData")
public class ShortData implements IData {
   private final ShortTag internal;

   public ShortData(ShortTag internal) {
      this.internal = internal;
   }

   @ZenCodeType.Constructor
   public ShortData(short internal) {
      this.internal = ShortTag.valueOf(internal);
   }

   @Override
   public IData add(IData other) {
      return this.of(this.asShort() + other.asShort());
   }

   @Override
   public IData sub(IData other) {
      return this.of(this.asShort() - other.asShort());
   }

   @Override
   public IData mul(IData other) {
      return this.of(this.asShort() * other.asShort());
   }

   @Override
   public IData div(IData other) {
      return this.of(this.asShort() / other.asShort());
   }

   @Override
   public IData mod(IData other) {
      return this.of(this.asShort() % other.asShort());
   }

   @Override
   public IData or(IData other) {
      return this.of(this.asShort() | other.asShort());
   }

   @Override
   public IData and(IData other) {
      return this.of(this.asShort() & other.asShort());
   }

   @Override
   public IData xor(IData other) {
      return this.of(this.asShort() ^ other.asShort());
   }

   @Override
   public IData neg() {
      return this.of(-this.asShort());
   }

   @Override
   public boolean contains(IData other) {
      return this.asShort() == other.asShort();
   }

   @Override
   public int compareTo(@NotNull IData other) {
      return Short.compare(this.asShort(), other.asShort());
   }

   @Override
   public boolean equalTo(IData other) {
      return this.asShort() == other.asShort();
   }

   @Override
   public IData shl(IData other) {
      return this.of(this.asShort() << other.asShort());
   }

   @Override
   public IData shr(IData other) {
      return this.of(this.asShort() >> other.asShort());
   }

   @Override
   public boolean asBool() {
      return this.asShort() == 1;
   }

   @Override
   public byte asByte() {
      return (byte)this.asShort();
   }

   @Override
   public short asShort() {
      return this.getInternal().getAsShort();
   }

   @Override
   public int asInt() {
      return this.asShort();
   }

   @Override
   public long asLong() {
      return this.asShort();
   }

   @Override
   public float asFloat() {
      return this.asShort();
   }

   @Override
   public double asDouble() {
      return this.asShort();
   }

   public ShortTag getInternal() {
      return this.internal;
   }

   @Override
   public IData copy() {
      return new ShortData(this.getInternal().copy());
   }

   @Override
   public IData copyInternal() {
      return this.copy();
   }

   @Override
   public <T> T accept(DataVisitor<T> visitor) {
      return visitor.visitShort(this);
   }

   @Override
   public IData.Type getType() {
      return IData.Type.SHORT;
   }

   private ShortData of(int value) {
      return new ShortData(ShortTag.valueOf((short)value));
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ShortData iData = (ShortData)o;
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
