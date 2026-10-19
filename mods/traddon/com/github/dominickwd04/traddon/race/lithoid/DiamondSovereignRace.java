package com.github.dominickwd04.traddon.race.lithoid;

import com.github.dominickwd04.traddon.config.race.ShardlingConfig;
import com.github.dominickwd04.traddon.registry.race.TrAddonRaces;
import com.github.dominickwd04.traddon.registry.skill.TrAddonUniqueSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import java.util.List;
import java.util.Map;
import net.minecraft.world.entity.LivingEntity;

public class DiamondSovereignRace extends PrismKnightRace {
   protected static final ShardlingConfig.DiamondSovereign CONFIG = ((ShardlingConfig)ConfigRegistry.getConfig(ShardlingConfig.class)).DiamondSovereign;

   public DiamondSovereignRace(Difficulty difficulty) {
      super(difficulty);
   }

   public DiamondSovereignRace() {
      this(Difficulty.EASY);
      this.applyDefaultAttributeModifiers();
   }

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Override
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   @Override
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return List.of();
   }

   @Override
   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return List.of((ManasRace)TrAddonRaces.PRISM_KNIGHT.get());
   }

   @Override
   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of(new EPRequirement(CONFIG.epRequirement), 100.0F);
   }

   @Override
   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasSkill> list = super.getIntrinsicSkills(instance, entity);
      list.add((ManasSkill)TrAddonUniqueSkills.RESONANT_CORE.get());
      list.add((ManasSkill)IntrinsicSkills.DIVINE_KI_RELEASE.get());
      return list;
   }
}
