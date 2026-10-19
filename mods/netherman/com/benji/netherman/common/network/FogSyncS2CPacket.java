package com.benji.netherman.common.network;

import com.benji.netherman.NetherExp;
import com.benji.netherman.client.ClientFogHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record FogSyncS2CPacket(boolean isInside) implements CustomPacketPayload {
   public static final Type<FogSyncS2CPacket> TYPE = new Type(NetherExp.location("fog_sync"));
   public static final StreamCodec<FriendlyByteBuf, FogSyncS2CPacket> STREAM_CODEC = StreamCodec.of(
      (buf, packet) -> buf.writeBoolean(packet.isInside()), buf -> new FogSyncS2CPacket(buf.readBoolean())
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handleData(FogSyncS2CPacket data, IPayloadContext context) {
      context.enqueueWork(() -> ClientFogHandler.isInsideMansion = data.isInside());
   }
}
