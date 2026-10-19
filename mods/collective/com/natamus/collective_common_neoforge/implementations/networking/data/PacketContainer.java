package com.natamus.collective_common_neoforge.implementations.networking.data;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record PacketContainer<T>(
   Type<? extends CustomPacketPayload> type,
   Class<T> classType,
   BiConsumer<T, FriendlyByteBuf> encoder,
   Function<FriendlyByteBuf, T> decoder,
   Consumer<PacketContext<T>> handler
) {
   public PacketContainer(
      ResourceLocation id, Class<T> classType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, Consumer<PacketContext<T>> handle
   ) {
      this(new Type(id), classType, encoder, decoder, handle);
   }

   public <K extends CustomPacketPayload> Type<K> getType() {
      return (Type<K>)this.type();
   }

   public StreamCodec<FriendlyByteBuf, CommonPacketWrapper> getCodec() {
      return CustomPacketPayload.codec(
         (packet, buf) -> this.encoder().accept((T)packet.packet(), buf), buf -> new CommonPacketWrapper<>(this, this.decoder().apply(buf))
      );
   }
}
