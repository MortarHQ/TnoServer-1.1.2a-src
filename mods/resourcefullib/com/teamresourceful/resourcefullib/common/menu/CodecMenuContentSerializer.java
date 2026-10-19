package com.teamresourceful.resourcefullib.common.menu;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.Nullable;

public interface CodecMenuContentSerializer<T extends MenuContent<T>> extends MenuContentSerializer<T> {
   ByteCodec<T> codec();

   @Nullable
   @Override
   default T from(FriendlyByteBuf buffer) {
      return (T)this.codec().decode(buffer);
   }

   @Override
   default void to(FriendlyByteBuf buffer, T content) {
      this.codec().encode(content, buffer);
   }
}
