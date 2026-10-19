package com.github.wal_bos.moreskills.race.bloodfiend;

import com.github.wal_bos.moreskills.configs.MoreskillsRaceConfig;
import com.github.wal_bos.moreskills.handler.BloodfiendData;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import com.mojang.datafixers.util.Pair;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.DefaultRace;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.BossRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import io.github.manasmods.tensura.storage.Alignment;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractBloodfiendRace extends DefaultRace {
   protected AbstractBloodfiendRace(Difficulty difficulty) {
      super(difficulty);
      this.applyDefaultAttributeModifiers();
   }

   protected MoreskillsRaceConfig.Config config() {
      return MoreskillsRaceConfig.get();
   }

   protected abstract MoreskillsRaceConfig.BloodfiendStats stats();

   @Nullable
   protected abstract ManasRace nextRace();

   protected abstract List<ManasRace> previousRaces();

   protected abstract MoreskillsRaceConfig.BloodfiendStats nextStats();

   public Default getDefaultConfig() {
      return this.stats();
   }

   public Pair<Double, Double> getBaseAuraRange() {
      MoreskillsRaceConfig.BloodfiendStats stats = this.stats();
      return Pair.of(stats.getMinAura(), stats.getMaxAura());
   }

   public Pair<Double, Double> getBaseMagiculeRange() {
      MoreskillsRaceConfig.BloodfiendStats stats = this.stats();
      return Pair.of(stats.getMinMagicule(), stats.getMaxMagicule());
   }

   public Alignment getAlignment() {
      return Alignment.MAJIN;
   }

   @Nullable
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return this.nextRace();
   }

   @Nullable
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return this.nextRace();
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      ManasRace next = this.nextRace();
      return next == null ? List.of() : List.of(next);
   }

   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return this.previousRaces();
   }

   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      MoreskillsRaceConfig.BloodfiendStats stats = this.stats();
      Map<EvolutionRequirement, Float> raw = new LinkedHashMap<>();
      if ((Double)stats.epRequirement.get() > 0.0) {
         raw.put(new EPRequirement((Double)stats.epRequirement.get()), 45.0F);
      }

      if ((Integer)stats.bossRequirement.get() > 0) {
         raw.put(new BossRequirement((Integer)stats.bossRequirement.get()), 20.0F);
      }

      if ((Integer)stats.bloodRequirement.get() > 0) {
         raw.put(new AbstractBloodfiendRace.BloodRequirement((Integer)stats.bloodRequirement.get()), 20.0F);
      }

      if ((Integer)stats.mobTurnRequirement.get() > 0) {
         raw.put(new AbstractBloodfiendRace.ThrallRequirement((Integer)stats.mobTurnRequirement.get()), 15.0F);
      }

      if (raw.isEmpty()) {
         return raw;
      }

      float total = 0.0F;

      for (Float value : raw.values()) {
         total += value;
      }

      if (total <= 0.0F) {
         return Map.of();
      }

      Map<EvolutionRequirement, Float> normalized = new LinkedHashMap<>();
      float assigned = 0.0F;
      int index = 0;
      int size = raw.size();

      for (Entry<EvolutionRequirement, Float> entry : raw.entrySet()) {
         float weight;
         if (++index == size) {
            weight = Math.max(0.0F, 100.0F - assigned);
         } else {
            float weightx = entry.getValue() * 100.0F / total;
            weight = Math.max(0.0F, Math.min(100.0F - assigned, weightx));
            assigned += weight;
         }

         normalized.put(entry.getKey(), weight);
      }

      return normalized;
   }

   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasSkill> list = new ArrayList<>();
      list.add((ManasSkill)AllSkills.BLOOD_ARTS.get());
      return list;
   }

   public static final class BloodRequirement extends EvolutionRequirement {
      private final int amount;

      public BloodRequirement(int amount) {
         this.amount = amount;
      }

      public float getProgress(ManasRaceInstance instance, LivingEntity entity) {
         return this.amount <= 0 ? 1.0F : Math.min(1.0F, (float)BloodfiendData.getTotalBloodDrained(entity) / this.amount);
      }

      public Component getRequirementComponent(ManasRaceInstance instance, LivingEntity entity) {
         return Component.literal("Drain " + this.amount + " blood");
      }
   }

   public static final class ThrallRequirement extends EvolutionRequirement {
      private final int amount;

      public ThrallRequirement(int amount) {
         this.amount = amount;
      }

      public float getProgress(ManasRaceInstance instance, LivingEntity entity) {
         return this.amount <= 0 ? 1.0F : Math.min(1.0F, (float)BloodfiendData.getTotalThrallsTurned(entity) / this.amount);
      }

      public Component getRequirementComponent(ManasRaceInstance instance, LivingEntity entity) {
         return Component.literal("Turn " + this.amount + " kindred with Kindred Rite");
      }
   }
}
