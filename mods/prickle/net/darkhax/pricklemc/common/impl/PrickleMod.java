package net.darkhax.pricklemc.common.impl;

import net.darkhax.pricklemc.common.api.services.Services;

public class PrickleMod {
   private static PrickleMod instance;
   private boolean hasInitialized = false;

   public void init() {
      if (this.hasInitialized) {
         throw new IllegalStateException("The Prickle has already been initialized.");
      }

      this.runStartupChecks();
      this.hasInitialized = true;
   }

   private void runStartupChecks() {
      if (Services.PLATFORM == null) {
         throw new IllegalStateException("Services are not available.");
      }
   }

   public static PrickleMod getInstance() {
      if (instance == null) {
         instance = new PrickleMod();
      }

      return instance;
   }
}
