package net.blay09.mods.waystones.client.requirement;

import net.blay09.mods.waystones.requirement.ExperiencePointsRequirement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class ExperiencePointsRequirementRenderer implements RequirementRenderer<ExperiencePointsRequirement> {
   private static final ResourceLocation[] ENABLED_LEVEL_SPRITES = new ResourceLocation[]{
      ResourceLocation.withDefaultNamespace("container/enchanting_table/level_1"),
      ResourceLocation.withDefaultNamespace("container/enchanting_table/level_2"),
      ResourceLocation.withDefaultNamespace("container/enchanting_table/level_3")
   };
   private static final ResourceLocation[] DISABLED_LEVEL_SPRITES = new ResourceLocation[]{
      ResourceLocation.withDefaultNamespace("container/enchanting_table/level_1_disabled"),
      ResourceLocation.withDefaultNamespace("container/enchanting_table/level_2_disabled"),
      ResourceLocation.withDefaultNamespace("container/enchanting_table/level_3_disabled")
   };

   public void renderWidget(
      Player player, ExperiencePointsRequirement requirement, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks, int x, int y
   ) {
      int points = requirement.getPoints();
      int levels = points > 0 ? Math.max(1, ExperiencePointsRequirement.calculateLevelCostFromExperiencePoints(player.experienceLevel, points)) : 0;
      if (levels > 0) {
         boolean canAfford = requirement.canAfford(player);
         int spriteIndex = Math.max(0, Math.min(levels, 3) - 1);
         guiGraphics.blitSprite(canAfford ? ENABLED_LEVEL_SPRITES[spriteIndex] : DISABLED_LEVEL_SPRITES[spriteIndex], x, y, 16, 16);
         Font font = Minecraft.getInstance().font;
         if (levels > 3) {
            guiGraphics.drawString(font, "+", x + 15, y + 4, 13172623);
         }
      }
   }
}
