package com.mortal_cultivation.registry.race;

import com.mortal_cultivation.race.demonic_cultivator.DemonicFoundationRace;
import com.mortal_cultivation.race.demonic_cultivator.DemonicGoldenCoreRace;
import com.mortal_cultivation.race.demonic_cultivator.DemonicGreatAscensionRace;
import com.mortal_cultivation.race.demonic_cultivator.DemonicNascentSoulRace;
import com.mortal_cultivation.race.demonic_cultivator.DemonicSpiritTransformationRace;
import com.mortal_cultivation.race.demonic_cultivator.DemonicTribulationTranscendenceRace;
import com.mortal_cultivation.race.demonic_cultivator.DemonicTrueImmortalRace;
import com.mortal_cultivation.race.demonic_cultivator.DemonicUnityRace;
import com.mortal_cultivation.race.demonic_cultivator.DemonicVoidRefinementRace;
import com.mortal_cultivation.race.demonic_cultivator.DemonicVoidReturnRace;
import com.mortal_cultivation.race.literal_translation.SigilImmortalRealmRace;
import com.mortal_cultivation.race.literal_translation.SigilMasterRace;
import com.mortal_cultivation.race.literal_translation.SigilSaintRace;
import com.mortal_cultivation.race.literal_translation.TalismanApprenticeRace;
import com.mortal_cultivation.race.literal_translation.TalismanMasterRace;
import com.mortal_cultivation.race.monkey.CosmicMindApeRace;
import com.mortal_cultivation.race.monkey.DemonMonkeyRace;
import com.mortal_cultivation.race.monkey.EnlightenedStoneMonkeyRace;
import com.mortal_cultivation.race.monkey.ImmortalApeRace;
import com.mortal_cultivation.race.monkey.MonkeyRace;
import com.mortal_cultivation.race.monkey.RedButtockedHorseMonkeyRace;
import com.mortal_cultivation.race.monkey.SixEaredMacaqueRace;
import com.mortal_cultivation.race.monkey.SoundwaveImmortalApeRace;
import com.mortal_cultivation.race.monkey.SpiritMonkeyRace;
import com.mortal_cultivation.race.monkey.SunWukongHeavenlyEqualRace;
import com.mortal_cultivation.race.monkey.SunWukongRace;
import com.mortal_cultivation.race.monkey.SunWukongVictoriousBuddhaRace;
import com.mortal_cultivation.race.right_way.DaoBreakingRace;
import com.mortal_cultivation.race.right_way.FoundationEstablishmentRace;
import com.mortal_cultivation.race.right_way.GoldenCoreRace;
import com.mortal_cultivation.race.right_way.GreatAscensionRace;
import com.mortal_cultivation.race.right_way.HeavenlyDaoRace;
import com.mortal_cultivation.race.right_way.NascentSoulRace;
import com.mortal_cultivation.race.right_way.SpiritTransformationRace;
import com.mortal_cultivation.race.right_way.TribulationTranscendenceRace;
import com.mortal_cultivation.race.right_way.TrueImmortalRace;
import com.mortal_cultivation.race.right_way.UnityRace;
import com.mortal_cultivation.race.right_way.VoidRefinementRace;
import com.mortal_cultivation.race.special.WastelanderRace;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.RaceAPI;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CultivationRaces {
   public static final DeferredRegister<ManasRace> RACES = DeferredRegister.create(RaceAPI.getRaceRegistryKey(), "mortal_cultivation");
   public static final DeferredHolder<ManasRace, FoundationEstablishmentRace> FOUNDATION_ESTABLISHMENT_RACE = RACES.register(
      "foundation_establishment_race", FoundationEstablishmentRace::new
   );
   public static final DeferredHolder<ManasRace, GoldenCoreRace> GOLDEN_CORE_RACE = RACES.register("golden_core_race", GoldenCoreRace::new);
   public static final DeferredHolder<ManasRace, NascentSoulRace> NASCENT_SOUL_RACE = RACES.register("nascent_soul_race", NascentSoulRace::new);
   public static final DeferredHolder<ManasRace, SpiritTransformationRace> SPIRIT_TRANSFORMATION_RACE = RACES.register(
      "spirit_transformation_race", SpiritTransformationRace::new
   );
   public static final DeferredHolder<ManasRace, VoidRefinementRace> VOID_REFINEMENT_RACE = RACES.register("void_refinement_race", VoidRefinementRace::new);
   public static final DeferredHolder<ManasRace, UnityRace> UNITY_RACE = RACES.register("unity_race", UnityRace::new);
   public static final DeferredHolder<ManasRace, GreatAscensionRace> GREAT_ASCENSION_RACE = RACES.register("great_ascension_race", GreatAscensionRace::new);
   public static final DeferredHolder<ManasRace, TribulationTranscendenceRace> TRIBULATION_TRANSCENDENCE_RACE = RACES.register(
      "tribulation_transcendence_race", TribulationTranscendenceRace::new
   );
   public static final DeferredHolder<ManasRace, TrueImmortalRace> TRUE_IMMORTAL_RACE = RACES.register("true_immortal_race", TrueImmortalRace::new);
   public static final DeferredHolder<ManasRace, HeavenlyDaoRace> HEAVENLY_DAO_RACE = RACES.register("heavenly_dao_race", HeavenlyDaoRace::new);
   public static final DeferredHolder<ManasRace, DaoBreakingRace> DAO_BREAKING_RACE = RACES.register("dao_breaking_race", DaoBreakingRace::new);
   public static final DeferredHolder<ManasRace, TalismanApprenticeRace> TALISMAN_APPRENTICE_RACE = RACES.register(
      "talisman_apprentice_race", TalismanApprenticeRace::new
   );
   public static final DeferredHolder<ManasRace, TalismanMasterRace> TALISMAN_MASTER_RACE = RACES.register("talisman_master_race", TalismanMasterRace::new);
   public static final DeferredHolder<ManasRace, SigilMasterRace> SIGIL_MASTER_RACE = RACES.register("sigil_master_race", SigilMasterRace::new);
   public static final DeferredHolder<ManasRace, SigilSaintRace> SIGIL_SAINT_RACE = RACES.register("sigil_saint_race", SigilSaintRace::new);
   public static final DeferredHolder<ManasRace, SigilImmortalRealmRace> SIGIL_IMMORTAL_REALM_RACE = RACES.register(
      "sigil_immortal_realm_race", SigilImmortalRealmRace::new
   );
   public static final DeferredHolder<ManasRace, DemonicFoundationRace> DEMONIC_FOUNDATION_RACE = RACES.register(
      "demonic_foundation_race", DemonicFoundationRace::new
   );
   public static final DeferredHolder<ManasRace, DemonicGoldenCoreRace> DEMONIC_GOLDEN_CORE_RACE = RACES.register(
      "demonic_golden_core_race", DemonicGoldenCoreRace::new
   );
   public static final DeferredHolder<ManasRace, DemonicNascentSoulRace> DEMONIC_NASCENT_SOUL_RACE = RACES.register(
      "demonic_nascent_soul_race", DemonicNascentSoulRace::new
   );
   public static final DeferredHolder<ManasRace, DemonicSpiritTransformationRace> DEMONIC_SPIRIT_TRANSFORMATION_RACE = RACES.register(
      "demonic_spirit_transformation_race", DemonicSpiritTransformationRace::new
   );
   public static final DeferredHolder<ManasRace, DemonicVoidRefinementRace> DEMONIC_VOID_REFINEMENT_RACE = RACES.register(
      "demonic_void_refinement_race", DemonicVoidRefinementRace::new
   );
   public static final DeferredHolder<ManasRace, DemonicUnityRace> DEMONIC_UNITY_RACE = RACES.register("demonic_unity_race", DemonicUnityRace::new);
   public static final DeferredHolder<ManasRace, DemonicGreatAscensionRace> DEMONIC_GREAT_ASCENSION_RACE = RACES.register(
      "demonic_great_ascension_race", DemonicGreatAscensionRace::new
   );
   public static final DeferredHolder<ManasRace, DemonicTribulationTranscendenceRace> DEMONIC_TRIBULATION_TRANSCENDENCE_RACE = RACES.register(
      "demonic_tribulation_transcendence_race", DemonicTribulationTranscendenceRace::new
   );
   public static final DeferredHolder<ManasRace, DemonicTrueImmortalRace> DEMONIC_TRUE_IMMORTAL_RACE = RACES.register(
      "demonic_true_immortal_race", DemonicTrueImmortalRace::new
   );
   public static final DeferredHolder<ManasRace, DemonicVoidReturnRace> DEMONIC_VOID_RETURN_RACE = RACES.register(
      "demonic_void_return_race", DemonicVoidReturnRace::new
   );
   public static final DeferredHolder<ManasRace, WastelanderRace> WASTELANDER_RACE = RACES.register("wastelander_race", WastelanderRace::new);
   public static final DeferredHolder<ManasRace, MonkeyRace> MONKEY_RACE = RACES.register("monkey_race", MonkeyRace::new);
   public static final DeferredHolder<ManasRace, SpiritMonkeyRace> SPIRIT_MONKEY_RACE = RACES.register("spirit_monkey_race", SpiritMonkeyRace::new);
   public static final DeferredHolder<ManasRace, DemonMonkeyRace> DEMON_MONKEY_RACE = RACES.register("demon_monkey_race", DemonMonkeyRace::new);
   public static final DeferredHolder<ManasRace, RedButtockedHorseMonkeyRace> RED_BUTTOCKED_HORSE_MONKEY_RACE = RACES.register(
      "red_buttocked_horse_monkey_race", RedButtockedHorseMonkeyRace::new
   );
   public static final DeferredHolder<ManasRace, ImmortalApeRace> IMMORTAL_APE_RACE = RACES.register("immortal_ape_race", ImmortalApeRace::new);
   public static final DeferredHolder<ManasRace, EnlightenedStoneMonkeyRace> ENLIGHTENED_STONE_MONKEY_RACE = RACES.register(
      "enlightened_stone_monkey_race", EnlightenedStoneMonkeyRace::new
   );
   public static final DeferredHolder<ManasRace, SunWukongRace> SUN_WUKONG_RACE = RACES.register("sun_wukong_race", SunWukongRace::new);
   public static final DeferredHolder<ManasRace, SunWukongHeavenlyEqualRace> SUN_WUKONG_HEAVENLY_EQUAL_RACE = RACES.register(
      "sun_wukong_heavenly_equal_race", SunWukongHeavenlyEqualRace::new
   );
   public static final DeferredHolder<ManasRace, SunWukongVictoriousBuddhaRace> SUN_WUKONG_VICTORIOUS_BUDDHA_RACE = RACES.register(
      "sun_wukong_victorious_buddha_race", SunWukongVictoriousBuddhaRace::new
   );
   public static final DeferredHolder<ManasRace, SixEaredMacaqueRace> SIX_EARED_MACAQUE_RACE = RACES.register(
      "six_eared_macaque_race", SixEaredMacaqueRace::new
   );
   public static final DeferredHolder<ManasRace, SoundwaveImmortalApeRace> SOUNDWAVE_IMMORTAL_APE_RACE = RACES.register(
      "soundwave_immortal_ape_race", SoundwaveImmortalApeRace::new
   );
   public static final DeferredHolder<ManasRace, CosmicMindApeRace> COSMIC_MIND_APE_RACE = RACES.register("cosmic_mind_ape_race", CosmicMindApeRace::new);

   public static void register(IEventBus modEventBus) {
      RACES.register(modEventBus);
   }
}
