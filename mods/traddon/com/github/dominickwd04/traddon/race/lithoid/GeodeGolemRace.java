package com.github.dominickwd04.traddon.race.lithoid;

import com.github.dominickwd04.traddon.config.race.ShardlingConfig;
import com.github.dominickwd04.traddon.registry.race.TrAddonRaces;
import com.github.dominickwd04.traddon.registry.skill.TrAddonIntrinsicSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import java.util.List;
import java.util.Map;
import net.minecraft.world.entity.LivingEntity;

public class GeodeGolemRace extends ShardlingRace {
   protected static final ShardlingConfig.GeodeGolem CONFIG = ((ShardlingConfig)ConfigRegistry.getConfig(ShardlingConfig.class)).GeodeGolem;

   public GeodeGolemRace(Difficulty difficulty) {
      super(difficulty);
   }

   public GeodeGolemRace() {
      this(Difficulty.EASY);
      this.applyDefaultAttributeModifiers();
   }

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Override
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)TrAddonRaces.PRISM_KNIGHT.get();
   }

   @Override
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return List.of((ManasRace)TrAddonRaces.PRISM_KNIGHT.get());
   }

   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return List.of((ManasRace)TrAddonRaces.SHARDLING.get());
   }

   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of(new EPRequirement(CONFIG.epRequirement), 100.0F);
   }

   @Override
   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasSkill> list = super.getIntrinsicSkills(instance, entity);
      list.add((ManasSkill)TrAddonIntrinsicSkills.REFRACTIVE_SHELL.get());
      return list;
   }
}
