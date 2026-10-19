package com.tensura_guild.network;

import com.tensura_guild.client.GuildShopClient;
import com.tensura_guild.data.GuildReputationShopService;
import io.netty.buffer.ByteBuf;
import java.util.List;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record GuildShopStatePayload(int rankLevel, long nextRefreshGameTime, List<GuildReputationShopService.ShopEntry> entries, List<Integer> purchasedCounts)
   implements CustomPacketPayload {
   public static final Type<GuildShopStatePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_guild", "guild_shop_state"));
   public static final StreamCodec<ByteBuf, GuildShopStatePayload> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.VAR_INT,
      GuildShopStatePayload::rankLevel,
      ByteBufCodecs.VAR_LONG,
      GuildShopStatePayload::nextRefreshGameTime,
      GuildReputationShopService.ShopEntry.STREAM_CODEC.apply(ByteBufCodecs.list()),
      GuildShopStatePayload::entries,
      ByteBufCodecs.VAR_INT.apply(ByteBufCodecs.list()),
      GuildShopStatePayload::purchasedCounts,
      GuildShopStatePayload::new
   );

   public Type<GuildShopStatePayload> type() {
      return TYPE;
   }

   public static void handle(GuildShopStatePayload payload, IPayloadContext context) {
      context.enqueueWork(() -> GuildShopClient.handleState(payload));
   }
}
