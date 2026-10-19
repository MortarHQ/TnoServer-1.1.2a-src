package com.github.b4ndithelps.tennogamenolife.race.Dwarf;

import com.github.b4ndithelps.tennogamenolife.config.race.DwarfRaceConfig;
import com.github.b4ndithelps.tennogamenolife.registry.item.Tenreincarnationitems;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.DefaultRace;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.ItemConsumeRequirement;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class OcainRace extends DefaultRace {
   private static final DwarfRaceConfig.Ocain CONFIG = new DwarfRaceConfig.Ocain();

   public OcainRace() {
      super(Difficulty.INTERMEDIATE);
      this.applyDefaultAttributeModifiers();
   }

   public Default getDefaultConfig() {
      return CONFIG;
   }

   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasSkill> list = new ArrayList<>();
      list.add((ManasSkill)IntrinsicSkills.ABSORB_DISSOLVE.get());
      list.add((ManasSkill)ResistanceSkills.PHYSICAL_ATTACK_RESISTANCE.get());
      list.add((ManasSkill)ResistanceSkills.MAGIC_RESISTANCE.get());
      return list;
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return new ArrayList<>();
   }

   @Nullable
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
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
      return Map.of(new EPRequirement(1000000.0), 50.0F, new ItemConsumeRequirement((Item)Tenreincarnationitems.STAR_ESSENCE.get(), 1), 50.0F);
   }
}
