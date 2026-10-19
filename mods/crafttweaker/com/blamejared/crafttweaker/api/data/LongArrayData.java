package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.data.LongArrayData")
@ZenRegister
@Document("vanilla/api/data/LongArrayData")
public class LongArrayData implements IData {
   private final LongArrayTag internal;

   public LongArrayData(LongArrayTag internal) {
      this.internal = internal;
   }

   @ZenCodeType.Constructor
   public LongArrayData(long[] internal) {
      this.internal = new LongArrayTag(internal);
   }

   public LongArrayTag getInternal() {
      return this.internal;
   }

   @Override
   public void put(String index, IData value) {
      try {
         this.getInternal().setTag(Integer.parseInt(index), LongTag.valueOf(value.asLong()));
      } catch (NumberFormatException e) {
         throw new IllegalArgumentException("Provided index: '%s' is not an Integer!".formatted(index));
      }
   }

   @Override
   public IData getAt(int index) {
      return TagToDataConverter.convert(this.getInternal().get(index));
   }

   @Override
   public void remove(int index) {
      this.getInternal().remove(index);
   }

   @Override
   public boolean contains(IData other) {
      if (!other.isListable()) {
         return this.getInternal().contains(LongTag.valueOf(other.asLong()));
      }

      List<IData> dataValues = other.asList();
      return dataValues != null && this.containsList(dataValues);
   }

   @Override
   public int compareTo(@NotNull IData other) {
      return Arrays.compare(this.asLongArray(), other.asLongArray());
   }

   @Override
   public boolean equalTo(IData other) {
      return Arrays.equals(this.asLongArray(), other.asLongArray());
   }

   @Override
   public List<IData> asList() {
      return Lists.transform(this.getInternal(), LongData::new);
   }

   @Override
   public boolean isListable() {
      return true;
   }

   @Override
   public byte[] asByteArray() {
      long[] longs = this.asLongArray();
      byte[] bytes = new byte[longs.length];

      for (int i = 0; i < longs.length; i++) {
         bytes[i] = (byte)longs[i];
      }

      return bytes;
   }

   @Override
   public int[] asIntArray() {
      long[] longs = this.asLongArray();
      int[] ints = new int[longs.length];

      for (int i = 0; i < longs.length; i++) {
         ints[i] = (int)longs[i];
      }

      return ints;
   }

   @Override
   public long[] asLongArray() {
      return this.getInternal().getAsLongArray();
   }

   @Override
   public int length() {
      return this.getInternal().size();
   }

   @NotNull
   @Override
   public Iterator<IData> iterator() {
      return this.asList().iterator();
   }

   @Override
   public IData copy() {
      return new LongArrayData(this.getInternal());
   }

   @Override
   public IData copyInternal() {
      return new LongArrayData(this.getInternal().copy());
   }

   @Override
   public <T> T accept(DataVisitor<T> visitor) {
      return visitor.visitLongArray(this);
   }

   @Override
   public IData.Type getType() {
      return IData.Type.LONG_ARRAY;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         LongArrayData iData = (LongArrayData)o;
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
