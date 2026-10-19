package com.natamus.collective_common_forge.implementations.networking;

import com.natamus.collective_common_forge.implementations.networking.api.NetworkHandler;
import com.natamus.collective_common_forge.implementations.networking.data.PacketContainer;
import com.natamus.collective_common_forge.implementations.networking.data.PacketContext;
import com.natamus.collective_common_forge.implementations.networking.data.Side;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public abstract class PacketRegistrationHandler implements NetworkHandler, PacketRegistrar {
   public final Map<Class<?>, PacketContainer<?>> PACKET_MAP = new HashMap<>();
   protected final Side side;

   public PacketRegistrationHandler(Side side) {
      this.side = side;
   }

   @Override
   public <T> PacketRegistrar registerPacket(
      ResourceLocation packetIdentifier,
      Class<T> packetClass,
      BiConsumer<T, FriendlyByteBuf> encoder,
      Function<FriendlyByteBuf, T> decoder,
      Consumer<PacketContext<T>> handler
   ) {
      PacketContainer<T> container = new PacketContainer<>(packetIdentifier, packetClass, encoder, decoder, handler);
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
