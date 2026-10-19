package com.shin.tensura_kumodesu.races.ogre;

import com.shin.tensura_kumodesu.config.race.OgreConfig;
import com.shin.tensura_kumodesu.registry.race.KumodesuRaces;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.registry.race.TensuraRaces;
import io.github.manasmods.tensura.registry.skill.CommonSkills;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class OgreKingRace extends HighOgreRace {
   public static final ResourceLocation OgreKing = ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "ogre_king");
   protected static final OgreConfig.OgreKing CONFIG = ((OgreConfig)ConfigRegistry.getConfig(OgreConfig.class)).OgreKing;

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

   @Nullable
   @Override
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)KumodesuRaces.WRATH_ONI.get());
      list.add((ManasRace)TensuraRaces.DIVINE_FIGHTER.get());
      return list;
   }

   @Override
   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)KumodesuRaces.HIGH_OGRE.get());
      return list;
   }

   @Override
   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasSkill> list = new ArrayList<>();
      list.add((ManasSkill)CommonSkills.STRENGTH.get());
      return list;
   }
}
