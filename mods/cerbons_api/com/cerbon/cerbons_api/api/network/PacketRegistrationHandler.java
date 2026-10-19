package com.cerbon.cerbons_api.api.network;

import com.cerbon.cerbons_api.api.network.data.PacketContainer;
import com.cerbon.cerbons_api.api.network.data.PacketContext;
import com.cerbon.cerbons_api.api.network.data.Side;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public abstract class PacketRegistrationHandler implements INetworkHandler, IPacketRegistrar {
   protected final Map<Class<?>, PacketContainer<?>> PACKET_MAP = new HashMap<>();
   protected final Side side;

   public PacketRegistrationHandler(Side side) {
      this.side = side;
   }

   @Override
   public <T> IPacketRegistrar registerPacket(
      Type<? extends CustomPacketPayload> type, Class<T> packetClass, StreamCodec<? extends FriendlyByteBuf, T> codec, Consumer<PacketContext<T>> handler
   ) {
      PacketContainer<T> container = new PacketContainer<>(type, packetClass, codec, handler);
      this.PACKET_MAP.put(packetClass, container);
      this.registerPacket(container);
      return this;
   }

   @Override
   public Side getSide() {
      return this.side;
   }

   protected abstract <T> void registerPacket(PacketContainer<T> var1);
}
