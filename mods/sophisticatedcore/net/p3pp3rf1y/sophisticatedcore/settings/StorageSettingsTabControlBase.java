package net.p3pp3rf1y.sophisticatedcore.settings;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.client.gui.SettingsScreen;
import net.p3pp3rf1y.sophisticatedcore.client.gui.SettingsTabControl;
import net.p3pp3rf1y.sophisticatedcore.client.gui.Tab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SettingsContainerMenu;

public abstract class StorageSettingsTabControlBase extends SettingsTabControl<SettingsScreen, SettingsTab<?>> {
   private final List<SettingsTab<?>> settingsTabs = new ArrayList<>();
   protected final SettingsScreen screen;

   protected static <C extends SettingsContainerBase<?>, T extends SettingsTab<C>> void addFactory(
      Builder<String, StorageSettingsTabControlBase.ISettingsTabFactory<?, ?>> builder,
      String categoryName,
      StorageSettingsTabControlBase.ISettingsTabFactory<C, T> factory
   ) {
      builder.put(categoryName, factory);
   }

   protected StorageSettingsTabControlBase(SettingsScreen screen, Position position) {
      super(position);
      this.screen = screen;
      this.addChild(this.instantiateReturnBackTab());
      ((SettingsContainerMenu)screen.getMenu())
         .forEachSettingsContainer(
            (categoryName, settingsContainer) -> {
               if (!this.isSettingsCategoryDisabled(categoryName)) {
                  this.settingsTabs
                     .add(
                        this.addSettingsTab(
                           () -> {}, () -> {}, this.instantiateContainer(categoryName, settingsContainer, new Position(this.x, this.getTopY()), screen)
                        )
                     );
               }
            }
         );
   }

   protected boolean isSettingsCategoryDisabled(String categoryName) {
      return false;
   }

   protected abstract Tab instantiateReturnBackTab();

   public void renderSlotOverlays(
      GuiGraphics guiGraphics, Slot slot, StorageSettingsTabControlBase.ISlotOverlayRenderer overlayRenderer, boolean templateLoadHovered
   ) {
      List<Integer> colors = new ArrayList<>();
      this.settingsTabs.forEach(tab -> tab.getSlotOverlayColor(slot.index, templateLoadHovered).ifPresent(colors::add));
      if (!colors.isEmpty()) {
         int stripeHeight = 16 / colors.size();
         int i = 0;

         for (int color : colors) {
            int yOffset = i * stripeHeight;
            color = color & 16777215 | 1342177280;
            overlayRenderer.renderSlotOverlay(guiGraphics, slot.x, slot.y + yOffset, i == colors.size() - 1 ? 16 - yOffset : stripeHeight, color);
            i++;
         }
      }
   }

   public ItemStack getSlotStackDisplayOverride(int slotNumber, boolean isTemplateLoadHovered) {
      for (SettingsTab<?> settingsTab : this.settingsTabs) {
         ItemStack stack = settingsTab.getItemDisplayOverride(slotNumber, isTemplateLoadHovered);
         if (!stack.isEmpty()) {
            return stack;
         }
      }

      return ItemStack.EMPTY;
   }

   public void renderSlotExtra(GuiGraphics guiGraphics, Slot slot) {
      this.settingsTabs.forEach(tab -> tab.renderExtra(guiGraphics, slot));
   }

   public void handleSlotClick(Slot slot, int mouseButton) {
      this.getOpenTab().ifPresent(tab -> tab.handleSlotClick(slot, mouseButton));
   }

   public boolean renderGuiItem(GuiGraphics guiGraphics, ItemRenderer itemRenderer, ItemStack itemstack, Slot slot, boolean templateLoadHovered) {
      for (SettingsTab<?> tab : this.settingsTabs) {
         int rotation = tab.getItemRotation(slot.index, templateLoadHovered);
         if (rotation != 0) {
            GuiHelper.tryRenderGuiItem(guiGraphics, itemRenderer, this.minecraft.player, itemstack, slot.x, slot.y, rotation);
            return true;
         }
      }

      if (!itemstack.isEmpty()) {
         guiGraphics.renderItem(itemstack, slot.x, slot.y);
         return true;
      } else {
         return false;
      }
   }

   public void drawSlotStackOverlay(GuiGraphics guiGraphics, Slot slot, boolean templateLoadHovered) {
      for (SettingsTab<?> tab : this.settingsTabs) {
         tab.drawSlotStackOverlay(guiGraphics, slot, templateLoadHovered);
      }
   }

   private <C extends SettingsContainerBase<?>> SettingsTab<C> instantiateContainer(String categoryName, C container, Position position, SettingsScreen screen) {
      return this.<C, SettingsTab<C>>getSettingsTabFactory(categoryName).create(container, position, screen);
   }

   protected abstract <C extends SettingsContainerBase<?>, T extends SettingsTab<C>> StorageSettingsTabControlBase.ISettingsTabFactory<C, T> getSettingsTabFactory(
      String var1
   );

   public interface ISettingsTabFactory<C extends SettingsContainerBase<?>, T extends SettingsTab<C>> {
      T create(C var1, Position var2, SettingsScreen var3);
   }

   public interface ISlotOverlayRenderer {
      void renderSlotOverlay(GuiGraphics var1, int var2, int var3, int var4, int var5);
   }
}
