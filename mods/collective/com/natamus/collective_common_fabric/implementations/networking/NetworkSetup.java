package com.natamus.collective_common_fabric.implementations.networking;

import com.natamus.collective_common_fabric.implementations.networking.data.PacketContext;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.class_2540;
import net.minecraft.class_2960;

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
      class_2960 packetIdentifier, Class<T> messageType, BiConsumer<T, class_2540> encoder, Function<class_2540, T> decoder, Consumer<PacketContext<T>> handler
   ) {
      return INSTANCE != null
         ? INSTANCE.packetRegistration.registerPacket(packetIdentifier, messageType, encoder, decoder, handler)
         : getDelayedHandler().registerPacket(packetIdentifier, messageType, encoder, decoder, handler);
   }

   public PacketRegistrationHandler getPacketRegistration() {
      return this.packetRegistration;
   }
}
