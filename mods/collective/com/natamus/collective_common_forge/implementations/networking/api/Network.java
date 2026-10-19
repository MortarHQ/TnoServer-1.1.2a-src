package com.natamus.collective_common_forge.implementations.networking.api;

import com.natamus.collective_common_forge.implementations.networking.NetworkSetup;
import com.natamus.collective_common_forge.implementations.networking.PacketRegistrar;
import com.natamus.collective_common_forge.implementations.networking.data.PacketContext;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class Network {
   public static <T> PacketRegistrar registerPacket(
      ResourceLocation packetIdentifier,
      Class<T> messageType,
      BiConsumer<T, FriendlyByteBuf> encoder,
      Function<FriendlyByteBuf, T> decoder,
      Consumer<PacketContext<T>> handler
   ) {
      return NetworkSetup.registerPacket(packetIdentifier, messageType, encoder, decoder, handler);
   }

   public static NetworkHandler getNetworkHandler() {
      return NetworkSetup.INSTANCE.getPacketRegistration();
   }
}
