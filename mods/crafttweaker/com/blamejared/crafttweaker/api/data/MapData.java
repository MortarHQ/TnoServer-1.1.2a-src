package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.datafixers.util.Pair;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.data.MapData")
@ZenRegister
@Document("vanilla/api/data/MapData")
public class MapData implements IData {
   private final CompoundTag internal;
   private final Set<String> boolDataKeys;

   public MapData(CompoundTag internal, Set<String> boolDataKeys) {
      this.internal = internal;
      this.boolDataKeys = boolDataKeys;
   }

   public MapData(CompoundTag internal) {
      this.internal = internal;
      this.boolDataKeys = new HashSet<>();
   }

   @ZenCodeType.Constructor
   public MapData() {
      this(new CompoundTag());
   }

   @ZenCodeType.Constructor
   public MapData(Map<String, IData> map) {
      this();
      this.putAll(map);
   }

   @ZenCodeType.Method
   public void putAll(Map<String, IData> map) {
      map.forEach((s, iData) -> {
         if (iData != null) {
            this.getInternal().put(s, iData.getInternal());
            if (iData instanceof BoolData) {
               this.boolDataKeys.add(s);
            }
         }
      });
   }

   @Override
   public void remove(int index) {
      this.put(String.valueOf(index), null);
   }

   @Override
   public void remove(String key) {
      this.put(key, null);
   }

   @Override
   public IData getAt(int index) {
      return this.getAt(Integer.toString(index));
   }

   @Override
   public IData getAt(String key) {
      if (this.boolDataKeys.contains(key)) {
         return this.getInternal().getByte(key) == 1 ? BoolData.TRUE : BoolData.FALSE;
      } else {
         return TagToDataConverter.convert(this.getInternal().get(key));
      }
   }

   @Override
   public boolean contains(IData other) {
      CompoundTag internal = this.getInternal();
      if (other instanceof StringData) {
         return internal.contains(other.getAsString());
      }

      if (!other.isMappable()) {
         return false;
      }

      Map<String, IData> dataMap = other.asMap();

      for (Entry<String, IData> dataEntry : dataMap.entrySet()) {
         if (!internal.contains(dataEntry.getKey())) {
            return false;
         }

         if (!TagToDataConverter.convert(internal.get(dataEntry.getKey())).contains(dataEntry.getValue())) {
            return false;
         }
      }

      return true;
   }

   @Override
   public void put(String name, @ZenCodeType.Nullable IData data) {
      if (data == null) {
         this.boolDataKeys.remove(name);
         this.getInternal().remove(name);
      } else {
         if (data instanceof BoolData) {
            this.boolDataKeys.add(name);
         }

         this.getInternal().put(name, data.getInternal());
      }
   }

   @Override
   public boolean equalTo(IData other) {
      if (this == other) {
         return true;
      }

      Map<String, IData> thisMap = this.asMap();
      Map<String, IData> otherMap = other.asMap();
      if (thisMap.size() != otherMap.size()) {
         return false;
      }

      for (Entry<String, IData> entry : this.asMap().entrySet()) {
         if (!otherMap.containsKey(entry.getKey())) {
            return false;
         }

         if (!otherMap.get(entry.getKey()).equalTo(entry.getValue())) {
            return false;
         }
      }

      return true;
   }

   @Override
   public boolean isMappable() {
      return true;
   }

   @Override
   public Map<String, IData> asMap() {
      return this.getInternal().getAllKeys().stream().map(s -> Pair.of(s, this.getAt(s))).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
   }

   @Override
   public int length() {
      return this.getInternal().size();
   }

   @Override
   public Set<String> getKeys() {
      return this.getInternal().getAllKeys();
   }

   @NotNull
   @Override
   public Iterator<IData> iterator() {
      return this.getInternal().getAllKeys().stream().map(StringData::new).iterator();
   }

   public CompoundTag getInternal() {
      return this.internal;
   }

   @Override
   public IData copy() {
      return new MapData(this.getInternal(), this.boolDataKeys);
   }

   @Override
   public IData copyInternal() {
      return new MapData(this.getInternal().copy(), new HashSet<>(this.boolDataKeys));
   }

   @Override
   public <T> T accept(DataVisitor<T> visitor) {
      return visitor.visitMap(this);
   }

   @Override
   public IData.Type getType() {
      return IData.Type.MAP;
   }

   public Set<String> boolDataKeys() {
      return this.boolDataKeys;
   }

   @Override
   public IData merge(IData other) {
      if (other instanceof MapData map) {
         Set<String> newBoolDataKeys = new HashSet<>(this.boolDataKeys);
         newBoolDataKeys.addAll(map.boolDataKeys);
         return new MapData(this.getInternal().merge(map.getInternal()), newBoolDataKeys);
      } else {
         throw new IllegalArgumentException("Cannot merge incompatible data type: " + other.getType());
      }
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         MapData iData = (MapData)o;
         return Objects.equals(this.getInternal(), iData.getInternal()) && Objects.equals(this.boolDataKeys, iData.boolDataKeys);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getInternal(), this.boolDataKeys);
   }

   @Override
   public String toString() {
      return this.getAsString();
   }
}
