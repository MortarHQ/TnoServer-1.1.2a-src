package com.github.wal_bos.moreskills.client.screen;

import com.github.wal_bos.moreskills.ability.skill.unique.MitamaFaithSkill;
import com.github.wal_bos.moreskills.menu.MitamaOfferingMenu;
import com.github.wal_bos.moreskills.net.msg.MitamaOfferingActionPayload;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.util.client.RenderHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.Nullable;

public class MitamaOfferingScreen extends AbstractContainerScreen<MitamaOfferingMenu> {
   private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/skill_creation/skill_creation.png");
   private static final ResourceLocation ABILITY_BAR = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/ability_button.png");
   private static final ResourceLocation SCROLL_BAR = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/scroll_bar.png");
   private static final ResourceLocation FALLBACK_ICON = ResourceLocation.fromNamespaceAndPath("tensura", "textures/temp_textures/item/confused_rimuru.png");
   private static final int VISIBLE_ROWS = 7;
   private static final int LIST_X = 6;
   private static final int LIST_Y = 43;
   private static final int ROW_W = 89;
   private static final int ROW_H = 13;
   private static final int SCROLL_X = 98;
   private static final int SCROLL_Y = 43;
   private static final int SCROLL_TOTAL = 91;
   private static final int DETAIL_X = 125;
   private static final int DETAIL_Y = 48;
   private static final int DETAIL_W = 94;
   private static final int DETAIL_H = 60;
   private final List<AbstractWidget> dynamicWidgets = new ArrayList<>();
   private final List<MitamaFaithSkill.MitamaOfferingEntry> filtered = new ArrayList<>();
   private EditBox searchField;
   private EditBox amountField;
   private MitamaFaithSkill.MitamaOfferingEntry selectedEntry;
   private MitamaFaithSkill.MitamaOfferingStat selectedStat = MitamaFaithSkill.MitamaOfferingStat.MAGICULE;
   private float scrollOffset;
   private boolean scrolling;
   private int listStartIndex;
   private int textStartIndex;
   private String nameFilter = "";

   public MitamaOfferingScreen(MitamaOfferingMenu menu, Inventory inventory, Component title) {
      super(menu, inventory, Component.literal(menuTitle(menu.getKind())));
      this.imageWidth = 233;
      this.imageHeight = 140;
      this.inventoryLabelY = 10000;
      this.titleLabelY = 10000;
   }

   protected void init() {
      super.init();
      this.scrollOffset = 0.0F;
      this.scrolling = false;
      this.listStartIndex = 0;
      this.textStartIndex = 0;
      this.searchField = new EditBox(this.font, this.leftPos + 19, this.topPos + 27, 79, 9, Component.empty());
      this.searchField.setBordered(false);
      this.searchField.setMaxLength(64);
      this.searchField.setResponder(value -> {
         this.nameFilter = value == null ? "" : value;
         this.scrollOffset = 0.0F;
         this.listStartIndex = 0;
         this.updateFilteredSkills();
      });
      this.searchField.setValue(this.nameFilter == null ? "" : this.nameFilter);
      this.addRenderableWidget(this.searchField);
      this.amountField = new EditBox(this.font, this.leftPos + 125, this.topPos + 158, 42, 12, Component.empty());
      this.amountField.setBordered(true);
      this.amountField.setMaxLength(32);
      this.amountField.setValue(String.valueOf((long)Math.max(1.0, this.getAvailable(this.selectedStat))));
      this.addRenderableWidget(this.amountField);
      this.updateFilteredSkills();
      this.rebuildMitamaWidgets();
   }

   private static String menuTitle(MitamaFaithSkill.MitamaMenuKind kind) {
      return switch (kind) {
         case CREATE -> "Faith Skill Creation";
         case GIFT -> "Mitama Bestowal";
         case DONATE -> "Mitama Offering";
      };
   }

   private void rebuildMitamaWidgets() {
      this.clearDynamicWidgets();
      boolean transfer = ((MitamaOfferingMenu)this.menu).getKind() != MitamaFaithSkill.MitamaMenuKind.CREATE;
      this.amountField.visible = transfer;
      this.amountField.active = transfer;
      if (transfer) {
         int y = this.topPos + 146;
         int x = this.leftPos + 125;

         for (MitamaFaithSkill.MitamaOfferingStat stat : MitamaFaithSkill.MitamaOfferingStat.values()) {
            int w = stat == MitamaFaithSkill.MitamaOfferingStat.AURA ? 30 : (stat == MitamaFaithSkill.MitamaOfferingStat.SOULS ? 28 : 22);
            Button b = this.addDyn(Button.builder(Component.literal(shortStatLabel(stat)), button -> {
               this.selectedStat = stat;
               this.setAmountMax();
               this.rebuildMitamaWidgets();
            }).bounds(x, y, w, 11).build());
            b.active = stat != this.selectedStat;
            x += w + 2;
         }

         this.addDyn(Button.builder(Component.literal("MAX"), bx -> this.setAmountMax()).bounds(this.leftPos + 170, this.topPos + 158, 28, 12).build());
         this.addDyn(
            Button.builder(Component.literal(this.statActionLabel()), bx -> this.sendStat()).bounds(this.leftPos + 200, this.topPos + 158, 34, 12).build()
         );
      }
   }

   private static String shortStatLabel(MitamaFaithSkill.MitamaOfferingStat stat) {
      return switch (stat) {
         case MAGICULE -> "MP";
         case AURA -> "Aura";
         case EP -> "EP";
         case SOULS -> "Soul";
      };
   }

   private Button addDyn(Button button) {
      this.dynamicWidgets.add(button);
      return (Button)this.addRenderableWidget(button);
   }

   private void clearDynamicWidgets() {
      for (AbstractWidget widget : this.dynamicWidgets) {
         this.removeWidget(widget);
      }

      this.dynamicWidgets.clear();
   }

   private void updateFilteredSkills() {
      this.filtered.clear();
      String needle = this.nameFilter == null ? "" : this.nameFilter.trim().toLowerCase(Locale.ROOT);

      for (MitamaFaithSkill.MitamaOfferingEntry entry : ((MitamaOfferingMenu)this.menu).getEntries()) {
         if (needle.isEmpty()
            || entry.name().toLowerCase(Locale.ROOT).contains(needle)
            || entry.id().toString().toLowerCase(Locale.ROOT).contains(needle)
            || entry.type().toLowerCase(Locale.ROOT).contains(needle)) {
            this.filtered.add(entry);
         }
      }

      if (this.selectedEntry != null && !this.filtered.contains(this.selectedEntry)) {
         this.selectedEntry = null;
      }

      this.scrolledScrollBar(0.0);
   }

   private void setAmountMax() {
      double available = Math.max(0.0, this.getAvailable(this.selectedStat));
      this.amountField.setValue(String.valueOf(Math.max(1L, (long)Math.floor(available))));
   }

   private double getAvailable(MitamaFaithSkill.MitamaOfferingStat stat) {
      return switch (stat) {
         case MAGICULE -> ((MitamaOfferingMenu)this.menu).getMagicule();
         case AURA -> ((MitamaOfferingMenu)this.menu).getAura();
         case EP -> ((MitamaOfferingMenu)this.menu).getEp();
         case SOULS -> ((MitamaOfferingMenu)this.menu).getSouls();
      };
   }

   private double readAmount() {
      String raw = this.amountField.getValue().trim().replace(",", "");
      if (raw.isEmpty()) {
         return 0.0;
      }

      try {
         return Math.max(0.0, Double.parseDouble(raw));
      } catch (NumberFormatException ignored) {
         return 0.0;
      }
   }

   private void sendStat() {
      if (((MitamaOfferingMenu)this.menu).getKind() != MitamaFaithSkill.MitamaMenuKind.CREATE) {
         double amount = this.readAmount();
         if (!(amount <= 0.0)) {
            NetworkManager.sendToServer(
               MitamaOfferingActionPayload.stat(
                  ((MitamaOfferingMenu)this.menu).getKind(), ((MitamaOfferingMenu)this.menu).getTargetUuid(), this.selectedStat, amount
               )
            );
            if (this.minecraft != null && this.minecraft.player != null) {
               this.minecraft.player.closeContainer();
            }
         }
      }
   }

   private void sendSkill() {
      if (this.selectedEntry != null) {
         NetworkManager.sendToServer(
            MitamaOfferingActionPayload.skill(
               ((MitamaOfferingMenu)this.menu).getKind(), ((MitamaOfferingMenu)this.menu).getTargetUuid(), this.selectedEntry.id()
            )
         );
         if (this.minecraft != null && this.minecraft.player != null) {
            this.minecraft.player.closeContainer();
         }
      }
   }

   protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
      RenderHelper.drawCenteredText(graphics, this.font, this.title, 56, this.titleLabelY, 16777215, false);
   }

   protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
      g.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
      this.renderButtons(g, mouseX, mouseY);
      this.renderScrollBar(g, mouseX, mouseY);
      this.renderSelectedSkill(g, mouseX, mouseY);
      this.renderFaithText(g);
   }

   private void renderFaithText(GuiGraphics g) {
      String faith = MitamaFaithSkill.format(((MitamaOfferingMenu)this.menu).getWorship());
      int x = this.leftPos + 118;
      g.drawString(this.font, "Faith", x, this.topPos - 13, 16770458, false);
      g.drawString(this.font, faith, x, this.topPos - 3, 16770458, false);
      if (((MitamaOfferingMenu)this.menu).getKind() != MitamaFaithSkill.MitamaMenuKind.CREATE) {
         g.drawString(this.font, this.selectedStat.display(), x, this.topPos + 7, 10790052, false);
         g.drawString(this.font, MitamaFaithSkill.format(this.getAvailable(this.selectedStat)), x, this.topPos + 17, 10790052, false);
      } else {
         g.drawString(this.font, "Create", x, this.topPos + 7, 10790052, false);
         g.drawString(this.font, "Unique", x, this.topPos + 17, 10790052, false);
      }
   }

   private void renderButtons(GuiGraphics g, int mouseX, int mouseY) {
      int lastVisible = Math.min(this.listStartIndex + 7, this.filtered.size());

      for (int i = this.listStartIndex; i < lastVisible && i < this.filtered.size(); i++) {
         int bx = this.leftPos + 6;
         int by = this.topPos + 43 + (i - this.listStartIndex) * 13;
         boolean hovering = mouseX >= bx && mouseY >= by && mouseX < bx + 89 && mouseY < by + 13;
         boolean selected = this.filtered.get(i).equals(this.selectedEntry);
         int v = !hovering && !selected ? 0 : 13;
         g.blit(ABILITY_BAR, bx, by, 0.0F, v, 89, 13, 89, 26);
         MitamaFaithSkill.MitamaOfferingEntry entry = this.filtered.get(i);
         RenderHelper.drawShortenedTextWithTooltip(
            g,
            this.font,
            Component.literal(entry.name()),
            Component.literal(entry.id().toString()),
            bx,
            by,
            3,
            3,
            88,
            13,
            mouseX,
            mouseY,
            selected ? 16770458 : 16777215,
            true,
            this
         );
      }

      if (this.filtered.isEmpty()) {
         g.drawString(this.font, "No skills", this.leftPos + 10, this.topPos + 48, 16777215, false);
      }

      if (mouseX >= this.leftPos + 162
         && mouseX < this.leftPos + 182
         && mouseY >= this.topPos + 116
         && mouseY < this.topPos + 136
         && this.selectedEntry != null) {
         g.blit(BACKGROUND, this.leftPos + 162, this.topPos + 116, 0, this.imageHeight, 20, 20);
      }
   }

   private void renderScrollBar(GuiGraphics g, int mouseX, int mouseY) {
      boolean active = this.filtered.size() > 7;
      int height = 13;
      int yOffset = (int)(this.topPos + 43 + (91 - height) * this.scrollOffset);
      boolean hovered = active && RenderHelper.mouseOver(mouseX, mouseY, this.leftPos + 98, this.leftPos + 98 + 10, yOffset, yOffset + height);
      g.blit(SCROLL_BAR, this.leftPos + 98, yOffset, 0.0F, hovered ? height : 0.0F, 10, height, 10, 26);
   }

   private void renderSelectedSkill(GuiGraphics g, int mouseX, int mouseY) {
      if (this.selectedEntry == null) {
         g.drawString(
            this.font,
            ((MitamaOfferingMenu)this.menu).getKind() == MitamaFaithSkill.MitamaMenuKind.CREATE ? "Select creation" : "Select skill",
            this.leftPos + 125,
            this.topPos + 48,
            10790052,
            false
         );
      } else {
         ManasSkill skill = this.skillFromEntry(this.selectedEntry);
         ResourceLocation icon = this.iconFor(skill);
         g.blit(icon, this.leftPos + 156, this.topPos + 6, 0.0F, 0.0F, 32, 32, 32, 32);
         Component description = skill == null ? Component.literal(this.selectedEntry.id().toString()) : skill.getSkillDescription();
         List<FormattedCharSequence> sequences = this.font.split(description, 94);
         int descriptionStart = Mth.clamp(this.textStartIndex, 0, Math.max(0, sequences.size() - 7));
         int descriptionEnd = Math.min(descriptionStart + 7, sequences.size());
         this.textStartIndex = descriptionStart;
         boolean hoveringDescription = RenderHelper.mouseOver(
            mouseX, mouseY, this.leftPos + 125, this.leftPos + 125 + 94, this.topPos + 48, this.topPos + 48 + 60
         );
         RenderHelper.drawScrollableTextInAreaSetHighlight(
            g, this.font, sequences, this.leftPos + 125, this.topPos + 48, 94, 60, 0, descriptionStart, descriptionEnd, hoveringDescription, 8750469, 863042
         );
         if (mouseX > this.leftPos + 156 && mouseX < this.leftPos + 188 && mouseY > this.topPos + 6 && mouseY < this.topPos + 38) {
            MutableComponent hover = this.getSkillName(skill)
               .copy()
               .append("\n")
               .append(Component.literal(this.selectedEntry.id().toString()).withColor(5592405));
            this.setTooltipForNextRenderPass(hover);
         }
      }
   }

   protected void renderTooltip(GuiGraphics g, int x, int y) {
      if (x >= this.leftPos + 162 && x < this.leftPos + 182 && y >= this.topPos + 116 && y < this.topPos + 136 && this.selectedEntry != null) {
         g.renderTooltip(this.font, Component.literal(this.skillActionLabel()), x, y);
      }
   }

   private String skillActionLabel() {
      return switch (((MitamaOfferingMenu)this.menu).getKind()) {
         case CREATE -> "Create";
         case GIFT -> "Bestow";
         case DONATE -> "Take";
      };
   }

   private String statActionLabel() {
      return ((MitamaOfferingMenu)this.menu).getKind() == MitamaFaithSkill.MitamaMenuKind.GIFT ? "Give" : "Take";
   }

   @Nullable
   private ManasSkill skillFromEntry(MitamaFaithSkill.MitamaOfferingEntry entry) {
      return entry == null ? null : (ManasSkill)SkillAPI.getSkillRegistry().get(entry.id());
   }

   private ResourceLocation iconFor(@Nullable ManasSkill skill) {
      return skill instanceof TensuraSkill tensuraSkill && tensuraSkill.getSkillIcon() != null ? tensuraSkill.getSkillIcon() : FALLBACK_ICON;
   }

   private MutableComponent getSkillName(@Nullable ManasSkill skill) {
      if (skill != null && skill.getName() != null) {
         return skill instanceof TensuraSkill tensuraSkill && tensuraSkill.getColoredName() != null ? tensuraSkill.getColoredName() : skill.getName();
      } else {
         return this.selectedEntry == null ? Component.translatable("tensura.skill.empty") : Component.literal(this.selectedEntry.name());
      }
   }

   public boolean mouseClicked(double x, double y, int button) {
      if (this.clickedScrollBar(x, y)) {
         return true;
      }

      if (x >= this.leftPos + 162 && x < this.leftPos + 182 && y >= this.topPos + 116 && y < this.topPos + 136 && this.selectedEntry != null) {
         Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
         this.sendSkill();
         return true;
      }

      int lastDisplayed = Math.min(this.listStartIndex + 7, this.filtered.size());

      for (int i = this.listStartIndex; i < lastDisplayed; i++) {
         int bx = this.leftPos + 6;
         int by = this.topPos + 43 + (i - this.listStartIndex) * 13;
         if (x >= bx && y >= by && x < bx + 89 && y < by + 13) {
            this.selectedEntry = this.filtered.get(i);
            this.textStartIndex = 0;
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            return true;
         }
      }

      if (!this.searchField.isHovered()) {
         this.searchField.setFocused(false);
      }

      if (!this.amountField.isHovered()) {
         this.amountField.setFocused(false);
      }

      return super.mouseClicked(x, y, button);
   }

   private boolean clickedScrollBar(double mouseX, double mouseY) {
      if (this.filtered.size() <= 7) {
         return false;
      } else {
         int x = this.leftPos + 98;
         int y = this.topPos + 43;
         int height = 13;
         int yOffset = (int)(y + (91 - height) * this.scrollOffset);
         if (mouseX >= x && mouseX < x + 10 && mouseY >= yOffset && mouseY < yOffset + height) {
            this.scrolling = true;
            return true;
         } else if (mouseX >= x && mouseX < x + 10 && mouseY >= y && mouseY < y + 91) {
            this.scrolling = true;
            this.scrollOffset = (float)((mouseY - y - 6.5) / (91 - height));
            this.scrollOffset = Mth.clamp(this.scrollOffset, 0.0F, 1.0F);
            this.listStartIndex = (int)(this.scrollOffset * (this.filtered.size() - 7) + 0.5F);
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
      if (RenderHelper.mouseOver(mouseX, mouseY, this.leftPos + 125, this.leftPos + 125 + 94, this.topPos + 43, this.topPos + 109)) {
         this.textStartIndex = Math.max(this.textStartIndex + (int)(-deltaY), 0);
      } else {
         this.scrolledScrollBar(deltaY);
      }

      return super.mouseScrolled(mouseX, mouseY, deltaX, deltaY);
   }

   private void scrolledScrollBar(double delta) {
      if (this.filtered.size() > 7) {
         int offscreen = this.filtered.size() - 7;
         float f = (float)delta / offscreen;
         this.scrollOffset = Mth.clamp(this.scrollOffset - f, 0.0F, 1.0F);
         this.listStartIndex = Mth.clamp(this.listStartIndex - (int)delta, 0, offscreen);
      } else {
         this.scrollOffset = 0.0F;
         this.listStartIndex = 0;
      }
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
      if (this.scrolling && this.filtered.size() > 7) {
         int y = this.topPos + 43;
         int height = 13;
         this.scrollOffset = (float)((mouseY - y - 6.5) / (91 - height));
         this.scrollOffset = Mth.clamp(this.scrollOffset, 0.0F, 1.0F);
         this.listStartIndex = (int)(this.scrollOffset * (this.filtered.size() - 7) + 0.5F);
         return true;
      } else {
         return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      this.scrolling = false;
      return super.mouseReleased(mouseX, mouseY, button);
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.searchField.keyPressed(keyCode, scanCode, modifiers)) {
         return true;
      } else if (this.searchField.isFocused() && this.searchField.isVisible() && keyCode != 256) {
         return true;
      } else if (this.amountField.visible && this.amountField.keyPressed(keyCode, scanCode, modifiers)) {
         return true;
      } else {
         return this.amountField.visible && this.amountField.isFocused() && this.amountField.isVisible() && keyCode != 256
            ? true
            : this.minecraft != null && this.minecraft.options.keySwapOffhand.matches(keyCode, scanCode) || super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   public boolean charTyped(char codePoint, int modifiers) {
      if (this.searchField.charTyped(codePoint, modifiers)) {
         return true;
      } else {
         return this.amountField.visible && this.amountField.isFocused()
            ? Character.isDigit(codePoint) && this.amountField.charTyped(codePoint, modifiers)
            : super.charTyped(codePoint, modifiers);
      }
   }

   public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
      this.renderBackground(g, mouseX, mouseY, partialTick);
      super.render(g, mouseX, mouseY, partialTick);
      this.renderTooltip(g, mouseX, mouseY);
   }
}
