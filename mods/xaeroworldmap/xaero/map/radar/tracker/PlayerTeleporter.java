package xaero.map.radar.tracker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import xaero.map.WorldMap;
import xaero.map.world.MapWorld;

public class PlayerTeleporter {
   public void teleport(Screen screen, MapWorld mapWorld, String name, int x, int y, int z) {
      Minecraft.getInstance().setScreen(null);
      String tpCommand = mapWorld.getEffectivePlayerTeleportCommandFormat();
      tpCommand = tpCommand.replace("{name}", name).replace("{x}", x + "").replace("{y}", y + "").replace("{z}", z + "");
      Minecraft mc = Minecraft.getInstance();
      if (tpCommand.startsWith("/")) {
         tpCommand = tpCommand.substring(1);
         if (!mc.player.connection.sendUnsignedCommand(tpCommand)) {
            mc.player.connection.sendCommand(tpCommand);
         }
      } else {
         mc.player.connection.sendChat(tpCommand);
      }
   }

   public void teleportToPlayer(Screen screen, MapWorld mapWorld, PlayerTrackerMapElement<?> target) {
      this.teleport(
         screen,
         mapWorld,
         WorldMap.trackedPlayerRenderer.getReader().getMenuName(target),
         (int)Math.floor(target.getX()),
         (int)Math.floor(target.getY()),
         (int)Math.floor(target.getZ())
      );
   }
}
