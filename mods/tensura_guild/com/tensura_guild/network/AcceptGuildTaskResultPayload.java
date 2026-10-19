package com.tensura_guild.network;

import com.tensura_guild.client.GuildTaskClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record AcceptGuildTaskResultPayload(ResourceLocation taskId, boolean accepted) implements CustomPacketPayload {
   public static final Type<AcceptGuildTaskResultPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_guild", "accept_guild_task_result"));
   public static final StreamCodec<ByteBuf, AcceptGuildTaskResultPayload> STREAM_CODEC = StreamCodec.composite(
      ResourceLocation.STREAM_CODEC,
      AcceptGuildTaskResultPayload::taskId,
      ByteBufCodecs.BOOL,
      AcceptGuildTaskResultPayload::accepted,
      AcceptGuildTaskResultPayload::new
   );

   public Type<AcceptGuildTaskResultPayload> type() {
      return TYPE;
   }

   public static void handle(AcceptGuildTaskResultPayload payload, IPayloadContext context) {
      context.enqueueWork(() -> GuildTaskClient.handleAcceptResult(payload));
   }
}
