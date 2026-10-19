package com.natamus.collective_common_neoforge.implementations.networking.data;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import org.jetbrains.annotations.NotNull;

public record CommonPacketWrapper<T>(PacketContainer<T> container, T packet) implements CustomPacketPayload {
   public void encode(FriendlyByteBuf buf) {
      this.container().encoder().accept(this.packet(), buf);
   }

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return this.container.type();
   }
}
