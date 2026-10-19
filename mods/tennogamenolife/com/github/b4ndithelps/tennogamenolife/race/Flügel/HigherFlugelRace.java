package com.github.b4ndithelps.tennogamenolife.race.Flügel;

import com.github.b4ndithelps.tennogamenolife.registry.race.Tenreincarnationraces;
import com.mojang.datafixers.util.Pair;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.race.TensuraRace;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;

public class HigherFlugelRace extends TensuraRace {
   private double auraMin = 1800.0;
   private double auraMax = 2600.0;
   private double startingMagiculeMin = 800.0;
   private double startingMagiculeMax = 1200.0;

   public HigherFlugelRace() {
      super(Difficulty.EASY);
   }

   public Pair<Double, Double> getBaseAuraRange() {
      return Pair.of(this.auraMin, this.auraMax);
   }

   public Pair<Double, Double> getBaseMagiculeRange() {
      return Pair.of(this.startingMagiculeMin, this.startingMagiculeMax);
   }

   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasSkill> list = new ArrayList<>();
      list.add((ManasSkill)ExtraSkills.HEAVENLY_EYE.get());
      list.add((ManasSkill)ExtraSkills.MAGIC_SENSE.get());
      list.add((ManasSkill)ExtraSkills.THOUGHT_ACCELERATION.get());
      list.add((ManasSkill)ResistanceSkills.PARALYSIS_NULLIFICATION.get());
      list.add((ManasSkill)ExtraSkills.WIND_MANIPULATION.get());
      return list;
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)Tenreincarnationraces.JIBRIL.get());
      return list;
   }

   @Nullable
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)Tenreincarnationraces.JIBRIL.get();
   }

   @Nullable
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)Tenreincarnationraces.JIBRIL.get();
   }

   @Nullable
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }
}
