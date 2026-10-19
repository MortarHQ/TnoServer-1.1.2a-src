package org.crypticdev.stextras.client;

import net.minecraft.client.Minecraft;
import org.crypticdev.stextras.client.config.STExtrasClientConfig;
import org.crypticdev.stextras.registary.STExtraRegistry;

public final class StextrasClient {
   private static boolean initialized;

   public static void init() {
      if (!initialized) {
         initialized = true;
         STExtrasClientConfig.init(Minecraft.getInstance());
         STExtraRegistry.clientInit();
      }
   }

   private StextrasClient() {
   }
}
