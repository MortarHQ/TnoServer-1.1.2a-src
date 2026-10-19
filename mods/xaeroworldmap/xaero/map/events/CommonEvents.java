package xaero.map.events;

import java.nio.file.Path;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;
import xaero.lib.common.config.profile.ConfigProfile;
import xaero.lib.common.config.server.ServerConfigManager;
import xaero.map.WorldMap;
import xaero.map.common.config.option.WorldMapProfiledConfigOptions;
import xaero.map.message.basic.ClientboundRulesPacket;
import xaero.map.message.basic.HandshakePacket;
import xaero.map.message.tracker.ClientboundPlayerTrackerResetPacket;
import xaero.map.server.MinecraftServerData;
import xaero.map.server.MineraftServerDataInitializer;
import xaero.map.server.level.LevelMapProperties;
import xaero.map.server.player.IServerPlayer;
import xaero.map.server.player.ServerPlayerData;

public class CommonEvents {
   protected void onPlayerClone(Player oldPlayer, Player newPlayer, boolean alive) {
      if (oldPlayer instanceof ServerPlayer oldServerPlayer) {
         ((IServerPlayer)newPlayer).setXaeroWorldMapPlayerData(ServerPlayerData.get(oldServerPlayer));
      }
   }

   public void onServerStarting(MinecraftServer server) {
      new MineraftServerDataInitializer().init(server);
   }

   public void onServerStopped(MinecraftServer server) {
   }

   public void onPlayerLogIn(Player player) {
      if (player instanceof ServerPlayer serverPlayer) {
         WorldMap.messageHandler.sendToPlayer(serverPlayer, new ClientboundPlayerTrackerResetPacket());
      }
   }

   public void onPlayerWorldJoin(ServerPlayer player) {
      WorldMap.messageHandler.sendToPlayer(player, new HandshakePacket());
      ServerConfigManager configManager = WorldMap.INSTANCE.getConfigs().getServerConfigManager();
      ConfigProfile defaultEnforcedProfile = configManager.getDefaultEnforcedProfile();
      boolean caveModeConfig = !Boolean.FALSE.equals(defaultEnforcedProfile.get(WorldMapProfiledConfigOptions.CAVE_MODE_ALLOWED));
      Set<ResourceLocation> caveModeDimensionsConfig = (Set<ResourceLocation>)defaultEnforcedProfile.get(
         WorldMapProfiledConfigOptions.CAVE_MODE_ALLOWED_DIMENSIONS
      );
      boolean allowOverworldCaveModeOnServer = caveModeConfig
         && (caveModeDimensionsConfig == null || caveModeDimensionsConfig.isEmpty() || caveModeDimensionsConfig.contains(Level.OVERWORLD.location()));
      boolean allowNetherCaveModeOnServer = caveModeConfig
         && (caveModeDimensionsConfig == null || caveModeDimensionsConfig.isEmpty() || caveModeDimensionsConfig.contains(Level.NETHER.location()));
      WorldMap.messageHandler.sendToPlayer(player, new ClientboundRulesPacket(allowOverworldCaveModeOnServer, allowNetherCaveModeOnServer));
      Path propertiesPath = player.level().getServer().getWorldPath(LevelResource.LEVEL_DATA_FILE).getParent().resolve("xaeromap.txt");

      try {
         MinecraftServerData serverData = MinecraftServerData.get(player.getServer());
         LevelMapProperties properties = serverData.getLevelProperties(propertiesPath);
         if (properties.isUsable()) {
            WorldMap.messageHandler.sendToPlayer(player, properties);
         }
      } catch (Throwable t) {
         WorldMap.LOGGER.error("suppressed exception", t);
         player.connection.disconnect(Component.translatable("gui.xaero_wm_error_loading_properties"));
      }
   }

   public void handlePlayerTickStart(Player player) {
      if (player instanceof ServerPlayer) {
         WorldMap.serverPlayerTickHandler.tick((ServerPlayer)player);
      } else {
         if (WorldMap.events != null) {
            WorldMap.events.handlePlayerTickStart(player);
         }
      }
   }
}
