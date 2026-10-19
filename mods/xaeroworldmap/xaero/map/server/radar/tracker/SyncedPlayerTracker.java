package xaero.map.server.radar.tracker;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import xaero.lib.XaeroLib;
import xaero.lib.common.config.Config;
import xaero.lib.common.config.primary.option.LibPrimaryCommonConfigOptions;
import xaero.lib.common.config.single.SingleConfigManager;
import xaero.map.WorldMap;
import xaero.map.message.tracker.ClientboundTrackedPlayerPacket;
import xaero.map.server.MinecraftServerData;
import xaero.map.server.mods.SupportServerMods;
import xaero.map.server.player.ServerPlayerData;

public class SyncedPlayerTracker {
   public void onTick(MinecraftServer server, ServerPlayer player, MinecraftServerData serverData, ServerPlayerData playerData) {
      long currentTime = System.currentTimeMillis();
      if (currentTime - playerData.getLastTrackedPlayerSync() >= 250L) {
         playerData.setLastTrackedPlayerSync(currentTime);
         boolean playerHasMod = playerData.hasMod();
         boolean shouldSyncToPlayer = playerHasMod;
         if (SupportServerMods.hasMinimap()
            && SupportServerMods.getMinimap().supportsTrackedPlayers()
            && SupportServerMods.getMinimap().playerSupportsTrackedPlayers(player)) {
            if (playerData.getCurrentlySyncedPlayers() != null && !playerData.getCurrentlySyncedPlayers().isEmpty()) {
               for (UUID id : playerData.getCurrentlySyncedPlayers()) {
                  this.sendRemovePacket(player, id);
               }

               playerData.getCurrentlySyncedPlayers().clear();
            }

            shouldSyncToPlayer = false;
         }

         SingleConfigManager<Config> primaryCommonConfig = WorldMap.INSTANCE.getConfigs().getPrimaryCommonConfigManager();
         SingleConfigManager<Config> libPrimaryCommonConfig = XaeroLib.INSTANCE.getLibConfigChannel().getPrimaryCommonConfigManager();
         boolean everyoneIsTracked = (Boolean)libPrimaryCommonConfig.getEffective(LibPrimaryCommonConfigOptions.EVERYONE_TRACKS_EVERYONE);
         Iterable<ISyncedPlayerTrackerSystem> playerTrackerSystems = serverData.getSyncedPlayerTrackerSystemManager().getSystems();
         Set<UUID> syncedPlayers = playerData.ensureCurrentlySyncedPlayers();
         Set<UUID> leftoverPlayers = new HashSet<>(syncedPlayers);
         SyncedTrackedPlayer toSync = playerData.getLastSyncedData();
         boolean shouldSyncToOthers = toSync == null || !toSync.matchesEnough(player, 0.0);
         if (shouldSyncToOthers) {
            toSync = playerData.ensureLastSyncedData();
            toSync.update(player);
         }

         boolean opacReceiveParty = shouldSyncToPlayer
            && SupportServerMods.hasOpac()
            && SupportServerMods.getOpac().getReceiveLocationsFromPartyConfigValue(player);
         boolean opacReceiveMutualAllies = shouldSyncToPlayer
            && SupportServerMods.hasOpac()
            && SupportServerMods.getOpac().getReceiveLocationsFromMutualAlliesConfigValue(player);
         if (SupportServerMods.hasOpac()) {
            SupportServerMods.getOpac().updateShareLocationConfigValues(player, playerData);
         }

         for (ServerPlayer otherPlayer : server.getPlayerList().getPlayers()) {
            if (otherPlayer != player) {
               leftoverPlayers.remove(otherPlayer.getUUID());
               ServerPlayerData otherPlayerData = ServerPlayerData.get(otherPlayer);
               if (shouldSyncToOthers) {
                  Set<UUID> otherPlayerSyncedPlayers = otherPlayerData.getCurrentlySyncedPlayers();
                  if (otherPlayerSyncedPlayers != null && otherPlayerSyncedPlayers.contains(player.getUUID())) {
                     this.sendTrackedPlayerPacket(otherPlayer, toSync);
                  }
               }

               if (shouldSyncToPlayer) {
                  boolean tracked = everyoneIsTracked;
                  if (!tracked) {
                     boolean opacConfigsAllowPartySync = !SupportServerMods.hasOpac()
                        || SupportServerMods.getOpac().isPositionSyncAllowed(2, otherPlayerData, opacReceiveParty);
                     boolean opacConfigsAllowAllySync = !SupportServerMods.hasOpac()
                        || SupportServerMods.getOpac().isPositionSyncAllowed(1, otherPlayerData, opacReceiveMutualAllies);

                     for (ISyncedPlayerTrackerSystem system : playerTrackerSystems) {
                        int trackingLevel = system.getTrackingLevel(player, otherPlayer);
                        if (trackingLevel > 0
                           && (!system.isPartySystem() || trackingLevel == 1 && opacConfigsAllowAllySync || trackingLevel > 1 && opacConfigsAllowPartySync)) {
                           tracked = true;
                           break;
                        }
                     }
                  }

                  boolean alreadySynced = syncedPlayers.contains(otherPlayer.getUUID());
                  if (!tracked) {
                     if (alreadySynced) {
                        syncedPlayers.remove(otherPlayer.getUUID());
                        this.sendRemovePacket(player, otherPlayer.getUUID());
                     }
                  } else if (!alreadySynced && otherPlayerData.getLastSyncedData() != null) {
                     syncedPlayers.add(otherPlayer.getUUID());
                     this.sendTrackedPlayerPacket(player, otherPlayerData.getLastSyncedData());
                  }
               }
            }
         }

         for (UUID offlineId : leftoverPlayers) {
            syncedPlayers.remove(offlineId);
            this.sendRemovePacket(player, offlineId);
         }
      }
   }

   private void sendRemovePacket(ServerPlayer player, UUID toRemove) {
      WorldMap.messageHandler.sendToPlayer(player, new ClientboundTrackedPlayerPacket(true, toRemove, 0.0, 0.0, 0.0, null));
   }

   private void sendTrackedPlayerPacket(ServerPlayer player, SyncedTrackedPlayer tracked) {
      WorldMap.messageHandler
         .sendToPlayer(
            player,
            new ClientboundTrackedPlayerPacket(false, tracked.getId(), tracked.getX(), tracked.getY(), tracked.getZ(), tracked.getDimension().location())
         );
   }
}
