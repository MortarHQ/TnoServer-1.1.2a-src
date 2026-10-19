package com.teamresourceful.resourcefullib.common.network.internal;

import com.teamresourceful.resourcefullib.common.network.Packet;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public record NetworkPacketPayload<T extends Packet<T>>(T packet, Type<NetworkPacketPayload<T>> type) implements CustomPacketPayload {
   public NetworkPacketPayload(T packet, ResourceLocation channel) {
      this(packet, packet.type().type(channel));
   }
}
