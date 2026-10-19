package com.github.wal_bos.moreskills.client.screen;

import com.github.wal_bos.moreskills.net.msg.NirvanaActionPayload;
import com.github.wal_bos.moreskills.nirvana.NirvanaData;
import com.github.wal_bos.moreskills.nirvana.NirvanaMenu;
import dev.architectury.networking.NetworkManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class NirvanaArchiveScreen extends AbstractContainerScreen<NirvanaMenu> {
   private static final int BG = -167508464;
   private static final int SURFACE = -536409835;
   private static final int CARD = -1190391776;
   private static final int CARD_HOVER = -988406991;
   private static final int CARD_SELECTED = -903733953;
   private static final int LINE = -13095066;
   private static final int LINE_DIM = -14935499;
   private static final int LINE_BRIGHT = -4346113;
   private static final int TEXT = -1185537;
   private static final int MUTED = -7371094;
   private static final int GOLD = -10639;
   private static final int GREEN = -8519744;
   private static final int RED = -39065;
   private static final int BLUE = -7939585;
   private static final int OVERVIEW_VISIBLE = 4;
   private static final int SKILL_VISIBLE = 8;
   private static NirvanaArchiveScreen activeScreen;
   private static String popupTitle = "";
   private static String popupBody = "";
   private static boolean popupSuccess;
   private static int popupTicks;
   private boolean detailOpen;
   private UUID selectedLifeId;
   private int selectedSkill;
   private int selectedMode;
   private int lifeScroll;
   private int skillScroll;
   private EditBox searchBox;
   private EditBox renameBox;
   private String lastRenameValue = "";
   private boolean draggingLives;
   private double dragStartX;
   private int dragStartScroll;

   public NirvanaArchiveScreen(NirvanaMenu menu, Inventory inv, Component title) {
      super(menu, inv, title);
      this.imageWidth = 520;
      this.imageHeight = 260;
      this.inventoryLabelY = 10000;
      this.titleLabelY = 10000;
   }

   protected void init() {
      super.init();
      activeScreen = this;
      this.searchBox = (EditBox)this.addRenderableWidget(
         new EditBox(this.font, this.leftPos + 158, this.topPos + 28, 204, 16, Component.translatable("tensuramoreskills.gui.nirvana.search"))
      );
      this.searchBox.setMaxLength(32);
      this.searchBox.setBordered(false);
      this.searchBox.setTextColor(-1185537);
      this.searchBox.setHint(Component.literal("Search life, race, or skill"));
      this.renameBox = (EditBox)this.addRenderableWidget(
         new EditBox(this.font, this.leftPos + 172, this.topPos + 29, 174, 16, Component.translatable("tensuramoreskills.gui.nirvana.rename"))
      );
      this.renameBox.setMaxLength(32);
      this.renameBox.setBordered(false);
      this.renameBox.setTextColor(-10639);
      this.renameBox.visible = false;
      this.renameBox.active = false;
   }

   protected void containerTick() {
      super.containerTick();
      this.clampScrolls();
      if (this.detailOpen && this.renameBox != null && this.renameBox.isFocused()) {
         CompoundTag life = this.selectedLife();
         if (life != null && life.hasUUID("Id")) {
            String value = this.renameBox.getValue();
            if (!value.equals(life.getString("Name"))) {
               life.putString("Name", value);
            }
         }
      }
   }

   protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
      this.clampScrolls();
      int x = this.leftPos;
      int y = this.topPos;
      graphics.fill(x, y, x + this.imageWidth, y + this.imageHeight, -167508464);
      graphics.fill(x + 6, y + 6, x + this.imageWidth - 6, y + this.imageHeight - 6, -536409835);
      int glow = this.pulse(-11122757, -4346113, 0.035F);
      graphics.fill(x + 8, y + 8, x + this.imageWidth - 8, y + 10, glow);
      graphics.fill(x + 8, y + this.imageHeight - 10, x + this.imageWidth - 8, y + this.imageHeight - 8, glow);
      graphics.drawCenteredString(this.font, Component.literal("NIRVANA ARCHIVE"), x + this.imageWidth / 2, y + 15, -1185537);
      this.drawCloseButton(graphics, mouseX, mouseY);
      if (this.detailOpen) {
         this.renderDetail(graphics, mouseX, mouseY);
      } else {
         this.renderOverview(graphics, mouseX, mouseY);
      }
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      super.render(graphics, mouseX, mouseY, partialTick);
      this.renderMigrationPopup(graphics);
      this.renderTooltip(graphics, mouseX, mouseY);
   }

   private void renderOverview(GuiGraphics graphics, int mouseX, int mouseY) {
      this.renameBox.visible = false;
      this.renameBox.active = false;
      this.searchBox.visible = true;
      this.searchBox.active = true;
      int x = this.leftPos;
      int y = this.topPos;
      this.drawFrame(graphics, x + 151, y + 25, 218, 22, this.searchBox.isFocused());
      graphics.drawString(this.font, "Karma " + shortNum(((NirvanaMenu)this.menu).getKarma()), x + 392, y + 31, -10639, false);
      List<CompoundTag> lives = this.filteredLives();
      int baseX = x + 29;
      int baseY = y + 57;
      int cardW = 112;
      int cardH = 151;
      int gap = 12;
      if (lives.isEmpty()) {
         graphics.drawCenteredString(this.font, Component.literal("No archived lives"), x + this.imageWidth / 2, y + 128, -7371094);
      }

      for (int i = 0; i < 4; i++) {
         int index = this.lifeScroll + i;
         if (index >= lives.size()) {
            break;
         }

         CompoundTag life = lives.get(index);
         int cx = baseX + i * (cardW + gap);
         this.drawLifeCard(graphics, life, cx, baseY, cardW, cardH, inside(mouseX, mouseY, cx, baseY, cardW, cardH), this.isSelected(life), mouseX, mouseY);
      }

      this.drawHorizontalScroll(graphics, x + 43, y + 213, 434, this.lifeScroll, lives.size(), 4, mouseX, mouseY);
      this.renderSortBar(graphics, mouseX, mouseY);
   }

   private void drawLifeCard(GuiGraphics graphics, CompoundTag life, int x, int y, int w, int h, boolean hovered, boolean selected, int mouseX, int mouseY) {
      graphics.fill(x, y, x + w, y + h, selected ? -903733953 : (hovered ? -988406991 : -1190391776));
      this.outline(graphics, x, y, x + w, y + h, selected ? -4346113 : (hovered ? -9017639 : -13095066));
      graphics.fill(x + 3, y + 3, x + w - 3, y + 23, 2114258705);
      this.drawSmall(graphics, trim(life.getString("Name"), 13), x + 8, y + 8, selected ? -10639 : -1185537);
      this.drawTrash(graphics, x + w - 19, y + 7, inside(mouseX, mouseY, x + w - 24, y + 2, 22, 22));
      graphics.enableScissor(x + 6, y + 28, x + w - 6, y + h - 30);
      this.renderPlayerModel(graphics, x + w / 2, y + h - 35, 48, mouseX, mouseY);
      graphics.disableScissor();
      graphics.hLine(x + 8, x + w - 9, y + 27, 1699495065);
      graphics.hLine(x + 8, x + w - 9, y + h - 29, 1699495065);
      this.drawSmall(graphics, "EP " + shortNum(life.getDouble("EP")), x + 8, y + h - 24, -8519744);
      this.drawSmall(graphics, "+" + shortNum(life.getLong("KarmaGained")), x + 8, y + h - 13, -10639);
   }

   private void renderDetail(GuiGraphics graphics, int mouseX, int mouseY) {
      this.searchBox.visible = false;
      this.searchBox.active = false;
      CompoundTag life = this.selectedLife();
      int x = this.leftPos;
      int y = this.topPos;
      if (life == null) {
         this.detailOpen = false;
      } else {
         this.renameBox.visible = true;
         this.renameBox.active = true;
         if (!this.renameBox.isFocused() && !this.renameBox.getValue().equals(life.getString("Name"))) {
            this.renameBox.setValue(life.getString("Name"));
            this.lastRenameValue = this.renameBox.getValue();
         }

         this.drawButton(graphics, x + 22, y + 27, 24, 18, "‹", inside(mouseX, mouseY, x + 22, y + 27, 24, 18));
         this.drawFrame(graphics, x + 165, y + 25, 188, 22, this.renameBox.isFocused());
         this.drawButton(graphics, x + 361, y + 26, 23, 19, "✓", inside(mouseX, mouseY, x + 361, y + 26, 23, 19));
         this.drawButton(graphics, x + 391, y + 26, 23, 19, "×", inside(mouseX, mouseY, x + 391, y + 26, 23, 19));
         graphics.drawString(this.font, "Karma " + shortNum(((NirvanaMenu)this.menu).getKarma()), x + 426, y + 31, -10639, false);
         this.panel(graphics, x + 20, y + 56, 157, 176);
         graphics.enableScissor(x + 25, y + 84, x + 172, y + 220);
         this.renderPlayerModel(graphics, x + 98, y + 207, 70, mouseX, mouseY);
         graphics.disableScissor();
         graphics.drawCenteredString(this.font, trim(life.getString("Race"), 21), x + 98, y + 67, -1185537);
         graphics.drawCenteredString(this.font, "EP " + shortNum(life.getDouble("EP")), x + 98, y + 79, -8519744);
         this.panel(graphics, x + 188, y + 56, 312, 176);
         this.drawSmall(graphics, "Time " + shortNum(life.getLong("TimeKarma")), x + 200, y + 66, -7371094);
         this.drawSmall(graphics, "EP " + shortNum(life.getLong("EPKarma")), x + 266, y + 66, -7371094);
         this.drawSmall(graphics, "Ach " + shortNum(life.getLong("AchievementKarma")), x + 321, y + 66, -7371094);
         this.drawSmall(graphics, "Boss " + shortNum(life.getLong("BossKarma")), x + 416, y + 66, -7371094);
         this.renderSkillPanel(graphics, mouseX, mouseY, life, x + 200, y + 84);
      }
   }

   private void renderSkillPanel(GuiGraphics graphics, int mouseX, int mouseY, CompoundTag life, int x, int y) {
      ListTag skills = life.getList("Skills", 10);

      for (int i = 0; i < 8; i++) {
         int index = this.skillScroll + i;
         if (index >= skills.size()) {
            break;
         }

         CompoundTag skill = skills.getCompound(index);
         int rowY = y + i * 17;
         boolean hovered = inside(mouseX, mouseY, x, rowY, 211, 14);
         boolean selected = index == this.selectedSkill;
         graphics.fill(x, rowY, x + 211, rowY + 14, selected ? -903733953 : (hovered ? -988406991 : 1343297060));
         this.outline(graphics, x, rowY, x + 211, rowY + 14, selected ? -4346113 : -14935499);
         this.drawSmall(graphics, trim(skill.getString("Name"), 24), x + 5, rowY + 3, selected ? -10639 : -1185537);
         this.drawSmall(graphics, trim(skill.getString("Type"), 9), x + 153, rowY + 3, -7371094);
      }

      this.drawVerticalScroll(graphics, x + 215, y, 133, this.skillScroll, skills.size(), 8);
      CompoundTag skill = this.selectedSkillTag();
      int modeX = x + 224;
      if (skill != null) {
         ListTag modes = skill.getList("ModeList", 10);
         this.drawSmall(graphics, "Modes", modeX, y, -7939585);

         for (int i = 0; i < Math.min(5, modes.size()); i++) {
            CompoundTag mode = modes.getCompound(i);
            int my = y + 16 + i * 20;
            int modeId = mode.getInt("Mode");
            boolean hovered = inside(mouseX, mouseY, modeX, my, 72, 17);
            boolean selected = this.selectedMode == modeId;
            boolean unlocked = ((NirvanaMenu)this.menu).isUnlocked(life, skill.getString("Skill"), modeId);
            graphics.fill(modeX, my, modeX + 72, my + 17, selected ? -903733953 : (hovered ? -988406991 : -1190391776));
            this.outline(graphics, modeX, my, modeX + 72, my + 17, selected ? -4346113 : -14935499);
            this.drawSmall(graphics, trim(mode.getString("ModeName"), 9), modeX + 4, my + 5, unlocked ? -8519744 : -1185537);
         }

         boolean canUnlock = !this.isSelectedModeUnlocked();
         this.drawButton(graphics, modeX, y + 132, 72, 20, canUnlock ? "Migrate" : "Owned", canUnlock && inside(mouseX, mouseY, modeX, y + 132, 72, 20));
      }
   }

   public static void showMigrationPopup(boolean success, String title, String body) {
      popupSuccess = success;
      popupTitle = title == null ? "" : title;
      popupBody = body == null ? "" : body;
      popupTicks = 120;
      if (activeScreen == null && Minecraft.getInstance().player != null) {
         Minecraft.getInstance().player.displayClientMessage(Component.literal(popupTitle + ": " + popupBody), true);
      }
   }

   private void renderMigrationPopup(GuiGraphics graphics) {
      if (popupTicks > 0 && !popupTitle.isEmpty()) {
         popupTicks--;
         int x = this.leftPos + 104;
         int y = this.topPos + 47;
         int w = 312;
         int h = 38;
         float pulse = 0.5F + 0.5F * Mth.sin((120 - popupTicks) * 0.22F);
         int edge = popupSuccess ? mix(-8519744, -1, pulse) : mix(-39065, -10639, pulse);
         int face = popupSuccess ? -502655200 : -501414128;
         graphics.fill(x - 2, y - 2, x + w + 2, y + h + 2, -1442840576);
         graphics.fill(x, y, x + w, y + h, face);
         this.outline(graphics, x, y, x + w, y + h, edge);
         graphics.fill(x + 3, y + 3, x + w - 3, y + 5, edge);
         graphics.drawCenteredString(
            this.font, popupSuccess ? "✦ " + popupTitle + " ✦" : "✧ " + popupTitle + " ✧", x + w / 2, y + 8, popupSuccess ? -8519744 : -39065
         );
         graphics.drawCenteredString(this.font, trim(popupBody, 42), x + w / 2, y + 23, -1185537);
      }
   }

   public void removed() {
      super.removed();
      if (activeScreen == this) {
         activeScreen = null;
      }
   }

   private void renderSortBar(GuiGraphics graphics, int mouseX, int mouseY) {
      int x = this.leftPos + 87;
      int y = this.topPos + 234;
      this.drawSortButton(graphics, NirvanaData.Sort.NEWEST, "Newest", x, y, 58, mouseX, mouseY);
      this.drawSortButton(graphics, NirvanaData.Sort.OLDEST, "Oldest", x + 64, y, 58, mouseX, mouseY);
      this.drawSortButton(graphics, NirvanaData.Sort.MOST_KARMA, "Karma", x + 128, y, 58, mouseX, mouseY);
      this.drawSortButton(graphics, NirvanaData.Sort.MOST_EP, "EP", x + 192, y, 44, mouseX, mouseY);
      this.drawSortButton(graphics, NirvanaData.Sort.NAME, "Name", x + 242, y, 58, mouseX, mouseY);
   }

   private void drawSortButton(GuiGraphics graphics, NirvanaData.Sort sort, String text, int x, int y, int w, int mouseX, int mouseY) {
      boolean selected = ((NirvanaMenu)this.menu).getSort() == sort;
      boolean hovered = inside(mouseX, mouseY, x, y, w, 14);
      graphics.fill(x, y, x + w, y + 14, selected ? -903733953 : (hovered ? -988406991 : 1896879140));
      this.outline(graphics, x, y, x + w, y + 14, selected ? -4346113 : -14935499);
      graphics.drawCenteredString(this.font, text, x + w / 2, y + 3, selected ? -10639 : -7371094);
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button != 0) {
         return super.mouseClicked(mouseX, mouseY, button);
      }

      if (inside(mouseX, mouseY, this.leftPos + this.imageWidth - 39, this.topPos + 13, 13, 13)) {
         this.onClose();
         return true;
      }

      if (!this.detailOpen && inside(mouseX, mouseY, this.leftPos + 151, this.topPos + 25, 218, 22)) {
         return super.mouseClicked(mouseX, mouseY, button);
      }

      if (this.detailOpen && inside(mouseX, mouseY, this.leftPos + 165, this.topPos + 25, 188, 22)) {
         return super.mouseClicked(mouseX, mouseY, button);
      }

      boolean handled = this.detailOpen ? this.clickDetail(mouseX, mouseY) : this.clickOverview(mouseX, mouseY);
      return handled ? true : super.mouseClicked(mouseX, mouseY, button);
   }

   private boolean clickOverview(double mouseX, double mouseY) {
      int x = this.leftPos;
      int y = this.topPos;
      List<CompoundTag> lives = this.filteredLives();
      int baseX = x + 29;
      int baseY = y + 57;
      int cardW = 112;
      int cardH = 151;
      int gap = 12;

      for (int i = 0; i < 4; i++) {
         int index = this.lifeScroll + i;
         if (index >= lives.size()) {
            break;
         }

         CompoundTag life = lives.get(index);
         int cx = baseX + i * (cardW + gap);
         if (inside(mouseX, mouseY, cx + cardW - 24, baseY + 2, 22, 22)) {
            this.deleteLife(life);
            return true;
         }

         if (inside(mouseX, mouseY, cx, baseY, cardW, cardH)) {
            this.selectLife(life);
            this.detailOpen = true;
            this.skillScroll = 0;
            this.selectedSkill = 0;
            this.selectedMode = 0;
            this.renameBox.setValue(life.getString("Name"));
            this.lastRenameValue = life.getString("Name");
            return true;
         }
      }

      if (inside(mouseX, mouseY, x + 17, y + 211, 20, 17)) {
         this.lifeScroll = Math.max(0, this.lifeScroll - 1);
         return true;
      } else if (inside(mouseX, mouseY, x + 483, y + 211, 20, 17)) {
         this.lifeScroll = Math.min(this.maxLifeScroll(), this.lifeScroll + 1);
         return true;
      } else {
         NirvanaData.Sort clickedSort = this.sortAt(mouseX, mouseY);
         if (clickedSort != null) {
            NetworkManager.sendToServer(NirvanaActionPayload.sort(clickedSort));
            return true;
         } else if (inside(mouseX, mouseY, x + 43, y + 213, 434, 9)) {
            this.draggingLives = true;
            this.updateLifeScrollFromMouse(mouseX);
            return true;
         } else {
            return false;
         }
      }
   }

   private boolean clickDetail(double mouseX, double mouseY) {
      int x = this.leftPos;
      int y = this.topPos;
      CompoundTag life = this.selectedLife();
      if (life == null) {
         this.detailOpen = false;
         return true;
      }

      if (inside(mouseX, mouseY, x + 22, y + 27, 24, 18)) {
         this.detailOpen = false;
         this.setFocused(this.searchBox);
         return true;
      }

      if (inside(mouseX, mouseY, x + 361, y + 26, 23, 19)) {
         this.renameLife(life, true);
         return true;
      }

      if (inside(mouseX, mouseY, x + 391, y + 26, 23, 19)) {
         this.deleteLife(life);
         this.detailOpen = false;
         return true;
      }

      int sx = x + 200;
      int sy = y + 84;
      ListTag skills = life.getList("Skills", 10);

      for (int i = 0; i < 8; i++) {
         int index = this.skillScroll + i;
         if (index >= skills.size()) {
            break;
         }

         if (inside(mouseX, mouseY, sx, sy + i * 17, 211, 14)) {
            this.selectedSkill = index;
            this.selectedMode = 0;
            return true;
         }
      }

      CompoundTag skill = this.selectedSkillTag();
      int modeX = sx + 224;
      if (skill != null) {
         ListTag modes = skill.getList("ModeList", 10);

         for (int i = 0; i < Math.min(5, modes.size()); i++) {
            int my = sy + 16 + i * 20;
            if (inside(mouseX, mouseY, modeX, my, 72, 17)) {
               this.selectedMode = modes.getCompound(i).getInt("Mode");
               return true;
            }
         }

         if (inside(mouseX, mouseY, modeX, sy + 132, 72, 20)) {
            this.unlockSelected();
            return true;
         }
      }

      return false;
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
      if (this.draggingLives) {
         this.updateLifeScrollFromMouse(mouseX);
         return true;
      } else {
         return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
      }
   }

   private void updateLifeScrollFromMouse(double mouseX) {
      int total = this.filteredLives().size();
      int max = Math.max(0, total - 4);
      if (max <= 0) {
         this.lifeScroll = 0;
      } else {
         int x = this.leftPos + 43;
         int width = 434;
         int barW = Math.max(42, width * 4 / Math.max(1, total));
         double usable = Math.max(1.0, width - barW);
         double local = Mth.clamp(mouseX - x - barW / 2.0, 0.0, usable);
         this.lifeScroll = Mth.clamp((int)Math.round(local / usable * max), 0, max);
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      this.draggingLives = false;
      return super.mouseReleased(mouseX, mouseY, button);
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
      if (!this.detailOpen) {
         this.lifeScroll = Mth.clamp(this.lifeScroll - (int)Math.signum(scrollY), 0, this.maxLifeScroll());
         return true;
      } else {
         CompoundTag life = this.selectedLife();
         if (life != null && inside(mouseX, mouseY, this.leftPos + 188, this.topPos + 56, 312, 176)) {
            this.skillScroll = Mth.clamp(this.skillScroll - (int)Math.signum(scrollY), 0, Math.max(0, life.getList("Skills", 10).size() - 8));
            return true;
         } else {
            return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
         }
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.minecraft != null && this.minecraft.options.keyInventory.matches(keyCode, scanCode)) {
         return true;
      }

      if ((keyCode == 257 || keyCode == 335) && this.detailOpen && this.renameBox.isFocused()) {
         CompoundTag life = this.selectedLife();
         if (life != null) {
            this.renameLife(life, true);
            this.renameBox.setFocused(false);
            this.setFocused(null);
            return true;
         }
      }

      if (keyCode == 256 && this.detailOpen) {
         this.detailOpen = false;
         this.setFocused(this.searchBox);
         return true;
      } else if (this.searchBox != null && this.searchBox.isFocused()) {
         return super.keyPressed(keyCode, scanCode, modifiers);
      } else {
         return this.renameBox != null && this.renameBox.isFocused() ? super.keyPressed(keyCode, scanCode, modifiers) : true;
      }
   }

   public boolean charTyped(char codePoint, int modifiers) {
      if (this.searchBox != null && this.searchBox.isFocused()) {
         return super.charTyped(codePoint, modifiers);
      } else {
         return this.renameBox != null && this.renameBox.isFocused() ? super.charTyped(codePoint, modifiers) : true;
      }
   }

   public boolean shouldCloseOnEsc() {
      return false;
   }

   private void selectLife(CompoundTag life) {
      if (life != null && life.hasUUID("Id")) {
         this.selectedLifeId = life.getUUID("Id");
      }
   }

   private CompoundTag selectedLife() {
      if (this.selectedLifeId != null) {
         for (CompoundTag life : ((NirvanaMenu)this.menu).getLives()) {
            if (life.hasUUID("Id") && this.selectedLifeId.equals(life.getUUID("Id"))) {
               return life;
            }
         }
      }

      return null;
   }

   private boolean isSelected(CompoundTag life) {
      return life != null && life.hasUUID("Id") && this.selectedLifeId != null && this.selectedLifeId.equals(life.getUUID("Id"));
   }

   private CompoundTag selectedSkillTag() {
      CompoundTag life = this.selectedLife();
      if (life == null) {
         return null;
      }

      ListTag skills = life.getList("Skills", 10);
      return this.selectedSkill >= 0 && this.selectedSkill < skills.size() ? skills.getCompound(this.selectedSkill) : null;
   }

   private void renameLife(CompoundTag life, boolean force) {
      if (life != null && life.hasUUID("Id")) {
         String value = this.renameBox.getValue();
         life.putString("Name", value);
         if (force || !value.equals(this.lastRenameValue)) {
            this.lastRenameValue = value;
            NetworkManager.sendToServer(NirvanaActionPayload.rename(((NirvanaMenu)this.menu).getSort(), life.getUUID("Id"), value));
         }
      }
   }

   private void deleteLife(CompoundTag life) {
      if (life != null && life.hasUUID("Id")) {
         UUID id = life.getUUID("Id");
         ((NirvanaMenu)this.menu).getLives().removeIf(tag -> tag.hasUUID("Id") && tag.getUUID("Id").equals(id));
         if (id.equals(this.selectedLifeId)) {
            this.selectedLifeId = null;
            this.detailOpen = false;
         }

         this.lifeScroll = Mth.clamp(this.lifeScroll, 0, this.maxLifeScroll());
         NetworkManager.sendToServer(NirvanaActionPayload.delete(((NirvanaMenu)this.menu).getSort(), id));
      }
   }

   private void unlockSelected() {
      CompoundTag life = this.selectedLife();
      CompoundTag skill = this.selectedSkillTag();
      if (life != null && skill != null && life.hasUUID("Id")) {
         ResourceLocation skillId = ResourceLocation.tryParse(skill.getString("Skill"));
         if (skillId != null) {
            NetworkManager.sendToServer(NirvanaActionPayload.unlock(((NirvanaMenu)this.menu).getSort(), life.getUUID("Id"), skillId, this.selectedMode));
         }
      }
   }

   private boolean isSelectedModeUnlocked() {
      CompoundTag life = this.selectedLife();
      CompoundTag skill = this.selectedSkillTag();
      return life != null && skill != null && ((NirvanaMenu)this.menu).isUnlocked(life, skill.getString("Skill"), this.selectedMode);
   }

   private List<CompoundTag> filteredLives() {
      String query = this.searchBox == null ? "" : this.searchBox.getValue().strip().toLowerCase(Locale.ROOT);
      if (query.isEmpty()) {
         return ((NirvanaMenu)this.menu).getLives();
      }

      List<CompoundTag> result = new ArrayList<>();

      for (CompoundTag life : ((NirvanaMenu)this.menu).getLives()) {
         String name = life.getString("Name").toLowerCase(Locale.ROOT);
         String race = life.getString("Race").toLowerCase(Locale.ROOT);
         if (name.contains(query) || race.contains(query) || this.lifeHasSkill(life, query)) {
            result.add(life);
         }
      }

      return result;
   }

   private boolean lifeHasSkill(CompoundTag life, String query) {
      ListTag skills = life.getList("Skills", 10);

      for (int i = 0; i < skills.size(); i++) {
         CompoundTag skill = skills.getCompound(i);
         if (skill.getString("Name").toLowerCase(Locale.ROOT).contains(query) || skill.getString("Skill").toLowerCase(Locale.ROOT).contains(query)) {
            return true;
         }
      }

      return false;
   }

   private void clampScrolls() {
      this.lifeScroll = Mth.clamp(this.lifeScroll, 0, this.maxLifeScroll());
      CompoundTag life = this.selectedLife();
      if (life != null) {
         this.skillScroll = Mth.clamp(this.skillScroll, 0, Math.max(0, life.getList("Skills", 10).size() - 8));
      } else {
         this.skillScroll = 0;
      }
   }

   private int maxLifeScroll() {
      return Math.max(0, this.filteredLives().size() - 4);
   }

   private NirvanaData.Sort sortAt(double mouseX, double mouseY) {
      int x = this.leftPos + 87;
      int y = this.topPos + 234;
      if (inside(mouseX, mouseY, x, y, 58, 14)) {
         return NirvanaData.Sort.NEWEST;
      } else if (inside(mouseX, mouseY, x + 64, y, 58, 14)) {
         return NirvanaData.Sort.OLDEST;
      } else if (inside(mouseX, mouseY, x + 128, y, 58, 14)) {
         return NirvanaData.Sort.MOST_KARMA;
      } else if (inside(mouseX, mouseY, x + 192, y, 44, 14)) {
         return NirvanaData.Sort.MOST_EP;
      } else {
         return inside(mouseX, mouseY, x + 242, y, 58, 14) ? NirvanaData.Sort.NAME : null;
      }
   }

   private void renderPlayerModel(GuiGraphics graphics, int x, int y, int scale, int mouseX, int mouseY) {
      if (Minecraft.getInstance().player != null) {
         InventoryScreen.renderEntityInInventoryFollowsMouse(
            graphics, x - 30, y - 102, x + 30, y + 7, scale, 0.0625F, mouseX, mouseY, Minecraft.getInstance().player
         );
      }
   }

   private void panel(GuiGraphics graphics, int x, int y, int w, int h) {
      graphics.fill(x, y, x + w, y + h, -1810885340);
      this.outline(graphics, x, y, x + w, y + h, -14935499);
   }

   private void drawFrame(GuiGraphics graphics, int x, int y, int w, int h, boolean focused) {
      graphics.fill(x, y, x + w, y + h, -1962538989);
      this.outline(graphics, x, y, x + w, y + h, focused ? -4346113 : -13095066);
   }

   private void drawButton(GuiGraphics graphics, int x, int y, int w, int h, String text, boolean hovered) {
      graphics.fill(x, y, x + w, y + h, hovered ? -988406991 : 1980765220);
      this.outline(graphics, x, y, x + w, y + h, hovered ? -4346113 : -14935499);
      graphics.drawCenteredString(this.font, text, x + w / 2, y + (h - 8) / 2, hovered ? -10639 : -1185537);
   }

   private void drawCloseButton(GuiGraphics graphics, int mouseX, int mouseY) {
      int x = this.leftPos + this.imageWidth - 39;
      int y = this.topPos + 13;
      boolean hovered = inside(mouseX, mouseY, x, y, 13, 13);
      graphics.fill(x, y, x + 13, y + 13, hovered ? -1962973337 : 1242567716);
      this.outline(graphics, x, y, x + 13, y + 13, hovered ? -39065 : -14935499);
      graphics.drawCenteredString(this.font, "×", x + 6, y + 2, hovered ? -1 : -7371094);
   }

   private void drawTrash(GuiGraphics graphics, int x, int y, boolean hovered) {
      int c = hovered ? -39065 : -7371094;
      graphics.hLine(x + 2, x + 11, y + 2, c);
      graphics.hLine(x + 4, x + 9, y + 4, c);
      graphics.vLine(x + 4, y + 5, y + 13, c);
      graphics.vLine(x + 10, y + 5, y + 13, c);
      graphics.hLine(x + 4, x + 10, y + 13, c);
      graphics.vLine(x + 6, y + 6, y + 11, c);
      graphics.vLine(x + 8, y + 6, y + 11, c);
   }

   private void drawHorizontalScroll(GuiGraphics graphics, int x, int y, int width, int scroll, int total, int visible, int mouseX, int mouseY) {
      this.drawButton(graphics, this.leftPos + 17, y - 2, 20, 17, "‹", inside(mouseX, mouseY, this.leftPos + 17, y - 2, 20, 17));
      this.drawButton(graphics, this.leftPos + 483, y - 2, 20, 17, "›", inside(mouseX, mouseY, this.leftPos + 483, y - 2, 20, 17));
      graphics.fill(x, y + 5, x + width, y + 7, 1882073176);
      if (total <= visible) {
         graphics.fill(x, y + 4, x + width, y + 8, -13095066);
      } else {
         int barW = Math.max(42, width * visible / total);
         int max = total - visible;
         int barX = x + (width - barW) * scroll / Math.max(1, max);
         graphics.fill(barX, y + 3, barX + barW, y + 9, -4346113);
      }
   }

   private void drawVerticalScroll(GuiGraphics graphics, int x, int y, int height, int scroll, int total, int visible) {
      graphics.fill(x, y, x + 2, y + height, 1882073176);
      if (total > visible) {
         int barH = Math.max(18, height * visible / total);
         int max = total - visible;
         int barY = y + (height - barH) * scroll / Math.max(1, max);
         graphics.fill(x, barY, x + 2, barY + barH, -4346113);
      }
   }

   private void outline(GuiGraphics graphics, int left, int top, int right, int bottom, int color) {
      graphics.hLine(left, right - 1, top, color);
      graphics.hLine(left, right - 1, bottom - 1, color);
      graphics.vLine(left, top, bottom - 1, color);
      graphics.vLine(right - 1, top, bottom - 1, color);
   }

   private void drawSmall(GuiGraphics graphics, String text, int x, int y, int color) {
      graphics.drawString(this.font, text, x, y, color, false);
   }

   private static boolean inside(double mx, double my, int x, int y, int w, int h) {
      return mx >= x && mx < x + w && my >= y && my < y + h;
   }

   private static String trim(String value, int max) {
      if (value == null) {
         return "";
      } else {
         return value.length() <= max ? value : value.substring(0, Math.max(0, max - 1)) + "…";
      }
   }

   private static String shortNum(double value) {
      if (value >= 1000000.0) {
         return String.format(Locale.ROOT, "%.1fM", value / 1000000.0);
      } else {
         return value >= 1000.0 ? String.format(Locale.ROOT, "%.1fk", value / 1000.0) : String.valueOf((long)value);
      }
   }

   private int pulse(int a, int b, float speed) {
      int tick = Minecraft.getInstance().player == null ? 0 : Minecraft.getInstance().player.tickCount;
      return mix(a, b, 0.5F + 0.5F * Mth.sin(tick * speed));
   }

   private static int mix(int a, int b, float t) {
      t = Mth.clamp(t, 0.0F, 1.0F);
      int aa = a >>> 24 & 0xFF;
      int ar = a >> 16 & 0xFF;
      int ag = a >> 8 & 0xFF;
      int ab = a & 0xFF;
      int ba = b >>> 24 & 0xFF;
      int br = b >> 16 & 0xFF;
      int bg = b >> 8 & 0xFF;
      int bb = b & 0xFF;
      int alpha = (int)(aa + (ba - aa) * t);
      int r = (int)(ar + (br - ar) * t);
      int g = (int)(ag + (bg - ag) * t);
      int bl = (int)(ab + (bb - ab) * t);
      return alpha << 24 | r << 16 | g << 8 | bl;
   }
}
