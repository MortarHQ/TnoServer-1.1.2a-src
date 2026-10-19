package com.cerbon.cerbons_api.api.network;

import com.cerbon.cerbons_api.api.network.data.PacketContext;
import java.util.function.Consumer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record CommonNetwork(PacketRegistrationHandler packetRegistration) {
   private static DelayedPacketRegistrationHandler delayedHandler;
   public static CommonNetwork INSTANCE;

   public CommonNetwork(PacketRegistrationHandler packetRegistration) {
      INSTANCE = this;
      this.packetRegistration = packetRegistration;
      getDelayedHandler().registerQueuedPackets(packetRegistration);
   }

   private static DelayedPacketRegistrationHandler getDelayedHandler() {
      if (delayedHandler == null) {
         delayedHandler = new DelayedPacketRegistrationHandler();
      }

      return delayedHandler;
   }

   public static <T> IPacketRegistrar registerPacket(
      Type<? extends CustomPacketPayload> type, Class<T> packetClass, StreamCodec<? extends FriendlyByteBuf, T> codec, Consumer<PacketContext<T>> handler
   ) {
      return INSTANCE != null
         ? INSTANCE.packetRegistration.registerPacket(type, packetClass, codec, handler)
         : getDelayedHandler().registerPacket(type, packetClass, codec, handler);
   }
}
