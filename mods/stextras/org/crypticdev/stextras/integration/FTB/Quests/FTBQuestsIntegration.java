package org.crypticdev.stextras.integration.FTB.Quests;

public final class FTBQuestsIntegration {
   private static boolean initialized;

   private FTBQuestsIntegration() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         STExtrasTaskTypes.init();
         STExtrasRewardTypes.init();
         FTBQuestsEventBridge.init();
      }
   }
}
