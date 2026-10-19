package com.github.wal_bos.moreskills.client.screen;

import com.github.wal_bos.moreskills.menu.VoidArchiveLibraryMenu;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class VoidArchiveLibraryScreen extends AbstractContainerScreen<VoidArchiveLibraryMenu> {
   private static final int TAB_DAMAGE = 0;
   private static final int TAB_EFFECT = 1;
   private static final int TAB_ECHO = 2;
   private static final int VISIBLE_ROWS = 6;
   private final List<Button> dynamicButtons = new ArrayList<>();
   private int tab;
   private int scroll;

   public VoidArchiveLibraryScreen(VoidArchiveLibraryMenu menu, Inventory inventory, Component title) {
      super(menu, inventory, title);
      this.imageWidth = 252;
      this.imageHeight = 220;
      this.inventoryLabelY = 10000;
   }

   protected void init() {
      super.init();
      this.rebuildWidgets();
   }

   protected void containerTick() {
      super.containerTick();
      int maxScroll = Math.max(0, this.size() - 6);
      if (this.scroll > maxScroll) {
         this.scroll = maxScroll;
         this.rebuildWidgets();
      }
   }

   protected void rebuildWidgets() {
      this.clearDynamicButtons();
      int x = this.leftPos;
      int y = this.topPos;
      Button damageTab = this.addDyn(Button.builder(Component.literal("Damage"), b -> {
         this.tab = 0;
         this.scroll = 0;
         this.rebuildWidgets();
      }).bounds(x + 12, y + 12, 66, 20).build());
      damageTab.active = this.tab != 0;
      Button effectTab = this.addDyn(Button.builder(Component.literal("Effects"), b -> {
         this.tab = 1;
         this.scroll = 0;
         this.rebuildWidgets();
      }).bounds(x + 82, y + 12, 66, 20).build());
      effectTab.active = this.tab != 1;
      Button echoTab = this.addDyn(Button.builder(Component.literal("Echoes"), b -> {
         this.tab = 2;
         this.scroll = 0;
         this.rebuildWidgets();
      }).bounds(x + 152, y + 12, 66, 20).build());
      echoTab.active = this.tab != 2;
      this.addDyn(Button.builder(Component.literal("▲"), b -> {
         this.scroll = Math.max(0, this.scroll - 1);
         this.rebuildWidgets();
      }).bounds(x + 222, y + 44, 16, 16).build());
      this.addDyn(Button.builder(Component.literal("▼"), b -> {
         this.scroll = Math.min(Math.max(0, this.size() - 6), this.scroll + 1);
         this.rebuildWidgets();
      }).bounds(x + 222, y + 62, 16, 16).build());
      if (this.tab == 0) {
         this.addDyn(
            Button.builder(
                  Component.literal("Clear Damage"),
                  b -> this.minecraft.gameMode.handleInventoryButtonClick(((VoidArchiveLibraryMenu)this.menu).containerId, 9000)
               )
               .bounds(x + 142, y + 192, 96, 20)
               .build()
         );
      } else if (this.tab == 1) {
         this.addDyn(
            Button.builder(
                  Component.literal("Clear Effects"),
                  b -> this.minecraft.gameMode.handleInventoryButtonClick(((VoidArchiveLibraryMenu)this.menu).containerId, 9001)
               )
               .bounds(x + 142, y + 192, 96, 20)
               .build()
         );
      } else {
         this.addDyn(
            Button.builder(
                  Component.literal("Clear Echoes"),
                  b -> this.minecraft.gameMode.handleInventoryButtonClick(((VoidArchiveLibraryMenu)this.menu).containerId, 9002)
               )
               .bounds(x + 142, y + 192, 96, 20)
               .build()
         );
      }

      int visible = Math.min(6, Math.max(0, this.size() - this.scroll));

      for (int i = 0; i < visible; i++) {
         int actual = this.scroll + i;
         int rowY = y + 44 + i * 24;
         if (this.tab == 0) {
            this.addDyn(
               Button.builder(
                     Component.literal("Use"),
                     b -> this.minecraft.gameMode.handleInventoryButtonClick(((VoidArchiveLibraryMenu)this.menu).containerId, 1000 + actual)
                  )
                  .bounds(x + 170, rowY + 3, 28, 16)
                  .build()
            );
            this.addDyn(
               Button.builder(
                     Component.literal("X"),
                     b -> this.minecraft.gameMode.handleInventoryButtonClick(((VoidArchiveLibraryMenu)this.menu).containerId, 2000 + actual)
                  )
                  .bounds(x + 200, rowY + 3, 18, 16)
                  .build()
            );
         } else if (this.tab == 1) {
            this.addDyn(
               Button.builder(
                     Component.literal("Use"),
                     b -> this.minecraft.gameMode.handleInventoryButtonClick(((VoidArchiveLibraryMenu)this.menu).containerId, 3000 + actual)
                  )
                  .bounds(x + 170, rowY + 3, 28, 16)
                  .build()
            );
            this.addDyn(
               Button.builder(
                     Component.literal("X"),
                     b -> this.minecraft.gameMode.handleInventoryButtonClick(((VoidArchiveLibraryMenu)this.menu).containerId, 4000 + actual)
                  )
                  .bounds(x + 200, rowY + 3, 18, 16)
                  .build()
            );
         } else {
            this.addDyn(
               Button.builder(
                     Component.literal("X"),
                     b -> this.minecraft.gameMode.handleInventoryButtonClick(((VoidArchiveLibraryMenu)this.menu).containerId, 5000 + actual)
                  )
                  .bounds(x + 200, rowY + 3, 18, 16)
                  .build()
            );
         }
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

   private int size() {
      return switch (this.tab) {
         case 0 -> ((VoidArchiveLibraryMenu)this.menu).getDamageCount();
         case 1 -> ((VoidArchiveLibraryMenu)this.menu).getEffectCount();
         default -> ((VoidArchiveLibraryMenu)this.menu).getEchoCount();
      };
   }

   protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
      int x1 = this.leftPos;
      int y1 = this.topPos;
      int x2 = x1 + this.imageWidth;
      int y2 = y1 + this.imageHeight;
      g.fillGradient(x1, y1, x2, y2, -267384552, -265677250);
      g.fill(x1 + 5, y1 + 5, x2 - 5, y2 - 5, -871101404);
      g.fill(x1 + 8, y1 + 38, x1 + 218, y1 + 186, -1441394904);
      g.fill(x1 + 12, y1 + 12, x2 - 12, y1 + 30, -1440869836);
      int visible = Math.min(6, Math.max(0, this.size() - this.scroll));

      for (int i = 0; i < visible; i++) {
         int y = y1 + 44 + i * 24;
         g.fill(x1 + 12, y, x1 + 214, y + 22, -869919168);
         g.fill(x1 + 13, y + 1, x1 + 213, y + 21, 1428164657);
      }
   }

   protected void renderLabels(GuiGraphics g, int mouseX, int mouseY) {
      g.drawString(this.font, this.title, 12, 18, 15982079, false);
      int visible = Math.min(6, Math.max(0, this.size() - this.scroll));

      for (int i = 0; i < visible; i++) {
         int actual = this.scroll + i;
         int y = 48 + i * 24;
         if (this.tab == 0) {
            if (actual == ((VoidArchiveLibraryMenu)this.menu).getActiveDamageIndex()) {
               g.drawString(this.font, "◆", 14, y, 16765565, false);
            }

            g.drawString(this.font, this.trim(((VoidArchiveLibraryMenu)this.menu).getDamageEntryName(actual), 18), 28, y, 16777215, false);
            g.drawString(this.font, this.trim(((VoidArchiveLibraryMenu)this.menu).getDamageEntrySubtitle(actual), 24), 28, y + 10, 12953087, false);
         } else if (this.tab == 1) {
            if (actual == ((VoidArchiveLibraryMenu)this.menu).getActiveEffectIndex()) {
               g.drawString(this.font, "◆", 14, y, 8249599, false);
            }

            g.drawString(this.font, this.trim(((VoidArchiveLibraryMenu)this.menu).getEffectEntryName(actual), 18), 28, y, 16777215, false);
            g.drawString(this.font, this.trim(((VoidArchiveLibraryMenu)this.menu).getEffectEntrySubtitle(actual), 24), 28, y + 10, 12953087, false);
         } else {
            g.drawString(this.font, this.trim(((VoidArchiveLibraryMenu)this.menu).getEchoEntryName(actual), 18), 16, y, 16777215, false);
            g.drawString(this.font, this.trim(((VoidArchiveLibraryMenu)this.menu).getEchoEntrySubtitle(actual), 24), 16, y + 10, 12953087, false);
         }
      }

      if (this.size() == 0) {
         g.drawString(this.font, "Nothing stored here yet.", 16, 52, 15259135, false);
      }
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
