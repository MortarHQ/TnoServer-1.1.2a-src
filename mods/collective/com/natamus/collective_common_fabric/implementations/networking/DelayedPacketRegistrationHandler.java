package com.natamus.collective_common_fabric.implementations.networking;

import com.natamus.collective_common_fabric.implementations.networking.data.PacketContainer;
import com.natamus.collective_common_fabric.implementations.networking.data.PacketContext;
import com.natamus.collective_common_fabric.implementations.networking.data.Side;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.class_2540;
import net.minecraft.class_2960;

public class DelayedPacketRegistrationHandler implements PacketRegistrar {
   private static final Map<Class<?>, PacketContainer<?>> QUEUED_PACKET_MAP = new HashMap<>();

   @Override
   public Side getSide() {
      return Side.CLIENT;
   }

   @Override
   public <T> PacketRegistrar registerPacket(
      class_2960 packetIdentifier, Class<T> messageType, BiConsumer<T, class_2540> encoder, Function<class_2540, T> decoder, Consumer<PacketContext<T>> handler
   ) {
      PacketContainer<T> container = new PacketContainer<>(packetIdentifier, messageType, encoder, decoder, handler);
      QUEUED_PACKET_MAP.put(messageType, container);
      return this;
   }

   public void registerQueuedPackets(PacketRegistrationHandler packetRegistration) {
      if (!QUEUED_PACKET_MAP.isEmpty()) {
         packetRegistration.PACKET_MAP.putAll(QUEUED_PACKET_MAP);
         QUEUED_PACKET_MAP.forEach((aClass, container) -> packetRegistration.registerPacket((PacketContainer<?>)container));
      }
   }
}
