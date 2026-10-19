package com.cerbon.cerbons_api.api.network.data;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import org.jetbrains.annotations.NotNull;

public record CommonPacketWrapper<T>(PacketContainer<T> container, T packet) implements CustomPacketPayload {
   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return this.container.type();
   }
}
