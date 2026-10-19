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
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.data.ByteArrayData")
@ZenRegister
@Document("vanilla/api/data/ByteArrayData")
public class ByteArrayData implements IData {
   private final ByteArrayTag internal;

   public ByteArrayData(ByteArrayTag internal) {
      this.internal = internal;
   }

   @ZenCodeType.Constructor
   public ByteArrayData(byte[] internal) {
      this.internal = new ByteArrayTag(internal);
   }

   @Override
   public void put(String index, IData value) {
      try {
         this.getInternal().setTag(Integer.parseInt(index), ByteTag.valueOf(value.asByte()));
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
         return this.getInternal().contains(ByteTag.valueOf(other.asByte()));
      }

      List<IData> dataValues = other.asList();
      return dataValues != null && this.containsList(dataValues);
   }

   @Override
   public int compareTo(@NotNull IData other) {
      return Arrays.compare(this.asByteArray(), other.asByteArray());
   }

   @Override
   public boolean equalTo(IData other) {
      return Arrays.equals(this.asByteArray(), other.asByteArray());
   }

   @Override
   public List<IData> asList() {
      return Lists.transform(this.getInternal(), ByteData::new);
   }

   @Override
   public boolean isListable() {
      return true;
   }

   @Override
   public byte[] asByteArray() {
      return this.getInternal().getAsByteArray();
   }

   @Override
   public int[] asIntArray() {
      byte[] bytes = this.asByteArray();
      int[] ints = new int[bytes.length];

      for (int i = 0; i < bytes.length; i++) {
         ints[i] = bytes[i];
      }

      return ints;
   }

   @Override
   public long[] asLongArray() {
      byte[] bytes = this.asByteArray();
      long[] longs = new long[bytes.length];

      for (int i = 0; i < bytes.length; i++) {
         longs[i] = bytes[i];
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

   public ByteArrayTag getInternal() {
      return this.internal;
   }

   public ByteArrayData copy() {
      return new ByteArrayData(this.getInternal());
   }

   public ByteArrayData copyInternal() {
      return new ByteArrayData((ByteArrayTag)this.getInternal().copy());
   }

   @Override
   public <T> T accept(DataVisitor<T> visitor) {
      return visitor.visitByteArray(this);
   }

   @Override
   public IData.Type getType() {
      return IData.Type.BYTE_ARRAY;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ByteArrayData iData = (ByteArrayData)o;
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
