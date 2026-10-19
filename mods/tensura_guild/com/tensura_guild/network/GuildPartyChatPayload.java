package com.tensura_guild.network;

import com.tensura_guild.data.GuildPartyService;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record GuildPartyChatPayload(String content) implements CustomPacketPayload {
   public static final Type<GuildPartyChatPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_guild", "guild_party_chat"));
   public static final StreamCodec<ByteBuf, GuildPartyChatPayload> STREAM_CODEC = new StreamCodec<ByteBuf, GuildPartyChatPayload>() {
      public GuildPartyChatPayload decode(ByteBuf buffer) {
         return new GuildPartyChatPayload(new FriendlyByteBuf(buffer).readUtf(160));
      }

      public void encode(ByteBuf buffer, GuildPartyChatPayload value) {
         new FriendlyByteBuf(buffer).writeUtf(value.content == null ? "" : value.content, 160);
      }
   };

   public Type<GuildPartyChatPayload> type() {
      return TYPE;
   }

   public static void handle(GuildPartyChatPayload payload, IPayloadContext context) {
      context.enqueueWork(() -> {
         if (context.player() instanceof ServerPlayer player) {
            GuildPartyService.sendChat(player, payload.content);
         }
      });
   }
}
