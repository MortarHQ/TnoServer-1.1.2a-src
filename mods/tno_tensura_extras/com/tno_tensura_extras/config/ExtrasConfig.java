package com.tno_tensura_extras.config;

import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.tensura.config.ReincarnationConfig;
import java.util.ArrayList;

public final class ExtrasConfig {
   private static final String ALL_ROUNDER_ID = "tno_tensura_extras:all_rounder";
   private static final String OPTIMIST_ID = "tno_tensura_extras:optimist";
   private static final String LOOTER_ID = "tno_tensura_extras:looter";
   private static final String TIME_TRAVELER_ID = "tno_tensura_extras:time_traveler";
   private static final String SEDUCER_ID = "tno_tensura_extras:seducer";

   private ExtrasConfig() {
   }

   public static void addOptimistToReincarnationPool() {
      addToReincarnationPool("tno_tensura_extras:optimist");
   }

   public static void addLooterToReincarnationPool() {
      addToReincarnationPool("tno_tensura_extras:looter");
   }

   public static void addUniqueSkillsToReincarnationPool() {
      ReincarnationConfig config = (ReincarnationConfig)ConfigRegistry.getConfig(ReincarnationConfig.class);
      if (config != null && config.Skills != null && config.Skills.startingSkills != null) {
         ArrayList<String> startingSkills = new ArrayList<>(config.Skills.startingSkills);
         boolean changed = false;
         if (!startingSkills.contains("tno_tensura_extras:all_rounder")) {
            startingSkills.add("tno_tensura_extras:all_rounder");
            changed = true;
         }

         if (!startingSkills.contains("tno_tensura_extras:optimist")) {
            startingSkills.add("tno_tensura_extras:optimist");
            changed = true;
         }

         if (!startingSkills.contains("tno_tensura_extras:looter")) {
            startingSkills.add("tno_tensura_extras:looter");
            changed = true;
         }

         if (!startingSkills.contains("tno_tensura_extras:time_traveler")) {
            startingSkills.add("tno_tensura_extras:time_traveler");
            changed = true;
         }

         if (!startingSkills.contains("tno_tensura_extras:seducer")) {
            startingSkills.add("tno_tensura_extras:seducer");
            changed = true;
         }

         if (changed) {
            config.Skills.startingSkills = startingSkills;
            ConfigRegistry.saveAllConfigs();
         }
      }
   }

   private static void addToReincarnationPool(String skillId) {
      ReincarnationConfig config = (ReincarnationConfig)ConfigRegistry.getConfig(ReincarnationConfig.class);
      if (config != null && config.Skills != null && config.Skills.startingSkills != null && !config.Skills.startingSkills.contains(skillId)) {
         ArrayList<String> startingSkills = new ArrayList<>(config.Skills.startingSkills);
         startingSkills.add(skillId);
         config.Skills.startingSkills = startingSkills;
         ConfigRegistry.saveAllConfigs();
      }
   }
}
