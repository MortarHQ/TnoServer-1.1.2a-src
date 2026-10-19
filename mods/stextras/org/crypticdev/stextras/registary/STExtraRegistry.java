package org.crypticdev.stextras.registary;

import dev.architectury.platform.Platform;
import org.crypticdev.stextras.effect.skill.WispModeEffect;
import org.crypticdev.stextras.handler.HandlePrestigeListeners;
import org.crypticdev.stextras.handler.HandleSkillLock;
import org.crypticdev.stextras.handler.ServerHooks;
import org.crypticdev.stextras.integration.FTB.Quests.FTBQuestsIntegration;
import org.crypticdev.stextras.integration.lootr.LootrIntegration;
import org.crypticdev.stextras.quest.registry.QuestRegistry;
import org.crypticdev.stextras.quest.schedule.QuestScheduleService;
import org.crypticdev.stextras.registary.effects.STExtrasMobEffects;
import org.crypticdev.stextras.registary.item.STExtrasItems;
import org.crypticdev.stextras.registary.menu.STExtrasMenuTypes;
import org.crypticdev.stextras.registary.race.STExtrasRaces;
import org.crypticdev.stextras.registary.skill.STExtrasExtraSkills;
import org.crypticdev.stextras.registary.skill.STExtrasIntrinsicSkills;
import org.crypticdev.stextras.registary.skill.STExtrasUniqueSkills;
import org.crypticdev.stextras.registary.sounds.STExtrasSoundEvents;

public class STExtraRegistry {
   public static void init() {
      STExtrasCommands.init();
      ServerHooks.register();
      STExtrasNet.init();
      STExtrasSoundEvents.init();
      STExtrasLootTables.init();
      QuestRegistry.registerReloadListener();
      HandlePrestigeListeners.init();
      HandleSkillLock.init();
      STExtrasMenuTypes.init();
      QuestScheduleService.initServerHooks();
      LootrIntegration.init();
      if (Platform.isModLoaded("ftbquests")) {
         FTBQuestsIntegration.init();
      }
   }

   public static void injectInit() {
      STExtrasMobEffects.init();
      WispModeEffect.initServerHooks();
      STExtrasItems.init();
      STExtrasRaces.init();
      STExtrasIntrinsicSkills.init();
      STExtrasExtraSkills.init();
      STExtrasUniqueSkills.init();
   }

   public static void clientInit() {
      STExtrasNet.initClient();
   }
}
