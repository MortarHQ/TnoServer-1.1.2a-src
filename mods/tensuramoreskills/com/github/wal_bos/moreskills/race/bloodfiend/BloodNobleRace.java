package com.github.wal_bos.moreskills.race.bloodfiend;

import com.github.wal_bos.moreskills.configs.MoreskillsRaceConfig;
import com.github.wal_bos.moreskills.registry.race.MoreskillsRaces;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class BloodNobleRace extends AbstractBloodfiendRace {
   public BloodNobleRace(Difficulty difficulty) {
      super(difficulty);
   }

   public BloodNobleRace() {
      this(Difficulty.HARD);
   }

   @Override
   protected MoreskillsRaceConfig.BloodfiendStats stats() {
      return this.config().BloodNoble;
   }

   @Nullable
   @Override
   protected ManasRace nextRace() {
      return (ManasRace)MoreskillsRaces.BLOOD_MONARCH.get();
   }

   @Override
   protected List<ManasRace> previousRaces() {
      return List.of((ManasRace)MoreskillsRaces.ELDER_BLOODFIEND.get());
   }

   @Override
   protected MoreskillsRaceConfig.BloodfiendStats nextStats() {
      return this.config().BloodMonarch;
   }
}
