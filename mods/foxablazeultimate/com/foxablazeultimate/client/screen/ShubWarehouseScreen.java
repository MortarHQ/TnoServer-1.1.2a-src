package com.foxablazeultimate.client.screen;

import com.foxablazeultimate.network.OpenShubWarehousePayload;
import com.foxablazeultimate.network.RequestShubWarehouseActionPayload;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.client.screen.templates.IScrollBar;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

public class ShubWarehouseScreen extends Screen implements IScrollBar {
   private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "textures/gui/shub_warehouse.png");
   private static final ResourceLocation ABILITY_BAR = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/ability_button.png");
   private static final int IMAGE_WIDTH = 233;
   private static final int IMAGE_HEIGHT = 140;
   private static final int VISIBLE_ROWS = 7;
   private static final int INPUT_A_X = 132;
   private static final int INPUT_A_Y = 70;
   private static final int INPUT_SIZE = 20;
   private static final int INPUT_B_X = 196;
   private static final int INPUT_B_Y = 70;
   private static final int RESULT_X = 158;
   private static final int RESULT_Y = 8;
   private static final int RESULT_SIZE = 28;
   private static final int COPY_X = 128;
   private static final int COPY_Y = 116;
   private static final int BUTTON_SIZE = 20;
   private static final int SYNTH_X = 162;
   private static final int SYNTH_Y = 116;
   private static final int GIFT_X = 196;
   private static final int GIFT_Y = 116;
   private final int abilitySlot;
   private final List<ResourceLocation> allSkills;
   private final Set<ResourceLocation> giftOnlySkills;
   private final List<OpenShubWarehousePayload.RecipeEntry> recipes;
   private List<ResourceLocation> filteredSkills = List.of();
   private ResourceLocation selected;
   private ResourceLocation inputA;
   private ResourceLocation inputB;
   private ResourceLocation result;
   private ResourceLocation draggedSkill;
   private int leftPos;
   private int topPos;
   private int listStart;
   private float scrollOffset;
   private boolean scrolling;
   private EditBox searchField;

   public ShubWarehouseScreen(OpenShubWarehousePayload payload) {
      super(Component.translatable("foxablazeultimate.skill.mode.shub_niggurath.warehouse"));
      this.abilitySlot = payload.abilitySlot();
      this.allSkills = payload.skills();
      this.giftOnlySkills = Set.copyOf(payload.giftOnlySkills());
      this.recipes = payload.recipes();
      this.filteredSkills = this.allSkills;
   }

   protected void init() {
      this.leftPos = (this.width - 233) / 2;
      this.topPos = (this.height - 140) / 2;
      this.listStart = 0;
      this.scrollOffset = 0.0F;
      this.scrolling = false;
      this.searchField = new EditBox(this.font, this.leftPos + 19, this.topPos + 27, 79, 9, Component.empty());
      this.searchField.setBordered(false);
      this.searchField.setResponder(this::filterSkills);
      this.addRenderableWidget(this.searchField);
      this.filterSkills("");
   }

   private void filterSkills(String filter) {
      String normalized = filter == null ? "" : filter.toLowerCase(Locale.ROOT).trim();
      this.filteredSkills = normalized.isEmpty()
         ? this.allSkills
         : this.allSkills
            .stream()
            .filter(
               id -> getSkillName(id).getString().toLowerCase(Locale.ROOT).contains(normalized) || id.toString().toLowerCase(Locale.ROOT).contains(normalized)
            )
            .toList();
      this.setScrollOffset(0.0F);
      this.setListStartIndex(0);
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      this.renderTransparentBackground(graphics);
      graphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, 233, 140);
      this.renderSkillList(graphics, mouseX, mouseY);
      this.renderScrollBar(graphics, mouseX, mouseY);
      this.renderSlots(graphics);
      this.renderButtonHover(graphics, mouseX, mouseY);
      super.render(graphics, mouseX, mouseY, partialTick);
      this.renderTooltips(graphics, mouseX, mouseY);
   }

   public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
   }

   private void renderSkillList(GuiGraphics graphics, int mouseX, int mouseY) {
      graphics.drawCenteredString(this.font, this.title, this.leftPos + 56, this.topPos + 7, 16777215);
      if (this.filteredSkills.isEmpty()) {
         graphics.drawCenteredString(
            this.font, Component.translatable("foxablazeultimate.skill.shub_niggurath.warehouse.empty"), this.leftPos + 51, this.topPos + 69, 7829367
         );
      } else {
         int end = Math.min(this.filteredSkills.size(), this.listStart + 7);

         for (int i = this.listStart; i < end; i++) {
            ResourceLocation id = this.filteredSkills.get(i);
            int x = this.leftPos + 6;
            int y = this.topPos + 43 + (i - this.listStart) * 13;
            boolean active = id.equals(this.selected) || this.isInside(mouseX, mouseY, x - this.leftPos, y - this.topPos, 89, 13);
            graphics.blit(ABILITY_BAR, x, y, 0.0F, active ? 13.0F : 0.0F, 89, 13, 89, 26);
            MutableComponent name = getSkillName(id);
            graphics.drawString(
               this.font,
               this.font.plainSubstrByWidth(name.getString(), 83),
               x + 3,
               y + 3,
               name.getStyle().getColor() == null ? 16777215 : name.getStyle().getColor().getValue(),
               false
            );
         }
      }
   }

   private void renderSlots(GuiGraphics graphics) {
      this.renderSkillIcon(graphics, this.inputA, 132, 70, 20);
      this.renderSkillIcon(graphics, this.inputB, 196, 70, 20);
      this.renderSkillIcon(graphics, this.result, 158, 8, 28);
   }

   private void renderSkillIcon(GuiGraphics graphics, ResourceLocation skillId, int x, int y, int size) {
      ManasSkill skill = lookup(skillId);
      if (skill != null && skill.getSkillIcon() != null) {
         graphics.blit(skill.getSkillIcon(), this.leftPos + x, this.topPos + y, 0.0F, 0.0F, size, size, size, size);
      }
   }

   private void renderButtonHover(GuiGraphics graphics, int mouseX, int mouseY) {
      boolean canCopy = this.selected != null && !this.isGiftOnly(this.selected) && this.inputA == null && this.inputB == null;
      boolean canSynthesize = this.inputA != null && this.inputB != null && this.result != null;
      boolean canGift = this.selected != null;
      if (canCopy && this.isInside(mouseX, mouseY, 128, 116, 20, 20)) {
         this.drawButtonHover(graphics, 128, 116);
      }

      if (canSynthesize && this.isInside(mouseX, mouseY, 162, 116, 20, 20)) {
         this.drawButtonHover(graphics, 162, 116);
      }

      if (canGift && this.isInside(mouseX, mouseY, 196, 116, 20, 20)) {
         this.drawButtonHover(graphics, 196, 116);
      }
   }

   private void drawButtonHover(GuiGraphics graphics, int x, int y) {
      graphics.blit(BACKGROUND, this.leftPos + x, this.topPos + y, 0, 140, 20, 20);
   }

   private void renderTooltips(GuiGraphics graphics, int mouseX, int mouseY) {
      ResourceLocation hovered = null;
      if (this.inputA != null && this.isInside(mouseX, mouseY, 132, 70, 20, 20)) {
         hovered = this.inputA;
      } else if (this.inputB != null && this.isInside(mouseX, mouseY, 196, 70, 20, 20)) {
         hovered = this.inputB;
      } else if (this.result != null && this.isInside(mouseX, mouseY, 158, 8, 28, 28)) {
         hovered = this.result;
      }

      if (hovered != null) {
         graphics.renderTooltip(this.font, getSkillName(hovered), mouseX, mouseY);
      } else {
         if (this.selected != null
            && !this.isGiftOnly(this.selected)
            && this.inputA == null
            && this.inputB == null
            && this.isInside(mouseX, mouseY, 128, 116, 20, 20)) {
            graphics.renderTooltip(this.font, Component.translatable("foxablazeultimate.skill.shub_niggurath.copy.confirm"), mouseX, mouseY);
         } else if (this.inputA != null && this.inputB != null && this.result != null && this.isInside(mouseX, mouseY, 162, 116, 20, 20)) {
            graphics.renderTooltip(this.font, Component.translatable("foxablazeultimate.skill.shub_niggurath.create.confirm"), mouseX, mouseY);
         } else if (this.selected != null && this.isInside(mouseX, mouseY, 196, 116, 20, 20)) {
            Component tooltip = this.isGiftOnly(this.selected)
               ? Component.translatable("foxablazeultimate.skill.shub_niggurath.gift.ultimate.prepare")
               : Component.translatable("foxablazeultimate.skill.shub_niggurath.gift.prepare");
            graphics.renderTooltip(this.font, tooltip, mouseX, mouseY);
         }
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0 && this.clickedScrollBar(mouseX, mouseY)) {
         return true;
      }

      this.draggedSkill = null;
      if (!this.searchField.isHovered()) {
         this.searchField.setFocused(false);
      }

      int end = Math.min(this.filteredSkills.size(), this.listStart + 7);

      for (int i = this.listStart; i < end; i++) {
         int x = 6;
         int y = 43 + (i - this.listStart) * 13;
         if (button == 0 && this.isInside(mouseX, mouseY, x, y, 89, 13)) {
            this.selected = this.filteredSkills.get(i);
            this.draggedSkill = this.selected;
            playClick();
            return true;
         }
      }

      if (this.isInside(mouseX, mouseY, 132, 70, 20, 20)) {
         if (button == 1) {
            this.inputA = null;
         } else {
            if (button != 0 || this.selected == null || this.isGiftOnly(this.selected)) {
               return super.mouseClicked(mouseX, mouseY, button);
            }

            this.inputA = this.selected;
         }

         this.updateResult();
         playClick();
         return true;
      } else if (!this.isInside(mouseX, mouseY, 196, 70, 20, 20)) {
         if (button == 0
            && this.selected != null
            && !this.isGiftOnly(this.selected)
            && this.inputA == null
            && this.inputB == null
            && this.isInside(mouseX, mouseY, 128, 116, 20, 20)) {
            playClick();
            NetworkManager.sendToServer(new RequestShubWarehouseActionPayload(0, this.abilitySlot, this.selected, null));
            this.onClose();
            return true;
         } else if (button == 0 && this.inputA != null && this.inputB != null && this.result != null && this.isInside(mouseX, mouseY, 162, 116, 20, 20)) {
            playClick();
            NetworkManager.sendToServer(new RequestShubWarehouseActionPayload(1, this.abilitySlot, this.inputA, this.inputB));
            return true;
         } else if (button == 0 && this.selected != null && this.isInside(mouseX, mouseY, 196, 116, 20, 20)) {
            playClick();
            NetworkManager.sendToServer(new RequestShubWarehouseActionPayload(2, this.abilitySlot, this.selected, null));
            this.onClose();
            return true;
         } else {
            return super.mouseClicked(mouseX, mouseY, button);
         }
      } else {
         if (button == 1) {
            this.inputB = null;
         } else {
            if (button != 0 || this.selected == null || this.isGiftOnly(this.selected)) {
               return super.mouseClicked(mouseX, mouseY, button);
            }

            this.inputB = this.selected;
         }

         this.updateResult();
         playClick();
         return true;
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      boolean wasScrolling = this.isScrolling();
      this.setScrolling(false);
      ResourceLocation dragged = this.draggedSkill;
      this.draggedSkill = null;
      if (wasScrolling) {
         return true;
      }

      if (button == 0 && dragged != null && !this.isGiftOnly(dragged)) {
         if (this.isInside(mouseX, mouseY, 132, 70, 20, 20)) {
            this.inputA = dragged;
            this.updateResult();
            return true;
         }

         if (this.isInside(mouseX, mouseY, 196, 70, 20, 20)) {
            this.inputB = dragged;
            this.updateResult();
            return true;
         }
      }

      return super.mouseReleased(mouseX, mouseY, button);
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
      return button == 0 && this.draggedScrollBar(mouseY) ? true : super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
   }

   private void updateResult() {
      this.result = null;
      if (this.inputA != null && this.inputB != null) {
         for (OpenShubWarehousePayload.RecipeEntry recipe : this.recipes) {
            boolean matches = recipe.first().equals(this.inputA) && recipe.second().equals(this.inputB)
               || recipe.first().equals(this.inputB) && recipe.second().equals(this.inputA);
            if (matches) {
               this.result = recipe.result();
               return;
            }
         }
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
      return !this.isInside(mouseX, mouseY, 6, 43, 102, 91)
         ? super.mouseScrolled(mouseX, mouseY, deltaX, deltaY)
         : this.scrolledScrollBar(deltaY) || super.mouseScrolled(mouseX, mouseY, deltaX, deltaY);
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.searchField.keyPressed(keyCode, scanCode, modifiers)) {
         return true;
      } else {
         return this.searchField.isFocused() && this.searchField.isVisible() && keyCode != 256 ? true : super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   public boolean isPauseScreen() {
      return false;
   }

   public int getScrollBarX() {
      return this.leftPos + 98;
   }

   public int getScrollBarY() {
      return this.topPos + 43;
   }

   public int getScrollBarTotalSpace() {
      return 91;
   }

   public int getScrollBarListSize() {
      return this.filteredSkills.size();
   }

   public int getScrollBarRenderCount() {
      return 7;
   }

   public float getScrollOffset() {
      return this.scrollOffset;
   }

   public boolean isScrolling() {
      return this.scrolling;
   }

   public void setListStartIndex(int listStartIndex) {
      this.listStart = listStartIndex;
   }

   public void setScrolling(boolean scrolling) {
      this.scrolling = scrolling;
   }

   public void setScrollOffset(float scrollOffset) {
      this.scrollOffset = scrollOffset;
   }

   private boolean isInside(double mouseX, double mouseY, int x, int y, int width, int height) {
      return mouseX >= this.leftPos + x && mouseX < this.leftPos + x + width && mouseY >= this.topPos + y && mouseY < this.topPos + y + height;
   }

   private boolean isGiftOnly(ResourceLocation skillId) {
      return skillId != null && this.giftOnlySkills.contains(skillId);
   }

   private static ManasSkill lookup(ResourceLocation id) {
      return id == null ? null : (ManasSkill)SkillAPI.getSkillRegistry().get(id);
   }

   private static MutableComponent getSkillName(ResourceLocation id) {
      ManasSkill skill = lookup(id);
      if (skill instanceof TensuraSkill tensuraSkill) {
         return tensuraSkill.getColoredName();
      } else {
         return skill == null ? Component.literal(String.valueOf(id)) : skill.getChatDisplayName(false);
      }
   }

   private static void playClick() {
      Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
   }
}
