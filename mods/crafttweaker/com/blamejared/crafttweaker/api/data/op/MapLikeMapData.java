package com.blamejared.crafttweaker.api.data.op;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.StringData;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapLike;
import java.util.stream.Stream;
import org.jetbrains.annotations.Nullable;

final class MapLikeMapData implements MapLike<IData> {
   private final MapData data;

   private MapLikeMapData(MapData data) {
      this.data = data;
   }

   static MapLikeMapData of(MapData data) {
      return new MapLikeMapData(GenericUtil.uncheck(data.copyInternal()));
   }

   @Nullable
   public IData get(IData key) {
      return this.get(key.getAsString());
   }

   @Nullable
   public IData get(String key) {
      return this.data.getAt(key);
   }

   public Stream<Pair<IData, IData>> entries() {
      return this.data.getKeys().stream().map(it -> Pair.of(new StringData(it), this.data.getAt(it)));
   }

   @Override
   public String toString() {
      return "MapLike[%s]".formatted(this.data);
   }
}
