package org.crypticdev.stextras.race.mystic_fox;

import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import java.util.List;
import java.util.Map;
import net.minecraft.world.entity.LivingEntity;
import org.crypticdev.stextras.registary.race.STExtrasRaces;

public class DivineFoxRace extends NinetailRace {
   public DivineFoxRace(Difficulty difficulty) {
      super(difficulty);
   }

   public DivineFoxRace() {
      this(Difficulty.EASY);
      this.applyDefaultAttributeModifiers();
   }

   @Override
   public Default getDefaultConfig() {
      return this.getMysticFoxConfig().DivineFox;
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
      return List.of((ManasRace)STExtrasRaces.NINETAIL.get());
   }

   @Override
   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance instance, LivingEntity entity) {
      return this.epRequirement(this.getMysticFoxConfig().DivineFox.getEpRequirement());
   }
}
