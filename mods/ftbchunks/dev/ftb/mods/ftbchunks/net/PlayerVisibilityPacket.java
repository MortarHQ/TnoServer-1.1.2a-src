package dev.ftb.mods.ftbchunks.net;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftbchunks.FTBChunksWorldConfig;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.api.FTBChunksProperties;
import dev.ftb.mods.ftbchunks.client.VisibleClientPlayers;
import dev.ftb.mods.ftbchunks.data.ChunkTeamDataImpl;
import dev.ftb.mods.ftbchunks.data.ClaimedChunkManagerImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public record PlayerVisibilityPacket(List<UUID> uuids) implements CustomPacketPayload {
   public static final Type<PlayerVisibilityPacket> TYPE = new Type(FTBChunksAPI.rl("player_visibility_packet"));
   public static final StreamCodec<FriendlyByteBuf, PlayerVisibilityPacket> STREAM_CODEC = StreamCodec.composite(
      UUIDUtil.STREAM_CODEC.apply(ByteBufCodecs.list()), PlayerVisibilityPacket::uuids, PlayerVisibilityPacket::new
   );

   public Type<PlayerVisibilityPacket> type() {
      return TYPE;
   }

   public static void handle(PlayerVisibilityPacket message, PacketContext context) {
      context.queue(() -> VisibleClientPlayers.updatePlayerList(message.uuids));
   }

   public static void syncToLevel(Level level) {
      if (level instanceof ServerLevel serverLevel) {
         syncToPlayers(serverLevel.getPlayers(p -> true));
      }
   }

   public static void syncToAll() {
      syncToPlayers(ClaimedChunkManagerImpl.getInstance().getMinecraftServer().getPlayerList().getPlayers());
   }

   public static void syncToPlayers(List<ServerPlayer> players) {
      if (players == null) {
         players = ClaimedChunkManagerImpl.getInstance().getMinecraftServer().getPlayerList().getPlayers();
      }

      List<PlayerVisibilityPacket.VisiblePlayerItem> playerList = players.stream()
         .map(player -> new PlayerVisibilityPacket.VisiblePlayerItem(player, ClaimedChunkManagerImpl.getInstance().getOrCreateData(player)))
         .toList();
      boolean override = (Boolean)FTBChunksWorldConfig.LOCATION_MODE_OVERRIDE.get();

      for (PlayerVisibilityPacket.VisiblePlayerItem recipient : playerList) {
         List<UUID> playerIds = new ArrayList<>();

         for (PlayerVisibilityPacket.VisiblePlayerItem other : playerList) {
            if (override || recipient.player.hasPermissions(2) || other.data.canPlayerUse(recipient.player, FTBChunksProperties.LOCATION_MODE)) {
               playerIds.add(other.player.getUUID());
            }
         }

         NetworkManager.sendToPlayer(recipient.player, new PlayerVisibilityPacket(playerIds));
      }
   }

   private record VisiblePlayerItem(ServerPlayer player, ChunkTeamDataImpl data) {
   }
}
