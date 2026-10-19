package com.github.wal_bos.moreskills.net;

import com.github.wal_bos.moreskills.client.fx.ClientVfxPacketGate;
import com.github.wal_bos.moreskills.net.msg.AlbisVinaVfxPayload;
import com.github.wal_bos.moreskills.net.msg.ArthurAfkRequestPayload;
import com.github.wal_bos.moreskills.net.msg.ArthurAfkVfxPayload;
import com.github.wal_bos.moreskills.net.msg.ArthurHolyAuraVfxPayload;
import com.github.wal_bos.moreskills.net.msg.ArthurHolyDisintegrationVfxPayload;
import com.github.wal_bos.moreskills.net.msg.ArthurHolyOrderVfxPayload;
import com.github.wal_bos.moreskills.net.msg.ArthurKingsBattleVfxPayload;
import com.github.wal_bos.moreskills.net.msg.ArthurLightDominationVfxPayload;
import com.github.wal_bos.moreskills.net.msg.ArthurPromisedVictoryVfxPayload;
import com.github.wal_bos.moreskills.net.msg.ArthurTrueExcaliburVfxPayload;
import com.github.wal_bos.moreskills.net.msg.AsmodayBlackHolePayload;
import com.github.wal_bos.moreskills.net.msg.AsmodayFallingCubePayload;
import com.github.wal_bos.moreskills.net.msg.AsmodayPrisonRealmPayload;
import com.github.wal_bos.moreskills.net.msg.AsmodaySpatialAbsorptionPayload;
import com.github.wal_bos.moreskills.net.msg.AsmodaySpatialCubePayload;
import com.github.wal_bos.moreskills.net.msg.AvalonDivineDisintegrationVfxPayload;
import com.github.wal_bos.moreskills.net.msg.AvalonDivineExcaliburVfxPayload;
import com.github.wal_bos.moreskills.net.msg.AvalonEternalVictoryVfxPayload;
import com.github.wal_bos.moreskills.net.msg.AvalonHolyAuraVfxPayload;
import com.github.wal_bos.moreskills.net.msg.AvalonKingdomVfxPayload;
import com.github.wal_bos.moreskills.net.msg.AvalonLawClosedPrisonVfxPayload;
import com.github.wal_bos.moreskills.net.msg.AvalonLawCrushingHeavenVfxPayload;
import com.github.wal_bos.moreskills.net.msg.AvalonLawEndlessReservoirVfxPayload;
import com.github.wal_bos.moreskills.net.msg.AvalonLawReversedProvidenceVfxPayload;
import com.github.wal_bos.moreskills.net.msg.AvalonLawSacredPurityVfxPayload;
import com.github.wal_bos.moreskills.net.msg.AvalonLawSanctuaryVfxPayload;
import com.github.wal_bos.moreskills.net.msg.AvalonLawSeveredExistenceVfxPayload;
import com.github.wal_bos.moreskills.net.msg.AvalonLawSilentThroneVfxPayload;
import com.github.wal_bos.moreskills.net.msg.AvalonNovariPhaseVfxPayload;
import com.github.wal_bos.moreskills.net.msg.AvalonRoundTableVfxPayload;
import com.github.wal_bos.moreskills.net.msg.AvalonSovereignHeavensFallVfxPayload;
import com.github.wal_bos.moreskills.net.msg.AvalonSovereignJudgementVfxPayload;
import com.github.wal_bos.moreskills.net.msg.AvalonSovereignSanctuaryVfxPayload;
import com.github.wal_bos.moreskills.net.msg.AxisJudgmentPayload;
import com.github.wal_bos.moreskills.net.msg.BerettaAbyssalGearSweepPayload;
import com.github.wal_bos.moreskills.net.msg.BerettaChaosCoreExposurePayload;
import com.github.wal_bos.moreskills.net.msg.BerettaChaosRingsPayload;
import com.github.wal_bos.moreskills.net.msg.BerettaDarknessCannonPayload;
import com.github.wal_bos.moreskills.net.msg.BerettaInvertedFusionPayload;
import com.github.wal_bos.moreskills.net.msg.BerettaLowVoidLaserPayload;
import com.github.wal_bos.moreskills.net.msg.BerettaMachinaGridPayload;
import com.github.wal_bos.moreskills.net.msg.BerettaMineralEruptionPayload;
import com.github.wal_bos.moreskills.net.msg.BerettaOrbCascadePayload;
import com.github.wal_bos.moreskills.net.msg.BerettaReversedCrossPayload;
import com.github.wal_bos.moreskills.net.msg.BlackCommunionVfxPayload;
import com.github.wal_bos.moreskills.net.msg.BloodfiendDataSyncPayload;
import com.github.wal_bos.moreskills.net.msg.ChronoJudgementClockPayload;
import com.github.wal_bos.moreskills.net.msg.ClaimFeldwayRewardPayload;
import com.github.wal_bos.moreskills.net.msg.ElfariaIcemaidenVfxPayload;
import com.github.wal_bos.moreskills.net.msg.FeldwayAvatarBeamPayload;
import com.github.wal_bos.moreskills.net.msg.FullCowlLightningPayload;
import com.github.wal_bos.moreskills.net.msg.GearshiftTrailPayload;
import com.github.wal_bos.moreskills.net.msg.GreedCasinoGamblePayload;
import com.github.wal_bos.moreskills.net.msg.GreedCasinoResultPayload;
import com.github.wal_bos.moreskills.net.msg.IWantLimboOpenPayload;
import com.github.wal_bos.moreskills.net.msg.IWantLimboRerollPayload;
import com.github.wal_bos.moreskills.net.msg.IWantLimboRevealPayload;
import com.github.wal_bos.moreskills.net.msg.IWantLimboRoomPayload;
import com.github.wal_bos.moreskills.net.msg.IWantLimboSelectPayload;
import com.github.wal_bos.moreskills.net.msg.ImperialHaloShotPayload;
import com.github.wal_bos.moreskills.net.msg.IstarothDoomTornadoPayload;
import com.github.wal_bos.moreskills.net.msg.MichaelAuraVfxPayload;
import com.github.wal_bos.moreskills.net.msg.MichaelHalberdShotVfxPayload;
import com.github.wal_bos.moreskills.net.msg.MichaelJerezVfxPayload;
import com.github.wal_bos.moreskills.net.msg.MichaelKeterVfxPayload;
import com.github.wal_bos.moreskills.net.msg.MichaelLataibBarrageVfxPayload;
import com.github.wal_bos.moreskills.net.msg.MichaelSegvaVfxPayload;
import com.github.wal_bos.moreskills.net.msg.MichaelShifuruMeteorVfxPayload;
import com.github.wal_bos.moreskills.net.msg.MitamaAtomicEdictPayload;
import com.github.wal_bos.moreskills.net.msg.MitamaDivineBeamsPayload;
import com.github.wal_bos.moreskills.net.msg.MitamaOfferingActionPayload;
import com.github.wal_bos.moreskills.net.msg.MorphSyncPayload;
import com.github.wal_bos.moreskills.net.msg.NaberiusActionPayload;
import com.github.wal_bos.moreskills.net.msg.NirvanaActionPayload;
import com.github.wal_bos.moreskills.net.msg.NirvanaToastPayload;
import com.github.wal_bos.moreskills.net.msg.NovariBlackWhiteVfxPayload;
import com.github.wal_bos.moreskills.net.msg.OpenFeldwayRewardScreenPayload;
import com.github.wal_bos.moreskills.net.msg.PainSixPathsVfxPayload;
import com.github.wal_bos.moreskills.net.msg.PhainonAcquirementVfxPayload;
import com.github.wal_bos.moreskills.net.msg.RonovaFinalityVfxPayload;
import com.github.wal_bos.moreskills.net.msg.SatellaVfxPayload;
import com.github.wal_bos.moreskills.net.msg.SeraphicGuillotinePayload;
import com.github.wal_bos.moreskills.net.msg.WitchOfEnvySatellaVfxPayload;
import io.github.manasmods.manascore.network.api.util.NetworkUtils;

public final class ModPayloads {
   private ModPayloads() {
   }

   public static void init() {
      NetworkUtils.registerC2SPayload(CaedrosMenuActionPayload.TYPE, CaedrosMenuActionPayload.STREAM_CODEC, CaedrosMenuActionPayload::handle);
      NetworkUtils.registerC2SPayload(
         MahoragaAdaptationActionPayload.TYPE, MahoragaAdaptationActionPayload.STREAM_CODEC, MahoragaAdaptationActionPayload::handle
      );
      NetworkUtils.registerC2SPayload(ArthurAfkRequestPayload.TYPE, ArthurAfkRequestPayload.STREAM_CODEC, ArthurAfkRequestPayload::handle);
      NetworkUtils.registerS2CPayload(ArthurAfkVfxPayload.TYPE, ArthurAfkVfxPayload.STREAM_CODEC, ArthurAfkVfxPayload::handle);
      NetworkUtils.registerS2CPayload(
         ArthurTrueExcaliburVfxPayload.TYPE,
         ArthurTrueExcaliburVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("arthur_true_excalibur", payload, context, ArthurTrueExcaliburVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         ArthurHolyOrderVfxPayload.TYPE,
         ArthurHolyOrderVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("arthur_holy_order", payload, context, ArthurHolyOrderVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         ArthurLightDominationVfxPayload.TYPE,
         ArthurLightDominationVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("arthur_light_domination", payload, context, ArthurLightDominationVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         ArthurHolyDisintegrationVfxPayload.TYPE,
         ArthurHolyDisintegrationVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("arthur_holy_disintegration", payload, context, ArthurHolyDisintegrationVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         ArthurHolyAuraVfxPayload.TYPE,
         ArthurHolyAuraVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("arthur_holy_aura", payload, context, ArthurHolyAuraVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         ArthurKingsBattleVfxPayload.TYPE,
         ArthurKingsBattleVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("arthur_kings_battle", payload, context, ArthurKingsBattleVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         ArthurPromisedVictoryVfxPayload.TYPE,
         ArthurPromisedVictoryVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("arthur_promised_victory", payload, context, ArthurPromisedVictoryVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         AvalonDivineExcaliburVfxPayload.TYPE,
         AvalonDivineExcaliburVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("avalon_divine_excalibur", payload, context, AvalonDivineExcaliburVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         AvalonKingdomVfxPayload.TYPE,
         AvalonKingdomVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("avalon_kingdom", payload, context, AvalonKingdomVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         AvalonLawSanctuaryVfxPayload.TYPE,
         AvalonLawSanctuaryVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("avalon_law_sanctuary", payload, context, AvalonLawSanctuaryVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         AvalonLawSacredPurityVfxPayload.TYPE,
         AvalonLawSacredPurityVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("avalon_law_sacred_purity", payload, context, AvalonLawSacredPurityVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         AvalonLawEndlessReservoirVfxPayload.TYPE,
         AvalonLawEndlessReservoirVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("avalon_law_endless_reservoir", payload, context, AvalonLawEndlessReservoirVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         AvalonLawSilentThroneVfxPayload.TYPE,
         AvalonLawSilentThroneVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("avalon_law_silent_throne", payload, context, AvalonLawSilentThroneVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         AvalonLawCrushingHeavenVfxPayload.TYPE,
         AvalonLawCrushingHeavenVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("avalon_law_crushing_heaven", payload, context, AvalonLawCrushingHeavenVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         AvalonLawClosedPrisonVfxPayload.TYPE,
         AvalonLawClosedPrisonVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("avalon_law_closed_prison", payload, context, AvalonLawClosedPrisonVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         AvalonLawReversedProvidenceVfxPayload.TYPE,
         AvalonLawReversedProvidenceVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("avalon_law_reversed_providence", payload, context, AvalonLawReversedProvidenceVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         AvalonLawSeveredExistenceVfxPayload.TYPE,
         AvalonLawSeveredExistenceVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("avalon_law_severed_existence", payload, context, AvalonLawSeveredExistenceVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         AvalonSovereignJudgementVfxPayload.TYPE,
         AvalonSovereignJudgementVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("avalon_sovereign_judgement", payload, context, AvalonSovereignJudgementVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         AvalonSovereignHeavensFallVfxPayload.TYPE,
         AvalonSovereignHeavensFallVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("avalon_sovereign_heavens_fall", payload, context, AvalonSovereignHeavensFallVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         AvalonSovereignSanctuaryVfxPayload.TYPE,
         AvalonSovereignSanctuaryVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("avalon_sovereign_sanctuary", payload, context, AvalonSovereignSanctuaryVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         AvalonDivineDisintegrationVfxPayload.TYPE,
         AvalonDivineDisintegrationVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("avalon_divine_disintegration", payload, context, AvalonDivineDisintegrationVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         AvalonEternalVictoryVfxPayload.TYPE,
         AvalonEternalVictoryVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("avalon_eternal_victory", payload, context, AvalonEternalVictoryVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         AvalonHolyAuraVfxPayload.TYPE,
         AvalonHolyAuraVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("avalon_holy_aura", payload, context, AvalonHolyAuraVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         AvalonRoundTableVfxPayload.TYPE,
         AvalonRoundTableVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("avalon_round_table", payload, context, AvalonRoundTableVfxPayload::handle)
      );
      NetworkUtils.registerC2SPayload(GreedCasinoGamblePayload.TYPE, GreedCasinoGamblePayload.STREAM_CODEC, GreedCasinoGamblePayload::handle);
      NetworkUtils.registerC2SPayload(MitamaOfferingActionPayload.TYPE, MitamaOfferingActionPayload.STREAM_CODEC, MitamaOfferingActionPayload::handle);
      NetworkUtils.registerS2CPayload(GreedCasinoResultPayload.TYPE, GreedCasinoResultPayload.STREAM_CODEC, GreedCasinoResultPayload::handle);
      NetworkUtils.registerS2CPayload(
         IstarothDoomTornadoPayload.TYPE,
         IstarothDoomTornadoPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("istaroth_doom_tornado", payload, context, IstarothDoomTornadoPayload::handle)
      );
      NetworkUtils.registerC2SPayload(NaberiusActionPayload.TYPE, NaberiusActionPayload.STREAM_CODEC, NaberiusActionPayload::handle);
      NetworkUtils.registerS2CPayload(
         AsmodayBlackHolePayload.TYPE,
         AsmodayBlackHolePayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("asmoday_black_hole", payload, context, AsmodayBlackHolePayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         GearshiftTrailPayload.TYPE,
         GearshiftTrailPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("gearshift_blue_trail", payload, context, GearshiftTrailPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         FullCowlLightningPayload.TYPE,
         FullCowlLightningPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("full_cowl_lightning", payload, context, FullCowlLightningPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         AxisJudgmentPayload.TYPE,
         AxisJudgmentPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("axis_judgment", payload, context, AxisJudgmentPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         FeldwayAvatarBeamPayload.TYPE,
         FeldwayAvatarBeamPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("feldway_avatar_beam", payload, context, FeldwayAvatarBeamPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         AsmodaySpatialCubePayload.TYPE,
         AsmodaySpatialCubePayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("asmoday_spatial_cube", payload, context, AsmodaySpatialCubePayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         AsmodayFallingCubePayload.TYPE,
         AsmodayFallingCubePayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("asmoday_falling_cube", payload, context, AsmodayFallingCubePayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         SeraphicGuillotinePayload.TYPE,
         SeraphicGuillotinePayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("seraphic_guillotine", payload, context, SeraphicGuillotinePayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         ChronoJudgementClockPayload.TYPE,
         ChronoJudgementClockPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("chrono_judgement_clock", payload, context, ChronoJudgementClockPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         ImperialHaloShotPayload.TYPE,
         ImperialHaloShotPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("imperial_halo_shot", payload, context, ImperialHaloShotPayload::handle)
      );
      NetworkUtils.registerS2CPayload(OpenFeldwayRewardScreenPayload.TYPE, OpenFeldwayRewardScreenPayload.STREAM_CODEC, OpenFeldwayRewardScreenPayload::handle);
      NetworkUtils.registerS2CPayload(
         AsmodaySpatialAbsorptionPayload.TYPE,
         AsmodaySpatialAbsorptionPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("asmoday_spatial_absorption", payload, context, AsmodaySpatialAbsorptionPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         SatellaVfxPayload.TYPE,
         SatellaVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("satella", payload, context, SatellaVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         AsmodayPrisonRealmPayload.TYPE,
         AsmodayPrisonRealmPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("asmoday_prison_realm", payload, context, AsmodayPrisonRealmPayload::handle)
      );
      NetworkUtils.registerC2SPayload(ClaimFeldwayRewardPayload.TYPE, ClaimFeldwayRewardPayload.STREAM_CODEC, ClaimFeldwayRewardPayload::handle);
      NetworkUtils.registerS2CPayload(BloodfiendDataSyncPayload.TYPE, BloodfiendDataSyncPayload.STREAM_CODEC, BloodfiendDataSyncPayload::handle);
      NetworkUtils.registerS2CPayload(
         PhainonAcquirementVfxPayload.TYPE,
         PhainonAcquirementVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("phainon_acquirement", payload, context, PhainonAcquirementVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         RonovaFinalityVfxPayload.TYPE,
         RonovaFinalityVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("ronova_finality", payload, context, RonovaFinalityVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(MorphSyncPayload.TYPE, MorphSyncPayload.STREAM_CODEC, MorphSyncPayload::handle);
      NetworkUtils.registerS2CPayload(
         AlbisVinaVfxPayload.TYPE,
         AlbisVinaVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("albis_vina", payload, context, AlbisVinaVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         BlackCommunionVfxPayload.TYPE,
         BlackCommunionVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("black_communion", payload, context, BlackCommunionVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         ElfariaIcemaidenVfxPayload.TYPE,
         ElfariaIcemaidenVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("elfaria_icemaiden", payload, context, ElfariaIcemaidenVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         MitamaDivineBeamsPayload.TYPE,
         MitamaDivineBeamsPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("mitama_divine_beams", payload, context, MitamaDivineBeamsPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         MitamaAtomicEdictPayload.TYPE,
         MitamaAtomicEdictPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("mitama_atomic_edict", payload, context, MitamaAtomicEdictPayload::handle)
      );
      NetworkUtils.registerS2CPayload(NirvanaToastPayload.TYPE, NirvanaToastPayload.STREAM_CODEC, NirvanaToastPayload::handle);
      NetworkUtils.registerS2CPayload(
         WitchOfEnvySatellaVfxPayload.TYPE,
         WitchOfEnvySatellaVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("witch_of_envy_satella", payload, context, WitchOfEnvySatellaVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         PainSixPathsVfxPayload.TYPE,
         PainSixPathsVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("pain_six_paths", payload, context, PainSixPathsVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         AvalonNovariPhaseVfxPayload.TYPE,
         AvalonNovariPhaseVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("avalon_novari_phase", payload, context, AvalonNovariPhaseVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         NovariBlackWhiteVfxPayload.TYPE,
         NovariBlackWhiteVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("novari_black_white", payload, context, NovariBlackWhiteVfxPayload::handle)
      );
      NetworkUtils.registerC2SPayload(NirvanaActionPayload.TYPE, NirvanaActionPayload.STREAM_CODEC, NirvanaActionPayload::handle);
      NetworkUtils.registerC2SPayload(IWantLimboOpenPayload.TYPE, IWantLimboOpenPayload.STREAM_CODEC, IWantLimboOpenPayload::handle);
      NetworkUtils.registerC2SPayload(IWantLimboSelectPayload.TYPE, IWantLimboSelectPayload.STREAM_CODEC, IWantLimboSelectPayload::handle);
      NetworkUtils.registerS2CPayload(IWantLimboRoomPayload.TYPE, IWantLimboRoomPayload.STREAM_CODEC, IWantLimboRoomPayload::handle);
      NetworkUtils.registerS2CPayload(IWantLimboRevealPayload.TYPE, IWantLimboRevealPayload.STREAM_CODEC, IWantLimboRevealPayload::handle);
      NetworkUtils.registerC2SPayload(IWantLimboRerollPayload.TYPE, IWantLimboRerollPayload.STREAM_CODEC, IWantLimboRerollPayload::handle);
      NetworkUtils.registerS2CPayload(
         MichaelAuraVfxPayload.TYPE,
         MichaelAuraVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("michael_aura", payload, context, MichaelAuraVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         MichaelSegvaVfxPayload.TYPE,
         MichaelSegvaVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("michael_segva", payload, context, MichaelSegvaVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         MichaelLataibBarrageVfxPayload.TYPE,
         MichaelLataibBarrageVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("michael_lataib_barrage", payload, context, MichaelLataibBarrageVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         MichaelHalberdShotVfxPayload.TYPE,
         MichaelHalberdShotVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("michael_halberd_shot", payload, context, MichaelHalberdShotVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         MichaelShifuruMeteorVfxPayload.TYPE,
         MichaelShifuruMeteorVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("michael_shifuru_meteor", payload, context, MichaelShifuruMeteorVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         MichaelJerezVfxPayload.TYPE,
         MichaelJerezVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("michael_jerez", payload, context, MichaelJerezVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         MichaelKeterVfxPayload.TYPE,
         MichaelKeterVfxPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("michael_keter", payload, context, MichaelKeterVfxPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         BerettaLowVoidLaserPayload.TYPE,
         BerettaLowVoidLaserPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("beretta_low_void_laser", payload, context, BerettaLowVoidLaserPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         BerettaOrbCascadePayload.TYPE,
         BerettaOrbCascadePayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("beretta_orb_cascade", payload, context, BerettaOrbCascadePayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         BerettaDarknessCannonPayload.TYPE,
         BerettaDarknessCannonPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("beretta_darkness_cannon", payload, context, BerettaDarknessCannonPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         BerettaMineralEruptionPayload.TYPE,
         BerettaMineralEruptionPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("beretta_mineral_eruption", payload, context, BerettaMineralEruptionPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         BerettaReversedCrossPayload.TYPE,
         BerettaReversedCrossPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("beretta_reversed_cross", payload, context, BerettaReversedCrossPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         BerettaChaosRingsPayload.TYPE,
         BerettaChaosRingsPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("beretta_chaos_rings", payload, context, BerettaChaosRingsPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         BerettaMachinaGridPayload.TYPE,
         BerettaMachinaGridPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("beretta_machina_grid", payload, context, BerettaMachinaGridPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         BerettaInvertedFusionPayload.TYPE,
         BerettaInvertedFusionPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("beretta_inverted_fusion", payload, context, BerettaInvertedFusionPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         BerettaAbyssalGearSweepPayload.TYPE,
         BerettaAbyssalGearSweepPayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("beretta_abyssal_gear_sweep", payload, context, BerettaAbyssalGearSweepPayload::handle)
      );
      NetworkUtils.registerS2CPayload(
         BerettaChaosCoreExposurePayload.TYPE,
         BerettaChaosCoreExposurePayload.STREAM_CODEC,
         (payload, context) -> ClientVfxPacketGate.run("beretta_chaos_core_exposure", payload, context, BerettaChaosCoreExposurePayload::handle)
      );
   }
}
