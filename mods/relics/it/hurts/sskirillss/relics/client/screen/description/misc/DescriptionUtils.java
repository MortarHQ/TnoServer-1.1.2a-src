package it.hurts.sskirillss.relics.client.screen.description.misc;

import it.hurts.sskirillss.relics.client.screen.description.ability.AbilityDescriptionScreen;
import it.hurts.sskirillss.relics.client.screen.description.experience.ExperienceDescriptionScreen;
import it.hurts.sskirillss.relics.client.screen.description.relic.RelicDescriptionScreen;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class DescriptionUtils {
   public static final int TEXT_COLOR = 6696723;
   private static final ResourceLocation TOOLTIP = ResourceLocation.fromNamespaceAndPath("relics", "textures/gui/description/general/tooltip.png");

   @OnlyIn(Dist.CLIENT)
   public static void drawTooltipBackground(GuiGraphics guiGraphics, int width, int height, int x, int y) {
      int texWidth = 12;
      int texHeight = 15;
      int xStep = 0;

      for (int i = 0; i < 2; i++) {
         guiGraphics.blit(TOOLTIP, x + xStep, y, 9, 7, 0.0F, 0.0F, 9, 7, texWidth, texHeight);
         guiGraphics.blit(TOOLTIP, x + xStep, y + 7, 9, height + 4, 0.0F, 7.0F, 9, 1, texWidth, texHeight);
         guiGraphics.blit(TOOLTIP, x + xStep, y + height + 9, 9, 7, 0.0F, 8.0F, 9, 7, texWidth, texHeight);
         xStep += width + 9;
      }

      guiGraphics.blit(TOOLTIP, x + 7, y + 5, 1, height + 6, 9.0F, 0.0F, 1, 1, texWidth, texHeight);
      guiGraphics.blit(TOOLTIP, x + width + 10, y + 5, 1, height + 6, 10.0F, 0.0F, 1, 1, texWidth, texHeight);
      guiGraphics.blit(TOOLTIP, x + 8, y + 5, width + 2, 3, 11.0F, 0.0F, 1, 3, texWidth, texHeight);
      guiGraphics.blit(TOOLTIP, x + 8, y + 8, width + 2, height + 1, 11.0F, 3.0F, 1, 1, texWidth, texHeight);
      guiGraphics.blit(TOOLTIP, x + 8, y + height + 9, width + 2, 3, 11.0F, 4.0F, 1, 3, texWidth, texHeight);
   }

   public static ItemStack gatherRelicStack(Player player, int slot) {
      if (!player.containerMenu.isValidSlotIndex(slot)) {
         return ItemStack.EMPTY;
      }

      ItemStack stack = player.containerMenu.getSlot(slot).getItem();
      return !(stack.getItem() instanceof IRelicItem) ? ItemStack.EMPTY : stack;
   }

   @OnlyIn(Dist.CLIENT)
   public static void openCachedScreen(IRelicItem relic, Player player, int slot, Screen screen) {
      Minecraft.getInstance().setScreen(switch (DescriptionCache.getEntry(relic).getSelectedPage()) {
         case ABILITY -> new AbilityDescriptionScreen(player, player.containerMenu.containerId, slot, screen);
         case EXPERIENCE -> new ExperienceDescriptionScreen(player, player.containerMenu.containerId, slot, screen);
         default -> new RelicDescriptionScreen(player, player.containerMenu.containerId, slot, screen);
      });
   }
}
