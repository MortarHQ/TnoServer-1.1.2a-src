package com.teamresourceful.resourcefullib.common.item;

import com.mojang.serialization.Codec;
import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.StreamCodecByteCodec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponentType.Builder;

public class ResourcefulComponentType<T> {
   private final Builder<T> builder = new Builder();

   public ResourcefulComponentType<T> cacheEncoding() {
      this.builder.cacheEncoding();
      return this;
   }

   public ResourcefulComponentType<T> persistent(Codec<T> codec) {
      this.builder.persistent(codec);
      return this;
   }

   public ResourcefulComponentType<T> networkSynchronized(ByteCodec<T> codec) {
      this.builder.networkSynchronized(StreamCodecByteCodec.toRegistry(codec));
      return this;
   }

   public DataComponentType<T> build() {
      return this.builder.build();
   }
}
