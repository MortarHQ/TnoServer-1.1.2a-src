package com.shin.tensura_kumodesu.races.ogre;

import com.shin.tensura_kumodesu.config.race.OgreConfig;
import com.shin.tensura_kumodesu.registry.race.KumodesuRaces;
import com.shin.tensura_kumodesu.registry.skill.KumoUniqueSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.AbilityRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.storage.Alignment;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class WrathOniRace extends OgreKingRace {
   public static final ResourceLocation WrathOni = ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "wrath_oni");
   protected static final OgreConfig.WrathOni CONFIG = ((OgreConfig)ConfigRegistry.getConfig(OgreConfig.class)).WrathOni;

   @Override
   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)KumodesuRaces.OGRE_KING.get());
      return list;
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return List.of();
   }

   @Override
   public Alignment getAlignment() {
      return Alignment.MAJIN;
   }

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Override
   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasSkill> list = new ArrayList<>();
      list.add((ManasSkill)IntrinsicSkills.DIVINE_KI_RELEASE.get());
      list.add((ManasSkill)KumoUniqueSkills.WEAPONCREATION.get());
      return list;
   }

   @Override
   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of(new EPRequirement(CONFIG.epRequirement), 50.0F, new AbilityRequirement((ManasSkill)UniqueSkills.WRATH.get(), false), 50.0F);
   }
}
