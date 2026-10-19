package io.github.Memoires.mysticism.config;

import io.github.Memoires.mysticism.config.ability.magic.SpiritualMysticismMagicConfig;
import io.github.Memoires.mysticism.config.ability.skill.ExtraMysticismSkillConfig;
import io.github.Memoires.mysticism.config.ability.skill.IntrinsicMysticismSkillConfig;
import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.config.effect.EffectConfig;
import io.github.Memoires.mysticism.config.entity.MysticismSpawnRateConfig;
import io.github.Memoires.mysticism.config.race.AngelConfig;
import io.github.Memoires.mysticism.config.race.DaemonDollConfig;
import io.github.Memoires.mysticism.config.race.DragonoidConfig;
import io.github.Memoires.mysticism.config.race.ElementalConfig;
import io.github.Memoires.mysticism.config.race.ForgottenConfig;
import io.github.Memoires.mysticism.config.race.PhantomConfig;
import io.github.Memoires.mysticism.config.race.RestrictedHumanConfig;
import io.github.Memoires.mysticism.config.race.SculkConfig;
import io.github.Memoires.mysticism.config.race.WyrmConfig;
import io.github.Memoires.mysticism.config.race.direwolf.DirewolfConfig;
import io.github.Memoires.mysticism.config.race.direwolf.SpecialDirewolfConfig;
import io.github.Memoires.mysticism.config.race.insect.AntConfig;
import io.github.Memoires.mysticism.config.race.insect.BeetleConfig;
import io.github.Memoires.mysticism.config.race.insect.CentipedeConfig;
import io.github.Memoires.mysticism.config.race.insect.MantisConfig;
import io.github.Memoires.mysticism.config.race.insect.ScorpionConfig;
import io.github.Memoires.mysticism.config.race.insect.WaspConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.tensura.config.ReincarnationConfig;
import io.github.manasmods.tensura.config.ability.skill.UniqueSkillConfig;
import io.github.manasmods.tensura.config.entity.PlayerConfig;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MysticismConfigs {
   protected static final List<String> races = List.of(
      "mysticism:attuned_wyrm",
      "mysticism:direwolf",
      "mysticism:dragonoid",
      "mysticism:forgotten",
      "mysticism:insect",
      "mysticism:lesser_angel",
      "mysticism:lesser_elemental",
      "mysticism:phantom",
      "mysticism:restricted_human",
      "mysticism:sculk_worm"
   );
   protected static final List<String> skills = List.of(
      "mysticism:butcher",
      "mysticism:captivator",
      "mysticism:coalescence",
      "mysticism:constant",
      "mysticism:corroder",
      "mysticism:crasher",
      "mysticism:cultivator",
      "mysticism:dreamer",
      "mysticism:gardener",
      "mysticism:hidden_ruler",
      "mysticism:jinchuriki",
      "mysticism:kyurem",
      "mysticism:malleable",
      "mysticism:phaser",
      "mysticism:provider",
      "mysticism:reducer",
      "mysticism:repeater",
      "mysticism:reshiram",
      "mysticism:scholar",
      "mysticism:spiritualist",
      "mysticism:stagnator",
      "mysticism:subjugator",
      "mysticism:victorious_harbinger",
      "mysticism:zekrom"
   );
   protected static final List<String> secondSkillsList = List.of(
      "mysticism:captivator", "mysticism:cultivator", "mysticism:gardener", "mysticism:hidden_ruler", "mysticism:provider", "mysticism:scholar"
   );
   protected static final List<String> enchantments = List.of("mysticism:holy_flames", "mysticism:boreal_frost");
   public static final Set<String> POWERFUL_SKILL_IDS = Set.of(
      "tensura:pride",
      "tensura:envy",
      "tensura:predator",
      "tensura:gluttony",
      "tensura:sloth",
      "tensura:lust",
      "tensura:greed",
      "tensura:wrath",
      "tensura:unyielding",
      "mysticism:coalescence",
      "tensura:great_sage",
      "tensura:creator",
      "tensura:chosen_one",
      "mysticism:reducer",
      "mysticism:provider",
      "tensura:infinity_prison",
      "mysticism:melancholy",
      "mysticism:subjugator",
      "mysticism:butcher"
   );
   public static final Set<String> resetCounterBoss = Set.of("mysticism:memoires");

   public static void init() {
      ConfigRegistry.registerConfig(new AngelConfig());
      ConfigRegistry.registerConfig(new AntConfig());
      ConfigRegistry.registerConfig(new BeetleConfig());
      ConfigRegistry.registerConfig(new CentipedeConfig());
      ConfigRegistry.registerConfig(new DaemonDollConfig());
      ConfigRegistry.registerConfig(new DirewolfConfig());
      ConfigRegistry.registerConfig(new SpecialDirewolfConfig());
      ConfigRegistry.registerConfig(new DragonoidConfig());
      ConfigRegistry.registerConfig(new EffectConfig());
      ConfigRegistry.registerConfig(new ElementalConfig());
      ConfigRegistry.registerConfig(new ExtraMysticismSkillConfig());
      ConfigRegistry.registerConfig(new ForgottenConfig());
      ConfigRegistry.registerConfig(new MantisConfig());
      ConfigRegistry.registerConfig(new MysticismGeneralConfig());
      ConfigRegistry.registerConfig(new IntrinsicMysticismSkillConfig());
      ConfigRegistry.registerConfig(new PhantomConfig());
      ConfigRegistry.registerConfig(new UniqueMysticismSkillConfig());
      ConfigRegistry.registerConfig(new ScorpionConfig());
      ConfigRegistry.registerConfig(new SculkConfig());
      ConfigRegistry.registerConfig(new WaspConfig());
      ConfigRegistry.registerConfig(new WyrmConfig());
      ConfigRegistry.registerConfig(new RestrictedHumanConfig());
      ConfigRegistry.registerConfig(new MysticismSpawnRateConfig());
      ConfigRegistry.registerConfig(new SpiritualMysticismMagicConfig());
   }

   public static void addToConfig() {
      if (shouldAddToConfig()) {
         boolean changesMade = false;
         ReincarnationConfig reinc = (ReincarnationConfig)ConfigRegistry.getConfig(ReincarnationConfig.class);
         UniqueSkillConfig unique = (UniqueSkillConfig)ConfigRegistry.getConfig(UniqueSkillConfig.class);
         PlayerConfig player = (PlayerConfig)ConfigRegistry.getConfig(PlayerConfig.class);
         ArrayList<String> startingSkills = new ArrayList<>(reinc.Skills.startingSkills);
         ArrayList<String> startingRaces = new ArrayList<>(reinc.Races.startingRaces);
         ArrayList<String> randomRaces = new ArrayList<>(reinc.Races.randomRaces);
         ArrayList<String> creatorSkills = new ArrayList<>(unique.Creator.uniqueSkills);
         ArrayList<String> godlyCraftmanBlacklist = new ArrayList<>(unique.GodlyCraftsman.enchantmentBlacklist);
         ArrayList<String> bossesCounter = new ArrayList<>(player.ResetScroll.bossesCounter);
         ArrayList<String> reincarnationRaces = new ArrayList<>(reinc.Races.reincarnationRaces);
         ArrayList<String> reincarnationRacesMastery = new ArrayList<>(reinc.Races.reincarnationRacesMastered);
         ArrayList<String> secondSkills = new ArrayList<>(reinc.Skills.secondSkills);
         ArrayList<String> separateBlacklist = new ArrayList<>(unique.Degenerate.separateBlacklist);
         ArrayList<String> synthesisBlacklist = new ArrayList<>(unique.Degenerate.synthesisBlacklist);
         LinkedHashSet<String> startSkillSet = new LinkedHashSet<>(startingSkills);
         LinkedHashSet<String> startRaceSet = new LinkedHashSet<>(startingRaces);
         LinkedHashSet<String> randomRaceSet = new LinkedHashSet<>(randomRaces);
         LinkedHashSet<String> creatorSet = new LinkedHashSet<>(creatorSkills);
         LinkedHashSet<String> godlyCraftmanBlacklistSet = new LinkedHashSet<>(godlyCraftmanBlacklist);
         LinkedHashSet<String> bossesCounterSet = new LinkedHashSet<>(bossesCounter);
         LinkedHashSet<String> reincarnationRacesSet = new LinkedHashSet<>(reincarnationRaces);
         LinkedHashSet<String> reincarnationRacesMasterySet = new LinkedHashSet<>(reincarnationRacesMastery);
         LinkedHashSet<String> secondSkillsSet = new LinkedHashSet<>(secondSkills);
         LinkedHashSet<String> separateBlacklistSet = new LinkedHashSet<>(separateBlacklist);
         LinkedHashSet<String> synthesisBlacklistSet = new LinkedHashSet<>(synthesisBlacklist);

         for (String skillRegistry : skills) {
            if (startSkillSet.add(skillRegistry)) {
               changesMade = true;
            }

            if (!POWERFUL_SKILL_IDS.contains(skillRegistry) && !"mysticism:restricted".equals(skillRegistry) && creatorSet.add(skillRegistry)) {
               changesMade = true;
            }
         }

         for (String skillRegistry : secondSkillsList) {
            if (secondSkillsSet.add(skillRegistry)) {
               changesMade = true;
            }
         }

         for (String enchantmentRegistry : enchantments) {
            if (godlyCraftmanBlacklistSet.add(enchantmentRegistry)) {
               changesMade = true;
            }
         }

         for (String enchantmentRegistry : enchantments) {
            if (separateBlacklistSet.add(enchantmentRegistry)) {
               changesMade = true;
            }
         }

         for (String enchantmentRegistry : enchantments) {
            if (synthesisBlacklistSet.add(enchantmentRegistry)) {
               changesMade = true;
            }
         }

         for (String raceRegistry : races) {
            if (startRaceSet.add(raceRegistry)) {
               changesMade = true;
            }

            if (randomRaceSet.add(raceRegistry)) {
               changesMade = true;
            }
         }

         for (String raceReincarnationRegistry : races) {
            if (!"mysticism:lesser_angel".equals(raceReincarnationRegistry)
               && !"mysticism:lesser_elemental".equals(raceReincarnationRegistry)
               && !"mysticism:phantom".equals(raceReincarnationRegistry)
               && !"mysticism:dragonoid".equals(raceReincarnationRegistry)
               && !"mysticism:forgotten".equals(raceReincarnationRegistry)
               && reincarnationRacesSet.add(raceReincarnationRegistry)) {
               changesMade = true;
            }
         }

         for (String raceReincarnationRegistry : races) {
            if (reincarnationRacesMasterySet.add(raceReincarnationRegistry)) {
               changesMade = true;
            }
         }

         for (String bossRegistry : resetCounterBoss) {
            if (bossesCounterSet.add(bossRegistry)) {
               changesMade = true;
            }

            if (bossesCounterSet.add(bossRegistry)) {
               changesMade = true;
            }
         }

         reinc.Skills.startingSkills = new ArrayList<>(startSkillSet);
         reinc.Races.startingRaces = new ArrayList<>(startRaceSet);
         reinc.Races.randomRaces = new ArrayList<>(randomRaceSet);
         unique.Creator.uniqueSkills = new ArrayList<>(creatorSet);
         unique.GodlyCraftsman.enchantmentBlacklist = new ArrayList<>(godlyCraftmanBlacklistSet);
         player.ResetScroll.bossesCounter = new ArrayList<>(bossesCounterSet);
         reinc.Skills.secondSkills = new ArrayList<>(secondSkillsSet);
         reinc.Races.reincarnationRaces = new ArrayList<>(reincarnationRacesSet);
         reinc.Races.reincarnationRacesMastered = new ArrayList<>(reincarnationRacesMasterySet);
         unique.Degenerate.separateBlacklist = new ArrayList<>(separateBlacklistSet);
         unique.Degenerate.synthesisBlacklist = new ArrayList<>(synthesisBlacklistSet);
         if (changesMade) {
            ConfigRegistry.saveAllConfigs();
         }

         ((MysticismGeneralConfig)ConfigRegistry.getConfig(MysticismGeneralConfig.class)).General.refresh = false;
         ConfigRegistry.saveAllConfigs();
      }
   }

   public static boolean shouldAddToConfig() {
      return ((MysticismGeneralConfig)ConfigRegistry.getConfig(MysticismGeneralConfig.class)).General.refresh;
   }
}
