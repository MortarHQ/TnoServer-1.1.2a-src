package com.github.wal_bos.moreskills.race.bloodfiend;

import com.github.wal_bos.moreskills.configs.MoreskillsRaceConfig;
import com.github.wal_bos.moreskills.registry.race.MoreskillsRaces;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class ElderBloodfiendRace extends AbstractBloodfiendRace {
   public ElderBloodfiendRace(Difficulty difficulty) {
      super(difficulty);
   }

   public ElderBloodfiendRace() {
      this(Difficulty.INTERMEDIATE);
   }

   @Override
   protected MoreskillsRaceConfig.BloodfiendStats stats() {
      return this.config().ElderBloodfiend;
   }

   @Nullable
   @Override
   protected ManasRace nextRace() {
      return (ManasRace)MoreskillsRaces.BLOOD_NOBLE.get();
   }

   @Override
   protected List<ManasRace> previousRaces() {
      return List.of((ManasRace)MoreskillsRaces.BLOODFIEND.get());
   }

   @Override
   protected MoreskillsRaceConfig.BloodfiendStats nextStats() {
      return this.config().BloodNoble;
   }
}
