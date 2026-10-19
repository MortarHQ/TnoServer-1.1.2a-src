package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Objects;
import net.minecraft.nbt.ByteTag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.data.BoolData")
@ZenRegister
@Document("vanilla/api/data/BoolData")
public class BoolData implements IData {
   public static final BoolData TRUE = new BoolData(true);
   public static final BoolData FALSE = new BoolData(false);
   private final boolean internalValue;
   private final ByteData internalData;

   @ZenCodeType.Constructor
   public BoolData(boolean internalValue) {
      this.internalValue = internalValue;
      this.internalData = new ByteData(ByteTag.valueOf(this.asBool()));
   }

   @ZenCodeType.Caster
   @ZenCodeType.Method
   public ByteData getByteData() {
      return new ByteData(ByteTag.valueOf(this.asBool()));
   }

   public ByteTag getInternal() {
      return this.internalData.getInternal();
   }

   @Override
   public IData or(IData other) {
      return this.of(this.asBool() | other.asBool());
   }

   @Override
   public IData and(IData other) {
      return this.of(this.asBool() & other.asBool());
   }

   @Override
   public IData xor(IData other) {
      return this.of(this.asBool() ^ other.asBool());
   }

   @Override
   public IData not() {
      return this.of(!this.asBool());
   }

   @Override
   public boolean contains(IData other) {
      return other.asBool() == this.asBool();
   }

   @Override
   public int compareTo(@NotNull IData other) {
      return Boolean.compare(this.asBool(), other.asBool());
   }

   @Override
   public boolean equalTo(IData other) {
      return this.asBool() == other.asBool();
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
      return this.asBool() ? 1 : 0;
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

   @ZenCodeType.Caster(implicit = true)
   @Override
   public boolean asBool() {
      return this.internalValue;
   }

   @Override
   public IData copy() {
      return this.of(this.asBool());
   }

   @Override
   public IData copyInternal() {
      return this.copy();
   }

   @Override
   public <T> T accept(DataVisitor<T> visitor) {
      return visitor.visitBool(this);
   }

   @Override
   public IData.Type getType() {
      return IData.Type.BOOL;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         BoolData boolData = (BoolData)o;
         return this.internalValue == boolData.internalValue;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.internalValue);
   }

   private BoolData of(boolean value) {
      return value ? TRUE : FALSE;
   }

   @Override
   public String toString() {
      return this.getAsString();
   }
}
