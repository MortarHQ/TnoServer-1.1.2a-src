package fzzyhmstrs.emi_loot.neoforge.events;

import fzzyhmstrs.emi_loot.EMILoot;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

@EventBusSubscriber(modid = "emi_loot", bus = Bus.GAME)
public class EMILootGameEvents {
   @SubscribeEvent
   public static void onDatapackSync(OnDatapackSyncEvent event) {
      event.getRelevantPlayers().forEach(player -> EMILoot.parser.registerServer(player));
   }
}
