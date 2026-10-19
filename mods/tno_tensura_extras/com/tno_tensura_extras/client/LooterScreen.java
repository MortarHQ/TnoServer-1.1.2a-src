package com.tno_tensura_extras.client;

import com.tno_tensura_extras.network.LooterPackets;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.tensura.client.screen.templates.IScrollBar;
import io.github.manasmods.tensura.util.client.RenderHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public final class LooterScreen extends Screen implements IScrollBar {
   private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/skill_creation/skill_creation.png");
   private static final ResourceLocation ABILITY_BAR = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/ability_button.png");
   private static final int IMG_W = 233;
   private static final int IMG_H = 140;
   private static final int BACKGROUND_TEXTURE_W = 256;
   private static final int BACKGROUND_TEXTURE_H = 256;
   private final List<LooterScreen.EntityEntry> allEntries = new ArrayList<>();
   private List<LooterScreen.EntityEntry> filtered = new ArrayList<>();
   private float scrollOffset;
   private boolean scrolling;
   private int listStartIndex;
   private int selectedIndex = -1;
   private EditBox searchField;
   private String nameFilter = "";
   private int leftPos;
   private int topPos;
   private LivingEntity cachedEntity;
   private String cachedEntityId;

   public LooterScreen(String encodedEntries) {
      super(Component.translatable("tno_tensura_extras.skill.looter"));
      this.parseEntries(encodedEntries);
      this.filtered = new ArrayList<>(this.allEntries);
   }

   private void parseEntries(String encodedEntries) {
      if (encodedEntries != null && !encodedEntries.isBlank()) {
         for (String encoded : encodedEntries.split(";", -1)) {
            String[] parts = encoded.split(",", 5);
            if (parts.length >= 2) {
               try {
                  UUID id = UUID.fromString(parts[0]);
                  ResourceLocation entityId = ResourceLocation.tryParse(parts[1]);
                  if (entityId != null && BuiltInRegistries.ENTITY_TYPE.containsKey(entityId)) {
                     double ep = parseNumber(parts, 2);
                     double mp = parseNumber(parts, 3);
                     double shp = parseNumber(parts, 4);
                     EntityType<?> type = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(entityId);
                     String stringId = entityId.toString();
                     this.allEntries.add(new LooterScreen.EntityEntry(id, stringId, entityId.getPath(), type.getDescription(), ep, mp, shp));
                  }
               } catch (IllegalArgumentException var17) {
               }
            }
         }
      }
   }

   private static double parseNumber(String[] parts, int index) {
      if (index < parts.length && !parts[index].isBlank()) {
         try {
            double value = Double.parseDouble(parts[index]);
            return Double.isFinite(value) && value >= 0.0 ? value : 0.0;
         } catch (NumberFormatException ignored) {
            return 0.0;
         }
      } else {
         return 0.0;
      }
   }

   protected void init() {
      super.init();
      this.leftPos = (this.width - 233) / 2;
      this.topPos = (this.height - 140) / 2;
      this.scrollOffset = 0.0F;
      this.listStartIndex = 0;
      this.searchField = new EditBox(this.font, this.leftPos + 19, this.topPos + 27, 79, 9, Component.empty());
      this.searchField.setBordered(false);
      this.searchField.setMaxLength(64);
      this.searchField.setResponder(value -> {
         this.nameFilter = value;
         this.setScrollOffset(0.0F);
         this.setListStartIndex(0);
         this.updateFiltered();
      });
      this.addRenderableWidget(this.searchField);
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      super.render(graphics, mouseX, mouseY, partialTick);
      graphics.blit(BACKGROUND, this.leftPos, this.topPos, 0.0F, 0.0F, 233, 140, 256, 256);
      RenderHelper.drawCenteredText(graphics, this.font, this.title, this.leftPos + 56, this.topPos + 5, 16777215, false);
      this.searchField.render(graphics, mouseX, mouseY, partialTick);
      int lastVisible = Math.min(this.listStartIndex + this.getScrollBarRenderCount(), this.filtered.size());
      this.renderListRows(graphics, mouseX, mouseY, lastVisible);
      this.renderScrollBar(graphics, mouseX, mouseY);
      this.renderRightPanel(graphics);
      this.renderConfirmButton(graphics, mouseX, mouseY);
   }

   private void renderListRows(GuiGraphics graphics, int mouseX, int mouseY, int lastVisible) {
      for (int i = this.listStartIndex; i < lastVisible; i++) {
         int x = this.leftPos + 6;
         int y = this.topPos + 43 + (i - this.listStartIndex) * 13;
         boolean hovered = mouseX >= x && mouseX < x + 89 && mouseY >= y && mouseY < y + 13;
         boolean selected = i == this.selectedIndex;
         graphics.blit(ABILITY_BAR, x, y, 0.0F, !hovered && !selected ? 0.0F : 13.0F, 89, 13, 89, 26);
         LooterScreen.EntityEntry entry = this.filtered.get(i);
         MutableComponent name = entry.name().copy();
         int color = selected ? 16776960 : 16777215;
         RenderHelper.drawShortenedTextWithTooltip(graphics, this.font, name, name, x, y, 3, 3, 88, 13, mouseX, mouseY, color, true, this);
      }
   }

   private void renderRightPanel(GuiGraphics graphics) {
      if (this.selectedIndex >= 0 && this.selectedIndex < this.filtered.size()) {
         LooterScreen.EntityEntry entry = this.filtered.get(this.selectedIndex);
         LivingEntity entity = this.getOrCreateEntity(entry.entityId());
         if (entity != null) {
            float size = Math.max(entity.getBbWidth(), entity.getBbHeight());
            float scale = size <= 0.0F ? 1.0F : Mth.clamp(20.0F / size, 1.0F, 18.0F);
            RenderHelper.renderEntityInInventoryFollowsMouse(
               graphics,
               this.leftPos + 146.0F,
               this.topPos + 4.0F,
               this.leftPos + 196.0F,
               this.topPos + 42.0F,
               scale,
               this.leftPos + 171.0F,
               this.topPos + 23.0F,
               false,
               entity
            );
         }
      }
   }

   private void renderConfirmButton(GuiGraphics graphics, int mouseX, int mouseY) {
      if (this.selectedIndex >= 0 && mouseX >= this.leftPos + 162 && mouseX < this.leftPos + 182 && mouseY >= this.topPos + 116 && mouseY < this.topPos + 136) {
         graphics.blit(BACKGROUND, this.leftPos + 162, this.topPos + 116, 0.0F, 140.0F, 20, 20, 256, 256);
         this.setTooltipForNextRenderPass(Component.translatable("tno_tensura_extras.skill.looter.summon"));
      }
   }

   private LivingEntity getOrCreateEntity(String entityId) {
      if (entityId.equals(this.cachedEntityId) && this.cachedEntity != null) {
         return this.cachedEntity;
      }

      if (Minecraft.getInstance().level == null) {
         return null;
      }

      try {
         ResourceLocation id = ResourceLocation.parse(entityId);
         EntityType<?> type = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(id);
         if (type == null) {
            return null;
         }

         if (type.create(Minecraft.getInstance().level) instanceof LivingEntity living) {
            this.cachedEntity = living;
            this.cachedEntityId = entityId;
            return living;
         }
      } catch (RuntimeException var6) {
      }

      return null;
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.clickedScrollBar(mouseX, mouseY)) {
         return true;
      }

      if (this.searchField != null && !this.searchField.isHovered()) {
         this.searchField.setFocused(false);
      }

      if (this.selectedIndex >= 0 && mouseX >= this.leftPos + 162 && mouseX < this.leftPos + 182 && mouseY >= this.topPos + 116 && mouseY < this.topPos + 136) {
         this.confirmSelection();
         return true;
      }

      int lastVisible = Math.min(this.listStartIndex + this.getScrollBarRenderCount(), this.filtered.size());

      for (int i = this.listStartIndex; i < lastVisible; i++) {
         int x = this.leftPos + 6;
         int y = this.topPos + 43 + (i - this.listStartIndex) * 13;
         if (mouseX >= x && mouseX < x + 89 && mouseY >= y && mouseY < y + 13) {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            this.selectedIndex = i;
            return true;
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   private void confirmSelection() {
      if (this.selectedIndex >= 0 && this.selectedIndex < this.filtered.size()) {
         LooterScreen.EntityEntry entry = this.filtered.get(this.selectedIndex);
         Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
         NetworkManager.sendToServer(new LooterPackets.SelectShadowPayload(entry.id().toString()));
         this.onClose();
      }
   }

   private void updateFiltered() {
      if (this.nameFilter != null && !this.nameFilter.isBlank()) {
         Predicate<LooterScreen.EntityEntry> predicate = entry -> entry.name()
            .getString()
            .toLowerCase(Locale.ROOT)
            .contains(this.nameFilter.toLowerCase(Locale.ROOT));
         this.filtered = new ArrayList<>(this.allEntries.stream().filter(predicate).toList());
         this.selectedIndex = -1;
      } else {
         this.filtered = new ArrayList<>(this.allEntries);
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
      return this.scrolledScrollBar(deltaY) || super.mouseScrolled(mouseX, mouseY, deltaX, deltaY);
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
      return this.draggedScrollBar(mouseY) || super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.searchField != null && this.searchField.keyPressed(keyCode, scanCode, modifiers)) {
         return true;
      } else {
         return this.searchField != null && this.searchField.isFocused() && this.searchField.isVisible() && keyCode != 256
            ? true
            : super.keyPressed(keyCode, scanCode, modifiers);
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

   public void setScrollOffset(float value) {
      this.scrollOffset = value;
   }

   public boolean isScrolling() {
      return this.scrolling;
   }

   public void setScrolling(boolean value) {
      this.scrolling = value;
   }

   public int getListStartIndex() {
      return this.listStartIndex;
   }

   public void setListStartIndex(int value) {
      this.listStartIndex = value;
   }

   private record EntityEntry(UUID id, String entityId, String shortId, Component name, double ep, double mp, double shp) {
   }
}
