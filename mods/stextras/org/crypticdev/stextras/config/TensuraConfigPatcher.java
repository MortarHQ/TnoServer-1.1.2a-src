package org.crypticdev.stextras.config;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.LifecycleEvent.ServerState;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.tensura.config.ReincarnationConfig;
import io.github.manasmods.tensura.config.ReincarnationConfig.Races;
import io.github.manasmods.tensura.config.ReincarnationConfig.Skills;
import io.github.manasmods.tensura.config.ability.skill.UniqueSkillConfig;
import io.github.manasmods.tensura.config.ability.skill.UniqueSkillConfig.Creator;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Consumer;
import org.crypticdev.stextras.Stextras;

public final class TensuraConfigPatcher {
   private static final List<String> STARTING_RACES = List.of("stextras:kobold", "stextras:wispfolk", "stextras:mystic_fox");
   private static final List<String> RANDOM_RACES = List.of("stextras:kobold", "stextras:wispfolk", "stextras:mystic_fox");
   private static final List<String> STARTING_SKILLS = List.of("stextras:imitator");
   private static final List<String> CREATOR_UNIQUE_SKILLS = List.of();

   public static void init() {
      LifecycleEvent.SERVER_STARTING.register((ServerState)server -> patchLiveConfigs());
   }

   private static void patchLiveConfigs() {
      TensuraConfigPatcherStateConfig stateConfig = (TensuraConfigPatcherStateConfig)ConfigRegistry.getConfig(TensuraConfigPatcherStateConfig.class);
      ReincarnationConfig reincarnationConfig = (ReincarnationConfig)ConfigRegistry.getConfig(ReincarnationConfig.class);
      UniqueSkillConfig uniqueSkillConfig = (UniqueSkillConfig)ConfigRegistry.getConfig(UniqueSkillConfig.class);
      if (stateConfig == null) {
         Stextras.LOGGER.warn("[ConfigPatcher] Missing STExtras patcher state config, skipping Tensura patch.");
      } else if (reincarnationConfig != null && uniqueSkillConfig != null) {
         ensureSections(stateConfig, reincarnationConfig, uniqueSkillConfig);
         boolean configChanged = false;
         boolean stateChanged = false;
         TensuraConfigPatcher.MergeResult result = merge(
            "startingRaces",
            STARTING_RACES,
            stateConfig.startingRaces,
            reincarnationConfig.Races.startingRaces,
            value -> reincarnationConfig.Races.startingRaces = value
         );
         configChanged |= result.configChanged();
         stateChanged |= result.stateChanged();
         result = merge(
            "randomRaces", RANDOM_RACES, stateConfig.randomRaces, reincarnationConfig.Races.randomRaces, value -> reincarnationConfig.Races.randomRaces = value
         );
         configChanged |= result.configChanged();
         stateChanged |= result.stateChanged();
         result = merge(
            "startingSkills",
            STARTING_SKILLS,
            stateConfig.startingSkills,
            reincarnationConfig.Skills.startingSkills,
            value -> reincarnationConfig.Skills.startingSkills = value
         );
         configChanged |= result.configChanged();
         stateChanged |= result.stateChanged();
         result = merge(
            "creatorUniqueSkills",
            CREATOR_UNIQUE_SKILLS,
            stateConfig.creatorUniqueSkills,
            uniqueSkillConfig.Creator.uniqueSkills,
            value -> uniqueSkillConfig.Creator.uniqueSkills = value
         );
         configChanged |= result.configChanged();
         stateChanged |= result.stateChanged();
         if (configChanged || stateChanged) {
            ConfigRegistry.saveAllConfigs();
            Stextras.LOGGER.info("[ConfigPatcher] Tensura config patch state updated.");
         }
      } else {
         Stextras.LOGGER.warn("[ConfigPatcher] Missing Tensura config instances, skipping patch.");
      }
   }

   private static void ensureSections(TensuraConfigPatcherStateConfig stateConfig, ReincarnationConfig reincarnationConfig, UniqueSkillConfig uniqueSkillConfig) {
      if (stateConfig.startingRaces == null) {
         stateConfig.startingRaces = new TensuraConfigPatcherStateConfig.EntryState();
      }

      if (stateConfig.randomRaces == null) {
         stateConfig.randomRaces = new TensuraConfigPatcherStateConfig.EntryState();
      }

      if (stateConfig.startingSkills == null) {
         stateConfig.startingSkills = new TensuraConfigPatcherStateConfig.EntryState();
      }

      if (stateConfig.creatorUniqueSkills == null) {
         stateConfig.creatorUniqueSkills = new TensuraConfigPatcherStateConfig.EntryState();
      }

      if (reincarnationConfig.Races == null) {
         reincarnationConfig.Races = new Races();
      }

      if (reincarnationConfig.Skills == null) {
         reincarnationConfig.Skills = new Skills();
      }

      if (uniqueSkillConfig.Creator == null) {
         uniqueSkillConfig.Creator = new Creator();
      }
   }

   private static TensuraConfigPatcher.MergeResult merge(
      String key, List<String> desiredRaw, TensuraConfigPatcherStateConfig.EntryState state, List<String> currentRaw, Consumer<List<String>> targetSetter
   ) {
      LinkedHashSet<String> desired = normalize(desiredRaw);
      LinkedHashSet<String> current = normalize(currentRaw);
      LinkedHashSet<String> applied = normalize(state.applied);
      LinkedHashSet<String> optOut = normalize(state.optOut);
      boolean configChanged = false;
      boolean stateChanged = false;
      if (applied.retainAll(desired)) {
         stateChanged = true;
      }

      if (optOut.retainAll(desired)) {
         stateChanged = true;
      }

      for (String id : desired) {
         if (current.contains(id) && !optOut.contains(id) && applied.add(id)) {
            stateChanged = true;
         }
      }

      LinkedHashSet<String> removed = new LinkedHashSet<>();

      for (String id : applied) {
         if (!current.contains(id)) {
            removed.add(id);
         }
      }

      if (!removed.isEmpty() && optOut.addAll(removed)) {
         stateChanged = true;
         Stextras.LOGGER.info("[ConfigPatcher] {} opted out by manual removal: {}", key, removed);
      }

      LinkedHashSet<String> reEnabled = new LinkedHashSet<>();

      for (String id : optOut) {
         if (current.contains(id)) {
            reEnabled.add(id);
         }
      }

      if (!reEnabled.isEmpty()) {
         optOut.removeAll(reEnabled);
         stateChanged = true;
         Stextras.LOGGER.info("[ConfigPatcher] {} re-enabled manually: {}", key, reEnabled);
      }

      LinkedHashSet<String> merged = new LinkedHashSet<>(current);
      List<String> toAdd = new ArrayList<>();

      for (String id : desired) {
         if (!merged.contains(id) && !optOut.contains(id)) {
            merged.add(id);
            toAdd.add(id);
         }
      }

      if (!toAdd.isEmpty()) {
         targetSetter.accept(new ArrayList<>(merged));
         applied.addAll(toAdd);
         configChanged = true;
         stateChanged = true;
         Stextras.LOGGER.info("[ConfigPatcher] Added {} to {}.", toAdd, key);
      }

      if (storeStateList(state.applied, applied, value -> state.applied = value)) {
         stateChanged = true;
      }

      if (storeStateList(state.optOut, optOut, value -> state.optOut = value)) {
         stateChanged = true;
      }

      return new TensuraConfigPatcher.MergeResult(configChanged, stateChanged);
   }

   private static boolean storeStateList(List<String> currentRaw, LinkedHashSet<String> updated, Consumer<List<String>> setter) {
      List<String> current = new ArrayList<>(normalize(currentRaw));
      List<String> next = new ArrayList<>(updated);
      if (current.equals(next)) {
         return false;
      }

      setter.accept(next);
      return true;
   }

   private static LinkedHashSet<String> normalize(List<String> raw) {
      LinkedHashSet<String> normalized = new LinkedHashSet<>();
      if (raw == null) {
         return normalized;
      }

      for (String value : raw) {
         if (value != null) {
            String trimmed = value.trim();
            if (!trimmed.isEmpty()) {
               normalized.add(trimmed);
            }
         }
      }

      return normalized;
   }

   private TensuraConfigPatcher() {
   }

   private record MergeResult(boolean configChanged, boolean stateChanged) {
   }
}
