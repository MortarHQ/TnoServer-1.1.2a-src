package it.hurts.octostudios.rarcompat.handlers;

import it.hurts.octostudios.rarcompat.items.WearableRelicItem;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber
public class TooltipHandler {
   @SubscribeEvent
   public static void onTooltip(ItemTooltipEvent event) {
      if (event.getItemStack().getItem() instanceof WearableRelicItem) {
         List<Component> tooltip = event.getToolTip();

         for (int i = 0; i < tooltip.size(); i++) {
            Component component = tooltip.get(i);
            if (component.getString().startsWith("artifacts")) {
               tooltip.set(
                  i,
                  Component.empty()
                     .setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GRAY))
                     .append(component)
                     .append(" | ")
                     .append(Component.translatable("tooltip.rarcompat.modified"))
               );
               break;
            }
         }
      }
   }
}
