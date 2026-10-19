package fzzyhmstrs.emi_loot.neoforge.events;

import fzzyhmstrs.emi_loot.EMILootClient;
import fzzyhmstrs.emi_loot.client.ClientResourceData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

@EventBusSubscriber(modid = "emi_loot", bus = Bus.MOD, value = Dist.CLIENT)
public class EMILootClientModEvents {
   @SubscribeEvent
   public static void onClientSetup(FMLClientSetupEvent event) {
      EMILootClient.onInitializeClient();
   }

   @SubscribeEvent
   public static void onResourceReload(RegisterClientReloadListenersEvent event) {
      event.registerReloadListener(new ClientResourceData.EntityOffsetsReloadListener());
   }
}
