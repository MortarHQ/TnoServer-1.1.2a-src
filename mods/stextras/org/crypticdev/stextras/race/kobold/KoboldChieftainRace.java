package org.crypticdev.stextras.race.kobold;

import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import java.util.List;
import java.util.Map;
import net.minecraft.world.entity.LivingEntity;
import org.crypticdev.stextras.registary.race.STExtrasRaces;

public class KoboldChieftainRace extends KoboldTraderRace {
   public KoboldChieftainRace(Difficulty difficulty) {
      super(difficulty);
   }

   public KoboldChieftainRace() {
      this(Difficulty.INTERMEDIATE);
      this.applyDefaultAttributeModifiers();
   }

   @Override
   public Default getDefaultConfig() {
      return this.getKoboldConfig().KoboldChieftain;
   }

   @Override
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)STExtrasRaces.DIVINE_KOBOLD.get();
   }

   @Override
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)STExtrasRaces.DIVINE_KOBOLD.get();
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return List.of((ManasRace)STExtrasRaces.DIVINE_KOBOLD.get());
   }

   @Override
   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return List.of((ManasRace)STExtrasRaces.KOBOLD_WARRIOR.get());
   }

   @Override
   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance instance, LivingEntity entity) {
      return this.epRequirement(this.getKoboldConfig().KoboldChieftain.getEpRequirement());
   }
}
