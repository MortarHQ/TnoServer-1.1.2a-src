package com.tensura_tno.race.fox_spirit;

import com.tensura_tno.config.race.FoxSpiritRaceConfig;
import com.tensura_tno.registry.TensuraTNORaces;
import com.tensura_tno.registry.TensuraTNOSkills;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.DefaultRace;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import io.github.manasmods.tensura.registry.magic.SummoningMagics;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;

public class MysticFoxMasterRace extends DefaultRace {
   private static final FoxSpiritRaceConfig.MysticFoxMaster CONFIG = new FoxSpiritRaceConfig.MysticFoxMaster();

   public MysticFoxMasterRace() {
      super(Difficulty.INTERMEDIATE);
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
      list.add((ManasSkill)SummoningMagics.SUMMON_HOUND_DOG.get());
      list.add((ManasSkill)SummoningMagics.SUMMON_BASILISK.get());
      list.add((ManasSkill)SummoningMagics.SUMMON_MEDIUM_ELEMENTAL.get());
      list.add((ManasSkill)SummoningMagics.SUMMON_GREATER_ELEMENTAL.get());
      list.add((ManasSkill)SummoningMagics.SUMMON_DAEMON.get());
      return list;
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)TensuraTNORaces.HEAVENLY_FOX_SOVEREIGN.get());
      return list;
   }

   @Nullable
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)TensuraTNORaces.HEAVENLY_FOX_SOVEREIGN.get();
   }

   @Nullable
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   @Nullable
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      Map<EvolutionRequirement, Float> map = new LinkedHashMap<>();
      map.put(new EPRequirement(100000.0), 50.0F);
      map.put(new AbsorbedTypesRequirement(30), 50.0F);
      return map;
   }
}
