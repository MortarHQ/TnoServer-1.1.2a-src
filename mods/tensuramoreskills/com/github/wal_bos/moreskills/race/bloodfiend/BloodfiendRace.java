package com.github.wal_bos.moreskills.race.bloodfiend;

import com.github.wal_bos.moreskills.configs.MoreskillsRaceConfig;
import com.github.wal_bos.moreskills.registry.race.MoreskillsRaces;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class BloodfiendRace extends AbstractBloodfiendRace {
   public BloodfiendRace(Difficulty difficulty) {
      super(difficulty);
   }

   public BloodfiendRace() {
      this(Difficulty.INTERMEDIATE);
   }

   @Override
   protected MoreskillsRaceConfig.BloodfiendStats stats() {
      return this.config().Bloodfiend;
   }

   @Nullable
   @Override
   protected ManasRace nextRace() {
      return (ManasRace)MoreskillsRaces.ELDER_BLOODFIEND.get();
   }

   @Override
   protected List<ManasRace> previousRaces() {
      return List.of();
   }

   @Override
   protected MoreskillsRaceConfig.BloodfiendStats nextStats() {
      return this.config().ElderBloodfiend;
   }
}
