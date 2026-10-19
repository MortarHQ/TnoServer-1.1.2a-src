package io.github.Memoires.mysticism.client.screen;

import dev.architectury.networking.NetworkManager;
import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.network.c2s.RequestRuptureOnTarget;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.client.screen.templates.IScrollBar;
import io.github.manasmods.tensura.client.screen.templates.SimpleScreen;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.client.RenderHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;

public class RupturingScreen extends SimpleScreen implements IScrollBar {
   private final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath("mysticism", "textures/gui/rupturing.png");
   protected static final UniqueMysticismSkillConfig.Butcher CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Butcher;
   protected ManasSkill skill;
   protected List<LivingEntity> targets = new ArrayList<>();
   protected List<Integer> selectedTargets = new ArrayList<>();
   private float scrollOffset;
   protected boolean scrolling;
   private int listStartIndex;
   private List<LivingEntity> filtered = new ArrayList<>();
   private int textStartIndex;
   private EditBox searchField;
   private String nameFilter = "";

   public RupturingScreen(Component pTitle, List<Integer> targetIds) {
      super(pTitle, 150, 150);

      for (int target : targetIds) {
         if (this.player.level().getEntity(target) instanceof LivingEntity living) {
            this.targets.add(living);
         }
      }

      if (this.targets.isEmpty()) {
         this.player.displayClientMessage(Component.translatable("mysticism.skill.mode.butcher.rupture.no_targets").withStyle(ChatFormatting.RED), false);
         this.onClose();
      }
   }

   protected void init() {
      super.init();
      this.scrollOffset = 0.0F;
      this.listStartIndex = 0;
      this.searchField = new EditBox(this.font, guiLeft + 20, guiTop + 30, 105, 9, Component.empty());
      this.searchField.setBordered(false);
      this.searchField.setResponder(s -> {
         this.nameFilter = s;
         this.setScrollOffset(0.0F);
         this.setListStartIndex(0);
         this.updateFilteredEntities();
      });
      this.addRenderableWidget(this.searchField);
      if (this.nameFilter == null) {
         this.nameFilter = "";
      } else {
         this.searchField.setValue(this.nameFilter);
      }
   }

   public void renderBackground(GuiGraphics graphics, int mX, int mY, float partialTick) {
      graphics.blit(this.BACKGROUND, guiLeft, guiTop, 0, 0, this.imageWidth, this.imageHeight);
      RenderHelper.drawCenteredText(
         graphics, this.font, Component.translatable("mysticism.skill.mode.butcher.rupture"), guiLeft + 75, guiTop + 10, 16711680, false
      );
      List<LivingEntity> filteredEntities = this.filtered;
      int lastVisibleElementIndex = Math.min(this.getListStartIndex() + this.getScrollBarRenderCount(), filteredEntities.size());
      this.renderButtons(graphics, mX, mY, lastVisibleElementIndex, filteredEntities);
      this.renderScrollBar(graphics, mX, mY);
   }

   private void renderButtons(GuiGraphics graphics, int mX, int mY, int pLastVisibleElementIndex, List<LivingEntity> list) {
      for (int i = this.getListStartIndex(); i < pLastVisibleElementIndex && i < list.size(); i++) {
         int x = guiLeft + 19;
         int y = guiTop + 45 + (i - this.getListStartIndex()) * 13;
         int offset = 152;
         boolean hovering = mX >= x && mY >= y && mX < x + 96 && mY < y + 13;
         if (hovering) {
            offset = 166;
         }

         graphics.blit(this.BACKGROUND, x, y, 0.0F, offset, 96, 13, 256, 256);
         LivingEntity livingEntity = list.get(i);
         MutableComponent name = livingEntity.getDisplayName().copy();
         RenderHelper.drawShortenedTextWithTooltip(graphics, this.font, name, Component.empty(), x, y, 3, 3, 88, 13, mX, mY, 16733525, true, this);
      }

      if (RenderHelper.mouseOver(mX, mY, guiLeft + 44, guiLeft + 105, guiTop + 129, guiTop + 143)) {
         graphics.blit(this.BACKGROUND, guiLeft + 44, guiTop + 129, guiLeft + 97, guiTop + 152, 62, 15, 256, 256);
      }
   }

   public boolean mouseClicked(double mX, double mY, int button) {
      if (this.clickedScrollBar(mX, mY)) {
         return true;
      }

      if (!this.searchField.isHovered()) {
         this.searchField.setFocused(false);
      }

      List<LivingEntity> targets = this.filtered;
      int lastDisplayedIndex = Math.min(this.getListStartIndex() + this.getScrollBarRenderCount(), targets.size());

      for (int i = this.getListStartIndex(); i < lastDisplayedIndex; i++) {
         int pX = guiLeft + 19;
         int pY = guiTop + 45 + (i - this.getListStartIndex()) * 13;
         if (targets.size() <= i) {
            break;
         }

         if (mX >= pX && mY >= pY && mX < pX + 96 && mY < pY + 13) {
            int id = targets.get(i).getId();
            if (!this.selectedTargets.contains(id)) {
               Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
               this.selectedTargets.add(id);
            } else {
               Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(TensuraSoundEvents.BUFF_DEACTIVATE, 1.0F));
               this.selectedTargets.remove(Integer.valueOf(id));
            }

            this.textStartIndex = 0;
            return true;
         }
      }

      if (RenderHelper.mouseOver(mX, mY, guiLeft + 44, guiLeft + 105, guiTop + 129, guiTop + 143) && !this.selectedTargets.isEmpty()) {
         NetworkManager.sendToServer(new RequestRuptureOnTarget(this.player.getId(), this.selectedTargets));
         this.onClose();
      }

      return super.mouseClicked(mX, mY, button);
   }

   public boolean mouseScrolled(double mX, double mY, double pDeltaX, double pDeltaY) {
      if (RenderHelper.mouseOver(mX, mY, guiLeft + 44, guiLeft + 106, guiTop + 129, guiTop + 144)) {
         this.textStartIndex = Math.max(this.textStartIndex + (int)(-pDeltaY), 0);
      } else {
         this.scrolledScrollBar(pDeltaY);
      }

      return super.mouseScrolled(mX, mY, pDeltaX, pDeltaY);
   }

   public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
      return this.draggedScrollBar(pMouseY) || super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
   }

   protected void updateFilteredEntities() {
      if (this.nameFilter != null && !this.nameFilter.isEmpty() && !this.nameFilter.isBlank()) {
         this.filtered = new ArrayList<>();
         Predicate<LivingEntity> predicate = entity -> entity.getName().getString().toLowerCase().contains(this.nameFilter.toLowerCase());
         this.filtered.addAll(this.targets.stream().filter(predicate).toList());
      } else {
         this.filtered = this.targets;
      }

      this.scrolledScrollBar(0.0);
   }

   public int getScrollBarX() {
      return guiLeft + 122;
   }

   public int getScrollBarY() {
      return guiTop + 45;
   }

   public int getScrollBarTotalSpace() {
      return 79;
   }

   public int getScrollBarListSize() {
      return this.filtered.size();
   }

   public int getScrollBarRenderCount() {
      return 7;
   }

   public int getScrollbarX() {
      return 0;
   }

   public int getScrollbarY() {
      return 0;
   }

   public int getScrollbarTotalSpace() {
      return 0;
   }

   public int getScrollbarListSize() {
      return 0;
   }

   public int getScrollbarRenderCount() {
      return 0;
   }

   public float getScrollOffset() {
      return this.scrollOffset;
   }

   public void setScrollOffset(float scrollOffset) {
      this.scrollOffset = scrollOffset;
   }

   public boolean isScrolling() {
      return this.scrolling;
   }

   public void setScrolling(boolean scrolling) {
      this.scrolling = scrolling;
   }

   public int getListStartIndex() {
      return this.listStartIndex;
   }

   public void setListStartIndex(int listStartIndex) {
      this.listStartIndex = listStartIndex;
   }
}
