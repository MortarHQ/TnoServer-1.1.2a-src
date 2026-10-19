package com.tensura_tno.client.screen;

import com.tensura_tno.network.EdoTenseiPackets;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.tensura.client.screen.templates.IScrollBar;
import io.github.manasmods.tensura.util.client.RenderHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class EdoTenseiScreen extends Screen implements IScrollBar {
   private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/skill_creation/skill_creation.png");
   private static final ResourceLocation ABILITY_BAR = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/ability_button.png");
   private static final int IMG_W = 233;
   private static final int IMG_H = 140;
   private final List<EdoTenseiScreen.EntityEntry> allEntries = new ArrayList<>();
   private List<EdoTenseiScreen.EntityEntry> filtered = new ArrayList<>();
   private float scrollOffset;
   private boolean scrolling;
   private int listStartIndex;
   private int selectedIndex = -1;
   private int textStartIndex;
   private EditBox searchField;
   private String nameFilter = "";
   private int leftPos;
   private int topPos;
   private LivingEntity cachedEntity;
   private String cachedEntityId;

   public EdoTenseiScreen(List<String> unlockedIds, boolean mastered) {
      super(Component.translatable("tensura_tno.edo_tensei.title"));

      for (String entityId : unlockedIds.stream().sorted().toList()) {
         if (EdoTenseiPackets.ALL_ENTITIES.contains(entityId)) {
            String shortId = entityId.substring(entityId.indexOf(58) + 1);
            Component displayName = Component.literal(shortId);

            try {
               ResourceLocation id = ResourceLocation.parse(entityId);
               EntityType<?> type = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(id);
               if (type != null) {
                  displayName = type.getDescription();
               }
            } catch (Exception var9) {
            }

            this.allEntries.add(new EdoTenseiScreen.EntityEntry(entityId, shortId, displayName));
         }
      }

      this.filtered = new ArrayList<>(this.allEntries);
   }

   protected void init() {
      super.init();
      this.leftPos = (this.width - 233) / 2;
      this.topPos = (this.height - 140) / 2;
      this.scrollOffset = 0.0F;
      this.listStartIndex = 0;
      this.searchField = new EditBox(this.font, this.leftPos + 19, this.topPos + 27, 79, 9, Component.empty());
      this.searchField.setBordered(false);
      this.searchField.setResponder(s -> {
         this.nameFilter = s;
         this.setScrollOffset(0.0F);
         this.setListStartIndex(0);
         this.updateFiltered();
      });
      this.addRenderableWidget(this.searchField);
      if (this.nameFilter != null && !this.nameFilter.isEmpty()) {
         this.searchField.setValue(this.nameFilter);
      }
   }

   public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
      super.render(gfx, mouseX, mouseY, partialTick);
      int x = this.leftPos;
      int y = this.topPos;
      gfx.blit(BACKGROUND, x, y, 0, 0, 233, 140);
      RenderHelper.drawCenteredText(gfx, this.font, this.title, this.leftPos + 56, this.topPos + 5, 16777215, false);
      this.searchField.render(gfx, mouseX, mouseY, partialTick);
      int lastVisible = Math.min(this.listStartIndex + this.getScrollBarRenderCount(), this.filtered.size());
      this.renderListRows(gfx, mouseX, mouseY, lastVisible);
      this.renderScrollBar(gfx, mouseX, mouseY);
      this.renderRightPanel(gfx, mouseX, mouseY, partialTick);
      this.renderConfirmButton(gfx, mouseX, mouseY);
   }

   private void renderListRows(GuiGraphics gfx, int mX, int mY, int lastVisible) {
      for (int i = this.listStartIndex; i < lastVisible && i < this.filtered.size(); i++) {
         int rx = this.leftPos + 6;
         int ry = this.topPos + 43 + (i - this.listStartIndex) * 13;
         boolean hovered = mX >= rx && mY >= ry && mX < rx + 89 && mY < ry + 13;
         boolean selected = i == this.selectedIndex;
         int vOff = !hovered && !selected ? 0 : 13;
         gfx.blit(ABILITY_BAR, rx, ry, 0.0F, vOff, 89, 13, 89, 26);
         EdoTenseiScreen.EntityEntry entry = this.filtered.get(i);
         MutableComponent name = (MutableComponent)entry.name;
         int color = selected ? 16776960 : 16777215;
         RenderHelper.drawShortenedTextWithTooltip(gfx, this.font, name, name, rx, ry, 3, 3, 88, 13, mX, mY, color, true, this);
      }
   }

   private void renderRightPanel(GuiGraphics gfx, int mX, int mY, float partialTick) {
      if (this.selectedIndex >= 0 && this.selectedIndex < this.filtered.size()) {
         EdoTenseiScreen.EntityEntry sel = this.filtered.get(this.selectedIndex);
         LivingEntity entity = this.getOrCreateEntity(sel.id);
         if (entity != null) {
            float scale = 20.0F / Math.max(entity.getBbWidth(), entity.getBbHeight());
            scale = Math.min(scale, 18.0F);
            RenderHelper.renderEntityInInventoryFollowsMouse(
               gfx, this.leftPos + 146, this.topPos + 4, this.leftPos + 196, this.topPos + 42, scale, this.leftPos + 171, this.topPos + 23, false, entity
            );
         }

         Component desc = Component.translatable("tensura_tno.skill.edo_tensei.description");
         List<FormattedCharSequence> sequences = this.font.split(desc, 94);
         int descStart = Math.max(0, Math.min(this.textStartIndex, Math.max(0, sequences.size() - 7)));
         int descEnd = Math.min(descStart + 7, sequences.size());
         this.textStartIndex = descStart;
         boolean hoveringDesc = RenderHelper.mouseOver(mX, mY, this.leftPos + 125, this.leftPos + 219, this.topPos + 48, this.topPos + 108);
         RenderHelper.drawScrollableTextInAreaSetHighlight(
            gfx, this.font, sequences, this.leftPos + 125, this.topPos + 48, 94, 60, 0, descStart, descEnd, hoveringDesc, 8750469, 863042
         );
      }
   }

   private void renderConfirmButton(GuiGraphics gfx, int mX, int mY) {
      if (this.selectedIndex >= 0 && mX >= this.leftPos + 162 && mX < this.leftPos + 182 && mY >= this.topPos + 116 && mY < this.topPos + 136) {
         gfx.blit(BACKGROUND, this.leftPos + 162, this.topPos + 116, 0, 140, 20, 20);
         this.setTooltipForNextRenderPass(Component.translatable("tensura_tno.edo_tensei.summon"));
      }
   }

   private LivingEntity getOrCreateEntity(String entityId) {
      if (entityId.equals(this.cachedEntityId) && this.cachedEntity != null) {
         return this.cachedEntity;
      }

      try {
         ResourceLocation rl = ResourceLocation.parse(entityId);
         if (!BuiltInRegistries.ENTITY_TYPE.containsKey(rl)) {
            return null;
         }

         EntityType<?> type = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(rl);
         if (type.create(Minecraft.getInstance().level) instanceof LivingEntity le) {
            this.cachedEntity = le;
            this.cachedEntityId = entityId;
            return le;
         }
      } catch (Exception var6) {
      }

      return null;
   }

   public boolean mouseClicked(double x, double y, int button) {
      if (this.clickedScrollBar(x, y)) {
         return true;
      }

      if (!this.searchField.isHovered()) {
         this.searchField.setFocused(false);
      }

      if (x >= this.leftPos + 162 && x < this.leftPos + 182 && y >= this.topPos + 116 && y < this.topPos + 136 && this.selectedIndex >= 0) {
         this.confirmSelection();
         return true;
      }

      int lastVisible = Math.min(this.listStartIndex + this.getScrollBarRenderCount(), this.filtered.size());

      for (int i = this.listStartIndex; i < lastVisible; i++) {
         int rx = this.leftPos + 6;
         int ry = this.topPos + 43 + (i - this.listStartIndex) * 13;
         if (x >= rx && y >= ry && x < rx + 89 && y < ry + 13) {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            this.selectedIndex = i;
            this.textStartIndex = 0;
            return true;
         }
      }

      return super.mouseClicked(x, y, button);
   }

   public boolean mouseScrolled(double mX, double mY, double deltaX, double deltaY) {
      if (RenderHelper.mouseOver(mX, mY, this.leftPos + 125, this.leftPos + 219, this.topPos + 43, this.topPos + 109)) {
         this.textStartIndex = Math.max(this.textStartIndex + (int)(-deltaY), 0);
      } else {
         this.scrolledScrollBar(deltaY);
      }

      return super.mouseScrolled(mX, mY, deltaX, deltaY);
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
      return this.draggedScrollBar(mouseY) || super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.searchField.keyPressed(keyCode, scanCode, modifiers)) {
         return true;
      } else {
         return this.searchField.isFocused() && this.searchField.isVisible() && keyCode != 256 ? true : super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   private void updateFiltered() {
      if (this.nameFilter != null && !this.nameFilter.isEmpty() && !this.nameFilter.isBlank()) {
         Predicate<EdoTenseiScreen.EntityEntry> pred = e -> e.name.getString().toLowerCase().contains(this.nameFilter.toLowerCase());
         this.filtered = new ArrayList<>(this.allEntries.stream().filter(pred).toList());
      } else {
         this.filtered = new ArrayList<>(this.allEntries);
      }

      this.selectedIndex = -1;
      this.scrolledScrollBar(0.0);
   }

   private void confirmSelection() {
      if (this.selectedIndex >= 0 && this.selectedIndex < this.filtered.size()) {
         Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
         NetworkManager.sendToServer(new EdoTenseiPackets.SelectEntityPayload(this.filtered.get(this.selectedIndex).id));
         this.onClose();
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
      return this.filtered.size();
   }

   public int getScrollBarRenderCount() {
      return 7;
   }

   public float getScrollOffset() {
      return this.scrollOffset;
   }

   public void setScrollOffset(float v) {
      this.scrollOffset = v;
   }

   public boolean isScrolling() {
      return this.scrolling;
   }

   public void setScrolling(boolean v) {
      this.scrolling = v;
   }

   public int getListStartIndex() {
      return this.listStartIndex;
   }

   public void setListStartIndex(int v) {
      this.listStartIndex = v;
   }

   private record EntityEntry(String id, String shortId, Component name) {
   }
}
