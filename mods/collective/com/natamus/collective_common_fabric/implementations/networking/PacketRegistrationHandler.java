package com.natamus.collective_common_fabric.implementations.networking;

import com.natamus.collective_common_fabric.implementations.networking.api.NetworkHandler;
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

public abstract class PacketRegistrationHandler implements NetworkHandler, PacketRegistrar {
   public final Map<Class<?>, PacketContainer<?>> PACKET_MAP = new HashMap<>();
   protected final Side side;

   public PacketRegistrationHandler(Side side) {
      this.side = side;
   }

   @Override
   public <T> PacketRegistrar registerPacket(
      class_2960 packetIdentifier, Class<T> packetClass, BiConsumer<T, class_2540> encoder, Function<class_2540, T> decoder, Consumer<PacketContext<T>> handler
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
