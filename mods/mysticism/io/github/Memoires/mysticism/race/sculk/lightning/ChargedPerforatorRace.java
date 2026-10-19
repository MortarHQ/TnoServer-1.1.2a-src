package io.github.Memoires.mysticism.race.sculk.lightning;

import io.github.Memoires.mysticism.config.race.SculkConfig;
import io.github.Memoires.mysticism.race.MysticismRaceUtils;
import io.github.Memoires.mysticism.race.sculk.SculkWormRace;
import io.github.Memoires.mysticism.registry.item.MysticismMobDropItems;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.ItemConsumeRequirement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public class ChargedPerforatorRace extends SculkWormRace {
   protected static final SculkConfig.ChargedPerforator CONFIG = ((SculkConfig)ConfigRegistry.getConfig(SculkConfig.class)).ChargedPerforator;

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Nullable
   @Override
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.OVERLOADING_WORM.get();
   }

   @Nullable
   @Override
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.LIGHTNING_ABERRATION.get();
   }

   @Nullable
   @Override
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.OVERLOADING_WORM.get();
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.OVERLOADING_WORM.get());
      return list;
   }

   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.SCULK_WORM.get());
      return list;
   }

   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of(
         new EPRequirement(CONFIG.epRequirement),
         50.0F,
         new ItemConsumeRequirement((Item)MysticismMobDropItems.LIGHTNING_ESSENCE.get(), CONFIG.essenceRequired),
         50.0F
      );
   }

   @Override
   public boolean canTick(ManasRaceInstance instance, LivingEntity entity) {
      return false;
   }

   @Override
   public void onTick(ManasRaceInstance instance, LivingEntity entity) {
   }

   @Override
   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      return MysticismRaceUtils.parseResourceIntoSkills(CONFIG.intrinsicSkills);
   }
}
