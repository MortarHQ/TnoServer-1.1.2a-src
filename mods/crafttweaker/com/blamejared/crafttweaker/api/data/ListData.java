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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.data.ListData")
@ZenRegister
@Document("vanilla/api/data/ListData")
public class ListData implements IData {
   private final ListTag internal;

   public ListData(ListTag internal) {
      this.internal = internal;
   }

   @ZenCodeType.Constructor
   public ListData() {
      this.internal = new ListTag();
   }

   @ZenCodeType.Constructor
   public ListData(List<IData> list) {
      this.internal = new ListTag();
      if (list != null) {
         list.forEach(iData -> this.getInternal().add(iData.getInternal()));
      }
   }

   @ZenCodeType.Constructor
   public ListData(IData[] array) {
      this(Arrays.asList(array));
   }

   public ListTag getInternal() {
      return this.internal;
   }

   @Override
   public IData add(IData other) {
      this.getInternal().add(other.getInternal());
      return this;
   }

   @Override
   public void put(String index, IData value) {
      try {
         this.getInternal().setTag(Integer.parseInt(index), value.getInternal());
      } catch (NumberFormatException e) {
         throw new IllegalArgumentException("Provided index: '%s' is not an Integer!".formatted(index));
      }
   }

   @Override
   public void remove(int index) {
      this.getInternal().remove(index);
   }

   @Override
   public IData getAt(int index) {
      return TagToDataConverter.convert(this.getInternal().get(index));
   }

   @Override
   public boolean contains(IData other) {
      if (other.isListable()) {
         List<IData> dataValues = other.asList();
         if (dataValues != null && this.containsList(dataValues)) {
            return true;
         }
      }

      for (Tag value : this.getInternal()) {
         if (TagToDataConverter.convert(value).contains(other)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean equalTo(IData other) {
      List<IData> values = this.asList();
      List<IData> others = other.asList();
      if (values.size() != others.size()) {
         return false;
      }

      for (int i = 0; i < values.size(); i++) {
         if (!values.get(i).equalTo(others.get(i))) {
            return false;
         }
      }

      return true;
   }

   @Override
   public List<IData> asList() {
      return Lists.transform(this.getInternal(), TagToDataConverter::convert);
   }

   @Override
   public boolean isListable() {
      return true;
   }

   @Override
   public byte[] asByteArray() {
      List<IData> values = this.asList();
      byte[] ret = new byte[values.size()];

      for (int i = 0; i < values.size(); i++) {
         ret[i] = values.get(i).asByte();
      }

      return ret;
   }

   @Override
   public int[] asIntArray() {
      List<IData> values = this.asList();
      int[] ret = new int[values.size()];

      for (int i = 0; i < values.size(); i++) {
         ret[i] = values.get(i).asInt();
      }

      return ret;
   }

   @Override
   public long[] asLongArray() {
      List<IData> values = this.asList();
      long[] ret = new long[values.size()];

      for (int i = 0; i < values.size(); i++) {
         ret[i] = values.get(i).asLong();
      }

      return ret;
   }

   @Override
   public int length() {
      return this.asList().size();
   }

   @Override
   public Set<String> getKeys() {
      return IntStream.range(0, this.length()).mapToObj(String::valueOf).collect(Collectors.toSet());
   }

   @NotNull
   @Override
   public Iterator<IData> iterator() {
      return this.asList().iterator();
   }

   @Override
   public IData copy() {
      return new ListData(this.getInternal());
   }

   @Override
   public IData copyInternal() {
      return new ListData(this.getInternal().copy());
   }

   @Override
   public <T> T accept(DataVisitor<T> visitor) {
      return visitor.visitList(this);
   }

   @Override
   public IData.Type getType() {
      return IData.Type.LIST;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ListData iData = (ListData)o;
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
