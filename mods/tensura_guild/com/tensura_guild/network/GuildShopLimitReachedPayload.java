package com.tensura_guild.network;

import com.tensura_guild.client.GuildShopClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record GuildShopLimitReachedPayload(int rankLevel, int slot) implements CustomPacketPayload {
   public static final Type<GuildShopLimitReachedPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_guild", "guild_shop_limit_reached"));
   public static final StreamCodec<ByteBuf, GuildShopLimitReachedPayload> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.VAR_INT,
      GuildShopLimitReachedPayload::rankLevel,
      ByteBufCodecs.VAR_INT,
      GuildShopLimitReachedPayload::slot,
      GuildShopLimitReachedPayload::new
   );

   public Type<GuildShopLimitReachedPayload> type() {
      return TYPE;
   }

   public static void handle(GuildShopLimitReachedPayload payload, IPayloadContext context) {
      context.enqueueWork(() -> GuildShopClient.handleLimitReached(payload));
   }
}
