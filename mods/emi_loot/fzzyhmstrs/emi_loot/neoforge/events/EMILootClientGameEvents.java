package fzzyhmstrs.emi_loot.neoforge.events;

import fzzyhmstrs.emi_loot.client.ClientLootTables;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;

@EventBusSubscriber(modid = "emi_loot", bus = Bus.GAME, value = Dist.CLIENT)
public class EMILootClientGameEvents {
   @SubscribeEvent
   public static void onLoggingOut(LoggingOut event) {
      ClientLootTables.INSTANCE.clearLoots();
   }
}
