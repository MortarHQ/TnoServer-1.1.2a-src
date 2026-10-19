package dev.ftb.mods.ftbchunks;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftbchunks.api.FTBChunksProperties;
import dev.ftb.mods.ftbchunks.data.ChunkTeamDataImpl;
import dev.ftb.mods.ftbchunks.data.ClaimedChunkManagerImpl;
import dev.ftb.mods.ftbchunks.net.SendPlayerPositionPacket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public enum LongRangePlayerTracker {
   INSTANCE;

   private long lastTick = 0L;
   private final Table<UUID, UUID, BlockPos> trackingMap = HashBasedTable.create();

   public void tick(MinecraftServer server) {
      int interval = (Integer)FTBChunksWorldConfig.LONG_RANGE_TRACKER_INTERVAL.get();
      if (interval != 0 && server.getTickCount() - this.lastTick > interval) {
         this.lastTick = server.getTickCount();
         List<ServerPlayer> players = server.getPlayerList().getPlayers();
         int maxDistSq = server.getPlayerList().getViewDistance() * server.getPlayerList().getViewDistance() * 256;
         players.forEach(trackingPlayer -> trackingPlayer.level().players().forEach(p2a -> {
            if (p2a instanceof ServerPlayer trackedPlayer) {
               if (this.shouldTrack(trackingPlayer, trackedPlayer, maxDistSq)) {
                  BlockPos lastPos = (BlockPos)this.trackingMap.get(trackingPlayer.getUUID(), trackedPlayer.getUUID());
                  if (lastPos == null || trackedPlayer.blockPosition().distSqr(lastPos) > 16.0) {
                     NetworkManager.sendToPlayer(trackingPlayer, SendPlayerPositionPacket.startTracking(trackedPlayer));
                     this.trackingMap.put(trackingPlayer.getUUID(), trackedPlayer.getUUID(), trackedPlayer.blockPosition());
                  }
               } else if (this.trackingMap.contains(trackingPlayer.getUUID(), trackedPlayer.getUUID())) {
                  NetworkManager.sendToPlayer(trackingPlayer, SendPlayerPositionPacket.stopTracking(trackedPlayer));
                  this.trackingMap.remove(trackingPlayer.getUUID(), trackedPlayer.getUUID());
               }
            }
         }));
      }
   }

   public void stopTracking(ServerPlayer player) {
      if (player.getServer() != null) {
         Map<UUID, UUID> toRemove = new HashMap<>();

         for (UUID trackingId : this.trackingMap.rowKeySet()) {
            if (this.trackingMap.contains(trackingId, player.getUUID())) {
               toRemove.put(trackingId, player.getUUID());
            }
         }

         toRemove.forEach((trackingIdx, disconnectedId) -> {
            ServerPlayer trackingPlayer = player.getServer().getPlayerList().getPlayer(trackingIdx);
            if (trackingPlayer != null) {
               NetworkManager.sendToPlayer(trackingPlayer, SendPlayerPositionPacket.stopTracking(player));
            }

            this.trackingMap.remove(trackingIdx, disconnectedId);
         });
      }
   }

   private boolean shouldTrack(ServerPlayer p1, ServerPlayer p2, int maxDistSq) {
      if (p1 == p2 || p1.distanceToSqr(p2) < maxDistSq) {
         return false;
      }

      if ((Boolean)FTBChunksWorldConfig.LOCATION_MODE_OVERRIDE.get()) {
         return true;
      }

      ChunkTeamDataImpl p2Team = ClaimedChunkManagerImpl.getInstance().getOrCreateData(p2);
      return p2Team != null && p2Team.canPlayerUse(p1, FTBChunksProperties.LOCATION_MODE);
   }
}
