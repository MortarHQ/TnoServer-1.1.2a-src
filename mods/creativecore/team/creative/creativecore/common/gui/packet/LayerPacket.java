package team.creative.creativecore.common.gui.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import team.creative.creativecore.common.gui.integration.IGuiIntegratedParent;
import team.creative.creativecore.common.network.CreativePacket;

public abstract class LayerPacket extends CreativePacket {
   @Override
   public void executeClient(Player player) {
      if (player != null && player.containerMenu instanceof IGuiIntegratedParent) {
         this.execute(player, (IGuiIntegratedParent)player.containerMenu);
      } else if (Minecraft.getInstance().screen instanceof IGuiIntegratedParent) {
         this.execute(player, (IGuiIntegratedParent)Minecraft.getInstance().screen);
      }
   }

   @Override
   public void executeServer(ServerPlayer player) {
      if (player.containerMenu instanceof IGuiIntegratedParent) {
         this.execute(player, (IGuiIntegratedParent)player.containerMenu);
      }
   }

   public abstract void execute(Player var1, IGuiIntegratedParent var2);
}
