package com.github.wal_bos.moreskills.client.screen;

import com.github.wal_bos.moreskills.menu.CaedrosAspectMenu;
import com.github.wal_bos.moreskills.net.CaedrosMenuActionPayload;
import dev.architectury.networking.NetworkManager;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class CaedrosAspectScreen extends AbstractContainerScreen<CaedrosAspectMenu> {
   private static final int ENTRY_BUTTON_WIDTH = 118;
   private static final int ENTRY_BUTTON_HEIGHT = 20;
   private static final int VISIBLE_ENTRY_COUNT = 6;
   private final List<AbstractWidget> entryWidgets = new ArrayList<>();
   private int scrollOffset = 0;
   private Button scrollUpButton;
   private Button scrollDownButton;

   public CaedrosAspectScreen(CaedrosAspectMenu menu, Inventory inventory, Component title) {
      super(menu, inventory, title);
      this.imageWidth = 266;
      this.imageHeight = 236;
      this.inventoryLabelY = 10000;
      this.titleLabelY = 10000;
   }

   protected void init() {
      super.init();
      int left = this.leftPos;
      int top = this.topPos;
      int buttonWidth = 96;
      int buttonHeight = 20;
      this.addRenderableWidget(
         Button.builder(Component.literal("Control"), btn -> this.send(CaedrosMenuActionPayload.Action.SET_ASPECT, "control"))
            .bounds(left + 14, top + 34, buttonWidth, buttonHeight)
            .build()
      );
      this.addRenderableWidget(
         Button.builder(Component.literal("Famine"), btn -> this.send(CaedrosMenuActionPayload.Action.SET_ASPECT, "famine"))
            .bounds(left + 14, top + 58, buttonWidth, buttonHeight)
            .build()
      );
      this.addRenderableWidget(
         Button.builder(Component.literal("War"), btn -> this.send(CaedrosMenuActionPayload.Action.SET_ASPECT, "war"))
            .bounds(left + 14, top + 82, buttonWidth, buttonHeight)
            .build()
      );
      this.addRenderableWidget(
         Button.builder(Component.literal("Death"), btn -> this.send(CaedrosMenuActionPayload.Action.SET_ASPECT, "death"))
            .bounds(left + 14, top + 106, buttonWidth, buttonHeight)
            .build()
      );
      this.addRenderableWidget(
         Button.builder(Component.literal("Clear Aspect"), btn -> this.send(CaedrosMenuActionPayload.Action.CLEAR_ASPECT, ""))
            .bounds(left + 14, top + 144, buttonWidth, buttonHeight)
            .build()
      );
      this.addRenderableWidget(
         Button.builder(Component.literal("Clear Lock"), btn -> this.send(CaedrosMenuActionPayload.Action.CLEAR_LOCK, ""))
            .bounds(left + 14, top + 168, buttonWidth, buttonHeight)
            .build()
      );
      this.scrollUpButton = (Button)this.addRenderableWidget(Button.builder(Component.literal("▲"), btn -> {
         this.scrollOffset = Math.max(0, this.scrollOffset - 1);
         this.rebuildEntryButtons();
      }).bounds(left + 236, top + 34, 18, 20).build());
      this.scrollDownButton = (Button)this.addRenderableWidget(Button.builder(Component.literal("▼"), btn -> {
         this.scrollOffset = Math.min(this.getMaxScroll(), this.scrollOffset + 1);
         this.rebuildEntryButtons();
      }).bounds(left + 236, top + 158, 18, 20).build());
      this.rebuildEntryButtons();
   }

   private void rebuildEntryButtons() {
      for (AbstractWidget widget : this.entryWidgets) {
         this.removeWidget(widget);
      }

      this.entryWidgets.clear();
      int left = this.leftPos;
      int top = this.topPos;
      int start = this.scrollOffset;
      int end = Math.min(((CaedrosAspectMenu)this.menu).entries().size(), start + 6);
      int y = top + 34;

      for (int i = start; i < end; i++) {
         String entry = ((CaedrosAspectMenu)this.menu).entries().get(i);
         String display = this.shortenEntry(entry);
         Button button = Button.builder(Component.literal(display), btn -> this.send(CaedrosMenuActionPayload.Action.LOCK_SKILL, entry))
            .bounds(left + 116, y, 118, 20)
            .build();
         this.entryWidgets.add(button);
         this.addRenderableWidget(button);
         y += 20;
      }

      this.scrollUpButton.active = this.scrollOffset > 0;
      this.scrollDownButton.active = this.scrollOffset < this.getMaxScroll();
   }

   private int getMaxScroll() {
      return Math.max(0, ((CaedrosAspectMenu)this.menu).entries().size() - 6);
   }

   private String shortenEntry(String entry) {
      String raw = entry;
      int colon = raw.indexOf(58);
      if (colon >= 0 && colon + 1 < raw.length()) {
         raw = raw.substring(colon + 1);
      }

      if (raw.length() > 18) {
         raw = raw.substring(0, 18) + "...";
      }

      return raw;
   }

   private String getCurrentLockPreview() {
      if (((CaedrosAspectMenu)this.menu).entries().isEmpty()) {
         return "No valid skills";
      }

      int index = Mth.clamp(this.scrollOffset, 0, ((CaedrosAspectMenu)this.menu).entries().size() - 1);
      String entry = ((CaedrosAspectMenu)this.menu).entries().get(index);
      return entry.length() > 30 ? entry.substring(0, 30) + "..." : entry;
   }

   private void send(CaedrosMenuActionPayload.Action action, String value) {
      NetworkManager.sendToServer(new CaedrosMenuActionPayload(action, value));
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
      int listLeft = this.leftPos + 116;
      int listTop = this.topPos + 34;
      int listRight = listLeft + 118;
      int listBottom = listTop + 120;
      if (mouseX >= listLeft && mouseX <= listRight && mouseY >= listTop && mouseY <= listBottom) {
         int oldOffset = this.scrollOffset;
         if (scrollY > 0.0) {
            this.scrollOffset = Math.max(0, this.scrollOffset - 1);
         } else if (scrollY < 0.0) {
            this.scrollOffset = Math.min(this.getMaxScroll(), this.scrollOffset + 1);
         }

         if (oldOffset != this.scrollOffset) {
            this.rebuildEntryButtons();
            return true;
         }
      }

      return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
   }

   protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
      int left = this.leftPos;
      int top = this.topPos;
      int right = left + this.imageWidth;
      int bottom = top + this.imageHeight;
      guiGraphics.fillGradient(left, top, right, bottom, -535818224, -266856424);
      guiGraphics.fill(left + 4, top + 4, right - 4, bottom - 4, -870510828);
      guiGraphics.fill(left + 8, top + 26, left + 108, bottom - 44, -1440081888);
      guiGraphics.fill(left + 112, top + 26, right - 14, top + 182, -1441129948);
      guiGraphics.fill(left + 8, top + 8, right - 8, top + 22, -1438970087);
      guiGraphics.fill(left + 8, bottom - 38, right - 8, bottom - 10, -1441261536);
      guiGraphics.fill(left + 112, top + 26, right - 14, top + 27, -43691);
      guiGraphics.fill(left + 112, top + 147, right - 14, top + 148, 1442840575);
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
      super.render(guiGraphics, mouseX, mouseY, partialTick);
      int left = this.leftPos;
      int top = this.topPos;

      String current = switch (((CaedrosAspectMenu)this.menu).currentAspectOrdinal()) {
         case 0 -> "CONTROL";
         case 1 -> "FAMINE";
         case 2 -> "WAR";
         case 3 -> "DEATH";
         default -> "OFF";
      };
      guiGraphics.drawString(this.font, "Caedros Aspects", left + 82, top + 11, 16777215, false);
      guiGraphics.drawString(this.font, "Aspects", left + 14, top + 15, 16758968, false);
      guiGraphics.drawString(this.font, "Skill Lock", left + 116, top + 15, 16758968, false);
      if (((CaedrosAspectMenu)this.menu).entries().size() > 6) {
         String range = this.scrollOffset + 1 + "-" + Math.min(((CaedrosAspectMenu)this.menu).entries().size(), this.scrollOffset + 6);
         guiGraphics.drawString(this.font, range, left + 205, top + 15, 11513775, false);
      }

      guiGraphics.drawString(this.font, "Current:", left + 14, top + 205, 13684944, false);
      guiGraphics.drawString(this.font, current, left + 58, top + 205, 16777215, false);
      guiGraphics.drawString(this.font, "Entries: " + ((CaedrosAspectMenu)this.menu).entries().size(), left + 116, top + 196, 13684944, false);
      guiGraphics.drawString(this.font, "Preview:", left + 116, top + 208, 13684944, false);
      guiGraphics.drawString(this.font, this.getCurrentLockPreview(), left + 163, top + 208, 11513775, false);
      this.renderTooltip(guiGraphics, mouseX, mouseY);
   }
}
