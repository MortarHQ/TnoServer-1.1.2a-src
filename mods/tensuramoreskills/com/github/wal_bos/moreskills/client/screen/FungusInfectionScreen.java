package com.github.wal_bos.moreskills.client.screen;

import com.github.wal_bos.moreskills.fungus.FungusInfectionMenu;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class FungusInfectionScreen extends AbstractContainerScreen<FungusInfectionMenu> {
   private static final int LEFT_PANEL_X = 12;
   private static final int RIGHT_PANEL_X = 184;
   private static final int TOP_PANEL_Y = 42;
   private Button prev;
   private Button next;
   private Button upDur;
   private Button upAmp;
   private final Button[] workshop = new Button[10];
   private final Button[] selects = new Button[6];

   public FungusInfectionScreen(FungusInfectionMenu menu, Inventory inv, Component title) {
      super(menu, inv, title);
      this.imageWidth = 368;
      this.imageHeight = 236;
      this.inventoryLabelY = 10000;
      this.titleLabelY = 10000;
   }

   protected void init() {
      super.init();
      int x = this.leftPos;
      int y = this.topPos;
      this.prev = (Button)this.addRenderableWidget(Button.builder(Component.literal("<"), b -> this.press(1)).bounds(x + 14, y + 18, 20, 20).build());
      this.next = (Button)this.addRenderableWidget(Button.builder(Component.literal(">"), b -> this.press(2)).bounds(x + 38, y + 18, 20, 20).build());

      for (int i = 0; i < 10; i++) {
         int col = i % 2;
         int row = i / 2;
         int bx = x + 184 + col * 72;
         int by = y + 48 + row * 20;
         int id = 10 + i;
         this.workshop[i] = (Button)this.addRenderableWidget(
            Button.builder(Component.literal("S" + (i + 1)), b -> this.press(id)).bounds(bx, by, 62, 18).build()
         );
      }

      this.upDur = (Button)this.addRenderableWidget(Button.builder(Component.literal("Dur +"), b -> this.press(3)).bounds(x + 184, y + 154, 68, 20).build());
      this.upAmp = (Button)this.addRenderableWidget(
         Button.builder(Component.literal("Amp +"), b -> this.press(4)).bounds(x + 184 + 78, y + 154, 68, 20).build()
      );

      for (int i = 0; i < 6; i++) {
         int by = y + 48 + i * 20;
         int id = 20 + i;
         this.selects[i] = (Button)this.addRenderableWidget(Button.builder(Component.literal(""), b -> this.press(id)).bounds(x + 12 + 2, by, 160, 18).build());
      }

      this.refreshButtons();
   }

   private void press(int id) {
      if (this.minecraft != null && this.minecraft.gameMode != null) {
         this.minecraft.gameMode.handleInventoryButtonClick(((FungusInfectionMenu)this.menu).containerId, id);
      }
   }

   private void refreshButtons() {
      List<String> page = ((FungusInfectionMenu)this.menu).getKeysPage();

      for (int i = 0; i < 6; i++) {
         if (i < page.size()) {
            this.selects[i].visible = true;
            this.selects[i].setMessage(this.fitTranslated(page.get(i), 148));
         } else {
            this.selects[i].visible = false;
            this.selects[i].setMessage(Component.literal(""));
         }
      }

      int p = ((FungusInfectionMenu)this.menu).getPage();
      this.prev.active = p > 0;
      this.next.active = p < ((FungusInfectionMenu)this.menu).getMaxPage();
      int avail = ((FungusInfectionMenu)this.menu).getSelectedAvail();
      this.upDur.active = avail >= 2;
      this.upAmp.active = avail >= 2;
      int limit = ((FungusInfectionMenu)this.menu).getWorkshopLimit();
      boolean hasSel = !((FungusInfectionMenu)this.menu).getSelectedKey().isEmpty();

      for (int i = 0; i < 10; i++) {
         this.workshop[i].visible = i < limit;
         this.workshop[i].active = hasSel && i < limit;
      }
   }

   protected void containerTick() {
      super.containerTick();
      this.refreshButtons();
   }

   protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
      int x = this.leftPos;
      int y = this.topPos;
      graphics.fill(x, y, x + this.imageWidth, y + this.imageHeight, -536541686);
      graphics.fill(x + 4, y + 4, x + this.imageWidth - 4, y + this.imageHeight - 4, -1072623080);
      int tick = this.minecraft != null && this.minecraft.player != null ? this.minecraft.player.tickCount : 0;
      int pulse = mix(-8947849, -13836164, 0.5F + 0.5F * Mth.sin(tick * 0.08F));
      graphics.fill(x + 5, y + 5, x + this.imageWidth - 5, y + 7, pulse);
      graphics.drawCenteredString(this.font, Component.translatable("tensuramoreskills.gui.fungus_infection.header"), x + this.imageWidth / 2, y + 10, -1706008);
      graphics.drawString(
         this.font,
         Component.translatable(
            "tensuramoreskills.gui.fungus_infection.page",
            new Object[]{((FungusInfectionMenu)this.menu).getPage() + 1, ((FungusInfectionMenu)this.menu).getMaxPage() + 1}
         ),
         x + 64,
         y + 24,
         -11142
      );
      graphics.fill(x + 12, y + 42, x + 170, y + 164, 1611863064);
      graphics.fill(x + 12, y + 168, x + 170, y + 224, 1611863064);
      graphics.fill(x + 184, y + 42, x + 350, y + 144, 1611863064);
      graphics.fill(x + 184, y + 148, x + 350, y + 224, 1611863064);
      graphics.drawString(this.font, Component.translatable("tensuramoreskills.gui.fungus_infection.storage_list"), x + 12 + 2, y + 34, -4264761);
      graphics.drawString(this.font, Component.translatable("tensuramoreskills.gui.fungus_infection.details"), x + 184, y + 34, -4264761);
      graphics.drawString(this.font, Component.translatable("tensuramoreskills.gui.fungus_infection.selected"), x + 12 + 2, y + 172, -4264761);
      String selectedKey = ((FungusInfectionMenu)this.menu).getSelectedKey();
      graphics.drawString(
         this.font, (Component)(selectedKey.isEmpty() ? Component.literal("-") : this.fitTranslated(selectedKey, 148)), x + 12 + 2, y + 184, -7209047
      );
      graphics.drawString(
         this.font,
         Component.translatable("tensuramoreskills.gui.fungus_infection.analyzed", new Object[]{((FungusInfectionMenu)this.menu).getSelectedCount()}),
         x + 12 + 2,
         y + 196,
         -11142
      );
      graphics.drawString(
         this.font,
         Component.translatable("tensuramoreskills.gui.fungus_infection.duration_level", new Object[]{((FungusInfectionMenu)this.menu).getSelectedDur()}),
         x + 12 + 2,
         y + 208,
         -11142
      );
      graphics.drawString(
         this.font,
         Component.translatable("tensuramoreskills.gui.fungus_infection.amplify_level", new Object[]{((FungusInfectionMenu)this.menu).getSelectedAmp()}),
         x + 96,
         y + 196,
         -11142
      );
      graphics.drawString(
         this.font,
         Component.translatable("tensuramoreskills.gui.fungus_infection.points", new Object[]{((FungusInfectionMenu)this.menu).getSelectedAvail()}),
         x + 96,
         y + 208,
         -11142
      );
      graphics.drawString(this.font, Component.translatable("tensuramoreskills.gui.fungus_infection.workshop_header"), x + 184, y + 180, -4264761);

      for (int i = 0; i < ((FungusInfectionMenu)this.menu).getWorkshopLimit() && i < 10; i++) {
         int col = i < 5 ? 0 : 1;
         int row = i % 5;
         int lineX = x + 184 + col * 82;
         int lineY = y + 192 + row * 10;
         this.drawWorkshopLine(graphics, lineX, lineY, i + 1, ((FungusInfectionMenu)this.menu).getWorkshopKey(i));
      }
   }

   private void drawWorkshopLine(GuiGraphics graphics, int x, int y, int slot, String key) {
      graphics.drawString(this.font, Component.literal(slot + "."), x, y, -11142);
      if (key != null && !key.isEmpty()) {
         graphics.drawString(this.font, this.fitTranslated(key, 68), x + 12, y, -7209047);
      } else {
         graphics.drawString(this.font, Component.literal("-"), x + 12, y, -7209047);
      }
   }

   private Component fitTranslated(String key, int maxWidth) {
      String full = Component.translatable(key).getString();
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

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      this.renderBackground(graphics, mouseX, mouseY, partialTick);
      super.render(graphics, mouseX, mouseY, partialTick);
      this.renderTooltip(graphics, mouseX, mouseY);
   }
}
