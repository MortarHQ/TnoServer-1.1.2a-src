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
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;

public class HigherDragoniaRace extends DefaultRace {
   private static final DragoniaRaceConfig.HigherDragonia CONFIG = new DragoniaRaceConfig.HigherDragonia();

   public HigherDragoniaRace() {
      super(Difficulty.EASY);
      this.applyDefaultAttributeModifiers();
   }

   public Default getDefaultConfig() {
      return CONFIG;
   }

   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasSkill> list = new ArrayList<>();
      list.add((ManasSkill)ExtraSkills.GRAVITY_MANIPULATION.get());
      return list;
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)Tenreincarnationraces.ARANREIVU.get());
      list.add((ManasRace)Tenreincarnationraces.HARTYLEIF.get());
      list.add((ManasRace)Tenreincarnationraces.REGINREIVU.get());
      return list;
   }

   @Nullable
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)Tenreincarnationraces.ARANREIVU.get();
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
      return Map.of(new EPRequirement(100000.0), 100.0F);
   }
}
