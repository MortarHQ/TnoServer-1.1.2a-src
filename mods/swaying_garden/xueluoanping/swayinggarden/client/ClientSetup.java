package xueluoanping.swayinggarden.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import xueluoanping.swayinggarden.SwayingGarden;
import xueluoanping.swayinggarden.mixin.SimpleMixinPlugin;

@EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
   @SubscribeEvent
   public static void onClientEvent(FMLClientSetupEvent event) {
      event.enqueueWork(() -> {
         if (!SimpleMixinPlugin.isIrisLikeLoad()) {
            SwayingGarden.LOGGER.warn("Iris like mod is not installed, the mod would not work without that mod.");
         }
      });
   }
}
