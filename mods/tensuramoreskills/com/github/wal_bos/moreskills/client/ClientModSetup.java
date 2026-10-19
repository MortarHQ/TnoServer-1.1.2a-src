package com.github.wal_bos.moreskills.client;

import com.github.wal_bos.moreskills.client.fx.AlbisVinaVfxClient;
import com.github.wal_bos.moreskills.client.fx.AsmodayBlackHoleClient;
import com.github.wal_bos.moreskills.client.fx.AxisJudgmentClient;
import com.github.wal_bos.moreskills.client.fx.BerettaAbyssalGearSweepVfxClient;
import com.github.wal_bos.moreskills.client.fx.BerettaCameraShakeClient;
import com.github.wal_bos.moreskills.client.fx.BerettaChaosCoreExposureVfxClient;
import com.github.wal_bos.moreskills.client.fx.BerettaChaosRingsVfxClient;
import com.github.wal_bos.moreskills.client.fx.BerettaDarknessCannonVfxClient;
import com.github.wal_bos.moreskills.client.fx.BerettaInvertedFusionVfxClient;
import com.github.wal_bos.moreskills.client.fx.BerettaLowVoidLaserVfxClient;
import com.github.wal_bos.moreskills.client.fx.BerettaMachinaGridVfxClient;
import com.github.wal_bos.moreskills.client.fx.BerettaMineralEruptionVfxClient;
import com.github.wal_bos.moreskills.client.fx.BerettaOrbCascadeVfxClient;
import com.github.wal_bos.moreskills.client.fx.BerettaReversedCrossVfxClient;
import com.github.wal_bos.moreskills.client.fx.BlackBloodDomainFogHandler;
import com.github.wal_bos.moreskills.client.fx.BlackCommunionVfxClient;
import com.github.wal_bos.moreskills.client.fx.ChronoJudgementClockClient;
import com.github.wal_bos.moreskills.client.fx.ClientVfxBootstrap;
import com.github.wal_bos.moreskills.client.fx.ClientVfxGate;
import com.github.wal_bos.moreskills.client.fx.ElfariaIcemaidenVfxClient;
import com.github.wal_bos.moreskills.client.fx.FeldwayAvatarBeamClient;
import com.github.wal_bos.moreskills.client.fx.FeldwayAwakeningBlockStorm;
import com.github.wal_bos.moreskills.client.fx.FeldwayAwakeningCameraShake;
import com.github.wal_bos.moreskills.client.fx.FeldwayAwakeningCoronationBeam;
import com.github.wal_bos.moreskills.client.fx.FeldwayAwakeningShader;
import com.github.wal_bos.moreskills.client.fx.FullCowlLightningClient;
import com.github.wal_bos.moreskills.client.fx.GearshiftBlueTrailClient;
import com.github.wal_bos.moreskills.client.fx.IWantLimboClient;
import com.github.wal_bos.moreskills.client.fx.ImperialHaloShotClient;
import com.github.wal_bos.moreskills.client.fx.IstarothClientCameraShake;
import com.github.wal_bos.moreskills.client.fx.IstarothGrayShader;
import com.github.wal_bos.moreskills.client.fx.IstarothScreenFx;
import com.github.wal_bos.moreskills.client.fx.MichaelVfxClients;
import com.github.wal_bos.moreskills.client.fx.MitamaAtomicEdictVfxClient;
import com.github.wal_bos.moreskills.client.fx.PainPlanetaryDevastationClient;
import com.github.wal_bos.moreskills.client.fx.PainSixPathsVfxClient;
import com.github.wal_bos.moreskills.client.fx.PathsAmbientParticles;
import com.github.wal_bos.moreskills.client.fx.PathsAuroraRenderer;
import com.github.wal_bos.moreskills.client.fx.PathsClientEffectsRegister;
import com.github.wal_bos.moreskills.client.fx.PathsClientEvents;
import com.github.wal_bos.moreskills.client.fx.PathsClientFX;
import com.github.wal_bos.moreskills.client.fx.PathsCloudKiller;
import com.github.wal_bos.moreskills.client.fx.PathsFogHandler;
import com.github.wal_bos.moreskills.client.fx.PathsPortalStrandRenderer;
import com.github.wal_bos.moreskills.client.fx.RonovaFinalityVfxClient;
import com.github.wal_bos.moreskills.client.fx.SatellaVfxClient;
import com.github.wal_bos.moreskills.client.fx.SeraphicGuillotineClient;
import com.github.wal_bos.moreskills.client.fx.WitchOfEnvySatellaVfxClient;
import com.github.wal_bos.moreskills.client.fx.arthur.ArthurAfkGoldenOrbsVfxClient;
import com.github.wal_bos.moreskills.client.fx.arthur.ArthurHolyAuraVfxClient;
import com.github.wal_bos.moreskills.client.fx.arthur.ArthurHolyDisintegrationVfxClient;
import com.github.wal_bos.moreskills.client.fx.arthur.ArthurHolyOrderVfxClient;
import com.github.wal_bos.moreskills.client.fx.arthur.ArthurKingsBattleVfxClient;
import com.github.wal_bos.moreskills.client.fx.arthur.ArthurLightDominationVfxClient;
import com.github.wal_bos.moreskills.client.fx.arthur.ArthurPromisedVictoryVfxClient;
import com.github.wal_bos.moreskills.client.fx.arthur.ArthurTrueExcaliburVfxClient;
import com.github.wal_bos.moreskills.client.fx.avalon.AvalonDimensionVfxClient;
import com.github.wal_bos.moreskills.client.fx.avalon.AvalonDivineDisintegrationVfxClient;
import com.github.wal_bos.moreskills.client.fx.avalon.AvalonDivineExcaliburVfxClient;
import com.github.wal_bos.moreskills.client.fx.avalon.AvalonEternalVictoryVfxClient;
import com.github.wal_bos.moreskills.client.fx.avalon.AvalonHolyAuraVfxClient;
import com.github.wal_bos.moreskills.client.fx.avalon.AvalonKingdomVfxClient;
import com.github.wal_bos.moreskills.client.fx.avalon.AvalonLawClosedPrisonVfxClient;
import com.github.wal_bos.moreskills.client.fx.avalon.AvalonLawCrushingHeavenVfxClient;
import com.github.wal_bos.moreskills.client.fx.avalon.AvalonLawEndlessReservoirVfxClient;
import com.github.wal_bos.moreskills.client.fx.avalon.AvalonLawReversedProvidenceVfxClient;
import com.github.wal_bos.moreskills.client.fx.avalon.AvalonLawSacredPurityVfxClient;
import com.github.wal_bos.moreskills.client.fx.avalon.AvalonLawSanctuaryVfxClient;
import com.github.wal_bos.moreskills.client.fx.avalon.AvalonLawSeveredExistenceVfxClient;
import com.github.wal_bos.moreskills.client.fx.avalon.AvalonLawSilentThroneVfxClient;
import com.github.wal_bos.moreskills.client.fx.avalon.AvalonRoundTableVfxClient;
import com.github.wal_bos.moreskills.client.fx.avalon.AvalonSovereignHeavensFallVfxClient;
import com.github.wal_bos.moreskills.client.fx.avalon.AvalonSovereignJudgementVfxClient;
import com.github.wal_bos.moreskills.client.fx.avalon.AvalonSovereignSanctuaryVfxClient;
import com.github.wal_bos.moreskills.client.particle.ModParticleFactories;
import com.github.wal_bos.moreskills.client.renderer.ArthurAfkAnimationRenderer;
import com.github.wal_bos.moreskills.client.renderer.MichaelAfkAnimationRenderer;
import com.github.wal_bos.moreskills.client.renderer.ModEntityRenderers;
import com.github.wal_bos.moreskills.client.renderer.MorphRenderHandler;
import com.github.wal_bos.moreskills.client.screen.CaedrosAspectScreen;
import com.github.wal_bos.moreskills.client.screen.CaedrosReconstructScreen;
import com.github.wal_bos.moreskills.client.screen.FungusInfectionScreen;
import com.github.wal_bos.moreskills.client.screen.GeneEditorScreen;
import com.github.wal_bos.moreskills.client.screen.GodlyWorkScreen;
import com.github.wal_bos.moreskills.client.screen.GreedCasinoScreen;
import com.github.wal_bos.moreskills.client.screen.IWantLimboScreen;
import com.github.wal_bos.moreskills.client.screen.MahoragaAdaptationScreen;
import com.github.wal_bos.moreskills.client.screen.MitamaOfferingScreen;
import com.github.wal_bos.moreskills.client.screen.NaberiusScreen;
import com.github.wal_bos.moreskills.client.screen.NirvanaArchiveScreen;
import com.github.wal_bos.moreskills.client.screen.VoidArchiveForgeScreen;
import com.github.wal_bos.moreskills.client.screen.VoidArchiveLibraryScreen;
import com.github.wal_bos.moreskills.client.screen.VoidArchiveLoadoutScreen;
import com.github.wal_bos.moreskills.client.vfx.PhainonAcquirementVfxClient;
import com.github.wal_bos.moreskills.client.vfx.PhainonVfxRenderTypes;
import com.github.wal_bos.moreskills.registry.menu.ModMenus;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.common.NeoForge;

public final class ClientModSetup {
   private ClientModSetup() {
   }

   public static void init(IEventBus modBus) {
      ClientVfxBootstrap.init();
      modBus.addListener(ClientModSetup::registerScreens);
      modBus.addListener(ClientModSetup::registerParticleProviders);
      modBus.addListener(ModEntityRenderers::registerRenderers);
      modBus.addListener(ModEntityRenderers::registerLayerDefinitions);
      modBus.addListener(MichaelAfkAnimationRenderer::onAddLayers);
      ClientVfxBootstrap.start("i_want_limbo", IWantLimboClient::init);
      modBus.addListener(PathsClientFX::onRegisterShaders);
      modBus.addListener(PathsClientEffectsRegister::registerEffects);
      modBus.addListener(AsmodayBlackHoleClient::registerShaders);
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("asmoday_black_hole")) {
            AsmodayBlackHoleClient.onClientTick(event);
         }
      });
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("asmoday_black_hole")) {
            AsmodayBlackHoleClient.render(event);
         }
      });
      ClientVfxBootstrap.start("beretta_low_void_laser", BerettaLowVoidLaserVfxClient::init);
      ClientVfxBootstrap.start("beretta_orb_cascade", BerettaOrbCascadeVfxClient::init);
      ClientVfxBootstrap.start("beretta_darkness_cannon", BerettaDarknessCannonVfxClient::init);
      ClientVfxBootstrap.start("beretta_mineral_eruption", BerettaMineralEruptionVfxClient::init);
      ClientVfxBootstrap.start("beretta_reversed_cross", BerettaReversedCrossVfxClient::init);
      ClientVfxBootstrap.start("beretta_chaos_rings", BerettaChaosRingsVfxClient::init);
      ClientVfxBootstrap.start("beretta_machina_grid", BerettaMachinaGridVfxClient::init);
      ClientVfxBootstrap.start("beretta_inverted_fusion", BerettaInvertedFusionVfxClient::init);
      ClientVfxBootstrap.start("beretta_abyssal_gear_sweep", BerettaAbyssalGearSweepVfxClient::init);
      ClientVfxBootstrap.start("beretta_chaos_core_exposure", BerettaChaosCoreExposureVfxClient::init);
      BerettaCameraShakeClient.init();
      ClientVfxBootstrap.start("feldway_awakening_block_storm", FeldwayAwakeningBlockStorm::init);
      ClientVfxBootstrap.start("witch_of_envy_satella", WitchOfEnvySatellaVfxClient::init);
      ClientVfxBootstrap.start("satella", SatellaVfxClient::init);
      ClientVfxBootstrap.start("elfaria_icemaiden", ElfariaIcemaidenVfxClient::init);
      ClientVfxBootstrap.start("ronova_finality", RonovaFinalityVfxClient::init);
      ClientVfxBootstrap.start("pain_six_paths", PainSixPathsVfxClient::init);
      ClientVfxBootstrap.start("pain_planetary_devastation", PainPlanetaryDevastationClient::init);
      ClientVfxBootstrap.start("exploiter_xray_hook", ExploiterClientXrayHook::init);
      ClientVfxBootstrap.start("arthur_true_excalibur", ArthurTrueExcaliburVfxClient::init);
      ClientVfxBootstrap.start("arthur_holy_order", ArthurHolyOrderVfxClient::init);
      ClientVfxBootstrap.start("arthur_light_domination", ArthurLightDominationVfxClient::init);
      ClientVfxBootstrap.start("arthur_holy_disintegration", ArthurHolyDisintegrationVfxClient::init);
      ClientVfxBootstrap.start("arthur_holy_aura", ArthurHolyAuraVfxClient::init);
      ClientVfxBootstrap.start("arthur_kings_battle", ArthurKingsBattleVfxClient::init);
      ClientVfxBootstrap.start("arthur_promised_victory", ArthurPromisedVictoryVfxClient::init);
      ClientVfxBootstrap.start("avalon_dimension", AvalonDimensionVfxClient::init);
      ClientVfxBootstrap.start("avalon_divine_excalibur", AvalonDivineExcaliburVfxClient::init);
      ClientVfxBootstrap.start("avalon_kingdom", AvalonKingdomVfxClient::init);
      ClientVfxBootstrap.start("avalon_law_sanctuary", AvalonLawSanctuaryVfxClient::init);
      ClientVfxBootstrap.start("avalon_law_sacred_purity", AvalonLawSacredPurityVfxClient::init);
      ClientVfxBootstrap.start("avalon_law_endless_reservoir", AvalonLawEndlessReservoirVfxClient::init);
      ClientVfxBootstrap.start("avalon_law_silent_throne", AvalonLawSilentThroneVfxClient::init);
      ClientVfxBootstrap.start("avalon_law_crushing_heaven", AvalonLawCrushingHeavenVfxClient::init);
      ClientVfxBootstrap.start("avalon_law_closed_prison", AvalonLawClosedPrisonVfxClient::init);
      ClientVfxBootstrap.start("avalon_law_reversed_providence", AvalonLawReversedProvidenceVfxClient::init);
      ClientVfxBootstrap.start("avalon_law_severed_existence", AvalonLawSeveredExistenceVfxClient::init);
      ClientVfxBootstrap.start("avalon_sovereign_judgement", AvalonSovereignJudgementVfxClient::init);
      ClientVfxBootstrap.start("avalon_sovereign_heavens_fall", AvalonSovereignHeavensFallVfxClient::init);
      ClientVfxBootstrap.start("avalon_sovereign_sanctuary", AvalonSovereignSanctuaryVfxClient::init);
      ClientVfxBootstrap.start("avalon_divine_disintegration", AvalonDivineDisintegrationVfxClient::init);
      ClientVfxBootstrap.start("avalon_eternal_victory", AvalonEternalVictoryVfxClient::init);
      ClientVfxBootstrap.start("avalon_holy_aura", AvalonHolyAuraVfxClient::init);
      ClientVfxBootstrap.start("avalon_round_table", AvalonRoundTableVfxClient::init);
      NeoForge.EVENT_BUS.addListener(PathsPortalStrandRenderer::render);
      modBus.addListener(PhainonVfxRenderTypes::registerShaders);
      ClientVfxBootstrap.start("phainon_acquirement", PhainonAcquirementVfxClient::init);
      ClientVfxBootstrap.start("mitama_atomic_edict", MitamaAtomicEdictVfxClient::init);
      ClientVfxBootstrap.start("feldway_awakening_coronation_beam", FeldwayAwakeningCoronationBeam::init);
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("feldway_avatar_beam")) {
            FeldwayAvatarBeamClient.onClientTick(event);
         }
      });
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("feldway_avatar_beam")) {
            FeldwayAvatarBeamClient.render(event);
         }
      });
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("axis_judgment")) {
            AxisJudgmentClient.onClientTick(event);
         }
      });
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("axis_judgment")) {
            AxisJudgmentClient.render(event);
         }
      });
      ClientVfxBootstrap.start("imperial_halo_shot", ImperialHaloShotClient::init);
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("feldway_awakening_shader")) {
            FeldwayAwakeningShader.onClientTick(event);
         }
      });
      NeoForge.EVENT_BUS.addListener(PathsClientEvents::onClientTick);
      NeoForge.EVENT_BUS.addListener(PathsClientEvents::renderFlash);
      NeoForge.EVENT_BUS.addListener(PathsAmbientParticles::onClientTick);
      NeoForge.EVENT_BUS.addListener(PathsCloudKiller::onClientTick);
      NeoForge.EVENT_BUS.addListener(PathsAuroraRenderer::renderAurora);
      NeoForge.EVENT_BUS.addListener(PathsFogHandler::fogColor);
      NeoForge.EVENT_BUS.addListener(PathsFogHandler::fogDensity);
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("istaroth_screen_fx")) {
            IstarothScreenFx.renderOverlay(event);
         }
      });
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("istaroth_gray_shader")) {
            IstarothGrayShader.onClientTick(event);
         }
      });
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("istaroth_camera_shake")) {
            IstarothClientCameraShake.onClientTick(event);
         }
      });
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("istaroth_camera_shake")) {
            IstarothClientCameraShake.onCameraAngles(event);
         }
      });
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("gearshift_blue_trail")) {
            GearshiftBlueTrailClient.onClientTick(event);
         }
      });
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("gearshift_blue_trail")) {
            GearshiftBlueTrailClient.render(event);
         }
      });
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("full_cowl_lightning")) {
            FullCowlLightningClient.onClientTick(event);
         }
      });
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("full_cowl_lightning")) {
            FullCowlLightningClient.render(event);
         }
      });
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("feldway_awakening_camera_shake")) {
            FeldwayAwakeningCameraShake.onClientTick(event);
         }
      });
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("feldway_awakening_camera_shake")) {
            FeldwayAwakeningCameraShake.onCameraAngles(event);
         }
      });
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("seraphic_guillotine")) {
            SeraphicGuillotineClient.onClientTick(event);
         }
      });
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("seraphic_guillotine")) {
            SeraphicGuillotineClient.render(event);
         }
      });
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("chrono_judgement_clock")) {
            ChronoJudgementClockClient.onClientTick(event);
         }
      });
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("chrono_judgement_clock")) {
            ChronoJudgementClockClient.render(event);
         }
      });
      NeoForge.EVENT_BUS.addListener(MichaelAfkAnimationRenderer::onClientTick);
      NeoForge.EVENT_BUS.addListener(MichaelAfkAnimationRenderer::onRenderPlayer);
      NeoForge.EVENT_BUS.addListener(MorphRenderHandler::onRenderPlayerPre);
      ClientVfxBootstrap.start("albis_vina", AlbisVinaVfxClient::init);
      ClientVfxBootstrap.start("istaroth_doom_tornado", IstarothClientDoomTornado::init);
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("black_blood_domain_fog")) {
            BlackBloodDomainFogHandler.fogColor(event);
         }
      });
      NeoForge.EVENT_BUS.addListener(event -> {
         if (ClientVfxGate.allowed("black_blood_domain_fog")) {
            BlackBloodDomainFogHandler.fogDensity(event);
         }
      });
      ClientVfxBootstrap.start("arthur_afk", ArthurAfkGoldenOrbsVfxClient::init);
      ClientVfxBootstrap.start("arthur_true_excalibur", ArthurTrueExcaliburVfxClient::init);
      modBus.addListener(ArthurAfkAnimationRenderer::onAddLayers);
      ClientVfxBootstrap.start("black_communion", BlackCommunionVfxClient::init);
      ClientVfxBootstrap.start("michael_vfx", MichaelVfxClients::init);
      NeoForge.EVENT_BUS.addListener(ArthurAfkAnimationRenderer::onClientTick);
      NeoForge.EVENT_BUS.addListener(ArthurAfkAnimationRenderer::onRenderPlayer);
   }

   private static void registerScreens(RegisterMenuScreensEvent event) {
      event.register((MenuType)ModMenus.FUNGUS_INFECTION_MENU.get(), FungusInfectionScreen::new);
      event.register((MenuType)ModMenus.CAEDROS_ASPECT_MENU.get(), CaedrosAspectScreen::new);
      event.register((MenuType)ModMenus.CAEDROS_RECONSTRUCT_MENU.get(), CaedrosReconstructScreen::new);
      event.register((MenuType)ModMenus.VOID_ARCHIVE_LIBRARY_MENU.get(), VoidArchiveLibraryScreen::new);
      event.register((MenuType)ModMenus.VOID_ARCHIVE_FORGE_MENU.get(), VoidArchiveForgeScreen::new);
      event.register((MenuType)ModMenus.VOID_ARCHIVE_LOADOUT_MENU.get(), VoidArchiveLoadoutScreen::new);
      event.register((MenuType)ModMenus.MAHORAGA_ADAPTATION_MENU.get(), MahoragaAdaptationScreen::new);
      event.register((MenuType)ModMenus.GREED_CASINO.get(), GreedCasinoScreen::new);
      event.register((MenuType)ModMenus.NABERIUS_MENU.get(), NaberiusScreen::new);
      event.register((MenuType)ModMenus.MITAMA_OFFERING_MENU.get(), MitamaOfferingScreen::new);
      event.register((MenuType)ModMenus.GENE_EDITOR_MENU.get(), GeneEditorScreen::new);
      event.register((MenuType)ModMenus.NIRVANA_ARCHIVE_MENU.get(), NirvanaArchiveScreen::new);
      event.register((MenuType)ModMenus.I_WANT_LIMBO_MENU.get(), IWantLimboScreen::new);
      event.register((MenuType)ModMenus.GODLY_WORK_MENU.get(), GodlyWorkScreen::new);
   }

   private static void registerParticleProviders(RegisterParticleProvidersEvent event) {
      ModParticleFactories.register(event);
   }
}
