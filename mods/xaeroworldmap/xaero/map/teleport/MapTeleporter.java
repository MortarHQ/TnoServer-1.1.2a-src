package xaero.map.teleport;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import xaero.lib.client.config.ClientConfigManager;
import xaero.map.WorldMap;
import xaero.map.common.config.option.WorldMapProfiledConfigOptions;
import xaero.map.world.MapConnectionNode;
import xaero.map.world.MapDimension;
import xaero.map.world.MapWorld;

public class MapTeleporter {
   public void teleport(Screen screen, MapWorld mapWorld, int x, int y, int z, ResourceKey<Level> d) {
      Minecraft.getInstance().setScreen(null);
      if (Minecraft.getInstance().gameMode.canHurtPlayer()) {
         MapDimension destinationDim = mapWorld.getDimension(d != null ? d : Minecraft.getInstance().level.dimension());
         MapConnectionNode playerMapKey = mapWorld.getPlayerMapKey();
         if (playerMapKey == null) {
            MutableComponent messageComponent = Component.literal(I18n.get("gui.xaero_wm_teleport_never_confirmed", new Object[0]));
            messageComponent.setStyle(messageComponent.getStyle().withColor(ChatFormatting.RED));
            Minecraft.getInstance().gui.getChat().addMessage(messageComponent);
            return;
         }

         MapConnectionNode destinationMapKey = destinationDim == null ? null : destinationDim.getSelectedMapKeyUnsynced();
         if (!mapWorld.getMapConnections().isConnected(playerMapKey, destinationMapKey)) {
            MutableComponent messageComponent = Component.literal(I18n.get("gui.xaero_wm_teleport_not_connected", new Object[0]));
            messageComponent.setStyle(messageComponent.getStyle().withColor(ChatFormatting.RED));
            Minecraft.getInstance().gui.getChat().addMessage(messageComponent);
            return;
         }
      }

      ClientConfigManager configManager = WorldMap.INSTANCE.getConfigs().getClientConfigManager();
      boolean partialYTeleportConfig = (Boolean)configManager.getEffective(WorldMapProfiledConfigOptions.PARTIAL_Y_TELEPORT);
      String tpCommand = d == null ? mapWorld.getEffectiveTeleportCommandFormat() : mapWorld.getEffectiveDimensionTeleportCommandFormat();
      String yString = y == 32767 ? "~" : (partialYTeleportConfig ? y + 0.5 + "" : y + "");
      tpCommand = tpCommand.replace("{x}", x + "").replace("{y}", yString).replace("{z}", z + "");
      if (d != null) {
         tpCommand = tpCommand.replace("{d}", d.location().toString());
      }

      Minecraft mc = Minecraft.getInstance();
      if (tpCommand.startsWith("/")) {
         tpCommand = tpCommand.substring(1);
         if (!mc.player.connection.sendUnsignedCommand(tpCommand)) {
            mc.player.connection.sendCommand(tpCommand);
         }
      } else {
         mc.player.connection.sendChat(tpCommand);
      }

      mapWorld.setCustomDimensionId(null);
      mapWorld.getMapProcessor().checkForWorldUpdate();
   }
}
