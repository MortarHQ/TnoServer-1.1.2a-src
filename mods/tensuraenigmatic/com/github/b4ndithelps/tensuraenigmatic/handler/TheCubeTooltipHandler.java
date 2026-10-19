package com.github.b4ndithelps.tensuraenigmatic.handler;

import auviotre.enigmatic.legacy.registries.EnigmaticItems;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public class TheCubeTooltipHandler {
   @SubscribeEvent
   public static void onItemTooltip(ItemTooltipEvent event) {
      if (event.getItemStack().is(EnigmaticItems.THE_CUBE) && Screen.hasShiftDown()) {
         int insertIndex = findInsertPosition(event.getToolTip());
         event.getToolTip().add(insertIndex, Component.translatable("tooltip.tensuraenigmatic.the_cube.goddess_blessing").withStyle(ChatFormatting.YELLOW));
         event.getToolTip()
            .add(
               insertIndex + 1,
               Component.translatable(
                     "tooltip.tensuraenigmatic.the_cube.magicule_recovery",
                     new Object[]{(int)(TheCubeEnhancementHandler.getMagiculeRecoveryPercentage() * 100.0)}
                  )
                  .withStyle(ChatFormatting.LIGHT_PURPLE)
            );
         event.getToolTip().add(insertIndex + 2, Component.empty());
      }
   }

   private static int findInsertPosition(List<Component> tooltip) {
      for (int i = 0; i < tooltip.size(); i++) {
         String text = tooltip.get(i).getString();
         if (text.contains("Passive") || text.contains("被动")) {
            return i;
         }
      }

      return Math.min(5, tooltip.size());
   }
}
