package com.cerbon.cerbons_api.api.network;

import com.cerbon.cerbons_api.api.network.data.PacketContext;
import com.cerbon.cerbons_api.api.network.data.Side;
import java.util.function.Consumer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public interface IPacketRegistrar {
   Side getSide();

   <T> IPacketRegistrar registerPacket(
      Type<? extends CustomPacketPayload> var1, Class<T> var2, StreamCodec<? extends FriendlyByteBuf, T> var3, Consumer<PacketContext<T>> var4
   );
}
