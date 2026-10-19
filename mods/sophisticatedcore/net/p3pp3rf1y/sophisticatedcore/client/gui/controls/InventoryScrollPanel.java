package net.p3pp3rf1y.sophisticatedcore.client.gui.controls;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.gui.widget.ScrollPanel;

public class InventoryScrollPanel extends ScrollPanel {
   private static final int TOP_Y_OFFSET = 1;
   private static boolean restrictScrollToScrollbar = false;
   private final InventoryScrollPanel.IInventoryScreen screen;
   private final int firstSlotIndex;
   private final int numberOfSlots;
   private final int slotsInARow;

   public static void setRestrictScrollToScrollbar(boolean restrictScrollToScrollbar) {
      InventoryScrollPanel.restrictScrollToScrollbar = restrictScrollToScrollbar;
   }

   public InventoryScrollPanel(
      Minecraft client, InventoryScrollPanel.IInventoryScreen screen, int firstSlotIndex, int numberOfSlots, int slotsInARow, int height, int top, int left
   ) {
      super(client, slotsInARow * 18 + 6, height, top, left, 0);
      this.screen = screen;
      this.firstSlotIndex = firstSlotIndex;
      this.numberOfSlots = numberOfSlots;
      this.slotsInARow = slotsInARow;
   }

   protected int getScrollAmount() {
      return 18;
   }

   protected int getContentHeight() {
      int rows = this.numberOfSlots / this.slotsInARow + (this.numberOfSlots % this.slotsInARow > 0 ? 1 : 0);
      return rows * 18;
   }

   protected void drawBackground(GuiGraphics guiGraphics, Tesselator tess, float partialTick) {
      this.screen.drawSlotBg(guiGraphics, this.screen.getVisibleSlotsCount());
   }

   protected void drawPanel(GuiGraphics guiGraphics, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
      PoseStack poseStack = guiGraphics.pose();
      poseStack.pushPose();
      poseStack.translate(this.screen.getLeftX(), this.screen.getTopY(), 0.0);
      this.screen.renderInventorySlots(guiGraphics, mouseX, mouseY, this.isMouseOver(mouseX, mouseY));
      poseStack.popPose();
   }

   public Optional<Slot> findSlot(double mouseX, double mouseY) {
      if (!this.isMouseOver(mouseX, mouseY)) {
         return Optional.empty();
      }

      for (int slotIndex = this.firstSlotIndex; slotIndex < this.firstSlotIndex + this.numberOfSlots; slotIndex++) {
         Slot slot = this.screen.getSlot(slotIndex);
         if (this.screen.isMouseOverSlot(slot, mouseX, mouseY) && slot.isActive()) {
            return Optional.of(slot);
         }
      }

      return Optional.empty();
   }

   public NarrationPriority narrationPriority() {
      return NarrationPriority.NONE;
   }

   public void updateNarration(NarrationElementOutput narrationElementOutput) {
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
      if (restrictScrollToScrollbar && this.findSlot(mouseX, mouseY).isPresent()) {
         return false;
      }

      boolean ret = super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
      this.updateSlotsPosition();
      return ret;
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      boolean ret = super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
      this.updateSlotsPosition();
      return ret;
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      return this.isMouseOver(mouseX, mouseY) ? super.mouseClicked(mouseX, mouseY, button) : false;
   }

   public void resetScrollDistance() {
      this.scrollDistance = 0.0F;
   }

   public void updateSlotsPosition() {
      this.screen.setVisibleSlotsCount(0);
      int filteredSlotsCount = 0;

      for (int i = this.firstSlotIndex; i < this.firstSlotIndex + this.numberOfSlots; i++) {
         int rowOffset = (int)this.scrollDistance / 18;
         int row = filteredSlotsCount / this.slotsInARow - rowOffset;
         boolean matchesFilter = this.screen.getStackFilter().test(this.screen.getSlot(i).getItem());
         if (matchesFilter) {
            filteredSlotsCount++;
         }

         int column = this.screen.getVisibleSlotsCount() % this.slotsInARow;
         int newY = this.top - this.screen.getTopY() + row * 18 + 1;
         int newX = this.left - this.screen.getLeftX() + column * 18 + 1;
         if (newY >= 1 && newY <= this.height && matchesFilter) {
            this.screen.setVisibleSlotsCount(this.screen.getVisibleSlotsCount() + 1);
         } else {
            newY = -100;
         }

         this.screen.getSlot(i).y = newY;
         this.screen.getSlot(i).x = newX;
      }
   }

   public interface IInventoryScreen {
      void renderInventorySlots(GuiGraphics var1, int var2, int var3, boolean var4);

      boolean isMouseOverSlot(Slot var1, double var2, double var4);

      void drawSlotBg(GuiGraphics var1, int var2);

      int getTopY();

      int getLeftX();

      Slot getSlot(int var1);

      Predicate<ItemStack> getStackFilter();

      default int getVisibleSlotsCount() {
         return 0;
      }

      default void setVisibleSlotsCount(int visibleSlotsCount) {
      }
   }
}
