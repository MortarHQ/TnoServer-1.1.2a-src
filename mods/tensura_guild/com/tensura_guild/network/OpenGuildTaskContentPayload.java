package com.tensura_guild.network;

import com.tensura_guild.client.GuildTaskClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenGuildTaskContentPayload(ResourceLocation taskId) implements CustomPacketPayload {
   public static final Type<OpenGuildTaskContentPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_guild", "open_guild_task_content"));
   public static final StreamCodec<ByteBuf, OpenGuildTaskContentPayload> STREAM_CODEC = ResourceLocation.STREAM_CODEC
      .map(OpenGuildTaskContentPayload::new, OpenGuildTaskContentPayload::taskId);

   public Type<OpenGuildTaskContentPayload> type() {
      return TYPE;
   }

   public static void handle(OpenGuildTaskContentPayload payload, IPayloadContext context) {
      context.enqueueWork(() -> GuildTaskClient.openCarriedTask(payload.taskId()));
   }
}
