package com.teamresourceful.resourcefullib.common.network.base;

import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.internal.NetworkPacketPayload;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;

@NonExtendable
public interface PacketType<T extends Packet<T>> {
   @Deprecated
   @ScheduledForRemoval(inVersion = "22.0")
   default Class<T> type() {
      return null;
   }

   ResourceLocation id();

   void encode(T var1, RegistryFriendlyByteBuf var2);

   T decode(RegistryFriendlyByteBuf var1);

   @Internal
   default StreamCodec<RegistryFriendlyByteBuf, NetworkPacketPayload<T>> codec(Type<NetworkPacketPayload<T>> type) {
      return StreamCodec.of((buf, payload) -> this.encode((T)payload.packet(), buf), buf -> new NetworkPacketPayload(this.decode(buf), type));
   }

   @Internal
   default Type<NetworkPacketPayload<T>> type(ResourceLocation channel) {
      return new Type(channel.withSuffix("/" + this.id().getNamespace() + "/" + this.id().getPath()));
   }
}
