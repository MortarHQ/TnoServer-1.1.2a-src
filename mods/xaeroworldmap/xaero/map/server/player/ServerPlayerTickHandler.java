package xaero.map.server.player;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import xaero.map.server.MinecraftServerData;

public class ServerPlayerTickHandler {
   public void tick(ServerPlayer player) {
      MinecraftServer server = player.getServer();
      MinecraftServerData serverData = MinecraftServerData.get(server);
      ServerPlayerData playerData = ServerPlayerData.get(player);
      serverData.getSyncedPlayerTracker().onTick(server, player, serverData, playerData);
   }
}
