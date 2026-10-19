package io.github.Memoires.mysticism.race.direwolf.brown;

import io.github.Memoires.mysticism.config.race.direwolf.DirewolfConfig;
import io.github.Memoires.mysticism.race.MysticismRaceUtils;
import io.github.Memoires.mysticism.race.direwolf.DirewolfRace;
import io.github.Memoires.mysticism.race.template.MysticismEvolutionRequirement;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.Memoires.mysticism.storage.MysticismStorages;
import io.github.Memoires.mysticism.storage.element.IElement;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.config.race.RaceConfig.Default;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.race.template.EvolutionRequirement.EPRequirement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.jetbrains.annotations.Nullable;

public class BrownFangRace extends DirewolfRace {
   protected static final DirewolfConfig.BrownFang CONFIG = ((DirewolfConfig)ConfigRegistry.getConfig(DirewolfConfig.class)).BrownFang;

   public BrownFangRace() {
      this.addAttributeModifier(Attributes.STEP_HEIGHT, DEFAULT_RACE_ID, CONFIG.stepHeight, Operation.ADD_VALUE);
   }

   @Override
   public Default getDefaultConfig() {
      return CONFIG;
   }

   @Nullable
   @Override
   public ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.MYSTICAL_BROWN_FANG.get();
   }

   @Nullable
   @Override
   public ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.MOUNTAIN_SPIRIT_WOLF.get();
   }

   @Nullable
   @Override
   public ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
      return (ManasRace)MysticismRaces.MYSTICAL_BROWN_FANG.get();
   }

   @Override
   public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.MYSTICAL_BROWN_FANG.get());
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
      EvolutionRequirement ep = new EPRequirement(CONFIG.epRequirement);
      EvolutionRequirement biomeOrHeight = new MysticismEvolutionRequirement.CompositeRequirement(
         List.of(
            new MysticismEvolutionRequirement.BiomeRequirementAnyTags(List.of(BiomeTags.IS_HILL)),
            new MysticismEvolutionRequirement.HeightRequirement(MysticismEvolutionRequirement.HeightRequirement.Mode.LOWER, 64)
         )
      );
      return Map.of(ep, 50.0F, biomeOrHeight, 50.0F);
   }

   public void onRaceSet(ManasRaceInstance instance, LivingEntity living) {
      IElement element = MysticismStorages.getElementFrom(living);
      if (element != null) {
         element.setElement(Element.EARTH);
         element.markDirty();
      }
   }
}
