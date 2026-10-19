package net.blay09.mods.waystones.client.requirement;

import net.blay09.mods.waystones.requirement.CooldownRequirement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class CooldownRequirementRenderer implements RequirementRenderer<CooldownRequirement> {
   public void renderWidget(Player player, CooldownRequirement requirement, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks, int x, int y) {
      String timeLeftStr = formatTimeLeft(player, requirement);
      if (timeLeftStr != null) {
         Font font = Minecraft.getInstance().font;
         guiGraphics.drawString(font, timeLeftStr, x + 1, y + 9 / 2, -21846);
      }
   }

   public int getWidth(Player player, CooldownRequirement requirement) {
      String timeLeftStr = formatTimeLeft(player, requirement);
      if (timeLeftStr == null) {
         return 0;
      }

      Font font = Minecraft.getInstance().font;
      return font.width(timeLeftStr) + 1;
   }

   @Nullable
   private static String formatTimeLeft(Player player, CooldownRequirement requirement) {
      long millisLeft = requirement.getCooldownMillisLeft(player);
      if (millisLeft <= 0L) {
         return null;
      }

      long secondsLeft = millisLeft / 1000L;
      long minutesLeft = secondsLeft / 60L;
      secondsLeft %= 60L;
      return String.format("%02d:%02d", minutesLeft, secondsLeft);
   }
}
