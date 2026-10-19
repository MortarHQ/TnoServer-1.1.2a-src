package org.crypticdev.stextras.net.S2C;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.Stextras;
import org.crypticdev.stextras.net.STNetworkCompat;
import org.crypticdev.stextras.net.STNetworkGuards;
import org.crypticdev.stextras.storage.STExtarsStorage;

public record STExtrasBossKillsSync(int kills) implements CustomPacketPayload {
   public static final Type<STExtrasBossKillsSync> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("stextras", "boss_kills_sync"));
   public static final StreamCodec<FriendlyByteBuf, STExtrasBossKillsSync> STREAM_CODEC = StreamCodec.of(
      (buf, p) -> buf.writeVarInt(p.kills()), buf -> new STExtrasBossKillsSync(buf.readVarInt())
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void registerCommon() {
      STNetworkCompat.registerS2CType(TYPE, STREAM_CODEC);
   }

   public static void registerClient() {
      NetworkManager.registerReceiver(
         NetworkManager.s2c(), TYPE, STREAM_CODEC, (payload, context) -> context.queue(() -> STExtarsStorage.Client.setBossKills(payload.kills()))
      );
   }

   public static void sendTo(ServerPlayer player, int kills) {
      if (!STNetworkGuards.canSendTo(player)) {
         Stextras.LOGGER
            .warn("[NETWORK_GUARD] Skipped STExtrasBossKillsSync payload because player cannot receive packets | player={}", STNetworkGuards.playerName(player));
      } else {
         NetworkManager.sendToPlayer(player, new STExtrasBossKillsSync(kills));
      }
   }
}
