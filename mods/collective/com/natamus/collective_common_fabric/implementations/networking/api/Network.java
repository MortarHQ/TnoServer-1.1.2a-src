package com.natamus.collective_common_fabric.implementations.networking.api;

import com.natamus.collective_common_fabric.implementations.networking.NetworkSetup;
import com.natamus.collective_common_fabric.implementations.networking.PacketRegistrar;
import com.natamus.collective_common_fabric.implementations.networking.data.PacketContext;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.class_2540;
import net.minecraft.class_2960;

public class Network {
   public static <T> PacketRegistrar registerPacket(
      class_2960 packetIdentifier, Class<T> messageType, BiConsumer<T, class_2540> encoder, Function<class_2540, T> decoder, Consumer<PacketContext<T>> handler
   ) {
      return NetworkSetup.registerPacket(packetIdentifier, messageType, encoder, decoder, handler);
   }

   public static NetworkHandler getNetworkHandler() {
      return NetworkSetup.INSTANCE.getPacketRegistration();
   }
}
