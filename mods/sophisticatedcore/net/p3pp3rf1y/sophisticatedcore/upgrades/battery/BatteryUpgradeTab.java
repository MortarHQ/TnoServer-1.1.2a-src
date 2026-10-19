package net.p3pp3rf1y.sophisticatedcore.upgrades.battery;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.inventory.Slot;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeSettingsTab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;

public class BatteryUpgradeTab extends UpgradeSettingsTab<BatteryUpgradeContainer> {
   public BatteryUpgradeTab(BatteryUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen) {
      super(upgradeContainer, position, screen, TranslationHelper.INSTANCE.translUpgrade("battery"), TranslationHelper.INSTANCE.translUpgradeTooltip("battery"));
      this.openTabDimension = new Dimension(48, 48);
   }

   @Override
   protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
      super.renderBg(guiGraphics, minecraft, mouseX, mouseY);
      if (this.getContainer().isOpen()) {
         GuiHelper.renderSlotsBackground(guiGraphics, this.x + 3, this.y + 24, 1, 1);
         GuiHelper.renderSlotsBackground(guiGraphics, this.x + 24, this.y + 24, 1, 1);
      }
   }

   @Override
   protected void moveSlotsToTab() {
      List<Slot> slots = this.getContainer().getSlots();
      this.positionSlot(slots.get(0), this.screen.getGuiLeft(), this.screen.getGuiTop(), 4);
      this.positionSlot(slots.get(1), this.screen.getGuiLeft(), this.screen.getGuiTop(), 25);
   }

   private void positionSlot(Slot slot, int screenGuiLeft, int screenGuiTop, int xOffset) {
      slot.x = this.x - screenGuiLeft + xOffset;
      slot.y = this.y - screenGuiTop + 25;
   }
}
