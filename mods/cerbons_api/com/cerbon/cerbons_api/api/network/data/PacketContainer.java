package com.cerbon.cerbons_api.api.network.data;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record PacketContainer<T>(
   Type<? extends CustomPacketPayload> type,
   Class<T> classType,
   BiConsumer<T, FriendlyByteBuf> encoder,
   Function<FriendlyByteBuf, T> decoder,
   StreamCodec<? super FriendlyByteBuf, T> codec,
   Consumer<PacketContext<T>> handler
) {
   public <B extends FriendlyByteBuf> PacketContainer(
      Type<? extends CustomPacketPayload> type, Class<T> classType, StreamCodec<? super B, T> codec, Consumer<PacketContext<T>> handle
   ) {
      this(type, classType, null, null, codec, handle);
   }

   public <K extends CustomPacketPayload> Type<K> getType() {
      return (Type<K>)this.type();
   }

   public <K extends FriendlyByteBuf> StreamCodec<K, CommonPacketWrapper> getCodec() {
      return this.codec() == null
         ? CustomPacketPayload.codec(
            (packet, buf) -> this.encoder().accept((T)packet.packet(), buf), buf -> new CommonPacketWrapper<>(this, this.decoder().apply(buf))
         )
         : CustomPacketPayload.codec(
            (packet, buf) -> this.codec().encode(buf, packet.packet()), buf -> new CommonPacketWrapper<>(this, (T)this.codec().decode(buf))
         );
   }
}
