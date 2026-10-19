package com.cerbon.cerbons_api.api.network;

import com.cerbon.cerbons_api.api.network.data.PacketContext;
import java.util.function.Consumer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public class Network {
   public static <T> IPacketRegistrar registerPacket(
      Type<? extends CustomPacketPayload> type, Class<T> packetClass, StreamCodec<? extends FriendlyByteBuf, T> codec, Consumer<PacketContext<T>> handler
   ) {
      return CommonNetwork.registerPacket(type, packetClass, codec, handler);
   }

   public static INetworkHandler getNetworkHandler() {
      return CommonNetwork.INSTANCE.packetRegistration();
   }
}
