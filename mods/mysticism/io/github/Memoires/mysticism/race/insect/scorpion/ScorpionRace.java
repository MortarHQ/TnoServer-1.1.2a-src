package io.github.Memoires.mysticism.race.insect.scorpion;

import io.github.Memoires.mysticism.config.race.insect.ScorpionConfig;
import io.github.Memoires.mysticism.race.MysticismRaceUtils;
import io.github.Memoires.mysticism.race.insect.InsectRace;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.storage.Alignment;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class ScorpionRace extends InsectRace {
   protected static final ScorpionConfig.Scorpion CONFIG = ((ScorpionConfig)ConfigRegistry.getConfig(ScorpionConfig.class)).Scorpion;

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Nullable
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.LOXODROME_SCORPION.get();
   }

   @Nullable
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.POISON_SOUL_INSECT.get();
   }

   @Nullable
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.LOXODROME_SCORPION.get();
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.LOXODROME_SCORPION.get());
      list.add((ManasRace)MysticismRaces.SINGULARITY_SCORPION.get());
      return list;
   }

   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.INSECT.get());
      return list;
   }

   @Override
   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      return MysticismRaceUtils.parseResourceIntoSkills(CONFIG.intrinsicSkills);
   }

   @Override
   public Alignment getAlignment() {
      return Alignment.MAJIN;
   }
}
