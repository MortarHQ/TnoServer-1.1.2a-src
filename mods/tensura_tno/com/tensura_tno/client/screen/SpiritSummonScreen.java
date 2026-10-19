package com.tensura_tno.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tensura_tno.network.SpiritSummonPackets;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class SpiritSummonScreen extends Screen implements IScrollBar {
   private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/skill_creation/skill_creation.png");
   private static final ResourceLocation ABILITY_BAR = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/ability_button.png");
   private static final int IMG_W = 233;
   private static final int IMG_H = 140;
   private final List<SpiritSummonScreen.EntityEntry> allEntries = new ArrayList<>();
   private List<SpiritSummonScreen.EntityEntry> filtered = new ArrayList<>();
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
   private int releaseConfirmIndex = -1;

   public SpiritSummonScreen(String absorbedCsv) {
      super(Component.translatable("tensura_tno.spirit_summon.title"));
      if (absorbedCsv != null && !absorbedCsv.isBlank()) {
         for (String entry : List.of(absorbedCsv.split(","))) {
            if (!entry.isBlank()) {
               double bonusEP = 0.0;
               float hp = 20.0F;
               float aura = 50.0F;
               float magicule = 50.0F;
               String[] parts = entry.split(":");
               String entityId;
               if (parts.length >= 5) {
                  try {
                     magicule = Float.parseFloat(parts[parts.length - 1]);
                     aura = Float.parseFloat(parts[parts.length - 2]);
                     hp = Float.parseFloat(parts[parts.length - 3]);
                     bonusEP = Double.parseDouble(parts[parts.length - 4]);
                     StringBuilder idBuilder = new StringBuilder();

                     for (int i = 0; i < parts.length - 4; i++) {
                        if (i > 0) {
                           idBuilder.append(":");
                        }

                        idBuilder.append(parts[i]);
                     }

                     entityId = idBuilder.toString();
                  } catch (NumberFormatException e) {
                     entityId = entry;
                  }
               } else if (parts.length == 3) {
                  try {
                     bonusEP = Double.parseDouble(parts[2]);
                     entityId = parts[0] + ":" + parts[1];
                  } catch (NumberFormatException e) {
                     entityId = entry;
                  }
               } else {
                  entityId = entry;
               }

               String shortId = entityId.substring(entityId.indexOf(58) + 1);
               Component displayName = Component.literal(shortId);

               try {
                  ResourceLocation id = ResourceLocation.parse(entityId);
                  EntityType<?> type = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(id);
                  if (type != null) {
                     displayName = type.getDescription();
                  }
               } catch (Exception var15) {
               }

               float ep = aura + magicule + (float)bonusEP;
               this.allEntries.add(new SpiritSummonScreen.EntityEntry(entityId, shortId, displayName, bonusEP, hp, ep, magicule));
            }
         }

         this.filtered = new ArrayList<>(this.allEntries);
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
         SpiritSummonScreen.EntityEntry entry = this.filtered.get(i);
         MutableComponent name = (MutableComponent)entry.name;
         int color = selected ? 16776960 : 16777215;
         RenderHelper.drawShortenedTextWithTooltip(gfx, this.font, name, name, rx, ry, 3, 3, 88, 13, mX, mY, color, true, this);
      }
   }

   private void renderRightPanel(GuiGraphics gfx, int mX, int mY, float partialTick) {
      if (this.selectedIndex >= 0 && this.selectedIndex < this.filtered.size()) {
         SpiritSummonScreen.EntityEntry sel = this.filtered.get(this.selectedIndex);
         LivingEntity entity = this.getOrCreateEntity(sel.id);
         if (entity != null) {
            float scale = 20.0F / Math.max(entity.getBbWidth(), entity.getBbHeight());
            scale = Math.min(scale, 18.0F);
            RenderHelper.renderEntityInInventoryFollowsMouse(
               gfx, this.leftPos + 146, this.topPos + 4, this.leftPos + 196, this.topPos + 42, scale, this.leftPos + 171, this.topPos + 23, false, entity
            );
            int px = this.leftPos + 125;
            int py = this.topPos + 48;
            float maxHp = entity.getMaxHealth();
            this.drawStatBar(gfx, px, py, 90, "HP", maxHp, maxHp, -44976);
            py += 14;
            float ep = sel.ep();
            float maxEp = ep - (float)sel.bonusEP();
            this.drawStatBar(gfx, px, py, 90, "EP", ep, maxEp, -8090);
            py += 14;
            this.drawStatBar(gfx, px, py, 90, "MP", sel.mp(), sel.mp(), -10053121);
         }
      }
   }

   private void drawStatBar(GuiGraphics gfx, int x, int y, int width, String label, float value, float max, int barColorARGB) {
      gfx.drawString(this.font, label + ":", x, y, -1, false);
      int barX = x + 22;
      int barW = width - 22;
      gfx.fill(barX, y, barX + barW, y + 7, -13421773);
      if (max > 0.0F) {
         int filled = Math.round(barW * Math.min(1.0F, value / max));
         if (filled > 0) {
            gfx.fill(barX, y, barX + filled, y + 7, barColorARGB);
         }
      }

      String text = fmt(value);
      gfx.drawString(this.font, text, barX + 1, y - 1, -1, true);
      RenderSystem.disableBlend();
   }

   private static String fmt(float v) {
      if (v >= 1.0E9F) {
         return stripTrailingZero(v / 1.0E9F) + "G";
      }

      if (v >= 1000000.0F) {
         return stripTrailingZero(v / 1000000.0F) + "M";
      }

      if (v >= 1000.0F) {
         return stripTrailingZero(v / 1000.0F) + "k";
      }

      long lv = (long)v;
      return (float)lv == v ? String.valueOf(lv) : String.format("%.1f", v);
   }

   private static String stripTrailingZero(double v) {
      long lv = (long)v;
      return lv == v ? String.valueOf(lv) : String.format("%.1f", v);
   }

   private void renderConfirmButton(GuiGraphics gfx, int mX, int mY) {
      if (this.selectedIndex >= 0) {
         boolean hovered = mX >= this.leftPos + 162 && mX < this.leftPos + 182 && mY >= this.topPos + 116 && mY < this.topPos + 136;
         if (hovered) {
            gfx.blit(BACKGROUND, this.leftPos + 162, this.topPos + 116, 0, 140, 20, 20);
         }

         if (this.releaseConfirmIndex == this.selectedIndex) {
            Component warn = Component.translatable("tensura_tno.spirit_summon.release_confirm").withStyle(s -> s.withColor(16733525));
            gfx.drawString(this.font, warn, this.leftPos + 125, this.topPos + 104, -43691, true);
         }

         if (hovered) {
            if (Screen.hasShiftDown()) {
               this.setTooltipForNextRenderPass(
                  this.releaseConfirmIndex == this.selectedIndex
                     ? Component.translatable("tensura_tno.spirit_summon.release_confirm_tooltip")
                     : Component.translatable("tensura_tno.spirit_summon.release_tooltip")
               );
            } else {
               this.setTooltipForNextRenderPass(Component.translatable("tensura_tno.spirit_summon.summon"));
            }
         }
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
         if (Screen.hasShiftDown()) {
            this.handleShiftClickRelease();
         } else {
            this.releaseConfirmIndex = -1;
            this.confirmSelection();
         }

         return true;
      } else {
         this.releaseConfirmIndex = -1;
         int lastVisible = Math.min(this.listStartIndex + this.getScrollBarRenderCount(), this.filtered.size());

         for (int i = this.listStartIndex; i < lastVisible; i++) {
            int rx = this.leftPos + 6;
            int ry = this.topPos + 43 + (i - this.listStartIndex) * 13;
            if (x >= rx && y >= ry && x < rx + 89 && y < ry + 13) {
               Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
               this.selectedIndex = i;
               return true;
            }
         }

         return super.mouseClicked(x, y, button);
      }
   }

   public boolean mouseScrolled(double mX, double mY, double deltaX, double deltaY) {
      this.scrolledScrollBar(deltaY);
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
         Predicate<SpiritSummonScreen.EntityEntry> pred = e -> e.name.getString().toLowerCase().contains(this.nameFilter.toLowerCase());
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
         NetworkManager.sendToServer(new SpiritSummonPackets.SelectEntityPayload(this.filtered.get(this.selectedIndex).id));
         this.onClose();
      }
   }

   private void handleShiftClickRelease() {
      if (this.selectedIndex >= 0 && this.selectedIndex < this.filtered.size()) {
         if (this.releaseConfirmIndex == this.selectedIndex) {
            String entityId = this.filtered.get(this.selectedIndex).id;
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_BREAK, 0.8F));
            NetworkManager.sendToServer(new SpiritSummonPackets.ReleaseEntityPayload(entityId));
            this.allEntries.removeIf(e -> e.id.equals(entityId));
            this.updateFiltered();
            this.releaseConfirmIndex = -1;
         } else {
            this.releaseConfirmIndex = this.selectedIndex;
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 0.6F));
         }
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

   private record EntityEntry(String id, String shortId, Component name, double bonusEP, float hp, float ep, float mp) {
   }
}
