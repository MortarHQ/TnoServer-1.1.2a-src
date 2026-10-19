package com.teamresourceful.resourcefullib.common.bytecodecs;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.IdMap;

public record IdMapByteCodec<T>(IdMap<T> map) implements ByteCodec<T> {
   public void encode(T value, ByteBuf buffer) {
      int id = this.map.getId(value);
      if (id == -1) {
         throw new IllegalArgumentException("Can't find id for '" + value + "' in map " + this.map);
      }

      ByteBufUtils.writeVarInt(buffer, id);
   }

   public T decode(ByteBuf buffer) {
      int id = ByteBufUtils.readVarInt(buffer);
      return (T)this.map.byId(id);
   }
}
