package twilightforest.client.overlay.display;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ItemDisplay {
   void render(ItemStack var1, GuiGraphics var2, Minecraft var3, Gui var4, Player var5, int var6);

   default ItemDisplay.DisplayPosition displayPosition() {
      return ItemDisplay.DisplayPosition.DEFAULT;
   }

   ItemDisplay.Bounds getWidgetSize(ItemStack var1, Minecraft var2, Gui var3, Player var4, int var5);

   record Bounds(int startX, int startY, int width, int height) {
   }

   enum DisplayPosition {
      TOP,
      DEFAULT,
      BOTTOM;
   }
}
