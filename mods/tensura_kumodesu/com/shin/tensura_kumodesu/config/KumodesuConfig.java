package com.shin.tensura_kumodesu.config;

import com.shin.tensura_kumodesu.config.race.OgreConfig;
import com.shin.tensura_kumodesu.config.race.TaratectConfig;
import com.shin.tensura_kumodesu.config.skill.KumodesuExtraSkillConfig;
import com.shin.tensura_kumodesu.config.skill.KumodesuIntrinsicSkillConfig;
import com.shin.tensura_kumodesu.config.skill.KumodesuUniqueSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.tensura.config.ReincarnationConfig;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class KumodesuConfig {
   protected static final List<String> races = List.of("tensura_kumodesu:small_lesser_taratect");

   public static void init() {
      ConfigRegistry.registerConfig(new TaratectConfig());
      ConfigRegistry.registerConfig(new OgreConfig());
      ConfigRegistry.registerConfig(new KumodesuIntrinsicSkillConfig());
      ConfigRegistry.registerConfig(new KumodesuExtraSkillConfig());
      ConfigRegistry.registerConfig(new KumodesuUniqueSkillConfig());
      ConfigRegistry.registerConfig(new KumodesuGeneralConfig());
      ConfigRegistry.registerConfig(new ReincarnationConfig());
   }

   public static void addToConfig() {
      if (shouldAddToConfig()) {
         ReincarnationConfig reinc = (ReincarnationConfig)ConfigRegistry.getConfig(ReincarnationConfig.class);
         ArrayList<String> startingRaces = new ArrayList<>(reinc.Races.startingRaces);
         ArrayList<String> randomRaces = new ArrayList<>(reinc.Races.randomRaces);
         LinkedHashSet<String> startRaceSet = new LinkedHashSet<>(startingRaces);
         LinkedHashSet<String> randomRaceSet = new LinkedHashSet<>(randomRaces);

         for (String raceRegistry : races) {
            startRaceSet.add(raceRegistry);
            randomRaceSet.add(raceRegistry);
         }

         reinc.Races.startingRaces = new ArrayList<>(startRaceSet);
         reinc.Races.randomRaces = new ArrayList<>(randomRaceSet);
         ((KumodesuGeneralConfig)ConfigRegistry.getConfig(KumodesuGeneralConfig.class)).General.refresh = false;
      }
   }

   public static boolean shouldAddToConfig() {
      return ((KumodesuGeneralConfig)ConfigRegistry.getConfig(KumodesuGeneralConfig.class)).General.refresh;
   }
}
