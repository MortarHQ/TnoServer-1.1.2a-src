package bigchadguys.sellingbin.util;

import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.client.ClientTickEvent.Client;

public class ClientScheduler {
   public static long TICK = 0L;

   public static long getTick() {
      return TICK;
   }

   public static double getTick(double delta) {
      return TICK + delta;
   }

   static {
      ClientTickEvent.CLIENT_PRE.register((Client)instance -> TICK++);
   }
}
