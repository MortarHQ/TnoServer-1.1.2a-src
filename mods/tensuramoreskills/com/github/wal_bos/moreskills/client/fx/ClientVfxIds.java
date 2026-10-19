package com.github.wal_bos.moreskills.client.fx;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class ClientVfxIds {
   public static final List<String> ALL = List.of(
      "albis_vina",
      "asmoday_black_hole",
      "asmoday_falling_cube",
      "asmoday_prison_realm",
      "asmoday_space",
      "asmoday_spatial_absorption",
      "asmoday_spatial_cube",
      "avalon_novari_phase",
      "axis_judgment",
      "arthur_true_excalibur",
      "arthur_holy_order",
      "arthur_light_domination",
      "arthur_holy_disintegration",
      "arthur_holy_aura",
      "arthur_kings_battle",
      "arthur_promised_victory",
      "avalon_divine_excalibur",
      "avalon_kingdom",
      "avalon_law_sanctuary",
      "avalon_law_sacred_purity",
      "avalon_law_endless_reservoir",
      "avalon_law_silent_throne",
      "avalon_law_crushing_heaven",
      "avalon_law_closed_prison",
      "avalon_law_reversed_providence",
      "avalon_law_severed_existence",
      "avalon_sovereign_judgement",
      "avalon_sovereign_heavens_fall",
      "avalon_sovereign_sanctuary",
      "avalon_divine_disintegration",
      "avalon_eternal_victory",
      "avalon_holy_aura",
      "avalon_round_table",
      "avalon_dimension",
      "beretta_abyssal_gear_sweep",
      "beretta_chaos_core_exposure",
      "beretta_chaos_rings",
      "beretta_darkness_cannon",
      "beretta_inverted_fusion",
      "beretta_low_void_laser",
      "beretta_machina_grid",
      "beretta_mineral_eruption",
      "beretta_orb_cascade",
      "beretta_reversed_cross",
      "black_blood_domain_fog",
      "black_communion",
      "chrono_judgement_clock",
      "elfaria_albis_ice_sheet",
      "elfaria_ares_weiss_bloom",
      "elfaria_fruzel_cardeneia",
      "elfaria_glacial_last_albis",
      "elfaria_icemaiden",
      "elfaria_myrdas_fridolet",
      "elfaria_oplce_shine",
      "elfaria_passive_ice_steps",
      "elfaria_stellas_natea",
      "elfaria_vfx_helper",
      "elfaria_vfx_vertex_helper",
      "exploiter_xray_hook",
      "feldway_avatar_beam",
      "feldway_awakening_block_storm",
      "feldway_awakening_camera_shake",
      "feldway_awakening_coronation_beam",
      "feldway_awakening_shader",
      "feldway_reward_client_fx",
      "full_cowl_lightning",
      "gearshift_blue_trail",
      "imperial_halo_shot",
      "istaroth_camera_shake",
      "istaroth_doom_tornado",
      "istaroth_gray_shader",
      "istaroth_screen_fx",
      "i_want_limbo",
      "michael_aura",
      "michael_halberd_shot",
      "michael_jerez",
      "michael_keter",
      "michael_lataib_barrage",
      "michael_segva",
      "michael_shifuru_meteor",
      "michael_vfx",
      "mitama_atomic_edict",
      "mitama_divine_beams",
      "novari_black_white",
      "pain_planetary_devastation",
      "pain_six_paths",
      "phainon_acquirement",
      "ronova_finality",
      "satella",
      "seraphic_guillotine",
      "witch_of_envy_satella"
   );
   private static final Set<String> ALL_SET = Set.copyOf(ALL);
   private static final Map<String, String> CLASS_TO_ID = createClassMap();

   private ClientVfxIds() {
   }

   public static String normalize(String value) {
      if (value == null) {
         return null;
      }

      String id = value.trim();
      if (id.isEmpty()) {
         return null;
      }

      String mapped = CLASS_TO_ID.get(simpleName(id));
      if (mapped != null) {
         return mapped;
      }

      String normalized = id.toLowerCase(Locale.ROOT).replace('-', '_').replace('.', '_');
      return ALL_SET.contains(normalized) ? normalized : null;
   }

   public static String idForClassName(String className) {
      return className == null ? null : CLASS_TO_ID.get(simpleName(className));
   }

   public static boolean exists(String id) {
      return id != null && ALL_SET.contains(id);
   }

   private static String simpleName(String className) {
      int dollar = className.indexOf(36);
      if (dollar >= 0) {
         className = className.substring(0, dollar);
      }

      int slash = className.lastIndexOf(47);
      if (slash >= 0) {
         className = className.substring(slash + 1);
      }

      int dot = className.lastIndexOf(46);
      if (dot >= 0) {
         className = className.substring(dot + 1);
      }

      return className;
   }

   private static Map<String, String> createClassMap() {
      Map<String, String> map = new LinkedHashMap<>();
      put(map, "AlbisVinaVfxClient", "albis_vina");
      put(map, "AsmodayBlackHoleClient", "asmoday_black_hole");
      put(map, "AsmodayFallingCubeClient", "asmoday_falling_cube");
      put(map, "AsmodayPrisonRealmClient", "asmoday_prison_realm");
      put(map, "AsmodaySpaceClient", "asmoday_space");
      put(map, "AsmodaySpatialAbsorptionClient", "asmoday_spatial_absorption");
      put(map, "AsmodaySpatialCubeClient", "asmoday_spatial_cube");
      put(map, "AvalonNovariPhaseVfxClient", "avalon_novari_phase");
      put(map, "AxisJudgmentClient", "axis_judgment");
      put(map, "BerettaAbyssalGearSweepVfxClient", "beretta_abyssal_gear_sweep");
      put(map, "BerettaChaosCoreExposureVfxClient", "beretta_chaos_core_exposure");
      put(map, "BerettaChaosRingsVfxClient", "beretta_chaos_rings");
      put(map, "BerettaDarknessCannonVfxClient", "beretta_darkness_cannon");
      put(map, "BerettaInvertedFusionVfxClient", "beretta_inverted_fusion");
      put(map, "BerettaLowVoidLaserVfxClient", "beretta_low_void_laser");
      put(map, "BerettaMachinaGridVfxClient", "beretta_machina_grid");
      put(map, "BerettaMineralEruptionVfxClient", "beretta_mineral_eruption");
      put(map, "BerettaOrbCascadeVfxClient", "beretta_orb_cascade");
      put(map, "BerettaReversedCrossVfxClient", "beretta_reversed_cross");
      put(map, "BlackBloodDomainFogHandler", "black_blood_domain_fog");
      put(map, "BlackCommunionVfxClient", "black_communion");
      put(map, "ChronoJudgementClockClient", "chrono_judgement_clock");
      put(map, "ElfariaAlbisIceSheetRenderer", "elfaria_albis_ice_sheet");
      put(map, "ElfariaAresWeissBloomVfxClient", "elfaria_ares_weiss_bloom");
      put(map, "ElfariaFruzelCardeneiaVfxClient", "elfaria_fruzel_cardeneia");
      put(map, "ElfariaGlacialLastAlbisVfxClient", "elfaria_glacial_last_albis");
      put(map, "ElfariaIcemaidenVfxClient", "elfaria_icemaiden");
      put(map, "ElfariaMyrdasFridoletVfxClient", "elfaria_myrdas_fridolet");
      put(map, "ElfariaOplceShineVfxClient", "elfaria_oplce_shine");
      put(map, "ElfariaPassiveIceStepsVfxClient", "elfaria_passive_ice_steps");
      put(map, "ElfariaStellasNateaVfxClient", "elfaria_stellas_natea");
      put(map, "ElfariaVfxHelper", "elfaria_vfx_helper");
      put(map, "ElfariaVfxVertexHelper", "elfaria_vfx_vertex_helper");
      put(map, "ExploiterClientXrayHook", "exploiter_xray_hook");
      put(map, "FeldwayAvatarBeamClient", "feldway_avatar_beam");
      put(map, "FeldwayAwakeningBlockStorm", "feldway_awakening_block_storm");
      put(map, "FeldwayAwakeningCameraShake", "feldway_awakening_camera_shake");
      put(map, "FeldwayAwakeningCoronationBeam", "feldway_awakening_coronation_beam");
      put(map, "FeldwayAwakeningShader", "feldway_awakening_shader");
      put(map, "FeldwayRewardClientFx", "feldway_reward_client_fx");
      put(map, "FullCowlLightningClient", "full_cowl_lightning");
      put(map, "GearshiftBlueTrailClient", "gearshift_blue_trail");
      put(map, "ImperialHaloShotClient", "imperial_halo_shot");
      put(map, "IstarothClientCameraShake", "istaroth_camera_shake");
      put(map, "IstarothClientDoomTornado", "istaroth_doom_tornado");
      put(map, "IstarothGrayShader", "istaroth_gray_shader");
      put(map, "IstarothScreenFx", "istaroth_screen_fx");
      put(map, "IWantLimboClient", "i_want_limbo");
      put(map, "MichaelAuraVfxClient", "michael_aura");
      put(map, "MichaelHalberdShotVfxClient", "michael_halberd_shot");
      put(map, "MichaelJerezVfxClient", "michael_jerez");
      put(map, "MichaelKeterVfxClient", "michael_keter");
      put(map, "MichaelLataibBarrageVfxClient", "michael_lataib_barrage");
      put(map, "MichaelSegvaVfxClient", "michael_segva");
      put(map, "MichaelShifuruMeteorVfxClient", "michael_shifuru_meteor");
      put(map, "MichaelVfxClients", "michael_vfx");
      put(map, "MitamaAtomicEdictVfxClient", "mitama_atomic_edict");
      put(map, "MitamaDivineBeamsClient", "mitama_divine_beams");
      put(map, "NovariBlackWhiteVfxClient", "novari_black_white");
      put(map, "PainPlanetaryDevastationClient", "pain_planetary_devastation");
      put(map, "PainSixPathsVfxClient", "pain_six_paths");
      put(map, "PhainonAcquirementVfxClient", "phainon_acquirement");
      put(map, "RonovaFinalityVfxClient", "ronova_finality");
      put(map, "SatellaVfxClient", "satella");
      put(map, "SeraphicGuillotineClient", "seraphic_guillotine");
      put(map, "WitchOfEnvySatellaVfxClient", "witch_of_envy_satella");
      put(map, "ArthurTrueExcaliburVfxClient", "arthur_true_excalibur");
      put(map, "ArthurHolyOrderVfxClient", "arthur_holy_order");
      put(map, "ArthurLightDominationVfxClient", "arthur_light_domination");
      put(map, "ArthurHolyDisintegrationVfxClient", "arthur_holy_disintegration");
      put(map, "ArthurHolyAuraVfxClient", "arthur_holy_aura");
      put(map, "ArthurKingsBattleVfxClient", "arthur_kings_battle");
      put(map, "ArthurPromisedVictoryVfxClient", "arthur_promised_victory");
      put(map, "AvalonDivineExcaliburVfxClient", "avalon_divine_excalibur");
      put(map, "AvalonKingdomVfxClient", "avalon_kingdom");
      put(map, "AvalonLawSanctuaryVfxClient", "avalon_law_sanctuary");
      put(map, "AvalonLawSacredPurityVfxClient", "avalon_law_sacred_purity");
      put(map, "AvalonLawEndlessReservoirVfxClient", "avalon_law_endless_reservoir");
      put(map, "AvalonLawSilentThroneVfxClient", "avalon_law_silent_throne");
      put(map, "AvalonLawCrushingHeavenVfxClient", "avalon_law_crushing_heaven");
      put(map, "AvalonLawClosedPrisonVfxClient", "avalon_law_closed_prison");
      put(map, "AvalonLawReversedProvidenceVfxClient", "avalon_law_reversed_providence");
      put(map, "AvalonLawSeveredExistenceVfxClient", "avalon_law_severed_existence");
      put(map, "AvalonSovereignJudgementVfxClient", "avalon_sovereign_judgement");
      put(map, "AvalonSovereignHeavensFallVfxClient", "avalon_sovereign_heavens_fall");
      put(map, "AvalonSovereignSanctuaryVfxClient", "avalon_sovereign_sanctuary");
      put(map, "AvalonDivineDisintegrationVfxClient", "avalon_divine_disintegration");
      put(map, "AvalonEternalVictoryVfxClient", "avalon_eternal_victory");
      put(map, "AvalonHolyAuraVfxClient", "avalon_holy_aura");
      put(map, "AvalonRoundTableVfxClient", "avalon_round_table");
      put(map, "AvalonDimensionVfxClient", "avalon_dimension");
      return Map.copyOf(map);
   }

   private static void put(Map<String, String> map, String className, String id) {
      map.put(className, id);
   }
}
