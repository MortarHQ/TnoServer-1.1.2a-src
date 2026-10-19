package com.tensura_tno;

import com.mojang.logging.LogUtils;
import com.tensura_tno.ability.skill.SpiritSummonLimits;
import com.tensura_tno.client.HeatSourceCollisionXray;
import com.tensura_tno.client.MagicOreSenseXray;
import com.tensura_tno.command.TNOAdminCommands;
import com.tensura_tno.compat.CuriosSoulBoundDeathHandler;
import com.tensura_tno.compat.TRUniqueMonstersCompat;
import com.tensura_tno.compat.TrAddonCompat;
import com.tensura_tno.compat.YigdCompat;
import com.tensura_tno.compat.jade.JadeCompatBootstrap;
import com.tensura_tno.config.TNOConfigInjector;
import com.tensura_tno.config.TNOGeneralConfig;
import com.tensura_tno.event.ContractLittleFoxEvents;
import com.tensura_tno.event.GhoulDaySleepHandler;
import com.tensura_tno.event.HarvestFestivalAchievementHandler;
import com.tensura_tno.event.HumanFormRaceChangeHandler;
import com.tensura_tno.event.IceAndFireDragonRemainsHandler;
import com.tensura_tno.event.MagicOreSenseAcquisitionHandler;
import com.tensura_tno.event.MysticismRestrictedSkillHandler;
import com.tensura_tno.event.RimuruModeReincarnationHandler;
import com.tensura_tno.event.SlimePredationHumanFormHandler;
import com.tensura_tno.food.FoodEPHandler;
import com.tensura_tno.food.FoodEPManager;
import com.tensura_tno.ftb.TNOFtbQuests;
import com.tensura_tno.network.ContractLittleFoxPackets;
import com.tensura_tno.network.EdoTenseiPackets;
import com.tensura_tno.network.MagicOreSensePackets;
import com.tensura_tno.network.ReincarnationRulesPackets;
import com.tensura_tno.network.RimuruModePackets;
import com.tensura_tno.network.SlimeHumanFormPackets;
import com.tensura_tno.network.SpiritSummonPackets;
import com.tensura_tno.race.SlimeHumanFormState;
import com.tensura_tno.race.fox_spirit.SpiritSummonNamingHandler;
import com.tensura_tno.race.fox_spirit.SpiritSummonPocketPersistence;
import com.tensura_tno.race.fox_spirit.SpiritSummonRaceSyncHandler;
import com.tensura_tno.registry.TensuraTNOBackpacks;
import com.tensura_tno.registry.TensuraTNOBlockEntities;
import com.tensura_tno.registry.TensuraTNOBlocks;
import com.tensura_tno.registry.TensuraTNOEntities;
import com.tensura_tno.registry.TensuraTNOItems;
import com.tensura_tno.registry.TensuraTNOLootModifiers;
import com.tensura_tno.registry.TensuraTNORaces;
import com.tensura_tno.registry.TensuraTNORecipeSerializers;
import com.tensura_tno.registry.TensuraTNOSkills;
import com.tensura_tno.world.TensuraTNOGameRules;
import com.tensura_tno.world.spawn.BiomeBlacklistConfig;
import com.tensura_tno.world.spawn.SpawnRelocator;
import com.tensura_tno.world.spawn.SpawnRespawnRelocator;
import io.github.manasmods.manascore.config.ConfigRegistry;
import java.util.concurrent.atomic.AtomicBoolean;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.slf4j.Logger;

@Mod("tensura_tno")
public class TensuraTNOMod {
   public static final String MOD_ID = "tensura_tno";
   public static final Logger LOGGER = LogUtils.getLogger();
   private static final AtomicBoolean compatInitDone = new AtomicBoolean(false);

   public TensuraTNOMod(IEventBus modBus, ModContainer modContainer) {
      modContainer.registerConfig(Type.COMMON, TensuraTNOCompatConfig.COMMON_SPEC);
      modContainer.registerConfig(Type.CLIENT, TensuraTNOCompatConfig.CLIENT_SPEC);
      ConfigRegistry.registerConfig(new TNOGeneralConfig());
      TensuraTNOGameRules.init();
      this.register(modBus);
      JadeCompatBootstrap.registerIfLoaded();
      modContainer.registerConfig(Type.COMMON, BiomeBlacklistConfig.COMMON_SPEC, "tensura_tno-spawn.toml");
      modBus.addListener(BiomeBlacklistConfig::onConfigLoad);
      NeoForge.EVENT_BUS.addListener(SpawnRelocator::onServerStarted);
      NeoForge.EVENT_BUS.addListener(SpawnRelocator::onPlayerLoggedIn);
      NeoForge.EVENT_BUS.addListener(SpawnRespawnRelocator::onPlayerRespawn);
      modBus.addListener(EventPriority.HIGHEST, RegisterEvent.class, event -> {
         if (compatInitDone.compareAndSet(false, true)) {
            TRUniqueMonstersCompat.triggerDeferredInit();
            TrAddonCompat.triggerDeferredInit();
         }
      });
      YigdCompat.register();
      CuriosSoulBoundDeathHandler.register();
      if (ModList.get().isLoaded("ftbquests")) {
         TNOFtbQuests.init();
      }

      FoodEPManager.init();
      NeoForge.EVENT_BUS.addListener(RegisterCommandsEvent.class, event -> TNOAdminCommands.register(event.getDispatcher()));
      NeoForge.EVENT_BUS.addListener(EdoTenseiPackets::onLivingDeath);
      NeoForge.EVENT_BUS.addListener(GhoulDaySleepHandler::onCanPlayerSleep);
      NeoForge.EVENT_BUS.addListener(GhoulDaySleepHandler::onCanContinueSleeping);
      NeoForge.EVENT_BUS.addListener(GhoulDaySleepHandler::onSleepFinished);
      NeoForge.EVENT_BUS.addListener(FoodEPHandler::onItemUseStart);
      NeoForge.EVENT_BUS.addListener(FoodEPHandler::onItemUseFinish);
      NeoForge.EVENT_BUS.addListener(FoodEPHandler::onTooltip);
      NeoForge.EVENT_BUS.addListener(FoodEPHandler::onItemCrafted);
      NeoForge.EVENT_BUS.addListener(FoodEPHandler::onItemSmelted);
      NeoForge.EVENT_BUS.addListener(SpiritSummonPocketPersistence::onPlayerClone);
      NeoForge.EVENT_BUS.addListener(SlimeHumanFormState::onPlayerClone);
      NeoForge.EVENT_BUS.addListener(SlimeHumanFormState::onPlayerLoggedIn);
      NeoForge.EVENT_BUS.addListener(SlimeHumanFormState::onPlayerRespawn);
      NeoForge.EVENT_BUS.addListener(SpiritSummonLimits::onPlayerLoggedIn);
      EdoTenseiPackets.registerC2S();
      SpiritSummonPackets.registerC2S();
      ContractLittleFoxPackets.registerC2S();
      ReincarnationRulesPackets.registerC2S();
      RimuruModePackets.registerC2S();
      SlimeHumanFormPackets.registerC2S();
      ContractLittleFoxEvents.registerArchitecturyEvents();
      MagicOreSenseAcquisitionHandler.registerArchitecturyEvents();
      MysticismRestrictedSkillHandler.register();
      IceAndFireDragonRemainsHandler.registerArchitecturyEvents();
      HumanFormRaceChangeHandler.register();
      SpiritSummonRaceSyncHandler.register();
      SpiritSummonNamingHandler.register();
      HarvestFestivalAchievementHandler.register();
      SlimePredationHumanFormHandler.register();
      RimuruModeReincarnationHandler.register();
      EdoTenseiPackets.registerS2C();
      SpiritSummonPackets.registerS2C();
      ContractLittleFoxPackets.registerS2C();
      MagicOreSensePackets.registerS2C();
      ReincarnationRulesPackets.registerS2C();
      RimuruModePackets.registerS2C();
      SlimeHumanFormPackets.registerS2C();
      if (FMLEnvironment.dist.isClient()) {
         MagicOreSenseXray.init();
         HeatSourceCollisionXray.init();
      }

      TensuraTNORaces.init();
      TNOConfigInjector.addToConfig();
      LOGGER.info("[TensuraTNO] 加载完成");
   }

   private void register(IEventBus modBus) {
      TensuraTNOBlocks.register(modBus);
      TensuraTNOBlockEntities.register(modBus);
      TensuraTNOBackpacks.register(modBus);
      TensuraTNOItems.register(modBus);
      TensuraTNORecipeSerializers.register(modBus);
      TensuraTNOSkills.register(modBus);
      TensuraTNOEntities.register(modBus);
      TensuraTNOLootModifiers.register(modBus);
   }
}
