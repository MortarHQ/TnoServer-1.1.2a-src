package com.github.wal_bos.moreskills.race.bloodfiend;

import com.github.wal_bos.moreskills.configs.MoreskillsRaceConfig;
import com.github.wal_bos.moreskills.registry.race.MoreskillsRaces;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class CrimsonProgenitorRace extends AbstractBloodfiendRace {
   public CrimsonProgenitorRace(Difficulty difficulty) {
      super(difficulty);
   }

   public CrimsonProgenitorRace() {
      this(Difficulty.HARD);
   }

   @Override
   protected MoreskillsRaceConfig.BloodfiendStats stats() {
      return this.config().CrimsonProgenitor;
   }

   @Nullable
   @Override
   protected ManasRace nextRace() {
      return null;
   }

   @Override
   protected List<ManasRace> previousRaces() {
      return List.of((ManasRace)MoreskillsRaces.BLOOD_MONARCH.get());
   }

   @Override
   protected MoreskillsRaceConfig.BloodfiendStats nextStats() {
      return null;
   }

   @Override
   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasSkill> list = new ArrayList<>(super.getIntrinsicSkills(instance, entity));
      list.add((ManasSkill)AllSkills.BLACK_COMMUNION.get());
      return list;
   }
}
