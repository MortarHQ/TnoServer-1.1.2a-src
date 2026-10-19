package com.foxablazeultimate.client.screen;

import com.foxablazeultimate.network.RequestPredationFilterSyncPayload;
import com.foxablazeultimate.network.UpdatePredationFilterPayload;
import com.foxablazeultimate.predation.PredationFilterHelper;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.tensura.util.client.RenderHelper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

public final class PredationFilterOverlay {
   public static final ResourceLocation BUTTON_TEXTURE = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "textures/gui/predation_filter.png");
   public static final int BUTTON_WIDTH = 24;
   public static final int BUTTON_HEIGHT = 21;
   private static final int BUTTON_X_INSET = 20;
   private static final int DEFAULT_BUTTON_Y_INSET = 2;
   private static final int SLOT_SIZE = 18;
   private static final int ROW_FRAME_INSET = 4;
   private static final int COLOR_FRAME_OUTER = -15789020;
   private static final int COLOR_FRAME_INNER = -13879728;
   private static final int COLOR_HIGHLIGHT = -10851700;
   private static final int COLOR_SLOT_BG = -15064522;
   private static final int COLOR_SLOT_DARK = -13158601;
   private static int slotCount;
   private static List<ItemStack> items = new ArrayList<>();
   private static boolean expanded;
   private static int buttonYInset = 2;

   private PredationFilterOverlay() {
   }

   public static void onScreenInit(int buttonYInsetForScreen) {
      buttonYInset = buttonYInsetForScreen;
      expanded = false;
      if (!hasRaphael()) {
         slotCount = 0;
         items = new ArrayList<>();
      } else {
         NetworkManager.sendToServer(new RequestPredationFilterSyncPayload());
      }
   }

   public static void forEachOverlayBound(int leftPos, int topPos, int imageWidth, PredationFilterOverlay.BoundsConsumer consumer) {
      if (hasRaphael()) {
         consumer.accept(buttonX(leftPos), buttonY(topPos), 24, 21);
         if (expanded && slotCount > 0) {
            int rowX = rowStartX(leftPos);
            int rowYpos = rowY(topPos);
            consumer.accept(rowX - 4, rowYpos - 4, slotCount * 18 + 8, 26);
         }
      }
   }

   public static boolean isMouseOverOverlay(double mouseX, double mouseY, int leftPos, int topPos, int imageWidth) {
      if (!hasRaphael()) {
         return false;
      }

      int btnX = buttonX(leftPos);
      int btnY = buttonY(topPos);
      if (isMouseOver(btnX, btnY, 24, 21, mouseX, mouseY)) {
         return true;
      }

      if (!expanded) {
         return false;
      }

      int rowX = rowStartX(leftPos);
      int rowYPos = rowY(topPos);
      int x1 = rowX - 4;
      int x2 = rowX + slotCount * 18 + 4;
      int y1 = rowYPos - 4;
      int y2 = rowYPos + 18 + 4;
      return mouseX >= x1 && mouseX < x2 && mouseY >= y1 && mouseY < y2;
   }

   public static void applySync(int incomingSlots, List<ItemStack> incomingItems) {
      slotCount = Math.max(0, incomingSlots);
      items = new ArrayList<>(slotCount);

      for (int i = 0; i < slotCount; i++) {
         ItemStack stack = i < incomingItems.size() ? incomingItems.get(i) : ItemStack.EMPTY;
         items.add(stack != null && !stack.isEmpty() ? stack.copyWithCount(1) : ItemStack.EMPTY);
      }
   }

   public static void render(GuiGraphics graphics, int leftPos, int topPos, int imageWidth, int mouseX, int mouseY) {
      if (hasRaphael()) {
         if (expanded) {
            renderRow(graphics, leftPos, topPos);
            renderGhostItems(graphics, leftPos, topPos);
         }

         renderButton(graphics, leftPos, topPos, mouseX, mouseY);
      }
   }

   private static void renderGhostItems(GuiGraphics graphics, int leftPos, int topPos) {
      int rowX = rowStartX(leftPos);
      int rowYPos = rowY(topPos);
      Minecraft mc = Minecraft.getInstance();

      for (int i = 0; i < slotCount; i++) {
         ItemStack ghost = ghostAt(i);
         if (!ghost.isEmpty()) {
            int x = rowX + i * 18 + 1;
            int y = rowYPos + 1;
            graphics.renderItem(ghost, x, y);
            graphics.renderItemDecorations(mc.font, ghost, x, y, "");
         }
      }
   }

   public static void renderTooltip(GuiGraphics graphics, int leftPos, int topPos, int imageWidth, int mouseX, int mouseY) {
      if (hasRaphael()) {
         int buttonX = buttonX(leftPos);
         int buttonY = buttonY(topPos);
         if (isMouseOver(buttonX, buttonY, 24, 21, mouseX, mouseY)) {
            Component header = Component.translatable("foxablazeultimate.predation_filter.button");
            Component hint = Component.translatable(
               expanded ? "foxablazeultimate.predation_filter.button.collapse" : "foxablazeultimate.predation_filter.button.expand"
            );
            graphics.renderTooltip(Minecraft.getInstance().font, List.of(header.getVisualOrderText(), hint.getVisualOrderText()), mouseX, mouseY);
         } else if (expanded) {
            int hovered = hoveredSlot(leftPos, topPos, mouseX, mouseY);
            if (hovered >= 0) {
               ItemStack ghost = ghostAt(hovered);
               if (!ghost.isEmpty()) {
                  graphics.renderTooltip(Minecraft.getInstance().font, ghost, mouseX, mouseY);
               }
            }
         }
      }
   }

   private static void renderButton(GuiGraphics graphics, int leftPos, int topPos, int mouseX, int mouseY) {
      int x = buttonX(leftPos);
      int y = buttonY(topPos);
      boolean hover = isMouseOver(x, y, 24, 21, mouseX, mouseY);
      int v = !hover && !expanded ? 0 : 21;
      graphics.blit(BUTTON_TEXTURE, x, y, 0.0F, v, 24, 21, 24, 42);
   }

   private static void renderRow(GuiGraphics graphics, int leftPos, int topPos) {
      int rowX = rowStartX(leftPos);
      int rowYPos = rowY(topPos);
      int totalW = slotCount * 18 + 8;
      int totalH = 26;
      int panelX = rowX - 4;
      int panelY = rowYPos - 4;
      graphics.fill(panelX, panelY, panelX + totalW, panelY + totalH, -15789020);
      graphics.fill(panelX + 1, panelY + 1, panelX + totalW - 1, panelY + totalH - 1, -13879728);
      graphics.fill(panelX + 1, panelY + 1, panelX + totalW - 1, panelY + 2, -10851700);

      for (int i = 0; i < slotCount; i++) {
         int sx = rowX + i * 18;
         int sy = rowYPos;
         graphics.fill(sx, sy, sx + 18, sy + 18, -13158601);
         graphics.fill(sx + 1, sy + 1, sx + 18, sy + 18, -10851700);
         graphics.fill(sx + 1, sy + 1, sx + 18 - 1, sy + 18 - 1, -15064522);
      }
   }

   public static boolean mouseClicked(double mouseX, double mouseY, int button, int leftPos, int topPos, int imageWidth) {
      if (!hasRaphael()) {
         return false;
      }

      int btnX = buttonX(leftPos);
      int btnY = buttonY(topPos);
      if (isMouseOver(btnX, btnY, 24, 21, mouseX, mouseY)) {
         if (button == 0) {
            expanded = !expanded;
            playClickSound(1.0F);
         }

         return true;
      } else {
         if (!isMouseOverOverlay(mouseX, mouseY, leftPos, topPos, imageWidth)) {
            return false;
         }

         if (expanded) {
            int hovered = hoveredSlot(leftPos, topPos, mouseX, mouseY);
            if (hovered >= 0) {
               ItemStack carried = currentCarried();
               ItemStack ghost = carried.isEmpty() ? ItemStack.EMPTY : carried.copyWithCount(1);
               if (!ghost.isEmpty() && !canAccept(ghost, hovered)) {
                  playClickSound(0.25F);
                  return true;
               }

               if (hovered < items.size()) {
                  items.set(hovered, ghost);
               }

               NetworkManager.sendToServer(new UpdatePredationFilterPayload(hovered, ghost));
               playClickSound(0.6F);
            }
         }

         return true;
      }
   }

   private static boolean canAccept(ItemStack ghost, int targetSlot) {
      if (!(ghost.getItem() instanceof BlockItem)) {
         return false;
      }

      for (int i = 0; i < items.size(); i++) {
         if (i != targetSlot) {
            ItemStack entry = items.get(i);
            if (!entry.isEmpty() && entry.is(ghost.getItem())) {
               return false;
            }
         }
      }

      return true;
   }

   private static void playClickSound(float volume) {
      Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, volume));
   }

   private static int buttonX(int leftPos) {
      return leftPos + 20;
   }

   private static int buttonY(int topPos) {
      return topPos + buttonYInset;
   }

   private static int rowStartX(int leftPos) {
      return buttonX(leftPos) + 24 + 4;
   }

   private static int rowY(int topPos) {
      return buttonY(topPos) + 1;
   }

   private static int hoveredSlot(int leftPos, int topPos, double mouseX, double mouseY) {
      if (slotCount <= 0) {
         return -1;
      } else {
         int rowX = rowStartX(leftPos);
         int rowYPos = rowY(topPos);
         if (mouseY < rowYPos || mouseY >= rowYPos + 18) {
            return -1;
         } else if (!(mouseX < rowX) && !(mouseX >= rowX + slotCount * 18)) {
            int col = (int)((mouseX - rowX) / 18.0);
            return col >= 0 && col < slotCount ? col : -1;
         } else {
            return -1;
         }
      }
   }

   private static boolean isMouseOver(int x, int y, int w, int h, double mouseX, double mouseY) {
      return RenderHelper.mouseOver(mouseX, mouseY, x, x + w, y, y + h);
   }

   private static ItemStack ghostAt(int i) {
      if (i >= 0 && i < items.size()) {
         ItemStack stack = items.get(i);
         return stack == null ? ItemStack.EMPTY : stack;
      } else {
         return ItemStack.EMPTY;
      }
   }

   private static ItemStack currentCarried() {
      LocalPlayer player = Minecraft.getInstance().player;
      return player != null && player.containerMenu != null ? player.containerMenu.getCarried() : ItemStack.EMPTY;
   }

   private static boolean hasRaphael() {
      LocalPlayer player = Minecraft.getInstance().player;
      return player != null && PredationFilterHelper.hasRaphael(player);
   }

   @FunctionalInterface
   public interface BoundsConsumer {
      void accept(int var1, int var2, int var3, int var4);
   }
}
