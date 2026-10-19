package com.tensura_guild.network;

import com.tensura_guild.data.GuildLevelData;
import com.tensura_guild.data.GuildReputationShopService;
import com.tensura_guild.data.GuildWorldData;
import com.tensura_guild.registry.GuildAttachments;
import io.netty.buffer.ByteBuf;
import java.util.List;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record RequestGuildShopStatePayload(int rankLevel) implements CustomPacketPayload {
   public static final Type<RequestGuildShopStatePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_guild", "request_guild_shop_state"));
   public static final StreamCodec<ByteBuf, RequestGuildShopStatePayload> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.VAR_INT, RequestGuildShopStatePayload::rankLevel, RequestGuildShopStatePayload::new
   );

   public Type<RequestGuildShopStatePayload> type() {
      return TYPE;
   }

   public static void handle(RequestGuildShopStatePayload payload, IPayloadContext context) {
      context.enqueueWork(() -> {
         if (context.player() instanceof ServerPlayer player) {
            sendState(player, payload.rankLevel());
         }
      });
   }

   public static void sendState(ServerPlayer player, int requestedRankLevel) {
      int rankLevel = Math.max(1, Math.min(GuildLevelData.MAX_LEVEL, requestedRankLevel));
      int unlockedLevel = ((GuildLevelData)player.getData(GuildAttachments.GUILD_LEVEL)).levelIndex();
      if (rankLevel > unlockedLevel) {
         rankLevel = unlockedLevel;
      }

      long gameTime = player.level().getGameTime();
      long refreshNonce = GuildWorldData.get(player.server).shopRefreshNonce();
      List<GuildReputationShopService.ShopEntry> entries = GuildReputationShopService.entriesForPlayer(
         player, GuildLevelData.rankForLevel(rankLevel), gameTime, refreshNonce
      );
      PacketDistributor.sendToPlayer(
         player,
         new GuildShopStatePayload(
            rankLevel,
            GuildReputationShopService.nextRefreshGameTime(gameTime),
            entries,
            GuildReputationShopService.purchasedCounts(player, rankLevel, entries, gameTime, refreshNonce)
         ),
         new CustomPacketPayload[0]
      );
   }
}
