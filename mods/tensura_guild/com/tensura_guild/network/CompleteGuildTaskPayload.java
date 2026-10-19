package com.tensura_guild.network;

import com.tensura_guild.data.GuildTaskService;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record CompleteGuildTaskPayload(ResourceLocation taskId) implements CustomPacketPayload {
   public static final Type<CompleteGuildTaskPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_guild", "complete_guild_task"));
   public static final StreamCodec<ByteBuf, CompleteGuildTaskPayload> STREAM_CODEC = ResourceLocation.STREAM_CODEC
      .map(CompleteGuildTaskPayload::new, CompleteGuildTaskPayload::taskId);

   public Type<CompleteGuildTaskPayload> type() {
      return TYPE;
   }

   public static void handle(CompleteGuildTaskPayload payload, IPayloadContext context) {
      context.enqueueWork(() -> {
         if (context.player() instanceof ServerPlayer player) {
            GuildTaskService.completeTask(player, payload.taskId());
         }
      });
   }
}
