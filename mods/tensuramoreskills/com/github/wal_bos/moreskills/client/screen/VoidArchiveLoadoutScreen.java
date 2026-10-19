package com.github.wal_bos.moreskills.client.screen;

import com.github.wal_bos.moreskills.menu.VoidArchiveLoadoutMenu;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class VoidArchiveLoadoutScreen extends AbstractContainerScreen<VoidArchiveLoadoutMenu> {
   private static final int VISIBLE_ROWS = 6;
   private final List<Button> dynamicButtons = new ArrayList<>();
   private int scroll;

   public VoidArchiveLoadoutScreen(VoidArchiveLoadoutMenu menu, Inventory inventory, Component title) {
      super(menu, inventory, title);
      this.imageWidth = 300;
      this.imageHeight = 222;
      this.inventoryLabelY = 10000;
   }

   protected void init() {
      super.init();
      this.rebuildWidgets();
   }

   protected void containerTick() {
      super.containerTick();
      int maxScroll = Math.max(0, ((VoidArchiveLoadoutMenu)this.menu).getMoveCount() - 6);
      if (this.scroll > maxScroll) {
         this.scroll = maxScroll;
         this.rebuildWidgets();
      }
   }

   protected void rebuildWidgets() {
      this.clearDynamicButtons();
      int maxScroll = Math.max(0, ((VoidArchiveLoadoutMenu)this.menu).getMoveCount() - 6);
      this.addDyn(Button.builder(Component.literal("▲"), b -> {
         this.scroll = Math.max(0, this.scroll - 1);
         this.rebuildWidgets();
      }).bounds(this.leftPos + 274, this.topPos + 44, 16, 16).build());
      this.addDyn(Button.builder(Component.literal("▼"), b -> {
         this.scroll = Math.min(maxScroll, this.scroll + 1);
         this.rebuildWidgets();
      }).bounds(this.leftPos + 274, this.topPos + 64, 16, 16).build());
      int visible = Math.min(6, Math.max(0, ((VoidArchiveLoadoutMenu)this.menu).getMoveCount() - this.scroll));

      for (int i = 0; i < visible; i++) {
         int actual = this.scroll + i;
         int rowY = this.topPos + 44 + i * 26;
         this.addDyn(
            Button.builder(
                  Component.literal("Use"),
                  b -> this.minecraft.gameMode.handleInventoryButtonClick(((VoidArchiveLoadoutMenu)this.menu).containerId, 1000 + actual)
               )
               .bounds(this.leftPos + 196, rowY + 3, 30, 18)
               .build()
         );
         if (((VoidArchiveLoadoutMenu)this.menu).isEquipped(actual)) {
            this.addDyn(
               Button.builder(
                     Component.literal("Out"),
                     b -> this.minecraft.gameMode.handleInventoryButtonClick(((VoidArchiveLoadoutMenu)this.menu).containerId, 3000 + actual)
                  )
                  .bounds(this.leftPos + 229, rowY + 3, 30, 18)
                  .build()
            );
         } else {
            this.addDyn(
               Button.builder(
                     Component.literal("In"),
                     b -> this.minecraft.gameMode.handleInventoryButtonClick(((VoidArchiveLoadoutMenu)this.menu).containerId, 2000 + actual)
                  )
                  .bounds(this.leftPos + 229, rowY + 3, 30, 18)
                  .build()
            );
         }

         this.addDyn(
            Button.builder(
                  Component.literal("X"),
                  b -> this.minecraft.gameMode.handleInventoryButtonClick(((VoidArchiveLoadoutMenu)this.menu).containerId, 4000 + actual)
               )
               .bounds(this.leftPos + 262, rowY + 3, 18, 18)
               .build()
         );
      }
   }

   private Button addDyn(Button button) {
      this.dynamicButtons.add(button);
      return (Button)this.addRenderableWidget(button);
   }

   private void clearDynamicButtons() {
      for (Button button : this.dynamicButtons) {
         this.removeWidget(button);
      }

      this.dynamicButtons.clear();
   }

   protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
      int x1 = this.leftPos;
      int y1 = this.topPos;
      int x2 = x1 + this.imageWidth;
      int y2 = y1 + this.imageHeight;
      g.fillGradient(x1, y1, x2, y2, -267384552, -265611201);
      g.fill(x1 + 5, y1 + 5, x2 - 5, y2 - 5, -871101404);
      g.fill(x1 + 8, y1 + 34, x2 - 8, y2 - 10, -1726345172);
      g.fill(x1 + 12, y1 + 12, x2 - 12, y1 + 30, -1440804300);
      int visible = Math.min(6, Math.max(0, ((VoidArchiveLoadoutMenu)this.menu).getMoveCount() - this.scroll));

      for (int i = 0; i < visible; i++) {
         int y = y1 + 44 + i * 26;
         g.fill(x1 + 12, y, x1 + 284, y + 23, -869919168);
         g.fill(x1 + 13, y + 1, x1 + 283, y + 22, 1428164657);
      }
   }

   protected void renderLabels(GuiGraphics g, int mouseX, int mouseY) {
      g.drawString(this.font, this.title, 12, 16, 15982079, false);
      g.drawString(this.font, "Forged Move Loadout", 12, 28, 13019391, false);
      int visible = Math.min(6, Math.max(0, ((VoidArchiveLoadoutMenu)this.menu).getMoveCount() - this.scroll));

      for (int i = 0; i < visible; i++) {
         int actual = this.scroll + i;
         int y = 49 + i * 26;
         if (actual == ((VoidArchiveLoadoutMenu)this.menu).getActiveMoveIndex()) {
            g.drawString(this.font, "◆", 16, y, 16765565, false);
         }

         if (((VoidArchiveLoadoutMenu)this.menu).isEquipped(actual)) {
            g.drawString(this.font, "■", 28, y, 8249599, false);
         }

         g.drawString(this.font, this.trim(((VoidArchiveLoadoutMenu)this.menu).getMoveName(actual), 18), 42, y, 16777215, false);
         g.drawString(this.font, this.trim(((VoidArchiveLoadoutMenu)this.menu).getMoveSubtitle(actual), 30), 42, y + 11, 13085183, false);
      }

      if (((VoidArchiveLoadoutMenu)this.menu).getMoveCount() <= 0) {
         g.drawString(this.font, "No forged moves yet.", 16, 56, 15784191, false);
         g.drawString(this.font, "Open Move Forge and create one first.", 16, 70, 12953087, false);
      }

      g.drawString(this.font, "Active ◆   Equipped ■", 12, 202, 12559602, false);
   }

   private Component trim(Component component, int maxChars) {
      String raw = component.getString();
      return (Component)(raw.length() <= maxChars ? component : Component.literal(raw.substring(0, Mth.clamp(maxChars - 3, 0, raw.length())) + "..."));
   }

   public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
      this.renderBackground(g, mouseX, mouseY, partialTick);
      super.render(g, mouseX, mouseY, partialTick);
      this.renderTooltip(g, mouseX, mouseY);
   }
}
