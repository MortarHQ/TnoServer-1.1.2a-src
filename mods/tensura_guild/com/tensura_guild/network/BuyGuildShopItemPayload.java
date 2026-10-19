package com.tensura_guild.network;

import com.tensura_guild.data.GuildReputationShopService;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record BuyGuildShopItemPayload(int rankLevel, int slot, int requestedMultiplier) implements CustomPacketPayload {
   public static final Type<BuyGuildShopItemPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensura_guild", "buy_guild_shop_item"));
   public static final StreamCodec<ByteBuf, BuyGuildShopItemPayload> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.VAR_INT,
      BuyGuildShopItemPayload::rankLevel,
      ByteBufCodecs.VAR_INT,
      BuyGuildShopItemPayload::slot,
      ByteBufCodecs.VAR_INT,
      BuyGuildShopItemPayload::requestedMultiplier,
      BuyGuildShopItemPayload::new
   );

   public Type<BuyGuildShopItemPayload> type() {
      return TYPE;
   }

   public static void handle(BuyGuildShopItemPayload payload, IPayloadContext context) {
      context.enqueueWork(
         () -> {
            if (context.player() instanceof ServerPlayer player) {
               GuildReputationShopService.BuyResult result = GuildReputationShopService.buy(
                  player, payload.rankLevel(), payload.slot(), payload.requestedMultiplier()
               );
               if (result == GuildReputationShopService.BuyResult.LIMIT_REACHED) {
                  PacketDistributor.sendToPlayer(player, new GuildShopLimitReachedPayload(payload.rankLevel(), payload.slot()), new CustomPacketPayload[0]);
               } else {
                  if (result == GuildReputationShopService.BuyResult.SUCCESS) {
                     RequestGuildShopStatePayload.sendState(player, payload.rankLevel());
                  }
               }
            }
         }
      );
   }
}
