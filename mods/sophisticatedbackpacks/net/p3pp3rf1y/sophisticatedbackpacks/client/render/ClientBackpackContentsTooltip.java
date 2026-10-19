package net.p3pp3rf1y.sophisticatedbackpacks.client.render;

import java.util.UUID;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.level.LevelEvent.Load;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.network.RequestBackpackInventoryContentsPayload;
import net.p3pp3rf1y.sophisticatedcore.client.render.ClientStorageContentsTooltipBase;

public class ClientBackpackContentsTooltip extends ClientStorageContentsTooltipBase {
   private final ItemStack backpack;

   public static void onWorldLoad(Load event) {
      refreshContents();
      lastRequestTime = 0L;
   }

   public void renderImage(Font font, int leftX, int topY, GuiGraphics guiGraphics) {
      this.renderTooltip(BackpackWrapper.fromStack(this.backpack), font, leftX, topY, guiGraphics);
   }

   public ClientBackpackContentsTooltip(BackpackItem.BackpackContentsTooltip tooltip) {
      this.backpack = tooltip.getBackpack();
   }

   protected void sendInventorySyncRequest(UUID uuid) {
      PacketDistributor.sendToServer(new RequestBackpackInventoryContentsPayload(uuid), new CustomPacketPayload[0]);
   }
}
