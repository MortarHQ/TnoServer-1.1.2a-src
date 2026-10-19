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
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;

public class BabySpiritFoxRace extends DefaultRace {
   private static final FoxSpiritRaceConfig.BabySpiritFox CONFIG = new FoxSpiritRaceConfig.BabySpiritFox();

   public BabySpiritFoxRace() {
      super(Difficulty.EXTREME);
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
      return list;
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)TensuraTNORaces.FOX_SPIRIT_ENVOY.get());
      return list;
   }

   @Nullable
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)TensuraTNORaces.FOX_SPIRIT_ENVOY.get();
   }

   @Nullable
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   @Nullable
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }
}
