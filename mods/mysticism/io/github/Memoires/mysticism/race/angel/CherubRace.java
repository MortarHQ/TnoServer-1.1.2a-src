package io.github.Memoires.mysticism.race.angel;

import io.github.Memoires.mysticism.config.race.AngelConfig;
import io.github.Memoires.mysticism.race.MysticismRaceUtils;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.Memoires.mysticism.registry.skill.IntrinsicMysticismSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.AwakenRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.NamedRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.PhysicalBodyRequirement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class CherubRace extends ArchAngelRace {
   protected static final AngelConfig.Cherub CONFIG = ((AngelConfig)ConfigRegistry.getConfig(AngelConfig.class)).Cherub;

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Nullable
   @Override
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.SERAPH.get();
   }

   @Nullable
   @Override
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.SERAPH.get();
   }

   @Nullable
   @Override
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.SERAPH.get());
      return list;
   }

   @Override
   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      return List.of((ManasRace)MysticismRaces.ARCH_ANGEL.get());
   }

   @Override
   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of(new AwakenRequirement(), 50.0F, new NamedRequirement(), 50.0F, new PhysicalBodyRequirement(), 50.0F);
   }

   @Override
   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      return MysticismRaceUtils.parseResourceIntoSkills(CONFIG.intrinsicSkills);
   }

   public void onRaceSet(ManasRaceInstance instance, LivingEntity living) {
      Skills storage = SkillAPI.getSkillsFrom(living);
      Optional<ManasSkillInstance> optional = storage.getSkill((ManasSkill)IntrinsicMysticismSkills.MAGISTEEL_BODY.get());
      if (!optional.isEmpty()) {
         ManasSkillInstance skillInstance = optional.get();
         if (skillInstance.isTemporarySkill()) {
            instance.addIntrinsicSkill((ManasSkill)IntrinsicMysticismSkills.MAGISTEEL_BODY.get());
            SkillHelper.learnSkill(living, (ManasSkill)IntrinsicMysticismSkills.MAGISTEEL_BODY.get());
         }
      }
   }
}
