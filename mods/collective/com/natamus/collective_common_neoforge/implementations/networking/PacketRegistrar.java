package com.natamus.collective_common_neoforge.implementations.networking;

import com.natamus.collective_common_neoforge.implementations.networking.data.PacketContext;
import com.natamus.collective_common_neoforge.implementations.networking.data.Side;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface PacketRegistrar {
   Side getSide();

   <T> PacketRegistrar registerPacket(
      ResourceLocation var1, Class<T> var2, BiConsumer<T, FriendlyByteBuf> var3, Function<FriendlyByteBuf, T> var4, Consumer<PacketContext<T>> var5
   );
}
