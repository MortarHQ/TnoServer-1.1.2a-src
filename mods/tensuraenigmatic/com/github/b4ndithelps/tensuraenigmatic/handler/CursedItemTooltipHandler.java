package com.github.b4ndithelps.tensuraenigmatic.handler;

import auviotre.enigmatic.legacy.handlers.EnigmaticHandler;
import auviotre.enigmatic.legacy.registries.EnigmaticComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public class CursedItemTooltipHandler {
   @SubscribeEvent
   public static void onItemTooltip(ItemTooltipEvent event) {
      if (event.getEntity() != null && !EnigmaticHandler.isTheCursedOne(event.getEntity())) {
         if ((Boolean)event.getItemStack().getOrDefault(EnigmaticComponents.CURSED, false)) {
            event.getToolTip()
               .replaceAll(
                  component -> (Component)(component.getContents() instanceof TranslatableContents loc
                        && loc.getKey().startsWith("tooltip.enigmaticlegacy.cursedOnesOnly")
                     ? component
                     : Component.literal(obscureString(component.getString())).withStyle(component.getStyle()))
               );
         }
      }
   }

   private static String obscureString(String input) {
      return input.replaceAll("[^\\s]", "§k?");
   }
}
