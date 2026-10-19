package com.tensura_tno.race.fox_spirit;

import com.tensura_tno.config.race.FoxSpiritRaceConfig;
import com.tensura_tno.registry.TensuraTNOSkills;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.DefaultRace;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.AwakenRequirement;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.world.entity.LivingEntity;

public class HeavenlyFoxSovereignRace extends DefaultRace {
   private static final FoxSpiritRaceConfig.HeavenlyFoxSovereign CONFIG = new FoxSpiritRaceConfig.HeavenlyFoxSovereign();

   public HeavenlyFoxSovereignRace() {
      super(Difficulty.HARD);
      this.applyDefaultAttributeModifiers();
   }

   public Default getDefaultConfig() {
      return CONFIG;
   }

   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasSkill> list = new ArrayList<>();
      list.add((ManasSkill)ExtraSkills.MAGIC_SENSE.get());
      list.add((ManasSkill)TensuraTNOSkills.CONTRACT_LITTLE_FOX.get());
      list.add((ManasSkill)TensuraTNOSkills.SPIRIT_SUMMON.get());
      list.add((ManasSkill)TensuraTNOSkills.SPIRIT_ENHANCEMENT.get());
      return list;
   }

   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      Map<EvolutionRequirement, Float> map = new LinkedHashMap<>();
      map.put(new AwakenRequirement(), 100.0F);
      map.put(new SummonMaxEPRequirement(120000.0), 100.0F);
      return map;
   }
}
