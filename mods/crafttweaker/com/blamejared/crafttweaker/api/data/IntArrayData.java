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
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.data.IntArrayData")
@ZenRegister
@Document("vanilla/api/data/IntArrayData")
public class IntArrayData implements IData {
   private final IntArrayTag internal;

   public IntArrayData(IntArrayTag internal) {
      this.internal = internal;
   }

   @ZenCodeType.Constructor
   public IntArrayData(int[] internal) {
      this.internal = new IntArrayTag(internal);
   }

   @Override
   public void put(String index, IData value) {
      try {
         this.getInternal().setTag(Integer.parseInt(index), IntTag.valueOf(value.asInt()));
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
         return this.getInternal().contains(IntTag.valueOf(other.asInt()));
      }

      List<IData> dataValues = other.asList();
      return dataValues != null && this.containsList(dataValues);
   }

   @Override
   public int compareTo(@NotNull IData other) {
      return Arrays.compare(this.asIntArray(), other.asIntArray());
   }

   @Override
   public boolean equalTo(IData other) {
      return Arrays.equals(this.asIntArray(), other.asIntArray());
   }

   @Override
   public List<IData> asList() {
      return Lists.transform(this.getInternal(), IntData::new);
   }

   @Override
   public boolean isListable() {
      return true;
   }

   @Override
   public byte[] asByteArray() {
      int[] ints = this.asIntArray();
      byte[] bytes = new byte[ints.length];

      for (int i = 0; i < ints.length; i++) {
         bytes[i] = (byte)ints[i];
      }

      return bytes;
   }

   @Override
   public int[] asIntArray() {
      return this.getInternal().getAsIntArray();
   }

   @Override
   public long[] asLongArray() {
      int[] ints = this.asIntArray();
      long[] longs = new long[ints.length];

      for (int i = 0; i < ints.length; i++) {
         longs[i] = ints[i];
      }

      return longs;
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

   public IntArrayTag getInternal() {
      return this.internal;
   }

   public IntArrayData copy() {
      return new IntArrayData(this.getInternal());
   }

   public IntArrayData copyInternal() {
      return new IntArrayData(this.getInternal().copy());
   }

   @Override
   public <T> T accept(DataVisitor<T> visitor) {
      return visitor.visitIntArray(this);
   }

   @Override
   public IData.Type getType() {
      return IData.Type.INT_ARRAY;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         IntArrayData iData = (IntArrayData)o;
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
