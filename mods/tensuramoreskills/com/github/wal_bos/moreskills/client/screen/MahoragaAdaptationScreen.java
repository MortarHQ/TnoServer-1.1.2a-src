package com.github.wal_bos.moreskills.client.screen;

import com.github.wal_bos.moreskills.menu.MahoragaAdaptationMenu;
import com.github.wal_bos.moreskills.net.MahoragaAdaptationActionPayload;
import dev.architectury.networking.NetworkManager;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class MahoragaAdaptationScreen extends AbstractContainerScreen<MahoragaAdaptationMenu> {
   private static final int TAB_DAMAGE = 0;
   private static final int TAB_EFFECT = 1;
   private static final int VISIBLE_ROWS = 7;
   private static final int ROW_HEIGHT = 24;
   private static final int TAB_Y = 16;
   private static final int LIST_X = 18;
   private static final int LIST_Y = 48;
   private static final int ROW_WIDTH = 224;
   private static final int TOGGLE_X = 248;
   private static final int TOGGLE_W = 42;
   private static final int SCROLL_X = 294;
   private final List<Button> dynamicButtons = new ArrayList<>();
   private int tab = 0;
   private int scroll;
   private float openAnim;

   public MahoragaAdaptationScreen(MahoragaAdaptationMenu menu, Inventory inventory, Component title) {
      super(menu, inventory, title);
      this.imageWidth = 332;
      this.imageHeight = 228;
      this.inventoryLabelY = 10000;
      this.titleLabelY = 10000;
   }

   protected void init() {
      super.init();
      this.clampTabToContent();
      this.clampScroll();
      this.rebuildWidgets();
   }

   public void containerTick() {
      super.containerTick();
      if (this.openAnim < 1.0F) {
         this.openAnim = Math.min(1.0F, this.openAnim + 0.08F);
      }

      this.clampTabToContent();
      int maxScroll = Math.max(0, this.currentIndices().size() - 7);
      if (this.scroll > maxScroll) {
         this.scroll = maxScroll;
         this.rebuildWidgets();
      }
   }

   protected void rebuildWidgets() {
      this.clearDynamicButtons();
      this.clampTabToContent();
      this.clampScroll();
      int x = this.leftPos;
      int y = this.topPos;
      Button damageTab = this.addDyn(Button.builder(Component.literal("Damage"), b -> {
         this.tab = 0;
         this.scroll = 0;
         this.rebuildWidgets();
      }).bounds(x + 18, y + 16, 92, 20).build());
      damageTab.active = this.tab != 0;
      Button effectTab = this.addDyn(Button.builder(Component.literal("Effects"), b -> {
         this.tab = 1;
         this.scroll = 0;
         this.rebuildWidgets();
      }).bounds(x + 114, y + 16, 92, 20).build());
      effectTab.active = this.tab != 1;
      this.addDyn(Button.builder(Component.literal("▲"), b -> {
         this.scroll = Math.max(0, this.scroll - 1);
         this.rebuildWidgets();
      }).bounds(x + 294, y + 48, 22, 18).build());
      this.addDyn(Button.builder(Component.literal("▼"), b -> {
         this.scroll = Math.min(Math.max(0, this.currentIndices().size() - 7), this.scroll + 1);
         this.rebuildWidgets();
      }).bounds(x + 294, y + 48 + 22, 22, 18).build());
      List<Integer> indices = this.currentIndices();
      int visible = Math.min(7, Math.max(0, indices.size() - this.scroll));

      for (int i = 0; i < visible; i++) {
         int actualIndex = indices.get(this.scroll + i);
         int rowY = y + 48 + i * 24;
         this.addDyn(
            Button.builder(
                  Component.literal(((MahoragaAdaptationMenu)this.menu).getEntry(actualIndex).enabled() ? "ON" : "OFF"), b -> this.toggleIndex(actualIndex)
               )
               .bounds(x + 248, rowY, 42, 20)
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

   private List<Integer> currentIndices() {
      return this.tab == 0 ? ((MahoragaAdaptationMenu)this.menu).getDamageIndices() : ((MahoragaAdaptationMenu)this.menu).getEffectIndices();
   }

   private void clampTabToContent() {
      if (this.tab == 0
         && ((MahoragaAdaptationMenu)this.menu).getDamageIndices().isEmpty()
         && !((MahoragaAdaptationMenu)this.menu).getEffectIndices().isEmpty()) {
         this.tab = 1;
      } else if (this.tab == 1
         && ((MahoragaAdaptationMenu)this.menu).getEffectIndices().isEmpty()
         && !((MahoragaAdaptationMenu)this.menu).getDamageIndices().isEmpty()) {
         this.tab = 0;
      }
   }

   private void clampScroll() {
      int max = Math.max(0, this.currentIndices().size() - 7);
      this.scroll = Mth.clamp(this.scroll, 0, max);
   }

   private void clickRow(int index) {
      if (index >= 0 && index < ((MahoragaAdaptationMenu)this.menu).getEntries().size()) {
         ((MahoragaAdaptationMenu)this.menu).setSelectedIndexClient(index);
         NetworkManager.sendToServer(new MahoragaAdaptationActionPayload(MahoragaAdaptationActionPayload.Action.SELECT, index));
      }
   }

   private void toggleIndex(int index) {
      if (index >= 0 && index < ((MahoragaAdaptationMenu)this.menu).getEntries().size()) {
         ((MahoragaAdaptationMenu)this.menu).toggleEntryClient(index);
         NetworkManager.sendToServer(new MahoragaAdaptationActionPayload(MahoragaAdaptationActionPayload.Action.TOGGLE, index));
         this.rebuildWidgets();
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (super.mouseClicked(mouseX, mouseY, button)) {
         return true;
      }

      List<Integer> indices = this.currentIndices();
      int visible = Math.min(7, Math.max(0, indices.size() - this.scroll));
      int baseX = this.leftPos + 18;
      int baseY = this.topPos + 48;

      for (int i = 0; i < visible; i++) {
         int actual = indices.get(this.scroll + i);
         int rowX = baseX;
         int rowY = baseY + i * 24;
         if (mouseX >= rowX && mouseX < rowX + 224 && mouseY >= rowY && mouseY < rowY + 20) {
            this.clickRow(actual);
            return true;
         }
      }

      return false;
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
      if (this.currentIndices().size() <= 7) {
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
      float pulse = (float)(0.5 + 0.5 * Math.sin((float)(this.minecraft.level != null ? this.minecraft.level.getGameTime() : 0L) * 0.08F));
      int glowAlpha = (int)(18.0F + pulse * 20.0F);
      int inset = (int)((1.0F - this.openAnim) * 16.0F);
      g.fillGradient(x1 + inset, y1 + inset, x2 - inset, y2 - inset, -462384, -991070);
      g.fill(x1 + 4 + inset, y1 + 4 + inset, x2 - 4 - inset, y2 - 4 - inset, -132109);
      g.fill(x1 + 10, y1 + 10, x2 - 10, y1 + 38, -793438);
      g.fill(x1 + 10, y1 + 42, x2 - 10, y2 - 10, -395282);
      g.fill(x1 + 10, y1 + 10, x2 - 10, y1 + 11, -2096);
      g.fill(x1 + 10, y2 - 11, x2 - 10, y2 - 10, -1582950);
      g.fill(x1 + 10, y1 + 10, x1 + 11, y2 - 10, -2096);
      g.fill(x2 - 11, y1 + 10, x2 - 10, y2 - 10, -1582950);
      g.fill(x1 + 18, y1 + 44, x2 - 18, y1 + 45, -2307716);
      int sweepX = x1
         + 18
         + (int)((this.imageWidth - 56) * ((Math.sin((float)(this.minecraft.level != null ? this.minecraft.level.getGameTime() : 0L) * 0.05F) + 1.0) * 0.5));
      g.fill(sweepX, y1 + 46, Math.min(sweepX + 18, x2 - 18), y2 - 18, glowAlpha << 24 | 16774095);
      this.drawRows(g);
      this.drawFooter(g);
   }

   private void drawRows(GuiGraphics g) {
      List<Integer> indices = this.currentIndices();
      int visible = Math.min(7, Math.max(0, indices.size() - this.scroll));

      for (int i = 0; i < visible; i++) {
         int actual = indices.get(this.scroll + i);
         int rx = this.leftPos + 18;
         int ry = this.topPos + 48 + i * 24;
         MahoragaAdaptationMenu.Entry entry = ((MahoragaAdaptationMenu)this.menu).getEntry(actual);
         boolean selected = actual == ((MahoragaAdaptationMenu)this.menu).getSelectedIndex();
         int outer = selected ? -993660 : -1054510;
         int inner = selected ? 1728051158 : 872415231;
         g.fill(rx, ry, rx + 224, ry + 20, outer);
         g.fill(rx + 1, ry + 1, rx + 224 - 1, ry + 19, inner);
         String leftTag = entry.damage() ? "DMG" : "EFF";
         String name = this.prettyName(entry.id(), entry.damage(), 15);
         String info = entry.damage() ? "DR " + entry.reduction() + "%  S" + entry.stage() : "Adapted";
         int leftColor = selected ? 8017920 : 9074240;
         int mainColor = 3089674;
         int infoColor = 8020490;
         g.drawString(this.font, leftTag, rx + 6, ry + 6, leftColor, false);
         g.drawString(this.font, name, rx + 34, ry + 6, mainColor, false);
         int infoWidth = this.font.width(info);
         g.drawString(this.font, info, rx + 224 - 8 - infoWidth, ry + 6, infoColor, false);
      }

      if (indices.isEmpty()) {
         String empty = this.tab == 0 ? "No damage adaptations yet." : "No effect adaptations yet.";
         g.drawString(this.font, empty, this.leftPos + 22, this.topPos + 54, 8153656, false);
      }
   }

   private void drawFooter(GuiGraphics g) {
      int y = this.topPos + this.imageHeight - 20;
      String left = ((MahoragaAdaptationMenu)this.menu).isSkillToggled() ? "Mahoraga Active" : "Mahoraga Off";
      g.drawString(this.font, left, this.leftPos + 20, y, ((MahoragaAdaptationMenu)this.menu).isSkillToggled() ? 9202432 : 9071202, false);
      String right = this.tab == 0 ? "Damage Adaptations" : "Effect Adaptations";
      int width = this.font.width(right);
      g.drawString(this.font, right, this.leftPos + this.imageWidth - 20 - width, y, 9202432, false);
   }

   private String prettyName(String raw, boolean damage, int maxChars) {
      String out = raw == null ? "" : raw;
      if (out.contains(":")) {
         out = out.substring(out.indexOf(58) + 1);
      }

      out = out.replace('_', ' ').replace('.', ' ').trim();
      if (damage) {
         out = switch (out.toLowerCase()) {
            case "mob" -> "Mob";
            case "player" -> "Player";
            case "magic" -> "Magic";
            case "indirect magic" -> "Indirect Magic";
            case "fire" -> "Fire";
            case "lightning bolt", "lightning" -> "Lightning";
            case "explosion player", "explosion" -> "Explosion";
            default -> this.capitalizeWords(out);
         };
      } else {
         out = this.capitalizeWords(out);
      }

      return out.length() <= maxChars ? out : out.substring(0, Math.max(0, maxChars - 3)) + "...";
   }

   private String capitalizeWords(String input) {
      if (input != null && !input.isBlank()) {
         String[] parts = input.split("\\s+");
         StringBuilder out = new StringBuilder();

         for (int i = 0; i < parts.length; i++) {
            String p = parts[i];
            if (!p.isEmpty()) {
               out.append(Character.toUpperCase(p.charAt(0)));
               if (p.length() > 1) {
                  out.append(p.substring(1).toLowerCase());
               }

               if (i < parts.length - 1) {
                  out.append(' ');
               }
            }
         }

         return out.toString();
      } else {
         return "";
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
