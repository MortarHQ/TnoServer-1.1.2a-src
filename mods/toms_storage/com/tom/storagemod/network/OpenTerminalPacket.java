package com.tom.storagemod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public class OpenTerminalPacket implements CustomPacketPayload {
   public static final Type<OpenTerminalPacket> ID = new Type(ResourceLocation.tryBuild("toms_storage", "open_terminal"));
   public static final StreamCodec<FriendlyByteBuf, OpenTerminalPacket> STREAM_CODEC = CustomPacketPayload.codec((a, b) -> {}, OpenTerminalPacket::new);

   public OpenTerminalPacket() {
   }

   private OpenTerminalPacket(FriendlyByteBuf buffer) {
   }

   public Type<OpenTerminalPacket> type() {
      return ID;
   }
}
