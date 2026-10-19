package com.github.wal_bos.moreskills.race.parasite;

import com.github.wal_bos.moreskills.configs.MoreskillsRaceConfig;
import com.mojang.datafixers.util.Pair;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.DefaultRace;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.storage.Alignment;
import java.util.List;
import java.util.Map;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class ParasiteRace extends DefaultRace {
   public ParasiteRace(Difficulty difficulty) {
      super(difficulty);
      this.applyDefaultAttributeModifiers();
   }

   public ParasiteRace() {
      this(Difficulty.EASY);
   }

   public Default getDefaultConfig() {
      return MoreskillsRaceConfig.get().Parasite;
   }

   public Pair<Double, Double> getBaseAuraRange() {
      return Pair.of(MoreskillsRaceConfig.get().Parasite.getMinAura(), MoreskillsRaceConfig.get().Parasite.getMaxAura());
   }

   public Pair<Double, Double> getBaseMagiculeRange() {
      return Pair.of(MoreskillsRaceConfig.get().Parasite.getMinMagicule(), MoreskillsRaceConfig.get().Parasite.getMaxMagicule());
   }

   public Alignment getAlignment() {
      return Alignment.MAJIN;
   }

   @Nullable
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   @Nullable
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return List.of();
   }

   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return List.of();
   }

   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of();
   }

   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      return List.of();
   }
}
