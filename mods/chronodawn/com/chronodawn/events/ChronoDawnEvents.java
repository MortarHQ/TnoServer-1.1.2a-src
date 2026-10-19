package com.chronodawn.events;

import com.chronodawn.ChronoDawn;

public class ChronoDawnEvents {
   public static void register() {
      EntityEventHandler.register();
      BlockEventHandler.register();
      PlayerEventHandler.register();
      TimeDistortionEventHandler.register();
      ChronoShieldTickHandler.register();
      ChronoDawn.LOGGER.debug("Registered ChronoDawnEvents");
   }
}
