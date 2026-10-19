package net.p3pp3rf1y.sophisticatedcore.settings;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntConsumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.client.gui.SettingsScreen;
import net.p3pp3rf1y.sophisticatedcore.client.gui.SettingsTabBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;

public abstract class SettingsTab<C extends SettingsContainerBase<?>> extends SettingsTabBase<SettingsScreen> {
   private final C settingsContainer;

   protected SettingsTab(
      C settingsContainer,
      Position position,
      SettingsScreen screen,
      Component tabLabel,
      List<Component> tooltip,
      List<Component> openTooltip,
      Function<IntConsumer, ButtonBase> getTabButton
   ) {
      super(position, screen, tabLabel, tooltip, openTooltip, getTabButton);
      this.settingsContainer = settingsContainer;
   }

   protected C getSettingsContainer() {
      return this.settingsContainer;
   }

   public abstract Optional<Integer> getSlotOverlayColor(int var1, boolean var2);

   public abstract void handleSlotClick(Slot var1, int var2);

   public int getItemRotation(int slotIndex, boolean templateLoadHovered) {
      return 0;
   }

   public void renderExtra(GuiGraphics guiGraphics, Slot slot) {
   }

   public ItemStack getItemDisplayOverride(int slotNumber, boolean templateLoadHovered) {
      return ItemStack.EMPTY;
   }

   public void drawSlotStackOverlay(GuiGraphics guiGraphics, Slot slot, boolean templateLoadHovered) {
   }
}
