package net.crypticmc.tr_unique_monsters.registry;

import net.crypticmc.tr_unique_monsters.config.TRUniqueMobsConfigs;
import net.crypticmc.tr_unique_monsters.handler.MasteryHandler;
import net.crypticmc.tr_unique_monsters.registry.skill.ExtraSkills;

public class TRUniqueMobsRegistry {
   public static void init() {
      MasteryHandler.init();
   }

   public static void injectInit() {
      TRUniqueMobsConfigs.init();
      ExtraSkills.init();
   }
}
