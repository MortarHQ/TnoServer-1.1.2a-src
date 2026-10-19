package com.github.b4ndithelps.tennogamenolife.config;

import com.mojang.logging.LogUtils;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.tensura.config.ReincarnationConfig;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import org.slf4j.Logger;

public class TNOConfigInjector {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final List<String> STARTING_RACES = List.of(
      "tennogamenolife:flugel", "tennogamenolife:machine", "tennogamenolife:lesser_dragonia", "tennogamenolife:dwarf"
   );
   private static final List<String> RANDOM_RACES = List.of(
      "tennogamenolife:flugel", "tennogamenolife:machine", "tennogamenolife:lesser_dragonia", "tennogamenolife:dwarf"
   );

   public static void addToConfig() {
      try {
         TenreincarnationGeneralConfig config = (TenreincarnationGeneralConfig)ConfigRegistry.getConfig(TenreincarnationGeneralConfig.class);
         if (config == null) {
            LOGGER.warn("TenreincarnationGeneralConfig is not registered, skipping race injection.");
            return;
         }

         if (!config.General.refresh) {
            LOGGER.info("TenNoGameNoLife ReincarnationConfig refresh is disabled, skipping race injection.");
            return;
         }

         ReincarnationConfig reinc = (ReincarnationConfig)ConfigRegistry.getConfig(ReincarnationConfig.class);
         if (reinc == null || reinc.Races == null) {
            LOGGER.warn("ReincarnationConfig is unavailable, skipping TenNoGameNoLife race injection.");
            return;
         }

         boolean changed = false;
         LinkedHashSet<String> startingRaces = new LinkedHashSet<>(reinc.Races.startingRaces != null ? reinc.Races.startingRaces : List.of());
         changed |= startingRaces.addAll(STARTING_RACES);
         LinkedHashSet<String> randomRaces = new LinkedHashSet<>(reinc.Races.randomRaces != null ? reinc.Races.randomRaces : List.of());
         changed |= randomRaces.addAll(RANDOM_RACES);
         LinkedHashSet<String> reincarnationRaces = new LinkedHashSet<>(reinc.Races.reincarnationRaces != null ? reinc.Races.reincarnationRaces : List.of());
         changed |= reincarnationRaces.addAll(STARTING_RACES);
         LinkedHashSet<String> reincarnationRacesMastered = new LinkedHashSet<>(
            reinc.Races.reincarnationRacesMastered != null ? reinc.Races.reincarnationRacesMastered : List.of()
         );
         changed |= reincarnationRacesMastered.addAll(STARTING_RACES);
         reinc.Races.startingRaces = new ArrayList<>(startingRaces);
         reinc.Races.randomRaces = new ArrayList<>(randomRaces);
         reinc.Races.reincarnationRaces = new ArrayList<>(reincarnationRaces);
         reinc.Races.reincarnationRacesMastered = new ArrayList<>(reincarnationRacesMastered);
         config.General.refresh = false;
         ConfigRegistry.saveAllConfigs();
         if (changed) {
            LOGGER.info("TenNoGameNoLife race entries injected and refresh disabled.");
         } else {
            LOGGER.info("TenNoGameNoLife race entries were already present; refresh disabled.");
         }
      } catch (Exception e) {
         LOGGER.error("Failed to inject TenNoGameNoLife race config.", e);
      }
   }
}
