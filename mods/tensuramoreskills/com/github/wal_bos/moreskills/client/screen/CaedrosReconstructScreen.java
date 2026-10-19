package com.github.wal_bos.moreskills.client.screen;

import com.github.wal_bos.moreskills.menu.CaedrosReconstructMenu;
import com.github.wal_bos.moreskills.net.CaedrosMenuActionPayload;
import dev.architectury.networking.NetworkManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class CaedrosReconstructScreen extends AbstractContainerScreen<CaedrosReconstructMenu> {
   public CaedrosReconstructScreen(CaedrosReconstructMenu menu, Inventory inventory, Component title) {
      super(menu, inventory, title);
      this.imageWidth = 300;
      this.imageHeight = 220;
      this.inventoryLabelY = 10000;
   }

   protected void init() {
      super.init();
      int left = this.leftPos;
      int top = this.topPos;
      int y = top + 42;

      for (int i = 0; i < ((CaedrosReconstructMenu)this.menu).entries().size() && i < 6; i++) {
         String entry = ((CaedrosReconstructMenu)this.menu).entries().get(i);
         this.addRenderableWidget(
            Button.builder(Component.literal("Guilt"), btn -> this.send(CaedrosMenuActionPayload.Action.BUY_BACK_GUILT, entry))
               .bounds(left + 170, y, 50, 20)
               .build()
         );
         this.addRenderableWidget(
            Button.builder(Component.literal("EP"), btn -> this.send(CaedrosMenuActionPayload.Action.BUY_BACK_EP, entry)).bounds(left + 226, y, 40, 20).build()
         );
         y += 24;
      }
   }

   private void send(CaedrosMenuActionPayload.Action action, String value) {
      NetworkManager.sendToServer(new CaedrosMenuActionPayload(action, value));
   }

   protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
      super.render(guiGraphics, mouseX, mouseY, partialTick);
      int left = this.leftPos;
      int top = this.topPos;
      guiGraphics.drawString(this.font, this.title, left + 8, top + 6, 16777215, false);
      guiGraphics.drawString(this.font, "Guilt: " + (long)((CaedrosReconstructMenu)this.menu).guilt(), left + 8, top + 18, 11158596, false);
      guiGraphics.drawString(this.font, "EP: " + (long)((CaedrosReconstructMenu)this.menu).ep(), left + 8, top + 30, 4521983, false);
      guiGraphics.drawString(this.font, "Eaten skills", left + 8, top + 42, 11184810, false);
      int y = top + 48;

      for (int i = 0; i < ((CaedrosReconstructMenu)this.menu).entries().size() && i < 6; i++) {
         guiGraphics.drawString(this.font, ((CaedrosReconstructMenu)this.menu).entries().get(i), left + 8, y + 6, 14737632, false);
         y += 24;
      }

      this.renderTooltip(guiGraphics, mouseX, mouseY);
   }
}
