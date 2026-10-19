package io.github.Memoires.mysticism.race.direwolf.scorch;

import io.github.Memoires.mysticism.config.race.direwolf.SpecialDirewolfConfig;
import io.github.Memoires.mysticism.race.MysticismRaceUtils;
import io.github.Memoires.mysticism.race.direwolf.DirewolfRace;
import io.github.Memoires.mysticism.race.template.MysticismEvolutionRequirement;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.AbilityRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.jetbrains.annotations.Nullable;

public class ScorchWolfRace extends DirewolfRace {
   protected static final SpecialDirewolfConfig.ScorchWolf CONFIG = ((SpecialDirewolfConfig)ConfigRegistry.getConfig(SpecialDirewolfConfig.class)).ScorchWolf;

   public ScorchWolfRace() {
      this.addAttributeModifier(Attributes.STEP_HEIGHT, DEFAULT_RACE_ID, CONFIG.stepHeight, Operation.ADD_VALUE);
   }

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Nullable
   @Override
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.BLAZING_SCORCH_WOLF.get();
   }

   @Nullable
   @Override
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.STORM_SPIRIT_WOLF.get();
   }

   @Nullable
   @Override
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.BLAZING_SCORCH_WOLF.get();
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.BLAZING_SCORCH_WOLF.get());
      return list;
   }

   public List<ManasRace> getPreviousEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.DIREWOLF.get());
      return list;
   }

   @Override
   public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
      return MysticismRaceUtils.parseResourceIntoSkills(CONFIG.intrinsicSkills);
   }

   public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
      return Map.of(
         new EPRequirement(CONFIG.epRequirement),
         20.0F,
         new AbilityRequirement(
            (ManasSkill)SkillAPI.getSkillRegistry().get(ResourceLocation.parse(CONFIG.abilityRequirement1)), CONFIG.abilityMasteryRequirement1
         ),
         20.0F,
         new AbilityRequirement(
            (ManasSkill)SkillAPI.getSkillRegistry().get(ResourceLocation.parse(CONFIG.abilityRequirement2)), CONFIG.abilityMasteryRequirement2
         ),
         20.0F,
         new AbilityRequirement(
            (ManasSkill)SkillAPI.getSkillRegistry().get(ResourceLocation.parse(CONFIG.abilityRequirement3)), CONFIG.abilityMasteryRequirement3
         ),
         20.0F,
         new MysticismEvolutionRequirement.BiomeRequirementAnyTags(List.of(BiomeTags.IS_NETHER)),
         20.0F
      );
   }
}
