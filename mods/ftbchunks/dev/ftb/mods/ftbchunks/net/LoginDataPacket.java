package dev.ftb.mods.ftbchunks.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record LoginDataPacket(UUID serverId) implements CustomPacketPayload {
   public static final Type<LoginDataPacket> TYPE = new Type(FTBChunksAPI.rl("login_data_packet"));
   public static final StreamCodec<FriendlyByteBuf, LoginDataPacket> STREAM_CODEC = StreamCodec.composite(
      UUIDUtil.STREAM_CODEC, LoginDataPacket::serverId, LoginDataPacket::new
   );

   public Type<LoginDataPacket> type() {
      return TYPE;
   }

   public static void handle(LoginDataPacket message, PacketContext context) {
      context.queue(() -> FTBChunksClient.INSTANCE.handlePlayerLogin(message.serverId));
   }
}
