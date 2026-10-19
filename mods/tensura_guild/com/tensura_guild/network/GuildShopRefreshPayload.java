package com.tensura_guild.network;

import com.tensura_guild.client.GuildShopClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record GuildShopRefreshPayload() implements CustomPacketPayload {
   public static final Type<GuildShopRefreshPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_guild", "guild_shop_refresh"));
   public static final StreamCodec<ByteBuf, GuildShopRefreshPayload> STREAM_CODEC = StreamCodec.unit(new GuildShopRefreshPayload());

   public Type<GuildShopRefreshPayload> type() {
      return TYPE;
   }

   public static void handle(GuildShopRefreshPayload payload, IPayloadContext context) {
      context.enqueueWork(GuildShopClient::handleRefresh);
   }
}
