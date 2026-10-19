package net.blay09.mods.waystones.client.requirement;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;

public interface RequirementRenderer<T> {
   void renderWidget(Player var1, T var2, GuiGraphics var3, int var4, int var5, float var6, int var7, int var8);

   @Deprecated
   default int getWidth(T requirement) {
      return 16;
   }

   default int getWidth(Player player, T requirement) {
      return this.getWidth(requirement);
   }

   default int getOrder() {
      return 100;
   }
}
