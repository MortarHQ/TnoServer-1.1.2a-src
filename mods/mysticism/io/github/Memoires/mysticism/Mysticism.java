package io.github.Memoires.mysticism;

import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import io.github.Memoires.mysticism.client.MysticismClient;
import io.github.Memoires.mysticism.command.MysticismCommands;
import io.github.Memoires.mysticism.config.MysticismConfigs;
import io.github.Memoires.mysticism.handler.MysticismHandlers;
import io.github.Memoires.mysticism.network.MysticismNetwork;
import io.github.Memoires.mysticism.registry.MysticismRegistry;
import io.github.Memoires.mysticism.storage.MysticismStorages;
import io.github.Memoires.mysticism.util.TimerUtils;
import io.github.Memoires.mysticism.world.MysticismGameRules;

public final class Mysticism {
   public static final String MOD_ID = "mysticism";

   public static void init() {
      MysticismConfigs.init();
      MysticismRegistry.init();
      MysticismHandlers.init();
      TimerUtils.register();
      MysticismStorages.init();
      MysticismNetwork.init();
      MysticismCommands.init();
      MysticismGameRules.init();
      MysticismConfigs.addToConfig();
      if (Platform.getEnvironment() == Env.CLIENT) {
         MysticismClient.init();
      }

      try {
         Class.forName("io.github.Memoires.mysticism.handler.BetaHandler");
      } catch (ClassNotFoundException e) {
         throw new RuntimeException("Modification to remove BetaHandler.class has been detected.", e);
      }
   }
}
