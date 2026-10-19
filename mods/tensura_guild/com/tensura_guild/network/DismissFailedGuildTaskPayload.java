package com.tensura_guild.network;

import com.tensura_guild.data.GuildTaskService;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record DismissFailedGuildTaskPayload(ResourceLocation taskId) implements CustomPacketPayload {
   public static final Type<DismissFailedGuildTaskPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_guild", "dismiss_failed_guild_task"));
   public static final StreamCodec<ByteBuf, DismissFailedGuildTaskPayload> STREAM_CODEC = ResourceLocation.STREAM_CODEC
      .map(DismissFailedGuildTaskPayload::new, DismissFailedGuildTaskPayload::taskId);

   public Type<DismissFailedGuildTaskPayload> type() {
      return TYPE;
   }

   public static void handle(DismissFailedGuildTaskPayload payload, IPayloadContext context) {
      context.enqueueWork(() -> {
         if (context.player() instanceof ServerPlayer player) {
            GuildTaskService.dismissFailedTask(player, payload.taskId());
         }
      });
   }
}
