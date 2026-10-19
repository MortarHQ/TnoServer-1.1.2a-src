package com.github.wal_bos.moreskills.client.screen;

import com.github.wal_bos.moreskills.gene.GeneEditorMenu;
import com.github.wal_bos.moreskills.item.GeneticSyringeItem;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class GeneEditorScreen extends AbstractContainerScreen<GeneEditorMenu> {
   private static final int ARCHIVE_X = 8;
   private static final int ARCHIVE_Y = 36;
   private static final int DETAILS_X = 204;
   private static final int DETAILS_Y = 36;
   private static final int SYRINGE_X = 346;
   private static final int SYRINGE_Y = 36;
   private static final int WORKSHOP_X = 8;
   private static final int WORKSHOP_Y = 154;
   private static final int HOTBAR_X = 147;
   private static final int HOTBAR_Y = 214;
   private final Button[] selects = new Button[5];
   private Button prev;
   private Button next;
   private Button add;
   private Button remove;
   private Button clear;
   private Button write;
   private EditBox amountBox;

   public GeneEditorScreen(GeneEditorMenu menu, Inventory inv, Component title) {
      super(menu, inv, title);
      this.imageWidth = 456;
      this.imageHeight = 236;
      this.inventoryLabelY = 10000;
      this.titleLabelY = 10000;
   }

   protected void init() {
      super.init();
      int x = this.leftPos;
      int y = this.topPos;
      this.prev = (Button)this.addRenderableWidget(Button.builder(Component.literal("<"), b -> this.pressSimple(1)).bounds(x + 12, y + 14, 20, 20).build());
      this.next = (Button)this.addRenderableWidget(Button.builder(Component.literal(">"), b -> this.pressSimple(2)).bounds(x + 38, y + 14, 20, 20).build());

      for (int i = 0; i < this.selects.length; i++) {
         int id = 40 + i;
         int by = y + 36 + 26 + i * 18;
         this.selects[i] = (Button)this.addRenderableWidget(
            Button.builder(Component.literal(""), b -> this.pressSimple(id)).bounds(x + 8 + 8, by, 170, 16).build()
         );
      }

      this.amountBox = (EditBox)this.addRenderableWidget(new EditBox(this.font, x + 204 + 8, y + 36 + 74, 116, 18, Component.literal("Amount")));
      this.amountBox.setMaxLength(18);
      this.amountBox.setValue("1000");
      this.amountBox
         .setFilter(
            value -> value.isEmpty()
               || value.equalsIgnoreCase("max")
               || value.equalsIgnoreCase("all")
               || value.equalsIgnoreCase("inf")
               || value.matches("[0-9]{0,18}")
         );
      this.add = (Button)this.addRenderableWidget(
         Button.builder(Component.literal("ADD"), b -> this.pressAdd()).bounds(x + 204 + 8, y + 36 + 96, 56, 18).build()
      );
      this.remove = (Button)this.addRenderableWidget(
         Button.builder(Component.literal("REM"), b -> this.pressRemove()).bounds(x + 204 + 68, y + 36 + 96, 56, 18).build()
      );
      this.write = (Button)this.addRenderableWidget(
         Button.builder(Component.literal("WRITE"), b -> this.pressSimple(6)).bounds(x + 346 + 10, y + 36 + 72, 86, 18).build()
      );
      this.clear = (Button)this.addRenderableWidget(
         Button.builder(Component.literal("CLEAR"), b -> this.pressSimple(5)).bounds(x + 346 + 10, y + 36 + 94, 86, 18).build()
      );
      this.refreshButtons();
   }

   private void pressSimple(int id) {
      ((GeneEditorMenu)this.menu).clientSelectPageButton(id);
      this.refreshButtons();
      if (this.minecraft != null && this.minecraft.gameMode != null) {
         this.minecraft.gameMode.handleInventoryButtonClick(((GeneEditorMenu)this.menu).containerId, id);
      }
   }

   private void pressAdd() {
      int id = ((GeneEditorMenu)this.menu).selectedIsStat() ? GeneEditorMenu.encodeAddAmount(this.parseAmount()) : 3;
      this.pressSimple(id);
   }

   private void pressRemove() {
      int id = ((GeneEditorMenu)this.menu).selectedIsStat() ? GeneEditorMenu.encodeRemoveAmount(this.parseAmount()) : 4;
      this.pressSimple(id);
   }

   private int parseAmount() {
      String raw = this.amountBox == null ? "1" : this.amountBox.getValue().trim();
      if (raw.equalsIgnoreCase("max") || raw.equalsIgnoreCase("all") || raw.equalsIgnoreCase("inf")) {
         return Integer.MAX_VALUE;
      }

      if (raw.isEmpty()) {
         return 1;
      }

      try {
         long value = Long.parseLong(raw);
         return value >= 999000000L ? Integer.MAX_VALUE : (int)Math.max(1L, value);
      } catch (Exception ignored) {
         return 1;
      }
   }

   private void refreshButtons() {
      List<String> page = ((GeneEditorMenu)this.menu).getKeysPage();

      for (int i = 0; i < this.selects.length; i++) {
         if (i < page.size()) {
            this.selects[i].visible = true;
            this.selects[i].setMessage(this.fit(Component.literal(((GeneEditorMenu)this.menu).displayName(page.get(i))), 160));
         } else {
            this.selects[i].visible = false;
            this.selects[i].setMessage(Component.literal(""));
         }
      }

      this.prev.active = ((GeneEditorMenu)this.menu).getPage() > 0;
      this.next.active = ((GeneEditorMenu)this.menu).getPage() < ((GeneEditorMenu)this.menu).getMaxPage();
      boolean hasSelected = !((GeneEditorMenu)this.menu).getSelectedKey().isEmpty();
      this.add.active = hasSelected;
      this.remove.active = hasSelected;
      if (this.amountBox != null) {
         this.amountBox.setEditable(((GeneEditorMenu)this.menu).selectedIsStat());
      }

      ItemStack syringe = ((GeneEditorMenu)this.menu).getSyringeStack();
      boolean syringeOk = !syringe.isEmpty() && syringe.getItem() instanceof GeneticSyringeItem;
      this.write.active = syringeOk && ((GeneEditorMenu)this.menu).getWorkshopSize() > 0;
      this.clear.active = ((GeneEditorMenu)this.menu).getWorkshopSize() > 0;
   }

   protected void containerTick() {
      super.containerTick();
      this.refreshButtons();
   }

   protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
      int x = this.leftPos;
      int y = this.topPos;
      graphics.fill(x, y, x + this.imageWidth, y + this.imageHeight, -536541936);
      graphics.fill(x + 4, y + 4, x + this.imageWidth - 4, y + this.imageHeight - 4, -804187876);
      int tick = this.minecraft != null && this.minecraft.player != null ? this.minecraft.player.tickCount : 0;
      int pulse = mix(-8497665, -11403293, 0.5F + 0.5F * Mth.sin(tick * 0.075F));
      graphics.fill(x + 6, y + 6, x + this.imageWidth - 6, y + 9, pulse);
      graphics.drawCenteredString(this.font, Component.literal("GENE ARCHIVE"), x + this.imageWidth / 2, y + 15, -1251329);
      this.drawPanel(graphics, x + 8, y + 36, 188, 112, -2146233571);
      this.drawPanel(graphics, x + 204, y + 36, 132, 112, -2146233571);
      this.drawPanel(graphics, x + 346, y + 36, 102, 112, -2146233571);
      this.drawPanel(graphics, x + 8, y + 154, 440, 48, -2146233571);
      this.drawPanel(graphics, x + 147 - 6, y + 214 - 13, 174, 27, -2146233571);
      graphics.drawString(
         this.font,
         Component.literal("Page " + (((GeneEditorMenu)this.menu).getPage() + 1) + "/" + (((GeneEditorMenu)this.menu).getMaxPage() + 1)),
         x + 68,
         y + 20,
         -11142
      );
      graphics.drawString(this.font, Component.literal("Genes"), x + 8 + 6, y + 36 + 7, -3294209);
      graphics.drawString(this.font, Component.literal("Details"), x + 204 + 8, y + 36 + 7, -3294209);
      String selected = ((GeneEditorMenu)this.menu).getSelectedKey();
      graphics.drawString(
         this.font,
         (Component)(selected.isEmpty() ? Component.literal("-") : this.fit(Component.literal(((GeneEditorMenu)this.menu).displayName(selected)), 116)),
         x + 204 + 8,
         y + 36 + 25,
         -7340047
      );
      graphics.drawString(this.font, Component.literal("Avail: " + ((GeneEditorMenu)this.menu).getSelectedAvailableText()), x + 204 + 8, y + 36 + 43, -11142);
      graphics.drawString(this.font, Component.literal("Chosen: " + ((GeneEditorMenu)this.menu).getSelectedChosenText()), x + 204 + 8, y + 36 + 56, -11142);
      graphics.drawString(this.font, Component.literal("Amount"), x + 204 + 8, y + 36 + 69, ((GeneEditorMenu)this.menu).selectedIsStat() ? -6422600 : -8947849);
      graphics.drawString(this.font, Component.literal("Syringe"), x + 346 + 12, y + 36 + 7, -3294209);
      this.drawSlot(graphics, x + 387, y + 52);
      ItemStack syringe = ((GeneEditorMenu)this.menu).getSyringeStack();
      String state = !syringe.isEmpty() && syringe.getItem() instanceof GeneticSyringeItem ? GeneticSyringeItem.state(syringe) : "none";
      graphics.drawString(this.font, Component.literal("State: " + state), x + 346 + 8, y + 36 + 52, -1251329);
      graphics.drawString(this.font, Component.literal("sample = store"), x + 346 + 8, y + 36 + 64, -5592406);
      graphics.drawString(this.font, Component.literal("Workshop"), x + 8 + 6, y + 154 + 6, -3294209);
      int visible = Math.min(10, ((GeneEditorMenu)this.menu).getWorkshopSize());

      for (int i = 0; i < visible; i++) {
         String key = ((GeneEditorMenu)this.menu).getWorkshopKey(i);
         String amountText = ((GeneEditorMenu)this.menu).getWorkshopAmountText(i);
         int col = i % 5;
         int row = i / 5;
         int itemX = x + 8 + 86 + col * 70;
         int itemY = y + 154 + 7 + row * 15;
         graphics.drawString(this.font, this.fit(Component.literal(i + 1 + ". " + ((GeneEditorMenu)this.menu).displayName(key)), 47), itemX, itemY, -7340047);
         graphics.drawString(this.font, Component.literal("x" + amountText), itemX + 48, itemY, -11142);
      }

      if (((GeneEditorMenu)this.menu).getWorkshopSize() > visible) {
         graphics.drawString(this.font, Component.literal("+" + (((GeneEditorMenu)this.menu).getWorkshopSize() - visible)), x + 8 + 410, y + 154 + 24, -5592406);
      }

      graphics.drawString(this.font, Component.literal("Hotbar"), x + 147, y + 214 - 11, -3294209);
      this.drawHotbarSlots(graphics, x, y);
   }

   private void drawPanel(GuiGraphics graphics, int x, int y, int w, int h, int color) {
      graphics.fill(x, y, x + w, y + h, color);
      graphics.fill(x, y, x + w, y + 1, -2136370177);
      graphics.fill(x, y + h - 1, x + w, y + h, -2146430952);
   }

   private void drawHotbarSlots(GuiGraphics graphics, int x, int y) {
      for (int col = 0; col < 9; col++) {
         this.drawSlot(graphics, x + 147 + col * 18, y + 214);
      }
   }

   private void drawSlot(GuiGraphics graphics, int x, int y) {
      graphics.fill(x, y, x + 18, y + 18, -14013900);
      graphics.fill(x + 1, y + 1, x + 17, y + 17, -15724520);
      graphics.fill(x + 1, y + 1, x + 17, y + 2, -9934728);
      graphics.fill(x + 1, y + 1, x + 2, y + 17, -9934728);
      graphics.fill(x + 16, y + 1, x + 17, y + 17, -16448246);
      graphics.fill(x + 1, y + 16, x + 17, y + 17, -16448246);
   }

   private Component fit(Component component, int maxWidth) {
      String full = component.getString();
      String clipped = this.font.plainSubstrByWidth(full, maxWidth);
      if (clipped.length() < full.length()) {
         if (!clipped.isEmpty()) {
            clipped = clipped.substring(0, Math.max(0, clipped.length() - 1));
         }

         clipped = clipped + "…";
      }

      return Component.literal(clipped);
   }

   private static int mix(int c1, int c2, float t) {
      t = Mth.clamp(t, 0.0F, 1.0F);
      int r1 = c1 >>> 16 & 0xFF;
      int g1 = c1 >>> 8 & 0xFF;
      int b1 = c1 & 0xFF;
      int r2 = c2 >>> 16 & 0xFF;
      int g2 = c2 >>> 8 & 0xFF;
      int b2 = c2 & 0xFF;
      int r = (int)(r1 + (r2 - r1) * t);
      int g = (int)(g1 + (g2 - g1) * t);
      int b = (int)(b1 + (b2 - b1) * t);
      return 0xFF000000 | r << 16 | g << 8 | b;
   }

   public boolean charTyped(char codePoint, int modifiers) {
      return this.amountBox != null && this.amountBox.charTyped(codePoint, modifiers) ? true : super.charTyped(codePoint, modifiers);
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      return this.amountBox != null && this.amountBox.keyPressed(keyCode, scanCode, modifiers) ? true : super.keyPressed(keyCode, scanCode, modifiers);
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      this.renderBackground(graphics, mouseX, mouseY, partialTick);
      super.render(graphics, mouseX, mouseY, partialTick);
      this.renderTooltip(graphics, mouseX, mouseY);
   }
}
