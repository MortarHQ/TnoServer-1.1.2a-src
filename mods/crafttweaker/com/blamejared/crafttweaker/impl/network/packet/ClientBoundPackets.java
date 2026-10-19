package com.blamejared.crafttweaker.impl.network.packet;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public enum ClientBoundPackets {
   COPY(new Type(ClientBoundCopyPacket.ID), ClientBoundCopyPacket.STREAM_CODEC),
   OPEN_FILE(new Type(ClientBoundSendOpenFileMessagePacket.ID), ClientBoundSendOpenFileMessagePacket.STREAM_CODEC),
   DATA(new Type(ClientBoundDataPacket.ID), ClientBoundDataPacket.STREAM_CODEC);

   private final Type<CraftTweakerPacket> type;
   private final StreamCodec<RegistryFriendlyByteBuf, CraftTweakerPacket> streamCodec;

   ClientBoundPackets(Type<CraftTweakerPacket> type, StreamCodec<RegistryFriendlyByteBuf, ? extends CraftTweakerPacket> streamCodec) {
      this.type = type;
      this.streamCodec = GenericUtil.uncheck(streamCodec);
   }

   public Type<CraftTweakerPacket> type() {
      return this.type;
   }

   public StreamCodec<RegistryFriendlyByteBuf, CraftTweakerPacket> streamCodec() {
      return this.streamCodec;
   }
}
