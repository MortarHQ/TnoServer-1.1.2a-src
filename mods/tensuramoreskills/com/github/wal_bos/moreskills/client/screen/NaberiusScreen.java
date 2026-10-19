package com.github.wal_bos.moreskills.client.screen;

import com.github.wal_bos.moreskills.menu.NaberiusMenu;
import com.github.wal_bos.moreskills.net.msg.NaberiusActionPayload;
import dev.architectury.networking.NetworkManager;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class NaberiusScreen extends AbstractContainerScreen<NaberiusMenu> {
   private static final int VISIBLE_ROWS = 7;
   private static final int ROW_H = 22;
   private static final int LIST_X = 16;
   private static final int LIST_Y = 42;
   private static final int ROW_W = 214;
   private static final int SELECT_X = 236;
   private static final int SELECT_W = 36;
   private static final int SMALL_W = 24;
   private static final int SCROLL_X = 304;
   private final List<Button> dynamicButtons = new ArrayList<>();
   private int scroll = 0;
   private float openAnim = 0.0F;

   public NaberiusScreen(NaberiusMenu menu, Inventory inventory, Component title) {
      super(menu, inventory, title);
      this.imageWidth = 332;
      this.imageHeight = 228;
      this.inventoryLabelY = 10000;
      this.titleLabelY = 10000;
   }

   protected void init() {
      super.init();
      this.clampScroll();
      this.rebuildWidgets();
   }

   public void containerTick() {
      super.containerTick();
      if (this.openAnim < 1.0F) {
         this.openAnim = Math.min(1.0F, this.openAnim + 0.08F);
      }

      int maxScroll = Math.max(0, ((NaberiusMenu)this.menu).getEntries().size() - 7);
      if (this.scroll > maxScroll) {
         this.scroll = maxScroll;
         this.rebuildWidgets();
      }
   }

   protected void rebuildWidgets() {
      this.clearDynamicButtons();
      this.clampScroll();
      int x = this.leftPos;
      int y = this.topPos;
      int page = ((NaberiusMenu)this.menu).getPageMode();
      if (page == 1) {
         int visible = Math.min(7, Math.max(0, ((NaberiusMenu)this.menu).getEntries().size() - this.scroll));

         for (int i = 0; i < visible; i++) {
            NaberiusMenu.Entry entry = ((NaberiusMenu)this.menu).getEntries().get(this.scroll + i);
            int rowY = y + 42 + i * 22;
            int index = entry.index();
            this.addDyn(
               Button.builder(
                     Component.literal(entry.selected() ? "ON" : "OFF"),
                     b -> NetworkManager.sendToServer(new NaberiusActionPayload(NaberiusActionPayload.Action.TOGGLE_SELECTED, index))
                  )
                  .bounds(x + 236, rowY, 36, 18)
                  .build()
            );
            this.addDyn(
               Button.builder(
                     Component.literal("-"), b -> NetworkManager.sendToServer(new NaberiusActionPayload(NaberiusActionPayload.Action.REDUCE_COUNT, index))
                  )
                  .bounds(x + 236 + 36 + 4, rowY, 24, 18)
                  .build()
            );
            this.addDyn(
               Button.builder(
                     Component.literal("X"), b -> NetworkManager.sendToServer(new NaberiusActionPayload(NaberiusActionPayload.Action.DELETE_SELECTED, index))
                  )
                  .bounds(x + 236 + 36 + 24 + 8, rowY, 24, 18)
                  .build()
            );
         }

         this.addDyn(
            Button.builder(
                  Component.literal("One"), b -> NetworkManager.sendToServer(new NaberiusActionPayload(NaberiusActionPayload.Action.MANIFEST_SELECTED, 0))
               )
               .bounds(x + 16, y + 194, 58, 20)
               .build()
         );
         this.addDyn(
            Button.builder(
                  Component.literal("All"), b -> NetworkManager.sendToServer(new NaberiusActionPayload(NaberiusActionPayload.Action.MANIFEST_ALL_SELECTED, 0))
               )
               .bounds(x + 78, y + 194, 58, 20)
               .build()
         );
         this.addDyn(
            Button.builder(
                  Component.literal("Dust"), b -> NetworkManager.sendToServer(new NaberiusActionPayload(NaberiusActionPayload.Action.CLEAR_ACTIVE_SUMMONS, 0))
               )
               .bounds(x + 140, y + 194, 58, 20)
               .build()
         );
         this.addDyn(Button.builder(Component.literal("▲"), b -> {
            this.scroll = Math.max(0, this.scroll - 1);
            this.rebuildWidgets();
         }).bounds(x + 304, y + 42, 18, 18).build());
         this.addDyn(Button.builder(Component.literal("▼"), b -> {
            this.scroll = Math.min(Math.max(0, ((NaberiusMenu)this.menu).getEntries().size() - 7), this.scroll + 1);
            this.rebuildWidgets();
         }).bounds(x + 304, y + 42 + 22, 18, 18).build());
      }

      if (page == 3) {
         this.addDyn(
            Button.builder(
                  Component.literal("Beast"), b -> NetworkManager.sendToServer(new NaberiusActionPayload(NaberiusActionPayload.Action.CRAFT_CHIMERA, 0))
               )
               .bounds(x + 24, y + 54, 96, 22)
               .build()
         );
         this.addDyn(
            Button.builder(
                  Component.literal("Seraph"), b -> NetworkManager.sendToServer(new NaberiusActionPayload(NaberiusActionPayload.Action.CRAFT_CHIMERA, 1))
               )
               .bounds(x + 24, y + 84, 96, 22)
               .build()
         );
         this.addDyn(
            Button.builder(
                  Component.literal("Hydra"), b -> NetworkManager.sendToServer(new NaberiusActionPayload(NaberiusActionPayload.Action.CRAFT_CHIMERA, 2))
               )
               .bounds(x + 24, y + 114, 96, 22)
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

   private void clampScroll() {
      this.scroll = Mth.clamp(this.scroll, 0, Math.max(0, ((NaberiusMenu)this.menu).getEntries().size() - 7));
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (super.mouseClicked(mouseX, mouseY, button)) {
         return true;
      }

      if (((NaberiusMenu)this.menu).getPageMode() != 1) {
         return false;
      }

      int visible = Math.min(7, Math.max(0, ((NaberiusMenu)this.menu).getEntries().size() - this.scroll));
      int baseX = this.leftPos + 16;
      int baseY = this.topPos + 42;

      for (int i = 0; i < visible; i++) {
         int rowX = baseX;
         int rowY = baseY + i * 22;
         if (mouseX >= rowX && mouseX < rowX + 214 && mouseY >= rowY && mouseY < rowY + 18) {
            NaberiusMenu.Entry entry = ((NaberiusMenu)this.menu).getEntries().get(this.scroll + i);
            NetworkManager.sendToServer(new NaberiusActionPayload(NaberiusActionPayload.Action.TOGGLE_SELECTED, entry.index()));
            return true;
         }
      }

      return false;
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
      if (((NaberiusMenu)this.menu).getPageMode() != 1 || ((NaberiusMenu)this.menu).getEntries().size() <= 7) {
         return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
      } else if (scrollY > 0.0) {
         this.scroll--;
         this.clampScroll();
         this.rebuildWidgets();
         return true;
      } else if (scrollY < 0.0) {
         this.scroll++;
         this.clampScroll();
         this.rebuildWidgets();
         return true;
      } else {
         return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
      }
   }

   protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
      int x1 = this.leftPos;
      int y1 = this.topPos;
      int x2 = x1 + this.imageWidth;
      int y2 = y1 + this.imageHeight;
      float t = this.minecraft != null && this.minecraft.player != null ? this.minecraft.player.tickCount + partialTick : partialTick;
      float pulse = 0.5F + 0.5F * Mth.sin(t * 0.08F);
      int inset = (int)((1.0F - this.openAnim) * 14.0F);
      int glowAlpha = (int)(35.0F + pulse * 45.0F);
      g.fill(x1 + inset, y1 + inset, x2 - inset, y2 - inset, -16379895);
      g.fill(x1 + 4 + inset, y1 + 4 + inset, x2 - 4 - inset, y2 - 4 - inset, -16049904);
      g.fill(x1 + 10, y1 + 10, x2 - 10, y1 + 35, -15321825);
      g.fill(x1 + 10, y1 + 39, x2 - 10, y2 - 10, -15852014);
      g.fill(x1 + 10, y1 + 10, x2 - 10, y1 + 11, -8585280);
      g.fill(x1 + 10, y2 - 11, x2 - 10, y2 - 10, -14455998);
      g.fill(x1 + 10, y1 + 10, x1 + 11, y2 - 10, -8585280);
      g.fill(x2 - 11, y1 + 10, x2 - 10, y2 - 10, -14455998);
      int sweepX = x1 + 18 + (int)((this.imageWidth - 58) * ((Mth.sin(t * 0.045F) + 1.0F) * 0.5F));
      g.fill(sweepX, y1 + 42, Math.min(sweepX + 22, x2 - 18), y2 - 18, glowAlpha << 24 | 65399);
      if (((NaberiusMenu)this.menu).getPageMode() == 1) {
         this.drawArchive(g);
      }

      if (((NaberiusMenu)this.menu).getPageMode() == 3) {
         this.drawChimera(g);
      }
   }

   private void drawArchive(GuiGraphics g) {
      int x = this.leftPos;
      int y = this.topPos;
      g.drawString(this.font, "Naberius", x + 18, y + 19, -3145744, false);
      int visible = Math.min(7, Math.max(0, ((NaberiusMenu)this.menu).getEntries().size() - this.scroll));

      for (int i = 0; i < visible; i++) {
         int actual = this.scroll + i;
         int rx = x + 16;
         int ry = y + 42 + i * 22;
         NaberiusMenu.Entry entry = ((NaberiusMenu)this.menu).getEntries().get(actual);
         int outer = entry.selected() ? -9633889 : -14791891;
         int inner = entry.selected() ? 1428684672 : 857497137;
         g.fill(rx, ry, rx + 214, ry + 18, outer);
         g.fill(rx + 1, ry + 1, rx + 214 - 1, ry + 17, inner);
         String tag = entry.player() ? "PLR" : "LIFE";
         String name = this.trim(entry.name(), 18);
         String stats = "x" + entry.count() + "  HP " + this.shortNum(entry.hp()) + "  EP " + this.shortNum(entry.ep());
         g.drawString(this.font, tag, rx + 6, ry + 5, entry.selected() ? -4194320 : -8205925, false);
         g.drawString(this.font, name, rx + 36, ry + 5, -1507344, false);
         int w = this.font.width(stats);
         g.drawString(this.font, stats, rx + 214 - 8 - w, ry + 5, -6298446, false);
      }

      if (((NaberiusMenu)this.menu).getEntries().isEmpty()) {
         g.drawString(this.font, "Empty", x + 22, y + 54, -9259897, false);
      }

      String left = "Active " + ((NaberiusMenu)this.menu).getActiveCount() + "/30";
      String right = "Stored " + ((NaberiusMenu)this.menu).getEntries().size() + "/100";
      g.drawString(this.font, left, x + 18, y + 179, -7602256, false);
      int rw = this.font.width(right);
      g.drawString(this.font, right, x + this.imageWidth - 20 - rw, y + 179, -7602256, false);
   }

   private void drawChimera(GuiGraphics g) {
      int x = this.leftPos;
      int y = this.topPos;
      g.drawString(this.font, "Chimera", x + 18, y + 19, -3145744, false);
      this.drawRecipe(g, x + 136, y + 59, "40k HP", "300k EP", "2");
      this.drawRecipe(g, x + 136, y + 89, "75k HP", "700k EP", "3");
      this.drawRecipe(g, x + 136, y + 119, "150k HP", "1.5m EP", "5");
      g.drawString(this.font, "Consumes archive", x + 22, y + 179, -7602256, false);
   }

   private void drawRecipe(GuiGraphics g, int x, int y, String hp, String ep, String souls) {
      g.drawString(this.font, hp, x, y, -2555934, false);
      g.drawString(this.font, ep, x + 58, y, -6298446, false);
      g.drawString(this.font, "x" + souls, x + 126, y, -7602256, false);
   }

   private String trim(String raw, int max) {
      if (raw == null) {
         return "";
      } else {
         return raw.length() <= max ? raw : raw.substring(0, Math.max(0, max - 3)) + "...";
      }
   }

   private String shortNum(double value) {
      double abs = Math.abs(value);
      if (abs >= 1.0E9) {
         return String.format("%.1fb", value / 1.0E9);
      } else if (abs >= 1000000.0) {
         return String.format("%.1fm", value / 1000000.0);
      } else {
         return abs >= 1000.0 ? String.format("%.1fk", value / 1000.0) : String.valueOf((long)value);
      }
   }

   protected void renderLabels(GuiGraphics g, int mouseX, int mouseY) {
   }

   public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
      this.renderBackground(g, mouseX, mouseY, partialTick);
      super.render(g, mouseX, mouseY, partialTick);
      this.renderTooltip(g, mouseX, mouseY);
   }
}
