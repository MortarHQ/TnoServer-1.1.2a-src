package com.github.dominickwd04.traddon.config;

import com.github.dominickwd04.traddon.config.ability.skill.TrAddonSkillConfig;
import com.github.dominickwd04.traddon.config.race.ShardlingConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.tensura.config.ReincarnationConfig;
import io.github.manasmods.tensura.config.ability.skill.UniqueSkillConfig;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import net.minecraft.resources.ResourceLocation;

public class TrAddonConfigs {
   protected static final List<ResourceLocation> races = List.of();
   protected static final List<ResourceLocation> skills = List.of();
   protected static final List<String> addRaces = List.of("traddon:shardling");
   protected static final List<String> addSkills = List.of("traddon:total_reflection", "traddon:devastator");

   public static void init() {
      ConfigRegistry.registerConfig(new TrAddonSkillConfig());
      ConfigRegistry.registerConfig(new ShardlingConfig());
      ConfigRegistry.registerConfig(new TrAddonGeneral());
   }

   public static void addToConfig() {
      if (shouldAddToConfig()) {
         boolean changesMade = false;
         ReincarnationConfig reincarnation = (ReincarnationConfig)ConfigRegistry.getConfig(ReincarnationConfig.class);
         UniqueSkillConfig unique = (UniqueSkillConfig)ConfigRegistry.getConfig(UniqueSkillConfig.class);
         ArrayList<String> startingSkills = new ArrayList<>(reincarnation.Skills.startingSkills);
         ArrayList<String> startingRaces = new ArrayList<>(reincarnation.Races.startingRaces);
         ArrayList<String> randomRaces = new ArrayList<>(reincarnation.Races.randomRaces);
         ArrayList<String> creatorSkills = new ArrayList<>(unique.Creator.uniqueSkills);
         ArrayList<String> reincarnationRaces = new ArrayList<>(reincarnation.Races.reincarnationRaces);
         LinkedHashSet<String> startSkillSet = new LinkedHashSet<>(startingSkills);
         LinkedHashSet<String> startRaceSet = new LinkedHashSet<>(startingRaces);
         LinkedHashSet<String> randomRaceSet = new LinkedHashSet<>(randomRaces);
         LinkedHashSet<String> creatorSet = new LinkedHashSet<>(creatorSkills);
         LinkedHashSet<String> reincarnationRaceSet = new LinkedHashSet<>(reincarnationRaces);

         for (String skillRegistry : addSkills) {
            if (startSkillSet.add(skillRegistry)) {
               changesMade = true;
            }

            if (creatorSet.add(skillRegistry)) {
               changesMade = true;
            }
         }

         for (String raceRegistry : addRaces) {
            if (startRaceSet.add(raceRegistry)) {
               changesMade = true;
            }

            if (randomRaceSet.add(raceRegistry)) {
               changesMade = true;
            }

            if (reincarnationRaceSet.add(raceRegistry)) {
               changesMade = true;
            }
         }

         reincarnation.Skills.startingSkills = new ArrayList<>(startSkillSet);
         reincarnation.Races.startingRaces = new ArrayList<>(startRaceSet);
         reincarnation.Races.randomRaces = new ArrayList<>(randomRaceSet);
         reincarnation.Races.reincarnationRaces = new ArrayList<>(reincarnationRaceSet);
         unique.Creator.uniqueSkills = new ArrayList<>(creatorSet);
         if (changesMade) {
            ConfigRegistry.saveAllConfigs();
         }

         ((TrAddonGeneral)ConfigRegistry.getConfig(TrAddonGeneral.class)).General.refresh = false;
         ConfigRegistry.saveAllConfigs();
      }
   }

   public static boolean shouldAddToConfig() {
      return ((TrAddonGeneral)ConfigRegistry.getConfig(TrAddonGeneral.class)).General.refresh;
   }
}
