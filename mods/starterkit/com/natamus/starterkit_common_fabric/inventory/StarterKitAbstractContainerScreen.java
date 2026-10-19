package com.natamus.starterkit_common_fabric.inventory;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import com.natamus.starterkit_common_fabric.data.Constants;
import java.util.List;
import java.util.Set;
import net.minecraft.class_1058;
import net.minecraft.class_124;
import net.minecraft.class_156;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_1713;
import net.minecraft.class_1735;
import net.minecraft.class_1799;
import net.minecraft.class_1921;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_3675;
import net.minecraft.class_3936;
import net.minecraft.class_437;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class StarterKitAbstractContainerScreen<T extends class_1703> extends class_437 implements class_3936<T> {
   public static final class_2960 INVENTORY_LOCATION = class_2960.method_60655("starterkit", "textures/gui/container/inventory.png");
   private static final float SNAPBACK_SPEED = 100.0F;
   private static final int QUICKDROP_DELAY = 500;
   public static final int SLOT_ITEM_BLIT_OFFSET = 100;
   private static final int HOVER_ITEM_BLIT_OFFSET = 200;
   protected int imageWidth = 176;
   protected int imageHeight = 166;
   protected int titleLabelX;
   protected int titleLabelY;
   protected int inventoryLabelX;
   protected int inventoryLabelY;
   protected T menu;
   protected class_2561 playerInventoryTitle;
   @Nullable
   protected class_1735 hoveredSlot;
   @Nullable
   private class_1735 clickedSlot;
   @Nullable
   private class_1735 snapbackEnd;
   @Nullable
   private class_1735 quickdropSlot;
   @Nullable
   private class_1735 lastClickSlot;
   protected int leftPos;
   protected int topPos;
   private boolean isSplittingStack;
   private class_1799 draggingItem;
   private int snapbackStartX;
   private int snapbackStartY;
   private long snapbackTime;
   private class_1799 snapbackItem;
   private long quickdropTime;
   protected Set<class_1735> quickCraftSlots;
   protected boolean isQuickCrafting;
   private int quickCraftingType;
   private int quickCraftingButton;
   private boolean skipNextRelease;
   private int quickCraftingRemainder;
   private long lastClickTime;
   private int lastClickButton;
   private boolean doubleclick;
   private class_1799 lastQuickMoved;

   public StarterKitAbstractContainerScreen(T menu, class_1661 playerInventory, class_2561 title) {
      super(title);
      if (playerInventory == null) {
         Constants.logger.warn("[Starter Kit] Cannot initiate StarterKitAbstractContainerScreen, the playerInventory is null.");
      } else {
         this.draggingItem = class_1799.field_8037;
         this.snapbackItem = class_1799.field_8037;
         this.quickCraftSlots = Sets.newHashSet();
         this.lastQuickMoved = class_1799.field_8037;
         this.menu = menu;
         this.playerInventoryTitle = playerInventory.method_5476();
         this.skipNextRelease = true;
         this.titleLabelX = 8;
         this.titleLabelY = 6;
         this.inventoryLabelX = 8;
         this.inventoryLabelY = this.imageHeight - 94;
      }
   }

   protected void method_25426() {
      this.leftPos = (this.field_22789 - this.imageWidth) / 2;
      this.topPos = (this.field_22790 - this.imageHeight) / 2;
   }

   public void method_25394(@NotNull class_332 guiGraphics, int mouseX, int mouseY, float partialTick) {
      int i = this.leftPos;
      int j = this.topPos;
      super.method_25394(guiGraphics, mouseX, mouseY, partialTick);
      RenderSystem.disableDepthTest();
      guiGraphics.method_51448().method_22903();
      guiGraphics.method_51448().method_46416(i, j, 0.0F);
      this.hoveredSlot = null;
      if (this.menu != null) {
         for (int k = 0; k < this.menu.field_7761.size(); k++) {
            class_1735 slot = (class_1735)this.menu.field_7761.get(k);
            if (slot.method_7682()) {
               this.renderSlot(guiGraphics, slot);
            }

            if (this.isHovering(slot, mouseX, mouseY) && slot.method_7682()) {
               this.hoveredSlot = slot;
               int l = slot.field_7873;
               int m = slot.field_7872;
               if (this.hoveredSlot.method_51306()) {
                  renderSlotHighlight(guiGraphics, l, m, 0);
               }
            }
         }

         this.renderLabels(guiGraphics, mouseX, mouseY);
         class_1799 itemStack = this.draggingItem.method_7960() ? this.menu.method_34255() : this.draggingItem;
         if (!itemStack.method_7960()) {
            int n = 1;
            int l = this.draggingItem.method_7960() ? 8 : 16;
            String string = null;
            if (!this.draggingItem.method_7960() && this.isSplittingStack) {
               itemStack = itemStack.method_46651(class_3532.method_15386(itemStack.method_7947() / 2.0F));
            } else if (this.isQuickCrafting && this.quickCraftSlots.size() > 1) {
               itemStack = itemStack.method_46651(this.quickCraftingRemainder);
               if (itemStack.method_7960()) {
                  string = class_124.field_1054 + "0";
               }
            }

            this.renderFloatingItem(guiGraphics, itemStack, mouseX - i - 8, mouseY - j - l, string);
         }

         if (!this.snapbackItem.method_7960()) {
            float f = (float)(class_156.method_658() - this.snapbackTime) / 100.0F;
            if (f >= 1.0F) {
               f = 1.0F;
               this.snapbackItem = class_1799.field_8037;
            }

            int l = this.snapbackEnd.field_7873 - this.snapbackStartX;
            int m = this.snapbackEnd.field_7872 - this.snapbackStartY;
            int o = this.snapbackStartX + (int)(l * f);
            int p = this.snapbackStartY + (int)(m * f);
            this.renderFloatingItem(guiGraphics, this.snapbackItem, o, p, null);
         }

         guiGraphics.method_51448().method_22909();
         RenderSystem.enableDepthTest();
      }
   }

   public void method_25420(@NotNull class_332 guiGraphics, int mouseX, int mouseY, float partialTick) {
      super.method_25420(guiGraphics, mouseX, mouseY, partialTick);
      this.renderBg(guiGraphics, partialTick, mouseX, mouseY);
   }

   public static void renderSlotHighlight(class_332 guiGraphics, int x, int y, int blitOffset) {
      guiGraphics.method_51740(class_1921.method_51785(), x, y, x + 16, y + 16, -2130706433, -2130706433, blitOffset);
   }

   protected void renderTooltip(class_332 guiGraphics, int x, int y) {
      if (this.menu.method_34255().method_7960() && this.hoveredSlot != null && this.hoveredSlot.method_7681()) {
         class_1799 itemStack = this.hoveredSlot.method_7677();
         guiGraphics.method_51437(this.field_22793, this.getTooltipFromContainerItem(itemStack), itemStack.method_32347(), x, y);
      }
   }

   protected List<class_2561> getTooltipFromContainerItem(class_1799 stack) {
      return method_25408(this.field_22787, stack);
   }

   private void renderFloatingItem(class_332 guiGraphics, class_1799 stack, int x, int y, String text) {
      guiGraphics.method_51448().method_22903();
      guiGraphics.method_51448().method_46416(0.0F, 0.0F, 232.0F);
      guiGraphics.method_51427(stack, x, y);
      guiGraphics.method_51432(this.field_22793, stack, x, y - (this.draggingItem.method_7960() ? 0 : 8), text);
      guiGraphics.method_51448().method_22909();
   }

   protected void renderLabels(class_332 guiGraphics, int mouseX, int mouseY) {
      guiGraphics.method_51439(this.field_22793, this.field_22785, this.titleLabelX, this.titleLabelY, 4210752, false);
      guiGraphics.method_51439(this.field_22793, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
   }

   protected abstract void renderBg(class_332 var1, float var2, int var3, int var4);

   protected void renderSlot(class_332 guiGraphics, class_1735 slot) {
      int i = slot.field_7873;
      int j = slot.field_7872;
      class_1799 itemStack = slot.method_7677();
      boolean bl = false;
      boolean bl2 = slot == this.clickedSlot && !this.draggingItem.method_7960() && !this.isSplittingStack;
      class_1799 itemStack2 = this.menu.method_34255();
      String string = null;
      if (slot == this.clickedSlot && !this.draggingItem.method_7960() && this.isSplittingStack && !itemStack.method_7960()) {
         itemStack = itemStack.method_46651(itemStack.method_7947() / 2);
      } else if (this.isQuickCrafting && this.quickCraftSlots.contains(slot) && !itemStack2.method_7960()) {
         if (this.quickCraftSlots.size() == 1) {
            return;
         }

         if (class_1703.method_7592(slot, itemStack2, true) && this.menu.method_7615(slot)) {
            bl = true;
            int k = Math.min(itemStack2.method_7914(), slot.method_7676(itemStack2));
            int l = slot.method_7677().method_7960() ? 0 : slot.method_7677().method_7947();
            int m = class_1703.method_7617(this.quickCraftSlots, this.quickCraftingType, itemStack2) + l;
            if (m > k) {
               m = k;
               String var10000 = class_124.field_1054.toString();
               string = var10000 + k;
            }

            itemStack = itemStack2.method_46651(m);
         } else {
            this.quickCraftSlots.remove(slot);
            this.recalculateQuickCraftRemaining();
         }
      }

      guiGraphics.method_51448().method_22903();
      guiGraphics.method_51448().method_46416(0.0F, 0.0F, 100.0F);
      if (itemStack.method_7960() && slot.method_7682()) {
         Pair<class_2960, class_2960> pair = slot.method_7679();
         if (pair != null) {
            class_1058 textureAtlasSprite = (class_1058)this.field_22787.method_1549((class_2960)pair.getFirst()).apply((class_2960)pair.getSecond());
            guiGraphics.method_25298(i, j, 0, 16, 16, textureAtlasSprite);
            bl2 = true;
         }
      }

      if (!bl2) {
         if (bl) {
            guiGraphics.method_25294(i, j, i + 16, j + 16, -2130706433);
         }

         int k = slot.field_7873 + slot.field_7872 * this.imageWidth;
         if (slot.method_55059()) {
            guiGraphics.method_55231(itemStack, i, j, k);
         } else {
            guiGraphics.method_51428(itemStack, i, j, k);
         }

         guiGraphics.method_51432(this.field_22793, itemStack, i, j, string);
      }

      guiGraphics.method_51448().method_22909();
   }

   private void recalculateQuickCraftRemaining() {
      class_1799 itemStack = this.menu.method_34255();
      if (!itemStack.method_7960() && this.isQuickCrafting) {
         if (this.quickCraftingType == 2) {
            this.quickCraftingRemainder = itemStack.method_7914();
         } else {
            this.quickCraftingRemainder = itemStack.method_7947();

            for (class_1735 slot : this.quickCraftSlots) {
               class_1799 itemStack2 = slot.method_7677();
               int i = itemStack2.method_7960() ? 0 : itemStack2.method_7947();
               int j = Math.min(itemStack.method_7914(), slot.method_7676(itemStack));
               int k = Math.min(class_1703.method_7617(this.quickCraftSlots, this.quickCraftingType, itemStack) + i, j);
               this.quickCraftingRemainder -= k - i;
            }
         }
      }
   }

   @Nullable
   private class_1735 findSlot(double mouseX, double mouseY) {
      for (int i = 0; i < this.menu.field_7761.size(); i++) {
         class_1735 slot = (class_1735)this.menu.field_7761.get(i);
         if (this.isHovering(slot, mouseX, mouseY) && slot.method_7682()) {
            return slot;
         }
      }

      return null;
   }

   public boolean method_25402(double mouseX, double mouseY, int button) {
      if (super.method_25402(mouseX, mouseY, button)) {
         return true;
      }

      boolean bl = this.field_22787.field_1690.field_1871.method_1433(button) && this.field_22787.field_1761.method_2914();
      class_1735 slot = this.findSlot(mouseX, mouseY);
      long l = class_156.method_658();
      this.doubleclick = this.lastClickSlot == slot && l - this.lastClickTime < 250L && this.lastClickButton == button;
      this.skipNextRelease = false;
      if (button != 0 && button != 1 && !bl) {
         this.checkHotbarMouseClicked(button);
      } else {
         int i = this.leftPos;
         int j = this.topPos;
         boolean bl2 = this.hasClickedOutside(mouseX, mouseY, i, j, button);
         int k = -1;
         if (slot != null) {
            k = slot.field_7874;
         }

         if (bl2) {
            k = -999;
         }

         if ((Boolean)this.field_22787.field_1690.method_42446().method_41753() && bl2 && this.menu.method_34255().method_7960()) {
            this.method_25419();
            return true;
         }

         if (k != -1) {
            if ((Boolean)this.field_22787.field_1690.method_42446().method_41753()) {
               if (slot != null && slot.method_7681()) {
                  this.clickedSlot = slot;
                  this.draggingItem = class_1799.field_8037;
                  this.isSplittingStack = button == 1;
               } else {
                  this.clickedSlot = null;
               }
            } else if (!this.isQuickCrafting) {
               if (this.menu.method_34255().method_7960()) {
                  if (bl) {
                     this.slotClicked(slot, k, button, class_1713.field_7796);
                  } else {
                     boolean bl3 = k != -999
                        && (
                           class_3675.method_15987(class_310.method_1551().method_22683().method_4490(), 340)
                              || class_3675.method_15987(class_310.method_1551().method_22683().method_4490(), 344)
                        );
                     class_1713 clickType = class_1713.field_7790;
                     if (bl3) {
                        this.lastQuickMoved = slot != null && slot.method_7681() ? slot.method_7677().method_7972() : class_1799.field_8037;
                        clickType = class_1713.field_7794;
                     } else if (k == -999) {
                        clickType = class_1713.field_7795;
                     }

                     this.slotClicked(slot, k, button, clickType);
                  }

                  this.skipNextRelease = true;
               } else {
                  this.isQuickCrafting = true;
                  this.quickCraftingButton = button;
                  this.quickCraftSlots.clear();
                  if (button == 0) {
                     this.quickCraftingType = 0;
                  } else if (button == 1) {
                     this.quickCraftingType = 1;
                  } else if (bl) {
                     this.quickCraftingType = 2;
                  }
               }
            }
         }
      }

      this.lastClickSlot = slot;
      this.lastClickTime = l;
      this.lastClickButton = button;
      return true;
   }

   private void checkHotbarMouseClicked(int keyCode) {
      if (this.hoveredSlot != null && this.menu.method_34255().method_7960()) {
         if (this.field_22787.field_1690.field_1831.method_1433(keyCode)) {
            this.slotClicked(this.hoveredSlot, this.hoveredSlot.field_7874, 40, class_1713.field_7791);
            return;
         }

         for (int i = 0; i < 9; i++) {
            if (this.field_22787.field_1690.field_1852[i].method_1433(keyCode)) {
               this.slotClicked(this.hoveredSlot, this.hoveredSlot.field_7874, i, class_1713.field_7791);
            }
         }
      }
   }

   protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int mouseButton) {
      return mouseX < guiLeft || mouseY < guiTop || mouseX >= guiLeft + this.imageWidth || mouseY >= guiTop + this.imageHeight;
   }

   public boolean method_25403(double mouseX, double mouseY, int button, double dragX, double dragY) {
      class_1735 slot = this.findSlot(mouseX, mouseY);
      class_1799 itemStack = this.menu.method_34255();
      if (this.clickedSlot != null && (Boolean)this.field_22787.field_1690.method_42446().method_41753()) {
         if (button == 0 || button == 1) {
            if (this.draggingItem.method_7960()) {
               if (slot != this.clickedSlot && !this.clickedSlot.method_7677().method_7960()) {
                  this.draggingItem = this.clickedSlot.method_7677().method_7972();
               }
            } else if (this.draggingItem.method_7947() > 1 && slot != null && class_1703.method_7592(slot, this.draggingItem, false)) {
               long l = class_156.method_658();
               if (this.quickdropSlot == slot) {
                  if (l - this.quickdropTime > 500L) {
                     this.slotClicked(this.clickedSlot, this.clickedSlot.field_7874, 0, class_1713.field_7790);
                     this.slotClicked(slot, slot.field_7874, 1, class_1713.field_7790);
                     this.slotClicked(this.clickedSlot, this.clickedSlot.field_7874, 0, class_1713.field_7790);
                     this.quickdropTime = l + 750L;
                     this.draggingItem.method_7934(1);
                  }
               } else {
                  this.quickdropSlot = slot;
                  this.quickdropTime = l;
               }
            }
         }
      } else if (this.isQuickCrafting
         && slot != null
         && !itemStack.method_7960()
         && (itemStack.method_7947() > this.quickCraftSlots.size() || this.quickCraftingType == 2)
         && class_1703.method_7592(slot, itemStack, true)
         && slot.method_7680(itemStack)
         && this.menu.method_7615(slot)) {
         this.quickCraftSlots.add(slot);
         this.recalculateQuickCraftRemaining();
      }

      return true;
   }

   public boolean method_25406(double mouseX, double mouseY, int button) {
      class_1735 slot = this.findSlot(mouseX, mouseY);
      int i = this.leftPos;
      int j = this.topPos;
      boolean bl = this.hasClickedOutside(mouseX, mouseY, i, j, button);
      int k = -1;
      if (slot != null) {
         k = slot.field_7874;
      }

      if (bl) {
         k = -999;
      }

      if (this.doubleclick && slot != null && button == 0 && this.menu.method_7613(class_1799.field_8037, slot)) {
         if (method_25442()) {
            if (!this.lastQuickMoved.method_7960()) {
               for (class_1735 slot2 : this.menu.field_7761) {
                  if (slot2 != null
                     && slot2.method_7674(this.field_22787.field_1724)
                     && slot2.method_7681()
                     && slot2.field_7871 == slot.field_7871
                     && class_1703.method_7592(slot2, this.lastQuickMoved, true)) {
                     this.slotClicked(slot2, slot2.field_7874, button, class_1713.field_7794);
                  }
               }
            }
         } else {
            this.slotClicked(slot, k, button, class_1713.field_7793);
         }

         this.doubleclick = false;
         this.lastClickTime = 0L;
      } else {
         if (this.isQuickCrafting && this.quickCraftingButton != button) {
            this.isQuickCrafting = false;
            this.quickCraftSlots.clear();
            this.skipNextRelease = true;
            return true;
         }

         if (this.skipNextRelease) {
            this.skipNextRelease = false;
            return true;
         }

         if (this.clickedSlot != null && (Boolean)this.field_22787.field_1690.method_42446().method_41753()) {
            if (button == 0 || button == 1) {
               if (this.draggingItem.method_7960() && slot != this.clickedSlot) {
                  this.draggingItem = this.clickedSlot.method_7677();
               }

               boolean bl2 = class_1703.method_7592(slot, this.draggingItem, false);
               if (k != -1 && !this.draggingItem.method_7960() && bl2) {
                  this.slotClicked(this.clickedSlot, this.clickedSlot.field_7874, button, class_1713.field_7790);
                  this.slotClicked(slot, k, 0, class_1713.field_7790);
                  if (this.menu.method_34255().method_7960()) {
                     this.snapbackItem = class_1799.field_8037;
                  } else {
                     this.slotClicked(this.clickedSlot, this.clickedSlot.field_7874, button, class_1713.field_7790);
                     this.snapbackStartX = class_3532.method_15357(mouseX - i);
                     this.snapbackStartY = class_3532.method_15357(mouseY - j);
                     this.snapbackEnd = this.clickedSlot;
                     this.snapbackItem = this.draggingItem;
                     this.snapbackTime = class_156.method_658();
                  }
               } else if (!this.draggingItem.method_7960()) {
                  this.snapbackStartX = class_3532.method_15357(mouseX - i);
                  this.snapbackStartY = class_3532.method_15357(mouseY - j);
                  this.snapbackEnd = this.clickedSlot;
                  this.snapbackItem = this.draggingItem;
                  this.snapbackTime = class_156.method_658();
               }

               this.clearDraggingState();
            }
         } else if (this.isQuickCrafting && !this.quickCraftSlots.isEmpty()) {
            this.slotClicked(null, -999, class_1703.method_7591(0, this.quickCraftingType), class_1713.field_7789);

            for (class_1735 slot2 : this.quickCraftSlots) {
               this.slotClicked(slot2, slot2.field_7874, class_1703.method_7591(1, this.quickCraftingType), class_1713.field_7789);
            }

            this.slotClicked(null, -999, class_1703.method_7591(2, this.quickCraftingType), class_1713.field_7789);
         } else if (!this.menu.method_34255().method_7960()) {
            if (this.field_22787.field_1690.field_1871.method_1433(button)) {
               this.slotClicked(slot, k, button, class_1713.field_7796);
            } else {
               boolean bl2 = k != -999
                  && (
                     class_3675.method_15987(class_310.method_1551().method_22683().method_4490(), 340)
                        || class_3675.method_15987(class_310.method_1551().method_22683().method_4490(), 344)
                  );
               if (bl2) {
                  this.lastQuickMoved = slot != null && slot.method_7681() ? slot.method_7677().method_7972() : class_1799.field_8037;
               }

               this.slotClicked(slot, k, button, bl2 ? class_1713.field_7794 : class_1713.field_7790);
            }
         }
      }

      if (this.menu.method_34255().method_7960()) {
         this.lastClickTime = 0L;
      }

      this.isQuickCrafting = false;
      return true;
   }

   public void clearDraggingState() {
      this.draggingItem = class_1799.field_8037;
      this.clickedSlot = null;
   }

   private boolean isHovering(class_1735 slot, double mouseX, double mouseY) {
      return this.isHovering(slot.field_7873, slot.field_7872, 16, 16, mouseX, mouseY);
   }

   protected boolean isHovering(int x, int y, int width, int height, double mouseX, double mouseY) {
      int i = this.leftPos;
      int j = this.topPos;
      mouseX -= i;
      mouseY -= j;
      return mouseX >= x - 1 && mouseX < x + width + 1 && mouseY >= y - 1 && mouseY < y + height + 1;
   }

   protected void slotClicked(class_1735 slot, int slotId, int mouseButton, class_1713 type) {
      if (slot != null) {
         slotId = slot.field_7874;
      }

      this.field_22787.field_1761.method_2906(this.menu.field_7763, slotId, mouseButton, type, this.field_22787.field_1724);
   }

   protected void handleSlotStateChanged(int slotId, int containerId, boolean newState) {
      this.field_22787.field_1761.method_54634(slotId, containerId, newState);
   }

   public boolean method_25404(int keyCode, int scanCode, int modifiers) {
      if (super.method_25404(keyCode, scanCode, modifiers)) {
         return true;
      }

      if (this.field_22787.field_1690.field_1822.method_1417(keyCode, scanCode)) {
         this.method_25419();
         return true;
      }

      this.checkHotbarKeyPressed(keyCode, scanCode);
      if (this.hoveredSlot != null && this.hoveredSlot.method_7681()) {
         if (this.field_22787.field_1690.field_1871.method_1417(keyCode, scanCode)) {
            this.slotClicked(this.hoveredSlot, this.hoveredSlot.field_7874, 0, class_1713.field_7796);
         } else if (this.field_22787.field_1690.field_1869.method_1417(keyCode, scanCode)) {
            this.slotClicked(this.hoveredSlot, this.hoveredSlot.field_7874, method_25441() ? 1 : 0, class_1713.field_7795);
         }
      }

      return true;
   }

   protected boolean checkHotbarKeyPressed(int keyCode, int scanCode) {
      if (this.menu.method_34255().method_7960() && this.hoveredSlot != null) {
         if (this.field_22787.field_1690.field_1831.method_1417(keyCode, scanCode)) {
            this.slotClicked(this.hoveredSlot, this.hoveredSlot.field_7874, 40, class_1713.field_7791);
            return true;
         }

         for (int i = 0; i < 9; i++) {
            if (this.field_22787.field_1690.field_1852[i].method_1417(keyCode, scanCode)) {
               this.slotClicked(this.hoveredSlot, this.hoveredSlot.field_7874, i, class_1713.field_7791);
               return true;
            }
         }
      }

      return false;
   }

   public void method_25432() {
      if (this.field_22787.field_1724 != null) {
         this.menu.method_7595(this.field_22787.field_1724);
      }
   }

   public boolean method_25421() {
      return false;
   }

   public final void method_25393() {
      super.method_25393();
      if (this.field_22787.field_1724.method_5805() && !this.field_22787.field_1724.method_31481()) {
         this.containerTick();
      } else {
         this.field_22787.field_1724.method_7346();
      }
   }

   protected void containerTick() {
   }

   @NotNull
   public T method_17577() {
      return this.menu;
   }

   public void method_25419() {
      this.field_22787.field_1724.method_7346();
      super.method_25419();
   }
}
