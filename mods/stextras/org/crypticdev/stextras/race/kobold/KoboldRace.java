package org.crypticdev.stextras.race.kobold;

import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.DefaultRace;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import java.util.List;
import java.util.Map;
import net.minecraft.world.entity.LivingEntity;
import org.crypticdev.stextras.config.race.KoboldConfig;
import org.crypticdev.stextras.registary.race.STExtrasRaces;

public class KoboldRace extends DefaultRace {
   public KoboldRace(Difficulty difficulty) {
      super(difficulty);
   }

   public KoboldRace() {
      this(Difficulty.INTERMEDIATE);
      this.applyDefaultAttributeModifiers();
   }

   public Default getDefaultConfig() {
      return this.getKoboldConfig().Kobold;
   }

   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      return List.of((ManasSkill)ExtraSkills.SAGE.get());
   }

   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)STExtrasRaces.KOBOLD_TRADER.get();
   }

   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)STExtrasRaces.KOBOLD_CHIEFTAIN.get();
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return List.of((ManasRace)STExtrasRaces.KOBOLD_TRADER.get());
   }

   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance instance, LivingEntity entity) {
      return Map.of();
   }

   protected KoboldConfig getKoboldConfig() {
      return (KoboldConfig)ConfigRegistry.getConfig(KoboldConfig.class);
   }

   protected Map<EvolutionRequirement, Float> epRequirement(double requirement) {
      return requirement <= 0.0 ? Map.of() : Map.of(new EPRequirement(requirement), 100.0F);
   }
}
