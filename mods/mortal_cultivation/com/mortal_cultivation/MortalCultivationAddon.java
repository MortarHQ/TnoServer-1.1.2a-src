package com.mortal_cultivation;

import com.mojang.logging.LogUtils;
import com.mortal_cultivation.config.MortalCultivationGeneralConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.tensura.config.ReincarnationConfig;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;

public class MortalCultivationAddon {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final List<String> CULTIVATION_RACES = List.of("mortal_cultivation:wastelander_race", "mortal_cultivation:monkey_race");

   public static void addToConfig() {
      try {
         MortalCultivationGeneralConfig config = (MortalCultivationGeneralConfig)ConfigRegistry.getConfig(MortalCultivationGeneralConfig.class);
         if (config == null) {
            LOGGER.warn("MortalCultivationGeneralConfig is not registered, skipping Mortal Cultivation injection.");
            return;
         }

         ReincarnationConfig reinc = (ReincarnationConfig)ConfigRegistry.getConfig(ReincarnationConfig.class);
         if (reinc == null || reinc.Races == null || reinc.Skills == null) {
            LOGGER.warn("ReincarnationConfig is unavailable, skipping Mortal Cultivation injection.");
            return;
         }

         boolean removedStartingSkill = removeMischiefStartingSkill(reinc);
         if (!config.General.refresh) {
            if (removedStartingSkill) {
               ConfigRegistry.saveAllConfigs();
            }

            LOGGER.info("Mortal Cultivation ReincarnationConfig refresh is disabled, skipping injection.");
            return;
         }

         List<String> startingRaces = normalizeManagedEntries(reinc.Races.startingRaces, CULTIVATION_RACES);
         List<String> randomRaces = normalizeManagedEntries(reinc.Races.randomRaces, CULTIVATION_RACES);
         boolean changed = !startingRaces.equals(reinc.Races.startingRaces) || !randomRaces.equals(reinc.Races.randomRaces);
         if (changed) {
            reinc.Races.startingRaces = startingRaces;
            reinc.Races.randomRaces = randomRaces;
            LOGGER.info("Mortal Cultivation ReincarnationConfig entries normalized.");
         } else {
            LOGGER.info("Mortal Cultivation ReincarnationConfig entries are already normalized.");
         }

         config.General.refresh = false;
         ConfigRegistry.saveAllConfigs();
         LOGGER.info("Mortal Cultivation ReincarnationConfig refresh disabled, config saved.");
      } catch (Exception e) {
         LOGGER.error("Failed to inject Mortal Cultivation config: {}", e.getMessage(), e);
      }
   }

   private static boolean removeMischiefStartingSkill(ReincarnationConfig reinc) {
      if (reinc.Skills.startingSkills == null) {
         return false;
      }

      List<String> retained = new ArrayList<>(reinc.Skills.startingSkills);
      boolean removed = retained.removeIf(value -> value != null && "mortal_cultivation:mischief".equals(value.trim()));
      if (removed) {
         reinc.Skills.startingSkills = retained;
      }

      return removed;
   }

   private static List<String> normalizeManagedEntries(List<String> current, List<String> managed) {
      Set<String> managedSet = Set.copyOf(managed);
      LinkedHashSet<String> normalized = new LinkedHashSet<>();
      if (current != null) {
         current.stream()
            .filter(Objects::nonNull)
            .map(String::trim)
            .filter(value -> !value.isEmpty())
            .filter(value -> !managedSet.contains(value))
            .forEach(normalized::add);
      }

      normalized.addAll(managed);
      return new ArrayList<>(normalized);
   }
}
