package com.natamus.collective_common_forge.implementations.networking;

import com.natamus.collective_common_forge.implementations.networking.data.PacketContext;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class NetworkSetup {
   private final PacketRegistrationHandler packetRegistration;
   private static DelayedPacketRegistrationHandler delayedHandler;
   public static NetworkSetup INSTANCE;

   public NetworkSetup(PacketRegistrationHandler packetRegistration) {
      INSTANCE = this;
      this.packetRegistration = packetRegistration;
      getDelayedHandler().registerQueuedPackets(packetRegistration);
   }

   public static DelayedPacketRegistrationHandler getDelayedHandler() {
      if (delayedHandler == null) {
         delayedHandler = new DelayedPacketRegistrationHandler();
      }

      return delayedHandler;
   }

   public static <T> PacketRegistrar registerPacket(
      ResourceLocation packetIdentifier,
      Class<T> messageType,
      BiConsumer<T, FriendlyByteBuf> encoder,
      Function<FriendlyByteBuf, T> decoder,
      Consumer<PacketContext<T>> handler
   ) {
      return INSTANCE != null
         ? INSTANCE.packetRegistration.registerPacket(packetIdentifier, messageType, encoder, decoder, handler)
         : getDelayedHandler().registerPacket(packetIdentifier, messageType, encoder, decoder, handler);
   }

   public PacketRegistrationHandler getPacketRegistration() {
      return this.packetRegistration;
   }
}
