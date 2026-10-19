package com.github.dominickwd04.traddon.race.lithoid;

import com.github.dominickwd04.traddon.config.race.ShardlingConfig;
import com.github.dominickwd04.traddon.registry.race.TrAddonRaces;
import com.github.dominickwd04.traddon.registry.skill.TrAddonIntrinsicSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.ManasRace.Difficulty;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.DefaultRace;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.storage.Alignment;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.LivingEntity;

public class ShardlingRace extends DefaultRace {
   protected static final ShardlingConfig.Shardling CONFIG = ((ShardlingConfig)ConfigRegistry.getConfig(ShardlingConfig.class)).Shardling;

   public ShardlingRace(Difficulty difficulty) {
      super(difficulty);
   }

   public ShardlingRace() {
      this(Difficulty.EASY);
      this.applyDefaultAttributeModifiers();
   }

   public Default getDefaultConfig() {
      return CONFIG;
   }

   public Alignment getAlignment() {
      return Alignment.MAJIN;
   }

   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)TrAddonRaces.GEODE_GOLEM.get();
   }

   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)TrAddonRaces.PRISM_KNIGHT.get();
   }

   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)TrAddonRaces.GEODE_GOLEM.get();
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return List.of((ManasRace)TrAddonRaces.GEODE_GOLEM.get());
   }

   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasSkill> list = new ArrayList<>();
      list.add((ManasSkill)ResistanceSkills.PHYSICAL_ATTACK_RESISTANCE.get());
      list.add((ManasSkill)TrAddonIntrinsicSkills.CRYSTAL_DUST.get());
      return list;
   }
}
