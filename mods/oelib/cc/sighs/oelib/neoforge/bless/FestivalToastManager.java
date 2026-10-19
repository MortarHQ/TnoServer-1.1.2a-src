package cc.sighs.oelib.neoforge.bless;

import cc.sighs.oelib.bless.util.BlessUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingIn;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = "oelib", value = Dist.CLIENT)
public class FestivalToastManager {
   private static int delayTicks = -1;

   private FestivalToastManager() {
   }

   @SubscribeEvent
   public static void onClientLoggingIn(LoggingIn event) {
      delayTicks = 40;
   }

   @SubscribeEvent
   public static void onClientTick(Post event) {
      if (delayTicks >= 0) {
         delayTicks--;
         if (delayTicks <= 0) {
            delayTicks = -1;
            BlessUtil.showFestivalToast();
         }
      }
   }
}
