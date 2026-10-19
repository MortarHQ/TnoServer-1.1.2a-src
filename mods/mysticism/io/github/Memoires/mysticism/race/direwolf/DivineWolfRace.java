package io.github.Memoires.mysticism.race.direwolf;

import io.github.Memoires.mysticism.config.race.direwolf.DirewolfConfig;
import io.github.Memoires.mysticism.race.MysticismRaceUtils;
import io.github.Memoires.mysticism.race.direwolf.black.MagicSpiritWolfRace;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.jetbrains.annotations.Nullable;

public class DivineWolfRace extends MagicSpiritWolfRace {
   protected static final DirewolfConfig.DivineWolf CONFIG = ((DirewolfConfig)ConfigRegistry.getConfig(DirewolfConfig.class)).DivineWolf;

   public DivineWolfRace() {
      this.addAttributeModifier(Attributes.STEP_HEIGHT, DEFAULT_RACE_ID, CONFIG.stepHeight, Operation.ADD_VALUE);
   }

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Nullable
   @Override
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   @Nullable
   @Override
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return new ArrayList<>();
   }

   @Override
   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.MAGIC_SPIRIT_WOLF.get());
      list.add((ManasRace)MysticismRaces.OCEAN_SPIRIT_WOLF.get());
      list.add((ManasRace)MysticismRaces.MOUNTAIN_SPIRIT_WOLF.get());
      list.add((ManasRace)MysticismRaces.PENUMBRA_SPIRIT_WOLF.get());
      list.add((ManasRace)MysticismRaces.GALE_SPIRIT_WOLF.get());
      list.add((ManasRace)MysticismRaces.STRING_SPIRIT_WOLF.get());
      list.add((ManasRace)MysticismRaces.ANTUMBRA_SPIRIT_WOLF.get());
      list.add((ManasRace)MysticismRaces.PLANET_SPIRIT_WOLF.get());
      list.add((ManasRace)MysticismRaces.VOLCANIC_SPIRIT_WOLF.get());
      list.add((ManasRace)MysticismRaces.STORM_SPIRIT_WOLF.get());
      return list;
   }

   @Override
   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of(new EPRequirement(CONFIG.epRequirement), 100.0F);
   }

   @Override
   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      return MysticismRaceUtils.parseResourceIntoSkills(CONFIG.intrinsicSkills);
   }
}
