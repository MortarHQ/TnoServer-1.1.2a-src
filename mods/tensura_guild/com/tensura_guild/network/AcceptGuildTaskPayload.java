package com.tensura_guild.network;

import com.tensura_guild.data.GuildTaskService;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record AcceptGuildTaskPayload(ResourceLocation taskId) implements CustomPacketPayload {
   public static final Type<AcceptGuildTaskPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_guild", "accept_guild_task"));
   public static final StreamCodec<ByteBuf, AcceptGuildTaskPayload> STREAM_CODEC = ResourceLocation.STREAM_CODEC
      .map(AcceptGuildTaskPayload::new, AcceptGuildTaskPayload::taskId);

   public Type<AcceptGuildTaskPayload> type() {
      return TYPE;
   }

   public static void handle(AcceptGuildTaskPayload payload, IPayloadContext context) {
      context.enqueueWork(() -> {
         if (context.player() instanceof ServerPlayer player) {
            boolean var4 = GuildTaskService.acceptTask(player, payload.taskId(), player.serverLevel().getGameTime());
            PacketDistributor.sendToPlayer(player, new AcceptGuildTaskResultPayload(payload.taskId(), var4), new CustomPacketPayload[0]);
         }
      });
   }
}
