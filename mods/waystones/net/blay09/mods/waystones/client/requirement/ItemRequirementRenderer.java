package net.blay09.mods.waystones.client.requirement;

import net.blay09.mods.waystones.requirement.ItemRequirement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;

public class ItemRequirementRenderer implements RequirementRenderer<ItemRequirement> {
   public void renderWidget(Player player, ItemRequirement requirement, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks, int x, int y) {
      Font font = Minecraft.getInstance().font;
      if (!requirement.canAfford(player)) {
         guiGraphics.setColor(1.0F, 1.0F, 1.0F, 0.5F);
      }

      guiGraphics.renderItem(requirement.getItemStack(), x, y, 16, 16);
      guiGraphics.renderItemDecorations(font, requirement.getItemStack(), x, y, requirement.getCount() > 1 ? String.valueOf(requirement.getCount()) : null);
      guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
   }

   @Override
   public int getOrder() {
      return 10;
   }
}
