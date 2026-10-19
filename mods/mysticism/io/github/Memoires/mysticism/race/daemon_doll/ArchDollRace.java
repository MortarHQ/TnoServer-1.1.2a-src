package io.github.Memoires.mysticism.race.daemon_doll;

import io.github.Memoires.mysticism.config.race.DaemonDollConfig;
import io.github.Memoires.mysticism.race.template.MysticismEvolutionRequirement;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.config.race.DaemonConfig;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import io.github.manasmods.tensura.registry.race.TensuraRaces;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class ArchDollRace extends GreaterDollRace {
   protected static final DaemonDollConfig.ArchDoll CONFIG = ((DaemonDollConfig)ConfigRegistry.getConfig(DaemonDollConfig.class)).ArchDoll;

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Nullable
   @Override
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.DAEMON_DOLL.get();
   }

   @Nullable
   @Override
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.DAEMON_DOLL.get();
   }

   @Nullable
   @Override
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.DAEMON_DOLL.get());
      list.add((ManasRace)MysticismRaces.CHAOS_DOLL.get());
      return list;
   }

   public List<TensuraSkill> getIntrinsicLearnable(ManasRaceInstance instance, LivingEntity entity) {
      return ((DaemonConfig)ConfigRegistry.getConfig(DaemonConfig.class))
         .ArchDaemon
         .learnableMagics
         .stream()
         .map(id -> (ManasSkill)SkillAPI.getSkillRegistry().get(ResourceLocation.parse(id)))
         .filter(skill -> skill instanceof TensuraSkill)
         .map(skill -> (TensuraSkill)skill)
         .toList();
   }

   @Override
   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return List.of((ManasRace)MysticismRaces.GREATER_DOLL.get());
   }

   @Override
   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return previous.getRace() == TensuraRaces.ARCH_DAEMON.get()
         ? Map.of(new MysticismEvolutionRequirement.PossessGolemRequirement(), 100.0F)
         : Map.of(new EPRequirement(CONFIG.epRequirement), 100.0F);
   }
}
