package com.github.b4ndithelps.tennogamenolife.race.Doragonia;

import com.github.b4ndithelps.tennogamenolife.config.race.DragoniaRaceConfig;
import com.github.b4ndithelps.tennogamenolife.registry.race.Tenreincarnationraces;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.DefaultRace;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.BossRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;

public class MiddleDragoniaRace extends DefaultRace {
   private static final DragoniaRaceConfig.MiddleDragonia CONFIG = new DragoniaRaceConfig.MiddleDragonia();

   public MiddleDragoniaRace() {
      super(Difficulty.EASY);
      this.applyDefaultAttributeModifiers();
   }

   public Default getDefaultConfig() {
      return CONFIG;
   }

   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      return new ArrayList<>();
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)Tenreincarnationraces.HIGHER_DRAGONIA.get());
      return list;
   }

   @Nullable
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)Tenreincarnationraces.HIGHER_DRAGONIA.get();
   }

   @Nullable
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   @Nullable
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of(new EPRequirement(55000.0), 50.0F, new BossRequirement(5), 50.0F);
   }
}
