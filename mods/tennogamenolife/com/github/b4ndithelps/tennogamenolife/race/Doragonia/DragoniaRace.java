package com.github.b4ndithelps.tennogamenolife.race.Doragonia;

import com.github.b4ndithelps.tennogamenolife.config.race.DragoniaRaceConfig;
import com.github.b4ndithelps.tennogamenolife.registry.race.Tenreincarnationraces;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.DefaultRace;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;

public class DragoniaRace extends DefaultRace {
   private static final DragoniaRaceConfig.Dragonia CONFIG = new DragoniaRaceConfig.Dragonia();

   public DragoniaRace() {
      super(Difficulty.EASY);
      this.applyDefaultAttributeModifiers();
   }

   public Default getDefaultConfig() {
      return CONFIG;
   }

   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasSkill> list = new ArrayList<>();
      list.add((ManasSkill)IntrinsicSkills.DRAGON_EAR.get());
      list.add((ManasSkill)IntrinsicSkills.DRAGON_EYE.get());
      list.add((ManasSkill)IntrinsicSkills.DRAGON_MODE.get());
      list.add((ManasSkill)IntrinsicSkills.DRAGON_SKIN.get());
      return list;
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)Tenreincarnationraces.LESSER_DRAGONIA.get());
      return list;
   }

   @Nullable
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)Tenreincarnationraces.LESSER_DRAGONIA.get();
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
