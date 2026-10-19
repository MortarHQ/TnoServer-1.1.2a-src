package com.tom.storagemod.polymorph;

import com.illusivesoulworks.polymorph.api.client.PolymorphWidgets;
import com.illusivesoulworks.polymorph.api.client.widgets.PlayerRecipesWidget;
import com.tom.storagemod.menu.CraftingTerminalMenu;
import com.tom.storagemod.screen.CraftingTerminalScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class PolymorphTerminalWidget extends PlayerRecipesWidget {
   protected final CraftingTerminalMenu menu;

   public PolymorphTerminalWidget(CraftingTerminalScreen containerScreen) {
      super(containerScreen, ((CraftingTerminalMenu)containerScreen.getMenu()).getCraftingResultSlot());
      this.menu = (CraftingTerminalMenu)containerScreen.getMenu();
   }

   public void selectRecipe(ResourceLocation resourceLocation) {
      super.selectRecipe(resourceLocation);
      Minecraft mc = Minecraft.getInstance();
      mc.level.getRecipeManager().byKey(resourceLocation).ifPresent(recipe -> mc.gameMode.handleInventoryButtonClick(this.menu.containerId, 1));
   }

   public void initChildWidgets() {
      Minecraft mc = Minecraft.getInstance();
      mc.gameMode.handleInventoryButtonClick(this.menu.containerId, 1);
      super.initChildWidgets();
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float renderPartialTicks) {
      this.resetWidgetOffsets();
      super.render(guiGraphics, mouseX, mouseY, renderPartialTicks);
   }

   public static void register() {
      PolymorphWidgets.getInstance().registerWidget(screen -> screen instanceof CraftingTerminalScreen s ? new PolymorphTerminalWidget(s) : null);
   }
}
