package io.github.Memoires.mysticism.race.daemon_doll;

import io.github.Memoires.mysticism.config.race.DaemonDollConfig;
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
import io.github.manasmods.tensura.race.template.EvolutionRequirement.AwakenRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.NamedRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.PhysicalBodyRequirement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class DaemonDollRace extends ArchDollRace {
   protected static final DaemonDollConfig.DaemonDoll CONFIG = ((DaemonDollConfig)ConfigRegistry.getConfig(DaemonDollConfig.class)).DaemonDoll;

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Nullable
   @Override
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.DEVIL_DOLL.get();
   }

   @Nullable
   @Override
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.DEVIL_DOLL.get();
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.DEVIL_DOLL.get());
      return list;
   }

   @Override
   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return List.of((ManasRace)MysticismRaces.ARCH_DOLL.get());
   }

   @Override
   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of(new AwakenRequirement(), 50.0F, new NamedRequirement(), 50.0F, new PhysicalBodyRequirement(), 50.0F);
   }

   @Override
   public List<TensuraSkill> getIntrinsicLearnable(ManasRaceInstance instance, LivingEntity entity) {
      return ((DaemonConfig)ConfigRegistry.getConfig(DaemonConfig.class))
         .DaemonLord
         .learnableMagics
         .stream()
         .map(id -> (ManasSkill)SkillAPI.getSkillRegistry().get(ResourceLocation.parse(id)))
         .filter(skill -> skill instanceof TensuraSkill)
         .map(skill -> (TensuraSkill)skill)
         .toList();
   }
}
