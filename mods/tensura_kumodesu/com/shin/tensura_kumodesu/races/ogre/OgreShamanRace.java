package com.shin.tensura_kumodesu.races.ogre;

import com.shin.tensura_kumodesu.config.race.OgreConfig;
import com.shin.tensura_kumodesu.registry.race.KumodesuRaces;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.ogre.OgreRace;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.ItemConsumeRequirement;
import io.github.manasmods.tensura.registry.item.TensuraMobDropItems;
import io.github.manasmods.tensura.registry.race.TensuraRaces;
import io.github.manasmods.tensura.registry.skill.CommonSkills;
import io.github.manasmods.tensura.storage.Alignment;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public class OgreShamanRace extends OgreRace {
   public static final ResourceLocation OgreShaman = ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "ogre_shaman");
   protected static final OgreConfig.OgreShaman CONFIG = ((OgreConfig)ConfigRegistry.getConfig(OgreConfig.class)).OgreShaman;

   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Nullable
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)KumodesuRaces.HIGH_OGRE.get();
   }

   @Nullable
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)KumodesuRaces.OGRE_KING.get();
   }

   @Nullable
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)KumodesuRaces.HIGH_OGRE.get();
   }

   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)KumodesuRaces.HIGH_OGRE.get());
      return list;
   }

   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)TensuraRaces.OGRE.get());
      return list;
   }

   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of(
         new ItemConsumeRequirement((Item)TensuraMobDropItems.DAEMON_ESSENCE.get(), CONFIG.essenceRequirement),
         33.0F,
         new ItemConsumeRequirement((Item)TensuraMobDropItems.ELEMENTAL_ESSENCE.get(), CONFIG.essenceRequirement),
         33.0F,
         new EPRequirement(CONFIG.epRequirement),
         34.0F
      );
   }

   public Alignment getAlignment() {
      return Alignment.DEFAULT;
   }

   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasSkill> list = new ArrayList<>();
      list.add((ManasSkill)CommonSkills.STRENGTH.get());
      return list;
   }
}
