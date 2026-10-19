package com.foxablazeultimate;

import com.foxablazeultimate.ability.skill.ciel.CielPassiveHandler;
import com.foxablazeultimate.ability.skill.shubniggurath.ShubNiggurathArchiveHandler;
import com.foxablazeultimate.config.AzathothConfig;
import com.foxablazeultimate.config.BeelzebubConfig;
import com.foxablazeultimate.config.CielConfig;
import com.foxablazeultimate.config.FortunaConfig;
import com.foxablazeultimate.config.FoxAblazeUltimateConfig;
import com.foxablazeultimate.config.HellSniperConfig;
import com.foxablazeultimate.config.MammonConfig;
import com.foxablazeultimate.config.RaphaelConfig;
import com.foxablazeultimate.config.SatanaelConfig;
import com.foxablazeultimate.config.ShubNiggurathConfig;
import com.foxablazeultimate.config.UrielConfig;
import com.foxablazeultimate.config.VeldoraConfig;
import com.foxablazeultimate.config.VelgryndConfig;
import com.foxablazeultimate.config.YogSothothConfig;
import com.foxablazeultimate.event.SatanaelCombatRecoveryHandler;
import com.foxablazeultimate.event.UltimateSkillProtector;
import com.foxablazeultimate.network.AzathothChargeVisualPayload;
import com.foxablazeultimate.network.OpenBeelzebubStoragePayload;
import com.foxablazeultimate.network.OpenRaphaelNamingPayload;
import com.foxablazeultimate.network.OpenShubWarehousePayload;
import com.foxablazeultimate.network.RequestBeelzebubFluidExtractPayload;
import com.foxablazeultimate.network.RequestCielTerrainActionPayload;
import com.foxablazeultimate.network.RequestPredationFilterSyncPayload;
import com.foxablazeultimate.network.RequestRaphaelRenamePayload;
import com.foxablazeultimate.network.RequestShubWarehouseActionPayload;
import com.foxablazeultimate.network.ShowWisdomCrystalEasterEggPayload;
import com.foxablazeultimate.network.SyncCrystalLockPayload;
import com.foxablazeultimate.network.SyncPredationFilterPayload;
import com.foxablazeultimate.network.UpdatePredationFilterPayload;
import com.foxablazeultimate.network.YogTimeStopStatePayload;
import com.foxablazeultimate.registry.FoxAblazeUltimateDataComponents;
import com.foxablazeultimate.registry.FoxAblazeUltimateEntityTypes;
import com.foxablazeultimate.registry.FoxAblazeUltimateItems;
import com.foxablazeultimate.registry.FoxAblazeUltimateLootModifiers;
import com.foxablazeultimate.registry.FoxAblazeUltimateMobEffects;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import com.foxablazeultimate.registry.FoxAblazeUltimateSounds;
import com.foxablazeultimate.world.FoxAblazeGameRules;
import com.mojang.logging.LogUtils;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.NetworkUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod("foxablazeultimate")
public class FoxAblazeUltimateMod {
   public static final String MOD_ID = "foxablazeultimate";
   public static final Logger LOGGER = LogUtils.getLogger();

   public FoxAblazeUltimateMod(IEventBus modBus, ModContainer modContainer) {
      FoxAblazeUltimateConfig generalCfg = new FoxAblazeUltimateConfig();
      FoxAblazeUltimateConfig.setInstance(generalCfg);
      ConfigRegistry.registerConfig(generalCfg);
      RaphaelConfig raphaelCfg = new RaphaelConfig();
      RaphaelConfig.setInstance(raphaelCfg);
      ConfigRegistry.registerConfig(raphaelCfg);
      CielConfig cielCfg = new CielConfig();
      CielConfig.setInstance(cielCfg);
      ConfigRegistry.registerConfig(cielCfg);
      BeelzebubConfig beelzebubCfg = new BeelzebubConfig();
      BeelzebubConfig.setInstance(beelzebubCfg);
      ConfigRegistry.registerConfig(beelzebubCfg);
      UrielConfig urielCfg = new UrielConfig();
      UrielConfig.setInstance(urielCfg);
      ConfigRegistry.registerConfig(urielCfg);
      VeldoraConfig veldoraCfg = new VeldoraConfig();
      VeldoraConfig.setInstance(veldoraCfg);
      ConfigRegistry.registerConfig(veldoraCfg);
      VelgryndConfig velgryndCfg = new VelgryndConfig();
      VelgryndConfig.setInstance(velgryndCfg);
      ConfigRegistry.registerConfig(velgryndCfg);
      SatanaelConfig satanaelCfg = new SatanaelConfig();
      SatanaelConfig.setInstance(satanaelCfg);
      ConfigRegistry.registerConfig(satanaelCfg);
      ShubNiggurathConfig shubNiggurathCfg = new ShubNiggurathConfig();
      ShubNiggurathConfig.setInstance(shubNiggurathCfg);
      ConfigRegistry.registerConfig(shubNiggurathCfg);
      FortunaConfig fortunaCfg = new FortunaConfig();
      FortunaConfig.setInstance(fortunaCfg);
      ConfigRegistry.registerConfig(fortunaCfg);
      MammonConfig mammonCfg = new MammonConfig();
      MammonConfig.setInstance(mammonCfg);
      ConfigRegistry.registerConfig(mammonCfg);
      HellSniperConfig hellSniperCfg = new HellSniperConfig();
      HellSniperConfig.setInstance(hellSniperCfg);
      ConfigRegistry.registerConfig(hellSniperCfg);
      YogSothothConfig yogSothothCfg = new YogSothothConfig();
      YogSothothConfig.setInstance(yogSothothCfg);
      ConfigRegistry.registerConfig(yogSothothCfg);
      AzathothConfig azathothCfg = new AzathothConfig();
      AzathothConfig.setInstance(azathothCfg);
      ConfigRegistry.registerConfig(azathothCfg);
      FoxAblazeGameRules.init();
      UltimateSkillProtector.init();
      ShubNiggurathArchiveHandler.init();
      CielPassiveHandler.init();
      NeoForge.EVENT_BUS.register(SatanaelCombatRecoveryHandler.class);
      FoxAblazeUltimateEntityTypes.register(modBus);
      FoxAblazeUltimateSkills.register(modBus);
      FoxAblazeUltimateSounds.register(modBus);
      FoxAblazeUltimateItems.register(modBus);
      FoxAblazeUltimateMobEffects.register(modBus);
      FoxAblazeUltimateDataComponents.register(modBus);
      FoxAblazeUltimateLootModifiers.register(modBus);
      NetworkUtils.registerS2CPayload(OpenBeelzebubStoragePayload.TYPE, OpenBeelzebubStoragePayload.STREAM_CODEC, OpenBeelzebubStoragePayload::handle);
      NetworkUtils.registerC2SPayload(
         RequestBeelzebubFluidExtractPayload.TYPE, RequestBeelzebubFluidExtractPayload.STREAM_CODEC, RequestBeelzebubFluidExtractPayload::handle
      );
      NetworkUtils.registerS2CPayload(SyncPredationFilterPayload.TYPE, SyncPredationFilterPayload.STREAM_CODEC, SyncPredationFilterPayload::handle);
      NetworkUtils.registerC2SPayload(
         RequestPredationFilterSyncPayload.TYPE, RequestPredationFilterSyncPayload.STREAM_CODEC, RequestPredationFilterSyncPayload::handle
      );
      NetworkUtils.registerC2SPayload(UpdatePredationFilterPayload.TYPE, UpdatePredationFilterPayload.STREAM_CODEC, UpdatePredationFilterPayload::handle);
      NetworkUtils.registerS2CPayload(OpenRaphaelNamingPayload.TYPE, OpenRaphaelNamingPayload.STREAM_CODEC, OpenRaphaelNamingPayload::handle);
      NetworkUtils.registerC2SPayload(RequestRaphaelRenamePayload.TYPE, RequestRaphaelRenamePayload.STREAM_CODEC, RequestRaphaelRenamePayload::handle);
      NetworkUtils.registerS2CPayload(SyncCrystalLockPayload.TYPE, SyncCrystalLockPayload.STREAM_CODEC, SyncCrystalLockPayload::handle);
      NetworkUtils.registerS2CPayload(
         ShowWisdomCrystalEasterEggPayload.TYPE, ShowWisdomCrystalEasterEggPayload.STREAM_CODEC, ShowWisdomCrystalEasterEggPayload::handle
      );
      NetworkUtils.registerS2CPayload(OpenShubWarehousePayload.TYPE, OpenShubWarehousePayload.STREAM_CODEC, OpenShubWarehousePayload::handle);
      NetworkUtils.registerC2SPayload(
         RequestShubWarehouseActionPayload.TYPE, RequestShubWarehouseActionPayload.STREAM_CODEC, RequestShubWarehouseActionPayload::handle
      );
      NetworkUtils.registerC2SPayload(
         RequestCielTerrainActionPayload.TYPE, RequestCielTerrainActionPayload.STREAM_CODEC, RequestCielTerrainActionPayload::handle
      );
      NetworkUtils.registerS2CPayload(YogTimeStopStatePayload.TYPE, YogTimeStopStatePayload.STREAM_CODEC, YogTimeStopStatePayload::handle);
      NetworkUtils.registerS2CPayload(AzathothChargeVisualPayload.TYPE, AzathothChargeVisualPayload.STREAM_CODEC, AzathothChargeVisualPayload::handle);
      LOGGER.info("[FoxAblazeUltimate] 加载完成");
   }
}
