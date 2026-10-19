package io.github.Memoires.mysticism.race.forgotten.empty;

import io.github.Memoires.mysticism.config.race.ForgottenConfig;
import io.github.Memoires.mysticism.race.MysticismRaceUtils;
import io.github.Memoires.mysticism.race.template.MysticismEvolutionRequirement;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import io.github.manasmods.tensura.registry.entity.HumanEntityTypes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class RevenantRace extends EmptyRace {
   protected static final ForgottenConfig.Revenant CONFIG = ((ForgottenConfig)ConfigRegistry.getConfig(ForgottenConfig.class)).Revenant;

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.DIVINE_INFERIUS.get());
      return list;
   }

   @Nullable
   @Override
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return null;
   }

   @Nullable
   @Override
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.DIVINE_INFERIUS.get();
   }

   @Override
   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.EMPTY.get());
      return list;
   }

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Override
   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of(
         new EPRequirement(CONFIG.epRequirement),
         60.0F,
         new MysticismEvolutionRequirement.SlayRequirement((EntityType<?>)HumanEntityTypes.FOLGEN.get(), 1),
         5.0F,
         new MysticismEvolutionRequirement.SlayRequirement((EntityType<?>)HumanEntityTypes.KIRARA_MIZUTANI.get(), 1),
         5.0F,
         new MysticismEvolutionRequirement.SlayRequirement((EntityType<?>)HumanEntityTypes.KYOYA_TACHIBANA.get(), 1),
         5.0F,
         new MysticismEvolutionRequirement.SlayRequirement((EntityType<?>)HumanEntityTypes.MAI_FURUKI.get(), 1),
         5.0F,
         new MysticismEvolutionRequirement.SlayRequirement((EntityType<?>)HumanEntityTypes.MARK_LAUREN.get(), 1),
         5.0F,
         new MysticismEvolutionRequirement.SlayRequirement((EntityType<?>)HumanEntityTypes.SHINJI_TANIMURA.get(), 1),
         5.0F,
         new MysticismEvolutionRequirement.SlayRequirement((EntityType<?>)HumanEntityTypes.SHIN_RYUSEI.get(), 1),
         5.0F,
         new MysticismEvolutionRequirement.SlayRequirement((EntityType<?>)HumanEntityTypes.SHOGO_TAGUCHI.get(), 1),
         5.0F
      );
   }

   @Override
   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      return MysticismRaceUtils.parseResourceIntoSkills(CONFIG.intrinsicSkills);
   }
}
