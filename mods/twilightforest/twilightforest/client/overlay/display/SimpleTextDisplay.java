package twilightforest.client.overlay.display;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public abstract class SimpleTextDisplay implements ItemDisplay {
   public abstract Component getText(ItemStack var1, Minecraft var2, Gui var3, Player var4);

   @Override
   public void render(ItemStack item, GuiGraphics graphics, Minecraft minecraft, Gui gui, Player player, int widestWidgetWidth) {
      graphics.drawCenteredString(minecraft.font, this.getText(item, minecraft, gui, player), widestWidgetWidth / 2, 0, 16777215);
   }

   @Override
   public ItemDisplay.Bounds getWidgetSize(ItemStack item, Minecraft minecraft, Gui gui, Player player, int widestWidgetWidth) {
      int textWidth = minecraft.font.width(this.getText(item, minecraft, gui, player));
      return new ItemDisplay.Bounds(Math.max(0, widestWidgetWidth / 2 - textWidth / 2), 0, textWidth, 9);
   }
}
