package com.github.b4ndithelps.tensuraenigmatic.handler;

import auviotre.enigmatic.legacy.registries.EnigmaticItems;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public class CursedRingTooltipHandler {
   @SubscribeEvent
   public static void onItemTooltip(ItemTooltipEvent event) {
      if (event.getItemStack().is(EnigmaticItems.CURSED_RING) && Screen.hasShiftDown()) {
         int insertIndex = findInsertPosition(event.getToolTip());
         event.getToolTip().add(insertIndex, Component.translatable("tooltip.tensuraenigmatic.cursed_ring.enhanced_header").withStyle(ChatFormatting.GOLD));
         event.getToolTip()
            .add(
               insertIndex + 1,
               Component.translatable(
                     "tooltip.tensuraenigmatic.cursed_ring.magicule_recovery", new Object[]{CursedRingEnhancementHandler.getMagiculeRecoveryPercentage()}
                  )
                  .withStyle(ChatFormatting.LIGHT_PURPLE)
            );
         event.getToolTip()
            .add(
               insertIndex + 2,
               Component.translatable("tooltip.tensuraenigmatic.cursed_ring.flavor")
                  .withStyle(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC})
            );
         event.getToolTip().add(insertIndex + 3, Component.empty());
      }
   }

   private static int findInsertPosition(List<Component> tooltip) {
      for (int i = 1; i < tooltip.size(); i++) {
         if (tooltip.get(i).getString().trim().isEmpty()) {
            return i + 1;
         }
      }

      return Math.min(3, tooltip.size());
   }
}
